package com.srllimited.srl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.CollectionData;
import com.srllimited.srl.data.PatientData;
import com.srllimited.srl.data.UserData;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.DateUtil;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.StringUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.utilities.AppDataBaseHelper;
import com.srllimited.srl.widget.RoundCornerProgressView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by codefyneandroid on 20-12-2016.
 */

public class CollectionActivity extends MenuBaseActivity implements View.OnClickListener
{
	public static Activity colvisit;
	private final Handler mHandler = new Handler();
	AppDataBaseHelper appDb = new AppDataBaseHelper(this);
	RelativeLayout postalrel;
	CardView card;
	LinearLayout bookfor;
	long opentime = 0;
	long closetime = 0;
	String open, close;
	private int pHour, pMinute, pHour2, pMinute2, pYear, pMonth, pDay, pAp, pAp2;
	private View datePicker, timePicker1, timePicker2;
	//    public static final long INTERVAL = 1000 * 60 * 60 * 12;
	//    public static final long INTERVALONE = 1000 * 60 * 60 * 24;
	private Context context;
	private TextView progress_count_text, progress_text;
	private RoundCornerProgressView mProgressView;
	private EditText mAddressEdit, mStateEdit, mCityEdit, mPostalEdit;
	private EditText timeSlotView1, timeSlotView2;
	private EditText dateView;
	private TextView collectionAddress, footer_message, preferredDateTime, username;
	private CheckBox mCheckBox;

	public static String format2LenStr(int num)
	{
		return (num < 10) ? "0" + num : String.valueOf(num);
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.activity_collection);
		context = CollectionActivity.this;
		colvisit = this;
		appDb = new AppDataBaseHelper(getApplicationContext());
		header_loc_name.setText("Collection Details");
		header_loc_name.setEnabled(false);
		progress_text = (TextView) findViewById(R.id.progress_text);
		progress_count_text = (TextView) findViewById(R.id.progress_count_text);
		collectionAddress = (TextView) findViewById(R.id.collectionAddress);
		footer_message = (TextView) findViewById(R.id.footer_msg);
		mCheckBox = (CheckBox) findViewById(R.id.checkboxx);
		postalrel = (RelativeLayout) findViewById(R.id.postrel);
		bookfor = (LinearLayout) findViewById(R.id.bookfor);
		card = (CardView) findViewById(R.id.card);
		username = (TextView) findViewById(R.id.username);
		preferredDateTime = (TextView) findViewById(R.id.preferredDateTime);

		if (Constants.isPatientDetails)
		{
			mCheckBox.setVisibility(View.VISIBLE);
		}
		else
		{
			mCheckBox.setVisibility(View.INVISIBLE);
		}

		opentime = SharedPrefsUtil.getLongPreference(context, "labopen", 0);
		closetime = SharedPrefsUtil.getLongPreference(context, "labclose", 0);

		progress_text.setText(getResources().getString(R.string.proceed));
		progress_count_text.setText(getResources().getString(R.string.progress5));

		progress_text.setText(getResources().getString(R.string.proceed));
		progress_count_text.setText(getResources().getString(R.string.progress4));

		mProgressView = (RoundCornerProgressView) findViewById(R.id.rcpv_progress_view);
		mProgressView.setProgress(0);
		mProgressView.setProgress(mProgressView.getProgress() + 70);

		progress_text.setOnClickListener(this);

		setProgress(70, true);

		mAddressEdit = (EditText) findViewById(R.id.addressEdit);
		mStateEdit = (EditText) findViewById(R.id.stateEdit);
		mCityEdit = (EditText) findViewById(R.id.cityEdit);
		mPostalEdit = (EditText) findViewById(R.id.postalEdit);

		datePicker = findViewById(R.id.selectDateLayout);
		timePicker1 = findViewById(R.id.selectTimeSlot1);
		timePicker2 = findViewById(R.id.selectTimeSlot2);

		datePicker.setOnClickListener(this);
		timePicker1.setOnClickListener(this);
		timePicker2.setOnClickListener(this);

		dateView = (EditText) findViewById(R.id.dateView);
		timeSlotView1 = (EditText) findViewById(R.id.timeSlotView1);
		timeSlotView2 = (EditText) findViewById(R.id.timeSlotView2);

		//CollectionData collectionData = new CollectionData();

