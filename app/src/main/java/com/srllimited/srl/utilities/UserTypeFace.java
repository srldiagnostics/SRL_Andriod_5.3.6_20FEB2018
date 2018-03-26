package com.srllimited.srl.utilities;

import java.util.Hashtable;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by sri on 12/14/2016.
 */

public class UserTypeFace
{
	public static final String BOLD;

	public static final String LIGHT;

	public static final String REGULAR;

	public static final String EXOREGULAR;

	public static final String BOLDLARGE;

	public static final String SEMIBOLD;
	private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();

	static
	{
		REGULAR = "fonts/JosefinSans-Bold.ttf";
		LIGHT = "fonts/JosefinSans-Bold.ttf";
		BOLD = "fonts/JosefinSans-Bold.ttf";
		SEMIBOLD = "fonts/JosefinSans-Bold.ttf";
		BOLDLARGE = "fonts/JosefinSans-Bold.ttf";
		EXOREGULAR = "fonts/Exo2-Regular.ttf";
	}

	private static Typeface getTypeFace(Context context, String assetPath)
	{
		synchronized (cache)
		{
			if (!cache.containsKey(assetPath))
			{
				try
				{
					Typeface typeFace = Typeface.createFromAsset(context.getAssets(), assetPath);
					cache.put(assetPath, typeFace);
				}
				catch (Exception e)
				{
					Log.e("TypeFaces", "Typeface not loaded.");
					return null;
				}
			}
			return cache.get(assetPath);
		}
	}

	public static void Setthin(TextView obj)
	{
		obj.setTypeface(getTypeFace(obj.getContext(), EXOREGULAR), Typeface.NORMAL);
	}

	public static void Setlight(TextView obj)
	{

		obj.setTypeface(getTypeFace(obj.getContext(), LIGHT), Typeface.NORMAL);
	}

	public static void SetBold(TextView obj)
	{
		obj.setTypeface(getTypeFace(obj.getContext(), BOLD), Typeface.NORMAL);
	}

	public static void SetSEMIBOLD(TextView obj)
	{
		obj.setTypeface(getTypeFace(obj.getContext(), SEMIBOLD), Typeface.NORMAL);
	}

	public static void SetRegular(TextView obj)
	{
		obj.setTypeface(getTypeFace(obj.getContext(), EXOREGULAR), Typeface.NORMAL);
	}

	public static void SetBoldLarge(TextView obj)
	{
		obj.setTypeface(getTypeFace(obj.getContext(), BOLDLARGE), Typeface.BOLD);
	}

	public static Typeface getRegular(View obj)
	{
		return getTypeFace(obj.getContext(), EXOREGULAR);
	}
}
