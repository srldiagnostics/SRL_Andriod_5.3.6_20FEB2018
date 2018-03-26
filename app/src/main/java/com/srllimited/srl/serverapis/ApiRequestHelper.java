package com.srllimited.srl.serverapis;

import android.content.Context;

import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.util.Log;
import com.srllimited.srl.utilities.Utility;

import java.util.HashMap;
import java.util.Map;

import static com.srllimited.srl.serverapis.ApiRequestReferralCode.ALL_CATEGORIES;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.Authenticate_MobileNo;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.CANCEL_ORDER;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.CHECK_VERSION;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.ChangePassword;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.DELETE_NOTIFICATION;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.Display_Family_Member;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.FILTER_PRODUCTS;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.FORGOT_PWD;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.Family_Member_Mapping;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.GET_CALL_US;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.GET_CAROUSELS;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.GET_CITY;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.GET_CONTACT_US;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.GET_FEEDBACK;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.GET_LOGO;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.GET_NOTIFICATION;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.GET_OTP;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.GET_PATIENTDETAIL;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.GET_PAYTM_STATUS;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.GET_PAY_SUCCESS;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.GET_PDF;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.GET_PRIVACY;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.GET_PROMO;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.GET_Paytm_checksum;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.GET_TERMS;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.Get_CONTENT;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.Get_SURVEY;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.LAB_LOCATIONS;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.LOGIN;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.MY_CART;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.OFFER_LIST;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.ORDERS;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.PACKAGE_DETAILS;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.PAY;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.PENDING_REGISTRATION;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.REPEAT_ORDER;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.REPORTS;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.RESEND_OTP;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.ResendOTP_Mobile;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.SEARCH_PRODUCTS;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.TOP_PRODUCTS;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.TRACK_ORDER;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.UPDATE_USER_DETAILS;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.USER;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.USER_DETAILS;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.USER_REGISTRATION;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.VALIDATE_MOBIKWIK;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.VALIDATE_PROMO;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.VALIDATE_REGISTRATION;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.VERIFY_OTP;
import static com.srllimited.srl.serverapis.ApiRequestReferralCode.Validate_MobileNo;

/**
 * Created by RuchiTiwari on 1/14/2017.
 */
