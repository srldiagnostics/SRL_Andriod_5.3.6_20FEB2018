package com.srllimited.srl.adapters;

/**
 * Created by Ruchi Tiwari on 02/12/16.
 */

import java.util.ArrayList;

import com.srllimited.srl.BookATestActivity;
import com.srllimited.srl.HealthTracker;
import com.srllimited.srl.LoginScreenActivity;
import com.srllimited.srl.MyFamilyActivity;
import com.srllimited.srl.MyReportEntryDetails;
import com.srllimited.srl.R;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.ScrollingListTemplate;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder>
{
	private Context mContext;

	private ArrayList<ScrollingListTemplate> countries;

	public DataAdapter(Context context, ArrayList<ScrollingListTemplate> countries)
	{
		this.mContext = context;
		this.countries = countries;
	}

	@Override
	public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_list_item, viewGroup, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, final int i)
	{

		viewHolder.content.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Semibold.ttf"));
		viewHolder.header.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Semibold.ttf"));

		viewHolder.content.setText(countries.get(i).getContent());
		viewHolder.header.setText(countries.get(i).getHeader());

		if (countries.get(i).getImage().equalsIgnoreCase("Book"))
		{
			viewHolder.item_image.setImageResource(R.drawable.homefamily1);
			viewHolder.recycler_header.setBackgroundResource(R.color.lightblue);
			viewHolder.btn_book_now.setText("Book Now");
		}

		if (countries.get(i).getImage().equalsIgnoreCase("Reports"))
		{
			viewHolder.item_image.setImageResource(R.drawable.home_reports);
			viewHolder.recycler_header.setBackgroundResource(R.color.lightblue);
			viewHolder.btn_book_now.setText("View Reports");
		}
		if (countries.get(i).getImage().equalsIgnoreCase("View Tracker"))
		{
			viewHolder.item_image.setImageResource(R.drawable.healthtracker1);
			viewHolder.recycler_header.setBackgroundResource(R.color.lightblue);
			viewHolder.btn_book_now.setText("View Tracker");
		}

		if (countries.get(i).getImage().equalsIgnoreCase("Family"))
		{
			viewHolder.item_image.setImageResource(R.drawable.family_section1);
			viewHolder.recycler_header.setBackgroundResource(R.color.lightblue);
			viewHolder.btn_book_now.setText("Add Now");
		}

		viewHolder.btn_book_now.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				if (countries.get(i).getImage().equalsIgnoreCase("Book"))
				{

					if (Util.isCity(mContext))
					{
						Constants.isPackage = false;

						Intent intent = new Intent(mContext, BookATestActivity.class);
						mContext.startActivity(intent);
					}
					else
					{
						Util.showCityAlert(mContext);
					}

				}
				if (countries.get(i).getImage().equalsIgnoreCase("Reports"))
				{
					navigateToMyReports();

				}

				if (countries.get(i).getImage().equalsIgnoreCase("Family"))
				{
					navigateTomyfamily();

				}
				if (countries.get(i).getImage().equalsIgnoreCase("View Tracker"))
				{
					navigateToHealth();
				}
			}
		});

	}

	@Override
	public int getItemCount()
	{
		return countries.size();
	}

	private void navigateToMyReports()
	{
		SharedPrefsUtil.setIntegerPreference(mContext, Constants.sharedPreferenceSelectedLoginActivity,
				Constants.m_reports);
		if (Util.getStoredUsername(mContext) != null && !Util.getStoredUsername(mContext).isEmpty()
				&& !Util.isRem(mContext))
		{
			Intent i = new Intent(mContext, MyReportEntryDetails.class);
			mContext.startActivity(i);
		}
		else
		{
			Intent intent = new Intent(mContext, LoginScreenActivity.class);
			mContext.startActivity(intent);
		}
	}

	private void navigateToHealth()
	{

		SharedPrefsUtil.setIntegerPreference(mContext, Constants.sharedPreferenceSelectedLoginActivity,
				Constants.m_health);
		if (Util.getStoredUsername(mContext) != null && !Util.getStoredUsername(mContext).isEmpty()
				&& !Util.isRem(mContext))
		{
			Util.killHealthTrack();
			Intent i = new Intent(mContext, HealthTracker.class);
			mContext.startActivity(i);
		}
		else
		{
			Util.killLogin();
			Intent intent = new Intent(mContext, LoginScreenActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(intent);
		}

	}

	private void navigateTomyfamily()
	{
		SharedPrefsUtil.setIntegerPreference(mContext, Constants.sharedPreferenceSelectedLoginActivity,
				Constants.m_family);
		if (Util.getStoredUsername(mContext) != null && !Util.getStoredUsername(mContext).isEmpty()
				&& !Util.isRem(mContext))
		{
			Constants.isFamilySel = false;
			Util.killMyFamily();
			Intent intent = new Intent(mContext, MyFamilyActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(intent);
		}
		else
		{
			Util.killLogin();
			Intent intent = new Intent(mContext, LoginScreenActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(intent);
		}
	}

	public class ViewHolder extends RecyclerView.ViewHolder
	{
		RelativeLayout recycler_header;
		ImageView item_image;
		private TextView content, header, btn_book_now;

		public ViewHolder(View view)
		{
			super(view);

			content = (TextView) view.findViewById(R.id.content);
			header = (TextView) view.findViewById(R.id.header);
			recycler_header = (RelativeLayout) view.findViewById(R.id.recycler_header);
			item_image = (ImageView) view.findViewById(R.id.item_image);
			btn_book_now = (TextView) view.findViewById(R.id.btn_book_now);

		}
	}

}
