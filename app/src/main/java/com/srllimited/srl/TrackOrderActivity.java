package com.srllimited.srl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.srllimited.srl.adapters.TrackOrderAdapter;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.data.TrackOrderData;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.widget.UISearchBar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TrackOrderActivity extends MenuBaseActivity
{

	static final int VT1 = 0;
	static final int VT2 = 1;
	public static Activity trackorder;
	public static String orderno = "";
	public static String orderdate = "";
	public static String confirmedDate = "";
	public static String cacelDate = "";
	public static String processDate = "";
	public static String reportDate = "";
	RecyclerView mRecyclerView;
	Context mContext;
	List<TrackOrderData> trackOrderDatas = new ArrayList<>();
	List<TrackOrderData> trackOrderDatasold = new ArrayList<>();
	LinearLayout orderlin;
	TextView torderno;
	RecyclerView.Adapter mAdapter;
	AlertDialog alert;
	private String ID = "ID";
	private String STATUS = "STATUS";
	private String FLAG = "FLAG";
	private String S_DATE = "S_DATE";
	private String ORDERNO = "ORDERNO";
	private String ORDER_DATE = "ORDER_DATE";
	private String CONFIRMED_DATE = "CONFIRMED_DATE";
	private String CANCELLED_DATE = "CANCELLED_DATE";
	private String PROCESS_DATE = "PROCESS_DATE";
	private String REPORT_DATE = "REPORT_DATE";
	private boolean isSearch = false;
	private UISearchBar mSearchView;
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			if (!isSearch)
			{
				trackOrderDatasold.clear();
			}
			trackOrderDatas.clear();
			if (serverResponseData != null)
			{

				setOrderList(serverResponseData);
			}
			else
			{
				if (mAdapter != null)
				{
					mAdapter.notifyDataSetChanged();
				}
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
		super.addContentView(R.layout.activity_two);

		mContext = TrackOrderActivity.this;
		trackorder = this;
		isSearch = false;

		//		recyclerView = (RecyclerView) findViewById(R.id.order_tracking_list);
		//		recyclerView.setHasFixedSize(true);
		//		recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		//		recyclerView.setAdapter(new OrderTrackingAdapter(this));

		mRecyclerView = (RecyclerView) findViewById(R.id.order_tracking_list);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		mRecyclerView.setHasFixedSize(true);
		mAdapter = new TrackOrderAdapter(mContext, trackOrderDatas);
		mRecyclerView.setAdapter(mAdapter);

		orderlin = (LinearLayout) findViewById(R.id.orderview);

		torderno = (TextView) findViewById(R.id.orderno);

		if (Constants.isTrack)
		{
			String orderid = SharedPrefsUtil.getStringPreference(mContext, "orderid");

			if (Validate.notEmpty(orderid) && !orderid.equalsIgnoreCase("null"))
			{
				orderlin.setVisibility(View.VISIBLE);
				torderno.setText(orderid);
			}

			if (Validate.notEmpty(SharedPrefsUtil.getStringPreference(mContext, "orderid")))
			{
				sendRequest(ApiRequestHelper.getTrackOrder(mContext,
						SharedPrefsUtil.getStringPreference(mContext, "orderid")));
			}
		}

		header_loc_name.setText("Track Your Order");
		header_loc_name.setEnabled(false);

		mSearchView = (UISearchBar) findViewById(R.id.searchView);
		mSearchView.editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		mSearchView.editText.setHint("Enter Order Id");
		if(Constants.isTrackFromConfirmationScreenButton)
		{
			mSearchView.editText.setText(OrderConfirmationActivity.orderid);
			String textToSearch1 = mSearchView.editText.getText().toString();
			performSearch(textToSearch1);
		}
		//		mSearchView.editText.setAllCaps(true);
		mSearchView.editText.setFilters(new InputFilter[] { new InputFilter.AllCaps() });
		mSearchView.editText.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{

				if (actionId == EditorInfo.IME_ACTION_SEARCH)
				{

					String textToSearch = mSearchView.editText.getText().toString();
					if (Validate.notEmpty(textToSearch))
					{
						Util.hideSoftKeyboard(mContext, v);
						performSearch(textToSearch);
					}
					else
					{
						Toast.makeText(mContext, "Please enter text to search", Toast.LENGTH_SHORT).show();
					}
					return true;
				}
				return false;
			}
		});

		mSearchView.btnClear.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				mSearchView.editText.setText("");
				orderlin.setVisibility(View.GONE);

				trackOrderDatas.clear();
				mAdapter.notifyDataSetChanged();
				isSearch = false;
			}
		});

		navBack.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (Constants.isTrack && Constants.isTrackFromOrderList)
				{
					Constants.isTrackFromConfirmationScreenButton=false;

					finish();
				}
				else if (Constants.isTrack)
				{
					Constants.isTrackFromConfirmationScreenButton=false;

					Util.killAll();
					Util.killPayOpt();
					Intent intent = new Intent(TrackOrderActivity.this, BookATestActivity.class);
					startActivity(intent);
					finish();
				}
				else
				{
					Constants.isTrackFromConfirmationScreenButton=false;

					finish();
				}
			}
		});
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	private void setOrderList(ServerResponseData serverResponseData)
	{
		orderno = "";
		orderdate = "";
		confirmedDate = "";
		cacelDate = "";
		processDate = "";
		reportDate = "";

		if (!isSearch)
		{
			trackOrderDatasold.clear();
		}
		trackOrderDatas.clear();

		JSONObject jObj = null;
		JSONArray jArray = null;
		JSONArray jArrayStatus = null;
		if (serverResponseData != null)
		{
			if (serverResponseData.getObjectData() != null)
			{
				jObj = serverResponseData.getObjectData();
			}
			if (jObj != null)
			{
				if (!jObj.isNull("HDR"))
				{
					try
					{
						jArray = jObj.getJSONArray("HDR");

						if (Validate.notNull(jArray))
						{
							trackOrderDatas.clear();
							for (int i = 0; i < jArray.length(); i++)
							{
								TrackOrderData trackOrderData = new TrackOrderData();
								trackOrderData.setID(jArray.getJSONObject(i).getString(ID));
								trackOrderData.setSTATUS(jArray.getJSONObject(i).getString(STATUS));
								trackOrderData.setFLAG(jArray.getJSONObject(i).getString(FLAG));
								trackOrderData.setS_DATE(jArray.getJSONObject(i).getString(S_DATE));
								trackOrderDatas.add(trackOrderData);
							}
						}

					}
					catch (JSONException e)
					{
						e.printStackTrace();
					}
				}

				try
				{
					jArrayStatus = jObj.getJSONArray("STATUS");

					if (jArrayStatus.length() > 0/*!jObj.isNull("STATUS")*/)
					{
						try
						{
							jArray = jObj.getJSONArray("STATUS");

							if (Validate.notNull(jArray))
							{

								for (int i = 0; i < jArray.length(); i++)
								{
									try
									{
										orderno = RestApiCallUtil
												.colepochToDate(jArray.getJSONObject(i).getLong(ORDERNO));
										// jArray.getJSONObject(0).getString("ORDERNO")
									}
									catch (Exception e)
									{
									}
									try
									{
										orderdate = RestApiCallUtil
												.colepochToDate(jArray.getJSONObject(i).getLong(ORDER_DATE));
									}
									catch (Exception e)
									{
									}

									try
									{
										confirmedDate = RestApiCallUtil
												.colepochToDate(jArray.getJSONObject(i).getLong(CONFIRMED_DATE));
										// jArray.getJSONObject(i).getString(CONFIRMED_DATE)
									}
									catch (Exception e)
									{
									}

									try
									{
										cacelDate = RestApiCallUtil
												.colepochToDate(jArray.getJSONObject(i).getLong(CANCELLED_DATE));
									}
									catch (Exception e)
									{
									}

									try
									{
										processDate = RestApiCallUtil
												.colepochToDate(jArray.getJSONObject(i).getLong(PROCESS_DATE));
									}
									catch (Exception e)
									{
									}

									try
									{
										reportDate = RestApiCallUtil
												.colepochToDate(jArray.getJSONObject(i).getLong(REPORT_DATE));
									}
									catch (Exception e)
									{
									}
									//								orderdate = jArray.getJSONObject(i).getString(ORDER_DATE);
									//								confirmedDate = jArray.getJSONObject(i).getString(CONFIRMED_DATE);
									//								cacelDate = jArray.getJSONObject(i).getString(CANCELLED_DATE);
									//								processDate = jArray.getJSONObject(i).getString(PROCESS_DATE);
									//								reportDate = jArray.getJSONObject(i).getString(REPORT_DATE);

								}
							}

						}
						catch (JSONException e)
						{
							e.printStackTrace();
						}
					}
					else
					{
						trackOrderDatas.clear();
						orderlin.setVisibility(View.INVISIBLE);
						torderno.setText("");
						showsAlert1();
					}
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
			}
		}

		mAdapter.notifyDataSetChanged();
		if (trackOrderDatas != null)
		{
			if (trackOrderDatasold.isEmpty())
			{
				trackOrderDatasold.addAll(trackOrderDatas);
			}

		}
	}

	private void performSearch(String searchText)
	{
		orderlin.setVisibility(View.VISIBLE);

		torderno.setText(searchText);
		isSearch = true;
		sendRequest(ApiRequestHelper.getTrackOrder(mContext, searchText));
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		if (Constants.isTrack && Constants.isTrackFromConfirmationScreenButton)
		{
			//Util.killBook();
			Constants.isTrackFromConfirmationScreenButton=false;

			Util.killAll();
			Util.killPayOpt();
			/* Intent intent = new Intent(TrackOrderActivity.this, BookATestActivity.class);
			startActivity(intent);*/
			   Intent intent = new Intent(TrackOrderActivity.this, Dashboard.class);
			startActivity(intent);
			finish();
		}
		else {
			finish();
		}
	}

	public void showsAlert1()
	{

		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(TrackOrderActivity.this,
				android.app.AlertDialog.THEME_HOLO_LIGHT);
		builder.setMessage("Order Not Found.").setCancelable(false).setPositiveButton("OK",
				new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});

		alert = builder.create();
		alert.show();
	}

	private class OrderTrackingAdapter extends RecyclerView.Adapter<OrderTrackingAdapter.ViewHolder>
	{
		Context ctx;

		ArrayList<String[]> objs = new ArrayList<String[]>()
		{
			{
				add(new String[] { "16 oct-2016 2:30pm", "Sample collection is not done" });
				add(new String[] { "16 oct-2016 2:30pm", "Sample collection is not done" });
				add(new String[] { "16 oct-2016 2:30pm", "Sample collection is not done" });
				add(new String[] { "16 oct-2016 2:30pm", "Sample collection is not done" });
				add(new String[] { "16 oct-2016 2:30pm", "Sample collection is not done" });
				add(new String[] { "16 oct-2016 2:30pm", "Sample collection is not done" });
				add(new String[] { "16 oct-2016 2:30pm", "Sample collection is not done" });
				add(new String[] { "16 oct-2016 2:30pm", "Sample collection is not done" });
				add(new String[] { "16 oct-2016 2:30pm", "Sample collection is not done" });
			}
		};

		public OrderTrackingAdapter(Context context)
		{
			ctx = context;
		}

		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
		{
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			return new ViewHolder(inflater.inflate(R.layout.item_tracking_step, parent, false));
		}

		@Override
		public void onBindViewHolder(ViewHolder holder, int position)
		{
			holder.date.setText(objs.get(position)[0]);
			holder.info.setText(objs.get(position)[1]);
			if (holder.getItemViewType() == VT2)
			{
				holder.line.setVisibility(View.GONE);
				holder.date.setTextColor(ContextCompat.getColor(ctx, R.color.lightGreen));
				holder.info.setTextColor(ContextCompat.getColor(ctx, R.color.lightGreen));
				holder.circle.setImageResource(R.drawable.circle_green);
			}
			else
			{
				holder.line.setVisibility(View.VISIBLE);
				holder.circle.setImageResource(R.drawable.circle);
			}

		}

		@Override
		public int getItemCount()
		{
			return objs.size();
		}

		@Override
		public int getItemViewType(int position)
		{
			if (position == objs.size() - 1)
			{
				return VT2;
			}
			else
			{
				return VT1;
			}
		}

		public class ViewHolder extends RecyclerView.ViewHolder
		{
			public final TextView date, info;

			public final View line;

			public final ImageView circle;

			public ViewHolder(View itemView)
			{
				super(itemView);
				date = (TextView) itemView.findViewById(R.id.date);
				info = (TextView) itemView.findViewById(R.id.info);
				line = itemView.findViewById(R.id.line);
				circle = (ImageView) itemView.findViewById(R.id.circle);
			}
		}

	}
}
