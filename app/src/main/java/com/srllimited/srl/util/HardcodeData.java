package com.srllimited.srl.util;

import java.util.ArrayList;

import com.srllimited.srl.adapters.DataAdapter;
import com.srllimited.srl.adapters.LocationAdapter;
import com.srllimited.srl.data.CityListData;
import com.srllimited.srl.data.LocationData;
import com.srllimited.srl.data.ScrollingListTemplate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Ruchi Tiwari on 04/12/16.
 */

public class HardcodeData
{
	public static ArrayList<ScrollingListTemplate> options = new ArrayList<ScrollingListTemplate>();

	public static ArrayList<CityListData> cityListData = new ArrayList<CityListData>();

	public static void populateRecyclerView(Context context, RecyclerView recyclerView)
	{
		options = new ArrayList<ScrollingListTemplate>();
		ScrollingListTemplate item = new ScrollingListTemplate();
		item.setHeader("Book A Test");
		item.setContent("Book a Lab Appointment or Home Collection Service");
		item.setImage("Book");

		options.add(item);

		item = new ScrollingListTemplate();
		item.setHeader("My Reports");
		item.setContent("Download latest reports and view historical reports ");
		item.setImage("Reports");

		options.add(item);

		item = new ScrollingListTemplate();
		item.setHeader("My Health Tracker");
		item.setContent("Track, compare and manage your health parameters");
		item.setImage("View Tracker");

		options.add(item);

		item = new ScrollingListTemplate();
		item.setHeader("My Family");
		item.setContent("Add family members, book tests and view their reports");
		item.setImage("Family");

		options.add(item);

		RecyclerView.Adapter adapter = new DataAdapter(context, options);
		recyclerView.setAdapter(adapter);

	}

	public static void populateCityist(Context context, RecyclerView recyclerView)
	{
		cityListData = new ArrayList<CityListData>();

		//		ScrollingListTemplate item = new ScrollingListTemplate();
		//		item.setHeader("Book A Test");
		//		item.setContent("Book home test, and save all your reports in your mobile");
		//		item.setImage("Book");
		//
		//		options.add(item);
		//
		//		item = new ScrollingListTemplate();
		//		item.setHeader("My Family");
		//		item.setContent("Add family members and view their lab results");
		//		item.setImage("Family");

		//		options.add(item);

		ArrayList<LocationData> locations = new ArrayList<>();
		if (cityListData != null)
		{
			for (CityListData data : cityListData)
			{
				locations.add(new LocationData(data.getCITY_NAME()));
			}
		}
		RecyclerView.Adapter adapter = new LocationAdapter(context, locations);
		recyclerView.setAdapter(adapter);
	}
}
