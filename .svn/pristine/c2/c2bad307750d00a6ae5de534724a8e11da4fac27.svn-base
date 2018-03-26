
package com.srllimited.srl.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class DangerousPermissionRequest
{
	Context context;

	String[] permissions;

	int requestCode;

	DangerousPermissionResponse response;

	public DangerousPermissionRequest(Context context, String[] permissions, int requestCode)
	{
		this.context = context;
		this.permissions = permissions;
		this.requestCode = requestCode;
	}

	public void enqueue(final DangerousPermResponseCallBack callback)
	{
		int[] granted = new int[permissions.length];

		if (!DangerousPermissionUtils.hasPermission(context, permissions, granted))
		{
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
			{
				DangerousPermissionUtils.startPermissionRequest(context, permissions, requestCode,
						new ResultReceiver(new Handler())
						{
							@Override
							protected void onReceiveResult(int resultCode, Bundle resultData)
							{
								super.onReceiveResult(resultCode, resultData);
								int[] grantResult = resultData.getIntArray(DangerousPermissionConstants.GRANTED_RESULT);
								String[] permissions = resultData
										.getStringArray(DangerousPermissionConstants.PERMISSIONS_ARRAY);
								response = new DangerousPermissionResponse(permissions, grantResult, resultCode);
								callback.onComplete(
										new DangerousPermissionResponse(permissions, grantResult, resultCode));
							}
						});
			}
			else
			{
				callback.onComplete(new DangerousPermissionResponse(permissions, granted, requestCode));
			}
		}
		else
		{
			callback.onComplete(new DangerousPermissionResponse(permissions,
					new int[] { PackageManager.PERMISSION_GRANTED }, requestCode));
		}
	}
}
