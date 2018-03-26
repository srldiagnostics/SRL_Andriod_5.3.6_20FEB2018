package com.srllimited.srl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.ReportsData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.data.ServerResponseData2;
import com.srllimited.srl.database.ReportsDatabase;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Ruchi Tiwari on 12/13/2016.
 */

public class MyReports extends MenuBaseActivity implements View.OnClickListener
{
	private static final String ACC_ID = "ACC_ID";
	private static final String PRDCT_ID = "PRDCT_ID";
	private static final String PRDCT_CD = "PRDCT_CD";
	private static final String PRDCT_NAME = "PRDCT_NAME";
	private static final String ELMNT_ID = "ELMNT_ID";
	private static final String ELMNT_CD = "ELMNT_CD";
	private static final String ELMNT_NAME = "ELMNT_NAME";
	private static final String ELMNT_RSLT_TYP = "ELMNT_RSLT_TYP";

	//	@Override
	//	public void onSuccessResponse(final int referralurlcall, final ServerResponseData serverResponseData)
	//	{
	//
	//		switch (referralurlcall)
	//		{
	//			case Constants.getOrders:
	//
	//				if (serverResponseData != null && serverResponseData.getFullData() != null)
	//				{
	//					setOrdersList(serverResponseData);
	//				}
	//				break;
	//		}
	//	}
	private static final String ELMNT_MIN_RANGE = "ELMNT_MIN_RANGE";
	private static final String ELMNT_MAX_RANGE = "ELMNT_MAX_RANGE";
	private static final String ELMNT_RSLT_UNIT = "ELMNT_RSLT_UNIT";
	private static final String RSLT = "RSLT";
	private static final String P_CMMNT = "P_CMMNT";
	private static final String RANGE_VAL = "RANGE_VAL";
	private static final String PRNT_RNG_TXT = "PRNT_RNG_TXT";
	private static final String RSLT_NORMAL_FLAG = "RSLT_NORMAL_FLAG";
	private static final String SR_NO = "SR_NO";
	private static final String PARENT_PRDCT_ID = "PARENT_PRDCT_ID";
	private static final String PARENT_PRDCT_CD = "PARENT_PRDCT_CD";
	private static final String PARENT_PRDCT_NAME = "PARENT_PRDCT_NAME";
	private static final String ACC_DT = "ACC_DT";
	private static final String PTNT_CD = "PTNT_CD";
	private static final String GENERIC_NAME = "GENERIC_NAME";
	private static final String CPT_CODE = "CPT_CODE";
	public static Activity myreport;
	Button loginwithUserIDBTNID, viewreportWithFirstNameBTNID, viewreportWithMobileNoBTNID;
	Context context;
	TextView mHeader_name;
	List<ReportsData> mReportsDatas = new ArrayList<>();
	ReportsDatabase reportDB;
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			switch (request.getReferralCode())
			{
				case REPORTS:
				{
					if (serverResponseData != null && serverResponseData.getObjectData() != null)
					{
						try
						{
							JSONObject jobj = serverResponseData.getObjectData().getJSONObject("create");

							if (jobj != null)
							{
								ServerResponseData2 serverResponseData2 = new ServerResponseData2();
								serverResponseData2.setCode(100);
								serverResponseData2.setMsg("");
								serverResponseData2.setData(jobj.getJSONArray("RSLT"));

								if (serverResponseData2 != null && serverResponseData2.getData() != null)
								{
									setReportsList(serverResponseData2);
								}
							}

						}
						catch (Exception e)
						{

						}

					}
				}
					break;
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
		super.addContentView(R.layout.myreports);

		context = MyReports.this;
		reportDB = new ReportsDatabase(getApplicationContext());

		header_loc_name.setText("My Report");
		header_loc_name.setEnabled(false);
		loginwithUserIDBTNID = (Button) findViewById(R.id.loginwithUserIDBTNID);
		viewreportWithFirstNameBTNID = (Button) findViewById(R.id.viewreportWithFirstNameBTNID);
		viewreportWithMobileNoBTNID = (Button) findViewById(R.id.viewreportWithMobileNoBTNID);

		loginwithUserIDBTNID.setOnClickListener(MyReports.this);
		viewreportWithFirstNameBTNID.setOnClickListener(MyReports.this);
		viewreportWithMobileNoBTNID.setOnClickListener(MyReports.this);
		mReportsDatas = reportDB.getReportsData(Util.getStoredUsername(context));

		if (mReportsDatas != null && !mReportsDatas.isEmpty())
		{
			Log.e("already", "stored in DB");
		}
		else
		{
			sendRequest(ApiRequestHelper.getReports(context, "SHRAF2510882", "mini66", Constants.deviceID, "1",
					"ANDROID", "1", "1", System.currentTimeMillis() + ""));
		}
	}

