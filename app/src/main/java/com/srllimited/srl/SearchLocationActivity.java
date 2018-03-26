package com.srllimited.srl;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.srllimited.srl.data.CityListData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.widget.SearchLocationView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SearchLocationActivity extends Activity
{

	public static final String EXTRA_PARAM_DATA = "com.srllimited.srl.location.data";
	private static final String TAG = Dashboard.class.getSimpleName();
	private static final String JSON_CITY_ID = "CITY_ID";
	private static final String JSON_CITY_NAME = "CITY_NAME";
	private static final String JSON_DISPLAY_NAME = "DISPLAY_NAME";
	private static final String JSON_STATE_ID = "STATE_ID";
	private static final String JSON_FAVOURITE = "FAVOURITE";
	private static final String JSON_ALIASES = "ALIASES";
	public static Activity searchloc;
	Context context;
	boolean isLaunched = false;
	private ProgressDialog mProgressDialog;
	private ArrayList<CityListData> allCitylist;

	private ArrayList<CityListData> mSearchCityList = new ArrayList<>();

	private SearchLocationView mSearchLocationView;
	private SearchLocationView.OnSearchLocationListener mSearchLocationListener = new SearchLocationView.OnSearchLocationListener()
	{

		@Override
		public void onClose()
		{
			finish();
		}

		@Override
		public void onClearSearch()
		{
			loadDefaultData();
		}

		@Override
		public void onSearchText(String text)
		{
			mSearchCityList.clear();
			if (Validate.notEmpty(text))
			{
				try
				{
					for (CityListData data : allCitylist)
					{
						if (text.length() > 0)
						{
							if (isSearchData(data.getCITY_NAME(), text) || isSearchData(data.getDISPLAY_NAME(), text))
							{
								mSearchCityList.add(data);
							}
						}

					}

					setData();
				}
				catch (Exception e)
				{

				}
			}
			else
			{
				loadDefaultData();
			}
			setData();

		}

		@Override
		public void onItemClick(View view, int position)
		{
			try
			{
				CityListData data = mSearchCityList.get(position);
				selectedLocationData(data);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		@Override
		public void onLongItemClick(View view, int position)
		{

		}

		private boolean isSearchFirstCharData(String name, String searchTxt)
		{
			return (Validate.notEmpty(name) && name.toLowerCase().charAt(0) == searchTxt.toLowerCase().charAt(0));
		}

		private boolean isSearchData(String name, String searchTxt)
		{
			return (Validate.notEmpty(name) && name.toLowerCase().contains(searchTxt.toLowerCase()));
		}
	};
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData mserverResponseData)
		{

			if (mserverResponseData != null)
			{
				allCitylist = parseCityList(mserverResponseData.getFullData());

				if (Validate.notEmpty(allCitylist))
				{
					loadDefaultData();
					hideProgressDialog();

				}
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
		setContentView(R.layout.activity_search_location);
		mSearchLocationView = (SearchLocationView) findViewById(R.id.searchLocationView);
		mSearchLocationView.setSearchLocationListener(mSearchLocationListener);
		context = SearchLocationActivity.this;

		fetchData();
		searchloc = this;
		try
		{
			Intent intent = getIntent();
			if (intent != null)
			{
				if (intent.getBooleanExtra("isLaunched", false))
				{
					isLaunched = true;
					mSearchLocationView.mCloseButton.setVisibility(View.GONE);
				}
				else
				{
					isLaunched = false;
					mSearchLocationView.mCloseButton.setVisibility(View.VISIBLE);
				}
			}
		}
		catch (Exception e)
		{

		}
	}

	private void showProgressDialog()
	{
		mProgressDialog = new ProgressDialog(this, AlertDialog.THEME_HOLO_LIGHT);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setMessage("Fetching Cities... Please wait");
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	}

	private void hideProgressDialog()
	{
		if (mProgressDialog != null)
		{
			mProgressDialog.dismiss();
		}
	}

	private void fetchData()
	{
		//        showProgressDialog();

		boolean fetchNewData = true;
		String stroreData = SharedPrefsUtil.getStringPreference(getApplicationContext(), TAG);
		if (Validate.notEmpty(stroreData))
		{
			JSONObject jObj = null;
			try
			{
				jObj = new JSONObject(stroreData);
			}
			catch (Exception e)
			{

			}
			if (jObj != null)
				allCitylist = parseCityList(jObj);
			if (Validate.notEmpty(allCitylist))
			{
				fetchNewData = false;
				loadDefaultData();
				hideProgressDialog();
			}
		}

		if (fetchNewData)
		{
			//            if (isLaunched)
			//                sendRequestWithoutProgress(ApiRequestHelper.getCity());
			//            else
			sendRequest(ApiRequestHelper.getCity(context));
			//            StringRequest req = new StringRequest(Request.Method.GET, ApiConstants.cityListurl,
			//                    new Response.Listener<String>() {
			//                        @Override
			//                        public void onResponse(String response) {
			//                            response = response.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
			//                            response = response.replace("<string xmlns=\"http://tempuri.org/\">", "");
			//                            response = response.replace("</string>", "");
			//
			//                            Log.d("response", response + "");
			//                            allCitylist = parseCityList(response);
			//                            if (Validate.notEmpty(allCitylist)) {
			//                                SharedPrefsUtil.setStringPreference(getApplicationContext(), TAG, response);
			//                                loadDefaultData();
			//                                hideProgressDialog();
			//                            } else {
			//                                hideProgressDialog();
			//                                finish();
			//                            }
			//                        }
			//                    },
			//                    new Response.ErrorListener() {
			//                        @Override
			//                        public void onErrorResponse(VolleyError error) {
			//                            hideProgressDialog();
			////					                                      Toast.makeText(getApplicationContext(), error + "", Toast.LENGTH_SHORT).show();
			//                            finish();
			//                        }
			//                    }
			//            );

			//            RequestQueue requestQueue = Volley.newRequestQueue(this);
			//            requestQueue.add(req);
		}
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	private void sendRequestWithoutProgress(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequestWithoutProgress(this, request, mResponseListener);
	}

	private ArrayList<CityListData> parseCityList(JSONObject jobj)
	{
		ArrayList<CityListData> cityListDatas = null;
		if (jobj != null)
		{
			try
			{
				JSONArray jArr = jobj.getJSONArray("data");
				if (Validate.notNull(jArr))
				{
					cityListDatas = new ArrayList<>();
					for (int i = 0; i < jArr.length(); i++)
					{
						CityListData cityListData = new CityListData();

						cityListData.setCITY_ID(jArr.getJSONObject(i).getInt(JSON_CITY_ID));
						cityListData.setCITY_NAME(jArr.getJSONObject(i).getString(JSON_CITY_NAME));
						cityListData.setDISPLAY_NAME(jArr.getJSONObject(i).getString(JSON_DISPLAY_NAME));
						cityListData.setSTATE_ID(jArr.getJSONObject(i).getInt(JSON_STATE_ID));
						cityListData.setFAVOURITE(jArr.getJSONObject(i).getString(JSON_FAVOURITE));
						cityListData.setALIASES(jArr.getJSONObject(i).getString(JSON_ALIASES));
						cityListDatas.add(cityListData);
					}
				}

			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}

		if (Validate.notEmpty(cityListDatas))
		{
			SharedPrefsUtil.setStringPreference(getApplicationContext(), TAG, jobj + "");
			hideProgressDialog();
		}
		else
		{
			hideProgressDialog();
			finish();
		}
		return cityListDatas;
	}

	private void loadDefaultData()
	{
		try
		{
			mSearchCityList.clear();
			for (CityListData cityListData : allCitylist)
			{
				if (cityListData.isFavourite())
				{
					mSearchCityList.add(cityListData);
				}
			}

			setData();
		}
		catch (Exception e)
		{
			Log.v("searchLocationActivity", "" + e);
		}
	}

	private void setData()
	{
		mSearchLocationView.setLocations(mSearchCityList);
	}

	private void selectedLocationData(CityListData cityListData)
	{
		Intent intent = new Intent();
		intent.putExtra(EXTRA_PARAM_DATA, cityListData);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	@Override
	public void onBackPressed()
	{

	}
}