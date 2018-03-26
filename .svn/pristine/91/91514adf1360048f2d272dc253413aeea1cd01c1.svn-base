package com.srllimited.srl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;

import com.srllimited.srl.adapters.LabVisitAdapter;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.LabLocatorsData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.widget.UISearchBar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

/**
 * Created by Ruchi Tiwari on 12/23/2016.
 */

public class VisitLabsList extends MenuBaseActivity
{

	public static Activity labvisit;
	public static Context context;
	private final List<LabLocatorsData> mLabData = new ArrayList<>();
	String data = "data";
	String CNTCT_PRSN = "CNTCT_PRSN";
	TextView lab_heading;
	RecyclerView.Adapter mAdapter;
	private String LCTN_CD = "LCTN_CD";
	private String LCTN_NM = "LCTN_NM";
	private String ADDR = "ADDR";
	private String CITY = "CITY";
	private String STATE = "STATE";
	private String PHN = "PHN";
	private String EMAIL = "EMAIL";
	private String FAX = "FAX";
	private String ZIP = "ZIP";
	private String TYP = "TYP";
	private String MAP_LOCATION = "MAP_LOCATION";
	private String LATITUDE = "LATITUDE";
	private String LONGITUDE = "LONGITUDE";
	private String DISTANCE = "DISTANCE";
	private String SORT_ORDER = "SORT_ORDER";
	private List<LabLocatorsData> mLabLocatorsDatas = new ArrayList<>();
	private List<LabLocatorsData> mLabFilteredData = new ArrayList<>();
	UISearchBar.OnSearchListener mSearchLister = new UISearchBar.OnSearchListener()
	{
		@Override
		public void onClearSearch()
		{
			loadDefaultData();
		}

		@Override
		public void onSearchText(String text)
		{
			mLabFilteredData.clear();
			if (Validate.notEmpty(text))
			{
				for (LabLocatorsData data : mLabLocatorsDatas)

				{
					if (text.length() > 0)
					{
						if (isSearchData(data.getLCTN_NM(), text))
						{
							mLabFilteredData.add(data);
						}
					}

				}

				setData();
			}
			else
			{
				loadDefaultData();
			}

		}
	};
	private UISearchBar mSearchView;
	private RecyclerView mRecyclerView;
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			switch (request.getReferralCode())
			{
				case LAB_LOCATIONS:
				{
					setLocations(serverResponseData.getArrayData());
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
		super.addContentView(R.layout.lab_list);
		context = VisitLabsList.this;
		labvisit = this;

		Constants.isRadio = true;
		lab_heading = (TextView) findViewById(R.id.lab_heading);
		header_loc_name.setText("Select A Lab location");
		header_loc_name.setEnabled(false);
		mSearchView = (UISearchBar) findViewById(R.id.searchView);
		mRecyclerView = (RecyclerView) findViewById(R.id.test_receyclerview);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		mRecyclerView.setHasFixedSize(true);

		mAdapter = new LabVisitAdapter(context, mLabLocatorsDatas, false);
		mRecyclerView.setAdapter(mAdapter);

		mSearchView.setOnSearchListener(mSearchLister);

		sendRequest(ApiRequestHelper.getLabLocations(context,
				SharedPrefsUtil.getStringPreference(context, "selectedcity"), "", "", "1", "5", 0 + "", 0 + ""));
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		Constants.isPatientDetails = false;//
	}

	private void setLocations(JSONArray jArray)
	{
		if (Validate.notNull(jArray))
		{
			mLabLocatorsDatas.clear();
			mLabData.clear();

			for (int i = 0; i < jArray.length(); i++)
			{
				try
				{
					LabLocatorsData labLocatorsData = LabLocatorsData.getGet(jArray.getJSONObject(i));
					int laballowed = 0;

					try
					{
						laballowed = (int) labLocatorsData.getLABVISITALLOWED();
					}
					catch (Exception e)
					{

					}

					if (labLocatorsData.getTYP().equalsIgnoreCase("0") && laballowed == 1)
						mLabLocatorsDatas.add(labLocatorsData);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}

			Collections.sort(mLabLocatorsDatas, new Comparator<LabLocatorsData>()
			{
				@Override
				public int compare(LabLocatorsData o1, LabLocatorsData o2)
				{

					final String d1 = o1.getLCTN_NM();
					final String d2 = o2.getLCTN_NM();
					return d1.compareToIgnoreCase(d2);
					// return (d1 < d2) ? -1 : (d1 < d2) ? 1 : 0;

				}
			});
			mLabData.addAll(mLabLocatorsDatas);

			char firstLetter = Character
					.toUpperCase(SharedPrefsUtil.getStringPreference(context, "selectedcity").toString().charAt(0));
			String CityName = firstLetter + SharedPrefsUtil.getStringPreference(context, "selectedcity").toString()
					.substring(1).toLowerCase(Locale.getDefault());

			if (mLabLocatorsDatas != null && mLabLocatorsDatas.size() > 0)
				lab_heading.setText("Found " + mLabLocatorsDatas.size() + " Labs in " + CityName + "");
			else
				lab_heading.setText("No lab location found in " + CityName + "");

			mAdapter.notifyDataSetChanged();
		}
	}

	private boolean isSearchFirstCharData(String name, String searchTxt)
	{
		return (Validate.notEmpty(name) && name.toLowerCase().charAt(0) == searchTxt.toLowerCase().charAt(0));
	}

	private boolean isSearchData(String name, String searchTxt)
	{
		return (Validate.notEmpty(name) && name.toLowerCase().contains(searchTxt.toLowerCase()));
	}

	private void loadDefaultData()
	{
		mLabLocatorsDatas.clear();
		for (LabLocatorsData labLocatorsData : mLabData)
		{
			mLabLocatorsDatas.add(labLocatorsData);
		}
		if (mLabLocatorsDatas != null && mLabLocatorsDatas.size() > 0)
			lab_heading.setText("Found " + mLabLocatorsDatas.size() + " Labs in "
					+ SharedPrefsUtil.getStringPreference(context, "selectedcity").toString().toLowerCase() + "");
		else
			lab_heading.setText("No lab location found in "
					+ SharedPrefsUtil.getStringPreference(context, "selectedcity").toString().toLowerCase() + "");

		mAdapter.notifyDataSetChanged();
	}

	private void setData()
	{

		mLabLocatorsDatas.clear();
		if (mLabLocatorsDatas != null)
		{
			for (LabLocatorsData labLocatorsData : mLabFilteredData)
			{
				mLabLocatorsDatas.add(labLocatorsData);
			}
		}

		if (mLabLocatorsDatas != null && mLabLocatorsDatas.size() > 0)
			lab_heading.setText("Found " + mLabLocatorsDatas.size() + " Labs in "
					+ SharedPrefsUtil.getStringPreference(context, "selectedcity").toString().toLowerCase() + "");
		else
			lab_heading.setText("No lab location found in "
					+ SharedPrefsUtil.getStringPreference(context, "selectedcity").toString().toLowerCase() + "");

		mAdapter.notifyDataSetChanged();
	}

}
