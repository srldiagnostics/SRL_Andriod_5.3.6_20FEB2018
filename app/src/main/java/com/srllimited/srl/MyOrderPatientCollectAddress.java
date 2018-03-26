package com.srllimited.srl;

import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.CollectionData;
import com.srllimited.srl.data.PatientData;
import com.srllimited.srl.data.UserData;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.Log;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.StringUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.utilities.AppDataBaseHelper;
import com.srllimited.srl.widget.RoundCornerProgressView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by sri on 12/14/2016.
 */

public class MyOrderPatientCollectAddress extends MenuBaseActivity implements View.OnClickListener
{

	public static Activity confirmPatientDetail;
	private final Handler mHandler = new Handler();
	AppDataBaseHelper appDb = new AppDataBaseHelper(this);
	UserData _userAppData;
	Context context;
	TextView progress_count_text, progress_text;
	EditText name, mobile, email, edob, address, state, city, zip, age, gender, lname, phone;
	TextView startTime, endTime, date, total_amount, collectAddTVID, details, colAddress;
	ImageView edit_personal, edit_addr;
	LinearLayout nameview, phoneview, postalCodeLLID, approxage, dateOfBirthLLID;
	private RoundCornerProgressView mProgressView;
	private TextView populartestTVID, pname;

	@Override

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.my_order_patient_collect_address);

		context = MyOrderPatientCollectAddress.this;
		confirmPatientDetail = this;
		name = (EditText) findViewById(R.id.patname);
		mobile = (EditText) findViewById(R.id.mobile);
		postalCodeLLID = (LinearLayout) findViewById(R.id.postalCodeLLID);
		colAddress = (TextView) findViewById(R.id.coladdress);
		populartestTVID = (TextView) findViewById(R.id.populartestTVID);
		pname = (TextView) findViewById(R.id.pname);
		email = (EditText) findViewById(R.id.email);
		header_loc_name.setEnabled(false);
		edob = (EditText) findViewById(R.id.dob);
		approxage = (LinearLayout) findViewById(R.id.approxage);
		dateOfBirthLLID = (LinearLayout) findViewById(R.id.dateOfBirthLLID);
		hideage();
		address = (EditText) findViewById(R.id.address);
		state = (EditText) findViewById(R.id.state);
		city = (EditText) findViewById(R.id.city);
		zip = (EditText) findViewById(R.id.zip);
		age = (EditText) findViewById(R.id.age);
		details = (TextView) findViewById(R.id.details);
		gender = (EditText) findViewById(R.id.gender);
		lname = (EditText) findViewById(R.id.name);
		phone = (EditText) findViewById(R.id.phone);
		collectAddTVID = (TextView) findViewById(R.id.collectAddTVID);
		nameview = (LinearLayout) findViewById(R.id.nameview);
		phoneview = (LinearLayout) findViewById(R.id.phoneview);

		edit_personal = (ImageView) findViewById(R.id.edit_personal);
		edit_addr = (ImageView) findViewById(R.id.edit_addr);

		if (Constants.isLabOrCollection)
		{
			edit_personal.setVisibility(View.GONE);
			edit_addr.setVisibility(View.GONE);
		}

		edit_personal.setOnClickListener(this);
		edit_addr.setOnClickListener(this);

		header_loc_name.setText("My Order");
		header_loc_name.setEnabled(false);

		startTime = (TextView) findViewById(R.id.startTime);
		endTime = (TextView) findViewById(R.id.endTime);
		date = (TextView) findViewById(R.id.date);
		total_amount = (TextView) findViewById(R.id.total_amount);

		progress_text = (TextView) findViewById(R.id.progress_text);
		progress_count_text = (TextView) findViewById(R.id.progress_count_text);

		progress_text.setText(getResources().getString(R.string.confirm_order_label));
		progress_count_text.setText(getResources().getString(R.string.progress5));
		if (Constants.isLabOrCollection)
		{
			edit_personal.setVisibility(View.GONE);
			edit_addr.setVisibility(View.GONE);
		}

		if (Constants.isLabOrCollection)
		{
			setLab();
		}
		else
		{
			setCol();
		}

		String getColData = SharedPrefsUtil.getStringPreference(context, Constants.sharedPreferenceCollectionData);

		CollectionData collectionData = (CollectionData) StringUtil.stringToObject(getColData);
		//String amt = SharedPrefsUtil.getStringPreference(context, "totalamout");// it will show total amount without discount

		//Chnage by trupti 21/03/2018
		String amt = SharedPrefsUtil.getStringPreference(context, "totalamout");
		if (Validate.notEmpty(amt))
		{
			total_amount.setText("\u20B9" + " " + Util.getIntegerToString(amt + ""));
		}
		if (collectionData != null)
		{
			try
			{
				date.setText(collectionData.getDate1() + "");
				startTime.setText(collectionData.getTime1() + "");
				endTime.setText(collectionData.getTime2() + "");
			}
			catch (Exception e)
			{

			}
		}

		mProgressView = (RoundCornerProgressView) findViewById(R.id.rcpv_progress_view);
		mProgressView.setProgress(0);
		mProgressView.setProgress(mProgressView.getProgress() + 88);
		progress_text.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Util.killPayOpt();
				Intent i = new Intent(MyOrderPatientCollectAddress.this, PaymentOptionActivity.class);
				startActivity(i);
			}
		});

		setProgress(88, true);

		try
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

				collectionData = (CollectionData) StringUtil.stringToObject(getColData);

			}
			catch (Exception e)
			{

			}
			appDb = new AppDataBaseHelper(getApplicationContext());

			String username = "";

			if (Constants.isFamilySel)
			{
				String uname = SharedPrefsUtil.getStringPreference(context, "selectedPerson");
				Log.e("uname", uname + "");
				_userAppData = getData(uname + "");
			}
			else
			{
				_userAppData = getData(Util.getStoredUsername(context));
			}
			try
			{
				if (_userAppData != null)
				{
					username = _userAppData.getFirst_name() + " " + _userAppData.getLast_name() + "";
				}
			}
			catch (Exception e)
			{

			}
			String testid = SharedPrefsUtil.getStringPreference(context, "testid");
			String disc = SharedPrefsUtil.getStringPreference(context, "disc");
			String dob = "";
			String dateof = "";
			long dobdate = 0;
			try
			{
				dobdate = Long.valueOf(_userAppData.getDob() + "");
				dob = RestApiCallUtil.epochToDate(dobdate);
				dateof = dob;
				//				String lage = getAge(dob);
				//				age.setText(lage+"");
			}
			catch (Exception e)
			{

			}
			String to = "";
			long todate = 0;
			try
			{
				todate = Long.valueOf(collectionData.getToDate() + "");
				to = RestApiCallUtil.colepochToDate(todate);
			}
			catch (Exception e)
			{

			}

			String from = "";
			long fromdate = 0;
			try
			{
				fromdate = Long.valueOf(collectionData.getFromDate() + "");
				from = RestApiCallUtil.colepochToDate(fromdate);
			}
			catch (Exception e)
			{

			}

			String cartid = SharedPrefsUtil.getStringPreference(context, "cartid");

			String labname = SharedPrefsUtil.getStringPreference(context, "labname");
			String labphone = SharedPrefsUtil.getStringPreference(context, "labphone");

			if (Validate.notEmpty(labname) && !labname.equalsIgnoreCase("null"))
			{
				lname.setText(labname);
			}

			if (Validate.notEmpty(labphone) && !labphone.equalsIgnoreCase("null"))
			{
				labphone = labphone.replace(",", ", ");
				phone.setText(labphone);
			}

			try
			{
				if (Constants.isPatientDetails)
				{
					name.setText(patientData1.getFname() + " " + patientData1.getLname() + "");
					email.setText(patientData1.getEmail() + "");
					mobile.setText(patientData1.getMobile() + "");
					edob.setText(patientData1.getDob() + "");

					if (patientData1.getAge() != null)
					{
						visibleage();
						age.setText(patientData1.getAge());
					}

					if (patientData1 != null && !Validate.notEmpty(patientData1.getDob()))
					{
						visibleage();
						//            if (!Validate.notEmpty(age)) {
						//                age_view.setVisibility(View.GONE);
						//                age_layout.setVisibility(View.GONE);
						//            }
					}
					else
					{
						hideage();
					}

					if (patientData1 != null && !Validate.notEmpty(patientData1.getAge()))
					{
						dateOfBirthLLID.setVisibility(View.GONE);
					}

					//                age_view.setVisibility(View.GONE);
					//                age_layout.setVisibility(View.GONE);
					//            }

					//					String lage = getAge(patientData1.getDob()+"");
					//					age.setText(lage+"");
					address.setText(Html.fromHtml(collectionData.getAddress() + ""));
					state.setText(collectionData.getState() + "");
					city.setText(collectionData.getCity() + "");
					zip.setText(collectionData.getPostalCode() + "");
					if (patientData1.getGender() != null && patientData1.getGender().equalsIgnoreCase("F"))
					{
						gender.setText("Female");
					}
					if (patientData1.getGender() != null && patientData1.getGender().equalsIgnoreCase("M"))
					{
						gender.setText("Male");
					}
					if (patientData1.getGender() != null && patientData1.getGender().equalsIgnoreCase("U"))
					{
						gender.setText("Transgender");
					}

				}
				else
				{
					if (_userAppData.getFirst_name() != null && !_userAppData.getFirst_name().equalsIgnoreCase("null"))
						name.setText(_userAppData.getFirst_name() + " " + _userAppData.getLast_name() + "");
					if (_userAppData.getEmail_id() != null && !_userAppData.getEmail_id().equalsIgnoreCase("null"))
						email.setText(_userAppData.getEmail_id() + "");
					if (_userAppData.getMobile_no() != null && !_userAppData.getMobile_no().equalsIgnoreCase("null"))
						mobile.setText(_userAppData.getMobile_no() + "");
					edob.setText(dateof + "");
					if (collectionData.getAddress() != null && !collectionData.getAddress().equalsIgnoreCase("null"))
						address.setText(Html.fromHtml(Util.toTitleCase(collectionData.getAddress()) + ""));
					state.setText(Html.fromHtml(collectionData.getState() + ""));
					city.setText(Html.fromHtml(collectionData.getCity() + ""));
					zip.setText(Html.fromHtml(collectionData.getPostalCode() + ""));
					if (_userAppData.getGender() != null && _userAppData.getGender().equalsIgnoreCase("F"))
					{
						gender.setText("Female");
					}
					if (_userAppData.getGender() != null && _userAppData.getGender().equalsIgnoreCase("M"))
					{
						gender.setText("Male");
					}
					if (_userAppData.getGender() != null && _userAppData.getGender().equalsIgnoreCase("U"))
					{
						gender.setText("Transgender");
					}
				}

				if (Constants.isLabOrCollection)
					setSelectedLabDetails();

			}
			catch (Exception e)
			{

			}

		}
		catch (Exception e)
		{

		}
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
	public void onClick(final View view)
	{
		switch (view.getId())
		{
			case R.id.edit_personal:
				if (Constants.isPatientDetails)
				{
					if (RegistrationScreen.registration != null)
					{
						RegistrationScreen.registration.finish();
					}
					Constants.isRegEdited = true;

					Util.killReg();
					Intent intent = new Intent(context, RegistrationScreen.class);
					startActivity(intent);
				}
				else
				{

					if (AddPatientActivity.homevisit != null)
					{
						AddPatientActivity.homevisit.finish();
					}
					Util.killAddPatient();
					Intent intent = new Intent(context, AddPatientActivity.class);
					startActivity(intent);
				}
				break;
			case R.id.edit_addr:
				Constants.isPatEdited = true;
				finish();
				break;
		}
	}

	private UserData getData(String ptntcode)
	{

		UserData userData = null;
		try
		{
			userData = appDb.getSelectedUserDetail(ptntcode);
		}
		catch (Exception e)
		{

		}

		return userData;
		//			String selname = "";
		//			if (userData != null)
		//			{
		//				selname = userData.getFirst_name() + " " + userData.getLast_name() + "";
		//			}

		//			name.setText(selname + "");
		//			SharedPrefsUtil.setStringPreference(context, "selectedPerson", ptntcode + "");
		//
		//			SharedPrefsUtil.setStringPreference(context, "selectedName", selname + "");

	}

	private void setLab()
	{
		postalCodeLLID.setVisibility(View.GONE);
		colAddress.setText("Address ");
		phoneview.setVisibility(View.VISIBLE);
		nameview.setVisibility(View.VISIBLE);
		collectAddTVID.setText("Visiting Lab Details Address");
		details.setText("Preferred collection Details");
		//        populartestTVID.setText("Lab Details");
		//        pname.setText("Lab Name");
	}

	private void setCol()
	{
		postalCodeLLID.setVisibility(View.VISIBLE);
		colAddress.setText("Collection Address");
		phoneview.setVisibility(View.GONE);
		nameview.setVisibility(View.GONE);
		details.setText("Collection Details");
		collectAddTVID.setText("Collection Address Details");
	}

	//	private String getAge(String dateofbirth){
	//		Calendar dob = Calendar.getInstance();
	//		Calendar today = Calendar.getInstance();
	//
	//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd");
	//		try
	//		{
	//			Date date = format.parse(dateofbirth);
	//			System.out.println(date);
	//			Calendar.getInstance().setTimeInMillis(date.getTime());
	//		}
	//		catch (ParseException e)
	//		{
	//			e.printStackTrace();
	//		}
	//
	//
	//		dob.set(Calendar.YEAR, Calendar.MONTH, Calendar.DATE);
	//
	//		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
	//
	//		if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
	//			age--;
	//		}
	//
	//		Integer ageInt = new Integer(age);
	//		String ageS = ageInt.toString();
	//
	//		return ageS;
	//	}
	//

	//	public String getAge(String dateofbirth) {
	//
	//		int age;
	//
	//		final Calendar calenderToday = Calendar.getInstance();
	//		int currentYear = calenderToday.get(Calendar.YEAR);
	//		int currentMonth = 1 + calenderToday.get(Calendar.MONTH);
	//		int todayDay = calenderToday.get(Calendar.DAY_OF_MONTH);
	//
	//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd");
	//		try
	//		{
	//			Date date = format.parse(dateofbirth);
	//			System.out.println(date);
	//			Calendar.getInstance().setTimeInMillis(date.getTime());
	//		}
	//		catch (ParseException e)
	//		{
	//			e.printStackTrace();
	//		}
	//
	//
	//
	//		age = currentYear - Calendar.YEAR;
	//
	//		if(Calendar.MONTH > currentMonth){
	//			--age;
	//		}
	//		else if(Calendar.MONTH == currentMonth){
	//			if(Calendar.DATE > todayDay){
	//				--age;
	//			}
	//		}
	//		return age+"";
	//	}

	private void setSelectedLabDetails()
	{
		String selcity = SharedPrefsUtil.getStringPreference(context, "labcity");
		String selstate = SharedPrefsUtil.getStringPreference(context, "labstate");
		String addr = SharedPrefsUtil.getStringPreference(context, "labaddr");
		String labname = SharedPrefsUtil.getStringPreference(context, "labname");
		String phn = SharedPrefsUtil.getStringPreference(context, "labphone");
		phn = phn.replace(",", ", ");

		if (Validate.notEmpty(addr) && !addr.equalsIgnoreCase("null"))
		{
			addr = Util.toTitleCase(addr);
			address.setText(Html.fromHtml(addr + ""));
		}
		if (Validate.notEmpty(selcity) && !selcity.equalsIgnoreCase("null"))
			state.setText(selstate + "");
		if (Validate.notEmpty(selstate) && !selstate.equalsIgnoreCase("null"))
			city.setText(selcity + "");
		if (Validate.notEmpty(phn) && !phn.equalsIgnoreCase("null"))
			phone.setText(phn + "");
		else
			phoneview.setVisibility(View.GONE);
	}

	private void visibleage()
	{
		dateOfBirthLLID.setVisibility(View.GONE);
		approxage.setVisibility(View.VISIBLE);
	}

	private void hideage()
	{
		dateOfBirthLLID.setVisibility(View.VISIBLE);
		approxage.setVisibility(View.GONE);
	}
}