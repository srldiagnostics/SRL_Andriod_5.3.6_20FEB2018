package com.srllimited.srl;

import java.util.ArrayList;
import java.util.List;

import com.srllimited.srl.adapters.AddPAteintFamilyMembersAdapter;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.UserData;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.utilities.AppDataBaseHelper;
import com.srllimited.srl.widget.RoundCornerProgressView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AddPatientActivity extends MenuBaseActivity implements View.OnClickListener
{

	public static final int RESULT_CODE_FMAILY = 1;
	public static Activity homevisit;
	public static Context context;
	private final Handler mHandler = new Handler();
	TextView progress_count_text, progress_text, member;
	TextView popupheader;
	FrameLayout progress_frame_layout;
	LinearLayout hidePopup;
	ImageView confirm, cancel;
	TextView patient_id, name;
	ArrayList<String> patientList = new ArrayList<String>();
	String selectedListItem = "MySelf";
	UserData _userAppData;
	LinearLayout bottomHRLID, myself, someoneelse;
	RelativeLayout myfamily;
	RecyclerView famillyRecyclerview;
	RecyclerView.Adapter mAdapter;
	//Update Priofile
	ArrayList<UserData> _updateuserData = new ArrayList<>();
	AppDataBaseHelper appDb = new AppDataBaseHelper(this);
	private RoundCornerProgressView mProgressView;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.select_patient);
		context = AddPatientActivity.this;
		homevisit = this;
		Constants.isFamilySel = false;
		appDb = new AppDataBaseHelper(getApplicationContext());
		homevisit = this;
		Constants.isRadio = true;
		String username = "";

		patient_id = (TextView) findViewById(R.id.patient_id);
		name = (TextView) findViewById(R.id.name);

		header_loc_name.setText("Select Customer");
		header_loc_name.setEnabled(false);
		progress_frame_layout = (FrameLayout) findViewById(R.id.progress_frame_layout);
		listView = (ListView) findViewById(com.srllimited.srl.R.id.popup_list);
		progress_text = (TextView) findViewById(R.id.progress_text);
		progress_count_text = (TextView) findViewById(R.id.progress_count_text);
		member = (TextView) findViewById(R.id.member);
		popupheader = (TextView) findViewById(R.id.popup_header);
		hidePopup = (LinearLayout) findViewById(R.id.hidePopup);
		cancel = (ImageView) findViewById(com.srllimited.srl.R.id.cancel);
		confirm = (ImageView) findViewById(com.srllimited.srl.R.id.confirm);
		famillyRecyclerview = (RecyclerView) findViewById(R.id.famillyRecyclerview);
		bottomHRLID = (LinearLayout) findViewById(R.id.bottomHRLID);
		myself = (LinearLayout) findViewById(R.id.myself);
		someoneelse = (LinearLayout) findViewById(R.id.someoneelse);
		myfamily = (RelativeLayout) findViewById(R.id.myfamily);

		SharedPrefsUtil.setStringPreference(context, "selectedPerson", "");
		member.setOnClickListener(this);

		progress_text.setText(getResources().getString(R.string.proceed));
		progress_count_text.setText(getResources().getString(R.string.progress3));
		confirm.setOnClickListener(this);
		cancel.setOnClickListener(this);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				selectedListItem = patientList.get(position);
			}
		});
		mProgressView = (RoundCornerProgressView) findViewById(R.id.rcpv_progress_view);
		progress_text.setOnClickListener(this);
		setProgress(55, true);
		bottomHRLID.setOnClickListener(this);
		getData(Util.getStoredUsername(context));
	}

	@Override
	public void onClick(final View view)
	{
		Animation bottomDown = AnimationUtils.loadAnimation(this, R.anim.bottom_down);
		switch (view.getId())
		{

			case R.id.bottomHRLID:
				Util.killAddMember();
				Intent intenti = new Intent(AddPatientActivity.this, AddMemberActivity.class);
				startActivity(intenti);

				break;
			case R.id.cancel:

				progress_frame_layout.setVisibility(View.VISIBLE);

				hidePopup.startAnimation(bottomDown);
				hidePopup.setVisibility(View.INVISIBLE);
				break;

			case R.id.confirm:

				member.setText("MySelf");
				member.setText(selectedListItem);

				progress_frame_layout.setVisibility(View.VISIBLE);

				hidePopup.startAnimation(bottomDown);
				hidePopup.setVisibility(View.INVISIBLE);

				if (selectedListItem.equalsIgnoreCase("My Family"))
				{
					Constants.isPatientDetails = false;
					Constants.isFamilySel = true;
					//					Util.killMyFamily();
					//					Intent intent = new Intent(AddPatientActivity.this, MyFamilyActivity.class);
					//					startActivityForResult(intent, RESULT_CODE_FMAILY);
					selectMyfamily();
				}

				if (selectedListItem.equalsIgnoreCase("MySelf"))
				{
					selectMyself();
					Constants.isPatientDetails = false;
					Constants.isFamilySel = false;
					getData(Util.getStoredUsername(context));

				}

				if (selectedListItem.equalsIgnoreCase("Someone Else"))
				{

					Constants.isPatientDetails = true;
					//					Util.killReg();
					//					Intent intent = new Intent(AddPatientActivity.this, RegistrationScreen.class);
					//					startActivity(intent);

					selectSomeoneelse();
				}

				break;

			case R.id.progress_text:
				if (selectedListItem.equalsIgnoreCase("My Family"))
				{

					String uname = SharedPrefsUtil.getStringPreference(context, "selectedPerson");

					if (Validate.notEmpty(uname) && !uname.equalsIgnoreCase(Util.getStoredUsername(context)))
					{

						Constants.isPatEdited = false;
						if (Constants.isPatientDetails)
						{
							Util.killReg();
							Intent intent = new Intent(AddPatientActivity.this, RegistrationScreen.class);
							startActivity(intent);
						}
						else
						{
							Util.killCollection();
							Intent intent = new Intent(context, CollectionActivity.class);
							startActivity(intent);
						}
					}
					else
					{
						Toast.makeText(context, "Please select a family member", Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					Constants.isPatEdited = false;
					if (Constants.isPatientDetails)
					{
						Util.killReg();
						Intent intent = new Intent(AddPatientActivity.this, RegistrationScreen.class);
						startActivity(intent);
					}
					else
					{
						Util.killCollection();
						Intent intent = new Intent(context, CollectionActivity.class);
						startActivity(intent);
					}
				}
				break;

			case R.id.member:
				popupheader.setText("Select Patient");
				progress_frame_layout.setVisibility(View.GONE);
				patientList = new ArrayList<String>();
				patientList.add("MySelf");
				patientList.add("My Family");
				patientList.add("Someone else");
				setPopupListAdapter(patientList);

				break;

		}
	}

	private void setPopupListAdapter(ArrayList<String> popupLstItems)
	{
		ArrayAdapter<String> ad = new ArrayAdapter<String>(this, R.layout.textcenter, R.id.textItem, patientList);
		listView.setAdapter(ad);

		Animation bottomUp = AnimationUtils.loadAnimation(this, R.anim.bottom_up);

		hidePopup.startAnimation(bottomUp);
		hidePopup.setVisibility(View.VISIBLE);
	}

	private void setProgress(final float progress, final boolean postDelayed)
	{
		if (postDelayed)
		{
			mHandler.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					mProgressView.setProgress(progress);
				}
			}, 200);
		}
		else
		{
			mProgressView.setProgress(progress);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_CODE_FMAILY)
		{
			String ptntcode = SharedPrefsUtil.getStringPreference(context, "selectedPerson");

			String selname = SharedPrefsUtil.getStringPreference(context, "selectedName");

			if (Validate.notEmpty(ptntcode))
			{
				patient_id.setText(ptntcode);
			}

			if (Validate.notEmpty(selname))
			{
				name.setText(selname);
			}

		}
		return;

	}

	private void getData(String ptntcode)
	{

		try
		{
			UserData userData = null;
			try
			{
				userData = appDb.getSelectedUserDetail(ptntcode);
			}
			catch (Exception e)
			{

			}
			String selname = "";
			if (userData != null)
			{
				//selname = userData.getFirst_name() + " " + userData.getLast_name() + "";
				if (userData.getFirst_name() != null && !userData.getFirst_name().equalsIgnoreCase("null"))
				{
					selname = userData.getFirst_name() + "";
					if (userData.getLast_name() != null && !userData.getLast_name().equalsIgnoreCase("null"))
					{
						selname = userData.getFirst_name() + " " + userData.getLast_name() + "";
					}
				}
			}

			patient_id.setText(ptntcode + "");
			name.setText(selname + "");
			SharedPrefsUtil.setStringPreference(context, "selectedPerson", ptntcode + "");
			SharedPrefsUtil.setStringPreference(context, "selectedName", selname + "");
		}
		catch (Exception e)
		{

		}
	}

	private void selectMyself()
	{
		myself.setVisibility(View.VISIBLE);
		myfamily.setVisibility(View.GONE);

		someoneelse.setVisibility(View.GONE);
	}

	private void selectMyfamily()
	{
		myself.setVisibility(View.GONE);
		myfamily.setVisibility(View.VISIBLE);

		someoneelse.setVisibility(View.GONE);
	}

	private void selectSomeoneelse()
	{
		myself.setVisibility(View.GONE);
		myfamily.setVisibility(View.GONE);

		someoneelse.setVisibility(View.VISIBLE);
	}

	private void myFamilymemebersList()
	{
		famillyRecyclerview = (RecyclerView) findViewById(R.id.famillyRecyclerview);
		famillyRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		famillyRecyclerview.setHasFixedSize(true);

		try
		{
			appDb = new AppDataBaseHelper(getApplicationContext());
			_updateuserData = new ArrayList<>();
			_userAppData = appDb.getLoginDetails();
			if (!Util.getStoredUsername(context).isEmpty() && !Util.isRem(context))
			{
				if (_userAppData != null)
				{
					_updateuserData.add(_userAppData);
					List<UserData> bookaTestDatas = new ArrayList<>();
					bookaTestDatas = appDb.getUSersList();
					List<UserData> mUSerData = new ArrayList<>();
					for (UserData bookdata : bookaTestDatas)
					{
						if (bookdata.getStatus().equalsIgnoreCase("false"))
						{
							mUSerData.add(bookdata);
						}
						else
						{
						}
					}
					if (mUSerData.size() != 0)
					{
						mAdapter = new AddPAteintFamilyMembersAdapter(context, mUSerData);
						famillyRecyclerview.setAdapter(mAdapter);
					}
				}
			}
			else
			{
				Util.killLogin();
				Intent intent = new Intent(this, LoginScreenActivity.class);
				startActivity(intent);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@Override
	protected void onResume()
	{
		myFamilymemebersList();
		super.onResume();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		SharedPrefsUtil.setStringPreference(context, "selectedPerson", "");
	}
}