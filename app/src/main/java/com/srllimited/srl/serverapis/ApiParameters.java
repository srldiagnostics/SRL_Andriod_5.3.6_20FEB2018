package com.srllimited.srl.serverapis;

import java.util.HashMap;
import java.util.Map;

import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.util.StringUtil;

/**
 * Created by RuchiTiwari on 17/12/2016.
 */

public class ApiParameters
{
	public static Map<String, String> passGetTopProductsParams(String city, String patient_code)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("P_CITY", city);
		params.put("P_PTNT_CD", patient_code);

		return params;
	}

	public static Map<String, String> getStatesList()
	{
		Map<String, String> params = new HashMap<String, String>();
		return params;
	}

	public static Map<String, String> getgraphList()
	{
		Map<String, String> params = new HashMap<String, String>();
		return params;
	}

	public static Map<String, String> passGetCitiesParams()
	{

		Map<String, String> params = new HashMap<String, String>();
		params.put("Device_ID", Constants.devicetobepassed);
		params.put("DEVICE_TOKEN", "1");
		params.put("OSTYPE", "ANDROID");
		params.put("OSVERSION", ApiConstants.osVersion);
		params.put("MYSRLVER", ApiConstants.appVersion);
		params.put("fcmToken", Constants.RegID);

		return params;
	}

	//Paytm status
	public static Map<String, String> passPaytmStatus(String mid, String orderId)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("MID", mid);
		params.put("ORDERID", orderId);

		return params;
	}

	//Get Cities Details List
	public static Map<String, String> getCitiesList(String city)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("str1", city);
		return params;
	}

	//
	public static void passCarousels()
	{

	}

	public static Map<String, String> repeatOrder(String order, String city)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("P_ORDER_ID", order);
		params.put("P_CITY", city);

		return params;
	}

	/////
	//Rate us Details
	public static Map<String, String> getRateusSuccess(String ptnt_cd, String p_rating, String p_feedBack,
			String p_deviceId, String p_os_version, String p_os_type, String p_app_version)
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("P_PTNT_CD", ptnt_cd);
		params.put("P_RATING", p_rating);
		params.put("P_FEEDBACK", p_feedBack);
		params.put("P_DEVICE_ID", p_deviceId);
		params.put("P_OS_VERSION", ApiConstants.osVersion);
		params.put("P_OS_TYPE", p_os_type);
		params.put("P_APP_VERSION", ApiConstants.appVersion);

		return params;
	}

	public static Map<String, String> getNotifications(String ptnt_cd, String p_deviceId, String p_os_type)
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("P_PTNT_CD", ptnt_cd);
		params.put("P_DEVICE_ID", p_deviceId);
		params.put("P_DEVICE_TOKEN", "");
		params.put("P_OSTYPE", p_os_type);
		params.put("P_OSVERSION", ApiConstants.osVersion);
		params.put("P_MYSRLVER", ApiConstants.appVersion);
		params.put("P_UNIX_TIME", "0");

		return params;
	}

	public static Map<String, String> deleteNotifications(String queueid, String ptnt_cd, String p_deviceId,
			String p_os_type)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("P_ID", queueid);
		params.put("P_PTNT_CD", ptnt_cd);
		params.put("P_DEVICE_ID", p_deviceId);
		params.put("P_DEVICE_TOKEN", "");
		params.put("P_OSTYPE", p_os_type);
		params.put("P_OSVERSION", ApiConstants.osVersion);
		params.put("P_MYSRLVER", ApiConstants.appVersion);
		params.put("P_UNIX_TIME", "0");

		return params;
	}

	public static Map<String, String> cancelOrder(String order)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("P_ORDER_ID", order);

		return params;
	}

	public static Map<String, String> getTrackOrder(String orderno)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("P_ORDERNO", orderno);

		return params;
	}

	public static Map<String, String> passFilterProducts(String city, String type, String category)
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("P_CITY", city);
		params.put("P_TYPE", type);
		params.put("P_PRDCT_CTGRY", category);

		return params;
	}

	public static Map<String, String> passGetPackageDetails(String city, String category, String ptncode, String str)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("P_CITY", city);
		params.put("P_PKG_CTGRY", category);
		params.put("P_PTNT_CD", ptncode);
		params.put("P_STR", str);

		return params;
	}

	public static Map<String, String> passGetPaySuccess(String orderno, String payid, String paydt, String amt,
			String transid, String paymethod, String ipaddress, String paystatus, String payopt)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("P_ORDERNO", orderno);
		params.put("P_PAYMENT_ID", payid);
		params.put("P_PAYMENT_DT", paydt);
		params.put("P_AMOUNT", amt);
		params.put("P_TRANS_ID", transid);
		params.put("P_PAYMENT_METHOD", paymethod);
		params.put("P_IPADDRESS", ipaddress);
		params.put("P_PAYMENT_STATUS", paystatus);
		params.put("P_PAYMENT_OPT", payopt);

		return params;
	}

	public static Map<String, String> passGetAllCategories()
	{
		Map<String, String> params = new HashMap<String, String>();

		return params;
	}

	public static Map<String, String> passMyCartDetails(String city, String testCode, String testId, String totalAmt,
			String grossAmt, String totaldisc, String ofrCode, String productGrossAmt, String disc, String promodisc,
			String otherCharge1, String otherCharge2, String grandTotal, String roundAmt, String promoCode)
	{

		Map<String, String> params = new HashMap<String, String>();
		params.put("P_CITY", city);
		params.put("P_TEST_CD", testCode);
		params.put("P_TEST_ID", testId);
		params.put("P_T_AMNT", totalAmt);
		params.put("P_T_GROSS_AMNT", grossAmt);
		params.put("P_T_DISC", totaldisc);
		params.put("P_T_OFR_CD", ofrCode);
		params.put("P_GROSS_AMNT", productGrossAmt);
		params.put("P_DISC", disc);
		params.put("P_PROMO_DISC", promodisc);
		params.put("P_OTHR_CHRG_1", otherCharge1);
		params.put("P_OTHR_CHRG_2", otherCharge2);
		params.put("P_GRNT_TOTAL", grandTotal);
		params.put("P_RND_AMNT", roundAmt);
		params.put("P_PROMOCODE", promoCode);
		return params;
	}

	public static Map<String, String> passPayDetails(String flag, String orderid, String ptntcode, String address,
			String loc, String city, String state, String country, String zip, String doctor, String colDateFrom,
			String colDateTo, String hardCopy, String colContact, String test, String ptntName, String createBy,
			String grossAmt, String discount_flag, String discount, String promocode, String paymode, String paymentOpt,
			String title, String firstName, String lastName, String dob, String gender, String dobActFlg,
			String mobileNo, String emailId, String cartId, String coltype, String labid, String ostype,
			String mysrl_version)
	{

		Map<String, String> params = new HashMap<String, String>();

		params.put("P_FLAG", flag);
		params.put("P_ORDERID", orderid);
		params.put("P_PTNT_CD", ptntcode);
		params.put("P_ADDRESS", address);
		params.put("P_LOCATION", loc);
		params.put("P_CITY", city);
		params.put("P_STATE", state);
		params.put("P_COUNTRY", country);
		params.put("P_ZIP", zip);
		params.put("P_DOCTOR", doctor);
		params.put("P_COLL_DATE_FROM", colDateFrom);
		params.put("P_COLL_DATE_TO", colDateTo);
		params.put("P_HARD_COPY", hardCopy);
		params.put("P_COLL_CONTACT", colContact);
		params.put("P_TESTS", test);
		params.put("P_PTNTNM", ptntName);
		params.put("P_CREATED_BY", createBy);
		params.put("P_GROSS_AMT", grossAmt);
		params.put("P_DISCOUNT_FLAG", discount_flag);
		params.put("P_DISCOUNT", discount);
		params.put("P_PROMOCODE", promocode);
		params.put("P_PAYMODE", paymode);
		params.put("P_PAYMENT_OPT", paymentOpt);
		params.put("P_TITLE", title);
		params.put("P_FIRST_NAME", firstName);
		params.put("P_LAST_NAME", lastName);
		params.put("P_DOB", dob);
		params.put("P_GENDER", gender);
		params.put("P_DOB_ACT_FLG", dobActFlg);
		params.put("P_MOBILE_NO", mobileNo);
		params.put("P_EMAIL_ID", emailId);
		params.put("P_CART_ID", cartId);
		params.put("P_COLL_TYPE", coltype);
		params.put("P_LAB_ID", labid);
		params.put(Constants.ostype, ostype);
		params.put(Constants.mysrl_version, mysrl_version);

		return params;
	}

	//Get Offer Details List
	public static Map<String, String> getOfferList(String city, String patient_code, String str)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("P_CITY", city);
		params.put("P_PTNT_CD", patient_code);
		params.put("P_STR", str);

		return params;
	}

	public static Map<String, String> passLabLocations(String city, String landmark, String pincode, String versionId,
			String radius, String lon, String lat)
	{

		Map<String, String> params = new HashMap<String, String>();

		params.put("CITY", city);
		params.put("LANDMARK", landmark);
		params.put("PINCODE", pincode);
		params.put("VERSION_ID", ApiConstants.appVersion);
		params.put("RADIUS", radius);
		params.put("FROM_LONGITUDE", lon);
		params.put("FROM_LATTITUDE", lat);

		return params;
	}

	public static Map<String, String> passGetOrders(String createdBy)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("CreatedBy", createdBy);

		return params;
	}

	public static Map<String, String> passGetReports(String ptntcode, String pwd, String deviceid, String deviceToken,
			String ostype, String osversion, String mysrl_version, String timestamp)
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("ptntcode", ptntcode);
		params.put("pwd", pwd);
		params.put("device_id", deviceid);
		params.put("device_token", deviceToken);
		params.put("ostype", ostype);
		params.put("osversion", ApiConstants.osVersion);
		params.put("mysrl_version", ApiConstants.appVersion);
		params.put("timestamp", timestamp);
		params.put("fcmtoken", Constants.RegID);
		return params;
	}

	//Get User Details
	public static Map<String, String> getUserDetails(String ptntcode)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("ptntcode", ptntcode);
		return params;
	}

	public static Map<String, String> getsurveys(String ptntcode,String mobileno)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("ptntcd", ptntcode);
		params.put("P_MOBILENO", mobileno);
		params.put("P_DEVICE_ID", Constants.devicetobepassed);
		params.put("P_OSTYPE", "ANDROID");
		params.put("P_OSVERSION", ApiConstants.osVersion);
		params.put("P_MYSRL_VERSION", ApiConstants.appVersion);
		return params;
	}

	//Update User Details
	public static Map<String, String> getUpdateUserDetails(String commonno, String userId, String password,
			String mobileNo, String emailid, String address1, String address2, String landmark, String city,
			String state, String country, String zip)
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("P_USERID", userId);
		params.put("P_PWD_ORG", password);
		params.put("P_MOBILE_NO", mobileNo);
		params.put("P_EMAIL_ID", emailid);
		params.put("P_ADDRESS1", address1);
		params.put("P_ADDRESS2", "");
		params.put("P_LANDMARK", landmark);
		params.put("P_CITY", city);
		params.put("P_STATE", state);
		params.put("P_COUNTRY", country);
		params.put("P_ZIP", zip);

		return params;
	}

	//Get User Details
	public static Map<String, String> getPromoCodes(String deviceid, String ptntcode, String mobile)
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("deviceid", deviceid);
		params.put("ptntcd", ptntcode);
		params.put("mobileno", mobile);

		return params;
	}

	public static Map<String, String> getMobikwik(String email, String amount, String cell, String orderid, String mid,
			String merchantname, String redirecturl, String showmobile, String version, String checksum)
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("email", "ritz@gmail.com");
		params.put("amount", "345");
		params.put("cell", "9704683480");
		params.put("orderid", "abcd");
		params.put("mid", "MBK9002");
		params.put("merchantname", "snapdeal.com");
		params.put("redirecturl", "http://www.snapdeal.com/mobikwikpaymentresponse.jsp");
		params.put("showmobile", "true");
		params.put("version", ApiConstants.appVersion);
		params.put("checksum", "ju6tygh7u7tdg554k098ujd5468o");

		return params;
	}

	public static Map<String, String> getValidatedPromo(String promo, String deviceid, String ptntcode, String mobile)
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("P_PROMOCODE", promo);
		params.put("P_DEVICEID", deviceid);
		params.put("P_PTNTCD", ptntcode);
		params.put("P_MOBILENO", mobile);

		return params;
	}

	public static Map<String, String> getValidatedPromo_cart(String P_City, String P_PROMOCODE, String P_DEVICEID,
			String P_PTNTCD, String P_MOBILENO, String P_TEST_CD, String P_TEST_ID, String P_T_AMNT,
			String P_T_GROSS_AMNT, String P_T_DISC, String P_T_OFR_CD, String P_GROSS_AMNT, String P_DISC,
			String P_PROMO_DISC, String P_OTHR_CHRG_1, String P_OTHR_CHRG_2, String P_GRNT_TOTAL, String P_RND_AMNT)
	{
		Map<String, String> params = new HashMap<String, String>();

		params.put("P_City", P_City);
		params.put("P_PROMOCODE", P_PROMOCODE);
		params.put("P_DEVICEID", P_DEVICEID);
		params.put("P_PTNTCD", P_PTNTCD);
		params.put("P_MOBILENO", P_MOBILENO);
		params.put("P_TEST_CD", P_TEST_CD);
		params.put("P_TEST_ID", P_TEST_ID);
		params.put("P_T_AMNT", P_T_AMNT);
		params.put("P_T_GROSS_AMNT", P_T_GROSS_AMNT);
		params.put("P_T_DISC", P_T_DISC);
		params.put("P_T_OFR_CD", P_T_OFR_CD);
		params.put("P_GROSS_AMNT", P_GROSS_AMNT);
		params.put("P_DISC", P_DISC);
		params.put("P_PROMO_DISC", P_PROMO_DISC);
		params.put("P_OTHR_CHRG_1", P_OTHR_CHRG_1);
		params.put("P_OTHR_CHRG_2", P_OTHR_CHRG_2);
		params.put("P_GRNT_TOTAL", P_GRNT_TOTAL);
		params.put("P_RND_AMNT", P_RND_AMNT);

		return params;
	}

	public static Map<String, String> getSearchedProducts(String city, String text)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("P_CITY", city);
		params.put("PRDCT_NAME", text);

		return params;
	}

	public static Map<String, String> getPdfReports(String city, String acc, String ptntcode, String mobile, String str,
			String deviceid, String deviceToken, String ostype, String osver, String mysrlver)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("P_CITY", city);
		params.put("P_ACC_ID", acc);
		params.put("P_PTNT_CD", ptntcode);
		params.put("P_MOBILENO", mobile);
		params.put("P_STR", str);
		params.put("P_DEVICE_ID", deviceid);
		params.put("P_DEVICE_TOKEN", deviceid);

		params.put("P_OSTYPE", ostype);
		params.put("P_OSVERSION", ApiConstants.osVersion);
		params.put("P_MYSRL_VERSION", ApiConstants.appVersion);

		return params;
	}

	public static Map<String, String> forgotPwd(String userid)
	{

		Map<String, String> params = new HashMap<String, String>();
		params.put("IN_USER_ID", userid);

		return params;
	}

	public static Map<String, String> login(String userid, String pwd)
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put(Constants.ptntcode, userid);
		params.put(Constants.pwd, pwd);
		return params;
	}

	public static Map<String, String> pendingRegistration(String devid)
	{

		Map<String, String> params = new HashMap<String, String>();
		params.put(Constants.deviceID, devid);
		return params;
	}

	public static Map<String, String> getUser(String userid)
	{

		Map<String, String> params = new HashMap<String, String>();
		params.put(Constants.ptntcode, userid);

		return params;
	}

	public static Map<String, String> getConfirmPassword(String ptntcode, String oldpwd, String newpwd)
	{

		Map<String, String> params = new HashMap<String, String>();
		params.put("ptntcode", ptntcode);
		params.put("pwd_org", oldpwd);
		params.put("pwd_new", newpwd);

		return params;
	}

	public static Map<String, String> getContentAPI(String screenid, String verid)
	{

		Map<String, String> params = new HashMap<String, String>();
		params.put("P_SCREENID", screenid);
		params.put("P_VER_ID", ApiConstants.appVersion);

		return params;
	}

	public static Map<String, String> getContentAPI1(String screenid, String verid)
	{

		Map<String, String> params = new HashMap<String, String>();
		params.put("P_SCREENID", screenid);
		params.put("P_VER_ID", verid);

		return params;
	}

	public static Map<String, String> getPrivacyAPI(String screenid, String verid)
	{

		Map<String, String> params = new HashMap<String, String>();
		params.put("P_SCREENID", screenid);
		params.put("P_VER_ID", ApiConstants.appVersion);

		return params;
	}

	public static Map<String, String> getTermsAPI(String screenid, String verid)
	{

		Map<String, String> params = new HashMap<String, String>();
		params.put("P_SCREENID", screenid);
		params.put("P_VER_ID", ApiConstants.appVersion);

		return params;
	}

	public static Map<String, String> resendOtp(String devidtobepassed)
	{

		Map<String, String> params = new HashMap<String, String>();
		params.put(Constants.IN_DEVICE_ID, devidtobepassed);

		return params;
	}

	public static Map<String, String> validateRegistration(String devidtobepassed, String otp)
	{

		Map<String, String> params = new HashMap<String, String>();
		params.put(Constants.IN_DEVICE_ID, devidtobepassed);
		params.put(Constants.IN_OTP, otp);

		return params;
	}

	public static Map<String, String> userRegistration(String salutation, String fname, String lname, String dob,
			String years, String months, String days, String gender, String mobile, String address1, String address2,
			String landmark, String city, String state, String country, String email, String zip, String deviceid,
			String ostype, String osversion, String mysrlver)
	{

		Map<String, String> params = new HashMap<String, String>();
		params.put(Constants.IN_TITLE, StringUtil.getValidString(salutation));
		params.put(Constants.IN_FIRST_NAME, StringUtil.getValidString(fname));
		params.put(Constants.IN_LAST_NAME, StringUtil.getValidString(lname));
		params.put(Constants.IN_DOB, StringUtil.getValidString(dob));
		params.put(Constants.IN_AGE_YYY, StringUtil.getValidString(years));
		params.put(Constants.IN_AGE_MM, StringUtil.getValidString(months));
		params.put(Constants.IN_AGE_DD, StringUtil.getValidString(days));
		params.put(Constants.IN_GENDER, StringUtil.getValidString(gender));
		params.put(Constants.IN_MOBILE_NO, StringUtil.getValidString(mobile));
		params.put(Constants.IN_ADDRESS1, StringUtil.getValidString(address1));
		params.put(Constants.IN_ADDRESS2, StringUtil.getValidString(address2));
		params.put(Constants.IN_LANDMARK, StringUtil.getValidString(landmark));
		params.put(Constants.IN_CITY, StringUtil.getValidString(city));
		params.put(Constants.IN_STATE, StringUtil.getValidString(state));
		params.put(Constants.IN_COUNTRY, StringUtil.getValidString(country));
		params.put(Constants.IN_EMAIL_ID, StringUtil.getValidString(email));
		params.put(Constants.IN_ZIP, StringUtil.getValidString(zip));
		params.put(Constants.IN_DEVICE_ID, StringUtil.getValidString(deviceid));
		params.put(Constants.IN_OSTYPE, StringUtil.getValidString(ostype));
		params.put(Constants.IN_OSVERSION, ApiConstants.osVersion);
		params.put(Constants.IN_MYSRLVER, ApiConstants.appVersion);

		return params;
	}

}
