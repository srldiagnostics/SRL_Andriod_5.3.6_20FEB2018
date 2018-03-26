
package com.srllimited.srl.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class DangerousPermissionActivity extends Activity
{
	private static final String TAG = "DangerousPermissionActivity";

	private ResultReceiver resultReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if (getIntent() != null)
		{
			resultReceiver = getIntent().getParcelableExtra(DangerousPermissionConstants.RESULT_RECEIVER);
			String[] permissionsArray = getIntent().getStringArrayExtra(DangerousPermissionConstants.PERMISSIONS_ARRAY);
			int requestCode = getIntent().getIntExtra(DangerousPermissionConstants.REQUEST_CODE,
					DangerousPermissionConstants.DEFAULT_CODE);
			if (!hasPermissions(permissionsArray))
			{
				ActivityCompat.requestPermissions(this, permissionsArray, requestCode);
			}
			else
			{
				onComplete(requestCode, permissionsArray, new int[] { PackageManager.PERMISSION_GRANTED });
			}
		}
		else
		{
			finish();
		}
	}

	private void onComplete(int requestCode, String[] permissions, int[] grantResults)
	{
		Bundle bundle = new Bundle();
		bundle.putStringArray(DangerousPermissionConstants.PERMISSIONS_ARRAY, permissions);
		bundle.putIntArray(DangerousPermissionConstants.GRANTED_RESULT, grantResults);
		bundle.putInt(DangerousPermissionConstants.REQUEST_CODE, requestCode);
		resultReceiver.send(requestCode, bundle);
		finish();

	}

	private boolean hasPermissions(String[] permissionsArray)
	{
		for (String permission : permissionsArray)
		{
			if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		onComplete(requestCode, permissions, grantResults);
	}
}
