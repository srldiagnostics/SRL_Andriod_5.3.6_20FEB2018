package com.srllimited.srl.adapters;

/**
 * Created by Ruchi Tiwari on 02/12/16.
 */

import java.util.ArrayList;
import java.util.List;

import com.srllimited.srl.R;
import com.srllimited.srl.data.AccessionListData;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TrendsAdapter extends RecyclerView.Adapter<TrendsAdapter.ViewHolder>
{
	private Context mContext;

	private List<AccessionListData> mAccessionListDatas = new ArrayList<>();

	public TrendsAdapter(Context context, List<AccessionListData> accessionListDatas)
	{
		this.mContext = context;
		this.mAccessionListDatas = accessionListDatas;

	}

	@Override
	public TrendsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.health_tracker_child, viewGroup,
				false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final TrendsAdapter.ViewHolder viewHolder, final int i)
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
					/*Util.killTrendsChart();
					Intent intent = new Intent(mContext, TrendsChartActivity.class);
					//Intent intent = new Intent(mContext, TestActivity.class);
					ReportsData reportsData = null;
					if (mAccessionListDatas.get(i).getReportsDatas() != null && mAccessionListDatas.get(i).getReportsDatas().size() > 0)
					{
						reportsData = mAccessionListDatas.get(i).getReportsDatas().get(0);
					}
					if (reportsData != null && reportsData.getCPT_CODE() != null && !reportsData.getCPT_CODE().equalsIgnoreCase("null"))
					{
						intent.putExtra("obj1", reportsData);
						mContext.startActivity(intent);
					}*/

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
