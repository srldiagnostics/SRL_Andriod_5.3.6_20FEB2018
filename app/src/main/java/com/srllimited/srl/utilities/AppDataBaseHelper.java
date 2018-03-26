package com.srllimited.srl.utilities;

import java.util.ArrayList;
import java.util.List;

import com.srllimited.srl.data.BmiUsersData;
import com.srllimited.srl.data.UserData;
import com.srllimited.srl.util.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AppDataBaseHelper extends SQLiteOpenHelper
{
	public static final String dbName = "MY_SRLDB";

	public static final String oneTimeRegistration = "oneTimeRegistration";

	public static final String deviceID = "deviceID";

	public static final String REGISTER_TABLE = "register";

	public static final String EMAIL_COLUMN = "email";

	public static final String USERNAME_COLUMN = "username";

	public static final String PASSWORD_COLUMN = "password";

	public static final String USER_TABLE = "userdata";

	public static final String SurveyQuestion_TABLE = "surveyquestion";

	public static final String SurveyAnswer_TABLE = "surveyanswer";



	public static final String USER_ID = "userid";

	public static final String PTNT_CD = "ptntcd";

	public static final String PTNT_TITTLE = "ptnttitle";

	public static final String FIRST_NAME = "firstname";

	public static final String LAST_NAME = "lastname";

	public static final String GENDER = "gender";

	public static final String DOB = "dob";

	public static final String MARITAL_STATUS = "maritalstatus";

	public static final String EMAIL_ID = "emailid";

	public static final String ZIP = "zip";

	public static final String MOBILE_NO = "mobileno";

	public static final String ADDRESS1 = "address";

	public static final String ADDRESS2 = "address2";

	public static final String LANDMARK = "landmark";

	public static final String CITY = "city";

	public static final String STATE = "state";

	public static final String COUNTRY = "country";

	public static final String PARENT_ID = "parentid";

	public static final String STATUS = "status";
	public static final String BMI_CALCULATION_TABLE = "bmitable";
	public static final String BMI_USER_NAME = "userName";
	public static final String BMI_GENDER = "gender";
	public static final String BMI_HEIGHT = "height";
	public static final String BMI_WEIGHT = "weight";
	public static final String BMI_MASS_INDEX = "massindex";
	public static final String BMI_IDEAL_WEIGHT = "idealweight";
	public static final String BMI_INDICATION = "indication";
	private static final String LOG = "AppDataBaseHelper";

	public AppDataBaseHelper(Context _ctx)
	{
		super(_ctx, dbName, null, 51);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		//db.execSQL("CREATE TABLE " + REGISTER_TABLE + " (" + EMAIL_COLUMN + " TEXT, " + USERNAME_COLUMN + " TEXT, " + PASSWORD_COLUMN + " TEXT)");
		db.execSQL("CREATE TABLE " + USER_TABLE + " (" + USER_ID + " TEXT, " + PTNT_CD + " TEXT, " + PTNT_TITTLE
				+ " TEXT, " + FIRST_NAME + " TEXT, " + LAST_NAME + " TEXT, " + PASSWORD_COLUMN + " TEXT, " + GENDER
				+ " TEXT, " + DOB + " TEXT, " + MARITAL_STATUS + " TEXT, " + EMAIL_ID + " TEXT, " + ZIP + " TEXT, "
				+ MOBILE_NO + " TEXT, " + ADDRESS1 + " TEXT, " + ADDRESS2 + " TEXT, " + LANDMARK + " TEXT, " + CITY
				+ " TEXT, " + STATE + " TEXT, " + COUNTRY + " TEXT, " + STATUS + " TEXT, " + PARENT_ID + " TEXT)");
		db.execSQL("CREATE TABLE " + BMI_CALCULATION_TABLE + " (" + BMI_USER_NAME + " TEXT, " + BMI_GENDER + " TEXT, "
				+ BMI_HEIGHT + " TEXT, " + BMI_WEIGHT + " TEXT, " + BMI_MASS_INDEX + " TEXT, " + BMI_IDEAL_WEIGHT
				+ " TEXT, " + BMI_INDICATION + " TEXT)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		//db.execSQL("DROP TABLE IF EXISTS " + REGISTER_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
		db.execSQL("DROP TABLE IF EXISTS " + BMI_CALCULATION_TABLE);
		onCreate(db);
	}

	public long addUserDetails(UserData _userdata, Context context)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		//db.execSQL("DELETE FROM " + USER_TABLE);
		ContentValues cv = new ContentValues();
		cv.put(USER_ID, _userdata.getUserid() + "");
		cv.put(PTNT_CD, _userdata.getPtnt_cd());
		cv.put(PTNT_TITTLE, _userdata.getPtnt_tittle());

		cv.put(FIRST_NAME, _userdata.getFirst_name());
		if (_userdata.getLast_name() != null && !_userdata.getLast_name().equalsIgnoreCase("null"))
			cv.put(LAST_NAME, _userdata.getLast_name());
		else
			cv.put(LAST_NAME, "");
		cv.put(GENDER, _userdata.getGender());
		cv.put(DOB, _userdata.getDob() + "");
		cv.put(MARITAL_STATUS, _userdata.getMarital_status());
		cv.put(EMAIL_ID, _userdata.getEmail_id());
		cv.put(ZIP, _userdata.getZip());
		cv.put(MOBILE_NO, _userdata.getMobile_no());
		cv.put(ADDRESS1, _userdata.getAddress1());
		cv.put(ADDRESS2, _userdata.getAddress2());
		cv.put(LANDMARK, _userdata.getLandmark());
		cv.put(CITY, _userdata.getCity());
		cv.put(STATE, _userdata.getState());
		cv.put(COUNTRY, _userdata.getCountry());
		cv.put(PASSWORD_COLUMN, _userdata.getPwd());
		cv.put(PARENT_ID, _userdata.getParent_id());
		if (_userdata.getPtnt_cd() != null && _userdata.getPtnt_cd().equalsIgnoreCase(Util.getStoredUsername(context)))
			cv.put(STATUS, "true");
		else
			cv.put(STATUS, "false");
		return db.insert(USER_TABLE, null, cv);
	}

	public UserData getLoginDetails()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("SELECT * FROM " + USER_TABLE, new String[] {});
		UserData loginDetails = null;
		if (cur.moveToFirst())
		{
			do
			{
				loginDetails = new UserData();
				loginDetails.setUserid(cur.getString(cur.getColumnIndex(USER_ID)));
				loginDetails.setPwd(cur.getString(cur.getColumnIndex(PASSWORD_COLUMN)));
				loginDetails.setPtnt_cd(cur.getString(cur.getColumnIndex(PTNT_CD)));
				loginDetails.setPtnt_tittle(cur.getString(cur.getColumnIndex(PTNT_TITTLE)));
				loginDetails.setFirst_name(cur.getString(cur.getColumnIndex(FIRST_NAME)));
				loginDetails.setLast_name(cur.getString(cur.getColumnIndex(LAST_NAME)));
				loginDetails.setGender(cur.getString(cur.getColumnIndex(GENDER)));
				try
				{
					loginDetails.setDob(cur.getLong(cur.getColumnIndex(DOB)));
				}
				catch (Exception e)
				{

				}
				loginDetails.setMarital_status(cur.getString(cur.getColumnIndex(MARITAL_STATUS)));
				loginDetails.setEmail_id(cur.getString(cur.getColumnIndex(EMAIL_ID)));
				loginDetails.setZip(cur.getString(cur.getColumnIndex(ZIP)));
				loginDetails.setMobile_no(cur.getString(cur.getColumnIndex(MOBILE_NO)));
				loginDetails.setAddress1(cur.getString(cur.getColumnIndex(ADDRESS1)));
				loginDetails.setAddress2(cur.getString(cur.getColumnIndex(ADDRESS2)));
				loginDetails.setLandmark(cur.getString(cur.getColumnIndex(LANDMARK)));
				loginDetails.setCity(cur.getString(cur.getColumnIndex(CITY)));
				loginDetails.setState(cur.getString(cur.getColumnIndex(STATE)));
				loginDetails.setCountry(cur.getString(cur.getColumnIndex(COUNTRY)));
				loginDetails.setParent_id(cur.getString(cur.getColumnIndex(PARENT_ID)));
				loginDetails.setStatus(cur.getString(cur.getColumnIndex(STATUS)));
			}
			while (cur.moveToNext());
		}
		cur.close();
		db.close();
		if (loginDetails != null)
		{
			return loginDetails;
		}
		else
		{
			return null;
		}
	}

	public List<UserData> getUSersList()
	{
		List<UserData> user_datas_list = new ArrayList<UserData>();
		String selectQuery = "SELECT  * FROM " + USER_TABLE;

		Log.e("", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cur.moveToFirst())
		{
			do
			{
				UserData loginDetails = new UserData();
				loginDetails.setUserid(cur.getString(cur.getColumnIndex(USER_ID)));
				loginDetails.setPtnt_cd(cur.getString(cur.getColumnIndex(PTNT_CD)));
				loginDetails.setPwd(cur.getString(cur.getColumnIndex(PASSWORD_COLUMN)));
				loginDetails.setPtnt_tittle(cur.getString(cur.getColumnIndex(PTNT_TITTLE)));
				loginDetails.setFirst_name(cur.getString(cur.getColumnIndex(FIRST_NAME)));
				loginDetails.setLast_name(cur.getString(cur.getColumnIndex(LAST_NAME)));
				loginDetails.setGender(cur.getString(cur.getColumnIndex(GENDER)));
				try
				{
					loginDetails.setDob(cur.getLong(cur.getColumnIndex(DOB)));
				}
				catch (Exception e)
				{

				}
				loginDetails.setMarital_status(cur.getString(cur.getColumnIndex(MARITAL_STATUS)));
				loginDetails.setEmail_id(cur.getString(cur.getColumnIndex(EMAIL_ID)));
				loginDetails.setZip(cur.getString(cur.getColumnIndex(ZIP)));
				loginDetails.setMobile_no(cur.getString(cur.getColumnIndex(MOBILE_NO)));
				loginDetails.setAddress1(cur.getString(cur.getColumnIndex(ADDRESS1)));
				loginDetails.setAddress2(cur.getString(cur.getColumnIndex(ADDRESS2)));
				loginDetails.setLandmark(cur.getString(cur.getColumnIndex(LANDMARK)));
				loginDetails.setCity(cur.getString(cur.getColumnIndex(CITY)));
				loginDetails.setState(cur.getString(cur.getColumnIndex(STATE)));
				loginDetails.setCountry(cur.getString(cur.getColumnIndex(COUNTRY)));
				loginDetails.setParent_id(cur.getString(cur.getColumnIndex(PARENT_ID)));
				loginDetails.setStatus(cur.getString(cur.getColumnIndex(STATUS)));
				/*if (cur.getString(cur.getColumnIndex(STATUS)) != null && cur.getString(cur.getColumnIndex(STATUS)).equalsIgnoreCase("true")) {
				    loginDetails.setStatus("true");
				} else {
				    loginDetails.setStatus("false");
				}*/
				// adding to todo list
				user_datas_list.add(loginDetails);
			}
			while (cur.moveToNext());
		}

		return user_datas_list;
	}

	public long addBmiUserDetails(BmiUsersData bmiUsersData)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(BMI_USER_NAME, bmiUsersData.getUserName_bmi() + "");
		cv.put(BMI_GENDER, bmiUsersData.getGender_bmi());
		cv.put(BMI_HEIGHT, bmiUsersData.getHeight_bmi());
		cv.put(BMI_WEIGHT, bmiUsersData.getWeight_bmi());
		cv.put(BMI_MASS_INDEX, bmiUsersData.getMassindex_bmi());
		cv.put(BMI_IDEAL_WEIGHT, bmiUsersData.getIdealweight_bmi());
		cv.put(BMI_INDICATION, bmiUsersData.getIndication_bmi());
		return db.insert(BMI_CALCULATION_TABLE, null, cv);
	}

	public BmiUsersData getBmiDetails()
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery("SELECT * FROM " + BMI_CALCULATION_TABLE, new String[] {});
		BmiUsersData bmiData = null;
		if (cur.moveToFirst())
		{
			do
			{
				bmiData = new BmiUsersData();
				bmiData.setUserName_bmi(cur.getString(cur.getColumnIndex(BMI_USER_NAME)));
				bmiData.setGender_bmi(cur.getString(cur.getColumnIndex(BMI_GENDER)));
				bmiData.setHeight_bmi(cur.getString(cur.getColumnIndex(BMI_HEIGHT)));
				bmiData.setWeight_bmi(cur.getString(cur.getColumnIndex(BMI_WEIGHT)));
				bmiData.setMassindex_bmi(cur.getString(cur.getColumnIndex(BMI_MASS_INDEX)));
				bmiData.setIdealweight_bmi(cur.getString(cur.getColumnIndex(BMI_IDEAL_WEIGHT)));
				bmiData.setIndication_bmi(cur.getString(cur.getColumnIndex(BMI_INDICATION)));
			}
			while (cur.moveToNext());
		}
		cur.close();
		db.close();
		if (bmiData != null)
		{
			return bmiData;
		}
		else
		{
			return null;
		}
	}

	public List<BmiUsersData> getBmiListOfDetails()
	{
		List<BmiUsersData> bmi_list_data = new ArrayList<BmiUsersData>();
		String selectQuery = "SELECT  * FROM " + BMI_CALCULATION_TABLE;

		Log.e("", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cur = db.rawQuery(selectQuery, null);

		BmiUsersData bmiData = null;
		// looping through all rows and adding to list
		if (cur.moveToFirst())
		{
			do
			{
				bmiData = new BmiUsersData();
				bmiData.setUserName_bmi(cur.getString(cur.getColumnIndex(BMI_USER_NAME)));
				bmiData.setGender_bmi(cur.getString(cur.getColumnIndex(BMI_GENDER)));
				bmiData.setHeight_bmi(cur.getString(cur.getColumnIndex(BMI_HEIGHT)));
				bmiData.setWeight_bmi(cur.getString(cur.getColumnIndex(BMI_WEIGHT)));
				bmiData.setMassindex_bmi(cur.getString(cur.getColumnIndex(BMI_MASS_INDEX)));
				bmiData.setIdealweight_bmi(cur.getString(cur.getColumnIndex(BMI_IDEAL_WEIGHT)));
				bmiData.setIndication_bmi(cur.getString(cur.getColumnIndex(BMI_INDICATION)));
				// adding to todo list
				bmi_list_data.add(bmiData);
			}
			while (cur.moveToNext());
		}

		return bmi_list_data;

		///////////////
	}

	public BmiUsersData getSelectBmiUserData(String bmi_user_name)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		String selectQuery = "SELECT  * FROM " + BMI_CALCULATION_TABLE + " WHERE " + BMI_USER_NAME + " = '"
				+ bmi_user_name + "'";
		Log.e(LOG, selectQuery);
		Cursor cur = db.rawQuery(selectQuery, null);
		if (cur != null)
		{
			cur.moveToFirst();
		}
		BmiUsersData bmiData = new BmiUsersData();
		try
		{
			bmiData = new BmiUsersData();
			bmiData.setUserName_bmi(cur.getString(cur.getColumnIndex(BMI_USER_NAME)));
			bmiData.setGender_bmi(cur.getString(cur.getColumnIndex(BMI_GENDER)));
			bmiData.setHeight_bmi(cur.getString(cur.getColumnIndex(BMI_HEIGHT)));
			bmiData.setWeight_bmi(cur.getString(cur.getColumnIndex(BMI_WEIGHT)));
			bmiData.setMassindex_bmi(cur.getString(cur.getColumnIndex(BMI_MASS_INDEX)));
			bmiData.setIdealweight_bmi(cur.getString(cur.getColumnIndex(BMI_IDEAL_WEIGHT)));
			bmiData.setIndication_bmi(cur.getString(cur.getColumnIndex(BMI_INDICATION)));
		}
		catch (Exception e)
		{
			Log.e(LOG, e + "");
		}
		return bmiData;
	}

	//	public UserData getSelectedUserDetail(String ptnt) {
	//
	//		String selectQuery = "SELECT  * FROM " + USER_TABLE + " WHERE " + USER_ID + " = "+ptnt+"";
	//
	//		Log.e("", selectQuery);
	//		UserData loginDetails = new UserData();
	//		SQLiteDatabase db = this.getReadableDatabase();
	//		Cursor cur = db.rawQuery(selectQuery, null);
	//
	//		// looping through all rows and adding to list
	//		if (cur.moveToFirst()) {
	//			do {
	//
	//				loginDetails.setUserid(cur.getString(cur.getColumnIndex(USER_ID)));
	//				loginDetails.setPtnt_cd(cur.getString(cur.getColumnIndex(PTNT_CD)));
	//				loginDetails.setPtnt_tittle(cur.getString(cur.getColumnIndex(PTNT_TITTLE)));
	//				loginDetails.setFirst_name(cur.getString(cur.getColumnIndex(FIRST_NAME)));
	//				loginDetails.setLast_name(cur.getString(cur.getColumnIndex(LAST_NAME)));
	//				loginDetails.setGender(cur.getString(cur.getColumnIndex(GENDER)));
	//				try {
	//					loginDetails.setDob(cur.getLong(cur.getColumnIndex(DOB)));
	//				} catch (Exception e) {
	//
	//				}
	//				loginDetails.setMarital_status(cur.getString(cur.getColumnIndex(MARITAL_STATUS)));
	//				loginDetails.setEmail_id(cur.getString(cur.getColumnIndex(EMAIL_ID)));
	//				loginDetails.setZip(cur.getString(cur.getColumnIndex(ZIP)));
	//				loginDetails.setMobile_no(cur.getString(cur.getColumnIndex(MOBILE_NO)));
	//				loginDetails.setAddress1(cur.getString(cur.getColumnIndex(ADDRESS1)));
	//				loginDetails.setAddress2(cur.getString(cur.getColumnIndex(ADDRESS2)));
	//				loginDetails.setLandmark(cur.getString(cur.getColumnIndex(LANDMARK)));
	//				loginDetails.setCity(cur.getString(cur.getColumnIndex(CITY)));
	//				loginDetails.setState(cur.getString(cur.getColumnIndex(STATE)));
	//				loginDetails.setCountry(cur.getString(cur.getColumnIndex(COUNTRY)));
	//				loginDetails.setParent_id(cur.getString(cur.getColumnIndex(PARENT_ID)));
	//				loginDetails.setStatus(cur.getString(cur.getColumnIndex(STATUS)));
	//
	//
	//			}
	//			while (cur.moveToNext());
	//		}
	//
	//		return loginDetails;
	//	}

	public UserData getSelectedUserDetail(String ptnt)
	{
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + USER_TABLE + " WHERE " + PTNT_CD + " = '" + ptnt + "'";
		Log.e(LOG, selectQuery);

		Cursor cur = db.rawQuery(selectQuery, null);

		if (cur != null)
		{
			cur.moveToFirst();
		}

		UserData loginDetails = new UserData();

		try
		{
			//			loginDetails.setUserid(cur.getString(cur.getColumnIndex(USER_ID)));
			loginDetails.setPtnt_cd(cur.getString(cur.getColumnIndex(PTNT_CD)));
			loginDetails.setPwd(cur.getString(cur.getColumnIndex(PASSWORD_COLUMN)));
			loginDetails.setPtnt_tittle(cur.getString(cur.getColumnIndex(PTNT_TITTLE)));
			loginDetails.setFirst_name(cur.getString(cur.getColumnIndex(FIRST_NAME)));
			loginDetails.setLast_name(cur.getString(cur.getColumnIndex(LAST_NAME)));
			loginDetails.setGender(cur.getString(cur.getColumnIndex(GENDER)));
			try
			{
				loginDetails.setDob(cur.getLong(cur.getColumnIndex(DOB)));
			}
			catch (Exception e)
			{

			}
			loginDetails.setMarital_status(cur.getString(cur.getColumnIndex(MARITAL_STATUS)));
			loginDetails.setEmail_id(cur.getString(cur.getColumnIndex(EMAIL_ID)));
			loginDetails.setZip(cur.getString(cur.getColumnIndex(ZIP)));
			loginDetails.setMobile_no(cur.getString(cur.getColumnIndex(MOBILE_NO)));
			loginDetails.setAddress1(cur.getString(cur.getColumnIndex(ADDRESS1)));
			loginDetails.setAddress2(cur.getString(cur.getColumnIndex(ADDRESS2)));
			loginDetails.setLandmark(cur.getString(cur.getColumnIndex(LANDMARK)));
			loginDetails.setCity(cur.getString(cur.getColumnIndex(CITY)));
			loginDetails.setState(cur.getString(cur.getColumnIndex(STATE)));
			loginDetails.setCountry(cur.getString(cur.getColumnIndex(COUNTRY)));
			loginDetails.setParent_id(cur.getString(cur.getColumnIndex(PARENT_ID)));
			loginDetails.setStatus(cur.getString(cur.getColumnIndex(STATUS)));

		}
		catch (Exception e)
		{
			Log.e(LOG, e + "");
		}

		return loginDetails;
	}

	public String getMainUserSelection(String status)
	{
		SQLiteDatabase db = getWritableDatabase();
		String selectString = "SELECT * FROM " + USER_TABLE + " WHERE " + STATUS + " =?";

		Cursor cursor = db.rawQuery(selectString, new String[] { status });
		boolean hasObject = false;
		String username = "";

		if (cursor.moveToFirst())
		{
			UserData loginDetails = new UserData();

			try
			{
				username = cursor.getString(cursor.getColumnIndex(PTNT_CD));
			}
			catch (Exception e)
			{
				Log.e(LOG, e + "");
			}

			int count = 0;
			while (cursor.moveToNext())
			{
				count++;
			}

		}
		cursor.close(); // Dont forget to close your cursor
		db.close(); //AND your Database!
		return username;
	}

	public int updateStatus(UserData updateStatus, String positive)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(PTNT_CD, updateStatus.getPtnt_cd());
		cv.put(STATUS, updateStatus.getStatus());
		// updating row
		return db.update(USER_TABLE, cv, STATUS + " = ?", new String[] { String.valueOf(positive) });

	}

	public boolean CheckIsDataAlreadyInDBorNot(String id)
	{
		SQLiteDatabase db = getWritableDatabase();
		String selectString = "SELECT * FROM " + USER_TABLE + " WHERE " + PTNT_CD + " =?";
		// Add the String you are searching by here.
		// Put it in an array to avoid an unrecognized token error
		Cursor cursor = db.rawQuery(selectString, new String[] { id });
		boolean hasObject = false;
		if (cursor.moveToFirst())
		{
			hasObject = true;
			//region if you had multiple records to check for, use this region.
			int count = 0;
			while (cursor.moveToNext())
			{
				count++;
			}
			//here, count is records found
			Log.d("", String.format("%d records found", count));
			//endregion
		}
		cursor.close(); // Dont forget to close your cursor
		db.close(); //AND your Database!
		return hasObject;
	}

	public void updateUserDetails(UserData _userdata, Context context)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(USER_ID, _userdata.getUserid());
		cv.put(PASSWORD_COLUMN, _userdata.getPwd());
		cv.put(PTNT_CD, _userdata.getPtnt_cd());
		cv.put(PTNT_TITTLE, _userdata.getPtnt_tittle());
		cv.put(FIRST_NAME, _userdata.getFirst_name());
		cv.put(FIRST_NAME, _userdata.getFirst_name());
		if (_userdata.getLast_name() != null && !_userdata.getLast_name().equalsIgnoreCase("null"))
			cv.put(LAST_NAME, _userdata.getLast_name());
		else
			cv.put(LAST_NAME, "");
		cv.put(GENDER, _userdata.getGender());
		cv.put(DOB, _userdata.getDob() + "");
		cv.put(MARITAL_STATUS, _userdata.getMarital_status());
		cv.put(EMAIL_ID, _userdata.getEmail_id());
		cv.put(ZIP, _userdata.getZip());
		cv.put(MOBILE_NO, _userdata.getMobile_no());
		cv.put(ADDRESS1, _userdata.getAddress1());
		cv.put(ADDRESS2, _userdata.getAddress2());
		cv.put(LANDMARK, _userdata.getLandmark());
		cv.put(CITY, _userdata.getCity());
		cv.put(STATE, _userdata.getState());
		cv.put(COUNTRY, _userdata.getCountry());
		cv.put(PARENT_ID, _userdata.getParent_id());
		if (_userdata.getPtnt_cd() != null && _userdata.getPtnt_cd().equalsIgnoreCase(Util.getStoredUsername(context)))
			cv.put(STATUS, "true");
		else
			cv.put(STATUS, "false");
		// updating row
		db.update(USER_TABLE, cv, PTNT_CD + " = ?", new String[] { String.valueOf(_userdata.getPtnt_cd()) });
		db.close();
		//return db.update(USER_TABLE, PTNT_CD + "='" + Ptntcd , null) > 0;
	}

	public boolean deleteUser(String ptntcd)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(USER_TABLE, PTNT_CD + "='" + ptntcd + "' ;", null) > 0;

	}

	public void deleteBmiData()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(BMI_CALCULATION_TABLE, null, null);
	}

	public void deleteFamilyMemberData()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(USER_TABLE, null, null);
	}

}
