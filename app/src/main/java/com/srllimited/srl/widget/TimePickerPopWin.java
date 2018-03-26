package com.srllimited.srl.widget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.bruce.pickerview.LoopScrollListener;
import com.bruce.pickerview.LoopView;
import com.srllimited.srl.R;
import com.srllimited.srl.util.Log;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created by codefyneandroid on 20-12-2016.
 */

public class TimePickerPopWin extends PopupWindow implements View.OnClickListener
{

	public ImageButton cancelBtn;
	public ImageButton confirmBtn;
	public LoopView hourLoopView;
	public LoopView minuteLoopView;
	public LoopView amPmLoopView;
	public View pickerContainerV;
	public TextView slot1;
	public View contentView;//root view
	String hr, mi, ap = "AM";
	List<String> hourList = new ArrayList<>();
	List<String> minuteList = new ArrayList<>();
	List<String> amPmList = new ArrayList<>();
	private int minHour; // min year
	private int maxMinute; // max year
	private int maxHour; // min year
	private int minMinute; // max year
	private int hourPos = 0;
	private int minutePos = 0;
	private Context mContext;
	private String textCancel;
	private String textConfirm;
	private int colorCancel;
	private int colorConfirm;
	private int btnTextsize;//text btnTextsize of cancel and confirm button
	private int viewTextSize;
	private boolean showDayMonthYear;
	private OnTimePickedListener mListener;

