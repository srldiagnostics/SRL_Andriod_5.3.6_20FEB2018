package com.srllimited.srl.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompletedReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(final Context context, final Intent intent)
	{
		if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()) && !MonitorService.isRunning)
		{
			Intent tracking = new Intent(context, MonitorService.class);
			context.startService(tracking);
		}
	}
}
