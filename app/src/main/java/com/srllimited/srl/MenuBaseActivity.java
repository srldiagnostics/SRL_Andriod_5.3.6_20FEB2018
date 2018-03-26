package com.srllimited.srl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appeaser.sublimenavigationviewlibrary.OnNavigationMenuEventListener;
import com.appeaser.sublimenavigationviewlibrary.SublimeBaseMenuItem;
import com.appeaser.sublimenavigationviewlibrary.SublimeMenu;
import com.appeaser.sublimenavigationviewlibrary.SublimeNavigationView;
import com.squareup.picasso.Picasso;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.CityListData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.data.UserData;
import com.srllimited.srl.internal.CarouselsTask;
import com.srllimited.srl.serverapis.ApiConstants;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiRequestReferralCode;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.utilities.AppDataBaseHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public abstract class MenuBaseActivity extends AppCompatActivity implements RestApiCallUtil.VolleyCallback
{
	private static final String TAG = Dashboard.class.getSimpleName();
	private static final int RESULT_CODE_SEARCH_LOCATION = 9;
	public static Activity menu;
	//    private IntentFilter mitentFilter;
	//-------Naviagtion V++iew ----------
	// Keys used when saving menu state to Bundle
	private final String SS_KEY_MENU_1 = "ss.key.menu.1";
	protected TextView loggedin_userid;
	protected DrawerLayout drawer;
	protected TextView header_loc_name;
	protected RelativeLayout headerlayout;
	protected Button history;
	protected TextView notification_count;
	//    public static final String mBroadcasActionNot = "com.truiton.broadcast.not";
	// nav drawer controls
	protected View navBack, navToggle;
	TextView logout_text;
	LinearLayout header_layout;
	ImageView srltext;
	ImageView mHeaderLocDropdown;
	//Data Base
	AppDataBaseHelper appDb = new AppDataBaseHelper(this);
	UserData _userAppData;
	//Update Priofile
	ArrayList<UserData> _updateuserData = new ArrayList<>();
	UserData _updateuserdataset;
	CircleImageView imageView;
	ImageView notification;
	String logo_url = "";
	private BroadcastReceiver mNotificationBroadcastReceiver;
	private LinearLayout logout;
	private Context context;
	//    public static final String RECEIVE_NOTIFICATIONS = "com.srllimited.srl.RECEIVE_NOTIFICATION";
	// For maintaining menu state in case of multiple menus
	private SublimeMenu firstMenu;
	// Navigation menu
	private SublimeNavigationView snv;
	private TextView tvFirstMenuLabel, tvSecondMenuLabel;
	private String loggedin_username = "", groupId = "";
	private String loggedin_pwd = "";
	private String loggedin_remember = "";
	private ImageView cancel_menu;
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			switch (request.getReferralCode())
			{

				///All States
				case PENDING_REGISTRATION:
				{
					String aaa = serverResponseData.getMsg();
					// {"code":100,"msg":"Not Registered User","data":null}
					JSONObject jObj = null;
					String responsetoActivity = "";
					if (serverResponseData != null)
					{

						try
						{
							jObj = serverResponseData.getFullData();
							responsetoActivity = serverResponseData.getMsg();//jObj.getString(Constants.fieldGetResponse);
							Log.e("createobj", responsetoActivity + "");
							if (responsetoActivity != null
									&& responsetoActivity.equalsIgnoreCase("Pending Registration"))
							{

								if (!jObj.isNull("data") && jObj.getJSONArray("data") != null)
								{
									JSONArray jArr = jObj.getJSONArray("data");
									Log.e("jArr", jArr + "");

									//  value.put(Constants.registered_mobile, jArr.getJSONObject(0).getString("MOBILE_NO"));
									if (jArr.length() > 0 && jArr.getJSONObject(0)
											.getString("MOBILE_NO") != null /*&& fetchValue.get("mobile") != null*/)
									{
										Util.killOtpReg();
										Intent intent = new Intent(MenuBaseActivity.this, OTPRegistration.class);
										intent.putExtra(Constants.registered_mobile,
												jArr.getJSONObject(0).getString("MOBILE_NO") + "");
										startActivity(intent);
									}
									else
									{
										Util.killReg();
										Constants.isRegEdited = false;
										Constants.isPatientDetails = false;
										Intent intent = new Intent(MenuBaseActivity.this, RegistrationScreen.class);
										startActivity(intent);
									}
								}
								//  Log.e("value", value + "");
							}
							else
							{

								Constants.isRegEdited = false;
								Constants.isPatientDetails = false;
								Util.killReg();
								Intent intent = new Intent(MenuBaseActivity.this, RegistrationScreen.class);
								startActivity(intent);

							}
						}
						catch (JSONException e)
						{
							e.printStackTrace();
						}
					}
				}
					break;
				case GET_LOGO:
					setLOGO(serverResponseData.getArrayData());
					break;
			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{

		}
	};
	//*********************************************************************
	//*********************************************************************
	private View.OnClickListener mFooterOnClickListener = new View.OnClickListener()
	{
		@Override
		public void onClick(final View v)
		{
			DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
			switch (v.getId())
			{
				case R.id.header_loc_name:
					setCityAlertDialog(false);
					break;
				case R.id.header_loc_dropdown:
					setCityAlertDialog(false);
					break;

				case R.id.cancel_menu:

					if (drawer.isDrawerOpen(GravityCompat.START))
					{
						drawer.closeDrawer(GravityCompat.START);
					}
					break;

				case R.id.logout:

					if (drawer.isDrawerOpen(GravityCompat.START))
					{
						drawer.closeDrawer(GravityCompat.START);
					}

					if (logout_text.getText().toString().equalsIgnoreCase("Logout"))
					{

						AlertDialog alert;

						android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
								MenuBaseActivity.this, android.app.AlertDialog.THEME_HOLO_LIGHT);
						builder.setMessage("Do you want to logout?").setCancelable(false)
								.setPositiveButton("No", new DialogInterface.OnClickListener()
								{
									public void onClick(DialogInterface dialog, int which)
									{
										if (dialog != null)
											dialog.dismiss();
									}
								}).setNegativeButton("Yes", new DialogInterface.OnClickListener()
								{
									public void onClick(DialogInterface dialog, int which)
									{
										SharedPrefsUtil.setStringPreference(context, "fourdigitpin", "");
										SharedPrefsUtil.setBooleanPreference(context, "splashpin", false);
										SharedPrefsUtil.setBooleanPreference(context, "remember", true);
										if (loggedin_remember != null && loggedin_remember.equalsIgnoreCase("true"))
										{
											Log.e("already", "logged in");

										}
										else
										{
											clearImage();
											appDb.deleteFamilyMemberData();
											loggedin_userid.setText("Guest");
											logout_text.setText("Login");
											SharedPrefsUtil.setStringPreference(MenuBaseActivity.this, Constants.CALLUS,
													"null");
											SharedPreferences pref = getApplicationContext()
													.getSharedPreferences(Constants.login_credentials, MODE_PRIVATE);
											SharedPreferences.Editor editor = pref.edit();
											editor.putString(Constants.loggedin_username, "");
											editor.putString(Constants.loggedin_pwd, "");
											editor.putString(Constants.rememberme, "false");
											SharedPrefsUtil.setIntegerPreference(context,
													Constants.sharedPreferenceSelectedLoginActivity,
													Constants.m_dashboard);
											Constants.isPatientDetails = false;
											editor.commit();
										}

										if (!Constants.isLogin)
										{
											SharedPrefsUtil.setStringPreference(context, Constants.LOGO, "null");
											//  sendRequest(ApiRequestHelper.getLOGO(context,"LOGO", ApiConstants.appVersion));
											Util.killLogin();
											Intent aboutintent = new Intent(MenuBaseActivity.this,
													LoginScreenActivity.class);
											startActivity(aboutintent);
										}
									}
								});

						alert = builder.create();
						alert.show();

					}
					else
					{
						SharedPrefsUtil.setBooleanPreference(context, "remember", false);

						if (!Constants.isLogin)
						{
							Util.killLogin();
							Intent aboutintent = new Intent(MenuBaseActivity.this, LoginScreenActivity.class);
							startActivity(aboutintent);
						}
					}

					break;
			}
		}
	};

	public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels)
	{
		Bitmap result = null;
		try
		{
			result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(result);

			int color = 0xff424242;
			Paint paint = new Paint();
			Rect rect = new Rect(0, 0, 200, 200);

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawCircle(100, 100, 100, paint);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);

		}
		catch (NullPointerException e)
		{
		}
		catch (OutOfMemoryError o)
		{
		}
		return result;
	}

	public void setUpNavDrawer()
	{
		navBack = findViewById(R.id.toolbar_back);
		navToggle = findViewById(R.id.toolbar_toggle);
		history = (Button) findViewById(R.id.history);
		notification = (ImageView) findViewById(R.id.notification);

		menu = this;
		navBack.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();

			}
		});
		navToggle.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				if (drawer.isDrawerOpen(Gravity.LEFT))
				{
					drawer.closeDrawer(Gravity.LEFT);
				}
				else
				{
					drawer.openDrawer(Gravity.LEFT);
				}
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_base);
		context = MenuBaseActivity.this;
		//  menu_trends
		SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.login_credentials, 0); // 0 - for private mode
		SharedPreferences.Editor editor = pref.edit();
		loggedin_username = pref.getString(Constants.loggedin_username, null);
		groupId = pref.getString(Constants.loggedin_grp_id, null);

		loggedin_pwd = pref.getString(Constants.loggedin_pwd, null);
		loggedin_remember = pref.getString(Constants.rememberme, "false");
		notification_count = (TextView) findViewById(R.id.notification_count);
		mHeaderLocDropdown = (ImageView) findViewById(R.id.header_loc_dropdown);
		mHeaderLocDropdown.setVisibility(View.GONE);
		mHeaderLocDropdown.setOnClickListener(mFooterOnClickListener);

		notification = (ImageView) findViewById(R.id.notification);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		logout_text = (TextView) findViewById(R.id.logout_text);
		header_layout = (LinearLayout) findViewById(R.id.header_layout);
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		srltext = (ImageView) findViewById(R.id.srltext);

		logo_url = SharedPrefsUtil.getStringPreference(context, Constants.LOGO);
		try
		{
			if (/*logo_url != null*/!(logo_url.equalsIgnoreCase("null")))
			{
				Picasso.with(context).load(logo_url).into(srltext);
			}
			else
			{
				sendRequest(ApiRequestHelper.getLOGO(context, "LOGO", ApiConstants.appVersion));
			}
		}
		catch (Exception e)
		{
			sendRequest(ApiRequestHelper.getLOGO(context, "LOGO", ApiConstants.appVersion));

		}
		//ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
		//		this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		setUpNavDrawer();

		//
		srltext.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				Util.killDashBoard();
				Intent intent = new Intent(MenuBaseActivity.this, Dashboard.class);
				startActivity(intent);
			}
		});

		notification.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Util.killNotification();
				Intent intent = new Intent(MenuBaseActivity.this, NotificationsActivity.class);
				startActivity(intent);
			}
		});

		//drawer.setDrawerListener(toggle);
		//toggle.syncState();
		header_loc_name = (TextView) findViewById(R.id.header_loc_name);
		headerlayout = (RelativeLayout) findViewById(R.id.headerlayout);
		if (savedInstanceState != null)
		{
			// retrieve saved instances of the two menus
			if (savedInstanceState.containsKey(SS_KEY_MENU_1))
			{
				firstMenu = savedInstanceState.getParcelable(SS_KEY_MENU_1);
			}
		}
		snv = (SublimeNavigationView) findViewById(R.id.navigation_view);

		cancel_menu = (ImageView) snv.getHeaderView().findViewById(R.id.cancel_menu);
		logout = (LinearLayout) snv.findViewById(R.id.logout);
		loggedin_userid = (TextView) snv.getHeaderView().findViewById(R.id.loggedin_userid);

		imageView = (CircleImageView) snv.getHeaderView().findViewById(R.id.profileImage);

		try
		{
			if (groupId == null)
			{
				(snv.getMenu().getMenuItem(R.id.menu_srl_locator)).setTitle("SRL Locator");
			}
			else
			{
				if (groupId.equalsIgnoreCase("3.0"))
				{
					(snv.getMenu().getMenuItem(R.id.menu_srl_locator)).setTitle("Lab Locator");
				}
				else
				{
					(snv.getMenu().getMenuItem(R.id.menu_srl_locator)).setTitle("SRL Locator");
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		if (loggedin_username != null && !loggedin_username.isEmpty())
		{
			//			loggedin_userid.setText(loggedin_username);
			logout_text.setText("Logout");
			getData(loggedin_username);

		}
		else
		{
			clearImage();
			loggedin_userid.setText("Guest");
			logout_text.setText("Login");
		}
		cancel_menu.setOnClickListener(mFooterOnClickListener);
		logout.setOnClickListener(mFooterOnClickListener);
		header_loc_name.setOnClickListener(mFooterOnClickListener);

		String selcity = SharedPrefsUtil.getStringPreference(context, "selectedcity");
		if (Validate.notEmpty(selcity))
		{
			header_loc_name.setText(selcity);
		}
		// set listener to get notified of menu events
		snv.setNavigationMenuEventListener(new OnNavigationMenuEventListener()
		{
			@Override
			public boolean onNavigationMenuEvent(OnNavigationMenuEventListener.Event event,
					SublimeBaseMenuItem menuItem)
			{
				switch (event)
				{
					case CHECKED:

						break;
					case UNCHECKED:

						break;
					case GROUP_EXPANDED:

						break;
					case GROUP_COLLAPSED:
						break;
					default:
						menuItem.setChecked(!menuItem.isChecked());
						DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
						if (drawer.isDrawerOpen(GravityCompat.START))
						{
							drawer.closeDrawer(GravityCompat.START);
						}

						Intent intent = null;

						if (menuItem.getTitle().toString().equalsIgnoreCase("Book A Test"))
						{
							if (Util.isCity(context))
							{
								Constants.isPackage = false;
								Util.killBook();
								intent = new Intent(MenuBaseActivity.this, BookATestActivity.class);
							}
							else
							{
								Util.showCityAlert(context);
							}
						}

						if (menuItem.getTitle().toString().equalsIgnoreCase("Packages"))
						{

							if (Util.isCity(context))
							{
								Util.killBook();
								Constants.isPackage = true;
								intent = new Intent(MenuBaseActivity.this, BookATestActivity.class);

							}
							else
							{
								Util.showCityAlert(context);
							}
						}

						if (menuItem.getTitle().toString().equalsIgnoreCase("Home"))
						{
							//Util.killDashBoard();
							Util.killAll();
							Util.killPayOpt();
							intent = new Intent(MenuBaseActivity.this, Dashboard.class);

						}

						if (menuItem.getTitle().toString().equalsIgnoreCase("Track Your Order"))
						{
							Constants.isTrack = false;
							Util.killTraxkOrder();
							intent = new Intent(MenuBaseActivity.this, TrackOrderActivity.class);
						}

						//                        if (menuItem.getTitle().toString().equalsIgnoreCase("Offers")) {
						//                            if (Util.isCity(context)) {
						//                                Util.killOffersList();
						//                                intent = new Intent(MenuBaseActivity.this, OffersListActivity.class);
						//                            } else
						//                                Util.showCityAlert(context);
						//
						//                        }

						if (menuItem.getTitle().toString().equalsIgnoreCase("My Reports"))
						{
							navigateToMyReports();
						}

						/* if (menuItem.getTitle().toString().equalsIgnoreCase("My Trends")) {
						    navigateToTrend();
						}*/
						if (menuItem.getTitle().toString().equalsIgnoreCase("My Health Tracker"))
						{
							navigateToHealth();
						}

						if (menuItem.getTitle().toString().equalsIgnoreCase("SRL Locator"))
						{
							Util.killYourLoc();
							intent = new Intent(MenuBaseActivity.this, YourLocationActivity.class);
						}

						if (menuItem.getTitle().toString().equalsIgnoreCase("Settings"))
						{
							navigateToSettings();
						}
						if (menuItem.getTitle().toString().equalsIgnoreCase("Change Password"))
						{
							navigateToChangePwd();

						}
						if (menuItem.getTitle().toString().equalsIgnoreCase("My Orders"))
						{
							navigateTomyOrders();
						}

						if (menuItem.getTitle().toString().equalsIgnoreCase("About Us"))
						{

							if (AboutUsActivity.aboutus != null)
							{
								AboutUsActivity.aboutus.finish();
							}
							Util.killAbout();
							intent = new Intent(MenuBaseActivity.this, AboutUsActivity.class);
						}

						if (menuItem.getTitle().toString().equalsIgnoreCase("Calculate BMI"))
						{
							Util.killBMIReg();
							intent = new Intent(MenuBaseActivity.this, BmiRegisterActivity.class);
						}
						if (menuItem.getTitle().toString().equalsIgnoreCase("Rate Us"))
						{
							Util.killRateUs();
							SharedPrefsUtil.setStringPreference(context, "Star", "");
							intent = new Intent(MenuBaseActivity.this, RateUsActivity.class);
						}
						//Profile Screen
						if (menuItem.getTitle().toString().equalsIgnoreCase("My Profile"))
						{

							navigateTomyprofile();
						}

						if (menuItem.getTitle().toString().equalsIgnoreCase("My Family"))
						{

							navigateTomyfamily();
							/*Constants.isFamilySel = false;
							Intent intent = new Intent(MenuBaseActivity.this, MyFamilyActivity.class);
							startActivity(intent);*/

						}

						if (menuItem.getTitle().toString().equalsIgnoreCase("Contact Us"))
						{
							Util.killSupport();
							intent = new Intent(MenuBaseActivity.this, SupportActivity.class);
						}

						if (menuItem.getTitle().toString().equalsIgnoreCase("Register"))
						{
							Map<String, String> params = new HashMap<String, String>();
							params.put(Constants.deviceID, Constants.devicetobepassed);
							//  sendRequest(ApiRequestHelper.getPendingRegistration(Constants.devicetobepassed.toUpperCase()/*Constants.deviceID*/));
							RestApiCallUtil.postServerResponse(context, ApiRequestReferralCode.PENDING_REGISTRATION,
									params);
						}

						if (intent != null)
						{
							startActivity(intent);

						}
						break;
				}
				return true;
			}
		});

		String launched = SharedPrefsUtil.getStringPreference(context, "launched");
		//        if (Validate.notEmpty(launched)) {

		//            setCityAlertDialog(false);
		//        }
		//        SharedPrefsUtil.setStringPreference(context, "launched", "launched");
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(context, request, mResponseListener);
	}

	private void setLOGO(JSONArray jArray)
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
						SharedPrefsUtil.setStringPreference(context, Constants.LOGO + "", content + "");
						Picasso.with(context).load(content).into(srltext);
					}
				}
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public void onBackPressed()
	{
		//        Constants.isLogin = false;
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START))
		{
			drawer.closeDrawer(GravityCompat.START);
		}
		else
		{

			super.onBackPressed();
		}
	}

	protected void clearImage()
	{
		imageView.setBackgroundResource(R.color.emptycolor);
		imageView.setImageBitmap(null);
		imageView.setBackgroundResource(R.drawable.guest_user_icon);
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
		try
		{
			logo_url = SharedPrefsUtil.getStringPreference(context, Constants.LOGO);
			if (!(logo_url.equalsIgnoreCase("null")))
			{
				Picasso.with(context).load(logo_url).into(srltext);
			}

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

			com.srllimited.srl.util.Log.e("unread", e + "");
		}
		//        if(!Constants.isLogin) {

		boolean isRem = SharedPrefsUtil.getBooleanPreference(context, "remember", true);

		Log.e("rem", isRem + "");

		//        if (logout_text.getText().toString().equalsIgnoreCase("Logout")) {
		//
		//            if (loggedin_username != null && !loggedin_username.isEmpty())
		//                getData(loggedin_username);
		//            else {
		//                loggedin_userid.setText("Guest");
		//                logout_text.setText("Login");
		//                clearImage();
		//            }
		//
		//        } else {
		//            loggedin_userid.setText("Guest");
		//            clearImage();
		//        }

		if (isRem)
		{

			logout_text.setText("Login");
			loggedin_userid.setText("Guest");
			clearImage();
		}
		else
		{
			if (loggedin_username != null && !loggedin_username.isEmpty())
			{
				loggedin_userid.setText(loggedin_username);
				logout_text.setText("Logout");
				getData(loggedin_username);
			}
		}
		//            } else {
		//                clearImage();
		//                loggedin_userid.setText("Guest");
		//                logout_text.setText("Login");
		//            }
		//        }
		//        if(!Constants.isLogin) {
		//            getData(Util.getStoredUsername(context));
		//            if (logout_text.getText().toString().equalsIgnoreCase("Login")) {
		////			loggedin_userid.setText(loggedin_username);
		//                logout_text.setText("Logout");
		//                getData(loggedin_username);
		//            } else {
		//                clearImage();
		//                loggedin_userid.setText("Guest");
		//                logout_text.setText("Login");
		//            }
		//        }
		//        mIntentFilter = new IntentFilter();
		//        mIntentFilter.addAction(MenuBaseActivity.mBroadcasActionNot);
		//        registerReceiver(mReceiver, mIntentFilter);

		//        mitentFilter = new IntentFilter();
		//        mitentFilter.addAction(mBroadcasActionNot);
		//        registerReceiver(mreceiver, mitentFilter);

	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		// save state for the menu
		outState.putParcelable(SS_KEY_MENU_1, firstMenu);
	}

	public void addContentView(int layoutId)
	{
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View contentView = inflater.inflate(layoutId, null, false);
		header_layout.addView(contentView, 0);
	}

	private void setCityAlertDialog(boolean isLaunched)
	{
		Util.killSearchLoc();
		Intent intent = new Intent(this, SearchLocationActivity.class);
		intent.putExtra("isLaunched", isLaunched);
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
				new CarouselsTask(getApplicationContext());

			}
		}
	}

	private void navigateToMyReports()
	{

		boolean isRem = SharedPrefsUtil.getBooleanPreference(context, "remember", false);

		SharedPrefsUtil.setIntegerPreference(context, Constants.sharedPreferenceSelectedLoginActivity,
				Constants.m_reports);
		if (Util.getStoredUsername(context) != null && !Util.getStoredUsername(context).isEmpty()
				&& !Util.isRem(context))
		{
			Util.killMyReportEntry();
			Intent i = new Intent(MenuBaseActivity.this, MyReportEntryDetails.class);
			startActivity(i);
		}
		else
		{
			Util.killLogin();
			Intent intent = new Intent(MenuBaseActivity.this, LoginScreenActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}

	}

	private void navigateToTrend()
	{

		SharedPrefsUtil.setIntegerPreference(context, Constants.sharedPreferenceSelectedLoginActivity,
				Constants.m_trend);
		if (Util.getStoredUsername(context) != null && !Util.getStoredUsername(context).isEmpty()
				&& !Util.isRem(context))
		{
			Util.killMyTrends();
			Intent i = new Intent(MenuBaseActivity.this, MyTrendActivity.class);
			// Intent i = new Intent(MenuBaseActivity.this, AnimatedXYPlotActivity.class);

			startActivity(i);
		}
		else
		{
			Util.killLogin();
			Intent intent = new Intent(MenuBaseActivity.this, LoginScreenActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
			Intent i = new Intent(MenuBaseActivity.this, HealthTracker.class);
			startActivity(i);
		}
		else
		{
			Util.killLogin();
			Intent intent = new Intent(MenuBaseActivity.this, LoginScreenActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}

	}

	private void navigateTomyprofile()
	{

		SharedPrefsUtil.setIntegerPreference(context, Constants.sharedPreferenceSelectedLoginActivity,
				Constants.m_profile);
		if (Util.getStoredUsername(context) != null && !Util.getStoredUsername(context).isEmpty()
				&& !Util.isRem(context))
		{
			Util.killMyProfile();
			Intent intent = new Intent(MenuBaseActivity.this, MyProfileActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
		else
		{
			Util.killLogin();
			Intent intent = new Intent(MenuBaseActivity.this, LoginScreenActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
	}

	private void navigateToSettings()
	{
		SharedPrefsUtil.setIntegerPreference(context, Constants.sharedPreferenceSelectedLoginActivity,
				Constants.m_settings);
		if (Util.getStoredUsername(context) != null && !Util.getStoredUsername(context).isEmpty()
				&& !Util.isRem(context))
		{
			Util.killSettings();
			Intent intent = new Intent(MenuBaseActivity.this, SettingsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
		else
		{
			Util.killLogin();
			Intent intent = new Intent(MenuBaseActivity.this, LoginScreenActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
	}

	private void navigateTomyfamily()
	{
		SharedPrefsUtil.setIntegerPreference(context, Constants.sharedPreferenceSelectedLoginActivity,
				Constants.m_family);
		if (Util.getStoredUsername(context) != null && !Util.getStoredUsername(context).isEmpty()
				&& !Util.isRem(context))
		{
			Constants.isFamilySel = false;
			Util.killMyFamily();
			Intent intent = new Intent(MenuBaseActivity.this, MyFamilyActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
		else
		{
			Util.killLogin();
			Intent intent = new Intent(MenuBaseActivity.this, LoginScreenActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
	}

	private void navigateTomyOrders()
	{
		SharedPrefsUtil.setIntegerPreference(context, Constants.sharedPreferenceSelectedLoginActivity,
				Constants.m_orders);
		if (Util.getStoredUsername(context) != null && !Util.getStoredUsername(context).isEmpty()
				&& !Util.isRem(context))
		{

			Util.killOrders();

			Intent intent = new Intent(MenuBaseActivity.this, OrdersActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
		else
		{

			Intent intent = new Intent(MenuBaseActivity.this, LoginScreenActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
	}

	private void navigateToChangePwd()
	{
		SharedPrefsUtil.setIntegerPreference(context, Constants.sharedPreferenceSelectedLoginActivity, Constants.m_pwd);
		if (Util.getStoredUsername(context) != null && !Util.getStoredUsername(context).isEmpty()
				&& !Util.isRem(context))
		{
			Util.killChangePwd();
			Intent intent = new Intent(MenuBaseActivity.this, ChangePwdActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
		else
		{

			Intent intent = new Intent(MenuBaseActivity.this, LoginScreenActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		}
	}

	//Profile Image in Dashboard
	private void getData(String ptntcode)
	{

		if (appDb != null)
		{
			UserData _userAppData = null;
			try
			{
				_userAppData = appDb.getSelectedUserDetail(ptntcode);

				String selname = "";
				if (_userAppData != null)
				{
					selname = _userAppData.getFirst_name() + " " + _userAppData.getLast_name() + "";
				}

				if (_userAppData.getFirst_name() != null && !_userAppData.getFirst_name().equalsIgnoreCase("null"))
				{
					loggedin_userid.setText(_userAppData.getFirst_name() + "");

				}
				if (_userAppData != null)
				{
					setData(_userAppData);
				}

			}
			catch (Exception e)
			{

			}
		}

	}

	private void setData(UserData userdata)
	{

		if (userdata.getAddress2() != null && !userdata.getAddress2().equalsIgnoreCase("null")
				&& !userdata.getAddress2().isEmpty())
		{
			Bitmap captured_img_bitMap = StringToBitMap(userdata.getAddress2());
			profileImageSetting(captured_img_bitMap);
		}
	}

	private void profileImageSetting(Bitmap captured_img_bitMap)
	{
		imageView.setBackgroundResource(R.color.emptycolor);
		imageView.setImageBitmap(null);
		//		Bitmap conv_bm = getRoundedRectBitmap(captured_img_bitMap, 100);
		imageView.setImageBitmap(captured_img_bitMap);
	}

	public Bitmap StringToBitMap(String encodedString)
	{
		try
		{
			byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
			return bitmap;
		}
		catch (Exception e)
		{
			e.getMessage();
			return null;
		}
	}

	@Override
	public void onSuccessResponse(ApiRequestReferralCode referralCode, String serverResponse,
			HashMap<String, String> fetchValue)
	{

		switch (referralCode)
		{

			case UPDATE_USER_DETAILS:

				break;

			case PENDING_REGISTRATION:
				if (serverResponse.equalsIgnoreCase("Pending Registration"))
				{

					if (fetchValue != null && fetchValue.get("mobile") != null)
					{
						Util.killOtpReg();
						Intent intent = new Intent(MenuBaseActivity.this, OTPRegistration.class);
						intent.putExtra(Constants.registered_mobile, fetchValue.get("mobile") + "");
						startActivity(intent);
					}
					else
					{
						Util.killReg();
						Constants.isRegEdited = false;
						Constants.isPatientDetails = false;
						Intent intent = new Intent(MenuBaseActivity.this, RegistrationScreen.class);
						startActivity(intent);
					}
				}
				else
				{

					Constants.isRegEdited = false;
					Constants.isPatientDetails = false;
					Util.killReg();
					Intent intent = new Intent(MenuBaseActivity.this, RegistrationScreen.class);
					startActivity(intent);

				}
				break;

		}
	}

	//    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
	//        @Override
	//        public void onReceive(Context context, Intent intent) {
	//            if (intent.getAction().equals(RECEIVE_NOTIFICATIONS)) {
	//
	//                String notificationscount = SharedPrefsUtil.getStringPreference(context, "notificationscount");
	//
	//                if (Validate.notEmpty(notificationscount)) {
	//                    notification_count.setText(notificationscount);
	//                    notification_count.setVisibility(View.VISIBLE);
	//                } else
	//                    notification_count.setVisibility(View.GONE);
	//            }
	//
	//        }
	//    };

	//    @Override
	//    protected void onPause() {
	//        unregisterReceiver(mReceiver);
	//        super.onPause();
	//    }

	//    private BroadcastReceiver mreceiver = new BroadcastReceiver() {
	//        @Override
	//        public void onReceive(Context context, Intent intent) {
	//            Toast.makeText(context, intent.getAction() + "", Toast.LENGTH_SHORT).show();
	//
	//            if (intent.getAction().equals(mBroadcasActionNot)) {
	//
	//                try {
	//
	//                    int unreadNotification = SharedPrefsUtil.getIntegerPreference(context, "notificationscount", 0);
	//                    int readNotification = SharedPrefsUtil.getIntegerPreference(context, "readmsg", 0);
	//
	//                    int displayNotification = unreadNotification - readNotification;
	//                    Toast.makeText(context, displayNotification + "", Toast.LENGTH_SHORT).show();
	//                    if (displayNotification != 0) {
	//                        String notificationscount = displayNotification + "";
	//                        if (Validate.notEmpty(notificationscount)) {
	//                            notification_count.setText(notificationscount);
	//                            notification_count.setVisibility(View.VISIBLE);
	//                        } else
	//                            notification_count.setVisibility(View.GONE);
	//                    } else
	//                        notification_count.setVisibility(View.GONE);
	//                } catch (Exception e) {
	//
	//                }
	//            }
	//        }
	//    };
	//
	//    @Override
	//    protected void onPause() {
	//        unregisterReceiver(mreceiver);
	////        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
	//        super.onPause();
	//    }

}