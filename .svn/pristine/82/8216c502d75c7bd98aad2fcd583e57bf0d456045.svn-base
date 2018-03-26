package com.srllimited.srl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.srllimited.srl.adapters.ParentListAdapter;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.ReportParentList;
import com.srllimited.srl.data.ReportsData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.util.Validation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Codefyne on 29/12/2016.
 */

public class MyReportsExpandableListView extends MenuBaseActivity
{
	public static Activity myreportexpandable;
	Context context;
	ExpandableListView expandleListViewID;
	List<ReportParentList> storeReportsFromMap = new ArrayList<>();

	HashMap<String, List<ReportsData>> accessionList = new HashMap<>();

	List<ReportsData> mReportsDatas = new ArrayList<>();

	TextView accessNoTVID;

	ImageView myreportPDfIVID, share, close_popup;

	Dialog alertDialog;

	TextView enter_user_id;

	TextView submit;

	EditText promo;

	boolean pdfAction = false;

	TextView parent_product_name;
	RecyclerView.Adapter mAdapter;

	//	public class ReportsExpandableListAdapter extends BaseExpandableListAdapter
	//	{
	//		private Context _context;
	//		private Context child_context;
	//		private List<ReportParentList> _listDataHeader;
	//		public List<ReportsData> chList;
	//		List<ReportsData> childbookList;
	//		private LayoutInflater layoutInflater = null;
	//		List<ReportsData> chListinside;
	//		RecyclerView Recylerchildlist;
	//
	//		public ReportsExpandableListAdapter(Context context, List<ReportParentList> _listDataHeader)
	//		{
	//			this._context = context;
	//			this._listDataHeader = _listDataHeader;
	//			layoutInflater = LayoutInflater.from(context);
	//		}
	//
	//		@Override
	//		public Object getChild(int groupPosition, int childPosititon)
	//		{
	//			try
	//			{
	//				chList = _listDataHeader.get(groupPosition).getReportsDataList();
	//				return chList.get(childPosititon);
	//			}
	//			catch (ArrayIndexOutOfBoundsException ee)
	//			{
	//				ee.printStackTrace();
	//			}
	//			catch (Exception eee)
	//			{
	//				eee.printStackTrace();
	//			}
	//			return null;
	//		}
	//
	//		@Override
	//		public long getChildId(int groupPosition, int childPosition)
	//		{
	//			return childPosition;
	//		}
	//
	//		@Override
	//		public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	//		{
	//			convertView = layoutInflater.inflate(R.layout.reports_expandable_child_list_item, null, false);
	//			Recylerchildlist = (RecyclerView) findViewById(R.id.Recylerchildlist);
	////			childbookList = (List<ReportsData>) getChild(groupPosition, childPosition);
	//
	//
	//
	//			Recylerchildlist.setHasFixedSize(true);
	//			ReportsChildListAdapter mAdapter = new ReportsChildListAdapter(_context, chList);
	//			Recylerchildlist.setAdapter(mAdapter);
	//
	//			ArrayList<Integer> arrayList = new ArrayList<Integer>();
	//			arrayList.add(groupPosition);
	//
	//			arrayList.add(childPosition);
	//			return convertView;
	//		}
	//
	//		@Override
	//		public int getChildrenCount(int groupPosition)
	//		{
	//			try
	//			{
	//				chListinside = _listDataHeader.get(groupPosition).getReportsDataList();
	//				return chListinside.size();
	//			}
	//			catch (ArrayIndexOutOfBoundsException ee)
	//			{
	//				ee.printStackTrace();
	//			}
	//			catch (Exception eee)
	//			{
	//				eee.printStackTrace();
	//			}
	//			return chListinside.size();
	//		}
	//
	//		@Override
	//		public Object getGroup(int groupPosition)
	//		{
	//			try
	//			{
	//				return _listDataHeader.get(groupPosition);
	//			}
	//			catch (ArrayIndexOutOfBoundsException ee)
	//			{
	//				ee.printStackTrace();
	//			}
	//			catch (Exception eee)
	//			{
	//				eee.printStackTrace();
	//			}
	//			return null;
	//		}
	//
	//		@Override
	//		public int getGroupCount()
	//		{
	//			return _listDataHeader.size();
	//		}
	//
	//		@Override
	//		public long getGroupId(int groupPosition)
	//		{
	//			return groupPosition;
	//		}
	//
	//		@Override
	//		public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	//		{
	//			//String headerTitle = (String) getGroup(groupPosition);
	//			final ReportParentList group = (ReportParentList) getGroup(groupPosition);
	//			if (convertView == null)
	//			{
	//				LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	//				convertView = infalInflater.inflate(R.layout.reports_expandable_list_parent_item, null);
	//			}
	//			final ExpandableListView mExpandableListView = (ExpandableListView) parent;
	//
	//
	//			ImageView viewDetailsIVID = (ImageView) convertView.findViewById(R.id.viewDetailsIVID);
	//			TextView parent_id = (TextView)convertView.findViewById(R.id.parent_id);
	//			parent_id.setText(group.getParentid());
	//			viewDetailsIVID.setOnClickListener(new View.OnClickListener()
	//			{
	//				@Override
	//				public void onClick(View view)
	//				{
	//					mExpandableListView.expandGroup(groupPosition);
	////
	////					group.getReportsDataList();
	//
	//				}
	//			});
	//			return convertView;
	//		}
	//
	//		@Override
	//		public boolean hasStableIds()
	//		{
	//			return true;
	//		}
	//
	//
	//		@Override
	//		public boolean isChildSelectable(int groupPosition, int childPosition)
	//		{
	//			return true;
	//		}
	//
	//
	//	}
	//
	//
	//	public class ReportsChildListAdapter extends RecyclerView.Adapter<ReportsChildListAdapter.ViewHolder> {
	//		Context mContext;
	//
	//		private List<ReportsData> listData;
	//
	//		public ReportsChildListAdapter(Context context, List<ReportsData> listData) {
	//			this.mContext = context;
	//			this.listData = listData;
	//		}
	//
	//		public class ViewHolder extends RecyclerView.ViewHolder
	//		{
	//			public final TextView chamicalnameTVID, millletersTVID, rangeTVID;
	//			public RelativeLayout reportData;
	//
	//			public ViewHolder(View itemView)
	//			{
	//				super(itemView);
	//				//reportData = (RelativeLayout) itemView.findViewById(R.id.reportData);
	//				chamicalnameTVID = (TextView) itemView.findViewById(R.id.chamicalnameTVID);
	//				millletersTVID = (TextView) itemView.findViewById(R.id.millletersTVID);
	//				rangeTVID = (TextView) itemView.findViewById(R.id.rangeTVID);
	//				//reportData.setOnClickListener(this);
	//			}
	//
	//		}
	//
	//
	//
	//
	//
	//		public int getItemCount() {
	//			return listData.size();
	//		}
	//
	//		public ReportsChildListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
	//			View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myreport_category_data_list, parent, false);
	//			return new ReportsChildListAdapter.ViewHolder(view);
	//		}
	//
	//		public void onBindViewHolder(final ReportsChildListAdapter.ViewHolder holder, final int position) {
	//			holder.chamicalnameTVID.setText("" + listData.get(position).getELMNT_NAME());
	//			holder.millletersTVID.setText("" + Html.fromHtml(listData.get(position).getELMNT_MIN_RANGE()));
	//			holder.rangeTVID.setText("" + Html.fromHtml(listData.get(position).getELMNT_MIN_RANGE()));
	//		}
	//
	//	}
	//}
	private RecyclerView mRecyclerView;
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			switch (request.getReferralCode())
			{
				case GET_PDF:
				{

					shareAlert();
					//                    String url = "";
					//                    try {
					//                        url = Html.fromHtml(serverResponseData.getFullData().getString("data")) + "0";
					//                        if (pdfAction) {
					//                            //Toast.makeText(mContext.getApplicationContext(), "Opening Pdf : " + url, Toast.LENGTH_SHORT).show();
					//                            try {
					//                                if (url != null) {
					//                                    //url = "http://104.211.96.182:1127/GenerateReports.aspx?acc=0002PL000124&lid=2&ctyp=W&session=WEB-APP-QA";
					//                                    Intent viewIntent =
					//                                            new Intent("android.intent.action.VIEW",
					//                                                    Uri.parse(url));
					//                                    context.startActivity(viewIntent);
					//                                }
					//                            } catch (Exception e) {
					//                            }
					//                        } else {
					//                            if (Validate.notEmpty(url)) {
					//                                shareAlert();
					//                            } else {
					//                                Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
					//                            }
					//                        }
					//
					//                    } catch (Exception e) {
					//
					//                    }
				}
				/*String url = "";
				try
				{
					url = serverResponseData.getFullData().getString("data");
				}
				catch (Exception e)
				{

				}

				if (Validate.notEmpty(url))
				{
					shareAlert();
				}
				else
				{
					Toast.makeText(context, "No Data", Toast.LENGTH_SHORT).show();
				}
				}
				break;*/
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
		super.addContentView(R.layout.myreportid_complete_details);

		myreportexpandable = this;
		context = MyReportsExpandableListView.this;

		accessNoTVID = (TextView) findViewById(R.id.accessNoTVID);
		parent_product_name = (TextView) findViewById(R.id.parent_product_name);
		accessNoTVID.setText("Accession No: " + Constants.storedAccesstion);
		header_loc_name.setText("My Reports");
		header_loc_name.setEnabled(false);
		//		expandleListViewID = (ExpandableListView) findViewById(R.id.expandleListViewID);

		myreportPDfIVID = (ImageView) findViewById(R.id.myreportPDfIVID);
		share = (ImageView) findViewById(R.id.share);

		myreportPDfIVID.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{

				//				pdfAction = true;
				//				sendRequest(ApiRequestHelper.getPdfReports("6", Constants.storedAccesstion, Util.getStoredUsername(context),
				//						"9704683480", "", Constants.devicetobepassed, "1", "ANDROID", "1", "1"));
				Util.killShowWeb();
				Intent intent = new Intent(context, ShowWebView.class);
				startActivity(intent);
			}
		});

		alertDialog = new Dialog(this);
		alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//		alertDialog.setCancelable(false);
		alertDialog.setContentView(R.layout.got_promocode);

		submit = (TextView) alertDialog.findViewById(R.id.alert_apply);
		submit.setText("Submit");
		enter_user_id = (TextView) alertDialog.findViewById(R.id.enter_user_id);
		promo = (EditText) alertDialog.findViewById(R.id.et_promo);
		close_popup = (ImageView) alertDialog.findViewById(R.id.close_popup);

		enter_user_id.setText("Email id");

		mReportsDatas = Constants.sReportsDatas;

		share.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				//                pdfAction = false;
				alertDialog.show();
			}
		});

		close_popup.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				if (alertDialog != null)
				{
					alertDialog.dismiss();
				}
			}
		});

		submit.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				String cityid = SharedPrefsUtil.getStringPreference(context, "selectedcityid");
				Util.hideSoftKeyboard(context, view);
				if (Validate.notEmpty(enter_user_id.getText().toString().trim()))
				{
					if (Validation.isEmailAddress(promo, true))
					{
						sendRequest(ApiRequestHelper.getPdfReports(context, cityid, Constants.storedAccesstion,
								Util.getStoredUsername(context), "9704683480", promo.getText().toString(),
								Constants.devicetobepassed, "1", "ANDROID", "1", "1"));

						if (alertDialog != null)
							alertDialog.dismiss();
					}
				}
				else
				{
					Toast.makeText(context, "Please enter email id", Toast.LENGTH_SHORT).show();
				}
			}
		});

		accessionList = new HashMap<>();
		storeReportsFromMap = new ArrayList<>();

		sortBySR();
		for (int j = 0; j < mReportsDatas.size(); j++)
		{
			Log.v("Test", mReportsDatas.get(j).getPRDCT_NAME() + " ::" + mReportsDatas.get(j).getSR_NO());
		}

		if (mReportsDatas != null)
		{

			if (mReportsDatas.size() > 0)
			{
				if (mReportsDatas.get(0) != null)
				{
					if (mReportsDatas.get(0).getPARENT_PRDCT_NAME() != null
							&& !mReportsDatas.get(0).getPARENT_PRDCT_NAME().equalsIgnoreCase("null"))
					{
						parent_product_name.setText(mReportsDatas.get(0).getPARENT_PRDCT_NAME() + "");
					}
				}
			}

			for (int i = 0; i < mReportsDatas.size(); i++)
			{

				if (!accessionList.containsKey(mReportsDatas.get(i).getPRDCT_NAME() + ""))
				{

					List<ReportsData> reportsDatas = new ArrayList<>();

					for (ReportsData reports : mReportsDatas)
					{

						if ((reports.getPRDCT_NAME() + "").equalsIgnoreCase(mReportsDatas.get(i).getPRDCT_NAME() + ""))
						{

							reportsDatas.add(reports);
						}

					}
					ReportParentList reportParentList = new ReportParentList();

					reportParentList.setParentid(mReportsDatas.get(i).getPRDCT_NAME() + "");
					reportParentList.setReportsDataList(reportsDatas);
					storeReportsFromMap.add(reportParentList);

					accessionList.put(mReportsDatas.get(i).getPRDCT_NAME() + "", reportsDatas);
					//					}
				}

			}
		}

		if (storeReportsFromMap != null)
		{
			setAccessionListAdapter();
		}

	}

	private void sortBySR()
	{
		Collections.sort(mReportsDatas, new Comparator<ReportsData>()
		{
			@Override
			public int compare(ReportsData o1, ReportsData o2)
			{

				final int d1 = o1.getSR_NO();
				final int d2 = o2.getSR_NO();
				// return (d1 < d2) ? -1 : (d1 < d2) ? 1 : 0;
				return (d1 < d2 ? -1 : (d1 == d2 ? 0 : 1));

			}
		});

	}

	private void setAccessionListAdapter()
	{

		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		mRecyclerView.setHasFixedSize(true);

		mAdapter = new ParentListAdapter(context, storeReportsFromMap);
		mRecyclerView.setAdapter(mAdapter);
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	public void shareAlert()
	{
		try
		{
			android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
					MyReportsExpandableListView.this, AlertDialog.THEME_HOLO_LIGHT);
			// builder.setMessage("Report is shared to your email id")
			builder.setMessage("The result report will be sent on provided email shortly.")

					.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{

							if (alertDialog != null)
							{
								alertDialog.dismiss();
							}
							dialog.cancel();

						}
					});
			builder.show();
		}
		catch (Exception e)
		{

		}
	}

}
