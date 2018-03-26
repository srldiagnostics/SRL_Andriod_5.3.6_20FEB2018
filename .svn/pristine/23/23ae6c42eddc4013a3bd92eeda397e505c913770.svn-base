package com.srllimited.srl;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.ebs.android.sdk.PaymentActivity;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.util.DateUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class PaymentSuccessActivity extends Activity
{
	public static Activity paysuccess;
	String payment_id;
	String PaymentId;
	String AccountId;
	String MerchantRefNo;
	String Amount;
	String DateCreated;
	String Description;
	String Mode;
	String IsFlagged;
	String BillingName;
	String BillingAddress;
	String BillingCity;
	String BillingState;
	String BillingPostalCode;
	String BillingCountry;
	String BillingPhone;
	String BillingEmail;
	String DeliveryName;
	String DeliveryAddress;
	String DeliveryCity;
	String DeliveryState;
	String DeliveryPostalCode;
	String DeliveryCountry;
	String DeliveryPhone;
	String PaymentStatus;
	String PaymentMode;
	String SecureHash;
	LinearLayout tryAgainLayout;

	Button btn_payment_success;

	Context context;
	AlertDialog alert;
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData mserverResponseData)
		{

			switch (request.getReferralCode())
			{

				case GET_PAY_SUCCESS:

					if (PaymentStatus.equalsIgnoreCase("failed"))
					{
						setError();
					}
					else
					{
						Intent intent = new Intent(PaymentSuccessActivity.this, OrderConfirmationActivity.class);
						startActivity(intent);
						finish();
					}

					break;
			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{
			//            if (PaymentStatus.equalsIgnoreCase("failed")) {
			//                setError();
			//            } else {
			//                Intent intent = new Intent(PaymentSuccessActivity.this, OrderConfirmationActivity.class);
			//                startActivity(intent);
			//                finish();
			//            }
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_payment_success);

		context = PaymentSuccessActivity.this;
		paysuccess = this;
		tryAgainLayout = (LinearLayout) findViewById(R.id.ll_button);
		btn_payment_success = (Button) findViewById(R.id.btn_payment_success);
		Intent intent = getIntent();

		payment_id = intent.getStringExtra("payment_id");
		System.out.println("Success and Failure response to merchant app..." + " " + payment_id);

		getJsonReport();

		btn_payment_success.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
				startActivity(i);
				finish();

			}
		});

		Button btn_retry = (Button) findViewById(R.id.btn_retry);
		btn_retry.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{

				Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
				PaymentSuccessActivity.this.finish();
				startActivity(i);

			}
		});
		Button btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_cancel.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				PaymentSuccessActivity.this.finish();
			}
		});

	}

	private void getJsonReport()
	{
		String response = payment_id;

		//        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG).show();

		JSONObject jObject;
		try
		{
			jObject = new JSONObject(response.toString());
			PaymentId = jObject.getString("PaymentId");
			AccountId = jObject.getString("AccountId");
			MerchantRefNo = jObject.getString("MerchantRefNo");
			Amount = jObject.getString("Amount");
			DateCreated = jObject.getString("DateCreated");
			Description = jObject.getString("Description");
			Mode = jObject.getString("Mode");
			IsFlagged = jObject.getString("IsFlagged");
			BillingName = jObject.getString("BillingName");
			BillingAddress = jObject.getString("BillingAddress");
			BillingCity = jObject.getString("BillingCity");
			BillingState = jObject.getString("BillingState");
			BillingPostalCode = jObject.getString("BillingPostalCode");
			BillingCountry = jObject.getString("BillingCountry");
			BillingPhone = jObject.getString("BillingPhone");
			BillingEmail = jObject.getString("BillingEmail");
			DeliveryName = jObject.getString("DeliveryName");
			DeliveryAddress = jObject.getString("DeliveryAddress");
			DeliveryCity = jObject.getString("DeliveryCity");
			DeliveryState = jObject.getString("DeliveryState");
			DeliveryPostalCode = jObject.getString("DeliveryPostalCode");
			DeliveryCountry = jObject.getString("DeliveryCountry");
			DeliveryPhone = jObject.getString("DeliveryPhone");
			PaymentStatus = jObject.getString("PaymentStatus");
			PaymentMode = jObject.getString("PaymentMode");
			SecureHash = jObject.getString("SecureHash");
			System.out.println("paymentid_rsp" + PaymentId);

			if (PaymentStatus.equalsIgnoreCase("failed"))
			{
				tryAgainLayout.setVisibility(View.VISIBLE);
				btn_payment_success.setVisibility(View.GONE);
			}
			else
			{
				btn_payment_success.setVisibility(View.VISIBLE);
				tryAgainLayout.setVisibility(View.GONE);
			}

			TableLayout table_payment = (TableLayout) findViewById(R.id.table_payment);
			ArrayList<String> arrlist = new ArrayList<String>();
			arrlist.add("PaymentId");
			arrlist.add("MerchantRefNo");
			arrlist.add("Amount");
			arrlist.add("DateCreated");
			arrlist.add("Description");
			arrlist.add("PaymentStatus");
			arrlist.add("PaymentMode");

			ArrayList<String> arrlist1 = new ArrayList<String>();
			arrlist1.add(PaymentId);
			arrlist1.add(MerchantRefNo);
			arrlist1.add(Amount);
			arrlist1.add(DateCreated);
			arrlist1.add(Description);
			arrlist1.add(PaymentStatus);
			arrlist1.add(PaymentMode);

			for (int i = 0; i < arrlist.size(); i++)
			{
				TableRow row = new TableRow(this);

				TextView textH = new TextView(this);
				TextView textC = new TextView(this);
				TextView textV = new TextView(this);

				textH.setText(arrlist.get(i));
				textC.setText(":  ");
				textV.setText(arrlist1.get(i));
				textV.setTypeface(null, Typeface.BOLD);

				row.addView(textH);
				row.addView(textC);
				row.addView(textV);

				table_payment.addView(row);
			}
			DateCreated = DateUtil.ebstime(DateCreated) + "";
			if (PaymentStatus.equalsIgnoreCase("failed"))
				sendRequest(ApiRequestHelper.getPaySuccess(context, PaymentOptionActivity.orderID, PaymentId,
						DateCreated, Amount, PaymentId, PaymentMode, "", "Failed", "EBS"));
			else
			{
				sendRequest(ApiRequestHelper.getPaySuccess(context, PaymentOptionActivity.orderID, PaymentId,
						DateCreated, Amount, PaymentId, PaymentMode, "", "Success", "EBS"));
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequestWithoutProgress(this, request, mResponseListener);
	}

	private void setError()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		builder.setMessage("Your payment couldn't be processed.").setCancelable(false)
				.setPositiveButton("Cancel", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int id)
					{
						//                        dialog.dismiss();
						finish();

					}
				}).setNegativeButton("Try Again", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int id)
					{
						Intent i = new Intent(getApplicationContext(), PaymentActivity.class);
						PaymentSuccessActivity.this.finish();
						startActivity(i);
						dialog.dismiss();

					}
				});
		alert = builder.create();
		alert.show();

	}

}