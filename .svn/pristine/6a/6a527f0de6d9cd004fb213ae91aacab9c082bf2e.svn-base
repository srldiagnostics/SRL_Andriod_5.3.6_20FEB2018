package com.srllimited.srl.adapters;

/**
 * Created by Ruchi Tiwari on 02/12/16.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.srllimited.srl.MyCartActivity;
import com.srllimited.srl.R;
import com.srllimited.srl.TrackCollectExportActivity;
import com.srllimited.srl.TrackOrderActivity;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.BookTestORPackagesData;
import com.srllimited.srl.data.OrdersHistoryData;
import com.srllimited.srl.data.ProductList;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.database.DatabaseHelper;
import com.srllimited.srl.database.ProductHeaderDatabase;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.DateUtil;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder>
{
	public Context mContext;

	DatabaseHelper db;

	ProductHeaderDatabase productHeaderDB;

	OrdersAdapter ordersAdapter;

	int pos;

	ViewHolder viewHolder_cancle;

	private List<OrdersHistoryData> mOrdersHistoryDatas;

	private int selectedIndex = -1;

	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			switch (request.getReferralCode())
			{

				case CANCEL_ORDER:
					if (serverResponseData != null)
					{
						try
						{
							String successData = serverResponseData.getFullData().getString("data");
							if (successData != null && successData.equalsIgnoreCase("Success"))
							{

								/*final OrdersHistoryData ordersHistoryData = mOrdersHistoryDatas.get(pos);
								mOrdersHistoryDatas.remove(ordersHistoryData);
								ordersAdapter.notifyDataSetChanged();*/

								viewHolder_cancle.cancel.setVisibility(View.INVISIBLE);
								viewHolder_cancle.status.setText("CANCELLED");

								Toast.makeText(mContext, "Order cancelled Successfully", Toast.LENGTH_SHORT).show();
							}
							else
							{
								Toast.makeText(mContext, "Order cancellation failed", Toast.LENGTH_SHORT).show();
							}
						}
						catch (Exception e)
						{

						}
					}
					break;
				case REPEAT_ORDER:
				{

					String bookATest = "B";
					String pkg = "P";
					if (serverResponseData != null)
					{
						JSONObject jObj = serverResponseData.getObjectData();
						JSONArray jArray = null;
						if (jObj != null)
						{
							if (!jObj.isNull("PRDCT"))
							{
								try
								{
									jArray = jObj.getJSONArray("PRDCT");

									if (Validate.notNull(jArray))
									{
										for (int i = 0; i < jArray.length(); i++)
										{

											BookTestORPackagesData booktest_or_pkgs = new BookTestORPackagesData();
											booktest_or_pkgs.setID(
													jArray.getJSONObject(i).getInt(Constants.booktest_package_id));
											booktest_or_pkgs.setPRDCT_CODE(jArray.getJSONObject(i)
													.getString(Constants.booktest_package_product_code));
											booktest_or_pkgs.setPRDCT_ALIASES(jArray.getJSONObject(i)
													.getString(Constants.booktest_package_product_aliases));
											booktest_or_pkgs.setGROSS_AMT(jArray.getJSONObject(i)
													.getString(Constants.booktest_package_gross_amt));
											booktest_or_pkgs.setPRICE(jArray.getJSONObject(i)
													.getDouble(Constants.booktest_package_price));
											booktest_or_pkgs.setDISC(
													jArray.getJSONObject(i).getDouble(Constants.booktest_package_disc));
											booktest_or_pkgs.setDISC_TYPE(jArray.getJSONObject(i)
													.getString(Constants.booktest_package_disc_type));
											booktest_or_pkgs.setPRDCT_CONSTNS(jArray.getJSONObject(i)
													.getString(Constants.booktest_package_prdct_constnts));
											booktest_or_pkgs.setCATEGORY(jArray.getJSONObject(i)
													.getString(Constants.booktest_package_category));
											booktest_or_pkgs.setPTNT_INSTRCTN(jArray.getJSONObject(i)
													.getString(Constants.booktest_package_product_instrctn));
											booktest_or_pkgs.setREP_TAT(jArray.getJSONObject(i)
													.getString(Constants.booktest_package_rep_tat));
											booktest_or_pkgs.setINFO(
													jArray.getJSONObject(i).getString(Constants.booktest_package_info));
											booktest_or_pkgs.setCartPrice(jArray.getJSONObject(i)
													.getString(Constants.booktest_package_price));
											booktest_or_pkgs.setCart(true);

											if (Constants.isPackage)
											{
												booktest_or_pkgs.setBook_pkg(pkg);
											}
											else
											{
												booktest_or_pkgs.setBook_pkg(bookATest);
											}

											JSONArray namesArray = null;
											namesArray = jArray.getJSONObject(i).names();
											ArrayList<String> lsitToBeAdded = new ArrayList<String>();

											if (namesArray != null)
											{
												for (int j = 0; j < namesArray.length(); j++)
												{
													lsitToBeAdded.add(namesArray.getString(j));
												}
											}

											BookTestORPackagesData dbBookTestORPackagesData = new BookTestORPackagesData();
											try
											{
												if (db != null
														&& db.getBook_Pkg_Object(booktest_or_pkgs.getID()) != null)
												{
													dbBookTestORPackagesData = db
															.getBook_Pkg_Object(booktest_or_pkgs.getID());
												}
											}
											catch (Exception e)
											{

											}
											if (dbBookTestORPackagesData != null
													&& dbBookTestORPackagesData.getID() != 0)
											{
												db.updateBookOrPkgs(booktest_or_pkgs);
											}
											else
											{
												db.createBookTestOrPackages(booktest_or_pkgs);
											}
										}
									}
								}
								catch (JSONException e)
								{
									e.printStackTrace();
								}
							}

						}
					}
					Util.killMyCart();
					Intent intent = new Intent(mContext, MyCartActivity.class);
					intent.putExtra(Constants.REPEAT_ORDER, true);
					mContext.startActivity(intent);
				}
					break;
			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{

		}
	};

	public OrdersAdapter(Context context, List<OrdersHistoryData> ordersHistoryDatas)
	{
		this.productHeaderDB = new ProductHeaderDatabase(context.getApplicationContext());
		this.db = new DatabaseHelper(context.getApplicationContext());
		this.mContext = context;
		this.mOrdersHistoryDatas = ordersHistoryDatas;
		ordersAdapter = this;
	}

	@Override
	public OrdersAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_oder_list_item, viewGroup, false);
		ViewHolder viewHolder = new ViewHolder(view, i);

		viewHolder.showDetails(selectedIndex == i);

		return viewHolder;
	}

	@Override
	public void onBindViewHolder(final OrdersAdapter.ViewHolder viewHolder, final int i)
	{

		try
		{

			//   String  currentMilliqqqq = "1510823400000";
			// long currentMilliqqqq1 =Long.parseLong(currentMilliqqqq);
			// mOrdersHistoryDatas.get(i).setBOOKING_TO(currentMilliqqqq1);
			if (viewHolder != null)
			{
				if (mOrdersHistoryDatas.get(i) != null)
					viewHolder.setIndex(i);
			}

			String stDate = RestApiCallUtil.orderepochToDate(mOrdersHistoryDatas.get(i).getENTERED_ON());

			if (!stDate.equalsIgnoreCase("null"))
			{
				viewHolder.order_date.setText(stDate);
			}
			else
			{
				viewHolder.order_date.setText("");
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		if (!mOrdersHistoryDatas.get(i).getORDERNO().equalsIgnoreCase("null"))
		{
			viewHolder.order_no.setText("Order No : " + mOrdersHistoryDatas.get(i).getORDERNO() + "");
		}
		else
		{
			viewHolder.order_no.setText("Order No : " + "");
		}
		if (!mOrdersHistoryDatas.get(i).getPATIENT_NAME().equalsIgnoreCase("null"))
		{
			viewHolder.patient_name.setText("Patient Name : " + mOrdersHistoryDatas.get(i).getPATIENT_NAME() + "");
		}
		else
		{
			viewHolder.patient_name.setText("Patient Name : " + "");
		}
		if (!mOrdersHistoryDatas.get(i).getCREATED_BY().equalsIgnoreCase("null"))
		{
			viewHolder.pateint_id.setText("User Id : " + mOrdersHistoryDatas.get(i).getCREATED_BY() + "");
		}

		if (mOrdersHistoryDatas.get(i).getORDER_STATUS() != null
				&& !mOrdersHistoryDatas.get(i).getORDER_STATUS().equalsIgnoreCase("null"))
		{
			viewHolder.status.setText("Status : " + mOrdersHistoryDatas.get(i).getORDER_STATUS() + "");
		}
		if (mOrdersHistoryDatas.get(i).getORDER_STATUS().equals("CANCELLED"))
		{
			viewHolder.cancel.setVisibility(View.INVISIBLE);
		}
		else
		{
			viewHolder.cancel.setVisibility(View.VISIBLE);
		}

		String stDatee = RestApiCallUtil.orderepochToDate(mOrdersHistoryDatas.get(i).getBOOKING_FROM());
		if (!stDatee.equalsIgnoreCase("null"))
		{
			viewHolder.dateTVID.setText(stDatee);
		}
		else
		{
			viewHolder.dateTVID.setText("");
		}

		String startDate = RestApiCallUtil.colFromToepochToDate(mOrdersHistoryDatas.get(i).getBOOKING_FROM());

		if (!startDate.equalsIgnoreCase("null"))
		{
			viewHolder.startTimeTVID.setText(startDate + "");
		}
		else
		{
			viewHolder.startTimeTVID.setText("");
		}

		String endDate = RestApiCallUtil.colFromToepochToDate(mOrdersHistoryDatas.get(i).getBOOKING_TO());

		if (!endDate.equalsIgnoreCase("null"))
		{
			viewHolder.endTimeTVID.setText(endDate + "");
		}
		else
		{
			viewHolder.endTimeTVID.setText("");
		}

		long currentMilli = System.currentTimeMillis();
		String dateTime = DateUtil.notificationepochToDate(mOrdersHistoryDatas.get(i).getBOOKING_TO());
		long epoch = DateUtil.dateTimeToEpoch1(dateTime);

		long start = /*"1515509640000";*/mOrdersHistoryDatas.get(i).getHOMECOLLECT_START_TIME();
		long end = /*"1515513240000";*/ mOrdersHistoryDatas.get(i).getHOMECOLLECT_END_TIME();
		long diff;

		String HOMECOLLECT_START_TIME_str = DateUtil.notificationepochToDate(start);
		long HOMECOLLECT_START_TIME_long = DateUtil.dateTimeToEpoch1(HOMECOLLECT_START_TIME_str);

		String HOMECOLLECT_END_TIME_str = DateUtil.notificationepochToDate(end);
		long HOMECOLLECT_END_TIME_long = DateUtil.dateTimeToEpoch1(HOMECOLLECT_END_TIME_str);

		try
		{
			if (HOMECOLLECT_END_TIME_long == 0 &&  HOMECOLLECT_START_TIME_long ==0)
			{
				viewHolder.TrackHomeCollectExpert.setVisibility(View.VISIBLE);
				viewHolder.TrackHomeCollectExpert.setImageResource(R.drawable.gray_track_home_collect_expert);
				viewHolder.TrackHomeCollectExpert.setEnabled(false);
			}
			else if (HOMECOLLECT_END_TIME_long == 0 && currentMilli > HOMECOLLECT_START_TIME_long)
			{
				viewHolder.TrackHomeCollectExpert.setVisibility(View.VISIBLE);
				viewHolder.TrackHomeCollectExpert.setImageResource(R.drawable.track_home_collect_expert);
				viewHolder.TrackHomeCollectExpert.setEnabled(true);

				viewHolder.TrackHomeCollectExpert.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						if (!mOrdersHistoryDatas.get(i).getHOMECOLLECT_LINK().toString().equalsIgnoreCase(""))
						{
							Util.killTrackView();
							Intent intent = new Intent(mContext, TrackCollectExportActivity.class);
							intent.putExtra("HOMECOLLECT_LINK",
									mOrdersHistoryDatas.get(i).getHOMECOLLECT_LINK().toString());
							mContext.startActivity(intent);
						}
						else
						{
							// Toast.makeText(mContext,"HOME COLLECT Link not found",Toast.LENGTH_SHORT).show();
						}
					}
				});
			}
			else if (currentMilli < HOMECOLLECT_END_TIME_long && currentMilli > HOMECOLLECT_START_TIME_long)
			{

				viewHolder.TrackHomeCollectExpert.setVisibility(View.VISIBLE);
				viewHolder.TrackHomeCollectExpert.setImageResource(R.drawable.gray_track_home_collect_expert);
				viewHolder.TrackHomeCollectExpert.setEnabled(false);
			}
			else
			{
				viewHolder.TrackHomeCollectExpert.setVisibility(View.VISIBLE);
				viewHolder.TrackHomeCollectExpert.setImageResource(R.drawable.gray_track_home_collect_expert);
				viewHolder.TrackHomeCollectExpert.setEnabled(false);
			}
		}
		catch (NumberFormatException e)
		{

			viewHolder.TrackHomeCollectExpert.setVisibility(View.VISIBLE);
			viewHolder.TrackHomeCollectExpert.setImageResource(R.drawable.gray_track_home_collect_expert);
			viewHolder.TrackHomeCollectExpert.setEnabled(false);

		}

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/*try
		{
			if (currentMilli < HOMECOLLECT_END_TIME_long && currentMilli > HOMECOLLECT_START_TIME_long)
			{
				viewHolder.TrackHomeCollectExpert.setVisibility(View.VISIBLE);
				viewHolder.TrackHomeCollectExpert.setImageResource(R.drawable.track_home_collect_expert);
				viewHolder.TrackHomeCollectExpert.setEnabled(true);
		
				viewHolder.TrackHomeCollectExpert.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						if (!mOrdersHistoryDatas.get(i).getHOMECOLLECT_LINK().toString().equalsIgnoreCase(""))
						{
							Util.killTrackView();
							Intent intent = new Intent(mContext, TrackCollectExportActivity.class);
							intent.putExtra("HOMECOLLECT_LINK",
									mOrdersHistoryDatas.get(i).getHOMECOLLECT_LINK().toString());
							mContext.startActivity(intent);
						}
						else
						{
							// Toast.makeText(mContext,"HOME COLLECT Link not found",Toast.LENGTH_SHORT).show();
						}
					}
				});
			}
			else
			{
				viewHolder.TrackHomeCollectExpert.setVisibility(View.VISIBLE);
				viewHolder.TrackHomeCollectExpert.setImageResource(R.drawable.gray_track_home_collect_expert);
				viewHolder.TrackHomeCollectExpert.setEnabled(false);
			}
		}
		catch (NumberFormatException e)
		{
		
			viewHolder.TrackHomeCollectExpert.setVisibility(View.VISIBLE);
			viewHolder.TrackHomeCollectExpert.setImageResource(R.drawable.gray_track_home_collect_expert);
			viewHolder.TrackHomeCollectExpert.setEnabled(false);
		
		}*/
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/*if (epoch + 3600000 > currentMilli)
		{
			// if(diff>3600000){ //1 hr
			viewHolder.TrackHomeCollectExpert.setVisibility(View.VISIBLE);
			viewHolder.TrackHomeCollectExpert.setImageResource(R.drawable.track_home_collect_expert);
			viewHolder.TrackHomeCollectExpert.setEnabled(true);
		
			viewHolder.TrackHomeCollectExpert.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if (!mOrdersHistoryDatas.get(i).getHOMECOLLECT_LINK().toString().equalsIgnoreCase(""))
					{
						Util.killTrackView();
						Intent intent = new Intent(mContext, TrackCollectExportActivity.class);
						intent.putExtra("HOMECOLLECT_LINK",
								mOrdersHistoryDatas.get(i).getHOMECOLLECT_LINK().toString());
						mContext.startActivity(intent);
					}
					else
					{
						// Toast.makeText(mContext,"HOME COLLECT Link not found",Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
		else
		{
		
			viewHolder.TrackHomeCollectExpert.setVisibility(View.VISIBLE);
			viewHolder.TrackHomeCollectExpert.setImageResource(R.drawable.gray_track_home_collect_expert);
			viewHolder.TrackHomeCollectExpert.setEnabled(false);
		}*/
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		if (!mOrdersHistoryDatas.get(i).getPAYMENT_MODE().equalsIgnoreCase("null"))
		{
			viewHolder.cardtypeTVID.setText(mOrdersHistoryDatas.get(i).getPAYMENT_MODE() + "");
		}
		else
		{
			viewHolder.cardtypeTVID.setText("");
		}
		//        if (!mOrdersHistoryDatas.get(i).getPRDCT_NM().equalsIgnoreCase("null")) {
		//            viewHolder.testData1TVID.setText(Html.fromHtml(mOrdersHistoryDatas.get(i).getPRDCT_NM() + ""));
		//        } else {
		//            viewHolder.testData1TVID.setText("");
		//        }
		String discount = String.valueOf(mOrdersHistoryDatas.get(i).getDISCOUNT());
		if (!discount.equalsIgnoreCase("null"))
		{
			if (discount.equalsIgnoreCase("0.0"))
			{
				discountViewHide(viewHolder);
			}
			else
			{
				discountViewShow(viewHolder);
				viewHolder.discountAmountTVID
						.setText(Util.getIntegerToString(mOrdersHistoryDatas.get(i).getDISCOUNT() + ""));
			}
		}
		else
		{
			viewHolder.discountAmountTVID.setText("");
		}

		String totalamount = String.valueOf(mOrdersHistoryDatas.get(i).getGROSS_AMT());
		if (!totalamount.equalsIgnoreCase("null"))
		{
			viewHolder.totalAmountTVID.setText(Util.getIntegerToString(mOrdersHistoryDatas.get(i).getGROSS_AMT() + ""));
		}
		else
		{
			viewHolder.totalAmountTVID.setText("");
		}

		if (!mOrdersHistoryDatas.get(i).getPAYMENT_STATUS().equalsIgnoreCase("null"))
		{
			viewHolder.paidStatusTVID.setText(mOrdersHistoryDatas.get(i).getPAYMENT_STATUS() + "");
		}
		else
		{
			viewHolder.paidStatusTVID.setText("");
		}

		viewHolder.repeat.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{

				String city = SharedPrefsUtil.getStringPreference(mContext, "selectedcityid") + "";
				sendRequest(ApiRequestHelper.getRepeatOrder(mContext, mOrdersHistoryDatas.get(i).getORDERNO() + "",
						city + ""));
			}
		});

		viewHolder.cancel.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				pos = i;
				viewHolder_cancle = viewHolder;
				sendRequest(ApiRequestHelper.getCancelOrder(mContext, mOrdersHistoryDatas.get(i).getORDERNO() + ""));
			}
		});
		viewHolder.TrackOrder.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// Util.killOrders();
				Constants.isTrack = true;
				Constants.isTrackFromOrderList = true;
				SharedPrefsUtil.setStringPreference(mContext, "orderid",
						mOrdersHistoryDatas.get(i).getORDERNO().toString());

				Intent intent = new Intent(mContext, TrackOrderActivity.class);
				intent.putExtra("orderNo", mOrdersHistoryDatas.get(i).getORDERNO().toString());
				mContext.startActivity(intent);

			}
		});

		viewHolder.showDetails(selectedIndex == i);

	}

	private void discountViewHide(ViewHolder viewHolder)
	{
		viewHolder.lay_discountPaybleLLID.setVisibility(View.GONE);
	}

	private void discountViewShow(ViewHolder viewHolder)
	{
		viewHolder.lay_discountPaybleLLID.setVisibility(View.VISIBLE);
	}

	@Override
	public int getItemCount()
	{
		return mOrdersHistoryDatas.size();
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(mContext, request, mResponseListener);
	}

	public class ViewHolder extends RecyclerView.ViewHolder
	{
		public FrameLayout showdetails_layout;

		public LinearLayout expanded_layout;

		public FrameLayout expanded_details;

		Button repeat, cancel;

		ProductsAdapter mAdapter;

		RecyclerView mRecyclerView;

		ImageView TrackHomeCollectExpert, TrackOrder;

		private TextView order_date, order_no, patient_name, pateint_id, status;

		private TextView dateTVID, startTimeTVID, endTimeTVID, cardtypeTVID, testData1TVID, totalAmountTVID,
				paidStatusTVID;

		private int mIndex;

		private TextView discountAmountTVID;

		private LinearLayout lay_discountPaybleLLID;

		private View.OnClickListener mOnClickListener = new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				switch ((view.getId()))
				{

					case R.id.showdetails_layout:
					{
						selectedIndex = getIndex();
						showDetails(true);
					}
						break;

					case R.id.expanded_details:
					{
						selectedIndex = -1;
						showDetails(false);
					}
						break;
				}
			}
		};

		public ViewHolder(View view, int index)
		{
			super(view);

			setIndex(index);

			order_date = (TextView) view.findViewById(R.id.order_date);
			order_no = (TextView) view.findViewById(R.id.order_no);
			patient_name = (TextView) view.findViewById(R.id.patient_name);
			status = (TextView) view.findViewById(R.id.status);
			pateint_id = (TextView) view.findViewById(R.id.pateint_id);

			//Invisisible TextViews
			dateTVID = (TextView) view.findViewById(R.id.dateTVID);
			startTimeTVID = (TextView) view.findViewById(R.id.startTimeTVID);
			endTimeTVID = (TextView) view.findViewById(R.id.endTimeTVID);
			cardtypeTVID = (TextView) view.findViewById(R.id.cardtypeTVID);
			testData1TVID = (TextView) view.findViewById(R.id.testData1TVID);
			totalAmountTVID = (TextView) view.findViewById(R.id.totalAmountTVID);
			paidStatusTVID = (TextView) view.findViewById(R.id.paidStatusTVID);
			discountAmountTVID = (TextView) view.findViewById(R.id.discountAmountTVID);

			repeat = (Button) view.findViewById(R.id.repeat);
			cancel = (Button) view.findViewById(R.id.cancel);

			showdetails_layout = (FrameLayout) view.findViewById(R.id.showdetails_layout);
			expanded_details = (FrameLayout) view.findViewById(R.id.expanded_details);
			expanded_layout = (LinearLayout) view.findViewById(R.id.expanded_layout);
			lay_discountPaybleLLID = (LinearLayout) view.findViewById(R.id.lay_discountPaybleLLID);
			mRecyclerView = (RecyclerView) view.findViewById(R.id.orders_receyclerview);
			TrackHomeCollectExpert = (ImageView) view.findViewById(R.id.TrackHomeCollectExpert);
			TrackOrder = (ImageView) view.findViewById(R.id.TrackOrder);
			RecyclerView.LayoutManager layoutManager = new CustomLinearLayoutManager(mContext);
			layoutManager.setAutoMeasureEnabled(true);
			mRecyclerView.setLayoutManager(layoutManager);

			//                                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
			//                                mRecyclerView.setHasFixedSize(true);
			mAdapter = new ProductsAdapter(mContext);
			mRecyclerView.setAdapter(mAdapter);

			showdetails_layout.setOnClickListener(mOnClickListener);
			expanded_details.setOnClickListener(mOnClickListener);

		}

		public int getIndex()
		{
			return mIndex;
		}

		public void setIndex(int mIndex)
		{
			this.mIndex = mIndex;
		}

		public void showDetails(boolean show)
		{
			if (show)
			{
				expanded_layout.setVisibility(View.VISIBLE);

				ArrayList<ProductList> productLists = null;
				final OrdersHistoryData ordersHistoryData = mOrdersHistoryDatas.get(getIndex());
				if (!Validate.isNull(ordersHistoryData))
				{
					productLists = ordersHistoryData.getProductLists();
				}
				mAdapter.reload(productLists);
			}
			else
			{
				mAdapter.reload(null);
				expanded_layout.setVisibility(View.GONE);
			}
		}
	}

}
