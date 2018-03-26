package com.srllimited.srl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.srllimited.srl.adapters.PromoCodesAdapter;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.PromoCodesData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class PromoCodes extends AppCompatActivity
{
	private static final String DEVICE_ID = "DEVICE_ID";

	private static final String COUPON_CODE = "COUPON_CODE";

	private static final String ISSUE_DATE = "ISSUE_DATE";

	private static final String SCHEME_ID = "SCHEME_ID";

	private static final String DISCOUNT_FLAG = "DISCOUNT_FLAG";

	private static final String DISCOUNT = "DISCOUNT";

	private static final String VALID_FROM = "VALID_FROM";

	private static final String VALID_TO = "VALID_TO";

	private static final String NOTIFICATION_MSG = "NOTIFICATION_MSG";

	public static Activity promocodes;

	private ImageView iv_close;

	private Context mContext;

	private RecyclerView mRecyclerView;

	private RecyclerView.Adapter mAdapter;

	private List<PromoCodesData> mPromoCodesDataList = new ArrayList<>();
	private View.OnClickListener mOnClickListener = new View.OnClickListener()
	{
		@Override
		public void onClick(final View view)
		{
			switch (view.getId())
			{
				case R.id.iv_close:
				{
					finish();
				}
					break;

				default:
					break;
			}
		}
	};
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			switch (request.getReferralCode())
			{
				case GET_PROMO:
				{
					setPromoCodesData(serverResponseData.getArrayData());
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
		setContentView(R.layout.needpromo);
		mContext = PromoCodes.this;
		promocodes = this;
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);

		findViewById(R.id.iv_close).setOnClickListener(mOnClickListener);

		iv_close = (ImageView) toolbar.findViewById(R.id.iv_close);

		iv_close.setOnClickListener(mOnClickListener);

		mRecyclerView = (RecyclerView) findViewById(R.id.recysclerlist);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		mRecyclerView.setHasFixedSize(true);
		mAdapter = new PromoCodesAdapter(mContext, mPromoCodesDataList);
		mRecyclerView.setAdapter(mAdapter);

		sendRequest(ApiRequestHelper.getPromoCodes(mContext, Constants.devicetobepassed,
				Util.getStoredUsername(mContext), "9704683480"));
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	private void setPromoCodesData(JSONArray jArray)
	{
		mPromoCodesDataList.clear();
		if (Validate.notNull(jArray))
		{
			for (int i = 0; i < jArray.length(); i++)
			{
				try
				{
					JSONObject jObject = jArray.getJSONObject(i);
					PromoCodesData promoCodes = new PromoCodesData();
					promoCodes.setDEVICE_ID(jObject.getString(DEVICE_ID));
					promoCodes.setCOUPON_CODE(jObject.getString(COUPON_CODE));
					promoCodes.setISSUE_DATE(jObject.getLong(ISSUE_DATE));
					promoCodes.setSCHEME_ID(jObject.getInt(SCHEME_ID));
					promoCodes.setDISCOUNT_FLAG(jObject.getString(DISCOUNT_FLAG));
					promoCodes.setDISCOUNT(jObject.getDouble(DISCOUNT));
					promoCodes.setVALID_FROM(jObject.getLong(VALID_FROM));
					promoCodes.setVALID_TO(jObject.getLong(VALID_TO));
					promoCodes.setNOTIFICATION_MSG(jObject.getString(NOTIFICATION_MSG));
					mPromoCodesDataList.add(promoCodes);
				}
				catch (JSONException e)
				{
					e.printStackTrace();
				}
			}
		}

		if (mPromoCodesDataList != null && mPromoCodesDataList.size() > 0)
		{

			mAdapter.notifyDataSetChanged();
		}
		else
		{
			NeResponseError("No Promocode available");
		}
	}

	private void NeResponseError(String msg)
	{

		try
		{
			final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
			alertDialogBuilder.setTitle(msg);
			alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface arg0, int arg1)
				{
					arg0.dismiss();
					finish();

				}
			});

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();

		}
		catch (Exception e)
		{

		}
	}
}