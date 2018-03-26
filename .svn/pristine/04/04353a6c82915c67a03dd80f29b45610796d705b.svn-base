package com.srllimited.srl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.ReportParentList;
import com.srllimited.srl.data.ReportsData;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by Ruchi Tiwari on 12/13/2016.
 */

public class MyReportCompleteDetails extends MenuBaseActivity
{
	public static Activity myreportcompletedetails;
	ScrollView internalLLID;
	TextView mHeader_name;

	Context context;

	String Action1 = "", Action2 = "", Action3 = "", Action4 = "";

	List<ReportParentList> storeReportsFromMap = new ArrayList<>();

	HashMap<String, List<ReportsData>> accessionList = new HashMap<>();

	List<ReportsData> mReportsDatas = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.myreportid_complete_details);

		context = MyReportCompleteDetails.this;
		myreportcompletedetails = this;
		mReportsDatas = Constants.sReportsDatas;

		//		orderDB = new OrderDatabase(getApplicationContext());
		header_loc_name.setText("My Reports");
		header_loc_name.setEnabled(false);
		internalLLID = (ScrollView) findViewById(R.id.internalSVID);

		accessionList = new HashMap<>();
		storeReportsFromMap = new ArrayList<>();

		if (mReportsDatas != null)
		{
			for (int i = 0; i < mReportsDatas.size(); i++)
			{

				//				if (accessionList != null)
				//				{
				if (!accessionList.containsKey(mReportsDatas.get(i).getPARENT_PRDCT_ID() + ""))
				{

					List<ReportsData> reportsDatas = new ArrayList<>();

					for (ReportsData reports : mReportsDatas)
					{
						if (reports.getACC_ID() != null)
						{
							if ((reports.getPARENT_PRDCT_ID() + "")
									.equalsIgnoreCase(mReportsDatas.get(i).getPARENT_PRDCT_ID() + ""))
							{

								reportsDatas.add(reports);
							}
						}
					}
					ReportParentList reportParentList = new ReportParentList();

					reportParentList.setParentid(mReportsDatas.get(i).getPARENT_PRDCT_ID() + "");
					reportParentList.setReportsDataList(reportsDatas);
					storeReportsFromMap.add(reportParentList);

					accessionList.put(mReportsDatas.get(i).getPARENT_PRDCT_ID() + "", reportsDatas);
					//					}
				}

			}
		}

	}

}
