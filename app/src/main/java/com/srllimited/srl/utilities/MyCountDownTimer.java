package com.srllimited.srl.utilities;

import android.content.Context;
import android.os.CountDownTimer;

import com.srllimited.srl.serverapis.RestApiCallUtil;

/**
 * Created by Lakhan.Yadav on 3/21/2018.
 */

public class MyCountDownTimer extends CountDownTimer {

    public MyCountDownTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);

    }

    @Override
    public void onTick(long millisUntilFinished) {
        //textCounter.setText(String.valueOf(millisUntilFinished));

        int progress = (int) (millisUntilFinished/1000);
        RestApiCallUtil.mProgressDialog.setMessage("Please Wait..."+progress+" secs");
        //progressBar.setProgress(progress);
    }

    @Override
    public void onFinish() {
            /*textCounter.setText("Task completed");
            progressBar.setProgress(0);*/
    }

}
