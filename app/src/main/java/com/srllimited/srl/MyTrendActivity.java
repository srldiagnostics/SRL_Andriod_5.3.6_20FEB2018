package com.srllimited.srl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.srllimited.srl.adapters.TrendsAdapter;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.AccessionListData;
import com.srllimited.srl.data.ReportsData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.data.ServerResponseData2;
import com.srllimited.srl.data.TrendGrapgData;
import com.srllimited.srl.data.UserData;
import com.srllimited.srl.database.ReportsDatabase;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.util.Log;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.utilities.AppDataBaseHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyTrendActivity extends MenuBaseActivity implements View.OnClickListener
{

	//report variable
	private static final String ACC_ID = "ACC_ID";
	private static final String PRDCT_ID = "PRDCT_ID";
	private static final String PRDCT_CD = "PRDCT_CD";
	private static final String PRDCT_NAME = "PRDCT_NAME";
	private static final String ELMNT_ID = "ELMNT_ID";
	private static final String ELMNT_CD = "ELMNT_CD";
	private static final String ELMNT_NAME = "ELMNT_NAME";
	private static final String ELMNT_RSLT_TYP = "ELMNT_RSLT_TYP";
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
	//trend graph variable
	private static final String PRDCT_PRDCT_ID = "PRDCT_PRDCT_ID";
	private static final String ELMNT_CD1 = "ELMNT_CD";
	private static final String ELMNT_ID1 = "ELMNT_ID";
	private static final String INSTR_ID = "INSTR_ID";
	private static final String CPT_CODE1 = "CPT_CODE";
	private static final String RANGE_VALUE = "RANGE_VALUE";
	private static final String REPORT_TYPE = "REPORT_TYPE";
	public static Activity myTrendtracker;
	AppDataBaseHelper appDb = new AppDataBaseHelper(this);

	//	ImageView pdfBTNID;
	Context context;

	LinearLayout hidePopup;

	TextView mHeader_name;

	ReportsDatabase reportDB;

	TextView login_name;
	AlertDialog alert;
	TextView name;
	List<ReportsData> mReportsDatas = new ArrayList<>();
	List<AccessionListData> storeReportsFromMap = new ArrayList<>();
	HashMap<String, List<ReportsData>> accessionList = new HashMap<>();
	ImageView confirm, cancel;
	List<UserData> userList = new ArrayList<UserData>();
	List<String> userListdata = new ArrayList<String>();
	String selectedListItem = "";
	String selectedPwd = "";
	ImageView dropdown;
	TextView popupheader;
	List<TrendGrapgData> mTrendsDatas = new ArrayList<>();
	RecyclerView.Adapter mAdapter;
	Bitmap bitmap = null;
	private ListView listView;
	private CircleImageView guestIVID;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;
	private RecyclerView mRecyclerView;
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

				case GET_GRAPHLIST:
				{
					if (serverResponseData != null && serverResponseData.getFullData() != null)
					{
						try
						{
							JSONObject jobj = serverResponseData.getFullData();
							if (jobj != null)
							{
								ServerResponseData2 serverResponseData2 = new ServerResponseData2();
								serverResponseData2.setCode(100);
								serverResponseData2.setMsg("");
								serverResponseData2.setData(serverResponseData.getFullData().getJSONArray("data"));

								if (serverResponseData2 != null && serverResponseData2.getData() != null)
								{
									setTrendGraphListData(serverResponseData2);
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

	public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels)
	{
		Bitmap result = null;
		try
		{
			result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(result);

			int color = 0xff424242;
			Paint paint = new Paint();
			Rect rect = new Rect(0, 0, 200, 200);

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawCircle(100, 100, 100, paint);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);

		}
		catch (NullPointerException e)
		{
		}
		catch (OutOfMemoryError o)
		{
		}
		return result;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.health_tracker_parent);
		context = MyTrendActivity.this;
		reportDB = new ReportsDatabase(getApplicationContext());
		appDb = new AppDataBaseHelper(getApplicationContext());
		try
		{
			mReportsDatas = reportDB.getReportsData(Util.getStoredUsername(context));
		}
		catch (Exception e)
		{

		}
		myTrendtracker = this;
		header_loc_name.setText("My Trends");
		header_loc_name.setEnabled(false);
		cancel = (ImageView) findViewById(R.id.cancel);
		confirm = (ImageView) findViewById(R.id.confirm);
		dropdown = (ImageView) findViewById(R.id.dropdown);
		listView = (ListView) findViewById(R.id.popup_list);
		popupheader = (TextView) findViewById(R.id.popup_header);
		hidePopup = (LinearLayout) findViewById(R.id.hidePopup);
		guestIVID = (CircleImageView) findViewById(R.id.guestIVID);
		login_name = (TextView) findViewById(R.id.login_name);
		name = (TextView) findViewById(R.id.name);
		login_name.setText(Util.getStoredUsername(context));
		Constants.isMember = (Util.getStoredUsername(context));
		getData(Util.getStoredUsername(context));

		//		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		//		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		//		mRecyclerView.setHasFixedSize(true);
		//
		//		mAdapter = new AccessionListAdapter(context, storeReportsFromMap);
		//		mRecyclerView.setAdapter(mAdapter);

		if (mReportsDatas != null && !mReportsDatas.isEmpty())
		{
			setAccessionList();
		}
		else
		{
			sendRequest(ApiRequestHelper.getReports(context, Util.getStoredUsername(context),
					Util.getStoredPwd(context), Constants.devicetobepassed, "1", "ANDROID", "1", "1", "0"));
		}
		/////////////////
		if (mTrendsDatas != null && !mTrendsDatas.isEmpty())
		{
			setAccessionList();
		}
		else
		{
			sendRequest(ApiRequestHelper.getGraphList(context));
		}

		getFamilyList();
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				selectedListItem = "";
				selectedListItem = userListdata.get(position);
			}
		});
		dropdown.setOnClickListener(this);
		confirm.setOnClickListener(this);
		cancel.setOnClickListener(this);
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
	}

	private void sortByName()
	{
		try
		{
			Collections.sort(storeReportsFromMap, new Comparator<AccessionListData>()
			{
				@Override
				public int compare(AccessionListData o1, AccessionListData o2)
				{
					return o1.getAcc_id().compareToIgnoreCase(o2.getAcc_id());
				}
			});
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private void setAccessionListAdapter()
	{

		if (Validate.notNull(storeReportsFromMap))
			sortByName();

		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		mRecyclerView.setHasFixedSize(true);

		mAdapter = new TrendsAdapter(context, storeReportsFromMap);
		mRecyclerView.setAdapter(mAdapter);

		mAdapter.notifyDataSetChanged();
	}

	private void setAccessionList()
	{
		accessionList = new HashMap<>();
		storeReportsFromMap = new ArrayList<>();
		///here match mReportstatus and Trendgraph data
		if (mTrendsDatas != null)
		{
			if (mReportsDatas != null)
			{
				for (int i = 0; i < mReportsDatas.size(); i++)
				{

					for (int j = 0; j < mTrendsDatas.size(); j++)
					{
						if (mTrendsDatas.get(j).getELMNT_ID() == mReportsDatas.get(i).getELMNT_ID()
								&& mTrendsDatas.get(j).getPRDCT_PRDCT_ID() == mReportsDatas.get(i).getPRDCT_ID())
						{
							Log.v("test1 ", i + "::" + j);
							Log.v("getELMNT_ID ", mTrendsDatas.get(j).getELMNT_ID() + " element : " + ""
									+ mTrendsDatas.get(j).getPRDCT_PRDCT_ID());
							if (!accessionList.containsKey(mReportsDatas.get(i).getGENERIC_NAME().toString()))
							{
								List<ReportsData> reportsDatas = new ArrayList<>();
								for (ReportsData reports : mReportsDatas)
								{
									if (reports.getGENERIC_NAME() != null && reports.getCPT_CODE() != null
											&& !reports.getCPT_CODE().equalsIgnoreCase("null")
											&& reports.getELMNT_RSLT_TYP() != null
											&& reports.getELMNT_RSLT_TYP().equalsIgnoreCase("N"))
									{
										if (reports.getGENERIC_NAME().toString()
												.equalsIgnoreCase(mReportsDatas.get(i).getGENERIC_NAME()))
										{

											reportsDatas.add(reports);
										}
									}
									else if (reports.getGENERIC_NAME() != null && reports.getCPT_CODE() != null
											&& !reports.getCPT_CODE().equalsIgnoreCase("null")
											&& reports.getELMNT_RSLT_TYP() != null
											&& reports.getELMNT_RSLT_TYP().equalsIgnoreCase("X"))
									{
										if (reports.getGENERIC_NAME().toString()
												.equalsIgnoreCase(mReportsDatas.get(i).getGENERIC_NAME()))
										{

											reportsDatas.add(reports);
										}
									}
								}

								if (reportsDatas != null && reportsDatas.size() > 0)
								{

									AccessionListData accessionListData = new AccessionListData();
									accessionListData.setAcc_id(mReportsDatas.get(i).getGENERIC_NAME().toString());
									accessionListData.setReportsDatas(reportsDatas);

									storeReportsFromMap.add(accessionListData);
								}

								accessionList.put(mReportsDatas.get(i).getGENERIC_NAME(), reportsDatas);
								//					}
							}
						}
					}

					//				if (!accessionList.containsKey(mReportsDatas.get(i).getGENERIC_NAME().toString()))
					//				{
					//					List<ReportsData> reportsDatas = new ArrayList<>();
					//					reportsDatas.add(mReportsDatas.get(i));
					//					AccessionListData accessionListData = new AccessionListData();
					//					accessionListData.setAcc_id(mReportsDatas.get(i).getGENERIC_NAME().toString());
					//
					//					storeReportsFromMap.add(accessionListData);
					//					accessionList.put(mReportsDatas.get(i).getGENERIC_NAME(), reportsDatas);
					////
					//
					//				}

					//				if (storeReportsFromMap != null)
					//				{
					//					sortByDate();
					//				}
					//				else
					//				{
					//					List<ReportsData> reportsDatas = new ArrayList<>();
					//
					//					for (ReportsData reports : mReportsDatas)
					//					{
					//						if (reports.getACC_ID() != null)
					//						{
					//							if (reports.getACC_ID().toString().equalsIgnoreCase(mReportsDatas.get(i).getACC_ID()))
					//							{
					//
					//								reportsDatas.add(reports);
					//							}
					//						}
					//					}
					//
					//					accessionList.put(mReportsDatas.get(i).getACC_ID(), reportsDatas);
					//				}

				}
				setAccessionListAdapter();
			}
			else
			{
				showSettingsAlert();
			}
		} //

	}

	private void setTrendGraphListData(ServerResponseData2 serverResponseData)
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
						mTrendsDatas = new ArrayList<>();
						if (jArray != null)
						{
							for (int i = 0; i < jArray.length(); i++)
							{
								TrendGrapgData trendGrapgData = new TrendGrapgData();

								trendGrapgData.setPRDCT_PRDCT_ID(jArray.getJSONObject(i).getInt(PRDCT_PRDCT_ID));
								trendGrapgData.setELMNT_CD(jArray.getJSONObject(i).getString(ELMNT_CD1));
								trendGrapgData.setELMNT_ID(jArray.getJSONObject(i).getInt(ELMNT_ID1));
								trendGrapgData.setINSTR_ID(jArray.getJSONObject(i).getString(INSTR_ID));
								trendGrapgData.setCPT_CODE(jArray.getJSONObject(i).getString(CPT_CODE1));

								try
								{
									trendGrapgData.setRANGE_VALUE(jArray.getJSONObject(i).getString(RANGE_VALUE));
								}
								catch (Exception e)
								{
									trendGrapgData.setRANGE_VALUE("0");

								}

								trendGrapgData.setREPORT_TYPE(jArray.getJSONObject(i).getString(REPORT_TYPE));

								mTrendsDatas.add(trendGrapgData);
								//     reportDB.createReports(reportsData);

								//								mOrdersHistoryDatas.add(ordersHistoryData);

							}
						}
					}
				}
				catch (JSONException e)
				{

				}
			}

			//   mReportsDatas = reportDB.getReportsData(Util.getStoredUsername(context));
			if (mReportsDatas != null)
			{
				setAccessionList();
			}
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
								reportsData.setELMNT_MAX_RANGE(jArray.getJSONObject(i).getString(ELMNT_MAX_RANGE));
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
										/*Util.getStoredUsername(context)*/jArray.getJSONObject(i).getString(PTNT_CD));
								reportsData.setGENERIC_NAME(jArray.getJSONObject(i).getString(GENERIC_NAME));
								reportsData.setCPT_CODE(jArray.getJSONObject(i).getString(CPT_CODE));

								//								mReportsDatas.add(reportsData);
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
			if (mReportsDatas != null)
			{
				setAccessionList();
			}
		}
	}

	public void showSettingsAlert()
	{

		AlertDialog.Builder builder = new AlertDialog.Builder(MyTrendActivity.this, AlertDialog.THEME_HOLO_LIGHT);
		builder.setMessage("No Data").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				MyTrendActivity.this.startActivity(intent);
				dialog.cancel();
				finish();
			}
		});

		alert = builder.create();
		alert.show();
	}

	@Override
	public void onClick(final View view)
	{

		Animation bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);
		switch (view.getId())
		{
			case R.id.cancel:
				hidePopup.startAnimation(bottomDown);
				hidePopup.setVisibility(View.INVISIBLE);
				break;

			case R.id.confirm:

				if (selectedListItem != null && !selectedListItem.equalsIgnoreCase("null")
						&& !selectedListItem.isEmpty())
				{

					String[] parts = selectedListItem.split("-");

					String part1 = "";
					String part2 = "";
					try
					{
						part1 = parts[0];
						part2 = parts[1];
					}
					catch (Exception e)
					{

					}

					login_name.setText(part2.trim());
					Constants.isMember = (part2.trim());
					name.setText(part1);

					if (part2.trim() != null)
					{
						mReportsDatas.clear();

						if (part2.trim().equalsIgnoreCase(Util.getStoredUsername(context)))
						{
							if (bitmap != null)
							{
								profileImageSetting(bitmap);
							}
						}
						else
						{
							//                            guestIVID.setBackgroundResource(R.color.emptycolor);
							guestIVID.setImageResource(R.drawable.guest_user_icon);

						}
						mReportsDatas = reportDB.getReportsData(part2.trim());
						setAccessionList();
					}
				}

				hidePopup.startAnimation(bottomDown);
				hidePopup.setVisibility(View.INVISIBLE);
				break;

			case R.id.dropdown:
				popupheader.setText("Select Member");
				getFamilyList();
				setPopupListAdapter(userListdata);
				break;
		}
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	private void getData(String ptntcode)
	{

		try
		{
			UserData userData = null;
			try
			{
				userData = appDb.getSelectedUserDetail(ptntcode);
			}
			catch (Exception e)
			{

			}
			String selname = "";
			if (userData != null)
			{

				/*if (userData.getLast_name() != null && !userData.getLast_name().equalsIgnoreCase("null"))
				{
					selname = userData.getFirst_name() + " " + userData.getLast_name() + "";
				}else{
					selname = userData.getFirst_name() + "";
				}*/

				if (userData.getFirst_name() != null && !userData.getFirst_name().equalsIgnoreCase("null"))
				{
					selname = userData.getFirst_name() + "";
					if (userData.getLast_name() != null && !userData.getLast_name().equalsIgnoreCase("null"))
					{
						selname = userData.getFirst_name() + " " + userData.getLast_name() + "";
					}
				}

			}
			name.setText(selname + "");
			if (userData != null && userData.getPtnt_cd() != null
					&& userData.getPtnt_cd().equalsIgnoreCase(Util.getStoredUsername(context)))
			{
				setData(userData);
			}
		}
		catch (Exception e)
		{

		}
	}

	private List<String> getFamilyList()
	{
		userList.clear();
		userListdata.clear();
		try
		{
			userList = appDb.getUSersList();

			for (UserData userData : userList)
			{
				String selname = "";
				if (userData != null)
				{

					//selname = userData.getFirst_name() + " " + userData.getLast_name() + "";
					if (userData.getFirst_name() != null && !userData.getFirst_name().equalsIgnoreCase("null"))
					{
						selname = userData.getFirst_name() + "";
						if (userData.getLast_name() != null && !userData.getLast_name().equalsIgnoreCase("null"))
						{
							selname = userData.getFirst_name() + " " + userData.getLast_name() + "";
						}

					}
					if (userData.getPtnt_cd() != null)
					{

						selname = selname + " - " + userData.getPtnt_cd() + "";
						userListdata.add(selname);
					}
				}
			}
		}
		catch (Exception e)
		{
		}

		return userListdata;
	}

	private void setPopupListAdapter(List<String> popupLstItems)
	{

		ArrayAdapter<String> ad = new ArrayAdapter<String>(this, R.layout.textcenter, R.id.textItem, userListdata);
		listView.setAdapter(ad);

		Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

		hidePopup.startAnimation(bottomUp);
		hidePopup.setVisibility(View.VISIBLE);
	}

	@Override
	protected void onResume()
	{
		if (getFamilyList().size() == 1)
			dropdown.setVisibility(View.GONE);
		else
			dropdown.setVisibility(View.VISIBLE);
		super.onResume();
	}

	private void setData(UserData userdata)
	{

		if (userdata.getAddress2() != null && !userdata.getAddress2().equalsIgnoreCase("null")
				&& !userdata.getAddress2().isEmpty())
		{
			Bitmap captured_img_bitMap = StringToBitMap(userdata.getAddress2());
			profileImageSetting(captured_img_bitMap);
		}
	}

	private void profileImageSetting(Bitmap captured_img_bitMap)
	{
		guestIVID.setBackgroundResource(R.color.emptycolor);
		guestIVID.setImageBitmap(null);
		bitmap = captured_img_bitMap;
		//		Bitmap conv_bm = getRoundedRectBitmap(captured_img_bitMap, 100);
		guestIVID.setImageBitmap(captured_img_bitMap);
	}

	public Bitmap StringToBitMap(String encodedString)
	{
		try
		{
			byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
			return bitmap;
		}
		catch (Exception e)
		{
			e.getMessage();
			return null;
		}
	}

	@Override
	public void onStart()
	{
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		Action viewAction = Action.newAction(Action.TYPE_VIEW, // TODO: choose an action type.
				"HealthTracker Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://com.srllimited.srl/http/host/path"));
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop()
	{
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(Action.TYPE_VIEW, // TODO: choose an action type.
				"HealthTracker Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app URL is correct.
				Uri.parse("android-app://com.srllimited.srl/http/host/path"));
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
	}
}
