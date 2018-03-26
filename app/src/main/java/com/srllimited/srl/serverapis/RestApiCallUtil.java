package com.srllimited.srl.serverapis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.srllimited.srl.SecureCer.SSLConection;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.serverapis.rest.RequestManager;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.utilities.Utility;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class RestApiCallUtil
{
	public static ProgressDialog mProgressDialog;

	/*
	public interface VolleyCallback1
	{
		void onSuccessResponse(int referralurlcall, ServerResponseData serverResponseData);
	}
	
	public interface VolleyCallback2
	{
		void onSuccessResponse(int referralurlcall, ServerResponseData2 serverResponseData);
	}
	*/
	/**
	 * Display error in case no net connection
	 */
	public static AlertDialog alert;

	public static void postServerResponse(final Context context, final ApiRequestReferralCode api,
			final Map<String, String> passedparams)
	{
		try
		{
			if (isOnline(context))
			{
				showProgressDialog(context);
				Log.e("params", passedparams + "");
				String url = "";

				switch (api)
				{
					case PENDING_REGISTRATION:
						url = ApiConstants.pendingRegistrationUrl;
						if (Constants.isTOKENAPI)
						{
							Utility.EncreptWithOneParam(context, passedparams.get("deviceid"), passedparams);
						}
						break;
					case USER_REGISTRATION:
						url = ApiConstants.userRegistrationUrl;
						if (Constants.isTOKENAPI)
						{

							Utility.EncreptWithTwoParam(context, passedparams.get("IN_FIRST_NAME"),
									passedparams.get("IN_MOBILE_NO"), passedparams);
						}
						break;
					case RESEND_OTP:

						url = ApiConstants.resendOTPUrl;
						if (Constants.isTOKENAPI)
						{

							Utility.EncreptWithOneParam(context, passedparams.get("IN_DEVICE_ID"), passedparams);
						}
						break;
					case VALIDATE_REGISTRATION:
						url = ApiConstants.validateRegistrationUrl;
						if (Constants.isTOKENAPI)
						{

							Utility.EncreptWithOneParam(context, passedparams.get("IN_DEVICE_ID"), passedparams);
						}
						break;
					case ValidateRegistration_Family:
						url = ApiConstants.validateRegistrationUrl_family;
						if (Constants.isTOKENAPI)
						{

							Utility.EncreptWithOneParam(context, passedparams.get("IN_DEVICE_ID"), passedparams);
						}
						break;
					case LOGIN:
						url = ApiConstants.loginUrl;
						if (Constants.isTOKENAPI)
						{

							Utility.EncreptWithTwoParam(context, passedparams.get(Constants.ptntcode),
									passedparams.get(Constants.pwd), passedparams);
						}
						break;

					case USER:
						url = ApiConstants.getUserUrl;

						if (Constants.isTOKENAPI)
						{
							Utility.EncreptWithOneParam(context, passedparams.get("ptntcode"), passedparams);
						}

						break;

					case FORGOT_PWD:
						url = ApiConstants.forgotPwdUrl;
						if (Constants.isTOKENAPI)
						{
							Utility.EncreptWithOneParam(context, passedparams.get("IN_USER_ID"), passedparams);
						}
						break;
				}
				Log.e("url", url + "");

				StringRequest req = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
				{
					@Override
					public void onResponse(String response)
					{

						response = response.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
						response = response.replace("<string xmlns=\"http://tempuri.org/\">", "");
						response = response.replace("<string xmlns=\"http://220.227.57.139:81/\">", "");
						response = response.replace("</string>", "");

						Log.e("response", response + "");
						JSONObject jObj = null;

						hideProgressDialog();

						try
						{
							try
							{

								jObj = new JSONObject(response);

							}
							catch (Exception e)
							{

							}

							HashMap<String, String> value = new HashMap<String, String>();
							String responsetoActivity = "";

							switch (api)
							{

								case PENDING_REGISTRATION:

									if (jObj != null)
									{
										responsetoActivity = jObj.getString(Constants.fieldGetResponse);
										Log.e("createobj", responsetoActivity + "");
										if (responsetoActivity != null
												&& responsetoActivity.equalsIgnoreCase("Pending Registration"))
										{

											if (!jObj.isNull("data") && jObj.getJSONArray("data") != null)
											{
												JSONArray jArr = jObj.getJSONArray("data");
												Log.e("jArr", jArr + "");
												value.put(Constants.registered_mobile,
														jArr.getJSONObject(0).getString("MOBILE_NO"));
											}
											Log.e("value", value + "");
										}
									}
									break;
								case USER_REGISTRATION:
									if (jObj != null)
									{
										responsetoActivity = jObj.getString(Constants.fieldGetResponse);
									}
									break;
								case RESEND_OTP:

									responsetoActivity = jObj.getString(Constants.fieldGetResponse);
									break;
								case VALIDATE_REGISTRATION:

									String strobj = "";
									try
									{
										strobj = jObj.getString(Constants.fieldGetResponse);
									}
									catch (Exception e)
									{

									}
									if (strobj != null)
									{
										responsetoActivity = strobj;
									}
									else
									{
										responsetoActivity = "invalid";
									}
									break;
								case ValidateRegistration_Family:
									String strobj1 = "";
									String strusername = "";
									String strpassword = "";
									try
									{
										strobj1 = jObj.getString(Constants.fieldGetResponse);
										if(Constants.isfromaddmenbers)
										{
											strusername=jObj.getString(Constants.jsonFieldPtnt_cd);
											strpassword=jObj.getString(Constants.jsonFieldPtnt_pwd);
											strobj1=strobj1+"-"+strusername+"-"+strpassword;
										}
									}
									catch (Exception e)
									{

									}
									if (strobj1 != null)
									{
										responsetoActivity = strobj1;
									}
									else
									{
										responsetoActivity = "invalid";
									}
									break;
								case LOGIN:

									if (jObj != null)
									{
										responsetoActivity = jObj.getString(Constants.fieldGetResponse);
									}
									if (responsetoActivity != null && !responsetoActivity.isEmpty()
											&& responsetoActivity.equalsIgnoreCase("Query Successful"))
									{
										JSONArray jArr = jObj.getJSONArray("data");

										///User Details
										try
										{
											value.put(Constants.jsonFieldUserID,
													jArr.getJSONObject(0).getString(Constants.jsonFieldUserID));
											value.put(Constants.jsonFieldPtnt_cd,
													jArr.getJSONObject(0).getString(Constants.jsonFieldPtnt_cd));
											value.put(Constants.jsonFieldPtnt_title,
													jArr.getJSONObject(0).getString(Constants.jsonFieldPtnt_title));
											value.put(Constants.jsonFieldFirstName,
													jArr.getJSONObject(0).getString(Constants.jsonFieldFirstName));
											value.put(Constants.jsonFieldLastName,
													jArr.getJSONObject(0).getString(Constants.jsonFieldLastName));

											value.put(Constants.jsonFieldGender,
													jArr.getJSONObject(0).getString(Constants.jsonFieldGender));
											value.put(Constants.jsonFieldDob,
													jArr.getJSONObject(0).getLong(Constants.jsonFieldDob) + "");
											value.put(Constants.jsonFieldmartial_status,
													jArr.getJSONObject(0).getString(Constants.jsonFieldmartial_status));
											value.put(Constants.jsonFieldEmail,
													jArr.getJSONObject(0).getString(Constants.jsonFieldEmail));
											value.put(Constants.jsonFieldZip,
													jArr.getJSONObject(0).getString(Constants.jsonFieldZip));
											value.put(Constants.jsonFieldMOBILE,
													jArr.getJSONObject(0).getString(Constants.jsonFieldMOBILE));
											value.put(Constants.jsonFieldAddress1,
													jArr.getJSONObject(0).getString(Constants.jsonFieldAddress1));
											value.put(Constants.jsonFieldAddress2,
													jArr.getJSONObject(0).getString(Constants.jsonFieldAddress2));
											value.put(Constants.jsonFieldlandmark,
													jArr.getJSONObject(0).getString(Constants.jsonFieldlandmark));
											value.put(Constants.jsonFieldcity,
													jArr.getJSONObject(0).getString(Constants.jsonFieldcity));
											value.put(Constants.jsonFieldstate,
													jArr.getJSONObject(0).getString(Constants.jsonFieldstate));
											value.put(Constants.jsonFieldMcountry,
													jArr.getJSONObject(0).getString(Constants.jsonFieldMcountry));
											value.put(Constants.jsonFieldparentid,
													jArr.getJSONObject(0).getString(Constants.jsonFieldparentid));
											value.put(Constants.GRP_ID,
													jArr.getJSONObject(0).getString(Constants.GRP_ID));

											if (!Util.getStoredUsername(context).isEmpty())
											{
												value.put(Constants.STATUS, "true");
											}
											else
											{
												value.put(Constants.STATUS, "false");
											}
										}
										catch (Exception e)
										{

											e.printStackTrace();
										}
									}

									break;

								case USER:
									String msg = "";
									if (jObj != null)
									{
										msg = responsetoActivity = jObj.getString(Constants.fieldGetResponse);
									}
									if (msg != null && !msg.isEmpty() && msg.equalsIgnoreCase("Query Successful"))
									{
										JSONArray jArr = jObj.getJSONArray("data");
										value.put(Constants.jsonFieldMOBILE,
												jArr.getJSONObject(0).getString(Constants.jsonFieldMOBILE));
										value.put(Constants.jsonFieldEmail,
												jArr.getJSONObject(0).getString(Constants.jsonFieldEmail));
										value.put(Constants.GRP_ID, jArr.getJSONObject(0).getString(Constants.GRP_ID));
										value.put(Constants.jsonFieldPtnt_cd,
												jArr.getJSONObject(0).getString(Constants.jsonFieldPtnt_cd));

									}
									break;

								case FORGOT_PWD:
									if (jObj != null)
									{
										responsetoActivity = jObj.getString(Constants.fieldGetResponse) + "--"
												+ jObj.getString(Constants.response_data_create);
									}
									break;
							}

							if (responsetoActivity != null && !responsetoActivity.isEmpty())
							{
								((VolleyCallback) context).onSuccessResponse(api, responsetoActivity, value);
							}
							else
							{
								NeResponseError(context);
							}

						}
						catch (JSONException e)
						{
							e.printStackTrace();
						}

					}
				}, new Response.ErrorListener()
				{
					@Override
					public void onErrorResponse(VolleyError error)
					{
						hideProgressDialog();
						NeResponseError(context);

					}
				})
				{
					@Override
					protected Map<String, String> getParams()
					{

						return passedparams;
					}

					/* @Override
					public byte[] getBody() {
					
					
					  // String temp ="{\"ptntcode\":\"SHRAF2510882\",\"pwd\":\"SHRA\",\"strToken\":\"bb5f7b15a7829ff7599e814eeb3d520d\"}";
					  //  String temp ="{ \"appToken\": [{ \"iToken\": \"SRLDIAG2017\"}]}";
					      String temp = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
					                "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"+
					                     "<soap:Header>\n" +
					            "    <AppToken xmlns=\"http://tempuri.org/\">\n" +
					            "      <iToken>SRLDIAG2017</iToken>\n" +
					            "    </AppToken>\n" +
					            "  </soap:Header>\n"+
					      "  <soap:Body>\n" +
					        "</soap:Envelope>";
					    Log.d("APP", "request String: " + temp);
					    byte[] b = temp.getBytes(Charset.forName("UTF-8"));
					
					    return b;
					}*/
					/*  @Override
					public Map<String, String> getHeaders() throws AuthFailureError {
					
					    Map<String, String> header = new HashMap<>();
					
					    header.put("iToken","SRLDIAG2017");
					    return header;
					}*/
				};
				SSLConection.allowAllSSL();
				RequestManager.getInstance().queueRequest(req);
			}
			else
			{
				NetworkError(context);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String dateToEpoch(String enteredDate)
	{
		long epoch = 0;
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		try
		{
			Date date = format.parse(enteredDate);
			System.out.println(date);
			epoch = date.getTime();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return epoch + "";
	}

	public static String epochToDate(long epoch)
	{
		return new SimpleDateFormat("dd-MMM-yyyy").format(new Date(epoch));
	}

	public static String orderepochToDate(long epoch)
	{
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("dd-MMM-yyyy");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormatGmt.format(epoch);
		//        return new SimpleDateFormat("dd-MMM-yyyy").format(new Date(epoch));
	}

	public static String colepochToDate(long epoch)
	{

		return new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(new Date(epoch));
	}

	/*public static String colFromToepochToDate(long epoch) {
	//        Calendar calendar = Calendar.getInstance();
	//        calendar.setTimeInMillis(epoch);
	//        calendar.add(Calendar.HOUR, -5);
	//        calendar.add(Calendar.MINUTE, -30);
	//        Log.e("timeless", calendar.getTimeInMillis() + "");
	
	    epoch = epoch - 19800000;
	    return new SimpleDateFormat("hh:mm a").format(new Date(epoch));
	}*/

	public static String colFromToepochToDate(long epoch)
	{
		//        epoch = epoch - 18000000;
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("hh:mm a");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormatGmt.format(epoch);
	}

	public static String dateTimeToEpoch(String enteredDate)
	{
		long epoch = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try
		{
			Date date = format.parse(enteredDate);
			System.out.println(date);
			epoch = date.getTime();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return epoch + "";
	}

	public static String coldateC(String enteredDate)
	{
		long epoch = 0;
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
		try
		{
			Date date = format.parse(enteredDate);
			System.out.println(date);
			epoch = date.getTime();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return epoch + "";
	}

	public static String coldateCol(String enteredDate)
	{
		long epoch = 0;
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
		try
		{
			Date date = format.parse(enteredDate);
			System.out.println(date);
			epoch = date.getTime();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return epoch + "";
	}

	public static void showProgressDialog(Context context)
	{

		try
		{
			hideProgressDialog();
			mProgressDialog = new ProgressDialog(context, AlertDialog.THEME_HOLO_LIGHT);
			mProgressDialog.setMessage("Please Wait...");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.setCancelable(false);
			mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
				}
			});
			mProgressDialog.show();
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

	/**
	 * Check Online Connectivity
	 */
	public static boolean isOnline(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}
	//

	/**
	 * Display error in case no net connection
	 */

	public static void NetworkError(Context context)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
		builder.setTitle("No Network").setMessage("Please check the internet connection").setCancelable(false)
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

	public static void NeResponseError(Context context)
	{

		try
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT);
			builder.setTitle("No Data")

					.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface dialog, int id)
						{
							dialog.dismiss();

						}
					});
			if (builder != null)
			{
				builder.create().show();
			}

		}
		catch (Exception e)
		{

		}
	}

	public static String longtoTime(long epoch)
	{

		return new SimpleDateFormat("hh:mm a").format(new Date(epoch));
	}

	/*
	public static void getServerResponse(final Context context, final int api, String url, final Map<String, String> passedparams)
	{
	
		try
		{
			if (isOnline(context))
			{
				showProgressDialog(context);
				Log.e("url", url + "");
				Log.e("para", passedparams + "");
	
				StringRequest req = new StringRequest(Request.Method.POST, url,
				                                      new Response.Listener<String>()
				                                      {
					                                      @Override
					                                      public void onResponse(String response)
					                                      {
						                                      response = response.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
						                                      response = response.replace("<string xmlns=\"http://tempuri.org/\">", "");
						                                      response = response.replace("<string xmlns=\"http://220.227.57.139:81/\">", "");
	
						                                      response = response.replace("</string>", "");
	
						                                      Log.e("response", response + "");
						                                      JSONObject jObj = null;
						                                      ServerResponseData serverResponseData = new ServerResponseData();
						                                      try
						                                      {
							                                      jObj = new JSONObject(response);
	
							                                      Log.e("response", response + "");
							                                      if (jObj != null)
							                                      {
								                                      if (!jObj.isNull(Constants.response_code))
								                                      {
									                                      serverResponseData.setCode(jObj.getInt(Constants.response_code));
								                                      }
								                                      if (!jObj.isNull(Constants.response_msg))
								                                      {
									                                      serverResponseData.setMsg(jObj.getString(Constants.response_msg));
								                                      }
	//
	
	
								                                      if (!jObj.isNull(Constants.response_data_create) && jObj
										                                      .getJSONObject(Constants.response_data_create) != null)
								                                      {
									                                      serverResponseData.setData(jObj.getJSONObject(Constants.response_data_create));
								                                      }
	
	
							                                      }
	
							                                      Log.e("serverresponse", serverResponseData + "");
							                                      hideProgressDialog();
	
							                                      if (serverResponseData != null)
							                                      {
								                                      ((VolleyCallback1)context).onSuccessResponse(api, serverResponseData);
							                                      }
							                                      else
							                                      {
								                                      NeResponseError(context);
							                                      }
	
						                                      }
						                                      catch (JSONException e)
						                                      {
							                                      e.printStackTrace();
						                                      }
	
					                                      }
				                                      },
				                                      new Response.ErrorListener()
				                                      {
					                                      @Override
					                                      public void onErrorResponse(VolleyError error)
					                                      {
						                                      NeResponseError(context);
						                                      hideProgressDialog();
					                                      }
				                                      })
				{
					@Override
					protected Map<String, String> getParams()
					{
	
						return passedparams;
					}
				};
				RequestManager.getInstance().queueRequest(req);
			}
			else
			{
				NetworkError(context);
			}
		}
		catch (Exception e)
		{
	
		}
	}
	
	public static void getServerResponse1(final Context context, final int api, String url, final Map<String, String> passedparams)
	{
		try
		{
	
			if (isOnline(context))
			{
				showProgressDialog(context);
				Log.e("url", url + "");
	
				StringRequest req = new StringRequest(Request.Method.POST, url,
				                                      new Response.Listener<String>()
				                                      {
					                                      @Override
					                                      public void onResponse(String response)
					                                      {
						                                      response = response.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
						                                      response = response.replace("<string xmlns=\"http://tempuri.org/\">", "");
						                                      response = response.replace("<string xmlns=\"http://220.227.57.139:81/\">", "");
	
						                                      response = response.replace("</string>", "");
	
						                                      Log.e("response", response + "");
						                                      JSONObject jObj = null;
						                                      ServerResponseData serverResponseData = new ServerResponseData();
						                                      try
						                                      {
							                                      jObj = new JSONObject(response);
	
							                                      Log.e("response", response + "");
							                                      if (jObj != null)
							                                      {
								                                      if (!jObj.isNull(Constants.response_code))
								                                      {
									                                      serverResponseData.setCode(jObj.getInt(Constants.response_code));
								                                      }
								                                      if (!jObj.isNull(Constants.response_msg))
								                                      {
									                                      serverResponseData.setMsg(jObj.getString(Constants.response_msg));
								                                      }
	//
								                                      serverResponseData.setData(jObj);
							                                      }
	
							                                      Log.e("serverresponse", serverResponseData + "");
							                                      hideProgressDialog();
	
							                                      if (serverResponseData != null)
							                                      {
								                                      ((VolleyCallback1)context).onSuccessResponse(api, serverResponseData);
							                                      }
							                                      else
							                                      {
								                                      try
								                                      {
									                                      if (context != null)
									                                      {
										                                      NeResponseError(context);
									                                      }
								                                      }
								                                      catch (Exception e)
								                                      {
	
								                                      }
							                                      }
	
	
						                                      }
						                                      catch (JSONException e)
						                                      {
							                                      e.printStackTrace();
						                                      }
	
					                                      }
				                                      },
				                                      new Response.ErrorListener()
				                                      {
					                                      @Override
					                                      public void onErrorResponse(VolleyError error)
					                                      {
						                                      try
						                                      {
							                                      if (context != null)
							                                      {
								                                      NeResponseError(context);
							                                      }
							                                      hideProgressDialog();
						                                      }
						                                      catch (Exception e)
						                                      {
	
						                                      }
	
					                                      }
				                                      })
				{
					@Override
					protected Map<String, String> getParams()
					{
	
						return passedparams;
					}
	
				};
	
	
				RequestManager.getInstance().queueRequest(req);
			}
			else
			{
				NetworkError(context);
			}
		}
		catch (Exception e)
		{
	
		}
	}
	
	//OfferList
	public static void getServerResponse2(final Context context, final int api, String url, final Map<String, String> passedparams)
	{
	
		try
		{
	
	
			if (isOnline(context))
			{
				showProgressDialog(context);
				Log.e("url", url + "");
	
				StringRequest req = new StringRequest(Request.Method.POST, url,
				                                      new Response.Listener<String>()
				                                      {
					                                      @Override
					                                      public void onResponse(String response)
					                                      {
						                                      response = response.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
						                                      response = response.replace("<string xmlns=\"http://tempuri.org/\">", "");
						                                      response = response.replace("<string xmlns=\"http://220.227.57.139:81/\">", "");
	
						                                      response = response.replace("</string>", "");
	
						                                      Log.e("response", response + "");
						                                      JSONObject jObj = null;
						                                      ServerResponseData2 serverResponseData = new ServerResponseData2();
						                                      try
						                                      {
							                                      jObj = new JSONObject(response);
	
							                                      Log.e("response", response + "");
							                                      if (jObj != null)
							                                      {
								                                      if (!jObj.isNull(Constants.response_code))
								                                      {
									                                      serverResponseData.setCode(jObj.getInt(Constants.response_code));
								                                      }
								                                      if (!jObj.isNull(Constants.response_msg))
								                                      {
									                                      serverResponseData.setMsg(jObj.getString(Constants.response_msg));
								                                      }
	//
								                                      Object obj = null;
								                                      if (!jObj.isNull("data"))
								                                      {
									                                      obj = jObj.get("data");
								                                      }
	
	
								                                      if (obj != null && obj instanceof JSONArray)
								                                      {
									                                      serverResponseData.setData(jObj.getJSONArray(Constants.response_data_create));
								                                      }
							                                      }
	
							                                      Log.e("serverresponse", serverResponseData + "");
							                                      hideProgressDialog();
	
							                                      if (serverResponseData != null)
							                                      {
								                                      ((VolleyCallback2) context).onSuccessResponse(api, serverResponseData);
							                                      }
							                                      else
							                                      {
								                                      NeResponseError(context);
							                                      }
	
						                                      }
						                                      catch (JSONException e)
						                                      {
							                                      e.printStackTrace();
						                                      }
	
					                                      }
				                                      },
				                                      new Response.ErrorListener()
				                                      {
					                                      @Override
					                                      public void onErrorResponse(VolleyError error)
					                                      {
						                                      NeResponseError(context);
						                                      hideProgressDialog();
					                                      }
				                                      })
				{
					@Override
					protected Map<String, String> getParams()
					{
						return passedparams;
					}
				};
	
				RequestManager.getInstance().queueRequest(req);
			}
			else
			{
				NetworkError(context);
			}
		}
		catch (Exception e)
		{
	
		}
	}
	*/

	public static String longtoMonth(long epoch)
	{
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("MMM");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		System.out.println(dateFormatGmt.format(new Date(epoch)) + "");

		return dateFormatGmt.format(new Date(epoch)) + "";
	}

	public static String longtoYear(long epoch)
	{

		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormatGmt.format(new Date(epoch)) + "";

		//   return new SimpleDateFormat("yyyy").format(new Date(epoch));
	}

	public static String longtoDate(long epoch)
	{
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("dd");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormatGmt.format(new Date(epoch)) + "";
		// return new SimpleDateFormat("dd").format(new Date(epoch));
	}

	public static int longtohr(long epoch)
	{
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("HH");
		// dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		return Integer.parseInt(dateFormatGmt.format(new Date(epoch)));
		// return new SimpleDateFormat("dd").format(new Date(epoch));
	}

	public static int longtoMinute(long epoch)
	{
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("mm");
		// dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		return Integer.parseInt(dateFormatGmt.format(new Date(epoch)));
		// return new SimpleDateFormat("dd").format(new Date(epoch));
	}

	public interface VolleyCallback
	{
		void onSuccessResponse(ApiRequestReferralCode referralCode, String result, HashMap<String, String> fetchValue);
	}
}
