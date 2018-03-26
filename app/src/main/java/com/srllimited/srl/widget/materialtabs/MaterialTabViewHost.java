package com.srllimited.srl.widget.materialtabs;

import com.srllimited.srl.R;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

/**
 * Created by RuchiTiwari on 12/18/2016.
 */

public class MaterialTabViewHost extends LinearLayout
{
	private int primaryColor;

	private int accentColor;

	private int textSize;

	private int textColor;

	private int iconColor;

	private boolean scrollable;

	private MaterialTabHost mImageTabHost;

	private MaterialTabHost mTextTabHost;

	private boolean isSelectionPersists;

	public MaterialTabViewHost(final Context context)
	{
		this(context, null);
	}

	public MaterialTabViewHost(final Context context, final AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public MaterialTabViewHost(final Context context, final AttributeSet attrs, final int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public MaterialTabViewHost(final Context context, final AttributeSet attrs, final int defStyleAttr,
			final int defStyleRes)
	{
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context, attrs, defStyleAttr);
	}

	private void init(final Context context, final AttributeSet attrs, final int defStyleAttr)
	{
		LayoutInflater.from(context).inflate(R.layout.material_tabview_host, this);

		mImageTabHost = (MaterialTabHost) this.findViewById(R.id.imageTabHost);
		mTextTabHost = (MaterialTabHost) this.findViewById(R.id.textTabHost);

		// get attributes
		if (attrs != null)
		{
			TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaterialTabViewHost, 0, 0);

			try
			{
				// custom attributes
				primaryColor = a.getColor(R.styleable.MaterialTabViewHost_materialViewTabsPrimaryColor,
						Color.parseColor("#009688"));
				accentColor = a.getColor(R.styleable.MaterialTabViewHost_materialViewTabsAccentColor,
						Color.parseColor("#00b0ff"));
				iconColor = a.getColor(R.styleable.MaterialTabViewHost_materialViewTabsIconColor, Color.WHITE);
				textColor = a.getColor(R.styleable.MaterialTabViewHost_materialViewTabsTextColor, Color.WHITE);
				textSize = a.getInteger(R.styleable.MaterialTabViewHost_materialViewTabsTextSize, 10);
				scrollable = a.getBoolean(R.styleable.MaterialTabViewHost_materialTabViewHostScrollable, false);
			}
			finally
			{
				a.recycle();
			}
		}

		setScrollable(scrollable);
	}

	public void setScrollable(final boolean scrollable)
	{
		this.scrollable = scrollable;
		mImageTabHost.setScrollable(scrollable);
		mTextTabHost.setScrollable(scrollable);
	}

	public boolean isSelectionPersists()
	{
		return isSelectionPersists;
	}

	public void setSelectionPersists(final boolean selectionPersists)
	{
		isSelectionPersists = selectionPersists;
		mImageTabHost.setSelectionPersists(isSelectionPersists);
		mTextTabHost.setSelectionPersists(isSelectionPersists);
	}

	public void setSelectedNavigationItem(int position)
	{
		mImageTabHost.setSelectedNavigationItem(position);
		mTextTabHost.setSelectedNavigationItem(position);
	}

	public void addTab(Context context, CharSequence text, Drawable icon, MaterialTabListener listener)
	{
		MaterialTab imageTab = mImageTabHost.newTab().setIcon(icon).setTabListener(listener);
		imageTab.setPrimaryColor(primaryColor);
		imageTab.setAccentColor(accentColor);
		imageTab.setIconColor(iconColor);

		MaterialTab textTab = mTextTabHost.newTab().setText(context, text).setTabListener(listener);
		textTab.setPrimaryColor(primaryColor);
		textTab.setAccentColor(accentColor);
		textTab.setTextColor(textColor);
		textTab.setTextSize(textSize);

		mImageTabHost.addTab(imageTab);
		mTextTabHost.addTab(textTab);
	}

	public void notifyDataSetChanged()
	{
		mImageTabHost.notifyDataSetChanged();
		mTextTabHost.notifyDataSetChanged();
	}
}
