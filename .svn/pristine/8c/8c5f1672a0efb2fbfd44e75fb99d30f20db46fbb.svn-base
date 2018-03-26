package com.srllimited.srl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.data.StatesListData;
import com.srllimited.srl.data.UserData;
import com.srllimited.srl.permission.DangerousPermResponseCallBack;
import com.srllimited.srl.permission.DangerousPermissionResponse;
import com.srllimited.srl.permission.DangerousPermissionUtils;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.util.DateUtil;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.util.Validation;
import com.srllimited.srl.utilities.AppDataBaseHelper;
import com.srllimited.srl.utilities.Utility;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Codefyne on 31/12/2016.
 */
public class MyProfileActivity extends MenuBaseActivity implements View.OnClickListener
{

	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final int RESULT_CODE_SEARCH_LOCATION = 10;
	private static final String TAG = Dashboard.class.getSimpleName();
	private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	public static Activity myprofile;
	private static int RESULT_LOAD_IMAGE = 1;
	private static int CAMERA_REQUES_CODE = 101;
	Context context;
	TextView usernameTVID, useridTVID, genderTVID, dobTVID;
	EditText mobileNoTVID, emailidTVID, doornoTVID, landmarkTVID, pincodeTVID;
	TextView stateTVID, cityTVID;
	ArrayList<UserData> _userData = new ArrayList<>();
	//Data Base
	AppDataBaseHelper appDb = new AppDataBaseHelper(this);
	UserData _userAppData;
	//Update Profile
	ArrayList<UserData> _updateuserData = new ArrayList<>();
	UserData _updateuserdataset;
	ImageView contactEditIVID, addressEditIVID;
	Button savecontactTVID, addresssaveIVID;
	boolean isAddressEdit = false;
	String saveOption = "";
	ArrayList<String> capturelist = new ArrayList<String>();
	String captured_img_str;
	ListView pop_up_states_list;
	LinearLayout hidePopup;
	TextView popup_header;
	ImageView cancel, confirm;
	Animation bottomDown;
	ArrayList<StatesListData> stateslistData = new ArrayList<StatesListData>();
	ArrayList<String> stateNames = new ArrayList<String>();
	boolean stateselected = false;
	String stateID = "";
	///
	ArrayList<String> cityName = new ArrayList<String>();
	boolean CaptureAction = false;
	boolean alertSubmit = false;
	private Dialog promoDialog;
	private ImageView close_popup;
	private TextView alert_submit;
	private TextInputEditText et_password;
	///Image Capture
	private CircleImageView mCapturedimage_Imageview;
	private Button mCapture_Button, mSend_Button;
	private File mCapturedImageFile;
	private Bitmap capturedImage = null;
	private String selectedListItem = "";
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			switch (request.getReferralCode())
			{

				case UPDATE_USER_DETAILS:
				{
					if (serverResponseData != null && Validate.notEmpty(serverResponseData.getMsg()))
					{
						if (serverResponseData.getMsg().equalsIgnoreCase("Query Successful"))
						{
							Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
							appDb.updateUserDetails(_updateuserdataset, context);
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Failed to Update...", Toast.LENGTH_SHORT).show();
						}
					}
				}
					break;

				///All States
				case GET_STATES:
				{

					setStateList(serverResponseData.getFullData());
				}
					break;
				///Selected State Cities
				case GET_CITIES:
				{

					setCitiesList(serverResponseData.getFullData());
				}
					break;
			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{

		}
	};

