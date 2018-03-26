package com.srllimited.srl.serverapis.rest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.srllimited.srl.util.Log;

/**
 * A simple adapter between OnResponseListener and volley.Response.Listener + volley.Response.ErrorListener
 *
 * @param <T>
 */
class VolleyResponseListenerAdapter<T> implements Response.Listener<T>, Response.ErrorListener
{
	private static final String TAG = "RequestManager"; //Keeping the tag the same as RequestManager for easy log filtering

	private RequestManager.OnResponseListener<T> mListener;

	public VolleyResponseListenerAdapter(RequestManager.OnResponseListener<T> listener)
	{
		mListener = listener;
	}

	@Override
	public void onResponse(final T response)
	{
		Log.d(TAG, response.toString());

		if (mListener != null)
		{
			mListener.onResponse(response);
		}
	}

	@Override
	public void onErrorResponse(final VolleyError error)
	{
		logError(error);

		if (mListener != null)
		{
			mListener.onResponseError(new RestError(error));
		}
	}

	private void logError(final VolleyError error)
	{
		Log.d(TAG, error.toString());

		NetworkResponse response = error.networkResponse;

		if (response != null)
		{
			Log.d(TAG, "statuscode = " + response.statusCode);
			Log.d(TAG, "data = " + new String(response.data));
			Log.d(TAG, "notmodified = " + response.notModified);
		}
	}
}
