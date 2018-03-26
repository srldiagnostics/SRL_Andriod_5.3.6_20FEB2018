package com.srllimited.srl.adapters;

/**
 * Created by Ruchi Tiwari on 02/12/16.
 */

import java.util.ArrayList;
import java.util.List;

import com.srllimited.srl.HealthChartActivity;
import com.srllimited.srl.R;
import com.srllimited.srl.data.ReportsData;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ChildListAdapter extends RecyclerView.Adapter<ChildListAdapter.ViewHolder>
{
	String result = "";
	double min = 0;
	double max = 0;
	String RSLT_NORMAL_FLAG = "";
	private Context mContext;
	private List<ReportsData> mReportParentLists = new ArrayList<>();

	public ChildListAdapter(Context context, List<ReportsData> accessionListDatas)
	{
		this.mContext = context;
		this.mReportParentLists = accessionListDatas;
	}

	@Override
	public ChildListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myreport_category_data_list, viewGroup,
				false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ChildListAdapter.ViewHolder viewHolder, final int i)
	{

		if (viewHolder != null)
		{
			if (mReportParentLists != null)
			{

				viewHolder.chamicalnameTVID.setText(mReportParentLists.get(i).getELMNT_NAME());

				try
				{

					if (Validate.notEmpty(mReportParentLists.get(i).getRSLT()))
					{
						result = mReportParentLists.get(i).getRSLT();
					}

					/* if (Validate.notEmpty(mReportParentLists.get(i).getELMNT_MIN_RANGE())) {
					
					    min = Double.valueOf(mReportParentLists.get(i).getELMNT_MIN_RANGE());
					}
					if (Validate.notEmpty(mReportParentLists.get(i).getELMNT_MAX_RANGE())) {
					    max = Double.valueOf(mReportParentLists.get(i).getELMNT_MAX_RANGE());
					}*/
					if (!(mReportParentLists.get(i).getRSLT_NORMAL_FLAG().equalsIgnoreCase("")))
					{
						RSLT_NORMAL_FLAG = mReportParentLists.get(i).getRSLT_NORMAL_FLAG();
					}

				}
				catch (Exception e)
				{

				}

				/*   if (result >= min && result <= max) {
				    viewHolder.millletersTVID.setTextColor(mContext.getResources().getColor(R.color.black));
				} else {
				    viewHolder.millletersTVID.setTextColor(mContext.getResources().getColor(R.color.red_dark));
				}*/

				if (RSLT_NORMAL_FLAG.equalsIgnoreCase("Y"))
				{
					viewHolder.millletersTVID.setTextColor(mContext.getResources().getColor(R.color.black));
				}
				else
				{
					viewHolder.millletersTVID.setTextColor(mContext.getResources().getColor(R.color.red_dark));
				}
				{
					if (mReportParentLists.get(i).getELMNT_RSLT_TYP() != null
							&& mReportParentLists.get(i).getELMNT_RSLT_TYP().equalsIgnoreCase("F"))
					{
						viewHolder.millletersTVID
								.setText(Util.fromHtml(mReportParentLists.get(i).getRSLT()).toString());
					}
					else
					{
						if (mReportParentLists.get(i).getRSLT() != null
								&& mReportParentLists.get(i).getELMNT_RSLT_UNIT() != null
								&& !mReportParentLists.get(i).getELMNT_RSLT_UNIT().equalsIgnoreCase("null"))
						{
							viewHolder.millletersTVID.setText(mReportParentLists.get(i).getRSLT() + " "
									+ mReportParentLists.get(i).getELMNT_RSLT_UNIT() + "");
						}
						else
						{
							if (mReportParentLists.get(i).getRSLT() != null)
							{
								viewHolder.millletersTVID.setText(mReportParentLists.get(i).getRSLT());
							}
						}
					}
				}

				if (mReportParentLists.get(i).getPRNT_RNG_TXT() != null
						&& !mReportParentLists.get(i).getPRNT_RNG_TXT().equalsIgnoreCase("null"))
				{
					viewHolder.rangeTVID.setText(Html.fromHtml(mReportParentLists.get(i).getPRNT_RNG_TXT()));
				}

				String resultyp = "";
				try
				{
					if (mReportParentLists.get(i).getELMNT_RSLT_TYP() != null)
						resultyp = mReportParentLists.get(i).getELMNT_RSLT_TYP() + "";
				}
				catch (Exception e)
				{

				}
				//                if (resultyp != null && resultyp.equalsIgnoreCase("N") && Validation.isNumericString(mReportParentLists.get(i).getCPT_CODE())) {
				//                    viewHolder.rangeTVID.setVisibility(View.VISIBLE);
				//                } else {
				//                    viewHolder.rangeTVID.setVisibility(View.GONE);
				//                }

				if (resultyp != null && resultyp.equalsIgnoreCase("F"))
				{
					viewHolder.rangeTVID.setVisibility(View.GONE);
					viewHolder.itemview.setVisibility(View.GONE);
				}
				else
				{
					viewHolder.itemview.setVisibility(View.VISIBLE);
					viewHolder.rangeTVID.setVisibility(View.VISIBLE);
				}

				String cpt = mReportParentLists.get(i).getCPT_CODE() + "";

				if (cpt != null && !cpt.equalsIgnoreCase("null") && resultyp != null)
				{
					if (resultyp.equalsIgnoreCase("X") || resultyp.equalsIgnoreCase("N"))
						viewHolder.graph.setVisibility(View.VISIBLE);
					else
						viewHolder.graph.setVisibility(View.GONE);
				}
				else
				{
					viewHolder.graph.setVisibility(View.GONE);
				}

				viewHolder.graph.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(View view)
					{
						Util.killHealthChart();
						Intent intent = new Intent(mContext, HealthChartActivity.class);

						if (mReportParentLists.get(i).getCPT_CODE() != null
								&& !mReportParentLists.get(i).getCPT_CODE().equalsIgnoreCase("null"))
						{
							intent.putExtra("PRDCT_CD", mReportParentLists.get(i).getPRDCT_ID());
							intent.putExtra("CPT_CODE", mReportParentLists.get(i).getCPT_CODE());
							mContext.startActivity(intent);
						}

					}
				});
			}
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
		View itemview;
		private TextView parent_id, chamicalnameTVID, millletersTVID, rangeTVID, graph;

		public ViewHolder(View view)
		{
			super(view);
			chamicalnameTVID = (TextView) view.findViewById(R.id.chamicalnameTVID);
			millletersTVID = (TextView) view.findViewById(R.id.millletersTVID);
			rangeTVID = (TextView) view.findViewById(R.id.rangeTVID);
			graph = (TextView) view.findViewById(R.id.graph);
			itemview = (View) view.findViewById(R.id.itemview);
		}

	}
}
