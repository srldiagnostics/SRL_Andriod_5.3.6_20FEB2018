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
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.srllimited.srl.adapters.BookATestAdapter;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.BookTestORPackagesData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.database.DatabaseHelper;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.widget.RoundCornerProgressView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyCartActivity extends MenuBaseActivity implements View.OnClickListener, BookATestAdapter.NotifyActivity
{
	public final static int RESULT_PROMO = 11;
	public static Activity mycart;

	public static RecyclerView.Adapter mAdapter;
	public static boolean isNeedProme = false;
	public static RecyclerView mRecyclerView;
	private final Handler mHandler = new Handler();
	double pr_disc = 0;
	double charge1 = 0;
	double charge2 = 0;
	TextView msg1, msg2;
	ImageView plus;
	View promoview, view;
	LinearLayout promo_discount_layout, charges_one_layout, charges_two_layout, discount_layout;
	AlertDialog alert;
	private Context context;
	private TextView progress_count_text, progress_text;
	private RoundCornerProgressView mProgressView;
	private DatabaseHelper db;
	private TextView total_amount;
	private Dialog promoDialog;
	private Dialog alertDialog;
	private RadioGroup mRadioGroup;
	private RadioButton radio_lab, radio_home;
	private double r_amt = 0;
	private double g_amt = 0;
	private double discount_perc = 0;
	private ImageView close_popup;
	private TextView alert_apply;
	private EditText et_promo;
	private boolean Order_repeat = false;
	private TextView saved_amt_text;
	private TextView gross_amount, discount, promo_discount, other_charges_two, other_charges_one, round_amount,
			grand_total, getpromo, needpromo;
	private List<String> grossAmtList = new ArrayList<>();
	private List<String> priceAmtList = new ArrayList<>();
	private List<String> testCodeList = new ArrayList<>();
	private List<String> testIdList = new ArrayList<>();
	private List<String> discList = new ArrayList<>();
	private List<String> discTypeList = new ArrayList<>();
	private String totalAmt = "";
	private double pdiscount = 0;
	private double promodiscount = 0; //sachin
	private List<BookTestORPackagesData> tempList = new ArrayList<>();
	private String gross = "";
	//public static boolean ispromo = false;
	private String price = "";
	private String testid = "";
	private String testCode = "";
	private String disc = "";
	private String discType = "";
	private String offr_cd = "";
	private boolean isOnactiveResult = false;
	private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener()
	{
		@Override
		public void onCheckedChanged(final RadioGroup radioGroup, final int i)
		{
			Intent intent = null;
			switch (radioGroup.getCheckedRadioButtonId())
			{
				case R.id.radio_lab:
				{
					Constants.isPatientDetails = false;
					Constants.isLabOrCollection = true;
					Util.killVisitLab();
					intent = new Intent(MyCartActivity.this, VisitLabsList.class);
				}
					break;
				case R.id.radio_home:
				{
					Constants.isLabOrCollection = false;
					Constants.isPatientDetails = false;
					if (Validate.notEmpty(Util.getStoredUsername(context)) && !Util.isRem(context))
					{
						Util.killAddPatient();
						intent = new Intent(MyCartActivity.this, AddPatientActivity.class);
					}
					else
					{
						Util.killLogin();
						//							Constants.isPatientDetails = true;
						SharedPrefsUtil.setIntegerPreference(context, Constants.sharedPreferenceSelectedLoginActivity,
								Constants.m_cart);
						intent = new Intent(context, LoginScreenActivity.class);
					}
				}
					break;
			}

			if (intent != null)
			{
				hideVisitOptionsPopup();
				startActivity(intent);
			}
		}
	};
	private ViewGroup mRootViewGroup;
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			switch (request.getReferralCode())
			{
				case Get_CONTENT:
					if (serverResponseData != null)
					{
						try
						{
							JSONArray jsonArray = serverResponseData.getArrayData();
							String oc1 = "";
							if (jsonArray != null && !jsonArray.isNull(0))
							{
								JSONObject jsonObject = null;
								JSONObject jsonObject2 = null;
								JSONObject jsonObject3 = null;
								JSONObject jsonObject4 = null;

								try
								{
									jsonObject = jsonArray.getJSONObject(0);
									jsonObject2 = jsonArray.getJSONObject(1);
									jsonObject3 = jsonArray.getJSONObject(2);
									jsonObject4 = jsonArray.getJSONObject(3);

								}
								catch (Exception e)
								{

								}

								try
								{
									if (jsonObject3 != null && !jsonObject3.isNull("SCREEN_NAME")
											&& jsonObject3.getString("SCREEN_NAME").equalsIgnoreCase("OTHER_CHARGES_1"))
									{
										charge1 = jsonObject3.getDouble("CONTENT");
									}
								}
								catch (Exception e)
								{

								}

								try
								{
									if (jsonObject4 != null && !jsonObject4.isNull("SCREEN_NAME")
											&& jsonObject4.getString("SCREEN_NAME").equalsIgnoreCase("OTHER_CHARGES_2"))
									{
										charge2 = jsonObject4.getDouble("CONTENT");
									}
								}
								catch (Exception e)
								{

								}

								try
								{
									if (jsonObject2 != null && !jsonObject2.isNull("SCREEN_NAME")
											&& jsonObject2.getString("SCREEN_NAME").equalsIgnoreCase("MESSAGE_1"))
									{
										msg1.setText(jsonObject2.getString("CONTENT") + "");
									}
									else
										msg1.setVisibility(View.GONE);
								}
								catch (Exception e)
								{

								}

								try
								{
									if (jsonObject4 != null && !jsonObject4.isNull("SCREEN_NAME")
											&& jsonObject4.getString("SCREEN_NAME").equalsIgnoreCase("MESSAGE_2"))
									{
										msg2.setText(jsonObject4.getString("CONTENT"));
									}
									else
										msg2.setVisibility(View.GONE);
								}
								catch (Exception e)
								{

								}

							}
						}
						catch (Exception e)
						{

						}
						if (Validate.notEmpty(charge1 + ""))
						{
							try
							{
								if (charge1 != 0)
								{
									visiblecharge1();
									other_charges_one.setText(Util.getIntegerToString(charge1 + ""));
								}
								else
									hidecharge1();
							}
							catch (Exception e)
							{

							}
						}

						if (Validate.notEmpty(charge2 + ""))
						{
							try
							{
								if (charge2 != 0)
								{
									visiblecharge2();
									other_charges_two.setText(Util.getIntegerToString(charge2 + ""));
								}
								else
									hidecharge2();
							}
							catch (Exception e)
							{

							}

						}

						try
						{
							g_amt = Double.valueOf(total_amount.getText().toString()) - pdiscount - pr_disc + charge1
									+ charge2;

							r_amt = Math.round(g_amt);
						}
						catch (Exception e)
						{

						}

						try
						{
							round_amount.setText(Util.getIntegerToString(r_amt + ""));
						}
						catch (Exception e)
						{

						}

						try
						{
							grand_total.setText(Util.getIntegerToString(g_amt + ""));
						}
						catch (Exception e)
						{

						}

						double savedAmount = pdiscount + pr_disc;
						try
						{
							if (savedAmount != 0)
							{
								visiblesavedAmount();
								saved_amt_text.setText(
										"You have saved  \u20B9  " + Util.getIntegerToString(savedAmount + ""));
							}
							else
								hidesavedAmount();
						}
						catch (Exception e)
						{

						}
					}
					break;
				case MY_CART:
					if (serverResponseData != null)
					{
						try
						{
							int cartid = -1;
							try
							{
								cartid = serverResponseData.getObjectData().getInt("CART_ID");
								SharedPrefsUtil.setStringPreference(context, "cartid", cartid + "");

							}
							catch (Exception e)
							{
								cartid = -1;
							}
							if (cartid != -1)
							{

								showVisitOptionsPopup();
								SharedPrefsUtil.setIntegerPreference(context,
										Constants.sharedPreferenceSelectedLoginActivity, Constants.m_mycart);
								String username = Util.getStoredUsername(context);
							}
							else
							{
								cartError1();
							}
						}
						catch (Exception e)
						{
						}
					}
					else
					{
						cartError();
					}
					break;

				case VALIDATE_PROMO:

					pr_disc = 0;
					//   ispromo = true;
					if (isNeedProme)
					{
						String promocode_temp = SharedPrefsUtil.getStringPreference(context, "promocode_temp");
						SharedPrefsUtil.setStringPreference(context, "promocode", promocode_temp + "");
						//   Constants.promocode = Constants.promocode;
					}
					else
					{
						//  Constants.promocode = et_promo.getText().toString();
						SharedPrefsUtil.setStringPreference(context, "promocode", et_promo.getText().toString() + "");
					}
					if (serverResponseData != null)
					{
						JSONObject data = null;
						JSONObject jsonObject = null;

						try
						{
							data = serverResponseData.getFullData();
							if (data != null)
							{
								jsonObject = data.getJSONObject("data");//.getJSONObject(0);
							}
						}
						catch (Exception e)
						{
							try
							{
								if (data.getString("msg").equalsIgnoreCase("Failed To Authenticate."))
								{
									cartMsg(data.getString("msg"));
									if (promoDialog != null)
									{
										promoDialog.dismiss();
									}
									return;
								}

							}
							catch (JSONException e1)
							{
								e1.printStackTrace();
							}

						}

						try
						{
							if (jsonObject.getString("CART_FLAG").equalsIgnoreCase("Y"))
							{
								JSONArray jsonArray = jsonObject.getJSONArray("CART");
								db.deleteBookATest();
								for (int i = 0; i < jsonArray.length(); i++)
								{
									BookTestORPackagesData bookTestORPackagesData = BookTestORPackagesData
											.newInit_cart((JSONObject) jsonArray.get(i));
									db.createBookTestOrPackages_cart(bookTestORPackagesData);
								}
								List<BookTestORPackagesData> bookaTestDatas = new ArrayList<>();
								bookaTestDatas = db.getAllBook_pkgs_List();
								List<BookTestORPackagesData> mBookaTestDatas = new ArrayList<>();

								for (BookTestORPackagesData bookdata : bookaTestDatas)
								{
									if (bookdata.isCart())
									{
										mBookaTestDatas.add(bookdata);
									}
								}
								mAdapter = new BookATestAdapter(context, mBookaTestDatas, true);
								mRecyclerView.setAdapter(mAdapter);

							}
						}
						catch (JSONException e)
						{

						}

						if (jsonObject != null && !jsonObject.isNull("DISCOUNT"))
						{
							try
							{
								if (jsonObject.getDouble("DISCOUNT") != 0)
								{
									discount_perc = jsonObject.getDouble("DISCOUNT");
									SharedPrefsUtil.setStringPreference(context, "promoDiscountAmt",
											discount_perc + "");
									// Constants.promocodeAmt = discount_perc + "";
									double gross_amt = Double.valueOf(total_amount.getText().toString());
									try
									{
										pr_disc = discount_perc;//gross_amt * (discount_perc / 100);
										try
										{
											if (pr_disc != 0)
											{
												visiblepromo();
												promo_discount.setText(Util.getIntegerToString(pr_disc + ""));
											}
											else
											{
												hidepromo();
											}
										}
										catch (Exception e)
										{

										}
										try
										{
											g_amt = gross_amt - pr_disc - pdiscount + charge1 + charge2;

											r_amt = Math.round(g_amt);
										}
										catch (Exception e)
										{

										}

										try
										{
											round_amount.setText(Util.getIntegerToString(r_amt + ""));
										}
										catch (Exception e)
										{

										}
										try
										{
											grand_total.setText(Util.getIntegerToString(g_amt + ""));
											SharedPrefsUtil.setStringPreference(context, Constants.GRAND_TOTAL, g_amt+"");
										}
										catch (Exception e)
										{

										}

										double savedAmount = pdiscount + pr_disc;
										try
										{
											if (savedAmount != 0)
											{
												visiblesavedAmount();
												saved_amt_text.setText("You have saved  \u20B9  "
														+ Util.getIntegerToString(savedAmount + ""));
											}
											else
											{
												hidesavedAmount();
											}
										}
										catch (Exception e)
										{

										}

									}
									catch (Exception e)
									{

									}
								}
							}
							catch (Exception e)
							{

							}
							if (pr_disc != 0)
							{

								Toast.makeText(context, "Promo Code Applied Successfully", Toast.LENGTH_SHORT).show();
							}
							else
							{
								try
								{
									if (jsonObject.getDouble("DISCOUNT") == 0)
									{
										cartMsg(jsonObject.getString("CART_MSG"));
										SharedPrefsUtil.setStringPreference(context, "promocode", "");
									}
									else
									{
										cartMsg("Invalid Promo Code");
										//Toast.makeText(context, "Invalid Promo Code", Toast.LENGTH_SHORT).show();
									}
								}
								catch (JSONException e)
								{
									e.printStackTrace();
								}

							}
						}
						else
						{
							Toast.makeText(context, "Invalid Promo Code", Toast.LENGTH_SHORT).show();
						}

						if (promoDialog != null)
						{
							promoDialog.dismiss();
						}
					}
					break;
			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.my_cart);
		context = MyCartActivity.this;
		// String
		//
		// = SharedPrefsUtil.getStringPreference(context, "regId");

		mycart = this;
		db = new DatabaseHelper(getApplicationContext());
		SharedPrefsUtil.setStringPreference(context, "cartid", "");
		header_loc_name.setText("My Cart");
		header_loc_name.setEnabled(false);
		total_amount = (TextView) findViewById(R.id.gross_amount);
		plus = (ImageView) findViewById(R.id.plus);
		getpromo = (TextView) findViewById(R.id.gotpromo);
		needpromo = (TextView) findViewById(R.id.needpromo);
		progress_text = (TextView) findViewById(R.id.progress_text);
		progress_count_text = (TextView) findViewById(R.id.progress_count_text);
		promo_discount_layout = (LinearLayout) findViewById(R.id.promo_discount_layout);
		charges_one_layout = (LinearLayout) findViewById(R.id.charges_one_layout);
		charges_two_layout = (LinearLayout) findViewById(R.id.charges_two_layout);
		discount_layout = (LinearLayout) findViewById(R.id.discount_layout);
		promoview = (View) findViewById(R.id.promoview);
		progress_text.setText(getResources().getString(R.string.check_out));
		progress_count_text.setText(getResources().getString(R.string.progress2));
		view = (View) findViewById(R.id.view);
		msg1 = (TextView) findViewById(R.id.msg1);
		msg2 = (TextView) findViewById(R.id.msg2);

		gross_amount = (TextView) findViewById(R.id.gross_amount);
		discount = (TextView) findViewById(R.id.discount);
		promo_discount = (TextView) findViewById(R.id.promo_discount);
		other_charges_one = (TextView) findViewById(R.id.other_charges_one);
		other_charges_two = (TextView) findViewById(R.id.other_charges_two);
		round_amount = (TextView) findViewById(R.id.round_amount);
		grand_total = (TextView) findViewById(R.id.grand_total);

		Constants.isRadio = false;

		promoDialog = new Dialog(this);
		promoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		promoDialog.setCancelable(false);
		promoDialog.setContentView(R.layout.got_promocode);
		close_popup = (ImageView) promoDialog.findViewById(R.id.close_popup);
		alert_apply = (TextView) promoDialog.findViewById(R.id.alert_apply);
		et_promo = (EditText) promoDialog.findViewById(R.id.et_promo);
		saved_amt_text = (TextView) findViewById(R.id.saved_amt_text);

		mProgressView = (RoundCornerProgressView) findViewById(R.id.rcpv_progress_view);
		progress_text.setOnClickListener(this);

		setBookATestAdapter();

		setProgress(34, true);

		needpromo.setOnClickListener(this);
		getpromo.setOnClickListener(this);
		close_popup.setOnClickListener(this);
		alert_apply.setOnClickListener(this);

		alertDialog = new Dialog(this);
		alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//		alertDialog.setCancelable(false);
		alertDialog.setContentView(R.layout.visit_options);
		alertDialog.setCancelable(false);
		//-----Views in forgot password popup
		mRadioGroup = (RadioGroup) alertDialog.findViewById(R.id.radio_group);
		radio_lab = (RadioButton) alertDialog.findViewById(R.id.radio_lab);
		radio_home = (RadioButton) alertDialog.findViewById(R.id.radio_home);

		sendRequest(ApiRequestHelper.getContent(context, "MY_CART", "4.1.1"));

		if (!getIntent().hasExtra(Constants.REPEAT_ORDER))
		{
			plus.setVisibility(View.VISIBLE);
		}

		plus.setOnClickListener(this);

		hidepromo();
		hidecharge1();
		hidecharge2();
		hidediscount();
		hidesavedAmount();

	}

	private void showVisitOptionsPopup()
	{
		mRadioGroup.clearCheck();
		mRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
		alertDialog.show();
	}

	private void hideVisitOptionsPopup()
	{
		mRadioGroup.setOnCheckedChangeListener(null);
		mRadioGroup.clearCheck();
		if (alertDialog.isShowing())
		{
			alertDialog.dismiss();
		}
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
	}

	private void setBookATestAdapter()
	{

		mRecyclerView = (RecyclerView) findViewById(R.id.test_receyclerview);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		mRecyclerView.setHasFixedSize(true);

		List<BookTestORPackagesData> bookaTestDatas = new ArrayList<>();
		bookaTestDatas = db.getAllBook_pkgs_List();
		List<BookTestORPackagesData> mBookaTestDatas = new ArrayList<>();

		for (BookTestORPackagesData bookdata : bookaTestDatas)
		{
			if (bookdata.isCart())
			{
				mBookaTestDatas.add(bookdata);
			}
		}

		//sachin
		mAdapter = new BookATestAdapter(context, mBookaTestDatas, true);
		mRecyclerView.setAdapter(mAdapter);

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.plus:
				finish();
				break;

			case R.id.alert_apply:
				String promo = et_promo.getText().toString();
				isNeedProme = false;

				if (Validate.notEmpty(promo))
				{
					gross = "";
					price = "";
					testid = "";
					testCode = "";
					disc = "";
					discType = "";
					offr_cd = "";
					String cityid = SharedPrefsUtil.getStringPreference(context, "selectedcityid");
					SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.login_credentials,
							MODE_PRIVATE);
					String loggedin_username = pref.getString(Constants.loggedin_username, "");
					if (tempList != null && !tempList.isEmpty())
					{

						for (BookTestORPackagesData bookTestORPackagesData : tempList)
						{

							if (bookTestORPackagesData.getGROSS_AMT() != null
									&& !bookTestORPackagesData.getCartPrice().isEmpty()
									&& !bookTestORPackagesData.getCartPrice().equalsIgnoreCase("0"))
							{

								if (!gross.isEmpty())
								{

									gross = gross + "," + bookTestORPackagesData.getGROSS_AMT() + "";

								}
								else
								{
									gross = bookTestORPackagesData.getGROSS_AMT() + "";
								}
							}

							if (bookTestORPackagesData.getPRICE() != 0)
							{
								if (!price.isEmpty())
								{
									price = price + "," + bookTestORPackagesData.getPRICE() + "";

								}
								else
								{
									price = bookTestORPackagesData.getPRICE() + "";

								}

							}

							if (bookTestORPackagesData.getPRDCT_CODE() != null
									&& !bookTestORPackagesData.getPRDCT_CODE().isEmpty())
							{
								if (!testCode.isEmpty())
								{

									testCode = testCode + "," + bookTestORPackagesData.getPRDCT_CODE() + "";
								}
								else
								{
									testCode = bookTestORPackagesData.getPRDCT_CODE() + "";
								}

							}

							if (bookTestORPackagesData.getOFR_CD() != null
									&& !bookTestORPackagesData.getOFR_CD().isEmpty())
							{
								if (!offr_cd.isEmpty())
								{

									offr_cd = offr_cd + "," + bookTestORPackagesData.getOFR_CD() + "";
								}
								else
								{
									offr_cd = bookTestORPackagesData.getOFR_CD() + "";
								}

							}

							if (bookTestORPackagesData.getDISC() != 0)
							{
								if (!disc.isEmpty())
								{
									disc = disc + "," + bookTestORPackagesData.getDISC() + "";

								}
								else
								{
									disc = bookTestORPackagesData.getDISC() + "";
								}

							}

							if (bookTestORPackagesData.getDISC_TYPE() != null
									&& !bookTestORPackagesData.getDISC_TYPE().isEmpty())
							{
								if (!discType.isEmpty())
								{

									discType = discType + "," + bookTestORPackagesData.getDISC_TYPE() + "";
								}
								else
								{
									discType = bookTestORPackagesData.getDISC_TYPE() + "";
								}

							}

							if (!testid.isEmpty())
							{

								testid = testid + "," + bookTestORPackagesData.getID() + "";
							}
							else
							{
								testid = bookTestORPackagesData.getID() + "";
							}

						}
					}
					/* this code only for reference */

					sendRequest(
							ApiRequestHelper.getValidatedPromo_Cart(context, cityid, promo, Constants.devicetobepassed,
									Util.getStoredUsername(context), "9704683480", testCode, testid, price, gross, disc,
									offr_cd, total_amount.getText().toString(), discount.getText().toString(), "", "",
									"", grand_total.getText().toString(), grand_total.getText().toString()));
					// sendRequest(ApiRequestHelper.getValidatedPromo(cityid,promo, Constants.devicetobepassed, Util.getStoredUsername(context), "9704683480"));
				}
				else
				{
					Toast.makeText(context, "Please enter Promo Code", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.close_popup:
				if (promoDialog != null)
				{
					promoDialog.dismiss();
				}
				break;
			case R.id.progress_text:
				if (RestApiCallUtil.isOnline(context))
				{
					SharedPrefsUtil.setStringPreference(context, "totalamout", round_amount.getText().toString());
					SharedPrefsUtil.setStringPreference(context, "round", total_amount.getText().toString());
//Change by sachin on 07/03/2018
					/*SharedPrefsUtil.setStringPreference(context, "totalamout", total_amount.getText().toString());
					SharedPrefsUtil.setStringPreference(context, "round", round_amount.getText().toString());*/
					formData();
				}
				else
				{

					RestApiCallUtil.NetworkError(context);
				}
				break;

			case R.id.gotpromo:
				et_promo.setText("");
				if (promoDialog != null)
				{
					promoDialog.show();
				}
				break;
			case R.id.needpromo:
				Util.killPromocodes();
				Intent cartintent = new Intent(MyCartActivity.this, PromoCodes.class);
				startActivityForResult(cartintent, RESULT_PROMO);
				break;
		}
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		String promoDiscountAm = SharedPrefsUtil.getStringPreference(context, "promoDiscountAmt");
		try
		{
			if (Double.parseDouble(promoDiscountAm) != 0)
			{
				visiblepromo();
				promo_discount.setText(Util.getIntegerToString(promoDiscountAm + ""));
			}
			else
			{
				hidepromo();
			}
		}
		catch (Exception e)
		{

		}

		/* if(!isOnactiveResult){
		    SharedPrefsUtil.setStringPreference(context, "promocode", "");
		    SharedPrefsUtil.setStringPreference(context, "promocode_temp", "");
		    SharedPrefsUtil.setStringPreference(context, "promoDiscountAmt","");
		}*/

	}

	@Override
	protected void onPause()
	{
		super.onPause();
		isOnactiveResult = true;
	}

	@Override
	public void onChangingListItem(final List<BookTestORPackagesData> bookTestORPackagesDatas)
	{

		totalAmt = "0";
		double cartCount = 0;
		pdiscount = 0;
		if (bookTestORPackagesDatas != null && !bookTestORPackagesDatas.isEmpty())
		{
			tempList = bookTestORPackagesDatas;
			for (BookTestORPackagesData bookTestORPackagesData : bookTestORPackagesDatas)
			{
				if (bookTestORPackagesData.getCartPrice() != null && !bookTestORPackagesData.getCartPrice().isEmpty())
				{
					try
					{
						try
						{
							if (bookTestORPackagesData.isCart())
							{
								pdiscount = pdiscount + (Double.valueOf(bookTestORPackagesData.getGROSS_AMT())
										- Double.valueOf(bookTestORPackagesData.getPRICE()));
							}
						}
						catch (Exception e)
						{

						}
						cartCount = cartCount + Double.valueOf(bookTestORPackagesData.getGROSS_AMT());
					}
					catch (Exception e)
					{

					}
				}

			}

		}

		try
		{
			total_amount.setText(Util.getIntegerToString(cartCount + ""));

		}
		catch (Exception e)
		{

		}

		try
		{
			if (pdiscount != 0)
			{
				visiblediscount();
				discount.setText(Util.getIntegerToString(pdiscount + ""));
			}
			else
			{
				hidediscount();
				discount.setText("0.0");
				// promo_discount.setText("0.0");
			}
			// SharedPrefsUtil.setStringPreference(context, "disc","");
		}
		catch (Exception e)
		{

		}
		String promocode = SharedPrefsUtil.getStringPreference(context, "promocode");

		if ((!promocode.equals("")) && BookATestAdapter.isRemoveitem)
		{
			SharedPrefsUtil.getStringPreference(context, "");
			// ispromo=false;
			BookATestAdapter.isRemoveitem = false;
			promo_discount.setText("0.0");
			pr_disc = 0.0;
			SharedPrefsUtil.setStringPreference(context, "promocode", "");
			SharedPrefsUtil.setStringPreference(context, "promoDiscountAmt", "");
			hidepromo();
			///////////////////
			cartMsg("Apply promo code again!");
			//////////////////
			//// hidepromo();

		}
		if (cartCount == 0)
		{
			totalAmt = cartCount + "";
			BookATestAdapter.isRemoveitem = false;
			Intent intent = new Intent();
			intent.putExtra("reset", "reset");
			setResult(BookATestActivity.RESULT_CODE_RESET, intent);
			finish();
		}

		try
		{
			g_amt = cartCount - pdiscount - pr_disc + charge1 + charge2;

			r_amt = Math.round(g_amt);
		}
		catch (Exception e)
		{

		}

		try
		{
			round_amount.setText(Util.getIntegerToString(r_amt + ""));
		}
		catch (Exception e)
		{

		}

		try
		{
			grand_total.setText(Util.getIntegerToString(g_amt + ""));
		}
		catch (Exception e)
		{

		}

		try
		{
			double savedAmount = pdiscount + pr_disc;
			if (savedAmount != 0)
			{
				visiblesavedAmount();
				saved_amt_text.setText("You have saved  \u20B9  " + Util.getIntegerToString(savedAmount + ""));
			}
			else
				hidesavedAmount();
		}
		catch (Exception e)
		{

		}
	}

	private void formData()
	{

		gross = "";
		price = "";
		testid = "";
		testCode = "";
		disc = "";
		discType = "";
		offr_cd = "";
		if (tempList != null && !tempList.isEmpty())
		{

			for (BookTestORPackagesData bookTestORPackagesData : tempList)
			{

				if (bookTestORPackagesData.getGROSS_AMT() != null && !bookTestORPackagesData.getCartPrice().isEmpty()
						&& !bookTestORPackagesData.getCartPrice().equalsIgnoreCase("0"))
				{

					if (!gross.isEmpty())
					{
						gross = gross + ","
								+ String.format("%.0f", Double.parseDouble(bookTestORPackagesData.getGROSS_AMT())) + "";

					}
					else
					{
						gross = String.format("%.0f", Double.parseDouble(bookTestORPackagesData.getGROSS_AMT())) + "";
					}
				}

				if (bookTestORPackagesData.getPRICE() != 0)
				{
					if (!price.isEmpty())
					{
						price = price + "," + bookTestORPackagesData.getPRICE() + "";

					}
					else
					{
						price = bookTestORPackagesData.getPRICE() + "";

					}

				}

				if (bookTestORPackagesData.getPRDCT_CODE() != null && !bookTestORPackagesData.getPRDCT_CODE().isEmpty())
				{
					if (!testCode.isEmpty())
					{

						testCode = testCode + "," + bookTestORPackagesData.getPRDCT_CODE() + "";
					}
					else
					{
						testCode = bookTestORPackagesData.getPRDCT_CODE() + "";
					}

				}

				if (bookTestORPackagesData.getOFR_CD() != null && !bookTestORPackagesData.getOFR_CD().isEmpty())
				{
					if (!offr_cd.isEmpty())
					{

						offr_cd = offr_cd + "," + bookTestORPackagesData.getOFR_CD() + "";
					}
					else
					{
						offr_cd = bookTestORPackagesData.getOFR_CD() + "";
					}

				}

				if (bookTestORPackagesData.getDISC() != 0)
				{
					if (!disc.isEmpty())
					{
						disc = disc + "," + bookTestORPackagesData.getDISC() + "";

					}
					else
					{
						disc = bookTestORPackagesData.getDISC() + "";
					}

				}

				if (bookTestORPackagesData.getDISC_TYPE() != null && !bookTestORPackagesData.getDISC_TYPE().isEmpty())
				{
					if (!discType.isEmpty())
					{

						discType = discType + "," + bookTestORPackagesData.getDISC_TYPE() + "";
					}
					else
					{
						discType = bookTestORPackagesData.getDISC_TYPE() + "";
					}

				}

				if (!testid.isEmpty())
				{

					testid = testid + "," + bookTestORPackagesData.getID() + "";
				}
				else
				{
					testid = bookTestORPackagesData.getID() + "";
				}

			}
		}

		SharedPrefsUtil.setStringPreference(context, "testid", testid + "");
		SharedPrefsUtil.setStringPreference(context, "disc", (Double.parseDouble(discount.getText().toString())
				+ Double.parseDouble(promo_discount.getText().toString()) + ""));

		if (RestApiCallUtil.isOnline(context))
		{
			try
			{
				String cityid = SharedPrefsUtil.getStringPreference(context, "selectedcityid");
				sendRequest(ApiRequestHelper.getMyCartDetails(context, cityid, testCode, testid, price, gross, disc,
						offr_cd, total_amount.getText().toString(), discount.getText().toString(),
						promo_discount.getText().toString(), other_charges_one.getText().toString(),
						other_charges_two.getText().toString(), grand_total.getText().toString(),
						round_amount.getText().toString(), ""));
			}
			catch (Exception e)
			{

			}
		}
		else
		{
			RestApiCallUtil.NetworkError(context);
		}

	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		//  ispromo =false;

	}

	private void calculatePromo(double discount_perc)
	{
		double gross_amt = Double.valueOf(total_amount.getText().toString() + "");
		try
		{
			pr_disc = gross_amt * (discount_perc / 100);

			try
			{
				if (pr_disc != 0)
				{
					visiblepromo();
					promo_discount.setText(Util.getIntegerToString(pr_disc + ""));
				}
				else
					hidepromo();
			}
			catch (Exception e)
			{

			}

			double savedAmount = pdiscount + pr_disc;

			try
			{
				if (savedAmount != 0)
				{
					visiblesavedAmount();
					saved_amt_text.setText("You have saved  \u20B9  " + Util.getIntegerToString(savedAmount + ""));
				}
				else
					hidesavedAmount();

			}
			catch (Exception e)
			{

			}

			try
			{
				g_amt = Double.valueOf(total_amount.getText().toString()) - pdiscount - pr_disc + charge1 + charge2;

				r_amt = Math.round(g_amt);
			}
			catch (Exception e)
			{

			}

			try
			{
				round_amount.setText(Util.getIntegerToString(r_amt + ""));
			}
			catch (Exception e)
			{

			}
			try
			{
				grand_total.setText(Util.getIntegerToString(g_amt + ""));
			}
			catch (Exception e)
			{

			}

		}
		catch (Exception e)
		{

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_PROMO)
		{

			if (data != null)
			{
				try
				{
					isNeedProme = true;
					isOnactiveResult = true;
					String promo = data.getStringExtra("couponcode");
					// Constants.promocode = promo;
					SharedPrefsUtil.setStringPreference(context, "promocode_temp", promo + "");
					/*  double disc = data.getDoubleExtra("disc", 0.0);

					calculatePromo(disc);*/ //comment by sachin because applying promocode service

					if (Validate.notEmpty(promo))
					{
						gross = "";
						price = "";
						testid = "";
						testCode = "";
						disc = "";
						discType = "";
						offr_cd = "";
						String cityid = SharedPrefsUtil.getStringPreference(context, "selectedcityid");
						SharedPreferences pref = getApplicationContext()
								.getSharedPreferences(Constants.login_credentials, MODE_PRIVATE);
						String loggedin_username = pref.getString(Constants.loggedin_username, "");
						if (tempList != null && !tempList.isEmpty())
						{

							for (BookTestORPackagesData bookTestORPackagesData : tempList)
							{

								if (bookTestORPackagesData.getGROSS_AMT() != null
										&& !bookTestORPackagesData.getCartPrice().isEmpty()
										&& !bookTestORPackagesData.getCartPrice().equalsIgnoreCase("0"))
								{

									if (!gross.isEmpty())
									{

										gross = gross + "," + bookTestORPackagesData.getGROSS_AMT() + "";

									}
									else
									{
										gross = bookTestORPackagesData.getGROSS_AMT() + "";
									}
								}

								if (bookTestORPackagesData.getPRICE() != 0)
								{
									if (!price.isEmpty())
									{
										price = price + "," + bookTestORPackagesData.getPRICE() + "";

									}
									else
									{
										price = bookTestORPackagesData.getPRICE() + "";

									}

								}
								if (bookTestORPackagesData.getPRDCT_CODE() != null
										&& !bookTestORPackagesData.getPRDCT_CODE().isEmpty())
								{
									if (!testCode.isEmpty())
									{

										testCode = testCode + "," + bookTestORPackagesData.getPRDCT_CODE() + "";
									}
									else
									{
										testCode = bookTestORPackagesData.getPRDCT_CODE() + "";
									}

								}

								if (bookTestORPackagesData.getOFR_CD() != null
										&& !bookTestORPackagesData.getOFR_CD().isEmpty())
								{
									if (!offr_cd.isEmpty())
									{

										offr_cd = offr_cd + "," + bookTestORPackagesData.getOFR_CD() + "";
									}
									else
									{
										offr_cd = bookTestORPackagesData.getOFR_CD() + "";
									}

								}

								if (bookTestORPackagesData.getDISC() != 0)
								{
									if (!disc.isEmpty())
									{
										disc = disc + "," + bookTestORPackagesData.getDISC() + "";

									}
									else
									{
										disc = bookTestORPackagesData.getDISC() + "";
									}

								}

								if (bookTestORPackagesData.getDISC_TYPE() != null
										&& !bookTestORPackagesData.getDISC_TYPE().isEmpty())
								{
									if (!discType.isEmpty())
									{

										discType = discType + "," + bookTestORPackagesData.getDISC_TYPE() + "";
									}
									else
									{
										discType = bookTestORPackagesData.getDISC_TYPE() + "";
									}

								}

								if (!testid.isEmpty())
								{

									testid = testid + "," + bookTestORPackagesData.getID() + "";
								}
								else
								{
									testid = bookTestORPackagesData.getID() + "";
								}

							}
						}
						/* this code only for reference */
						/* sendRequest(ApiRequestHelper.getMyCartDetails(cityid, testCode, testid, price, gross, disc, offr_cd, total_amount.getText().toString(),
						    discount.getText().toString(), promo_discount.getText().toString(), other_charges_one.getText().toString(),
						    other_charges_two.getText().toString(), grand_total.getText().toString(),
						    round_amount.getText().toString(), ""));*/
						/*  public static Map<String, String> getValidatedPromo_cart(String P_City, String P_PROMOCODE, String P_DEVICEID, String P_PTNTCD,
						    String P_MOBILENO,String P_TEST_CD,String P_TEST_ID,String P_T_AMNT,
						    String P_T_GROSS_AMNT,String P_T_DISC,String P_T_OFR_CD,String P_GROSS_AMNT,
						    String P_DISC,String P_PROMO_DISC,String P_OTHR_CHRG_1,String P_OTHR_CHRG_2,
						    String P_GRNT_TOTAL,String P_RND_AMNT){}*/
						sendRequest(ApiRequestHelper.getValidatedPromo_Cart(context, cityid, promo,
								Constants.devicetobepassed, Util.getStoredUsername(context), "9704683480", testCode,
								testid, price, gross, disc, offr_cd, total_amount.getText().toString(),
								discount.getText().toString(), "", "", "", grand_total.getText().toString(),
								grand_total.getText().toString()));
						// sendRequest(ApiRequestHelper.getValidatedPromo(cityid,promo, Constants.devicetobepassed, Util.getStoredUsername(context), "9704683480"));
					}
					else
					{
						Toast.makeText(context, "Please enter Promo Code", Toast.LENGTH_SHORT).show();
					}
				}
				catch (Exception e)
				{

				}
			}
		}
	}

	private void hidepromo()
	{
		promo_discount_layout.setVisibility(View.GONE);

	}

	private void visiblepromo()
	{
		promo_discount_layout.setVisibility(View.VISIBLE);

	}

	private void hidecharge1()
	{
		charges_one_layout.setVisibility(View.GONE);
	}

	private void hidecharge2()
	{
		charges_two_layout.setVisibility(View.GONE);

	}

	private void visiblecharge1()
	{
		charges_one_layout.setVisibility(View.VISIBLE);

	}

	private void visiblecharge2()
	{
		charges_two_layout.setVisibility(View.VISIBLE);

	}

	private void hidediscount()
	{
		discount_layout.setVisibility(View.GONE);

	}

	private void visiblediscount()
	{
		discount_layout.setVisibility(View.VISIBLE);

	}

	private void hidesavedAmount()
	{
		saved_amt_text.setVisibility(View.GONE);
		view.setVisibility(View.GONE);
	}

	private void visiblesavedAmount()
	{
		saved_amt_text.setVisibility(View.VISIBLE);
		view.setVisibility(View.VISIBLE);
	}

	private void cartError()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		builder.setTitle("Unknown Error").setMessage("Please notify the app manager").setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int id)
					{
						dialog.dismiss();

					}
				});
		alert = builder.create();
		alert.show();

	}

	private void cartError1()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		builder.setTitle("Unknown Error").setMessage("Please notify the app manager..").setCancelable(false)
				.setPositiveButton("OK", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int id)
					{
						dialog.dismiss();

					}
				});
		alert = builder.create();
		alert.show();

	}

	private void cartMsg(String msg)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		builder.setTitle("").setMessage(msg).setCancelable(false).setPositiveButton("OK",
				new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int id)
					{
						dialog.dismiss();

					}
				});
		alert = builder.create();
		alert.show();
	}
}