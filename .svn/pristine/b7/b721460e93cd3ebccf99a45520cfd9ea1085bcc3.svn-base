package com.srllimited.srl;

import java.util.ArrayList;
import java.util.List;

import com.srllimited.srl.adapters.MyFamilyMembersAdapter;
import com.srllimited.srl.data.UserData;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.utilities.AppDataBaseHelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

//import com.codefyne.mysrl.adapters.MyFamilyMembersAdapter;

public class MyFamilyActivity extends MenuBaseActivity implements View.OnClickListener
{

	public static Activity myfamily;
	Context context;
	LinearLayout bottomHRLID;
	RecyclerView famillyRecyclerview;
	RecyclerView.Adapter mAdapter;
	//Update Priofile
	ArrayList<UserData> _updateuserData = new ArrayList<>();

	AppDataBaseHelper appDb = new AppDataBaseHelper(this);

	UserData _userAppData = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.my_family_list_activity);
		context = MyFamilyActivity.this;
		myfamily = this;
		bottomHRLID = (LinearLayout) findViewById(R.id.bottomHRLID);
		famillyRecyclerview = (RecyclerView) findViewById(R.id.famillyRecyclerview);
		header_loc_name.setText("My Family");
		header_loc_name.setEnabled(false);
		myFamilymemebersList();
		bottomHRLID.setOnClickListener(this);
	}

	@Override
	public void onClick(final View view)
	{
		switch (view.getId())
		{
			case R.id.bottomHRLID:
				Util.killAddMember();
				Intent intent = new Intent(MyFamilyActivity.this, AddMemberActivity.class);
				startActivity(intent);

				break;

		}
	}

	//
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
						mAdapter = new MyFamilyMembersAdapter(MyFamilyActivity.this, mUSerData);
						famillyRecyclerview.setAdapter(mAdapter);
					}
				}
			}
			else
			{
				Util.killLogin();
				Intent intent = new Intent(MyFamilyActivity.this, LoginScreenActivity.class);
				startActivity(intent);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public void updateAdapter()
	{
		mAdapter.notifyDataSetChanged(); //update adapter
	}

	@Override
	protected void onResume()
	{
		myFamilymemebersList();
		super.onResume();
	}
}