package com.srllimited.srl;

import java.util.HashMap;
import java.util.Map;

import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.UserData;
import com.srllimited.srl.serverapis.ApiRequestReferralCode;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.utilities.AppDataBaseHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ResetPinActivity extends MenuBaseActivity implements View.OnClickListener, RestApiCallUtil.VolleyCallback
{

	public static Activity resetpin;
	Context context;
	EditText pwd;
	TextView userid, patientid;
	Button done;
	AppDataBaseHelper appDb = new AppDataBaseHelper(this);

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//        setContentView(R.layout.reset_pin);
		super.addContentView(R.layout.reset_pin);
		context = ResetPinActivity.this;
		header_loc_name.setText("Reset PIN");
		header_loc_name.setEnabled(false);
		navBack.setVisibility(View.GONE);
		navToggle.setVisibility(View.GONE);
		appDb = new AppDataBaseHelper(getApplicationContext());
		resetpin = this;
		userid = (TextView) findViewById(R.id.userid);
		patientid = (TextView) findViewById(R.id.patientid);
		pwd = (EditText) findViewById(R.id.pwd);
		done = (Button) findViewById(R.id.done);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		patientid.setText(Util.getStoredUsername(context));
		getData(Util.getStoredUsername(context));
		done.setOnClickListener(this);

	}

	private void getData(String ptntcode)
	{

		try
		{
			UserData userData = null;
			try
			{
				userData = appDb.getSelectedUserDetail(ptntcode);
			}
			catch (Exception e)
			{

			}
			String selname = "";
			if (userData != null)
			{

				/*if (userData.getLast_name() != null && !userData.getLast_name().equalsIgnoreCase("null"))
				{
					selname = userData.getFirst_name() + " " + userData.getLast_name() + "";
				}else{
					selname = userData.getFirst_name() + "";
				}*/

				if (userData.getFirst_name() != null && !userData.getFirst_name().equalsIgnoreCase("null"))
				{
					selname = userData.getFirst_name() + "";
					if (userData.getLast_name() != null && !userData.getLast_name().equalsIgnoreCase("null"))
					{
						selname = userData.getFirst_name() + " " + userData.getLast_name() + "";
					}
				}

			}
			userid.setText(selname + "");

		}
		catch (Exception e)
		{

		}
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.done:
				Map<String, String> params = new HashMap<String, String>();
				params.put(Constants.ptntcode, patientid.getText().toString().trim());
				params.put(Constants.pwd, pwd.getText().toString().trim());
				RestApiCallUtil.postServerResponse(context, ApiRequestReferralCode.LOGIN, params);
				break;
		}
	}

	@Override
	public void onSuccessResponse(ApiRequestReferralCode referralCode, String serverResponse,
			HashMap<String, String> fetchValue)
	{
		switch (referralCode)
		{
			case LOGIN:
				if (serverResponse != null && serverResponse.equalsIgnoreCase("Query Successful"))
				{

					Constants.isResetPIN = true;
					Util.killfourdigit();
					SharedPrefsUtil.setStringPreference(context, "setpin", "false");
					Intent intent = new Intent(this, FourDigitActivity.class);
					startActivity(intent);
					finish();
				}
				else
				{
					Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}

	@Override
	public void onBackPressed()
	{
		Util.killSplashFourDigit();
		Intent intent = new Intent(ResetPinActivity.this, SplashFourdigitPinActivity.class);
		startActivity(intent);
		finish();
		super.onBackPressed();
	}
}