public class ApiRequestHelper {
    public static ApiRequest getTopProducts(Context context, String city, String patientCode) {

        Map<String, String> params = ApiParameters.passGetTopProductsParams(city, patientCode);

        ApiRequest request = new ApiRequest(TOP_PRODUCTS, ApiConstants.getTopProductsUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, city, params));
        } else {
            request.setParams(params);
        }
        return request;
    }

    //Paytm Status
    public static ApiRequest getPaytmPaymentStatus(String mid, String orderId) {
        String url = "https://secure.paytm.in/oltp/HANDLER_INTERNAL/TXNSTATUS?JsonData={%22MID%22:%22+" + mid + "%22"
                + "," + "%22ORDERID%22:%22" + orderId + "%22" + "}";
        ApiRequest request = new ApiRequest(GET_PAYTM_STATUS, url);
        return request;
    }

    public static ApiRequest getCity(Context context) {
        Map<String, String> params = ApiParameters.passGetCitiesParams();

        ApiRequest request = new ApiRequest(GET_CITY, ApiConstants.cityurl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, Constants.devicetobepassed, params));
        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest getOTP(Context context, String mobile) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.p_mobileno, mobile);
        ApiRequest request = new ApiRequest(GET_OTP, ApiConstants.getOTP_url);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, mobile, params));
        } else {
            request.setParams(params);
        }
        //P_MOBILENO
        return request;
    }

    public static ApiRequest getOTP_Login(Context context, String mobile) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.mobileno, mobile);
        ApiRequest request = new ApiRequest(Validate_MobileNo, ApiConstants.getLoginOTP_url);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, mobile, params));
        } else {
            request.setParams(params);
        }
        //P_MOBILENO
        return request;
    }
    public static ApiRequest getresendOTP_Login(Context context, String mobile) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.mobile_no2, mobile);
        ApiRequest request = new ApiRequest(ResendOTP_Mobile, ApiConstants.getresendLoginOTP_url);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, mobile, params));
        } else {
            request.setParams(params);
        }
        //P_MOBILENO
        return request;
    }
    public static ApiRequest getPatientDetail(Context context, String mobile) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.phoneNo, mobile);
        ApiRequest request = new ApiRequest(GET_PATIENTDETAIL, ApiConstants.getPatientDetails_url);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, mobile, params));
        } else {
            request.setParams(params);
        }
        //P_MOBILENO
        return request;
    }

    public static ApiRequest verifyOTP(Context context, String mobile, String optNo) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.p_mobileno, mobile);
        params.put(Constants.p_otpno, optNo);

        ApiRequest request = new ApiRequest(VERIFY_OTP, ApiConstants.VerifyOTP_url);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, mobile, params));
        } else {
            request.setParams(params);
        }
        //P_MOBILENO
        return request;
    }

    public static ApiRequest verifyOTPLogin(Context context, String mobile, String optNo, String ostype, String deviceid) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.mobileno, mobile);
        params.put(Constants.otpno, optNo);
        params.put(Constants.DEVICE_ID, deviceid);
        params.put(Constants.ostype, ostype);

        ApiRequest request = new ApiRequest(Authenticate_MobileNo, ApiConstants.VerifyOTPLogin_url);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, mobile, params));
        } else {
            request.setParams(params);
        }
        //P_MOBILENO
        return request;
    }

    public static ApiRequest Family_Member_Mapping(Context context, String primary_id, String familymember, String ostype, String deviceid, String mobileno) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.primary_user_id, primary_id);
        params.put(Constants.family_member_id, familymember);
        params.put(Constants.DEVICE_ID, deviceid);
        params.put(Constants.mobileno, mobileno);
        params.put(Constants.ostype, ostype);


        ApiRequest request = new ApiRequest(Family_Member_Mapping, ApiConstants.Family_Member_Mapping);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, primary_id, params));
        } else {
            request.setParams(params);
        }
        //P_MOBILENO
        return request;
    }
    public static ApiRequest Display_Family_Member(Context context ,String ostype, String deviceid, String mobileno) {
        Map<String, String> params = new HashMap<String, String>();


        params.put(Constants.DEVICE_ID, deviceid);
        params.put(Constants.mobileno, mobileno);
        params.put(Constants.ostype, ostype);


        ApiRequest request = new ApiRequest(Display_Family_Member, ApiConstants.Display_Family_Member);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, deviceid, params));
        } else {
            request.setParams(params);
        }
        //P_MOBILENO
        return request;
    }
    //sachin temp
    public static ApiRequest getLogin(String usename, String pass) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.ptntcode, usename);
        params.put(Constants.pwd, pass);
        ApiRequest request = new ApiRequest(LOGIN, ApiConstants.loginUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithTwoParam(usename, pass, params));
        } else {
            request.setParams(params);
        }

        //P_MOBILENO
        return request;
    }

    public static ApiRequest getCarousels(Context context, String city, String patient_code, String str) {
        Map<String, String> params = ApiParameters.getOfferList(city, patient_code, "");

        ApiRequest request = new ApiRequest(GET_CAROUSELS, ApiConstants.getCarouselsUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, city, params));
        } else {
            request.setParams(params);
        }

        return request;
    }

    //
    //	public static ApiRequest getCarousels()
    //	{
    //
    //		ApiRequest request = new ApiRequest(GET_CAROUSELS, ApiConstants.getCarouselsUrl);
    //		return request;
    //	}

    public static ApiRequest getTrackOrder(Context context, String order) {
        Map<String, String> params = ApiParameters.getTrackOrder(order);

        ApiRequest request = new ApiRequest(TRACK_ORDER, ApiConstants.TRACK_ORDERURL);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, order, params));
        } else {
            request.setParams(params);
        }
        return request;
    }

    public static ApiRequest getRepeatOrder(Context context, String order, String city) {
        Map<String, String> params = ApiParameters.repeatOrder(order, city);

        ApiRequest request = new ApiRequest(REPEAT_ORDER, ApiConstants.REPEAT_ORDERURL);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, order, params));
        } else {
            request.setParams(params);
        }
        return request;
    }

    public static ApiRequest getCancelOrder(Context context, String order) {
        Map<String, String> params = ApiParameters.cancelOrder(order);

        ApiRequest request = new ApiRequest(CANCEL_ORDER, ApiConstants.CANCEL_ORDERURL);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, order, params));
        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest getFilterProducts(Context context, String city, String type, String category) {
        Map<String, String> params = ApiParameters.passFilterProducts(city, type, category);

        ApiRequest request = new ApiRequest(FILTER_PRODUCTS, ApiConstants.filterProductsUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, city, params));
        } else {
            request.setParams(params);
        }
        return request;
    }

    public static ApiRequest getPackageDetails(Context context, String city, String category, String ptntcode,
                                               String str) {
        Map<String, String> params = ApiParameters.passGetPackageDetails(city, category, ptntcode, str);

        ApiRequest request = new ApiRequest(PACKAGE_DETAILS, ApiConstants.getPackageDetailsUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, city, params));
        } else {
            request.setParams(params);
        }
        return request;
    }

    public static ApiRequest getPaySuccess(Context context, String orderno, String payid, String paydt, String amt,
                                           String transid, String paymethod, String ipaddress, String paystatus, String payopt) {
        Map<String, String> params = ApiParameters.passGetPaySuccess(orderno, payid, paydt, amt, transid, paymethod,
                ipaddress, paystatus, payopt);

        Log.e("par", params + "");
        ApiRequest request = new ApiRequest(GET_PAY_SUCCESS, ApiConstants.getPaySuccessUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, orderno, params));
        } else {
            request.setParams(params);
        }
        return request;
    }

    public static ApiRequest getPaytmChecksum(Context context) {
        Map<String, String> params = ApiParameters.passGetAllCategories();

        ApiRequest request = new ApiRequest(GET_Paytm_checksum, ApiConstants.getpaytmchecksum_url);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithdefaultParam(context, params)); //need to as per encription parameter
        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest getAllCategories(Context context) {
        Map<String, String> params = ApiParameters.passGetAllCategories();

        ApiRequest request = new ApiRequest(ALL_CATEGORIES, ApiConstants.getAllCategoriesUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithdefaultParam(context, params));
        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest getMyCartDetails(Context context, String city, String testCode, String testId,
                                              String totalAmt, String grossAmt, String totaldisc, String ofrCode, String productGrossAmt, String disc,
                                              String promodisc, String otherCharge1, String otherCharge2, String grandTotal, String roundAmt,
                                              String promoCode) {

        Map<String, String> params = ApiParameters.passMyCartDetails(city, testCode, testId, totalAmt, grossAmt,
                totaldisc, ofrCode, productGrossAmt, disc, promodisc, otherCharge1, otherCharge2, grandTotal, roundAmt,
                promoCode);

        ApiRequest request = new ApiRequest(MY_CART, ApiConstants.myCartUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithTwoParam(context, city, grossAmt, params));
        } else {
            request.setParams(params);
        }
        return request;
    }

    public static ApiRequest getPayDetails(Context context, String flag, String orderid, String ptntcode,
                                           String address, String loc, String city, String state, String country, String zip, String doctor,
                                           String colDateFrom, String colDateTo, String hardCopy, String colContact, String test, String ptntName,
                                           String createBy, String grossAmt, String discount_flag, String discount, String promocode, String paymode,
                                           String paymentOpt, String title, String firstName, String lastName, String dob, String gender,
                                           String dobActFlg, String mobileNo, String emailId, String cartId, String coltype, String labid,
                                           String ostype, String mysrl_version) {

        Map<String, String> params = ApiParameters.passPayDetails(flag, orderid, ptntcode, address, loc, city, state,
                country, zip, doctor, colDateFrom, colDateTo, hardCopy, colContact, test, ptntName, createBy, grossAmt,
                discount_flag, discount, promocode, paymode, paymentOpt, title, firstName, lastName, dob, gender,
                dobActFlg, mobileNo, emailId, cartId, coltype, labid, ostype, mysrl_version);

        Log.e("params", params + "");
        ApiRequest request = new ApiRequest(PAY, ApiConstants.getPayUrl);

        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithTwoParam(context, ptntName, grossAmt, params));

        } else {
            request.setParams(params);
        }
        return request;
    }

    //Get Offer Details List
    public static ApiRequest getOfferList(String city, String patient_code, String str) {
        Map<String, String> params = ApiParameters.getOfferList(city, patient_code, "");

        ApiRequest request = new ApiRequest(OFFER_LIST, ApiConstants.getPromotionOffersUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(city, params));

        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest getLabLocations(Context context, String city, String landmark, String pincode,
                                             String versionId, String radius, String lon, String lat) {
        Map<String, String> params = ApiParameters.passLabLocations(city, landmark, pincode, versionId, radius, lon,
                lat);

        ApiRequest request = new ApiRequest(LAB_LOCATIONS, ApiConstants.getLabLocationsUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, city, params));
        } else {
            request.setParams(params);
        }
        return request;
    }

    public static ApiRequest getOrders(Context context, String createdBy) {
        Map<String, String> params = ApiParameters.passGetOrders(createdBy);

        ApiRequest request = new ApiRequest(ORDERS, ApiConstants.getOrdersUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, createdBy, params));
        } else {
            request.setParams(params);
        }
        return request;
    }

    public static ApiRequest getReports(Context context, String ptntcode, String pwd, String deviceid,
                                        String deviceToken, String ostype, String osversion, String mysrl_version, String timestamp) {
        Map<String, String> params = ApiParameters.passGetReports(ptntcode, pwd, deviceid, deviceToken, ostype,
                osversion, mysrl_version, timestamp);

        ApiRequest request = new ApiRequest(REPORTS, ApiConstants.getREportsUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithTwoParam(context, ptntcode, pwd.toUpperCase(), params));

        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest getUserDetails(Context context, String ptntcode) {
        Map<String, String> params = ApiParameters.getUserDetails(ptntcode);

        ApiRequest request = new ApiRequest(USER_DETAILS, ApiConstants.getUserDetailsUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, ptntcode, params));
            //  request.setParams(Utility.EncreptWithOneParam(ptntcode, params)); //grptype always 1
        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest getsurvey(Context context, String ptntcode, String mobileno) {
        Map<String, String> params = ApiParameters.getsurveys(ptntcode, mobileno);

        ApiRequest request = new ApiRequest(Get_SURVEY, ApiConstants.GetSurveyUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, mobileno, params));
            //  request.setParams(Utility.EncreptWithOneParam(ptntcode, params)); //grptype always 1
        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest getUpdateUserDetails(Context context, String commonno, String userId, String password,
                                                  String mobileNo, String emailid, String address1, String address2, String landmark, String city,
                                                  String state, String country, String zip) {

        Map<String, String> params = ApiParameters.getUpdateUserDetails(commonno, userId, password, mobileNo, emailid,
                address1, address2, landmark, city, state, country, zip);

        ApiRequest request = new ApiRequest(UPDATE_USER_DETAILS, ApiConstants.updateUserDetails);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, userId, params));
        } else {
            request.setParams(params);
        }
        return request;
    }

    public static ApiRequest getPromoCodes(Context context, String deviceid, String ptntcode, String mobile) {
        Map<String, String> params = ApiParameters.getPromoCodes(deviceid, ptntcode, mobile);

        ApiRequest request = new ApiRequest(GET_PROMO, ApiConstants.getPromoUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, deviceid, params));
        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest getValidatedPromo_Cart(Context context, String P_City, String P_PROMOCODE,
                                                    String P_DEVICEID, String P_PTNTCD, String P_MOBILENO, String P_TEST_CD, String P_TEST_ID, String P_T_AMNT,
                                                    String P_T_GROSS_AMNT, String P_T_DISC, String P_T_OFR_CD, String P_GROSS_AMNT, String P_DISC,
                                                    String P_PROMO_DISC, String P_OTHR_CHRG_1, String P_OTHR_CHRG_2, String P_GRNT_TOTAL, String P_RND_AMNT) {
        Map<String, String> params = ApiParameters.getValidatedPromo_cart(P_City, P_PROMOCODE, P_DEVICEID, P_PTNTCD,
                P_MOBILENO, P_TEST_CD, P_TEST_ID, P_T_AMNT, P_T_GROSS_AMNT, P_T_DISC, P_T_OFR_CD, P_GROSS_AMNT, P_DISC,
                P_PROMO_DISC, P_OTHR_CHRG_1, P_OTHR_CHRG_2, P_GRNT_TOTAL, P_RND_AMNT);

        // ApiRequest request = new ApiRequest(VALIDATE_PROMO, ApiConstants.validatePromoUrl);
        ApiRequest request = new ApiRequest(VALIDATE_PROMO, ApiConstants.validatePromoUrl_cart);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithTwoParam(context, P_PROMOCODE, P_T_GROSS_AMNT, params));
        } else {
            request.setParams(params);
        }
        return request;
    }

    public static ApiRequest getValidatedPromo(String promo, String deviceid, String ptntcode, String mobile) {
        Map<String, String> params = ApiParameters.getValidatedPromo(promo, deviceid, ptntcode, mobile);

        ApiRequest request = new ApiRequest(VALIDATE_PROMO, ApiConstants.validatePromoUrl);
        request.setParams(params);

        return request;
    }

    public static ApiRequest getSearchedProducts(Context context, String city, String text) {
        Map<String, String> params = ApiParameters.getSearchedProducts(city, text);

        ApiRequest request = new ApiRequest(SEARCH_PRODUCTS, ApiConstants.searchProductsUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithTwoParam(context, city, text, params));
        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest getPdfReports(Context context, String city, String acc, String ptntcode, String mobile,
                                           String str, String deviceid, String deviceToken, String ostype, String osver, String mysrlver) {
        Map<String, String> params = ApiParameters.getPdfReports(city, acc, ptntcode, mobile, str, deviceid,
                deviceToken, ostype, osver, mysrlver);

        ApiRequest request = new ApiRequest(GET_PDF, ApiConstants.getpdfUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithTwoParam(context, city, acc, params));
        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest getForgotPwd(String userid) {
        Map<String, String> params = ApiParameters.forgotPwd(userid);

        ApiRequest request = new ApiRequest(FORGOT_PWD, ApiConstants.forgotPwdUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(userid, params));
        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest getPendingRegistration(String devid) {
        Map<String, String> params = ApiParameters.pendingRegistration(devid);

        ApiRequest request = new ApiRequest(PENDING_REGISTRATION, ApiConstants.pendingRegistrationUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(devid, params));
        } else {
            request.setParams(params);
        }
        return request;
    }

    public static ApiRequest getUser(String userid) {
        Map<String, String> params = ApiParameters.forgotPwd(userid);

        ApiRequest request = new ApiRequest(USER, ApiConstants.getUserUrl);
        request.setParams(params);

        return request;
    }

    public static ApiRequest getResendOtp(String devidtobepassed) {
        Map<String, String> params = ApiParameters.resendOtp(devidtobepassed);

        ApiRequest request = new ApiRequest(RESEND_OTP, ApiConstants.resendOTPUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(devidtobepassed, params));
        } else {
            request.setParams(params);
        }
        return request;
    }

    public static ApiRequest getChangePassword(Context context, String ptntcode, String oldpwd, String newpwd) {
        Map<String, String> params = ApiParameters.getConfirmPassword(ptntcode, oldpwd, newpwd);

        ApiRequest request = new ApiRequest(ChangePassword, ApiConstants.changePasswordURl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, ptntcode, params));
        } else {
            request.setParams(params);
        }
        return request;
    }

    public static ApiRequest getContent(Context context, String screenid, String verid) {
        Map<String, String> params = ApiParameters.getContentAPI(screenid, verid);

        ApiRequest request = new ApiRequest(Get_CONTENT, ApiConstants.getContentUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, screenid, params));
        } else {
            request.setParams(params);
        }
        return request;
    }

    public static ApiRequest getCheckVersion(Context context, String screenid, String verid) {
        Map<String, String> params = ApiParameters.getContentAPI1(screenid, verid); ///need to change

        ApiRequest request = new ApiRequest(CHECK_VERSION, ApiConstants.getContentUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, screenid, params));
        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest getCALL_US(Context context, String screenid, String verid) {
        Map<String, String> params = ApiParameters.getPrivacyAPI(screenid, verid);

        ApiRequest request = new ApiRequest(GET_CALL_US, ApiConstants.getContentUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, screenid, params));
        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest getContactus(Context context, String screenid, String verid) {
        Map<String, String> params = ApiParameters.getPrivacyAPI(screenid, verid);

        ApiRequest request = new ApiRequest(GET_CONTACT_US, ApiConstants.getContentUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, screenid, params));
        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest getLOGO(Context context, String screenid, String verid) {
        Map<String, String> params = ApiParameters.getPrivacyAPI(screenid, verid);

        ApiRequest request = new ApiRequest(GET_LOGO, ApiConstants.getContentUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, screenid, params));
        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest getPrivacy(Context context, String screenid, String verid) {
        Map<String, String> params = ApiParameters.getPrivacyAPI(screenid, verid);

        ApiRequest request = new ApiRequest(GET_PRIVACY, ApiConstants.getContentUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, screenid, params));
        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest getTermsOfUse(Context context, String screenid, String verid) {
        Map<String, String> params = ApiParameters.getTermsAPI(screenid, verid);

        ApiRequest request = new ApiRequest(GET_TERMS, ApiConstants.getContentUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, screenid, params));
        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest getValidateRegistation(String devid, String devidtobepassed) {
        Map<String, String> params = ApiParameters.validateRegistration(devid, devidtobepassed);

        ApiRequest request = new ApiRequest(VALIDATE_REGISTRATION, ApiConstants.validateRegistrationUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(devid, params));
        } else {
            request.setParams(params);
        }
        return request;
    }

    public static ApiRequest getMobikiwik(String email, String amount, String cell, String orderid, String mid,
                                          String merchantname, String redirecturl, String showmobile, String version, String checksum) {
        Map<String, String> params = ApiParameters.getMobikwik(email, amount, cell, orderid, mid, merchantname,
                redirecturl, showmobile, version, checksum);

        ApiRequest request = new ApiRequest(VALIDATE_MOBIKWIK, ApiConstants.validatemobikwikurl);
        request.setParams(params);

        return request;
    }

    public static ApiRequest getUserRegistation(String salutation, String fname, String lname, String dob, String years,
                                                String months, String days, String gender, String mobile, String address1, String address2, String landmark,
                                                String city, String state, String country, String email, String zip, String deviceid, String ostype,
                                                String osversion, String mysrlver) {
        Map<String, String> params = ApiParameters.userRegistration(salutation, fname, lname, dob, years, months, days,
                gender, mobile, address1, address2, landmark, city, state, country, email, zip, deviceid, ostype,
                osversion, mysrlver);

        ApiRequest request = new ApiRequest(USER_REGISTRATION, ApiConstants.userRegistrationUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithTwoParam(fname, lname, params));
        } else {
            request.setParams(params);
        }
        return request;
    }

    ///
    //Rate Us
    public static ApiRequest getRateUs(Context context, String ptnt_cd, String p_rating, String p_feedBack,
                                       String p_deviceId, String p_os_version, String p_os_type, String p_app_version) {
        Map<String, String> params = ApiParameters.getRateusSuccess(ptnt_cd, p_rating, p_feedBack, p_deviceId,
                p_os_version, p_os_type, p_app_version);

        ApiRequest request = new ApiRequest(GET_FEEDBACK, ApiConstants.getFeedbackUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, p_os_type, params));
        } else {
            request.setParams(params);
        }
        return request;
    }

    public static ApiRequest getNotifications(Context context, String ptnt_cd, String p_deviceId, String p_os_type) {
        Map<String, String> params = ApiParameters.getNotifications(ptnt_cd, p_deviceId, p_os_type);

        ApiRequest request = new ApiRequest(GET_NOTIFICATION, ApiConstants.getNotificationUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithTwoParam(context, ptnt_cd, p_deviceId, params));

        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest deleteNotifications(Context context, String queueid, String ptnt_cd, String p_deviceId,
                                                 String p_os_type) {
        Map<String, String> params = ApiParameters.deleteNotifications(queueid, ptnt_cd, p_deviceId, p_os_type);

        ApiRequest request = new ApiRequest(DELETE_NOTIFICATION, ApiConstants.deleteNotificationUrl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, queueid, params));

        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest deleteNotifications(String ptnt_cd, String p_deviceId, String p_os_type) {
        Map<String, String> params = ApiParameters.getNotifications(ptnt_cd, p_deviceId, p_os_type);

        ApiRequest request = new ApiRequest(GET_NOTIFICATION, ApiConstants.getNotificationUrl);
        request.setParams(params);

        return request;
    }

    public static ApiRequest getAllStates(Context context) {
        Map<String, String> params = ApiParameters.getStatesList();

        ApiRequest request = new ApiRequest(ApiRequestReferralCode.GET_STATES, ApiConstants.allStatesurl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithdefaultParam(context, params));
        } else {
            request.setParams(params);
        }

        return request;
    }

    public static ApiRequest getGraphList(Context context) {
        Map<String, String> params = ApiParameters.getgraphList();

        ApiRequest request = new ApiRequest(ApiRequestReferralCode.GET_GRAPHLIST, ApiConstants.getGraphList_url);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithdefaultParam(context, params));
        } else {
            request.setParams(params);
        }
        return request;
    }

    //Get Cities List based on States
    public static ApiRequest getCities(Context context, String stateId) {
        Map<String, String> params = ApiParameters.getCitiesList(stateId);

        ApiRequest request = new ApiRequest(ApiRequestReferralCode.GET_CITIES, ApiConstants.citiesurl);
        if (Constants.isTOKENAPI) {
            request.setParams(Utility.EncreptWithOneParam(context, stateId, params));
        } else {
            request.setParams(params);
        }

        return request;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
	public static ApiRequest getTopProducts(String city, String patientCode) {
	
	Map<String, String> params = ApiParameters.passGetTopProductsParams(city, patientCode);
	
	ApiRequest request = new ApiRequest(TOP_PRODUCTS, ApiConstants.getTopProductsUrl);
	if(Constants.isTOKENAPI) {
	    request.setParams(Utility.EncreptWithOneParam(city, params));
	}
	else{
	    request.setParams(params);
	}
	return request;
	}
	
	//Paytm Status
	public static ApiRequest getPaytmPaymentStatus(String mid, String orderId) {
	    String url = "https://secure.paytm.in/oltp/HANDLER_INTERNAL/TXNSTATUS?JsonData={%22MID%22:%22+" + mid + "%22" + "," + "%22ORDERID%22:%22" + orderId + "%22" + "}";
	    ApiRequest request = new ApiRequest(GET_PAYTM_STATUS, url);
	    return request;
	}
	
	public static ApiRequest getCity() {
	    Map<String, String> params = ApiParameters.passGetCitiesParams();
	
	    ApiRequest request = new ApiRequest(GET_CITY, ApiConstants.cityurl);
	    if(Constants.isTOKENAPI) {
	        request.setParams(Utility.EncreptWithOneParam(Constants.devicetobepassed, params));
	    }
	    else{
	        request.setParams(params);
	    }
	
	    return request;
	}
	public static ApiRequest getOTP( String mobile) {
	    Map<String, String> params = new HashMap<String, String>();
	    params.put(Constants.p_mobileno, mobile);
	    ApiRequest request = new ApiRequest(GET_OTP,ApiConstants.getOTP_url);
	    if(Constants.isTOKENAPI) {
	        request.setParams(Utility.EncreptWithOneParam(mobile, params));
	    }
	    else{
	        request.setParams(params);
	    }
	    //P_MOBILENO
	    return request;
	}
	public static ApiRequest getPatientDetail( String mobile) {
	    Map<String, String> params = new HashMap<String, String>();
	    params.put(Constants.phoneNo, mobile);
	    ApiRequest request = new ApiRequest(GET_PATIENTDETAIL,ApiConstants.getPatientDetails_url);
	    if(Constants.isTOKENAPI) {
	        request.setParams(Utility.EncreptWithOneParam(mobile, params));
	    }
	    else{
	        request.setParams(params);
	    }
	    //P_MOBILENO
	    return request;
	}
	public static ApiRequest verifyOTP( String mobile,String optNo) {
	    Map<String, String> params = new HashMap<String, String>();
	    params.put(Constants.p_mobileno, mobile);
	    params.put(Constants.p_otpno, optNo);
	
	    ApiRequest request = new ApiRequest(VERIFY_OTP,ApiConstants.VerifyOTP_url);
	    if(Constants.isTOKENAPI) {
	        request.setParams(Utility.EncreptWithOneParam(mobile, params));
	    }
	    else{
	        request.setParams(params);
	    }
	    //P_MOBILENO
	    return request;
	}
	//sachin temp
	public static ApiRequest getLogin( String usename,String pass) {
	    Map<String, String> params = new HashMap<String, String>();
	    params.put(Constants.ptntcode, usename);
	    params.put(Constants.pwd, pass);
	    ApiRequest request = new ApiRequest(LOGIN,ApiConstants.loginUrl);
	    if(Constants.isTOKENAPI) {
	        request.setParams(Utility.EncreptWithTwoParam(usename, pass, params));
	    }
	    else{
	        request.setParams(params);
	    }
	
	    //P_MOBILENO
	    return request;
	}
	public static ApiRequest getCarousels(Context context,String city, String patient_code, String str) {
	    Map<String, String> params = ApiParameters.getOfferList(city, patient_code, "");
	
	    ApiRequest request = new ApiRequest(GET_CAROUSELS, ApiConstants.getCarouselsUrl);
	    if(Constants.isTOKENAPI) {
	        request.setParams(Utility.EncreptWithOneParam(context,city, params));
	    }
	    else{
	        request.setParams(params);
	    }
	
	    return request;
	}
	
	
	//
	//	public static ApiRequest getCarousels()
	//	{
	//
	//		ApiRequest request = new ApiRequest(GET_CAROUSELS, ApiConstants.getCarouselsUrl);
	//		return request;
	//	}
	
	public static ApiRequest getTrackOrder(String order) {
	    Map<String, String> params = ApiParameters.getTrackOrder(order);
	
	    ApiRequest request = new ApiRequest(TRACK_ORDER, ApiConstants.TRACK_ORDERURL);
	    if(Constants.isTOKENAPI) {
	        request.setParams(Utility.EncreptWithOneParam(order,params));
	    }
	    else{
	        request.setParams(params);
	    }
	    return request;
	}
	
	public static ApiRequest getRepeatOrder(String order,String city) {
	    Map<String, String> params = ApiParameters.repeatOrder(order,city);
	
	    ApiRequest request = new ApiRequest(REPEAT_ORDER, ApiConstants.REPEAT_ORDERURL);
	    if(Constants.isTOKENAPI) {
	        request.setParams(Utility.EncreptWithOneParam(order,params));
	    }
	    else{
	        request.setParams(params);
	    }
	    return request;
	}
	
	
	public static ApiRequest getCancelOrder(String order) {
	    Map<String, String> params = ApiParameters.cancelOrder(order);
	
	    ApiRequest request = new ApiRequest(CANCEL_ORDER, ApiConstants.CANCEL_ORDERURL);
	    if(Constants.isTOKENAPI) {
	        request.setParams(Utility.EncreptWithOneParam(order,params));
	    }
	    else{
	        request.setParams(params);
	    }
	
	
	    return request;
	}
	
	public static ApiRequest getFilterProducts(String city, String type, String category) {
	    Map<String, String> params = ApiParameters.passFilterProducts(city, type, category);
	
	    ApiRequest request = new ApiRequest(FILTER_PRODUCTS, ApiConstants.filterProductsUrl);
	    if(Constants.isTOKENAPI) {
	        request.setParams(Utility.EncreptWithOneParam(city,params));
	    }
	    else{
	        request.setParams(params);
	    }
	    return request;
	}
	
	public static ApiRequest getPackageDetails(String city, String category, String ptntcode, String str) {
	    Map<String, String> params = ApiParameters.passGetPackageDetails(city, category, ptntcode, str);
	
	    ApiRequest request = new ApiRequest(PACKAGE_DETAILS, ApiConstants.getPackageDetailsUrl);
	    if(Constants.isTOKENAPI) {
	        request.setParams(Utility.EncreptWithOneParam(city,params));
	    }
	    else{
	        request.setParams(params);
	    }
	    return request;
	}
	
	
	public static ApiRequest getPaySuccess(String orderno, String payid, String paydt, String amt, String transid, String paymethod, String ipaddress, String paystatus, String payopt) {
	    Map<String, String> params = ApiParameters.passGetPaySuccess(orderno, payid, paydt, amt, transid, paymethod, ipaddress, paystatus, payopt);
	
	    Log.e("par",params+"");
	    ApiRequest request = new ApiRequest(GET_PAY_SUCCESS, ApiConstants.getPaySuccessUrl);
	    if(Constants.isTOKENAPI) {
	        request.setParams(Utility.EncreptWithOneParam(orderno,params));
	    }
	    else{
	        request.setParams(params);
	    }
	    return request;
	}
	
	public static ApiRequest getAllCategories() {
	    Map<String, String> params = ApiParameters.passGetAllCategories();
	
	    ApiRequest request = new ApiRequest(ALL_CATEGORIES, ApiConstants.getAllCategoriesUrl);
	    if(Constants.isTOKENAPI) {
	        request.setParams(Utility.EncreptWithdefaultParam(params));
	    }
	    else{
	        request.setParams(params);
	    }
	
	    return request;
	}
	
	public static ApiRequest getMyCartDetails(String city, String testCode, String testId, String totalAmt, String grossAmt,
	                                          String totaldisc, String ofrCode, String productGrossAmt, String disc, String promodisc,
	                                          String otherCharge1, String otherCharge2, String grandTotal, String roundAmt, String promoCode) {
	
	    Map<String, String> params = ApiParameters.passMyCartDetails(city, testCode, testId, totalAmt, grossAmt, totaldisc,
	            ofrCode, productGrossAmt, disc, promodisc, otherCharge1,
	            otherCharge2, grandTotal, roundAmt, promoCode);
	
	    ApiRequest request = new ApiRequest(MY_CART, ApiConstants.myCartUrl);
	    if(Constants.isTOKENAPI) {
	        request.setParams(Utility.EncreptWithTwoParam(city,grossAmt,params));
	    }
	    else{
	        request.setParams(params);
	    }
	    return request;
	}
	*/
	/* need to remove after testing *//*
										
										public static ApiRequest getMyLogin(Map<String, String> params){
										
										ApiRequest request = new ApiRequest(LOGIN, ApiConstants.myCartUrl);
										request.setParams(params);
										*/
	/* String strHead =   iToken","SRLDIAG2017");
	    request.setPostData();*//*
									
									
									return request;
									}
									
									public static ApiRequest getPayDetails(String flag, String orderid, String ptntcode, String address, String loc,
									           String city, String state, String country, String zip, String doctor,
									           String colDateFrom, String colDateTo, String hardCopy, String colContact,
									           String test, String ptntName, String createBy, String grossAmt, String discount_flag,
									           String discount, String promocode, String paymode, String paymentOpt,
									           String title, String firstName, String lastName, String dob, String gender,
									           String dobActFlg, String mobileNo, String emailId, String cartId,
									           String coltype, String labid,String ostype,String mysrl_version) {
									
									Map<String, String> params = ApiParameters.passPayDetails(flag, orderid, ptntcode, address, loc,
									city, state, country, zip, doctor,
									colDateFrom, colDateTo, hardCopy, colContact,
									test, ptntName, createBy, grossAmt, discount_flag,
									discount, promocode, paymode, paymentOpt,
									title, firstName, lastName, dob, gender,
									dobActFlg, mobileNo, emailId, cartId, coltype, labid,ostype,mysrl_version);
									
									Log.e("params", params + "");
									ApiRequest request = new ApiRequest(PAY, ApiConstants.getPayUrl);
									
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithTwoParam(ptntName, grossAmt, params));
									
									}
									else{
									request.setParams(params);
									}
									return request;
									}
									
									
									//Get Offer Details List
									public static ApiRequest getOfferList(String city, String patient_code, String str) {
									Map<String, String> params = ApiParameters.getOfferList(city, patient_code, "");
									
									ApiRequest request = new ApiRequest(OFFER_LIST, ApiConstants.getPromotionOffersUrl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithOneParam(city, params));
									
									}
									else{
									request.setParams(params);
									}
									
									return request;
									}
									
									
									public static ApiRequest getLabLocations(String city, String landmark, String pincode, String versionId,
									             String radius, String lon, String lat) {
									Map<String, String> params = ApiParameters.passLabLocations(city, landmark, pincode, versionId, radius, lon, lat);
									
									ApiRequest request = new ApiRequest(LAB_LOCATIONS, ApiConstants.getLabLocationsUrl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithOneParam(city, params));
									}
									else{
									request.setParams(params);
									}
									return request;
									}
									
									
									public static ApiRequest getOrders(String createdBy) {
									Map<String, String> params = ApiParameters.passGetOrders(createdBy);
									
									ApiRequest request = new ApiRequest(ORDERS, ApiConstants.getOrdersUrl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithOneParam(createdBy, params));
									}
									else{
									request.setParams(params);
									}
									return request;
									}
									
									public static ApiRequest getReports(String ptntcode, String pwd, String deviceid, String deviceToken,
									        String ostype, String osversion, String mysrl_version, String timestamp) {
									Map<String, String> params = ApiParameters.passGetReports(ptntcode, pwd, deviceid, deviceToken, ostype, osversion, mysrl_version, timestamp);
									
									ApiRequest request = new ApiRequest(REPORTS, ApiConstants.getREportsUrl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithTwoParam(ptntcode, pwd.toUpperCase(), params));
									
									}
									else{
									request.setParams(params);
									}
									
									return request;
									}
									
									public static ApiRequest getUserDetails(String ptntcode) {
									Map<String, String> params = ApiParameters.getUserDetails(ptntcode);
									
									ApiRequest request = new ApiRequest(USER_DETAILS, ApiConstants.getUserDetailsUrl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithOneParam(ptntcode, params));
									}
									else{
									request.setParams(params);
									}
									
									return request;
									}
									
									public static ApiRequest getUpdateUserDetails(String commonno, String userId, String password, String mobileNo, String emailid,
									                  String address1, String address2, String landmark, String city, String state, String country, String zip) {
									
									Map<String, String> params = ApiParameters.getUpdateUserDetails(commonno, userId, password, mobileNo, emailid,
									address1, address2, landmark, city, state, country, zip);
									
									ApiRequest request = new ApiRequest(UPDATE_USER_DETAILS, ApiConstants.updateUserDetails);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithOneParam(userId, params));
									}
									else{
									request.setParams(params);
									}
									return request;
									}
									
									public static ApiRequest getPromoCodes(String deviceid, String ptntcode, String mobile) {
									Map<String, String> params = ApiParameters.getPromoCodes(deviceid, ptntcode, mobile);
									
									ApiRequest request = new ApiRequest(GET_PROMO, ApiConstants.getPromoUrl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithOneParam(deviceid, params));
									}
									else{
									request.setParams(params);
									}
									
									return request;
									}
									public static ApiRequest getValidatedPromo_Cart(String P_City, String P_PROMOCODE, String P_DEVICEID, String P_PTNTCD,
									                    String P_MOBILENO,String P_TEST_CD,String P_TEST_ID,String P_T_AMNT,
									                    String P_T_GROSS_AMNT,String P_T_DISC,String P_T_OFR_CD,String P_GROSS_AMNT,
									                    String P_DISC,String P_PROMO_DISC,String P_OTHR_CHRG_1,String P_OTHR_CHRG_2,
									                    String P_GRNT_TOTAL,String P_RND_AMNT) {
									Map<String, String> params = ApiParameters.getValidatedPromo_cart(P_City, P_PROMOCODE, P_DEVICEID, P_PTNTCD,
									P_MOBILENO,P_TEST_CD,P_TEST_ID,P_T_AMNT,
									P_T_GROSS_AMNT,P_T_DISC,P_T_OFR_CD,P_GROSS_AMNT,
									P_DISC,P_PROMO_DISC,P_OTHR_CHRG_1,P_OTHR_CHRG_2,
									P_GRNT_TOTAL,P_RND_AMNT);
									
									// ApiRequest request = new ApiRequest(VALIDATE_PROMO, ApiConstants.validatePromoUrl);
									ApiRequest request = new ApiRequest(VALIDATE_PROMO, ApiConstants.validatePromoUrl_cart);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithTwoParam(P_PROMOCODE,P_T_GROSS_AMNT, params));
									}
									else{
									request.setParams(params);
									}
									return request;
									}
									public static ApiRequest getValidatedPromo(String promo, String deviceid, String ptntcode, String mobile) {
									Map<String, String> params = ApiParameters.getValidatedPromo(promo, deviceid, ptntcode, mobile);
									
									ApiRequest request = new ApiRequest(VALIDATE_PROMO, ApiConstants.validatePromoUrl);
									request.setParams(params);
									
									return request;
									}
									
									public static ApiRequest getSearchedProducts(String city, String text) {
									Map<String, String> params = ApiParameters.getSearchedProducts(city, text);
									
									ApiRequest request = new ApiRequest(SEARCH_PRODUCTS, ApiConstants.searchProductsUrl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithTwoParam(city, text,params));
									}
									else{
									request.setParams(params);
									}
									
									return request;
									}
									
									public static ApiRequest getPdfReports(String city, String acc, String ptntcode, String mobile, String str,
									           String deviceid, String deviceToken, String ostype, String osver, String mysrlver) {
									Map<String, String> params = ApiParameters.getPdfReports(city, acc, ptntcode, mobile, str, deviceid, deviceToken, ostype, osver, mysrlver);
									
									ApiRequest request = new ApiRequest(GET_PDF, ApiConstants.getpdfUrl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithTwoParam(city,acc, params));
									}
									else{
									request.setParams(params);
									}
									
									return request;
									}
									
									
									
									public static ApiRequest getForgotPwd(String userid) {
									Map<String, String> params = ApiParameters.forgotPwd(userid);
									
									ApiRequest request = new ApiRequest(FORGOT_PWD, ApiConstants.forgotPwdUrl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithOneParam(userid, params));
									}
									else{
									request.setParams(params);
									}
									
									return request;
									}
									
									public static ApiRequest getPendingRegistration(String devid) {
									Map<String, String> params = ApiParameters.pendingRegistration(devid);
									
									ApiRequest request = new ApiRequest(PENDING_REGISTRATION, ApiConstants.pendingRegistrationUrl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithOneParam(devid, params));
									}
									else{
									request.setParams(params);
									}
									return request;
									}
									
									public static ApiRequest getUser(String userid) {
									Map<String, String> params = ApiParameters.forgotPwd(userid);
									
									ApiRequest request = new ApiRequest(USER, ApiConstants.getUserUrl);
									request.setParams(params);
									
									return request;
									}
									
									
									public static ApiRequest getResendOtp(String devidtobepassed) {
									Map<String, String> params = ApiParameters.resendOtp(devidtobepassed);
									
									ApiRequest request = new ApiRequest(RESEND_OTP, ApiConstants.resendOTPUrl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithOneParam(devidtobepassed,params));
									}
									else{
									request.setParams(params);
									}
									return request;
									}
									
									
									public static ApiRequest getChangePassword(String ptntcode, String oldpwd, String newpwd) {
									Map<String, String> params = ApiParameters.getConfirmPassword(ptntcode, oldpwd, newpwd);
									
									ApiRequest request = new ApiRequest(ChangePassword, ApiConstants.changePasswordURl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithOneParam(ptntcode,params));
									}
									else{
									request.setParams(params);
									}
									return request;
									}
									
									
									public static ApiRequest getContent(String screenid, String verid) {
									Map<String, String> params = ApiParameters.getContentAPI(screenid, verid);
									
									ApiRequest request = new ApiRequest(Get_CONTENT, ApiConstants.getContentUrl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithOneParam(screenid,params));
									}
									else{
									request.setParams(params);
									}
									return request;
									}
									public static ApiRequest getCheckVersion(String screenid, String verid) {
									Map<String, String> params = ApiParameters.getContentAPI1(screenid, verid); ///need to change
									
									ApiRequest request = new ApiRequest(CHECK_VERSION, ApiConstants.getContentUrl);
									if(Constants.isTOKENAPI){
									request.setParams(Utility.EncreptWithOneParam(screenid,params));
									}
									else{
									request.setParams(params);
									}
									
									return request;
									}
									
									public static ApiRequest getPrivacy(String screenid, String verid) {
									Map<String, String> params = ApiParameters.getPrivacyAPI(screenid, verid);
									
									ApiRequest request = new ApiRequest(GET_PRIVACY, ApiConstants.getContentUrl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithOneParam(screenid,params));
									}
									else{
									request.setParams(params);
									}
									
									return request;
									}
									
									public static ApiRequest getTermsOfUse(String screenid, String verid) {
									Map<String, String> params = ApiParameters.getTermsAPI(screenid, verid);
									
									ApiRequest request = new ApiRequest(GET_TERMS, ApiConstants.getContentUrl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithOneParam(screenid,params));
									}
									else{
									request.setParams(params);
									}
									
									return request;
									}
									
									
									public static ApiRequest getValidateRegistation(String devid, String devidtobepassed) {
									Map<String, String> params = ApiParameters.validateRegistration(devid, devidtobepassed);
									
									ApiRequest request = new ApiRequest(VALIDATE_REGISTRATION, ApiConstants.validateRegistrationUrl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithOneParam(devid,params));
									}
									else{
									request.setParams(params);
									}
									return request;
									}
									
									public static ApiRequest getMobikiwik(String email, String
									amount, String
									                  cell, String
									                  orderid, String
									                  mid, String
									                  merchantname, String
									                  redirecturl, String
									                  showmobile, String
									                  version, String
									                  checksum) {
									Map<String, String> params = ApiParameters.getMobikwik(email,
									amount,
									cell,
									orderid,
									mid,
									merchantname,
									redirecturl,
									showmobile,
									version,
									checksum);
									
									ApiRequest request = new ApiRequest(VALIDATE_MOBIKWIK, ApiConstants.validatemobikwikurl);
									request.setParams(params);
									
									return request;
									}
									
									
									public static ApiRequest getUserRegistation(String salutation, String fname, String lname, String dob, String years,
									                String months, String days, String gender, String mobile, String address1,
									                String address2, String landmark, String city, String state, String country,
									                String email, String zip, String deviceid, String ostype,
									                String osversion, String mysrlver) {
									Map<String, String> params = ApiParameters.userRegistration(salutation, fname, lname, dob, years, months, days,
									gender, mobile, address1, address2, landmark, city,
									state, country, email, zip, deviceid, ostype, osversion,
									mysrlver);
									
									ApiRequest request = new ApiRequest(USER_REGISTRATION, ApiConstants.userRegistrationUrl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithTwoParam(fname,lname,params));
									}
									else{
									request.setParams(params);
									}
									return request;
									}
									
									///
									//Rate Us
									public static ApiRequest getRateUs(String ptnt_cd, String p_rating, String p_feedBack, String p_deviceId, String p_os_version,
									       String p_os_type, String p_app_version) {
									Map<String, String> params = ApiParameters.getRateusSuccess(ptnt_cd, p_rating, p_feedBack, p_deviceId, p_os_version, p_os_type, p_app_version);
									
									ApiRequest request = new ApiRequest(GET_FEEDBACK, ApiConstants.getFeedbackUrl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithOneParam(p_os_type,params));
									}
									else{
									request.setParams(params);
									}
									return request;
									}
									
									
									public static ApiRequest getNotifications(String ptnt_cd, String p_deviceId,
									              String p_os_type) {
									Map<String, String> params = ApiParameters.getNotifications( ptnt_cd,  p_deviceId,
									p_os_type);
									
									ApiRequest request = new ApiRequest(GET_NOTIFICATION, ApiConstants.getNotificationUrl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithTwoParam(ptnt_cd,p_deviceId,params));
									
									}
									else{
									request.setParams(params);
									}
									
									return request;
									}
									
									public static ApiRequest deleteNotifications(String queueid,String ptnt_cd, String p_deviceId,
									                 String p_os_type) {
									Map<String, String> params = ApiParameters.deleteNotifications( queueid,ptnt_cd,  p_deviceId,
									p_os_type);
									
									ApiRequest request = new ApiRequest(DELETE_NOTIFICATION, ApiConstants.deleteNotificationUrl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithOneParam(queueid,params));
									
									}
									else{
									request.setParams(params);
									}
									
									return request;
									}
									
									public static ApiRequest deleteNotifications(String ptnt_cd, String p_deviceId,
									                 String p_os_type) {
									Map<String, String> params = ApiParameters.getNotifications( ptnt_cd,  p_deviceId,
									p_os_type);
									
									ApiRequest request = new ApiRequest(GET_NOTIFICATION, ApiConstants.getNotificationUrl);
									request.setParams(params);
									
									return request;
									}
									
									public static ApiRequest getAllStates() {
									Map<String, String> params = ApiParameters.getStatesList();
									
									ApiRequest request = new ApiRequest(ApiRequestReferralCode.GET_STATES, ApiConstants.allStatesurl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithdefaultParam(params));
									
									}
									else{
									request.setParams(params);
									}
									
									return request;
									}
									public static ApiRequest getGraphList() {
									Map<String, String> params = ApiParameters.getgraphList();
									
									ApiRequest request = new ApiRequest(ApiRequestReferralCode.GET_GRAPHLIST, ApiConstants.getGraphList_url);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithdefaultParam(params));
									}
									else{
									request.setParams(params);
									}
									return request;
									}
									//Get Cities List based on States
									public static ApiRequest getCities(String stateId) {
									Map<String, String> params = ApiParameters.getCitiesList(stateId);
									
									ApiRequest request = new ApiRequest(ApiRequestReferralCode.GET_CITIES, ApiConstants.citiesurl);
									if(Constants.isTOKENAPI) {
									request.setParams(Utility.EncreptWithOneParam(stateId,params));
									}
									else{
									request.setParams(params);
									}
									
									return request;
									}
									*/

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}