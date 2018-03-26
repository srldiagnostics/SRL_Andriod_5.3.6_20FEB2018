package com.srllimited.srl.adapters;

/**
 * Created by Ruchi Tiwari on 02/12/16.
 */

import java.util.ArrayList;
import java.util.List;

import com.srllimited.srl.R;
import com.srllimited.srl.data.PatientDetailsData;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PatientDetailAdapter extends RecyclerView.Adapter<PatientDetailAdapter.ViewHolder>
{
	private Context mContext;

	private List<PatientDetailsData> mPatientDetailsDatas = new ArrayList<>();

	private String lastname = "";

	public PatientDetailAdapter(Context context, List<PatientDetailsData> patientDetailsDatases)
	{
		this.mContext = context;
		this.mPatientDetailsDatas = patientDetailsDatases;
	}

	@Override
	public PatientDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_detail_child, viewGroup,
				false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final PatientDetailAdapter.ViewHolder viewHolder, final int i)
	{

		if (viewHolder != null)
		{
			if (mPatientDetailsDatas.get(i) != null)

			{
				if (mPatientDetailsDatas.get(i).getPTNT_CD() != null
						&& !mPatientDetailsDatas.get(i).getPTNT_CD().equalsIgnoreCase("null"))
				{
					viewHolder.txtptntcd.setText(mPatientDetailsDatas.get(i).getPTNT_CD());
				}

				if (mPatientDetailsDatas.get(i).getFIRST_NAME() != null
						&& !mPatientDetailsDatas.get(i).getFIRST_NAME().equalsIgnoreCase("null"))
				{
					if (mPatientDetailsDatas.get(i).getLAST_NAME().toString().trim().equalsIgnoreCase("null"))
					{
						lastname = "";
					}
					else
					{
						lastname = mPatientDetailsDatas.get(i).getLAST_NAME().toString().trim();
					}
					viewHolder.txtpatientName
							.setText(mPatientDetailsDatas.get(i).getFIRST_NAME().toString().trim() + " " + lastname);
				}
			}

			/*viewHolder.graphrel.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					Util.killTrendsChart();
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
					}
			
				}
			});*/

		}

	}

	@Override
	public int getItemCount()
	{
		if (mPatientDetailsDatas.size() > 0)
		{
			return mPatientDetailsDatas.size();
		}
		else
		{
			return 0;
		}

	}

	public class ViewHolder extends RecyclerView.ViewHolder
	{
		private TextView txtptntcd, txtpatientName;

		private LinearLayout graphrel;

		public ViewHolder(View view)
		{
			super(view);
			graphrel = (LinearLayout) view.findViewById(R.id.graphrel);
			txtptntcd = (TextView) view.findViewById(R.id.txtptntcd);
			txtpatientName = (TextView) view.findViewById(R.id.txtpatientName);
		}

	}

}
