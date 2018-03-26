package com.srllimited.srl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.paytm.pgsdk.PaytmMerchant;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Codefyne on 04/01/2017.
 */

public class PaytmActivity extends Activity
{
	public static Activity paytm;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.paytm_merchant_actvity);
		initOrderId();
		paytm = this;
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	//This is to reload the order id: Only for the Sample App's purpose.
	@Override
	protected void onStart()
	{
		super.onStart();
		initOrderId();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	}

	private void initOrderId()
	{
		Random r = new Random(System.currentTimeMillis());
		String orderId = "ORDER" + (1 + r.nextInt(2)) * 10000 + r.nextInt(10000);
		EditText orderIdEditText = (EditText) findViewById(R.id.order_id);
		orderIdEditText.setText(orderId);
	}

	public void onStartTransaction(View view)
	{
		PaytmPGService Service = PaytmPGService.getStagingService();
		Map<String, String> paramMap = new HashMap<String, String>();
		// these are mandatory parameters

		paramMap.put("ORDER_ID", ((EditText) findViewById(R.id.order_id)).getText().toString());
		paramMap.put("MID", ((EditText) findViewById(R.id.merchant_id)).getText().toString());
		paramMap.put("CUST_ID", ((EditText) findViewById(R.id.customer_id)).getText().toString());
		paramMap.put("CHANNEL_ID", ((EditText) findViewById(R.id.channel_id)).getText().toString());
		paramMap.put("INDUSTRY_TYPE_ID", ((EditText) findViewById(R.id.industry_type_id)).getText().toString());
		paramMap.put("WEBSITE", ((EditText) findViewById(R.id.website)).getText().toString());
		paramMap.put("TXN_AMOUNT", ((EditText) findViewById(R.id.transaction_amount)).getText().toString());
		paramMap.put("THEME", ((EditText) findViewById(R.id.theme)).getText().toString());
		paramMap.put("EMAIL", ((EditText) findViewById(R.id.cust_email_id)).getText().toString());
		paramMap.put("MOBILE_NO", ((EditText) findViewById(R.id.cust_mobile_no)).getText().toString());
		paramMap.put( "CALLBACK_URL" , "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID=ORDER0000000001");
		//paramMap.put( "CHECKSUMHASH" , "w2QDRMgp1/BNdEnJEAPCIOmNgQvsi+BhpqijfM9KvFfRiPmGSt3Ddzw+oTaGCLneJwxFFq5mqTMwJXdQE2EzK4px2xruDqKZjHupz9yXev4=");
		paramMap.put("CHECKSUMHASH","");
		PaytmOrder Order = new PaytmOrder(paramMap);

		/*PaytmMerchant Merchant = new PaytmMerchant("https://pguat.paytm.com/paytmchecksum/paytmCheckSumGenerator.jsp",
				"https://pguat.paytm.com/paytmchecksum/paytmCheckSumVerify.jsp");*/

		Service.initialize(Order, null);

		Service.startPaymentTransaction(this, true, true, new PaytmPaymentTransactionCallback()
		{
			@Override
			public void someUIErrorOccurred(String inErrorMessage)
			{
				// Some UI Error Occurred in Payment Gateway Activity.
				// // This may be due to initialization of views in
				// Payment Gateway Activity or may be due to //
				// initialization of webview. // Error Message details
				// the error occurred.
			}

			/*@Override
			public void onTransactionSuccess(Bundle inResponse)
			{

				Toast.makeText(getApplicationContext(), "Payment Transaction is successful ", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onTransactionFailure(String inErrorMessage, Bundle inResponse)
			{

				Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
			}*/

			@Override
			public void onTransactionResponse(Bundle bundle) {
				Toast.makeText(getApplicationContext(), "Payment Transaction is successful ", Toast.LENGTH_LONG).show();

			}

			@Override
			public void networkNotAvailable()
			{ // If network is not
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

			@Override
			public void onTransactionCancel(String s, Bundle bundle) {
				Toast.makeText(getBaseContext(), "Payment Transaction Failed ", Toast.LENGTH_LONG).show();

			}

		});
	}
}