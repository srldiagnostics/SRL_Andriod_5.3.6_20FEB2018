package com.srllimited.srl.util;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by codefyneandroid on 12-12-2016.
 */

public class TypefaceUtil
{

	public static final int openSans_Bold = 1;

	public static final int openSans_BoldItalic = 2;

	public static final int openSans_ExtraBold = 3;

	public static final int openSans_ExtraBoldItalic = 4;

	public static final int openSans_Italic = 5;

	public static final int openSans_Light = 6;

	public static final int openSans_LightItalic = 7;

	public static final int openSans_Regular = 8;

	public static final int openSans_SemiBold = 9;

	public static final int openSans_SemiBoldItalic = 10;

	public static void setTypeFace(Context context, int typefaceCount, TextView view)
	{

		switch (typefaceCount)
		{
			case TypefaceUtil.openSans_Bold:
				view.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSans-Bold.ttf"));
				break;
			case TypefaceUtil.openSans_BoldItalic:
				view.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSans-BoldItalic.ttf"));
				break;
			case TypefaceUtil.openSans_ExtraBold:
				view.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSans-ExtraBold.ttf"));
				break;
			case TypefaceUtil.openSans_ExtraBoldItalic:
				view.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSans-ExtraBoldItalic.ttf"));
				break;
			case TypefaceUtil.openSans_Italic:
				view.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSans-Italic.ttf"));
				break;
			case TypefaceUtil.openSans_Light:
				view.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSans-Light.ttf"));
				break;
			case TypefaceUtil.openSans_LightItalic:
				view.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSans-LightItalic.ttf"));
				break;
			case TypefaceUtil.openSans_Regular:
				view.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSans-Regular.ttf"));
				break;
			case TypefaceUtil.openSans_SemiBold:
				view.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSans-Semibold.ttf"));
				break;
			case TypefaceUtil.openSans_SemiBoldItalic:
				view.setTypeface(Typeface.createFromAsset(context.getAssets(), "OpenSans-Semibolditalic.ttf"));
				break;

		}
	}
}