	public static Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels)
	{
		Bitmap result = null;
		try
		{
			result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(result);

			int color = 0xff424242;
			Paint paint = new Paint();
			Rect rect = new Rect(0, 0, 200, 200);

			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(color);
			canvas.drawCircle(100, 100, 100, paint);
			paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rect, paint);

		}
		catch (NullPointerException e)
		{
		}
		catch (OutOfMemoryError o)
		{
		}
		return result;
	}

	private static File getOutputMediaFile(int type)
	{
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);
		if (!mediaStorageDir.exists())
		{
			if (!mediaStorageDir.mkdirs())
			{
				Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create " + IMAGE_DIRECTORY_NAME + " directory");
				return null;
			}
		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE)
		{
			mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
		}
		else
		{
			return null;
		}

		return mediaFile;
	}

	private static int getRotation(Context context, Uri selectedImage)
	{
		int rotation = 0;
		ContentResolver content = context.getContentResolver();

		Cursor mediaCursor = content.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				new String[] { "orientation", "date_added" }, null, null, "date_added desc");

		if (mediaCursor != null && mediaCursor.getCount() != 0)
		{
			while (mediaCursor.moveToNext())
			{
				rotation = mediaCursor.getInt(0);
				break;
			}
		}
		mediaCursor.close();
		return rotation;
	}

	private static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage)
	{

		int rotation = getRotation(context, selectedImage);
		if (rotation != 0)
		{
			Matrix matrix = new Matrix();
			matrix.postRotate(rotation);
			Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
			img.recycle();
			return rotatedImg;
		}
		else
		{
			return img;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.my_profile_actvity);
		context = MyProfileActivity.this;
		appDb = new AppDataBaseHelper(getApplicationContext());
		header_loc_name.setText("Profile");
		header_loc_name.setEnabled(false);
		myprofile = this;
		usernameTVID = (TextView) findViewById(R.id.usernameTVID);
		useridTVID = (TextView) findViewById(R.id.useridTVID);
		genderTVID = (TextView) findViewById(R.id.genderTVID);
		dobTVID = (TextView) findViewById(R.id.dobTVID);
		mobileNoTVID = (EditText) findViewById(R.id.mobileNoTVID);
		emailidTVID = (EditText) findViewById(R.id.emailidTVID);
		doornoTVID = (EditText) findViewById(R.id.doornoTVID);
		landmarkTVID = (EditText) findViewById(R.id.landmarkTVID);
		cityTVID = (TextView) findViewById(R.id.cityTVID);
		stateTVID = (TextView) findViewById(R.id.stateTVID);
		pincodeTVID = (EditText) findViewById(R.id.pincodeTVID);

		contactEditIVID = (ImageView) findViewById(R.id.contactEditIVID);
		addressEditIVID = (ImageView) findViewById(R.id.addressEditIVID);
		savecontactTVID = (Button) findViewById(R.id.savecontactTVID);
		addresssaveIVID = (Button) findViewById(R.id.addresssaveIVID);

		mCapturedimage_Imageview = (CircleImageView) findViewById(R.id.capture_pic);
		mCapturedimage_Imageview.setOnClickListener(this);

		contactEditIVID.setOnClickListener(MyProfileActivity.this);
		addressEditIVID.setOnClickListener(MyProfileActivity.this);
		savecontactTVID.setOnClickListener(MyProfileActivity.this);
		addresssaveIVID.setOnClickListener(MyProfileActivity.this);
		cityTVID.setOnClickListener(MyProfileActivity.this);

		stateTVID.setOnClickListener(MyProfileActivity.this);

		bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);

		promoDialog = new Dialog(this);
		promoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		promoDialog.setCancelable(false);
		promoDialog.setContentView(R.layout.enter_password_activity);
		close_popup = (ImageView) promoDialog.findViewById(R.id.close_popup);
		alert_submit = (TextView) promoDialog.findViewById(R.id.alert_submit);
		et_password = (TextInputEditText) promoDialog.findViewById(R.id.et_password);

		hidePopup = (LinearLayout) findViewById(R.id.hidePopup); //Hidden List View
		popup_header = (TextView) findViewById(R.id.popup_header);

		pop_up_states_list = (ListView) findViewById(R.id.pop_up_states_list);
		cancel = (ImageView) findViewById(com.srllimited.srl.R.id.cancel);
		confirm = (ImageView) findViewById(com.srllimited.srl.R.id.confirm);
		confirm.setOnClickListener(this);
		cancel.setOnClickListener(this);
		String username = Util.getStoredUsername(context);

		if (Validate.notEmpty(username))
		{
			getData(username);
		}
		else
		{
			Util.killLogin();
			SharedPrefsUtil.setIntegerPreference(context, Constants.sharedPreferenceSelectedLoginActivity,
					Constants.m_profile);
			Intent i = new Intent(MyProfileActivity.this, LoginScreenActivity.class);
			startActivity(i);
		}

		pop_up_states_list.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{

				if (CaptureAction)
				{
					selectedListItem = capturelist.get(position);
				}
				else
				{
					if (stateselected)
					{
						stateID = stateslistData.get(position).getStateID();
						stateTVID.setText(Html.fromHtml(stateslistData.get(position).getStateName()));
						cityTVID.setText("Select");
					}
					else
					{
						cityTVID.setText(Html.fromHtml(cityName.get(position)));
					}
				}
			}
		}

		);

		close_popup.setOnClickListener(this);
		alert_submit.setOnClickListener(this);

	}

	@Override
	public void onClick(View view)
	{

		switch (view.getId())
		{
			case R.id.contactEditIVID:
				contactEditIVID.setVisibility(View.GONE);
				savecontactTVID.setVisibility(View.VISIBLE);
				mobileNoTVID.setEnabled(true);
				mobileNoTVID.isCursorVisible();
				emailidTVID.setEnabled(true);
				break;
			case R.id.savecontactTVID:
				Util.hideSoftKeyboard(context, view);
				saveOption = "CONTACT_SAVE";
				if (promoDialog != null)
				{
					et_password.setText("");
					promoDialog.show();
				}
				break;
			case R.id.addressEditIVID:
				isAddressEdit = true;
				getRequest_States(ApiRequestHelper.getAllStates(context));
				setEnabledTrue();
				break;
			case R.id.addresssaveIVID:
				Util.hideSoftKeyboard(context, view);
				if (promoDialog != null)
				{
					saveOption = "ADDRESS_SAVE";
					et_password.setText("");
					promoDialog.show();
				}
				break;
			case R.id.cityTVID:
				setDisabled();
				CaptureAction = false;
				popup_header.setText("Cities");
				if (!stateID.isEmpty() && !stateID.equalsIgnoreCase("null") && stateID != null)
				{
					getRequest_Cities(ApiRequestHelper.getCities(context, stateID));
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please Select State", Toast.LENGTH_LONG).show();
				}
				// setCityAlertDialog(false);
				break;
			//Alert Dailog

			case R.id.stateTVID:
				setDisabled();
				CaptureAction = false;
				popup_header.setText("States");
				isAddressEdit = false;
				getRequest_States(ApiRequestHelper.getAllStates(context));
				break;
			case R.id.close_popup:
				setEnable();
				if (promoDialog != null)
				{
					promoDialog.dismiss();
					Util.hideSoftKeyboard(context, view);
				}
				break;
			case R.id.alert_submit:
				alertSubmit = true;
				String password = et_password.getText().toString();
				if (Validate.notEmpty(password))
				{
					if (password.equalsIgnoreCase(Util.getStoredPwd(context)))
					{
						if (saveOption.equalsIgnoreCase("CONTACT_SAVE"))
						{
							Util.hideSoftKeyboard(context, view);
							contactDetailsSaved();
						}
						else
						{
							Util.hideSoftKeyboard(context, view);
							addressDetailsSaved();
						}
					}
					else
					{
						Toast.makeText(context, "Please enter valid password.. ", Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					Toast.makeText(context, "Please enter password.. ", Toast.LENGTH_SHORT).show();
				}
				break;

			case R.id.cancel:
				selectedListItem = "";
				hidePopup.startAnimation(bottomDown);
				hidePopup.setVisibility(View.INVISIBLE);
				setEnable();
				break;

			case R.id.confirm:
				if (CaptureAction)
				{
					handleTaskWithUserPermission(CAMERA_REQUES_CODE);
				}
				else
				{
					hidePopup.startAnimation(bottomDown);
				}
				hidePopup.setVisibility(View.INVISIBLE);
				setEnable();
				break;

			case R.id.capture_pic:
				setDisabled();
				CaptureAction = true;
				popup_header.setText("Capture");
				capturelist = new ArrayList<String>();
				capturelist.add("Camera");
				capturelist.add("Gallery");
				setPopupListAdapter(capturelist);
				break;

		}

	}

	/*Here Capture type ListView,State ListView, City List View Commom Id.  i.e pop_up_states_list */
	private void setPopupListAdapter(ArrayList<String> popupLstItems)
	{

		setDisabled();
		pop_up_states_list.setAdapter(null);
		ArrayAdapter<String> ad = new ArrayAdapter<String>(this, R.layout.textcenter, R.id.textItem, popupLstItems);
		pop_up_states_list.setAdapter(ad);
		/*ad = new ArrayAdapter<String>(this, R.layout.textcenter, popupLstItems)
		{
			public View getView(int position, View view, ViewGroup viewGroup)
			{
				View v = super.getView(position, view, viewGroup);
				((TextView) v).setText(Html.fromHtml("<b>" + position + "</b> <br/>"));
				return v;
			}
		};*/

		Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

		hidePopup.startAnimation(bottomUp);
		hidePopup.setVisibility(View.VISIBLE);

	}

	private void setEnable()
	{
		doornoTVID.setEnabled(true);
		landmarkTVID.setEnabled(true);
		pincodeTVID.setEnabled(true);

		if (savecontactTVID.getVisibility() == View.VISIBLE)
		{
			mobileNoTVID.setEnabled(true);
			emailidTVID.setEnabled(true);
		}
		else
		{
			mobileNoTVID.setEnabled(false);
			emailidTVID.setEnabled(false);
		}

	}

	private void setDisabled()
	{
		doornoTVID.setEnabled(false);
		landmarkTVID.setEnabled(false);
		pincodeTVID.setEnabled(false);
		mobileNoTVID.setEnabled(false);
		emailidTVID.setEnabled(false);
	}

	private void contactDetailsSaved()
	{
		String mobileNo = mobileNoTVID.getText().toString();
		String emailId = emailidTVID.getText().toString();
		if (validationProfileDetails(emailId, mobileNo))
		{
			boolean saved_Con = true;
			updateUserProfile(saved_Con);
			contactEditIVID.setVisibility(View.VISIBLE);
			savecontactTVID.setVisibility(View.GONE);
			mobileNoTVID.setEnabled(false);
			emailidTVID.setEnabled(false);
			promoDialog.dismiss();

		}
	}

	private void addressDetailsSaved()
	{
		boolean saved_add = false;
		updateUserProfile(saved_add);
		addressEditIVID.setVisibility(View.VISIBLE);
		addresssaveIVID.setVisibility(View.GONE);
		setEnabledFalse();
		promoDialog.dismiss();
	}

	private void updateUserProfile(boolean saved)
	{
		/*Note : here address2 string  inside Passing Profile Image string i.e address2 = captured_img_str*/
		_userAppData = appDb.getLoginDetails();
		String userid = _userAppData.getPtnt_cd();

		String password = Util.getStoredPwd(context).toString();

		String mobileNo = mobileNoTVID.getText().toString();
		String emailId = emailidTVID.getText().toString();
		String address1 = doornoTVID.getText().toString();
		//String address2 = doornoTVID.getText().toString();
		String address2 = "";
		try
		{
			if (!captured_img_str.equalsIgnoreCase("null") && captured_img_str != null)
			{
				address2 = captured_img_str; // address2 passing Profile Image
			}
			else
			{
				address2 = "";
			}
		}
		catch (Exception e)
		{

		}

		String landmark = landmarkTVID.getText().toString();
		String city = cityTVID.getText().toString();
		String state = stateTVID.getText().toString();
		String country = _userAppData.getCountry();
		String zip = pincodeTVID.getText().toString();

		boolean validated = validationProfileDetails(emailId, mobileNo);

		if (validated && saved)
		{

			userProfilesValidated(userid, password, emailId, zip, mobileNo, address1, address2, landmark, city, state,
					country);
		}
		else
		{
			if (!validated && saved)
			{
				Log.e("Validated", emailId + mobileNo + "");
			}
			else
			{

				userProfilesValidated(userid, password, emailId, zip, mobileNo, address1, address2, landmark, city,
						state, country);
			}
		}

	}

	private void userProfilesValidated(String userid, String password, String emailid, String zip, String mobileNo,
			String address1, String address2, String landmark, String city, String state, String country)
	{
		List<UserData> usersDataTest = new ArrayList<>();
		usersDataTest = appDb.getUSersList();
		List<UserData> mUSerData = new ArrayList<>();
		for (UserData userdata : usersDataTest)
		{

			if (userdata.getStatus().equalsIgnoreCase("true"))
			{
				String saveEmailid = userdata.getEmail_id();

				_updateuserData = new ArrayList<>();
				_updateuserdataset = new UserData();
				_updateuserdataset.setUserid(userdata.getPtnt_cd());
				_updateuserdataset.setPtnt_cd(userdata.getPtnt_cd());
				_updateuserdataset.setPtnt_tittle(userdata.getPtnt_tittle());
				_updateuserdataset.setFirst_name(userdata.getFirst_name());
				_updateuserdataset.setLast_name(userdata.getLast_name());
				_updateuserdataset.setGender(userdata.getGender());
				_updateuserdataset.setDob(userdata.getDob());
				_updateuserdataset.setMarital_status(userdata.getMarital_status());
				_updateuserdataset.setEmail_id(emailid);
				_updateuserdataset.setZip(zip);
				_updateuserdataset.setMobile_no(mobileNo);
				_updateuserdataset.setAddress1(address1);
				_updateuserdataset.setAddress2(address2);
				_updateuserdataset.setLandmark(landmark);
				_updateuserdataset.setCity(city);
				_updateuserdataset.setState(state);
				_updateuserdataset.setCountry(country);
				_updateuserdataset.setParent_id(userdata.getParent_id());
				_updateuserData.add(_updateuserdataset);
				if (alertSubmit)
				{
					if (Utility.isOnline(MyProfileActivity.this))
					{
						sendRequest(ApiRequestHelper.getUpdateUserDetails(context, "", userid, password, mobileNo,
								emailid, address1, address2, landmark, city, state, country, zip));

					}
					else
					{
						Toast.makeText(getApplicationContext(), "No internet Connection", Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					appDb.updateUserDetails(_updateuserdataset, context);
				}
			}
			else
			{
			}
		}
	}

	private void userProfilesImageUpload()
	{
		String address2 = "";
		try
		{
			if (!captured_img_str.equalsIgnoreCase("null") && captured_img_str != null)
			{
				address2 = captured_img_str; // address2 passing Profile Image
			}
			else
			{
				address2 = "";
			}
		}
		catch (Exception e)
		{

		}
		List<UserData> usersDataTest = new ArrayList<>();
		usersDataTest = appDb.getUSersList();
		List<UserData> mUSerData = new ArrayList<>();
		for (UserData userdata : usersDataTest)
		{

			if (userdata.getStatus().equalsIgnoreCase("true"))
			{
				String saveEmailid = userdata.getEmail_id();

				_updateuserData = new ArrayList<>();
				_updateuserdataset = new UserData();
				_updateuserdataset.setUserid(userdata.getPtnt_cd());
				_updateuserdataset.setPtnt_cd(userdata.getPtnt_cd());
				_updateuserdataset.setPtnt_tittle(userdata.getPtnt_tittle());
				_updateuserdataset.setFirst_name(userdata.getFirst_name());
				_updateuserdataset.setLast_name(userdata.getLast_name());
				_updateuserdataset.setGender(userdata.getGender());
				_updateuserdataset.setDob(userdata.getDob());
				_updateuserdataset.setMarital_status(userdata.getMarital_status());
				_updateuserdataset.setEmail_id(userdata.getEmail_id());
				_updateuserdataset.setZip(userdata.getZip());
				_updateuserdataset.setMobile_no(userdata.getMobile_no());
				_updateuserdataset.setAddress1(userdata.getAddress1());
				_updateuserdataset.setAddress2(address2);
				_updateuserdataset.setLandmark(userdata.getLandmark());
				_updateuserdataset.setCity(userdata.getCity());
				_updateuserdataset.setState(userdata.getState());
				_updateuserdataset.setCountry(userdata.getCountry());
				_updateuserdataset.setParent_id(userdata.getParent_id());
				_updateuserData.add(_updateuserdataset);

			}
			else
			{

			}
		}
	}

	private boolean validationProfileDetails(String emailid, String mobileNo)
	{
		boolean ret = true;
		if (!emailid.equalsIgnoreCase("null") && !Validation.isEmailAddress(emailidTVID, true))
		{
			emailidTVID.setError(null);
			Toast.makeText(getApplicationContext(), "Please enter valid emailId", Toast.LENGTH_LONG).show();
			ret = false;
		}

		if (!mobileNo.equalsIgnoreCase("null") && !Validation.isValidPhoneNumber(mobileNoTVID))
		{
			mobileNoTVID.setError(null);
			Toast.makeText(getApplicationContext(), "Please enter valid Phone Number", Toast.LENGTH_LONG).show();
			ret = false;
		}
		return ret;
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	private void setData(UserData userdata)
	{

		if (userdata.getAddress2() != null && !userdata.getAddress2().equalsIgnoreCase("null")
				&& !userdata.getAddress2().isEmpty())
		{
			Bitmap captured_img_bitMap = StringToBitMap(userdata.getAddress2());
			profileImageSetting(captured_img_bitMap);
			/*mCapturedimage_Imageview.setBackgroundResource(R.color.emptycolor);
			mCapturedimage_Imageview.setImageBitmap(null);
			Bitmap conv_bm = getRoundedRectBitmap(captured_img_bitMap, 100);
			mCapturedimage_Imageview.setImageBitmap(conv_bm);*/
		}

		if (userdata.getPtnt_cd() != null && !userdata.getPtnt_cd().equalsIgnoreCase("null"))
		{
			useridTVID.setText(userdata.getPtnt_cd());
		}

		if (userdata.getGender() != null && !userdata.getGender().equalsIgnoreCase("null"))
		{
			if (userdata.getGender().equalsIgnoreCase("F"))
			{
				genderTVID.setText("Female");
			}
			if (userdata.getGender().equalsIgnoreCase("M"))
			{
				genderTVID.setText("Male");
			}
		}
		/*String selname = "";
		selname = userdata.getFirst_name() + " " + userdata.getLast_name() + "";*/

		if (!userdata.getFirst_name().equalsIgnoreCase("null") && userdata.getFirst_name() != null)
		{
			usernameTVID.setText(Html.fromHtml(userdata.getFirst_name()) + "");
			if (!userdata.getLast_name().equalsIgnoreCase("null") && userdata.getLast_name() != null)
			{
				usernameTVID.setText(
						Html.fromHtml(userdata.getFirst_name()) + " " + Html.fromHtml(userdata.getLast_name()) + "");
			}
		}

		if (userdata.getEmail_id() != null && !userdata.getEmail_id().equalsIgnoreCase("null"))
		{
			emailidTVID.setText(Html.fromHtml(userdata.getEmail_id()));
		}

		if (userdata.getMobile_no() != null && !userdata.getMobile_no().equalsIgnoreCase("null"))
		{
			mobileNoTVID.setText(userdata.getMobile_no());
		}

		if (userdata.getAddress1() != null && !userdata.getAddress1().equalsIgnoreCase("null"))
		{
			doornoTVID.setText(Html.fromHtml(userdata.getAddress1()) + "");
		}

		if (userdata.getLandmark() != null && !userdata.getLandmark().equalsIgnoreCase("null"))
		{
			landmarkTVID.setText(userdata.getLandmark());
		}

		if (userdata.getCity() != null && !userdata.getCity().equalsIgnoreCase("null"))
		{
			cityTVID.setText(Html.fromHtml(userdata.getCity()));

		}
		else
		{
			cityTVID.setText("Select");
		}

		if (userdata.getState() != null && !userdata.getState().equalsIgnoreCase("null"))
		{
			stateTVID.setText(Html.fromHtml(userdata.getState()) + "");
		}

		if (userdata.getZip() != null && !userdata.getZip().equalsIgnoreCase("null"))
		{
			pincodeTVID.setText(Html.fromHtml(userdata.getZip()) + "");
		}

		if (userdata.getDob() != 0)
		{
			try
			{
				dobTVID.setText(Html.fromHtml(DateUtil.epochToDate(userdata.getDob())) + "");
			}
			catch (Exception e)
			{
			}
		}

	}

	private void profileImageSetting(final Bitmap captured_img_bitMap)
	{
		mCapturedimage_Imageview.setBackgroundResource(R.color.emptycolor);
		mCapturedimage_Imageview.setImageBitmap(null);
		mCapturedimage_Imageview.setImageBitmap(captured_img_bitMap);
		MyProfileActivity.this.runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				imageView.setImageBitmap(captured_img_bitMap);
			}//public void run() {
		});

		captured_img_str = BitMapToString(captured_img_bitMap);
		userProfilesImageUpload();
		/* boolean saved_add = false;
		updateUserProfile(saved_add);*/

	}

	private void setEnabledFalse()
	{
		doornoTVID.setEnabled(false);
		landmarkTVID.setEnabled(false);
		cityTVID.setEnabled(false);
		cityTVID.setClickable(false);
		stateTVID.setEnabled(false);
		stateTVID.setClickable(false);
		pincodeTVID.setEnabled(false);
	}

	private void setEnabledTrue()
	{
		addressEditIVID.setVisibility(View.GONE);
		addresssaveIVID.setVisibility(View.VISIBLE);
		doornoTVID.setEnabled(true);
		landmarkTVID.setEnabled(true);
		cityTVID.setEnabled(true);
		cityTVID.setClickable(true);
		stateTVID.setEnabled(true);
		stateTVID.setClickable(true);
		pincodeTVID.setEnabled(true);
	}

	private void getData(String ptntcode)
	{
		UserData _userAppData = null;
		try
		{
			_userAppData = appDb.getSelectedUserDetail(ptntcode);
			if (_userAppData != null)
			{
				setData(_userAppData);
			}
		}
		catch (Exception e)
		{
			Log.v("MyProfileActivity", e.getMessage());
		}

	}

	private void getRequest_States(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	private void getRequest_Cities(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	private void setStateList(JSONObject jsonObject)
	{
		JSONArray jArray = null;
		try
		{
			if (!jsonObject.isNull("data"))
			{
				Object obj = jsonObject.get("data");
				if (obj != null && obj instanceof JSONArray)
				{
					jArray = jsonObject.getJSONArray(Constants.response_data_create);
				}
			}
		}
		catch (Exception e)
		{
		}
		if (jArray != null)
		{
			try
			{
				if (Validate.notNull(jArray))
				{
					if (!isAddressEdit)
					{
						stateselected = true;
						stateNames = new ArrayList<String>();
						stateslistData = new ArrayList<StatesListData>();
						for (int i = 0; i < jArray.length(); i++)
						{
							stateslistData.add(new StatesListData(jArray.getJSONObject(i).getString(Constants.state_id),
									jArray.getJSONObject(i).getString(Constants.state_name)));
						}

						if (stateslistData.size() > 0)
						{
							for (int i = 0; i < stateslistData.size(); i++)
							{
								stateNames.add(Html.fromHtml(stateslistData.get(i).getStateName()).toString());
							}
						}
						setPopupListAdapter(stateNames);
					}
					else
					{
						for (int i = 0; i < jArray.length(); i++)
						{
							//////
							if (stateTVID.getText().toString()
									.equalsIgnoreCase(jArray.getJSONObject(i).getString(Constants.state_name)))
							{
								stateID = jArray.getJSONObject(i).getString(Constants.state_id);
							}
						}
					}
				}
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void setCitiesList(JSONObject jsonObject)
	{
		JSONArray jArray = null;
		try
		{
			if (!jsonObject.isNull("data"))
			{
				Object obj = jsonObject.get("data");
				if (obj != null && obj instanceof JSONArray)
				{
					jArray = jsonObject.getJSONArray(Constants.response_data_create);
				}
			}
		}
		catch (Exception e)
		{
		}
		if (jArray != null)
		{
			try
			{
				if (Validate.notNull(jArray))
				{
					stateselected = false;
					cityName = new ArrayList<String>();
					for (int i = 0; i < jArray.length(); i++)
					{
						cityName.add(jArray.getJSONObject(i).getString(Constants.city_name));
					}
					setPopupListAdapter(cityName);
				}
			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
	}

	/////////Image Capture Code
	private void handleTaskWithUserPermission(int requestCode)
	{
		DangerousPermissionUtils.getPermission(context,
				new String[] { Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE }, requestCode)
				.enqueue(new DangerousPermResponseCallBack()
				{
					@Override
					public void onComplete(final DangerousPermissionResponse permissionResponse)
					{
						if (permissionResponse.isGranted())
						{
							if (permissionResponse.getRequestCode() == CAMERA_REQUES_CODE)
							{
								if (ActivityCompat.checkSelfPermission(context,
										Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
										&& ActivityCompat.checkSelfPermission(context,
												Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
								{
									return;
								}

								if (selectedListItem.equalsIgnoreCase("Camera"))
								{
									captureImage();
								}
								else
								{
									if (selectedListItem.equalsIgnoreCase("Gallery"))
									{
										try
										{
											Intent i = new Intent(Intent.ACTION_PICK,
													android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
											startActivityForResult(i, RESULT_LOAD_IMAGE);
										}
										catch (Exception e)
										{
										}

									}
								}
							}
						}
					}
				});
	}

	private void captureImage()
	{
		mCapturedImageFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
		Uri fileUri = getOutputMediaFileUri(mCapturedImageFile);
		if (fileUri != null)
		{
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent,
					PackageManager.MATCH_DEFAULT_ONLY);
			for (ResolveInfo resolveInfo : resInfoList)
			{
				String packageName = resolveInfo.activityInfo.packageName;
				context.grantUriPermission(packageName, fileUri,
						Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
			}
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
			startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
		}
	}

	public Uri getOutputMediaFileUri(File mediaFile)
	{
		//		return Uri.fromFile(getOutputMediaFile(type));
		if (mediaFile != null)
		{
			return FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", mediaFile);
		}
		return null;
	}

	public Bitmap StringToBitMap(String encodedString)
	{
		try
		{
			byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
			return bitmap;
		}
		catch (Exception e)
		{
			e.getMessage();
			return null;
		}
	}

	public String BitMapToString(Bitmap bitmap)
	{
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			byte[] b = baos.toByteArray();
			String temp = Base64.encodeToString(b, Base64.DEFAULT);
			return temp;
		}
		catch (Exception e)
		{

		}
		return "null";
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data)
		{
			try
			{
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.MediaColumns.DATA };

				Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String picturePath = cursor.getString(columnIndex);
				cursor.close();

				final BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 8;
				capturedImage = BitmapFactory.decodeFile(picturePath, options);

				try
				{
					ExifInterface ei = new ExifInterface(picturePath);
					int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
							ExifInterface.ORIENTATION_NORMAL);

					switch (orientation)
					{
						case ExifInterface.ORIENTATION_ROTATE_90:

							capturedImage = rotateImageIfRequired(context, capturedImage, selectedImage);
							break;
						case ExifInterface.ORIENTATION_ROTATE_180:
							capturedImage = rotateImageIfRequired(context, capturedImage, selectedImage);
							break;
					}
				}
				catch (Exception e)
				{
				}
				if (capturedImage != null)
				{
					profileImageSetting(capturedImage);
				}
				updateUserProfile(false);
			}
			catch (Exception e)
			{

			}
		}
		else if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE)
		{
			if (resultCode == RESULT_OK)
			{
				previewCapturedImage();
			}
			else if (resultCode == RESULT_CANCELED)
			{
				Toast.makeText(getApplicationContext(), "User cancelled image capture", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Sorry! Failed to capture image", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void previewCapturedImage()
	{
		try
		{

			String filePath = mCapturedImageFile.getAbsolutePath();
			mCapturedimage_Imageview.setVisibility(View.VISIBLE);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 8;

			capturedImage = BitmapFactory.decodeFile(filePath, options);

			try
			{
				Uri fileUri = getOutputMediaFileUri(mCapturedImageFile);
				ExifInterface ei = new ExifInterface(filePath);
				int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

				switch (orientation)
				{
					case ExifInterface.ORIENTATION_ROTATE_90:
						capturedImage = rotateImageIfRequired(context, capturedImage, fileUri);
						break;
					case ExifInterface.ORIENTATION_ROTATE_180:
						capturedImage = rotateImageIfRequired(context, capturedImage, fileUri);
						break;
				}
			}
			catch (Exception e)
			{

			}

			if (capturedImage != null)
			{
				profileImageSetting(capturedImage);
			}
			alertSubmit = false;
			updateUserProfile(false);
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
	}

}