	public TimePickerPopWin(Builder builder)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());

		int hour = calendar.get(Calendar.HOUR_OF_DAY) - 1;
		int minute = calendar.get(Calendar.MINUTE);

		if (hour >= 12)
		{
			hour = hour - 12;
		}
		Log.e("hr", hour + "");
		Log.e("min", minute + "");

		this.minHour = hour;
		this.minMinute = minute;
		this.maxHour = builder.maxHour;
		this.maxMinute = builder.maxMinute;
		this.textCancel = builder.textCancel;
		this.textConfirm = builder.textConfirm;
		this.mContext = builder.context;
		this.mListener = builder.listener;
		this.colorCancel = builder.colorCancel;
		this.colorConfirm = builder.colorConfirm;
		this.btnTextsize = builder.btnTextSize;
		this.viewTextSize = builder.viewTextSize;
		this.showDayMonthYear = builder.showDayMonthYear;
		this.hourPos = hour;
		this.minutePos = minute;
		initView();
	}

	/**
	 * Transform int to String with prefix "0" if less than 10
	 * @param num
	 * @return
	 */
	public static String format2LenStr(int num)
	{

		return (num < 10) ? "0" + num : String.valueOf(num);
	}

	private void initView()
	{

		contentView = LayoutInflater.from(mContext)
				.inflate(showDayMonthYear ? com.bruce.pickerview.R.layout.layout_date_picker_inverted
						: R.layout.layout_time_picker, null);
		cancelBtn = (ImageButton) contentView.findViewById(R.id.btn_cancel);
		confirmBtn = (ImageButton) contentView.findViewById(R.id.btn_confirm);
		hourLoopView = (LoopView) contentView.findViewById(R.id.hourLoop);
		minuteLoopView = (LoopView) contentView.findViewById(R.id.minuteLoop);
		amPmLoopView = (LoopView) contentView.findViewById(R.id.amPmLoop);
		pickerContainerV = contentView.findViewById(R.id.container_picker);

		hourLoopView.setLoopListener(new LoopScrollListener()
		{
			@Override
			public void onItemSelect(int item)
			{
				hourPos = item;
				hr = hourList.get(item);
			}
		});
		minuteLoopView.setLoopListener(new LoopScrollListener()
		{
			@Override
			public void onItemSelect(int item)
			{
				minutePos = item;
				mi = minuteList.get(item);
			}
		});
		amPmLoopView.setLoopListener(new LoopScrollListener()
		{
			@Override
			public void onItemSelect(int item)
			{
				if (item == 0)
				{
					ap = "AM";
				}
				else
				{
					ap = "PM";
				}
			}
		});

		initPickerViews(); // init year and month loop view

		cancelBtn.setOnClickListener(this);
		confirmBtn.setOnClickListener(this);
		contentView.setOnClickListener(this);

		setTouchable(true);
		setFocusable(true);
		// setOutsideTouchable(true);
		setBackgroundDrawable(new BitmapDrawable());
		setAnimationStyle(com.bruce.pickerview.R.style.FadeInPopWin);
		setContentView(contentView);
		setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
		setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
	}

	/**
	 * Init year and month loop view,
	 * Let the day loop view be handled separately
	 */
	private void initPickerViews()
	{

		for (int i = 1; i <= maxHour; i++)
		{
			hourList.add(format2LenStr(i));
		}

		for (int j = 0; j <= maxMinute; j++)
		{
			minuteList.add(format2LenStr(j));
		}

		hourLoopView.setDataList(hourList);
		hourLoopView.setInitPosition(hourPos);

		minuteLoopView.setDataList(minuteList);
		minuteLoopView.setInitPosition(minutePos);

		// am pm list
		amPmList.add("AM");
		amPmList.add("PM");
		amPmList.add("AM");
		amPmList.add("PM");
		amPmList.add("AM");
		amPmList.add("PM");
		amPmList.add("AM");
		amPmList.add("PM");

		amPmLoopView.setDataList(amPmList);
		amPmLoopView.setInitPosition(0);

		// day List

		Calendar cal = Calendar.getInstance();
		int ampm = cal.get(Calendar.AM_PM);

		if (ampm == 0)
		{
			ap = "AM";
		}
		else
		{
			ap = "PM";
		}
		amPmLoopView.setInitPosition(ampm);
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd");

	}

	/**
	 * Show date picker popWindow
	 *
	 * @param activity
	 */
	public void showPopWin(Activity activity)
	{

		if (null != activity)
		{

			TranslateAnimation trans = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
					0, Animation.RELATIVE_TO_SELF, 1, Animation.RELATIVE_TO_SELF, 0);

			showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
			trans.setDuration(400);
			trans.setInterpolator(new AccelerateDecelerateInterpolator());

			pickerContainerV.startAnimation(trans);
		}
	}

	/**
	 * Dismiss date picker popWindow
	 */
	public void dismissPopWin()
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

		pickerContainerV.startAnimation(trans);
	}

	@Override
	public void onClick(View v)
	{

		if (v == contentView || v == cancelBtn)
		{

			dismissPopWin();
		}
		else if (v == confirmBtn)
		{

			if (null != mListener)
			{

				StringBuffer sb = new StringBuffer();

				sb.append(hourList.get(hourPos));
				sb.append(":");
				sb.append(minuteList.get(minutePos));
				sb.append(" " + ap);
				mListener.onTimePickCompleted(hourPos, minutePos, ap, sb.toString());
			}

			dismissPopWin();
		}
	}

	public interface OnTimePickedListener
	{
		void onTimePickCompleted(int hour, int minute, String amPm, String timeDesc);
	}

	public static class Builder
	{

		//Required
		private Context context;

		private OnTimePickedListener listener;
		private int minMinute = 0;
		private int minHour = 0;
		private int maxMinute = 59;
		private int maxHour = 12;
		//Option
		private boolean showDayMonthYear = false;
		private String textCancel = "Cancel";
		private String textConfirm = "Confirm";
		private int colorCancel = Color.parseColor("#999999");
		private int colorConfirm = Color.parseColor("#303F9F");
		private int btnTextSize = 16;//text btnTextsize of cancel and confirm button
		private int viewTextSize = 25;
		private int hourPos, minutePos;

		public Builder(Context context, OnTimePickedListener listener)
		{
			this.context = context;
			this.listener = listener;
		}

		public Builder colorCancel(int colorCancel)
		{
			this.colorCancel = colorCancel;
			return this;
		}

		public Builder colorConfirm(int colorConfirm)
		{
			this.colorConfirm = colorConfirm;
			return this;
		}

		/**
		 * set btn text btnTextSize
		 * @param textSize dp
		 */
		public Builder btnTextSize(int textSize)
		{
			this.btnTextSize = textSize;
			return this;
		}

		public Builder viewTextSize(int textSize)
		{
			this.viewTextSize = textSize;
			return this;
		}

		public Builder setHour(int hour)
		{
			this.hourPos = hour;
			return this;
		}

		public Builder setMinute(int minute)
		{
			this.minutePos = minute;
			return this;
		}

		public TimePickerPopWin build()
		{

			return new TimePickerPopWin(this);
		}

	}

}
