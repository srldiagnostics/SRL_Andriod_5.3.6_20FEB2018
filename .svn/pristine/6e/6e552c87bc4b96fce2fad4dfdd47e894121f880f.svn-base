package com.srllimited.srl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.srllimited.srl.data.AboutUsData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.serverapis.ApiConstants;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.util.Validate;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class AboutUsActivity extends MenuBaseActivity
{
	public static Activity aboutus;
	private final String screen_id = "SCREEN_ID";
	private final String screen_name = "SCREEN_NAME";
	private final String content = "CONTENT";
	private final String mysrlver = "MYSRLVER";
	Context context;
	TextView about_us_content, versionum;
	List<AboutUsData> mAboutUsDatasList = new ArrayList<>();
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

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.about_us);
		context = AboutUsActivity.this;
		aboutus = this;
		header_loc_name.setText("About Us");
		header_loc_name.setEnabled(false);
		versionum = (TextView) findViewById(R.id.versionnum);
		versionum.setText(ApiConstants.appVersion);
		about_us_content = (TextView) findViewById(R.id.about_us_content);
		sendRequest(ApiRequestHelper.getContent(context, "ABOUT_US", ApiConstants.appVersion));
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
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
						if (aboutUsData.getSCREEN_NAME().equalsIgnoreCase("ABOUT US"))
						{
							if (aboutUsData.getCONTENT() != null && !aboutUsData.getCONTENT().equalsIgnoreCase("null"))
								versionum.setText(aboutUsData.getCONTENT() + "");
						}
						if (aboutUsData.getSCREEN_NAME().equalsIgnoreCase("MESSAGE"))
						{
							if (aboutUsData.getCONTENT() != null && !aboutUsData.getCONTENT().equalsIgnoreCase("null"))
								about_us_content.setText(aboutUsData.getCONTENT() + "");
						}
					}
				}
			}
		}

	}

}