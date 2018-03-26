package com.srllimited.srl.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.ReportsData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ReportsDatabase extends SQLiteOpenHelper
{

	// Logcat tag
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 27;

	// Database Name
	private static final String DATABASE_NAME = "ReportsDatabase";

	// Table Names
	private static final String TABLE_Reports = "ReportsData";

	private static final String PDT_EL = "PDT_EL";

	private static final String ACC_ID = "ACC_ID";

	private static final String PRDCT_ID = "PRDCT_ID";

	private static final String PRDCT_CD = "PRDCT_CD";

	private static final String PRDCT_NAME = "PRDCT_NAME";

	private static final String ELMNT_ID = "ELMNT_ID";

	private static final String ELMNT_CD = "ELMNT_CD";

	private static final String ELMNT_NAME = "ELMNT_NAME";

	private static final String ELMNT_RSLT_TYP = "ELMNT_RSLT_TYP";

	private static final String ELMNT_MIN_RANGE = "ELMNT_MIN_RANGE";

	private static final String ELMNT_MAX_RANGE = "ELMNT_MAX_RANGE";

	private static final String ELMNT_RSLT_UNIT = "ELMNT_RSLT_UNIT";

	private static final String RSLT = "RSLT";

	private static final String P_CMMNT = "P_CMMNT";

	private static final String RANGE_VAL = "RANGE_VAL";

	private static final String PRNT_RNG_TXT = "PRNT_RNG_TXT";

	private static final String RSLT_NORMAL_FLAG = "RSLT_NORMAL_FLAG";

	private static final String SR_NO = "SR_NO";

	private static final String PARENT_PRDCT_ID = "PARENT_PRDCT_ID";

	private static final String PARENT_PRDCT_CD = "PARENT_PRDCT_CD";

	private static final String PARENT_PRDCT_NAME = "PARENT_PRDCT_NAME";

	private static final String ACC_DT = "ACC_DT";

	private static final String PTNT_CD = "PTNT_CD";

	private static final String GENERIC_NAME = "GENERIC_NAME";

	private static final String CPT_CODE = "CPT_CODE";

	// Table Create Statements
	// Todo table create statement
	private static final String CREATE_TABLE_REPORTS = "CREATE TABLE " + TABLE_Reports + "(" + PDT_EL + " TEXT,"
			+ ACC_ID + " TEXT," + PRDCT_CD + " TEXT," + PRDCT_ID + " TEXT," + PRDCT_NAME + " TEXT," + ELMNT_ID
			+ " TEXT," + ELMNT_CD + " TEXT," + ELMNT_NAME + " TEXT," + ELMNT_RSLT_TYP + " TEXT," + ELMNT_MIN_RANGE
			+ " TEXT," + ELMNT_MAX_RANGE + " TEXT," + ELMNT_RSLT_UNIT + " TEXT," + RSLT + " TEXT," + P_CMMNT + " TEXT,"
			+ RANGE_VAL + " TEXT," + PRNT_RNG_TXT + " TEXT," + RSLT_NORMAL_FLAG + " TEXT," + SR_NO + " TEXT,"
			+ PARENT_PRDCT_ID + " TEXT," + PARENT_PRDCT_CD + " TEXT," + PARENT_PRDCT_NAME + " TEXT," + ACC_DT + " TEXT,"
			+ PTNT_CD + " TEXT," + GENERIC_NAME + " TEXT," + CPT_CODE + " TEXT" + ")";

	public ReportsDatabase(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{

		// creating required tables
		db.execSQL(CREATE_TABLE_REPORTS);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		//		// on upgrade drop older tables
		//		db.execSQL("DROP TABLE IF EXISTS " + CREATE_CREATE_TABLE_REPORTS);
		//
		//
		//		// create new tables
		//		onCreate(db);
	}

	// ------------------------ "todos" table methods ----------------//

	/*
	 * Creating a todo
	 */
	public long createReports(ReportsData reportsData)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();

		values.put(PDT_EL, reportsData.getACC_ID() + "" + reportsData.getELMNT_ID() + "");
		values.put(ACC_ID, reportsData.getACC_ID() + "");
		values.put(PRDCT_CD, reportsData.getPRDCT_CD() + "");
		values.put(PRDCT_ID, reportsData.getPRDCT_ID() + "");
		values.put(PRDCT_NAME, reportsData.getPRDCT_NAME() + "");
		values.put(ELMNT_ID, reportsData.getELMNT_ID() + "");
		values.put(ELMNT_NAME, reportsData.getELMNT_NAME() + "");
		values.put(RSLT, reportsData.getRSLT() + "");
		values.put(ELMNT_RSLT_TYP, reportsData.getELMNT_RSLT_TYP() + "");
		values.put(ELMNT_MIN_RANGE, reportsData.getELMNT_MIN_RANGE() + "");
		values.put(ELMNT_MAX_RANGE, reportsData.getELMNT_MAX_RANGE() + "");
		values.put(ELMNT_RSLT_UNIT, reportsData.getELMNT_RSLT_UNIT() + "");
		values.put(P_CMMNT, reportsData.getP_CMMNT() + "");
		if (reportsData.getRANGE_VAL() + "" != null && !(reportsData.getRANGE_VAL() + "").equalsIgnoreCase("null"))
		{
			values.put(RANGE_VAL, reportsData.getRANGE_VAL() + "");
		}
		else
		{
			values.put(RANGE_VAL, "0");
		}
		values.put(PRNT_RNG_TXT, reportsData.getPRNT_RNG_TXT() + "");
		values.put(RSLT_NORMAL_FLAG, reportsData.getRSLT_NORMAL_FLAG() + "");
		values.put(SR_NO, reportsData.getSR_NO() + "");
		values.put(PARENT_PRDCT_ID, reportsData.getPARENT_PRDCT_ID() + "");
		values.put(PARENT_PRDCT_CD, reportsData.getPARENT_PRDCT_CD() + "");
		values.put(PARENT_PRDCT_NAME, reportsData.getPARENT_PRDCT_NAME() + "");
		values.put(ACC_DT, reportsData.getACC_DT() + "");
		values.put(PTNT_CD, reportsData.getPTNT_CD() + "");
		values.put(GENERIC_NAME, reportsData.getGENERIC_NAME() + "");
		values.put(CPT_CODE, reportsData.getCPT_CODE() + "");

		Log.e("reports", reportsData.getACC_ID() + "");
		return db.insert(TABLE_Reports, null, values);

	}

	/*
	 * get single todo
	 */
	public ReportsData getReportObject(long todo_id)
	{
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_Reports + " WHERE " + PRDCT_ID + " = " + todo_id + " AND ("
				+ ELMNT_RSLT_TYP + "= 'N'" + " OR " + ELMNT_RSLT_TYP + "= 'X')" + " AND " + PTNT_CD + "= '"
				+ Constants.isMember + "'";

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
		{
			c.moveToFirst();
		}

		ReportsData reportsData = new ReportsData();
		reportsData.setACC_ID(c.getString(c.getColumnIndex(ACC_ID)));
		reportsData.setPRDCT_ID(c.getInt(c.getColumnIndex(PRDCT_ID)));
		reportsData.setPRDCT_CD(c.getString(c.getColumnIndex(PRDCT_CD)));
		reportsData.setPRDCT_NAME(c.getString(c.getColumnIndex(PRDCT_NAME)));
		reportsData.setELMNT_ID(c.getInt(c.getColumnIndex(ELMNT_ID)));
		reportsData.setELMNT_CD(c.getString(c.getColumnIndex(ELMNT_CD)));
		reportsData.setELMNT_NAME(c.getString(c.getColumnIndex(ELMNT_NAME)));
		reportsData.setELMNT_RSLT_TYP(c.getString(c.getColumnIndex(ELMNT_RSLT_TYP)));
		reportsData.setELMNT_MIN_RANGE(c.getString(c.getColumnIndex(ELMNT_MIN_RANGE)));
		reportsData.setELMNT_MAX_RANGE(c.getString(c.getColumnIndex(ELMNT_MAX_RANGE)));
		reportsData.setELMNT_RSLT_UNIT(c.getString(c.getColumnIndex(ELMNT_RSLT_UNIT)));
		reportsData.setRSLT(c.getString(c.getColumnIndex(RSLT)));
		reportsData.setPDT_EL(c.getString(c.getColumnIndex(ACC_ID)) + c.getString(c.getColumnIndex(ELMNT_ID)));
		reportsData.setP_CMMNT(c.getString(c.getColumnIndex(P_CMMNT)));

		try
		{
			reportsData.setRANGE_VAL(c.getInt(c.getColumnIndex(RANGE_VAL)));
		}
		catch (Exception e)
		{
			reportsData.setRANGE_VAL(0);
		}

		reportsData.setPRNT_RNG_TXT(c.getString(c.getColumnIndex(PRNT_RNG_TXT)));
		reportsData.setRSLT_NORMAL_FLAG(c.getString(c.getColumnIndex(RSLT_NORMAL_FLAG)));
		reportsData.setSR_NO(c.getInt(c.getColumnIndex(SR_NO)));
		reportsData.setPARENT_PRDCT_ID(c.getInt(c.getColumnIndex(PARENT_PRDCT_ID)));
		reportsData.setPARENT_PRDCT_CD(c.getString(c.getColumnIndex(PARENT_PRDCT_CD)));
		reportsData.setPARENT_PRDCT_NAME(c.getString(c.getColumnIndex(PARENT_PRDCT_NAME)));
		reportsData.setACC_DT(c.getLong(c.getColumnIndex(ACC_DT)));
		reportsData.setPTNT_CD(c.getString(c.getColumnIndex(PTNT_CD)));
		reportsData.setGENERIC_NAME(c.getString(c.getColumnIndex(GENERIC_NAME)));
		reportsData.setCPT_CODE(c.getString(c.getColumnIndex(CPT_CODE)));

		//
		return reportsData;
	}

	public ReportsData getReportswithid(String todo_id)
	{
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_Reports + " WHERE " + PDT_EL + " = '" + todo_id + "'";

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
		{
			c.moveToFirst();
		}

		ReportsData reportsData = new ReportsData();
		reportsData.setACC_ID(c.getString(c.getColumnIndex(ACC_ID)));
		reportsData.setPRDCT_ID(c.getInt(c.getColumnIndex(PRDCT_ID)));
		reportsData.setPRDCT_CD(c.getString(c.getColumnIndex(PRDCT_CD)));
		reportsData.setPRDCT_NAME(c.getString(c.getColumnIndex(PRDCT_NAME)));
		reportsData.setELMNT_ID(c.getInt(c.getColumnIndex(ELMNT_ID)));
		reportsData.setELMNT_CD(c.getString(c.getColumnIndex(ELMNT_CD)));
		reportsData.setELMNT_NAME(c.getString(c.getColumnIndex(ELMNT_NAME)));
		reportsData.setELMNT_RSLT_TYP(c.getString(c.getColumnIndex(ELMNT_RSLT_TYP)));
		reportsData.setELMNT_MIN_RANGE(c.getString(c.getColumnIndex(ELMNT_MIN_RANGE)));
		reportsData.setELMNT_MAX_RANGE(c.getString(c.getColumnIndex(ELMNT_MAX_RANGE)));
		reportsData.setELMNT_RSLT_UNIT(c.getString(c.getColumnIndex(ELMNT_RSLT_UNIT)));
		reportsData.setRSLT(c.getString(c.getColumnIndex(RSLT)));
		reportsData.setPDT_EL(c.getString(c.getColumnIndex(ACC_ID)) + c.getString(c.getColumnIndex(ELMNT_ID)));
		reportsData.setP_CMMNT(c.getString(c.getColumnIndex(P_CMMNT)));

		try
		{
			reportsData.setRANGE_VAL(c.getInt(c.getColumnIndex(RANGE_VAL)));
		}
		catch (Exception e)
		{
			reportsData.setRANGE_VAL(0);
		}

		reportsData.setPRNT_RNG_TXT(c.getString(c.getColumnIndex(PRNT_RNG_TXT)));
		reportsData.setRSLT_NORMAL_FLAG(c.getString(c.getColumnIndex(RSLT_NORMAL_FLAG)));
		reportsData.setSR_NO(c.getInt(c.getColumnIndex(SR_NO)));
		reportsData.setPARENT_PRDCT_ID(c.getInt(c.getColumnIndex(PARENT_PRDCT_ID)));
		reportsData.setPARENT_PRDCT_CD(c.getString(c.getColumnIndex(PARENT_PRDCT_CD)));
		reportsData.setPARENT_PRDCT_NAME(c.getString(c.getColumnIndex(PARENT_PRDCT_NAME)));
		reportsData.setACC_DT(c.getLong(c.getColumnIndex(ACC_DT)));
		reportsData.setPTNT_CD(c.getString(c.getColumnIndex(PTNT_CD)));
		reportsData.setGENERIC_NAME(c.getString(c.getColumnIndex(GENERIC_NAME)));
		reportsData.setCPT_CODE(c.getString(c.getColumnIndex(CPT_CODE)));

		return reportsData;
	}

	/**
	 * getting all todos
	 */
	public List<ReportsData> getReportsData(String ptndcode)
	{
		List<ReportsData> reports_list = new ArrayList<ReportsData>();
		String selectQuery = "SELECT  * FROM " + TABLE_Reports + " WHERE " + PTNT_CD + " = '" + ptndcode + "'";

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst())
		{
			do
			{
				ReportsData reportsData = new ReportsData();

				reportsData.setACC_ID(c.getString(c.getColumnIndex(ACC_ID)));
				reportsData.setPRDCT_ID(c.getInt(c.getColumnIndex(PRDCT_ID)));
				reportsData.setPRDCT_CD(c.getString(c.getColumnIndex(PRDCT_CD)));
				reportsData.setPRDCT_NAME(c.getString(c.getColumnIndex(PRDCT_NAME)));
				reportsData.setELMNT_ID(c.getInt(c.getColumnIndex(ELMNT_ID)));
				reportsData.setELMNT_CD(c.getString(c.getColumnIndex(ELMNT_CD)));
				reportsData.setELMNT_NAME(c.getString(c.getColumnIndex(ELMNT_NAME)));

				reportsData.setELMNT_RSLT_TYP(c.getString(c.getColumnIndex(ELMNT_RSLT_TYP)));
				reportsData.setELMNT_MIN_RANGE(c.getString(c.getColumnIndex(ELMNT_MIN_RANGE)));
				reportsData.setELMNT_MAX_RANGE(c.getString(c.getColumnIndex(ELMNT_MAX_RANGE)));
				reportsData.setELMNT_RSLT_UNIT(c.getString(c.getColumnIndex(ELMNT_RSLT_UNIT)));
				reportsData.setRSLT(c.getString(c.getColumnIndex(RSLT)));
				reportsData.setP_CMMNT(c.getString(c.getColumnIndex(P_CMMNT)));

				reportsData.setRANGE_VAL(c.getInt(c.getColumnIndex(RANGE_VAL)));
				reportsData.setPDT_EL(c.getString(c.getColumnIndex(ACC_ID)) + c.getString(c.getColumnIndex(ELMNT_ID)));

				reportsData.setPRNT_RNG_TXT(c.getString(c.getColumnIndex(PRNT_RNG_TXT)));
				reportsData.setRSLT_NORMAL_FLAG(c.getString(c.getColumnIndex(RSLT_NORMAL_FLAG)));
				reportsData.setSR_NO(c.getInt(c.getColumnIndex(SR_NO)));
				reportsData.setPARENT_PRDCT_ID(c.getInt(c.getColumnIndex(PARENT_PRDCT_ID)));
				reportsData.setPARENT_PRDCT_CD(c.getString(c.getColumnIndex(PARENT_PRDCT_CD)));
				reportsData.setPARENT_PRDCT_NAME(c.getString(c.getColumnIndex(PARENT_PRDCT_NAME)));

				try
				{
					long acdate = Long.parseLong(c.getString(c.getColumnIndex(ACC_DT)));
					reportsData.setACC_DT(acdate);
					Log.e("string", c.getString(c.getColumnIndex(ACC_DT)) + "");
					Log.e("date", acdate + "");
				}
				catch (Exception e)
				{

				}
				reportsData.setPTNT_CD(c.getString(c.getColumnIndex(PTNT_CD)));
				reportsData.setGENERIC_NAME(c.getString(c.getColumnIndex(GENERIC_NAME)));
				reportsData.setCPT_CODE(c.getString(c.getColumnIndex(CPT_CODE)));

				if (reportsData != null)
				{
					reports_list.add(reportsData);
				}
			}
			while (c.moveToNext());
		}

		return reports_list;
	}

	/**
	 * getting all todos
	 */
	public List<ReportsData> getHealthTrackedData(int prdct_id)
	{
		List<ReportsData> reports_list = new ArrayList<ReportsData>();
		String selectQuery = "SELECT  * FROM " + TABLE_Reports + " WHERE " + PRDCT_ID + " = " + prdct_id;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (c.moveToFirst())
		{
			do
			{
				ReportsData reportsData = new ReportsData();

				reportsData.setACC_ID(c.getString(c.getColumnIndex(ACC_ID)));
				reportsData.setPRDCT_ID(c.getInt(c.getColumnIndex(PRDCT_ID)));
				reportsData.setPRDCT_CD(c.getString(c.getColumnIndex(PRDCT_CD)));
				reportsData.setPRDCT_NAME(c.getString(c.getColumnIndex(PRDCT_NAME)));
				reportsData.setELMNT_ID(c.getInt(c.getColumnIndex(ELMNT_ID)));
				reportsData.setELMNT_CD(c.getString(c.getColumnIndex(ELMNT_CD)));
				reportsData.setELMNT_NAME(c.getString(c.getColumnIndex(ELMNT_NAME)));

				reportsData.setELMNT_RSLT_TYP(c.getString(c.getColumnIndex(ELMNT_RSLT_TYP)));
				reportsData.setELMNT_MIN_RANGE(c.getString(c.getColumnIndex(ELMNT_MIN_RANGE)));
				reportsData.setELMNT_MAX_RANGE(c.getString(c.getColumnIndex(ELMNT_MAX_RANGE)));
				reportsData.setELMNT_RSLT_UNIT(c.getString(c.getColumnIndex(ELMNT_RSLT_UNIT)));
				reportsData.setRSLT(c.getString(c.getColumnIndex(RSLT)));
				reportsData.setP_CMMNT(c.getString(c.getColumnIndex(P_CMMNT)));

				reportsData.setRANGE_VAL(c.getInt(c.getColumnIndex(RANGE_VAL)));
				reportsData.setPDT_EL(c.getString(c.getColumnIndex(ACC_ID)) + c.getString(c.getColumnIndex(ELMNT_ID)));

				reportsData.setPRNT_RNG_TXT(c.getString(c.getColumnIndex(PRNT_RNG_TXT)));
				reportsData.setRSLT_NORMAL_FLAG(c.getString(c.getColumnIndex(RSLT_NORMAL_FLAG)));
				reportsData.setSR_NO(c.getInt(c.getColumnIndex(SR_NO)));
				reportsData.setPARENT_PRDCT_ID(c.getInt(c.getColumnIndex(PARENT_PRDCT_ID)));
				reportsData.setPARENT_PRDCT_CD(c.getString(c.getColumnIndex(PARENT_PRDCT_CD)));
				reportsData.setPARENT_PRDCT_NAME(c.getString(c.getColumnIndex(PARENT_PRDCT_NAME)));

				try
				{
					long acdate = Long.parseLong(c.getString(c.getColumnIndex(ACC_DT)));
					reportsData.setACC_DT(acdate);
					Log.e("string", c.getString(c.getColumnIndex(ACC_DT)) + "");
					Log.e("date", acdate + "");
				}
				catch (Exception e)
				{

				}
				reportsData.setPTNT_CD(c.getString(c.getColumnIndex(PTNT_CD)));
				reportsData.setGENERIC_NAME(c.getString(c.getColumnIndex(GENERIC_NAME)));
				reportsData.setCPT_CODE(c.getString(c.getColumnIndex(CPT_CODE)));

				if (reportsData != null)
				{
					reports_list.add(reportsData);
				}
			}
			while (c.moveToNext());
		}

		return reports_list;
	}

	public List<ReportsData> getCpdtCodeResult(String cpdt_code)
	{
		List<ReportsData> reports_list = new ArrayList<ReportsData>();
		String selectQuery = "";

		selectQuery = "SELECT  * FROM " + TABLE_Reports + " WHERE " + CPT_CODE + " = '" + cpdt_code + "' AND ("
				+ ELMNT_RSLT_TYP + " = 'N'" + " OR " + ELMNT_RSLT_TYP + "= 'X')" + " AND " + PTNT_CD + "= '"
				+ Constants.isMember + "'";

		Log.e(LOG, selectQuery);

		try
		{
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor c = db.rawQuery(selectQuery, null);

			// looping through all rows and adding to list
			if (c.moveToFirst())
			{
				do
				{
					ReportsData reportsData = new ReportsData();

					reportsData.setACC_ID(c.getString(c.getColumnIndex(ACC_ID)));
					reportsData.setPRDCT_ID(c.getInt(c.getColumnIndex(PRDCT_ID)));
					reportsData.setPRDCT_CD(c.getString(c.getColumnIndex(PRDCT_CD)));
					reportsData.setPRDCT_NAME(c.getString(c.getColumnIndex(PRDCT_NAME)));
					reportsData.setELMNT_ID(c.getInt(c.getColumnIndex(ELMNT_ID)));
					reportsData.setELMNT_CD(c.getString(c.getColumnIndex(ELMNT_CD)));
					reportsData.setELMNT_NAME(c.getString(c.getColumnIndex(ELMNT_NAME)));

					reportsData.setELMNT_RSLT_TYP(c.getString(c.getColumnIndex(ELMNT_RSLT_TYP)));
					reportsData.setELMNT_MIN_RANGE(c.getString(c.getColumnIndex(ELMNT_MIN_RANGE)));
					reportsData.setELMNT_MAX_RANGE(c.getString(c.getColumnIndex(ELMNT_MAX_RANGE)));
					reportsData.setELMNT_RSLT_UNIT(c.getString(c.getColumnIndex(ELMNT_RSLT_UNIT)));
					reportsData.setRSLT(c.getString(c.getColumnIndex(RSLT)));
					reportsData.setP_CMMNT(c.getString(c.getColumnIndex(P_CMMNT)));
					reportsData
							.setPDT_EL(c.getString(c.getColumnIndex(ACC_ID)) + c.getString(c.getColumnIndex(ELMNT_ID)));
					reportsData.setRANGE_VAL(c.getInt(c.getColumnIndex(RANGE_VAL)));

					reportsData.setPRNT_RNG_TXT(c.getString(c.getColumnIndex(PRNT_RNG_TXT)));
					reportsData.setRSLT_NORMAL_FLAG(c.getString(c.getColumnIndex(RSLT_NORMAL_FLAG)));
					reportsData.setSR_NO(c.getInt(c.getColumnIndex(SR_NO)));
					reportsData.setPARENT_PRDCT_ID(c.getInt(c.getColumnIndex(PARENT_PRDCT_ID)));
					reportsData.setPARENT_PRDCT_CD(c.getString(c.getColumnIndex(PARENT_PRDCT_CD)));
					reportsData.setPARENT_PRDCT_NAME(c.getString(c.getColumnIndex(PARENT_PRDCT_NAME)));
					reportsData
							.setPDT_EL(c.getString(c.getColumnIndex(ACC_ID)) + c.getString(c.getColumnIndex(ELMNT_ID)));
					try
					{
						long acdate = Long.parseLong(c.getString(c.getColumnIndex(ACC_DT)));
						reportsData.setACC_DT(acdate);
						Log.e("string", c.getString(c.getColumnIndex(ACC_DT)) + "");
						Log.e("date", acdate + "");
					}
					catch (Exception e)
					{

					}
					reportsData.setPTNT_CD(c.getString(c.getColumnIndex(PTNT_CD)));
					reportsData.setGENERIC_NAME(c.getString(c.getColumnIndex(GENERIC_NAME)));
					reportsData.setCPT_CODE(c.getString(c.getColumnIndex(CPT_CODE)));

					if (reportsData != null)
					{
						reports_list.add(reportsData);
					}
				}
				while (c.moveToNext());
			}
		}
		catch (Exception e)
		{

		}
		return reports_list;
	}

	//
	//	public List<BookTestORPackagesData> getAllBookATests()
	//	{
	//		List<BookTestORPackagesData> book_pkgs_list = new ArrayList<BookTestORPackagesData>();
	//		String selectQuery = "SELECT  * FROM " + CREATE_TABLE_REPORTS;
	//
	//		Log.e(LOG, selectQuery);
	//
	//		SQLiteDatabase db = this.getReadableDatabase();
	//		Cursor c = db.rawQuery(selectQuery, null);
	//
	//		// looping through all rows and adding to list
	//		if (c.moveToFirst())
	//		{
	//			do
	//			{
	//				BookTestORPackagesData book_pkgs = new BookTestORPackagesData();
	//				book_pkgs.setID(c.getInt(c.getColumnIndex(KEY_ID)));
	//				book_pkgs.setPRDCT_CODE((c.getString(c.getColumnIndex(KEY_PRDCT_CODE))));
	//				book_pkgs.setPRDCT_ALIASES(c.getString(c.getColumnIndex(KEY_PRDCT_ALIASES)));
	//				book_pkgs.setGROSS_AMT((c.getString(c.getColumnIndex(KEY_GROSS_AMT))));
	//				book_pkgs.setPRICE(c.getString(c.getColumnIndex(KEY_PRICE)));
	//				book_pkgs.setDISC((c.getString(c.getColumnIndex(KEY_DISC))));
	//				book_pkgs.setDISC_TYPE(c.getString(c.getColumnIndex(KEY_DISC_TYPE)));
	//				book_pkgs.setPRDCT_CONSTNS((c.getString(c.getColumnIndex(KEY_PRDCT_CONSTNS))));
	//				book_pkgs.setCATEGORY(c.getString(c.getColumnIndex(KEY_CATEGORY)));
	//				book_pkgs.setPTNT_INSTRCTN((c.getString(c.getColumnIndex(KEY_PTNT_INSTRCTN))));
	//				book_pkgs.setREP_TAT((c.getString(c.getColumnIndex(KEY_REP_TAT))));
	//				book_pkgs.setINFO(c.getString(c.getColumnIndex(KEY_INFO)));
	//				book_pkgs.setCartPrice(c.getString(c.getColumnIndex(KEY_CARTPRICE)));
	//				if (c.getString(c.getColumnIndex(KEY_IsCART)) != null && c.getString(c.getColumnIndex(KEY_IsCART)).equalsIgnoreCase("true"))
	//				{
	//					book_pkgs.setCart(true);
	//				}
	//				else
	//				{
	//					book_pkgs.setCart(false);
	//				}
	//				// adding to todo list
	//				book_pkgs_list.add(book_pkgs);
	//			}
	//			while (c.moveToNext());
	//		}
	//
	//		return book_pkgs_list;
	//	}
	//

	/*
	 * getting todo count
	 */
	public int getToDoCount()
	{
		String countQuery = "SELECT * FROM " + TABLE_Reports;
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
	public int updateReports(ReportsData reportsData)
	{
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(PDT_EL, reportsData.getACC_ID() + "" + reportsData.getELMNT_ID() + "");
		values.put(ACC_ID, reportsData.getACC_ID() + "");
		values.put(PRDCT_CD, reportsData.getPRDCT_CD() + "");
		values.put(PRDCT_ID, reportsData.getPRDCT_ID() + "");
		values.put(PRDCT_NAME, reportsData.getPRDCT_NAME() + "");
		values.put(ELMNT_ID, reportsData.getELMNT_ID() + "");
		values.put(ELMNT_NAME, reportsData.getELMNT_NAME() + "");
		values.put(RSLT, reportsData.getRSLT() + "");
		values.put(ELMNT_RSLT_TYP, reportsData.getELMNT_RSLT_TYP() + "");
		values.put(ELMNT_MIN_RANGE, reportsData.getELMNT_MIN_RANGE() + "");
		values.put(ELMNT_MAX_RANGE, reportsData.getELMNT_MAX_RANGE() + "");
		values.put(ELMNT_RSLT_UNIT, reportsData.getELMNT_RSLT_UNIT() + "");
		values.put(P_CMMNT, reportsData.getP_CMMNT() + "");
		if (reportsData.getRANGE_VAL() + "" != null && !(reportsData.getRANGE_VAL() + "").equalsIgnoreCase("null"))
		{
			values.put(RANGE_VAL, reportsData.getRANGE_VAL() + "");
		}
		else
		{
			values.put(RANGE_VAL, "0");
		}
		values.put(PRNT_RNG_TXT, reportsData.getPRNT_RNG_TXT() + "");
		values.put(RSLT_NORMAL_FLAG, reportsData.getRSLT_NORMAL_FLAG() + "");
		values.put(SR_NO, reportsData.getSR_NO() + "");
		values.put(PARENT_PRDCT_ID, reportsData.getPARENT_PRDCT_ID() + "");
		values.put(PARENT_PRDCT_CD, reportsData.getPARENT_PRDCT_CD() + "");
		values.put(PARENT_PRDCT_NAME, reportsData.getPARENT_PRDCT_NAME() + "");
		values.put(ACC_DT, reportsData.getACC_DT() + "");
		values.put(PTNT_CD, reportsData.getPTNT_CD() + "");
		values.put(GENERIC_NAME, reportsData.getGENERIC_NAME() + "");
		values.put(CPT_CODE, reportsData.getCPT_CODE() + "");

		// updating row
		try
		{
			return db.update(TABLE_Reports, values, PDT_EL + " = ?", new String[] { String.valueOf(
					reportsData.getACC_ID() + "" + reportsData.getELMNT_ID() + ""/*reportsData.getPDT_EL()*/) });
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return -1;
	}
	//

	//	/*
	//	 * Deleting a todo
	//	 */
	//	public void deleteToDo(long tado_id)
	//	{
	//		SQLiteDatabase db = this.getWritableDatabase();
	//		db.delete(CREATE_CREATE_TABLE_REPORTS, KEY_ID + " = ?",
	//		          new String[]{String.valueOf(tado_id)});
	//	}

	// ------------------------ "tags" table methods ----------------//

	// closing database
	public  void deleteTABLE_Reports()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from "+ TABLE_Reports);
		db.close();
	}
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
}
