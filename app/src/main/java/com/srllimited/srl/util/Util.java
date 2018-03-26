package com.srllimited.srl.util;

import java.util.ArrayList;
import java.util.List;

import com.srllimited.srl.AboutUsActivity;
import com.srllimited.srl.AddMemberActivity;
import com.srllimited.srl.AddPatientActivity;
import com.srllimited.srl.BmiCalculation;
import com.srllimited.srl.BmiListView;
import com.srllimited.srl.BmiRegisterActivity;
import com.srllimited.srl.BookATestActivity;
import com.srllimited.srl.ChangePwdActivity;
import com.srllimited.srl.CollectionActivity;
import com.srllimited.srl.ConfirmRegistation;
import com.srllimited.srl.Dashboard;
import com.srllimited.srl.FourDigitActivity;
import com.srllimited.srl.HealthChartActivity;
import com.srllimited.srl.HealthTracker;
import com.srllimited.srl.LabLocatorsActivity;
import com.srllimited.srl.LoginScreenActivity;
import com.srllimited.srl.MenuBaseActivity;
import com.srllimited.srl.MobikwikActivity;
import com.srllimited.srl.MoreActivity;
import com.srllimited.srl.MyCartActivity;
import com.srllimited.srl.MyFamilyActivity;
import com.srllimited.srl.MyOrderPatientCollectAddress;
import com.srllimited.srl.MyProfileActivity;
import com.srllimited.srl.MyReportCompleteDetails;
import com.srllimited.srl.MyReportEntryDetails;
import com.srllimited.srl.MyReports;
import com.srllimited.srl.MyReportsExpandableListView;
import com.srllimited.srl.MyTrendActivity;
import com.srllimited.srl.NotificationsActivity;
import com.srllimited.srl.OTPRegistration;
import com.srllimited.srl.OfferDetails;
import com.srllimited.srl.OrderConfirmationActivity;
import com.srllimited.srl.OrderTrack;
import com.srllimited.srl.OrdersActivity;
import com.srllimited.srl.PaymentOptionActivity;
import com.srllimited.srl.PaymentSuccessActivity;
import com.srllimited.srl.PaytmActivity;
import com.srllimited.srl.PlayStoreActivity;
import com.srllimited.srl.PromoCodes;
import com.srllimited.srl.R;
import com.srllimited.srl.RateUsActivity;
import com.srllimited.srl.RegistrationScreen;
import com.srllimited.srl.ReportLoginAccessNo;
import com.srllimited.srl.ResetPinActivity;
import com.srllimited.srl.SearchLocationActivity;
import com.srllimited.srl.SettingsActivity;
import com.srllimited.srl.ShowWebView;
import com.srllimited.srl.SplashFourdigitPinActivity;
import com.srllimited.srl.SupportActivity;
import com.srllimited.srl.TrackCollectExportActivity;
import com.srllimited.srl.TrackOrderActivity;
import com.srllimited.srl.TrackingDetails;
import com.srllimited.srl.VisitLabsList;
import com.srllimited.srl.YourLocationActivity;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.interfaces.AlertListner;
import com.srllimited.srl.permission.DangerousPermResponseCallBack;
import com.srllimited.srl.permission.DangerousPermissionResponse;
import com.srllimited.srl.permission.DangerousPermissionUtils;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

//import com.codefyne.mysrl.OffersListActivity;

public class Util
{
	/**
	 * Hides the soft keyboard
	 */
	public static int PHONE_REQUES_CODE = 1;

	public static Dialog alertDialog;

	public static ImageView close_popup;

	public static TextView phnno;

	public static TextView callnow;

	public static TextView cancel, calltext;

	public static ProgressDialog mProgressDialog;

	public static List<String> monthList = new ArrayList()
	{
		{
			add("Jan");
			add("Feb");
			add("Mar");
			add("Apr");
			add("May");
			add("Jun");
			add("Jul");
			add("Aug");
			add("Sep");
			add("Oct");
			add("Nov");
			add("Dec");
		}
	};

