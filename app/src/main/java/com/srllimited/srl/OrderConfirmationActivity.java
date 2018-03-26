package com.srllimited.srl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AFInAppEventType;
import com.appsflyer.AppsFlyerLib;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.BookTestORPackagesData;
import com.srllimited.srl.data.CollectionData;
import com.srllimited.srl.database.DatabaseHelper;
import com.srllimited.srl.database.ProductHeaderDatabase;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.StringUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sri on 12/15/2016.
 */

public class OrderConfirmationActivity extends MenuBaseActivity implements View.OnClickListener {
    public static Activity orderconfirm;
    public static Activity otpreg;
    TextView orderNoTVID, thankyou, order;
    Context context;
    DatabaseHelper db;
    ProductHeaderDatabase productHeaderDB;
    TextView track_my_order, datecommet, btnHome;
    private FirebaseAnalytics firebaseAnalytics;
    String[] content_ids = null;
    Double[] prices = null;
    int[] quintities = null;
    public static String orderid = "";
    private String loggedin_username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.addContentView(R.layout.order_confirmation_activity);
        context = OrderConfirmationActivity.this;
        orderconfirm = this;
        db = new DatabaseHelper(getApplicationContext());
        productHeaderDB = new ProductHeaderDatabase(getApplicationContext());

/////
        List<BookTestORPackagesData> bookaTestDatas = new ArrayList<>();
        bookaTestDatas = db.getAllBook_pkgs_List();
        List<BookTestORPackagesData> mBookaTestDatas = new ArrayList<>();
        for (BookTestORPackagesData bookdata : bookaTestDatas) {
            if (bookdata.isCart()) {
                mBookaTestDatas.add(bookdata);
            }
        }
        content_ids = new String[mBookaTestDatas.size()];
        prices = new Double[mBookaTestDatas.size()];
        quintities = new int[mBookaTestDatas.size()];
        for (int i = 0; i < mBookaTestDatas.size(); i++) {
            content_ids[i] = String.valueOf(mBookaTestDatas.get(i).getID());
            prices[i] = mBookaTestDatas.get(i).getPRICE();
            quintities[i] = 1;
        }
        //////
        header_loc_name.setText("Order Confirmation");
        headerlayout.setEnabled(false);
        orderNoTVID = (TextView) findViewById(R.id.orderNoTVID);
        thankyou = (TextView) findViewById(R.id.thankyou);
        datecommet = (TextView) findViewById(R.id.datecomment);
        order = (TextView) findViewById(R.id.order);
        track_my_order = (TextView) findViewById(R.id.track_my_order);
        btnHome = (TextView) findViewById(R.id.btnHome);

        //String next = "<font color='#86c867'>REW0094575</font>";
        String amount = "";
        String amt = SharedPrefsUtil.getStringPreference(context, "totalamout");
        String orderId = SharedPrefsUtil.getStringPreference(context, "orderid");
        //	String amt = SharedPrefsUtil.getStringPreference(context, "OrderConfirm");

