package com.srllimited.srl.otp;

import com.srllimited.srl.otp.sms.SMSBroadcastReceiver;
import com.srllimited.srl.otp.sms.SMSHistoryObserver;
import com.srllimited.srl.otp.sms.SMSHistoryReader;
import com.srllimited.srl.permission.DangerousPermResponseCallBack;
import com.srllimited.srl.permission.DangerousPermissionResponse;
import com.srllimited.srl.permission.DangerousPermissionUtils;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;

public class OTPReader
{

	private static final int DEFAULT_MAX_TIMEOUT_IN_MILLIS = 1000;
	private static final int DEFAULT_TIMEOUT_IN_MILLIS = 30000;
	private static final int DEFAULT_MIN_TIMEOUT_IN_MILLIS = 300000;
	private final ContentResolver mContentResolver;
	private final SMSHistoryObserver mSmsHistoryObserver;
	private Context mContext;
	private int mTimeout;
	private long mStartTime;
	private boolean mStarted;
	private String mPattern;
	private Handler mTimeoutHandler;
	private OnOTPListener mOnOTPListener;
	private SMSBroadcastReceiver mSmsReceiver;
	private SMSHistoryReader mSmsHistoryReader;
	private SMSBroadcastReceiver.OnSMSReceiver mSMSReceiverListener = new SMSBroadcastReceiver.OnSMSReceiver()
	{
		@Override
		public void onReceive(String message)
		{
			checkPattern(message);
		}
	};
	private SMSHistoryObserver.OnSMSObserver mSMSObserverListener = new SMSHistoryObserver.OnSMSObserver()
	{
		@Override
		public void onChange(boolean z, Uri uri)
		{
			checkSmsHistory();
		}
	};

	public OTPReader(Context context, String pattern, OnOTPListener listener)
	{
		this(context, pattern, listener, DEFAULT_TIMEOUT_IN_MILLIS);
	}

	public OTPReader(Context context, String pattern, OnOTPListener listener, int timeoutInMillis)
	{

		checkArgument(context, pattern, listener);

		/*if (timeoutInMillis < DEFAULT_MIN_TIMEOUT_IN_MILLIS && timeoutInMillis > DEFAULT_MAX_TIMEOUT_IN_MILLIS)
		{
			timeoutInMillis = DEFAULT_TIMEOUT_IN_MILLIS;
		}*/
		if (timeoutInMillis < DEFAULT_TIMEOUT_IN_MILLIS && timeoutInMillis > DEFAULT_MAX_TIMEOUT_IN_MILLIS)
		{
			timeoutInMillis = DEFAULT_TIMEOUT_IN_MILLIS;
		}
		this.mStarted = false;
		this.mPattern = pattern;
		this.mContext = context;
		this.mOnOTPListener = listener;
		this.mTimeout = timeoutInMillis;
		this.mTimeoutHandler = new Handler();
		this.mStartTime = System.currentTimeMillis();
		this.mContentResolver = this.mContext.getContentResolver();
		this.mSmsHistoryReader = new SMSHistoryReader(this.mContentResolver);
		this.mSmsReceiver = new SMSBroadcastReceiver(this.mSMSReceiverListener);
		this.mSmsHistoryObserver = new SMSHistoryObserver(this.mTimeoutHandler, this.mSMSObserverListener);
	}

	public void start()
	{
		if (!this.mStarted)
		{
			this.mStarted = true;
			if (!canStart())
			{
				getPermission();
			}
		}
	}

	public void stop()
	{
		if (this.mStarted)
		{
			this.mTimeoutHandler.removeCallbacksAndMessages(null);
			if (canReadLog())
			{
				this.mContentResolver.unregisterContentObserver(this.mSmsHistoryObserver);
			}
			if (canIntercept())
			{
				try {
					this.mContext.unregisterReceiver(this.mSmsReceiver);
				}
				catch (Exception e)
				{

				}
			}
			this.mStarted = false;
		}
	}

	private void checkSmsHistory()
	{
		if (this.mStarted)
		{
			for (String message : this.mSmsHistoryReader.readCodeFromHistory(this.mStartTime))
			{
				checkPattern(message);
			}
		}
	}

	private void checkPattern(String message)
	{
		if (!TextUtils.isEmpty(message) && message.contains(mPattern))
		{
			notifyResult(true, message);
		}
	}

	private void notifyResult(boolean isVerified, String message)
	{
		this.mOnOTPListener.onComplete(isVerified, message);
		stop();
	}

	private void checkArgument(Context context, String pattern, OnOTPListener listener)
	{
		if (context == null)
		{
			throw new IllegalArgumentException("Context is null");
		}

		if (TextUtils.isEmpty(pattern))
		{
			throw new IllegalArgumentException("Pattern can't be emoty");
		}

		if (listener == null)
		{
			throw new IllegalArgumentException("OTP Listener can't be null");
		}
	}

	private boolean canStart()
	{
		if (canIntercept() || canReadLog())
		{

			this.mOnOTPListener.onProcess();

			if (canIntercept())
			{
				this.mContext.registerReceiver(this.mSmsReceiver, SMSBroadcastReceiver.getIntentFilter());
			}
			if (canReadLog())
			{
				this.mContentResolver.registerContentObserver(SMSHistoryReader.SMS_URI, true, this.mSmsHistoryObserver);
			}
			this.mTimeoutHandler.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					if (mStarted)
					{
						stop();
						notifyResult(false, "Timeout");
					}
				}
			}, (long) this.mTimeout);
			return true;
		}
		return false;
	}

	private boolean canIntercept()
	{
		return hasPermission(Manifest.permission.RECEIVE_SMS);
	}

	private boolean canReadLog()
	{
		return hasPermission(Manifest.permission.READ_SMS);
	}

	private boolean hasPermission(String permission)
	{
		return DangerousPermissionUtils.hasPermission(mContext, permission);
	}

	private void getPermission()
	{
		final int PERMISSION_REQUES_CODE = 101;
		final String[] PERMISSIONS = new String[] { Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS };
		DangerousPermissionUtils.getPermission(mContext, PERMISSIONS, PERMISSION_REQUES_CODE)
				.enqueue(new DangerousPermResponseCallBack()
				{
					@Override
					public void onComplete(final DangerousPermissionResponse permissionResponse)
					{
						if (permissionResponse.getRequestCode() == PERMISSION_REQUES_CODE)
						{
							boolean isShowError = true;
							if (permissionResponse.isGranted())
							{
								isShowError = !canStart();
							}
							if (isShowError)
							{
								notifyResult(false, "Permission denied");
							}
						}
					}
				});
	}

	public interface OnOTPListener
	{
		void onProcess();

		void onComplete(boolean isReceived, String message);
	}
}