		//        datePicker.setOnClickListener(new View.OnClickListener() {
		//            @Override
		//            public void onClick(View view) {
		//               /* hideKeyboard();
		//                final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		//                CollectionPickerPopup pickerPopWin = new CollectionPickerPopup.Builder(CollectionActivity.this, new CollectionPickerPopup.OnColDatePickedListener() {
		//                    @Override
		//                    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
		//                        dateView.setError(null);
		//                        StringBuffer sb = new StringBuffer();
		//                        sb.append(format2LenStr(day));
		//                        sb.append("-");
		//
		//                        sb.append(CollectionPickerPopup.monthList.get(month - 1));
		//                        sb.append("-");
		//                        sb.append(String.valueOf(year));
		//
		//
		//                        Calendar cal = Calendar.getInstance();
		//                        cal.add(Calendar.DATE, -1);
		//                        long curtime = cal.getTimeInMillis();
		//                        long datesel = DateUtil.colTime(sb.toString());
		//
		//                        if (datesel > curtime) {
		//                            dateView.setText(sb.toString());
		//                            timeSlotView1.setText("");
		//                            timeSlotView2.setText("");
		//                        } else {
		//                            Toast.makeText(context, "Previous Dates are not accepted", Toast.LENGTH_SHORT).show();
		//                        }
		//
		//                    }
		//                }).build();
		//                if (Constants.isLabOrCollection)
		//                    pickerPopWin.heading.setText("Select Date");
		//                else
		//                    pickerPopWin.heading.setText("Collection Date");
		//                pickerPopWin.showPopWin(CollectionActivity.this);*/
		//////////////////////////////////////////////////////
		//
		//              int mYear,mMonth,mDay;
		//
		//                    // Get Current Date
		//                    final Calendar c = Calendar.getInstance();
		//                    mYear = c.get(Calendar.YEAR);
		//                    mMonth = c.get(Calendar.MONTH);
		//                    mDay = c.get(Calendar.DAY_OF_MONTH);
		//
		//
		//                    DatePickerDialog datePickerDialog = new DatePickerDialog(this,
		//                            new DatePickerDialog.OnDateSetListener() {
		//
		//                                @Override
		//                                public void onDateSet(DatePicker view, int year,
		//                                                      int monthOfYear, int dayOfMonth) {
		//
		//                                  //  txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
		//
		//                                }
		//                            }, mYear, mMonth, mDay);
		//                    datePickerDialog.show();
		//                }
		///////////////////////////////////////////////////
		//
		//        });

		/* timePicker1.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		        hideKeyboard();
		        if (Validate.notEmpty(dateView.getText().toString())) {
		            TimePickerPopWin pickerPopWin = new TimePickerPopWin.Builder(CollectionActivity.this, new TimePickerPopWin.OnTimePickedListener() {

		                @Override
		                public void onTimePickCompleted(int hour, int minute, String ap, String timeDesc) {

		                    pHour = hour;
		                    pMinute = minute;


		                    long hm = DateUtil.labtiming(timeDesc);

		                    if (ap.equalsIgnoreCase("AM")) {
		                        pAp = 0;
		                    }
		                    long ftime = DateUtil.timeequlality(dateView.getText().toString() + " " + timeDesc);

		                    long currenttime = System.currentTimeMillis();
		                    Calendar calendar = Calendar.getInstance();
		                    calendar.add(Calendar.MINUTE, -1);
		                    long curTime = calendar.getTimeInMillis()
		                            ;
		                    if (Constants.isLabOrCollection) {
		                        if (ftime >=curTime) {
		                            if (hm >= opentime && hm <= closetime) {
		                                timeSlotView1.setText(timeDesc);
		                                timeSlotView1.setError(null);
		                            } else {
		                                Toast.makeText(context, "Time should be between the selected lab timings", Toast.LENGTH_SHORT).show();
		                            }
		                        } else
		                            Toast.makeText(context, "Time should be greater than current time", Toast.LENGTH_SHORT).show();
		                    } else {
		                        if (ftime > curTime) {
		                            timeSlotView1.setText(timeDesc);
		                            timeSlotView1.setError(null);
		                        } else
		                            Toast.makeText(context, "Time should be greater than current time", Toast.LENGTH_SHORT).show();

		                    }


		                }
		            }).setHour(pHour)
		                    .setMinute(pMinute)
		                    .build();
		            pickerPopWin.slot1 = (TextView) pickerPopWin.contentView.findViewById(R.id.slot1);
		            pickerPopWin.slot1.setText("From");
		            pickerPopWin.showPopWin(CollectionActivity.this);
		        } else {
		            Toast.makeText(context, "Please enter date", Toast.LENGTH_SHORT).show();
		        }
		    }
		});*/
		/*  timePicker2.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View view) {
		        hideKeyboard();
		        if (Validate.notEmpty(dateView.getText().toString())) {
		            if (Validate.notEmpty(timeSlotView1.getText().toString())) {
		                TimePickerPopWin pickerPopWin = new TimePickerPopWin.Builder(CollectionActivity.this, new TimePickerPopWin.OnTimePickedListener() {
		                    @Override
		                    public void onTimePickCompleted(int hour, int minute, String ap, String timeDesc) {

		                        pHour2 = hour;
		                        pMinute2 = minute;
		                        long hm = DateUtil.labtiming(timeDesc);
		                        if (ap.equalsIgnoreCase("PM")) {
		                            pAp2 = 1;
		                        }

		                        String fromtime = timeSlotView1.getText().toString();
		                        long ftime = DateUtil.timeequlality(dateView.getText().toString() + " " + fromtime);
		                        long ttime = DateUtil.timeequlality(dateView.getText().toString() + " " + timeDesc);

		                        if (Validate.notEmpty(fromtime)) {
		                            boolean isTime = false;
		                            if (fromtime.equalsIgnoreCase(timeDesc)) {
		                                isTime = true;
		                                Toast.makeText(context, "From and To Time cannot be same", Toast.LENGTH_SHORT).show();
		                            }

		                            if (ttime < ftime) {
		                                isTime = true;
		                                Toast.makeText(context, "To time cannot be less than From time", Toast.LENGTH_SHORT).show();
		                            }

		                            if (!isTime) {
		                                if (Constants.isLabOrCollection) {
		                                    if (hm >= opentime && hm <= closetime) {
		                                        timeSlotView2.setText(timeDesc);
		                                        timeSlotView2.setError(null);
		                                    } else {
		                                        Toast.makeText(context, "Time should be between the selected lab timings", Toast.LENGTH_SHORT).show();
		                                    }
		                                } else {

		                                    timeSlotView2.setText(timeDesc);
		                                    timeSlotView2.setError(null);
		                                }
		                            }
		                        }
		                    }
		                }) //text of cancel button
		                        .setHour(pHour2)
		                        .setMinute(pMinute2)
		                        .build();
		                pickerPopWin.slot1 = (TextView) pickerPopWin.contentView.findViewById(R.id.slot1);
		                pickerPopWin.slot1.setText("To");
		                pickerPopWin.showPopWin(CollectionActivity.this);
		            } else {
		                Toast.makeText(context, "Please enter From Time", Toast.LENGTH_SHORT).show();
		            }

		        } else {
		            Toast.makeText(context, "Please enter date", Toast.LENGTH_SHORT).show();
		        }
		    }

		});*/

