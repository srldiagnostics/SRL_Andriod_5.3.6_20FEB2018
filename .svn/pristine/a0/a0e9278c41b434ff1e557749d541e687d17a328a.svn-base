
package com.srllimited.srl.permission;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.ResultReceiver;
import android.support.v4.content.ContextCompat;

public class DangerousPermissionUtils
{
	public static DangerousPermissionRequest getPermission(Context context, String permission, int requestCode)
	{
		return new DangerousPermissionRequest(context, new String[] { permission }, requestCode);
	}

	public static DangerousPermissionRequest getPermission(Context context, String[] permissions, int requestCode)
	{
		return new DangerousPermissionRequest(context, permissions, requestCode);
	}

	public static boolean hasPermission(Context context, String[] permissions)
	{
		return hasPermission(context, permissions, null);
	}

	public static boolean hasPermission(Context context, String[] permissions, int[] granted)
	{
		if (permissions == null)
		{
			return false;
		}

		boolean allGranted = true;

		if (granted == null)
		{
			granted = new int[permissions.length];
		}

		for (int n = 0; n < permissions.length; n++)
		{
			granted[n] = ContextCompat.checkSelfPermission(context, permissions[n]);

			if (granted[n] != PackageManager.PERMISSION_GRANTED)
			{
				allGranted = false;
			}
		}

		return allGranted;
	}

	public static boolean hasPermission(Context context, String permission)
	{
		return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
	}

	public static void startPermissionRequest(Context context, String[] permissions, int requestCode,
			ResultReceiver receiver)
	{
		Intent intent = new Intent(context, DangerousPermissionActivity.class);

		intent.putExtra(DangerousPermissionConstants.REQUEST_CODE, requestCode);
		intent.putExtra(DangerousPermissionConstants.PERMISSIONS_ARRAY, permissions);
		intent.putExtra(DangerousPermissionConstants.RESULT_RECEIVER, receiver);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		context.startActivity(intent);
	}

}
