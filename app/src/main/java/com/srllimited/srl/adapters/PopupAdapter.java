package com.srllimited.srl.adapters;

/**
 * Created by Ruchi Tiwari on 02/12/16.
 */

import java.util.ArrayList;

import com.srllimited.srl.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PopupAdapter extends RecyclerView.Adapter<PopupAdapter.ViewHolder>
{
	private Context mContext;

	private ArrayList<String> popupData;

	public PopupAdapter(Context context, ArrayList<String> mpopupData)
	{
		this.mContext = context;
		this.popupData = mpopupData;
	}

	@Override
	public PopupAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_item, viewGroup, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(PopupAdapter.ViewHolder viewHolder, int i)
	{

		viewHolder.header.setText(popupData.get(i));

		viewHolder.header.setGravity(Gravity.CENTER_HORIZONTAL);
	}

	@Override
	public int getItemCount()
	{
		return popupData.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder
	{
		private TextView header;

		public ViewHolder(View view)
		{
			super(view);

			header = (TextView) view.findViewById(R.id.header);

		}
	}

}
