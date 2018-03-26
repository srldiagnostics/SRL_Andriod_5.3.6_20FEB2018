package com.srllimited.srl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.srllimited.srl.adapters.LabLocatorAdapter;
import com.srllimited.srl.data.AddressData;
import com.srllimited.srl.data.LabLocatorsData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.location.GetAddress;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.LocationHandler;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.StringUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class YourLocationActivity extends MenuBaseActivity
		implements LocationListener, View.OnClickListener, LabLocatorAdapter.NotifyActivity
{

	public static final int LAB_CODE_RESULT = 1;
	public static Activity yourloc;
	Marker currLocationMarker;
	RelativeLayout lab_layout;
	TextView yourPresentLocationTVID, yoir_cur_loc;
	Context context;
	boolean isSelectedCity = false;
	RecyclerView.Adapter mAdapter;
	TextView myreportsTVID;
	LocationHandler locationHandler = null;
	android.app.AlertDialog alert;
	ServerResponseData serverResponseData = null;
	private GoogleMap mMap;
	private List<LabLocatorsData> mLabLocatorsDatas = new ArrayList<>();
	private RecyclerView mRecyclerView;
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData dserverResponseData)
		{
			switch (request.getReferralCode())
			{
				case LAB_LOCATIONS:
				{
					serverResponseData = dserverResponseData;
					new AsyncTaskRunner().execute();
				}
					break;
			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{

		}
	};

	//	@Override
	//	protected void onStart()
	//	{
	//		super.onStart();
	//		GPSTracker gpsTracker = new GPSTracker(YourLocationActivity.this);
	//		Boolean gpsEnabled = gpsTracker.canGetLocation();
	//		if (gpsTracker.canGetLocation())
	//		{
	//
	//			AddressData fetchAddress = GetAddress.getAddress(context, Constants.latitude, Constants.longitude);
	//			if (fetchAddress != null)
	//			{
	//				latLng = new LatLng(Constants.latitude, Constants.longitude);
	//				mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
	//				mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
	//				CameraPosition cameraPosition = new CameraPosition.Builder()
	//						.target(latLng).zoom(14).build();
	//				mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	//
	//				MarkerOptions markerOptions = new MarkerOptions();
	//				markerOptions.position(latLng);
	//				markerOptions.title("Current Position");
	//				markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
	//				currLocationMarker = mMap.addMarker(markerOptions);
	//			}
	//		}
	//		else
	//		{
	//			showSettingsAlert();
	//		}
	//	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.your_location_activity);
		context = YourLocationActivity.this;
		yourloc = this;
		myreportsTVID = (TextView) findViewById(R.id.myreportsTVID);
		yourPresentLocationTVID = (TextView) findViewById(R.id.yourPresentLocationTVID);
		yoir_cur_loc = (TextView) findViewById(R.id.yourCurrentLocationTVID);
		lab_layout = (RelativeLayout) findViewById(R.id.lab_layout);

		header_loc_name.setText("SRL Locator");
		header_loc_name.setEnabled(false);

		initMap();

		lab_layout.setOnClickListener(this);

		mRecyclerView = (RecyclerView) findViewById(R.id.loc_receyclerview);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		mRecyclerView.setHasFixedSize(true);

		mAdapter = new LabLocatorAdapter(context, mLabLocatorsDatas, false);
		mRecyclerView.setAdapter(mAdapter);
		checkLocationStatus();
	}

	private void initMap()
	{
		if (mMap == null)
		{
			SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map);
			supportMapFragment.getMapAsync(new OnMapReadyCallback()
			{
				@Override
				public void onMapReady(GoogleMap googleMap)
				{
					mMap = googleMap;

					if (mMap != null)
					{
						checkLocationStatus();
					}
				}
			});

			try
			{
				mMap.setMyLocationEnabled(true);
			}
			catch (Exception e)
			{

			}
		}
	}

	private void setCurrentLocation()
	{
		locationHandler = new LocationHandler(this, new LocationHandler.onLocationResponseListener()
		{
			@Override
			public void onResponse(final boolean success)
			{
				if (success)
				{
					yoir_cur_loc.setText("Your Current Location");
					isSelectedCity = false;
					final Location location = LocationHandler.getLocation();
					if (Validate.notNull(location))
					{
						LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
						getLabLocations("", latLng);
					}
				}
			}
		});
		locationHandler.checkWithLastLocation();

		if (locationHandler != null && !locationHandler.hasLocation())
		{
			yourPresentLocationTVID.setText("Tap Here");
			yoir_cur_loc.setText("Your Current Location Is Unknown");
			//			isSelectedCity = true;
			//			LatLng latLng = new LatLng(0, 0);
			//			getLabLocations("", SharedPrefsUtil.getStringPreference(context, "selectedcity") + "", latLng);
		}
	}

	@Override
	public void onLocationChanged(Location location)
	{

	}

	@Override
	public void onStatusChanged(String s, int i, Bundle bundle)
	{

	}

	@Override
	public void onProviderEnabled(String s)
	{

	}

	@Override
	public void onProviderDisabled(String s)
	{

	}

	@Override
	public void onClick(final View view)
	{
		switch (view.getId())
		{
			case R.id.lab_layout:
				Util.killLabLocators();
				Intent intent = new Intent(YourLocationActivity.this, LabLocatorsActivity.class);
				startActivityForResult(intent, LAB_CODE_RESULT);
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == LAB_CODE_RESULT)
		{
			if (Validate.notNull(data))
			{
				LatLng selectedAddress = data.getParcelableExtra("latlng");
				getLabLocations("", selectedAddress);

			}
			return;
		}
	}

	private void setLocText(AddressData fetchAddress)
	{

		String cityAndState = "";

		if (fetchAddress != null)
		{
			if (Validate.notEmpty(fetchAddress.getKnownName()) && Validate.notEmpty(fetchAddress.getCity()))
			{
				cityAndState = fetchAddress.getKnownName() + "," + fetchAddress.getCity();
			}
			else if (Validate.notEmpty(fetchAddress.getCity()) && Validate.notEmpty(fetchAddress.getCity()))
			{
				cityAndState = fetchAddress.getCity() + "," + fetchAddress.getCity();
			}
			else if (Validate.notEmpty(fetchAddress.getKnownName()))
			{
				cityAndState = fetchAddress.getKnownName();
			}
			else if (Validate.notEmpty(fetchAddress.getCity()))
			{
				cityAndState = fetchAddress.getCity();
			}

		}

		if (Validate.notEmpty(cityAndState))
		{
			cityAndState = cityAndState.replace(",", ", ");
			yourPresentLocationTVID.setText(cityAndState);
			yoir_cur_loc.setText("Your Current Location");
		}

	}

	private void getLabLocations(String cityAndState, String selectedAddress)
	{
		getLabLocations(cityAndState, GetAddress.getLocationFromAddress(context, selectedAddress));
	}

	private void getLabLocations(String cityAndState, LatLng latlng)
	{
		mLabLocatorsDatas.clear();
		mAdapter.notifyDataSetChanged();
		//myreportsTVID.setText("Found " + 0 + " Lab Locations from your current location");
		myreportsTVID.setText("No lab location found.");

		if (Validate.notNull(latlng))
		{
			AddressData address = GetAddress.getAddress(context, latlng.latitude, latlng.longitude);
			if (!Validate.notEmpty(cityAndState))
			{
				cityAndState = GetAddress.fetchCityAndState(address);
			}

			String selCity = address.getCity();
			if (selCity == null || selCity.isEmpty())
			{
				selCity = address.getKnownName();
			}

			getLabLocations(cityAndState, selCity, latlng);

		}
		cityAndState = cityAndState.replace(",", ", ");
		yourPresentLocationTVID.setText(StringUtil.getValidString(cityAndState));
		yoir_cur_loc.setText("Your Current Location");
	}

	private void getLabLocations(String cityAndState, String city, LatLng latlng)
	{
		setMap(latlng, cityAndState);

		sendRequest(ApiRequestHelper.getLabLocations(context, StringUtil.getValidString(city), "", "", "1", "5",
				latlng.longitude + "", latlng.latitude + ""));
	}

	private void setMap(LatLng latln, String title)
	{
		try
		{
			if (latln != null)
			{
				initMap();

				if (mMap != null)
				{
					mMap.clear();
				}

				mMap.moveCamera(CameraUpdateFactory.newLatLng(latln));
				mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
				CameraPosition cameraPosition = new CameraPosition.Builder().target(latln).zoom(17).build();
				mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

				MarkerOptions markerOptions = new MarkerOptions();
				markerOptions.position(latln);
				markerOptions.title(title);
				//markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
				markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon));
				mMap.addMarker(markerOptions);
			}
		}
		catch (Exception e)
		{

		}

	}

	private void fetchLocation()
	{
		boolean isLocationData = false;
		try
		{
			double lat = getStoredLat();
			double lon = getStoredLon();

			if (lat != 0 && lon != 0)
			{
				LatLng latLng = new LatLng(lat, lon);
				getLabLocations("", latLng);
			}
			else
			{
				isLocationData = false;
				LatLng latLng = new LatLng(0, 0);
				getLabLocations("", SharedPrefsUtil.getStringPreference(context, "city"), latLng);
			}
		}
		catch (Exception e)
		{

		}
	}

	private double getStoredLat()
	{
		double lat = 0;

		try
		{
			lat = Double.valueOf(SharedPrefsUtil.getStringPreference(context, "latitude"));
		}
		catch (Exception e)
		{
			lat = 0;
		}

		return lat;
	}

	private double getStoredLon()
	{

		double lon = 0;
		try
		{
			lon = Double.valueOf(SharedPrefsUtil.getStringPreference(context, "longitude"));
		}
		catch (Exception e)
		{
			lon = 0;
		}
		return lon;
	}

	private void checkLocationStatus()
	{

		if (getStoredLat() != 0 && getStoredLon() != 0)
		{
			fetchLocation();
		}
		else
		{
			setCurrentLocation();
		}
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	private void setLocations(ServerResponseData serverResponseData)
	{
		JSONArray jArray = serverResponseData.getArrayData();

		if (Validate.notNull(jArray))
		{
			mLabLocatorsDatas.clear();

			for (int i = 0; i < jArray.length(); i++)
			{
				try
				{
					LabLocatorsData labLocatorsData = LabLocatorsData.getGet(jArray.getJSONObject(i));
					mLabLocatorsDatas.add(labLocatorsData);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}

			//            Collections.sort(mLabLocatorsDatas, new Comparator<LabLocatorsData>() {
			//                @Override
			//                public int compare(LabLocatorsData o1, LabLocatorsData o2) {
			//
			//                    final double d1 = o1.getDISTANCE();
			//                    final double d2 = o2.getDISTANCE();
			//                    return (d1 < d2) ? -1 : (d1 < d2) ? 1 : 0;
			//
			//                }
			//            });

		}
	}

	private void setFoundLocationCount()
	{

		if (mLabLocatorsDatas != null && mLabLocatorsDatas.size() > 0)
			myreportsTVID.setText("Found " + mLabLocatorsDatas.size() + " Lab locations in "
					+ mLabLocatorsDatas.get(0).getCITY().toLowerCase());
		else
			myreportsTVID.setText("No lab location found.");

	}

	private void showSelecyedCityOnMap()
	{

		if (yourPresentLocationTVID.getText().toString().isEmpty())
		{
			if (mLabLocatorsDatas != null && mLabLocatorsDatas.size() > 0)
			{
				if (mLabLocatorsDatas.get(0).getCITY() != null
						&& !mLabLocatorsDatas.get(0).getCITY().equalsIgnoreCase("null"))
					yourPresentLocationTVID.setText(Util.toTitleCase(
							mLabLocatorsDatas.get(0).getCITY() + ", " + mLabLocatorsDatas.get(0).getSTATE()));
				if (mLabLocatorsDatas.get(0).getSTATE() == null
						|| mLabLocatorsDatas.get(0).getSTATE().equalsIgnoreCase("null"))
				{
					if (mLabLocatorsDatas.get(0).getCITY() != null
							&& !mLabLocatorsDatas.get(0).getCITY().equalsIgnoreCase("null"))
						yourPresentLocationTVID.setText(Util.toTitleCase(mLabLocatorsDatas.get(0).getCITY() + ""));
				}

				yoir_cur_loc.setText("Your Current Location");
			}
		}

		if (isSelectedCity)
		{
			if (mLabLocatorsDatas != null && mLabLocatorsDatas.size() > 0)
			{
				double lat = mLabLocatorsDatas.get(0).getLATITUDE();
				double lon = mLabLocatorsDatas.get(0).getLONGITUDE();

				if (yourPresentLocationTVID.getText().toString().isEmpty())
				{
					if (mLabLocatorsDatas.get(0).getCITY() != null
							&& !mLabLocatorsDatas.get(0).getCITY().equalsIgnoreCase("null"))
						yourPresentLocationTVID.setText(
								mLabLocatorsDatas.get(0).getCITY() + " ," + mLabLocatorsDatas.get(0).getSTATE());
					if (mLabLocatorsDatas.get(0).getSTATE() == null
							|| mLabLocatorsDatas.get(0).getSTATE().equalsIgnoreCase("null"))
					{
						if (mLabLocatorsDatas.get(0).getCITY() != null
								&& !mLabLocatorsDatas.get(0).getCITY().equalsIgnoreCase("null"))
							yourPresentLocationTVID.setText(mLabLocatorsDatas.get(0).getCITY() + "");
					}
					yoir_cur_loc.setText("Your Current Location");

				}

				LatLng latLng = new LatLng(lat, lon);
				String citynstate = "";
				if (Validate.notEmpty(mLabLocatorsDatas.get(0).getCITY())
						&& Validate.notEmpty(mLabLocatorsDatas.get(0).getSTATE()))
				{
					citynstate = mLabLocatorsDatas.get(0).getCITY() + "," + mLabLocatorsDatas.get(0).getSTATE();
				}
				else
				{
					citynstate = SharedPrefsUtil.getStringPreference(context, "selectedcity");
				}

				setMap(latLng, citynstate);
			}
		}
	}

	@Override
	public void onChangingListItem(LatLng latLng, String city)
	{

		setMap(latLng, city);
	}

	@Override
	protected void onResume()
	{
		super.onResume();

	}

	//    private class AsyncTaskCity extends AsyncTask<String, String, String> {
	//
	//        private String resp;
	//
	//        @Override
	//        protected String doInBackground(String... params) {
	//            publishProgress("Fetching City...");
	//            try {
	//
	//                JSONObject jsonObj = parser_Json.getJSONfromURL("http://maps.googleapis.com/maps/api/geocode/json?latlng=" + Global.curLatitude + ","
	//                        + Global.curLongitude + "&sensor=true");
	//                String Status = jsonObj.getString("status");
	//                if (Status.equalsIgnoreCase("OK")) {
	//                    JSONArray Results = jsonObj.getJSONArray("results");
	//                    JSONObject zero = Results.getJSONObject(0);
	//                    JSONArray address_components = zero.getJSONArray("address_components");
	//
	//                    for (int i = 0; i < address_components.length(); i++) {
	//                        JSONObject zero2 = address_components.getJSONObject(i);
	//                        String long_name = zero2.getString("long_name");
	//                        JSONArray mtypes = zero2.getJSONArray("types");
	//                        String Type = mtypes.getString(0);
	//
	//                        if (TextUtils.isEmpty(long_name) == false || !long_name.equals(null) || long_name.length() > 0 || long_name != "") {
	//                          if (Type.equalsIgnoreCase("locality")) {
	//
	//                                City = long_name;
	//                            }
	//                        }
	//
	//
	//                    }
	//                }
	//
	//            } catch (Exception e) {
	//                e.printStackTrace();
	//            }
	//            return resp;
	//        }
	//
	//        @Override
	//        protected void onPostExecute(String result) {
	//
	//        }
	//
	//
	//        @Override
	//        protected void onPreExecute() {
	//            RestApiCallUtil.hideProgressDialog();
	//            RestApiCallUtil.showProgressDialog(context);
	//        }
	//
	//        @Override
	//        protected void onProgressUpdate(String... text) {
	//
	//        }
	//    }

	private class AsyncTaskRunner extends AsyncTask<String, String, String>
	{

		private String resp;

		@Override
		protected String doInBackground(String... params)
		{
			publishProgress("Fetching Nearby Labs...");
			setLocations(serverResponseData);

			return resp;
		}

		@Override
		protected void onPostExecute(String result)
		{
			RestApiCallUtil.hideProgressDialog();
			mAdapter.notifyDataSetChanged();
			setFoundLocationCount();
			showSelecyedCityOnMap();
		}

		@Override
		protected void onPreExecute()
		{
			RestApiCallUtil.hideProgressDialog();
			RestApiCallUtil.showProgressDialog(context);
		}

		@Override
		protected void onProgressUpdate(String... text)
		{

		}
	}

}
