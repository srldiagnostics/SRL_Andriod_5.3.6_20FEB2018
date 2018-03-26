package com.srllimited.srl.serverapis;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.serverapis.rest.RequestManager;
import com.srllimited.srl.util.Log;
import com.srllimited.srl.util.NetworkConnectivity;
import com.srllimited.srl.util.Validate;

import org.json.JSONObject;

/**
 * Created by RuchiTiwari on 1/14/2017.
 */
public class ApiRequestManager
{
	private static final String TAG = "ApiRequestManager";

	private static ApiRequestManager sInstance;

	private ApiRequest mCurrectRequest;

	private AlertDialog mAlertDialog;

	private ProgressDialog mProgressDialog;

	private ApiRequestManager(Context context)
	{

		RequestManager.startup(context);
	}

	public static synchronized void startup(Context context)
	{
		if (sInstance == null)
		{
			sInstance = new ApiRequestManager(context);
		}
	}

	public static ApiRequestManager getInstance()
	{
		return sInstance;
	}

	public void sendRequest(Context context, ApiRequest request, ApiResponseListener listener)
	{
		if (NetworkConnectivity.isOnline(context))
		{
			Log.d(TAG, "Request : " + request.toString());

			request.sendRequest(new ResponseCallBack(context, listener, false));
			mCurrectRequest = request;
			showProgressDialog(context);
			return;
		}
		showNetworkError(context);
	}

	//    public void sendRequestWithoutProgress(Context context, ApiRequest request, ApiResponseListener listener) {
	//        Log.d(TAG, "Request : " + request.toString());
	//        request.sendRequest(new ResponseCallBack(context, listener,false));
	//        return;
	//
	//    }

	public void sendRequestWithoutProgress(Context context, ApiRequest request, ApiResponseListener listener)
	{
		Log.d(TAG, "Request : " + request.toString());
		//  SSLConection.allowAllSSL();
		request.sendRequest(new ResponseCallBack(context, listener, true));
		return;
	}

	private String getStringResponse(Object responseObject)
	{
		String response = "";
		if (!Validate.isNull(responseObject) && (responseObject instanceof String))
		{
			response = (String) responseObject;
		}

		if (Validate.notEmpty(response))
		{
			response = response.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
			response = response.replace("<string xmlns=\"http://tempuri.org/\">", "");
			response = response.replace("<string xmlns=\"http://220.227.57.139:81/\">", "");

			response = response.replace("</string>", "");
			return response;
		}
		return "";
	}

	public void showProgressDialog(Context context)
	{
		try
		{
			hideProgressDialog();

			mProgressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);

			mProgressDialog.setMessage("Please Wait...");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.setCancelable(false);
			mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					try
					{
						mCurrectRequest.cancel();
					}
					catch (Exception e)
					{
					}
					hideProgressDialog();
				}
			});
			mProgressDialog.show();
		}
		catch (Exception e)
		{

		}
	}

	public void hideProgressDialog()
	{
		try
		{
			if (mProgressDialog != null && mProgressDialog.isShowing())
			{
				mProgressDialog.dismiss();
			}
			mProgressDialog = null;
		}
		catch (Exception e)
		{
		}
	}

	private void showNetworkError(Context context)
	{
		showErrorPopup(context, "No Network", "Please check the internet connection");
	}

	private void showResponseError(Context context)
	{
		showErrorPopup(context, "No Data", "Please try after sometime");
	}

	private void showErrorPopup(Context context, String title, String message)
	{
		try
		{
			hideErrorPopup();
			AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
			builder.setTitle(title).setMessage(message).setCancelable(false).setPositiveButton("OK",
					new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int id)
						{
							dialog.dismiss();

						}
					});
			mAlertDialog = builder.create();
			mAlertDialog.show();
		}
		catch (Exception e)
		{
		}
	}

	private void hideErrorPopup()
	{
		try
		{
			if (mAlertDialog != null)
			{
				mAlertDialog.dismiss();
			}
			mAlertDialog = null;
		}
		catch (Exception e)
		{
		}
	}

	private class ResponseCallBack implements ApiResponseListener<String>
	{
		private Context mContext;

		private ApiResponseListener mListener;

		private boolean isResponseError = false;

		public ResponseCallBack(final Context context, final ApiResponseListener listener, final Boolean isError)
		{
			mContext = context;
			mListener = listener;
			isResponseError = isError;
		}

		@Override
		public void onResponse(final ApiRequest request, String response)
		{
			try
			{
				hideMainProgressDialog(request);
				response = getStringResponse(response);
				Log.d(TAG, "request1: " + request.toString() + " " + response);

				if (mListener != null)
				{
					String error = "";
					ServerResponseData responseData = null;
					try
					{
						JSONObject jsonObject = new JSONObject(response);
						if (jsonObject != null)
						{
							responseData = new ServerResponseData(jsonObject);
						}
					}
					catch (Exception e)
					{
						error = e.getLocalizedMessage();
					}

					if (responseData != null && responseData.getFullData() != null)
					{
						mListener.onResponse(request, responseData);
					}
					else
					{
						if (!isResponseError)
							showResponseError(mContext);
						if (!Validate.notEmpty(error))
						{
							error = "No Data";
						}
						mListener.onResponseError(request, new Exception(error));
					}
				}
				//}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{
			hideMainProgressDialog(request);

			Log.d(TAG, "request : " + request.toString());
			Log.d(TAG, "onResponseError : " + error.toString());

			if (mListener != null)
			{
				if (!isResponseError)
					showResponseError(mContext);
				mListener.onResponseError(request, error);
			}
		}

		private void hideMainProgressDialog(ApiRequest request)
		{
			try
			{
				if (mCurrectRequest.equals(request))
				{
					hideProgressDialog();
				}
			}
			catch (Exception e)
			{

			}
		}
	}

}
