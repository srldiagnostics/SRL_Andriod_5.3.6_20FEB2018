package com.srllimited.srl.adapters;

/**
 * Created by Ruchi Tiwari on 02/12/16.
 */

import java.util.ArrayList;

import com.srllimited.srl.R;
import com.srllimited.srl.data.LocationData;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>
{
	private Context mContext;

	private ArrayList<LocationData> mLocations;

	public LocationAdapter(Context context, ArrayList<LocationData> locations)
	{
		this.mContext = context;
		this.mLocations = locations;
	}

	private static String toTitleCase(String str)
	{

		if (str == null)
		{
			return null;
		}

		boolean space = true;
		StringBuilder builder = new StringBuilder(str);
		final int len = builder.length();

		for (int i = 0; i < len; ++i)
		{
			char c = builder.charAt(i);
			if (space)
			{
				if (!Character.isWhitespace(c))
				{
					// Convert to title case and switch out of whitespace mode.
					builder.setCharAt(i, Character.toTitleCase(c));
					space = false;
				}
			}
			else if (Character.isWhitespace(c) || c == '(')
			{
				space = true;
			}
			else
			{
				builder.setCharAt(i, Character.toLowerCase(c));
			}
		}

		return builder.toString();
	}

	@Override
	public LocationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_list_item, viewGroup, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(LocationAdapter.ViewHolder viewHolder, int i)
	{
		viewHolder.header.setText(Html.fromHtml(toTitleCase(mLocations.get(i).getName())));
	}

	@Override
	public int getItemCount()
	{
		return mLocations.size();
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
