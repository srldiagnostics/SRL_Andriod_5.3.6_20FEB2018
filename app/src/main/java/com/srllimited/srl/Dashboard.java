package com.srllimited.srl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.AddressData;
import com.srllimited.srl.data.CarouselData;
import com.srllimited.srl.data.CityListData;
import com.srllimited.srl.data.ReportsData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.database.ReportsDatabase;
import com.srllimited.srl.internal.CarouselsTask;
import com.srllimited.srl.location.GetAddress;
import com.srllimited.srl.permission.DangerousPermResponseCallBack;
import com.srllimited.srl.permission.DangerousPermissionResponse;
import com.srllimited.srl.permission.DangerousPermissionUtils;
import com.srllimited.srl.serverapis.ApiConstants;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.service.Config;
import com.srllimited.srl.util.HardcodeData;
import com.srllimited.srl.util.LocationHandler;
import com.srllimited.srl.util.Log;
import com.srllimited.srl.util.NotificationUtils;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.StringUtil;
import com.srllimited.srl.util.TypefaceUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.utilities.Utility;
import com.srllimited.srl.widget.materialtabs.MaterialTab;
import com.srllimited.srl.widget.materialtabs.MaterialTabListener;
import com.srllimited.srl.widget.stikkyheaders.StikkyHeaderBuilder;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard extends MenuBaseActivity implements View.OnClickListener
{

	public static final String RECEIVE_CAROUSELS = "com.srllimited.srl.RECEIVE_CAROUSEL";
	public static final String RECEIVE_LOC = "com.srllimited.srl.RECEIVE_LOC";
	public static final String RECEIVE_LAT = "lat";
	public static final String RECEIVE_LON = "lon";
	public static final String mBroadcastStringAction = "com.truiton.broadcast.string";
	public static final String mBroadcasAction = "com.truiton.broadcast.loc";
	public static final String RECEIVE_NOTIFICATIONS = "com.srllimited.srl.RECEIVE_NOTIFICATION";
	public static final String mBroadcasActionNot = "com.truiton.broadcast.not";
	private static final String TAG = Dashboard.class.getSimpleName();
	private static final int RESULT_CODE_SEARCH_LOCATION = 9;
	private static final String JSON_CITY_ID = "CITY_ID";
	private static final String JSON_CITY_NAME = "CITY_NAME";
	private static final String JSON_DISPLAY_NAME = "DISPLAY_NAME";
	private static final String JSON_STATE_ID = "STATE_ID";
	private static final String JSON_FAVOURITE = "FAVOURITE";

	//	private ImageView mFooterArrow, mHeaderLocDropdown;
	//	private View mFooterOptions;
	private static final String JSON_ALIASES = "ALIASES";
	public static Activity home;
	public static String currentLoc = "";
	List<ReportsData> mReportsDatas = new ArrayList<>();
	ReportsDatabase reportDB;
	CarouselView carouselView;
	//	private MaterialTabViewHost mTabViewHost;
	String cityn = "";
	RelativeLayout yourLocationRLID, srlLocatorRLID;

	Location location;
	TextView enter_user_id, alert_heading;
	ImageView alert_header_image, close_popup;
	LinearLayout not_sure_view;
	RelativeLayout not_sure_call;
	RadioGroup radio_group;
	View view1, view2;
	Dialog alertDialog;
	String loggedin_username = "", groupId = "";
	List<CarouselData> carouselData = new ArrayList<>();
	LinearLayout lTab1, lTab2, lTab3, lTab4, lTab5;
	int PHONE_REQUES_CODE = 1;
	AlertDialog alert;
	private BroadcastReceiver mRegistrationBroadcastReceiver;
	private ArrayList<CityListData> allCitylist;
	//	private String tapHereAddress = "Tap Here";
	//	private String yourLocationNotAvailable = "Your Current Location N/A";
	private String yourLocation = "Your Current Location";
	private Context context;
	private TextView your_cur_loc, address, srl_address;
	private LinearLayout home_footer_aboutus, home_footer_call_us;
	private IntentFilter mIntentFilter;
	private IntentFilter intentFilter;
	//ShowcaseView showcaseView;
	private LocationHandler locationHandler;
	private int counterShowcase = 1;
	private View.OnClickListener mOnTabClickListener = new View.OnClickListener()
	{
		@Override
		public void onClick(final View view)
		{
			Intent intent = null;

			//			Toast.makeText(getApplicationContext(),"tab - "+tab.getPosition(),Toast.LENGTH_SHORT).show();
			switch (view.getId())
			{
				case R.id.tab1:

					if (Util.isCity(context))
					{
						Util.killBook();
						Constants.isPackage = false;
						intent = new Intent(Dashboard.this, BookATestActivity.class);
					}
					else
					{
						Util.showCityAlert(context);
					}

					break;

				case R.id.tab2:

					if (Util.isCity(context))
					{
						Util.killBook();
						Constants.isPackage = true;
						intent = new Intent(Dashboard.this, BookATestActivity.class);
					}
					else
					{
						Util.showCityAlert(context);
					}
					break;

				case R.id.tab3:

					// if (Util.isCity(context)) { //comment because city not mandatory to get report
					navigateToMyReports();
					/* } else {
					    Util.showCityAlert(context);
					}*/
					break;

				case R.id.tab4:

					Util.killMyFamily();
					navigateTomyfamily();

					//
					//                    if (Util.isCity(context)) {
					//                        Util.killHealthTrack();
					//                        navigateToHealth();
					//                    } else {
					//                        Util.showCityAlert(context);
					//                    }
					break;

				case R.id.tab5:

					Util.killMore();
					Intent more = new Intent(Dashboard.this, MoreActivity.class);
					startActivity(more);

					break;
			}
			if (intent != null)
			{
				startActivity(intent);
			}
		}
	};
	private MaterialTabListener mMaterialTabListener = new MaterialTabListener()
	{
		@Override
		public void onTabSelected(final MaterialTab tab)
		{
			//			mTabViewHost.setSelectedNavigationItem(tab.getPosition());

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
	private RecyclerView mRecyclerView;
	private ViewGroup mRootViewGroup;
	private List<String> mRes = new ArrayList<>();

	//    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
	//        public ViewPagerAdapter(FragmentManager fm) {
	//            super(fm);
	//        }
	//
	//        public Fragment getItem(int num) {
	//            return new FragmentText();
	//        }
	//
	//        @Override
	//        public int getCount() {
	//            return 5;
	//        }
	//
	//        @Override
	//        public CharSequence getPageTitle(int position) {
	//            return null;
	//        }
	//    }
	ImageListener imageListener = new ImageListener()
	{
		@Override
		public void setImageForPosition(int position, ImageView imageView)
		{

			try
			{
				if (mRes != null && !mRes.isEmpty())
				{
					if (mRes.get(position).equalsIgnoreCase("default"))
					{
						imageView.setBackgroundResource(R.drawable.banner_default);
					}
					else
					{
						Utility.setDimensions(context);
						int width = Utility.screenWidth;
						int heigth = Utility.screenHeight;
						Log.e("Screen Sizes Width :", width + "");
						Log.e("Screen Sizes Height :", heigth + "");
						String url = mRes.get(position) + "android_HDPI.png" + "";
						if (width > 0 && width < 700)
						{
							url = mRes.get(position) + "android_HDPI.png" + "";
						}
						if (width > 700 && width < 1000)
						{
							url = mRes.get(position) + "android_XHDPI.png" + "";
						}
						if (width > 1000)
						{
							url = mRes.get(position) + "android_XXHDPI.png" + "";
						}

						Picasso.with(context).load(url).into(imageView);

						//                imageView.setImageResource(sampleImages[position]);
					}
				}
			}
			catch (Exception e)
			{

			}
		}
	};
	private BroadcastReceiver mReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			//            Toast.makeText(context, intent.getAction() + "", Toast.LENGTH_SHORT).show();

			if (intent.getAction().equals(mBroadcasAction))
			{
				initSlidingBanners();
			}
			if (intent.getAction().equals(RECEIVE_LOC))
			{

				///sachin//      startLocationActivity(null);

			}

			if (intent.getAction().equals(mBroadcasActionNot))
			{

				try
				{
					String unreadNotification = SharedPrefsUtil.getStringPreference(context, "notificationscount");
					String readNotification = SharedPrefsUtil.getStringPreference(context, "readmsg");

					int displayNotification = Util.convertandcal(unreadNotification, readNotification);
					if (displayNotification != 0)
					{
						String notificationscount = displayNotification + "";
						if (Validate.notEmpty(notificationscount))
						{
							notification_count.setText(notificationscount);
							notification_count.setVisibility(View.VISIBLE);
						}
						else
							notification_count.setVisibility(View.GONE);
					}
					else
						notification_count.setVisibility(View.GONE);
				}
				catch (Exception e)
				{

					Log.e("unread", e + "");
				}
			}
		}
	};

	//*********************************************************************
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData mserverResponseData)
		{

			if (mserverResponseData != null)
			{
				switch (request.getReferralCode())
				{

					case Get_CONTENT:
						mserverResponseData.getArrayData();
						/// setAboutUsContent(mserverResponseData.getArrayData());

						break;
					case GET_CITY:
						allCitylist = parseCityList(mserverResponseData.getFullData());

						if (Validate.notEmpty(allCitylist))
						{

							for (CityListData cityListData : allCitylist)
							{

								if (cityListData.getCITY_NAME().toUpperCase().contains(cityn.toUpperCase()))
								{

									header_loc_name.setText(cityListData.getCITY_NAME().toString());
									SharedPrefsUtil.setStringPreference(context, "selectedcityid",
											cityListData.getCITY_ID() + "");
									SharedPrefsUtil.setStringPreference(context, "selectedcity",
											cityListData.getCITY_NAME().toString());
									SharedPrefsUtil.setStringPreference(context, "displayname",
											cityListData.getDISPLAY_NAME().toString());
									new CarouselsTask(getApplicationContext());
									break;
								}
								else if (cityListData.getALIASES() != null
										&& cityListData.getALIASES().toUpperCase().contains(cityn.toUpperCase()))
								{

									header_loc_name.setText(cityListData.getCITY_NAME().toString());
									SharedPrefsUtil.setStringPreference(context, "selectedcityid",
											cityListData.getCITY_ID() + "");
									SharedPrefsUtil.setStringPreference(context, "selectedcity",
											cityListData.getCITY_NAME().toString());
									SharedPrefsUtil.setStringPreference(context, "displayname",
											cityListData.getDISPLAY_NAME().toString());
									new CarouselsTask(getApplicationContext());
									break;
								}
							}
						}

						if (header_loc_name.getText().toString().equalsIgnoreCase("Select a city"))
						{
							setCityAlertDialog(false);
						}
						break;
					case GET_CALL_US:
						setCallUS(mserverResponseData.getArrayData());
						break;
				}
				//  allCitylist = parseCityList(mserverResponseData.getFullData());

			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{

		}
	};
	private View.OnClickListener mFooterOnClickListener = new View.OnClickListener()
	{
		@Override
		public void onClick(final View v)
		{
			DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
			switch (v.getId())
			{

				case R.id.yourLocationRLID:

					if (address.getText().toString().equalsIgnoreCase("Tap Here"))
					{
						String selectedAddress = address.getText().toString();
						if (!LocationHandler.canGetLocation(context))
						{
							if (selectedAddress.equalsIgnoreCase("Tap Here"))
							{
								showSettingsAlert();
							}
						}
					}
					startLocationActivity(null);
					break;

				case R.id.srlLocatorRLID:
					Util.killYourLoc();
					//startLocationActivity(YourLocationActivity.class);
					Intent labintent = new Intent(context, YourLocationActivity.class);
					startActivity(labintent);
					/*Intent labintent = new Intent(context, TakeSurvey_Activity.class);
					startActivity(labintent);*/

					break;

				//				case R.id.home_footer_arrow:
				//					if (mFooterOptions.getVisibility() == View.VISIBLE)
				//					{
				//						hideFooter();
				//					}
				//					else if (mFooterOptions.getVisibility() == View.GONE)
				//					{
				//						showFooter();
				//					}
				//					break;

				case R.id.home_footer_aboutus:
					Util.killSupport();
					Intent intent = new Intent(Dashboard.this, SupportActivity.class);
					startActivity(intent);
					break;
				case R.id.home_footer_call_us:

					//					handleTaskWithUserPermission(PHONE_REQUES_CODE);
					//                    notsurePopup(v);
					String callus = SharedPrefsUtil.getStringPreference(Dashboard.this, Constants.CALLUS);
					if (!(callus.equalsIgnoreCase("null")))
					{
						Util.callNow(context, callus/*"1800-222-000"*/);
					}
					else
					{
						sendRequest(ApiRequestHelper.getCALL_US(context, "CALL_US", ApiConstants.appVersion));
					}

					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.content_dashboard);
		context = Dashboard.this;
		reportDB = new ReportsDatabase(getApplicationContext());
		home = this;

		SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.login_credentials, 0); // 0 - for private mode
		loggedin_username = pref.getString(Constants.loggedin_username, null);
		groupId = pref.getString(Constants.loggedin_grp_id, null);

		//		mHeaderLocDropdown = (ImageView) findViewById(R.id.header_loc_dropdown);
		mHeaderLocDropdown.setVisibility(View.VISIBLE);
		//Facebook Analytic launch
		FacebookSdk.sdkInitialize(getApplicationContext());
		AppEventsLogger.activateApp(this);
		//        Util.readphonestate(context);
		/*onNewIntent(getIntent());*/
		String splash = SharedPrefsUtil.getStringPreference(context, "splash");

		try
		{
			Constants.devicetobepassed = Settings.Secure.getString(getApplicationContext().getContentResolver(),
					Settings.Secure.ANDROID_ID);
		}
		catch (Exception e)
		{

		}

		try
		{
			ApiConstants.osVersion = Build.VERSION.RELEASE + "";
		}
		catch (Exception e)
		{

		}

		navBack.setVisibility(View.GONE);
		your_cur_loc = (TextView) findViewById(R.id.your_cur_loc);
		address = (TextView) findViewById(R.id.address);
		srl_address = (TextView) findViewById(R.id.srl_address);
		home_footer_aboutus = (LinearLayout) findViewById(R.id.home_footer_aboutus);
		home_footer_call_us = (LinearLayout) findViewById(R.id.home_footer_call_us);

		yourLocationRLID = (RelativeLayout) findViewById(R.id.yourLocationRLID);
		srlLocatorRLID = (RelativeLayout) findViewById(R.id.srlLocatorRLID);

		home_footer_aboutus.setOnClickListener(mFooterOnClickListener);
		home_footer_call_us.setOnClickListener(mFooterOnClickListener);
		yourLocationRLID.setOnClickListener(mFooterOnClickListener);
		srlLocatorRLID.setOnClickListener(mFooterOnClickListener);

		//		headerlayout.setOnClickListener(mFooterOnClickListener);
		TypefaceUtil.setTypeFace(context, TypefaceUtil.openSans_SemiBold, your_cur_loc);
		TypefaceUtil.setTypeFace(context, TypefaceUtil.openSans_SemiBold, address);
		TypefaceUtil.setTypeFace(context, TypefaceUtil.openSans_SemiBold, srl_address);

		initTabs();
		/*  boolean tooltipdash = SharedPrefsUtil.getBooleanPreference(context, Constants.tooltips_dashboard, false);
		boolean SFirstLaunch_ToolTips = SharedPrefsUtil.getBooleanPreference(context, Constants.sp_iSFirstLaunch_ToolTips, false);
		if (!tooltipdash && !SFirstLaunch_ToolTips) {
		    showcase();
		}*/
		init();
		initSlidingBanners();
		initFooter();
		removePreviousPromocode();

		setAddressValues(null);

		/*
		* If grouptype user is 3 //
		* */
		try
		{

			if (groupId == null)
			{
				srl_address.setText("SRL Locator");
			}
			else
			{
				if (groupId.equalsIgnoreCase("3.0"))
				{
					srl_address.setText("Lab Locator");
				}
				else
				{
					srl_address.setText("SRL Locator");

				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		if (getIntent().hasExtra(Constants.NOTIFICATIONTYPE))
		{
			String type = getIntent().getStringExtra(Constants.NOTIFICATIONTYPE);
			String title = getIntent().getStringExtra(Constants.NOTIFICATIONTitle);
			String msg1 = getIntent().getStringExtra(Constants.NOTIFICATIONMSG);
			String buttontype = "";

			if (type.equalsIgnoreCase("1"))
			{
				buttontype = "View Report";
				notificationAlert(type, buttontype, title, msg1);
			}
			else if (type.equalsIgnoreCase("2"))
			{
				buttontype = "Book A Test";
				notificationAlert(type, buttontype, title, msg1);
			}
			else if (type.equalsIgnoreCase("3"))
			{
				genericAlert1(title, msg1);
			}

		}

		//////////sa/////
		AppsFlyerLib.getInstance().startTracking(this.getApplication(), Constants.APP_FLYER_KEY);
		Map<String, Object> eventValue = new HashMap<String, Object>();
		eventValue.put(AFInAppEventParameterName.CITY, cityn);
		eventValue.put(AFInAppEventParameterName.CUSTOMER_USER_ID, loggedin_username);
		AppsFlyerLib.getInstance().trackEvent(context, "Dashboard", eventValue);

		//-----Popup on clicking forgot password
		alertDialog = new Dialog(this);
		alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//-----Views in forgot password popup
		alertDialog.setCancelable(false);
		alertDialog.setContentView(R.layout.callus);

		//-----Views in forgot password popup
		/*  if (RestApiCallUtil.isOnline(context))
		 sendRequestWithoutProgress(ApiRequestHelper.getContent("CHECK_VERSION", "5.2.1"));
		 //   sendRequest(ApiRequestHelper.getContent("CHECK_VERSION", "5.2.1"));
		*/
		close_popup = (ImageView) alertDialog.findViewById(R.id.close_popup);
		not_sure_call = (RelativeLayout) alertDialog.findViewById(R.id.not_sure_call);

		not_sure_call.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				handleTaskWithUserPermission(PHONE_REQUES_CODE, "1800-222-000");
			}
		});

		close_popup.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				if (alertDialog != null)
				{
					alertDialog.dismiss();
				}
			}
		});

		//
		//		if (RestApiCallUtil.isOnline(context))
		//		{
		//			sendRequest(ApiRequestHelper.getCarousels());
		//		}
		//		else
		//		{
		initSlidingBanners();

		//		}

		if (Validate.notEmpty(splash) && splash.equalsIgnoreCase("true"))
		{
			if (header_loc_name.getText().toString().equalsIgnoreCase("select a city")
					&& !LocationHandler.canGetLocation(context))
			{
				showSettingsAlert();
			}
		}

		AppRater.app_launched(home);

		mRegistrationBroadcastReceiver = new BroadcastReceiver()
		{
			@Override
			public void onReceive(Context context, Intent intent)
			{
				// checking for type intent filter
				if (intent.getAction().equals(Config.REGISTRATION_COMPLETE))
				{
					// gcm successfully registered
					// now subscribe to `global` topic to receive app wide notifications
					try
					{
						FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL_SRL);

						displayFirebaseRegId();
					}
					catch (Exception e)
					{

					}

				}
				else if (intent.getAction().equals(Config.PUSH_NOTIFICATION))
				{
					// new push notification is received

					String message = intent.getStringExtra("message");

					//                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

				}
			}
		};
		//

		try
		{
			displayFirebaseRegId();
		}
		catch (Exception e)
		{

		}
		//        checkForUpdates();
		startLocationActivity(null);
	}

	//*********************************************************************

	//    private ViewPager intro_images;
	//    private LinearLayout pager_indicator;
	//    private int dotsCount;
	//    private ImageView[] dots;
	//    private SlidingBannersAdapter mAdapter;

	//	private int[] mImageResources = {
	//			R.drawable.banner_02,
	//			R.drawable.banner_01,
	//			R.drawable.banner_03
	//	};

	private void removePreviousPromocode()
	{
		SharedPrefsUtil.setStringPreference(context, "promocode", "");
		SharedPrefsUtil.setStringPreference(context, "promocode_temp", "");
		SharedPrefsUtil.setStringPreference(context, "promoDiscountAmt", "");
	}

	//    public void initSlidingBanners() {
	//        int carcount = SharedPrefsUtil.getIntegerPreference(context, "carouselcount", 0);
	////		int[] mImageResources = new int[carcount+1];
	//
	//
	//        mRes.clear();
	//
	//        if (carcount > 0) {
	//            for (int i = 0; i <= carcount; i++) {
	//
	//
	//                mRes.add(SharedPrefsUtil.getStringPreference(context, "carouselimg" + i + ""));
	//            }
	//            intro_images = (ViewPager) findViewById(R.id.pager_introduction);
	//
	//            // Disable clip to padding
	//            intro_images.setClipToPadding(false);
	//            // set padding manually, the more you set the padding the more you see of prev & next page
	//            intro_images.setPadding(40, 20, 40, 20);
	//            // sets a margin b/w individual pages to ensure that there is a gap b/w them
	//            intro_images.setPageMargin(20);
	//
	//            pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
	//
	//            mAdapter = new SlidingBannersAdapter(Dashboard.this, mRes);
	//            intro_images.setAdapter(mAdapter);
	//            intro_images.setCurrentItem(0);
	//            intro_images.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
	//                @Override
	//                public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
	//
	//                }
	//
	//                @Override
	//                public void onPageSelected(final int position) {
	//                    // when user do a swipe the selected tab change
	//                    for (int i = 0; i < dotsCount; i++) {
	//                        dots[i].setImageResource(R.drawable.nonselecteditem_dot);
	//                    }
	//
	//                    dots[position].setImageResource(R.drawable.selecteditem_dot);
	//
	//                }
	//
	//                @Override
	//                public void onPageScrollStateChanged(final int state) {
	//
	//                }
	//            });
	//
	//            setUiPageViewController();
	//        } else {
	//            mRes.add("default");
	//            intro_images = (ViewPager) findViewById(R.id.pager_introduction);
	//
	//            // Disable clip to padding
	//            intro_images.setClipToPadding(false);
	//            // set padding manually, the more you set the padding the more you see of prev & next page
	//            intro_images.setPadding(40, 20, 40, 20);
	//            // sets a margin b/w individual pages to ensure that there is a gap b/w them
	//            intro_images.setPageMargin(20);
	//
	//            pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
	//
	//            mAdapter = new SlidingBannersAdapter(Dashboard.this, mRes);
	//            intro_images.setAdapter(mAdapter);
	//            intro_images.setCurrentItem(0);
	//        }
	//    }
	//
	//    private void setUiPageViewController() {
	//        if (pager_indicator.getVisibility() == View.VISIBLE) {
	//            dotsCount = mAdapter.getCount();
	//
	//            if (dotsCount > 0) {
	//                dots = new ImageView[dotsCount];
	//                pager_indicator.removeAllViews();
	//                for (int i = 0; i < dotsCount; i++) {
	//                    dots[i] = new ImageView(this);
	//                    dots[i].setImageResource(R.drawable.nonselecteditem_dot);
	//
	//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
	//                            LinearLayout.LayoutParams.WRAP_CONTENT,
	//                            LinearLayout.LayoutParams.WRAP_CONTENT
	//                    );
	//
	//                    params.setMargins(4, 0, 4, 0);
	//
	//                    pager_indicator.addView(dots[i], params);
	//                }
	//
	//                dots[0].setImageResource(R.drawable.selecteditem_dot);
	//            }
	//        }
	//    }

	//*********************************************************************

	@Override
	protected void onNewIntent(Intent intent)
	{
		super.onNewIntent(intent);
	}

	//	private void hideFooter()
	//	{
	//		AnimationUtil.collapse(mFooterOptions, 400);
	//		mFooterArrow.setImageResource(R.drawable.bottom_arrow_icon);
	//	}
	//
	//	private void showFooter()
	//	{
	//		AnimationUtil.expand(mFooterOptions, 300);
	//		mFooterArrow.setImageResource(R.drawable.bottom_arrowup_icon);
	//	}

	@Override
	public void onBackPressed()
	{
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START))
		{
			drawer.closeDrawer(GravityCompat.START);
		}
		else
		{
			super.onBackPressed();

		}
		Util.killPayOpt();
	}

	private void initTabs()
	{
		//		mTabViewHost = (MaterialTabViewHost) findViewById(R.id.tabview_host);
		//		mTabViewHost.setSelectionPersists(false);
		//
		//		// insert all tabs from mViewPagerAdapter data
		//		for (int i = 0; i < 5; i++)
		//		{
		//			mTabViewHost.addTab(context, getPageTitle(i), getIcon(i), mMaterialTabListener);
		//		}
		//
		//		mTabViewHost.notifyDataSetChanged();

		LinearLayout lTab1 = (LinearLayout) findViewById(R.id.tab1);
		LinearLayout lTab2 = (LinearLayout) findViewById(R.id.tab2);
		LinearLayout lTab3 = (LinearLayout) findViewById(R.id.tab3);
		LinearLayout lTab4 = (LinearLayout) findViewById(R.id.tab4);
		LinearLayout lTab5 = (LinearLayout) findViewById(R.id.tab5);

		findViewById(R.id.tab1).setOnClickListener(mOnTabClickListener);

		findViewById(R.id.tab2).setOnClickListener(mOnTabClickListener);

		findViewById(R.id.tab3).setOnClickListener(mOnTabClickListener);

		findViewById(R.id.tab4).setOnClickListener(mOnTabClickListener);

		findViewById(R.id.tab5).setOnClickListener(mOnTabClickListener);

	}

	private String getPageTitle(int position)
	{
		switch (position)
		{
			case 0:
				return "Book A Test";
			case 1:
				return "Packages";
			case 2:
				return "Reports";
			case 3:
				return "Offers";
			case 4:
				return "More";
			default:
				return null;
		}
	}

	private Drawable getIcon(int position)
	{
		int resId = -1;
		switch (position)
		{
			case 0:
				resId = R.drawable.book_a_test_icon;
				break;
			case 1:
				resId = R.drawable.packages_icon;
				break;
			case 2:
				resId = R.drawable.reports_icon;
				break;
			case 3:
				resId = R.drawable.offers_icon;
				break;
			case 4:
				resId = R.drawable.more_icon;
				break;
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			return getResources().getDrawable(resId, getApplicationContext().getTheme());
		}
		else
		{
			return getResources().getDrawable(resId);
		}
	}

	private void init()
	{
		String FCM_Token = SharedPrefsUtil.getStringPreference(Dashboard.this, Constants.FCM_RegId);
		Constants.RegID = FCM_Token;
		mRootViewGroup = (ViewGroup) findViewById(R.id.layout_container);
		mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		mRecyclerView.setHasFixedSize(true);

		StikkyHeaderBuilder.stickTo(mRecyclerView).setHeader(R.id.header, mRootViewGroup)
				.minHeightHeaderDim(R.dimen.min_height_header).build();

		HardcodeData.populateRecyclerView(context, mRecyclerView);
	}

	private void initFooter()
	{
		//		mFooterArrow = (ImageView) findViewById(R.id.home_footer_arrow);
		//		mFooterOptions = findViewById(R.id.home_footer_options);

		//		hideFooter();

		//		mFooterArrow.setOnClickListener(mFooterOnClickListener);
		findViewById(R.id.home_footer_aboutus).setOnClickListener(mFooterOnClickListener);
		findViewById(R.id.home_footer_call_us).setOnClickListener(mFooterOnClickListener);
	}

	private void startLocationActivity(final Class<?> cls)
	{

		locationHandler = new LocationHandler(context, new LocationHandler.onLocationResponseListener()
		{
			@Override
			public void onResponse(final boolean success)
			{
				if (success)
				{
					final Intent intent = locationHandler.getSelectedClass();

					if (address.getText().toString().equalsIgnoreCase("Tap Here"))
					{

						setAddressValue(LocationHandler.getLocation());
					}

					if (Validate.notNull(intent))
					{
						startActivity(intent);
					}
				}
			}
		});
		locationHandler.checkWithLastLocation();

		//
		//
		//		if (locationHandler == null)
		//		{
		//			locationHandler = new LocationHandler(context, new LocationHandler.onLocationResponseListener()
		//			{
		//				@Override
		//				public void onResponse(final boolean success)
		//				{
		//					if (success)
		//					{
		//						final Intent intent = locationHandler.getSelectedClass();
		//
		//						if (address.getText().toString().equalsIgnoreCase("Tap Here"))
		//						{
		//							setAddressValue(LocationHandler.getLocation());
		//						}
		//
		//						if (Validate.notNull(intent))
		//						{
		//							startActivity(intent);
		//						}
		//					}
		//
		//					locationHandler.isRunning = false;
		//
		//				}
		//			});
		//		}
		//
		//		if (!locationHandler.isRunning)
		//		{
		//			locationHandler.setSelectedClass(null);
		//			if (cls != null)
		//			{
		//				Intent intent2 = new Intent(Dashboard.this, cls);
		//				locationHandler.setSelectedClass(intent2);
		//			}
		//			locationHandler.checkWithLastLocation();
		//		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.test_nav_menu_1, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{

		int id = item.getItemId();

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		String serverversion = SharedPrefsUtil.getStringPreference(Dashboard.this, "serverVersion");
		try
		{
			if (!(serverversion.equalsIgnoreCase("null")))
			{
				if (Integer.parseInt(serverversion) > Integer.parseInt(ApiConstants.appVersion.replace(".", "")))
				{
					showSettingsAlert1();
				}
				else
				{

				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		String city = SharedPrefsUtil.getStringPreference(context, "selectedcity");
		if (Validate.notEmpty(city))
			header_loc_name.setText(city);
		if (LocationHandler.canGetLocation(context))
		{
			String cur = SharedPrefsUtil.getStringPreference(context, "currentloc");
			if (!Validate.notEmpty(cur))
			{
				callCity();
			}
		}

		try
		{
			String unreadNotification = SharedPrefsUtil.getStringPreference(context, "notificationscount");
			String readNotification = SharedPrefsUtil.getStringPreference(context, "readmsg");

			int displayNotification = Util.convertandcal(unreadNotification, readNotification);

			if (displayNotification != 0)
			{
				String notificationscount = displayNotification + "";
				if (Validate.notEmpty(notificationscount))
				{
					notification_count.setText(notificationscount);
					notification_count.setVisibility(View.VISIBLE);
				}
				else
					notification_count.setVisibility(View.GONE);
			}
			else
				notification_count.setVisibility(View.GONE);
		}
		catch (Exception e)
		{

			Log.e("unread", e + "");
		}

		mIntentFilter = new IntentFilter();
		mIntentFilter.addAction(Dashboard.mBroadcastStringAction);
		mIntentFilter.addAction(Dashboard.mBroadcasAction);
		mIntentFilter.addAction(mBroadcasActionNot);
		registerReceiver(mReceiver, mIntentFilter);

		//////////////
		// register GCM registration complete receiver
		LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
				new IntentFilter(Config.REGISTRATION_COMPLETE));

		// register new push message receiver
		// by doing this, the activity will be notified each time a new message arrives
		LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
				new IntentFilter(Config.PUSH_NOTIFICATION));

		// clear the notification area when the app is opened
		NotificationUtils.clearNotifications(getApplicationContext());

		/*  String  serverversion = SharedPrefsUtil.getStringPreference(Dashboard.this, "serverVersion");
		if(serverversion.equalsIgnoreCase(ApiConstants.appVersion)){

		}
		else{
		    showSettingsAlert1();
		}*/
		//        checkForCrashes();
	}

	private void callCity()
	{
		if (header_loc_name.getText().toString().equalsIgnoreCase("Select a city"))
		{
			startLocationActivity(null);
		}
		SharedPrefsUtil.setStringPreference(context, "currentloc", "cur");
	}

	private void setAddressValue(Location location)
	{
		if (Validate.notNull(location))
		{
			setAddressValue(new LatLng(location.getLatitude(), location.getLongitude()));
		}
		else
		{

			setAddressValues(null);
		}
	}

	private void setAddressValue(LatLng latLng)
	{
		setAddressValues(GetAddress.fetchCityAndState(this, latLng));
	}

	private void setAddressValues(AddressData value)
	{

		if (value != null)
		{
			String city = value.getCity();
			if (Validate.notEmpty(city))
			{
				your_cur_loc.setText(yourLocation);
				String citystte = GetAddress.fetchCityAndState(value);
				if (Validate.notEmpty(citystte))
					address.setText(StringUtil.getValidString(citystte));
				if (header_loc_name.getText().toString().equalsIgnoreCase("Select a city"))
				{
					currentLoc = StringUtil.getValidString(city);
					//                    header_loc_name.setText(StringUtil.getValidString(city));
					Toast.makeText(context, "Your Current city is: " + city + "", Toast.LENGTH_SHORT).show();
					//                    SharedPrefsUtil.setStringPreference(context, "selectedcity", currentLoc);
					//                    SharedPrefsUtil.setStringPreference(context, "displayname", citystte);
					cityn = StringUtil.getValidString(city);
					header_loc_name.setText("Select a city");

					sendRequestWithoutProgress(ApiRequestHelper.getCity(context));
				}
			}
		}
		else
		{
			address.setText("Tap Here");
		}

	}

	private void navigateToMyReports()
	{
		SharedPrefsUtil.setIntegerPreference(context, Constants.sharedPreferenceSelectedLoginActivity,
				Constants.m_reports);
		if (Util.getStoredUsername(context) != null && !Util.getStoredUsername(context).isEmpty()
				&& !Util.isRem(context))
		{
			Util.killMyReportEntry();
			Intent i = new Intent(Dashboard.this, MyReportEntryDetails.class);
			startActivity(i);
		}
		else
		{
			Util.killLogin();
			Intent intent = new Intent(Dashboard.this, LoginScreenActivity.class);
			startActivity(intent);
		}

	}

	private void navigateToHealth()
	{

		SharedPrefsUtil.setIntegerPreference(context, Constants.sharedPreferenceSelectedLoginActivity,
				Constants.m_health);
		if (Util.getStoredUsername(context) != null && !Util.getStoredUsername(context).isEmpty()
				&& !Util.isRem(context))
		{
			Util.killHealthTrack();
			Intent i = new Intent(Dashboard.this, HealthTracker.class);
			startActivity(i);
		}
		else
		{
			Util.killLogin();
			Intent intent = new Intent(Dashboard.this, LoginScreenActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}

	}

	@Override
	protected void onStart()
	{
		super.onStart();
		//		checkLocationProgress();
		SharedPrefsUtil.setIntegerPreference(context, Constants.sharedPreferenceSelectedLoginActivity,
				Constants.m_login);
	}

	//    private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>() {
	//        @Override
	//        public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData) {
	//
	//            Log.e("serverresponse", serverResponseData + "");
	//            switch (request.getReferralCode()) {
	//                case GET_CAROUSELS:
	//
	//                    setCarouselData(serverResponseData.getArrayData());
	//                    break;
	//            }
	//        }
	//
	//        @Override
	//        public void onResponseError(final ApiRequest request, final Exception error) {
	//
	//        }
	//    };

	private void showSettingsAlert()
	{

		AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		builder.setMessage("Location Services Disabled")
				.setPositiveButton("Cancel", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int id)
					{
						if (Util.isAppLaunched(context))
						{
							setCityAlertDialog(false);
						}
						SharedPrefsUtil.setStringPreference(context, "splash", "false");
						SharedPrefsUtil.setStringPreference(context, Constants.isAppLaunched, "true");
						dialog.dismiss();
						dialog.dismiss();
					}
				}).setNegativeButton("Enable", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int id)
					{
						SharedPrefsUtil.setStringPreference(context, "splash", "false");
						SharedPrefsUtil.setStringPreference(context, Constants.isAppLaunched, "true");
						Intent myIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						context.startActivity(myIntent);
					}
				});

		builder.create().show();

	}

	private void handleTaskWithUserPermission(int requestCode, final String phn)
	{
		DangerousPermissionUtils.getPermission(context,
				new String[] { Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE }, requestCode)
				.enqueue(new DangerousPermResponseCallBack()
				{
					@Override
					public void onComplete(final DangerousPermissionResponse permissionResponse)
					{
						if (permissionResponse.isGranted())
						{
							if (permissionResponse.getRequestCode() == PHONE_REQUES_CODE)
							{
								if (ActivityCompat.checkSelfPermission(context,
										Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
										&& ActivityCompat.checkSelfPermission(context,
												Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
								{
									return;
								}

								try
								{

									Intent callIntent = new Intent(Intent.ACTION_CALL);
									callIntent.setData(Uri.parse("tel:" + phn + ""));
									context.startActivity(callIntent);
								}
								catch (Exception e)
								{

								}

							}
						}
					}
				});
	}

	private void notsurePopup(View v)
	{
		try
		{
			if (alertDialog != null)
			{
				alertDialog.dismiss();
			}
			alertDialog.show();
		}
		catch (Exception e)
		{

		}
	}

	//    @Override
	//    protected void onDestroy() {
	//        if(mReceiver!=null)
	//        unregisterReceiver(mReceiver);
	//        super.onDestroy();
	//    }

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	private void setCarouselData(JSONArray jArray)
	{
		int count = 0;
		if (jArray != null)
		{
			try
			{
				if (Validate.notNull(jArray))
				{

					for (int i = 0; i < jArray.length(); i++)
					{
						CarouselData carouselData1 = new CarouselData();
						if (jArray.getJSONObject(i).getString("BANNER_FLG") != null
								&& jArray.getJSONObject(i).getString("BANNER_FLG").equalsIgnoreCase("Y"))
						{
							if (jArray.getJSONObject(i).getString("IMAGE_URL") != null)
							{

								SharedPrefsUtil.setIntegerPreference(context, "carouselcount", count);
								SharedPrefsUtil.setStringPreference(context, "carouselimg" + count + "",
										jArray.getJSONObject(i).getString("IMAGE_URL"));
								count++;
							}
						}
					}
				}
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}

			initSlidingBanners();
		}

	}

	@Override
	protected void onPause()
	{

		unregisterReceiver(mReceiver);
		LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
		super.onPause();
		//        unregisterManagers();
	}

	private void setCityAlertDialog(boolean isLaunched)
	{
		Util.killSearchLoc();
		Intent intent = new Intent(this, SearchLocationActivity.class);
		intent.putExtra("isLaunched", true);
		startActivityForResult(intent, RESULT_CODE_SEARCH_LOCATION);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode != RESULT_CODE_SEARCH_LOCATION)
		{
			return;
		}

		if (resultCode != Activity.RESULT_OK)
		{
			return;
		}

		if (Validate.notNull(data))
		{
			CityListData selectedCity = (CityListData) data
					.getSerializableExtra(SearchLocationActivity.EXTRA_PARAM_DATA);
			if (Validate.notNull(selectedCity))
			{
				header_loc_name.setText(selectedCity.getCITY_NAME().toString());
				SharedPrefsUtil.setStringPreference(context, "selectedcityid", selectedCity.getCITY_ID() + "");
				SharedPrefsUtil.setStringPreference(context, "selectedcity", selectedCity.getCITY_NAME().toString());
				SharedPrefsUtil.setStringPreference(context, "displayname", selectedCity.getDISPLAY_NAME().toString());
			}
		}
	}

	private void sendRequestWithoutProgress(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequestWithoutProgress(this, request, mResponseListener);
	}

	private void setCallUS(JSONArray jArray)
	{
		try
		{
			if (Validate.notNull(jArray))
			{

				for (int i = 0; i < jArray.length(); i++)
				{
					JSONObject jsonObject = jArray.getJSONObject(i);
					String screenName = jsonObject.getString("SCREEN_NAME");
					String content = jsonObject.getString("CONTENT");

					if (Validate.notEmpty(screenName) && Validate.notEmpty(content))
					{
						SharedPrefsUtil.setStringPreference(context, Constants.CALLUS + "", content + "");
						Util.callNow(context, content);
					}
				}
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

	}

	private ArrayList<CityListData> parseCityList(JSONObject jobj)
	{
		ArrayList<CityListData> cityListDatas = null;
		if (jobj != null)
		{
			try
			{
				JSONArray jArr = jobj.getJSONArray("data");
				if (Validate.notNull(jArr))
				{
					cityListDatas = new ArrayList<>();
					for (int i = 0; i < jArr.length(); i++)
					{
						CityListData cityListData = new CityListData();

						cityListData.setCITY_ID(jArr.getJSONObject(i).getInt(JSON_CITY_ID));
						cityListData.setCITY_NAME(jArr.getJSONObject(i).getString(JSON_CITY_NAME));
						cityListData.setDISPLAY_NAME(jArr.getJSONObject(i).getString(JSON_DISPLAY_NAME));
						cityListData.setSTATE_ID(jArr.getJSONObject(i).getInt(JSON_STATE_ID));
						cityListData.setFAVOURITE(jArr.getJSONObject(i).getString(JSON_FAVOURITE));
						cityListData.setALIASES(jArr.getJSONObject(i).getString(JSON_ALIASES));
						cityListDatas.add(cityListData);
					}
				}

			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}

		if (Validate.notEmpty(cityListDatas))
		{
			SharedPrefsUtil.setStringPreference(getApplicationContext(), TAG, jobj + "");
			//            hideProgressDialog();
		}
		else
		{
			//            hideProgressDialog();
			finish();
		}
		return cityListDatas;
	}

	//    private void setUiPageViewController() {
	//        if (pager_indicator.getVisibility() == View.VISIBLE) {
	//            dotsCount = mAdapter.getCount();
	//
	//            if (dotsCount > 0) {
	//                dots = new ImageView[dotsCount];
	//                pager_indicator.removeAllViews();
	//                for (int i = 0; i < dotsCount; i++) {
	//                    dots[i] = new ImageView(this);
	//                    dots[i].setImageResource(R.drawable.nonselecteditem_dot);
	//
	//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
	//                            LinearLayout.LayoutParams.WRAP_CONTENT,
	//                            LinearLayout.LayoutParams.WRAP_CONTENT
	//                    );
	//
	//                    params.setMargins(4, 0, 4, 0);
	//
	//                    pager_indicator.addView(dots[i], params);
	//                }
	//
	//                dots[0].setImageResource(R.drawable.selecteditem_dot);
	//            }
	//        }
	//    }

	// Fetches reg id from shared preferences
	// and displays on the screen
	private void displayFirebaseRegId()
	{

		try
		{
			String FCM_RegId = SharedPrefsUtil.getStringPreference(Dashboard.this, Constants.FCM_RegId);
			SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
			String regId = pref.getString("regId", null);
		}
		catch (Exception e)
		{

		}

	}

	public void initSlidingBanners()
	{

		carouselView = (CarouselView) findViewById(R.id.carouselView);
		int carcount = SharedPrefsUtil.getIntegerPreference(context, "carouselcount", 0);

		carouselView.setPageCount(carcount + 1);

		mRes.clear();

		if (carcount > 0)
		{
			for (int i = 0; i <= carcount; i++)
			{
				mRes.add(SharedPrefsUtil.getStringPreference(context, "carouselimg" + i + ""));
			}
		}
		carouselView.setImageListener(imageListener);

		carouselView.setImageClickListener(new ImageClickListener()
		{
			@Override
			public void onClick(int position)
			{
				String action = "";
				try
				{
					action = String.valueOf(SharedPrefsUtil.getLongPreference(context, mRes.get(position) + "", 0));
				}
				catch (Exception e)
				{

				}
				String screen = SharedPrefsUtil.getStringPreference(context, action);

				if (Validate.notEmpty(screen))
				{
					naviagteToBannerScreen(screen);

				}
			}
		});
		//            intro_images = (ViewPager) findViewById(R.id.pager_introduction);
		//
		//            // Disable clip to padding
		//            intro_images.setClipToPadding(false);
		//            // set padding manually, the more you set the padding the more you see of prev & next page
		//            intro_images.setPadding(40, 20, 40, 20);
		//            // sets a margin b/w individual pages to ensure that there is a gap b/w them
		//            intro_images.setPageMargin(20);
		//
		//            pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
		//
		//            mAdapter = new SlidingBannersAdapter(Dashboard.this, mRes);
		//            intro_images.setAdapter(mAdapter);
		//            intro_images.setCurrentItem(0);
		//            intro_images.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
		//                @Override
		//                public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {
		//
		//                }
		//
		//                @Override
		//                public void onPageSelected(final int position) {
		//                    // when user do a swipe the selected tab change
		//                    for (int i = 0; i < dotsCount; i++) {
		//                        dots[i].setImageResource(R.drawable.nonselecteditem_dot);
		//                    }
		//
		//                    dots[position].setImageResource(R.drawable.selecteditem_dot);
		//
		//                }
		//
		//                @Override
		//                public void onPageScrollStateChanged(final int state) {
		//
		//                }
		//            });
		//
		//            setUiPageViewController();
		//        } else {
		//            mRes.add("default");
		//            intro_images = (ViewPager) findViewById(R.id.pager_introduction);
		//
		//            // Disable clip to padding
		//            intro_images.setClipToPadding(false);
		//            // set padding manually, the more you set the padding the more you see of prev & next page
		//            intro_images.setPadding(40, 20, 40, 20);
		//            // sets a margin b/w individual pages to ensure that there is a gap b/w them
		//            intro_images.setPageMargin(20);
		//
		//            pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
		//
		//            mAdapter = new SlidingBannersAdapter(Dashboard.this, mRes);
		//            intro_images.setAdapter(mAdapter);
		//            intro_images.setCurrentItem(0);
		//        }
	}

	//    private void navigateToMyReports()
	//    {
	//        SharedPrefsUtil.setIntegerPreference(context, Constants.sharedPreferenceSelectedLoginActivity, Constants.m_reports);
	//        if (Util.getStoredUsername(context) != null && !Util.getStoredUsername(context).isEmpty()&& !Util.isRem(context))
	//        {
	//            Util.killMyReportEntry();
	//            Intent i = new Intent(context, MyReportEntryDetails.class);
	//            context.startActivity(i);
	//        }
	//        else
	//        {
	//            Util.killLogin();
	//            Intent intent = new Intent(context, LoginScreenActivity.class);
	//            context.startActivity(intent);
	//        }
	//    }

	private void naviagteToBannerScreen(String screen)
	{
		Intent intent = null;
		if (screen.equalsIgnoreCase("Book A Test"))
		{
			Util.killBook();
			intent = new Intent(context, BookATestActivity.class);
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
			intent = new Intent(context, YourLocationActivity.class);
		}

		if (intent != null)
		{
			context.startActivity(intent);
		}
	}

	private void navigateTomyfamily()
	{
		SharedPrefsUtil.setIntegerPreference(context, Constants.sharedPreferenceSelectedLoginActivity,
				Constants.m_family);
		if (Util.getStoredUsername(context) != null && !Util.getStoredUsername(context).isEmpty()
				&& !Util.isRem(context))
		{
			Util.killMyFamily();
			Constants.isFamilySel = false;
			Intent intent = new Intent(context, MyFamilyActivity.class);
			context.startActivity(intent);
		}
		else
		{
			Util.killLogin();
			Intent intent = new Intent(context, LoginScreenActivity.class);
			context.startActivity(intent);
		}
	}

	private void playStroreAccess()
	{
		Intent i = new Intent(Intent.ACTION_VIEW,
				Uri.parse("https://play.google.com/store/apps/details?id=com.srllimited.srl"));
		startActivity(i);
	}

	public void showSettingsAlert1()
	{

		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Dashboard.this,
				android.app.AlertDialog.THEME_HOLO_LIGHT);
		builder.setMessage("New version is available on Play-store.Please update to continue.").setCancelable(false)
				/*.setPositiveButton("No",
				        new DialogInterface.OnClickListener() {
				            public void onClick(DialogInterface dialog, int which) {
				                if (dialog != null)
				                    dialog.dismiss();
				            }
				        })*/.setNegativeButton("UPDATE", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						playStroreAccess();
					}
				});

		alert = builder.create();
		alert.show();
	}

	/*  private void showcase() {
	    int tab = 0;
	    String title = "";
	    String Content = "";
	    switch (counterShowcase) {
	        case 1:
	            tab = R.id.tab1;
	            title = "Book A Test";
	            Content = "Book A Test";
	            break;
	        case 2:
	            tab = R.id.tab2;
	            title = "Package";
	            Content = "Package";
	            break;
	        case 3:
	            tab = R.id.tab3;
	            title = "Report";
	            Content = "Report";
	            break;
	        case 4:
	            tab = R.id.tab4;
	            title = "My Family";
	            Content = "My Family";
	            break;
	        case 5:
	            tab = R.id.tab5;
	            title = "More";
	            Content = "More";
	            break;
	        case 6:
	            tab = R.id.tab5;
	            title = "More";
	            Content = "More";
	            break;
	    }
	    showcaseView = new ShowcaseView.Builder(this).withMaterialShowcase().setContentTitle(title).setContentText(String.format("Showing %1$s", Content)).setTarget(new ViewTarget(tab, this)).setShowcaseEventListener(new SimpleShowcaseEventListener() {
	        @Override
	        public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
	            if (counterShowcase == 5) {
	                showcaseView.hide();
	                try {
	                    SharedPrefsUtil.setBooleanPreference(context, Constants.tooltips_dashboard, true);
	
	                    SharedPrefsUtil.setBooleanPreference(context, Constants.sp_iSFirstLaunch_ToolTips, true);
	
	
	                } catch (Exception e) {
	
	                }
	
	            } else {
	                counterShowcase++;
	                showcase();
	
	            }
	        }
	    }).setStyle(R.style.CustomShowcaseTheme3).replaceEndButton(R.layout.view_custom_button)
	
	            .build();
	}*/

	@Override
	public void onClick(View v)
	{
		/* switch (counterShowcase) {
		    case 0:
		        showcaseView.setShowcase(new ViewTarget(lTab1), true);
		        break;
		
		    case 1:
		        showcaseView.setShowcase(new ViewTarget(lTab2), true);
		        break;
		    case 2:
		        showcaseView.setShowcase(new ViewTarget(lTab3), true);
		        break;
		
		    case 3:
		        showcaseView.setShowcase(new ViewTarget(lTab4), true);
		        break;
		    case 4:
		        showcaseView.setShowcase(new ViewTarget(lTab5), true);
		        break;
		
		
		    case 5:
		        showcaseView.setTarget(Target.NONE);
		        showcaseView.setContentTitle("Check it out");
		        showcaseView.setContentText("You don't always need a target to showcase");
		        showcaseView.setButtonText(getString(R.string.close));
		        setAlpha(0.4f, lTab1, lTab2,lTab3,lTab4,lTab5);
		
		
		      */
		/*  showcaseView.hide();
		setAlpha(1.0f, textView1, textView2, textView3);*//*
															break;
															
															
															}*/

	}

	private void setAlpha(float alpha, View... views)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			for (View view : views)
			{
				view.setAlpha(alpha);
			}
		}
	}

	/* @Override
	 public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
	
	     switch(requestCode) {
	
	         case LOCATION_REQUEST:
	             if (canAccessLocation()) {
	                 doLocationThing();
	             }
	             else {
	                 bzzzt();
	             }
	             break;
	     }
	 }
	
	
	
	 private boolean canAccessLocation() {
	     return(hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
	 }
	
	*/
	public void showSkipAlert()
	{

		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Dashboard.this,
				android.app.AlertDialog.THEME_HOLO_LIGHT);
		builder.setMessage("Skipping the PIN security may lead to unauthorized access.Do you still want to skip?")
				.setCancelable(false).setPositiveButton("No", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						if (dialog != null)
							dialog.dismiss();
					}
				}).setNegativeButton("YES", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						// navigateToActivity();
					}
				});

		alert = builder.create();
		alert.show();
	}

	private void notificationAlert(final String type, String buttontype, String title, String Msg)
	{
		final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this,
				AlertDialog.THEME_HOLO_LIGHT);
		builder.setTitle(title);
		builder.setMessage(Msg);

		builder.setPositiveButton(buttontype, new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface arg0, int arg1)
			{

				if (type.equalsIgnoreCase("1"))
				{
					navigateToMyReports();
					/* Util.killMyReportEntry();
					Intent i = new Intent(Dashboard.this, MyReportEntryDetails.class);
					startActivity(i);*/
				}
				else if (type.equalsIgnoreCase("2"))
				{
					Util.killBook();
					Intent intent = new Intent(Dashboard.this, BookATestActivity.class);
					startActivity(intent);
				}

				else
				{

				}
			}
		});

		builder.setNegativeButton("Not Now", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});

		AlertDialog alertDialog = builder.create();
		alertDialog.show();

	}

	private void genericAlert1(String title, String Msg)
	{
		final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this,
				AlertDialog.THEME_HOLO_LIGHT);
		builder.setTitle(title);
		builder.setMessage(Msg);

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int arg1)
			{

				dialog.dismiss();
			}
		});

		/*  builder.setNegativeButton("Not Now", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.dismiss();
		    }
		});*/

		AlertDialog alertDialog = builder.create();
		alertDialog.show();

	}
	/* private void checkForCrashes() {
	    CrashManager.register(this);
	}
	
	private void checkForUpdates() {
	    // Remove this for store builds!
	    UpdateManager.register(this);
	}
	
	private void unregisterManagers() {
	    UpdateManager.unregister();
	}*/
}
