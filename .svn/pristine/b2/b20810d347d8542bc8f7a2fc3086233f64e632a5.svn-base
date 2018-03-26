package com.srllimited.srl.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.srllimited.srl.data.ProductHeaderData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ProductHeaderDatabase extends SQLiteOpenHelper
{

	// Logcat tag
	private static final String LOG = "HeaderDatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 5;

	// Database Name
	private static final String DATABASE_NAME = "HeaderDatabase";

	// Table Names
	private static final String TABLE_PRODUCT_HEADER = "productheader";

	// Common column names
	private static final String KEY_LBL_ID = "LBL_ID";

	private static final String KEY_LBL_NAME = "LBL_NAME";

	private static final String KEY_LBL_HDR = "LBL_HDR";

	private static final String KEY_DISP_FLAG = "DISP_FLAG";

	private static final String KEY_ORDER_ID = "ORDER_ID";

	// Table Create Statements
	// Todo table create statement
	private static final String CREATE_TABLE_HEADER = "CREATE TABLE " + TABLE_PRODUCT_HEADER + "(" + KEY_LBL_ID
			+ " INTEGER PRIMARY KEY," + KEY_LBL_NAME + " TEXT," + KEY_LBL_HDR + " TEXT," + KEY_DISP_FLAG + " TEXT,"
			+ KEY_ORDER_ID + " TEXT" + ")";

	public ProductHeaderDatabase(Context context)
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
	public long createHeaderData(ProductHeaderData headerData)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_LBL_ID, ((int) headerData.getLBL_ID()));
		values.put(KEY_LBL_NAME, headerData.getLBL_NAME());
		values.put(KEY_LBL_HDR, headerData.getLBL_HDR());
		values.put(KEY_DISP_FLAG, headerData.getDISP_FLAG());
		values.put(KEY_ORDER_ID, headerData.getORDER_ID() + "");

		return db.insert(TABLE_PRODUCT_HEADER, null, values);
	}

	/*
	 * get single todo
	 */
	public ProductHeaderData getHeaderObject(long todo_id)
	{
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT_HEADER + " WHERE " + KEY_LBL_ID + " = " + todo_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
		{
			c.moveToFirst();
		}

		ProductHeaderData productHeaderData = new ProductHeaderData();
		productHeaderData.setLBL_ID(c.getDouble(c.getColumnIndex(KEY_LBL_ID)));
		productHeaderData.setLBL_NAME(c.getString(c.getColumnIndex(KEY_LBL_NAME)));
		productHeaderData.setLBL_HDR(c.getString(c.getColumnIndex(KEY_LBL_HDR)));
		productHeaderData.setDISP_FLAG(c.getString(c.getColumnIndex(KEY_DISP_FLAG)));
		productHeaderData.setORDER_ID(c.getDouble(c.getColumnIndex(KEY_ORDER_ID)));

		return productHeaderData;
	}

	/**
	 * getting all todos
	 */
	public List<ProductHeaderData> getAllHeaderList()
	{
		List<ProductHeaderData> header_list = new ArrayList<ProductHeaderData>();

		String selectQuery = "SELECT  * FROM " + TABLE_PRODUCT_HEADER;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst())
		{
			do
			{

				ProductHeaderData productHeaderData = new ProductHeaderData();
				productHeaderData.setLBL_ID(c.getDouble(c.getColumnIndex(KEY_LBL_ID)));
				productHeaderData.setLBL_NAME(c.getString(c.getColumnIndex(KEY_LBL_NAME)));
				productHeaderData.setLBL_HDR(c.getString(c.getColumnIndex(KEY_LBL_HDR)));
				productHeaderData.setDISP_FLAG(c.getString(c.getColumnIndex(KEY_DISP_FLAG)));
				productHeaderData.setORDER_ID(c.getDouble(c.getColumnIndex(KEY_ORDER_ID)));
				header_list.add(productHeaderData);
			}
			while (c.moveToNext());
		}

		return header_list;
	}

	/*
	 * getting todo count
	 */
	public int getToDoCount()
	{
		String countQuery = "SELECT  * FROM " + TABLE_PRODUCT_HEADER;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

	/*
	 * Updating a todo
	 */
	public int updateBookOrPkgs(ProductHeaderData headerData)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_LBL_ID, headerData.getLBL_ID());
		values.put(KEY_LBL_NAME, headerData.getLBL_NAME());
		values.put(KEY_LBL_HDR, headerData.getLBL_HDR());
		values.put(KEY_DISP_FLAG, headerData.getDISP_FLAG());
		values.put(KEY_ORDER_ID, headerData.getORDER_ID());

		// updating row
		return db.update(TABLE_PRODUCT_HEADER, values, KEY_LBL_ID + " = ?",
				new String[] { String.valueOf(headerData.getLBL_ID()) });
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

	public void deleteHeader()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_PRODUCT_HEADER, null, null);
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
