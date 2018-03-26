package com.srllimited.srl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.kyleduo.switchbutton.SwitchButton;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.AboutUsData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.location.LocationMonitor;
import com.srllimited.srl.serverapis.ApiConstants;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.Log;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends MenuBaseActivity
{

	public static Activity settings;
	private final String screen_id = "SCREEN_ID";
	private final String screen_name = "SCREEN_NAME";
	private final String content = "CONTENT";
	private final String mysrlver = "MYSRLVER";
	SwitchButton sb_use_delay;
	TextView pintext;
	RelativeLayout resetpin;
	RelativeLayout resetapp;
	Context context;
	TextView about_us_content, versionum;
	List<AboutUsData> mAboutUsDatasList = new ArrayList<>();
	TextView samever;
	RelativeLayout checkupdatever;
	TextView versiono, oldversion;
	Button update;
	AlertDialog alert;
	private LinearLayout newver, version;
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{

			mAboutUsDatasList.clear();

			switch (request.getReferralCode())
			{
				case Get_CONTENT:
					setAboutUsContent(serverResponseData.getArrayData());

					break;

			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{

		}
	};
	private LocationMonitor.OnLocationChangedListener mLocationChangedListener = new LocationMonitor.OnLocationChangedListener()
	{
		@Override
		public void onLocationChanged(final Location location, final Location lastLocation)
		{

			if (location != null)
			{
				android.util.Log.d("SettingActivity", location.toString());
			}

			//  sendLocation(location);
		}
	};

	public static boolean deleteDir(File dir)
	{

		if (dir != null && dir.isDirectory())
		{
			try
			{
				String[] children = dir.list();
				for (int i = 0; i < children.length; i++)
				{
					boolean success = deleteDir(new File(dir, children[i]));
					if (!success)
					{
						return false;
					}
					else
					{

					}

				}
			}
			catch (Exception e)
			{

			}
		}

		return dir.delete();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.settings_activity);
		context = SettingsActivity.this;

		settings = this;
		Constants.isResetPIN = false;
		header_loc_name.setText("Settings");
		header_loc_name.setEnabled(false);
		pintext = (TextView) findViewById(R.id.pintext);
		resetpin = (RelativeLayout) findViewById(R.id.resetpin);
		resetapp = (RelativeLayout) findViewById(R.id.resetapp);
		checkupdatever = (RelativeLayout) findViewById(R.id.checkupdatever);
		sb_use_delay = (SwitchButton) findViewById(R.id.sb_use_delay);
		newver = (LinearLayout) findViewById(R.id.newver);
		samever = (TextView) findViewById(R.id.sameversion);
		versiono = (TextView) findViewById(R.id.versiono);
		oldversion = (TextView) findViewById(R.id.oldversion);
		version = (LinearLayout) findViewById(R.id.version);
		update = (Button) findViewById(R.id.update);
		oldversion.setText(ApiConstants.appVersion);
		resetapp.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//                                            Intent i = getBaseContext().getPackageManager()
				//                                                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
				//                                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//                                            startActivity(i);
				showSettingsAlert();
			}
		});

		sb_use_delay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(final CompoundButton compoundButton, final boolean b)
			{

				if (b)
				{

					String sharedpin = SharedPrefsUtil.getStringPreference(context, "fourdigitpin");
					if (Validate.notEmpty(sharedpin))
					{
						resetpin.setVisibility(View.VISIBLE);
					}
					SharedPrefsUtil.setBooleanPreference(context, "splashpin", true);
					if (!Validate.notEmpty(sharedpin))
					{
						SharedPrefsUtil.setStringPreference(context, "setpin", "false");

						Util.killfourdigit();
						Intent intent = new Intent(SettingsActivity.this, FourDigitActivity.class);
						intent.putExtra("From", "SettingsActivity");
						startActivity(intent);
					}
				}
				else
				{
					resetpin.setVisibility(View.GONE);
					//                                                            SharedPrefsUtil.setStringPreference(context, "fourdigitpin", "");
					SharedPrefsUtil.setBooleanPreference(context, "splashpin", false);
					resetpin.setVisibility(View.GONE);
				}
			}
		});

		resetpin.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Util.killResetPin();
				Intent intent = new Intent(SettingsActivity.this, ResetPinActivity.class);
				startActivity(intent);
				finish();

			}
		});

		hideall();

		if (RestApiCallUtil.isOnline(context))
			sendRequest(ApiRequestHelper.getContent(context, "APP_VERSION", ApiConstants.appVersion/*"5.0"*/));

		checkupdatever.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				if (!versiono.getText().toString().isEmpty())
				{
					playStroreAccess();
				}
				else
				{
					if (RestApiCallUtil.isOnline(context))
						sendRequest(
								ApiRequestHelper.getContent(context, "APP_VERSION", ApiConstants.appVersion/*"5.0"*/));
					else
					{
						RestApiCallUtil.NetworkError(context);
					}
				}
			}
		});

		update.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				playStroreAccess();
			}
		});
	}

	private void playStroreAccess()
	{
		Intent i = new Intent(Intent.ACTION_VIEW,
				Uri.parse("https://play.google.com/store/apps/details?id=com.srllimited.srl"));
		startActivity(i);
	}

	@Override
	protected void onResume()
	{
		boolean pin = SharedPrefsUtil.getBooleanPreference(context, "splashpin", false);
		//        String pin = SharedPrefsUtil.getStringPreference(context, "fourdigitpin");
		String sharedpin = SharedPrefsUtil.getStringPreference(context, "fourdigitpin");
		if (Validate.notEmpty(sharedpin))
		{

			pintext.setText("Is Security PIN is enabled?");
		}

		if (pin)
		{

			if (Validate.notEmpty(sharedpin))
			{
				sb_use_delay.setChecked(true);
				resetpin.setVisibility(View.VISIBLE);
			}
		}
		else
		{
			resetpin.setVisibility(View.GONE);
		}

		super.onResume();
	}

	public void clearApplicationData()
	{
		try
		{

			String currentCity = SharedPrefsUtil.getStringPreference(context, "selectedcity");
			String currentCityID = SharedPrefsUtil.getStringPreference(context, "selectedcityid");
			String displayname = SharedPrefsUtil.getStringPreference(context, "displayname");
			String FCM_Token = SharedPrefsUtil.getStringPreference(context, Constants.FCM_RegId); //used in dashboard to get city
			Util.killAll();
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
			prefs.edit().clear().commit();
			SharedPreferences.Editor editor = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
			editor.clear();
			editor.commit();

			SharedPrefsUtil.setStringPreference(context, "selectedcity", currentCity);
			SharedPrefsUtil.setStringPreference(context, "selectedcityid", currentCityID);
			SharedPrefsUtil.setStringPreference(context, "displayname", displayname); //reset current location
			SharedPrefsUtil.setStringPreference(context, Constants.FCM_RegId, FCM_Token);

			Constants.RegID = FCM_Token;
			File cache = getCacheDir();
			File appDir = new File(cache.getParent());
			if (appDir.exists())
			{
				String[] children = appDir.list();
				for (String s : children)
				{
					if (!s.equals("lib"))
					{
						deleteDir(new File(appDir, s));
						Log.i("TAG", "File /data/data/APP_PACKAGE/" + s + " DELETED");
					}
				}
				Toast.makeText(context, "Your app data has been cleared Successfully.", Toast.LENGTH_SHORT).show();
				Intent i = getBaseContext().getPackageManager()
						.getLaunchIntentForPackage(getBaseContext().getPackageName());
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
		}
		catch (Exception e)
		{
		}

	}

	public void showSettingsAlert()
	{

		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(SettingsActivity.this,
				android.app.AlertDialog.THEME_HOLO_LIGHT);
		builder.setMessage("Are you sure you want to clear app data?").setCancelable(false)
				.setPositiveButton("No", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						if (dialog != null)
							dialog.dismiss();
					}
				}).setNegativeButton("Yes", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						clearApplicationData();

					}
				});

		alert = builder.create();
		alert.show();
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequestWithoutProgress(this, request, mResponseListener);
	}

	private void setAboutUsContent(JSONArray jArray)
	{

		if (jArray != null)
		{
			try
			{
				if (Validate.notNull(jArray))
				{
					mAboutUsDatasList.clear();
					for (int i = 0; i < jArray.length(); i++)
					{
						AboutUsData aboutUsData = new AboutUsData();
						aboutUsData.setSCREEN_ID(screen_id);
						aboutUsData.setCONTENT(jArray.getJSONObject(i).getString(content));
						aboutUsData.setMYSRLVER(jArray.getJSONObject(i).getString(mysrlver));
						aboutUsData.setSCREEN_NAME(jArray.getJSONObject(i).getString(screen_name));
						mAboutUsDatasList.add(aboutUsData);
					}
				}
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}

			if (mAboutUsDatasList != null)
			{

				for (AboutUsData aboutUsData : mAboutUsDatasList)
				{

					if (aboutUsData.getSCREEN_NAME() != null)
					{
						if (aboutUsData.getSCREEN_NAME().equalsIgnoreCase("APP VERSION"))
						{
							if (aboutUsData.getCONTENT() != null && !aboutUsData.getCONTENT().equalsIgnoreCase("null"))
							{
								versiono.setText(aboutUsData.getCONTENT() + "");
							}
							/* if (!versiono.getText().toString().equalsIgnoreCase(ApiConstants.appVersion)) {
							    Log.e("newver", versiono.getText().toString());
							    viewnewver();
							} else {
							    Log.e("samever", versiono.getText().toString());
							    viewsamever();
							}*/

							if (Integer.parseInt(oldversion.getText().toString().replace(".", "")) < Integer
									.parseInt(versiono.getText().toString().replace(".", "")))
							{
								Log.e("newver", versiono.getText().toString());
								viewnewver();
							}
							else
							{
								Log.e("samever", versiono.getText().toString());
								viewsamever();
							}
						}

					}
				}
			}
		}

	}

	private void viewsamever()
	{
		checkupdatever.setVisibility(View.GONE);
		newver.setVisibility(View.GONE);
		samever.setVisibility(View.GONE);
		version.setVisibility(View.VISIBLE);
	}

	private void viewnewver()
	{
		checkupdatever.setVisibility(View.GONE);
		newver.setVisibility(View.VISIBLE);
		samever.setVisibility(View.VISIBLE);
		version.setVisibility(View.VISIBLE);
	}

	private void hideall()
	{
		checkupdatever.setVisibility(View.VISIBLE);
		version.setVisibility(View.GONE);

	}

}