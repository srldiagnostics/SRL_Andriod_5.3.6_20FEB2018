package com.srllimited.srl.permission;

import android.content.pm.PackageManager;

/**
 * created by Ruchi Tiwari
 */

public class DangerousPermissionResponse
{
	String[] permission;

	int[] grantResult;

	int requestCode;

	public DangerousPermissionResponse(String[] permission, int[] grantResult, int requestCode)
	{
		this.permission = permission;
		this.grantResult = grantResult;
		this.requestCode = requestCode;
	}

	public boolean isGranted()
	{
		if (grantResult != null && grantResult.length > 0)
		{
			for (int granted : grantResult)
				if (granted != PackageManager.PERMISSION_GRANTED)
					return false;

			return true;
		}

		return false;
	}

	public String[] getPermission()
	{
		return permission;
	}

	public int[] getGrantResult()
	{
		return grantResult;
	}

	public int getRequestCode()
	{
		return requestCode;
	}
}
