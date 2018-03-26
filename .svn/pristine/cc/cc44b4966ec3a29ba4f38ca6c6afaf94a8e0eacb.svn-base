package com.srllimited.srl.adapters;

/**
 * Created by Ruchi Tiwari on 02/12/16.
 */

import java.util.List;

import com.srllimited.srl.R;
import com.srllimited.srl.TrackOrderActivity;
import com.srllimited.srl.data.TrackOrderData;
import com.srllimited.srl.util.Validate;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class TrackOrderAdapter extends RecyclerView.Adapter<TrackOrderAdapter.ViewHolder>
{
	private Context mContext;

	private List<TrackOrderData> mTrackOrderDatas;

	private String ORDERNO = "ORDERNO";

	private String ORDER_DATE = "ORDER_DATE";

	private String CONFIRMED_DATE = "CONFIRMED_DATE";

	private String CANCELLED_DATE = "CANCELLED_DATE";

	private String PROCESS_DATE = "PROCESS_DATE";

	private String REPORT_DATE = "REPORT_DATE";

	private boolean isCacel = false;

	public TrackOrderAdapter(Context context, List<TrackOrderData> trackOrderDatas)
	{
		this.mContext = context;

		this.mTrackOrderDatas = trackOrderDatas;
	}

	@Override
	public TrackOrderAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tracking_step, viewGroup, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final TrackOrderAdapter.ViewHolder viewHolder, final int i)
	{
		if (viewHolder != null)
		{
			if (mTrackOrderDatas.get(i) != null)
			{
				if (!isCacel)
				{
					viewHolder.info.setVisibility(View.VISIBLE);
					viewHolder.line.setVisibility(View.VISIBLE);
					viewHolder.date.setVisibility(View.VISIBLE);
					viewHolder.circle.setVisibility(View.VISIBLE);
					viewHolder.info.setText(mTrackOrderDatas.get(i).getSTATUS() + "");
					viewHolder.info.setTextColor(
							ContextCompat.getColor(mContext, R.color.collectionText/*R.color.lightGreen*/));

					viewHolder.line.setVisibility(View.VISIBLE);
					viewHolder.circle.setImageResource(R.drawable.circle_green);

					if (i == mTrackOrderDatas.size() - 1)
					{
						viewHolder.line.setVisibility(View.GONE);
					}

					String sDate = mTrackOrderDatas.get(i).getS_DATE() + "";

					if (sDate != null && sDate.equalsIgnoreCase(ORDERNO)
							&& Validate.notEmpty(TrackOrderActivity.orderno)
							&& !TrackOrderActivity.orderno.equalsIgnoreCase("null"))
					{

						viewHolder.date.setText(TrackOrderActivity.orderno);
						viewHolder.date.setTextColor(ContextCompat.getColor(mContext, R.color.collectionText));
						viewHolder.circle.setImageResource(R.drawable.circle);
					}

					viewHolder.date.setText("");

					if (sDate != null && sDate.equalsIgnoreCase(ORDER_DATE)
							&& Validate.notEmpty(TrackOrderActivity.orderdate)
							&& !TrackOrderActivity.orderdate.equalsIgnoreCase("null"))
					{
						viewHolder.date.setText(TrackOrderActivity.orderdate);
						viewHolder.date.setTextColor(ContextCompat.getColor(mContext, R.color.collectionText));
						viewHolder.circle.setImageResource(R.drawable.circle);
					}

					if (sDate != null && sDate.equalsIgnoreCase(CONFIRMED_DATE)
							&& Validate.notEmpty(TrackOrderActivity.confirmedDate)
							&& !TrackOrderActivity.confirmedDate.equalsIgnoreCase("null"))
					{
						viewHolder.date.setText(TrackOrderActivity.confirmedDate);
						viewHolder.date.setTextColor(ContextCompat.getColor(mContext, R.color.collectionText));
						viewHolder.circle.setImageResource(R.drawable.circle);
					}

					if (sDate != null && sDate.equalsIgnoreCase(CANCELLED_DATE)
							&& Validate.notEmpty(TrackOrderActivity.cacelDate)
							&& !TrackOrderActivity.cacelDate.equalsIgnoreCase("null"))
					{
						viewHolder.date.setText(TrackOrderActivity.cacelDate);
						viewHolder.date.setTextColor(ContextCompat.getColor(mContext, R.color.collectionText));
						viewHolder.circle.setImageResource(R.drawable.circle);
						viewHolder.line.setVisibility(View.GONE);
						isCacel = true;
					}

					if (sDate != null && sDate.equalsIgnoreCase(PROCESS_DATE)
							&& Validate.notEmpty(TrackOrderActivity.processDate)
							&& !TrackOrderActivity.processDate.equalsIgnoreCase("null"))
					{
						viewHolder.date.setText(TrackOrderActivity.processDate);
						viewHolder.date.setTextColor(ContextCompat.getColor(mContext, R.color.collectionText));
						viewHolder.circle.setImageResource(R.drawable.circle);
					}

					if (sDate != null && sDate.equalsIgnoreCase(REPORT_DATE)
							&& Validate.notEmpty(TrackOrderActivity.reportDate)
							&& !TrackOrderActivity.reportDate.equalsIgnoreCase("null"))
					{
						viewHolder.date.setText(TrackOrderActivity.reportDate);
						viewHolder.date.setTextColor(ContextCompat.getColor(mContext, R.color.collectionText));
						viewHolder.circle.setImageResource(R.drawable.circle);
					}

				}
				else
				{
					viewHolder.info.setVisibility(View.GONE);
					viewHolder.line.setVisibility(View.GONE);
					viewHolder.date.setVisibility(View.GONE);
					viewHolder.circle.setVisibility(View.GONE);
				}
			}
		}
	}

	@Override
	public int getItemCount()
	{
		if (mTrackOrderDatas.size() > 0)
		{
			return mTrackOrderDatas.size();
		}
		else
		{
			return 0;
		}

	}

	public class ViewHolder extends RecyclerView.ViewHolder
	{
		public final TextView date, info;

		public final View line;

		public final ImageView circle;

		public ViewHolder(View view)
		{
			super(view);

			date = (TextView) view.findViewById(R.id.date);
			info = (TextView) view.findViewById(R.id.info);
			line = view.findViewById(R.id.line);
			circle = (ImageView) view.findViewById(R.id.circle);
		}

	}

}
