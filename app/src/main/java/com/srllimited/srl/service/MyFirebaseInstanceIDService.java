package com.srllimited.srl.service;

/**
 * Created by Codefyne on 28-04-2017.
 */

import com.appsflyer.AppsFlyerLib;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.util.SharedPrefsUtil;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService
{
	private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

	@Override
	public void onTokenRefresh()
	{
		super.onTokenRefresh();
		String refreshedToken = FirebaseInstanceId.getInstance().getToken();

		// Saving reg id to shared preferences
		storeRegIdInPref(refreshedToken);
		Constants.RegID = refreshedToken;
		// sending reg id to your server
		sendRegistrationToServer(refreshedToken);

		AppsFlyerLib.getInstance().updateServerUninstallToken(getApplicationContext(), refreshedToken); // send FCM TOken to Appflyer to track the uninstall event

		// Notify UI that registration has completed, so the progress indicator can be hidden.
		Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
		registrationComplete.putExtra("token", refreshedToken);
		LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
	}

	private void sendRegistrationToServer(final String token)
	{
		// sending gcm token to server
		Log.e(TAG, "sendRegistrationToServer: " + token);
	}

	private void storeRegIdInPref(String token)
	{

		SharedPrefsUtil.setStringPreference(MyFirebaseInstanceIDService.this, Constants.FCM_RegId, token + "");

		/* SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString("regId", token);
		editor.commit();*/
	}
}
