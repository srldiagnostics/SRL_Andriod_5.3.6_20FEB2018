package com.srllimited.srl;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

/**
 * Created by sri on 12/19/2016.
 */

public class GPSTracker extends Service implements LocationListener
{
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 2; // 2 meters
	private static final long MIN_TIME_BW_UPDATES = 1000 * 10 * 1; // 10 Seconds
	private final Context mContext;
	protected LocationManager locationManager;
	// flag for GPS status
	boolean isGPSEnabled = false;
	// flag for network status
	boolean isNetworkEnabled = false;
	// flag for GPS status
	boolean canGetLocation = false;
	// The minimum distance to change Updates in meters
	Location location; // location
	// The minimum time between updates in milliseconds
	double latitude; // latitude
	// Declaring a Location Manager
	double longitude; // longitude

	public GPSTracker(Context context)
	{
		this.mContext = context;
		getLocation();
	}

	public Location getLocation()
	{
		try
		{
			locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
			// getting GPS status
			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			// getting network status
			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			if (!isGPSEnabled)
			{
				// no network provider is enabled
			}
			else
			{
				this.canGetLocation = true;
				// First get location from Network Provider
				if (isNetworkEnabled)
				{
					try
					{
						locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

						if (locationManager != null)
						{
							location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
							if (location != null)
							{
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}

					}
					catch (Exception e)
					{

					}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled)
				{
					if (location == null)
					{
						locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

						if (locationManager != null)
						{
							location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null)
							{
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return location;
	}

	/**
	 * Stop using GPS listener Calling this function will stop using GPS in your
	 * app
	 */
	public void stopUsingGPS()
	{
		/*if (locationManager != null) {
		    locationManager.removeUpdates(GPSTracker.this);
		}*/
		/*if (locationManager != null) {
		    if (ContextCompat.checkSelfPermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}) == PackageManager.PERMISSION_GRANTED
		            || ContextCompat.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
		        locationManager.removeUpdates(GPSTracker.this);
		    }
		}*/
		try
		{
			locationManager.removeUpdates(GPSTracker.this);
		}
		catch (SecurityException e)
		{

		}
	}

	/**
	 * Function to get latitude
	 */
	public double getLatitude()
	{
		if (location != null)
		{
			latitude = location.getLatitude();
		}
		// return latitude
		return latitude;
	}

	/**
	 * Function to get longitude
	 */
	public double getLongitude()
	{
		if (location != null)
		{
			longitude = location.getLongitude();
		}
		// return longitude
		return longitude;
	}

	/**
	 * Function to check GPS/wifi enabled
	 *
	 * @return boolean
	 */
	public boolean canGetLocation()
	{
		return this.canGetLocation;
	}

	public boolean resetLocation()
	{
		return false;
	}

	@Override
	public void onLocationChanged(Location location)
	{
	}

	@Override
	public void onProviderDisabled(String provider)
	{
	}

	@Override
	public void onProviderEnabled(String provider)
	{
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
	}

	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}
}
