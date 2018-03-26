
package com.srllimited.srl.location;

import android.content.Context;
import android.location.Location;

public class LocationMonitorManager
{
	private LocationMonitor mLocationMonitor;

	public LocationMonitorManager(final Context context,
			final LocationMonitor.OnLocationChangedListener locationChangedListener)
	{
		this.mLocationMonitor = new LocationMonitor(context);
		this.mLocationMonitor.setLocationUpdatedListener(locationChangedListener);
	}

	public Location getPreviousBestLocation()
	{
		if (mLocationMonitor != null && mLocationMonitor.getLastLocation() != null)
		{
			return mLocationMonitor.getLastLocation();
		}
		return null;
	}

	public void startLocation()
	{
		mLocationMonitor.startLocation();
	}

	public void stopLocation()
	{
		mLocationMonitor.stopLocation();
	}

}
