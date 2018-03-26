package com.srllimited.srl.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public class Semicircle extends View
{

	private Paint markerPaint;

	private Paint textPaint;

	private Paint circlePaint;

	private int textHeight;

	public Semicircle(Context context)
	{
		this(context, null);
	}

	public Semicircle(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public Semicircle(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public Semicircle(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes)
	{
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr)
	{
		initCircleView();
	}

	protected void initCircleView()
	{
		setFocusable(true);

		circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		circlePaint.setColor(getResources().getColor(android.R.color.black));
		circlePaint.setStrokeWidth(2);
		circlePaint.setStyle(Paint.Style.STROKE);

		markerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		markerPaint.setColor(getResources().getColor(android.R.color.darker_gray));
	}

	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int measuredWidth = measure(widthMeasureSpec);
		int measuredHeight = measure(heightMeasureSpec);

		int d = Math.min(measuredWidth, measuredHeight);

		setMeasuredDimension(d, d);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		// TODO Auto-generated method stub
		int px, py;

		px = getMeasuredWidth();
		py = getMeasuredHeight();

		int radius = Math.min(px, py);

		RectF oval = new RectF();
		oval.set(px - radius, py - radius, px + radius, py + radius);
		//        oval.set(px + radius, py + radius, px - radius, py - radius);

		canvas.drawArc(oval, -180, -180, false, circlePaint);
		//canvas.drawCircle(px, py, radius, circlePaint);
		canvas.save();

	}

	private int measure(int measureSpec)
	{
		int result = 0;

		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.UNSPECIFIED)
		{
			result = 200;
		}
		else
		{
			result = specSize;
		}

		return result;
	}

}