package com.srllimited.srl.data;

import org.json.JSONObject;

/**
 * Created by RuchiTiwari on 12/26/2016.
 */

public class LabLocatorsData
{
	private static final String TAG_CNTCT_PRSN = "CNTCT_PRSN";

	private static final String TAG_LCTN_CD = "LCTN_CD";

	private static final String TAG_LCTN_NM = "LCTN_NM";

	private static final String TAG_ADDR = "ADDR";

	private static final String TAG_CITY = "CITY";

	private static final String TAG_STATE = "STATE";

	private static final String TAG_PHN = "PHN";

	private static final String TAG_EMAIL = "EMAIL";

	private static final String TAG_FAX = "FAX";

	private static final String TAG_ZIP = "ZIP";

	private static final String TAG_TYP = "TYP";

	private static final String TAG_MAP_LOCATION = "MAP_LOCATION";

	private static final String TAG_LATITUDE = "LATITUDE";

	private static final String TAG_LONGITUDE = "LONGITUDE";

	private static final String TAG_DISTANCE = "DISTANCE";

	private static final String TAG_SORT_ORDER = "SORT_ORDER";

	private static final String TAG_LAB_OPENING_TM = "LAB_OPENING_TM";

	private static final String TAG_LAB_CLOSING_TM = "LAB_CLOSING_TM";

	private static final String TAG_LAB_MSG = "LAB_MSG";

	private static final String TAG_LAB_ID = "LAB_ID";

	private static final String TAG_LABVISITALLOWED = "LABVISITALLOWED";

	private String CNTCT_PRSN;

	private String LCTN_CD;

	private String LCTN_NM;

	private String ADDR;

	private String CITY;

	private String STATE;

	private String PHN;

	private String EMAIL;

	private String FAX;

	private String ZIP;

	private String TYP;

	private String MAP_LOCATION;

	private double LATITUDE;

	private double LONGITUDE;

	private double DISTANCE;

	private double SORT_ORDER;

	private String LAB_OPENING_TM;

	private String LAB_CLOSING_TM;

	private String LAB_MSG;

	private double LAB_ID;

	private double LABVISITALLOWED;

	public static LabLocatorsData getGet(JSONObject jsonObject) throws Exception
	{
		LabLocatorsData labLocatorsData = new LabLocatorsData();

		labLocatorsData.setCNTCT_PRSN(jsonObject.getString(TAG_CNTCT_PRSN));
		labLocatorsData.setLCTN_CD(jsonObject.getString(TAG_LCTN_CD));
		labLocatorsData.setLCTN_NM(jsonObject.getString(TAG_LCTN_NM));

		labLocatorsData.setADDR(jsonObject.getString(TAG_ADDR));
		labLocatorsData.setCITY(jsonObject.getString(TAG_CITY));
		labLocatorsData.setSTATE(jsonObject.getString(TAG_STATE));

		labLocatorsData.setPHN(jsonObject.getString(TAG_PHN));
		labLocatorsData.setEMAIL(jsonObject.getString(TAG_EMAIL));
		labLocatorsData.setFAX(jsonObject.getString(TAG_FAX));

		labLocatorsData.setZIP(jsonObject.getString(TAG_ZIP));
		labLocatorsData.setTYP(jsonObject.getString(TAG_TYP));
		labLocatorsData.setMAP_LOCATION(jsonObject.getString(TAG_MAP_LOCATION));

		labLocatorsData.setLATITUDE(jsonObject.getDouble(TAG_LATITUDE));
		labLocatorsData.setLONGITUDE(jsonObject.getDouble(TAG_LONGITUDE));
		labLocatorsData.setDISTANCE(jsonObject.getDouble(TAG_DISTANCE));
		labLocatorsData.setSORT_ORDER(jsonObject.getDouble(TAG_SORT_ORDER));
		labLocatorsData.setLAB_OPENING_TM(jsonObject.getString(TAG_LAB_OPENING_TM));
		labLocatorsData.setLAB_CLOSING_TM(jsonObject.getString(TAG_LAB_CLOSING_TM));
		labLocatorsData.setLAB_MSG(jsonObject.getString(TAG_LAB_MSG));
		labLocatorsData.setLAB_ID(jsonObject.getDouble(TAG_LAB_ID));
		labLocatorsData.setLABVISITALLOWED(jsonObject.getDouble(TAG_LABVISITALLOWED));
		return labLocatorsData;
	}

	public String getCNTCT_PRSN()
	{
		return CNTCT_PRSN;
	}

	public void setCNTCT_PRSN(final String CNTCT_PRSN)
	{
		this.CNTCT_PRSN = CNTCT_PRSN;
	}

	public String getLCTN_CD()
	{
		return LCTN_CD;
	}

	public void setLCTN_CD(final String LCTN_CD)
	{
		this.LCTN_CD = LCTN_CD;
	}

