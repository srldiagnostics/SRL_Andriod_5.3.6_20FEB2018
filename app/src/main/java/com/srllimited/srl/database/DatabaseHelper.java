package com.srllimited.srl.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.srllimited.srl.data.BookTestORPackagesData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{

	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 12;

	// Database Name
	private static final String DATABASE_NAME = "BookATestOrPackagesDatabase";

	// Table Names
	private static final String TABLE_BOOKTEST_PACKAGES = "booktestorpackages";

	// Common column names
	private static final String KEY_ID = "ID";

	private static final String KEY_PRDCT_CODE = "PRDCT_CODE";

	private static final String KEY_PRDCT_ALIASES = "PRDCT_ALIASES";

	private static final String KEY_GROSS_AMT = "GROSS_AMT";

	private static final String KEY_PRICE = "PRICE";

	private static final String KEY_DISC = "DISC";

	private static final String KEY_DISC_TYPE = "DISC_TYPE";

	private static final String KEY_PRDCT_CONSTNS = "PRDCT_CONSTNS";

	private static final String KEY_CATEGORY = "CATEGORY";

	private static final String KEY_PTNT_INSTRCTN = "PTNT_INSTRCTN";

	private static final String KEY_OFR_CD = "OFR_CD";

	private static final String KEY_REP_TAT = "REP_TAT";

	private static final String KEY_INFO = "INFO";

	private static final String KEY_IsCART = "IsCart";

	private static final String KEY_CARTPRICE = "CARTPRICE";

	private static final String KEY_BOOK_PKG = "BOOK_PKG";

	// Table Create Statements
	// Todo table create statement
	private static final String CREATE_TABLE_BOOKTEST_PACKAGES = "CREATE TABLE " + TABLE_BOOKTEST_PACKAGES + "("
			+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_PRDCT_CODE + " TEXT," + KEY_PRDCT_ALIASES + " TEXT,"
			+ KEY_GROSS_AMT + " TEXT," + KEY_PRICE + " TEXT," + KEY_DISC + " TEXT," + KEY_DISC_TYPE + " TEXT,"
			+ KEY_PRDCT_CONSTNS + " TEXT," + KEY_CATEGORY + " TEXT," + KEY_PTNT_INSTRCTN + " TEXT," + KEY_REP_TAT
			+ " TEXT," + KEY_INFO + " TEXT," + KEY_OFR_CD + " TEXT," + KEY_IsCART + " TEXT," + KEY_CARTPRICE + " TEXT,"
			+ KEY_BOOK_PKG + " TEXT" + ")";

	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{

		// creating required tables
		db.execSQL(CREATE_TABLE_BOOKTEST_PACKAGES);

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
	public long createBookTestOrPackages(BookTestORPackagesData book_pkgs)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, book_pkgs.getID());
		values.put(KEY_PRDCT_CODE, book_pkgs.getPRDCT_CODE());
		values.put(KEY_PRDCT_ALIASES, book_pkgs.getPRDCT_ALIASES());
		values.put(KEY_GROSS_AMT, book_pkgs.getGROSS_AMT());
		values.put(KEY_PRICE, book_pkgs.getPRICE());
		values.put(KEY_DISC, book_pkgs.getDISC());
		values.put(KEY_DISC_TYPE, book_pkgs.getDISC_TYPE());
		values.put(KEY_PRDCT_CONSTNS, book_pkgs.getPRDCT_CONSTNS());
		values.put(KEY_PTNT_INSTRCTN, book_pkgs.getPTNT_INSTRCTN());
		values.put(KEY_REP_TAT, book_pkgs.getREP_TAT());
		values.put(KEY_INFO, book_pkgs.getINFO());
		values.put(KEY_OFR_CD, book_pkgs.getOFR_CD());
		values.put(KEY_IsCART, book_pkgs.isCart() + "");
		values.put(KEY_CARTPRICE, book_pkgs.getCartPrice() + "");
		values.put(KEY_BOOK_PKG, book_pkgs.getBook_pkg() + "");

		return db.insert(TABLE_BOOKTEST_PACKAGES, null, values);
	}

	/*create by sachin to store the promocde reasponse */
	public long createBookTestOrPackages_cart(BookTestORPackagesData book_pkgs)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, book_pkgs.getID());
		values.put(KEY_PRDCT_CODE, book_pkgs.getPRDCT_CODE());
		values.put(KEY_PRDCT_ALIASES, book_pkgs.getPRDCT_ALIASES());
		values.put(KEY_GROSS_AMT, book_pkgs.getGROSS_AMT());
		values.put(KEY_PRICE, book_pkgs.getPRICE());
		values.put(KEY_DISC, book_pkgs.getDISC());
		values.put(KEY_DISC_TYPE, book_pkgs.getDISC_TYPE());
		values.put(KEY_OFR_CD, book_pkgs.getOFR_CD());

		values.put(KEY_PRDCT_CONSTNS, book_pkgs.getPRDCT_CONSTNS() + "");
		values.put(KEY_PTNT_INSTRCTN, book_pkgs.getPTNT_INSTRCTN() + "");
		values.put(KEY_REP_TAT, book_pkgs.getREP_TAT() + "");
		values.put(KEY_INFO, book_pkgs.getINFO() + "");

		values.put(KEY_IsCART, book_pkgs.isCart() + "");
		values.put(KEY_CARTPRICE, book_pkgs.getCartPrice() + "");
		values.put(KEY_BOOK_PKG, book_pkgs.getBook_pkg() + "");
		/*"ID": 100000054,
			"PRDCT_CODE": "4836H",
			"PRDCT_ALIASES": "CALCIUM, SERUM",
			"GROSS_AMT": 220.0,
			"PRICE": 220.0,
			"DISC": "0",
			"DISC_TYPE": "P",
			"OFR_CD": "MOP"*/

		return db.insert(TABLE_BOOKTEST_PACKAGES, null, values);
	}

	/*
	 * get single todo
	 */
	public BookTestORPackagesData getBook_Pkg_Object(long todo_id)
	{
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_BOOKTEST_PACKAGES + " WHERE " + KEY_ID + " = " + todo_id;

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
		{
			c.moveToFirst();
		}

		BookTestORPackagesData book_pkgs = new BookTestORPackagesData();

		try
		{
			book_pkgs.setID(c.getInt(c.getColumnIndex(KEY_ID)));
			book_pkgs.setPRDCT_CODE((c.getString(c.getColumnIndex(KEY_PRDCT_CODE))));
			book_pkgs.setPRDCT_ALIASES(c.getString(c.getColumnIndex(KEY_PRDCT_ALIASES)));
			book_pkgs.setGROSS_AMT((c.getString(c.getColumnIndex(KEY_GROSS_AMT))));
			book_pkgs.setPRICE(c.getDouble(c.getColumnIndex(KEY_PRICE)));
			book_pkgs.setDISC((c.getDouble(c.getColumnIndex(KEY_DISC))));
			book_pkgs.setDISC_TYPE(c.getString(c.getColumnIndex(KEY_DISC_TYPE)));
			book_pkgs.setPRDCT_CONSTNS((c.getString(c.getColumnIndex(KEY_PRDCT_CONSTNS))));
			book_pkgs.setCATEGORY(c.getString(c.getColumnIndex(KEY_CATEGORY)));
			book_pkgs.setPTNT_INSTRCTN((c.getString(c.getColumnIndex(KEY_PTNT_INSTRCTN))));
			book_pkgs.setREP_TAT((c.getString(c.getColumnIndex(KEY_REP_TAT))));
			book_pkgs.setINFO(c.getString(c.getColumnIndex(KEY_INFO)));
			book_pkgs.setOFR_CD(c.getString(c.getColumnIndex(KEY_OFR_CD)));
			book_pkgs.setCartPrice(c.getString(c.getColumnIndex(KEY_CARTPRICE)));
			book_pkgs.setBook_pkg(c.getString(c.getColumnIndex(KEY_BOOK_PKG)));
		}
		catch (Exception e)
		{

		}

		if (c.getString(c.getColumnIndex(KEY_IsCART)) != null
				&& c.getString(c.getColumnIndex(KEY_IsCART)).equalsIgnoreCase("true"))
		{
			book_pkgs.setCart(true);
		}
		else
		{
			book_pkgs.setCart(false);
		}

		return book_pkgs;
	}

	/**
	 * getting all todos
	 */
	public List<BookTestORPackagesData> getAllBook_pkgs_List()
	{
		List<BookTestORPackagesData> book_pkgs_list = new ArrayList<BookTestORPackagesData>();
		String selectQuery = "SELECT  * FROM " + TABLE_BOOKTEST_PACKAGES;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst())
		{
			do
			{

				BookTestORPackagesData book_pkgs = new BookTestORPackagesData();
				book_pkgs.setID(c.getInt(c.getColumnIndex(KEY_ID)));
				book_pkgs.setPRDCT_CODE((c.getString(c.getColumnIndex(KEY_PRDCT_CODE))));
				book_pkgs.setPRDCT_ALIASES(c.getString(c.getColumnIndex(KEY_PRDCT_ALIASES)));
				book_pkgs.setGROSS_AMT((c.getString(c.getColumnIndex(KEY_GROSS_AMT))));
				book_pkgs.setPRICE(c.getDouble(c.getColumnIndex(KEY_PRICE)));
				book_pkgs.setDISC((c.getDouble(c.getColumnIndex(KEY_DISC))));
				book_pkgs.setDISC_TYPE(c.getString(c.getColumnIndex(KEY_DISC_TYPE)));
				book_pkgs.setPRDCT_CONSTNS((c.getString(c.getColumnIndex(KEY_PRDCT_CONSTNS))));
				book_pkgs.setCATEGORY(c.getString(c.getColumnIndex(KEY_CATEGORY)));
				book_pkgs.setPTNT_INSTRCTN((c.getString(c.getColumnIndex(KEY_PTNT_INSTRCTN))));
				book_pkgs.setREP_TAT((c.getString(c.getColumnIndex(KEY_REP_TAT))));
				book_pkgs.setINFO(c.getString(c.getColumnIndex(KEY_INFO)));
				book_pkgs.setOFR_CD(c.getString(c.getColumnIndex(KEY_OFR_CD)));
				book_pkgs.setCartPrice(c.getString(c.getColumnIndex(KEY_CARTPRICE)));
				book_pkgs.setBook_pkg(c.getString(c.getColumnIndex(KEY_BOOK_PKG)));
				if (c.getString(c.getColumnIndex(KEY_IsCART)) != null
						&& c.getString(c.getColumnIndex(KEY_IsCART)).equalsIgnoreCase("true"))
				{
					book_pkgs.setCart(true);
				}
				else
				{
					book_pkgs.setCart(false);
				}
				// adding to todo list
				book_pkgs_list.add(book_pkgs);
			}
			while (c.moveToNext());
		}

		return book_pkgs_list;
	}

	public List<BookTestORPackagesData> getAllBookATests()
	{
		List<BookTestORPackagesData> book_pkgs_list = new ArrayList<BookTestORPackagesData>();
		String selectQuery = "SELECT  * FROM " + TABLE_BOOKTEST_PACKAGES;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst())
		{
			do
			{
				BookTestORPackagesData book_pkgs = new BookTestORPackagesData();
				book_pkgs.setID(c.getInt(c.getColumnIndex(KEY_ID)));
				book_pkgs.setPRDCT_CODE((c.getString(c.getColumnIndex(KEY_PRDCT_CODE))));
				book_pkgs.setPRDCT_ALIASES(c.getString(c.getColumnIndex(KEY_PRDCT_ALIASES)));
				book_pkgs.setGROSS_AMT((c.getString(c.getColumnIndex(KEY_GROSS_AMT))));
				book_pkgs.setPRICE(c.getDouble(c.getColumnIndex(KEY_PRICE)));
				book_pkgs.setDISC((c.getDouble(c.getColumnIndex(KEY_DISC))));
				book_pkgs.setDISC_TYPE(c.getString(c.getColumnIndex(KEY_DISC_TYPE)));
				book_pkgs.setPRDCT_CONSTNS((c.getString(c.getColumnIndex(KEY_PRDCT_CONSTNS))));
				book_pkgs.setCATEGORY(c.getString(c.getColumnIndex(KEY_CATEGORY)));
				book_pkgs.setPTNT_INSTRCTN((c.getString(c.getColumnIndex(KEY_PTNT_INSTRCTN))));
				book_pkgs.setREP_TAT((c.getString(c.getColumnIndex(KEY_REP_TAT))));
				book_pkgs.setINFO(c.getString(c.getColumnIndex(KEY_INFO)));
				book_pkgs.setOFR_CD(c.getString(c.getColumnIndex(KEY_OFR_CD)));
				book_pkgs.setCartPrice(c.getString(c.getColumnIndex(KEY_CARTPRICE)));
				book_pkgs.setBook_pkg(c.getString(c.getColumnIndex(KEY_BOOK_PKG)));
				if (c.getString(c.getColumnIndex(KEY_IsCART)) != null
						&& c.getString(c.getColumnIndex(KEY_IsCART)).equalsIgnoreCase("true"))
				{
					book_pkgs.setCart(true);
				}
				else
				{
					book_pkgs.setCart(false);
				}
				// adding to todo list
				book_pkgs_list.add(book_pkgs);
			}
			while (c.moveToNext());
		}

		return book_pkgs_list;
	}

	public List<BookTestORPackagesData> getBookATestsOrPkgs(String selectedCart)
	{
		List<BookTestORPackagesData> book_pkgs_list = new ArrayList<BookTestORPackagesData>();
		String selectQuery = "SELECT  * FROM " + TABLE_BOOKTEST_PACKAGES + " WHERE " + KEY_BOOK_PKG + " = '"
				+ selectedCart + "'";
		;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst())
		{
			do
			{
				BookTestORPackagesData book_pkgs = new BookTestORPackagesData();
				book_pkgs.setID(c.getInt(c.getColumnIndex(KEY_ID)));
				book_pkgs.setPRDCT_CODE((c.getString(c.getColumnIndex(KEY_PRDCT_CODE))));
				book_pkgs.setPRDCT_ALIASES(c.getString(c.getColumnIndex(KEY_PRDCT_ALIASES)));
				book_pkgs.setGROSS_AMT((c.getString(c.getColumnIndex(KEY_GROSS_AMT))));
				book_pkgs.setPRICE(c.getDouble(c.getColumnIndex(KEY_PRICE)));
				book_pkgs.setDISC((c.getDouble(c.getColumnIndex(KEY_DISC))));
				book_pkgs.setDISC_TYPE(c.getString(c.getColumnIndex(KEY_DISC_TYPE)));
				book_pkgs.setPRDCT_CONSTNS((c.getString(c.getColumnIndex(KEY_PRDCT_CONSTNS))));
				book_pkgs.setCATEGORY(c.getString(c.getColumnIndex(KEY_CATEGORY)));
				book_pkgs.setPTNT_INSTRCTN((c.getString(c.getColumnIndex(KEY_PTNT_INSTRCTN))));
				book_pkgs.setREP_TAT((c.getString(c.getColumnIndex(KEY_REP_TAT))));
				book_pkgs.setINFO(c.getString(c.getColumnIndex(KEY_INFO)));
				book_pkgs.setOFR_CD(c.getString(c.getColumnIndex(KEY_OFR_CD)));
				book_pkgs.setCartPrice(c.getString(c.getColumnIndex(KEY_CARTPRICE)));
				book_pkgs.setBook_pkg(c.getString(c.getColumnIndex(KEY_BOOK_PKG)));
				if (c.getString(c.getColumnIndex(KEY_IsCART)) != null
						&& c.getString(c.getColumnIndex(KEY_IsCART)).equalsIgnoreCase("true"))
				{
					book_pkgs.setCart(true);
				}
				else
				{
					book_pkgs.setCart(false);
				}
				// adding to todo list
				book_pkgs_list.add(book_pkgs);
			}
			while (c.moveToNext());
		}

		return book_pkgs_list;
	}

	/*
	 * getting todo count
	 */
	public int getToDoCount()
	{
		String countQuery = "SELECT  * FROM " + TABLE_BOOKTEST_PACKAGES;
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
	public int updateBookOrPkgs(BookTestORPackagesData book_pkgs)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, book_pkgs.getID());
		values.put(KEY_PRDCT_CODE, book_pkgs.getPRDCT_CODE());
		values.put(KEY_PRDCT_ALIASES, book_pkgs.getPRDCT_ALIASES());
		values.put(KEY_GROSS_AMT, book_pkgs.getGROSS_AMT());
		values.put(KEY_PRICE, book_pkgs.getPRICE());
		values.put(KEY_DISC, book_pkgs.getDISC());
		values.put(KEY_DISC_TYPE, book_pkgs.getDISC_TYPE());
		values.put(KEY_PRDCT_CONSTNS, book_pkgs.getPRDCT_CONSTNS());
		values.put(KEY_PTNT_INSTRCTN, book_pkgs.getPTNT_INSTRCTN());
		values.put(KEY_REP_TAT, book_pkgs.getREP_TAT());
		values.put(KEY_INFO, book_pkgs.getINFO());
		values.put(KEY_IsCART, book_pkgs.isCart() + "");
		values.put(KEY_BOOK_PKG, book_pkgs.getBook_pkg() + "");

		values.put(KEY_CARTPRICE, book_pkgs.getCartPrice() + "");

		// updating row
		return db.update(TABLE_BOOKTEST_PACKAGES, values, KEY_ID + " = ?",
				new String[] { String.valueOf(book_pkgs.getID()) });
	}

	public List<BookTestORPackagesData> getAllPackages()
	{
		List<BookTestORPackagesData> book_pkgs_list = new ArrayList<BookTestORPackagesData>();
		String selectQuery = "SELECT  * FROM " + TABLE_BOOKTEST_PACKAGES;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst())
		{
			do
			{
				BookTestORPackagesData book_pkgs = new BookTestORPackagesData();
				book_pkgs.setID(c.getInt(c.getColumnIndex(KEY_ID)));
				book_pkgs.setPRDCT_CODE((c.getString(c.getColumnIndex(KEY_PRDCT_CODE))));
				book_pkgs.setPRDCT_ALIASES(c.getString(c.getColumnIndex(KEY_PRDCT_ALIASES)));
				book_pkgs.setGROSS_AMT((c.getString(c.getColumnIndex(KEY_GROSS_AMT))));
				book_pkgs.setPRICE(c.getDouble(c.getColumnIndex(KEY_PRICE)));
				book_pkgs.setDISC((c.getDouble(c.getColumnIndex(KEY_DISC))));
				book_pkgs.setDISC_TYPE(c.getString(c.getColumnIndex(KEY_DISC_TYPE)));
				book_pkgs.setPRDCT_CONSTNS((c.getString(c.getColumnIndex(KEY_PRDCT_CONSTNS))));
				book_pkgs.setCATEGORY(c.getString(c.getColumnIndex(KEY_CATEGORY)));
				book_pkgs.setPTNT_INSTRCTN((c.getString(c.getColumnIndex(KEY_PTNT_INSTRCTN))));
				book_pkgs.setREP_TAT((c.getString(c.getColumnIndex(KEY_REP_TAT))));
				book_pkgs.setINFO(c.getString(c.getColumnIndex(KEY_INFO)));
				book_pkgs.setCartPrice(c.getString(c.getColumnIndex(KEY_CARTPRICE)));
				book_pkgs.setBook_pkg(c.getString(c.getColumnIndex(KEY_BOOK_PKG)));
				if (c.getString(c.getColumnIndex(KEY_IsCART)) != null
						&& c.getString(c.getColumnIndex(KEY_IsCART)).equalsIgnoreCase("true"))
				{
					book_pkgs.setCart(true);
				}
				else
				{
					book_pkgs.setCart(false);
				}
				// adding to todo list
				book_pkgs_list.add(book_pkgs);
			}
			while (c.moveToNext());
		}

		return book_pkgs_list;
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

	public void deleteBookATest()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_BOOKTEST_PACKAGES, null, null);
	}

	//	private void search()
	//	{
	//
	//		List<ProductHeaderData> datalist = null;
	//		try
	//		{
	//			datalist = productHeaderDB.getAllHeaderList();
	//		}
	//		catch (Exception e)
	//		{
	//			e.printStackTrace();
	//		}
	//
	//		List<BookTestORPackagesData> datas = new ArrayList<>();
	//
	//		try
	//		{
	//
	//			BookTestORPackagesData bookTestORPackagesData = BookTestORPackagesData.newInit(jObject);
	//
	//			if (bookTestORPackagesData != null)
	//			{
	//
	//				if (datalist != null && !datalist.isEmpty())
	//				{
	//					List<ProductHeaderListItemData> productHeaderListItemDatas = new ArrayList<>();
	//					JSONArray namesArray = jObject.names();
	//					ArrayList<String> lsitToBeAdded = new ArrayList<String>();
	//
	//					if (namesArray != null)
	//					{
	//						for (int j = 0; j < namesArray.length(); j++)
	//						{
	//							lsitToBeAdded.add(namesArray.getString(j));
	//						}
	//					}
	//
	//					if (lsitToBeAdded != null && !lsitToBeAdded.isEmpty())
	//					{
	//						for (ProductHeaderData productHeaderData : datalist)
	//						{
	//
	//							for (String checkHeaders : lsitToBeAdded)
	//							{
	//								if (productHeaderData.getLBL_NAME().equalsIgnoreCase(checkHeaders.toString()))
	//								{
	//
	//									ProductHeaderListItemData productHeaderListItemData = new ProductHeaderListItemData();
	//
	//									String desc = jObject.getString(checkHeaders);
	//
	//									if (productHeaderData.getLBL_HDR() != null && !productHeaderData.getLBL_HDR().isEmpty()
	//											&& desc != null && !desc.isEmpty() && !desc.equalsIgnoreCase("null"))
	//									{
	//										productHeaderListItemData.setHeader(productHeaderData.getLBL_HDR());
	//										productHeaderListItemData.setDesc(desc);
	//
	//										productHeaderListItemDatas.add(productHeaderListItemData);
	//									}
	//								}
	//							}
	//						}
	//
	//						bookTestORPackagesData.setProductHeaderListItemDataList(productHeaderListItemDatas);
	//					}
	//
	//
	//	}

}
