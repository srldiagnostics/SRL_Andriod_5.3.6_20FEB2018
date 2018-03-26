package com.srllimited.srl;

import java.util.HashMap;
import java.util.Map;

import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.serverapis.ApiRequestReferralCode;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Codefyne on 20-02-2017.
 */

public class MoreActivity extends MenuBaseActivity implements View.OnClickListener, RestApiCallUtil.VolleyCallback
{

	public static Activity more;

	RelativeLayout register_layout, track_layout, setting_layout;

	private Context context;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.more_activity);
		context = MoreActivity.this;
		header_loc_name.setText("More");
		header_loc_name.setEnabled(false);
		more = this;
		register_layout = (RelativeLayout) findViewById(R.id.register_layout);
		track_layout = (RelativeLayout) findViewById(R.id.track_layout);
		setting_layout = (RelativeLayout) findViewById(R.id.setting_layout);

		register_layout.setOnClickListener(this);
		track_layout.setOnClickListener(this);
		setting_layout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.register_layout:

				Map<String, String> params = new HashMap<String, String>();
				params.put(Constants.deviceID, Constants.devicetobepassed);
				RestApiCallUtil.postServerResponse(context, ApiRequestReferralCode.PENDING_REGISTRATION, params);

				Constants.isRegEdited = false;
				Constants.isPatientDetails = false;
				Util.killReg();
				Intent i = new Intent(MoreActivity.this, RegistrationScreen.class);
				startActivity(i);
				break;
			case R.id.track_layout:
				Util.killTraxkOrder();
				Intent i1 = new Intent(MoreActivity.this, TrackOrderActivity.class);
				startActivity(i1);
				break;
			case R.id.setting_layout:
				SharedPrefsUtil.setIntegerPreference(context, Constants.sharedPreferenceSelectedLoginActivity,
						Constants.m_settings);
				if (Util.getStoredUsername(context) != null && !Util.getStoredUsername(context).isEmpty()
						&& !Util.isRem(context))
				{
					Util.killSettings();
					Intent i2 = new Intent(MoreActivity.this, SettingsActivity.class);
					startActivity(i2);
				}
				else
				{
					Util.killLogin();
					Intent intent = new Intent(MoreActivity.this, LoginScreenActivity.class);
					startActivity(intent);
				}
				break;
		}

	}

	@Override
	public void onSuccessResponse(ApiRequestReferralCode referralCode, String serverResponse,
			HashMap<String, String> fetchValue)
	{

		switch (referralCode)
		{

			case PENDING_REGISTRATION:
				if (serverResponse.equalsIgnoreCase("Pending Registration"))
				{

					if (fetchValue != null && fetchValue.get("mobile") != null)
					{
						Util.killOtpReg();
						Intent intent = new Intent(MoreActivity.this, OTPRegistration.class);
						intent.putExtra(Constants.registered_mobile, fetchValue.get("mobile") + "");
						startActivity(intent);
					}
					else
					{
						Util.killReg();
						Constants.isRegEdited = false;
						Constants.isPatientDetails = false;
						Intent intent = new Intent(MoreActivity.this, RegistrationScreen.class);
						startActivity(intent);
					}
				}
				else
				{

					Constants.isRegEdited = false;
					Constants.isPatientDetails = false;
					Util.killReg();
					Intent intent = new Intent(MoreActivity.this, RegistrationScreen.class);
					startActivity(intent);

				}
				break;

		}
	}
}
