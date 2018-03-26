package com.srllimited.srl.utilities;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by OFFICE on 3/18/2016.
 */
public class SharedPreference
{
	public static Context mContext;

	public static void setPreference(Context context, String key, String value)
	{
		mContext = context;
		SharedPreferences.Editor editor = mContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static String getPreferences(Context context, String key)
	{
		mContext = context;
		SharedPreferences prefs = mContext.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
		String text = prefs.getString(key, "");
		return text;
	}
}
