package com.srllimited.srl;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by sri on 12/15/2016.
 */

public class TrackingDetails extends Activity
{
	public static Activity trackingdetails;
	TextView myreportsTVID;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_tracked_details);
		trackingdetails = this;
		myreportsTVID = (TextView) findViewById(R.id.myreportsTVID);
		myreportsTVID.setText("Track your order");
	}
}
