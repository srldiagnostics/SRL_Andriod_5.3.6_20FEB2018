package com.srllimited.srl.service;

import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import com.srllimited.srl.location.LocationMonitor;
import com.srllimited.srl.location.LocationMonitorManager;

import android.app.Service;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Handler;
import android.os.IBinder;
import android.text.format.DateUtils;
import android.util.Log;

public class MonitorService extends Service
{
	public static boolean isRunning = false;

	private static LocationMonitorManager mLocationMonitorManager;

	private final String TAG = "MonitorService";
	private LocationMonitor.OnLocationChangedListener mLocationChangedListener = new LocationMonitor.OnLocationChangedListener()
	{
		@Override
		public void onLocationChanged(final Location location, final Location lastLocation)
		{

			if (location != null)
			{
				Log.d(TAG, location.toString());
			}

			sendLocation(location);
		}
	};

	public static void startLocation()
	{
		if (mLocationMonitorManager != null)
		{
			mLocationMonitorManager.startLocation();

		}
	}

	public static void stopLocation()
	{
		if (mLocationMonitorManager != null)
		{
			mLocationMonitorManager.stopLocation();
		}
	}

	@Override
	public void onCreate()
	{

		//		FCMService.subscribeToTopic(Constants.FCM_TOPIC);
		mLocationMonitorManager = new LocationMonitorManager(this, mLocationChangedListener);
		startLocation();
		callAsynchronousTask();
		Log.d(TAG, ">>>onCreate()");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		isRunning = true;

		if (mLocationMonitorManager == null)
		{
			mLocationMonitorManager = new LocationMonitorManager(this, mLocationChangedListener);
		}

		Log.d(TAG, ">>>onStartCommand()");
		// If we get killed, after returning from here, restart
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		// We don't provide binding, so return null
		return null;
	}

	@Override
	public void onDestroy()
	{
		isRunning = false;
		Log.d(TAG, ">>>onDestroy()");
	}

	private void sendLocation(Location location)
	{
		if (location != null)
		{
			Log.e("location", location.getLatitude() + "");

		}
	}

	//	private void postData(double latitude, double longitude)
	//	{
	//		String jsonString = "{\"to\":\"/topics/" + Constants.FCM_TOPIC + "\" ,\"data\":{\"username\":" + getString(Util.getSavedUsername(getApplicationContext())) + ",\"latitude\":" + getString(
	//				latitude+"") + ",\"longitude\":" + getString(longitude+"") + ",\"address\":" + getString(getCompleteAddressString(latitude, longitude)) + "}}";
	//		Log.d(TAG, jsonString);
	//
	//		UpdateLocation.send(jsonString);
	//	}

	private String getCompleteAddressString(double LATITUDE, double LONGITUDE)
	{
		String strAdd = "";
		Geocoder geocoder = new Geocoder(this, Locale.getDefault());
		try
		{
			List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
			if (addresses != null)
			{
				Address returnedAddress = addresses.get(0);
				StringBuilder strReturnedAddress = new StringBuilder("");

				for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++)
				{
					strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
				}
				strAdd = strReturnedAddress.toString();
				Log.w(TAG, "" + strReturnedAddress.toString());
			}
			else
			{
				Log.w(TAG, "No Address returned!");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.w(TAG, "Canont get Address!");
		}
		return strAdd;
	}

	private String getString(String value)
	{
		return "\"" + value + "\"";
	}

	private void callAsynchronousTask()
	{
		final Handler handler = new Handler();
		Timer timer = new Timer();
		TimerTask doAsynchronousTask = new TimerTask()
		{
			@Override
			public void run()
			{
				handler.post(new Runnable()
				{
					public void run()
					{
						Log.d(TAG, "Service is running...");

						Location location = mLocationMonitorManager.getPreviousBestLocation();
						sendLocation(location);
					}
				});
			}
		};
		timer.schedule(doAsynchronousTask, 0, 15 * DateUtils.SECOND_IN_MILLIS);
	}
}