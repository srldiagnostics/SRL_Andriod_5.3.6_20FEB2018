package com.srllimited.srl.adapters;

/**
 * Created by Ruchi Tiwari on 02/12/16.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.srllimited.srl.R;
import com.srllimited.srl.data.ReportParentList;
import com.srllimited.srl.data.ReportsData;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ParentListAdapter extends RecyclerView.Adapter<ParentListAdapter.ViewHolder>
{
	private Context mContext;

	private List<ReportParentList> mReportParentLists = new ArrayList<>();

	public ParentListAdapter(Context context, List<ReportParentList> accessionListDatas)
	{
		this.mContext = context;
		this.mReportParentLists = accessionListDatas;
	}

	@Override
	public ParentListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reports_expandable_list_parent_item,
				viewGroup, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ParentListAdapter.ViewHolder viewHolder, final int i)
	{

		if (viewHolder != null)
		{
			if (mReportParentLists != null && mReportParentLists.size() > 0)
			{
				viewHolder.parent_id.setText(mReportParentLists.get(i).getParentid());
			}

			try
			{
				if (mReportParentLists.size() > 0 && mReportParentLists.get(i).getReportsDataList() != null)
				{
					if (mReportParentLists.get(0).getReportsDataList().size() > 0)
					{
						if (mReportParentLists.get(0).getReportsDataList().get(0).getELMNT_RSLT_TYP() != null
								&& mReportParentLists.get(0).getReportsDataList().get(0).getELMNT_RSLT_TYP()
										.equalsIgnoreCase("F"))
						{
							viewHolder.rangeBtn.setVisibility(View.GONE);
						}
						else
							viewHolder.rangeBtn.setVisibility(View.VISIBLE);
					}
				}

				Collections.sort(mReportParentLists.get(i).getReportsDataList(), new Comparator<ReportsData>()
				{
					@Override
					public int compare(ReportsData o1, ReportsData o2)
					{

						final int d1 = o1.getSR_NO();
						final int d2 = o2.getSR_NO();
						return (d1 < d2) ? -1 : (d1 < d2) ? 1 : 0;

					}
				});
			}
			catch (Exception e)
			{

			}

			viewHolder.mRecyclerView
					.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
			viewHolder.mRecyclerView.setHasFixedSize(true);

			viewHolder.mAdapter = new ChildListAdapter(mContext, mReportParentLists.get(i).getReportsDataList());
			viewHolder.mRecyclerView.setAdapter(viewHolder.mAdapter);

			viewHolder.viewDetailsIVID.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{

					if (viewHolder.expandable_layout.getVisibility() == View.VISIBLE)
					{
						viewHolder.viewDetailsIVID.setBackgroundResource(R.drawable.add_circle);
						viewHolder.expandable_layout.setVisibility(View.GONE);
					}
					else
					{
						viewHolder.viewDetailsIVID.setBackgroundResource(R.drawable.remove_circle);
						viewHolder.expandable_layout.setVisibility(View.VISIBLE);

					}
				}
			});

		}
	}

	@Override
	public int getItemCount()
	{
		if (mReportParentLists.size() > 0)
		{
			return mReportParentLists.size();
		}
		else
		{

			return 0;
		}

	}

	public class ViewHolder extends RecyclerView.ViewHolder
	{
		ImageView viewDetailsIVID;
		RecyclerView.Adapter mAdapter;
		Button rangeBtn;
		LinearLayout expandable_layout;
		private TextView parent_id;
		private RecyclerView mRecyclerView;

		public ViewHolder(View view)
		{
			super(view);
			mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
			parent_id = (TextView) view.findViewById(R.id.parent_id);
			viewDetailsIVID = (ImageView) view.findViewById(R.id.viewDetailsIVID);
			expandable_layout = (LinearLayout) view.findViewById(R.id.expandable_layout);
			rangeBtn = (Button) view.findViewById(R.id.rangeBtn);
		}

	}
}
