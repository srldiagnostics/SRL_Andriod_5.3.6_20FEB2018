package com.srllimited.srl.adapters;

/**
 * Created by Ruchi Tiwari on 02/12/16.
 */

import java.util.ArrayList;
import java.util.List;

import com.srllimited.srl.HealthChartActivity;
import com.srllimited.srl.R;
import com.srllimited.srl.data.AccessionListData;
import com.srllimited.srl.data.ReportsData;
import com.srllimited.srl.util.Util;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HealthTrackerAdapter extends RecyclerView.Adapter<HealthTrackerAdapter.ViewHolder>
{
	private Context mContext;

	private List<AccessionListData> mAccessionListDatas = new ArrayList<>();

	public HealthTrackerAdapter(Context context, List<AccessionListData> accessionListDatas)
	{
		this.mContext = context;
		this.mAccessionListDatas = accessionListDatas;

	}

	@Override
	public HealthTrackerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.health_tracker_child, viewGroup,
				false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final HealthTrackerAdapter.ViewHolder viewHolder, final int i)
	{

		if (viewHolder != null)
		{
			if (mAccessionListDatas.get(i) != null)

			{
				if (mAccessionListDatas.get(i).getAcc_id() != null
						&& !mAccessionListDatas.get(i).getAcc_id().equalsIgnoreCase("null"))
				{
					viewHolder.genericname.setText(mAccessionListDatas.get(i).getAcc_id());
				}
			}

			viewHolder.graphrel.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					Util.killHealthChart();
					Intent intent = new Intent(mContext, HealthChartActivity.class);
					ReportsData reportsData = null;
					if (mAccessionListDatas.get(i).getReportsDatas() != null
							&& mAccessionListDatas.get(i).getReportsDatas().size() > 0)
					{
						reportsData = mAccessionListDatas.get(i).getReportsDatas().get(0);
					}
					if (reportsData != null && reportsData.getCPT_CODE() != null
							&& !reportsData.getCPT_CODE().equalsIgnoreCase("null"))
					{
						intent.putExtra("PRDCT_CD", reportsData.getPRDCT_ID());
						intent.putExtra("CPT_CODE", reportsData.getCPT_CODE());
						mContext.startActivity(intent);
					}

				}
			});

		}

	}

	@Override
	public int getItemCount()
	{
		if (mAccessionListDatas.size() > 0)
		{
			return mAccessionListDatas.size();
		}
		else
		{

			return 0;
		}

	}

	public class ViewHolder extends RecyclerView.ViewHolder
	{
		private TextView genericname;

		private RelativeLayout graphrel;

		public ViewHolder(View view)
		{
			super(view);
			graphrel = (RelativeLayout) view.findViewById(R.id.graphrel);
			genericname = (TextView) view.findViewById(R.id.genericname);
		}

	}

}
