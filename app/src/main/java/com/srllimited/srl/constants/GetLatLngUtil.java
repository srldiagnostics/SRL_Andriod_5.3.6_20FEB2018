package com.srllimited.srl.constants;

/**
 * Created by RuchiTiwari on 1/16/2017.
 */

import java.util.List;

import com.google.android.gms.vision.barcode.Barcode;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

public class GetLatLngUtil
{

	public static void getLatLng(Context context, String address, GetLatLngResult resultListener)
	{
		GetLatLngAsyncTask latLngAsyncTask = new GetLatLngAsyncTask(context, resultListener);
		latLngAsyncTask.execute(address);
	}

	public static interface GetLatLngResult
	{
		public void onLatLngReceive(double lat, double lng);

		public void onError(String message);
	}

	public static class GetLatLngAsyncTask extends AsyncTask<String, Void, Barcode.GeoPoint>
	{

		private Context mContext;

		private String mAddress;

		private GetLatLngResult mResultListener;

		public GetLatLngAsyncTask(Context mContext, GetLatLngResult resultListener)
		{
			this.mContext = mContext;
			this.mResultListener = resultListener;
		}

		@Override
		protected Barcode.GeoPoint doInBackground(String... params)
		{
			mAddress = params[0];
			return getFromLocation(mAddress);
		}

		@Override
		protected void onPostExecute(Barcode.GeoPoint result)
		{
			super.onPostExecute(result);
			if (mResultListener != null)
			{
				if (result != null)
					mResultListener.onLatLngReceive(result.lat / 1E6, result.lng / 1E6);
				else
					mResultListener.onError(null);
			}
		}

		private Barcode.GeoPoint getFromLocation(String address)
		{
			Geocoder geoCoder = new Geocoder(mContext);
			Barcode.GeoPoint p = null;
			try
			{
				List<Address> addresses = geoCoder.getFromLocationName(address, 1);
				if (addresses.size() > 0)
				{
					p = new Barcode.GeoPoint(0, (double) (addresses.get(0).getLatitude() * 1E6),
							(double) (addresses.get(0).getLongitude() * 1E6));

				}
			}
			catch (Exception ee)
			{
				Log.e("", ee.getMessage());
			}
			return p;
		}
	}

}
