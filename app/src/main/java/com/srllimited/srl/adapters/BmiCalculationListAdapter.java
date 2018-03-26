package com.srllimited.srl.adapters;

import java.util.List;

import com.srllimited.srl.BmiListView;
import com.srllimited.srl.R;
import com.srllimited.srl.data.BmiUsersData;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Codefyne on 17-02-2017.
 */

public class BmiCalculationListAdapter extends RecyclerView.Adapter<BmiCalculationListAdapter.ViewHolder>
{
	Context mContext;

	//    OffersListAdapter.OnItemClickListener mItemClickListener;
	private List<BmiUsersData> listData;

	public BmiCalculationListAdapter(BmiListView context, List<BmiUsersData> bmiData)
	{
		this.mContext = context;
		this.listData = bmiData;
	}

	public int getItemCount()
	{
		return listData.size();
	}

	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bmi_list_item, parent, false);
		return new ViewHolder(view);
	}

	//    public void setOnItemClickListener(final OffersListAdapter.OnItemClickListener mItemClickListener) {
	//        this.mItemClickListener = mItemClickListener;
	//    }

	public void onBindViewHolder(final ViewHolder holder, final int position)
	{

		holder.name.setText("" + "Name : " + listData.get(position).getUserName_bmi());
		holder.height.setText("" + "Height : " + listData.get(position).getHeight_bmi());
		holder.weight.setText("" + "Weight : " + listData.get(position).getWeight_bmi());
		holder.date.setText("" + "Date : " + listData.get(position).getIdealweight_bmi()); // here wil get Date and Time
		holder.result.setText("" + "Your BMI result is " + listData.get(position).getMassindex_bmi());
		holder.indicates.setText("" + listData.get(position).getIndication_bmi());

	}

	public interface OnItemClickListener
	{
		void onItemClick(View view, int position);
	}

	public class ViewHolder extends RecyclerView.ViewHolder
	{
		public final TextView name, height, weight, date, result;
		public LinearLayout bmiDetails;
		Button indicates;

		public ViewHolder(View itemView)
		{
			super(itemView);
			bmiDetails = (LinearLayout) itemView.findViewById(R.id.bmiDetails);
			name = (TextView) itemView.findViewById(R.id.name);
			height = (TextView) itemView.findViewById(R.id.height);
			weight = (TextView) itemView.findViewById(R.id.weight);
			date = (TextView) itemView.findViewById(R.id.date);
			result = (TextView) itemView.findViewById(R.id.result);
			indicates = (Button) itemView.findViewById(R.id.indicates);

			//            bmiDetails.setOnClickListener(this);
		}

		//        @Override
		//        public void onClick(View v) {
		//            if (mItemClickListener != null) {
		//                mItemClickListener.onItemClick(itemView, getPosition());
		//            }
		//        }
	}
}
