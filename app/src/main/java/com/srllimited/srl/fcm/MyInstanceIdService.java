package com.srllimited.srl.fcm;

import com.appsflyer.AppsFlyerLib;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Lakhan.Yadav on 5/26/2017.
 */

public class MyInstanceIdService extends FirebaseInstanceIdService
{

	@Override
	public void onTokenRefresh()
	{
		super.onTokenRefresh();

		// Get updated InstanceID token.
		FirebaseInstanceId instance = FirebaseInstanceId.getInstance();
		String refreshedToken;
		if (instance != null)
		{
			refreshedToken = instance.getToken();
			// ...
			//// sendTokenToMyBackend(refreshedToken); // example for general use case
			// ...
			AppsFlyerLib.getInstance().updateServerUninstallToken(getApplicationContext(), refreshedToken); // ADD THIS LINE HERE
		}
	}
}