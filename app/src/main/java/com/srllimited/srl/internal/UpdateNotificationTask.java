package com.srllimited.srl.internal;

/**
 * Created by RuchiTiwari on 2/16/2017.
 */

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.srllimited.srl.Dashboard;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.AboutUsData;
import com.srllimited.srl.data.NotificationsData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.database.NotificationDatabase;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class UpdateNotificationTask
{
	private final String screen_id = "SCREEN_ID";
	private final String screen_name = "SCREEN_NAME";
	private final String content = "CONTENT";
	private final String mysrlver = "MYSRLVER";
	List<AboutUsData> mPrivacyPolicyList = new ArrayList<>();
	NotificationDatabase notificationsDB;
	private Context mContext;
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			Log.e("serverresponse", serverResponseData + "");
			switch (request.getReferralCode())
			{
				case GET_NOTIFICATION:
				{
					JSONArray array = null;
					JSONObject jsonObject = serverResponseData.getObjectData();
					try
					{
						if (jsonObject != null)
							array = jsonObject.getJSONArray("NOTIFICATIONS");
					}
					catch (Exception e)
					{

					}

					setNotification(array);

				}
					break;
			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{

		}
	};

	public UpdateNotificationTask(Context context)
	{
		mContext = context;
		if (RestApiCallUtil.isOnline(mContext))
		{
			notificationsDB = new NotificationDatabase(mContext);
			sendRequest(ApiRequestHelper.getNotifications(mContext, Util.getStoredUsername(context),
					Constants.devicetobepassed + "", "ANDROID"));
		}
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequestWithoutProgress(mContext, request, mResponseListener);
	}

	private void notifyNotification()
	{
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(Dashboard.mBroadcasActionNot);
		broadcastIntent.putExtra(Dashboard.RECEIVE_NOTIFICATIONS, "Broadcast Notifications");
		mContext.sendBroadcast(broadcastIntent);
	}

	private void setNotification(JSONArray jArray)
	{
		if (Validate.isNull(jArray))
			return;

		for (int i = 0; i < jArray.length(); i++)
		{
			JSONObject jsonObject = jArray.optJSONObject(i);
			NotificationsData notificationsData = NotificationsData.init(jsonObject);
			SharedPrefsUtil.setStringPreference(mContext, "notificationscount", jArray.length() + "");
			if (Validate.notNull(notificationsData))
			{

				int queue_id = notificationsData.getQUEUE_ID();
				if (Validate.notNull(queue_id))
				{

					NotificationsData storedNotificationsData = null;
					try
					{
						storedNotificationsData = notificationsDB.getNotificationWithId(queue_id);
					}
					catch (Exception e)
					{
					}

					if (Validate.notNull(storedNotificationsData) && storedNotificationsData.getQUEUE_ID() != 0)
					{
						notificationsDB.updateNotifications(notificationsData);
					}
					else
					{
						notificationsDB.createHeaderData(notificationsData);
					}
				}

			}

		}

		notifyNotification();
	}

}