	public String getLCTN_NM()
	{
		return LCTN_NM;
	}

	public void setLCTN_NM(final String LCTN_NM)
	{
		this.LCTN_NM = LCTN_NM;
	}

	public String getADDR()
	{
		return ADDR;
	}

	public void setADDR(final String ADDR)
	{
		this.ADDR = ADDR;
	}

	public String getCITY()
	{
		return CITY;
	}

	public void setCITY(final String CITY)
	{

		this.CITY = CITY;
	}

	public String getLAB_OPENING_TM()
	{
		return LAB_OPENING_TM;
	}

	public void setLAB_OPENING_TM(final String LAB_OPENING_TM)
	{
		this.LAB_OPENING_TM = LAB_OPENING_TM;
	}

	public String getLAB_CLOSING_TM()
	{
		return LAB_CLOSING_TM;
	}

	public void setLAB_CLOSING_TM(final String LAB_CLOSING_TM)
	{
		this.LAB_CLOSING_TM = LAB_CLOSING_TM;
	}

	public String getLAB_MSG()
	{
		return LAB_MSG;
	}

	public void setLAB_MSG(final String LAB_MSG)
	{
		this.LAB_MSG = LAB_MSG;
	}

	public String getSTATE()
	{
		return STATE;
	}

	public void setSTATE(final String STATE)
	{
		this.STATE = STATE;
	}

	public String getPHN()
	{
		return PHN;
	}

	public void setPHN(final String PHN)
	{
		this.PHN = PHN;
	}

	public String getEMAIL()
	{
		return EMAIL;
	}

	public void setEMAIL(final String EMAIL)
	{
		this.EMAIL = EMAIL;
	}

	public String getFAX()
	{
		return FAX;
	}

	public void setFAX(final String FAX)
	{
		this.FAX = FAX;
	}

	public String getZIP()
	{
		return ZIP;
	}

	public void setZIP(final String ZIP)
	{
		this.ZIP = ZIP;
	}

	public String getTYP()
	{
		return TYP;
	}

	public void setTYP(final String TYP)
	{
		this.TYP = TYP;
	}

	public String getMAP_LOCATION()
	{
		return MAP_LOCATION;
	}

	public void setMAP_LOCATION(final String MAP_LOCATION)
	{
		this.MAP_LOCATION = MAP_LOCATION;
	}

	public double getLATITUDE()
	{
		return LATITUDE;
	}

	public void setLATITUDE(final double LATITUDE)
	{
		this.LATITUDE = LATITUDE;
	}

	public double getLAB_ID()
	{

		return LAB_ID;
	}

	public void setLAB_ID(final double LAB_ID)
	{
		this.LAB_ID = LAB_ID;
	}

	public double getLABVISITALLOWED()
	{

		return LABVISITALLOWED;
	}

	public void setLABVISITALLOWED(final double LABVISITALLOWED)
	{
		this.LABVISITALLOWED = LABVISITALLOWED;
	}

	public double getLONGITUDE()
	{
		return LONGITUDE;
	}

	public void setLONGITUDE(final double LONGITUDE)
	{
		this.LONGITUDE = LONGITUDE;
	}

	public double getDISTANCE()
	{
		return DISTANCE;
	}

	public void setDISTANCE(final double DISTANCE)
	{
		this.DISTANCE = DISTANCE;
	}

	public double getSORT_ORDER()
	{
		return SORT_ORDER;
	}

	public void setSORT_ORDER(final double SORT_ORDER)
	{
		this.SORT_ORDER = SORT_ORDER;
	}

	@Override
	public String toString()
	{
		return "LabLocatorsData{" + "CNTCT_PRSN='" + CNTCT_PRSN + '\'' + ", LCTN_CD='" + LCTN_CD + '\'' + ", LCTN_NM='"
				+ LCTN_NM + '\'' + ", ADDR='" + ADDR + '\'' + ", CITY='" + CITY + '\'' + ", STATE='" + STATE + '\''
				+ ", PHN='" + PHN + '\'' + ", EMAIL='" + EMAIL + '\'' + ", FAX='" + FAX + '\'' + ", ZIP='" + ZIP + '\''
				+ ", TYP='" + TYP + '\'' + ", MAP_LOCATION='" + MAP_LOCATION + '\'' + ", LATITUDE=" + LATITUDE
				+ ", LONGITUDE=" + LONGITUDE + ", DISTANCE=" + DISTANCE + ", SORT_ORDER=" + SORT_ORDER
				+ ", LAB_OPENING_TM='" + LAB_OPENING_TM + '\'' + ", LAB_CLOSING_TM='" + LAB_CLOSING_TM + '\''
				+ ", LAB_MSG='" + LAB_MSG + '\'' + ", LAB_ID=" + LAB_ID + ", LABVISITALLOWED=" + LABVISITALLOWED + '}';
	}
}