	public static void hideSoftKeyboard(Context context, View currentFocusedView)
	{
		if (Validate.notNull(currentFocusedView))
		{
			InputMethodManager inputMethodManager = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), 0);
		}
	}

	/**
	 * Shows the soft keyboard
	 */
	public static void showSoftKeyboard(Context context, View view)
	{
		if (Validate.notNull(view))
		{
			InputMethodManager inputMethodManager = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			view.requestFocus();
			inputMethodManager.showSoftInput(view, 0);
		}
	}

	public static String getStoredUsername(Context context)
	{
		String username = "";
		SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Constants.login_credentials, 0); // 0 - for private mode
		SharedPreferences.Editor editor = pref.edit();
		username = pref.getString(Constants.loggedin_username, null);

		if (username == null)
		{
			username = "";
		}
		return username;
	}

	public static boolean isRem(Context context)
	{
		boolean isRem = SharedPrefsUtil.getBooleanPreference(context, "remember", true);
		return isRem;
	}

	public static String getStoredPwd(Context context)
	{
		String pwd = "";
		SharedPreferences pref = context.getApplicationContext().getSharedPreferences(Constants.login_credentials, 0); // 0 - for private mode
		SharedPreferences.Editor editor = pref.edit();
		pwd = pref.getString(Constants.loggedin_pwd, null);
		if (pwd == null)
		{
			pwd = "";
		}
		return pwd;
	}

	@SuppressWarnings("deprecation")
	public static Spanned fromHtml(String html)
	{
		Spanned result;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
		{
			result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
		}
		else
		{
			result = Html.fromHtml(html);
		}
		return result;
	}

	public static boolean isAppLaunched(Context context)
	{
		String isLaunched = SharedPrefsUtil.getStringPreference(context, Constants.isAppLaunched);
		return !Validate.notEmpty(isLaunched);
	}

	public static void shareToGMail(Context context, String email)
	{
		try
		{
			Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
			context.startActivity(Intent.createChooser(emailIntent, null));
		}
		catch (Exception e)
		{

		}
	}

	public static boolean isCity(Context context)
	{
		String city = SharedPrefsUtil.getStringPreference(context, "selectedcity");

		if (Validate.notEmpty(city))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static void showCityAlert(Context context)
	{
		try
		{
			android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context,
					AlertDialog.THEME_HOLO_LIGHT);
			builder.setMessage("Please select city").setCancelable(false).setPositiveButton("Ok",
					new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							dialog.cancel();
						}
					});
			builder.show();
		}
		catch (Exception e)
		{

		}
	}

	public static void hideProgressDialog()
	{
		try
		{
			if (mProgressDialog != null)
			{
				mProgressDialog.dismiss();
			}
		}
		catch (Exception e)
		{

		}
	}

	public static void showProgressDialog(Context context, String message)
	{

		try
		{
			hideProgressDialog();
			mProgressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
			mProgressDialog.setMessage(message);
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.setCancelable(false);
			//			mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
			//			                          new DialogInterface.OnClickListener()
			//			                          {
			//				                          @Override
			//				                          public void onClick(DialogInterface dialog, int which)
			//				                          {
			//					                          dialog.dismiss();
			//				                          }
			//			                          });
			mProgressDialog.show();
		}
		catch (Exception e)
		{

		}
	}

	public static void callNow(final Context context, final String phn)
	{
		alertDialog = new Dialog(context);
		alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		alertDialog.setCancelable(false);
		alertDialog.setContentView(R.layout.callpopup);
		close_popup = (ImageView) alertDialog.findViewById(R.id.close_popup);
		phnno = (TextView) alertDialog.findViewById(R.id.phnno);
		callnow = (TextView) alertDialog.findViewById(R.id.callnow);
		cancel = (TextView) alertDialog.findViewById(R.id.cancel);
		calltext = (TextView) alertDialog.findViewById(R.id.calltext);
		calltext.setVisibility(View.GONE);
		phnno.setText(phn + "");

		alertDialog.show();
		callnow.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				handleTaskWithUserPermission(context, PHONE_REQUES_CODE, phn);
			}
		});

		cancel.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				alertDialog.dismiss();
			}
		});

	}

	public static void locatorcallNow(final Context context, final String phn)
	{
		alertDialog = new Dialog(context);
		alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		alertDialog.setCancelable(false);
		alertDialog.setContentView(R.layout.callpopup);
		close_popup = (ImageView) alertDialog.findViewById(R.id.close_popup);
		phnno = (TextView) alertDialog.findViewById(R.id.phnno);
		callnow = (TextView) alertDialog.findViewById(R.id.callnow);
		cancel = (TextView) alertDialog.findViewById(R.id.cancel);
		calltext = (TextView) alertDialog.findViewById(R.id.calltext);
		calltext.setVisibility(View.VISIBLE);
		phnno.setText(phn + "");

		alertDialog.show();
		callnow.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				handleTaskWithUserPermission(context, PHONE_REQUES_CODE, phn);
			}
		});

		cancel.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				alertDialog.dismiss();
			}
		});

	}

	public static void handleTaskWithUserPermission(final Context context, int requestCode, final String phn)
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

	public static void killAbout()
	{
		if (AboutUsActivity.aboutus != null)
			AboutUsActivity.aboutus.finish();
	}

	public static void killAddMember()
	{
		if (AddMemberActivity.addmember != null)
			AddMemberActivity.addmember.finish();
	}

	public static void killAddPatient()
	{
		if (AddPatientActivity.homevisit != null)
			AddPatientActivity.homevisit.finish();
	}

	public static void killBMICalc()
	{
		if (BmiCalculation.bmical != null)
			BmiCalculation.bmical.finish();
	}

	public static void killBMIList()
	{
		if (BmiListView.bmilist != null)
			BmiListView.bmilist.finish();
	}

	public static void killBMIReg()
	{
		if (BmiRegisterActivity.bmireg != null)
			BmiRegisterActivity.bmireg.finish();
	}

	public static void killBook()
	{
		if (BookATestActivity.bookatest != null)
			BookATestActivity.bookatest.finish();
	}

	public static void killChangePwd()
	{
		if (ChangePwdActivity.changepwd != null)
			ChangePwdActivity.changepwd.finish();
	}

	public static void killCollection()
	{
		if (CollectionActivity.colvisit != null)
			CollectionActivity.colvisit.finish();
	}

	public static void killConfirmReg()
	{
		if (ConfirmRegistation.confirmreg != null)
			ConfirmRegistation.confirmreg.finish();
	}

	public static void killDashBoard()
	{
		if (Dashboard.home != null)
			Dashboard.home.finish();
	}

	public static void killfourdigit()
	{
		if (FourDigitActivity.fourdigit != null)
			FourDigitActivity.fourdigit.finish();
	}

	public static void killHealthChart()
	{
		if (HealthChartActivity.healthchart != null)
			HealthChartActivity.healthchart.finish();
	}
	/* public static void killTrendsChart() {
	    if (TrendsChartActivity.Trendschart!= null)
	        TrendsChartActivity.Trendschart.finish();
	}*/

	public static void killHealthTrack()
	{
		if (HealthTracker.healthtracker != null)
			HealthTracker.healthtracker.finish();
	}

	public static void killMyTrends()
	{
		if (MyTrendActivity.myTrendtracker != null)
			MyTrendActivity.myTrendtracker.finish();
	}

	public static void killLabLocators()
	{
		if (LabLocatorsActivity.lablocator != null)
			LabLocatorsActivity.lablocator.finish();
	}

	public static void killLogin()
	{
		if (LoginScreenActivity.loginact != null)
			LoginScreenActivity.loginact.finish();
	}

	public static void killMenu()
	{
		if (MenuBaseActivity.menu != null)
			MenuBaseActivity.menu.finish();
	}

	public static void killMobikwik()
	{
		if (MobikwikActivity.mobi != null)
			MobikwikActivity.mobi.finish();
	}

	public static void killMore()
	{
		if (MoreActivity.more != null)
			MoreActivity.more.finish();
	}

	public static void killMyCart()
	{
		if (MyCartActivity.mycart != null)
			MyCartActivity.mycart.finish();
	}

	public static void killMyFamily()
	{
		if (MyFamilyActivity.myfamily != null)
			MyFamilyActivity.myfamily.finish();
	}

	public static void killOrderConfirm()
	{
		if (OrderConfirmationActivity.orderconfirm != null)
			OrderConfirmationActivity.orderconfirm.finish();
	}

	public static void killMyOrderPatient()
	{
		if (MyOrderPatientCollectAddress.confirmPatientDetail != null)
			MyOrderPatientCollectAddress.confirmPatientDetail.finish();
	}

	public static void killMyProfile()
	{
		if (MyProfileActivity.myprofile != null)
			MyProfileActivity.myprofile.finish();
	}

	public static void killMyReportComplet()
	{
		if (MyReportCompleteDetails.myreportcompletedetails != null)
			MyReportCompleteDetails.myreportcompletedetails.finish();
	}

	public static void killMyReportEntry()
	{
		if (MyReportEntryDetails.myreportentry != null)
			MyReportEntryDetails.myreportentry.finish();
	}

	public static void killMyReports()
	{
		if (MyReports.myreport != null)
			MyReports.myreport.finish();
	}

	public static void killMyReportsExpandable()
	{
		if (MyReportsExpandableListView.myreportexpandable != null)
			MyReportsExpandableListView.myreportexpandable.finish();
	}

	public static void killOfferDetails()
	{
		if (OfferDetails.offersdetail != null)
			OfferDetails.offersdetail.finish();
	}

	//    public static void killOffersList() {
	//        if (OffersListActivity.offerslist != null)
	//            OffersListActivity.offerslist.finish();
	//    }

	public static void killOrders()
	{
		if (OrdersActivity.orders != null)
			OrdersActivity.orders.finish();
	}

	public static void killTrackView()
	{
		if (TrackCollectExportActivity.trackview != null)
			TrackCollectExportActivity.trackview.finish();
	}

	public static void killOrderTrack()
	{
		if (OrderTrack.ordertrack != null)
			OrderTrack.ordertrack.finish();
	}

	public static void killOtpReg()
	{
		if (OTPRegistration.otpreg != null)
			OTPRegistration.otpreg.finish();
	}

	public static void killPayOpt()
	{
		if (PaymentOptionActivity.payoption != null)
			PaymentOptionActivity.payoption.finish();
	}

	public static void killNotification()
	{
		if (NotificationsActivity.notifications != null)
			NotificationsActivity.notifications.finish();
	}

	public static void killPaySuccess()
	{
		if (PaymentSuccessActivity.paysuccess != null)
			PaymentSuccessActivity.paysuccess.finish();
	}

	public static void killPayTm()
	{
		if (PaytmActivity.paytm != null)
			PaytmActivity.paytm.finish();
	}

	public static void killPlayStore()
	{
		if (PlayStoreActivity.playstore != null)
			PlayStoreActivity.playstore.finish();
	}

	public static void killPromocodes()
	{
		if (PromoCodes.promocodes != null)
			PromoCodes.promocodes.finish();
	}

	public static void killRateUs()
	{
		if (RateUsActivity.rateus != null)
			RateUsActivity.rateus.finish();
	}

	public static void killReg()
	{
		if (RegistrationScreen.registration != null)
			RegistrationScreen.registration.finish();
	}

	public static void killReportLogin()
	{
		if (ReportLoginAccessNo.reportlogin != null)
			ReportLoginAccessNo.reportlogin.finish();
	}

	public static void killResetPin()
	{
		if (ResetPinActivity.resetpin != null)
			ResetPinActivity.resetpin.finish();
	}

	public static void killSearchLoc()
	{
		if (SearchLocationActivity.searchloc != null)
			SearchLocationActivity.searchloc.finish();
	}

	public static void killSettings()
	{
		if (SettingsActivity.settings != null)
			SettingsActivity.settings.finish();
	}

	public static void killShowWeb()
	{
		if (ShowWebView.sowweb != null)
			ShowWebView.sowweb.finish();
	}

	public static void killSplashFourDigit()
	{
		if (SplashFourdigitPinActivity.splashfour != null)
			SplashFourdigitPinActivity.splashfour.finish();
	}

	public static void killSupport()
	{
		if (SupportActivity.support != null)
			SupportActivity.support.finish();
	}

	public static void killTrackDetails()
	{
		if (TrackingDetails.trackingdetails != null)
			TrackingDetails.trackingdetails.finish();
	}

	public static void killTraxkOrder()
	{
		if (TrackOrderActivity.trackorder != null)
			TrackOrderActivity.trackorder.finish();
	}

	public static void killVisitLab()
	{
		if (VisitLabsList.labvisit != null)
			VisitLabsList.labvisit.finish();
	}

	public static void killYourLoc()
	{
		if (YourLocationActivity.yourloc != null)
			YourLocationActivity.yourloc.finish();
	}

	public static void killAll()
	{

		if (NotificationsActivity.notifications != null)
			NotificationsActivity.notifications.finish();

		if (AboutUsActivity.aboutus != null)
			AboutUsActivity.aboutus.finish();

		if (AddMemberActivity.addmember != null)
			AddMemberActivity.addmember.finish();

		if (AddPatientActivity.homevisit != null)
			AddPatientActivity.homevisit.finish();

		if (BmiCalculation.bmical != null)
			BmiCalculation.bmical.finish();

		if (BmiListView.bmilist != null)
			BmiListView.bmilist.finish();

		if (BmiRegisterActivity.bmireg != null)
			BmiRegisterActivity.bmireg.finish();

		if (BookATestActivity.bookatest != null)
			BookATestActivity.bookatest.finish();

		if (CollectionActivity.colvisit != null)
			CollectionActivity.colvisit.finish();

		if (ConfirmRegistation.confirmreg != null)
			ConfirmRegistation.confirmreg.finish();

		if (Dashboard.home != null)
			Dashboard.home.finish();

		if (FourDigitActivity.fourdigit != null)
			FourDigitActivity.fourdigit.finish();

		if (HealthChartActivity.healthchart != null)
			HealthChartActivity.healthchart.finish();

		if (HealthTracker.healthtracker != null)
			HealthTracker.healthtracker.finish();

		if (LabLocatorsActivity.lablocator != null)
			LabLocatorsActivity.lablocator.finish();

		if (LoginScreenActivity.loginact != null)
			LoginScreenActivity.loginact.finish();

		if (MenuBaseActivity.menu != null)
			MenuBaseActivity.menu.finish();

		if (MobikwikActivity.mobi != null)
			MobikwikActivity.mobi.finish();

		if (MoreActivity.more != null)
			MoreActivity.more.finish();

		if (MyCartActivity.mycart != null)
			MyCartActivity.mycart.finish();

		if (MyFamilyActivity.myfamily != null)
			MyFamilyActivity.myfamily.finish();

		if (OrderConfirmationActivity.orderconfirm != null)
			OrderConfirmationActivity.orderconfirm.finish();

		if (MyOrderPatientCollectAddress.confirmPatientDetail != null)
			MyOrderPatientCollectAddress.confirmPatientDetail.finish();

		if (MyProfileActivity.myprofile != null)
			MyProfileActivity.myprofile.finish();

		if (MyReportCompleteDetails.myreportcompletedetails != null)
			MyReportCompleteDetails.myreportcompletedetails.finish();

		if (MyReportEntryDetails.myreportentry != null)
			MyReportEntryDetails.myreportentry.finish();

		if (MyReports.myreport != null)
			MyReports.myreport.finish();

		if (MyReportsExpandableListView.myreportexpandable != null)
			MyReportsExpandableListView.myreportexpandable.finish();

		if (OfferDetails.offersdetail != null)
			OfferDetails.offersdetail.finish();

		//
		//        if (OffersListActivity.offerslist != null)
		//            OffersListActivity.offerslist.finish();

		if (OrdersActivity.orders != null)
			OrdersActivity.orders.finish();

		if (OrderTrack.ordertrack != null)
			OrderTrack.ordertrack.finish();

		if (OTPRegistration.otpreg != null)
			OTPRegistration.otpreg.finish();

		if (PaymentOptionActivity.payopt != null)
			PaymentOptionActivity.payopt.finish();

		if (PaymentSuccessActivity.paysuccess != null)
			PaymentSuccessActivity.paysuccess.finish();

		if (PaytmActivity.paytm != null)
			PaytmActivity.paytm.finish();

		if (PlayStoreActivity.playstore != null)
			PlayStoreActivity.playstore.finish();

		if (PromoCodes.promocodes != null)
			PromoCodes.promocodes.finish();

		if (RateUsActivity.rateus != null)
			RateUsActivity.rateus.finish();

		if (RegistrationScreen.registration != null)
			RegistrationScreen.registration.finish();

		if (ReportLoginAccessNo.reportlogin != null)
			ReportLoginAccessNo.reportlogin.finish();

		if (ResetPinActivity.resetpin != null)
			ResetPinActivity.resetpin.finish();

		if (SearchLocationActivity.searchloc != null)
			SearchLocationActivity.searchloc.finish();

		if (SettingsActivity.settings != null)
			SettingsActivity.settings.finish();

		if (ShowWebView.sowweb != null)
			ShowWebView.sowweb.finish();

		if (SplashFourdigitPinActivity.splashfour != null)
			SplashFourdigitPinActivity.splashfour.finish();

		if (SupportActivity.support != null)
			SupportActivity.support.finish();

		if (TrackingDetails.trackingdetails != null)
			TrackingDetails.trackingdetails.finish();

		if (TrackOrderActivity.trackorder != null)
			TrackOrderActivity.trackorder.finish();

		if (VisitLabsList.labvisit != null)
			VisitLabsList.labvisit.finish();

		if (YourLocationActivity.yourloc != null)
			YourLocationActivity.yourloc.finish();

		if (ChangePwdActivity.changepwd != null)
			ChangePwdActivity.changepwd.finish();

	}

	public static String toTitleCase(String str)
	{

		if (str == null)
		{
			return null;
		}

		boolean space = true;
		StringBuilder builder = new StringBuilder(str);
		final int len = builder.length();

		for (int i = 0; i < len; ++i)
		{
			char c = builder.charAt(i);
			if (space)
			{
				if (!Character.isWhitespace(c))
				{
					// Convert to title case and switch out of whitespace mode.
					builder.setCharAt(i, Character.toTitleCase(c));
					space = false;
				}
			}
			else if (Character.isWhitespace(c) || c == '(')
			{
				space = true;
			}
			else
			{
				builder.setCharAt(i, Character.toLowerCase(c));
			}
		}

		return builder.toString();
	}

	public static String getIntegerToString(String amount)
	{

		int amt = 0;
		try
		{
			double dou = 0;
			if (amount.contains("."))
			{
				dou = Double.valueOf(amount);
				amt = (int) (dou);
			}
			else
			{
				amt = Integer.valueOf(amount);
			}
		}
		catch (Exception e)
		{

			Log.e("intexc", e + "");
		}

		return amt + "";
	}

	public static int getIntegerToInteger(String amount)
	{

		int amt = 0;
		try
		{
			amt = Integer.valueOf(amount);
		}
		catch (Exception e)
		{

		}

		return amt;
	}

	public static int convertandcal(String unread, String read)
	{
		int calculated = 0;

		int cal1 = 0;
		int cal2 = 0;
		try
		{

			if (Validate.notEmpty(unread))
				cal1 = Integer.valueOf(unread);
			if (Validate.notEmpty(read))
				cal2 = Integer.valueOf(read);

			if (cal1 <= cal2)
			{
				calculated = 0;
			}
			else
			{
				calculated = cal1 - cal2;
			}

		}
		catch (Exception e)
		{

		}

		return calculated;
	}

	public static void commonInfoAlert(Context context, String msg)
	{
		AlertDialog alert;
		android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context,
				android.app.AlertDialog.THEME_HOLO_LIGHT);
		builder.setMessage(msg).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				if (dialog != null)
					dialog.dismiss();
			}
		});/*.setNegativeButton("YES", new DialogInterface.OnClickListener() {
			 public void onClick(DialogInterface dialog, int which) {
			
			 }
			});*/

		alert = builder.create();
		alert.show();
	}

	public static void confirmAlert(Context context, String title, String Msg, final AlertListner alertListner)
	{
		final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context,
				AlertDialog.THEME_HOLO_LIGHT);
		builder.setTitle(title);
		builder.setMessage(Msg);

		builder.setPositiveButton("YES", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int arg1)
			{

				alertListner.onOK();
				dialog.dismiss();

			}
		});

		builder.setNegativeButton("NO", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				alertListner.onCancel();
			}
		});

		AlertDialog alertDialog = builder.create();
		alertDialog.show();

	}
}
