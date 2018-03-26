package com.srllimited.srl.serverapis;

/**
 * Referral int for url call
 * <p>
 * Created by RuchiTiwari on 2/12/2017.
 */
public enum ApiRequestReferralCode {
    PAY(1), USER(2), LOGIN(3), ORDERS(4), GET_PDF(5), MY_CART(6), REPORTS(7), GET_PROMO(8), RESEND_OTP(9), FORGOT_PWD(
            10), OFFER_LIST(11), USER_DETAILS(12), TOP_PRODUCTS(13), LAB_LOCATIONS(14), ALL_CATEGORIES(
            15), VALIDATE_PROMO(16), SEARCH_PRODUCTS(17), FILTER_PRODUCTS(18), PACKAGE_DETAILS(
            19), USER_REGISTRATION(20), PENDING_REGISTRATION(21), UPDATE_USER_DETAILS(
            22), VALIDATE_REGISTRATION(23), VALIDATE_MOBIKWIK(24), Get_CONTENT(
            25), ChangePassword(26), TRACK_ORDER(27), REPEAT_ORDER(28), CANCEL_ORDER(
            29), GET_FEEDBACK(30), GET_CAROUSELS(31), GET_STATES(
            32), GET_CITIES(33), GET_CITY(34), GET_PRIVACY(
            35), GET_TERMS(36), GET_PAY_SUCCESS(
            37), GET_PAYTM_STATUS(38), GET_NOTIFICATION(
            39), DELETE_NOTIFICATION(
            40), CHECK_VERSION(
            41), GET_GRAPHLIST(
            42), GET_OTP(
            43), VERIFY_OTP(
            44), GET_PATIENTDETAIL(
            45), GET_LOGO(
            46), GET_CALL_US(
            47),
    GET_CONTACT_US(48),
    GET_Paytm_checksum(49),ValidateRegistration_Family(50),Get_SURVEY(51),Validate_MobileNo(52),Authenticate_MobileNo(53),Family_Member_Mapping(54),Display_Family_Member(55),ResendOTP_Mobile(56);

    private int code;

    ApiRequestReferralCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + "";
    }
}
