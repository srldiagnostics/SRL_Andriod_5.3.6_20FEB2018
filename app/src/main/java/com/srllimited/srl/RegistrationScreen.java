package com.srllimited.srl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.srllimited.srl.adapters.PatientDetailAdapter;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.AboutUsData;
import com.srllimited.srl.data.PatientData;
import com.srllimited.srl.data.PatientDetailsData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.data.StatesListData;
import com.srllimited.srl.otp.OTPReader;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.DateUtil;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.StringUtil;
import com.srllimited.srl.util.TypefaceUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.util.Validation;
import com.srllimited.srl.utilities.MyCountDownTimer;
import com.srllimited.srl.widget.ApproxAge;
import com.srllimited.srl.widget.RoundCornerProgressView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationScreen extends MenuBaseActivity implements View.OnClickListener
{
	MyCountDownTimer myCountDownTimer;
	public static Activity registration;
	public static int RESULT_CODE_EDIT = 1;
	private final String screen_id = "SCREEN_ID";
	private final String screen_name = "SCREEN_NAME";
	private final String content = "CONTENT";
	private final String mysrlver = "MYSRLVER";
	private final Handler mHandler = new Handler();
	Context context;
	Button next;
	List<AboutUsData> mAboutUsDatasList = new ArrayList<>();
	List<String> saluationList = new ArrayList<>();
	TextView gender_edittext, salutation_edittext;
	TextView dob_edittext;
	LinearLayout hidePopup;
	EditText firstname_edittext, lastname_edittext, email_edittext, mobile_edittext;
	List<String> monthList = new ArrayList<>();
	List<String> yearList = new ArrayList<>();
	List<PatientDetailsData> patientDetailsDatasstr = new ArrayList<>();
	TextView appx_age_edittext;
	String years = "";
	String months = "";
	String days = "";
	ArrayList<String> cityName = new ArrayList<String>();
	TextView popupheader;
	TextView stateTVID, cityTVID;
	TextView popup_header;
	String stateID = "";
	FrameLayout progress_frame_layout;
	TextView progress_count_text, progress_text;
	ArrayList<StatesListData> stateslistData = new ArrayList<StatesListData>();
	ArrayList<String> stateNames = new ArrayList<String>();
	boolean stateselected = false;
	LinearLayout lblcityLayout, lblstateLayout;
	ArrayList<String> salutationList = new ArrayList<String>();
	ImageView cancel, confirm;
	Dialog dialog;
	String name = "";
	String mobile = "";
	String email = "";
	String dob = "";
	String gender = "";
	String fname = "";
	String lname = "";
	String salutation = "";
	String age = "";
	String getyears = "";
	String getmonths = "";
	String getdays = "";
	String City = "";
	String State = "";
	private FirebaseAnalytics firebaseAnalytics;
	//	TextView header_loc_name;
	private boolean isSalutation = false;
	private boolean isState = false;
	private boolean isCity = false;
	private boolean isGenger = false;
	private String selectedListItem = "";
	private RoundCornerProgressView mProgressView;
	private boolean isFirstTimeHitPatientDetail = true;
	private ListView listView;
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			switch (request.getReferralCode())
			{

				///All States
				case GET_STATES:
				{

					setStateList(serverResponseData.getFullData());
				}
					break;
				///Selected State Cities
				case GET_CITIES:
				{

					setCitiesList(serverResponseData.getFullData());
				}
					break;
				case GET_OTP:
				{
					//   serverResponseData.getFullData()
					Log.v("OTP", serverResponseData.toString());
					VerifyOTP();
					startOTPReader();
					// setCitiesList(serverResponseData.getFullData());
				}
					break;
				case VERIFY_OTP:
				{
					if (serverResponseData.getMsg().equalsIgnoreCase("VALID"))
					{
						sendRequest(ApiRequestHelper.getPatientDetail(context,
								mobile_edittext.getText().toString().trim()));
					}
					else
					{
						Toast.makeText(RegistrationScreen.this, "Entered OTP is invalid", Toast.LENGTH_SHORT).show();
					}
				}
					break;

				case GET_PATIENTDETAIL:
				{

					if (serverResponseData.getMsg().equalsIgnoreCase("Data not found."))
					{
						//  dialog.dismiss();
					}
					else
					{
						if (isFirstTimeHitPatientDetail)
						{
							isFirstTimeHitPatientDetail = false;
							sendRequest(ApiRequestHelper.getOTP(context, mobile_edittext.getText().toString().trim()));
						}
						else
						{
							dialog.dismiss();
							setPatientDetail(serverResponseData.getFullData());
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

	public static String format2LenStr(int num)
	{
		return (num < 10) ? "0" + num : String.valueOf(num);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.registration);
		context = RegistrationScreen.this;

		// Obtain the Firebase Analytics instance.
		firebaseAnalytics = FirebaseAnalytics.getInstance(this);
		Bundle bundle = new Bundle();
		bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, 1);
		bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Registration");
		//Logs an app event.
		firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
		//Sets whether analytics collection is enabled for this app on this device.
		firebaseAnalytics.setAnalyticsCollectionEnabled(true);

		//App Flyer
		AppsFlyerLib.getInstance().enableUninstallTracking(Constants.FCM_sender_id); // ADD THIS LINE HERE
		AppsFlyerLib.getInstance().startTracking(this.getApplication(), Constants.APP_FLYER_KEY);

		Map<String, Object> eventValue = new HashMap<String, Object>();
		eventValue.put(AFInAppEventParameterName.LEVEL, 9);
		eventValue.put(AFInAppEventParameterName.SCORE, 100);
		AppsFlyerLib.getInstance().trackEvent(context, "Registration", eventValue);
		//Facebook Analytic
		AppEventsLogger logger = AppEventsLogger.newLogger(this);
		logger.logEvent("Registration");
		registration = this;
		lblcityLayout = (LinearLayout) findViewById(R.id.lblcityLayout);
		lblstateLayout = (LinearLayout) findViewById(R.id.lblstateLayout);

		cityTVID = (TextView) findViewById(R.id.cityTVID);
		stateTVID = (TextView) findViewById(R.id.stateTVID);
		popup_header = (TextView) findViewById(R.id.popup_header);
		next = (Button) findViewById(R.id.next);
		dob_edittext = (TextView) findViewById(R.id.dob_edittext);
		gender_edittext = (TextView) findViewById(R.id.gender_edittext);
		hidePopup = (LinearLayout) findViewById(R.id.hidePopup);
		cancel = (ImageView) findViewById(com.srllimited.srl.R.id.cancel);
		confirm = (ImageView) findViewById(com.srllimited.srl.R.id.confirm);

		popupheader = (TextView) findViewById(R.id.popup_header);
		listView = (ListView) findViewById(com.srllimited.srl.R.id.popup_list);
		firstname_edittext = (EditText) findViewById(R.id.firstname_edittext);
		lastname_edittext = (EditText) findViewById(R.id.lastname_edittext);
		email_edittext = (EditText) findViewById(R.id.email_edittext);
		mobile_edittext = (EditText) findViewById(R.id.mobile_edittext);
		appx_age_edittext = (TextView) findViewById(R.id.appx_age_edittext);
		progress_frame_layout = (FrameLayout) findViewById(R.id.progress_frame_layout);
		salutation_edittext = (TextView) findViewById(R.id.salutation_edittext);
		header_loc_name = (TextView) findViewById(R.id.header_loc_name);
		TypefaceUtil.setTypeFace(context, TypefaceUtil.openSans_SemiBold, header_loc_name);

		progress_text = (TextView) findViewById(R.id.progress_text);
		progress_count_text = (TextView) findViewById(R.id.progress_count_text);

		progress_text.setText(getResources().getString(R.string.proceed));
		progress_count_text.setText(getResources().getString(R.string.progress3));
		cityTVID.setOnClickListener(RegistrationScreen.this);

		stateTVID.setOnClickListener(RegistrationScreen.this);
		mProgressView = (RoundCornerProgressView) findViewById(R.id.rcpv_progress_view);
		progress_text.setOnClickListener(this);

		if (Constants.isPatientDetails)
		{
			progress_frame_layout.setVisibility(View.VISIBLE);
			next.setVisibility(View.GONE);
			header_loc_name.setText("Patient Details");
			hideStateCity();
		}
		else
		{
			progress_frame_layout.setVisibility(View.GONE);
			next.setVisibility(View.VISIBLE);
			header_loc_name.setText("Register");
			showStateCity();
		}

		header_loc_name.setEnabled(false);
		next.setOnClickListener(this);
		dob_edittext.setOnClickListener(this);
		salutation_edittext.setOnClickListener(this);
		confirm.setOnClickListener(this);
		cancel.setOnClickListener(this);
		gender_edittext.setOnClickListener(this);
		appx_age_edittext.setOnClickListener(this);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				selectedListItem = salutationList.get(position);
				if (isState)
				{
					stateID = stateslistData.get(position).getStateID();
				}
			}
		});
		setProgress(55, true);

		if (Constants.isPatientDetails && Constants.isRegEdited)
		{
			PatientData patientData1 = new PatientData();

			try
			{
				String getStoredData = SharedPrefsUtil.getStringPreference(context,
						Constants.sharedPreferencePatientData);
				patientData1 = (PatientData) StringUtil.stringToObject(getStoredData);

			}
			catch (Exception e)
			{

			}

			try
			{
				firstname_edittext.setText(patientData1.getFname() + "");
				lastname_edittext.setText(StringUtil.getValidString(patientData1.getLname()) + "");
				email_edittext.setText(StringUtil.getValidString(patientData1.getEmail()) + "");
				mobile_edittext.setText(StringUtil.getValidString(patientData1.getMobile()) + "");
				dob_edittext.setText(patientData1.getDob() + "");
				if (patientData1.getGender().equalsIgnoreCase("M"))
				{
					gender_edittext.setText("Male");
				}
				if (patientData1.getGender().equalsIgnoreCase("F"))
				{
					gender_edittext.setText("Female");
				}
				if (patientData1.getGender().equalsIgnoreCase("U"))
				{
					gender_edittext.setText("Transgender");
				}
				salutation_edittext.setText(StringUtil.getValidString(patientData1.getSalutation()));
			}
			catch (Exception e)
			{

			}

		}

		firstname_edittext.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2)
			{
				setNext();
			}

			@Override
			public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2)
			{
				setNext();
			}

			@Override
			public void afterTextChanged(final Editable editable)
			{
				setNext();
			}
		});
		lastname_edittext.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2)
			{
				setNext();
			}

			@Override
			public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2)
			{
				setNext();
			}

			@Override
			public void afterTextChanged(final Editable editable)
			{
				setNext();
			}
		});
		mobile_edittext.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2)
			{

				setNext();
			}

			@Override
			public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2)
			{
				setNext();
				if (charSequence.length() != 0)
				{
					if (charSequence.length() == 10)
					{
						isFirstTimeHitPatientDetail = true;
						sendRequest(ApiRequestHelper.getPatientDetail(context,
								mobile_edittext.getText().toString().trim()));

					}
				}

			}

			@Override
			public void afterTextChanged(final Editable editable)
			{
				setNext();
			}
		});
		email_edittext.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void beforeTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2)
			{
				setNext();
			}

			@Override
			public void onTextChanged(final CharSequence charSequence, final int i, final int i1, final int i2)
			{
				setNext();
			}

			@Override
			public void afterTextChanged(final Editable editable)
			{
				setNext();
			}
		});

	}

	private void hideStateCity()
	{
		cityTVID.setVisibility(View.GONE);
		stateTVID.setVisibility(View.GONE);
		lblcityLayout.setVisibility(View.GONE);
		lblstateLayout.setVisibility(View.GONE);
	}

	private void showStateCity()
	{
		cityTVID.setVisibility(View.VISIBLE);
		stateTVID.setVisibility(View.VISIBLE);
		lblcityLayout.setVisibility(View.VISIBLE);
		lblstateLayout.setVisibility(View.VISIBLE);
	}

	private void setProgress(final float progress, final boolean postDelayed)
	{
		if (postDelayed)
		{
			mHandler.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					mProgressView.setProgress(progress);
				}
			}, 200);
		}
		else
		{
			mProgressView.setProgress(progress);
		}
	}

	@Override
	public void onClick(View v)
	{
		Animation bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);
		switch (v.getId())
		{
			case R.id.progress_text:

				if (validateEnteredData())
				{

					//					colintent.putExtra(Constants.registered_name, salutation_edittext.getText() + "" + firstname_edittext.getText() + " " + lastname_edittext.getText() + "");
					//					colintent.putExtra(Constants.registered_mobile, mobile_edittext.getText() + "");
					//					colintent.putExtra(Constants.registered_email, email_edittext.getText() + "");
					//					colintent.putExtra(Constants.registered_dob, dob_edittext.getText() + "");
					//					colintent.putExtra(Constants.registered_gender, gender_edittext.getText().toString());
					//					colintent.putExtra(Constants.registered_fname, firstname_edittext.getText() + "");
					//					colintent.putExtra(Constants.registered_lname, lastname_edittext.getText() + "");
					//					colintent.putExtra(Constants.registered_salutation, salutation_edittext.getText() + "");
					//					colintent.putExtra(Constants.registered_apprxage, appx_age_edittext.getText() + "");
					//					colintent.putExtra(Constants.registered_years, years + "");
					//					colintent.putExtra(Constants.registered_months, months + "");
					//					colintent.putExtra(Constants.registered_days, days + "");

					PatientData patientData = new PatientData();

					patientData.setName(salutation_edittext.getText() + "" + firstname_edittext.getText() + " "
							+ lastname_edittext.getText() + "");
					patientData.setMobile(mobile_edittext.getText() + "");
					patientData.setEmail(email_edittext.getText() + "");
					patientData.setDob(dob_edittext.getText() + "");
					if (gender_edittext.getText().toString().equalsIgnoreCase("Male"))
					{
						patientData.setGender("M");
					}
					if (gender_edittext.getText().toString().equalsIgnoreCase("Female"))
					{

						patientData.setGender("F");
					}

					if (gender_edittext.getText().toString().equalsIgnoreCase("Transgender"))
					{

						patientData.setGender("U");
					}
					patientData.setFname(firstname_edittext.getText() + "");
					patientData.setLname(lastname_edittext.getText() + "");
					patientData.setSalutation(salutation_edittext.getText() + "");
					patientData.setAge(years + " years - " + months + " months - " + days + " days");
					patientData.setYears(years + "");
					patientData.setMonths(months + "");
					patientData.setDays(days + "");

					String patientObjectToString = StringUtil.objectToString(patientData);

					SharedPrefsUtil.setStringPreference(context, Constants.sharedPreferencePatientData,
							patientObjectToString);
					Util.killCollection();
					Intent colintent = new Intent(RegistrationScreen.this, CollectionActivity.class);
					startActivity(colintent);

				}

				break;
			case R.id.gender_edittext:

				setNext();
				popupheader.setText("Gender");
				Util.hideSoftKeyboard(context, v);
				gender_edittext.setError(null);
				isSalutation = false;
				isState = false;
				isCity = false;
				isGenger = true;
				next.setVisibility(View.GONE);
				progress_frame_layout.setVisibility(View.GONE);
				salutationList = new ArrayList<String>();
				salutationList.add("Male");
				salutationList.add("Female");
				salutationList.add("Transgender");
				setPopupListAdapter(salutationList);

				break;
			case R.id.cancel:
				setEnabled();
				selectedListItem = "";
				if (Constants.isPatientDetails)
				{
					progress_frame_layout.setVisibility(View.VISIBLE);
				}
				else
				{
					next.setVisibility(View.VISIBLE);
				}
				hidePopup.startAnimation(bottomDown);
				hidePopup.setVisibility(View.INVISIBLE);
				break;

			case R.id.confirm:
				setEnabled();

				if (Validate.notEmpty(selectedListItem))
				{

					if (isSalutation)
					{
						salutation_edittext.setText(selectedListItem);
					}
					else if (isCity)
					{
						cityTVID.setText(selectedListItem);
						cityTVID.setError(null);
					}
					else if (isState)
					{
						stateTVID.setText(selectedListItem);
						stateTVID.setError(null);
					}
					else if (isGenger)
					{
						gender_edittext.setText(selectedListItem);
					}

				}
				if (Constants.isPatientDetails)
				{
					progress_frame_layout.setVisibility(View.VISIBLE);
				}
				else
				{
					next.setVisibility(View.VISIBLE);
				}
				hidePopup.startAnimation(bottomDown);
				hidePopup.setVisibility(View.INVISIBLE);
				setNext();
				break;
			case R.id.salutation_edittext:

				popupheader.setText("Salutation");
				Util.hideSoftKeyboard(context, v);
				salutation_edittext.setError(null);
				isSalutation = true;
				isState = false;
				isCity = false;
				isGenger = false;
				if (Constants.isPatientDetails)
				{
					progress_frame_layout.setVisibility(View.GONE);
				}
				else
				{
					next.setVisibility(View.GONE);
				}
				salutationList = new ArrayList<String>();
				salutationList.add("Mr.");
				salutationList.add("Mrs.");
				salutationList.add("Master");
				salutationList.add("Ms.");
				setPopupListAdapter(salutationList);

				break;
			case R.id.next:
				setNext();
				if (validateEnteredData())
				{
					if (RestApiCallUtil.isOnline(context))
					{
						Util.killConfirmReg();
						Intent intent = new Intent(RegistrationScreen.this, ConfirmRegistation.class);
						intent.putExtra(Constants.registered_name, salutation_edittext.getText() + ""
								+ firstname_edittext.getText() + " " + lastname_edittext.getText() + "");
						intent.putExtra(Constants.registered_mobile, mobile_edittext.getText() + "");
						intent.putExtra(Constants.registered_email, email_edittext.getText() + "");
						intent.putExtra(Constants.registered_dob, dob_edittext.getText() + "");
						intent.putExtra(Constants.registered_gender, gender_edittext.getText().toString());
						intent.putExtra(Constants.registered_fname, firstname_edittext.getText() + "");
						intent.putExtra(Constants.registered_lname, lastname_edittext.getText() + "");
						intent.putExtra(Constants.registered_salutation, salutation_edittext.getText() + "");
						intent.putExtra(Constants.registered_apprxage,
								years + " years - " + months + " months - " + days + " days");
						intent.putExtra(Constants.registered_years, years + "");
						intent.putExtra(Constants.registered_months, months + "");
						intent.putExtra(Constants.registered_days, days + "");
						intent.putExtra(Constants.registered_state, stateTVID.getText() + "");
						intent.putExtra(Constants.registered_city, cityTVID.getText() + "");
						startActivity(intent);

					}
					else
					{
						RestApiCallUtil.NetworkError(context);
					}

				}
				break;
			case R.id.dob_edittext:

				/*  Util.hideSoftKeyboard(context, v);
				dob_edittext.setError(null);

				int currentYear = Calendar.getInstance().get(Calendar.YEAR);
				DatePickerPopup pickerPopWin = new DatePickerPopup.Builder(RegistrationScreen.this, new DatePickerPopup.OnDatePickedListener() {
				    @Override
				    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
				        String entereddob = year + "-" + month + "-" + day + "";
				        long dob = DateUtil.dob(entereddob);

				        Calendar.getInstance().set(Calendar.DATE, +1);
				        long currentdate = Calendar.getInstance().getTimeInMillis();

				        if (dob < currentdate) {

				            StringBuffer sb = new StringBuffer();
				            sb.append(format2LenStr(day));
				            sb.append("-");
				            sb.append(CollectionPickerPopup.monthList.get(month - 1));
				            sb.append("-");
				            sb.append(String.valueOf(year));


				            dob_edittext.setText(sb.toString());
				            appx_age_edittext.setText("");
				            setNext();
				        } else
				            Toast.makeText(context, "Future dates are not accepted!", Toast.LENGTH_SHORT).show();
				    }
				}).maxYear(currentYear + 1).build();
				SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
				pickerPopWin.setSelectedDate(mFormat.format(new Date()));
				pickerPopWin.showPopWin(RegistrationScreen.this);
				setError();*/
				//////////////////////////////////////////////////

				Util.hideSoftKeyboard(context, v);
				appx_age_edittext.setText("");
				dob_edittext.setError(null);
				int mYear, mMonth, mDay;
				// Get Current Date
				final Calendar c = Calendar.getInstance();
				mYear = c.get(Calendar.YEAR);
				mMonth = c.get(Calendar.MONTH);
				mDay = c.get(Calendar.DAY_OF_MONTH);

				DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePickerTheme,
						new DatePickerDialog.OnDateSetListener()
						{

							@Override
							public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
							{
								String entereddob = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + "";
								long dob = DateUtil.dob(entereddob);

								Calendar.getInstance().set(Calendar.DATE, +1);
								long currentdate = Calendar.getInstance().getTimeInMillis();
								if (dob < currentdate)
								{
									dob_edittext.setText(format2LenStr(dayOfMonth) + "-"
											+ Util.monthList.get(monthOfYear) + "-" + year);
									setNext();
								}
								else
								{
									Toast.makeText(context, "Future dates are not accepted!", Toast.LENGTH_SHORT)
											.show();
								}

							}
						}, mYear, mMonth, mDay);
				datePickerDialog.show();

				setError();
				///////////////////////////////////////////

				break;

			case R.id.appx_age_edittext:

				years = "";
				months = "";
				days = "";
				Util.hideSoftKeyboard(context, v);
				dob_edittext.setError(null);
				appx_age_edittext.setText("");
				setNext();
				ApproxAge approxage = new ApproxAge.Builder(RegistrationScreen.this,
						new ApproxAge.OnDatePickedListener()
						{
							@Override
							public void onDatePickCompleted(int year, int month, int day, String dateDesc)
							{
								years = String.valueOf(year);
								months = ApproxAge.monthList.get(month - 1);
								days = format2LenStr(day);
								StringBuffer sb = new StringBuffer();
								if (years.length() == 1)
									sb.append("0" + years);
								else
									sb.append(years);
								sb.append("-");
								sb.append(months);
								sb.append("-");
								sb.append(days);
								setNext();
								dob_edittext.setText("");
								if (years.equalsIgnoreCase("0") && months.equalsIgnoreCase("00")
										&& days.equalsIgnoreCase("00"))
								{
									Toast.makeText(context, "Approximate age cannot be zero", Toast.LENGTH_SHORT)
											.show();
								}
								else
								{
									appx_age_edittext.setText(sb.toString());
									setNext();
								}
								;
							}
						}).minYear(00) //min year in loop
								.maxYear(100).build();
				approxage.showPopWin(RegistrationScreen.this);

				setError();
				break;
			case R.id.cityTVID:
				next.setVisibility(View.GONE);

				//  CaptureAction = false;
				isSalutation = false;
				isState = false;
				isCity = true;
				isGenger = false;
				popup_header.setText("Cities");
				if (!stateID.isEmpty() && !stateID.equalsIgnoreCase("null") && stateID != null)
				{
					setDisabled();
					getRequest_Cities(ApiRequestHelper.getCities(context, stateID));
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please Select State", Toast.LENGTH_LONG).show();
				}
				// setCityAlertDialog(false);
				break;
			//Alert Dailog

			case R.id.stateTVID:
				next.setVisibility(View.GONE);
				setDisabled();
				isSalutation = false;
				isState = true;
				isCity = false;
				isGenger = false;
				//  CaptureAction = false;
				popup_header.setText("States");
				getRequest_States(ApiRequestHelper.getAllStates(context));
				break;
		}
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(context, request, mResponseListener);
	}

	private void getRequest_States(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	private void getRequest_Cities(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	private void setPatientDetail(JSONObject jsonObject)
	{
		JSONArray jArray = null;
		try
		{
			if (!jsonObject.isNull("data"))
			{
				Object obj = jsonObject.get("data");
				if (obj != null && obj instanceof JSONArray)
				{
					jArray = jsonObject.getJSONArray(Constants.response_data_create);
				}
			}
		}
		catch (Exception e)
		{
		}

		if (jArray != null)
		{
			try
			{
				if (Validate.notNull(jArray))
				{
					stateselected = false;
					patientDetailsDatasstr = new ArrayList<>();
					for (int i = 0; i < jArray.length(); i++)
					{
						PatientDetailsData patientDetailsData = new PatientDetailsData();
						patientDetailsData.setPTNT_CD(jArray.getJSONObject(i).getString(Constants.ptntCd1));
						patientDetailsData.setPTNT_TITTLE(jArray.getJSONObject(i).getString(Constants.ptntTittle1));
						patientDetailsData.setFIRST_NAME(jArray.getJSONObject(i).getString(Constants.firstName1));
						patientDetailsData.setLAST_NAME(jArray.getJSONObject(i).getString(Constants.last_name1));
						patientDetailsData.setADDRESS1(jArray.getJSONObject(i).getString(Constants.address11));
						patientDetailsData.setADDRESS2(jArray.getJSONObject(i).getString(Constants.address12));
						/* patientDetailsData.setCOUNTRY(jArray.getJSONObject(i).getString(Constants.country1));
						patientDetailsData.setCITY(jArray.getJSONObject(i).getString(Constants.city1));
						patientDetailsData.setSTATE(jArray.getJSONObject(i).getString(Constants.state1))*/;
						patientDetailsData.setZIP(jArray.getJSONObject(i).getString(Constants.zip1));
						patientDetailsData.setMOBILE_NO(jArray.getJSONObject(i).getString(Constants.mobile_no1));
						patientDetailsData.setEMAIL_ID(jArray.getJSONObject(i).getString(Constants.email_id1));

						patientDetailsDatasstr.add(patientDetailsData);
					}
					getPatientDetail(patientDetailsDatasstr);
				}
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void setStateList(JSONObject jsonObject)
	{
		JSONArray jArray = null;
		try
		{
			if (!jsonObject.isNull("data"))
			{
				Object obj = jsonObject.get("data");
				if (obj != null && obj instanceof JSONArray)
				{
					jArray = jsonObject.getJSONArray(Constants.response_data_create);
				}
			}
		}
		catch (Exception e)
		{
		}
		if (jArray != null)
		{
			try
			{
				if (Validate.notNull(jArray))
				{
					stateselected = true;
					stateNames = new ArrayList<String>();
					stateslistData = new ArrayList<StatesListData>();
					for (int i = 0; i < jArray.length(); i++)
					{
						stateslistData.add(new StatesListData(jArray.getJSONObject(i).getString(Constants.state_id),
								jArray.getJSONObject(i).getString(Constants.state_name)));
					}

					if (stateslistData.size() > 0)
					{
						for (int i = 0; i < stateslistData.size(); i++)
						{
							stateNames.add(Html.fromHtml(stateslistData.get(i).getStateName()).toString());
						}
					}
					setPopupListStateCity(stateNames);
				}
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
	}
	EditText edtotp;
	private void VerifyOTP()
	{
		// Create custom dialog object
		dialog = new Dialog(RegistrationScreen.this);
		// Include dialog.xml file
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog);
		dialog.setCancelable(false);
		// Set dialog title
		// dialog.setTitle("Custom Dialog");
		// set values for custom dialog components - text, image and button
		edtotp = (EditText) dialog.findViewById(R.id.edtotp);
		dialog.show();

		Button btnoptVerify = (Button) dialog.findViewById(R.id.btnoptVerify);
		/* Button btnclose = (Button) dialog.findViewById(R.id.btnclose);
		btnclose.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        dialog.dismiss();
		    }
		});*/
		// if decline button is clicked, close the custom dialog
		btnoptVerify.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// Close dialog
				if (!(TextUtils.isEmpty(edtotp.getText().toString()))
						&& TextUtils.isDigitsOnly(edtotp.getText().toString()))
					sendRequest(ApiRequestHelper.verifyOTP(context, mobile_edittext.getText().toString().trim(),
							edtotp.getText().toString().trim()));
			}
		});
	}
	private OTPReader mOtpReader;
	int time = 0;
	private static final String sPATTERN = "OTP to verify your SRL DIAGNOSTICS user account is ";
	private void startOTPReader()
	{
		if (mOtpReader != null)
		{
			mOtpReader.stop();
		}

		mOtpReader = new OTPReader(this, sPATTERN, new OTPReader.OnOTPListener()
		{
			@Override
			public void onProcess()
			{
				RestApiCallUtil.showProgressDialog(context);
				if (RestApiCallUtil.mProgressDialog != null)
				{
					RestApiCallUtil.mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
							new DialogInterface.OnClickListener()
							{
								@Override
								public void onClick(DialogInterface dialog, int which)
								{
									mOtpReader.stop();
									dialog.dismiss();
								}
							});
				}
			}

			@Override
			public void onComplete(boolean isReceived, String message)
			{

				mOtpReader.stop();
				RestApiCallUtil.hideProgressDialog();

				if (isReceived)
				{
					edtotp.setText(message.replace(sPATTERN, "").trim());
				}
			}
		});
		mOtpReader.start();
		RestApiCallUtil.showProgressDialog(RegistrationScreen.this);
		myCountDownTimer = new MyCountDownTimer(30000, 1000);
		myCountDownTimer.start();
	}
	private void getPatientDetail(List<PatientDetailsData> patientDetailsDatas)
	{
		// Create custom dialog object
		// List<PatientDetailsData> patientDetailsDatas= new ArrayList<>();
		dialog = new Dialog(RegistrationScreen.this);
		// Include dialog.xml file
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.patient_detail_popup);
		dialog.setCancelable(false);
		// Set dialog title
		// dialog.setTitle("Custom Dialog");
		//

		// set values for custom dialog components - text, image and button
		final EditText edtotp = (EditText) dialog.findViewById(R.id.edtotp);
		final TextView txtmsg = (TextView) dialog.findViewById(R.id.txtmsg);
		final RecyclerView recycler_view = (RecyclerView) dialog.findViewById(R.id.recycler_view);

		txtmsg.setText("Following are the User Id(s) registered with mobile no " + mobile_edittext.getText().toString()
				+ ".\nDo you want to create new User Id other than below Id(s)?");
		recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		recycler_view.setHasFixedSize(true);

		/*patientDetailsDatas = new ArrayList<PatientDetailsData> ();
		for(int i=0;i<30;i++) {
		    PatientDetailsData patientDetailsData1 = new PatientDetailsData();
		    patientDetailsData1.setPTNT_CD("PRIYAF1233234");
		    patientDetailsData1.setFIRST_NAME("PREETY");
		    patientDetailsData1.setLAST_NAME("ANATRGHBMGEDfgeregfg");
		    patientDetailsDatas.add(patientDetailsData1);
		}*/
		PatientDetailAdapter mAdapter = new PatientDetailAdapter(context, patientDetailsDatas);
		recycler_view.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();

		Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
		Button btnNo = (Button) dialog.findViewById(R.id.btnNo);
		btnNo.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Util.killReg();
				Intent intent = new Intent(RegistrationScreen.this, Dashboard.class);
				startActivity(intent);
				finish();

			}
		});
		// if decline button is clicked, close the custom dialog
		btnYes.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// Close dialog
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	private void setCitiesList(JSONObject jsonObject)
	{
		JSONArray jArray = null;
		if (jsonObject.has("data"))
		{
			try
			{

				if (!jsonObject.isNull("data"))
				{
					Object obj = jsonObject.get("data");
					if (obj != null && obj instanceof JSONArray)
					{
						jArray = jsonObject.getJSONArray(Constants.response_data_create);
					}
				}

			}
			catch (Exception e)
			{
			}
			if (jArray != null)
			{
				try
				{
					if (Validate.notNull(jArray))
					{
						stateselected = false;
						cityName = new ArrayList<String>();
						for (int i = 0; i < jArray.length(); i++)
						{
							cityName.add(jArray.getJSONObject(i).getString(Constants.city_name));
						}
						setPopupListStateCity(cityName);
					}
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
			}
		}
		else
		{
			try
			{
				if (Validate.notNull(jsonObject))
				{
					if (jsonObject.getString("code").equalsIgnoreCase("107"))
					{
						setEnabled();
						Util.commonInfoAlert(RegistrationScreen.this, "No City Available!");
					}
				}

			}
			catch (Exception e)
			{

			}
		}
	}

	private void setPopupListStateCity(ArrayList<String> popupLstItems)
	{
		setDisabled();
		setError();
		selectedListItem = "";
		salutationList = popupLstItems;
		ArrayAdapter<String> ad = new ArrayAdapter<String>(this, R.layout.textcenter, R.id.textItem, salutationList);
		listView.setAdapter(ad);

		Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

		hidePopup.startAnimation(bottomUp);
		hidePopup.setVisibility(View.VISIBLE);
	}

	private void setPopupListAdapter(ArrayList<String> popupLstItems)
	{
		setDisabled();
		setError();
		selectedListItem = "";
		ArrayAdapter<String> ad = new ArrayAdapter<String>(this, R.layout.textcenter, R.id.textItem, salutationList);
		listView.setAdapter(ad);

		Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

		hidePopup.startAnimation(bottomUp);
		hidePopup.setVisibility(View.VISIBLE);
	}

	private boolean validateEnteredData()
	{

		boolean isAllValid = false;
		if (Constants.isPatientDetails)
		{
			if (personalValidationWithoutCityState())
			{
				isAllValid = true;
			}
		}
		else
		{
			if (personalValidation())
			{
				isAllValid = true;
			}
		}
		return isAllValid;
	}

	private boolean personalValidation()
	{
		boolean ret = true;
		//		String entered_salutation = salutation_edittext.getText().toString();
		String entered_dob = dob_edittext.getText().toString();
		String entered_gender = gender_edittext.getText().toString();
		String appox_age = appx_age_edittext.getText().toString();
		String city = cityTVID.getText().toString();
		String state = stateTVID.getText().toString();

		//		if (entered_salutation == null || entered_salutation.isEmpty())
		//		{
		//			salutation_edittext.setError("Select Salutation");
		//			ret = false;
		//		}
		if (!Validation.isValidFName(firstname_edittext, true))
		{
			firstname_edittext.requestFocus();
			ret = false;
			return ret;

		}
		else if (!Validation.isValidLName(lastname_edittext, true))
		{
			lastname_edittext.requestFocus();
			ret = false;
			return ret;
		}
		else if (!Validation.isEmailAddress(email_edittext, true))
		{
			email_edittext.requestFocus();
			ret = false;
			return ret;
		}
		else if (!Validation.isValidPhoneNumber(mobile_edittext))
		{
			mobile_edittext.requestFocus();
			ret = false;
			return ret;
		}

		if (Validate.notEmpty(entered_dob) || Validate.notEmpty(appox_age))
		{
			Log.e("dob", "available");
		}
		else
		{
			if (entered_dob == null || entered_dob.isEmpty())
			{

				dob_edittext.setError("Select Date of birth");
				ret = false;
			}
		}

		if (entered_gender == null || entered_gender.isEmpty())
		{
			gender_edittext.setError("Select Gender");
			ret = false;
		}
		else if (state == null || state.isEmpty())
		{
			stateTVID.setError("Select State");
			// Validation.isValidState(stateTVID, true);
			// stateTVID.setError("Select State");
			ret = false;
		}
		else if (city == null || city.isEmpty())
		{
			cityTVID.setError("Select City");
			// Validation.isValidCity(cityTVID, true);

			//cityTVID.setError("Select City");
			ret = false;
		}
		return ret;
	}

	private boolean personalValidationWithoutCityState()
	{
		boolean ret = true;
		//		String entered_salutation = salutation_edittext.getText().toString();
		String entered_dob = dob_edittext.getText().toString();
		String entered_gender = gender_edittext.getText().toString();
		String appox_age = appx_age_edittext.getText().toString();

		//		if (entered_salutation == null || entered_salutation.isEmpty())
		//		{
		//			salutation_edittext.setError("Select Salutation");
		//			ret = false;
		//		}
		if (!Validation.isValidFName(firstname_edittext, true))
		{
			firstname_edittext.requestFocus();
			ret = false;
			return ret;

		}
		else if (!Validation.isValidLName(lastname_edittext, true))
		{
			lastname_edittext.requestFocus();
			ret = false;
			return ret;
		}
		else if (!Validation.isEmailAddress(email_edittext, true))
		{
			email_edittext.requestFocus();
			ret = false;
			return ret;
		}
		else if (!Validation.isValidPhoneNumber(mobile_edittext))
		{
			mobile_edittext.requestFocus();
			ret = false;
			return ret;
		}

		if (Validate.notEmpty(entered_dob) || Validate.notEmpty(appox_age))
		{
			Log.e("dob", "available");
		}
		else
		{
			if (entered_dob == null || entered_dob.isEmpty())
			{

				dob_edittext.setError("Select Date of birth");
				ret = false;
			}
		}

		if (entered_gender == null || entered_gender.isEmpty())
		{
			gender_edittext.setError("Select Gender");
			ret = false;
		}

		return ret;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_CODE_EDIT)
		{
			if (Validate.notNull(data))
			{

				try
				{
					Intent intent = getIntent();
					if (intent != null)
					{

						name = intent.getStringExtra(Constants.registered_name) + "";
						mobile = intent.getStringExtra(Constants.registered_mobile) + "";
						dob = intent.getStringExtra(Constants.registered_dob) + "";
						email = intent.getStringExtra(Constants.registered_email) + "";
						gender = intent.getStringExtra(Constants.registered_gender) + "";
						fname = intent.getStringExtra(Constants.registered_fname) + "";
						lname = intent.getStringExtra(Constants.registered_lname) + "";
						salutation = intent.getStringExtra(Constants.registered_salutation) + "";
						age = intent.getStringExtra(Constants.registered_apprxage) + "";
						getyears = intent.getStringExtra(Constants.registered_years) + "";
						getmonths = intent.getStringExtra(Constants.registered_months) + "";
						getdays = intent.getStringExtra(Constants.registered_days) + "";
						State = intent.getStringExtra(Constants.registered_state) + "";
						City = intent.getStringExtra(Constants.registered_city) + "";

						salutation_edittext.setText(salutation + "");
						mobile_edittext.setText(mobile + "");
						email_edittext.setText(email + "");
						dob_edittext.setText(dob + "");
						gender_edittext.setText(gender + "");
						firstname_edittext.setText(fname + "");
						lastname_edittext.setText(lname + "");
						appx_age_edittext.setText(age + "");
						stateTVID.setText(State);
						cityTVID.setText(City);

					}

				}
				catch (Exception e)
				{

				}
			}
			return;
		}
	}

	private boolean validateData()
	{
		boolean ret = true;
		String entered_dob = dob_edittext.getText().toString();
		String entered_gender = gender_edittext.getText().toString();
		String appox_age = appx_age_edittext.getText().toString();
		String city = cityTVID.getText().toString();
		String state = stateTVID.getText().toString();

		if (!Validation.isValidFName(firstname_edittext, true))
		{
			firstname_edittext.setError(null);
			//            firstname_edittext.requestFocus();
			ret = false;
			//            return ret;

		}
		if (!Validation.isValidLName(lastname_edittext, true))
		{
			lastname_edittext.setError(null);
			//            lastname_edittext.requestFocus();
			ret = false;
			//            return ret;
		}

		if (!Validation.isEmailAddress(email_edittext, true))
		{
			email_edittext.setError(null);
			//            email_edittext.requestFocus();
			ret = false;
			//            return ret;
		}
		if (!Validation.isValidPhoneNumber(mobile_edittext))
		{
			mobile_edittext.setError(null);
			//            mobile_edittext.requestFocus();
			ret = false;
			//            return ret;
		}

		if (Validate.notEmpty(entered_dob) || Validate.notEmpty(appox_age))
		{
			dob_edittext.setError(null);
			appx_age_edittext.setError(null);

		}
		else
		{
			if (entered_dob == null || entered_dob.isEmpty())
			{

				ret = false;
				//                return ret;
			}
		}

		if (entered_gender == null || entered_gender.isEmpty())
		{
			gender_edittext.setError(null);
			ret = false;
			//            return ret;
		}

		if (state == null || state.isEmpty())
		{
			stateTVID.setError(null);
			ret = false;
		}

		if (city == null || city.isEmpty())
		{
			cityTVID.setError(null);
			ret = false;
		}
		return ret;
	}

	private void setNext()
	{
		if (validateData())
		{
			next.setVisibility(View.VISIBLE);
			next.setBackgroundResource(R.color.lightblue);

		}
		else
		{

			next.setBackgroundResource(R.color.ligth_white);

		}
	}

	//	private void sendRequest(ApiRequest request)
	//	{
	//		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	//	}
	//
	//	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	//	{
	//		@Override
	//		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
	//		{
	//
	//			mAboutUsDatasList.clear();
	//			Log.e("serverresponse", serverResponseData + "");
	//			switch (request.getReferralCode())
	//			{
	//				case Get_CONTENT:
	//					setAboutUsContent(serverResponseData.getArrayData());
	//
	//					break;
	//
	//			}
	//		}
	//
	//		@Override
	//		public void onResponseError(final ApiRequest request, final Exception error)
	//		{
	//
	//		}
	//	};

	/*private void setAboutUsContent(JSONArray jArray)
	{
		saluationList.clear();
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
	
				for(AboutUsData aboutUsData : mAboutUsDatasList){
	
					if(aboutUsData.getSCREEN_NAME()!=null){
	
							if(aboutUsData.getCONTENT()!=null && !aboutUsData.getCONTENT().equalsIgnoreCase("null"))
							{
	
							}
					}
				}
			}
		}
	
	}
	*/

	private void setEnabled()
	{
		firstname_edittext.setEnabled(true);
		lastname_edittext.setEnabled(true);
		gender_edittext.setEnabled(true);
		dob_edittext.setEnabled(true);
		salutation_edittext.setEnabled(true);
		email_edittext.setEnabled(true);
		appx_age_edittext.setEnabled(true);
		mobile_edittext.setEnabled(true);
		stateTVID.setEnabled(true);
		cityTVID.setEnabled(true);
	}

	private void setDisabled()
	{
		firstname_edittext.setEnabled(false);
		lastname_edittext.setEnabled(false);
		gender_edittext.setEnabled(false);
		dob_edittext.setEnabled(false);
		salutation_edittext.setEnabled(false);
		email_edittext.setEnabled(false);
		appx_age_edittext.setEnabled(false);
		mobile_edittext.setEnabled(false);
		stateTVID.setEnabled(false);
		cityTVID.setEnabled(false);
	}

	private void setError()
	{
		firstname_edittext.setError(null);
		lastname_edittext.setError(null);
		email_edittext.setError(null);
		mobile_edittext.setError(null);

	}
}