	@Override
	public void onClick(View v)
	{
		int id = v.getId();
		switch (id)
		{
			case R.id.loginwithUserIDBTNID:
				//				Toast.makeText(getBaseContext(), "btn1", Toast.LENGTH_LONG).show();
				Util.killReportLogin();
				Intent i = new Intent(MyReports.this, ReportLoginAccessNo.class);
				startActivity(i);

				break;
			case R.id.viewreportWithFirstNameBTNID:
				Util.killReportLogin();
				//				Toast.makeText(getBaseContext(), "btn2", Toast.LENGTH_LONG).show();
				Intent i2 = new Intent(MyReports.this, ReportLoginAccessNo.class);
				startActivity(i2);
				break;
			case R.id.viewreportWithMobileNoBTNID:
				Util.killReportLogin();
				//				Toast.makeText(getBaseContext(), "btn3", Toast.LENGTH_LONG).show();
				Intent i3 = new Intent(MyReports.this, ReportLoginAccessNo.class);
				startActivity(i3);
				break;
		}
	}

	private void setReportsList(ServerResponseData2 serverResponseData)
	{

		JSONArray jArray = null;
		if (serverResponseData != null)
		{
			if (serverResponseData.getData() != null)
			{
				jArray = serverResponseData.getData();
			}
			if (jArray != null)
			{
				try
				{
					if (Validate.notNull(jArray))
					{
						mReportsDatas = new ArrayList<>();
						if (jArray != null)
						{
							for (int i = 0; i < jArray.length(); i++)
							{
								ReportsData reportsData = new ReportsData();

								reportsData.setACC_ID(jArray.getJSONObject(i).getString(ACC_ID));
								reportsData.setPRDCT_ID(jArray.getJSONObject(i).getInt(PRDCT_ID));
								reportsData.setPRDCT_CD(jArray.getJSONObject(i).getString(PRDCT_CD));
								reportsData.setPRDCT_NAME(jArray.getJSONObject(i).getString(PRDCT_NAME));
								reportsData.setELMNT_ID(jArray.getJSONObject(i).getInt(ELMNT_ID));
								reportsData.setELMNT_CD(jArray.getJSONObject(i).getString(ELMNT_CD));
								reportsData.setELMNT_NAME(jArray.getJSONObject(i).getString(ELMNT_NAME));

								reportsData.setELMNT_RSLT_TYP(jArray.getJSONObject(i).getString(ELMNT_RSLT_TYP));
								reportsData.setELMNT_MIN_RANGE(jArray.getJSONObject(i).getString(ELMNT_MIN_RANGE));
								reportsData.setPRDCT_CD(jArray.getJSONObject(i).getString(ELMNT_MAX_RANGE));
								reportsData.setELMNT_RSLT_UNIT(jArray.getJSONObject(i).getString(ELMNT_RSLT_UNIT));
								reportsData.setRSLT(jArray.getJSONObject(i).getString(RSLT));
								reportsData.setP_CMMNT(jArray.getJSONObject(i).getString(P_CMMNT));

								try
								{
									reportsData.setRANGE_VAL(jArray.getJSONObject(i).getInt(RANGE_VAL));
								}
								catch (Exception e)
								{
									reportsData.setRANGE_VAL(0);
								}

								reportsData.setPRNT_RNG_TXT(jArray.getJSONObject(i).getString(PRNT_RNG_TXT));
								reportsData.setRSLT_NORMAL_FLAG(jArray.getJSONObject(i).getString(RSLT_NORMAL_FLAG));
								reportsData.setSR_NO(jArray.getJSONObject(i).getInt(SR_NO));
								reportsData.setPARENT_PRDCT_ID(jArray.getJSONObject(i).getInt(PARENT_PRDCT_ID));
								reportsData.setPARENT_PRDCT_CD(jArray.getJSONObject(i).getString(PARENT_PRDCT_CD));
								reportsData.setPARENT_PRDCT_NAME(jArray.getJSONObject(i).getString(PARENT_PRDCT_NAME));
								reportsData.setACC_DT(jArray.getJSONObject(i).getLong(ACC_DT));

								reportsData.setPTNT_CD(
										Util.getStoredUsername(context)/*jArray.getJSONObject(i).getString(PTNT_CD)*/);
								reportsData.setGENERIC_NAME(jArray.getJSONObject(i).getString(GENERIC_NAME));
								reportsData.setCPT_CODE(jArray.getJSONObject(i).getString(CPT_CODE));

								reportDB.createReports(reportsData);

								//								mOrdersHistoryDatas.add(ordersHistoryData);

							}
						}
					}
				}
				catch (JSONException e)
				{

				}
			}

			mReportsDatas = reportDB.getReportsData(Util.getStoredUsername(context));
			//			if (mOrdersHistoryDatas != null)
			//			{
			//

			//
			//			}
		}
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

}
