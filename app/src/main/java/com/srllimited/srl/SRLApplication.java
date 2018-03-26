package com.srllimited.srl;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.srllimited.srl.serverapis.ApiRequestManager;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import java.util.Map;

/**
 * Created by RuchiTiwari on 1/9/2017.
 */
public class SRLApplication extends Application
{
	public static SRLApplication mInstance1;
	private static SRLApplication mInstance;

	public static synchronized SRLApplication getInstance()
	{
		return mInstance;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		/*AppsFlyerConversionListener conversionDataListener =
				new AppsFlyerConversionListener() {
					@Override
					public void onInstallConversionDataLoaded(Map<String, String> map) {

					}

					@Override
					public void onInstallConversionFailure(String s) {

					}

					@Override
					public void onAppOpenAttribution(Map<String, String> map) {

					}

					@Override
					public void onAttributionFailure(String s) {

					}
					//...
				};
		AppsFlyerLib.getInstance().init("zGXJrkpHk7dx5ZMKPbQTiY"*//*AF_DEV_KEY*//*, getConversionListener(), getApplicationContext());
		AppsFlyerLib.getInstance().startTracking(this);*/
		mInstance = this;
		ApiRequestManager.startup(this);
	}

	@Override
	protected void attachBaseContext(final Context base)
	{
		super.attachBaseContext(base);
		MultiDex.install(this);
	}
}
