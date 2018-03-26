package com.srllimited.srl;

import com.srllimited.srl.util.SharedPrefsUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by RuchiTiwari on 5/1/2017.
 */

public class AppRater
{

	private final static String APP_TITLE = "Us";// App Name

	private final static String APP_PNAME = "com.example.name";// Package Name

	private final static int DAYS_UNTIL_PROMPT = 3;//Min number of days

	private final static int LAUNCHES_UNTIL_PROMPT = 3;//Min number of launches

	private final static int DAYS_LATER_PROMPT = 7;//Later days

	private static AppRater appRater = null;

	private static boolean checked1 = true;

	private static LinearLayout rate_layout;

	private static TextView rateustitle;

	private static TextView good;

	private static LinearLayout mainLayout;

	private static LinearLayout layout;

	private static Context mContextt;

	private static Dialog promoDialog;

	private static LinearLayout chartersEntredLayout;

	private static Button never, later;

	private static LinearLayout.LayoutParams headerlp;

	public static void app_launched(Context mContext)
	{
		SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
		if (prefs.getBoolean("dontshowagain", false))
		{
			return;
		}

		//SharedPreferences.Editor editor = prefs.edit();
		SharedPreferences.Editor editor = prefs.edit();

		// Increment launch counter
		long launch_count = prefs.getLong("launch_count", 0) + 1;
		editor.putLong("launch_count", launch_count);

		// Get date of first launch
		Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
		if (date_firstLaunch == 0)
		{
			date_firstLaunch = System.currentTimeMillis();
			editor.putLong("date_firstlaunch", date_firstLaunch);
		}

		// Wait at least n days before opening
		if (launch_count >= LAUNCHES_UNTIL_PROMPT)
		{
			if (prefs.getBoolean("later_reopens", false)
					&& System.currentTimeMillis() >= date_firstLaunch + (DAYS_LATER_PROMPT * 24 * 60 * 60 * 1000))
			{
				Log.v("Days", "7 days completed");
				showRateDialog(mContext, editor);
			}
			else
			{
				if (prefs.getBoolean("later_reopens", false))
				{
					Log.v("Days", "7 days not completed");
					//
				}
				else
				{
					if (System.currentTimeMillis() >= date_firstLaunch + (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000))
					{
						Log.v("Days", "App installed 3 days before");
						showRateDialog(mContext, editor);
					}
				}
			}
		}

		editor.commit();
	}

	public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor)
	{
		mContextt = mContext;
		promoDialog = new Dialog(mContext);
		promoDialog.setTitle("Rate " + APP_TITLE);
		promoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		promoDialog.setCancelable(false);
		promoDialog.setContentView(R.layout.rate_us_activity);

		mainLayout = (LinearLayout) promoDialog.findViewById(R.id.recycler_header);
		rate_layout = (LinearLayout) promoDialog.findViewById(R.id.rate_layout);
		rateustitle = (TextView) promoDialog.findViewById(R.id.rateustitle);
		good = (TextView) promoDialog.findViewById(R.id.good);

		chartersEntredLayout = (LinearLayout) promoDialog.findViewById(R.id.chartersEntredLayout);
		layout = (LinearLayout) promoDialog.findViewById(R.id.layout);

		never = (Button) promoDialog.findViewById(R.id.cancel_btn);
		later = (Button) promoDialog.findViewById(R.id.submit_btn);

		rateustitle.setVisibility(View.VISIBLE);
		layout.setVisibility(View.GONE);
		chartersEntredLayout.setVisibility(View.GONE);
		good.setVisibility(View.GONE);

		appRater = new AppRater();
		appRater.ratingStars(editor);

		//		rateustitle.setOnTouchListener(new View.OnTouchListener()
		//		{
		//			@Override
		//			public boolean onTouch(final View view, final MotionEvent motionEvent)
		//			{
		//				appRater.rateUsActivity(1);
		//				return false;
		//			}
		//		});

		never.setText("Never");
		never.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				if (editor != null)
				{
					editor.putBoolean("dontshowagain", true);
					editor.commit();
				}
				promoDialog.dismiss();
			}
		});
		later.setText("Later");
		later.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				if (editor != null)
				{
					editor.putBoolean("later_reopens", true); // Reopens after 7days
					editor.commit();
				}
				promoDialog.dismiss();
			}
		});
		promoDialog.show();
	}

	private void rateUsActivity(int imgId, final SharedPreferences.Editor editor)
	{
		promoDialog.dismiss();
		if (imgId == 3 || imgId == 4)
		{
			if (editor != null)
			{
				editor.putBoolean("dontshowagain", true);
				editor.commit();
			}
			Intent i = new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.srllimited.srl"));
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContextt.startActivity(i);
		}
		else
		{
			SharedPrefsUtil.setStringPreference(mContextt, "Star", String.valueOf(imgId) + "");
			Intent intent = new Intent(mContextt, RateUsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContextt.startActivity(intent);
		}

	}

	private void ratingStars(final SharedPreferences.Editor editor)
	{
		try
		{
			for (int i = 0; i < 5; i++)
			{
				headerlp = new LinearLayout.LayoutParams(75, 75);
				rate_layout.setOrientation(LinearLayout.HORIZONTAL);
				ImageView starIv = new ImageView(mContextt);
				starIv.setId(i);
				starIv.setTag("true");
				starIv.setBackgroundResource(R.drawable.star_normal);
				headerlp.setMargins(15, 0, 15, 0);
				starIv.setLayoutParams(headerlp);
				rate_layout.addView(starIv);
				starIv.setOnClickListener(new View.OnClickListener()
				{
					public void onClick(View arg0)
					{
						try
						{
							int imgId = arg0.getId();
							ImageView fetchimage = (ImageView) promoDialog.findViewById(imgId);
							if (fetchimage != null)
							{
								if (fetchimage.getTag().toString().equalsIgnoreCase("false"))
								{
									checked1 = false;
								}
								else
								{
									checked1 = true;
								}
							}
							if (checked1)
							{
								for (int i = 0; i <= imgId; i++)
								{
									ImageView image = (ImageView) promoDialog.findViewById(i);
									Log.e("imgId", imgId + "");
									if (image != null)
									{
										image.setTag(false);
										image.setBackgroundResource(R.drawable.star_hover);
									}
								}
								appRater.rateUsActivity(imgId, editor);
							}
						}
						catch (Exception e)
						{
							Log.e("Error", e + "");
						}

					}
				});
			}
		}
		catch (Exception e)
		{
			Log.e("Error", e + "");
		}
	}
}