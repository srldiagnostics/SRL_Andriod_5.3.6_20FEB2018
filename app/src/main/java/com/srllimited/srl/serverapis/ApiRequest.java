package com.srllimited.srl.serverapis;

import java.util.Date;
import java.util.Map;

import org.json.JSONObject;

import com.srllimited.srl.serverapis.rest.RequestManager;
import com.srllimited.srl.serverapis.rest.RestError;
import com.srllimited.srl.util.Util;

/**
 * Created by RuchiTiwari on 1/14/2017.
 */
public class ApiRequest
{
	private static final String TAG = "ApiRequest";

	private String mTag;

	private String mUrl;

	private JSONObject mPostData;

	private ApiRequestReferralCode mReferralCode;

	private Map<String, String> mParams;

	private ApiResponseListener mListener;
	private RequestManager.OnResponseListener mOnListener = new RequestManager.OnResponseListener()
	{
		@Override
		public void onResponse(final Object object)
		{
			notifyCallBack(object, null);
		}

		@Override
		public void onResponseError(final RestError error)
		{
			notifyCallBack(null, error);
		}
	};

	ApiRequest(final ApiRequestReferralCode referralCode, final String url)
	{
		mReferralCode = referralCode;
		mUrl = url;
		mTag = getUniqueTag();
	}

	private static String getUniqueTag()
	{
		long time = new Date().getTime();
		return String.valueOf(time);
	}

	public ApiRequestReferralCode getReferralCode()
	{
		return mReferralCode;
	}

	public Map<String, String> getParams()
	{
		return mParams;
	}

	public void setParams(final Map<String, String> params)
	{
		mParams = params;
	}

	public JSONObject getPostData()
	{
		return mPostData;
	}

	public void setPostData(final JSONObject postData)
	{
		mPostData = postData;
	}

	public void sendRequest(ApiResponseListener listener)
	{
		mListener = listener;
		RequestManager.getInstance().doStringPost(mTag, mUrl, getParams(), getPostData(), mOnListener);
	}

	public void cancel()
	{
		RequestManager.getInstance().cancelAll(mTag);
	}

	@Override
	public String toString()
	{
		return "ApiRequest{" + "mUrl='" + mUrl + '\'' + ", mParams=" + mParams + ", mReferralCode=" + mReferralCode
				+ ", mPostData=" + mPostData + '}';
	}

	private void notifyCallBack(final Object object, final RestError error)
	{
	if (mListener != null)
	{
		if (error != null)
		{
			mListener.onResponseError(this, error);
		}
		else
		{
			Util.hideProgressDialog();
			mListener.onResponse(this, object);
		}
	}
}
}
