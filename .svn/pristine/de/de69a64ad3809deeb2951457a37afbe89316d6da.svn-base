package com.srllimited.srl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.model.DescriptionTerm;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;
import com.seatgeek.placesautocomplete.model.PlaceLocation;
import com.srllimited.srl.data.RecentSearchData;
import com.srllimited.srl.location.GetAddress;
import com.srllimited.srl.location.findplaces.FindPlaces;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.LocationHandler;
import com.srllimited.srl.util.RecentSearchPlaces;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.utilities.SharedPreference;
import com.srllimited.srl.widget.UISearchBar;

/**
 * Created by Ruchi Tiwari
 */

public class LabLocatorsActivity extends MenuBaseActivity implements View.OnClickListener
{
	private static final String LOG_TAG = "ExampleApp";
	private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";
	//------------ make your specific key ------------
	private static final String API_KEY = "AIzaSyA1TSQsui2xYu6hFUk33pE5QyX5vq_fLb4";
	public static Activity lablocator;
	String addressToSend = "";
	String cityToSend = "";
	double lat = 0;
	double lon = 0;
	GoogleMap mGoogleMap;

	//	LatLng latLng;
	Marker currLocationMarker;

	LinearLayout autoDetectLLID, mapViewLLID;

	ScrollView mRecentSearchSVID;

