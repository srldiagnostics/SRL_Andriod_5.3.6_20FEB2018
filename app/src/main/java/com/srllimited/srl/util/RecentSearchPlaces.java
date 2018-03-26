package com.srllimited.srl.util;

import com.srllimited.srl.data.RecentSearchData;

import android.content.Context;
import android.util.Log;

/**
 * Created by RuchiTiwari on 12/31/2016.
 */

public class RecentSearchPlaces
{
	private static final String KEY_RECENT_SEARCHED_PLACE1 = "searchedplace1";

	private static final String KEY_RECENT_SEARCHED_PLACE2 = "searchedplace2";

	private static final String KEY_RECENT_SEARCHED_PLACE3 = "searchedplace3";

	private static final String KEY_RECENT_SEARCHED_Lat1 = "lat1";

	private static final String KEY_RECENT_SEARCHED_Lat2 = "lat2";

	private static final String KEY_RECENT_SEARCHED_Lat3 = "lat3";

	private static final String KEY_RECENT_SEARCHED_Lon1 = "lon1";

	private static final String KEY_RECENT_SEARCHED_Lon2 = "lon2";

	private static final String KEY_RECENT_SEARCHED_Lon3 = "lon3";

	public static void add(Context context, String selectedcity, String lat, String lon)
	{

		RecentSearchData searchedplace1 = getPlace1(context);

		if (searchedplace1 != null && Validate.notEmpty(searchedplace1.getCity() + ""))
		{
			Log.e("already", "stored");
		}
		else
			searchedplace1 = null;

		if (Validate.isNull(searchedplace1))
		{
			SharedPrefsUtil.setStringPreference(context, KEY_RECENT_SEARCHED_PLACE1, selectedcity);
			SharedPrefsUtil.setStringPreference(context, KEY_RECENT_SEARCHED_Lat1, lat);
			SharedPrefsUtil.setStringPreference(context, KEY_RECENT_SEARCHED_Lon1, lon);

		}
		else
		{

			RecentSearchData searchedplace2 = getPlace2(context);

			if (searchedplace2 != null && Validate.notEmpty(searchedplace2.getCity() + ""))
			{
				Log.e("already", "stored");
			}
			else
				searchedplace2 = null;

			if (Validate.isNull(searchedplace2))
			{
				SharedPrefsUtil.setStringPreference(context, KEY_RECENT_SEARCHED_PLACE1, selectedcity + "");
				SharedPrefsUtil.setStringPreference(context, KEY_RECENT_SEARCHED_Lat1, lat);
				SharedPrefsUtil.setStringPreference(context, KEY_RECENT_SEARCHED_Lon1, lon);
				SharedPrefsUtil.setStringPreference(context, KEY_RECENT_SEARCHED_PLACE2, searchedplace1.getCity());
				SharedPrefsUtil.setStringPreference(context, KEY_RECENT_SEARCHED_Lat2, searchedplace1.getLat() + "");
				SharedPrefsUtil.setStringPreference(context, KEY_RECENT_SEARCHED_Lon2, searchedplace1.getLon() + "");
			}
			else
			{
				SharedPrefsUtil.setStringPreference(context, KEY_RECENT_SEARCHED_PLACE1, selectedcity + "");
				SharedPrefsUtil.setStringPreference(context, KEY_RECENT_SEARCHED_Lat1, lat);
				SharedPrefsUtil.setStringPreference(context, KEY_RECENT_SEARCHED_Lon1, lon);
				SharedPrefsUtil.setStringPreference(context, KEY_RECENT_SEARCHED_PLACE2, searchedplace1.getCity());
				SharedPrefsUtil.setStringPreference(context, KEY_RECENT_SEARCHED_Lat2, searchedplace1.getLat() + "");
				SharedPrefsUtil.setStringPreference(context, KEY_RECENT_SEARCHED_Lon2, searchedplace1.getLon() + "");
				SharedPrefsUtil.setStringPreference(context, KEY_RECENT_SEARCHED_PLACE3, searchedplace2.getCity());
				SharedPrefsUtil.setStringPreference(context, KEY_RECENT_SEARCHED_Lat3, searchedplace2.getLat() + "");
				SharedPrefsUtil.setStringPreference(context, KEY_RECENT_SEARCHED_Lon3, searchedplace2.getLon() + "");
			}
		}
	}

	public static RecentSearchData getPlace1(Context context)
	{
		RecentSearchData recentSearchData = new RecentSearchData();
		recentSearchData.setCity(
				StringUtil.getValidString(SharedPrefsUtil.getStringPreference(context, KEY_RECENT_SEARCHED_PLACE1)));
		try
		{
			recentSearchData.setLat(Double.valueOf(
					StringUtil.getValidString(SharedPrefsUtil.getStringPreference(context, KEY_RECENT_SEARCHED_Lat1))));
			recentSearchData.setLon(Double.valueOf(
					StringUtil.getValidString(SharedPrefsUtil.getStringPreference(context, KEY_RECENT_SEARCHED_Lon1))));
		}
		catch (Exception e)
		{

		}
		return recentSearchData;
	}

	public static RecentSearchData getPlace2(Context context)
	{
		RecentSearchData recentSearchData = new RecentSearchData();
		recentSearchData.setCity(
				StringUtil.getValidString(SharedPrefsUtil.getStringPreference(context, KEY_RECENT_SEARCHED_PLACE2)));
		try
		{
			recentSearchData.setLat(Double.valueOf(
					StringUtil.getValidString(SharedPrefsUtil.getStringPreference(context, KEY_RECENT_SEARCHED_Lat2))));
			recentSearchData.setLon(Double.valueOf(
					StringUtil.getValidString(SharedPrefsUtil.getStringPreference(context, KEY_RECENT_SEARCHED_Lon2))));
		}
		catch (Exception e)
		{

		}
		return recentSearchData;
	}

	public static RecentSearchData getPlace3(Context context)
	{
		RecentSearchData recentSearchData = new RecentSearchData();
		recentSearchData.setCity(
				StringUtil.getValidString(SharedPrefsUtil.getStringPreference(context, KEY_RECENT_SEARCHED_PLACE3)));
		try
		{
			recentSearchData.setLat(Double.valueOf(
					StringUtil.getValidString(SharedPrefsUtil.getStringPreference(context, KEY_RECENT_SEARCHED_Lat3))));
			recentSearchData.setLon(Double.valueOf(
					StringUtil.getValidString(SharedPrefsUtil.getStringPreference(context, KEY_RECENT_SEARCHED_Lon3))));
		}
		catch (Exception e)
		{

		}
		return recentSearchData;
	}

	public static boolean isEmptyPlace1(Context context)
	{
		return !Validate.notEmpty(SharedPrefsUtil.getStringPreference(context, KEY_RECENT_SEARCHED_PLACE1));
	}

	public static boolean isEmptyPlace2(Context context)
	{
		return !Validate.notEmpty(SharedPrefsUtil.getStringPreference(context, KEY_RECENT_SEARCHED_PLACE2));
	}

	public static boolean isEmptyPlace3(Context context)
	{
		return !Validate.notEmpty(SharedPrefsUtil.getStringPreference(context, KEY_RECENT_SEARCHED_PLACE3));
	}

	public static boolean isAllEmpty(Context context)
	{
		return isEmptyPlace1(context) && isEmptyPlace2(context) && isEmptyPlace3(context);

	}

}
