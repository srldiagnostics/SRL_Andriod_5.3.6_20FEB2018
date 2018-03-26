package com.srllimited.srl;

import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashFourdigitPinActivity extends MenuBaseActivity
{

	public static Activity splashfour;
	Context context;
	EditText pin1, pin2, pin3, pin4;
	LinearLayout pinerror;
	TextView attempt;
	int countpin = 3;
	TextView retry;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//        setContentView(R.layout.enter_four_digit_pin);
		super.addContentView(R.layout.enter_four_digit_pin);
		context = SplashFourdigitPinActivity.this;
		splashfour = this;
		header_loc_name.setText("Login");
		header_loc_name.setEnabled(false);
		//navToggle.setVisibility(G);
		pin1 = (EditText) findViewById(R.id.pin1);
		pin2 = (EditText) findViewById(R.id.pin2);
		pin3 = (EditText) findViewById(R.id.pin3);
		pin4 = (EditText) findViewById(R.id.pin4);
		navBack.setVisibility(View.GONE);
		navToggle.setVisibility(View.GONE);
		pinerror = (LinearLayout) findViewById(R.id.pinerror);
		attempt = (TextView) findViewById(R.id.attempt);
		retry = (TextView) findViewById(R.id.retry);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		pin1.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{

				if (count > 0)
				{
					setChangeEditText(pin2);
					validatePin();
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
					validatePin();
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
					validatePin();
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
					validatePin();
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

	}

	private boolean getEqual()
	{
		pinerror.setVisibility(View.VISIBLE);

		countpin--;

		boolean isEqual = false;
		String setpin = pin1.getText().toString().trim() + pin2.getText().toString().trim()
				+ pin3.getText().toString().trim() + pin4.getText().toString().trim();
		String confirmpin = SharedPrefsUtil.getStringPreference(context, "fourdigitpin");
		if (setpin.equalsIgnoreCase(confirmpin))
		{
			isEqual = true;
		}
		pin1.setText("");
		pin2.setText("");
		pin3.setText("");
		pin4.setText("");

		return isEqual;
	}

	private void setChangeEditText(EditText ed)
	{
		ed.setBackgroundResource(R.drawable.edittext_modify_states);
		ed.requestFocus();
		ed.setEnabled(true);
	}

	private void validatePin()
	{

		if (isAllPinEntered())
		{
			if (getEqual())
			{
				attempt.setVisibility(View.GONE);
				retry.setVisibility(View.GONE);
				Util.killDashBoard();
				Intent intent = new Intent(getApplicationContext(), Dashboard.class);
				startActivity(intent);
				finish();
			}
			else
			{
				attempt.setVisibility(View.VISIBLE);
				retry.setVisibility(View.VISIBLE);
				attempt.setText("Invalid PIN, " + countpin + " out of 3 attempts left");
				if (countpin == 0)
				{

					Util.killResetPin();
					Intent intent = new Intent(SplashFourdigitPinActivity.this, ResetPinActivity.class);
					startActivity(intent);
					finish();
				}
			}
		}
	}

	private boolean isAllPinEntered()
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
}