	Button doneBTNID;
	//	PlaceAutocompleteFragment autocompleteFragment;
	int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
	SharedPreference shared_preference;
	Context context;
	View recent_searches_layout, v_place_layout1, v_place_layout2, v_place_layout3;
	TextView tv_place1, tv_place2, tv_place3;
	LatLng latlng = null;
	LocationHandler locationHandler;
	AlertDialog alert;
	private UISearchBar mPlacesSearchBar;
	private ListView mSearchedPlacesListView;
	private FindPlaces mFindPlaces;
	private UISearchBar.OnSearchListener mSearchListener = new UISearchBar.OnSearchListener()
	{
		@Override
		public void onClearSearch()
		{
			mFindPlaces.filter("");
			hideSearchedPlacesView();
		}

		@Override
		public void onSearchText(final String text)
		{
			mFindPlaces.filter(text);

			if (!Validate.notEmpty(text) || mFindPlaces.getAdapter().isEmpty())
			{
				hideSearchedPlacesView();
			}

			else
			{
				showSearchedPlacesView();
			}
		}
	};
	private DetailsCallback mPlaceDetailsCallback = new DetailsCallback()
	{
		@Override
		public void onSuccess(final PlaceDetails details)
		{

			PlaceLocation placeLocation = details.geometry.location;
			//			Toast.makeText(getApplicationContext(), placeLocation.lat+", "+ placeLocation.lng, Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onFailure(final Throwable failure)
		{

		}
	};
	private ListView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener()
	{
		@Override
		public void onItemClick(final AdapterView<?> adapterView, final View view, final int i, final long l)
		{
			hideSearchedPlacesView();
			Util.hideSoftKeyboard(context, view);

			new Thread()
			{
				public void run()
				{

					try
					{
						runOnUiThread(new Runnable()
						{

							@Override
							public void run()
							{
								final Place place = mFindPlaces.getAdapter().getItem(i);
								addressToSend = place.description;
								mFindPlaces.getDetailsFor(place, mPlaceDetailsCallback);

								mPlacesSearchBar.clear();
								Util.hideSoftKeyboard(context, view);

								mFindPlaces.getDetailsFor(place, mPlaceDetailsCallback);

								mPlacesSearchBar.clear();

								String header = "";

								if (Validate.notEmpty(place.description))
								{
									int commaindex = place.description.indexOf(",");
									try
									{
										if (commaindex > 0)
										{
											header = place.description.substring(0, commaindex).trim();
										}
										else
										{
											if (Validate.notEmpty(place.terms))
											{
												final int size = place.terms.size();
												for (int j = 0; j < size; j++)
												{
													DescriptionTerm term = place.terms.get(i);
													if (Validate.notNull(term) && Validate.notEmpty(term.value.trim()))

													{
														if (!Validate.notEmpty(header))
														{
															header = term.value.trim();
														}

													}
												}
											}
										}
									}
									catch (Exception e)
									{

									}
								}

								mPlacesSearchBar.getEditText().setHint(header);
								//			mPlacesSearchBar.getEditText().setTextColor(getResources().getColor(R.color.bg_color_book_now));
								trackLocation(place.description);

								try
								{
									cityToSend = header;
									LatLng location = new LatLng(0, 0);
									if (RestApiCallUtil.isOnline(context))
									{
										location = GetAddress.getLocationFromAddress(context, place.description);
									}
									if (cityToSend != null && !cityToSend.equalsIgnoreCase("null"))
										if (Validate.notEmpty(cityToSend))
										{

											RecentSearchPlaces.add(context, cityToSend, location.latitude + "",
													location.longitude + "");
											showRecentSearces();
										}
								}
								catch (Exception e)
								{

								}

							}
						});
					}
					catch (Exception e)
					{
						e.printStackTrace();

					}
				}
			}.start();

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.lab_locator_activity);

		context = LabLocatorsActivity.this;
		header_loc_name.setText("Lab Locator");
		header_loc_name.setEnabled(false);
		lablocator = this;
		mRecentSearchSVID = (ScrollView) findViewById(R.id.recentsearchSVID);
		doneBTNID = (Button) findViewById(R.id.doneBTNID);
		autoDetectLLID = (LinearLayout) findViewById(R.id.autoDetectLLID);
		mapViewLLID = (LinearLayout) findViewById(R.id.mapViewLLID);
		recent_searches_layout = findViewById(R.id.recent_searches_layout);
		v_place_layout1 = findViewById(R.id.search1);
		v_place_layout2 = findViewById(R.id.search2);
		v_place_layout3 = findViewById(R.id.search3);

		tv_place1 = (TextView) findViewById(R.id.place1);
		tv_place2 = (TextView) findViewById(R.id.place2);
		tv_place3 = (TextView) findViewById(R.id.place3);

		v_place_layout1.setOnClickListener(this);
		v_place_layout2.setOnClickListener(this);
		v_place_layout3.setOnClickListener(this);

		showRecentSearces();

		autoDetectLLID.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				mapViewLLID.setVisibility(View.VISIBLE);
				doneBTNID.setVisibility(View.VISIBLE);

				locationHandler = new LocationHandler(LabLocatorsActivity.this,
						new LocationHandler.onLocationResponseListener()
						{
							@Override
							public void onResponse(final boolean success)
							{
								if (success)
								{
									final Location location = LocationHandler.getLocation();
									LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
									if (latLng != null && latLng.latitude != 0 && latLng.longitude != 0)
									{
										setMap(latLng);
									}
								}
							}
						});
				locationHandler.checkWithLastLocation();

				if (locationHandler != null && !locationHandler.hasLocation())
				{
					if (!LocationHandler.canGetLocation(context))
					{
						showSettingsAlert();
					}
				}

			}
		});

		doneBTNID.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent();
				intent.putExtra("latlng", latlng);

				setResult(YourLocationActivity.LAB_CODE_RESULT, intent);
				finish();
			}
		});

		mFindPlaces = new FindPlaces(getApplicationContext());

		mPlacesSearchBar = (UISearchBar) findViewById(R.id.place_search_view);
		mPlacesSearchBar.getEditText().setHint("Search Places");
		mPlacesSearchBar.setOnSearchListener(mSearchListener);

		mSearchedPlacesListView = (ListView) findViewById(R.id.searched_places_list_view);
		mSearchedPlacesListView.setAdapter(mFindPlaces.getAdapter());
		mSearchedPlacesListView.setOnItemClickListener(mItemClickListener);
		mSearchedPlacesListView.setVisibility(View.GONE);

		doneBTNID.setVisibility(View.VISIBLE);
		mRecentSearchSVID.setVisibility(View.VISIBLE);

		SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		fragment.getMapAsync(new OnMapReadyCallback()
		{
			@Override
			public void onMapReady(GoogleMap googleMap)
			{
				mGoogleMap = googleMap;
			}
		});

	}

	private void showRecentSearces()
	{
		if (RecentSearchPlaces.isAllEmpty(context))
		{
			recent_searches_layout.setVisibility(View.GONE);
		}
		else
		{
			recent_searches_layout.setVisibility(View.VISIBLE);

			v_place_layout1.setVisibility(View.VISIBLE);
			v_place_layout2.setVisibility(View.VISIBLE);
			v_place_layout3.setVisibility(View.VISIBLE);

			if (RecentSearchPlaces.isEmptyPlace1(context))
			{
				v_place_layout1.setVisibility(View.GONE);
			}
			if (RecentSearchPlaces.isEmptyPlace2(context))
			{
				v_place_layout2.setVisibility(View.GONE);
			}
			if (RecentSearchPlaces.isEmptyPlace3(context))
			{
				v_place_layout3.setVisibility(View.GONE);
			}

			RecentSearchData recceRecentSearchData1 = new RecentSearchData();
			RecentSearchData recceRecentSearchData2 = new RecentSearchData();
			RecentSearchData recceRecentSearchData3 = new RecentSearchData();

			recceRecentSearchData1 = RecentSearchPlaces.getPlace1(context);
			recceRecentSearchData2 = RecentSearchPlaces.getPlace2(context);
			recceRecentSearchData3 = RecentSearchPlaces.getPlace3(context);

			if (recceRecentSearchData1 != null)
			{
				tv_place1.setText(recceRecentSearchData1.getCity());
			}
			if (recceRecentSearchData2 != null)
			{
				tv_place2.setText(recceRecentSearchData2.getCity());
			}
			if (recceRecentSearchData3 != null)
			{
				tv_place3.setText(recceRecentSearchData3.getCity());
			}
		}
	}

	private void showSearchedPlacesView()
	{
		if (mSearchedPlacesListView.getVisibility() != View.VISIBLE)
		{
			doneBTNID.setVisibility(View.GONE);
			mRecentSearchSVID.setVisibility(View.GONE);
			mSearchedPlacesListView.setVisibility(View.VISIBLE);
		}
	}

	//	@Override
	//	protected void onStart()
	//	{
	//		super.onStart();
	//		AddressData address = GetAddress.getAddress(context, Constants.latitude, Constants.longitude);
	//		trackLocation(address.getAddress());
	//	}

	private void hideSearchedPlacesView()
	{
		if (mSearchedPlacesListView.getVisibility() == View.VISIBLE)
		{
			mSearchedPlacesListView.setVisibility(View.GONE);
			mRecentSearchSVID.setVisibility(View.VISIBLE);
			doneBTNID.setVisibility(View.VISIBLE);
		}
	}

	protected void trackLocation(String address)
	{
		if (RestApiCallUtil.isOnline(context))
		{
			GPSTracker gpsTracker = new GPSTracker(LabLocatorsActivity.this);
			Boolean gpsEnabled = gpsTracker.canGetLocation();
			if (gpsTracker.canGetLocation())
			{
				mapViewLLID.setVisibility(View.VISIBLE);
				doneBTNID.setVisibility(View.VISIBLE);
				LatLng location = GetAddress.getLocationFromAddress(context, address);
				setMap(location);
			}
			else
			{
				showSettingsAlert();
			}
		}
		else
		{
			RestApiCallUtil.NetworkError(context);
		}
	}

	public void showSettingsAlert()
	{

		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(LabLocatorsActivity.this,
				AlertDialog.THEME_HOLO_LIGHT);
		builder.setMessage("Enable GPS?").setCancelable(false)
				.setPositiveButton("Ok", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						LabLocatorsActivity.this.startActivity(intent);
						dialog.cancel();
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.cancel();
					}
				});
		alert = builder.create();
		alert.show();
	}

	private void setMap(LatLng latLng)
	{
		mapViewLLID.setVisibility(View.VISIBLE);
		doneBTNID.setVisibility(View.VISIBLE);

		latlng = latLng;
		if (Validate.notNull(latLng))
		{
			mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
			mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(16));
			CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(16).build();
			mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
			MarkerOptions markerOptions = new MarkerOptions();
			markerOptions.position(latLng);
			markerOptions.title("Current Position");
			//	markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
			markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.markericon));
			currLocationMarker = mGoogleMap.addMarker(markerOptions);
		}
	}

	@Override
	public void onClick(final View view)
	{
		switch (view.getId())
		{
			case R.id.search1:
				RecentSearchData searchData1 = RecentSearchPlaces.getPlace1(context);
				LatLng latLng = new LatLng(searchData1.getLat(), searchData1.getLon());
				setMap(latLng);
				break;
			case R.id.search2:
				RecentSearchData searchData2 = RecentSearchPlaces.getPlace2(context);
				LatLng latLng2 = new LatLng(searchData2.getLat(), searchData2.getLon());
				setMap(latLng2);
				break;
			case R.id.search3:
				RecentSearchData searchData3 = RecentSearchPlaces.getPlace3(context);
				LatLng latLng3 = new LatLng(searchData3.getLat(), searchData3.getLon());
				setMap(latLng3);
				break;
		}
	}

	private void runThread()
	{
		runOnUiThread(new Thread(new Runnable()
		{
			public void run()
			{

				try
				{

				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}

		}));
	}

}