        if (Validate.notEmpty(amt)) {
            amount = amt;
        }
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.login_credentials, 0); // 0 - for private mode
        loggedin_username = pref.getString(Constants.loggedin_username, null);
        // Obtain the Firebase Analytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, 1);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "OrderConfirmationActivity");
        //Logs an app event.
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        //Sets whether analytics collection is enabled for this app on this device.
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);

        //App Flyer
        AppsFlyerLib.getInstance().enableUninstallTracking(Constants.FCM_sender_id); // ADD THIS LINE HERE
        AppsFlyerLib.getInstance().startTracking(this.getApplication(), Constants.APP_FLYER_KEY);
        Map<String, Object> eventValue = new HashMap<String, Object>();
        Map<String, Object> orderIdValue = new HashMap<String, Object>();
        String promocode = SharedPrefsUtil.getStringPreference(context, "promocode");
        eventValue.put(AFInAppEventParameterName.REVENUE, amount);
        eventValue.put(AFInAppEventParameterName.PRICE, prices);
        //eventValue.put(AFInAppEventParameterName.DEEP_LINK, "com.srllimited.srl.OrderConfirmationActivity");
        eventValue.put(AFInAppEventParameterName.CONTENT_TYPE, "OrderConfirmation");
        eventValue.put(AFInAppEventParameterName.CONTENT_ID, content_ids/* loggedin_username*/);
        eventValue.put(AFInAppEventParameterName.CURRENCY, "RS");
        eventValue.put(AFInAppEventParameterName.COUPON_CODE, promocode);
        eventValue.put(AFInAppEventParameterName.QUANTITY, quintities);
        eventValue.put(AFInAppEventParameterName.COUPON_CODE, promocode);
        orderIdValue.put(AFInAppEventType.ORDER_ID, orderId);

        //af_revenue, af_content_type, af_content_id, af_price, af_quantity, af_currency, af_order_id, af_city

        AppsFlyerLib.getInstance().trackEvent(context, AFInAppEventType.ORDER_ID, orderIdValue);
        AppsFlyerLib.getInstance().trackEvent(context, AFInAppEventType.PURCHASE, eventValue);
        //AppsFlyerLib.getInstance().trackEvent(context, "OrderConfirm", eventValue);
        //Facebook Analytic
        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent("OrderConfirm");
        SharedPrefsUtil.setStringPreference(context, "promocode", "");
        SharedPrefsUtil.setStringPreference(context, "promoDiscountAmt", "");
        SharedPrefsUtil.setStringPreference(context, "round", "");

        try {
            if (db != null) {
                db.deleteBookATest();
            }
        } catch (Exception e) {

        }

        String thanksWish = "Thank you for making payment of ";
        String rupee = "<font color='#05b65c'>" + "₹" + "</font>";
        String Amount = "<font color='#05b65c'>" + amount + "</font>";
        //        String thankyou = Html.fromHtml(thanksWish + rupee + Amount)+"";

        String getColData = SharedPrefsUtil.getStringPreference(context, Constants.sharedPreferenceCollectionData);

        CollectionData collectionData = (CollectionData) StringUtil.stringToObject(getColData);

        String sdate = "";
        String fTime = "";
        String tTime = "";

        if (collectionData != null) {
            try {
                sdate = collectionData.getDate1() + "";
                fTime = collectionData.getTime1() + "";
                tTime = collectionData.getTime2() + "";
            } catch (Exception e) {

            }
        }

        if (Constants.isLabOrCollection) {
            thankyou.setText(Html.fromHtml(thanksWish + rupee + Amount));
            datecommet.setText("Please Make a Note of Your Lab visit." + "\n" + "Date: " + sdate + " and Time " + fTime
                    + " - " + tTime);

        } else {
            thankyou.setText(Html.fromHtml(thanksWish + rupee + Amount));
            datecommet.setText(
                    "Our Customer care executive will call you to confirm your sample collection date and time.");
        }

        String pay = SharedPrefsUtil.getStringPreference(context, "selectedpay");

        if (Validate.notEmpty(pay)) {
            if (pay.equalsIgnoreCase("true")) {
                thankyou.setVisibility(View.GONE);
            } else
                thankyou.setVisibility(View.VISIBLE);
        }

        track_my_order.setOnClickListener(this);
        btnHome.setOnClickListener(this);

        orderid = SharedPrefsUtil.getStringPreference(context, "orderid");
        SharedPrefsUtil.setStringPreference(context, "orderid", "");
        orderNoTVID.setText(getResources().getString(R.string.order_number));
        order.setText(orderid + "");
        finishStackActivity();

        navBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Util.killBook();
                Intent intent = new Intent(OrderConfirmationActivity.this, BookATestActivity.class);
                startActivity(intent);
                finish();*/
                Util.killAll();
                Util.killPayOpt();
                Intent intent1 = new Intent(OrderConfirmationActivity.this, Dashboard.class);
                startActivity(intent1);
                finish();
            }
        });

    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.track_my_order:
                Constants.isTrack = true;
                Constants.isTrackFromConfirmationScreenButton = true;
                Intent intent = new Intent(OrderConfirmationActivity.this, TrackOrderActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnHome:
                Util.killAll();
                Util.killPayOpt();
                Intent intent1 = new Intent(OrderConfirmationActivity.this, Dashboard.class);
                startActivity(intent1);
                finish();
                break;
        }
    }

    private void finishStackActivity() {
        String orderid = SharedPrefsUtil.getStringPreference(context, "orderid");

        if (Validate.notEmpty(orderid)) {
            if (MyCartActivity.mycart != null) {
                MyCartActivity.mycart.finish();
            }

            if (BookATestActivity.bookatest != null) {
                BookATestActivity.bookatest.finish();
            }

            if (AddPatientActivity.homevisit != null) {
                AddPatientActivity.homevisit.finish();
            }

            if (VisitLabsList.labvisit != null) {
                VisitLabsList.labvisit.finish();
            }

            if (CollectionActivity.colvisit != null) {
                CollectionActivity.colvisit.finish();
            }

            if (PaymentOptionActivity.payoption != null) {
                PaymentOptionActivity.payoption.finish();
            }

            if (MyOrderPatientCollectAddress.confirmPatientDetail != null) {
                MyOrderPatientCollectAddress.confirmPatientDetail.finish();
            }

            if (RegistrationScreen.registration != null) {
                RegistrationScreen.registration.finish();
            }

            if (CollectionActivity.colvisit != null) {
                CollectionActivity.colvisit.finish();
            }

            SharedPrefsUtil.setStringPreference(context, Constants.sharedPreferenceCollectionData, "");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Util.killBook();
        Util.killAll();
        Util.killPayOpt();
        Intent intent = new Intent(OrderConfirmationActivity.this, Dashboard.class);
        startActivity(intent);
        finish();
    }
}
