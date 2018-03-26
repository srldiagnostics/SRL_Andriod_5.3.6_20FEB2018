package com.srllimited.srl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerLib;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.srllimited.srl.adapters.OTPLogin_PatientDetailAdapter;
import com.srllimited.srl.adapters.PatientDetailAdapter;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.PatientDetailsData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.data.UserData;
import com.srllimited.srl.database.OrderDatabase;
import com.srllimited.srl.otp.OTPReader;
import com.srllimited.srl.serverapis.ApiConstants;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiRequestReferralCode;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.TypefaceUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.utilities.AppDataBaseHelper;
import com.srllimited.srl.utilities.MyCountDownTimer;
import com.srllimited.srl.utilities.Utility;

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
import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginScreenActivity extends MenuBaseActivity
        implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, RestApiCallUtil.VolleyCallback {
    public static Activity loginact;
    ArrayList<UserData> _userData = new ArrayList<>();
    Context context;
    //Update Priofile
    ArrayList<UserData> _updateuserData = new ArrayList<>();
    Dialog dialog;
    UserData _updateuserdataset;
    List<PatientDetailsData> patientDetailsDatasstr = new ArrayList<>();

    EditText userid_edittext, mobile_edittext;
    TextView btnotplogin;
    TextInputEditText pwd_edittext;
    boolean stateselected = false;

    MyCountDownTimer myCountDownTimer;
    Dialog alertDialog;
    //Data Base
    AppDataBaseHelper appDb = new AppDataBaseHelper(this);
    UserData _userAppData;
    OrderDatabase orderDB;
    View view = null;
    TextView enter_user_id, alert_submit, alert_heading;
    EditText edittext_user_id, radio_mobile_edittext, radio_email_edittext, radio_mobileoremail_edittext;
    ImageView alert_header_image, close_popup;
    View view1, view2;
    RadioGroup radio_group;
    RadioButton radio_mobile_no, radio_email, radio_mobileoremail, radio_not_sure;
    TextView forgot_pwd, call_request, entered_userid, landline_number;
    Button register_btn, login_btn;
    LinearLayout not_sure_view;
    RelativeLayout not_sure_call;
    String loggedin_username = "";
    String loggedin_pwd = "";
    CheckBox remember_me;
    String loggedin_remember = "";
    String fetchedMobileNo = "";
    String fetchedEmailId = "";
    Button login_as_guest_btn;
    String userid, serpassword;
    JSONObject jsonobj;
    private FirebaseAnalytics firebaseAnalytics;
    private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>() {
        @Override
        public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData) {
            Log.e("TAG12", String.valueOf(serverResponseData.getArrayData()) + " " + request.getReferralCode());
            switch (request.getReferralCode()) {
                case USER_DETAILS: {
                    setProfileData(serverResponseData.getArrayData());

                }
                break;
                case GET_LOGO: {
                    setLOGO(serverResponseData.getArrayData());
                }
                break;
                case GET_CALL_US: {
                    setCallUS(serverResponseData.getArrayData());
                }
                break;

                case LOGIN: {
                    if (serverResponseData != null /*&& serverResponse.equalsIgnoreCase("Query Successful")*/) {
                        SharedPrefsUtil.setBooleanPreference(context, "remember", false);
                        Constants.isLogin = false;
                        if (logout_text.getText().toString().equalsIgnoreCase("Login")) {
                            logout_text.setText("Logout");
                        }

                        SharedPreferences pref = getApplicationContext()
                                .getSharedPreferences(Constants.login_credentials, MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(Constants.loggedin_username, userid_edittext.getText().toString());
                        editor.putString(Constants.loggedin_pwd, pwd_edittext.getText().toString());
                        editor.putString(Constants.rememberme, remember_me.isChecked() + "");
                        editor.commit();
                        SharedPrefsUtil.setStringPreference(context, Constants.constantUsername,
                                userid_edittext.getText().toString());
                        appDb = new AppDataBaseHelper(getApplicationContext());
                        orderDB = new OrderDatabase(getApplicationContext());
                        try {
                            String userd = getData(userid_edittext.getText().toString());
                            if (userd != null && !userd.equalsIgnoreCase("null")) {
                                navigateToActivity();
                            } else {
                                sendRequest(ApiRequestHelper.getUserDetails(context, Util.getStoredUsername(context)));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }

                }
                break;

                case Validate_MobileNo: {
                    //   serverResponseData.getFullData()
                    Log.v("OTP", serverResponseData.toString());
                    if (serverResponseData.getMsg().equalsIgnoreCase("VALID")) {
                        VerifyOTP();
                        setResendVisibleAfter3Minutes();
                        startOTPReader();
                    } else {
                        //Utility.NeResponseError(LoginScreenActivity.this, "This is not a registered mobile number.Please register to continue.");
//Change as per mail from shraddha 23/03/2018

                        String msg="The Mobile Number entered is not registered.\n" +
                                "If you are an Existing user – Login using the User ID & update your Mobile Number\n" +
                                "If you are a New User – Please Register .";
                        alertDialog = new Dialog(LoginScreenActivity.this);
                        //alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        alertDialog.setCancelable(false);
                        alertDialog.setContentView(R.layout.otpvalidationalert);
                        WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
                        lWindowParams.copyFrom(alertDialog.getWindow().getAttributes());
                        lWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT; // this is where the magic happens
                        lWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        alertDialog.getWindow().setAttributes(lWindowParams);
                        TextView txtmag= (TextView) alertDialog.findViewById(R.id.txtmag);
                        Button btnok=(Button)alertDialog.findViewById(R.id.btnok);
                        txtmag.setText(msg);
                        btnok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();
                        /*Utility.LoginOTPAlert(LoginScreenActivity.this, "The Mobile Number entered is not registered.\n" +
                                "If you are an Existing user – Login using the User ID & update your Mobile Number\n" +
                                "If you are a New User – Please Register .");*/
/*
                        try
                        {
                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginScreenActivity.this);
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

                        }*/
                    }
                    // setCitiesList(serverResponseData.getFullData());
                }
                break;
                case ResendOTP_Mobile:
                    VerifyOTP();
                    setResendVisibleAfter3Minutes();
                    startOTPReader();
                    break;
                case Authenticate_MobileNo: {
                    if (serverResponseData.getMsg().equalsIgnoreCase("Query Successful")) {
                        /*sendRequest(ApiRequestHelper.getPatientDetail(context,
                                mobile_edittext.getText().toString().trim()));*/
                        JSONArray jasonarray = serverResponseData.getArrayData();
                        /*for(int i=0;i<jasonarray.length();i++)
                        {
                            JSONObject jsonobj=new JSONObject();
                            try {
                                jsonobj=jasonarray.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }*/

                        setPatientDetail(serverResponseData.getFullData());
                    } else {
                        // Toast.makeText(RegistrationScreen.this, "Entered OTP is invalid", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
                case Family_Member_Mapping:
                    if (serverResponseData.getMsg().equalsIgnoreCase("Query Successful")) {
                        //gdfgdhgf

                        SharedPrefsUtil.setBooleanPreference(context, "remember", false);
                        Constants.isLogin = false;
                        if (logout_text.getText().toString().equalsIgnoreCase("Login")) {
                            logout_text.setText("Logout");
                        }

                        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.login_credentials, MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(Constants.loggedin_username, userid);
                        editor.putString(Constants.loggedin_pwd, serpassword);
                        editor.putString(Constants.rememberme, remember_me.isChecked() + "");
                        editor.commit();
                        SharedPrefsUtil.setStringPreference(context, Constants.constantUsername,
                                userid_edittext.getText().toString());
                        appDb = new AppDataBaseHelper(getApplicationContext());
                        orderDB = new OrderDatabase(getApplicationContext());
                        JSONArray ja = new JSONArray();
                        ja.put(jsonobj);
                        setProfileData(ja);

                        try {
                            String userd = getData(userid_edittext.getText().toString());
                            if (userd != null && !userd.equalsIgnoreCase("null")) {
                                navigateToActivity();
                            } else {
                                //setOTP_ProfileData(jsonobj);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case Display_Family_Member:
                    //appDb.deleteFamilyMemberData();
                    if (serverResponseData.getMsg().equalsIgnoreCase("Query Successful")) {

                        JSONObject jsonObject = serverResponseData.getFullData();
                        JSONArray jArray = null;
                        JSONArray jArray_familymembers = null;
                        try {
                            if (!jsonObject.isNull("data")) {
                                JSONObject obj = jsonObject.getJSONObject("data");
                                Object objprimary = obj.get("PRIMARY_USER");
                                if (objprimary != null && objprimary instanceof JSONArray) {
                                    jArray = obj.getJSONArray("PRIMARY_USER");
                                }
                            }
                        } catch (Exception e) {
                        }
                        try {
                            if (!jsonObject.isNull("data")) {
                                JSONObject obj1 = jsonObject.getJSONObject("data");
                                Object objfamilly = obj1.get("FAMILY_MEMBER");
                                if (objfamilly != null && objfamilly instanceof JSONArray) {
                                    jArray_familymembers = obj1.getJSONArray("FAMILY_MEMBER");
                                }
                            }
                        } catch (Exception e) {
                        }
                        for (int i = 0; i < jArray.length(); i++) {
                            try {
                                userid = jArray.getJSONObject(i).getString(Constants.ptnt_cd);
                                serpassword = jArray.getJSONObject(i).getString(Constants.ptnt_pwd);
                                SharedPrefsUtil.setBooleanPreference(context, "remember", false);
                                Constants.isLogin = false;
                                if (logout_text.getText().toString().equalsIgnoreCase("Login")) {
                                    logout_text.setText("Logout");
                                }

                                SharedPreferences pref = getApplicationContext()
                                        .getSharedPreferences(Constants.login_credentials, MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString(Constants.loggedin_username, jArray.getJSONObject(i).getString(Constants.ptnt_cd));
                                editor.putString(Constants.loggedin_pwd, jArray.getJSONObject(i).getString(Constants.jsonFieldPtnt_pwd));
                                editor.putString(Constants.rememberme, remember_me.isChecked() + "");
                                editor.commit();
                                SharedPrefsUtil.setStringPreference(context, Constants.constantUsername,
                                        jArray.getJSONObject(i).getString(Constants.ptnt_cd));
                                appDb = new AppDataBaseHelper(getApplicationContext());
                                orderDB = new OrderDatabase(getApplicationContext());

                                try {
                                    String userd = getData(jArray.getJSONObject(i).getString(Constants.ptnt_cd));
                                    if (userd != null && !userd.equalsIgnoreCase("null")) {
                                        navigateToActivity();
                                    } else {
                                        //setOTP_ProfileData(jArray.getJSONObject(i));
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } catch (Exception e) {

                            }
                        }

                        for (int j = 0; j < jArray_familymembers.length(); j++) {
                            try {

                                UserData _userdataset = new UserData();
                                _userdataset.setUserid(jArray_familymembers.getJSONObject(j).getString("USER_ID") + "");
                                _userdataset.setPtnt_cd(jArray_familymembers.getJSONObject(j).getString("PTNT_CD") + "");
                                _userdataset.setPtnt_tittle(jArray_familymembers.getJSONObject(j).getString("PTNT_TITTLE") + "");
                                _userdataset.setFirst_name(jArray_familymembers.getJSONObject(j).getString("FIRST_NAME") + "");
                                _userdataset.setLast_name(jArray_familymembers.getJSONObject(j).getString("LAST_NAME") + "");
                                _userdataset.setGender(jArray_familymembers.getJSONObject(j).getString("GENDER") + "");
                                try {

                                    _userdataset.setDob(Long.valueOf(jArray_familymembers.getJSONObject(j).getLong("DOB")));
                                } catch (Exception e) {

                                }
                                _userdataset.setMarital_status(jArray_familymembers.getJSONObject(j).getString("MARITAL_STATUS") + "");
                                _userdataset.setEmail_id(jArray_familymembers.getJSONObject(j).getString("EMAIL_ID") + "");
                                _userdataset.setZip(jArray_familymembers.getJSONObject(j).getString("ZIP") + "");
                                _userdataset.setMobile_no(jArray_familymembers.getJSONObject(j).getString("MOBILE_NO") + "");
                                _userdataset.setAddress1(jArray_familymembers.getJSONObject(j).getString("ADDRESS1") + "");
                                _userdataset.setAddress2(jArray_familymembers.getJSONObject(j).getString("ADDRESS2") + "");
                                _userdataset.setLandmark(jArray_familymembers.getJSONObject(j).getString("LANDMARK") + "");
                                _userdataset.setCity(jArray_familymembers.getJSONObject(j).getString("CITY") + "");
                                _userdataset.setState(jArray_familymembers.getJSONObject(j).getString("STATE") + "");
                                _userdataset.setCountry(jArray_familymembers.getJSONObject(j).getString("COUNTRY") + "");
                                _userdataset.setParent_id(jArray_familymembers.getJSONObject(j).getString("PARENT_ID") + "");
                                _userdataset.setStatus("false");
                                _userdataset.setPwd(jArray_familymembers.getJSONObject(j).getString("PTNT_PWD"));
                                _userData.add(_userdataset);
                                appDb.addUserDetails(_userdataset, context);
                            } catch (Exception e) {

                            }
                        }
                        setProfileData(jArray);
                    }
                    break;
            }
        }

        @Override
        public void onResponseError(final ApiRequest request, final Exception error) {

        }
    };

    private void setPatientDetail(JSONObject jsonObject) {
        JSONArray jArray = null;
        try {
            if (!jsonObject.isNull("data")) {
                Object obj = jsonObject.get("data");
                if (obj != null && obj instanceof JSONArray) {
                    jArray = jsonObject.getJSONArray(Constants.response_data_create);
                }
            }
        } catch (Exception e) {
        }

        if (jArray != null) {
            try {
                if (Validate.notNull(jArray)) {
                    stateselected = false;
                    patientDetailsDatasstr = new ArrayList<>();
                    for (int i = 0; i < jArray.length(); i++) {
                        PatientDetailsData patientDetailsData = new PatientDetailsData();
                        patientDetailsData.setPTNT_CD(jArray.getJSONObject(i).getString(Constants.ptntCd1));
                        patientDetailsData.setPTNT_TITTLE(jArray.getJSONObject(i).getString(Constants.ptntTittle1));
                        patientDetailsData.setFIRST_NAME(jArray.getJSONObject(i).getString(Constants.firstName1));
                        patientDetailsData.setLAST_NAME(jArray.getJSONObject(i).getString(Constants.last_name1));
                        patientDetailsData.setADDRESS1(jArray.getJSONObject(i).getString(Constants.address11));
                        patientDetailsData.setADDRESS2(jArray.getJSONObject(i).getString(Constants.address12));
                        /* patientDetailsData.setCOUNTRY(jArray.getJSONObject(i).getString(Constants.country1));
                        patientDetailsData.setCITY(jArray.getJSONObject(i).getString(Constants.city1));
						patientDetailsData.setSTATE(jArray.getJSONObject(i).getString(Constants.state1))*/
                        ;
                        patientDetailsData.setZIP(jArray.getJSONObject(i).getString(Constants.zip1));
                        patientDetailsData.setMOBILE_NO(jArray.getJSONObject(i).getString(Constants.mobile_no1));
                        patientDetailsData.setEMAIL_ID(jArray.getJSONObject(i).getString(Constants.email_id1));

                        patientDetailsDatasstr.add(patientDetailsData);
                    }
                    appDb.deleteFamilyMemberData();
                    if (jsonObject.get("CHK_FMLY").toString().equalsIgnoreCase("False")) {
                       /* if(jArray.length()<=1)
                        {
                            String familymembers="";
                            for (int i = 0; i < jArray.length(); i++) {
                                try {

                                    if (jArray.getJSONObject(i).getString(Constants.ptntCd1).equalsIgnoreCase(OTPLogin_PatientDetailAdapter.patientDetailsDataobj.getPTNT_CD())) {
                                        if (jArray != null *//*&& serverResponse.equalsIgnoreCase("Query Successful")*//*) {
                                            userid = jArray.getJSONObject(i).getString(Constants.ptnt_cd);
                                            serpassword = jArray.getJSONObject(i).getString(Constants.ptnt_pwd);
                                            jsonobj = jArray.getJSONObject(i);
                                            sendRequest(ApiRequestHelper.Family_Member_Mapping(context, jArray.getJSONObject(i).getString(Constants.ptnt_cd),
                                                    familymembers, "Android", Constants.devicetobepassed, jArray.getJSONObject(i).getString(Constants.mobile_no)));

                                        } else {
                                            Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                        }


                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }*/
                        //else {
                            getPatientDetail(patientDetailsDatasstr, jArray);
                        //}
                    } else {
                        sendRequest(ApiRequestHelper.Display_Family_Member(context, "Android", Constants.devicetobepassed, mobile_edittext.getText().toString()));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void getPatientDetail(List<PatientDetailsData> patientDetailsDatas, final JSONArray jArray) {
        // Create custom dialog object
        // List<PatientDetailsData> patientDetailsDatas= new ArrayList<>();
        dialog = new Dialog(LoginScreenActivity.this);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.patient_detailspopup_otplogin);
        dialog.setCancelable(false);
        // Set dialog title
        // dialog.setTitle("Custom Dialog");
        //

        // set values for custom dialog components - text, image and button
        //final EditText edtotp = (EditText) dialog.findViewById(R.id.edtotp);
        final TextView txtmsg = (TextView) dialog.findViewById(R.id.txtmsg);
        final RecyclerView recycler_view = (RecyclerView) dialog.findViewById(R.id.recycler_view);

        txtmsg.setText("Following are the User Id(s) registered with mobile no " + mobile_edittext.getText().toString()
                + ".\nPlease select Primary ID from below list.");
        recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycler_view.setHasFixedSize(true);

		/*patientDetailsDatas = new ArrayList<PatientDetailsData> ();
		for(int i=0;i<30;i++) {
		    PatientDetailsData patientDetailsData1 = new PatientDetailsData();
		    patientDetailsData1.setPTNT_CD("PRIYAF1233234");
		    patientDetailsData1.setFIRST_NAME("PREETY");
		    patientDetailsData1.setLAST_NAME("ANATRGHBMGEDfgeregfg");
		    patientDetailsDatas.add(patientDetailsData1);
		}*/
        OTPLogin_PatientDetailAdapter mAdapter = new OTPLogin_PatientDetailAdapter(context, patientDetailsDatas);
        recycler_view.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
        Button btnNo = (Button) dialog.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.killReg();
                Intent intent = new Intent(LoginScreenActivity.this, Dashboard.class);
                startActivity(intent);
                finish();

            }
        });

        // if decline button is clicked, close the custom dialog
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                dialog.dismiss();

                if (OTPLogin_PatientDetailAdapter.selectionflag) {
                    String familymembers = "";
                    //appDb.deleteFamilyMemberData();
                    for (int i = 0; i < jArray.length(); i++) {
                        try {
                            if (!jArray.getJSONObject(i).getString(Constants.ptntCd1).equalsIgnoreCase(OTPLogin_PatientDetailAdapter.patientDetailsDataobj.getPTNT_CD())) {
                                familymembers = familymembers + jArray.getJSONObject(i).getString(Constants.ptnt_cd) + ",";
                                UserData _userdataset = new UserData();
                                _userdataset.setUserid(jArray.getJSONObject(i).getString("USER_ID") + "");
                                _userdataset.setPtnt_cd(jArray.getJSONObject(i).getString("PTNT_CD") + "");
                                _userdataset.setPtnt_tittle(jArray.getJSONObject(i).getString("PTNT_TITTLE") + "");
                                _userdataset.setFirst_name(jArray.getJSONObject(i).getString("FIRST_NAME") + "");
                                _userdataset.setLast_name(jArray.getJSONObject(i).getString("LAST_NAME") + "");
                                _userdataset.setGender(jArray.getJSONObject(i).getString("GENDER") + "");
                                try {

                                    _userdataset.setDob(Long.valueOf(jArray.getJSONObject(i).getLong("DOB")));
                                } catch (Exception e) {

                                }
                                _userdataset.setMarital_status(jArray.getJSONObject(i).getString("MARITAL_STATUS") + "");
                                _userdataset.setEmail_id(jArray.getJSONObject(i).getString("EMAIL_ID") + "");
                                _userdataset.setZip(jArray.getJSONObject(i).getString("ZIP") + "");
                                _userdataset.setMobile_no(jArray.getJSONObject(i).getString("MOBILE_NO") + "");
                                _userdataset.setAddress1(jArray.getJSONObject(i).getString("ADDRESS1") + "");
                                _userdataset.setAddress2(jArray.getJSONObject(i).getString("ADDRESS2") + "");
                                _userdataset.setLandmark(jArray.getJSONObject(i).getString("LANDMARK") + "");
                                _userdataset.setCity(jArray.getJSONObject(i).getString("CITY") + "");
                                _userdataset.setState(jArray.getJSONObject(i).getString("STATE") + "");
                                _userdataset.setCountry(jArray.getJSONObject(i).getString("COUNTRY") + "");
                                _userdataset.setParent_id(jArray.getJSONObject(i).getString("PARENT_ID") + "");
                                _userdataset.setStatus("false");
                                _userdataset.setPwd(jArray.getJSONObject(i).getString("PTNT_PWD"));
                                _userData.add(_userdataset);
                                appDb.addUserDetails(_userdataset, context);
                            }

                        } catch (Exception e) {
                        }
                    }
                    if (!familymembers.equalsIgnoreCase("")) {
                        familymembers = familymembers.substring(0, familymembers.length() - 1);
                    }
                    for (int i = 0; i < jArray.length(); i++) {
                        try {


                            if (jArray.getJSONObject(i).getString(Constants.ptntCd1).equalsIgnoreCase(OTPLogin_PatientDetailAdapter.patientDetailsDataobj.getPTNT_CD())) {
                                if (jArray != null /*&& serverResponse.equalsIgnoreCase("Query Successful")*/) {
                                    userid = jArray.getJSONObject(i).getString(Constants.ptnt_cd);
                                    serpassword = jArray.getJSONObject(i).getString(Constants.ptnt_pwd);
                                    jsonobj = jArray.getJSONObject(i);
                                    sendRequest(ApiRequestHelper.Family_Member_Mapping(context, jArray.getJSONObject(i).getString(Constants.ptnt_cd),
                                            familymembers, "Android", Constants.devicetobepassed, jArray.getJSONObject(i).getString(Constants.mobile_no)));

                                } else {
                                    Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                }


                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.addContentView(R.layout.login_screen);
        context = LoginScreenActivity.this;
        loginact = this;
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.login_credentials, 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        loggedin_username = pref.getString(Constants.loggedin_username, null);
        loggedin_pwd = pref.getString(Constants.loggedin_pwd, null);

        loggedin_remember = pref.getString(Constants.rememberme, "false");
        orderDB = new OrderDatabase(getApplicationContext());
        //               if (loggedin_username != null && !loggedin_username.isEmpty() && loggedin_remember != null && loggedin_remember.equalsIgnoreCase("false")) {
        //            Util.killDashBoard();
        //            Intent intent = new Intent(LoginScreenActivity.this, Dashboard.class);
        //            startActivity(intent);
        //            finish();
        //        }

        // Obtain the Firebase Analytics instance.
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, 1);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "LoginScreen");
        //Logs an app event.
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        //Sets whether analytics collection is enabled for this app on this device.
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);

        //App Flyer
        // AppsFlyerLib.getInstance().enableUninstallTracking(Constants.FCM_sender_id); // ADD THIS LINE HERE
        AppsFlyerLib.getInstance().startTracking(this.getApplication(), Constants.APP_FLYER_KEY);

        Map<String, Object> eventValue = new HashMap<String, Object>();
        eventValue.put(AFInAppEventParameterName.CITY, "");
        eventValue.put(AFInAppEventParameterName.SCORE, 100);
        AppsFlyerLib.getInstance().trackEvent(context, "LoginScreen", eventValue);

        //Facebook Analytic
        AppEventsLogger logger = AppEventsLogger.newLogger(this);
        logger.logEvent("LoginScreen");

        // Constants.isLogin = true;

        final String userid = SharedPrefsUtil.getStringPreference(context, Constants.loggedin_username);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        userid_edittext = (EditText) findViewById(R.id.userid_edittext);
        remember_me = (CheckBox) findViewById(R.id.remember_me);
        pwd_edittext = (TextInputEditText) findViewById(R.id.pwd_edittext);
        login_as_guest_btn = (Button) findViewById(R.id.login_as_guest_btn);
        forgot_pwd = (TextView) findViewById(R.id.forgot_pwd);
        register_btn = (Button) findViewById(R.id.register_btn);
        login_btn = (Button) findViewById(R.id.login_btn);

        mobile_edittext = (EditText) findViewById(R.id.mobile_edittext);
        btnotplogin = (TextView) findViewById(R.id.btnotplogin);

        header_loc_name.setText("Login");
        header_loc_name.setEnabled(false);

        TypefaceUtil.setTypeFace(context, TypefaceUtil.openSans_SemiBold, header_loc_name);

        if (loggedin_remember != null && loggedin_remember.equalsIgnoreCase("true")) {
            remember_me.setChecked(true);
            userid_edittext.setText(loggedin_username);
            pwd_edittext.setText(loggedin_pwd);
        }

        userid_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    pwd_edittext.setBackgroundResource(R.drawable.edittext_modify_states);
                    userid_edittext.setBackgroundResource(R.drawable.bottom_edittext_line);
                } else {
                    userid_edittext.setBackgroundResource(R.drawable.edittext_modify_states);
                }
            }
        });

        pwd_edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    userid_edittext.setBackgroundResource(R.drawable.edittext_modify_states);

                    pwd_edittext.setBackgroundResource(R.drawable.bottom_edittext_line);
                } else {
                    pwd_edittext.setBackgroundResource(R.drawable.edittext_modify_states);
                }

            }
        });

        //-----Popup on clicking forgot password
        alertDialog = new Dialog(this);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setCancelable(false);
        alertDialog.setContentView(R.layout.forgot_pwd_alert_dialog);

        //-----Views in forgot password popup
        alert_heading = (TextView) alertDialog.findViewById(R.id.alert_heading);
        enter_user_id = (TextView) alertDialog.findViewById(R.id.enter_user_id);
        call_request = (TextView) alertDialog.findViewById(R.id.call_request);
        entered_userid = (TextView) alertDialog.findViewById(R.id.entered_userid);
        not_sure_call = (RelativeLayout) alertDialog.findViewById(R.id.not_sure_call);
        landline_number = (TextView) alertDialog.findViewById(R.id.landline_number);
        not_sure_view = (LinearLayout) alertDialog.findViewById(R.id.not_sure_view);
        alert_submit = (TextView) alertDialog.findViewById(R.id.alert_submit);
        edittext_user_id = (EditText) alertDialog.findViewById(R.id.edittext_user_id);
        radio_mobile_edittext = (EditText) alertDialog.findViewById(R.id.radio_mobile_edittext);
        radio_email_edittext = (EditText) alertDialog.findViewById(R.id.radio_email_edittext);
        radio_mobileoremail_edittext = (EditText) alertDialog.findViewById(R.id.radio_mobileoremail_edittext);
        alert_header_image = (ImageView) alertDialog.findViewById(R.id.alert_header_image);
        close_popup = (ImageView) alertDialog.findViewById(R.id.close_popup);
        view1 = (View) alertDialog.findViewById(R.id.view1);
        view2 = (View) alertDialog.findViewById(R.id.view2);
        radio_mobile_no = (RadioButton) alertDialog.findViewById(R.id.radio_mobile_no);
        radio_email = (RadioButton) alertDialog.findViewById(R.id.radio_email);
        radio_mobileoremail = (RadioButton) alertDialog.findViewById(R.id.radio_mobileoremail);
        radio_not_sure = (RadioButton) alertDialog.findViewById(R.id.radio_not_sure);
        radio_group = (RadioGroup) alertDialog.findViewById(R.id.radio_group);

        login_as_guest_btn.setOnClickListener(this);
        forgot_pwd.setOnClickListener(this);
        alert_submit.setOnClickListener(this);
        radio_group.setOnCheckedChangeListener(this);
        register_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        close_popup.setOnClickListener(this);

        edittext_user_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edittext_user_id.setBackgroundResource(R.drawable.edittext_border);
                } else {
                    edittext_user_id.setBackgroundResource(R.color.lightgrey);
                }

            }
        });

        if (userid != null && !userid.isEmpty()) {
            userid_edittext.setText(userid);
            userid_edittext.setEnabled(false);
            userid_edittext.setBackgroundResource(R.color.lightgrey);
        }

        //
        //        if (logout_text.getText().toString().equalsIgnoreCase("Logout")) {
        //			loggedin_userid.setText(loggedin_username);
        clearImage();
        loggedin_userid.setText("Guest");
        logout_text.setText("Login");
        //        } else {
        //            logout_text.setText("Logout");
        //        }

        if (remember_me.isChecked()) {
            clearImage();
            loggedin_userid.setText("Guest");
            logout_text.setText("Login");
        }
        btnotplogin.setOnClickListener(this);
        logout_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }

                if (logout_text.getText().toString().equalsIgnoreCase("Login")) {
                    SharedPrefsUtil.setBooleanPreference(context, "remember", true);

                    if (!userid_edittext.getText().toString().trim().isEmpty()
                            && !pwd_edittext.getText().toString().trim().isEmpty()) {

                        SharedPreferences pref = getApplicationContext()
                                .getSharedPreferences(Constants.login_credentials, MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(Constants.loggedin_username, "");

                        String tempUserId = userid_edittext.getText().toString();
                        if (tempUserId != null && !tempUserId.isEmpty()) {
                            myFamilyDataBaseDeltion(tempUserId);
                        }

                        Map<String, String> params = new HashMap<String, String>();
                        params.put(Constants.ptntcode, userid_edittext.getText().toString());
                        params.put(Constants.pwd, pwd_edittext.getText().toString());

                        RestApiCallUtil.postServerResponse(context, ApiRequestReferralCode.LOGIN, params);

                    } else {
                        if (!userid_edittext.getText().toString().isEmpty()) {
                            if (pwd_edittext.getText().toString().isEmpty()) {
                                Toast.makeText(context, "Please enter password", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Please enter user name", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else if (logout_text.getText().toString().equalsIgnoreCase("Logout")) {

                    AlertDialog alert;

                    android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(
                            LoginScreenActivity.this, android.app.AlertDialog.THEME_HOLO_LIGHT);
                    builder.setMessage("Are you sure you want to logout?").setCancelable(false)
                            .setPositiveButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    if (dialog != null)
                                        dialog.dismiss();
                                }
                            }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPrefsUtil.setStringPreference(context, "fourdigitpin", "");
                            SharedPrefsUtil.setBooleanPreference(context, "splashpin", false);
                            SharedPrefsUtil.setBooleanPreference(context, "remember", false);
                            loggedin_userid.setText("");
                            clearImage();
                            logout_text.setText("Login");
                            if (remember_me.isChecked()) {

                                loggedin_remember = "true";
                            } else
                                loggedin_remember = "false";
                            if (loggedin_remember != null && loggedin_remember.equalsIgnoreCase("true")) {
                                Log.e("already", "logged in");
                            } else {
                                clearImage();
                                loggedin_userid.setText("Guest");
                                logout_text.setText("Login");
                                SharedPreferences pref = getApplicationContext()
                                        .getSharedPreferences(Constants.login_credentials, MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString(Constants.loggedin_username, "");
                                editor.putString(Constants.loggedin_pwd, "");
                                editor.putString(Constants.rememberme, "false");
                                //							SharedPrefsUtil.setStringPreference(context, Constants.constantUsername, "");
                                editor.commit();
                            }

                            Util.killLogin();
                            Intent aboutintent = new Intent(LoginScreenActivity.this,
                                    LoginScreenActivity.class);
                            startActivity(aboutintent);
                        }
                    });

                    alert = builder.create();
                    alert.show();
                }
            }
        });
    }

    public EditText edtotp;
    public Button resend;
    private OTPReader mOtpReader;
    int time = 0;
    private static final String sPATTERN = "OTP to verify your SRL DIAGNOSTICS user account is ";

    private void startOTPReader() {
        if (mOtpReader != null) {
            mOtpReader.stop();
        }

        mOtpReader = new OTPReader(this, sPATTERN, new OTPReader.OnOTPListener() {
            @Override
            public void onProcess() {
                RestApiCallUtil.showProgressDialog(context);
                if (RestApiCallUtil.mProgressDialog != null) {
                    RestApiCallUtil.mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mOtpReader.stop();
                                    dialog.dismiss();
                                }
                            });
                }
            }

            @Override
            public void onComplete(boolean isReceived, String message) {

                mOtpReader.stop();
                RestApiCallUtil.hideProgressDialog();

                if (isReceived) {
                    edtotp.setText(message.replace(sPATTERN, "").trim());
                }
            }
        });
        mOtpReader.start();
        RestApiCallUtil.showProgressDialog(LoginScreenActivity.this);
        myCountDownTimer = new MyCountDownTimer(30000, 1000);
        myCountDownTimer.start();
    }

    private void setResendVisibleAfter3Minutes() {
        final Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (time == 3) {
                            resend.setVisibility(View.VISIBLE);
                            t.cancel();
                            return;
                        }

                        time++;

                    }
                });
            }
        }, 0, 60000);
    }


    private void VerifyOTP() {
        // Create custom dialog object
        dialog = new Dialog(LoginScreenActivity.this);
        // Include dialog.xml file
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.otp_dialog);
        //dialog.setCancelable(false);
        // Set dialog title
        // dialog.setTitle("Custom Dialog");
        // set values for custom dialog components - text, image and button
        edtotp = (EditText) dialog.findViewById(R.id.edtotp);
        TextView otptext = (TextView) dialog.findViewById(R.id.otptext);
        otptext.setText("Please provide OTP sent on +91" + mobile_edittext.getText().toString());


        WindowManager.LayoutParams lWindowParams = new WindowManager.LayoutParams();
        lWindowParams.copyFrom(dialog.getWindow().getAttributes());
        lWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT; // this is where the magic happens
        lWindowParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lWindowParams);
        dialog.show();// I was told to call show first I am not sure if this it to cause layout to happen so that we can override width?

        //dialog.show();

        Button btnoptVerify = (Button) dialog.findViewById(R.id.btnoptVerify);
        resend = (Button) dialog.findViewById(R.id.resend);
        resend.setVisibility(View.GONE);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.IN_DEVICE_ID, Constants.devicetobepassed);

                //                String params = "?"+Constants.IN_DEVICE_ID+"="+Constants.devicetobepassed+"";
                RestApiCallUtil.postServerResponse(context, ApiRequestReferralCode.RESEND_OTP, params);*/
                dialog.dismiss();
                sendRequest(ApiRequestHelper.getresendOTP_Login(context, mobile_edittext.getText().toString().trim()));
                //dialog.dismiss();
            }
        });
		/* Button btnclose = (Button) dialog.findViewById(R.id.btnclose);
		btnclose.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		        dialog.dismiss();
		    }
		});*/
        // if decline button is clicked, close the custom dialog
        btnoptVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                if (!(TextUtils.isEmpty(edtotp.getText().toString()))
                        && TextUtils.isDigitsOnly(edtotp.getText().toString()))
                    sendRequest(ApiRequestHelper.verifyOTPLogin(context, mobile_edittext.getText().toString().trim(),
                            edtotp.getText().toString().trim(), "Android", Constants.devicetobepassed));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_as_guest_btn:
                SharedPrefsUtil.setStringPreference(context, Constants.constantUsername,
                        userid_edittext.getText().toString());
                int navigationcount = 0;
                navigationcount = SharedPrefsUtil.getIntegerPreference(LoginScreenActivity.this,
                        Constants.sharedPreferenceSelectedLoginActivity, 0);

                switch (navigationcount) {
                    case Constants.m_cart:
                        Constants.isRegEdited = false;
                        Constants.isPatientDetails = true;
                        Util.killReg();
                        Intent intent = new Intent(LoginScreenActivity.this, RegistrationScreen.class);
                        startActivity(intent);
                        finish();
                        break;
                    case Constants.m_lab:
                        Util.killReg();
                        Constants.isRegEdited = false;
                        Constants.isPatientDetails = true;
                        Intent intenti = new Intent(LoginScreenActivity.this, RegistrationScreen.class);
                        startActivity(intenti);
                        finish();
                        break;

                    case Constants.m_dashboard:
                        Util.killDashBoard();
                        Intent intent1 = new Intent(LoginScreenActivity.this, Dashboard.class);
                        startActivity(intent1);
                        finish();
                        break;
                    default:
                        finish();
                        break;
                }
                break;

            case R.id.login_btn:
                if (!userid_edittext.getText().toString().isEmpty() && !pwd_edittext.getText().toString().isEmpty()) {

                    SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.login_credentials,
                            MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Constants.loggedin_username, "");

                    String tempUserId = userid_edittext.getText().toString();
                    if (tempUserId != null && !tempUserId.isEmpty()) {
                        myFamilyDataBaseDeltion(tempUserId);
                    }

                    Map<String, String> params = new HashMap<String, String>();
                    params.put(Constants.ptntcode, userid_edittext.getText().toString());
                    params.put(Constants.pwd, pwd_edittext.getText().toString());
                    // sendRequest(ApiRequestHelper.getLogin(userid_edittext.getText().toString(),pwd_edittext.getText().toString()));
                    RestApiCallUtil.postServerResponse(context, ApiRequestReferralCode.LOGIN, params);

                } else {
                    if (!userid_edittext.getText().toString().isEmpty()) {
                        if (pwd_edittext.getText().toString().isEmpty()) {
                            Toast.makeText(context, "Please enter password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Please enter user name", Toast.LENGTH_SHORT).show();
                    }
                }

                //////////////////////////////////////////////////////////////
                /* Map<String, String> params1 = new HashMap<String, String>();
				params1.put(Constants.ptntcode, userid_edittext.getText().toString());
				params1.put(Constants.pwd, pwd_edittext.getText().toString());
				sendRequest(ApiRequestHelper.getMyLogin(params1));*/
                //////////////////////////////////////////////////////////////////

                break;
            case R.id.register_btn:
                //                Intent intentreg = new Intent(LoginScreenActivity.this, RegistrationScreen.class);
                //                startActivity(intentreg);
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.deviceID, Constants.devicetobepassed);
                RestApiCallUtil.postServerResponse(context, ApiRequestReferralCode.PENDING_REGISTRATION, params);

                break;
            case R.id.forgot_pwd:
                alertDialog.show();
                //				if (!userid_edittext.getText().toString().isEmpty())
                //				{
                //					confirmForgotPwdPopup(v);
                //					edittext_user_id.setText(userid_edittext.getText());
                //				}
                //				else
                //				{
                edittext_user_id.setText(userid_edittext.getText());
                submitForgotPwdPopup();
                //				}
                break;
            case R.id.alert_submit:

                if (validatepopupUserId()) {

                    if (radio_group.getVisibility() == View.GONE && alert_submit.getText().toString().equals("Submit")) {
                        if (!edittext_user_id.getText().toString().isEmpty()) {
                            view = v;
                            Map<String, String> getUserParams = new HashMap<String, String>();
                            getUserParams.put(Constants.ptntcode, edittext_user_id.getText().toString());
                            //  getUserParams.put("strToken", "bb5f7b15a7829ff7599e814eeb3d520d");
                            RestApiCallUtil.postServerResponse(context, ApiRequestReferralCode.USER, getUserParams);

                        } else {
                            Toast.makeText(context, "Please enter User Id", Toast.LENGTH_SHORT).show();
                        }

                    }
					/* if (radio_group.getVisibility() == View.GONE && alert_submit.getText().toString().equals("SUBMIT")) {
					    Map<String, String> userparams = new HashMap<String, String>();
					    userparams.put(Constants.IN_USER_ID, edittext_user_id.getText().toString());
					    RestApiCallUtil.postServerResponse(context, ApiRequestReferralCode.FORGOT_PWD, userparams);
					}
					*/
                    else if (alert_submit.getText().toString().equalsIgnoreCase("Call now")) {
                        alertDialog.dismiss();

                        Util.handleTaskWithUserPermission(context, Util.PHONE_REQUES_CODE, "1800-222-000");
                    }
                    //					else if (radio_group.getVisibility() == View.GONE && alert_submit.getText().toString().equalsIgnoreCase("Submit"))
                    //					{
                    //						confirmForgotPwdPopup(v);
                    //						alert_submit.setText("Confirm");
                    //					}
                    else {

                        if (radio_not_sure.isChecked()) {
                            notsurePopup(v);
                        } else if (validateSelectedRadioEmail()) {
                            Map<String, String> userparams = new HashMap<String, String>();
                            userparams.put(Constants.IN_USER_ID, edittext_user_id.getText().toString());
                            RestApiCallUtil.postServerResponse(context, ApiRequestReferralCode.FORGOT_PWD, userparams);
                        }
                        //                            alertDialog.dismiss();
                    }
                }
                break;

            case R.id.close_popup:
                edittext_user_id.setText("");
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                break;

            case R.id.btnotplogin:
