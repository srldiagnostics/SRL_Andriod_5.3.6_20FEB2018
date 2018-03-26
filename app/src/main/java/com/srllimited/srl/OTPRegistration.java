package com.srllimited.srl;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.internal.UpdateNotificationTask;
import com.srllimited.srl.otp.OTPReader;
import com.srllimited.srl.serverapis.ApiRequestReferralCode;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.utilities.MyCountDownTimer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class OTPRegistration extends MenuBaseActivity implements RestApiCallUtil.VolleyCallback {
    MyCountDownTimer myCountDownTimer;
    public static String user_id, user_pass;
    private static final String sPATTERN = "OTP to activate your MYSRL user account is ";
    public static Activity otpreg;
    Context context;
    Button next;
    TextView otptext;
    Button resend;
    EditText enter_otp;
    String mobile = "";
    Button verifynow;
    int time = 0;
    String fetchedDOTP = "";
    private OTPReader mOtpReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.addContentView(R.layout.verify_mobile);
        context = OTPRegistration.this;

        next = (Button) findViewById(R.id.next);
        otpreg = this;
        header_loc_name.setText("Verify your mobile number");
        header_loc_name.setEnabled(false);

        enter_otp = (EditText) findViewById(R.id.enter_otp);
        resend = (Button) findViewById(R.id.resend);
        verifynow = (Button) findViewById(R.id.verifynow);
        otptext = (TextView) findViewById(R.id.otptext);

        Intent intent = getIntent();
        if (intent.getStringExtra(Constants.registered_mobile) != null) {
            mobile = intent.getStringExtra(Constants.registered_mobile);
        }
        otptext.setText("Please provide OTP sent on +91" + mobile);

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.IN_DEVICE_ID, Constants.devicetobepassed);

                //                String params = "?"+Constants.IN_DEVICE_ID+"="+Constants.devicetobepassed+"";
                RestApiCallUtil.postServerResponse(context, ApiRequestReferralCode.RESEND_OTP, params);
            }
        });
        verifynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOTP();
            }
        });

        resend.setVisibility(View.GONE);
        setResendVisibleAfter3Minutes();

        startOTPReader();
    }

    private void startOTPReader() {
        if (mOtpReader != null) {
            mOtpReader.stop();
        }

        mOtpReader = new OTPReader(this, sPATTERN, new OTPReader.OnOTPListener() {
            @Override
            public void onProcess() {
                RestApiCallUtil.showProgressDialog(context);
                if (RestApiCallUtil.mProgressDialog != null) {
                    RestApiCallUtil.mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",

                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mOtpReader.stop();
                                    dialog.dismiss();
                                }
                            });
                }
            }

            @Override
            public void onComplete(boolean isReceived, String message) {

                mOtpReader.stop();
                RestApiCallUtil.hideProgressDialog();

                if (isReceived) {
                    enter_otp.setText(message.replace(sPATTERN, "").trim());
                }
            }
        });
        mOtpReader.start();
        RestApiCallUtil.showProgressDialog(OTPRegistration.this);
        myCountDownTimer = new MyCountDownTimer(30000, 1000);
        myCountDownTimer.start();
    }

    private void verifyOTP() {
        if (RestApiCallUtil.isOnline(context)) {
            if (enter_otp.getText().toString() != null && !enter_otp.getText().toString().isEmpty()) {
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.IN_DEVICE_ID, Constants.devicetobepassed);
                params.put(Constants.IN_OTP, enter_otp.getText().toString());
                //                String params = "?"+Constants.IN_DEVICE_ID+"="+Constants.devicetobepassed+"&"+Constants.IN_OTP+"="+fetchedDOTP;

                if (Constants.isfromaddmenbers) {
                    RestApiCallUtil.postServerResponse(context, ApiRequestReferralCode.ValidateRegistration_Family, params);

                } else {
                    RestApiCallUtil.postServerResponse(context, ApiRequestReferralCode.VALIDATE_REGISTRATION, params);
                }
            } else {
                enter_otp.setError("Please enter otp");
            }
        } else {
            RestApiCallUtil.NetworkError(context);
        }
    }

    @Override
    public void onSuccessResponse(ApiRequestReferralCode referralCode, String result, HashMap<String, String> value) {
        switch (referralCode) {
            case RESEND_OTP:
                //                Toast.makeText(context,result+"",Toast.LENGTH_SHORT).show();
                fetchedDOTP = result;
                startOTPReader();
                break;
            case VALIDATE_REGISTRATION:

                if (result != null && result.equalsIgnoreCase("Validation Successful")) {
                    showSettingsAlert();
                } else {
                    Toast.makeText(context, "The OTP entered is incorrect!", Toast.LENGTH_SHORT).show();
                }

                break;
            case ValidateRegistration_Family:

                if (result != null && result.contains("Validation Successful")) {
                    user_id = result.split("-")[1];
                    user_pass = result.split("-")[2];
                    //showSettingsAlert();

                    try {
                        if (RegistrationScreen.registration != null) {
                            RegistrationScreen.registration.finish();
                        }
                        new UpdateNotificationTask(OTPRegistration.this);
                    } catch (Exception e) {

                    }


                    finish();
                } else {
                    Toast.makeText(context, "The OTP entered is incorrect!", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    public void showSettingsAlert() {
        try {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(OTPRegistration.this,
                    AlertDialog.THEME_HOLO_LIGHT);
            builder.setMessage("Login id/password is sent to your registered mobile no/email address")
                    .setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        if (RegistrationScreen.registration != null) {
                            RegistrationScreen.registration.finish();
                        }
                        new UpdateNotificationTask(OTPRegistration.this);
                    } catch (Exception e) {

                    }

                    dialog.cancel();
                    finish();
                }
            });
            builder.show();
        } catch (Exception e) {

        }
    }

    private void setResendVisibleAfter3Minutes() {
        final Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (time == 3) {
                            resend.setVisibility(View.VISIBLE);
                            t.cancel();
                            return;
                        }

                        time++;

                    }
                });
            }
        }, 0, 60000);
    }
}