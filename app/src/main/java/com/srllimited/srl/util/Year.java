package com.srllimited.srl.util;

/**
 * Created by Codefyne on 04-05-2017.
 */

public class Year
{
	private static final String[] monthNames = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
			"Sep", "Oct", "Nov", "Dec" };

	int day;

	int month;

	int year;

	public Year(int day, int month, int year)
	{
		this.day = day;
		this.month = month;
		this.year = year;
	}

	@Override
	public String toString()
	{
		return day + "-" + monthNames[month - 1] + "-" + year;
	}

}
