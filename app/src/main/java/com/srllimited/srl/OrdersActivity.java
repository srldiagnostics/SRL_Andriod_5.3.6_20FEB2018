package com.srllimited.srl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.srllimited.srl.adapters.OrdersAdapter;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.OrdersHistoryData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.data.UserData;
import com.srllimited.srl.database.OrderDatabase;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.util.Log;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.utilities.Utility;
import com.srllimited.srl.widget.UISearchBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Ruchi Tiwari on 12/13/2016.
 */

public class OrdersActivity extends MenuBaseActivity implements View.OnClickListener
{
	private static final String TAG = "OrdersActivity";
	AlertDialog alert;

	public static Activity orders;
	final List<OrdersHistoryData> mLabData = new ArrayList<>();
	Button loginwithUserIDBTNID, viewreportWithFirstNameBTNID, viewreportWithMobileNoBTNID;
	Context context;
	Bitmap bitmap = null;
	TextView mHeader_name;
	List<UserData> userList = new ArrayList<UserData>();
	List<String> userListdata = new ArrayList<String>();
	ImageView dropdown;
	List<OrdersHistoryData> mOrdersHistoryDatas = new ArrayList<>();
	List<OrdersHistoryData> mLabFilteredData = new ArrayList<>();
	OrderDatabase orderDB;
	RecyclerView.Adapter mAdapter;
	TextView popupheader;
	String selectedListItem = "";
	private String SelectedFamilyId;
	LinearLayout hidePopup;
	private String selectedFamily = "";
	ImageView confirm, cancel;
	TextView login_name,name;
	private ListView listView;
	private CircleImageView guestIVID;
	UISearchBar.OnSearchListener mSearchLister = new UISearchBar.OnSearchListener()
	{
		@Override
		public void onClearSearch()
		{
			loadDefaultData();
		}

		@Override
		public void onSearchText(String text)
		{
			mLabFilteredData.clear();
			if (Validate.notEmpty(text))
			{
				for (OrdersHistoryData data : mOrdersHistoryDatas)

				{
					if (text.length() > 0)
					{
						if (isSearchData(data.getORDERNO(), text))
						{
							mLabFilteredData.add(data);
						}
					}
				}

				setData();
			}
			else
			{
				loadDefaultData();
			}

		}
	};
	private UISearchBar mSearchView;
	private RecyclerView mRecyclerView;
	private JSONArray array = null;
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			switch (request.getReferralCode())
			{
				case ORDERS:
				{
					array=null;
					JSONObject jsonObject =	 serverResponseData.getObjectData();
					try
					{
						if (jsonObject != null)
							array = jsonObject.getJSONArray("ORDER");
					}
					catch (Exception e)
					{

					}
					new AsyncTaskRunner().execute();
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
		super.addContentView(R.layout.my_order_list);
		orders = this;
		context = OrdersActivity.this;
		orderDB = new OrderDatabase(getApplicationContext());
		header_loc_name.setText("My Orders");
		header_loc_name.setEnabled(false);
		cancel = (ImageView) findViewById(com.srllimited.srl.R.id.cancel);
		confirm = (ImageView) findViewById(com.srllimited.srl.R.id.confirm);
		guestIVID = (CircleImageView) findViewById(R.id.guestIVID);
		mSearchView = (UISearchBar) findViewById(R.id.searchView);
		dropdown = (ImageView) findViewById(com.srllimited.srl.R.id.dropdown);
		mSearchView.setOnSearchListener(mSearchLister);

		popupheader = (TextView) findViewById(R.id.popup_header);
		hidePopup = (LinearLayout) findViewById(R.id.hidePopup);
		listView = (ListView) findViewById(com.srllimited.srl.R.id.popup_list);
		login_name = (TextView) findViewById(R.id.login_name);
		name = (TextView) findViewById(R.id.name);
		login_name.setText(Util.getStoredUsername(context));
		mRecyclerView = (RecyclerView) findViewById(R.id.orders_receyclerview);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		mRecyclerView.setHasFixedSize(true);
		mAdapter = new OrdersAdapter(context, mOrdersHistoryDatas);
		mRecyclerView.setAdapter(mAdapter);
		getData(Util.getStoredUsername(context));
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
		sendRequest(ApiRequestHelper.getOrders(context, Util.getStoredUsername(context)));
	}

	@Override
	public void onClick(View v)
	{
		Animation bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);
		int id = v.getId();
		switch (id)
		{
			case R.id.dropdown:
				popupheader.setText("Select Member");
				getFamilyList();
				setPopupListAdapter(userListdata);
				break;

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
						mOrdersHistoryDatas.clear();

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
						sendRequest(ApiRequestHelper.getOrders(context, SelectedFamilyId));
					}
				}

				hidePopup.startAnimation(bottomDown);
				hidePopup.setVisibility(View.INVISIBLE);

