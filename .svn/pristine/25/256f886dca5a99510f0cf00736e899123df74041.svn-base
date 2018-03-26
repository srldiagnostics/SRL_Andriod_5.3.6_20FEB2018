package com.srllimited.srl.adapters;

/**
 * Created by Ruchi Tiwari on 02/12/16.
 */

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.srllimited.srl.R;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.BookTestORPackagesData;
import com.srllimited.srl.data.ProductHeaderListItemData;
import com.srllimited.srl.database.DatabaseHelper;
import com.srllimited.srl.database.ProductHeaderDatabase;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookATestAdapter extends RecyclerView.Adapter<BookATestAdapter.ViewHolder>
{
	public static NotifyActivity notifyActivity;
	public static boolean isRemoveitem = false;
	DatabaseHelper db;
	ProductHeaderDatabase productHeaderDB;
	int count = 0;
	Dialog alertDialog;
	LinearLayout dynamic;
	Button close_popup;
	FirebaseAnalytics firebaseAnalytics;
	String citys = "";
	String loggedin_username = "";
	private Context mContext;
	private List<BookTestORPackagesData> mBook_A_Test_Data;
	private boolean mIsCart = false;

	public BookATestAdapter(Context context, List<BookTestORPackagesData> bookatestdata, boolean isCart)
	{
		this.mContext = context;
		this.mIsCart = isCart;
		this.mBook_A_Test_Data = bookatestdata;
		this.db = new DatabaseHelper(context);
		this.productHeaderDB = new ProductHeaderDatabase(context);

		alertDialog = new Dialog(mContext);
		alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		alertDialog.setCancelable(false);
		alertDialog.setContentView(R.layout.bookatestpopup);
		dynamic = (LinearLayout) alertDialog.findViewById(R.id.dynamic);
		close_popup = (Button) alertDialog.findViewById(R.id.close_popup);
		close_popup.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				alertDialog.dismiss();
			}
		});

		SharedPreferences pref = mContext.getSharedPreferences(Constants.login_credentials, 0); // 0 - for private mode
		loggedin_username = pref.getString(Constants.loggedin_username, null);
		citys = SharedPrefsUtil.getStringPreference(context, "selectedcity");
	}

	@Override
	public BookATestAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.book_a_test_item, viewGroup, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final BookATestAdapter.ViewHolder viewHolder, final int i)
	{
		if (viewHolder != null)
		{
			if (mBook_A_Test_Data.get(i) != null)
			{
				viewHolder.setIndex(i);
				viewHolder.book_code.setText(mBook_A_Test_Data.get(i).getPRDCT_CODE());
				viewHolder.book_test_name.setText(Html.fromHtml(mBook_A_Test_Data.get(i).getPRDCT_ALIASES()));
				if (!mIsCart)
				{
					if (mBook_A_Test_Data.get(i).getHOME_COLL_AVAIL().equalsIgnoreCase("N"))
					{
						viewHolder.txtVisitRequire.setText("Lab Visit Required");
					}
					else
					{
						viewHolder.txtVisitRequire.setText("");
					}
				}
				String grossAmount = Util.getIntegerToString(mBook_A_Test_Data.get(i).getGROSS_AMT() + "");
				if (mBook_A_Test_Data.get(i).getPRICE() != 0 && Validate.notEmpty(grossAmount)
						&& !grossAmount.equalsIgnoreCase("0"))
				{
					viewHolder.book_default_price.setText(
							"₹ " + " " + Util.getIntegerToString(mBook_A_Test_Data.get(i).getGROSS_AMT() + ""));
					viewHolder.default_price.setText(
							"₹ " + " " + Util.getIntegerToString(mBook_A_Test_Data.get(i).getGROSS_AMT() + ""));
				}
				else
				{
					viewHolder.book_default_price.setVisibility(View.GONE);
				}

				try
				{
					if ((mBook_A_Test_Data.get(i).getPRICE() + "")
							.equalsIgnoreCase(mBook_A_Test_Data.get(i).getGROSS_AMT().toString()))
					{
						viewHolder.layout_percentage.setVisibility(View.INVISIBLE);
						//						viewHolder.book_default_price.setBackgroundResource(0);
						viewHolder.book_default_price.setVisibility(View.INVISIBLE);
						viewHolder.default_price.setVisibility(View.INVISIBLE);
					}
					else
					{
						viewHolder.book_default_price.setVisibility(View.VISIBLE);
						viewHolder.layout_percentage.setVisibility(View.VISIBLE);
						//						viewHolder.book_default_price.setBackgroundResource(R.drawable.strike_line);
						viewHolder.default_price.setVisibility(View.VISIBLE);
					}
				}
				catch (Exception e)
				{

				}

				String price = Util.getIntegerToString(mBook_A_Test_Data.get(i).getPRICE() + "");

				if (Validate.notEmpty(price) && !price.equalsIgnoreCase("0"))
				{
					viewHolder.book_original_price
							.setText("₹ " + Util.getIntegerToString(mBook_A_Test_Data.get(i).getPRICE() + ""));
					//                    viewHolder.cart_price.setText("₹ " + Util.getIntegerToString(mBook_A_Test_Data.get(i).getPRICE() + ""));
				}

				if (Validate.notEmpty(mBook_A_Test_Data.get(i).getCartPrice())
						&& !mBook_A_Test_Data.get(i).getCartPrice().equalsIgnoreCase("0"))
				{
					viewHolder.cart_price
							.setText("₹ " + Util.getIntegerToString(mBook_A_Test_Data.get(i).getCartPrice() + ""));
				}

				if (mBook_A_Test_Data.get(i).getDISC() != 0)
				{

					try
					{
						int x = (int) mBook_A_Test_Data.get(i).getDISC();
						String newValue = x + "";
						if ((mBook_A_Test_Data.get(i).getDISC_TYPE() + "").equalsIgnoreCase("P"))
						{
							viewHolder.book_percentage.setText(newValue + " %");
						}
						else
						{
							viewHolder.book_percentage.setText("₹ " + mBook_A_Test_Data.get(i).getDISC() + "");
						}
						viewHolder.layout_percentage.setVisibility(View.VISIBLE);
					}
					catch (Exception e)
					{

					}

				}
				else
				{
					viewHolder.layout_percentage.setVisibility(View.INVISIBLE);
				}

				if (!mIsCart)
				{
					if (mBook_A_Test_Data.get(i).isCart())
					{
						viewHolder.add_test_icon.setVisibility(View.GONE);
						viewHolder.test_done.setVisibility(View.VISIBLE);
					}
					else
					{
						viewHolder.test_done.setVisibility(View.GONE);
						viewHolder.add_test_icon.setVisibility(View.VISIBLE);
					}

				}
				viewHolder.test_done.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(final View view)
					{

						mBook_A_Test_Data.get(i).setCart(false);
						mBook_A_Test_Data.get(i).setCartPrice("0");
						db.updateBookOrPkgs(mBook_A_Test_Data.get(i));

						notifyDataSetChanged();

					}
				});
				viewHolder.remove.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(final View view)
					{
						BookTestORPackagesData bookTestORPackagesData = new BookTestORPackagesData();
						mBook_A_Test_Data.get(i).setCart(false);
						mBook_A_Test_Data.get(i).setCartPrice("0");
						bookTestORPackagesData = mBook_A_Test_Data.get(i);
						db.updateBookOrPkgs(mBook_A_Test_Data.get(i));
						mBook_A_Test_Data.remove(i);
						viewHolder.add_test_icon.setVisibility(View.GONE);
						viewHolder.test_done.setVisibility(View.GONE);
						isRemoveitem = true;

						notifyDataSetChanged();
					}
				});

				viewHolder.expanded_details.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(final View view)
					{
						viewHolder.expanded_layout.setVisibility(View.GONE);
					}
				});

				viewHolder.infoicon.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(final View view)
					{
						//						viewHolder.expanded_layout.setVisibility(View.VISIBLE);

						List<ProductHeaderListItemData> datas = mBook_A_Test_Data.get(i)
								.getProductHeaderListItemDataList();
						if (datas != null)
						{
							dynamic.removeAllViews();

							for (ProductHeaderListItemData productHeaderListItemData : datas)
							{

								LinearLayout rel = new LinearLayout(mContext);
								LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
										LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
								LinearLayout.LayoutParams headerlp = new LinearLayout.LayoutParams(
										LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
								rel.setOrientation(LinearLayout.VERTICAL);
								headerlp.setMargins(5, 5, 5, 5);

								LinearLayout.LayoutParams headerlp1 = new LinearLayout.LayoutParams(
										LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
								headerlp1.setMargins(5, 0, 5, 0);

								rel.setLayoutParams(lp);
								TextView header = new TextView(mContext);
								header.setTextSize(16);
								header.setTextColor(mContext.getResources().getColor(R.color.collectionText));
								header.setTypeface(null, Typeface.BOLD);
								header.setId(i);
								header.setPadding(5, 5, 5, 5);
								header.setLayoutParams(headerlp);
								header.setText(productHeaderListItemData.getHeader());

								TextView desc = new TextView(mContext);
								desc.setTextSize(14);
								desc.setLayoutParams(headerlp1);
								desc.setTextColor(mContext.getResources().getColor(R.color.list_group_title));
								desc.setText(Html.fromHtml(productHeaderListItemData.getDesc()));

								rel.addView(header);
								rel.addView(desc);
								dynamic.addView(rel);
							}

							alertDialog.show();
						}
					}
				});
			}

		}

		notifyActivity = (NotifyActivity) mContext;
		notifyActivity.onChangingListItem(mBook_A_Test_Data);
	}

	@Override
	public int getItemCount()
	{
		if (mBook_A_Test_Data.size() > 0)
		{
			return mBook_A_Test_Data.size();
		}
		else
		{
			notifyActivity = (NotifyActivity) mContext;
			notifyActivity.onChangingListItem(mBook_A_Test_Data);
			return 0;
		}

	}

	private void showOrHideCart(BookTestORPackagesData bookTestORPackagesData)
	{
	}

	public void setHeaderDescription()
	{

	}

	public interface NotifyActivity
	{
		void onChangingListItem(List<BookTestORPackagesData> bookTestORPackagesDatas);
	}

	public class ViewHolder extends RecyclerView.ViewHolder
	{
		public FrameLayout showdetails_layout;
		public LinearLayout expanded_layout;
		public FrameLayout expanded_details;
		LinearLayout book_test_layout, mycart_layout, layout_percentage, remove, dynamic_layout;
		Button test_done;
		Button add_test_icon;
		CardView card;
		ImageView infoicon;
		TextView txtVisitRequire;
		View view;
		private int mIndex;
		private TextView book_code, book_test_name, book_default_price, book_original_price, book_percentage,
				cart_price, default_price;
		private View.OnClickListener mOnClickListener = new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				final int pos = getIndex();
				switch ((view.getId()))
				{

					case R.id.showdetails_layout:

						break;

					case R.id.add_test_icon:
					{

						mBook_A_Test_Data.get(pos).setCart(true);
						String price = Util.getIntegerToString(mBook_A_Test_Data.get(pos).getPRICE() + "");
						if (Validate.notEmpty(price) && !price.equalsIgnoreCase("0"))
							mBook_A_Test_Data.get(pos).setCartPrice(price);
						db.updateBookOrPkgs(mBook_A_Test_Data.get(pos));

						notifyDataSetChanged();
						// Obtain the Firebase Analytics instance.
						firebaseAnalytics = FirebaseAnalytics.getInstance(mContext);
						Bundle bundle = new Bundle();
						bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, 1);
						bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Add To cart");
						//Logs an app event.
						firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
						//Sets whether analytics collection is enabled for this app on this device.
						firebaseAnalytics.setAnalyticsCollectionEnabled(true);

						//App Flyer
						AppsFlyerLib.getInstance().enableUninstallTracking(Constants.FCM_sender_id); // ADD THIS LINE HERE
						AppsFlyerLib.getInstance().startTracking((Application) mContext.getApplicationContext(),
								Constants.APP_FLYER_KEY);

						Map<String, Object> eventValue = new HashMap<String, Object>();
						eventValue.put(AFInAppEventParameterName.CITY, citys);
						eventValue.put(AFInAppEventParameterName.DEEP_LINK,"com.srllimited.srl.BookATestActivity");
						eventValue.put(AFInAppEventParameterName.CUSTOMER_USER_ID, loggedin_username);
						AppsFlyerLib.getInstance().trackEvent(mContext, "Add To cart", eventValue);

						//Facebook Analytic
						Bundle parameters = new Bundle();
						parameters.putString(AppEventsConstants.EVENT_PARAM_CURRENCY, "USD");
						parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, "product");
						parameters.putString(AppEventsConstants.EVENT_PARAM_CONTENT_ID, "HDFU-8452");

						AppEventsLogger logger = AppEventsLogger.newLogger(mContext);
						logger.logEvent(AppEventsConstants.EVENT_NAME_ADDED_TO_CART, 10.00, parameters);

					}
						break;
				}
			}
		};

		public ViewHolder(View view)
		{
			super(view);
			book_code = (TextView) view.findViewById(R.id.book_code);
			book_test_name = (TextView) view.findViewById(R.id.book_test_name);
			book_default_price = (TextView) view.findViewById(R.id.book_default_price);
			infoicon = (ImageView) view.findViewById(R.id.infoicon);
			default_price = (TextView) view.findViewById(R.id.default_price);
			book_original_price = (TextView) view.findViewById(R.id.book_original_price);
			book_percentage = (TextView) view.findViewById(R.id.book_percentage);
			cart_price = (TextView) view.findViewById(R.id.cart_price);
			showdetails_layout = (FrameLayout) view.findViewById(R.id.showdetails_layout);
			expanded_details = (FrameLayout) view.findViewById(R.id.expanded_details);
			expanded_layout = (LinearLayout) view.findViewById(R.id.expanded_layout);
			dynamic_layout = (LinearLayout) view.findViewById(R.id.dynamic_layout);
			book_test_layout = (LinearLayout) view.findViewById(R.id.book_test_layout);
			layout_percentage = (LinearLayout) view.findViewById(R.id.layout_percentage);
			mycart_layout = (LinearLayout) view.findViewById(R.id.my_cart_layout);
			remove = (LinearLayout) view.findViewById(R.id.remove);
			add_test_icon = (Button) view.findViewById(R.id.add_test_icon);
			card = (CardView) view.findViewById(R.id.card);
			test_done = (Button) view.findViewById(R.id.test_done);
			txtVisitRequire = (TextView) view.findViewById(R.id.txtVisitRequire);
			view = (View) view.findViewById(R.id.view);

			if (mIsCart)
			{
				view.setVisibility(View.GONE);
				test_done.setVisibility(View.GONE);
				add_test_icon.setVisibility(View.GONE);
				showdetails_layout.setVisibility(View.GONE);
				mycart_layout.setVisibility(View.VISIBLE);
				book_test_layout.setVisibility(View.GONE);
			}
			else
			{
				mycart_layout.setVisibility(View.GONE);
				book_test_layout.setVisibility(View.VISIBLE);
				showdetails_layout.setVisibility(View.VISIBLE);
			}

			setOnClickListener();
		}

		private void setOnClickListener()
		{
			add_test_icon.setOnClickListener(mOnClickListener);
		}

		public int getIndex()
		{
			return mIndex;
		}

		public void setIndex(final int index)
		{
			mIndex = index;
		}
	}

}
