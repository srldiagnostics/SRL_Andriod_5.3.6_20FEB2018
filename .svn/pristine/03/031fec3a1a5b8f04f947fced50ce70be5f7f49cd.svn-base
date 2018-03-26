package com.srllimited.srl.widget;

import java.util.ArrayList;

import com.srllimited.srl.R;
import com.srllimited.srl.adapters.LocationAdapter;
import com.srllimited.srl.data.CityListData;
import com.srllimited.srl.data.LocationData;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by codefyneandroid on 12-12-2016.
 */

public class SearchLocationView extends FrameLayout
{
	public static ImageView mCloseButton;
	private TextView mHeading;
	private UISearchBar mSearchView;
	private RecyclerView mRecyclerView;
	private RecyclerView.Adapter mAdapter;
	private ArrayList<LocationData> locations = new ArrayList<>();
	private boolean mIsSearchEnabled;
	private OnSearchLocationListener mSearchLocationListener;
	private UISearchBar.OnSearchListener mSearchListener = new UISearchBar.OnSearchListener()
	{
		@Override
		public void onClearSearch()
		{
			mIsSearchEnabled = false;
			if (Validate.notNull(mSearchLocationListener))
			{
				mSearchLocationListener.onClearSearch();
			}
		}

		@Override
		public void onSearchText(String newText)
		{
			mIsSearchEnabled = false;
			newText = newText.trim();
			if (Validate.notNull(mSearchLocationListener))
			{
				if (Validate.notEmpty(newText))
				{
					mIsSearchEnabled = true;
					mSearchLocationListener.onSearchText(newText);
				}
				else
				{
					mSearchLocationListener.onClearSearch();
				}
			}
		}
	};
	private View.OnClickListener mOnClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.close:
				{
					Util.hideSoftKeyboard(getContext(), mSearchView.getEditText());
					if (Validate.notNull(mSearchLocationListener))
					{
						mSearchLocationListener.onClose();
					}
				}
					break;
			}
		}
	};
	private RecyclerItemClickListener.OnItemClickListener onItemClickListener = new RecyclerItemClickListener.OnItemClickListener()
	{
		@Override
		public void onItemClick(View view, int position)
		{
			Util.hideSoftKeyboard(getContext(), mSearchView.getEditText());
			if (Validate.notNull(mSearchLocationListener))
			{
				mSearchLocationListener.onItemClick(view, position);
			}
		}

		@Override
		public void onLongItemClick(View view, int position)
		{
			Util.hideSoftKeyboard(getContext(), mSearchView.getEditText());
			if (Validate.notNull(mSearchLocationListener))
			{
				mSearchLocationListener.onLongItemClick(view, position);
			}
		}
	};

	public SearchLocationView(Context context)
	{
		this(context, null);
	}

	public SearchLocationView(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public SearchLocationView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public SearchLocationView(final Context context, final AttributeSet attrs, final int defStyleAttr,
			final int defStyleRes)
	{
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr)
	{
		LayoutInflater.from(getContext()).inflate(R.layout.search_location_list, this);

		this.mHeading = (TextView) findViewById(R.id.heading);
		this.mCloseButton = (ImageView) findViewById(R.id.close);
		this.mSearchView = (UISearchBar) findViewById(R.id.searchView);

		this.mRecyclerView = (RecyclerView) findViewById(R.id.search_location_recyclerView);
		this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
		this.mRecyclerView.setHasFixedSize(true);
		this.mRecyclerView.addOnItemTouchListener(
				new RecyclerItemClickListener(getContext(), mRecyclerView, onItemClickListener));
		this.mAdapter = new LocationAdapter(getContext(), locations);
		this.mRecyclerView.setAdapter(mAdapter);

		mCloseButton.setOnClickListener(mOnClickListener);
		mSearchView.setOnSearchListener(mSearchListener);
		mSearchView.getEditText().setHint(getResources().getString(R.string.search_bar_city_hint));
	}

	public void setSearchLocationListener(OnSearchLocationListener searchLocationListener)
	{
		this.mSearchLocationListener = searchLocationListener;
	}

	public void setLocations(ArrayList<CityListData> cityList)
	{
		locations.clear();
		if (cityList != null)
		{
			for (CityListData data : cityList)
			{
				if (mIsSearchEnabled)
				{
					locations.add(new LocationData(data.getDISPLAY_NAME()));
				}
				else
				{
					locations.add(new LocationData(data.getCITY_NAME()));
				}
			}
		}
		mAdapter.notifyDataSetChanged();
	}

	public interface OnSearchLocationListener
	{
		void onClose();

		void onClearSearch();

		void onSearchText(String text);

		void onItemClick(View view, int position);

		void onLongItemClick(View view, int position);
	}

}
