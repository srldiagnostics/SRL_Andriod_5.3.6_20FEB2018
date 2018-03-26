package com.srllimited.srl.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.srllimited.srl.data.NotificationsData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NotificationDatabase extends SQLiteOpenHelper
{

	// Logcat tag
	private static final String LOG = "NotificationHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "NotificationDatabase";

	// Table Names
	private static final String TABLE_NOTIFICATION_HEADER = "notificationheader";

	// Common column names
	private static final String KEY_QUEUE_ID = "QUEUE_ID";

	private static final String KEY_PTNT_CD = "PTNT_CD";

	private static final String KEY_DT_TIME = "DT_TIME";

	private static final String KEY_MSG_TYP = "MSG_TYP";

	private static final String KEY_DISPLAY_FLAG = "DISPLAY_FLAG";

	private static final String KEY_MSG_TITLE = "MSG_TITLE";

	private static final String KEY_MSG_MAIN = "MSG_MAIN";

	// Table Create Statements
	// Todo table create statement
	private static final String CREATE_TABLE_HEADER = "CREATE TABLE " + TABLE_NOTIFICATION_HEADER + "(" + KEY_QUEUE_ID
			+ " INTEGER PRIMARY KEY," + KEY_PTNT_CD + " TEXT," + KEY_DT_TIME + " TEXT," + KEY_MSG_TYP + " TEXT,"
			+ KEY_DISPLAY_FLAG + " TEXT," + KEY_MSG_TITLE + " TEXT," + KEY_MSG_MAIN + " TEXT" + ")";

	public NotificationDatabase(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{

		// creating required tables
		db.execSQL(CREATE_TABLE_HEADER);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		//		// on upgrade drop older tables
		//		db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_BOOKTEST_PACKAGES);
		//
		//
		//		// create new tables
		//		onCreate(db);
	}

	// ------------------------ "todos" table methods ----------------//

	/*
	 * Creating a todo
	 */
	public long createHeaderData(NotificationsData notificationsData)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_QUEUE_ID, ((int) notificationsData.getQUEUE_ID()));
		values.put(KEY_PTNT_CD, notificationsData.getPTNT_CD());
		values.put(KEY_DT_TIME, notificationsData.getDT_TIME() + "");
		values.put(KEY_MSG_TYP, notificationsData.getMSG_TYP() + "");
		values.put(KEY_DISPLAY_FLAG, notificationsData.getDISPLAY_FLAG() + "");
		values.put(KEY_MSG_TITLE, notificationsData.getMSG_TITLE() + "");
		values.put(KEY_MSG_MAIN, notificationsData.getMSG_MAIN() + "");

		return db.insert(TABLE_NOTIFICATION_HEADER, null, values);
	}

	/*
	 * get single todo
	 */
	public NotificationsData getNotificationWithId(int todo_id)
	{
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATION_HEADER + " WHERE " + KEY_QUEUE_ID + " = " + todo_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
		{
			c.moveToFirst();
		}

		NotificationsData notificationsData = new NotificationsData();
		notificationsData.setQUEUE_ID(c.getInt(c.getColumnIndex(KEY_QUEUE_ID)));
		notificationsData.setPTNT_CD(c.getString(c.getColumnIndex(KEY_PTNT_CD)));
		notificationsData.setDT_TIME(c.getLong(c.getColumnIndex(KEY_DT_TIME)));
		notificationsData.setMSG_TYP(c.getInt(c.getColumnIndex(KEY_MSG_TYP)));
		notificationsData.setDISPLAY_FLAG(c.getString(c.getColumnIndex(KEY_DISPLAY_FLAG)));
		notificationsData.setMSG_TITLE(c.getString(c.getColumnIndex(KEY_MSG_TITLE)));

		notificationsData.setMSG_MAIN(c.getString(c.getColumnIndex(KEY_MSG_MAIN)));

		return notificationsData;
	}

	public List<NotificationsData> getAllNotifications()
	{
		List<NotificationsData> notificationsDataList = new ArrayList<NotificationsData>();

		String selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATION_HEADER;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst())
		{
			do
			{

				NotificationsData notificationsData = new NotificationsData();
				notificationsData.setQUEUE_ID(c.getInt(c.getColumnIndex(KEY_QUEUE_ID)));
				notificationsData.setPTNT_CD(c.getString(c.getColumnIndex(KEY_PTNT_CD)));
				notificationsData.setDT_TIME(c.getLong(c.getColumnIndex(KEY_DT_TIME)));
				notificationsData.setMSG_TYP(c.getInt(c.getColumnIndex(KEY_MSG_TYP)));
				notificationsData.setDISPLAY_FLAG(c.getString(c.getColumnIndex(KEY_DISPLAY_FLAG)));
				notificationsData.setMSG_TITLE(c.getString(c.getColumnIndex(KEY_MSG_TITLE)));

				notificationsData.setMSG_MAIN(c.getString(c.getColumnIndex(KEY_MSG_MAIN)));
				notificationsDataList.add(notificationsData);
			}
			while (c.moveToNext());
		}

		return notificationsDataList;
	}

	public String getNotificationIds(boolean isPersonal)
	{
		String notifications = "";
		String selectQuery = "";
		if (isPersonal)
		{
			//  msgType  1 and 3 select
			selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATION_HEADER + " WHERE " + KEY_MSG_TYP + "='1' OR "
					+ KEY_MSG_TYP + "='3'";
		}
		else
		{
			// msgType 2
			selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATION_HEADER + " WHERE " + KEY_MSG_TYP + "='2'";
		}

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst())
		{
			do
			{

				if (notifications.isEmpty())
					notifications = c.getInt(c.getColumnIndex(KEY_QUEUE_ID)) + "";
				else
					notifications = notifications + "," + c.getInt(c.getColumnIndex(KEY_QUEUE_ID)) + "";
			}
			while (c.moveToNext());
		}

		return notifications;
	}

	public List<NotificationsData> getNotificationByType(int type)
	{
		List<NotificationsData> notificationsDataList = new ArrayList<NotificationsData>();
		String selectQuery = "";
		if (type == 1)
		{
			selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATION_HEADER + " WHERE " + KEY_MSG_TYP + " = '1' OR "
					+ KEY_MSG_TYP + " = '3'";
		}
		else
		{
			selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATION_HEADER + " WHERE " + KEY_MSG_TYP + " = '2'";
		}
		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst())
		{
			do
			{

				NotificationsData notificationsData = new NotificationsData();
				notificationsData.setQUEUE_ID(c.getInt(c.getColumnIndex(KEY_QUEUE_ID)));
				notificationsData.setPTNT_CD(c.getString(c.getColumnIndex(KEY_PTNT_CD)));
				notificationsData.setDT_TIME(c.getLong(c.getColumnIndex(KEY_DT_TIME)));
				notificationsData.setMSG_TYP(c.getInt(c.getColumnIndex(KEY_MSG_TYP)));
				notificationsData.setDISPLAY_FLAG(c.getString(c.getColumnIndex(KEY_DISPLAY_FLAG)));
				notificationsData.setMSG_TITLE(c.getString(c.getColumnIndex(KEY_MSG_TITLE)));

				notificationsData.setMSG_MAIN(c.getString(c.getColumnIndex(KEY_MSG_MAIN)));
				notificationsDataList.add(notificationsData);
			}
			while (c.moveToNext());
		}

		return notificationsDataList;
	}

	/*
	 * Updating a todo
	 */
	public int updateNotifications(NotificationsData notificationsData)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_QUEUE_ID, ((int) notificationsData.getQUEUE_ID()));
		values.put(KEY_PTNT_CD, notificationsData.getPTNT_CD());
		values.put(KEY_DT_TIME, notificationsData.getDT_TIME() + "");
		values.put(KEY_MSG_TYP, notificationsData.getMSG_TYP() + "");
		values.put(KEY_DISPLAY_FLAG, notificationsData.getDISPLAY_FLAG() + "");
		values.put(KEY_MSG_TITLE, notificationsData.getMSG_TITLE() + "");
		values.put(KEY_MSG_MAIN, notificationsData.getMSG_MAIN() + "");

		// updating row
		return db.update(TABLE_NOTIFICATION_HEADER, values, KEY_QUEUE_ID + " = ?",
				new String[] { String.valueOf(notificationsData.getQUEUE_ID()) });
	}

	//	/*
	//	 * Deleting a todo
	//	 */
	public void deleteNotification(int queue_id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NOTIFICATION_HEADER, KEY_QUEUE_ID + " = ?", new String[] { String.valueOf(queue_id) });
	}

	// ------------------------ "tags" table methods ----------------//

	// closing database
	public void closeDB()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
		{
			db.close();
		}
	}

	public void deletetNotificationType(int type)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		if (type == 1) //
			db.execSQL("DELETE FROM " + TABLE_NOTIFICATION_HEADER + " WHERE " + KEY_MSG_TYP + "='1' OR " + KEY_MSG_TYP
					+ "='3'");
		else
			db.execSQL("DELETE FROM " + TABLE_NOTIFICATION_HEADER + " WHERE " + KEY_MSG_TYP + "='2'");
	}

	public void deleteHeader()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_NOTIFICATION_HEADER, null, null);

	}

	/**
	 * get datetime
	 */
	private String getDateTime()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
}
