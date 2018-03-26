package com.srllimited.srl.util;

import com.srllimited.srl.Dashboard;
import com.srllimited.srl.GPSTracker;
import com.srllimited.srl.location.LocationMonitor;
import com.srllimited.srl.location.LocationMonitorManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;

/**
 * Created by RuchiTiwari on 12/31/2016.
 */

public class LocationHandler
{
	private static Location sLocation;
	public boolean isRunning;
	private Intent selectedIntent = null;
	private Context mContext;
	private LocationMonitorManager mLocationMonitorManager;
	private onLocationResponseListener mLocationChangedListener;
	private ProgressDialog mProgressDialog;

	public LocationHandler(final Context context, final onLocationResponseListener locationResponseListener)
	{
		this.mContext = context;
		this.mLocationChangedListener = locationResponseListener;
	}

	public static Location getLocation()
	{
		return sLocation;
	}

	private static void setLocation(final Location location)
	{
		LocationHandler.sLocation = location;
	}

	public static boolean hasLocation()
	{
		return !Validate.isNull(getLocation());
	}

	public static boolean canGetLocation(Context context)
	{
		GPSTracker gpsTracker = new GPSTracker(context);
		Boolean gpsEnabled = gpsTracker.canGetLocation();
		return gpsEnabled;
	}

	public Intent getSelectedClass()
	{
		return selectedIntent;
	}

	public void setSelectedClass(final Intent selectedClass)
	{
		this.selectedIntent = selectedClass;
	}

	public void checkWithLastLocation()
	{
		isRunning = true;
		if (!LocationHandler.hasLocation())
		{
			getNewLocation();
		}
		else
		{
			notifyResponseResult(true);
		}
	}

	public void getNewLocation()
	{
		isRunning = true;
		if (!showDisabledGpsSettings())
		{
			initLocationMonitorManage(mContext);
		}
	}

	private boolean showDisabledGpsSettings()
	{
		if (!canGetLocation(mContext))
		{
			//			AlertDialog.Builder builder = new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_LIGHT);
			//			builder.setMessage("Location Services Disabled")
			//			       .setPositiveButton("Cancel",
			//			                          new DialogInterface.OnClickListener()
			//			                          {
			//				                          @Override
			//				                          public void onClick(DialogInterface dialog, int id)
			//				                          {
			//					                          dialog.dismiss();
			//					                          notifyResponseResult(false);
			//				                          }
			//			                          })
			//			       .setNegativeButton("Enable",
			//			                          new DialogInterface.OnClickListener()
			//			                          {
			//				                          @Override
			//				                          public void onClick(DialogInterface dialog, int id)
			//				                          {
			//					                          dialog.dismiss();
			//
			//					                          Intent myIntent = new Intent(
			//							                          android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			//					                          mContext.startActivity(myIntent);
			//
			//					                          initLocationMonitorManage(mContext);
			//				                          }
			//			                          });
			//
			//			builder.create().show();

			return true;
		}
		return false;
	}

	public void showProgressDialog()
	{
		if (mProgressDialog != null && mProgressDialog.isShowing())
		{
			return;
		}

		if (canGetLocation(mContext))
		{
			try
			{
				hideProgressDialog();

				mProgressDialog = new ProgressDialog(mContext, AlertDialog.THEME_HOLO_LIGHT);
				mProgressDialog.setMessage("Fetching Location...");
				mProgressDialog.setIndeterminate(false);
				mProgressDialog.setCancelable(false);
				mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
						new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog, int which)
							{
								notifyResponseResult(false);
								dialog.dismiss();
							}
						});
				mProgressDialog.show();

				Runnable progressRunnable = new Runnable()
				{

					@Override
					public void run()
					{
						if (mProgressDialog != null)
							mProgressDialog.dismiss();
					}
				};

				Handler pdCanceller = new Handler();
				pdCanceller.postDelayed(progressRunnable, 3000);
			}
			catch (Exception e)
			{
			}
		}
	}

	public void hideProgressDialog()
	{
		try
		{
			if (mProgressDialog != null)
			{
				mProgressDialog.dismiss();
			}
		}
		catch (Exception e)
		{
		}
	}

	private void initLocationMonitorManage(Context context)
	{
		showProgressDialog();
		mLocationMonitorManager = new LocationMonitorManager(context, new LocationMonitor.OnLocationChangedListener()
		{
			@Override
			public void onLocationChanged(final Location location, final Location lastLocation)
			{
				if (location != null)
				{
					if (Dashboard.currentLoc.isEmpty())
					{
						Intent broadcastIntent = new Intent();
						broadcastIntent.setAction(Dashboard.mBroadcasAction);
						broadcastIntent.putExtra(Dashboard.RECEIVE_LOC, "loc");

						mContext.sendBroadcast(broadcastIntent);
					}

					setLocation(location);
					mLocationMonitorManager.stopLocation();
					notifyResponseResult(true);
				}
			}
		});
		mLocationMonitorManager.startLocation();
	}

	private void notifyResult()
	{
		Intent broadcastIntent = new Intent();
		broadcastIntent.setAction(Dashboard.mBroadcastStringAction);
		broadcastIntent.putExtra(Dashboard.RECEIVE_LOC, "loc");
		mContext.sendBroadcast(broadcastIntent);
	}

	private void notifyResponseResult(boolean responseResult)
	{
		hideProgressDialog();
		if (Validate.notNull(mLocationChangedListener))
		{
			mLocationChangedListener.onResponse(responseResult);
		}
	}

	public interface onLocationResponseListener
	{
		void onResponse(boolean success);
	}

	//	public static boolean showDisabledGpsSettings(final Context context)
	//	{
	//		if (!canGetLocation(context))
	//		{
	//			AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
	//			builder.setMessage("Location Services Disabled")
	//			       .setPositiveButton("Cancel",
	//			                          new DialogInterface.OnClickListener()
	//			                          {
	//				                          @Override
	//				                          public void onClick(DialogInterface dialog, int id)
	//				                          {
	//					                          dialog.dismiss();
	//				                          }
	//			                          })
	//			       .setNegativeButton("Enable",
	//			                          new DialogInterface.OnClickListener()
	//			                          {
	//				                          @Override
	//				                          public void onClick(DialogInterface dialog, int id)
	//				                          {
	//					                          Intent myIntent = new Intent(
	//							                          android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
	//					                          context.startActivity(myIntent);
	//				                          }
	//			                          });
	//
	//			builder.create().show();
	//
	//			return true;
	//		}
	//		return false;
	//	}

}
