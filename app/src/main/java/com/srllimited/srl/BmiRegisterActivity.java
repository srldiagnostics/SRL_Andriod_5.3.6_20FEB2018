package com.srllimited.srl;

import java.util.ArrayList;

import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Codefyne on 14-02-2017.
 */

public class BmiRegisterActivity extends MenuBaseActivity implements View.OnClickListener
{
	public static Activity bmireg;
	Activity activity;
	Context context;

	EditText name, weigth, heigth;

	TextView gender;

	Button calculate, clear;
	TextView txtxcalculation;
	ImageView cancel, confirm;
	LinearLayout hidePopup;
	TextView popupheader;
	ListView listView;
	ArrayList<String> salutationList = new ArrayList<String>();
	Animation bottomDown;
	String nameStr;
	String genderStr;
	String weigthStr;
	String heigthStr;
	private double bmi = 0;
	private double valueheight = 0;
	private double valueweight = 0;
	private String resulttext;
	private String selectedListItem = "";

	// validate first name
	public static boolean validateName(String firstName)
	{
		return firstName.matches("[A-Z][a-zA-Z]*");
	}

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.bmi_calc_register_activity);
		bmireg = this;
		name = (EditText) findViewById(R.id.name);
		weigth = (EditText) findViewById(R.id.weigth);
		heigth = (EditText) findViewById(R.id.heigth);
		calculate = (Button) findViewById(R.id.calculate);
		clear = (Button) findViewById(R.id.clear);
		txtxcalculation = (TextView) findViewById(R.id.txtxcalculation);
		header_loc_name.setEnabled(false);
		gender = (TextView) findViewById(R.id.gender);
		hidePopup = (LinearLayout) findViewById(R.id.hidePopup);
		cancel = (ImageView) findViewById(R.id.cancel);
		confirm = (ImageView) findViewById(R.id.confirm);
		popupheader = (TextView) findViewById(R.id.popup_header);
		listView = (ListView) findViewById(R.id.popup_list);

		context = BmiRegisterActivity.this;

		/*header_loc_name.setText("Calculate BMI");
		header_layout.setEnabled(false);*/

		header_loc_name.setText("Calculate BMI");
		header_layout.setEnabled(false);
		history.setText("History");
		history.setVisibility(View.VISIBLE);
		history.setOnClickListener(this);

		calculate.setOnClickListener(this);
		clear.setOnClickListener(this);

		gender.setOnClickListener(this);
		confirm.setOnClickListener(this);
		cancel.setOnClickListener(this);

		bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				selectedListItem = salutationList.get(position);
			}
		});

	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.calculate:
				bmiCaliculation();
				break;

			case R.id.clear:
				clearData();
				break;
			case R.id.gender:
				Util.hideSoftKeyboard(context, view);
				popupheader.setText("Gender");
				Util.hideSoftKeyboard(context, view);
				salutationList = new ArrayList<String>();
				salutationList.add("Male");
				salutationList.add("Female");
				salutationList.add("Transgender");
				setPopupListAdapter(salutationList);

				break;
			case R.id.cancel:
				setEnable();
				selectedListItem = "";
				hidePopup.startAnimation(bottomDown);
				hidePopup.setVisibility(View.INVISIBLE);
				break;

			case R.id.confirm:
				if (Validate.notEmpty(selectedListItem))
				{
					gender.setText(selectedListItem);
				}

				hidePopup.startAnimation(bottomDown);
				hidePopup.setVisibility(View.INVISIBLE);
				setEnable();
				break;
			case R.id.history:
				Intent in = new Intent(BmiRegisterActivity.this, BmiListView.class);
				startActivity(in);
				break;
		}
	}

	private void clearData()
	{
		heigth.setText("");
		weigth.setText("");
		name.setText("");
		gender.setText("--Select--");
		nameStr = "";
		genderStr = "";
		heigthStr = "";
		weigthStr = "";
	}

	private void bmiCaliculation()
	{
		try
		{
			boolean ret;
			ret = validationEntryData();
			if (ret)
			{
				Util.killBMICalc();
				Intent i = new Intent(BmiRegisterActivity.this, BmiCalculation.class);
				i.putExtra("name", nameStr);
				i.putExtra("gender", genderStr);
				i.putExtra("height", heigthStr);
				i.putExtra("weight", weigthStr);
				startActivity(i);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private boolean validationEntryData()
	{
		boolean ret = true;
		nameStr = name.getText().toString();
		genderStr = gender.getText().toString();
		heigthStr = heigth.getText().toString();
		weigthStr = weigth.getText().toString();

		if (nameStr == null || nameStr.isEmpty())
		{
			Toast.makeText(getApplicationContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
			ret = false;
			return ret;
		}

		if (genderStr == null || genderStr.isEmpty() || genderStr.equalsIgnoreCase("--Select--"))
		{
			Toast.makeText(getApplicationContext(), "Please Select Gender", Toast.LENGTH_SHORT).show();
			ret = false;
			return ret;
		}

		if (Validate.notEmpty(weigthStr))
		{
			valueweight = Double.parseDouble(weigthStr);
			ret = true;
			//return ret;
		}
		else
		{
			if (weigthStr == null || weigthStr.isEmpty())
			{
				Toast.makeText(getApplicationContext(), "Please Enter Weight", Toast.LENGTH_SHORT).show();
				ret = false;
				return ret;
			}
		}
		if (Validate.notEmpty(heigthStr))
		{
			valueheight = Double.parseDouble(heigthStr);
			ret = true;
			//return ret;
		}
		else
		{
			if (heigthStr == null || heigthStr.isEmpty())
			{
				Toast.makeText(getApplicationContext(), "Please Enter Height", Toast.LENGTH_SHORT).show();
				ret = false;
				return ret;
			}
		}

		return ret;
	}

	private void setPopupListAdapter(ArrayList<String> popupLstItems)
	{
		setDisabled();
		ArrayAdapter<String> ad = new ArrayAdapter<String>(this, R.layout.textcenter, R.id.textItem, salutationList);
		listView.setAdapter(ad);

		Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

		hidePopup.startAnimation(bottomUp);
		hidePopup.setVisibility(View.VISIBLE);
	}

	private void setDisabled()
	{
		name.setEnabled(false);
		weigth.setEnabled(false);
		heigth.setEnabled(false);
	}

	private void setEnable()
	{
		name.setEnabled(true);
		weigth.setEnabled(true);
		heigth.setEnabled(true);
	}
}
