package com.srllimited.srl.widget;

import com.srllimited.srl.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RoundCornerProgressView extends FrameLayout
{
	private final static int DEFAULT_MAX_PROGRESS = 100;
	private final static int DEFAULT_PROGRESS = 0;
	private final static int DEFAULT_SECONDARY_PROGRESS = 0;
	private final static int DEFAULT_PROGRESS_RADIUS = 0;
	private final static int DEFAULT_BACKGROUND_PADDING = 0;
	private static final android.view.animation.Interpolator DEFAULT_INTERPOLATER = new AccelerateDecelerateInterpolator();
	private View mViewBackground;
	private View mViewProgress;
	private View mViewSecondaryProgress;
	private int mRadius;
	private int mPadding;
	private int mTotalWidth;
	private float mMax;
	private float mProgress;
	private float mSecondaryProgress;
	private int mColorBackground;
	private int mColorProgress;
	private int mColorSecondaryProgress;
	private boolean mIsReverse;
	private boolean mIsAnimate = true;
	private ValueAnimator mAnimator;
	private ValueAnimator mAnimatorSecondary;
	private RoundCornerProgressView.OnProgressChangedListener mProgressChangedListener;

	public RoundCornerProgressView(Context context)
	{
		this(context, null);
	}

	public RoundCornerProgressView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public RoundCornerProgressView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public RoundCornerProgressView(final Context context, final AttributeSet attrs, final int defStyleAttr,
			final int defStyleRes)
	{
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr)
	{
		if (isInEditMode())
		{
			previewLayout(context);
		}
		else
		{
			setup(context, attrs);
		}
	}

	private void previewLayout(Context context)
	{
		//		setGravity(Gravity.CENTER);
		TextView tv = new TextView(context);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		tv.setLayoutParams(params);
		tv.setGravity(Gravity.CENTER);
		tv.setText(getClass().getSimpleName());
		tv.setTextColor(Color.WHITE);
		tv.setBackgroundColor(Color.GRAY);
		addView(tv);
	}

	public void setup(Context context, AttributeSet attrs)
	{
		setupStyleable(context, attrs);

		removeAllViews();
		// Setup layout for sub class
		LayoutInflater.from(context).inflate(R.layout.round_corner_progress_view, this);
		// Initial default view
		mViewBackground = findViewById(R.id.layout_background);
		mViewProgress = findViewById(R.id.view_progress);
		mViewSecondaryProgress = findViewById(R.id.view_secondary_progress);
	}

	// Retrieve initial parameter from view attribute
	public void setupStyleable(Context context, AttributeSet attrs)
	{
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerProgressView);

		mRadius = (int) typedArray.getDimension(R.styleable.RoundCornerProgressView_rcpvRadius,
				dp2px(DEFAULT_PROGRESS_RADIUS));
		mPadding = (int) typedArray.getDimension(R.styleable.RoundCornerProgressView_rcpvBackgroundPadding,
				dp2px(DEFAULT_BACKGROUND_PADDING));

		mIsReverse = typedArray.getBoolean(R.styleable.RoundCornerProgressView_rcpvReverse, false);

		mMax = typedArray.getFloat(R.styleable.RoundCornerProgressView_rcpvMax, DEFAULT_MAX_PROGRESS);
		mProgress = typedArray.getFloat(R.styleable.RoundCornerProgressView_rcpvProgress, DEFAULT_PROGRESS);
		mSecondaryProgress = typedArray.getFloat(R.styleable.RoundCornerProgressView_rcpvSecondaryProgress,
				DEFAULT_SECONDARY_PROGRESS);

		mColorBackground = typedArray.getColor(R.styleable.RoundCornerProgressView_rcpvBackgroundColor,
				Color.TRANSPARENT);
		mColorProgress = typedArray.getColor(R.styleable.RoundCornerProgressView_rcpvProgressColor, Color.WHITE);
		mColorSecondaryProgress = typedArray.getColor(R.styleable.RoundCornerProgressView_rcpvSecondaryProgressColor,
				Color.GRAY);
		typedArray.recycle();
	}

	// Progress bar always reload when view size has changed
	@Override
	protected void onSizeChanged(int newWidth, int newHeight, int oldWidth, int oldHeight)
	{
		super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight);
		if (!isInEditMode())
		{
			mTotalWidth = newWidth;
			drawAll();
			postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					drawPrimaryProgress();
					drawSecondaryProgress();
				}
			}, 5);
		}
	}

	// Redraw all view
	protected void drawAll()
	{
		drawBackgroundProgress();
		drawPadding();
		drawProgressReverse();
		drawPrimaryProgress();
		drawSecondaryProgress();
	}

	// Draw mProgress background
	@SuppressWarnings("deprecation")
	private void drawBackgroundProgress()
	{
		GradientDrawable backgroundDrawable = createGradientDrawable(mColorBackground);
		int newRadius = mRadius - (mPadding / 2);
		backgroundDrawable.setCornerRadii(
				new float[] { newRadius, newRadius, newRadius, newRadius, newRadius, newRadius, newRadius, newRadius });
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
		{
			mViewBackground.setBackground(backgroundDrawable);
		}
		else
		{
			mViewBackground.setBackgroundDrawable(backgroundDrawable);
		}
	}

	// Create an empty color rectangle gradient drawable
	protected GradientDrawable createGradientDrawable(int color)
	{
		GradientDrawable gradientDrawable = new GradientDrawable();
		gradientDrawable.setShape(GradientDrawable.RECTANGLE);
		gradientDrawable.setColor(color);
		return gradientDrawable;
	}

	private void drawPrimaryProgress()
	{
		drawProgress(mViewProgress, mMax, mProgress, mTotalWidth, mRadius, mPadding, mColorProgress, mIsReverse);
	}

	private void drawSecondaryProgress()
	{
		drawProgress(mViewSecondaryProgress, mMax, mSecondaryProgress, mTotalWidth, mRadius, mPadding,
				mColorSecondaryProgress, mIsReverse);
	}

	private void drawProgressReverse()
	{
		setupReverse(mViewProgress);
		setupReverse(mViewSecondaryProgress);
	}

	// Change mProgress position by depending on mIsReverse flag
	private void setupReverse(View viewProgress)
	{
		RelativeLayout.LayoutParams progressParams = (RelativeLayout.LayoutParams) viewProgress.getLayoutParams();
		removeLayoutParamsRule(progressParams);
		if (mIsReverse)
		{
			progressParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			// For support with RTL on API 17 or more
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
			{
				progressParams.addRule(RelativeLayout.ALIGN_PARENT_END);
			}
		}
		else
		{
			progressParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			// For support with RTL on API 17 or more
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
			{
				progressParams.addRule(RelativeLayout.ALIGN_PARENT_START);
			}
		}
		viewProgress.setLayoutParams(progressParams);
	}

	private void drawPadding()
	{
		mViewBackground.setPadding(mPadding, mPadding, mPadding, mPadding);
	}

	// Remove all of relative align rule
	private void removeLayoutParamsRule(RelativeLayout.LayoutParams layoutParams)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
		{
			layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_END);
			layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
			layoutParams.removeRule(RelativeLayout.ALIGN_PARENT_START);
		}
		else
		{
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
			layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
		}
	}

	@SuppressLint("NewApi")
	protected float dp2px(float dp)
	{
		DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
		return Math.round(dp * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
	}

	public boolean isReverse()
	{
		return mIsReverse;
	}

	public void setReverse(boolean isReverse)
	{
		this.mIsReverse = isReverse;
		drawProgressReverse();
		drawPrimaryProgress();
		drawSecondaryProgress();
	}

	public int getRadius()
	{
		return mRadius;
	}

	public void setRadius(int radius)
	{
		if (radius >= 0)
		{
			this.mRadius = radius;
		}
		drawBackgroundProgress();
		drawPrimaryProgress();
		drawSecondaryProgress();
	}

	public int getPadding()
	{
		return mPadding;
	}

	public void setPadding(int padding)
	{
		if (padding >= 0)
		{
			this.mPadding = padding;
		}
		drawPadding();
		drawPrimaryProgress();
		drawSecondaryProgress();
	}

	public float getMax()
	{
		return mMax;
	}

	public void setMax(float max)
	{
		if (max >= 0)
		{
			this.mMax = max;
		}
		if (this.mProgress > max)
		{
			this.mProgress = max;
		}
		drawPrimaryProgress();
		drawSecondaryProgress();
	}

	public float getLayoutWidth()
	{
		return mTotalWidth;
	}

	public float getProgress()
	{
		return mProgress;
	}

	public void setProgress(float progress)
	{
		setDrawProgress(progress, true);
	}

	public float getSecondaryProgressWidth()
	{
		if (mViewSecondaryProgress != null)
		{
			return mViewSecondaryProgress.getWidth();
		}
		return 0;
	}

	public float getSecondaryProgress()
	{
		return mSecondaryProgress;
	}

	public void setSecondaryProgress(float secondaryProgress)
	{
		setDrawProgress(secondaryProgress, false);
	}

	private void setDrawProgress(float progress, final boolean isPrimaryProgress)
	{
		if (progress < 0)
		{
			progress = 0;
		}
		else if (progress > mMax)
		{
			progress = mMax;
		}

		if (!mIsAnimate)
		{
			if (isPrimaryProgress)
			{
				this.mProgress = progress;
				drawPrimaryProgress();
				notifyProgressChanged(this.mProgress, true);
			}
			else
			{
				this.mSecondaryProgress = progress;
				drawSecondaryProgress();
				notifyProgressChanged(this.mSecondaryProgress, false);
			}
			return;
		}

		if (isPrimaryProgress)
		{
			startAnimator(this.mProgress, progress);
		}
		else
		{
			startAnimatorSecondary(this.mSecondaryProgress, progress);
		}
	}

	private void startAnimator(float lastProgress, float progress)
	{
		if (mAnimator != null)
		{
			mAnimator.cancel();
		}

		if (mAnimator == null)
		{
			mAnimator = ValueAnimator.ofFloat(lastProgress, progress);
			mAnimator.setInterpolator(DEFAULT_INTERPOLATER);
			mAnimator.setDuration(900);
			mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
			{
				@Override
				public void onAnimationUpdate(ValueAnimator animation)
				{
					mProgress = (Float) animation.getAnimatedValue();
					drawPrimaryProgress();
				}
			});

			mAnimator.addListener(new AnimatorListenerAdapter()
			{
				@Override
				public void onAnimationEnd(final Animator animation)
				{
					super.onAnimationEnd(animation);
					notifyProgressChanged(mProgress, true);
				}
			});
		}
		else
		{
			mAnimator.setFloatValues(lastProgress, progress);
		}
		mAnimator.start();
	}

	private void startAnimatorSecondary(float lastProgress, float progress)
	{
		if (mAnimatorSecondary != null)
		{
			mAnimatorSecondary.cancel();
		}

		if (mAnimatorSecondary == null)
		{
			mAnimatorSecondary = ValueAnimator.ofFloat(lastProgress, progress);
			mAnimatorSecondary.setInterpolator(DEFAULT_INTERPOLATER);
			mAnimatorSecondary.setDuration(500);
			mAnimatorSecondary.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
			{
				@Override
				public void onAnimationUpdate(ValueAnimator animation)
				{
					mSecondaryProgress = (Float) animation.getAnimatedValue();
					drawSecondaryProgress();
				}
			});
			mAnimator.addListener(new AnimatorListenerAdapter()
			{
				@Override
				public void onAnimationEnd(final Animator animation)
				{
					super.onAnimationEnd(animation);
					notifyProgressChanged(mSecondaryProgress, false);
				}
			});
		}
		else
		{
			mAnimatorSecondary.setFloatValues(lastProgress, progress);
		}
		mAnimatorSecondary.start();
	}

	private void notifyProgressChanged(float progress, boolean isPrimaryProgress)
	{
		if (mProgressChangedListener != null)
		{
			mProgressChangedListener.onProgressChanged(getId(), progress, isPrimaryProgress,
					(isPrimaryProgress ? false : true));
		}
	}

	public int getProgressBackgroundColor()
	{
		return mColorBackground;
	}

	public void setProgressBackgroundColor(int colorBackground)
	{
		this.mColorBackground = colorBackground;
		drawBackgroundProgress();
	}

	public int getProgressColor()
	{
		return mColorProgress;
	}

	public void setProgressColor(int colorProgress)
	{
		this.mColorProgress = colorProgress;
		drawPrimaryProgress();
	}

	public int getSecondaryProgressColor()
	{
		return mColorSecondaryProgress;
	}

	public void setSecondaryProgressColor(int colorSecondaryProgress)
	{
		this.mColorSecondaryProgress = colorSecondaryProgress;
		drawSecondaryProgress();
	}

	public void setOnProgressChangedListener(RoundCornerProgressView.OnProgressChangedListener listener)
	{
		mProgressChangedListener = listener;
	}

	@Override
	public void invalidate()
	{
		super.invalidate();
		drawAll();
	}

	@Override
	protected Parcelable onSaveInstanceState()
	{
		Parcelable superState = super.onSaveInstanceState();
		RoundCornerProgressView.SavedState ss = new RoundCornerProgressView.SavedState(superState);

		ss.radius = this.mRadius;
		ss.padding = this.mPadding;

		ss.colorBackground = this.mColorBackground;
		ss.colorProgress = this.mColorProgress;
		ss.colorSecondaryProgress = this.mColorSecondaryProgress;

		ss.max = this.mMax;
		ss.progress = this.mProgress;
		ss.secondaryProgress = this.mSecondaryProgress;

		ss.isReverse = this.mIsReverse;
		return ss;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state)
	{
		if (!(state instanceof RoundCornerProgressView.SavedState))
		{
			super.onRestoreInstanceState(state);
			return;
		}

		RoundCornerProgressView.SavedState ss = (RoundCornerProgressView.SavedState) state;
		super.onRestoreInstanceState(ss.getSuperState());

		this.mRadius = ss.radius;
		this.mPadding = ss.padding;

		this.mColorBackground = ss.colorBackground;
		this.mColorProgress = ss.colorProgress;
		this.mColorSecondaryProgress = ss.colorSecondaryProgress;

		this.mMax = ss.max;
		this.mProgress = ss.progress;
		this.mSecondaryProgress = ss.secondaryProgress;

		this.mIsReverse = ss.isReverse;
	}

	public boolean isAnimate()
	{
		return mIsAnimate;
	}

	public void setAnimate(boolean animate)
	{
		this.mIsAnimate = animate;
	}

	private void drawProgress(View viewProgress, float max, float progress, float totalWidth, int radius, int padding,
			int colorProgress, boolean isReverse)
	{
		GradientDrawable backgroundDrawable = createGradientDrawable(colorProgress);
		int newRadius = radius - (padding / 2);
		backgroundDrawable.setCornerRadii(
				new float[] { newRadius, newRadius, newRadius, newRadius, newRadius, newRadius, newRadius, newRadius });
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
		{
			viewProgress.setBackground(backgroundDrawable);
		}
		else
		{
			viewProgress.setBackgroundDrawable(backgroundDrawable);
		}

		float ratio = max / progress;
		int progressWidth = (int) ((totalWidth - (padding * 2)) / ratio);
		ViewGroup.LayoutParams progressParams = viewProgress.getLayoutParams();
		progressParams.width = progressWidth;
		viewProgress.setLayoutParams(progressParams);
	}

	public interface OnProgressChangedListener
	{
		void onProgressChanged(int viewId, float progress, boolean isPrimaryProgress, boolean isSecondaryProgress);
	}

	private static class SavedState extends BaseSavedState
	{
		public static final Creator<RoundCornerProgressView.SavedState> CREATOR = new Creator<RoundCornerProgressView.SavedState>()
		{
			public RoundCornerProgressView.SavedState createFromParcel(Parcel in)
			{
				return new RoundCornerProgressView.SavedState(in);
			}

			public RoundCornerProgressView.SavedState[] newArray(int size)
			{
				return new RoundCornerProgressView.SavedState[size];
			}
		};
		private float max;
		private float progress;
		private float secondaryProgress;
		private int radius;
		private int padding;
		private int colorBackground;
		private int colorProgress;
		private int colorSecondaryProgress;
		private boolean isReverse;

		SavedState(Parcelable superState)
		{
			super(superState);
		}

		private SavedState(Parcel in)
		{
			super(in);
			this.max = in.readFloat();
			this.progress = in.readFloat();
			this.secondaryProgress = in.readFloat();

			this.radius = in.readInt();
			this.padding = in.readInt();

			this.colorBackground = in.readInt();
			this.colorProgress = in.readInt();
			this.colorSecondaryProgress = in.readInt();

			this.isReverse = in.readByte() != 0;
		}

		@Override
		public void writeToParcel(Parcel out, int flags)
		{
			super.writeToParcel(out, flags);
			out.writeFloat(this.max);
			out.writeFloat(this.progress);
			out.writeFloat(this.secondaryProgress);

			out.writeInt(this.radius);
			out.writeInt(this.padding);

			out.writeInt(this.colorBackground);
			out.writeInt(this.colorProgress);
			out.writeInt(this.colorSecondaryProgress);

			out.writeByte((byte) (this.isReverse ? 1 : 0));
		}
	}
}
