package com.srllimited.srl.internal;

/**
 * Created by RuchiTiwari on 2/16/2017.
 */

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.srllimited.srl.Dashboard;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.AboutUsData;
import com.srllimited.srl.data.NotificationsData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.database.NotificationDatabase;
import com.srllimited.srl.serverapis.ApiConstants;
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

public class CarouselsTask
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
				case Get_CONTENT:
					setCarouselActionScreens(serverResponseData.getArrayData());
					break;

				case GET_CAROUSELS:
					setCarouselData(serverResponseData.getArrayData());
					break;

				case GET_PRIVACY:
					setAboutUsContent(serverResponseData.getArrayData());
					break;

				case GET_TERMS:
					setTerms(serverResponseData.getArrayData());
					break;

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
				case CHECK_VERSION:

					JSONObject jsonObject = serverResponseData.getFullData();
					Log.v("CHECK_VERSION", jsonObject.toString());
					try
					{
						if (jsonObject != null)
						{
							JSONArray jsonArray = jsonObject.getJSONArray("data");
							if (jsonArray.length() > 0)
							{
								for (int i = 0; i < jsonArray.length(); i++)
								{
									JSONObject jsonObject1 = jsonArray.getJSONObject(i);
									SharedPrefsUtil.setStringPreference(mContext, "SCREEN_ID1",
											jsonObject1.getString("SCREEN_ID") + "");
									SharedPrefsUtil.setStringPreference(mContext, "serverVersion",
											jsonObject1.getString("CONTENT").replace(".", "").toString().trim() + "");
									break;
								}
							}
						}
					}
					catch (Exception e)
					{

					}
					//check_version
					break;
				case GET_LOGO:
					JSONObject jsonObject1 = serverResponseData.getFullData();
					Log.v("LOGO", jsonObject1.toString());
					setLOGO(serverResponseData.getArrayData());
					break;
				case GET_CALL_US:
					setCallUS(serverResponseData.getArrayData());
					break;
			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{

		}
	};

	public CarouselsTask(Context context)
	{
		mContext = context;
		if (RestApiCallUtil.isOnline(mContext))
		{
			String cityid = SharedPrefsUtil.getStringPreference(context, "selectedcityid");
			sendRequest(ApiRequestHelper.getContent(context, "BANNER_ACTIONS", ApiConstants.appVersion));
			sendRequest(ApiRequestHelper.getLOGO(context, "LOGO", ApiConstants.appVersion));
			sendRequest(ApiRequestHelper.getCALL_US(context, "CALL_US", ApiConstants.appVersion));
			notificationsDB = new NotificationDatabase(mContext);
			if (cityid != null)
				sendRequest(ApiRequestHelper.getCarousels(context, cityid, Util.getStoredUsername(context), ""));
			else
				sendRequest(ApiRequestHelper.getCarousels(context, "6", Util.getStoredUsername(context), ""));
			String terms = SharedPrefsUtil.getStringPreference(mContext, "terms");
			String privacy = SharedPrefsUtil.getStringPreference(mContext, "privacy");

			if (!Validate.notEmpty(privacy))
				sendRequest(ApiRequestHelper.getPrivacy(mContext, "PRIVACY_POLICY", ApiConstants.appVersion));
			if (!Validate.notEmpty(terms))
				sendRequest(ApiRequestHelper.getTermsOfUse(mContext, "TERMS_OF_USE", ApiConstants.appVersion));
			sendRequest(ApiRequestHelper.getNotifications(mContext, Util.getStoredUsername(context),
					Constants.devicetobepassed + "", "ANDROID"));
			String check_version = SharedPrefsUtil.getStringPreference(mContext, "serverVersion");

			// if(!Validate.notEmpty(check_version))
			sendRequest(ApiRequestHelper.getCheckVersion(context, "CHECK_VERSION_ANDROID", ApiConstants.appVersion));
		}
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequestWithoutProgress(mContext, request, mResponseListener);
	}

	private void setLOGO(JSONArray jArray)
	{
		try
		{
			if (Validate.notNull(jArray))
			{

				for (int i = 0; i < jArray.length(); i++)
				{
					JSONObject jsonObject = jArray.getJSONObject(i);
					String screenName = jsonObject.getString("SCREEN_NAME");
					String content = jsonObject.getString("CONTENT");

					if (Validate.notEmpty(screenName) && Validate.notEmpty(content))
					{
						SharedPrefsUtil.setStringPreference(mContext, Constants.LOGO + "", content + "");
					}
				}
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

	}

	private void setCallUS(JSONArray jArray)
	{
		try
		{
			if (Validate.notNull(jArray))
			{

				for (int i = 0; i < jArray.length(); i++)
				{
					JSONObject jsonObject = jArray.getJSONObject(i);
					String screenName = jsonObject.getString("SCREEN_NAME");
					String content = jsonObject.getString("CONTENT");

					if (Validate.notEmpty(screenName) && Validate.notEmpty(content))
					{
						SharedPrefsUtil.setStringPreference(mContext, Constants.CALLUS + "", content + "");
					}
				}
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

	}

	private void setCarouselData(JSONArray jArray)
	{
		int count = 0;
		if (Validate.notNull(jArray))
		{
			for (int i = 0; i < jArray.length(); i++)
			{
				try
				{
					JSONObject jObject = jArray.getJSONObject(i);

					String bannerFlg = jObject.getString("BANNER_FLG");

					if (Validate.notEmpty(bannerFlg) && bannerFlg.equalsIgnoreCase("Y"))
					{
						String imageUrl = jObject.getString("IMAGE_URL");
						if (Validate.notEmpty(imageUrl))
						{
							Log.e("counti", i + "");
							SharedPrefsUtil.setIntegerPreference(mContext, "carouselcount", count);
							SharedPrefsUtil.setStringPreference(mContext, "carouselimg" + count + "", imageUrl);
							SharedPrefsUtil.setLongPreference(mContext, imageUrl + "", jObject.getLong("ACTION"));
							count++;
						}
					}
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
			}

			notifyResult();
		}
	}

	private void notifyResult()
	{
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(Dashboard.mBroadcasAction);
		broadcastIntent.putExtra(Dashboard.RECEIVE_CAROUSELS, "Broadcast Data");
		mContext.sendBroadcast(broadcastIntent);
	}

	private void notifyNotification()
	{
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(Dashboard.mBroadcasActionNot);
		broadcastIntent.putExtra(Dashboard.RECEIVE_NOTIFICATIONS, "Broadcast Notifications");
		mContext.sendBroadcast(broadcastIntent);
	}

	private void setCarouselActionScreens(JSONArray jArray)
	{
		try
		{
			if (Validate.notNull(jArray))
			{

				for (int i = 0; i < jArray.length(); i++)
				{
					JSONObject jsonObject = jArray.getJSONObject(i);
					String screenName = jsonObject.getString("SCREEN_NAME");
					String content = jsonObject.getString("CONTENT");

					if (Validate.notEmpty(screenName) && Validate.notEmpty(content))
					{
						SharedPrefsUtil.setStringPreference(mContext, screenName + "", content + "");
					}
				}
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

	}

	private void setAboutUsContent(JSONArray jArray)
	{

		try
		{
			if (Validate.notNull(jArray))
			{
				mPrivacyPolicyList.clear();
				for (int i = 0; i < jArray.length(); i++)
				{
					JSONObject jsonObject = jArray.getJSONObject(i);
					AboutUsData aboutUsData = new AboutUsData();
					aboutUsData.setSCREEN_ID(screen_id);
					aboutUsData.setCONTENT(jsonObject.getString(content));
					aboutUsData.setMYSRLVER(jsonObject.getString(mysrlver));
					aboutUsData.setSCREEN_NAME(jsonObject.getString(screen_name));
					mPrivacyPolicyList.add(aboutUsData);
				}
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

		if (mPrivacyPolicyList != null)
		{

			for (AboutUsData aboutUsData : mPrivacyPolicyList)
			{
				if (aboutUsData.getSCREEN_NAME() != null)
				{
					if (aboutUsData.getCONTENT() != null && !aboutUsData.getCONTENT().equalsIgnoreCase("null"))
					{
						//                        Log.e("textpolicy", aboutUsData.getCONTENT() + "");

						SharedPrefsUtil.setStringPreference(mContext, "privacy", aboutUsData.getCONTENT() + "");
						//                           terms_of_use = aboutUsData.getCONTENT() + "";
						//                        } else {
						//                            privacyPolicy = aboutUsData.getCONTENT() + "";
						//                        }
						//                        policy_text.setText(aboutUsData.getCONTENT() + "");
					}

				}
			}

		}

	}

	private void setTerms(JSONArray jArray)
	{

		try
		{
			if (Validate.notNull(jArray))
			{
				mPrivacyPolicyList.clear();
				for (int i = 0; i < jArray.length(); i++)
				{
					JSONObject jsonObject = jArray.getJSONObject(i);
					AboutUsData aboutUsData = new AboutUsData();
					aboutUsData.setSCREEN_ID(screen_id);
					aboutUsData.setCONTENT(jsonObject.getString(content));
					aboutUsData.setMYSRLVER(jsonObject.getString(mysrlver));
					aboutUsData.setSCREEN_NAME(jsonObject.getString(screen_name));
					mPrivacyPolicyList.add(aboutUsData);
				}
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

		if (mPrivacyPolicyList != null)
		{

			for (AboutUsData aboutUsData : mPrivacyPolicyList)
			{
				if (aboutUsData.getSCREEN_NAME() != null)
				{
					if (aboutUsData.getCONTENT() != null && !aboutUsData.getCONTENT().equalsIgnoreCase("null"))
					{
						//                        Log.e("textpolicy", aboutUsData.getCONTENT() + "");

						SharedPrefsUtil.setStringPreference(mContext, "terms", aboutUsData.getCONTENT() + "");
						//                           terms_of_use = aboutUsData.getCONTENT() + "";
						//                        } else {
						//                            privacyPolicy = aboutUsData.getCONTENT() + "";
						//                        }
						//                        policy_text.setText(aboutUsData.getCONTENT() + "");
					}

				}
			}

		}

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
