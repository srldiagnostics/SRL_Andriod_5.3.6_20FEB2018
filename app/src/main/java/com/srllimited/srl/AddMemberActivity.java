package com.srllimited.srl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.srllimited.srl.adapters.OTPLogin_PatientDetailAdapter;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.ReportsData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.data.ServerResponseData2;
import com.srllimited.srl.data.UserData;
import com.srllimited.srl.database.ReportsDatabase;
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
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddMemberActivity extends Activity implements View.OnClickListener, RestApiCallUtil.VolleyCallback {
    private static final String ACC_ID = "ACC_ID";
    private static final String PRDCT_ID = "PRDCT_ID";
    private static final String PRDCT_CD = "PRDCT_CD";
    private static final String PRDCT_NAME = "PRDCT_NAME";
    private static final String ELMNT_ID = "ELMNT_ID";
    private static final String ELMNT_CD = "ELMNT_CD";
    private static final String ELMNT_NAME = "ELMNT_NAME";
    private static final String ELMNT_RSLT_TYP = "ELMNT_RSLT_TYP";
    private static final String ELMNT_MIN_RANGE = "ELMNT_MIN_RANGE";
    private static final String ELMNT_MAX_RANGE = "ELMNT_MAX_RANGE";
    private static final String ELMNT_RSLT_UNIT = "ELMNT_RSLT_UNIT";
    private static final String RSLT = "RSLT";
    private static final String P_CMMNT = "P_CMMNT";
    private static final String RANGE_VAL = "RANGE_VAL";
    private static final String PRNT_RNG_TXT = "PRNT_RNG_TXT";
    private static final String RSLT_NORMAL_FLAG = "RSLT_NORMAL_FLAG";
    private static final String SR_NO = "SR_NO";
    private static final String PARENT_PRDCT_ID = "PARENT_PRDCT_ID";
    private static final String PARENT_PRDCT_CD = "PARENT_PRDCT_CD";
    private static final String PARENT_PRDCT_NAME = "PARENT_PRDCT_NAME";
    private static final String ACC_DT = "ACC_DT";
    private static final String PTNT_CD = "PTNT_CD";
    private static final String GENERIC_NAME = "GENERIC_NAME";
    private static final String CPT_CODE = "CPT_CODE";
    public static Activity addmember;
    String user_id, passwd;
    public static ArrayList<UserData> _userData = new ArrayList<>();
    Context context;
    ImageView close;
    EditText userIDETID;
    TextInputEditText passwordETID;
    Button autheniticateBTNID, registerBTNID;
    ReportsDatabase reportDB;
    ArrayList<UserData> _muserData = new ArrayList<>();
    //Data Base
    //Update Priofile
    ArrayList<UserData> _updateuserData = new ArrayList<>();
    AppDataBaseHelper appDb = new AppDataBaseHelper(this);
    UserData _userAppData;
    private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>() {
        @Override
        public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData) {
            switch (request.getReferralCode()) {
                case REPORTS: {
                    if (serverResponseData != null && serverResponseData.getObjectData() != null) {
                        try {
                            JSONObject jobj = serverResponseData.getObjectData().getJSONObject("create");

                            if (jobj != null) {
                                ServerResponseData2 serverResponseData2 = new ServerResponseData2();
                                serverResponseData2.setCode(100);
                                serverResponseData2.setMsg("");
                                serverResponseData2.setData(jobj.getJSONArray("RSLT"));

                                if (serverResponseData2 != null && serverResponseData2.getData() != null) {
                                    setReportsList(serverResponseData2);
                                }
                            }
                        } catch (Exception e) {

                        }

                    }
                }
                break;

                case Family_Member_Mapping: {
                    if (serverResponseData.getMsg().equalsIgnoreCase("Query Successful")) {
                        String a = "";
                    }
                }
                break;
            }
        }

        @Override
        public void onResponseError(final ApiRequest request, final Exception error) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_member_activity);
        context = AddMemberActivity.this;

        addmember = this;
        userIDETID = (EditText) findViewById(R.id.userIDETID);
        passwordETID = (TextInputEditText) findViewById(R.id.passwordETID);

        autheniticateBTNID = (Button) findViewById(R.id.autheniticateBTNID);
        reportDB = new ReportsDatabase(getApplicationContext());
        registerBTNID = (Button) findViewById(R.id.registerBTNID);

        close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(this);

        autheniticateBTNID.setOnClickListener(this);
        registerBTNID.setOnClickListener(this);

        appDb = new AppDataBaseHelper(getApplicationContext());

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Constants.isfromaddmenbers && OTPRegistration.user_id !=null) {
            user_id = OTPRegistration.user_id;
            passwd = OTPRegistration.user_pass;
            authendicatingDetailsLocalDB(user_id, passwd);
            Constants.isfromaddmenbers = false;
        }
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.autheniticateBTNID:
                user_id = userIDETID.getText().toString();
                passwd = passwordETID.getText().toString();
                authendicatingDetailsLocalDB(user_id, passwd);
                break;
            case R.id.registerBTNID:
                Constants.isRegEdited = false;
                Constants.isfromaddmenbers = true;
                Util.killReg();
                Intent i = new Intent(AddMemberActivity.this, RegistrationScreen.class);
                startActivity(i);
                break;

            case R.id.close:
                //				Util.killMyFamily();
                //				Intent intent = new Intent(AddMemberActivity.this, MyFamilyActivity.class);
                //				startActivity(intent);
                finish();
                break;
        }
    }

    private void authendicatingDetailsLocalDB(String user_id, String pass_word) {

        if (!user_id.isEmpty() && !pass_word.isEmpty()) {
            Map<String, String> params = new HashMap<String, String>();
            params.put(Constants.ptntcode, user_id);
            params.put(Constants.pwd, pass_word);
            /*params.put(Constants.ptntcode, "SHIVM1007160");
            params.put(Constants.pwd, "432801");*/

            if (!Util.getStoredUsername(context).equalsIgnoreCase(user_id)) {
                List<UserData> userData = new ArrayList<>();
                userData = appDb.getUSersList();
                if (userData.size() != 0) {
                    boolean alreadyExixtUser = appDb.CheckIsDataAlreadyInDBorNot(user_id);
                    if (!alreadyExixtUser) {
                        RestApiCallUtil.postServerResponse(context, ApiRequestReferralCode.LOGIN, params);
                    } else {
                        Toast.makeText(context, "This UserId already exists...", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    RestApiCallUtil.postServerResponse(context, ApiRequestReferralCode.LOGIN, params);
                }
            } else {
                Toast.makeText(context, "Your Already Logged user...", Toast.LENGTH_SHORT).show();
            }

            //ApiParameters.getLoginDetails(context, "13", params);
        } else {
            if (!user_id.isEmpty()) {
                if (pass_word.isEmpty()) {
                    Toast.makeText(context, "Please enter password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Please enter user name", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSuccessResponse(ApiRequestReferralCode referralCode, String serverResponse,
                                  HashMap<String, String> fetchValue) {
        if (serverResponse != null && serverResponse.equalsIgnoreCase("Query Successful")) {
            Toast.makeText(context, "Successfully Added..", Toast.LENGTH_SHORT).show();

            //			sendRequest(ApiRequestHelper.getReports(userIDETID.getText().toString().trim(), passwordETID.getText().toString().trim(),
            //			                                        Constants.devicetobepassed, "1", "ANDROID", "1", "1", System.currentTimeMillis() + ""));

            String userid = user_id;
            String pwd = passwd;
            new AsyncTaskRunner(fetchValue, userid, pwd);
        } else {
            Toast.makeText(context, "Authentication Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //		Util.killMyFamily();
        //		Intent intent = new Intent(AddMemberActivity.this, MyFamilyActivity.class);
        //		startActivity(intent);
        finish();
    }

    private void sendRequest(ApiRequest request) {
        ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
    }

    private void setReportsList(ServerResponseData2 serverResponseData) {

        JSONArray jArray = null;
        if (serverResponseData != null) {
            if (serverResponseData.getData() != null) {
                jArray = serverResponseData.getData();
            }

            try {
                if (Validate.notNull(jArray)) {
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject jsonObject = jArray.getJSONObject(i);
                        ReportsData reportsData = new ReportsData();

                        reportsData.setACC_ID(jsonObject.getString(ACC_ID));
                        reportsData.setPRDCT_ID(jsonObject.getInt(PRDCT_ID));
                        reportsData.setPRDCT_CD(jsonObject.getString(PRDCT_CD));
                        reportsData.setPRDCT_NAME(jsonObject.getString(PRDCT_NAME));
                        reportsData.setELMNT_ID(jsonObject.getInt(ELMNT_ID));
                        reportsData.setELMNT_CD(jsonObject.getString(ELMNT_CD));
                        reportsData.setELMNT_NAME(jsonObject.getString(ELMNT_NAME));

                        reportsData.setELMNT_RSLT_TYP(jsonObject.getString(ELMNT_RSLT_TYP));
                        reportsData.setELMNT_MIN_RANGE(jsonObject.getString(ELMNT_MIN_RANGE));
                        reportsData.setELMNT_MAX_RANGE(jsonObject.getString(ELMNT_MAX_RANGE));
                        reportsData.setELMNT_RSLT_UNIT(jsonObject.getString(ELMNT_RSLT_UNIT));
                        reportsData.setRSLT(jsonObject.getString(RSLT));
                        reportsData.setP_CMMNT(jsonObject.getString(P_CMMNT));

                        try {
                            reportsData.setRANGE_VAL(jsonObject.getInt(RANGE_VAL));
                        } catch (Exception e) {
                            reportsData.setRANGE_VAL(0);
                        }

                        reportsData.setPRNT_RNG_TXT(jsonObject.getString(PRNT_RNG_TXT));
                        reportsData.setRSLT_NORMAL_FLAG(jsonObject.getString(RSLT_NORMAL_FLAG));
                        reportsData.setSR_NO(jsonObject.getInt(SR_NO));
                        reportsData.setPARENT_PRDCT_ID(jsonObject.getInt(PARENT_PRDCT_ID));
                        reportsData.setPARENT_PRDCT_CD(jsonObject.getString(PARENT_PRDCT_CD));
                        reportsData.setPARENT_PRDCT_NAME(jsonObject.getString(PARENT_PRDCT_NAME));
                        reportsData.setACC_DT(jsonObject.getLong(ACC_DT));

                        reportsData.setPTNT_CD(/*Util.getStoredUsername(context)*/jsonObject.getString(PTNT_CD));
                        reportsData.setGENERIC_NAME(jsonObject.getString(GENERIC_NAME));
                        reportsData.setCPT_CODE(jsonObject.getString(CPT_CODE));

                        reportDB.createReports(reportsData);

                        //								mOrdersHistoryDatas.add(ordersHistoryData);

                    }

                }
            } catch (JSONException e) {

            }

        }
    }

    private class AsyncTaskRunner extends AsyncTask<Void, Void, Void> {
        HashMap<String, String> fetchValue = new HashMap<>();
        private ApiRequest mRequest;
        private ServerResponseData mServerResponseData;
        private String userId = "";
        private String pwd = "";

        public AsyncTaskRunner(HashMap<String, String> fetchedValue, String userid, String upwd) {
            fetchValue = fetchedValue;
            this.userId = userid;
            this.pwd = upwd;
            execute();
        }

        @Override
        protected void onPreExecute() {
            Util.hideProgressDialog();
            Util.showProgressDialog(context, "Please wait...");

        }

        @Override
        protected Void doInBackground(final Void... voids) {
            sendRequest(ApiRequestHelper.getReports(context, userId, pwd, Constants.devicetobepassed, "1", "ANDROID",
                    "1", "1", "0"));


            _userData = new ArrayList<>();
            UserData _userdataset = new UserData();
            _userdataset.setUserid(fetchValue.get("USER_ID") + "");
            _userdataset.setPtnt_cd(fetchValue.get("PTNT_CD") + "");
            _userdataset.setPtnt_tittle(fetchValue.get("PTNT_TITTLE") + "");
            _userdataset.setFirst_name(fetchValue.get("FIRST_NAME") + "");
            _userdataset.setLast_name(fetchValue.get("LAST_NAME") + "");
            _userdataset.setGender(fetchValue.get("GENDER") + "");
            try {

                _userdataset.setDob(Long.valueOf(fetchValue.get("DOB")));
            } catch (Exception e) {

            }
            _userdataset.setMarital_status(fetchValue.get("MARITAL_STATUS") + "");
            _userdataset.setEmail_id(fetchValue.get("EMAIL_ID") + "");
            _userdataset.setZip(fetchValue.get("ZIP") + "");
            _userdataset.setMobile_no(fetchValue.get("MOBILE_NO") + "");
            _userdataset.setAddress1(fetchValue.get("ADDRESS1") + "");
            _userdataset.setAddress2(fetchValue.get("ADDRESS2") + "");
            _userdataset.setLandmark(fetchValue.get("LANDMARK") + "");
            _userdataset.setCity(fetchValue.get("CITY") + "");
            _userdataset.setState(fetchValue.get("STATE") + "");
            _userdataset.setCountry(fetchValue.get("COUNTRY") + "");
            _userdataset.setParent_id(fetchValue.get("PARENT_ID") + "");
            _userdataset.setStatus("false");
            _userdataset.setPwd(pwd);
            _userData.add(_userdataset);
            appDb.addUserDetails(_userdataset, context);

            List<UserData> arrayFamily = appDb.getUSersList();
            String familymembers = "";
            final String primaryuserid = Util.getStoredUsername(context);

            UserData primaryuserdata=   appDb.getSelectedUserDetail(primaryuserid);
            for (int i = 0; i < arrayFamily.size(); i++) {
                //if (!jArray.getJSONObject(i).getString(Constants.ptntCd1).equalsIgnoreCase(OTPLogin_PatientDetailAdapter.patientDetailsDataobj.getPTNT_CD())) {
                if(!arrayFamily.get(i).getPtnt_cd().equalsIgnoreCase(primaryuserid)) {
                    familymembers = familymembers + arrayFamily.get(i).getPtnt_cd() + ",";
                }


            }
            if (!familymembers.equalsIgnoreCase("")) {
                familymembers = familymembers.substring(0, familymembers.length() - 1);
            }

            sendRequest(ApiRequestHelper.Family_Member_Mapping(context, primaryuserid,
                    familymembers, "Android", Constants.devicetobepassed, primaryuserdata.getMobile_no()));// Add family membar on server
            //			Intent i = new Intent(AddMemberActivity.this, MyFamilyActivity.class);
            //			startActivity(i);
            //			finish();

            return null;
        }

        @Override
        protected void onPostExecute(final Void aVoid) {
            super.onPostExecute(aVoid);
            Util.hideProgressDialog();
            //			Util.killMyFamily();
            //			Intent i = new Intent(AddMemberActivity.this, MyFamilyActivity.class);
            //			startActivity(i);
            finish();

        }
    }

}