if(mobile_edittext.getText().toString().length()==10) {
    sendRequest(ApiRequestHelper.getOTP_Login(context, mobile_edittext.getText().toString().trim()));
}
else
{
    Toast.makeText(context, "Please enter valid mobile no.", Toast.LENGTH_SHORT).show();
}

                break;
        }
    }

    private boolean validatepopupUserId() {
        boolean isUserIdEntered = false;
        if (edittext_user_id.getText().toString().isEmpty()) {
            isUserIdEntered = false;
            Toast.makeText(context, "Please enter user id", Toast.LENGTH_SHORT).show();
        } else {
            isUserIdEntered = true;
        }

        return isUserIdEntered;
    }

    //	private void confirmForgotPwdPopup(View v)
    //	{
    //		hideSoftKeyboard(v);
    //		alert_heading.setText("Forgot Your Password?");
    //		not_sure_call.setVisibility(View.GONE);
    //		alert_header_image.setVisibility(View.GONE);
    //		alert_heading.setVisibility(View.VISIBLE);
    //		alert_submit.setVisibility(View.VISIBLE);
    //		alert_submit.setText("Confirm");
    //		enter_user_id.setVisibility(View.VISIBLE);
    //		edittext_user_id.setVisibility(View.VISIBLE);
    //		radio_group.setVisibility(View.GONE);
    //		alert_submit.setText("Confirm");
    //		view1.setVisibility(View.VISIBLE);
    //		view2.setVisibility(View.VISIBLE);
    //		not_sure_view.setVisibility(View.GONE);
    //	}

    private void submitForgotPwdPopup() {
        enter_user_id.setVisibility(View.VISIBLE);
        alert_heading.setText("Forgot Password?");
        radio_group.clearCheck();
        not_sure_call.setVisibility(View.GONE);
        alert_header_image.setVisibility(View.GONE);
        alert_heading.setVisibility(View.VISIBLE);
        alert_submit.setVisibility(View.VISIBLE);
        alert_submit.setText("Submit");
        if (edittext_user_id.getText() != null && !edittext_user_id.getText().toString().isEmpty()) {
            enter_user_id.setText("User Id");
        } else {
            enter_user_id.setText("Enter User Id");
        }

        edittext_user_id.setVisibility(View.VISIBLE);
        radio_group.setVisibility(View.GONE);
        view1.setVisibility(View.VISIBLE);
        view2.setVisibility(View.VISIBLE);
        not_sure_view.setVisibility(View.GONE);
    }

    private void optionsForgotPwdPopup(View v) {
        hideSoftKeyboard(v);
        radio_mobile_no.setChecked(false);
        radio_email.setChecked(false);
        radio_mobileoremail.setChecked(false);
        radio_not_sure.setChecked(false);
        radio_mobile_edittext.setVisibility(View.GONE);
        radio_email_edittext.setVisibility(View.GONE);
        radio_mobileoremail_edittext.setVisibility(View.GONE);
        not_sure_call.setVisibility(View.GONE);
        alert_header_image.setVisibility(View.VISIBLE);
        alert_heading.setVisibility(View.VISIBLE);
        view1.setVisibility(View.GONE);
        alert_heading.setText("Where would you like to receive your password?");
        alert_submit.setVisibility(View.GONE);
        enter_user_id.setVisibility(View.GONE);
        edittext_user_id.setVisibility(View.GONE);
        radio_group.setVisibility(View.VISIBLE);
        alert_submit.setText("Submit");
        view2.setVisibility(View.GONE);
        not_sure_view.setVisibility(View.GONE);
    }

    private void radioClickedAction() {
        radio_email_edittext.setVisibility(View.GONE);
        radio_mobile_edittext.setVisibility(View.GONE);
        radio_mobileoremail_edittext.setVisibility(View.GONE);
        view2.setVisibility(View.VISIBLE);
        alert_submit.setVisibility(View.VISIBLE);
        radio_email_edittext.setText("");
        radio_mobile_edittext.setText("");
        radio_mobileoremail_edittext.setText("");
    }

    private void notsurePopup(View v) {
        hideSoftKeyboard(v);
        String callus = SharedPrefsUtil.getStringPreference(LoginScreenActivity.this, Constants.CALLUS);
		/*  if (!(callus.equalsIgnoreCase("null"))) {
		    landline_number.setText(callus);
		   }
		else{*/
        sendRequest(ApiRequestHelper.getCALL_US(context, "CALL_US", ApiConstants.appVersion));
        // }
        not_sure_call.setVisibility(View.VISIBLE);
        alert_header_image.setVisibility(View.VISIBLE);
        alert_heading.setVisibility(View.GONE);

        view1.setVisibility(View.GONE);
        alert_submit.setVisibility(View.VISIBLE);
        not_sure_view.setVisibility(View.VISIBLE);
        enter_user_id.setVisibility(View.GONE);
        edittext_user_id.setVisibility(View.GONE);
        radio_group.setVisibility(View.GONE);
        alert_submit.setText("Call now");
        entered_userid.setText("User Id : " + edittext_user_id.getText() + "");
        view2.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {

            case R.id.radio_mobile_no:
                radioClickedAction();
                view2.setVisibility(View.VISIBLE);
                alert_submit.setVisibility(View.VISIBLE);
                radio_mobile_edittext.setVisibility(View.VISIBLE);
                break;
            case R.id.radio_email:
                radioClickedAction();
                view2.setVisibility(View.VISIBLE);
                alert_submit.setVisibility(View.VISIBLE);
                radio_email_edittext.setVisibility(View.VISIBLE);
                break;
            case R.id.radio_mobileoremail:
                radioClickedAction();
                view2.setVisibility(View.VISIBLE);
                alert_submit.setVisibility(View.VISIBLE);
                radio_mobileoremail_edittext.setVisibility(View.VISIBLE);
                break;
            case R.id.radio_not_sure:
                radioClickedAction();
                break;
        }
    }

    private boolean validateSelectedRadioEmail() {
        boolean isSelectedRadioEditEntered = false;
        if (radio_mobile_edittext.getVisibility() == View.VISIBLE) {
            if (radio_mobile_edittext.getText().toString().isEmpty()) {
                Toast.makeText(context, "Please enter mobile number", Toast.LENGTH_SHORT).show();
            } else {
                if (radio_mobile_edittext.getText().toString().equalsIgnoreCase(fetchedMobileNo)) {
                    isSelectedRadioEditEntered = true;
                } else {
                    Toast.makeText(context, "Mobile number is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (radio_email_edittext.getVisibility() == View.VISIBLE) {
            if (radio_email_edittext.getText().toString().isEmpty()) {
                Toast.makeText(context, "Please enter email", Toast.LENGTH_SHORT).show();
            } else {
                if (radio_email_edittext.getText().toString().toLowerCase().trim()
                        .equalsIgnoreCase(fetchedEmailId.toLowerCase().trim())) {
                    isSelectedRadioEditEntered = true;
                } else {
                    Toast.makeText(context, "Email id is incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (radio_mobileoremail_edittext.getVisibility() == View.VISIBLE) {
            if (radio_mobileoremail_edittext.getText().toString().isEmpty()) {
                Toast.makeText(context, "Please enter mobile/email number", Toast.LENGTH_SHORT).show();
            } else {
                if (radio_mobileoremail_edittext.getText().toString().toLowerCase().trim()
                        .equalsIgnoreCase(fetchedEmailId.toLowerCase().trim())
                        || radio_mobileoremail_edittext.getText().toString().equalsIgnoreCase(fetchedMobileNo)) {
                    isSelectedRadioEditEntered = true;
                } else {
                    Toast.makeText(context, "emailid/mobileno is incorrect", Toast.LENGTH_SHORT).show();
                }
            }

        }

        return isSelectedRadioEditEntered;
    }

    public void hideSoftKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (v == null) {
            v = new View(context);
        }
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onSuccessResponse(ApiRequestReferralCode referralCode, String serverResponse,
                                  HashMap<String, String> fetchValue) {

        switch (referralCode) {

            case UPDATE_USER_DETAILS:

                break;

            case PENDING_REGISTRATION:
                if (serverResponse.equalsIgnoreCase("Pending Registration")) {

                    if (fetchValue != null && fetchValue.get("mobile") != null) {
                        Util.killOtpReg();
                        Intent intent = new Intent(LoginScreenActivity.this, OTPRegistration.class);
                        intent.putExtra(Constants.registered_mobile, fetchValue.get("mobile") + "");
                        startActivity(intent);
                    } else {
                        Util.killReg();
                        Constants.isRegEdited = false;
                        Constants.isPatientDetails = false;
                        Intent intent = new Intent(LoginScreenActivity.this, RegistrationScreen.class);
                        startActivity(intent);
                    }
                } else {

                    Constants.isRegEdited = false;
                    Constants.isPatientDetails = false;
                    Util.killReg();
                    Intent intent = new Intent(LoginScreenActivity.this, RegistrationScreen.class);
                    startActivity(intent);

                }
                break;

            case LOGIN:
                if (serverResponse != null && serverResponse.equalsIgnoreCase("Query Successful")) {
                    SharedPrefsUtil.setBooleanPreference(context, "remember", false);
                    Constants.isLogin = false;
                    if (logout_text.getText().toString().equalsIgnoreCase("Login")) {
                        logout_text.setText("Logout");
                    }

                    SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.login_credentials,
                            MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(Constants.loggedin_username, userid_edittext.getText().toString());
                    editor.putString(Constants.loggedin_grp_id, fetchValue.get(Constants.GRP_ID));
                    editor.putString(Constants.loggedin_pwd, pwd_edittext.getText().toString());
                    editor.putString(Constants.rememberme, remember_me.isChecked() + "");
                    editor.commit();
                    SharedPrefsUtil.setStringPreference(context, Constants.constantUsername,
                            userid_edittext.getText().toString());
                    SharedPrefsUtil.setStringPreference(context, Constants.loggedin_mobile,
                            fetchValue.get(Constants.jsonFieldMOBILE));
                    SharedPrefsUtil.setStringPreference(context, Constants.loggedin_emailid,
                            fetchValue.get(Constants.jsonFieldEmail));

                    appDb = new AppDataBaseHelper(getApplicationContext());
                    orderDB = new OrderDatabase(getApplicationContext());
                    try {
                        String userd = getData(userid_edittext.getText().toString());
                        sendRequest(ApiRequestHelper.getLOGO(context, "LOGO", ApiConstants.appVersion));
                        sendRequest(ApiRequestHelper.getCALL_US(context, "CALL_US", ApiConstants.appVersion));

                        if (userd != null && !userd.equalsIgnoreCase("null")) {
                            int navigationcount = SharedPrefsUtil.getIntegerPreference(LoginScreenActivity.this,
                                    Constants.sharedPreferenceSelectedLoginActivity, 0);
                            navigateToActivity();
                        } else {
                            sendRequest(ApiRequestHelper.getUserDetails(context, Util.getStoredUsername(context)));

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }

                break;

            case USER:
                SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.login_credentials,
                        MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                if (fetchValue != null && fetchValue.get(Constants.jsonFieldMOBILE) != null) {
                    fetchedMobileNo = fetchValue.get(Constants.jsonFieldMOBILE);
                    editor.putString(Constants.loggedin_username, fetchValue.get(Constants.jsonFieldPtnt_cd));
                    editor.putString(Constants.loggedin_grp_id, fetchValue.get(Constants.GRP_ID));
                    editor.putString(Constants.rememberme, remember_me.isChecked() + "");
                    editor.commit();

                    String mappedMobile = fetchValue.get(Constants.jsonFieldMOBILE);
                    String tempMob = "";
                    if (mappedMobile != null && !mappedMobile.equalsIgnoreCase("null") && mappedMobile.length() > 3) {
                        tempMob = mappedMobile.substring(3, mappedMobile.length() - 3);
                    }
                    String valToReplace = "";
                    for (int i = 0; i < tempMob.length(); i++) {
                        valToReplace = valToReplace + "X";
                    }

                    mappedMobile = mappedMobile.replace(tempMob, valToReplace);

                    radio_mobile_no.setText("To my registered mobile number " + mappedMobile);
                }
                if (fetchValue != null && fetchValue.get(Constants.jsonFieldEmail) != null) {
                    fetchedEmailId = fetchValue.get(Constants.jsonFieldEmail);
                    String mappedEmail = fetchValue.get(Constants.jsonFieldEmail);
                    mappedEmail = mappedEmail.replaceAll("(?<=.{3}).(?=.*@)", "X");
                    radio_email.setText("To my registered email address " + mappedEmail);
                }

                if (serverResponse != null && !serverResponse.isEmpty()
                        && serverResponse.equalsIgnoreCase("Query Successful") && view != null) {
                    optionsForgotPwdPopup(view);
                } else {
                    Toast.makeText(context, "User id doesn't exist", Toast.LENGTH_SHORT).show();
                }
                break;

            case FORGOT_PWD:

                if (serverResponse != null && serverResponse.split("--")[0].equalsIgnoreCase("Query Successful")
                        && serverResponse.split("--")[1].equalsIgnoreCase("true")) {
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                    Toast.makeText(context, "Your password has been sent to your email/mobile no", Toast.LENGTH_SHORT)
                            .show();
                } else if (serverResponse != null && serverResponse.split("--")[0].equalsIgnoreCase("Query Successful")
                        && serverResponse.split("--")[1].equalsIgnoreCase("INVALID")) {
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                    Toast.makeText(context, "User id Invalid", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Please check your email/mobile no", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }

    private void myFamilyDataBaseDeltion(String tempUserId) {
        String existingMainUser = "";
        try {
            existingMainUser = appDb.getMainUserSelection("true");
        } catch (Exception e) {
        }

        if (existingMainUser != null && !existingMainUser.isEmpty()) {
            if (!tempUserId.equalsIgnoreCase(existingMainUser)) {
                orderDB.deleteOrder();
                appDb.deleteFamilyMemberData();
            }
        }

    }

    private void sendRequest(ApiRequest request) {
        ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
    }

    private void setCallUS(JSONArray jArray) {
        try {
            if (Validate.notNull(jArray)) {

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    String screenName = jsonObject.getString("SCREEN_NAME");
                    String content = jsonObject.getString("CONTENT");

                    if (Validate.notEmpty(screenName) && Validate.notEmpty(content)) {
                        SharedPrefsUtil.setStringPreference(context, Constants.CALLUS + "", content + "");
                        landline_number.setText(content);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setLOGO(JSONArray jArray) {
        try {
            if (Validate.notNull(jArray)) {

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonObject = jArray.getJSONObject(i);
                    String screenName = jsonObject.getString("SCREEN_NAME");
                    String content = jsonObject.getString("CONTENT");

                    if (Validate.notEmpty(screenName) && Validate.notEmpty(content)) {
                        SharedPrefsUtil.setStringPreference(context, Constants.LOGO + "", content + "");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setProfileData(JSONArray jArray) {
        if (jArray != null) {
            try {
                if (Validate.notNull(jArray)) {
                    _userData = new ArrayList<>();
                    for (int i = 0; i < jArray.length(); i++) {
                        UserData _userdataset = new UserData();
                        _userdataset.setUserid(jArray.getJSONObject(i).getString(Constants.userid));
                        _userdataset.setPtnt_cd(jArray.getJSONObject(i).getString(Constants.ptnt_cd));
                        _userdataset.setPtnt_tittle(jArray.getJSONObject(i).getString(Constants.ptnt_tittle));
                        _userdataset.setFirst_name(jArray.getJSONObject(i).getString(Constants.first_name));
                        _userdataset.setLast_name(jArray.getJSONObject(i).getString(Constants.last_name));
                        _userdataset.setGender(jArray.getJSONObject(i).getString(Constants.gender));
                        try {
                            _userdataset.setDob(jArray.getJSONObject(i).getLong(Constants.dob));
                        } catch (Exception e) {

                        }
                        _userdataset.setMarital_status(jArray.getJSONObject(i).getString(Constants.marital_status));
                        _userdataset.setEmail_id(jArray.getJSONObject(i).getString(Constants.email_id));
                        _userdataset.setZip(jArray.getJSONObject(i).getString(Constants.zip));
                        _userdataset.setMobile_no(jArray.getJSONObject(i).getString(Constants.mobile_no));
                        _userdataset.setAddress1(jArray.getJSONObject(i).getString(Constants.address1));
                        _userdataset.setAddress2(jArray.getJSONObject(i).getString(Constants.address2));
                        _userdataset.setLandmark(jArray.getJSONObject(i).getString(Constants.landmark));
                        _userdataset.setCity(jArray.getJSONObject(i).getString(Constants.city));
                        _userdataset.setState(jArray.getJSONObject(i).getString(Constants.state));
                        _userdataset.setCountry(jArray.getJSONObject(i).getString(Constants.country));
                        _userdataset.setParent_id(jArray.getJSONObject(i).getString(Constants.parent_id));
                        _userdataset.setStatus("0");
                        _userdataset.setPwd(pwd_edittext.getText().toString().trim());

                        _userData.add(_userdataset);
                        appDb.addUserDetails(_userdataset, context);
                    }
                }

                navigateToActivity();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            navigateToActivity();
        }
    }

    private void setOTP_ProfileData(JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                if (Validate.notNull(jsonObject)) {
                    _userData = new ArrayList<>();

                    UserData _userdataset = new UserData();
                    _userdataset.setUserid(jsonObject.getString(Constants.userid));
                    _userdataset.setPtnt_cd(jsonObject.getString(Constants.ptnt_cd));
                    _userdataset.setPtnt_tittle(jsonObject.getString(Constants.ptnt_tittle));
                    _userdataset.setFirst_name(jsonObject.getString(Constants.first_name));
                    _userdataset.setLast_name(jsonObject.getString(Constants.last_name));
                    _userdataset.setGender(jsonObject.getString(Constants.gender));
                    try {
                        _userdataset.setDob(jsonObject.getLong(Constants.dob));
                    } catch (Exception e) {

                    }
                    _userdataset.setMarital_status(jsonObject.getString(Constants.marital_status));
                    _userdataset.setEmail_id(jsonObject.getString(Constants.email_id));
                    _userdataset.setZip(jsonObject.getString(Constants.zip));
                    _userdataset.setMobile_no(jsonObject.getString(Constants.mobile_no));
                    _userdataset.setAddress1(jsonObject.getString(Constants.address1));
                    _userdataset.setAddress2(jsonObject.getString(Constants.address2));
                    _userdataset.setLandmark(jsonObject.getString(Constants.landmark));
                    _userdataset.setCity(jsonObject.getString(Constants.city));
                    _userdataset.setState(jsonObject.getString(Constants.state));
                    _userdataset.setCountry(jsonObject.getString(Constants.country));
                    _userdataset.setParent_id(jsonObject.getString(Constants.parent_id));
                    _userdataset.setStatus("0");
                    _userdataset.setPwd(jsonObject.getString(Constants.ptnt_pwd));

                    _userData.add(_userdataset);
                    appDb.addUserDetails(_userdataset, context);

                }

                navigateToActivity();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {
            navigateToActivity();
        }
    }

    private void navigateToActivity() {

        String sharedpin = SharedPrefsUtil.getStringPreference(context, "fourdigitpin");

        if (Validate.notEmpty(sharedpin)) {

            int navigationcount = 0;

            navigationcount = SharedPrefsUtil.getIntegerPreference(LoginScreenActivity.this,
                    Constants.sharedPreferenceSelectedLoginActivity, 0);
            Intent intent = null;
            switch (navigationcount) {
                case Constants.m_pwd:
                    Util.killChangePwd();
                    intent = new Intent(LoginScreenActivity.this, ChangePwdActivity.class);
                    break;
                case Constants.m_orders:
                    Util.killOrders();
                    intent = new Intent(LoginScreenActivity.this, OrdersActivity.class);
                    break;
                case Constants.m_login:
                    Util.killDashBoard();
                    intent = new Intent(LoginScreenActivity.this, Dashboard.class);
                    break;
                case Constants.m_reports:
                    Util.killMyReportEntry();
                    intent = new Intent(LoginScreenActivity.this, MyReportEntryDetails.class);
                    break;
                case Constants.m_cart:
                    Util.killAddPatient();
                    Constants.isLabOrCollection = false;
                    intent = new Intent(LoginScreenActivity.this, AddPatientActivity.class);
                    finish();
                    break;
                case Constants.m_lab:
                    Util.killAddPatient();
                    Constants.isLabOrCollection = true;
                    intent = new Intent(LoginScreenActivity.this, AddPatientActivity.class);
                    finish();
                    break;

                case Constants.m_family:
                    Util.killMyFamily();
                    intent = new Intent(LoginScreenActivity.this, MyFamilyActivity.class);
                    finish();
                    break;

                case Constants.m_profile:
                    Util.killMyProfile();
                    intent = new Intent(LoginScreenActivity.this, MyProfileActivity.class);
                    finish();
                    break;
                case Constants.m_health:
                    Util.killHealthTrack();
                    intent = new Intent(LoginScreenActivity.this, HealthTracker.class);
                    finish();
                    break;

                case Constants.m_settings:
                    Util.killSettings();
                    intent = new Intent(LoginScreenActivity.this, SettingsActivity.class);
                    finish();
                    break;

                default:
                    Util.killDashBoard();
                    intent = new Intent(LoginScreenActivity.this, Dashboard.class);
                    finish();
                    break;

            }

            if (intent != null) {
                startActivity(intent);
                finish();
            }

        } else {
            SharedPrefsUtil.setStringPreference(context, "setpin", "true");
            Intent intent = new Intent(LoginScreenActivity.this, FourDigitActivity.class);
            finish();
            startActivity(intent);

        }
    }

    private String getData(String ptntcode) {
        UserData _userAppData = null;
        try {
            _userAppData = appDb.getSelectedUserDetail(ptntcode);
        } catch (Exception e) {

        }
        return _userAppData.getPtnt_cd();
    }

    //    @Override
    //    protected void onResume() {
    //        fsdfsd
    //        super.onResume();
    //    }

    @Override
    protected void onDestroy() {
        Constants.isLogin = false;

        if (logout_text.getText().toString().equalsIgnoreCase("Login")) {
            logout_text.setText("Logout");

        } else {

            logout_text.setText("Login");
        }
        super.onDestroy();
    }


}