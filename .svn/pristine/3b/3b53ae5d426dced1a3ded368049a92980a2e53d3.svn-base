package com.srllimited.srl.util;

/**
 * Created by codefyneandroid on 09-12-2016.
 */

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;

/**
 * PopWindow for Date Pick
 */
public class PopupAnimation extends PopupWindow
{

	ArrayList<String> salutationList = new ArrayList<String>();

	ImageView cancel, confirm;
	Context mcontext;
	private RecyclerView mRecyclerView;

	public PopupAnimation(Context context, String s)
	{
		this.mcontext = context;

	}

	public void showPopWin(Activity activity, View View)
	{

		if (null != activity)
		{

			Log.e("activity", activity + "");
			Log.e("view", View + "");
			TranslateAnimation trans = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
					0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);

			showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
			trans.setDuration(400);
			trans.setInterpolator(new AccelerateDecelerateInterpolator());

			View.startAnimation(trans);
		}
	}

	public void dismissPopWin(View view)
	{

		TranslateAnimation trans = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);

		trans.setDuration(400);
		trans.setInterpolator(new AccelerateInterpolator());
		trans.setAnimationListener(new Animation.AnimationListener()
		{

			@Override
			public void onAnimationStart(Animation animation)
			{

			}

			@Override
			public void onAnimationRepeat(Animation animation)
			{
			}

			@Override
			public void onAnimationEnd(Animation animation)
			{

				dismiss();
			}
		});

		view.startAnimation(trans);
	}

}
