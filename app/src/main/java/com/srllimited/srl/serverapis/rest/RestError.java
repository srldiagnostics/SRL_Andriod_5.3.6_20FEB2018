
package com.srllimited.srl.serverapis.rest;

import java.util.Arrays;
import java.util.Map;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;

//Simple class for exposing rest errors while hiding the underlying stack
public class RestError extends Exception
{
	private int mResponseCode;

	private byte[] mResponseData;

	private Map<String, String> mHeaders;

	private boolean mNotModified;

	private boolean mNotConnected;

	RestError(VolleyError error)
	{
		super(error.getLocalizedMessage());

		mNotConnected = error instanceof NoConnectionError;

		if (error.networkResponse != null)
		{
			mResponseCode = error.networkResponse.statusCode;
			mResponseData = error.networkResponse.data;
			mHeaders = error.networkResponse.headers;
			mNotModified = error.networkResponse.notModified;
		}
	}

	public int getStatusCode()
	{
		return mResponseCode;
	}

	public byte[] getData()
	{
		return mResponseData;
	}

	public Map<String, String> getHeaders()
	{
		return mHeaders;
	}

	public boolean isNotModified()
	{
		return mNotModified;
	}

	public boolean isNotConnected()
	{
		return mNotConnected;
	}

	@Override
	public String toString()
	{
		return "{" + "mResponseCode=" + mResponseCode + ", mResponseData=" + Arrays.toString(mResponseData)
				+ ", mHeaders=" + mHeaders + ", mNotModified=" + mNotModified + ", mNotConnected=" + mNotConnected
				+ '}';
	}
}
