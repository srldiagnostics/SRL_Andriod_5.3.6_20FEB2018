package com.srllimited.srl;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.data.UserData;
import com.srllimited.srl.database.OrderDatabase;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.utilities.AppDataBaseHelper;

public class TakeSurvey_Activity extends AppCompatActivity {
TextView btn_takesurvey;
    EditText edtname,edtgender,edtage,edtmobileno,edtemailid;
    AppDataBaseHelper appDb = new AppDataBaseHelper(this);
String userid="";
    TakeSurvey_Activity context;
    UserData _userAppData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_survey_);
        btn_takesurvey=(TextView)findViewById(R.id.btn_takesurvey);
        edtname=(EditText) findViewById(R.id.edtname);
        edtgender=(EditText)findViewById(R.id.edtgender);
        edtage=(EditText)findViewById(R.id.edtage);
        edtmobileno=(EditText)findViewById(R.id.edtmobileno);
        edtemailid=(EditText)findViewById(R.id.edtemailid);
        context =this;

        //userid= SharedPrefsUtil.getStringPreference(TakeSurvey_Activity.this, Constants.loggedin_username);
        userid =Util.getStoredUsername(context);

        _userAppData =getData(userid);

        edtname.setText(_userAppData.getFirst_name()+" "+_userAppData.getLast_name());
        edtgender.setText(_userAppData.getGender());
        edtage.setText("");
        edtmobileno.setText(_userAppData.getMobile_no());
        edtemailid.setText(_userAppData.getEmail_id());

        btn_takesurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequest(ApiRequestHelper.getsurvey(context, userid,_userAppData.getMobile_no()));
            }
        });
    }
    private void sendRequest(ApiRequest request)
    {
        ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
    }

    private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
    {
        @Override
        public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
        {
            Log.e("TAG12", String.valueOf(serverResponseData.getArrayData()) + " " + request.getReferralCode());
            switch (request.getReferralCode())
            {
                case Get_SURVEY:
                {
                    Log.e("TAG12", String.valueOf(serverResponseData.getArrayData()) + " " + request.getReferralCode());

                }
                break;

            }
        }

        @Override
        public void onResponseError(final ApiRequest request, final Exception error)
        {

        }
    };
    private UserData getData(String ptntcode)
    {
        UserData _userAppData = null;
        try
        {
            _userAppData = appDb.getSelectedUserDetail(ptntcode);
        }
        catch (Exception e)
        {

        }
        return _userAppData;
    }
}
