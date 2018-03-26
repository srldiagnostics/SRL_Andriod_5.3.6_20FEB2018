package com.srllimited.srl;

import static com.srllimited.srl.R.id.header_loc_name;

import com.srllimited.srl.util.TypefaceUtil;
import com.srllimited.srl.util.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by sri on 12/15/2016.
 */

public class OrderTrack extends AppCompatActivity
{
	public static Activity ordertrack;
	TextView orderNoTVID, thankyouTVID;
	TextView youTVID, call_confirm_youTVID, dateTVID;
	Button trackBTNID;
	Context context;
	TextView mHeader_name;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_track);

		context = OrderTrack.this;
		ordertrack = this;
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);

		mHeader_name = (TextView) findViewById(header_loc_name);
		TypefaceUtil.setTypeFace(context, TypefaceUtil.openSans_SemiBold, mHeader_name);
		mHeader_name.setText("My Order");

		orderNoTVID = (TextView) findViewById(R.id.orderNoTVID);
		thankyouTVID = (TextView) findViewById(R.id.thankyou);

		trackBTNID = (Button) findViewById(R.id.trackBTNID);
		orderNoTVID.setText("Your order number is SEA0234575");
		thankyouTVID.setVisibility(View.GONE);

		trackBTNID.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Util.killTrackDetails();
				Intent i = new Intent(OrderTrack.this, TrackingDetails.class);
				startActivity(i);
			}
		});

	}
}
