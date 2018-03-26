package com.srllimited.srl;

import java.util.ArrayList;
import java.util.List;

import com.srllimited.srl.adapters.BmiCalculationListAdapter;
import com.srllimited.srl.data.BmiUsersData;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.utilities.AppDataBaseHelper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Codefyne on 17-02-2017.
 */

public class BmiListView extends MenuBaseActivity implements View.OnClickListener
{
	public static Activity bmilist;

	Context context;

	RecyclerView bmi_listView;

	TextView nodata;

	RecyclerView.Adapter mAdapter;

	ArrayList<BmiUsersData> _bmiUsersData = new ArrayList<>();

	AppDataBaseHelper appDb;

	BmiUsersData _BmiUsersData = null;

	private Dialog promoDialog;

	private TextView alert_no;

	private TextView alert_yes;

	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.bmi_list_view);
		context = BmiListView.this;
		bmilist = this;
		promoDialog = new Dialog(this);
		promoDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		promoDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		promoDialog.setCancelable(false);
		promoDialog.setContentView(R.layout.bmi_clear_data_popup_alert);
		alert_yes = (TextView) promoDialog.findViewById(R.id.alert_yes);
		alert_no = (TextView) promoDialog.findViewById(R.id.alert_no);

		header_loc_name.setText("BMI History");
		header_loc_name.setEnabled(false);
		history.setText("Clear");
		history.setVisibility(View.VISIBLE);
		history.setOnClickListener(this);
		alert_yes.setOnClickListener(this);
		alert_no.setOnClickListener(this);

		bmi_listView = (RecyclerView) findViewById(R.id.bmi_listView);
		bmi_listView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
		bmi_listView.setHasFixedSize(true);

		nodata = (TextView) findViewById(R.id.nodata);

		try
		{
			appDb = new AppDataBaseHelper(getApplicationContext());
			_BmiUsersData = appDb.getBmiDetails();
			_bmiUsersData.add(_BmiUsersData);

			List<BmiUsersData> bookaTestDatas = new ArrayList<>();
			bookaTestDatas = appDb.getBmiListOfDetails();
			List<BmiUsersData> mBmiUsersData = new ArrayList<>();
			for (BmiUsersData bookdata : bookaTestDatas)
			{
				mBmiUsersData.add(bookdata);
			}
			if (mBmiUsersData.size() != 0)
			{
				nodata.setVisibility(View.GONE);
				mAdapter = new BmiCalculationListAdapter(BmiListView.this, mBmiUsersData);
				bmi_listView.setAdapter(mAdapter);
			}
			else
			{
				nodata.setVisibility(View.VISIBLE);
			}
		}
		catch (Exception e)
		{

		}

	}

	@Override
	public void onClick(View view)
	{

		switch (view.getId())
		{
			case R.id.history:
				if (promoDialog != null)
				{
					promoDialog.show();
				}
				break;
			case R.id.alert_yes:
				appDb.deleteBmiData();
				Toast.makeText(getApplicationContext(), "Your BMI Data has been Cleared Successfully",
						Toast.LENGTH_LONG).show();
				if (promoDialog != null)
				{
					promoDialog.dismiss();
					Util.hideSoftKeyboard(context, view);
				}
				bmi_listView.setAdapter(null);
				nodata.setVisibility(View.VISIBLE);
				history.setClickable(false);
				/*Intent in = new Intent(BmiListView.this, Dashboard.class);
				startActivity(in);*/

				break;
			case R.id.alert_no:
				if (promoDialog != null)
				{
					promoDialog.dismiss();
					Util.hideSoftKeyboard(context, view);
				}
				break;
		}
	}
}