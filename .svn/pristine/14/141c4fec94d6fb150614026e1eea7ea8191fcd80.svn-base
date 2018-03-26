package com.srllimited.srl.otp.sms;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

public class SMSHistoryReader
{

	public static final Uri SMS_URI = Uri.parse("content://sms/");

	private static final String BODY_COLUMN = "body";

	private static final String DATE_COLUMN = "date";

	private ContentResolver mContentResolver;

	public SMSHistoryReader(ContentResolver contentResolver)
	{
		this.mContentResolver = contentResolver;
	}

	public List<String> readCodeFromHistory(long sinceEpoch)
	{
		List<String> smsList = new ArrayList();
		Cursor cursor = this.mContentResolver.query(SMSHistoryReader.SMS_URI, new String[] { DATE_COLUMN, BODY_COLUMN },
				"date > " + sinceEpoch, null, DATE_COLUMN);
		if (cursor != null)
		{
			try
			{
				int bodyIndex = cursor.getColumnIndex(BODY_COLUMN);
				while (cursor.moveToNext())
				{
					smsList.add(cursor.getString(bodyIndex));
				}
			}
			catch (Exception e)
			{
			}
			finally
			{
				cursor.close();
			}
		}
		return smsList;
	}
}
