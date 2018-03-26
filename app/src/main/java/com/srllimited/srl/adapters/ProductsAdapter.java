package com.srllimited.srl.adapters;

/**
 * Created by Ruchi Tiwari on 02/12/16.
 */

import java.util.ArrayList;
import java.util.List;

import com.srllimited.srl.R;
import com.srllimited.srl.data.ProductList;
import com.srllimited.srl.util.Util;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder>
{
	private Context mContext;

	private List<ProductList> mPRProductLists = new ArrayList<>();

	public ProductsAdapter(Context context)
	{

		this.mContext = context;
	}

	public void reload(List<ProductList> productLists)
	{
		if (productLists != null)
		{
			mPRProductLists = new ArrayList<>(productLists);
		}
		else
		{
			mPRProductLists.clear();
		}
		this.notifyDataSetChanged();
	}

	@Override
	public ProductsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_items, viewGroup, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final ProductsAdapter.ViewHolder viewHolder, final int i)
	{

		try
		{

			if (!mPRProductLists.get(i).getPRDCT_NM().equalsIgnoreCase("null"))
			{
				viewHolder.testData1TVID.setText(Html.fromHtml(mPRProductLists.get(i).getPRDCT_NM() + ""));
			}
			else
			{
				viewHolder.testData1TVID.setText("");
			}

			String totalamount = String.valueOf(mPRProductLists.get(i).getBASIC_COST());
			if (!totalamount.equalsIgnoreCase("null"))
			{
				viewHolder.price.setText(Util.getIntegerToString(mPRProductLists.get(i).getBASIC_COST() + ""));
			}
			else
			{
				viewHolder.price.setText("");
			}

		}
		catch (Exception e)
		{

		}
	}

	@Override
	public int getItemCount()
	{
		return mPRProductLists.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder
	{

		private TextView price, testData1TVID;

		public ViewHolder(View view)
		{
			super(view);

			testData1TVID = (TextView) view.findViewById(R.id.testData1TVID);
			price = (TextView) view.findViewById(R.id.price);
		}
	}
}
