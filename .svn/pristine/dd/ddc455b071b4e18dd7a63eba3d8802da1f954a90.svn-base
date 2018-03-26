package com.srllimited.srl;

import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.internal.CarouselsTask;
import com.srllimited.srl.permission.DangerousPermResponseCallBack;
import com.srllimited.srl.permission.DangerousPermissionResponse;
import com.srllimited.srl.permission.DangerousPermissionUtils;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import android.*;
import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import static com.srllimited.srl.util.Util.PHONE_REQUES_CODE;

public class SplashActivity extends AppCompatActivity {
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH_IN_SECOND = 1;

    Context context;

    private ImageView mSplashView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        context = SplashActivity.this;

        Constants.isLogin = false;
        SharedPrefsUtil.setStringPreference(context, "splash", "true");
        new CarouselsTask(getApplicationContext());

        mSplashView = (ImageView) findViewById(R.id.splash_logo);
        mSplashView.setVisibility(View.VISIBLE);

		/* New Animation to start the Menu-Activity
        * and close this Splash-Screen after some seconds.*/
        Animation animation = new AlphaAnimation(0.01f, 1.00f);

        animation.setDuration(SPLASH_DISPLAY_LENGTH_IN_SECOND * DateUtils.SECOND_IN_MILLIS);
        animation.setAnimationListener(new Animation.AnimationListener() {

            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub
                mSplashView.setVisibility(View.VISIBLE);
            }

            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            public void onAnimationEnd(Animation animation) {
                startActivity();
            }
        });

        mSplashView.startAnimation(animation);
    }

    private void handleTaskWithUserPermission(int requestCode) {
        DangerousPermissionUtils.getPermission(context,
                new String[]{Manifest.permission.USE_FINGERPRINT}, requestCode)
                .enqueue(new DangerousPermResponseCallBack() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onComplete(final DangerousPermissionResponse permissionResponse) {
                        if (permissionResponse.isGranted()) {
                            if (permissionResponse.getRequestCode() == PHONE_REQUES_CODE) {
                                if (ActivityCompat.checkSelfPermission(context,
                                        android.Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED
                                        ) {
                                    return;
                                }

                                try {
                                    FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(Context.FINGERPRINT_SERVICE);
                                    if (!fingerprintManager.isHardwareDetected()) {
                                        // Device doesn't support fingerprint authentication
                                        Intent intent = new Intent(getApplicationContext(), SplashFourdigitPinActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                                        // User hasn't enrolled any fingerprints to authenticate with
                                        Intent intent = new Intent(getApplicationContext(), SplashFourdigitPinActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // Everything is ready for fingerprint authentication
                                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                        startActivity(intent);
                                        finish();
                                    }


                                } catch (Exception e) {

                                }

                            }
                        }
                    }
                });
    }

    private void startActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
				/* Create an Intent that will start the Login/Main Activity. */

                Class<?> activityClass = null;

                if (Validate.notEmpty(Util.getStoredUsername(context))) {
                    boolean isPinEnabled = SharedPrefsUtil.getBooleanPreference(context, "splashpin", false);

                    if (isPinEnabled) {


                        String pin = SharedPrefsUtil.getStringPreference(context, "fourdigitpin");
                        if (Validate.notEmpty(pin)) {
                            handleTaskWithUserPermission(PHONE_REQUES_CODE);


                        } else {
                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    boolean policyFirsttime = SharedPrefsUtil.getBooleanPreference(context, "policyFirsttime", true);
                    if (policyFirsttime) {
                        Intent intent = new Intent(getApplicationContext(), ConfirmPolicy.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(intent);
                        finish();
                    }
                }

            }
        }, SPLASH_DISPLAY_LENGTH_IN_SECOND * DateUtils.SECOND_IN_MILLIS);
    }

}