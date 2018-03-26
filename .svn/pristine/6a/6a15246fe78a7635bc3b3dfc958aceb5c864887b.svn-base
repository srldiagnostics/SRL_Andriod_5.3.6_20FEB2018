package com.srllimited.srl.adapters;

import java.net.URL;
import java.util.List;

import com.squareup.picasso.Picasso;
import com.srllimited.srl.BookATestActivity;
import com.srllimited.srl.LoginScreenActivity;
import com.srllimited.srl.MyFamilyActivity;
import com.srllimited.srl.MyReportEntryDetails;
import com.srllimited.srl.R;
import com.srllimited.srl.YourLocationActivity;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.util.Log;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.utilities.Utility;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

//import com.codefyne.mysrl.OffersListActivity;

/**
 * Created by Ruchi Tiwari on 02/12/16.
 */
public class SlidingBannersAdapter extends PagerAdapter
{

	private Context mContext;

	private List<String> mResources;

	public SlidingBannersAdapter(Context mContext, List<String> mResources)
	{
		this.mContext = mContext;
		this.mResources = mResources;
	}

	public static void LoadImageFromWebOperations(String url, ImageView view)
	{
		try
		{

			//			Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);
			url = url + "android_HDPI.png" + "";

			URL newurl = new URL(url);
			Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
			view.setImageBitmap(mIcon_val);
			//			try {
			//
			//				Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(url).getContent());
			//				Log.e("imagebitmap",bitmap+"");
			//				view.setImageBitmap(bitmap);
			//			} catch (MalformedURLException e) {
			//				e.printStackTrace();
			//			} catch (IOException e) {
			//				e.printStackTrace();
			//			}

			//			Log.e("bannerurl",url+"");
			//			InputStream is = (InputStream) new URL(url).getContent();
			//			Drawable d = Drawable.createFromStream(is, "src name");
			//			return d;
		}
		catch (Exception e)
		{
			return;
		}
	}

	@Override
	public int getCount()
	{
		return mResources.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object)
	{
		return view == ((LinearLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position)
	{
		View itemView = LayoutInflater.from(mContext).inflate(R.layout.sliding_banners_item, container, false);

		ImageView imageView = (ImageView) itemView.findViewById(R.id.img_pager_item);
		try
		{

			if (mResources.get(position).equalsIgnoreCase("default"))
			{
				imageView.setBackgroundResource(R.drawable.banner_default);
			}
			else
			{
				Utility.setDimensions(mContext);
				int width = Utility.screenWidth;
				int heigth = Utility.screenHeight;
				Log.e("Screen Sizes Width :", width + "");
				Log.e("Screen Sizes Height :", heigth + "");
				String url = mResources.get(position) + "android_HDPI.png" + "";
				if (width > 0 && width < 700)
				{
					url = mResources.get(position) + "android_HDPI.png" + "";
				}
				if (width > 700 && width < 1000)
				{
					url = mResources.get(position) + "android_XHDPI.png" + "";
				}
				if (width > 1000)
				{
					url = mResources.get(position) + "android_XXHDPI.png" + "";
				}

				Picasso.with(mContext).load(url).into(imageView);

				imageView.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(final View view)
					{
						String action = "";
						try
						{
							action = String.valueOf(
									SharedPrefsUtil.getLongPreference(mContext, mResources.get(position) + "", 0));
						}
						catch (Exception e)
						{

						}
						String screen = SharedPrefsUtil.getStringPreference(mContext, action);

						if (Validate.notEmpty(screen))
						{
							naviagteToBannerScreen(screen);

						}
					}
				});
			}

		}
		catch (Exception e)
		{

		}
		container.addView(itemView);

		return itemView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		container.removeView((LinearLayout) object);
	}

	private void naviagteToBannerScreen(String screen)
	{
		Intent intent = null;
		if (screen.equalsIgnoreCase("Book A Test"))
		{
			Util.killBook();
			intent = new Intent(mContext, BookATestActivity.class);
		}

		if (screen.equalsIgnoreCase("My Reports"))
		{
			navigateToMyReports();
		}

		if (screen.equalsIgnoreCase("MY FAMILY"))
		{
			navigateTomyfamily();
		}

		//		if (screen.equalsIgnoreCase("OFFERS"))
		//		{
		//			Util.killOffersList();
		//			intent = new Intent(mContext, OffersListActivity.class);
		//		}
		if (screen.equalsIgnoreCase("SRL LOCATOR"))
		{
			Util.killYourLoc();
			intent = new Intent(mContext, YourLocationActivity.class);
		}

		if (intent != null)
		{
			mContext.startActivity(intent);
		}
	}

	private void navigateToMyReports()
	{
		SharedPrefsUtil.setIntegerPreference(mContext, Constants.sharedPreferenceSelectedLoginActivity,
				Constants.m_reports);
		if (Util.getStoredUsername(mContext) != null && !Util.getStoredUsername(mContext).isEmpty()
				&& !Util.isRem(mContext))
		{
			Util.killMyReportEntry();
			Intent i = new Intent(mContext, MyReportEntryDetails.class);
			mContext.startActivity(i);
		}
		else
		{
			Util.killLogin();
			Intent intent = new Intent(mContext, LoginScreenActivity.class);
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
			Util.killMyFamily();
			Constants.isFamilySel = false;
			Intent intent = new Intent(mContext, MyFamilyActivity.class);
			mContext.startActivity(intent);
		}
		else
		{
			Util.killLogin();
			Intent intent = new Intent(mContext, LoginScreenActivity.class);
			mContext.startActivity(intent);
		}
	}

}