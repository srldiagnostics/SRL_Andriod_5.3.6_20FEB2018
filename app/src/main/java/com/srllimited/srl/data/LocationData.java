package com.srllimited.srl.data;

/**
 * Created by codefyneandroid on 12-12-2016.
 */

public class LocationData
{

	private String mName;

	public LocationData(String name)
	{
		this.mName = name;
	}

	public String getName()
	{
		return mName;
	}

	public void setName(String name)
	{
		this.mName = name;
	}

	@Override
	public String toString()
	{
		return "LocationData{" + "Name='" + mName + '\'' + '}';
	}
}
