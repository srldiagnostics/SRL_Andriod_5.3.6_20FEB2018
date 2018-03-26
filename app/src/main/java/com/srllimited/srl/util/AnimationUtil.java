package com.srllimited.srl.util;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Ruchi Tiwari on 04/12/16.
 */

public class AnimationUtil
{
	public static void expand(final View v)
	{
		expand(v, 0);
	}

	public static void expand(final View v, final int durationMillis)
	{
		v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		final int targetHeight = v.getMeasuredHeight();

		// Older versions of android (pre API 21) cancel animations for views with a height of 0.
		v.getLayoutParams().height = 1;
		v.setVisibility(View.VISIBLE);
		Animation a = new Animation()
		{
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t)
			{
				v.getLayoutParams().height = interpolatedTime == 1 ? ViewGroup.LayoutParams.WRAP_CONTENT
						: (int) (targetHeight * interpolatedTime);
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds()
			{
				return true;
			}
		};

		// if duration is zero then auto duration is 1dp/ms
		a.setDuration(durationMillis > 0 ? durationMillis
				: ((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density)));

		v.startAnimation(a);
	}

	public static void collapse(final View v)
	{
		collapse(v, 0);
	}

	public static void collapse(final View v, final int durationMillis)
	{
		final int initialHeight = v.getMeasuredHeight();

		Animation a = new Animation()
		{
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t)
			{
				if (interpolatedTime == 1)
				{
					v.setVisibility(View.GONE);
				}
				else
				{
					v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds()
			{
				return true;
			}
		};

		// if duration is zero then auto duration is 1dp/ms
		a.setDuration(durationMillis > 0 ? durationMillis
				: ((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density)));
		v.startAnimation(a);
	}
}
