package com.srllimited.srl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.srllimited.srl.adapters.NotificationsAdapter;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.NotificationsData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.database.NotificationDatabase;
import com.srllimited.srl.interfaces.AlertListner;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.Log;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import info.hoang8f.android.segmented.SegmentedGroup;

public class NotificationsActivity extends MenuBaseActivity
{

	public static Activity notifications;
	Context context;
	NotificationDatabase notificationsDB;
	RecyclerView mRecyclerView;
	RecyclerView.Adapter mAdapter;
	List<NotificationsData> mNotificationsDatas = new ArrayList<>();

	SegmentedGroup segmented3;

	boolean isPersonal = true;

	Button clearAll;
	private JSONArray array = null;
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			switch (request.getReferralCode())
			{
				case GET_NOTIFICATION:
				{

					JSONObject jsonObject = serverResponseData.getObjectData();
					try
					{
						if (jsonObject != null)
							array = jsonObject.getJSONArray("NOTIFICATIONS");
					}
					catch (Exception e)
					{

					}

					new AsyncTaskRunner().execute();

				}
					break;

				case DELETE_NOTIFICATION:
				{

					try
					{
						if (serverResponseData.getObjectData() != null
								&& serverResponseData.getObjectData().getBoolean("RESPONSE"))
						{

							Toast.makeText(context, "Notifications has been deleted successfully.. ", Toast.LENGTH_LONG)
									.show();
							if (isPersonal)
							{ // delete type 1 & 3
								notificationsDB.deletetNotificationType(1);
							}
							else
							{ // delete type 2
								notificationsDB.deletetNotificationType(2);
							}
							clearAll.setVisibility(View.GONE);

							mNotificationsDatas.clear();

							mAdapter.notifyDataSetChanged();
						}
						else
						{
							Toast.makeText(context, "Failed to delete the notifications.. ", Toast.LENGTH_LONG).show();
						}
					}
					catch (Exception e)
					{

					}

				}
					break;

			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.notificationslist);
		context = NotificationsActivity.this;
		notifications = this;
		notificationsDB = new NotificationDatabase(getApplicationContext());
		mRecyclerView = (RecyclerView) findViewById(R.id.notificationRecyclerview);
		header_loc_name.setText("Notifications");
		header_loc_name.setEnabled(false);
		clearAll = (Button) findViewById(R.id.clearAll);

		mRecyclerView = (RecyclerView) findViewById(R.id.notificationRecyclerview);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		mRecyclerView.setHasFixedSize(true);
		mAdapter = new NotificationsAdapter(NotificationsActivity.this, mNotificationsDatas);
		mRecyclerView.setAdapter(mAdapter);

		try
		{
			String unreadNotification = SharedPrefsUtil.getStringPreference(context, "notificationscount");
			SharedPrefsUtil.setStringPreference(context, "readmsg", unreadNotification + "");
		}
		catch (Exception e)
		{

		}
		try
		{
			String unreadNotification = SharedPrefsUtil.getStringPreference(context, "notificationscount");
			String readNotification = SharedPrefsUtil.getStringPreference(context, "readmsg");

			int displayNotification = Util.convertandcal(unreadNotification, readNotification);

			if (displayNotification != 0)
			{
				String notificationscount = displayNotification + "";
				if (Validate.notEmpty(notificationscount))
				{
					notification_count.setText(notificationscount);
					notification_count.setVisibility(View.VISIBLE);
				}
				else
					notification_count.setVisibility(View.GONE);
			}
			else
				notification_count.setVisibility(View.GONE);
		}
		catch (Exception e)
		{

			Log.e("unread", e + "");
		}
		//        mNotificationsDatas.clear();
		//        setNotificationsAdapter();

		//        if (mNotificationsDatas == null || mNotificationsDatas.isEmpty()) {

		if (RestApiCallUtil.isOnline(context))
			sendRequest(ApiRequestHelper.getNotifications(context, Util.getStoredUsername(context),
					Constants.devicetobepassed + "", "ANDROID"));
		else
			RestApiCallUtil.NetworkError(context);
		//        }

		segmented3 = (SegmentedGroup) findViewById(R.id.segmented3);

