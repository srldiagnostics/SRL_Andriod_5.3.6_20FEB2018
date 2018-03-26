package com.srllimited.srl.location;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.srllimited.srl.permission.DangerousPermResponseCallBack;
import com.srllimited.srl.permission.DangerousPermissionResponse;
import com.srllimited.srl.permission.DangerousPermissionUtils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.format.DateUtils;

public class LocationMonitor
{
	private static float mLocationSmallestDisplacement = 200.0f;
	private static int REQUEST_CODE_START_LOCATION_UPDATES = 101;
	private static int REQUEST_CODE_STOP_LOCATION_UPDATES = 102;
	private Context mContext;
	private Location mLastLocation;
	private GoogleApiClient mGoogleApiClient;
	private LocationRequest mLocationRequest;
	private MyLocationListener mLocationListener;
	private OnLocationChangedListener mLocationUpdatedListener;

	public LocationMonitor(final Context context)
	{
		mContext = context;
		buildGoogleApiClient();
	}

	public static float getSmallestDisplacement()
	{
		return mLocationSmallestDisplacement;
	}

	public static void setSmallestDisplacement(final float smallestDisplacement)
	{
		LocationMonitor.mLocationSmallestDisplacement = mLocationSmallestDisplacement;
	}

	public Location getLastLocation()
	{
		return mLastLocation;
	}

	public OnLocationChangedListener getLocationUpdatedListener()
	{
		return mLocationUpdatedListener;
	}

	public void setLocationUpdatedListener(final OnLocationChangedListener locationChangedListener)
	{
		mLocationUpdatedListener = locationChangedListener;
	}

	public void startLocation()
	{
		if (mLocationListener == null)
		{
			if (mGoogleApiClient != null)
			{
				mGoogleApiClient.connect();
			}
		}
	}

	public void stopLocation()
	{
		stopLocationUpdates();
	}

	private synchronized void buildGoogleApiClient()
	{
		mGoogleApiClient = new GoogleApiClient.Builder(mContext)
				.addConnectionCallbacks(new MyGoogleApiClientConnectionCallbacks())
				.addOnConnectionFailedListener(new MyGoogleApiClientOnConnectionFailedListener())
				.addApi(LocationServices.API).build();
	}

	private void startLocationUpdates()
	{
		createLocationRequest();
		mLocationListener = new MyLocationListener();

		if (ActivityCompat.checkSelfPermission(mContext,
				Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
		{
			return;
		}
		LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mLocationListener);
	}

	private void createLocationRequest()
	{
		mLocationRequest = new LocationRequest();
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setSmallestDisplacement(getSmallestDisplacement());
		//mLocationRequest.setInterval(10 * DateUtils.SECOND_IN_MILLIS);
		mLocationRequest.setInterval(5 * DateUtils.MINUTE_IN_MILLIS); //prevent excesive wakeup

	}

	private void stopLocationUpdates()
	{
		handleTaskWithUserPermission(REQUEST_CODE_STOP_LOCATION_UPDATES);
	}

	private void updateLocation(Location location, Location lastLocation)
	{
		if (mLocationUpdatedListener != null)
		{
			mLocationUpdatedListener.onLocationChanged(location, lastLocation);
		}
	}

	private void handleTaskWithUserPermission(int requestCode)
	{
		DangerousPermissionUtils
				.getPermission(mContext,
						new String[] { Manifest.permission.ACCESS_FINE_LOCATION,
								Manifest.permission.ACCESS_COARSE_LOCATION },
						requestCode)
				.enqueue(new DangerousPermResponseCallBack()
				{
					@Override
					public void onComplete(final DangerousPermissionResponse permissionResponse)
					{
						if (permissionResponse.isGranted())
						{
							if (permissionResponse.getRequestCode() == REQUEST_CODE_START_LOCATION_UPDATES)
							{
								if (ActivityCompat.checkSelfPermission(mContext,
										Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
								{
									return;
								}
								Location mLastLocation = LocationServices.FusedLocationApi
										.getLastLocation(mGoogleApiClient);
								if (mLastLocation != null)
								{
									if (LocationMonitor.this.mLastLocation == null)
									{

										LocationMonitor.this.mLastLocation = mLastLocation;

									}

									updateLocation(mLastLocation, LocationMonitor.this.mLastLocation);
								}

								startLocationUpdates();
							}
							else if (permissionResponse.getRequestCode() == REQUEST_CODE_STOP_LOCATION_UPDATES)
							{
								if (mLocationListener != null)
								{
									LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,
											mLocationListener);
								}
							}
						}
					}
				});
	}

	public interface OnLocationChangedListener
	{
		void onLocationChanged(Location location, Location lastLocation);
	}

	private class MyGoogleApiClientConnectionCallbacks implements GoogleApiClient.ConnectionCallbacks
	{
		@Override
		public void onConnected(@Nullable final Bundle bundle)
		{
			handleTaskWithUserPermission(REQUEST_CODE_START_LOCATION_UPDATES);
		}

		@Override
		public void onConnectionSuspended(final int i)
		{

		}
	}

	private class MyGoogleApiClientOnConnectionFailedListener implements GoogleApiClient.OnConnectionFailedListener
	{

		@Override
		public void onConnectionFailed(@NonNull final ConnectionResult connectionResult)
		{

		}
	}

	private class MyLocationListener implements LocationListener
	{
		public void onLocationChanged(final Location loc)
		{
			if (mLastLocation == null)
			{
				mLastLocation = loc;
			}

			updateLocation(loc, mLastLocation);
			mLastLocation = loc;
		}
	}
}
