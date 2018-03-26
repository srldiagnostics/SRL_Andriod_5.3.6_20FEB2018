package com.srllimited.srl.otp.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSBroadcastReceiver extends BroadcastReceiver
{

	private OnSMSReceiver mReceive;

	public SMSBroadcastReceiver(OnSMSReceiver receive)
	{
		if (receive == null)
		{
			throw new IllegalArgumentException("SMS Receiver can't be null");
		}
		this.mReceive = receive;
	}

	public static IntentFilter getIntentFilter()
	{
		return new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
	}

	public void onReceive(Context context, Intent intent)
	{
		Bundle extras = intent.getExtras();
		if (extras != null)
		{
			Object[] pdus = (Object[]) extras.get("pdus");
			if (pdus.length > 0)
			{
				SmsMessage msg = SmsMessage.createFromPdu((byte[]) pdus[0]);
				if (msg != null && msg.getMessageBody() != null)
				{
					this.mReceive.onReceive(msg.getMessageBody());
				}
			}
		}
	}

	public interface OnSMSReceiver
	{
		void onReceive(String message);
	}
}