				break;
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
				//setData(userData);
			}
		}
		catch (Exception e)
		{

		}
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
	private void setPopupListAdapter(List<String> popupLstItems)
	{

		ArrayAdapter<String> ad = new ArrayAdapter<String>(this, R.layout.textcenter, R.id.textItem, userListdata);
		listView.setAdapter(ad);

		Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

		hidePopup.startAnimation(bottomUp);
		hidePopup.setVisibility(View.VISIBLE);

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
	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	private void setOrderFromAsync(JSONArray jArray)
	{
		if (Validate.isNull(jArray))
			return;

		mLabData.clear();
		mOrdersHistoryDatas.clear();

		for (int i = 0; i < jArray.length(); i++)
		{
			JSONObject jsonObject = jArray.optJSONObject(i);
			OrdersHistoryData ordersHistoryData = OrdersHistoryData.init(jsonObject);
			if (Validate.notNull(ordersHistoryData))
			{

				String orderNo = ordersHistoryData.getORDERNO();
				if (Validate.notNull(orderDB))
				{

					OrdersHistoryData storedOrdersHistoryData = null;
					try
					{
						storedOrdersHistoryData = orderDB.getOrder_object(orderNo);
					}
					catch (Exception e)
					{
					}

					if (Validate.notNull(storedOrdersHistoryData) && storedOrdersHistoryData.getORDERNO() != null)
					{
						orderDB.updateOrder(ordersHistoryData);
					}
					else
					{
						orderDB.createHeaderData(ordersHistoryData);
					}
				}

				Log.d(TAG, "OrderNo: " + orderNo);

				mOrdersHistoryDatas.add(ordersHistoryData);

			}
		}

		sortByDate();
		mLabData.addAll(mOrdersHistoryDatas);
	}

	//    private void sortByDate() {
	//        Collections.sort(mOrdersHistoryDatas, new Comparator<OrdersHistoryData>() {
	//            @Override
	//            public int compare(OrdersHistoryData o1, OrdersHistoryData o2) {
	//
	//                final long d1 = o1.getENTERED_ON();
	//                final long d2 = o2.getENTERED_ON();
	//                return (d1 > d2) ? -1 : (d1 > d2) ? 1 : 0;
	//
	//
	//            }
	//        });
	//    }

	private void setOrdersAdapter()
	{

		if (Validate.notNull(mOrdersHistoryDatas))
			mAdapter.notifyDataSetChanged();

	}

	private void sortByDate()
	{
		Collections.sort(mOrdersHistoryDatas, new Comparator<OrdersHistoryData>()
		{
			@Override
			public int compare(OrdersHistoryData o1, OrdersHistoryData o2)
			{

				final long d1 = o1.getENTERED_ON();
				final long d2 = o2.getENTERED_ON();
				return Double.compare(d2, d1);

			}
		});

		//        Collections.reverse(mOrdersHistoryDatas);
	}

	private boolean isSearchFirstCharData(String name, String searchTxt)
	{
		return (Validate.notEmpty(name) && name.toLowerCase().charAt(0) == searchTxt.toLowerCase().charAt(0));
	}

	private boolean isSearchData(String name, String searchTxt)
	{
		return (Validate.notEmpty(name) && name.toLowerCase().contains(searchTxt.toLowerCase()));
	}

	private void loadDefaultData()
	{
		mOrdersHistoryDatas.clear();
		for (OrdersHistoryData labLocatorsData : mLabData)
		{
			mOrdersHistoryDatas.add(labLocatorsData);
		}
		sortByDate();
		mAdapter.notifyDataSetChanged();
	}

	private void setData()
	{
		try
		{

			mOrdersHistoryDatas.clear();

			mOrdersHistoryDatas.addAll(mLabFilteredData);

		}
		catch (Exception e)
		{

		}

		sortByDate();
		mAdapter.notifyDataSetChanged();
	}

	@Override
	protected void onDestroy()
	{

		super.onDestroy();
	}
	public void showSettingsAlert()
	{

		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(OrdersActivity.this,
				android.app.AlertDialog.THEME_HOLO_LIGHT);
		builder.setMessage("No Data").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{

				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				OrdersActivity.this.startActivity(intent);
				dialog.cancel();
				finish();
			}
		});

		alert = builder.create();
		alert.show();
	}
	private class AsyncTaskRunner extends AsyncTask<String, String, String>
	{

		private String resp;

		@Override
		protected String doInBackground(String... params)
		{
			setOrderFromAsync(array);
			return resp;
		}

		@Override
		protected void onPostExecute(String result)
		{

			if (mOrdersHistoryDatas != null)
			{
				sortByDate();
				setOrdersAdapter();
			}
			if(mOrdersHistoryDatas.size()<=0)
			{
				//showSettingsAlert();
				Utility.NeResponseError(OrdersActivity.this, "No orders booked");
			}
			Util.hideProgressDialog();
		}

		@Override
		protected void onPreExecute()
		{
			Util.hideProgressDialog();
			Util.showProgressDialog(context, "Fetching Orders...");
		}

		@Override
		protected void onProgressUpdate(String... text)
		{

		}
	}
}
