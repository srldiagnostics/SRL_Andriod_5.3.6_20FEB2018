package com.srllimited.srl;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.CategoryData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class BookATestFilterActivity extends Activity
{
	public static final String selectedCategoryId = "category";

	public static final String selectedType = "type";
	private static final String data = "data";
	private static final String id = "ID";
	private static final String name = "NAME";
	private static final String order = "C_ORDER";
	public static boolean isFilter = true;
	private ImageView close;

	private TextView apply;

	private Context context;

	private RadioGroup mRadioGroup;

	private ScrollView ScrlView;

	private CheckBox mTestCheckBox, mPackagesCheckBox;

	private ArrayList<CategoryData> mCategoryDatas = new ArrayList<>();

	private FirebaseAnalytics firebaseAnalytics;
	private View.OnClickListener mClickListener = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.close:
					finish();
					break;

				case R.id.apply:
					if (validateCheckBox())
					{
						selectedFiltered();
					}
					//					else
					//					{
					//						Toast.makeText(context, "Please select Test/Packages", Toast.LENGTH_SHORT).show();
					//					}
					break;

			}
		}
	};
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			switch (request.getReferralCode())
			{
				case ALL_CATEGORIES:
					try
					{
						JSONArray jArray = serverResponseData.getArrayData();
						mCategoryDatas = new ArrayList<>();
						if (jArray != null)
						{
							for (int i = 0; i < jArray.length(); i++)
							{
								CategoryData categoryData = new CategoryData();
								categoryData.setID(jArray.getJSONObject(i).getDouble(id));
								categoryData.setNAME(jArray.getJSONObject(i).getString(name));
								categoryData.setC_ORDER(jArray.getJSONObject(i).getDouble(order));

								mCategoryDatas.add(categoryData);
							}
						}
					}
					catch (JSONException e)
					{
						e.printStackTrace();
					}

					if (mCategoryDatas != null)
					{
						setCategoriesToRadio(mCategoryDatas);
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
		setContentView(R.layout.book_a_test_filter);
		context = BookATestFilterActivity.this;
		close = (ImageView) findViewById(R.id.close);
		mTestCheckBox = (CheckBox) findViewById(R.id.filter_test);
		mPackagesCheckBox = (CheckBox) findViewById(R.id.filter_pacakge);
		// Obtain the Firebase Analytics instance.
		firebaseAnalytics = FirebaseAnalytics.getInstance(this);
		Bundle bundle = new Bundle();
		bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, 1);
		bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Filter");
		//Logs an app event.
		firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
		//Sets whether analytics collection is enabled for this app on this device.
		firebaseAnalytics.setAnalyticsCollectionEnabled(true);
		//flyer not requird on this page as mention in email
		//Facebook Analytic
		AppEventsLogger logger = AppEventsLogger.newLogger(this);
		logger.logEvent("Filter");

		apply = (TextView) findViewById(R.id.apply);
		mRadioGroup = (RadioGroup) findViewById(R.id.filter_radio_group);
		ScrlView = (ScrollView) findViewById(R.id.ScrlView);
		close.setOnClickListener(mClickListener);
		apply.setOnClickListener(mClickListener);

		if (Constants.isPackage)
		{
			mTestCheckBox.setVisibility(View.GONE);
		}

		sendRequest(ApiRequestHelper.getAllCategories(context));

	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	private void setCategoriesToRadio(ArrayList<CategoryData> categories)
	{
		for (int i = 0; i < categories.size(); i++)
		{
			int myInt = 0;
			RadioButton mRadioButton = new RadioButton(this);
			mRadioButton.setText(Html.fromHtml(categories.get(i).getNAME()));
			mRadioButton.setId(myInt = (int) categories.get(i).getID());
			mRadioButton.setPadding(5, 5, 5, 5);
			mRadioButton.setTextColor(getResources().getColor(R.color.HeaderTitleColor));
			mRadioGroup.addView(mRadioButton);
		}
	}

	private void selectedFiltered()
	{
		Intent intent = new Intent();
		String selectedRadioButton = "";
		try
		{
			RadioButton rd = (RadioButton) findViewById(mRadioGroup.getCheckedRadioButtonId());
			selectedRadioButton = rd.getText().toString();
		}
		catch (Exception e)
		{

		}
		intent.putExtra(selectedCategoryId, selectedRadioButton + "");
		if (mPackagesCheckBox.isChecked() && mTestCheckBox.isChecked())
		{
			intent.putExtra(selectedType, "B");
		}
		else
		{
			if (mPackagesCheckBox.isChecked())
			{
				intent.putExtra(selectedType, "P");
			}
			else if (mTestCheckBox.isChecked())
			{
				intent.putExtra(selectedType, "T");
			}
			if (!mPackagesCheckBox.isChecked() && !mTestCheckBox.isChecked())
			{
				intent.putExtra(selectedType, "B");
			}
		}
		setResult(BookATestActivity.RESULT_CODE_FILTERED_PRODUCT, intent);
		finish();
	}

	private boolean validateCheckBox()
	{
		boolean isChecked = false;
		if (mPackagesCheckBox.isChecked() && mTestCheckBox.isChecked())
		{
			isChecked = true;
		}
		if (mPackagesCheckBox.isChecked())
		{
			isChecked = true;
		}
		if (mTestCheckBox.isChecked())
		{
			isChecked = true;
		}
		String selectedRadioButton = "";
		try
		{
			RadioButton rd = (RadioButton) findViewById(mRadioGroup.getCheckedRadioButtonId());
			selectedRadioButton = rd.getText().toString();
		}
		catch (Exception e)
		{

		}

		if (selectedRadioButton != null && !selectedRadioButton.isEmpty())
			isChecked = true;

		if (!isChecked)
			Toast.makeText(context, "Please select any option for filteration", Toast.LENGTH_SHORT).show();
		//		else
		//		Toast.makeText(context,"Please select condition", Toast.LENGTH_SHORT).show();

		return isChecked;
	}
}