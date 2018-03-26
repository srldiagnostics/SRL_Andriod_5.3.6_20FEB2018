package com.srllimited.srl.service;

/**
 * Created by Codefyne on 28-04-2017.
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONObject;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.srllimited.srl.Dashboard;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.util.NotificationUtils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MyFirebaseMessagingService extends FirebaseMessagingService
{

	private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

	private NotificationUtils notificationUtils;

	public static String getTimeMilliSec()
	{
		Calendar calendar = Calendar.getInstance();
		long timestamp = calendar.getTimeInMillis();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		calendar.setTimeInMillis(timestamp);
		return dateFormat.format(calendar.getTime());

		/* SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
		    Date date = format.parse(String.valueOf(timestamp));
		    return date.getTime();
		} catch (ParseException e) {
		    e.printStackTrace();
		}*/

	}

	@Override
	public void onMessageReceived(RemoteMessage remoteMessage)
	{
		Log.e(TAG, "From: " + remoteMessage.getFrom());

		/* if(remoteMessage !=null) {

		    String remoteMessage1 =  remoteMessage.toString();//"Bundle[{google.sent_time=1502440726818, gcm.notification.e=1, gcm.notification.sound=default, gcm.notification.title=SRL Diagnostics, body=Report is ready!!, from=775538925339, type=1, gcm.notification.sound2=Enabled, sound=Enabled, title=SRL Diagnostics, google.message_id=0:1502440726835086%83e41f9983e41f99, gcm.notification.body=Report is ready!!, gcm.notification.type=1, collapse_key=com.srllimited.srl}]";
		    JSONObject jsonObject = null;

		    String[] sss = remoteMessage1.split("Bundle");
		    String[] content = sss[1].split(",");
		    JSONObject jsonObject1 = new JSONObject();
		    JSONObject dataObj = new JSONObject();
		    try {
		        jsonObject1.put("title",content[3].split("=")[1]);
		        jsonObject1.put("body",content[4].split("=")[1]);
		        jsonObject1.put("sound",content[8].split("=")[1]);
		        jsonObject1.put("type",content[6].split("=")[1]);
		        dataObj.put("data", jsonObject1);
		    } catch (JSONException e) {
		        e.printStackTrace();
		        try {
		            dataObj.put("data", jsonObject1);
		        } catch (JSONException e1) {
		            e1.printStackTrace();
		        }
		    } if (dataObj !=null) {
		        Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

		        try {
		           // JSONObject json = new JSONObject(remoteMessage.getData().toString());
		            handleDataMessage(dataObj);
		        } catch (Exception e) {
		            Log.e(TAG, "Exception: " + e.getMessage());
		        }
		    }
		}*/

		if (remoteMessage == null)
			return;

		// Check if message contains a notification payload.

		/* if (remoteMessage.getNotification() != null) {
		  handleNotification(remoteMessage.getNotification().getBody());
		}*/
		// Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());

		// Check if message contains a data payload.

		if (remoteMessage.getData().size() > 0)
		{
			Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

			try
			{
				//   {body=Report is ready!!, type=2, sound=Enabled, title=SRL Diagnostics}

				String body = remoteMessage.getData().get("body");
				String type = remoteMessage.getData().get("type");
				String sound = remoteMessage.getData().get("sound");
				String title = remoteMessage.getData().get("title");

				JSONObject json = new JSONObject();
				json.put("body", body);
				json.put("type", type);
				json.put("sound", sound);
				json.put("title", title);

				// JSONObject json = new JSONObject(remoteMessage.getData().toString());
				handleDataMessage(json);
			}
			catch (Exception e)
			{
				Log.e(TAG, "Exception: " + e.getMessage());
			}
		}
	}

	private void handleNotification(String message)
	{
		if (!NotificationUtils.isAppIsInBackground(getApplicationContext()))
		{
			// app is in foreground, broadcast the push message
			Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
			pushNotification.putExtra("title", message);
			LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

			// play notification sound
			NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
			notificationUtils.playNotificationSound();
		}
		else
		{
			// If the app is in background, firebase itself handles the notification
		}
	}

	private void handleDataMessage(JSONObject json)
	{
		Log.e(TAG, "push json: " + json.toString());
		//   {body=Report is ready!!, type=2, sound=Enabled, title=SRL Diagnostics}
		try
		{
			// JSONObject data = json.getJSONObject("data");
			JSONObject data = new JSONObject(json.toString());
			String title = data.getString("title");
			String message = data.getString("body");
			//  boolean isBackground = data.getBoolean("is_background");
			String sound = data.getString("sound");
			String type = data.getString("type");
			// JSONObject payload = data.getJSONObject("payload");

			Log.e(TAG, "title: " + title);
			Log.e(TAG, "message: " + message);
			//  Log.e(TAG, "isBackground: " + isBackground);
			//  Log.e(TAG, "payload: " + payload.toString());
			Log.e(TAG, "imageUrl: " + sound);
			Log.e(TAG, "timestamp: " + type);
			if (!NotificationUtils.isAppIsInBackground(getApplicationContext()))
			{
				// app is in foreground, broadcast the push message
				/* Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
				pushNotification.putExtra("message", message);
				LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

				// play notification sound
				NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
				notificationUtils.playNotificationSound();*/

				Intent resultIntent = new Intent(getApplicationContext(), Dashboard.class);
				resultIntent.setAction(Intent.ACTION_MAIN);
				resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				resultIntent.putExtra("NotifyService", true);

				resultIntent.putExtra(Constants.NOTIFICATIONTYPE, type);
				resultIntent.putExtra(Constants.NOTIFICATIONTitle, title);
				resultIntent.putExtra(Constants.NOTIFICATIONMSG, message);
				// check for image attachment
				showNotificationMessage(getApplicationContext(), title, message, getTimeMilliSec(), resultIntent);

			}
			else
			{
				// app is in background, show the notification in notification tray
				Intent resultIntent = new Intent(getApplicationContext(), Dashboard.class);
				resultIntent.setAction(Intent.ACTION_MAIN);
				resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
				resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
				resultIntent.putExtra("NotifyService", true);

				resultIntent.putExtra(Constants.NOTIFICATIONTYPE, type);
				resultIntent.putExtra(Constants.NOTIFICATIONTitle, title);
				resultIntent.putExtra(Constants.NOTIFICATIONMSG, message);
				// check for image attachment
				showNotificationMessage(getApplicationContext(), title, message, getTimeMilliSec(), resultIntent);
				/* if (TextUtils.isEmpty(imageUrl)) {
				    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
				} else {
				    // image is present, show notification with image
				    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
				}*/
			}
		}
		catch (Exception e)
		{
			Log.e(TAG, "Json Exception: " + e.getMessage());
		}
	}

	/**
	 * Showing notification with text only
	 */
	private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent)
	{
		notificationUtils = new NotificationUtils(context);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
	}

	/**
	 * Showing notification with text and image
	 */
	private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp,
			Intent intent, String imageUrl)
	{
		notificationUtils = new NotificationUtils(context);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
	}
}
