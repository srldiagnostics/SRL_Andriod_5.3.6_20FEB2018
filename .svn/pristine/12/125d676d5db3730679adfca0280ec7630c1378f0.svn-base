package com.srllimited.srl;

import java.util.HashMap;
import java.util.Map;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.utilities.Utility;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Codefyne on 02/02/2017.
 */

public class RateUsActivity extends MenuBaseActivity implements View.OnClickListener
{
	public static Activity rateus;
	Context context;
	View starView1, starView2, starView3, starView4, starView5;

	EditText comment;

	LinearLayout layout;

	Button cancel_btn, submit_btn;

	String commentStr;

	boolean checked1 = true;

	boolean checked2 = true;

	boolean checked3 = true;

	boolean checked4 = true;

	boolean checked5 = true;

	String rating = "";

	LinearLayout rate_layout;

	//    ImageView starIv;
	LinearLayout.LayoutParams headerlp;

	String ptnt_cd;

	String p_rating;

	String p_feedBack;

	String p_deviceId;

	String p_os_version;

	String p_os_type;

	String p_app_version;

	TextView good, inputlength;
	//
	String citys = "";
	String loggedin_username = "";
	private Dialog promoDialog;
	private ImageView close_popup;
	private TextView alert_submit;
	private FirebaseAnalytics firebaseAnalytics;
	private ProgressDialog mProgressDialog;
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{

			switch (request.getReferralCode())
			{
				case GET_FEEDBACK:
				{
					if (serverResponseData != null && Validate.notEmpty(serverResponseData.getMsg()))
					{
						if (serverResponseData.getMsg().equalsIgnoreCase("Query Successful"))
						{
							//	Toast.makeText(getApplicationContext(), "Thanks for your feedback..", Toast.LENGTH_SHORT).show();
							if (p_rating != null && !p_rating.equalsIgnoreCase("null"))
							{
								if (!(p_rating.equals("4")))
								{
									if (!(p_rating.equals("5")))
									{
										SharedPreferences prefs = context.getSharedPreferences("apprater", 0);
										SharedPreferences.Editor editor = prefs.edit();
										editor.putBoolean("dontshowagain", true);
										editor.commit();
										if (promoDialog != null)
										{
											promoDialog.show();
										}
										//10min his
									}
									else
									{
										playStroreAccess();
									}
								}
								else
								{
									playStroreAccess();
								}
							}
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Failed to Send...", Toast.LENGTH_SHORT).show();
						}
					}
				}
					break;
			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.rate_us_activity);
		rate_layout = (LinearLayout) findViewById(R.id.rate_layout);
		rateus = this;
		good = (TextView) findViewById(R.id.good);

		inputlength = (TextView) findViewById(R.id.inputlength);
		good.setVisibility(View.GONE);

		context = RateUsActivity.this;
		// Obtain the Firebase Analytics instance.
		firebaseAnalytics = FirebaseAnalytics.getInstance(this);
		Bundle bundle = new Bundle();
		bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, 1);
		bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Rate US");
		//Logs an app event.
		firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
		//Sets whether analytics collection is enabled for this app on this device.
		firebaseAnalytics.setAnalyticsCollectionEnabled(true);
		SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.login_credentials, 0); // 0 - for private mode

		loggedin_username = pref.getString(Constants.loggedin_username, null);
		citys = SharedPrefsUtil.getStringPreference(context, "selectedcity");
		//App Flyer
		AppsFlyerLib.getInstance().enableUninstallTracking(Constants.FCM_sender_id); // ADD THIS LINE HERE
		AppsFlyerLib.getInstance().startTracking(this.getApplication(), Constants.APP_FLYER_KEY);

		Map<String, Object> eventValue = new HashMap<String, Object>();
		eventValue.put(AFInAppEventParameterName.CITY, citys);
		eventValue.put(AFInAppEventParameterName.CUSTOMER_USER_ID, loggedin_username);
		AppsFlyerLib.getInstance().trackEvent(context, "Rate US", eventValue);

		//Facebook Analytic
		AppEventsLogger logger = AppEventsLogger.newLogger(this);
		logger.logEvent("Rate US");
		//		int stars = 10;
		//		String ratingStar = SharedPrefsUtil.getStringPreference(context,"ratingstar");
		//		if(ratingStar!=null && !ratingStar.isEmpty()){
		//			try
		//			{
		//				stars = Integer.valueOf(ratingStar);
		//			}catch (Exception e){
		//				stars = 0;
		//			}
		//		}

		promoDialog = new Dialog(this);
		promoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		promoDialog.setCancelable(false);
		promoDialog.setContentView(R.layout.rate_us_pop_alert);
		alert_submit = (TextView) promoDialog.findViewById(R.id.alert_apply);
		alert_submit.setOnClickListener(this);

		try
		{
			for (int i = 0; i < 5; i++)
			{
				headerlp = new LinearLayout.LayoutParams(75, 75);
				rate_layout.setOrientation(LinearLayout.HORIZONTAL);
				ImageView starIv = new ImageView(context);
				starIv.setId(i);
				starIv.setTag("true");
				//starIv.setPadding(5, 5, 5, 5);
				starIv.setBackgroundResource(R.drawable.star_normal);
				headerlp.setMargins(15, 0, 15, 0);
				starIv.setLayoutParams(headerlp);

				//				if(i<stars)
				//				{
				//					p_rating = stars+"";
				//					starIv.setBackgroundResource(R.drawable.star_hover);
				//					if(i==3 || i==4){
				//						playStroreAccess();
				//					}
				//				}

				String selStar = SharedPrefsUtil.getStringPreference(context, "Star");
				try
				{
					if (Validate.notEmpty(selStar))
					{
						int storedStar = Integer.valueOf(selStar);
						good.setVisibility(View.VISIBLE);

						try
						{

							int imgId = storedStar;
							p_rating = String.valueOf(imgId + 1);

							for (int k = 0; k <= imgId; k++)
							{
								ImageView image = (ImageView) findViewById(k);

								if (image != null)
								{
									image.setTag(false);
									image.setBackgroundResource(R.drawable.star_hover);

									if (k == 0)
									{
										good.setText("Too Bad");
									}
									if (k == 1)
									{
										good.setText("Bad");
									}
									if (k == 2)
									{
										good.setText("Good");
									}

								}
							}

						}
						catch (Exception e)
						{

						}
					}
				}
				catch (Exception e)
				{

				}

				rate_layout.addView(starIv);
				starIv.setOnClickListener(new View.OnClickListener()
				{

					public void onClick(View arg0)
					{
						good.setVisibility(View.VISIBLE);

						try
						{

							int imgId = arg0.getId();
							p_rating = String.valueOf(imgId + 1);
							ImageView fetchimage = (ImageView) findViewById(imgId);
							if (fetchimage != null)
							{
								if (fetchimage.getTag().toString().equalsIgnoreCase("false"))
								{
									checked1 = false;
								}
								else
								{
									checked1 = true;
								}
							}
							if (checked1)
							{
								for (int i = 0; i <= imgId; i++)
								{
									ImageView image = (ImageView) findViewById(i);

									if (image != null)
									{
										image.setTag(false);
										image.setBackgroundResource(R.drawable.star_hover);

										if (i == 0)
										{
											good.setText("Too Bad");
										}
										if (i == 1)
										{
											good.setText("Bad");
										}
										if (i == 2)
										{
											good.setText("Good");
										}

										//good.setText("Too Bad");
										if (i == 3)
										{
											good.setText("Preety Good");
											datasubmission();
											//playStroreAccess();
										}
										if (i == 4)
										{
											good.setText("Excellent");
											datasubmission();
											//playStroreAccess();
										}
									}
								}
							}
							else
							{
								for (int j = imgId + 1; j < 5; j++)
								{
									ImageView image = (ImageView) findViewById(j);
									if (imgId == 0)
									{
										good.setText("Too Bad");
									}
									if (imgId == 1)
									{
										good.setText("Bad");
									}
									if (imgId == 2)
									{
										good.setText("Good");
									}
									//good.setText("Too Bad");
									if (imgId == 3)
									{
										good.setText("Preety Good");
										datasubmission();
										//playStroreAccess();
									}
									if (imgId == 4)
									{
										good.setText("Excellent");
										datasubmission();
										//playStroreAccess();
									}

									if (image != null)
									{
										image.setTag(true);
										image.setBackgroundResource(R.drawable.star_normal);
									}
									/*if (j == 1) {
									    good.setText("Too Bad");
									}
									if (j == 2) {
									    good.setText("Bad");
									}
									if (j == 3) {
									    good.setText("Good");
									}
									if (j == 4) {
									    good.setText("Too Bad");
									}
									//good.setText("Too Bad");
									if (j == 5) {
									    good.setText("Preety Good");
									    //playStroreAccess();
									}
									if (j == 6) {
									    good.setText("Exellent");
									   // playStroreAccess();
									}*/
								}
							}
						}
						catch (Exception e)
						{

						}

					}
				});
			}
		}
		catch (Exception e)
		{

		}

		comment = (EditText) findViewById(R.id.comment);
		layout = (LinearLayout) findViewById(R.id.layout);
		cancel_btn = (Button) findViewById(R.id.cancel_btn);
		submit_btn = (Button) findViewById(R.id.submit_btn);
		submit_btn.setOnClickListener(this);
		cancel_btn.setOnClickListener(this);

		header_loc_name.setEnabled(false);
		header_layout.setEnabled(false);
		header_loc_name.setText("Rate Us");
		header_layout.setEnabled(false);

		//		String ratingComment = SharedPrefsUtil.getStringPreference(context, "ratingcomment");
		//		if (ratingComment != null && !ratingComment.isEmpty())
		//		{
		//			comment.setText(ratingComment);
		//		}
		//		commentStr = comment.getText().toString();

		comment.setOnTouchListener(new View.OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				comment.setHint("");
				layout.setBackgroundResource(R.drawable.rectangle_green_show);
				return false;
			}
		});

		comment.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2)
			{
				commenttsize();
			}

			@Override
			public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2)
			{
				commenttsize();
			}

			@Override
			public void afterTextChanged(final Editable editable)
			{
				commenttsize();
			}
		});

	}

	private void commenttsize()
	{
		try
		{
			comment.setHint("");
			String size = comment.getText().toString();
			size = String.valueOf(size.length());

			inputlength.setText(size + "");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View view)
	{

		switch (view.getId())
		{
			case R.id.cancel_btn:
				clearData();
				break;

			case R.id.submit_btn:
				datasubmission();
				break;

			case R.id.alert_apply:
				if (promoDialog != null)
				{
					Util.hideSoftKeyboard(context, view);
					promoDialog.dismiss();
					Util.killDashBoard();

					Intent intent = new Intent(RateUsActivity.this, Dashboard.class);
					startActivity(intent);
					finish();

					/*if (p_rating != null && !p_rating.equalsIgnoreCase("null"))
					{
						if (p_rating.equalsIgnoreCase("4") || p_rating.equalsIgnoreCase("5"))
						{
							playStroreAccess();
						}
						else
						{
							Intent intent = new Intent(RateUsActivity.this, Dashboard.class);
							startActivity(intent);
							finish();
						}
					}*/

				}
				break;

		}
	}

	private void datasubmission()
	{
		if (Utility.isOnline(RateUsActivity.this))
		{
			if (Validate.notEmpty(p_rating))
			{
				ptnt_cd = Util.getStoredUsername(context);
				p_feedBack = comment.getText().toString();
				p_deviceId = Constants.devicetobepassed; //0005
				p_os_type = "ANDROID";
				p_os_version = "1";
				p_app_version = "1";

				//Toast.makeText(getApplicationContext(), "username" + Util.getStoredUsername(context), Toast.LENGTH_SHORT).show();
				sendRequest(ApiRequestHelper.getRateUs(context, ptnt_cd, p_rating, p_feedBack, p_deviceId, p_os_version,
						p_os_type, p_app_version));

				/*if (Util.getStoredUsername(context) != null && !Util.getStoredUsername(context).isEmpty())
				{

					Toast.makeText(getApplicationContext(), "username" + Util.getStoredUsername(context), Toast.LENGTH_SHORT).show();
					*//*Toast.makeText(getApplicationContext(), "p_rating :" + p_rating, Toast.LENGTH_SHORT).show();
						Toast.makeText(getApplicationContext(), "p_feedBack :" + p_feedBack, Toast.LENGTH_SHORT).show();
						Toast.makeText(getApplicationContext(), "p_os_version :" + p_os_version, Toast.LENGTH_SHORT).show();
						Toast.makeText(getApplicationContext(), "p_os_type :" + p_os_type, Toast.LENGTH_SHORT).show();
						Toast.makeText(getApplicationContext(), "p_app_version :" + p_app_version, Toast.LENGTH_SHORT).show();
						Toast.makeText(getApplicationContext(), "p_deviceId :" + p_deviceId, Toast.LENGTH_SHORT).show();*//*
																															sendRequest(ApiRequestHelper.getRateUs(ptnt_cd, p_rating, p_feedBack, p_deviceId, p_os_version, p_os_type, p_app_version));
																															}
																															else
																															{
																															Util.killLogin();
																															Intent intent = new Intent(RateUsActivity.this, LoginScreenActivity.class);
																															startActivity(intent);
																															finish();
																															}*/
			}
			else
			{
				Toast.makeText(context, "Please rate!", Toast.LENGTH_SHORT).show();
			}
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Please check Internet Connection..", Toast.LENGTH_LONG).show();
		}

	}

	private void clearData()
	{
		finish();
	}

	private void playStroreAccess()
	{
		Intent i = new Intent(Intent.ACTION_VIEW,
				Uri.parse("https://play.google.com/store/apps/details?id=com.srllimited.srl"));
		startActivity(i);
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
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

	private void notifyResponseResult(boolean responseResult)
	{
		hideProgressDialog();
	}
}
