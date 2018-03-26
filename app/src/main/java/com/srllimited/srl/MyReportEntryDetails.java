package com.srllimited.srl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.srllimited.srl.adapters.AccessionListAdapter;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.AccessionListData;
import com.srllimited.srl.data.ReportsData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.data.ServerResponseData2;
import com.srllimited.srl.data.UserData;
import com.srllimited.srl.database.ReportsDatabase;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.Log;
import com.srllimited.srl.util.NetworkConnectivity;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.utilities.AppDataBaseHelper;
import com.srllimited.srl.utilities.Utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

//import com.codefyne.mysrl.adapters.AccessionListAdapter;

/**
 * Created by Ruchi Tiwari on 12/13/2016.
 */

public class MyReportEntryDetails extends MenuBaseActivity implements OnClickListener
{

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
	public static Activity myreportentry;
	AppDataBaseHelper appDb = new AppDataBaseHelper(this);

	//	ImageView pdfBTNID;
	Context context;

	LinearLayout hidePopup;

	LinearLayout sort_date, sort_accession;

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
	LinearLayout dateacc;
	ImageView datesorticon, datesorttopicon, acessionsorticon, acessionsorttopicon;
	SwipeRefreshLayout mSwipeRefreshLayout;
	ProgressDialog progressDialog1;
	RecyclerView.Adapter mAdapter;
	Bitmap bitmap = null;
	String familypwd = "";
	private ListView listView;
	private CircleImageView guestIVID;
	private String SelectedFamilyId;//to get the selected family member
	private RecyclerView mRecyclerView;
	private String selectedFamily = "";
	private JSONArray array = null;
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			switch (request.getReferralCode())
			{
				case REPORTS:
				{
					dismissProgressDialogstart();
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
									array = serverResponseData2.getData();
									dismissProgressDialogstart();

									new AsyncTaskRunner().execute();
								}
							}
							else
							{
								setData();

								onItemsLoadComplete();
							}
						}
						catch (Exception e)
						{

						}

					}
					else
					{
						setData();
						onItemsLoadComplete();
					}
				}
					break;
			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{
			setData();
			onItemsLoadComplete();
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
		super.addContentView(R.layout.myreport_entry_details);
		//		pdfBTNID = (ImageView) findViewById(R.id.pdfBTNID);
		myreportentry = this;
		context = MyReportEntryDetails.this;
		reportDB = new ReportsDatabase(getApplicationContext());
		appDb = new AppDataBaseHelper(getApplicationContext());

		guestIVID = (CircleImageView) findViewById(R.id.guestIVID);
		header_loc_name.setText("My Reports");
		header_loc_name.setEnabled(false);
		sort_date = (LinearLayout) findViewById(R.id.sort_date);
		sort_accession = (LinearLayout) findViewById(R.id.sort_accession);
		cancel = (ImageView) findViewById(com.srllimited.srl.R.id.cancel);
		confirm = (ImageView) findViewById(com.srllimited.srl.R.id.confirm);
		dropdown = (ImageView) findViewById(com.srllimited.srl.R.id.dropdown);
		listView = (ListView) findViewById(com.srllimited.srl.R.id.popup_list);
		popupheader = (TextView) findViewById(R.id.popup_header);
		hidePopup = (LinearLayout) findViewById(R.id.hidePopup);
		dateacc = (LinearLayout) findViewById(R.id.dateacc);
		sort_date.setOnClickListener(this);
		sort_accession.setOnClickListener(this);
		login_name = (TextView) findViewById(R.id.login_name);
		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
		name = (TextView) findViewById(R.id.name);
		SelectedFamilyId = Util.getStoredUsername(context);
		login_name.setText(Util.getStoredUsername(context));
		Constants.isMember = (Util.getStoredUsername(context));
		getData(Util.getStoredUsername(context));

		datesorticon = (ImageView) findViewById(R.id.datesorticon);
		datesorttopicon = (ImageView) findViewById(R.id.datesorttopicon);
		acessionsorticon = (ImageView) findViewById(R.id.acessionsorticon);
		acessionsorttopicon = (ImageView) findViewById(R.id.acessionsorttopicon);

		mReportsDatas = reportDB.getReportsData(Util.getStoredUsername(context));
		/* sendRequestWithoutProgress(ApiRequestHelper.getReports(Util.getStoredUsername(context), Util.getStoredPwd(context),
		        Constants.devicetobepassed, "1", "ANDROID", "1", "1", "0"));*/
		if(mReportsDatas.size()<=0) {
			if (NetworkConnectivity.isOnline(context))
			{
				showProgressDialogstart();
			}
			sendRequest(ApiRequestHelper.getReports(context, Util.getStoredUsername(context), Util.getStoredPwd(context),
					Constants.devicetobepassed, "1", "ANDROID", "1", "1", "0"));
		}
		else
		{
			setAccessionList();
			sendRequest(ApiRequestHelper.getReports(context, Util.getStoredUsername(context), Util.getStoredPwd(context),
					Constants.devicetobepassed, "1", "ANDROID", "1", "1", "0"));
		}

		//        if (mReportsDatas != null && !mReportsDatas.isEmpty()) {
		//            setAccessionList();
		//        } else {
		//            sendRequest(ApiRequestHelper.getReports(Util.getStoredUsername(context), Util.getStoredPwd(context),
		//                    Constants.devicetobepassed, "1", "ANDROID", "1", "1", "0"));
		//        }

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
		selectedFamily = Util.getStoredUsername(context);
		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
		{
			@Override
			public void onRefresh()
			{
				// Refresh items
				refreshItems();
			}
		});
	}

	private void refreshItems()
	{
		mSwipeRefreshLayout.setRefreshing(true);
		mSwipeRefreshLayout.setEnabled(false);
		sendRequest(ApiRequestHelper.getReports(context, selectedFamily, getSelectedMemberPwd(selectedFamily) + "",
				Constants.devicetobepassed, "1", "ANDROID", "1", "1", "0"));
	}

	void onItemsLoadComplete()
	{
		try
		{
			mSwipeRefreshLayout.setEnabled(true);
			mSwipeRefreshLayout.setRefreshing(false);
		}
		catch (Exception e)
		{

		}
	}

	private void setAccessionListAdapter()
	{

		if (storeReportsFromMap != null && storeReportsFromMap.size() > 0)
		{
			viewdatehead();
		} //else
			//  hidedatehead();
			//		Log.e("accesslist", storeReportsFromMap + "");
		if (storeReportsFromMap.size() != 0)
		{

			mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
			mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
			mRecyclerView.setHasFixedSize(true);

			mAdapter = new AccessionListAdapter(context, storeReportsFromMap);
			mRecyclerView.setAdapter(mAdapter);

			mAdapter.notifyDataSetChanged();
		}
		else
		{
			Utility.NeResponseError(MyReportEntryDetails.this, "No reports available");
			try {
				mAdapter = new AccessionListAdapter(context, storeReportsFromMap);
				mRecyclerView.setAdapter(mAdapter);

				mAdapter.notifyDataSetChanged();
			}
			catch (Exception e)
			{

			}
		}
	}

	private void setAccessionList()
	{
		accessionList = new HashMap<>();
		storeReportsFromMap = new ArrayList<>();

		if (mReportsDatas != null)
		{
			for (int i = 0; i < mReportsDatas.size(); i++)
			{

				//				if (accessionList != null)
				//				{

				if (!accessionList.containsKey(mReportsDatas.get(i).getACC_ID().toString()))
				{
					List<ReportsData> reportsDatas = new ArrayList<>();
					for (ReportsData reports : mReportsDatas)
					{
						if (reports.getACC_ID() != null)
						{
							if (reports.getACC_ID().toString().equalsIgnoreCase(mReportsDatas.get(i).getACC_ID()))
							{

								reportsDatas.add(reports);
							}
						}
					}

					AccessionListData accessionListData = new AccessionListData();
					accessionListData.setSerialno(mReportsDatas.get(i).getSR_NO());
					accessionListData.setAcc_id(mReportsDatas.get(i).getACC_ID().toString());
					accessionListData.setAcc_date(reportsDatas.get(0).getACC_DT());
					accessionListData.setReportsDatas(reportsDatas);

					storeReportsFromMap.add(accessionListData);

					accessionList.put(mReportsDatas.get(i).getACC_ID(), reportsDatas);
					//					}
				}

				if (!accessionList.containsKey(mReportsDatas.get(i).getACC_ID().toString()))
				{
					List<ReportsData> reportsDatas = new ArrayList<>();
					reportsDatas.add(mReportsDatas.get(i));
					AccessionListData accessionListData = new AccessionListData();
					accessionListData.setSerialno(mReportsDatas.get(i).getSR_NO());
					accessionListData.setAcc_id(mReportsDatas.get(i).getACC_ID().toString());
					accessionListData.setAcc_date(mReportsDatas.get(i).getACC_DT());

					storeReportsFromMap.add(accessionListData);
					accessionList.put(mReportsDatas.get(i).getACC_ID(), reportsDatas);
					//

				}

				if (storeReportsFromMap != null)
				{
					sortByTopDate();
				}
				//

			}
			setAccessionListAdapter();
		}
		else
		{
			showSettingsAlert();
			//setAccessionListAdapter();
		}

	}

	private void setReportsList(JSONArray jArray)
	{

		//        JSONArray jArray = null;
		//        if (serverResponseData != null) {
		//            if (serverResponseData.getData() != null) {
		//                jArray = serverResponseData.getData();
		//            }
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

							/*reportsData.setPTNT_CD(
									Util.getStoredUsername(context)*//*jArray.getJSONObject(i).getString(PTNT_CD)*//*);*/ //this change as discussed with shradhha

							reportsData.setPTNT_CD(jArray.getJSONObject(i).getString(PTNT_CD));// As discussed with Shraddha reverted.
							reportsData.setGENERIC_NAME(jArray.getJSONObject(i).getString(GENERIC_NAME));
							reportsData.setCPT_CODE(jArray.getJSONObject(i).getString(CPT_CODE));

							String pdt = reportsData.getACC_ID() + "" + reportsData.getELMNT_ID() + "";

							ReportsData reportsData1 = new ReportsData();

							try
							{
								reportsData1 = reportDB.getReportswithid(pdt);
							}
							catch (Exception e)
							{
								Log.e("crash", e + "");
							}
							if (Validate.notNull(reportsData1) && reportsData1.getACC_ID() != null
									&& !reportsData1.getACC_ID().equalsIgnoreCase("null"))
							{

								reportDB.updateReports(reportsData);
							}
							else
								reportDB.createReports(reportsData);

						}
					}
				}
			}
			catch (JSONException e)
			{

			}
			//            }

		}
	}

	public void showSettingsAlert()
	{

		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MyReportEntryDetails.this,
				android.app.AlertDialog.THEME_HOLO_LIGHT);
		builder.setMessage("No Data").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{

				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				MyReportEntryDetails.this.startActivity(intent);
				dialog.cancel();
				finish();
			}
		});

		alert = builder.create();
		alert.show();
	}

	private void sortByAccession()
	{
		Collections.sort(storeReportsFromMap, new Comparator<AccessionListData>()
		{
			@Override
			public int compare(AccessionListData o1, AccessionListData o2)
			{

				final int d1 = o1.getSerialno();
				final int d2 = o2.getSerialno();
				return (d1 < d2) ? -1 : (d1 < d2) ? 1 : 0;

			}
		});
	}

	private void sortByTopAccession()
	{
		Collections.sort(storeReportsFromMap, new Comparator<AccessionListData>()
		{
			@Override
			public int compare(AccessionListData o1, AccessionListData o2)
			{

				final int d1 = o1.getSerialno();
				final int d2 = o2.getSerialno();
				return (d1 > d2) ? -1 : (d1 > d2) ? 1 : 0;

			}
		});
	}

	private void sortByTopDate()
	{
		Collections.sort(storeReportsFromMap, new Comparator<AccessionListData>()
		{
			@Override
			public int compare(AccessionListData o1, AccessionListData o2)
			{

				final long d1 = o1.getAcc_date();
				final long d2 = o2.getAcc_date();
				return (d1 > d2) ? -1 : (d1 > d2) ? 1 : 0;

			}
		});
	}

	private void sortByDate()
	{
		Collections.sort(storeReportsFromMap, new Comparator<AccessionListData>()
		{
			@Override
			public int compare(AccessionListData o1, AccessionListData o2)
			{

				final long d1 = o1.getAcc_date();
				final long d2 = o2.getAcc_date();
				return (d1 < d2) ? -1 : (d1 < d2) ? 1 : 0;

			}
		});
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
					SelectedFamilyId = part2.trim();
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

						selectedFamily = part2.trim();
						mReportsDatas = reportDB.getReportsData(selectedFamily);
						if(mReportsDatas.size()<=0) {
							sendRequest1(ApiRequestHelper.getReports(context, selectedFamily, getSelectedMemberPwd(selectedFamily) + "",
									Constants.devicetobepassed, "1", "ANDROID", "1", "1", "0"));
						}
						else {
						mReportsDatas = reportDB.getReportsData(part2.trim());
						setAccessionList();
						}
					}
				}

				hidePopup.startAnimation(bottomDown);
				hidePopup.setVisibility(View.INVISIBLE);

				break;

			case R.id.sort_accession:

				if (acessionsorticon.getVisibility() == View.VISIBLE)
				{
					acessionsorticon.setVisibility(View.GONE);
					acessionsorttopicon.setVisibility(View.VISIBLE);
					sortByAccession();
					setAccessionListAdapter();
				}
				else if (acessionsorttopicon.getVisibility() == View.VISIBLE)
				{
					acessionsorticon.setVisibility(View.VISIBLE);
					acessionsorttopicon.setVisibility(View.GONE);
					sortByTopAccession();
					setAccessionListAdapter();
				}

				break;
			case R.id.sort_date:
				/*sortByDate();
				setAccessionListAdapter();
				if (mAdapter != null) {
				    mAdapter.notifyDataSetChanged();
				}*/
				if (datesorticon.getVisibility() == View.VISIBLE)
				{
					datesorticon.setVisibility(View.GONE);
					datesorttopicon.setVisibility(View.VISIBLE);
					sortByDate();
					setAccessionListAdapter();

				}
				else if (datesorttopicon.getVisibility() == View.VISIBLE)
				{
					datesorttopicon.setVisibility(View.GONE);
					datesorticon.setVisibility(View.VISIBLE);
					sortByTopDate();
					setAccessionListAdapter();
				}
				break;

			case R.id.dropdown:
				popupheader.setText("Select Member");
				getFamilyList();
				setPopupListAdapter(userListdata);
				break;
		}
	}

	// ApiRequest{mUrl='http://srldiagapp.mysrl.in:93/service.asmx/FetchDataNew', mParams={timestamp=0, grouptyp=1, pwd=srl1234, ptntcode=PRIYF1104872, device_token=1, ostype=ANDROID, device_id=f7fd4687a3a962ff, strVldTk=463665dda6d91e234620f9487c3e6471, fcmtoken=dQn6ZQrw9aQ:APA91bEnOid5lwxYJluPAMdUgl6QDvSP2Lpu1OH7fkx25Y-dm1Pg_-L4L_ltuqC1KOwzwROmExHcObYPptUF4jZtjFGTqPEvNl9Wr1E8Tp3JINIyA-V0rTq4tI_xJ2xhw9UZjX3Cbamw, osversion=4.4.4, mysrl_version=5.1.9}, mReferralCode=7, mPostData=null}
	private void sendRequestWithoutProgress(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequestWithoutProgress(this, request, mResponseListener);
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequestWithoutProgress(this, request, mResponseListener);
		// ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}
	private void sendRequest1(ApiRequest request)
	{
		//ApiRequestManager.getInstance().sendRequestWithoutProgress(this, request, mResponseListener);
		 ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}
	private void setData()
	{
		try
		{
			mReportsDatas = reportDB.getReportsData(Util.getStoredUsername(context));
			sortByDate();
			setAccessionList();
		}
		catch (Exception e)
		{

		}
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
				//selname = userData.getFirst_name() + " " + userData.getLast_name() + "";
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

	private void hidedatehead()
	{
		dateacc.setVisibility(View.GONE);
	}

	private void viewdatehead()
	{
		dateacc.setVisibility(View.VISIBLE);
	}

	private String getSelectedMemberPwd(String ptntcode)
	{
		familypwd = "";
		UserData _userAppData = null;
		try
		{
			_userAppData = appDb.getSelectedUserDetail(ptntcode);
		}
		catch (Exception e)
		{

		}
		if (Validate.notNull(_userAppData) && Validate.notEmpty(_userAppData.getPwd()))
			familypwd = _userAppData.getPwd();
		return familypwd;
	}

	private void showProgressDialogstart()

	{
		dismissProgressDialogstart();
		progressDialog1 = ProgressDialog.show(MyReportEntryDetails.this, "", "Fetching Reports....");
		progressDialog1.setCancelable(false);
		progressDialog1.setCanceledOnTouchOutside(false);
		/*  MyReportEntryDetails.this.runOnUiThread(new Runnable()
		{
		    @Override
		    public void run() {
		        dismissProgressDialogstart();
		        progressDialog1 = ProgressDialog.show(MyReportEntryDetails.this, "", "Fetching Reports....");
		        progressDialog1.setCancelable(true);
		    }
		});*/
	}

	private void dismissProgressDialogstart()
	{

		if (progressDialog1 != null && progressDialog1.isShowing())
		{
			progressDialog1.dismiss();
			// progressDialog1 = null;
		}
		/*  MyReportEntryDetails.this.runOnUiThread(new Runnable()
		{
		    @Override
		    public void run() {
		        if (progressDialog1 != null && progressDialog1.isShowing()) {
		            progressDialog1.dismiss();
		            progressDialog1 = null;
		        }
		    }
		});*/
	}

	private class AsyncTaskRunner extends AsyncTask<String, String, String>
	{

		private String resp;

		@Override
		protected String doInBackground(String... params)
		{
			setReportsList(array);
			return resp;
		}

		@Override
		protected void onPostExecute(String result)
		{
			dismissProgressDialogstart();
			Util.hideProgressDialog();
			RestApiCallUtil.hideProgressDialog();
			mReportsDatas = reportDB.getReportsData(SelectedFamilyId/*Util.getStoredUsername(context)*/);
			if (mReportsDatas != null)
			{
				sortByDate();
				setAccessionList();
			}
			else
			{
				setData();
				sortByDate();
				setAccessionList();
			}
			onItemsLoadComplete();
			Util.hideProgressDialog();
		}

		@Override
		protected void onPreExecute()
		{
			Util.hideProgressDialog();
			if(mReportsDatas.size()<=0) {
				Util.showProgressDialog(context, " Fetching Reports..");
			}
		}

	}

}