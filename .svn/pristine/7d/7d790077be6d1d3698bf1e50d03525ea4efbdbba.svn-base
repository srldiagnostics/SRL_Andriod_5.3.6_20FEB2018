package com.srllimited.srl.constants;

/**
 * Created by RuchiTiwari on 1/16/2017.
 */

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class GoogleAutoCompleteParser implements ClearableAutoTextView.AutoCompleteResponseParser
{

	public static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
	public static final String GOOGLE_GEOCODER = "http://maps.googleapis.com/maps/api/geocode/json?latlng=";
	private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
	private static final String OUT_JSON = "/json";

	public static String getGooglePlacesUrl(String apiKey)
	{
		StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
		sb.append("?sensor=false&key=" + apiKey);
		return sb.toString();
	}

	@Override
	public ArrayList<ClearableAutoTextView.DisplayStringInterface> parseAutoCompleteResponse(String jsonResults)
	{
		ArrayList<ClearableAutoTextView.DisplayStringInterface> dsi = null;
		try
		{
			JSONObject jsonObj = new JSONObject(jsonResults.toString());
			final JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

			dsi = new ArrayList<ClearableAutoTextView.DisplayStringInterface>();
			for (int i = 0; i < predsJsonArray.length(); i++)
			{
				final int j = i;
				ClearableAutoTextView.DisplayStringInterface ds = new ClearableAutoTextView.DisplayStringInterface()
				{

					@Override
					public String getDisplayString()
					{

						try
						{
							return predsJsonArray.getJSONObject(j).getString("description").toString();
						}
						catch (JSONException e)
						{
							e.printStackTrace();
						}
						return null;
					}
				};
				dsi.add(ds);
			}
		}
		catch (JSONException e)
		{
			Log.e("AppUtil", "Cannot process JSON results", e);
		}

		return dsi;
	}

	public class ResultList implements ClearableAutoTextView.DisplayStringInterface
	{

		String str;

		public String getStr()
		{
			return str;
		}

		public void setStr(String str)
		{
			this.str = str;
		}

		@Override
		public String getDisplayString()
		{
			return str;
		}

	}
}