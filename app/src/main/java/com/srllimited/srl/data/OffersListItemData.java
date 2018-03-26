package com.srllimited.srl.data;

/**
 * Created by codefyneandroid on 24-12-2016.
 */

public class OffersListItemData
{
	String offerTitle, offerDetails;
	int circleColor;
	private int ID;
	private String DESCRIPTION;
	private String IMAGE_URL;
	private String CREATED_DATE;
	private String ACTIVE_FLAG;
	private String NAME;
	private String FRM_DT;
	private long TO_DT;
	private String SEQUENCE;
	private String TESTCODE;
	private String DISCOUNT_TYPE;
	private String PERC_AMT;
	private String ACTION;
	private String BANNER_FLG;

	/*public OffersListItemData(int ID, String offerTitle, String offerDetails, int circleColor) {
	    this.ID = ID;
	    this.offerTitle = offerTitle;
	    this.offerDetails = offerDetails;
	    this.circleColor = circleColor;
	}*/

	public int getID()
	{
		return ID;
	}

	//ID
	public void setID(final int ID)
	{
		this.ID = ID;
	}

	public String getDESCRIPTION()
	{
		return DESCRIPTION;
	}

	//DESCRIPTION
	public void setDESCRIPTION(final String DESCRIPTION)
	{
		this.DESCRIPTION = DESCRIPTION;
	}

	public String getIMAGE_URL()
	{
		return IMAGE_URL;
	}

	//IMAGE_URL
	public void setIMAGE_URL(final String IMAGE_URL)
	{
		this.IMAGE_URL = IMAGE_URL;
	}

	//CREATED_DATE
	public void setCREATED_DATE(final String CREATED_DATE)
	{
		this.CREATED_DATE = CREATED_DATE;
	}

	public void getCREATED_DATE(final String CREATED_DATE)
	{
		this.CREATED_DATE = CREATED_DATE;
	}

	//ACTIVE_FLAG
	public void setACTIVE_FLAG(final String ACTIVE_FLAG)
	{
		this.ACTIVE_FLAG = ACTIVE_FLAG;
	}

	public void getACTIVE_FLAG(final String ACTIVE_FLAG)
	{
		this.ACTIVE_FLAG = ACTIVE_FLAG;
	}

	public String getNAME()
	{
		return NAME;
	}

	//NAME
	public void setNAME(final String NAME)
	{
		this.NAME = NAME;
	}

	public String getFRM_DT()
	{
		return FRM_DT;
	}

	//FRM_DT
	public void setFRM_DT(final String FRM_DT)
	{
		this.FRM_DT = FRM_DT;
	}

	public long getTO_DT()
	{
		return TO_DT;
	}

	//TO_DT
	public void setTO_DT(final long TO_DT)
	{
		this.TO_DT = TO_DT;
	}

	public String getSEQUENCE()
	{
		return SEQUENCE;
	}

	//SEQUENCE
	public void setSEQUENCE(final String SEQUENCE)
	{
		this.SEQUENCE = SEQUENCE;
	}

	public String getTESTCODE()
	{
		return TESTCODE;
	}

	//TESTCODE
	public void setTESTCODE(final String TESTCODE)
	{
		this.TESTCODE = TESTCODE;
	}

	public String getDISCOUNT_TYPE()
	{
		return DISCOUNT_TYPE;
	}

	//DISCOUNT_TYPE
	public void setDISCOUNT_TYPE(final String DISCOUNT_TYPE)
	{
		this.DISCOUNT_TYPE = DISCOUNT_TYPE;
	}

	public String getPERC_AMT()
	{
		return PERC_AMT;
	}

	//PERC_AMT
	public void setPERC_AMT(final String PERC_AMT)
	{
		this.PERC_AMT = PERC_AMT;
	}

	public String getACTION()
	{
		return ACTION;
	}

	//ACTION
	public void setACTION(final String ACTION)
	{
		this.ACTION = ACTION;
	}

	public String getBANNER_FLG()
	{
		return BANNER_FLG;
	}

	//BANNER_FLG
	public void setBANNER_FLG(final String BANNER_FLG)
	{
		this.BANNER_FLG = BANNER_FLG;
	}

	public int getCircleColor()
	{
		return circleColor;
	}

	public String getOfferDetails()
	{
		return offerDetails;
	}

	public String getOfferTitle()
	{
		return offerTitle;
	}
}
