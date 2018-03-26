package com.srllimited.srl.adapters;

/**
 * Created by Ruchi Tiwari on 02/12/16.
 */

import java.util.ArrayList;
import java.util.List;

import com.srllimited.srl.MyCartActivity;
import com.srllimited.srl.R;
import com.srllimited.srl.data.PromoCodesData;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.StringUtil;
import com.srllimited.srl.util.Validate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PromoCodesAdapter extends RecyclerView.Adapter<PromoCodesAdapter.ViewHolder>
{
	private Context mContext;

	private List<PromoCodesData> mPromoCodesDatas = new ArrayList<>();

	public PromoCodesAdapter(Context context, List<PromoCodesData> promoCodesDatas)
	{
		this.mContext = context;
		this.mPromoCodesDatas = promoCodesDatas;
	}

	@Override
	public PromoCodesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.need_promo_list_item, viewGroup,
				false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final PromoCodesAdapter.ViewHolder viewHolder, final int i)
	{

		if (viewHolder != null)
		{
			if (mPromoCodesDatas.get(i) != null)
			{

				viewHolder.coupon.setText(mPromoCodesDatas.get(i).getCOUPON_CODE() + "");

				if (Validate.notEmpty(mPromoCodesDatas.get(i).getNOTIFICATION_MSG())
						&& !mPromoCodesDatas.get(i).getNOTIFICATION_MSG().equalsIgnoreCase("null"))
				{
					viewHolder.desc.setVisibility(View.VISIBLE);
					viewHolder.desc
							.setText(StringUtil.getValidString(mPromoCodesDatas.get(i).getNOTIFICATION_MSG()) + "");
				}
				else

				{
					viewHolder.desc.setVisibility(View.GONE);
				}
				try
				{

					String stDate = RestApiCallUtil.epochToDate(mPromoCodesDatas.get(i).getVALID_TO());

					viewHolder.expdate.setText("Expires " + stDate + "");
				}
				catch (Exception e)
				{

				}
			}

			viewHolder.apply.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(final View view)
				{
					Intent intent = new Intent();

					try
					{

						//intent.putExtra("disc",  mPromoCodesDatas.get(i).getDISCOUNT());
						intent.putExtra("couponcode", mPromoCodesDatas.get(i).getCOUPON_CODE());
					}
					catch (Exception e)
					{

					}
					((Activity) mContext).setResult(MyCartActivity.RESULT_PROMO, intent);
					((Activity) mContext).finish();

				}
			});

		}
	}

	@Override
	public int getItemCount()
	{
		if (mPromoCodesDatas.size() > 0)
		{
			return mPromoCodesDatas.size();
		}
		else
		{

			return 0;
		}

	}

	public class ViewHolder extends RecyclerView.ViewHolder
	{
		ImageView pdfBTNID;
		private TextView apply, coupon, desc, expdate;

		public ViewHolder(View view)
		{
			super(view);
			coupon = (TextView) view.findViewById(R.id.coupon);
			desc = (TextView) view.findViewById(R.id.desc);
			expdate = (TextView) view.findViewById(R.id.expdate);
			apply = (TextView) view.findViewById(R.id.apply);

		}

	}
}
