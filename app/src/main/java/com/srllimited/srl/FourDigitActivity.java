package com.srllimited.srl;

import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FourDigitActivity extends MenuBaseActivity implements View.OnClickListener
{
	public static Activity fourdigit;

	Context context;

	Button next, done, reset, skip;

	LinearLayout confirm_pin_layout, enter_pin_layout;

	EditText pin1, pin2, pin3, pin4, confirm1, confirm2, confirm3, confirm4;

	View view;
	AlertDialog alert;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.set_pin_activity);
		context = FourDigitActivity.this;
		header_loc_name.setEnabled(false);

		fourdigit = this;
		String pin = SharedPrefsUtil.getStringPreference(context, "fourdigitpin");

		if (Validate.notEmpty(pin))
			header_loc_name.setText("Reset PIN");
		else
			header_loc_name.setText("Set PIN");

		if (Constants.isResetPIN)
		{
			navBack.setVisibility(View.GONE);
			navToggle.setVisibility(View.GONE);
		}

		reset = (Button) findViewById(R.id.reset);
		next = (Button) findViewById(R.id.next);
		done = (Button) findViewById(R.id.done);
		skip = (Button) findViewById(R.id.skip);

		confirm_pin_layout = (LinearLayout) findViewById(R.id.confirm_pin_layout);
		enter_pin_layout = (LinearLayout) findViewById(R.id.enter_pin_layout);

		pin1 = (EditText) findViewById(R.id.pin1);
		pin2 = (EditText) findViewById(R.id.pin2);
		pin3 = (EditText) findViewById(R.id.pin3);
		pin4 = (EditText) findViewById(R.id.pin4);

		confirm1 = (EditText) findViewById(R.id.confirm1);
		confirm2 = (EditText) findViewById(R.id.confirm2);
		confirm3 = (EditText) findViewById(R.id.confirm3);
		confirm4 = (EditText) findViewById(R.id.confirm4);

		String setpin = SharedPrefsUtil.getStringPreference(context, "setpin");

		if (Validate.notEmpty(setpin) && setpin.equalsIgnoreCase("true"))
		{
			setSkip();
		}
		else
			setDone();

		hideConfirmPIN();

		next.setOnClickListener(this);
		done.setOnClickListener(this);
		skip.setOnClickListener(this);

		pin1.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

				if (count > 0)
				{
					setChangeEditText(pin2);

				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		});

		pin2.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

				if (count > 0)
				{
					setChangeEditText(pin3);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		});

		pin3.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

				if (count > 0)
				{
					setChangeEditText(pin4);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		});
		pin4.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

				if (count > 0)
				{

					Util.hideSoftKeyboard(context, pin4);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		});

		confirm1.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

				if (count > 0)
				{
					setChangeEditText(confirm2);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		});

		confirm2.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

				if (count > 0)
				{
					setChangeEditText(confirm3);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		});

		confirm3.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

				if (count > 0)
				{
					setChangeEditText(confirm4);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		});

		confirm4.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{

			}

			@Override
			public void afterTextChanged(Editable s)
			{

			}
		});

		reset.setVisibility(View.VISIBLE);

		reset.setOnClickListener(this);

	}

	@Override
	public void onClick(final View view)
	{
		switch (view.getId())
		{

			case R.id.skip:
				showSkipAlert();

				break;

			case R.id.reset:
				resetPin();
				break;
			case R.id.next:
				if (validatePIN())
				{
					next.setVisibility(View.INVISIBLE);
					showConfirmPIN();

				}
				else
				{
					Toast.makeText(context, "Please enter complete PIN", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.done:

				String setpin = SharedPrefsUtil.getStringPreference(context, "setpin");

				if (validatePIN() && validateConfirmPIN() && getEqual())
				{
					if (Validate.notEmpty(setpin) && setpin.equalsIgnoreCase("true"))
					{
						String pin = pin1.getText().toString() + pin2.getText().toString() + pin3.getText().toString()
								+ pin4.getText().toString();
						SharedPrefsUtil.setStringPreference(context, "fourdigitpin", pin);
						SharedPrefsUtil.setBooleanPreference(context, "splashpin", true);
						navigateToActivity();
					}
					else
					{
						String pin = pin1.getText().toString() + pin2.getText().toString() + pin3.getText().toString()
								+ pin4.getText().toString();
						SharedPrefsUtil.setStringPreference(context, "fourdigitpin", pin);
						if (Constants.isResetPIN)
						{
							Util.killSplashFourDigit();
							Intent intent = new Intent(this, SplashFourdigitPinActivity.class);
							startActivity(intent);
							finish();
						}
						else
						{
							finish();
						}
					}
				}
				else
				{
					Toast.makeText(context, "PIN doesn't match", Toast.LENGTH_SHORT).show();
				}

				break;
		}
	}

	private void showConfirmPIN()
	{
		skip.setVisibility(View.GONE);
		done.setVisibility(View.VISIBLE);
		next.setVisibility(View.GONE);
		confirm_pin_layout.setVisibility(View.VISIBLE);
		enter_pin_layout.setVisibility(View.VISIBLE);
	}

	private void hideConfirmPIN()
	{
		if (getIntent().hasExtra("From"))
		{
			skip.setVisibility(View.GONE);
		}
		else
		{
			skip.setVisibility(View.VISIBLE);
		}
		next.setVisibility(View.VISIBLE);
		done.setVisibility(View.GONE);

		confirm_pin_layout.setVisibility(View.GONE);
		enter_pin_layout.setVisibility(View.GONE);
	}

	private boolean validatePIN()
	{
		boolean ispin = false;

		if (Validate.notEmpty(pin1.getText().toString()))
		{
			if (Validate.notEmpty(pin2.getText().toString()))
			{
				if (Validate.notEmpty(pin3.getText().toString()))
				{
					if (Validate.notEmpty(pin4.getText().toString()))
					{
						ispin = true;
					}
				}
			}
		}
		return ispin;
	}

	private boolean validateConfirmPIN()
	{
		boolean isconfirmpin = false;

		if (Validate.notEmpty(confirm1.getText().toString()))
		{
			if (Validate.notEmpty(confirm2.getText().toString()))
			{
				if (Validate.notEmpty(confirm3.getText().toString()))
				{
					if (Validate.notEmpty(confirm4.getText().toString()))
					{
						isconfirmpin = true;
					}
				}
			}
		}
		return isconfirmpin;
	}

	private boolean getEqual()
	{

		boolean isEqual = false;
		String setpin = pin1.getText().toString().trim() + pin2.getText().toString().trim()
				+ pin3.getText().toString().trim() + pin4.getText().toString().trim();
		String confirmpin = confirm1.getText().toString().trim() + confirm2.getText().toString().trim()
				+ confirm3.getText().toString().trim() + confirm4.getText().toString();
		if (setpin.equalsIgnoreCase(confirmpin))
		{
			isEqual = true;
		}

		return isEqual;
	}

	private void setChangeEditText(EditText ed)
	{
		ed.setBackgroundResource(R.drawable.edittext_modify_states);
		ed.requestFocus();
		ed.setEnabled(true);
	}

	private void resetPin()
	{
		pin1.requestFocus();
		pin2.setEnabled(false);
		pin3.setEnabled(false);
		pin4.setEnabled(false);
		confirm2.setEnabled(false);
		confirm3.setEnabled(false);
		confirm4.setEnabled(false);

		pin1.setText("");
		pin2.setText("");
		pin3.setText("");
		pin4.setText("");
		confirm1.setText("");
		confirm2.setText("");
		confirm3.setText("");
		confirm4.setText("");
		hideConfirmPIN();

		pin2.setBackgroundResource(R.drawable.edittext_modify_states_grey);
		pin3.setBackgroundResource(R.drawable.edittext_modify_states_grey);
		pin4.setBackgroundResource(R.drawable.edittext_modify_states_grey);

		confirm2.setBackgroundResource(R.drawable.edittext_modify_states_grey);
		confirm3.setBackgroundResource(R.drawable.edittext_modify_states_grey);
		confirm4.setBackgroundResource(R.drawable.edittext_modify_states_grey);

	}

	@Override
	public void onBackPressed()
	{

		if (Constants.isResetPIN)
		{
			Util.killResetPin();
			Intent intent = new Intent(this, ResetPinActivity.class);
			startActivity(intent);
			finish();
		}
		super.onBackPressed();
	}

	private void navigateToActivity()
	{
		int navigationcount = 0;

		navigationcount = SharedPrefsUtil.getIntegerPreference(FourDigitActivity.this,
				Constants.sharedPreferenceSelectedLoginActivity, 0);
		Intent intent = null;
		switch (navigationcount)
		{
			case Constants.m_pwd:
				Util.killChangePwd();
				intent = new Intent(FourDigitActivity.this, ChangePwdActivity.class);

				break;
			case Constants.m_orders:
				Util.killOrders();
				intent = new Intent(FourDigitActivity.this, OrdersActivity.class);
				break;
			case Constants.m_login:
				Util.killDashBoard();
				intent = new Intent(FourDigitActivity.this, Dashboard.class);
				break;
			case Constants.m_reports:
				Util.killMyReportEntry();
				intent = new Intent(FourDigitActivity.this, MyReportEntryDetails.class);
				finish();
				break;
			case Constants.m_cart:
				Util.killAddPatient();
				Constants.isLabOrCollection = false;
				intent = new Intent(FourDigitActivity.this, AddPatientActivity.class);
				finish();
				break;
			case Constants.m_lab:
				Util.killAddPatient();
				Constants.isLabOrCollection = true;
				intent = new Intent(FourDigitActivity.this, AddPatientActivity.class);
				finish();
				break;

			case Constants.m_family:
				Util.killMyFamily();
				intent = new Intent(FourDigitActivity.this, MyFamilyActivity.class);
				finish();
				break;

			case Constants.m_profile:
				Util.killMyProfile();
				intent = new Intent(FourDigitActivity.this, MyProfileActivity.class);
				finish();
				break;
			case Constants.m_health:
				Util.killHealthTrack();
				intent = new Intent(FourDigitActivity.this, HealthTracker.class);
				finish();
				break;

			case Constants.m_settings:
				Util.killSettings();
				intent = new Intent(FourDigitActivity.this, SettingsActivity.class);
				finish();
				break;

			default:
				Util.killDashBoard();
				intent = new Intent(FourDigitActivity.this, Dashboard.class);
				finish();
				break;

		}

		if (intent != null)
		{
			startActivity(intent);
			finish();
		}
	}

	private void setSkip()
	{
		skip.setVisibility(View.VISIBLE);
		done.setVisibility(View.GONE);
	}

	private void setDone()
	{
		done.setVisibility(View.VISIBLE);
		skip.setVisibility(View.GONE);
	}

	public void showSkipAlert()
	{

		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(FourDigitActivity.this,
				android.app.AlertDialog.THEME_HOLO_LIGHT);
		builder.setMessage("Skipping the PIN security may lead to unauthorized access.Do you still want to skip?")
				.setCancelable(false).setPositiveButton("No", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						if (dialog != null)
							dialog.dismiss();
					}
				}).setNegativeButton("YES", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						navigateToActivity();
					}
				});

		alert = builder.create();
		alert.show();
	}
}