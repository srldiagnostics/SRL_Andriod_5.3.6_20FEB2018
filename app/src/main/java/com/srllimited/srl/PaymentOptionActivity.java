package com.srllimited.srl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ebs.android.sdk.Config;
import com.ebs.android.sdk.EBSPayment;
import com.ebs.android.sdk.PaymentRequest;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.CollectionData;
import com.srllimited.srl.data.PatientData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.data.UserData;
import com.srllimited.srl.serverapis.ApiConstants;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.AgeCalculate;
import com.srllimited.srl.util.Crypt;
import com.srllimited.srl.util.DateUtil;
import com.srllimited.srl.util.JSONParser;
import com.srllimited.srl.util.Log;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.StringUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.util.Year;
import com.srllimited.srl.utilities.AppDataBaseHelper;
import com.srllimited.srl.widget.RoundCornerProgressView;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PaymentOptionActivity extends MenuBaseActivity implements View.OnClickListener {
    private static final int ACC_ID = 5653;// Provided by EBS
    private static final String SECRET_KEY = "62cecb5e63339feb50bd1886dfa53eea";// Provided by EBS
    private static final double PER_UNIT_PRICE = 1.00;
    public static Context context;
    public static Activity payopt;
    //	int count = 0;
    public static String orderID = "";
    public static Activity payoption;
    private static String HOST_NAME = "";
    private final Handler mHandler = new Handler();
    Button next;
    int isChecked = 0;
    RadioGroup payment_radio_group;
    //String orderId;
    TextView progress_count_text, progress_text, total_amount;
    AppDataBaseHelper appDb = new AppDataBaseHelper(this);
    UserData _userAppData;
    String amt = "";
    //----EBS-------
    Double amount;
    ArrayList<HashMap<String, String>> custom_post_parameters;
    double totalamount;
    AlertDialog alert;
    String ageresult = "";
    private RoundCornerProgressView mProgressView;
    private WebView webView;
    private String paystatus = "";
    private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>() {
        @Override
        public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData) {

            switch (request.getReferralCode()) {

                case GET_Paytm_checksum:
                    //paytmDetailsAccess("");
                    break;
                case PAY:
                    try {

                        String orderId = serverResponseData.getFullData().getString("data");
                        orderID = orderId;
                        SharedPrefsUtil.setStringPreference(context, "orderid", orderId + "");
                    } catch (Exception e) {
                    }
                    Intent intent = null;
                    switch (isChecked) {
                        case 0:
                            Toast.makeText(context, "Please select payment mode", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Util.killOrderConfirm();
                            intent = new Intent(PaymentOptionActivity.this, OrderConfirmationActivity.class);
                            break;
                        case 2:
                            Util.killOrderConfirm();
                            intent = new Intent(PaymentOptionActivity.this, OrderConfirmationActivity.class);

                            break;
                        case 3:
                            Util.killOrderConfirm();
                            intent = new Intent(PaymentOptionActivity.this, OrderConfirmationActivity.class);

                            break;
                        case 4:
                            //	paytmDetailsAccess();
                            getPaytmChecksumDetailsAccess1();
                            break;
                        case 5:
                            try {
                                callEbsKit(PaymentOptionActivity.this);
                            } catch (Exception e) {

                            }
                            break;
                        case 6:
                            Util.killOrderConfirm();
                            intent = new Intent(PaymentOptionActivity.this, OrderConfirmationActivity.class);

                            break;

                    }
                    if (intent != null) {
                        startActivity(intent);
                    }

                    break;
                case GET_PAYTM_STATUS:
                    if (serverResponseData != null) {
                        try {
                            JSONObject jsonObject = serverResponseData.getFullData();
                            Log.d("json", jsonObject + "");
                            String txnid = jsonObject.getString("TXNID");
                            String txndate = jsonObject.getString("TXNDATE");
                            String paymode = jsonObject.getString("PAYMENTMODE");
                            String Amount = jsonObject.getString("TXNAMOUNT");
                            String status = jsonObject.getString("STATUS");
                            String banktxnid = jsonObject.getString("BANKTXNID");
                            paystatus = status;

                            txndate = DateUtil.paytime(txndate) + "";
                            if (status != null && status.equalsIgnoreCase("TXN_SUCCESS")) {
                                sendRequest(ApiRequestHelper.getPaySuccess(context, PaymentOptionActivity.orderID,
                                        txnid, txndate, Amount, banktxnid, paymode, "", "Success", "PAYTM"));
                            } else {
                                if (!banktxnid.equalsIgnoreCase("")) { //replace banktxnid to TXNID
                                    sendRequest(ApiRequestHelper.getPaySuccess(context, PaymentOptionActivity.orderID,
                                            txnid, txndate, Amount, banktxnid, paymode, "", "Failed", "PAYTM"));
                                } else {

                                    sendRequest(ApiRequestHelper.getPaySuccess(context, PaymentOptionActivity.orderID,
                                            txnid, txndate, Amount, txnid, paymode, "", "Failed", "PAYTM"));
                                }
                                setError("ORDERID:   " + PaymentOptionActivity.orderID + "\n \nResponse Msg : "
                                        + jsonObject.getString("RESPMSG") + "\n\nTXTAMOUNT :  "
                                        + jsonObject.getString("TXNAMOUNT"));
                            }
                        } catch (Exception e) {

                        }

                    }
                    break;

                case GET_PAY_SUCCESS:
                    if (paystatus != null && paystatus.equalsIgnoreCase("TXN_SUCCESS")) {
                        Toast.makeText(getApplicationContext(), "Payment Transaction is successful ", Toast.LENGTH_LONG)
                                .show();
                        Util.killOrderConfirm();
                        Intent intenti = new Intent(PaymentOptionActivity.this, OrderConfirmationActivity.class);
                        startActivity(intenti);
                        finish();

                    } else {
                        //  setError("");
                    }
                    break;
            }
        }

        @Override
        public void onResponseError(final ApiRequest request, final Exception error) {
            switch (request.getReferralCode()) {

                case PAY:

                    SharedPrefsUtil.setStringPreference(context, "promocode", "");
                    SharedPrefsUtil.setStringPreference(context, "promoDiscountAmt", "");
                    break;
            }
        }
    };

    public static String getSecureKeyHashGenerated(HashMap<String, String> hashpostvalues) {
        String appendGeneratedString = "";
        String secureKey = PaymentRequest.getInstance().getSecureKey();
        String generatedhash = null;
        ArrayList sortedKeys = new ArrayList(hashpostvalues.keySet());
        Collections.sort(sortedKeys);

        for (int e = 0; e < hashpostvalues.size(); ++e) {
            if (((String) hashpostvalues.get(sortedKeys.get(e))).toString().trim().length() > 0) {
                appendGeneratedString = appendGeneratedString + ((String) sortedKeys.get(e)).toUpperCase(Locale.ENGLISH)
                        + "=" + (String) hashpostvalues.get(sortedKeys.get(e));
            }
        }

        appendGeneratedString = appendGeneratedString + secureKey;

        try {
            generatedhash = Crypt.SHA1(appendGeneratedString).toUpperCase(Locale.ENGLISH);
        } catch (NoSuchAlgorithmException var6) {
            var6.printStackTrace();
        } catch (UnsupportedEncodingException var7) {
            var7.printStackTrace();
        }

        return generatedhash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.addContentView(R.layout.payment_option);
        context = PaymentOptionActivity.this;

        payoption = this;
        next = (Button) findViewById(R.id.next);

        header_loc_name.setText("Payment Option");
        header_loc_name.setEnabled(false);
        total_amount = (TextView) findViewById(R.id.total_amount);
        progress_text = (TextView) findViewById(R.id.progress_text);
        progress_count_text = (TextView) findViewById(R.id.progress_count_text);
        payment_radio_group = (RadioGroup) findViewById(R.id.payment_radio_group);

        progress_text.setText(getResources().getString(R.string.proceed));
        progress_count_text.setText(getResources().getString(R.string.progress6));

        mProgressView = (RoundCornerProgressView) findViewById(R.id.rcpv_progress_view);
        mProgressView.setProgress(0);
        mProgressView.setProgress(mProgressView.getProgress() + 100);

        progress_text.setOnClickListener(this);

        amt = SharedPrefsUtil.getStringPreference(context, "totalamout");

        calculateTotalAmount(amt);
        if (Validate.notEmpty(amt)) {
            total_amount.setText("\u20B9" + " " + amt + "");
        }

        setProgress(100, true);

        payment_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final RadioGroup radioGroup, final int i) {
                switch (i) {
                    case R.id.credit_debit_pay:
                        isChecked = 1;
                        break;

                    case R.id.netpay:

                        isChecked = 2;
                        break;

                    case R.id.cashon_delivery:
                        isChecked = 3;
                        break;

                    case R.id.paytm:
                        isChecked = 4;
                        //						count = 1;

                        break;

                    case R.id.ebs:
                        isChecked = 5;

                        //						callEbsKit(PaymentOptionActivity.this);
                        break;

                    case R.id.mobikwik:
                        isChecked = 6;
                        //						setMobikwik();
                        break;
                }
            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progress_text.setEnabled(true);
    }

	/*private void initOrderId()
    {
		Random r = new Random(System.currentTimeMillis());
		orderId = "ORDER" + (1 + r.nextInt(2)) * 10000
				+ r.nextInt(10000);
	}*/

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.progress_text:

                if (isChecked == 0) {
                    Log.v("test1", "click");
                    Toast.makeText(context, "Please select payment mode", Toast.LENGTH_SHORT).show();
                    progress_text.setEnabled(true);
                    return;
                }
                getOrderId();
                progress_text.setEnabled(false);
                break;
        }
    }

    private void setProgress(final float progress, final boolean postDelayed) {
        if (postDelayed) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProgressView.setProgress(progress);
                }
            }, 200);
        } else {
            mProgressView.setProgress(progress);
        }
    }

    private void getPaytmChecksumDetailsAccess1() {

        //sendRequest(ApiRequestHelper.getPaytmChecksum(context));
        ChecksumAsync checksumAsync = new ChecksumAsync(PaymentOptionActivity.this, orderID, Constants.Pay_Cust_id, amt + "", mResponseListener);
        checksumAsync.execute();
    }

    public void paytmDetailsAccess(String checksum) {

        //PaytmPGService Service = PaytmPGService.getStagingService(); // Test
        PaytmPGService Service = PaytmPGService.getProductionService(); //Live
        Map<String, String> paramMap = new HashMap<String, String>();

        paramMap.put("ORDER_ID", orderID);
        paramMap.put("MID", getString(R.string.sample_merchant_id_staging));
        paramMap.put("CUST_ID", Constants.Pay_Cust_id);
        paramMap.put("CHANNEL_ID", getString(R.string.sample_channel_id));
        paramMap.put("INDUSTRY_TYPE_ID", getString(R.string.sample_industry_type_id));
        paramMap.put("WEBSITE", getString(R.string.sample_website));
        paramMap.put("TXN_AMOUNT", amt + "");
        // paramMap.put("TXN_AMOUNT", "1");

        paramMap.put("THEME", getString(R.string.sample_theme));
        paramMap.put("EMAIL", getString(R.string.sample_cust_email_id));
        paramMap.put("MOBILE_NO", getString(R.string.sample_cust_mobile_no));
//https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=ORDER0000000001
        String call_url="https://securegw.paytm.in/theia/paytmCallback?ORDER_ID="+orderID;
        //paramMap.put("CALLBACK_URL", "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp");
        paramMap.put( "CALLBACK_URL" , call_url);
        //paramMap.put( "CHECKSUMHASH" , "w2QDRMgp1/BNdEnJEAPCIOmNgQvsi+BhpqijfM9KvFfRiPmGSt3Ddzw+oTaGCLneJwxFFq5mqTMwJXdQE2EzK4px2xruDqKZjHupz9yXev4=");
        paramMap.put("CHECKSUMHASH", checksum);
        paramMap.put("REQUEST_TYPE", "DEFAULT");
        PaytmOrder Order = new PaytmOrder(paramMap);

		/*PaytmMerchant Merchant = new PaytmMerchant("http://220.226.188.70/paytm/paytmCheckSumGenerator.jsp",
				"http://220.226.188.70/paytm/paytmCheckSumVerify.jsp");*/
        //		         PaytmMerchant Merchant = new PaytmMerchant(
        //				"https://pguat.paytm.com/merchant-chksum/ChecksumGenerator",
        //				"https://pguat.paytm.com/merchant-chksum/ValidateChksum");

        //Service.initialize(Order, Merchant, null);

        Service.initialize(Order, null);
        Service.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback() {
            @Override
            public void onTransactionResponse(Bundle bundle) {
                //paytmSendStatus();


                if (bundle != null) {
                    try {
                        //JSONObject jsonObject = bundle.ge;
                        //Log.d("json", jsonObject + "");
						/*if (bundle.get("RESPCODE").toString().equalsIgnoreCase("14112")) {
							setError(bundle.get("RESPMSG").toString());
						} else {*/

                        String banktxnid = "";
                        String paymode = "";
                        String txndate = "";
                        String status = bundle.get("STATUS").toString();
                        String txnid = bundle.get("TXNID").toString();
                        String Amount = bundle.get("TXNAMOUNT").toString();
                        paystatus = status;
                        if (status.equalsIgnoreCase("TXN_FAILURE")) {
                            //String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                            Calendar c = Calendar.getInstance();
                            System.out.println("Current time =&gt; " + c.getTime());

                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String formattedDate = df.format(c.getTime());
                            banktxnid = bundle.get("TXNID").toString();
                            paymode = "ONLINE";
                            txndate = formattedDate;
                        } else {
                            banktxnid = bundle.get("BANKTXNID").toString();
                            paymode = bundle.get("PAYMENTMODE").toString();
                            txndate = bundle.get("TXNDATE").toString();
                        }


                        txndate = DateUtil.paytime(txndate) + "";
                        if (status != null && status.equalsIgnoreCase("TXN_SUCCESS")) {
                            sendRequest(ApiRequestHelper.getPaySuccess(context, PaymentOptionActivity.orderID,
                                    txnid, txndate, Amount, banktxnid, paymode, "", "Success", "PAYTM"));
                        } else {
                            if (!banktxnid.equalsIgnoreCase("")) { //replace banktxnid to TXNID
                                sendRequest(ApiRequestHelper.getPaySuccess(context, PaymentOptionActivity.orderID,
                                        txnid, txndate, Amount, banktxnid, paymode, "", "Failed", "PAYTM"));
                            } else {

                                sendRequest(ApiRequestHelper.getPaySuccess(context, PaymentOptionActivity.orderID,
                                        txnid, txndate, Amount, txnid, paymode, "", "Failed", "PAYTM"));
                            }
                            setError("ORDERID:   " + PaymentOptionActivity.orderID + "\n \nResponse Msg : "
                                    + bundle.get("RESPMSG").toString() + "\n\nTXTAMOUNT :  "
                                    + bundle.get("TXNAMOUNT").toString());
                        }
                        //}
                    } catch (Exception e) {

                    }

                }
            }

            @Override
            public void networkNotAvailable() {

            }

            @Override
            public void clientAuthenticationFailed(String s) {

            }

            @Override
            public void someUIErrorOccurred(String s) {

            }

            @Override
            public void onErrorLoadingWebPage(int i, String s, String s1) {

            }

            @Override
            public void onBackPressedCancelTransaction() {

            }

            @Override
            public void onTransactionCancel(String s, Bundle bundle) {
                paytmSendStatus();
            }
        });
		/*Service.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback()
		{
			@Override
			public void someUIErrorOccurred(String inErrorMessage)
			{
				// Some UI Error Occurred in Payment Gateway Activity.
				// // This may be due to initialization of views in
				// Payment Gateway Activity or may be due to //
				// initialization of webview.
				// Error Message details
				// the error occurred.
			}

			@Override
			public void onTransactionSuccess(Bundle inResponse)
			{

				//sendRequest(ApiRequestHelper.getPaySuccess(orderID, PaymentId, DateCreated, Amount, MerchantRefNo, PaymentMode, ipaddress, PaymentStatus, "PAYTM"));

				paytmSendStatus();
			}

			@Override
			public void onTransactionFailure(String inErrorMessage, Bundle inResponse)
			{
				paytmSendStatus();
				//                        Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
			}

			@Override
			public void networkNotAvailable()
			{
				// If network is not
				// available, then this
				// method gets called.
			}

			@Override
			public void clientAuthenticationFailed(String inErrorMessage)
			{
				// This method gets called if client authentication
				// failed. // Failure may be due to following reasons //
				// 1. Server error or downtime. // 2. Server unable to
				// generate checksum or checksum response is not in
				// proper format. // 3. Server failed to authenticate
				// that client. That is value of payt_STATUS is 2. //
				// Error Message describes the reason for failure.
			}

			@Override
			public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl)
			{

			}

			// had to be added: NOTE
			@Override
			public void onBackPressedCancelTransaction()
			{
				// TODO Auto-generated method stub
			}

		});*/

    }

    private void paytmSendStatus() {
        sendRequest(ApiRequestHelper.getPaytmPaymentStatus(getString(R.string.sample_merchant_id_staging), orderID));
        //        sendRequest(ApiRequestHelper.getPaytmPaymentStatus(getString(R.string.sample_merchant_id_staging), "QD000026"));

    }

    @Override
    protected void onStart() {
        super.onStart();
        //initOrderId();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void sendRequest(ApiRequest request) {

        ApiRequestManager.getInstance().sendRequestWithoutProgress(this, request, mResponseListener);
    }

    private void calculateTotalAmount(String amt) {

        try {
            if (Validate.notEmpty(amt)) {

                double quantity = Double.valueOf(amt);
                totalamount = quantity * PER_UNIT_PRICE;

            } else {
                totalamount = 0.00;
            }
        } catch (Exception e) {

        }
    }

    //
    private void callEbsKit(PaymentOptionActivity buyProduct) {

        PaymentRequest.getInstance().setTransactionAmount(Util.getIntegerToString(/*1.0*/totalamount + ""));

        PaymentRequest.getInstance().setAccountId(ACC_ID);

        PaymentRequest.getInstance().setPage_id("8113");
        // Reference No
        PaymentRequest.getInstance().setReferenceNo(orderID);
        PaymentRequest.getInstance().setReturnUrl("http://220.226.188.70/ebs/response.jsp");
        //		/** Mandatory */
        PaymentRequest.getInstance().setBillingEmail("test@testmail.com");
        //		/** Mandatory */
        PaymentRequest.getInstance().setFailureid("1");
        //		// Currency
        PaymentRequest.getInstance().setCurrency("INR");
        PaymentRequest.getInstance().setTransactionDescription(orderID);
        /** Billing Details */
        PaymentRequest.getInstance().setBillingName("Test_Name");
        /** Optional */
        PaymentRequest.getInstance().setBillingAddress("North Mada Street");
        /** Optional */
        PaymentRequest.getInstance().setBillingCity("Chennai");
        /** Optional */
        PaymentRequest.getInstance().setBillingPostalCode("600019");
        /** Optional */
        PaymentRequest.getInstance().setBillingState("Tamilnadu");
        /** Optional */
        PaymentRequest.getInstance().setBillingCountry("IND");
        /** Optional */
        PaymentRequest.getInstance().setBillingPhone("01234567890");
        /** Optional */
        /** Shipping Details */
        PaymentRequest.getInstance().setShippingName("Test_Name");
        /** Optional */
        PaymentRequest.getInstance().setShippingAddress("North Mada Street");
        /** Optional */
        PaymentRequest.getInstance().setShippingCity("Chennai");
        /** Optional */
        PaymentRequest.getInstance().setShippingPostalCode("600019");
        /** Optional */
        PaymentRequest.getInstance().setShippingState("Tamilnadu");
        /** Optional */
        PaymentRequest.getInstance().setShippingCountry("IND");
        /** Optional */
        PaymentRequest.getInstance().setShippingEmail("test@testmail.com");
        /** Optional */
        PaymentRequest.getInstance().setShippingPhone("01234567890");
        /** Optional */
        PaymentRequest.getInstance().setLogEnabled("1");

        /** Optional */
        PaymentRequest.getInstance().setHidePaymentOption(false);

        /** Optional */
        PaymentRequest.getInstance().setHideCashCardOption(false);

        /** Optional */
        PaymentRequest.getInstance().setHideCreditCardOption(false);

        /** Optional */
        PaymentRequest.getInstance().setHideDebitCardOption(false);

        /** Optional */
        PaymentRequest.getInstance().setHideNetBankingOption(false);

        /** Optional */
        PaymentRequest.getInstance().setHideStoredCardOption(false);

        //		/**
        //		 * Initialise parameters for dyanmic values sending from merchant
        //		 */

        custom_post_parameters = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hashpostvalues = new HashMap<String, String>();
        hashpostvalues.put("account_details", "saving");
        hashpostvalues.put("merchant_type", "gold");
        custom_post_parameters.add(hashpostvalues);

        PaymentRequest.getInstance().setCustomPostValues(custom_post_parameters);
        /** Optional-Set dyanamic values */

        // PaymentRequest.getInstance().setFailuremessage(getResources().getString(R.string.payment_failure_message));
        HOST_NAME = "srl.in";
        EBSPayment.getInstance().init(buyProduct, ACC_ID, "ebskey", Config.Mode.ENV_LIVE,
                Config.Encryption.ALGORITHM_MD5, HOST_NAME);

    }

    private void setMobikwik() {
        Util.killMobikwik();
        Intent intent = new Intent(PaymentOptionActivity.this, MobikwikActivity.class);
        startActivity(intent);
        //		sendRequest(ApiRequestHelper
        //				            .getMobikiwik("ritz@gmail.com", "345", "9704683480", "abcd", "MBK9002", "snapdeal.com", "http://www.snapdeal.com/mobikwikpaymentresponse.jsp", "true",
        //				                          "1", "ju6tygh7u7tdg554k098ujd5468o"));
    }

    private void getOrderId() {

        String cityid = SharedPrefsUtil.getStringPreference(context, "selectedcityid");
        PatientData patientData1 = new PatientData();

        try {
            String getStoredData = SharedPrefsUtil.getStringPreference(context, Constants.sharedPreferencePatientData);
            patientData1 = (PatientData) StringUtil.stringToObject(getStoredData);

        } catch (Exception e) {

        }
        CollectionData collectionData = new CollectionData();
        try {
            String getColData = SharedPrefsUtil.getStringPreference(context, Constants.sharedPreferenceCollectionData);

            collectionData = (CollectionData) StringUtil.stringToObject(getColData);

        } catch (Exception e) {

        }
        appDb = new AppDataBaseHelper(getApplicationContext());

        String username = "";
        String ptnt_code = "";
        if (Constants.isFamilySel) {
            String uname = SharedPrefsUtil.getStringPreference(context, "selectedPerson");
            _userAppData = getData(uname + "");
        } else {
            _userAppData = getData(Util.getStoredUsername(context));
        }
        try {
            if (_userAppData != null) {
                username = _userAppData.getFirst_name() + " " + _userAppData.getLast_name() + "";
            }
        } catch (Exception e) {

        }

        try {
            if (_userAppData != null) {
                ptnt_code = _userAppData.getPtnt_cd() + "";
            }
        } catch (Exception e) {

        }

        String testid = SharedPrefsUtil.getStringPreference(context, "testid");
        String disc = SharedPrefsUtil.getStringPreference(context, "disc");
        String dob = "";
        long dobdate = 0;
        try {
            dobdate = Long.valueOf(_userAppData.getDob() + "");
            dob = RestApiCallUtil.epochToDate(dobdate);
        } catch (Exception e) {

        }
        String to = "";
        long todate = 0;
        try {
            todate = Long.valueOf(collectionData.getToDate() + "");
            to = RestApiCallUtil.colepochToDate(todate);
        } catch (Exception e) {

        }

        String from = "";
        long fromdate = 0;
        try {
            fromdate = Long.valueOf(collectionData.getFromDate() + "");

            from = RestApiCallUtil.colepochToDate(fromdate);
        } catch (Exception e) {

        }

        String labid = "";
        String isLab = "";
        String cartid = SharedPrefsUtil.getStringPreference(context, "cartid");

        if (Constants.isLabOrCollection) {
            labid = SharedPrefsUtil.getStringPreference(context, "labid");
            isLab = "L";
        } else {
            labid = "";
            isLab = "H";
        }

        String paymode = "";
        String payopt = "";
        switch (isChecked) {
            case 1:
                SharedPrefsUtil.setStringPreference(context, "selectedpay", "true");
                payopt = "";
                paymode = "Offline";
                break;
            case 2:
                SharedPrefsUtil.setStringPreference(context, "selectedpay", "true");
                payopt = "CARD";
                paymode = "Offline";
                break;
            case 3:
                SharedPrefsUtil.setStringPreference(context, "selectedpay", "true");
                payopt = "CASH";
                paymode = "Offline";
                break;
            case 4:
                SharedPrefsUtil.setStringPreference(context, "selectedpay", "false");
                payopt = "PAYTM";
                paymode = "Online";
                break;
            case 5:
                SharedPrefsUtil.setStringPreference(context, "selectedpay", "false");
                payopt = "EBS";
                paymode = "Online";
                break;
            case 6:
                SharedPrefsUtil.setStringPreference(context, "selectedpay", "false");
                payopt = "MOBIKWIK";
                paymode = "Online";
                break;
        }

        String promocode = SharedPrefsUtil.getStringPreference(context, "promocode");
        String round_amount = SharedPrefsUtil.getStringPreference(context, "round");
        String promocodeamt = SharedPrefsUtil.getStringPreference(context, "promoDiscountAmt");
        if ((!promocode.equalsIgnoreCase("") && !promocodeamt.equalsIgnoreCase(""))
                || (promocode.equalsIgnoreCase("") && promocodeamt.equalsIgnoreCase(""))) {
            try {
                if (Constants.isPatientDetails) {

                    String pdob = "";

                    try {
                        pdob = patientData1.getDob();
                        if (!Validate.notEmpty(pdob)) {
                            pdob = getAge(patientData1.getYears(), patientData1.getMonths(), patientData1.getDays());
                        }
                        if (Validate.notEmpty(patientData1.getMobile())) {
                            Constants.Pay_Cust_id = patientData1.getMobile();
                        } else {

                            if (Validate.notEmpty(patientData1.getEmail())) {
                                Constants.Pay_Cust_id = patientData1.getEmail();
                            } else {
                                Constants.Pay_Cust_id = getString(R.string.sample_customer_id);
                            }
                        }
                    } catch (Exception e) {

                    }
                    // Constants.isPatientDetails
                    sendRequest(ApiRequestHelper.getPayDetails(context, "I", "", "", collectionData.getAddress(), "",
                            collectionData.getCity(), collectionData.getState(), "", collectionData.getPostalCode(), "",
                            from + "", to + "", "P", patientData1.getMobile(), testid, patientData1.getName(),
                            Util.getStoredUsername(context), round_amount + "", "A", disc, promocode, paymode, payopt,
                            "", patientData1.getFname(), patientData1.getLname(), pdob, patientData1.getGender(), "",
                            patientData1.getMobile(), patientData1.getEmail(), cartid, isLab, labid, "ANDROID",
                            ApiConstants.appVersion/*android.os.Build.VERSION.RELEASE*/));

                } else {
                    String loggedin_mobile = SharedPrefsUtil.getStringPreference(context, Constants.loggedin_mobile);
                    String loggedin_emailid = SharedPrefsUtil.getStringPreference(context, Constants.loggedin_emailid);
                    if (Validate.notEmpty(loggedin_mobile)) {
                        Constants.Pay_Cust_id = loggedin_mobile;
                    } else {
                        if (Validate.notEmpty(loggedin_emailid)) {
                            Constants.Pay_Cust_id = loggedin_emailid;
                        } else {
                            Constants.Pay_Cust_id = getString(R.string.sample_customer_id);
                        }

                    }
                    sendRequest(ApiRequestHelper.getPayDetails(context, "I", "", ptnt_code + "",
                            collectionData.getAddress(), "", collectionData.getCity(), collectionData.getState(), "",
                            collectionData.getPostalCode(), "", from + "", to + "", "P",
                            _userAppData.getMobile_no() + "", testid,
                            _userAppData.getFirst_name() + " " + _userAppData.getLast_name() + "",
                            Util.getStoredUsername(context), round_amount /*amt*/ + "", "A", disc, promocode, paymode,
                            payopt, "", _userAppData.getFirst_name() + " ", _userAppData.getLast_name() + "", dob + "",
                            _userAppData.getGender() + "", "", _userAppData.getMobile_no() + "",
                            _userAppData.getEmail_id() + "", cartid, isLab, labid, "ANDROID", ApiConstants.appVersion));

                }
            } catch (Exception e) {

            }
        } else {
            promocodeAlert("Something went wrong.Please apply promocode again and retry.!", "check promocode");

        }
    }

    private void callPayment() {
        if (Validate.notEmpty(orderID)) {
            paytmDetailsAccess("");
        } else {
            getOrderId();
        }
    }

    private UserData getData(String ptntcode) {

        UserData userData = null;
        try {
            userData = appDb.getSelectedUserDetail(ptntcode);
        } catch (Exception e) {

        }

        return userData;
        //			String selname = "";
        //			if (userData != null)
        //			{
        //				selname = userData.getFirst_name() + " " + userData.getLast_name() + "";
        //			}

        //			name.setText(selname + "");
        //			SharedPrefsUtil.setStringPreference(context, "selectedPerson", ptntcode + "");
        //
        //			SharedPrefsUtil.setStringPreference(context, "selectedName", selname + "");

    }

    private void promocodeAlert(String Msg, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle(title);
        builder.setMessage(Msg/*"Your payment couldn't be processed..."*/).setCancelable(false).setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        Util.killMyCart();
                        Intent intent = new Intent(PaymentOptionActivity.this, MyCartActivity.class);
                        //  intent.putExtra(Constants.REPEAT_ORDER,true);
                        startActivity(intent);
                        // finishStackActivity();

                    }
                });
        alert = builder.create();
        alert.show();

    }

    private void setError(String Msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
        builder.setTitle("Response Error");
        builder.setMessage(Msg/*"Your payment couldn't be processed..."*/).setCancelable(false)
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // finishStackActivity();

                    }
                }).setNegativeButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                getOrderId();
                //  paytmDetailsAccess();
                //                Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
                //                startActivity(i);
                // finish();
                dialog.dismiss();

            }
        });
        alert = builder.create();
        alert.show();

    }

    private void finishStackActivity() {

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

        if (MyOrderPatientCollectAddress.confirmPatientDetail != null) {
            MyOrderPatientCollectAddress.confirmPatientDetail.finish();
        }

        if (RegistrationScreen.registration != null) {
            RegistrationScreen.registration.finish();
        }

        if (CollectionActivity.colvisit != null) {
            CollectionActivity.colvisit.finish();
        }
        if (PaymentOptionActivity.payoption != null) {
            PaymentOptionActivity.payoption.finish();
        }

    }

    private String getAge(String ayear, String amonth, String adate) {

        try {
            Calendar calendar = Calendar.getInstance();
            int ageyear = calendar.get(Calendar.YEAR);
            int agemonth = calendar.get(Calendar.MONTH);
            int agedate = calendar.get(Calendar.DATE);

            Year birthYear = new Year(Integer.valueOf(adate), Integer.valueOf(amonth), Integer.valueOf(adate));
            Year endYear = new Year(agedate, agemonth + 1, ageyear);

            new AgeCalculate() {
                @Override
                protected void getResult(String result) {
                    System.out.println(result);
                }
            }.calculateAGE(birthYear, endYear);

            System.out.println("\n");
            Year date = new Year(agedate, agemonth + 1, ageyear);
            Year ageAt = new Year(Integer.valueOf(adate), Integer.valueOf(amonth), Integer.valueOf(adate));
            new AgeCalculate() {
                @Override
                protected void getResult(final String result) {
                    ageresult = result;

                    Log.e("agere", ageresult);
                }
            }.calculateDOB(date, ageAt);

        } catch (Exception e) {

        }

        return ageresult;
    }

    class ChecksumAsync extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;
        Context ctx;
        String url;
        String orderId, custId, billAmt;
        String CHECKSUMHASH;
        ApiResponseListener listener;

        public ChecksumAsync(Context ctx, String orderId, String custId, String billAmt, ApiResponseListener listener) {
            this.ctx = ctx;
            this.orderId = orderId;
            this.custId = custId;
            this.billAmt = billAmt;
            this.listener = listener;
        }

        @Override
        protected String doInBackground(String... params) {
            //url = "http://mysrl.in:89/Checksumcsharp/GenerateChecksum.aspx"; // Development link
            url = "http://mysrl.in:92/Checksumcsharp/GenerateChecksum.aspx";// Production link
            //SoapObject result=checkSumGeneration(getString(R.string.sample_merchant_id_staging),orderId,getString(R.string.sample_industry_type_id),getString(R.string.sample_channel_id),billAmt,custId,getString(R.string.sample_cust_mobile_no),getString(R.string.sample_cust_email_id),"DEFAULT","https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp");

            //CHECKSUMHASH=result.getName().
            //url ="http://servpro70.servpro.in/SRL_PAYTMService/webservice.asmx?op=Generate_Checksum";
            JSONParser jsonParser = new JSONParser(ctx);
            //String call_url="https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp"; //development callback_url
            String call_url="https://securegw.paytm.in/theia/paytmCallback?ORDER_ID="+orderID; //production callback_url

            String param = "ORDER_ID=" + orderId +
                    "&MID=" + getString(R.string.sample_merchant_id_staging) +
                    "&CUST_ID=" + custId +
                    "&CHANNEL_ID=" + getString(R.string.sample_channel_id) +
                    "&INDUSTRY_TYPE_ID=" + getString(R.string.sample_industry_type_id) +
                    "&WEBSITE=" + getString(R.string.sample_website) +
                    "&TXN_AMOUNT=" + billAmt +
                    "&THEME=" + getString(R.string.sample_theme) +
                    "&EMAIL=" + getString(R.string.sample_cust_email_id) +
                    "&MOBILE_NO=" + getString(R.string.sample_cust_mobile_no) +
                    "&REQUEST_TYPE=DEFAULT" +
                    "&CALLBACK_URL="+call_url;
                    //"&CALLBACK_URL=https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";
            //"&CALLBACK_URL="+call_url;


            JSONObject jsonObject = jsonParser.makeHttpRequest(url, "POST", param);
            android.util.Log.e("CheckSum result >>", jsonObject.toString());
            if (jsonObject != null) {
                android.util.Log.d("CheckSum result >>", jsonObject.toString());
                try {

                    CHECKSUMHASH = jsonObject.has("CHECKSUMHASH") ? jsonObject.getString("CHECKSUMHASH") : "";
                    android.util.Log.e("CheckSum result >>", CHECKSUMHASH);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            progressDialog.dismiss();
            //finalResult.setText(result);
            //listener.onResponse(request, CHECKSUMHASH);
            paytmDetailsAccess(CHECKSUMHASH);
        }


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(ctx,
                    "ProgressDialog", "");
        }


    }
}