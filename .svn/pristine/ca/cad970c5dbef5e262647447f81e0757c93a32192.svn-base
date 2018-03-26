package com.srllimited.srl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.AboutUsData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.permission.DangerousPermResponseCallBack;
import com.srllimited.srl.permission.DangerousPermissionResponse;
import com.srllimited.srl.permission.DangerousPermissionUtils;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiRequestReferralCode;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import info.hoang8f.android.segmented.SegmentedGroup;

public class ConfirmRegistation extends MenuBaseActivity
		implements View.OnClickListener, RestApiCallUtil.VolleyCallback, SendImageInterface
{
	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private static final int CAMERA_REQUEST = 8888;
	public static Activity confirmreg;
	private static int CAMERA_REQUES_CODE = 101;
	private static int RESULT_LOAD_IMAGE = 1;
	private final String screen_id = "SCREEN_ID";
	private final String screen_name = "SCREEN_NAME";
	private final String content = "CONTENT";
	private final String mysrlver = "MYSRLVER";
	Context context;
	boolean isPrivacy = false;
	CheckBox privacy_policy;
	Dialog alertDialog;
	SegmentedGroup segmented3;
	TextView alert_close;
	Button confirm;
	TextView registered_name, registered_mobile, registered_email, registered_dob, registered_gender, registered_age,
			registered_city, registered_state;
	ImageView editprofile;
	String name = "";
	String mobile = "";
	String email = "";
	String dob = "";
	String gender = "";
	String fname = "";
	String lname = "";
	String salutation = "";
	String age = "";
	String years = "";
	String months = "";
	String days = "";
	String State = "";
	String City = "";
	LinearLayout hidePopup;
	LinearLayout dob_layout, age_layout;
	View dob_view, age_view;
	List<AboutUsData> mPrivacyPolicyList = new ArrayList<>();
	Button buttonLoadImage;
	TextView popupheader;
	ArrayList<String> capturelist = new ArrayList<String>();
	ImageView image_confirm, image_cancel;
	LinearLayout confirm_footer;
	String privacyPolicy = "";
	String terms_of_use = "";
	TextView termcondition;
	String privacy = "";
	String terms = "";
	ProgressDialog progressDialog;
	private String selectedListItem = "";
	private ListView listView;
	private ImageView mCapturedimage_Imageview;
	private TextView policy_text;
	private Button mCapture_Button, mSend_Button;
	private Uri fileUri;
	private Bitmap capturedImage = null;
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{

			mPrivacyPolicyList.clear();

			switch (request.getReferralCode())
			{
				case Get_CONTENT:
					setAboutUsContent(serverResponseData.getArrayData());

					break;

			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{

		}
	};

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
		super.addContentView(R.layout.cofirm_registration);
		context = ConfirmRegistation.this;
		confirmreg = this;
		header_loc_name.setText("Confirm");
		header_loc_name.setEnabled(false);
		confirm = (Button) findViewById(R.id.mconfirm);
		popupheader = (TextView) findViewById(R.id.popup_header);
		listView = (ListView) findViewById(com.srllimited.srl.R.id.popup_list);
		mCapturedimage_Imageview = (ImageView) findViewById(R.id.capture_pic);
		dob_layout = (LinearLayout) findViewById(R.id.dob_layout);
		age_layout = (LinearLayout) findViewById(R.id.age_layout);
		dob_view = findViewById(R.id.dob_view);
		hidePopup = (LinearLayout) findViewById(R.id.hidePopup);
		age_view = findViewById(R.id.age_view);
		image_cancel = (ImageView) findViewById(com.srllimited.srl.R.id.cancel);
		image_confirm = (ImageView) findViewById(com.srllimited.srl.R.id.confirm);
		editprofile = (ImageView) findViewById(R.id.edit_profile);
		registered_name = (TextView) findViewById(R.id.registered_name);
		registered_mobile = (TextView) findViewById(R.id.registered_mobileno);
		registered_dob = (TextView) findViewById(R.id.registered_dob);
		registered_gender = (TextView) findViewById(R.id.registered_gender);
		registered_email = (TextView) findViewById(R.id.registered_email_address);
		registered_age = (TextView) findViewById(R.id.registered_age);
		registered_state = (TextView) findViewById(R.id.registered_state);
		registered_city = (TextView) findViewById(R.id.registered_city);
		confirm_footer = (LinearLayout) findViewById(R.id.confirm_footer);

		hidePopup.setVisibility(View.GONE);

		termcondition = (TextView) findViewById(R.id.termcondition);
		termcondition.setOnClickListener(this);
		termAndCondition();

		Intent intent = getIntent();
		if (intent != null)
		{

			name = intent.getStringExtra(Constants.registered_name) + "";
			mobile = intent.getStringExtra(Constants.registered_mobile) + "";
			dob = intent.getStringExtra(Constants.registered_dob) + "";
			email = intent.getStringExtra(Constants.registered_email) + "";
			gender = intent.getStringExtra(Constants.registered_gender) + "";
			fname = intent.getStringExtra(Constants.registered_fname) + "";
			lname = intent.getStringExtra(Constants.registered_lname) + "";
			salutation = intent.getStringExtra(Constants.registered_salutation) + "";
			age = intent.getStringExtra(Constants.registered_apprxage) + "";
			years = intent.getStringExtra(Constants.registered_years) + "";
			months = intent.getStringExtra(Constants.registered_months) + "";
			days = intent.getStringExtra(Constants.registered_days) + "";
			State = intent.getStringExtra(Constants.registered_state) + "";
			City = intent.getStringExtra(Constants.registered_city) + "";

			registered_name.setText(name);
			registered_mobile.setText(mobile);
			registered_dob.setText(dob);
			registered_gender.setText(gender);
			registered_email.setText(email);
			registered_age.setText(age);
			registered_state.setText(State);
			registered_city.setText(City);

		}

		if (!Validate.notEmpty(dob))
		{
			dob_layout.setVisibility(View.GONE);
			dob_view.setVisibility(View.GONE);
			age_view.setVisibility(View.VISIBLE);
			age_layout.setVisibility(View.VISIBLE);
			//            if (!Validate.notEmpty(age)) {
			//                age_view.setVisibility(View.GONE);
			//                age_layout.setVisibility(View.GONE);
			//            }
		}
		else
		{
			dob_layout.setVisibility(View.VISIBLE);
			dob_view.setVisibility(View.VISIBLE);
			age_view.setVisibility(View.GONE);
			age_layout.setVisibility(View.GONE);
		}

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				selectedListItem = capturelist.get(position);
			}
		});
		header_loc_name.setText("Register");

		editprofile.setOnClickListener(this);
		confirm.setOnClickListener(this);
		privacy_policy = (CheckBox) findViewById(R.id.privacy_policy);
		mCapturedimage_Imageview.setOnClickListener(this);
		image_confirm.setOnClickListener(this);
		image_cancel.setOnClickListener(this);

		alertDialog = new Dialog(this);
		alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		alertDialog.setCancelable(false);
		alertDialog.setContentView(R.layout.toggole_button);
		policy_text = (TextView) alertDialog.findViewById(R.id.policy_text);
		alert_close = (TextView) alertDialog.findViewById(R.id.alert_close);

		privacy = SharedPrefsUtil.getStringPreference(context, "privacy");
		terms = SharedPrefsUtil.getStringPreference(context, "terms");
		sendRequest(ApiRequestHelper.getContent(context, "PRIVACY_POLICY", "4.1.1"));
		if (Validate.notEmpty(privacy))
		{
			policy_text.setText(privacy + "");
		}
		segmented3 = (SegmentedGroup) alertDialog.findViewById(R.id.segmented3);
		segmented3.check(R.id.privacy);
		segmented3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(final RadioGroup radioGroup, final int checkedId)
			{
				switch (checkedId)
				{

					case R.id.privacy:
						isPrivacy = false;
						if (Validate.notEmpty(privacy))
						{
							policy_text.setText(privacy + "");
						}
						else
						{

							policy_text.setText(privacyPolicy + "");
							sendRequest(ApiRequestHelper.getContent(context, "PRIVACY_POLICY", "4.1.1"));
						}
						break;
					case R.id.terms:
						isPrivacy = true;
						if (Validate.notEmpty(terms))
						{
							policy_text.setText(terms + "");
						}
						else
						{

							if (Validate.notEmpty(terms_of_use))
							{
								policy_text.setText(terms_of_use + "");
							}
							else
							{
								sendRequest(ApiRequestHelper.getContent(context, "TERMS_OF_USE", "4.1.1"));
							}
						}
						break;

				}
			}
		});

		alert_close.setOnClickListener(this);
		privacy_policy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if (isChecked)
				{
					if (RestApiCallUtil.isOnline(context))
					{
						alertDialog.show();
					}
					else
					{
						RestApiCallUtil.NetworkError(context);
					}
				}
			}
		});

		//		sendRequest(ApiRequestHelper.getContent("PRIVACY_POLICY", "4.1.1"));

	}

	private void termAndCondition()
	{
		try
		{
			String termsText = "By Registering With Us You Agree To Our \n";
			String privacyLink = " <a href=>Privacy Policy</a>";
			privacyLink = "<font color='#0076bc'>" + privacyLink + "</font>";
			String and = " and";
			String termsLink = " <a href =>Terms & Conditions</a>";
			termsLink = "<font color='#0076bc'>" + termsLink + "</font>";
			String allText = termsText + privacyLink + and + termsLink;
			String text = "By Registering With Us You Agree To Our \n <font color=\"blue\"> <a href=>Privacy Policy</a></font> and <font color=\"blue\"><a href=>Terms & Conditions</a></font>";

			/*text = "Driver is nearby "
			+ "<font color=\"#E72A02\"><bold>"
			+ "43, KR Rd, Tata Silk Farm, Jayanagar"
			+ "</bold></font>"
			+ " and he is "
			+ "<font color=\"#B92000\"><bold>"
			+ "11 km"
			+ "</bold></font>"
			+ " & "
			+ "<font color=\"#B92000\"><bold>"
			+ "20 mins"
			+ "</bold></font>"
			+ " away from your current location.";*/
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
			{
				termcondition.setText(Html.fromHtml(termsText + privacyLink + and, Html.FROM_HTML_MODE_LEGACY) + ""
						+ Html.fromHtml(termsLink, Html.FROM_HTML_MODE_LEGACY));
			}
			else
			{
				termcondition.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
			}

		}
		catch (Exception e)
		{
		}
	}

	@Override
	public void onClick(View v)
	{
		Animation bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);

		switch (v.getId())
		{
			case R.id.cancel:
				confirm_footer.setVisibility(View.VISIBLE);
				hidePopup.startAnimation(bottomDown);
				hidePopup.setVisibility(View.INVISIBLE);
				break;

			case R.id.confirm:
				confirm_footer.setVisibility(View.VISIBLE);
				hidePopup.startAnimation(bottomDown);
				hidePopup.setVisibility(View.INVISIBLE);
				handleTaskWithUserPermission(CAMERA_REQUES_CODE);

				break;

			case R.id.capture_pic:
				capturelist = new ArrayList<String>();
				capturelist.add("Camera");
				capturelist.add("Gallery");
				setPopupListAdapter(capturelist);

				break;
			case R.id.edit_profile:

				Intent intent = new Intent();
				intent.putExtra(Constants.registered_name, name);
				intent.putExtra(Constants.registered_mobile, mobile);
				intent.putExtra(Constants.registered_email, email);
				intent.putExtra(Constants.registered_dob, dob);
				intent.putExtra(Constants.registered_gender, gender);
				intent.putExtra(Constants.registered_fname, fname);
				intent.putExtra(Constants.registered_lname, lname);
				intent.putExtra(Constants.registered_salutation, salutation);
				intent.putExtra(Constants.registered_apprxage, age);
				intent.putExtra(Constants.registered_years, years + "");
				intent.putExtra(Constants.registered_months, months + "");
				intent.putExtra(Constants.registered_days, days + "");

				setResult(RegistrationScreen.RESULT_CODE_EDIT, intent);
				finish();
				break;

			case R.id.alert_close:
				if (alertDialog != null)
				{
					alertDialog.dismiss();
				}
				break;
			case R.id.mconfirm:

				if (RestApiCallUtil.isOnline(context))
				{
					if (privacy_policy.isChecked())
					{

						Map<String, String> params = new HashMap<String, String>();
						params.put(Constants.IN_TITLE, salutation);
						params.put(Constants.IN_FIRST_NAME, fname);
						params.put(Constants.IN_LAST_NAME, lname);
						params.put(Constants.IN_DOB, RestApiCallUtil.dateToEpoch(dob));
						params.put(Constants.IN_AGE_YYY, years + "");
						params.put(Constants.IN_AGE_MM, months + "");
						params.put(Constants.IN_AGE_DD, days + "");
						if (gender != null && gender.equalsIgnoreCase("Male"))
						{
							params.put(Constants.IN_GENDER, "M");
						}
						else
						{
							params.put(Constants.IN_GENDER, "F");
						}
						params.put(Constants.IN_MOBILE_NO, mobile);
						params.put(Constants.IN_ADDRESS1, "");
						params.put(Constants.IN_ADDRESS2, "");
						params.put(Constants.IN_LANDMARK, "");
						params.put(Constants.IN_CITY, City);
						params.put(Constants.IN_STATE, State);
						params.put(Constants.IN_COUNTRY, "");
						params.put(Constants.IN_EMAIL_ID, email);
						params.put(Constants.IN_ZIP, "");
						params.put(Constants.IN_DEVICE_ID, Constants.devicetobepassed);
						params.put(Constants.IN_OSTYPE, "ANDROID");
						params.put(Constants.IN_OSVERSION, android.os.Build.VERSION.RELEASE);
						params.put(Constants.IN_MYSRLVER, getAppversion());

						try
						{
							RestApiCallUtil.postServerResponse(context, ApiRequestReferralCode.USER_REGISTRATION,
									params);
						}
						catch (Exception e)
						{

						}

					}
					else
					{
						Toast.makeText(context, "Please accept the privacy policy", Toast.LENGTH_SHORT).show();
					}

				}
				else
				{
					RestApiCallUtil.NetworkError(context);
				}
				break;

			case R.id.termcondition:
				if (RestApiCallUtil.isOnline(context))
				{
					alertDialog.show();
				}
				else
				{
					RestApiCallUtil.NetworkError(context);
				}
				break;

		}
	}

	private String getAppversion()
	{
		PackageManager manager = context.getPackageManager();
		PackageInfo info = null;
		try
		{
			info = manager.getPackageInfo(context.getPackageName(), 0);
		}
		catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return info.versionName;
	}

	@Override
	public void onSuccessResponse(ApiRequestReferralCode referralCode, String result, HashMap<String, String> value)
	{
		switch (referralCode)
		{
			case USER_REGISTRATION:
				if (result != null)
				{
					Util.killOtpReg();
					Intent intent1 = new Intent(ConfirmRegistation.this, OTPRegistration.class);
					intent1.putExtra(Constants.registered_mobile, mobile);
					startActivity(intent1);
					finish();
					/*showSettingsAlert();*/
				}
				break;
		}
	}

	public void showSettingsAlert()
	{
		try
		{
			android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ConfirmRegistation.this,
					AlertDialog.THEME_HOLO_LIGHT);
			builder.setMessage("Login id/password is sent to your registered mobile no/email address")
					.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							try
							{

								ConfirmRegistation.this.finish();
								Intent intent = new Intent(ConfirmRegistation.this, Dashboard.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(intent);

							}
							catch (Exception e)
							{

							}

							dialog.cancel();
							finish();
						}
					});
			builder.show();
		}
		catch (Exception e)
		{

		}
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

				mCapturedimage_Imageview.setImageBitmap(capturedImage);
			}
			catch (Exception e)
			{

			}
		}

		if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE)
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

	@Override
	public void onImagePreExecute()
	{

	}

	@Override
	public void onImagePostExecute(boolean result)
	{
		if (progressDialog != null)
		{
			progressDialog.cancel();
		}

		Toast.makeText(ConfirmRegistation.this, result ? "Sent succesfully." : "Failed to send.", Toast.LENGTH_SHORT)
				.show();
	}

	private String convertImageStr(Bitmap image)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] imageBytes = baos.toByteArray();
		String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
		return encodedImage;
	}

	private void captureImage()
	{
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

		startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putParcelable("file_uri", fileUri);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		fileUri = savedInstanceState.getParcelable("file_uri");
	}

	private void previewCapturedImage()
	{
		try
		{
			mCapturedimage_Imageview.setVisibility(View.VISIBLE);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 8;

			capturedImage = BitmapFactory.decodeFile(fileUri.getPath(), options);

			try
			{
				ExifInterface ei = new ExifInterface(fileUri.getPath());
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

			mCapturedimage_Imageview.setImageBitmap(capturedImage);
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
	}

	public Uri getOutputMediaFileUri(int type)
	{
		return Uri.fromFile(getOutputMediaFile(type));
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
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] b = baos.toByteArray();
		String temp = Base64.encodeToString(b, Base64.DEFAULT);
		return temp;
	}

	private void setPopupListAdapter(ArrayList<String> popupLstItems)
	{
		ArrayAdapter<String> ad = new ArrayAdapter<String>(this, R.layout.textcenter, R.id.textItem, capturelist);
		listView.setAdapter(ad);

		Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

		hidePopup.startAnimation(bottomUp);
		hidePopup.setVisibility(View.VISIBLE);
		confirm_footer.setVisibility(View.GONE);

	}

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

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
	}

	private void setAboutUsContent(JSONArray jArray)
	{

		try
		{
			if (Validate.notNull(jArray))
			{
				mPrivacyPolicyList.clear();
				for (int i = 0; i < jArray.length(); i++)
				{
					JSONObject jsonObject = jArray.getJSONObject(i);
					AboutUsData aboutUsData = new AboutUsData();
					aboutUsData.setSCREEN_ID(screen_id);
					aboutUsData.setCONTENT(jsonObject.getString(content));
					aboutUsData.setMYSRLVER(jsonObject.getString(mysrlver));
					aboutUsData.setSCREEN_NAME(jsonObject.getString(screen_name));
					mPrivacyPolicyList.add(aboutUsData);
				}
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}

		if (mPrivacyPolicyList != null)
		{

			for (AboutUsData aboutUsData : mPrivacyPolicyList)
			{

				if (aboutUsData.getSCREEN_NAME() != null)
				{

					if (aboutUsData.getCONTENT() != null && !aboutUsData.getCONTENT().equalsIgnoreCase("null"))
					{

						if (isPrivacy)
						{
							terms_of_use = aboutUsData.getCONTENT() + "";
						}
						else
						{
							privacyPolicy = aboutUsData.getCONTENT() + "";
						}
						policy_text.setText(aboutUsData.getCONTENT() + "");
					}

				}
			}

		}

	}

}