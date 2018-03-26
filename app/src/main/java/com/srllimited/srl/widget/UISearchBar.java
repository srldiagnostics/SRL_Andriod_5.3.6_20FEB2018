package com.srllimited.srl.widget;

/**
 * Created by codefyneandroid on 12-12-2016.
 */

import com.srllimited.srl.R;
import com.srllimited.srl.util.Validate;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class UISearchBar extends FrameLayout
{
	public static EditText editText;
	public static ImageView btnClear;
	private OnSearchListener searchListener;
	private TextWatcher mTextWatcher = new TextWatcher()
	{
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count)
		{
			if (searchListener != null)
			{
				searchListener.onSearchText(s.toString());
			}
		}

		@Override
		public void afterTextChanged(Editable s)
		{
			String text = s.toString();
			if (Validate.notEmpty(text))
			{
				btnClear.setVisibility(View.VISIBLE);
			}
			else
			{
				btnClear.setVisibility(View.GONE);
			}
		}
	};
	private OnClickListener onClickListener = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.iv_clear:
				{
					clear();
				}
					break;
			}
		}
	};

	public UISearchBar(Context context)
	{
		this(context, null);
	}

	public UISearchBar(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}

	public UISearchBar(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public UISearchBar(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes)
	{
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleAttr)
	{
		LayoutInflater.from(getContext()).inflate(R.layout.search_bar, this);

		this.editText = (EditText) findViewById(R.id.et_search_input);
		this.btnClear = (ImageView) findViewById(R.id.iv_clear);
		this.editText.addTextChangedListener(mTextWatcher);
		this.btnClear.setOnClickListener(onClickListener);
	}

	public void clear()
	{
		editText.setText(new String(""));

		if (searchListener != null)
		{
			searchListener.onClearSearch();
		}
	}

	public void setOnSearchListener(OnSearchListener listener)
	{
		this.searchListener = listener;
	}

	public EditText getEditText()
	{
		return editText;
	}

	public String getSearchQuery()
	{
		return editText.getText().toString();
	}

	public void setSearchQuery(String query)
	{
		editText.setText(query);
	}

	public interface OnSearchListener
	{
		void onClearSearch();

		void onSearchText(String text);
	}
}