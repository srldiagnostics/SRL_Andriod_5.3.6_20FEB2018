package com.srllimited.srl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by RuchiTiwari on 1/14/2017.
 */

public class DateUtil
{

	public static long dateToEpoch(String enteredDate)
	{
		long epoch = 0;
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
		try
		{
			Date date = format.parse(enteredDate);
			System.out.println(date);
			epoch = date.getTime();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return epoch;
	}

	public static long dob(String enteredDate)
	{
		long epoch = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			Date date = format.parse(enteredDate);
			System.out.println(date);
			epoch = date.getTime();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return epoch;
	}

	public static String ageCal(String enteredDate)
	{
		long epoch = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MMM-dd");
		try
		{
			Date date = format.parse(enteredDate);
			System.out.println(date);
			epoch = date.getTime();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return epoch + "";
	}

	public static String epochToDate(long epoch)
	{
		return new SimpleDateFormat("dd-MM-yyyy").format(new Date(epoch));
	}

	public static String epochToTime(long epoch)
	{
		return new SimpleDateFormat("HHmm").format(new Date(epoch));

	}

	public static String epochToTimeGMT(long epoch)
	{
		//        epoch = epoch - 18000000;
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("HHmm");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormatGmt.format(epoch);
	}

	public static String epochToDateTime(long epoch)
	{
		/*SimpleDateFormat dateFormatGmt = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormatGmt.format(new Date(epoch));*/
		/*SimpleDateFormat inputFormat = new SimpleDateFormat(
				"EEE MMM dd HH:mm:ss 'GMT' yyyy", Locale.US);
		inputFormat.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));

		SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy h:mm a");
		// Adjust locale and zone appropriately

		String  intput = inputFormat.format(new Date(epoch));
		String outputText = outputFormat.format(epoch);
			dateToEpoch(intput);*/

		return new SimpleDateFormat("dd-MM-yyyy hh:mm a").format(new Date(epoch));
	}

	public static String fetchGraphDate(long epoch)
	{
		/*Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(1507112706);
		String date = DateFormat.format("MMM yy", cal).toString();*/

		return new SimpleDateFormat("MMM yy").format(new Date(epoch));
	}

	public static String fetchGraphDate11(int epoch)
	{
		/*Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(1507112706);
		String date = DateFormat.format("MMM yy", cal).toString();*/
		/*Date date = new Date(epoch *1000);
		DateFormat format = new SimpleDateFormat("MMM yy");
		//format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
		String formatted = format.format(date);*/

		return new SimpleDateFormat("MMM yy", Locale.US).format(new Date(epoch));
	}

	public static String colepochToDate(long epoch)
	{
		return new SimpleDateFormat("dd-MMM-yyyy HH:mm").format(new Date(epoch));
	}

	public static String notificationepochToDate(long epoch)
	{
		/*Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(epoch);
		calendar.getTime().toGMTString();*/
		//calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
		try {
			 if(epoch==0){
			 	return "";
			 }
			String Date1 = "";
			String Time1 = "";
			SimpleDateFormat dateFormatGmt = new SimpleDateFormat("hh:mm a");
			SimpleDateFormat dateFormatGmt1 = new SimpleDateFormat("dd-MMM-yyyy");
			dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
			Date1 = dateFormatGmt1.format(epoch);
			Time1 = dateFormatGmt.format(epoch);
			return Date1 + " "
					+ Time1;/*new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a",Locale.getDefault()).format(calendar.getTime())*/
		}
		catch (Exception e){
			return "";
		}
		/*	SimpleDateFormat dateFormatGmt = new SimpleDateFormat("hh:mm a");
			dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
			return dateFormatGmt.format(epoch);*/
	}

	public static String dateTimeToEpoch(String enteredDate)
	{
		long epoch = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try
		{
			Date date = format.parse(enteredDate);
			System.out.println(date);
			epoch = date.getTime();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return epoch + "";
	}

	public static long dateTimeToEpoch1(String enteredDate)
	{
		long epoch = 0;
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
		try
		{
			Date date = format.parse(enteredDate);
			System.out.println(date);
			epoch = date.getTime();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return epoch;
	}

	public static long colTime(String enteredDate)
	{

		long epoch = 0;
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		try
		{
			Date date = format.parse(enteredDate);
			System.out.println(date);
			epoch = date.getTime();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return epoch;
	}

	public static long paytime(String enteredDate)
	{

		long epoch = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		try
		{
			Date date = format.parse(enteredDate);
			System.out.println(date);
			epoch = date.getTime();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return epoch;
	}

	public static long ebstime(String enteredDate)
	{

		long epoch = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
		try
		{
			Date date = format.parse(enteredDate);
			System.out.println(date);
			epoch = date.getTime();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return epoch;
	}

	public static long timeequlality(String enteredDate)
	{
		enteredDate.replace("AM", "am");
		enteredDate.replace("PM", "pm");
		Log.e("entereddate", enteredDate + "");
		long epoch = 0;
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
		try
		{
			Date date = format.parse(enteredDate);
			System.out.println(date);
			epoch = date.getTime();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return epoch;
	}

	public static String coldateC(String enteredDate)
	{
		long epoch = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		try
		{
			Date date = format.parse(enteredDate);
			System.out.println(date);
			epoch = date.getTime();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return epoch + "";
	}

	public static String agetoDob(String enteredDate)
	{
		long epoch = 0;
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
		try
		{
			Date date = format.parse(enteredDate);
			System.out.println(date);
			epoch = date.getTime();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return epoch + "";
	}

	public static long labtiming(String labtime)
	{
		long epoch = 0;
		SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
		try
		{
			Date date = format.parse(labtime);
			System.out.println(date);
			epoch = date.getTime();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return epoch;
	}

	public static long labtiming1(String labtime)
	{
		long epoch = 0;
		SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
		try
		{
			Date date = format.parse(labtime);
			System.out.println(date);
			epoch = date.getTime();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return epoch;
	}

	public String getDateCurrentTimeZone(long timestamp)
	{
		try
		{
			Calendar calendar = Calendar.getInstance();
			TimeZone tz = TimeZone.getDefault();
			calendar.setTimeInMillis(timestamp * 1000);
			calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date currenTimeZone = (Date) calendar.getTime();
			return sdf.format(currenTimeZone);
		}
		catch (Exception e)
		{
		}
		return "";
	}

}
