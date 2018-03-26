package com.srllimited.srl;

import java.util.ArrayList;
import java.util.Calendar;

import com.srllimited.srl.data.BmiUsersData;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.utilities.AppDataBaseHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Codefyne on 14-02-2017.
 */
public class BmiCalculation extends MenuBaseActivity implements View.OnClickListener
{
	public static Activity bmical;
	public static ArrayList<BmiUsersData> _bmiUsersData = new ArrayList<>();
	Activity activity;
	Context context;
	TextView name, weight, height;
	Button calculate, close;
	String bmi_indicates = "";
	TextView txtxcalculation, gender;
	TextView your_body_txt, your_idel_txt, your_bmi_indicates_txt, muscle_txt;
	WebView bmi_descprtion, bmi_descprtion_second;
	String myData, myData2;
	String htmlText;
	Double weigthIncreaseValue;
	String bmiresult;
	LinearLayout bmiCircle;
	AppDataBaseHelper appDb = new AppDataBaseHelper(this);
	private double bmi = 0;
	private String nameStr = "", genderStr = "", heightStr = "", weightStr = "";
	private String resulttext;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.bmi_details_activity);
		bmical = this;
		name = (TextView) findViewById(R.id.name);
		gender = (TextView) findViewById(R.id.gender);
		weight = (TextView) findViewById(R.id.weigth);
		height = (TextView) findViewById(R.id.heigth);
		calculate = (Button) findViewById(R.id.recalculate);
		close = (Button) findViewById(R.id.close);
		txtxcalculation = (TextView) findViewById(R.id.txtxcalculation);
		header_loc_name.setEnabled(false);
		your_body_txt = (TextView) findViewById(R.id.your_body_txt);
		your_idel_txt = (TextView) findViewById(R.id.your_idel_txt);
		your_bmi_indicates_txt = (TextView) findViewById(R.id.your_bmi_indicates_txt);
		bmiCircle = (LinearLayout) findViewById(R.id.bmiCircle);

		bmi_descprtionWebView();

		context = BmiCalculation.this;
		appDb = new AppDataBaseHelper(context);

		header_loc_name.setText("Calculate BMI");
		header_layout.setEnabled(false);
		history.setVisibility(View.VISIBLE);
		history.setOnClickListener(this);

		calculate.setOnClickListener(this);
		close.setOnClickListener(this);

		try
		{
			Bundle b = new Bundle();
			b = getIntent().getExtras();

			nameStr = b.getString("name");
			genderStr = b.getString("gender");
			//valueheight = b.getDouble("height");
			heightStr = b.getString("height");
			weightStr = b.getString("weight");

			if (!nameStr.equalsIgnoreCase("null") && nameStr != null && !nameStr.isEmpty())
			{
				name.setText(nameStr);
			}

			if (!genderStr.equalsIgnoreCase("null") && !genderStr.isEmpty() && genderStr != null)
			{
				gender.setText(genderStr + "");
			}

			if (heightStr != null && !heightStr.isEmpty())
			{
				height.setText(heightStr + " /Cm " + "");
			}

			if (weightStr != null && !weightStr.isEmpty())
			{
				weight.setText(weightStr + " /Kg" + "");
			}

			bmiCaliculation();
		}
		catch (Exception e)
		{
			e.printStackTrace();

		}
	}

	private void bmi_descprtionWebView()
	{
		bmi_descprtion = (WebView) findViewById(R.id.bmi_descprtion);
		bmi_descprtion_second = (WebView) findViewById(R.id.bmi_descprtion_second);
		htmlText = " %s ";
		myData = "Maintain your weight range by doing moderate intensity exercises like brisk walking, swiming, gardening, jogging, for 30 to 45 minutes everyday.";
		myData2 = "Muscle and bone strengthening activities prevent loss of bone density at old age";
		bmi_descprtion.loadData(String.format(htmlText, myData), "text/html", "utf-8");
		bmi_descprtion_second.loadData(String.format(htmlText, myData2), "text/html", "utf-8");
		//"<p style=\"text-align: justify\">"+ "  [YOUR TEXT] " +"</p>", "text/html", "UTF-8"
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.recalculate:
				Util.killBMIReg();
				Intent i = new Intent(BmiCalculation.this, BmiRegisterActivity.class);
				startActivity(i);
				break;
			case R.id.close:
				Intent in1 = new Intent(BmiCalculation.this, Dashboard.class);
				startActivity(in1);
				finish();
				// finish();
				break;
			case R.id.history:
				Intent in = new Intent(BmiCalculation.this, BmiListView.class);
				startActivity(in);
				//addingInBmiDataBase();
				//Toast.makeText(getApplicationContext(), "It wil come soon..!! ", Toast.LENGTH_LONG).show();
				/* Intent in = new Intent(BmiCalculation.this, BmiListView.class);
				startActivity(in);*/
				break;
		}
	}

	private void addingInBmiDataBase()
	{
		String currentDate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
		_bmiUsersData = new ArrayList<>();
		BmiUsersData _bmidataset = new BmiUsersData();
		_bmidataset.setUserName_bmi(nameStr + "");
		_bmidataset.setGender_bmi(genderStr + "");
		_bmidataset.setWeight_bmi(weightStr + "");
		_bmidataset.setHeight_bmi(heightStr + "");
		_bmidataset.setMassindex_bmi(bmiresult + "");
		_bmidataset.setIdealweight_bmi(currentDate + ""); // here passing Date and Time
		_bmidataset.setIndication_bmi(bmi_indicates + "");
		_bmiUsersData.add(_bmidataset);
		appDb.addBmiUserDetails(_bmidataset);
		Util.killBMIList();

	}

	private void bmiCaliculation()
	{
		Double valueheightmeters;
		valueheightmeters = Double.parseDouble(heightStr) / 100; // Converting to meters.
		bmi = (Double.parseDouble(weightStr) / (valueheightmeters * valueheightmeters));

		if (bmi >= 30)
		{ /* obese */
			bmi_indicates = "Above Obese";
			bmiCircle.setBackgroundResource(R.color.emptycolor);
			bmiCircle.setBackgroundResource(R.drawable.bmi_red_circle);
			setBmiData(bmi_indicates);
		}
		else if (bmi >= 25)
		{
			bmi_indicates = "Over Weight";
			bmiCircle.setBackgroundResource(R.color.emptycolor);
			bmiCircle.setBackgroundResource(R.drawable.bmi_red_circle);
			setBmiData(bmi_indicates);
		}
		else if (bmi >= 18.5)
		{
			bmi_indicates = "Normal";
			bmiCircle.setBackgroundResource(R.color.emptycolor);
			bmiCircle.setBackgroundResource(R.drawable.bmi_green_circle);
			setBmiData(bmi_indicates);
		}
		else
		{
			bmi_indicates = "Under Weight";
			bmiCircle.setBackgroundResource(R.color.emptycolor);
			bmiCircle.setBackgroundResource(R.drawable.bmi_red_circle);
			setBmiData(bmi_indicates);
		}

	}

	private void setBmiData(String bmi_indicates)
	{
		bmiresult = String.format("%1.2f", bmi);
		txtxcalculation.setText(bmiresult);
		redColorString1();

		/*double d = bmi;
		String str = String.format("%1.2f", d);
		d = Double.valueOf(str);
		txtxcalculation.setText(String.valueOf(d));*/
		//redColorString1();

		redColorString2(bmi_indicates);
		redColorString3();

		addingInBmiDataBase();

	}

	private void redColorString1()
	{
		SpannableStringBuilder builder = new SpannableStringBuilder();
		SpannableString str1 = new SpannableString(txtxcalculation.getText().toString());
		str1.setSpan(new ForegroundColorSpan(Color.RED), 0, str1.length(), 0);
		builder.append(str1);
		your_body_txt.setText(builder, TextView.BufferType.SPANNABLE);
	}

	private void redColorString2(String bmi_indicates)
	{
		String first = "Your BMI indicates that your weight is ";
		String next = "<font color='#EE0000'>" + bmi_indicates + "</font>";
		your_bmi_indicates_txt.setText(Html.fromHtml(first + next));
		your_bmi_indicates_txt.setTextSize(15);
	}

	private void redColorString3()
	{
		weigthIncreaseValue = Double.parseDouble(weightStr) + 4.0;
		String first = "Your ideal weight range based on your BMI should be between ";
		String next = "<font color='#EE0000'>" + weightStr + " to " + weigthIncreaseValue + " Kgs" + "</font>";
		your_idel_txt.setText(Html.fromHtml(first + next));
		your_idel_txt.setTextSize(15);
	}
}