		if (Constants.isLabOrCollection)
		{
			setLab();
		}
		else
		{
			setCol();
		}

		//
		//		if (Constants.isPatEdited)
		//		{
		CollectionData collectionData = new CollectionData();
		collectionData.setAddress("");

		collectionData.setPostalCode("");
		collectionData.setFromDate("");
		collectionData.setToDate("");
		collectionData.setTime1("");
		collectionData.setTime2("");
		collectionData.setDate1("");

		String collectionObjectToString = StringUtil.objectToString(collectionData);
		SharedPrefsUtil.setStringPreference(context, Constants.sharedPreferenceCollectionData,
				collectionObjectToString);
		try
		{

			String getColData = SharedPrefsUtil.getStringPreference(context, Constants.sharedPreferenceCollectionData);

			collectionData = (CollectionData) StringUtil.stringToObject(getColData);

		}
		catch (Exception e)
		{

		}

		boolean isLAbHome = Constants.isLabOrCollection;
		if (collectionData != null && collectionData.isLab() == isLAbHome)
		{
			try
			{
				//mPostalEdit.setText(collectionData.getPostalCode() + "");

				dateView.setText(collectionData.getDate1() + "");
				timeSlotView1.setText(collectionData.getTime1() + "");
				timeSlotView2.setText(collectionData.getTime2() + "");
				if (!Constants.isLabOrCollection)
				{

					//mAddressEdit.setText(Html.fromHtml(Util.toTitleCase(collectionData.getAddress() + "")));
					//                    if (Constants.isPatientDetails) {
					//                        mAddressEdit.setText("");
					//                        mAddressEdit.setEnabled(true);
					//                    }
				}
			}
			catch (Exception e)
			{

			}
		}
		//		}
	}

	private void hideKeyboard()
	{
		View view = this.getCurrentFocus();
		if (view != null)
		{
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.progress_text:

				ValidationError();
				boolean Validated = ValidateCol();
				if (Validated)
				{
					CollectionData collectionData = new CollectionData();
					collectionData.setAddress(mAddressEdit.getText().toString());
					collectionData.setLab(Constants.isLabOrCollection);
					collectionData.setState(mStateEdit.getText().toString());
					collectionData.setCity(mCityEdit.getText().toString());
					collectionData.setPostalCode(mPostalEdit.getText().toString());
					collectionData.setFromDate(RestApiCallUtil
							.coldateCol(dateView.getText().toString() + " " + timeSlotView1.getText().toString() + ""));
					collectionData.setToDate(RestApiCallUtil
							.coldateCol(dateView.getText().toString() + " " + timeSlotView2.getText().toString() + ""));
					collectionData.setTime1(timeSlotView1.getText().toString());
					collectionData.setTime2(timeSlotView2.getText().toString());
					collectionData.setDate1(dateView.getText().toString());
					String collectionObjectToString = StringUtil.objectToString(collectionData);
					SharedPrefsUtil.setStringPreference(context, Constants.sharedPreferenceCollectionData,
							collectionObjectToString);
					Intent i = new Intent(CollectionActivity.this, MyOrderPatientCollectAddress.class);
					startActivity(i);
				}
				break;
			case R.id.selectDateLayout:
				////////////////////////////////////////////////////
				hideKeyboard();
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
								String entereddob = year + "-" + (monthOfYear + 1) + "-" + format2LenStr(dayOfMonth)
										+ "";
								long datesel = DateUtil.dob(entereddob);

								Calendar cal = Calendar.getInstance();
								cal.add(Calendar.DATE, -1);
								long curtime = cal.getTimeInMillis();

								if (datesel >= curtime)
								{
									dateView.setText(format2LenStr(dayOfMonth) + "-" + Util.monthList.get(monthOfYear)
											+ "-" + year);

									timeSlotView1.setText("");
									timeSlotView2.setText("");
									dateView.setError(null);

								}
								else
								{
									Toast.makeText(context, "Previous Dates are not accepted", Toast.LENGTH_SHORT)
											.show();
								}

							}
						}, mYear, mMonth, mDay);
				datePickerDialog.show();
				////////////////////////////////////////////////////
				/*  hideKeyboard();
				final int currentYear = Calendar.getInstance().get(Calendar.YEAR);
				CollectionPickerPopup pickerPopWin = new CollectionPickerPopup.Builder(CollectionActivity.this, new CollectionPickerPopup.OnColDatePickedListener() {
				    @Override
				    public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
				        dateView.setError(null);
				        StringBuffer sb = new StringBuffer();
				        sb.append(format2LenStr(day));
				        sb.append("-");

				        sb.append(CollectionPickerPopup.monthList.get(month - 1));
				        sb.append("-");
				        sb.append(String.valueOf(year));


				        Calendar cal = Calendar.getInstance();
				        cal.add(Calendar.DATE, -1);
				        long curtime = cal.getTimeInMillis();
				        long datesel = DateUtil.colTime(sb.toString());

				        if (datesel > curtime) {
				            dateView.setText(sb.toString());
				            timeSlotView1.setText("");
				            timeSlotView2.setText("");
				        } else {
				            Toast.makeText(context, "Previous Dates are not accepted", Toast.LENGTH_SHORT).show();
				        }

				    }
				}).build();
				if (Constants.isLabOrCollection)
				    pickerPopWin.heading.setText("Select Date");
				else
				    pickerPopWin.heading.setText("Collection Date");
				pickerPopWin.showPopWin(CollectionActivity.this);
				*/
				break;
			case R.id.selectTimeSlot1:
				/* hideKeyboard();
				if (Validate.notEmpty(dateView.getText().toString())) {
				    TimePickerPopWin pickerPopWin = new TimePickerPopWin.Builder(CollectionActivity.this, new TimePickerPopWin.OnTimePickedListener() {

				        @Override
				        public void onTimePickCompleted(int hour, int minute, String ap, String timeDesc) {

				            pHour = hour;
				            pMinute = minute;


				            long hm = DateUtil.labtiming(timeDesc);

				            if (ap.equalsIgnoreCase("AM")) {
				                pAp = 0;
				            }
				            long ftime = DateUtil.timeequlality(dateView.getText().toString() + " " + timeDesc);

				            long currenttime = System.currentTimeMillis();
				            Calendar calendar = Calendar.getInstance();
				            calendar.add(Calendar.MINUTE, -1);
				            long curTime = calendar.getTimeInMillis();
				            if (Constants.isLabOrCollection) {
				                if (ftime >=curTime) {
				                    if (hm >= opentime && hm <= closetime) {
				                        timeSlotView1.setText(timeDesc);
				                        timeSlotView1.setError(null);
				                    } else {
				                        Toast.makeText(context, "Time should be between the selected lab timings", Toast.LENGTH_SHORT).show();
				                    }
				                } else
				                    Toast.makeText(context, "Time should be greater than current time", Toast.LENGTH_SHORT).show();
				            } else {
				                if (ftime > curTime) {
				                    timeSlotView1.setText(timeDesc);
				                    timeSlotView1.setError(null);
				                } else
				                    Toast.makeText(context, "Time should be greater than current time", Toast.LENGTH_SHORT).show();

				            }


				        }
				    }).setHour(pHour)
				            .setMinute(pMinute)
				            .build();
				    pickerPopWin.slot1 = (TextView) pickerPopWin.contentView.findViewById(R.id.slot1);
				    pickerPopWin.slot1.setText("From");
				    pickerPopWin.showPopWin(CollectionActivity.this);
				} else {
				    Toast.makeText(context, "Please enter date", Toast.LENGTH_SHORT).show();
				}*/

				////////////////////////////////////////////////////
				// Get Current Time
				if (Validate.notEmpty(dateView.getText().toString()))
				{
					int mHour, mMinute;
					final Calendar c1 = Calendar.getInstance();
					mHour = c1.get(Calendar.HOUR_OF_DAY);
					mMinute = c1.get(Calendar.MINUTE);

					// Launch Time Picker Dialog
					TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DatePickerTheme,
							new TimePickerDialog.OnTimeSetListener()
							{
								@Override
								public void onTimeSet(TimePicker view, int hourOfDay, int minute)
								{

									String time = hourOfDay + ":" + minute;

									SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
									Date date = null;
									try
									{
										date = fmt.parse(time);
									}
									catch (ParseException e)
									{

										e.printStackTrace();
									}
									SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");
									String timeDesc = fmtOut.format(date);

									long hm = DateUtil.labtiming1(dateView.getText().toString() + " " + timeDesc);

									if (timeDesc.endsWith("AM"))
									{
										pAp = 0;
									}
									long ftime = DateUtil.timeequlality(dateView.getText().toString() + " " + timeDesc);

									long currenttime = System.currentTimeMillis();
									Calendar calendar = Calendar.getInstance();
									calendar.add(Calendar.MINUTE, -1);
									long curTime = calendar.getTimeInMillis();

									if (Constants.isLabOrCollection)
									{

										opentime = DateUtil.labtiming1(dateView.getText().toString() + " " + open);
										closetime = DateUtil.labtiming1(dateView.getText().toString() + " " + close);
										// if (ftime >= curTime) { //remove this validation as said by shradhha
										///  if (hm >= opentime && hm <= closetime) {
										timeSlotView1.setText(timeDesc);
										timeSlotView1.setError(null);
										/* } else {
										     Toast.makeText(context, "Time should be between the selected lab timings", Toast.LENGTH_SHORT).show();
										 }*/
										// } else Toast.makeText(context, "Time should be greater than current time", Toast.LENGTH_SHORT).show();

									}
									else
									{

										if (ftime > curTime)
										{
											timeSlotView1.setText(timeDesc);
											timeSlotView1.setError(null);
										}
										else
											Toast.makeText(context, "Time should be greater than current time",
													Toast.LENGTH_SHORT).show();

									}

								}
							}, mHour, mMinute, false);
					timePickerDialog.show();
				}
				else
				{
					Toast.makeText(context, "Please enter date", Toast.LENGTH_SHORT).show();
				}
				///////////////////////////////////////////////////
				break;
			case R.id.selectTimeSlot2:

				/*  hideKeyboard();
				if (Validate.notEmpty(dateView.getText().toString())) {
				    if (Validate.notEmpty(timeSlotView1.getText().toString())) {
				        TimePickerPopWin pickerPopWin = new TimePickerPopWin.Builder(CollectionActivity.this, new TimePickerPopWin.OnTimePickedListener() {
				            @Override
				            public void onTimePickCompleted(int hour, int minute, String ap, String timeDesc) {

				                pHour2 = hour;
				                pMinute2 = minute;
				                long hm = DateUtil.labtiming(timeDesc);
				                if (ap.equalsIgnoreCase("PM")) {
				                    pAp2 = 1;
				                }

				                String fromtime = timeSlotView1.getText().toString();
				                long ftime = DateUtil.timeequlality(dateView.getText().toString() + " " + fromtime);
				                long ttime = DateUtil.timeequlality(dateView.getText().toString() + " " + timeDesc);

				                if (Validate.notEmpty(fromtime)) {
				                    boolean isTime = false;
				                    if (fromtime.equalsIgnoreCase(timeDesc)) {
				                        isTime = true;
				                        Toast.makeText(context, "From and To Time cannot be same", Toast.LENGTH_SHORT).show();
				                    }

				                    if (ttime < ftime) {
				                        isTime = true;
				                        Toast.makeText(context, "To time cannot be less than From time", Toast.LENGTH_SHORT).show();
				                    }

				                    if (!isTime) {
				                        if (Constants.isLabOrCollection) {
				                            if (hm >= opentime && hm <= closetime) {
				                                timeSlotView2.setText(timeDesc);
				                                timeSlotView2.setError(null);
				                            } else {
				                                Toast.makeText(context, "Time should be between the selected lab timings", Toast.LENGTH_SHORT).show();
				                            }
				                        } else {

				                            timeSlotView2.setText(timeDesc);
				                            timeSlotView2.setError(null);
				                        }
				                    }
				                }
				            }
				        }) //text of cancel button
				                .setHour(pHour2).setMinute(pMinute2).build();
				        pickerPopWin.slot1 = (TextView) pickerPopWin.contentView.findViewById(R.id.slot1);
				        pickerPopWin.slot1.setText("To");
				        pickerPopWin.showPopWin(CollectionActivity.this);
				    } else {
				        Toast.makeText(context, "Please enter From Time", Toast.LENGTH_SHORT).show();
				    }

				} else {
				    Toast.makeText(context, "Please enter date", Toast.LENGTH_SHORT).show();
				}
				*/
				///////////////////////////////////////////////////////////////////
				// Get Current Time

				hideKeyboard();
				if (Validate.notEmpty(dateView.getText().toString()))
				{
					int mHour, mMinute;
					final Calendar c2 = Calendar.getInstance();
					mHour = c2.get(Calendar.HOUR_OF_DAY);
					mMinute = c2.get(Calendar.MINUTE);

					// Launch Time Picker Dialog
					TimePickerDialog timePickerDialog1 = new TimePickerDialog(this, R.style.DatePickerTheme,
							new TimePickerDialog.OnTimeSetListener()
							{

								@Override
								public void onTimeSet(TimePicker view, int hourOfDay, int minute)
								{
									String time = hourOfDay + ":" + minute;

									SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
									Date date = null;
									try
									{
										date = fmt.parse(time);
									}
									catch (ParseException e)
									{

										e.printStackTrace();
									}

									SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");
									String timeDesc = fmtOut.format(date);
									long hm = DateUtil.labtiming1(dateView.getText().toString() + " " + timeDesc);
									if (timeDesc.endsWith("PM"))
									{
										pAp2 = 1;
									}

									String fromtime = timeSlotView1.getText().toString();
									long ftime = DateUtil.timeequlality(dateView.getText().toString() + " " + fromtime);
									long ttime = DateUtil.timeequlality(dateView.getText().toString() + " " + timeDesc);

									if (Validate.notEmpty(fromtime))
									{
										boolean isTime = false;
										if (fromtime.equalsIgnoreCase(timeDesc))
										{
											isTime = true;
										}

										if (ttime < ftime)
										{
											isTime = true;
											Toast.makeText(context, "To time cannot be less than From time",
													Toast.LENGTH_SHORT).show();
										}
										opentime = DateUtil.labtiming1(dateView.getText().toString() + " " + open);
										closetime = DateUtil.labtiming1(dateView.getText().toString() + " " + close);
										if (!isTime)
										{
											if (Constants.isLabOrCollection)
											{
												// if (hm >= opentime && hm <= closetime) { //remove this validation as said by shradhha
												timeSlotView2.setText(timeDesc);
												timeSlotView2.setError(null);
												/* } else {
												    Toast.makeText(context, "Time should be between the selected lab timings", Toast.LENGTH_SHORT).show();
												}*/
											}
											else
											{

												timeSlotView2.setText(timeDesc);
												timeSlotView2.setError(null);
											}
										}
										/* sachin commnet this code because issue in diff device */

									}

								}
							}, mHour, mMinute, false);
					timePickerDialog1.show();
				}
				else
				{
					Toast.makeText(context, "Please enter date", Toast.LENGTH_SHORT).show();
				}
				///////////////////////////////////////////////////////////////////////
				break;
		}
	}

	//	private boolean pinCodeValidate()
	//	{
	//		boolean ret = true;
	//		String pincode = mPostalEdit.getText().toString();
	//		if (pincode.length() != 0)
	//		{
	//			Log.e("Pin", pincode.length() + pincode + "");
	//			if (!Validation.isPinCodeValidation(mPostalEdit))
	//			{
	//				mPostalEdit.setError(null);
	//				mPostalEdit.requestFocus();
	//				mPostalEdit.setError("Please enter valid Pin Code");
	//				ret = false;
	//			}
	//		}
	//		else
	//		{
	//			mPostalEdit.requestFocus();
	//			mPostalEdit.setError("Please enter Postal Code");
	//			ret = false;
	//		}
	//		return ret;
	//	}

	private boolean validation()
	{
		boolean isValid = false;

		if (!Validate.isNull(mAddressEdit.getText().toString()))
		{
			if (!Validate.isNull(mStateEdit.getText().toString()))
			{

				if (!Validate.isNull(mCityEdit.getText().toString()))
				{
					//
					if (!Validate.isNull(mPostalEdit.getText().toString()))
					{
						if (mPostalEdit.getText().toString().length() < 6)
						{
							if (!Validate.isNull(dateView.getText().toString()))
							{
								if (!Validate.isNull(timeSlotView1.getText().toString()))
								{
									if (!Validate.isNull(timeSlotView2.getText().toString()))
									{
										isValid = true;

									}
									else
									{
										Toast.makeText(context, "Please enter to time", Toast.LENGTH_SHORT).show();
									}

								}
								else
								{
									Toast.makeText(context, "Please enter from time", Toast.LENGTH_SHORT).show();
								}

							}
							else
							{
								Toast.makeText(context, "Please enter date", Toast.LENGTH_SHORT).show();
							}
						}
						else
						{
							mPostalEdit.requestFocus();
							mPostalEdit.setError("Invalid Postal Code");
						}
					}
					else
					{
						mPostalEdit.requestFocus();
						mPostalEdit.setError("Please enter Postal Code");
					}
				}
				else
				{
					mCityEdit.requestFocus();
					mCityEdit.setError("Please enter city");
				}
			}
			else
			{
				mStateEdit.requestFocus();
				mStateEdit.setError("Please enter state");
			}
		}
		else
		{
			mAddressEdit.requestFocus();
			mAddressEdit.setError("Please enter address");
		}

		return isValid;
	}

	private void setCol()
	{
		card.setVisibility(View.VISIBLE);
		postalrel.setVisibility(View.VISIBLE);
		header_loc_name.setText("Collection Details");
		footer_message.setVisibility(View.GONE);
		//		mCheckBox.setVisibility(View.VISIBLE);
		collectionAddress.setText("Collection Address");
		preferredDateTime.setText("Preferred Collection Date & Time");
		String city = SharedPrefsUtil.getStringPreference(context, "selectedcity");
		String fetchstate = SharedPrefsUtil.getStringPreference(context, "displayname");
		bookfor.setVisibility(View.VISIBLE);
		if (Validate.notEmpty(fetchstate))
		{
			try
			{
				int stIndex = fetchstate.indexOf("(");
				int ltIndex = fetchstate.indexOf(")");

				fetchstate = fetchstate.substring(stIndex + 1, ltIndex);
			}
			catch (Exception e)
			{

			}
		}

		if (Validate.notEmpty(fetchstate) && !fetchstate.equalsIgnoreCase("null"))
		{
			mStateEdit.setText(Util.toTitleCase(fetchstate.toUpperCase() + ""));
			mStateEdit.setEnabled(false);
		}
		else
		{
			mStateEdit.setEnabled(true);
		}

		if (Validate.notEmpty(city) && !city.equalsIgnoreCase("null"))
		{

			UserData _userAppData = null;
			if (Constants.isFamilySel)
			{
				String uname = SharedPrefsUtil.getStringPreference(context, "selectedPerson");

				_userAppData = getData(uname + "");
			}
			else
			{
				_userAppData = getData(Util.getStoredUsername(context));
			}
			if (_userAppData.getCity() != null && _userAppData.getCity().equalsIgnoreCase(city))
			{
				if (_userAppData.getAddress1() != null && !_userAppData.getAddress1().equalsIgnoreCase("null"))
				{
					mAddressEdit.setText(Html.fromHtml(Util.toTitleCase(_userAppData.getAddress1())));
					if (Constants.isPatientDetails)
					{
						mAddressEdit.setText("");
						mAddressEdit.setEnabled(true);
					}
				}
				if (_userAppData.getZip() != null && !_userAppData.getZip().equalsIgnoreCase("null"))
				{
					mPostalEdit.setText(_userAppData.getZip());
					if (Constants.isPatientDetails)
					{
						mPostalEdit.setText("");

					}
				}
			}
			mCityEdit.setText(Util.toTitleCase(city.toUpperCase() + ""));
			mCityEdit.setEnabled(false);
		}
		else
		{
			mCityEdit.setEnabled(true);
		}
	}

	private void setLab()
	{

		open = SharedPrefsUtil.getStringPreference(context, "open");
		close = SharedPrefsUtil.getStringPreference(context, "close");

		footer_message.setText("Lab Timings : " + open + "" + " - " + close + "");

		collectionAddress = (TextView) findViewById(R.id.collectionAddress);
		footer_message.setVisibility(View.VISIBLE);
		card.setVisibility(View.GONE);
		postalrel.setVisibility(View.GONE);
		//		mCheckBox.setVisibility(View.GONE);
		bookfor.setVisibility(View.GONE);
		collectionAddress.setText("Lab Address");
		header_loc_name.setText("Visiting Lab Details");
		preferredDateTime.setText("Preferred Visit Date & Time");

		String city = SharedPrefsUtil.getStringPreference(context, "labcity");
		String state = SharedPrefsUtil.getStringPreference(context, "labstate");
		String addr = SharedPrefsUtil.getStringPreference(context, "labaddr");

		if (Validate.notEmpty(city) && !city.equalsIgnoreCase("null"))
		{
			mCityEdit.setText(Util.toTitleCase(city.toUpperCase() + ""));
			mCityEdit.setEnabled(false);
		}
		else
		{
			mCityEdit.setEnabled(true);
		}

		if (Validate.notEmpty(state) && !state.equalsIgnoreCase("null"))
		{
			mStateEdit.setText(Util.toTitleCase(state.toUpperCase() + ""));
			mStateEdit.setEnabled(false);
		}
		else
		{
			mStateEdit.setEnabled(true);
		}

		if (Validate.notEmpty(addr) && !addr.equalsIgnoreCase("null"))
		{
			mAddressEdit.setText(Html.fromHtml(Util.toTitleCase(addr.toUpperCase() + "")));
			mAddressEdit.setEnabled(false);
			//            if (Constants.isPatientDetails) {
			//                mAddressEdit.setText("");
			//                mAddressEdit.setEnabled(true);
			//            }
		}
		else
		{
			mAddressEdit.setEnabled(true);
		}

	}

	private boolean ValidateCol()
	{
		boolean isValid = false;

		String addr = mAddressEdit.getText().toString();
		String state = mStateEdit.getText().toString();
		String city = mCityEdit.getText().toString();
		String postal = mPostalEdit.getText().toString();
		String date = dateView.getText().toString();
		String time1 = timeSlotView1.getText().toString();
		String time2 = timeSlotView2.getText().toString();

		if (!Validate.notEmpty(addr))
		{
			return false;

		}
		else
		{
			isValid = true;
		}
		if (!Validate.notEmpty(state))
		{
			return false;
		}
		else
		{
			isValid = true;
		}
		if (!Validate.notEmpty(city))
		{

			return false;
		}
		else
		{
			isValid = true;
		}

		if (postalrel.getVisibility() == View.VISIBLE)
		{
			if (!Validate.notEmpty(postal))
			{
				return false;
			}
			else
			{
				if (postal.length() < 6)
				{
					return false;
				}
				else
				{
					isValid = true;
				}
			}
		}
		if (!Validate.notEmpty(date))
		{
			return false;
		}
		else
		{
			isValid = true;
		}

		if (!Validate.notEmpty(time1))
		{
			return false;
		}
		else
		{
			isValid = true;
		}

		if (!Validate.notEmpty(time2))
		{
			return false;
		}
		else
		{
			isValid = true;
		}

		return isValid;
	}

	private void ValidationError()
	{

		String addr = mAddressEdit.getText().toString();
		String state = mStateEdit.getText().toString();
		String city = mCityEdit.getText().toString();
		String postal = mPostalEdit.getText().toString();
		String date = dateView.getText().toString();
		String time1 = timeSlotView1.getText().toString();
		String time2 = timeSlotView2.getText().toString();

		if (!Validate.notEmpty(addr))
		{

			mAddressEdit.requestFocus();
			mAddressEdit.setError("Enter address");
		}

		if (!Validate.notEmpty(state))
		{

			mStateEdit.requestFocus();
			mStateEdit.setError("Enter State");
		}

		if (!Validate.notEmpty(city))
		{

			mCityEdit.requestFocus();
			mCityEdit.setError("Enter city");
		}
		if (postalrel.getVisibility() == View.VISIBLE)
		{

			if (!Validate.notEmpty(postal))
			{
				mPostalEdit.setError("Enter PinCode");

			}
			else
			{
				if (postal.length() < 6)
				{
					mPostalEdit.setError("Invalid PinCode");
				}
			}
		}
		if (!Validate.notEmpty(date))
		{

			dateView.setError("Select Date");
		}

		if (!Validate.notEmpty(time1))
		{

			timeSlotView1.setError("Select From Time");
		}

		if (!Validate.notEmpty(time2))
		{

			timeSlotView2.setError("Select To Time");
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

		if (Constants.isPatientDetails)
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

			username.setText(patientData1.getFname() + " " + patientData1.getLname() + "");

		}
		else
		{
			String uname = "";

			if (Constants.isFamilySel)
			{
				uname = SharedPrefsUtil.getStringPreference(context, "selectedPerson");
				username.setText(uname);
			}
			else
			{
				if (userData != null && userData.getFirst_name() != null
						&& !userData.getFirst_name().equalsIgnoreCase("null"))
					uname = userData.getFirst_name() + " " + userData.getLast_name() + "";
				username.setText(uname);
			}

		}
		return userData;
	}

}
