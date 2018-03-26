package com.srllimited.srl.location;

import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.model.LatLng;
import com.srllimited.srl.GPSTracker;
import com.srllimited.srl.data.AddressData;
import com.srllimited.srl.permission.DangerousPermResponseCallBack;
import com.srllimited.srl.permission.DangerousPermissionResponse;
import com.srllimited.srl.permission.DangerousPermissionUtils;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Validate;

import android.Manifest;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

/**
 * Created by RuchiTiwari on 12/26/2016.
 */

public class GetAddress
{

	public static String getCurrentLocation = "";
	private static int requestCode = 102;

	public static AddressData getAddress(Context context, double latitude, double longitude)
	{
		Geocoder geocoder;
		List<Address> addresses;
		geocoder = new Geocoder(context, Locale.getDefault());
		AddressData addressData = new AddressData();
		if (RestApiCallUtil.isOnline(context))
		{
			try
			{
				addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

				addressData.setCompleteAddress(addresses.get(0).toString());
				addressData.setAddress(addresses.get(0).getAddressLine(0)); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
				addressData.setCity(addresses.get(0).getLocality());
				addressData.setState(addresses.get(0).getAdminArea());
				addressData.setCountry(addresses.get(0).getCountryName());
				addressData.setPostalCode(addresses.get(0).getPostalCode());
				addressData.setKnownName(addresses.get(0).getSubLocality());

			}
			catch (Exception e)
			{

			}
		}
		return addressData;
	}

	public static LatLng getLocationFromAddress(Context context, String strAddress)
	{
		LatLng latLng = null;
		if (RestApiCallUtil.isOnline(context))
		{
			try
			{

				Geocoder coder = new Geocoder(context);
				List<Address> address = coder.getFromLocationName(strAddress, 5);
				if (address == null)
				{
					return null;
				}
				Address location = address.get(0);
				location.getLatitude();
				location.getLongitude();

				latLng = new LatLng(location.getLatitude(), location.getLongitude());

			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}

		return latLng;
	}

	public static AddressData fetchCityAndState(Context context, LatLng latLng)
	{
		if (RestApiCallUtil.isOnline(context))
		{
			AddressData fetchAddress = getAddress(context, latLng.latitude, latLng.longitude);
			return fetchAddress;
			//			return fetchCityAndState(fetchAddress);
		}
		return null;
	}

	public static String fetchCityAndState(AddressData fetchAddress)
	{
		String cityAndState = "";
		try
		{
			if (fetchAddress != null)
			{
				if (Validate.notEmpty(fetchAddress.getKnownName()) && Validate.notEmpty(fetchAddress.getCity()))
				{
					cityAndState = fetchAddress.getKnownName() + ", " + fetchAddress.getCity();
				}
				else if (Validate.notEmpty(fetchAddress.getCity()) && Validate.notEmpty(fetchAddress.getCity()))
				{
					cityAndState = fetchAddress.getCity() + ", " + fetchAddress.getCity();
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
		}
		catch (Exception e)
		{

		}

		return cityAndState;
	}

	public static String getLocation(final Context context)
	{
		getCurrentLocation = "";
		DangerousPermissionUtils
				.getPermission(context,
						new String[] { Manifest.permission.ACCESS_FINE_LOCATION,
								Manifest.permission.ACCESS_COARSE_LOCATION },
						requestCode)
				.enqueue(new DangerousPermResponseCallBack()
				{
					@Override
					public void onComplete(final DangerousPermissionResponse permissionResponse)
					{
						if (permissionResponse.isGranted())
						{
							GPSTracker gpsTracker = new GPSTracker(context);
							Boolean gpsEnabled = gpsTracker.canGetLocation();
							if (gpsEnabled)
							{
								Location location = gpsTracker.getLocation();

								if (location != null)
								{
									Log.e(location.getLatitude() + "", location.getLongitude() + "");
									SharedPrefsUtil.setStringPreference(context, "latitude",
											location.getLatitude() + "");
									SharedPrefsUtil.setStringPreference(context, "longitude",
											location.getLongitude() + "");
									AddressData addressData = GetAddress.getAddress(context, location.getLatitude(),
											location.getLongitude());
									if (addressData != null)
									{

										String currentLocation = GetAddress.fetchCityAndState(addressData);
										//								               address.setText(currentLocation);
										getCurrentLocation = currentLocation;
										SharedPrefsUtil.setStringPreference(context, "citynstate",
												currentLocation + "");

									}
								}
							}
						}
					}
				});

		if (!Validate.notEmpty(getCurrentLocation))
		{
			getCurrentLocation = "";
		}

		return getCurrentLocation;
	}

}
