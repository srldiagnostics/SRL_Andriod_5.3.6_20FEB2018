package com.srllimited.srl.serverapis;

/**
 * Created by RuchiTiwari on 1/14/2017.
 */

public class ApiConstants {
    public static final String appVersion = "5.4.0";//Changed As per Mail Thu 1/11/2018 5:51 PM
    public static final String validatemobikwikurl = "http://test.mobikwik.com/wallet";

    //Development
    /*private static final String ipAddress = "http://104.211.96.182:90/Service.asmx";
    private static final String ipAddress2 = "http://104.211.96.182:90/Service.asmx";*/

    //private static final String ipAddress3 = "http://104.211.96.182:90/Service.asmx";

    //93 with token added production

    private static final String ipAddress = "http://srldiagapp.mysrl.in:93/service.asmx";
    private static final String ipAddress2 = "http://srldiagapp.mysrl.in:93/service.asmx";

// quility
 /*private static final String ipAddress = "http://srldiagapp.mysrl.in:92/service.asmx";
 private static final String ipAddress2 = "http://srldiagapp.mysrl.in:92/service.asmx";*/

    public static final String cityurl = ipAddress + "/GetCityStateList";
    public static final String cityListurl = ipAddress
            + "/?Device_ID=009&DEVICE_TOKEN=1&OSTYPE=ANDROID&OSVERSION=1&MYSRLVER=1";
    public static final String pendingRegistrationUrl = ipAddress + "/PendingRegistration";
    public static final String userRegistrationUrl = ipAddress + "/UserRegistration";
    public static final String resendOTPUrl = ipAddress + "/ResendOTP";
    public static final String validateRegistrationUrl = ipAddress + "/ValidateRegistration";
    public static final String validateRegistrationUrl_family = ipAddress + "/ValidateRegistration_Family";
    public static final String forgotPwdUrl = ipAddress + "/ForgotPassword";
    public static final String getREportsUrl = ipAddress + "/FetchDataNew";
    public static final String getPromoUrl = ipAddress + "/GetPromocode";
    public static final String getTopProductsUrl = ipAddress + "/GetTopProducts";
    public static final String getCarouselsUrl = ipAddress + "/GetPromotionOffers";
    public static final String filterProductsUrl = ipAddress + "/FilterProducts";
    public static final String getPackageDetailsUrl = ipAddress + "/GetPackageDetails";
    public static final String getPaySuccessUrl = ipAddress + "/PaymentStatus_Options";
    public static final String getAllCategoriesUrl = ipAddress + "/GetAllCategories";
    public static final String myCartUrl = ipAddress + "/MyCartDetails";
    public static final String getLabLocationsUrl = ipAddress + "/SearchLocations_OnMap";
    public static final String getPromotionOffersUrl = ipAddress + "/GetPromotionOffers"; //Offers List
    public static final String getOrdersUrl = ipAddress + "/GetOrderHistory_Product";
    public static final String getPayUrl = ipAddress + "/CreateOrder_Guest";
    public static final String getUserDetailsUrl = ipAddress + "/Get_User"; //Profile Details
    public static final String updateUserDetails = ipAddress + "/UpdateProfile"; //Profile Update
    public static final String validatePromoUrl = ipAddress + "/ValidatePromocode";
    public static final String searchProductsUrl = ipAddress + "/Search_Test";
    public static final String getpdfUrl = ipAddress + "/GetResultReport";
    public static final String getContentUrl = ipAddress + "/GetContent";
    public static final String changePasswordURl = ipAddress + "/ChangePassword";
    public static final String TRACK_ORDERURL = ipAddress + "/TrackYourOrder";
    public static final String REPEAT_ORDERURL = ipAddress + "/RepeateOrder";
    public static final String CANCEL_ORDERURL = ipAddress + "/CancelOrder";
    public static final String getFeedbackUrl = ipAddress + "/GetFeedback"; //feedback
    public static final String getNotificationUrl = ipAddress + "/GetNotification"; //notification
    public static final String deleteNotificationUrl = ipAddress + "/DeleteNotification"; //notification
    public static final String allStatesurl = ipAddress + "/GetAllStates";
    public static final String citiesurl = ipAddress + "/GetCities";
    public static final String validatePromoUrl_cart = ipAddress + "/ValidatePromocode_CART";
    public static final String getGraphList_url = ipAddress + "/GetGraphList";
    public static final String getOTP_url = ipAddress + "/GetOTP";
    public static final String getLoginOTP_url = ipAddress + "/Validate_MobileNo";
    public static final String getresendLoginOTP_url = ipAddress + "/ResendOTP_Mobile";
    public static final String VerifyOTP_url = ipAddress + "/VerifyOTP";
    public static final String VerifyOTPLogin_url = ipAddress + "/Authenticate_MobileNo";
    public static final String Family_Member_Mapping = ipAddress + "/Family_Member_Mapping";
    public static final String Display_Family_Member = ipAddress + "/Display_Family_Member";
    public static final String getPatientDetails_url = ipAddress + "/GetPatientDetails ";
    public static final String loginUrl = /*ipAddress2*/ipAddress2 + "/Authenticate_User";
    public static final String paytmStatusurl = "http://secure.paytm.in/oltp/HANDLER_INTERNAL/TXNSTATUS";
    public static final String getUserUrl = ipAddress2 + "/Get_User";

    public static final String GetSurveyUrl = ipAddress2 + "/GetSurvey";
    public static final String getpaytmchecksum_url = ipAddress2 + "/Get_User";//change paytm checksum url
    public static String osVersion = "";
}
