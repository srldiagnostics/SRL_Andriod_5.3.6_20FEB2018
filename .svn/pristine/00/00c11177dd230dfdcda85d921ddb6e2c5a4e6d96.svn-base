package com.srllimited.srl;

import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ChangePwdActivity extends MenuBaseActivity implements View.OnClickListener
{
	public static Activity changepwd;

	TextInputEditText oldpwd, newpwd, confirmpwd;

	Button done;

	Context mContext;
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			if (serverResponseData != null)
			{
				String message = serverResponseData.getMsg();

				if (Validate.notEmpty(message))
				{
					if (message.equalsIgnoreCase("Password updated"))
					{
						Toast.makeText(mContext, "Password updated successfully", Toast.LENGTH_SHORT).show();
						SharedPrefsUtil.setBooleanPreference(mContext, "remember", true);
						clearImage();
						loggedin_userid.setText("Guest");
						logout_text.setText("Login");
						SharedPreferences pref = getApplicationContext()
								.getSharedPreferences(Constants.login_credentials, MODE_PRIVATE);
						SharedPreferences.Editor editor = pref.edit();
						editor.putString(Constants.loggedin_username, "");
						editor.putString(Constants.loggedin_pwd, "");
						editor.putString(Constants.rememberme, "false");
						SharedPrefsUtil.setIntegerPreference(mContext, Constants.sharedPreferenceSelectedLoginActivity,
								Constants.m_login);
						//							SharedPrefsUtil.setStringPreference(context, Constants.constantUsername, "");
						editor.commit();
						Util.killAll();
						SharedPrefsUtil.setStringPreference(mContext, Constants.LOGO, "null");
						Intent intent = new Intent(ChangePwdActivity.this, LoginScreenActivity.class);
						startActivity(intent);

					}
					else
					{
						Toast.makeText(mContext, "Failed to Update", Toast.LENGTH_SHORT).show();
					}
				}
			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.change_password_activity);
		mContext = ChangePwdActivity.this;
		changepwd = this;
		header_loc_name.setText("Change Password");
		header_loc_name.setEnabled(false);

		oldpwd = (TextInputEditText) findViewById(R.id.oldpwd);
		newpwd = (TextInputEditText) findViewById(R.id.newpwd);
		confirmpwd = (TextInputEditText) findViewById(R.id.confirmpwd);
		done = (Button) findViewById(R.id.done);

		done.setOnClickListener(this);
	}

	@Override
	public void onClick(final View view)
	{
		switch (view.getId())
		{
			case R.id.done:
				String old_Pwd = oldpwd.getText().toString().trim();
				String new_Pwd = newpwd.getText().toString().trim();
				String confirm_Pwd = confirmpwd.getText().toString().trim();

				if (Validate.notEmpty(old_Pwd))
				{
					if (Validate.notEmpty(new_Pwd))
					{

						if (Validate.notEmpty(confirm_Pwd))
						{
							if (new_Pwd.equalsIgnoreCase(confirm_Pwd))
							{
								sendRequest(ApiRequestHelper.getChangePassword(mContext,
										Util.getStoredUsername(mContext), old_Pwd, new_Pwd));
							}
							else
							{
								Toast.makeText(mContext, "New password and Confirm password doesnot match",
										Toast.LENGTH_SHORT).show();
							}
						}
						else
						{
							Toast.makeText(mContext, "Please enter confirm password", Toast.LENGTH_SHORT).show();
						}

					}
					else
					{
						Toast.makeText(mContext, "Please enter new password", Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					Toast.makeText(mContext, "Please enter old password", Toast.LENGTH_SHORT).show();
				}
				break;
		}
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

}