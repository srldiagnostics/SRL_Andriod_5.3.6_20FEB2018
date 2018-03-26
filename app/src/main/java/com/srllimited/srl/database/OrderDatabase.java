package com.srllimited.srl.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.srllimited.srl.data.OrdersHistoryData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderDatabase extends SQLiteOpenHelper
{

	// Logcat tag
	private static final String LOG = "OrderDatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 20;

	// Database Name
	private static final String DATABASE_NAME = "OrderDatabase";

	// Table Names
	private static final String TABLE_ORDER = "OrderHistory";

	//	private static final String ID = "ID";
	private static final String ORDERNO = "ORDERNO";

	private static final String PATIENT_NAME = "PATIENT_NAME";

	private static final String CREATED_BY = "CREATED_BY";

	private static final String GROSS_AMT = "GROSS_AMT";

	private static final String DISCOUNT_FLAG = "DISCOUNT_FLAG";

	private static final String DISCOUNT = "DISCOUNT";

	private static final String PROMOCODE = "PROMOCODE";

	private static final String PAYMENT_ID = "PAYMENT_ID";

	private static final String ENTERED_ON = "ENTERED_ON";

	private static final String BOOKING_FROM = "BOOKING_FROM";

	private static final String BOOKING_TO = "BOOKING_TO";

	private static final String PRDCT_CD = "PRDCT_CD";

	private static final String PRDCT_NM = "PRDCT_NM";

	private static final String BASIC_COST = "BASIC_COST";

	private static final String PAYMENT_ID1 = "PAYMENT_ID1";

	private static final String PAYMENT_MODE = "PAYMENT_MODE";

	private static final String PAYMENT_STATUS = "PAYMENT_STATUS";

	private static final String PAYMENT_SOURCE = "PAYMENT_SOURCE";

	private static final String ORDER_STATUS = "ORDER_STATUS";

	private static final String HOMECOLLECT_LINK = "HOMECOLLECT_LINK";

	private static final String HOMECOLLECT_START_TIME = "HOMECOLLECT_START_TIME";

	private static final String HOMECOLLECT_END_TIME = "HOMECOLLECT_END_TIME";

	// Table Create Statements
	// Todo table create statement
	private static final String CREATE_TABLE_ORDER = "CREATE TABLE " + TABLE_ORDER + "(" + ORDERNO + " TEXT,"
			+ PATIENT_NAME + " TEXT," + CREATED_BY + " TEXT," + GROSS_AMT + " TEXT," + DISCOUNT_FLAG + " TEXT,"
			+ DISCOUNT + " TEXT," + PROMOCODE + " TEXT," + PAYMENT_ID + " TEXT," + ENTERED_ON + " TEXT," + BOOKING_FROM
			+ " TEXT," + BOOKING_TO + " TEXT," + PRDCT_CD + " TEXT," + PRDCT_NM + " TEXT," + BASIC_COST + " TEXT,"
			+ PAYMENT_ID1 + " TEXT," + PAYMENT_MODE + " TEXT," + PAYMENT_STATUS + " TEXT," + ORDER_STATUS + " TEXT,"
			+ HOMECOLLECT_LINK + " TEXT," + HOMECOLLECT_START_TIME + " TEXT," + HOMECOLLECT_END_TIME + " TEXT,"
			+ PAYMENT_SOURCE + " TEXT" + ")";

	public OrderDatabase(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{

		// creating required tables
		db.execSQL(CREATE_TABLE_ORDER);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);

		// create new tables
		onCreate(db);
	}

	// ------------------------ "todos" table methods ----------------//

	/*
	 * Creating a todo
	 */
	public long createHeaderData(OrdersHistoryData ordersHistoryData)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(ORDERNO, (ordersHistoryData.getORDERNO()));
		values.put(PATIENT_NAME, ordersHistoryData.getPATIENT_NAME());
		values.put(CREATED_BY, ordersHistoryData.getCREATED_BY());
		values.put(GROSS_AMT, ordersHistoryData.getGROSS_AMT() + "");
		values.put(DISCOUNT_FLAG, ordersHistoryData.getDISCOUNT_FLAG() + "");
		values.put(DISCOUNT, (ordersHistoryData.getDISCOUNT() + ""));
		values.put(PROMOCODE, ordersHistoryData.getPROMOCODE() + "");
		values.put(PAYMENT_ID, ordersHistoryData.getPAYMENT_ID() + "");
		values.put(ENTERED_ON, ordersHistoryData.getENTERED_ON() + "");
		values.put(BOOKING_FROM, ordersHistoryData.getBOOKING_FROM() + "");
		values.put(BOOKING_TO, (ordersHistoryData.getBOOKING_TO() + ""));
		values.put(PRDCT_CD, ordersHistoryData.getPROMOCODE() + "");
		values.put(PRDCT_NM, ordersHistoryData.getJson() + "");
		values.put(PAYMENT_MODE, (ordersHistoryData.getPAYMENT_MODE() + ""));
		values.put(PAYMENT_STATUS, ordersHistoryData.getPAYMENT_STATUS() + "");
		values.put(PAYMENT_SOURCE, ordersHistoryData.getPAYMENT_SOURCE() + "");
		values.put(ORDER_STATUS, ordersHistoryData.getORDER_STATUS() + "");
		values.put(HOMECOLLECT_LINK, ordersHistoryData.getHOMECOLLECT_LINK() + "");
		values.put(HOMECOLLECT_START_TIME, ordersHistoryData.getHOMECOLLECT_START_TIME());
		values.put(HOMECOLLECT_END_TIME, ordersHistoryData.getHOMECOLLECT_END_TIME());
		return db.insert(TABLE_ORDER, null, values);
	}

	/*
	 * get single todo
	 */
	public OrdersHistoryData getHeaderObject(long todo_id)
	{
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_ORDER + " WHERE " + ORDERNO + " = " + todo_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
		{
			c.moveToFirst();
		}

		OrdersHistoryData ordersHistoryData = new OrdersHistoryData();
		ordersHistoryData.setORDERNO(c.getString(c.getColumnIndex(ORDERNO)));
		ordersHistoryData.setPATIENT_NAME(c.getString(c.getColumnIndex(PATIENT_NAME)));
		ordersHistoryData.setCREATED_BY(c.getString(c.getColumnIndex(CREATED_BY)));
		ordersHistoryData.setGROSS_AMT(c.getDouble(c.getColumnIndex(GROSS_AMT)));
		ordersHistoryData.setDISCOUNT(c.getDouble(c.getColumnIndex(DISCOUNT_FLAG)));
		ordersHistoryData.setDISCOUNT(c.getDouble(c.getColumnIndex(DISCOUNT)));
		ordersHistoryData.setPROMOCODE(c.getString(c.getColumnIndex(PROMOCODE)));
		ordersHistoryData.setPAYMENT_ID(c.getString(c.getColumnIndex(PAYMENT_ID)));
		ordersHistoryData.setENTERED_ON(c.getLong(c.getColumnIndex(ENTERED_ON)));
		ordersHistoryData.setBOOKING_FROM(c.getLong(c.getColumnIndex(BOOKING_FROM)));
		ordersHistoryData.setBOOKING_TO(c.getLong(c.getColumnIndex(BOOKING_TO)));
		ordersHistoryData.setJson(c.getString(c.getColumnIndex(PRDCT_NM)));
		ordersHistoryData.setPAYMENT_MODE(c.getString(c.getColumnIndex(PAYMENT_MODE)));
		ordersHistoryData.setPAYMENT_STATUS(c.getString(c.getColumnIndex(PAYMENT_STATUS)));
		ordersHistoryData.setPAYMENT_SOURCE(c.getString(c.getColumnIndex(PAYMENT_SOURCE)));
		ordersHistoryData.setORDER_STATUS(c.getString(c.getColumnIndex(ORDER_STATUS)));
		ordersHistoryData.setHOMECOLLECT_START_TIME(c.getLong(c.getColumnIndex(HOMECOLLECT_START_TIME)));
		ordersHistoryData.setHOMECOLLECT_END_TIME(c.getLong(c.getColumnIndex(HOMECOLLECT_END_TIME)));
		return ordersHistoryData;
	}

	/**
	 * getting all todos
	 */
	public List<OrdersHistoryData> getOrdersList()
	{
		List<OrdersHistoryData> order_list = new ArrayList<OrdersHistoryData>();

		String selectQuery = "SELECT  * FROM " + TABLE_ORDER;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst())
		{
			do
			{

				OrdersHistoryData ordersHistoryData = new OrdersHistoryData();
				ordersHistoryData.setORDERNO(c.getString(c.getColumnIndex(ORDERNO)));
				ordersHistoryData.setPATIENT_NAME(c.getString(c.getColumnIndex(PATIENT_NAME)));
				ordersHistoryData.setCREATED_BY(c.getString(c.getColumnIndex(CREATED_BY)));
				ordersHistoryData.setGROSS_AMT(c.getDouble(c.getColumnIndex(GROSS_AMT)));
				ordersHistoryData.setDISCOUNT(c.getDouble(c.getColumnIndex(DISCOUNT_FLAG)));
				ordersHistoryData.setDISCOUNT(c.getDouble(c.getColumnIndex(DISCOUNT)));
				ordersHistoryData.setPROMOCODE(c.getString(c.getColumnIndex(PROMOCODE)));
				ordersHistoryData.setPAYMENT_ID(c.getString(c.getColumnIndex(PAYMENT_ID)));
				ordersHistoryData.setENTERED_ON(c.getLong(c.getColumnIndex(ENTERED_ON)));
				ordersHistoryData.setBOOKING_FROM(c.getLong(c.getColumnIndex(BOOKING_FROM)));
				ordersHistoryData.setBOOKING_TO(c.getLong(c.getColumnIndex(BOOKING_TO)));

				ordersHistoryData.setJson(c.getString(c.getColumnIndex(PRDCT_NM)));
				ordersHistoryData.setPAYMENT_MODE(c.getString(c.getColumnIndex(PAYMENT_MODE)));
				ordersHistoryData.setPAYMENT_STATUS(c.getString(c.getColumnIndex(PAYMENT_STATUS)));
				ordersHistoryData.setPAYMENT_SOURCE(c.getString(c.getColumnIndex(PAYMENT_SOURCE)));

				ordersHistoryData.setORDER_STATUS(c.getString(c.getColumnIndex(ORDER_STATUS)));
				order_list.add(ordersHistoryData);
			}
			while (c.moveToNext());
		}

		return order_list;
	}

	/*
	 * getting todo count
	 */
	public int getToDoCount()
	{
		String countQuery = "SELECT  * FROM " + TABLE_ORDER;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

	public OrdersHistoryData getOrder_object(String todo_id)
	{
		OrdersHistoryData ordersHistoryData = null;
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_ORDER + " WHERE " + ORDERNO + " = '" + todo_id + "'";

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
		{
			c.moveToFirst();
		}

		try
		{
			ordersHistoryData = new OrdersHistoryData();
			ordersHistoryData.setORDERNO(c.getString(c.getColumnIndex(ORDERNO)));
			ordersHistoryData.setPATIENT_NAME(c.getString(c.getColumnIndex(PATIENT_NAME)));
			ordersHistoryData.setCREATED_BY(c.getString(c.getColumnIndex(CREATED_BY)));
			ordersHistoryData.setGROSS_AMT(c.getDouble(c.getColumnIndex(GROSS_AMT)));
			ordersHistoryData.setDISCOUNT(c.getDouble(c.getColumnIndex(DISCOUNT_FLAG)));
			ordersHistoryData.setDISCOUNT(c.getDouble(c.getColumnIndex(DISCOUNT)));
			ordersHistoryData.setPROMOCODE(c.getString(c.getColumnIndex(PROMOCODE)));
			ordersHistoryData.setPAYMENT_ID(c.getString(c.getColumnIndex(PAYMENT_ID)));
			ordersHistoryData.setENTERED_ON(c.getLong(c.getColumnIndex(ENTERED_ON)));
			ordersHistoryData.setBOOKING_FROM(c.getLong(c.getColumnIndex(BOOKING_FROM)));
			ordersHistoryData.setBOOKING_TO(c.getLong(c.getColumnIndex(BOOKING_TO)));
			ordersHistoryData.setJson(c.getString(c.getColumnIndex(PRDCT_NM)));
			ordersHistoryData.setPAYMENT_MODE(c.getString(c.getColumnIndex(PAYMENT_MODE)));
			ordersHistoryData.setPAYMENT_STATUS(c.getString(c.getColumnIndex(PAYMENT_STATUS)));
			ordersHistoryData.setPAYMENT_SOURCE(c.getString(c.getColumnIndex(PAYMENT_SOURCE)));
			ordersHistoryData.setORDER_STATUS(c.getString(c.getColumnIndex(ORDER_STATUS)));
			ordersHistoryData.setHOMECOLLECT_LINK(c.getString(c.getColumnIndex(HOMECOLLECT_LINK)));
			ordersHistoryData.setHOMECOLLECT_START_TIME(c.getLong(c.getColumnIndex(HOMECOLLECT_START_TIME)));
			ordersHistoryData.setHOMECOLLECT_END_TIME(c.getLong(c.getColumnIndex(HOMECOLLECT_END_TIME)));
		}
		catch (Exception e)
		{

		}

		return ordersHistoryData;
	}

	/*
	 * Updating a todo
	 */
	public int updateOrder(OrdersHistoryData ordersHistoryData)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(ORDERNO, (ordersHistoryData.getORDERNO()));
		values.put(PATIENT_NAME, ordersHistoryData.getPATIENT_NAME());
		values.put(CREATED_BY, ordersHistoryData.getCREATED_BY());
		values.put(GROSS_AMT, ordersHistoryData.getGROSS_AMT() + "");
		values.put(DISCOUNT_FLAG, ordersHistoryData.getDISCOUNT_FLAG() + "");
		values.put(DISCOUNT, (ordersHistoryData.getDISCOUNT() + ""));
		values.put(PROMOCODE, ordersHistoryData.getPROMOCODE() + "");
		values.put(PAYMENT_ID, ordersHistoryData.getPAYMENT_ID() + "");
		values.put(ENTERED_ON, ordersHistoryData.getENTERED_ON() + "");
		values.put(BOOKING_FROM, ordersHistoryData.getBOOKING_FROM() + "");
		values.put(BOOKING_TO, (ordersHistoryData.getBOOKING_TO() + ""));
		values.put(PRDCT_CD, ordersHistoryData.getPROMOCODE() + "");
		values.put(PRDCT_NM, ordersHistoryData.getJson() + "");
		values.put(PAYMENT_MODE, (ordersHistoryData.getPAYMENT_MODE() + ""));
		values.put(PAYMENT_STATUS, ordersHistoryData.getPAYMENT_STATUS() + "");
		values.put(PAYMENT_SOURCE, ordersHistoryData.getPAYMENT_SOURCE() + "");
		values.put(ORDER_STATUS, ordersHistoryData.getORDER_STATUS() + "");
		values.put(HOMECOLLECT_LINK, ordersHistoryData.getHOMECOLLECT_LINK() + "");
		values.put(HOMECOLLECT_START_TIME, ordersHistoryData.getHOMECOLLECT_START_TIME() + "");
		values.put(HOMECOLLECT_END_TIME, ordersHistoryData.getHOMECOLLECT_END_TIME() + "");

		// updating row
		return db.update(TABLE_ORDER, values, ORDERNO + " = ?",
				new String[] { String.valueOf(ordersHistoryData.getORDERNO()) });
	}

	//	/*
	//	 * Deleting a todo
	//	 */
	//	public void deleteToDo(long tado_id)
	//	{
	//		SQLiteDatabase db = this.getWritableDatabase();
	//		db.delete(CREATE_TABLE_BOOKTEST_PACKAGES, KEY_ID + " = ?",
	//		          new String[]{String.valueOf(tado_id)});
	//	}

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

	/**
	 * get datetime
	 */
	private String getDateTime()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void deleteOrder()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_ORDER, null, null);
	}

}
