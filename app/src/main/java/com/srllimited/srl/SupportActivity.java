package com.srllimited.srl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.serverapis.ApiConstants;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.utilities.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by codefyneandroid on 21-12-2016.
 */

public class SupportActivity extends MenuBaseActivity implements View.OnClickListener
{
	public static Activity support;

	LinearLayout callLayout;

	LinearLayout mailLayout;

	ImageView ratingLayout, facebookLayout, gPlusLayout;

	Context mContext;

	TextView txtCall, txtEmail;
	int PHONE_REQUES_CODE = 1;
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			Log.e("serverresponse", serverResponseData + "");
			switch (request.getReferralCode())
			{
				case GET_CONTACT_US:
					setContactus(serverResponseData.getArrayData());
					break;
			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{

		}
	};

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.activity_support);
		mContext = SupportActivity.this;
		header_loc_name.setText("Support/Feedback");
		headerlayout.setEnabled(false);
		support = this;
		txtCall = (TextView) findViewById(R.id.txtCall);
		txtEmail = (TextView) findViewById(R.id.txtEmail);
		callLayout = (LinearLayout) findViewById(R.id.callLayout);
		mailLayout = (LinearLayout) findViewById(R.id.mailLayout);
		ratingLayout = (ImageView) findViewById(R.id.ratingLayout);
		facebookLayout = (ImageView) findViewById(R.id.facebookLayout);
		gPlusLayout = (ImageView) findViewById(R.id.gPlusLayout);
		header_loc_name.setEnabled(false);
		callLayout.setOnClickListener(this);
		mailLayout.setOnClickListener(this);
		ratingLayout.setOnClickListener(this);
		facebookLayout.setOnClickListener(this);
		gPlusLayout.setOnClickListener(this);

		sendRequest(ApiRequestHelper.getContactus(mContext, "CONTACT_US", ApiConstants.appVersion));
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequestWithoutProgress(mContext, request, mResponseListener);
	}

	private void setContactus(JSONArray jArray)
	{
		try
		{
			if (Validate.notNull(jArray))
			{

				for (int i = 0; i < jArray.length(); i++)
				{
					JSONObject jsonObject = jArray.getJSONObject(i);
					String screenName = jsonObject.getString("SCREEN_NAME");
					String content = jsonObject.getString("CONTENT");

					if (Validate.notEmpty(screenName) && Validate.notEmpty(content))
					{
						SharedPrefsUtil.setStringPreference(mContext, screenName + "", content + "");
						if (screenName.equalsIgnoreCase("CALL"))
						{
							txtCall.setText(content + "");
						}
						else if (screenName.equalsIgnoreCase("EMAIL"))
						{
							txtEmail.setText(content + "");
						}
						else if (screenName.equalsIgnoreCase("FACEBOOK"))
						{

						}
						else if (screenName.equalsIgnoreCase("GOOGLE"))
						{
						}
					}
				}
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(final View view)
	{
		switch (view.getId())
		{
			case R.id.callLayout:
				Util.callNow(mContext, txtCall.getText().toString().trim());
				break;
			case R.id.mailLayout:
				if (Utility.isOnline(SupportActivity.this))
				{
					shareToGMail(txtEmail.getText().toString().trim()/*"connect@srl.in"*/);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please check Internet Connection..", Toast.LENGTH_LONG)
							.show();
				}
				break;
			case R.id.facebookLayout:
				String facebook_url = SharedPrefsUtil.getStringPreference(mContext, Constants.FACEBOOK + "");

				if (Utility.isOnline(SupportActivity.this))
				{
					if (!(facebook_url.equalsIgnoreCase("null")))
					{
						Intent i1 = new Intent(Intent.ACTION_VIEW, Uri.parse(facebook_url));
						startActivity(i1);
					}
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please check Internet Connection..", Toast.LENGTH_LONG)
							.show();
				}
				break;
			case R.id.gPlusLayout:
				/*Intent i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/107034977859364912929/posts/5McBnjLKuzR"));
				startActivity(i2);*/
				String google_url = SharedPrefsUtil.getStringPreference(mContext, Constants.GPLUS + "");
				if (Utility.isOnline(SupportActivity.this))
				{
					if (!(google_url.equalsIgnoreCase("null")))
					{
						Intent i1 = new Intent(Intent.ACTION_VIEW, Uri.parse(google_url));
						startActivity(i1);
					}

				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please check Internet Connection...", Toast.LENGTH_LONG)
							.show();
				}
				break;
			case R.id.ratingLayout:
				Util.killPlayStore();
				if (Utility.isOnline(SupportActivity.this))
				{
					Intent i = new Intent(Intent.ACTION_VIEW,
							Uri.parse("https://play.google.com/store/apps/details?id=com.srllimited.srl"));
					startActivity(i);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please check Internet Connection..", Toast.LENGTH_LONG)
							.show();
				}
				/*Intent intent = new Intent(SupportActivity.this, PlayStoreActivity.class);
				startActivity(intent);*/
				break;
		}
	}

	public void shareToGMail(String email)
	{
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
		startActivity(Intent.createChooser(emailIntent, null));
	}

}