		segmented3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(final RadioGroup radioGroup, final int checkedId)
			{
				switch (checkedId)
				{

					case R.id.personals:
						isPersonal = true;
						setPersonalsNotificationsAdapter();
						break;
					case R.id.offers:
						isPersonal = false;
						setOffersNotificationsAdapter();
						break;

				}
			}
		});

		clearAll.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				Util.confirmAlert(NotificationsActivity.this, "", "Do you want to clear all Notification ?",
						new AlertListner()
						{
							@Override
							public void onOK()
							{
								if (isPersonal)
								{

								}
								String notifications = notificationsDB.getNotificationIds(isPersonal);

								sendRequest(ApiRequestHelper.deleteNotifications(context, notifications + "",
										Util.getStoredUsername(context), Constants.devicetobepassed + "", "ANDROID"));

							}

							@Override
							public void onCancel()
							{

							}
						});

			}
		});
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	public void updateAdapter()
	{
		mAdapter.notifyDataSetChanged(); //update adapter
	}

	@Override
	protected void onResume()
	{
		mAdapter.notifyDataSetChanged();
		super.onResume();
	}

	private void setOrderFromAsync(JSONArray jArray)
	{
		if (Validate.isNull(jArray))
			return;

		for (int i = 0; i < jArray.length(); i++)
		{
			SharedPrefsUtil.setStringPreference(context, "notificationscount", jArray.length() + "");
			//            notification_count.setVisibility(View.VISIBLE);
			//            notification_count.setText(jArray.length()+"");
			JSONObject jsonObject = jArray.optJSONObject(i);
			NotificationsData notificationsData = NotificationsData.init(jsonObject);
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
	}

	private void setPersonalsNotificationsAdapter()
	{
		mNotificationsDatas.clear();
		try
		{
			mNotificationsDatas.addAll(notificationsDB.getNotificationByType(1));
		}
		catch (Exception e)
		{

		}
		if (Validate.notNull(mNotificationsDatas))
		{
			sortByDate();
			mAdapter.notifyDataSetChanged();
			if (mNotificationsDatas.size() > 0)
				clearAll.setVisibility(View.VISIBLE);
			else
				clearAll.setVisibility(View.GONE);
		}
		else
			clearAll.setVisibility(View.GONE);
	}

	private void setOffersNotificationsAdapter()
	{
		mNotificationsDatas.clear();
		try
		{
			mNotificationsDatas.addAll(notificationsDB.getNotificationByType(2));
		}
		catch (Exception e)
		{

		}
		if (Validate.notNull(mNotificationsDatas))
		{
			sortByDate();
			mAdapter.notifyDataSetChanged();
			if (mNotificationsDatas.size() > 0)
				clearAll.setVisibility(View.VISIBLE);
			else
				clearAll.setVisibility(View.GONE);
		}
		else
			clearAll.setVisibility(View.GONE);
	}

	private void sortByDate()
	{
		Collections.sort(mNotificationsDatas, new Comparator<NotificationsData>()
		{
			@Override
			public int compare(NotificationsData o1, NotificationsData o2)
			{

				final long d1 = o1.getDT_TIME();
				final long d2 = o2.getDT_TIME();
				return (d1 < d2) ? -1 : (d1 < d2) ? 1 : 0;

			}
		});

		Collections.reverse(mNotificationsDatas);
	}

	private class AsyncTaskRunner extends AsyncTask<String, String, String>
	{

		private String resp;

		@Override
		protected String doInBackground(String... params)
		{
			setOrderFromAsync(array);
			return resp;
		}

		@Override
		protected void onPostExecute(String result)
		{
			Util.hideProgressDialog();

			RadioButton personals = (RadioButton) findViewById(R.id.personals);
			personals.setChecked(true);
			if (mNotificationsDatas != null)
			{
				setPersonalsNotificationsAdapter();
			}
		}

		@Override
		protected void onPreExecute()
		{
			Util.hideProgressDialog();
			Util.showProgressDialog(context, "Fetching Notifications...");
		}

		@Override
		protected void onProgressUpdate(String... text)
		{

		}
	}
}