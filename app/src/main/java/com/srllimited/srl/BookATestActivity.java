package com.srllimited.srl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.srllimited.srl.adapters.BookATestAdapter;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.BookTestORPackagesData;
import com.srllimited.srl.data.ProductHeaderData;
import com.srllimited.srl.data.ProductHeaderListItemData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.database.DatabaseHelper;
import com.srllimited.srl.database.ProductHeaderDatabase;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.TypefaceUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.widget.RoundCornerProgressView;
import com.srllimited.srl.widget.UISearchBar;
import com.srllimited.srl.widget.materialtabs.MaterialTab;
import com.srllimited.srl.widget.materialtabs.MaterialTabHost;
import com.srllimited.srl.widget.materialtabs.MaterialTabListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookATestActivity extends MenuBaseActivity implements View.OnClickListener, BookATestAdapter.NotifyActivity
{

	public static final int RESULT_CODE_FILTERED_PRODUCT = 10;
	public static final int RESULT_CODE_RESET = 12;
	private static final int RESULT_CODE_SEARCH_LOCATION = 9;
	public static Activity bookatest;
	private static ArrayList<BookTestORPackagesData> mBookaTestFilteredData = new ArrayList<>();
	private final Handler mHandler = new Handler();
	ServerResponseData serverResponseData;
	DatabaseHelper db;
	ProductHeaderDatabase productHeaderDB;
	Context context;
	TextView mSelected_menu_name, count_packages;
	ImageView mHeader_loc_dropdown, mFilter_img_view;
	RecyclerView mTest_receyclerview;
	float dX;
	ImageView offers;
	String listname = "";
	float dY;
	String existingCity = "";
	TextView cart_count;
	int lastAction;
	RecyclerView.Adapter mAdapter;
	SwipeRefreshLayout mSwipeRefreshLayout;
	View dragView;
	TextView ordernow;
	LinearLayout book_test_offer_layout;
	List<BookTestORPackagesData> mBookATestActivities = new ArrayList<>();
	FrameLayout progress_frame_layout;
	TextView cartpricetext, mostpopular;
	TextView progress_count_text, progress_text;
	boolean isProgress = false;
	LinearLayout sortprice, sortestname;
	ImageView testsorticon, pricesorticon, testsorttopicon, pricesorttopicon;
	ImageView iv_magnify;
	List<BookTestORPackagesData> tempBookATestActivities = new ArrayList<>();
	List<BookTestORPackagesData> temp = new ArrayList<>();
	BookTestORPackagesData tempBookTestORPackagesData;
	String citys = "";
	//	private EditText mFilterEditText;
	String state = "";
	String cityid = "";
	EditText editText;
	String loggedin_username = "";
	ImageView btnClear;
	String selType = "";
	String city = "";
	int cart_numbers = 0;
	ImageView close_popup;
	TextView phnno, callnow, cancel;
	private FirebaseAnalytics firebaseAnalytics;
	private String bookATest = "B";
	private String pkg = "P";
	private Dialog alertDialog = null;
	//	private ImageView btnClear;
	private UISearchBar mSearchView;
	//	UISearchBar.OnSearchListener mSearchLister = new UISearchBar.OnSearchListener()
	//	{
	//		@Override
	//		public void onClearSearch()
	//		{
	//			loadDefaultData();
	//		}
	//
	//		@Override
	//		public void onSearchText(String text)
	//		{
	//			mBookaTestFilteredData.clear();
	//			if (Validate.notEmpty(text))
	//			{
	//				for (BookTestORPackagesData data : mBookaTestDatas)
	//
	//				{
	//					if (text.length() > 1)
	//					{
	//						if (isSearchData(data.getPRDCT_CODE(), text) || isSearchData(data.getPRDCT_ALIASES(), text))
	//						{
	//							mBookaTestFilteredData.add(data);
	//						}
	//					}
	//					else if (isSearchFirstCharData(data.getPRDCT_ALIASES(), text))
	//					{
	//						mBookaTestFilteredData.add(data);
	//					}
	//				}
	//			}
	//			else
	//			{
	//				loadDefaultData();
	//			}
	//			setData();
	//		}
	//	};
	//
	//
	//	private boolean isSearchFirstCharData(String name, String searchTxt)
	//	{
	//		return (Validate.notEmpty(name)
	//				&& name.toLowerCase().charAt(0) == searchTxt.toLowerCase().charAt(0));
	//	}
	//
	//	private boolean isSearchData(String name, String searchTxt)
	//	{
	//		return (Validate.notEmpty(name)
	//				&& name.toLowerCase().contains(searchTxt.toLowerCase()));
	//	}
	private MaterialTabHost mTextTabHost;
	private RoundCornerProgressView mProgressView;
	private RecyclerView mRecyclerView;
	private ViewGroup mRootViewGroup;
	private MaterialTabListener mMaterialTabListener = new MaterialTabListener()
	{
		@Override
		public void onTabSelected(final MaterialTab tab)
		{

			mTextTabHost.setSelectedNavigationItem(tab.getPosition());
			Toast.makeText(context, "Clicked - " + tab.getPosition(), Toast.LENGTH_SHORT).show();

			switch (tab.getPosition())
			{
				case 0:

					break;

				case 2:

					break;
			}

		}

		@Override
		public void onTabReselected(final MaterialTab tab)
		{

		}

		@Override
		public void onTabUnselected(final MaterialTab tab)
		{

		}
	};
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData mserverResponseData)
		{
			new AsyncTaskRunner(request, mserverResponseData);
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{
			onItemsLoadComplete();
		}
	};
	UISearchBar.OnSearchListener mSearchLister = new UISearchBar.OnSearchListener()
	{
		@Override
		public void onClearSearch()
		{
			loadDefaultData();
		}

		@Override
		public void onSearchText(String text)
		{
			mBookaTestFilteredData.clear();
			if (Validate.notEmpty(text))
			{
				/* for (BookTestORPackagesData data : mBookATestActivities)

				{
				    if (text.length() > 0) {
				        if (isSearchData(data.getPRDCT_ALIASES(), text)) {
				            mBookaTestFilteredData.add(data);
				        }
				    }

				}

				setData();*/
			}
			else
			{
				loadDefault();
				//   loadDefaultData();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.book_a_test);
		context = BookATestActivity.this;
		bookatest = this;
		db = new DatabaseHelper(getApplicationContext());
		productHeaderDB = new ProductHeaderDatabase(getApplicationContext());
		existingCity = SharedPrefsUtil.getStringPreference(context, "selectedcity");
		SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.login_credentials, 0); // 0 - for private mode
		loggedin_username = pref.getString(Constants.loggedin_username, null);
		BookATestFilterActivity.isFilter = false;

		citys = SharedPrefsUtil.getStringPreference(context, "selectedcity");
		state = SharedPrefsUtil.getStringPreference(context, "displayname");
		cityid = SharedPrefsUtil.getStringPreference(context, "selectedcityid");

		// Obtain the Firebase Analytics instance.
		firebaseAnalytics = FirebaseAnalytics.getInstance(this);
		Bundle bundle = new Bundle();
		bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, 1);
		if (Constants.isPackage)
		{
			bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Package");
		}
		else
		{
			bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Book A Test");
		}
		//Logs an app event.
		firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
		//Sets whether analytics collection is enabled for this app on this device.
		firebaseAnalytics.setAnalyticsCollectionEnabled(true);

		//App Flyer
		AppsFlyerLib.getInstance().enableUninstallTracking(Constants.FCM_sender_id); // ADD THIS LINE HERE
		AppsFlyerLib.getInstance().startTracking(this.getApplication(), Constants.APP_FLYER_KEY);
		Map<String, Object> eventValue = new HashMap<String, Object>();
		eventValue.put(AFInAppEventParameterName.CITY, citys);
		eventValue.put(AFInAppEventParameterName.CUSTOMER_USER_ID, loggedin_username);
		eventValue.put(AFInAppEventParameterName.DEEP_LINK, "com.srllimited.srl.BookATestActivity");
		if (Constants.isPackage)
		{
			AppsFlyerLib.getInstance().trackEvent(context, "Package", eventValue);
		}
		else
		{
			AppsFlyerLib.getInstance().trackEvent(context, "Book A Test", eventValue);

		}
		//Facebook Analytic
		AppEventsLogger logger = AppEventsLogger.newLogger(this);
		if (Constants.isPackage)
		{
			logger.logEvent("Package");
		}
		else
		{
			logger.logEvent("Book A Test");
		}

		bookatest = this;
		//        ordernow = (TextView) findViewById(R.id.ordernow);
		mSelected_menu_name = (TextView) findViewById(R.id.selected_menu_name);
		mHeader_loc_dropdown = (ImageView) findViewById(R.id.header_loc_dropdown);
		count_packages = (TextView) findViewById(R.id.count_packages);
		mSearchView = (UISearchBar) findViewById(R.id.searchView);
		btnClear = (ImageView) mSearchView.findViewById(R.id.iv_clear);
		editText = (EditText) mSearchView.findViewById(R.id.et_search_input);
		mostpopular = (TextView) findViewById(R.id.mostpopular);
		iv_magnify = (ImageView) mSearchView.findViewById(R.id.iv_magnify);
		sortestname = (LinearLayout) findViewById(R.id.sortestname);
		sortprice = (LinearLayout) findViewById(R.id.sortprice);
		testsorticon = (ImageView) findViewById(R.id.testsorticon);
		pricesorticon = (ImageView) findViewById(R.id.pricesorticon);
		testsorttopicon = (ImageView) findViewById(R.id.testsorttopicon);
		pricesorttopicon = (ImageView) findViewById(R.id.pricesorttopicon);
		sortestname.setOnClickListener(this);
		sortprice.setOnClickListener(this);

		editText.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		editText.setHint("Search a test or package");

		iv_magnify.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Util.hideSoftKeyboard(context, v);
				String textToSearch = editText.getText().toString();
				if (Validate.notEmpty(textToSearch))
				{
					performSearch(textToSearch);
				}
				else
				{
					Toast.makeText(context, "Please enter text to search", Toast.LENGTH_SHORT).show();
				}
			}
		});
		editText.setOnEditorActionListener(new TextView.OnEditorActionListener()
		{
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
			{

				if (actionId == EditorInfo.IME_ACTION_SEARCH)
				{

					Util.hideSoftKeyboard(context, v);
					String textToSearch = editText.getText().toString();
					if (Validate.notEmpty(textToSearch))
					{
						performSearch(textToSearch);
					}
					else
					{
						Toast.makeText(context, "Please enter text to search", Toast.LENGTH_SHORT).show();
					}
					return true;
				}
				return false;
			}
		});

		btnClear.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				editText.setText("");
				loadDefault();
			}
		});

		mFilter_img_view = (ImageView) findViewById(R.id.filter_img_view);
		cart_count = (TextView) findViewById(R.id.cart_count);
		offers = (ImageView) findViewById(R.id.offers);
		offers.setVisibility(View.GONE);
		dragView = findViewById(R.id.draggable_view);

		cartpricetext = (TextView) findViewById(R.id.cartpricetext);
		mTextTabHost = (MaterialTabHost) findViewById(R.id.textTabHost);
		progress_frame_layout = (FrameLayout) findViewById(R.id.progress_frame_layout);
		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);

		mTextTabHost.setSelectionPersists(true);

		book_test_offer_layout = (LinearLayout) findViewById(R.id.book_test_offer_layout);
		dragView.setOnTouchListener(new OnDragTouchListener(dragView, new OnDragTouchListener.OnDragActionListener()
		{
			@Override
			public void onDragStart(final View view)
			{

			}

			@Override
			public void onDragEnd(final View view)
			{

			}

			@Override
			public void onSingleTap(final View view)
			{
				onClick(view);
			}
		}));
		TypefaceUtil.setTypeFace(context, TypefaceUtil.openSans_SemiBold, header_loc_name);
		//		header_loc_name.setText("Mumbai");

		mSelected_menu_name.setVisibility(View.VISIBLE);
		mHeader_loc_dropdown.setVisibility(View.VISIBLE);

		mSearchView.setOnSearchListener(mSearchLister); //on textchange
		mFilter_img_view.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (RestApiCallUtil.isOnline(context))
				{

					Intent intent = new Intent(BookATestActivity.this, BookATestFilterActivity.class);
					startActivityForResult(intent, RESULT_CODE_FILTERED_PRODUCT);
				}
				else
					RestApiCallUtil.NetworkError(context);

			}
		});

		mProgressView = (RoundCornerProgressView) findViewById(R.id.rcpv_progress_view);
		progress_text = (TextView) findViewById(R.id.progress_text);
		progress_count_text = (TextView) findViewById(R.id.progress_count_text);
		progress_text.setText("Order Now");
		progress_count_text.setText(getResources().getString(R.string.progress1));
		progress_text.setOnClickListener(this);
		offers.setOnClickListener(this);
		initTabs();

		String saveCity = SharedPrefsUtil.getStringPreference(context, "savecity");
		if (existingCity != null && saveCity != null && !existingCity.trim().equalsIgnoreCase(saveCity.trim()))
		{

			db.deleteBookATest();
			productHeaderDB.deleteHeader();
		}

		if (Constants.isPackage)
		{
			editText.setHint("Search a package");
			setPackage();
		}
		else
		{
			editText.setHint("Search a test or package");
			setBookATest();
		}
		progress_frame_layout.setVisibility(View.GONE);
		dragView.setVisibility(View.GONE);

		mBookATestActivities.clear();
		mRecyclerView = (RecyclerView) findViewById(R.id.test_receyclerview);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		mRecyclerView.setHasFixedSize(true);

		mAdapter = new BookATestAdapter(context, mBookATestActivities, false);
		mRecyclerView.setAdapter(mAdapter);

		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
		{
			@Override
			public void onRefresh()
			{
				// Refresh items
				refreshItems();
			}
		});

	}

	private void setProgress(final float progress, final boolean postDelayed)
	{
		if (postDelayed)
		{
			mHandler.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					mProgressView.setProgress(progress);
				}
			}, 200);
		}
		else
		{
			mProgressView.setProgress(progress);
		}

		//        setCartIcon(mBookATestActivities);
	}

	//Uncomment by sachin
	private void loadDefaultData()
	{
		mBookaTestFilteredData.clear();
		for (BookTestORPackagesData bookatestdata : temp)
		{
			mBookaTestFilteredData.add(bookatestdata);
		}

		setData();
	}

	private boolean isSearchData(String name, String searchTxt)
	{
		return (Validate.notEmpty(name) && name.toLowerCase().contains(searchTxt.toLowerCase()));
	}

	private void setData()
	{
		mBookATestActivities.clear();
		if (mBookATestActivities != null)
		{
			for (BookTestORPackagesData bookatestdata : mBookaTestFilteredData)
			{
				mBookATestActivities.add(bookatestdata);
			}
		}
		Log.e("setData", mBookATestActivities + "");
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{

			case R.id.draggable_view:

				Util.killMyCart();
				Intent cartintent = new Intent(BookATestActivity.this, MyCartActivity.class);
				startActivityForResult(cartintent, RESULT_CODE_RESET);
				break;
			//            case R.id.offers:
			//                if (RestApiCallUtil.isOnline(context)) {
			//                    Util.killOffersList();
			//                    Intent intenti = new Intent(BookATestActivity.this, OffersListActivity.class);
			//                    startActivity(intenti);
			//                } else {
			//                    RestApiCallUtil.NetworkError(context);
			//                }
			//                break;

			case R.id.searchView:
				Intent intent = new Intent(BookATestActivity.this, BookATestFilterActivity.class);
				startActivity(intent);
				break;

			case R.id.progress_text:
				Util.killMyCart();
				Intent cartinten = new Intent(BookATestActivity.this, MyCartActivity.class);
				startActivityForResult(cartinten, RESULT_CODE_RESET);

				break;

			case R.id.sortestname:

				if (testsorticon.getVisibility() == View.VISIBLE)
				{
					testsorticon.setVisibility(View.GONE);
					testsorttopicon.setVisibility(View.VISIBLE);
					sortByTestName();
				}
				else if (testsorttopicon.getVisibility() == View.VISIBLE)
				{
					testsorticon.setVisibility(View.VISIBLE);
					testsorttopicon.setVisibility(View.GONE);
					sortByTestTopName();
				}
				if (mAdapter != null)
				{
					mAdapter.notifyDataSetChanged();
				}

				break;

			case R.id.sortprice:
				if (pricesorticon.getVisibility() == View.VISIBLE)
				{
					pricesorticon.setVisibility(View.GONE);
					pricesorttopicon.setVisibility(View.VISIBLE);
					sortByPrice();
				}
				else if (pricesorttopicon.getVisibility() == View.VISIBLE)
				{
					pricesorticon.setVisibility(View.VISIBLE);
					pricesorttopicon.setVisibility(View.GONE);
					sortByTopPrice();
				}

				if (mAdapter != null)
					mAdapter.notifyDataSetChanged();

				break;

		}
	}

	private void initTabs()
	{

		// insert all tabs from mViewPagerAdapter data
		for (int i = 0; i < 3; i++)
		{
			mTextTabHost.addTab(
					mTextTabHost.newTab().setText(context, getPageTitle(i)).setTabListener(mMaterialTabListener));
		}
	}

	private String getPageTitle(int position)
	{
		switch (position)
		{
			case 0:
				return "All Health Packages";
			case 1:
				return "Health Check up";
			case 2:
				return "Health";

			default:
				return null;
		}
	}

	private void setBookATest()
	{
		mTextTabHost.setVisibility(View.GONE);
		count_packages.setVisibility(View.GONE);
		book_test_offer_layout.setVisibility(View.VISIBLE);
		mSelected_menu_name.setText("Book A Test");
		isProgress = false;
		mostpopular.setText("Most Popular Tests");
		String cityid = SharedPrefsUtil.getStringPreference(context, "selectedcityid");
		//!(logo_url.equalsIgnoreCase("null"))
		if (cityid != null)
		{ //
			sendRequest(ApiRequestHelper.getTopProducts(context, cityid, Util.getStoredUsername(context)));
		}
		else
		{
			Toast.makeText(BookATestActivity.this, "please select city first!", Toast.LENGTH_SHORT).show();
		}
	}

	private void setPackage()
	{
		mTextTabHost.setVisibility(View.GONE);
		count_packages.setVisibility(View.GONE);
		book_test_offer_layout.setVisibility(View.VISIBLE);
		mSelected_menu_name.setText("Packages");
		mostpopular.setText("No Package Found");
		isProgress = false;
		String cityid = SharedPrefsUtil.getStringPreference(context, "selectedcityid");
		sendRequest(ApiRequestHelper.getPackageDetails(context, cityid, "", Util.getStoredUsername(context), ""));
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	private void setBookTestOrPackagesAdapter()
	{

		if (mBookATestActivities != null && mBookATestActivities.size() > 0)
		{

			if (Constants.isPackage)
			{
				editText.setHint("Search a package");
				if (mBookATestActivities != null && mBookATestActivities.size() > 0)
					mostpopular.setText(mBookATestActivities.size() + " Packages Found");
				else
					mostpopular.setText("No Data Found");

			}
			else
			{
				editText.setHint("Search a test or package");
				if (mBookATestActivities != null && mBookATestActivities.size() > 0)
					mostpopular.setText("Most Popular Tests");
			}

			if (listname.equalsIgnoreCase(Constants.products))
			{
				if (selType != null && selType.equalsIgnoreCase("P"))
				{
					mostpopular.setText(mBookATestActivities.size() + " Packages Found");
				}
			}

			setCartIcon(mBookATestActivities);
			mAdapter.notifyDataSetChanged();
		}
		else
		{
			if (listname.equalsIgnoreCase(Constants.products))
			{
				mostpopular.setText("No Data Found");
				NeResponseError("No Data Available For This Category");
			}
			else if (Constants.isPackage)
			{
				editText.setHint("Search a package");
				if (mBookATestActivities != null && mBookATestActivities.size() > 0)
					mostpopular.setText(mBookATestActivities.size() + " Packages Found");
				else
					mostpopular.setText("No Data Found");
				if (listname.equalsIgnoreCase(Constants.prdct))
				{
					NeResponseError("No Data Available For Searched Package");
				}
				else
				{
					NeResponseError("No Data Available");
				}
			}
			else
			{
				editText.setHint("Search a test or package");
				mostpopular.setText("No Data Found");
				if (listname.equalsIgnoreCase(Constants.prdct))
				{
					NeResponseError("No Data Available For Searched Test");
				}
				else
				{
					NeResponseError("No Data Available");
				}
			}
			mAdapter.notifyDataSetChanged();
		}
	}

	private void setBookTestOrPkgsLiat(ServerResponseData serverResponseData, String listname)
	{

		JSONArray jArray = null;
		if (serverResponseData != null && serverResponseData.getObjectData() != null)
		{
			try
			{
				JSONObject jObj = serverResponseData.getObjectData();
				jArray = jObj.getJSONArray(listname);
			}
			catch (Exception e)
			{
			}
		}

		if (Validate.notNull(jArray))
		{
			List<ProductHeaderData> datalist = null;
			try
			{
				datalist = productHeaderDB.getAllHeaderList();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			List<BookTestORPackagesData> datas = new ArrayList<>();
			for (int i = 0; i < jArray.length(); i++)
			{
				try
				{
					JSONObject jObject = jArray.getJSONObject(i);
					BookTestORPackagesData bookTestORPackagesData = BookTestORPackagesData.newInit(jObject);

					if (bookTestORPackagesData != null)
					{
						if (Constants.isPackage)
						{
							//                            editText.setHint("Search a package");
							bookTestORPackagesData.setBook_pkg(pkg);
						}
						else
						{
							//                            editText.setHint("Search a test or package");
							bookTestORPackagesData.setBook_pkg(bookATest);
						}

						if (datalist != null && !datalist.isEmpty())
						{
							List<ProductHeaderListItemData> productHeaderListItemDatas = new ArrayList<>();
							JSONArray namesArray = jObject.names();
							ArrayList<String> lsitToBeAdded = new ArrayList<String>();

							if (namesArray != null)
							{
								for (int j = 0; j < namesArray.length(); j++)
								{
									lsitToBeAdded.add(namesArray.getString(j));
								}
							}

							if (lsitToBeAdded != null && !lsitToBeAdded.isEmpty())
							{
								for (ProductHeaderData productHeaderData : datalist)
								{

									for (String checkHeaders : lsitToBeAdded)
									{
										if (productHeaderData.getLBL_NAME().equalsIgnoreCase(checkHeaders.toString()))
										{

											ProductHeaderListItemData productHeaderListItemData = new ProductHeaderListItemData();

											String desc = jObject.getString(checkHeaders);

											if (productHeaderData.getLBL_HDR() != null
													&& !productHeaderData.getLBL_HDR().isEmpty() && desc != null
													&& !desc.isEmpty() && !desc.equalsIgnoreCase("null"))
											{
												productHeaderListItemData.setHeader(productHeaderData.getLBL_HDR());
												productHeaderListItemData.setDesc(desc);

												productHeaderListItemDatas.add(productHeaderListItemData);
											}
										}
									}
								}

								bookTestORPackagesData.setProductHeaderListItemDataList(productHeaderListItemDatas);
							}
						}

						BookTestORPackagesData dbBookTestORPackagesData = null;
						try
						{
							dbBookTestORPackagesData = db.getBook_Pkg_Object(bookTestORPackagesData.getID());
						}
						catch (Exception e)
						{
						}

						if (dbBookTestORPackagesData != null && dbBookTestORPackagesData.getID() != 0)
						{

							bookTestORPackagesData.setCart(dbBookTestORPackagesData.isCart());
							if (dbBookTestORPackagesData.isCart())
							{
								bookTestORPackagesData.setCartPrice(dbBookTestORPackagesData.getPRICE() + "");
							}
							db.updateBookOrPkgs(bookTestORPackagesData);
						}
						else
						{

							db.createBookTestOrPackages(bookTestORPackagesData);
						}
						datas.add(bookTestORPackagesData);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}

			//            if (datas != null && datas.size() > 0) {
			mBookATestActivities.clear();
			temp.clear();

			mBookATestActivities.addAll(datas);
			temp.addAll(datas); //this array help to search list

			//				if (!listname.equalsIgnoreCase(Constants.prdct))
			//				{
			//					mBookaTestDatas.add(bookTestORPackagesData);
			//				}
			//            }

		}

	}

	private void setHeaderList(ServerResponseData serverResponseData)
	{

		List<ProductHeaderData> productHeaderDataList = new ArrayList<>();
		JSONObject jObj = null;
		JSONArray jArray = null;
		if (serverResponseData != null)
		{
			if (serverResponseData.getObjectData() != null)
			{
				jObj = serverResponseData.getObjectData();
			}
			if (jObj != null)
			{
				if (!jObj.isNull("HDR"))
				{
					try
					{
						jArray = jObj.getJSONArray("HDR");

						if (Validate.notNull(jArray))
						{
							productHeaderDataList = new ArrayList<>();
							for (int i = 0; i < jArray.length(); i++)
							{
								ProductHeaderData productHeaderData = new ProductHeaderData();
								productHeaderData.setLBL_ID(jArray.getJSONObject(i).getInt(Constants.headerID));
								productHeaderData
										.setLBL_NAME(jArray.getJSONObject(i).getString(Constants.header_lbl_name));
								productHeaderData
										.setLBL_HDR(jArray.getJSONObject(i).getString(Constants.header_lbl_header));
								productHeaderData
										.setDISP_FLAG(jArray.getJSONObject(i).getString(Constants.header_display_flag));
								productHeaderData
										.setORDER_ID(jArray.getJSONObject(i).getDouble(Constants.header_order_id));
								productHeaderDB.createHeaderData(productHeaderData);
								productHeaderDataList.add(productHeaderData);
							}
						}
					}
					catch (JSONException e)
					{

					}
				}
			}
		}

		if (productHeaderDataList != null)
		{
			List<ProductHeaderData> datalist = productHeaderDB.getAllHeaderList();

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_CODE_RESET)
		{
			isProgress = false;
			//			loadDefault();
			if (data != null && data.getStringExtra("reset") != null
					&& data.getStringExtra("reset").equalsIgnoreCase("reset"))
			{
				Toast.makeText(context, "Your cart is empty.", Toast.LENGTH_SHORT).show();
			}

			String cityid = SharedPrefsUtil.getStringPreference(context, "selectedcityid");
			if (Constants.isPackage)
			{
				editText.setHint("Search a package");
				isProgress = false;
				sendRequest(
						ApiRequestHelper.getPackageDetails(context, cityid, "", Util.getStoredUsername(context), ""));
			}
			else
			{
				editText.setHint("Search a test or package");
				isProgress = false;
				sendRequest(ApiRequestHelper.getTopProducts(context, cityid, Util.getStoredUsername(context)));
			}
		}
		if (requestCode == RESULT_CODE_FILTERED_PRODUCT)
		{
			if (Validate.notNull(data))
			{
				String selectedCategory = data.getStringExtra(BookATestFilterActivity.selectedCategoryId);
				String selectedType = data.getStringExtra(BookATestFilterActivity.selectedType);
				if (Constants.isPackage)
					selectedType = "P";
				selType = selectedType + "";
				isProgress = false;
				BookATestFilterActivity.isFilter = false;
				String cityid = SharedPrefsUtil.getStringPreference(context, "selectedcityid");
				sendRequest(ApiRequestHelper.getFilterProducts(context, cityid, selectedType, selectedCategory));
			}
			return;
		}

		if (resultCode != Activity.RESULT_OK)
		{
			return;
		}

	}

	@Override
	protected void onResume()
	{
		super.onResume();

		//        if(!BookATestFilterActivity.isFilter) {
		//        mSearchView = (UISearchBar) findViewById(R.id.searchView);
		//        mSearchView.setEnabled(true);
		//        mSearchView.setClickable(true);
		String currentCity = SharedPrefsUtil.getStringPreference(context, "selectedcity");
		if (Validate.notEmpty(currentCity) && Validate.notEmpty(existingCity))
		{

			if (!existingCity.trim().equalsIgnoreCase(currentCity.trim()))
			{
				if (dragView.getVisibility() == View.VISIBLE)
				{
					callNow();
				}
				else
				{
					header_loc_name.setText(currentCity);
					existingCity = currentCity;

					citys = SharedPrefsUtil.getStringPreference(context, "selectedcity");
					state = SharedPrefsUtil.getStringPreference(context, "displayname");
					cityid = SharedPrefsUtil.getStringPreference(context, "selectedcityid");

					db.deleteBookATest();
					productHeaderDB.deleteHeader();

					if (Constants.isPackage)
					{
						editText.setHint("Search a package");
						setPackage();
					}
					else
					{
						editText.setHint("Search a test or package");
						setBookATest();
					}
				}
			}
		}
		//        }
		//
	}

	private void setCityAlertDialog()
	{
		Util.killSearchLoc();
		Intent intent = new Intent(this, SearchLocationActivity.class);
		startActivityForResult(intent, RESULT_CODE_SEARCH_LOCATION);
	}

	@Override
	public void onChangingListItem(final List<BookTestORPackagesData> bookTestORPackagesDatas)
	{
		setCartIcon(bookTestORPackagesDatas);
	}

	private void setCartIcon(List<BookTestORPackagesData> bookTestORPackagesDatas)
	{
		double cartCount = 0;
		cart_numbers = 0;

		try
		{
			if (db != null)
			{
				bookTestORPackagesDatas = db.getAllBook_pkgs_List();
			}
		}
		catch (Exception e)
		{

		}

		if (bookTestORPackagesDatas != null)
		{
			for (BookTestORPackagesData bookTestORPackagesData : bookTestORPackagesDatas)
			{
				if (bookTestORPackagesData.isCart())
				{
					cart_numbers++;
				}
				if (bookTestORPackagesData.getCartPrice() != null && !bookTestORPackagesData.getCartPrice().isEmpty())
				{
					try
					{
						cartCount = cartCount + Double.valueOf(bookTestORPackagesData.getCartPrice());
					}
					catch (Exception e)
					{

					}
				}
			}
		}
		if (cartCount > 0)
		{
			cart_count.setText(cart_numbers + "");
			progress_frame_layout.setVisibility(View.VISIBLE);
			setProgress(17, true);
			mProgressView.setVisibility(View.VISIBLE);
			dragView.setVisibility(View.VISIBLE);

			try
			{
				cartpricetext.setText("\u20B9" + Util.getIntegerToString(cartCount + ""));
			}
			catch (Exception e)
			{

			}
		}
		else
		{
			progress_frame_layout.setVisibility(View.GONE);
			mProgressView.setVisibility(View.GONE);
			dragView.setVisibility(View.GONE);
		}
	}

	private void performSearch(String searchText)
	{
		isProgress = false;
		String cityid = SharedPrefsUtil.getStringPreference(context, "selectedcityid");
		if (Constants.isPackage)
		{
			editText.setHint("Search a package");
			sendRequest(ApiRequestHelper.getPackageDetails(context, cityid, "", Util.getStoredUsername(context),
					searchText));
		}
		else
		{
			editText.setHint("Search a test or package");
			sendRequest(ApiRequestHelper.getSearchedProducts(context, cityid, searchText));
		}
	}

	private void loadDefault()
	{
		mBookATestActivities.clear();

		//        if (db != null) {
		//            if (Constants.isPackage) {
		//                editText.setHint("Search a package");
		//                mBookATestActivities.addAll(db.getBookATestsOrPkgs(pkg));
		//            } else {
		//                editText.setHint("Search a test or package");
		//                mBookATestActivities.addAll(db.getBookATestsOrPkgs(bookATest));
		//            }
		////			mBookATestActivities.addAll(mBookaTestDatas);
		//
		//
		//        }

		if (Constants.isPackage)
		{
			editText.setHint("Search a package");
			setPackage();
		}
		else
		{
			editText.setHint("Search a test or package");
			setBookATest();
		}

		//        setBookTestOrPackagesAdapter();
	}

	private void refreshItems()
	{
		//		isProgress = true;

		editText.setText("");

		mSwipeRefreshLayout.setRefreshing(true);
		mSwipeRefreshLayout.setEnabled(false);

		if (Constants.isPackage)
		{
			editText.setHint("Search a package");
			setPackage();
		}
		else
		{
			editText.setHint("Search a test or package");

			setBookATest();
		}
		//		sendRequest(ApiRequestHelper.getTopProducts("6", Util.getStoredUsername(context)));
	}

	void onItemsLoadComplete()
	{
		try
		{
			mSwipeRefreshLayout.setEnabled(true);
			mSwipeRefreshLayout.setRefreshing(false);
		}
		catch (Exception e)
		{

		}
	}

	private void NeResponseError(String msg)
	{

		try
		{
			final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, AlertDialog.THEME_HOLO_LIGHT);
			alertDialogBuilder.setTitle(msg);
			alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface arg0, int arg1)
				{
					arg0.dismiss();
				}
			});

			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();

		}
		catch (Exception e)
		{

		}
	}

	private void sortByPrice()
	{
		Collections.sort(mBookATestActivities, new Comparator<BookTestORPackagesData>()
		{
			@Override
			public int compare(BookTestORPackagesData o1, BookTestORPackagesData o2)
			{
				final double d1 = o1.getPRICE();
				final double d2 = o2.getPRICE();
				return Double.compare(d1, d2);

			}
		});

	}

	//    private void testNameSortingwithString(BookTestORPackagesData x) {

	/*for (int i = 0; i < mBookATestActivities.size(); i++)
	{
		tempBookTestORPackagesData = new BookTestORPackagesData();
		tempBookTestORPackagesData.setPRDCT_ALIASES(x.getPRDCT_ALIASES());
		tempBookATestActivities.add(tempBookTestORPackagesData);
	}
	
	boolean flag = true;  // will determine when the sort is finished
	BookTestORPackagesData temp;
	while (flag)
	{
		flag = false;
		for (int j = 0; j < tempBookATestActivities.size() - 1; j++)
		{
	
		}
	}*/
	/*Collections.sort(list, new Comparator<String>() {
	    @Override
		public int compare(String s1, String s2) {
			return s1.compareToIgnoreCase(s2);
		}
	});*/

	private void sortByTopPrice()
	{
		Collections.sort(mBookATestActivities, new Comparator<BookTestORPackagesData>()
		{
			@Override
			public int compare(BookTestORPackagesData o1, BookTestORPackagesData o2)
			{
				final double d1 = o1.getPRICE();
				final double d2 = o2.getPRICE();
				return Double.compare(d2, d1);

			}
		});

		//        Collections.reverse(mBookATestActivities);

	}

	private void sortByTestName()
	{
		try
		{
			Collections.sort(mBookATestActivities, new Comparator<BookTestORPackagesData>()
			{
				@Override
				public int compare(BookTestORPackagesData o1, BookTestORPackagesData o2)
				{
					return o1.getPRDCT_ALIASES().compareToIgnoreCase(o2.getPRDCT_ALIASES());
				}
			});
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private void sortByTestTopName()
	{
		try
		{
			//            Collections.sort(mBookATestActivities, new Comparator<BookTestORPackagesData>() {
			//                @Override
			//                public int compare(BookTestORPackagesData o1, BookTestORPackagesData o2) {
			//                    return o1.getPRDCT_ALIASES().compareToIgnoreCase(o2.getPRDCT_ALIASES());
			//                }
			//            });

			Collections.reverse(mBookATestActivities);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private void callNow()
	{

		if (alertDialog == null)
		{
			alertDialog = new Dialog(context);
			alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			alertDialog.setCancelable(false);
			alertDialog.setContentView(R.layout.callpopup);
			close_popup = (ImageView) alertDialog.findViewById(R.id.close_popup);
			phnno = (TextView) alertDialog.findViewById(R.id.phnno);

			phnno.setText("Your cart will be reset.Do you want to continue?");
			callnow = (TextView) alertDialog.findViewById(R.id.callnow);
			cancel = (TextView) alertDialog.findViewById(R.id.cancel);

			callnow.setText("Yes");
			cancel.setText("No");
			alertDialog.show();

			callnow.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(final View view)
				{
					String currentCity = SharedPrefsUtil.getStringPreference(context, "selectedcity");

					header_loc_name.setText(currentCity);
					existingCity = currentCity;

					citys = SharedPrefsUtil.getStringPreference(context, "selectedcity");
					state = SharedPrefsUtil.getStringPreference(context, "displayname");
					cityid = SharedPrefsUtil.getStringPreference(context, "selectedcityid");

					db.deleteBookATest();
					productHeaderDB.deleteHeader();

					if (Constants.isPackage)
					{
						editText.setHint("Search a package");
						setPackage();
					}
					else
					{
						editText.setHint("Search a test or package");
						setBookATest();
					}
					if (alertDialog != null)
					{
						alertDialog.dismiss();
						alertDialog = null;
					}
				}
			});

			cancel.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(final View view)
				{
					if (alertDialog != null)
					{

						header_loc_name.setText(existingCity);

						SharedPrefsUtil.setStringPreference(context, "selectedcityid", cityid + "");
						SharedPrefsUtil.setStringPreference(context, "selectedcity", citys);
						SharedPrefsUtil.setStringPreference(context, "displayname", state);

						alertDialog.dismiss();
						alertDialog = null;
					}
				}
			});
		}
	}

	@Override
	protected void onDestroy()
	{
		SharedPrefsUtil.setStringPreference(context, "savecity", existingCity);
		super.onDestroy();
	}

	public static class OnDragTouchListener implements View.OnTouchListener
	{
		private GestureDetector gestureDetector;
		private View mView;
		private View mParent;
		private boolean isDragging;
		private boolean isInitialized = false;
		private int width;
		private float xWhenAttached;
		private float maxLeft;
		private float maxRight;
		private float dX;
		private int height;
		private float yWhenAttached;
		private float maxTop;
		private float maxBottom;
		private float dY;
		private OnDragActionListener mOnDragActionListener;

		public OnDragTouchListener(View view)
		{
			this(view, (View) view.getParent(), null);
		}

		public OnDragTouchListener(View view, View parent)
		{
			this(view, parent, null);
		}

		public OnDragTouchListener(View view, OnDragActionListener onDragActionListener)
		{
			this(view, (View) view.getParent(), onDragActionListener);
		}

		public OnDragTouchListener(View view, View parent, OnDragActionListener onDragActionListener)
		{
			initListener(view, parent);
			setOnDragActionListener(onDragActionListener);
		}

		public void setOnDragActionListener(OnDragActionListener onDragActionListener)
		{
			mOnDragActionListener = onDragActionListener;
		}

		public void initListener(View view, View parent)
		{
			View v = parent.getRootView();

			gestureDetector = new GestureDetector(v.getContext(), new SingleTapConfirm());

			mView = view;
			mParent = parent;
			isDragging = false;
			isInitialized = false;

		}

		public void updateBounds()
		{
			updateViewBounds();
			updateParentBounds();
			isInitialized = true;
		}

		public void updateViewBounds()
		{
			width = mView.getWidth();
			xWhenAttached = mView.getX();
			dX = 0;

			height = mView.getHeight();
			yWhenAttached = mView.getY();
			dY = 0;
		}

		public void updateParentBounds()
		{
			maxLeft = 0;
			maxRight = maxLeft + mParent.getWidth();

			maxTop = 0;
			maxBottom = maxTop + mParent.getHeight();
		}

		@Override
		public boolean onTouch(View v, MotionEvent event)
		{
			if (gestureDetector.onTouchEvent(event))
			{
				// single tap
				if (mOnDragActionListener != null)
				{
					mOnDragActionListener.onSingleTap(mView);
				}
				return true;
			}
			else
			{
				if (isDragging)
				{
					float[] bounds = new float[4];
					// LEFT
					bounds[0] = event.getRawX() + dX;
					if (bounds[0] < maxLeft)
					{
						bounds[0] = maxLeft;
					}
					// RIGHT
					bounds[2] = bounds[0] + width;
					if (bounds[2] > maxRight)
					{
						bounds[2] = maxRight;
						bounds[0] = bounds[2] - width;
					}
					// TOP
					bounds[1] = event.getRawY() + dY;
					if (bounds[1] < maxTop)
					{
						bounds[1] = maxTop;
					}
					// BOTTOM
					bounds[3] = bounds[1] + height;
					if (bounds[3] > maxBottom)
					{
						bounds[3] = maxBottom;
						bounds[1] = bounds[3] - height;
					}

					switch (event.getAction())
					{
						case MotionEvent.ACTION_CANCEL:
						case MotionEvent.ACTION_UP:
							onDragFinish();
							break;
						case MotionEvent.ACTION_MOVE:
							mView.animate().x(bounds[0]).setDuration(0).start();
							mView.animate().y(bounds[1]).setDuration(0).start();
							break;
					}
					return true;
				}
				else
				{
					switch (event.getAction())
					{
						case MotionEvent.ACTION_DOWN:
							isDragging = true;
							if (!isInitialized)
							{
								updateBounds();
							}
							dX = v.getX() - event.getRawX();
							dY = v.getY() - event.getRawY();
							if (mOnDragActionListener != null)
							{
								mOnDragActionListener.onDragStart(mView);
							}
							return true;
					}
				}
			}
			return false;
		}

		private void onDragFinish()
		{
			if (mOnDragActionListener != null)
			{
				mOnDragActionListener.onDragEnd(mView);
			}

			dX = 0;
			dY = 0;
			isDragging = false;
		}

		public interface OnDragActionListener
		{
			void onDragStart(View view);

			void onDragEnd(View view);

			void onSingleTap(View view);
		}

		private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener
		{
			@Override
			public boolean onSingleTapUp(MotionEvent event)
			{
				return true;
			}
		}
	}

	private class ViewPagerAdapter extends FragmentStatePagerAdapter
	{
		public ViewPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		public Fragment getItem(int num)
		{
			return new FragmentText();
		}

		@Override
		public int getCount()
		{
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			return null;
		}
	}

	private class AsyncTaskRunner extends AsyncTask<Void, Void, Void>
	{
		private ApiRequest mRequest;

		private ServerResponseData mServerResponseData;

		public AsyncTaskRunner(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			mRequest = request;
			mServerResponseData = serverResponseData;
			execute();
		}

		@Override
		protected void onPreExecute()
		{
			Util.hideProgressDialog();
			Util.showProgressDialog(context, "Loading Tests...");

			//			mBookATestActivities.clear();
			//			setBookTestOrPackagesAdapter();
			//			if (!isProgress)
			//			{
			//			}
		}

		@Override
		protected Void doInBackground(final Void... voids)
		{

			List<ProductHeaderData> datalist = null;
			try
			{
				datalist = productHeaderDB.getAllHeaderList();
			}
			catch (Exception e)
			{

			}
			serverResponseData = null;
			listname = "";
			serverResponseData = mServerResponseData;

			switch (mRequest.getReferralCode())
			{
				case TOP_PRODUCTS:

					if (datalist != null && !datalist.isEmpty())
					{
						Log.d("header list", "in db");
					}
					else
					{
						setHeaderList(serverResponseData);
					}
					listname = Constants.top_products;
					break;

				case PACKAGE_DETAILS:
					if (datalist != null && !datalist.isEmpty())
					{
						Log.d("header list", "in db");
					}
					else
					{
						setHeaderList(serverResponseData);
					}
					listname = Constants.packages;
					break;
				case SEARCH_PRODUCTS:
					if (datalist != null && !datalist.isEmpty())
					{
						Log.d("header list", "in db");
					}
					else
					{
						setHeaderList(serverResponseData);
					}
					listname = Constants.prdct;
					break;
				case FILTER_PRODUCTS:
					listname = Constants.products;
					break;
			}

			setBookTestOrPkgsLiat(serverResponseData, listname);

			return null;
		}

		@Override
		protected void onPostExecute(final Void aVoid)
		{
			super.onPostExecute(aVoid);

			setBookTestOrPackagesAdapter();
			onItemsLoadComplete();

			Util.hideProgressDialog();
		}
	}
}