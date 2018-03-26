package com.srllimited.srl.data;

/**
 * Created by RuchiTiwari on 2/3/2017.
 */

public class CarouselData
{
	private double ID;

	private String DESCRIPTION;

	private String IMAGE_URL;

	private long CREATED_DATE;

	private String ACTIVE_FLAG;

	private String NAME;

	private long FRM_DT;

	private long TO_DT;

	private double SEQUENCE;

	private String TESTCODE;

	private String DISCOUNT_TYPE;

	private String PERC_AM;

	private double ACTION;

	private String BANNER_FLG;

	public double getID()
	{
		return ID;
	}

	public void setID(final double ID)
	{
		this.ID = ID;
	}

	public String getDESCRIPTION()
	{
		return DESCRIPTION;
	}

	public void setDESCRIPTION(final String DESCRIPTION)
	{
		this.DESCRIPTION = DESCRIPTION;
	}

	public String getIMAGE_URL()
	{
		return IMAGE_URL;
	}

	public void setIMAGE_URL(final String IMAGE_URL)
	{
		this.IMAGE_URL = IMAGE_URL;
	}

	public long getCREATED_DATE()
	{
		return CREATED_DATE;
	}

	public void setCREATED_DATE(final long CREATED_DATE)
	{
		this.CREATED_DATE = CREATED_DATE;
	}

	public String getACTIVE_FLAG()
	{
		return ACTIVE_FLAG;
	}

	public void setACTIVE_FLAG(final String ACTIVE_FLAG)
	{
		this.ACTIVE_FLAG = ACTIVE_FLAG;
	}

	public String getNAME()
	{
		return NAME;
	}

	public void setNAME(final String NAME)
	{
		this.NAME = NAME;
	}

	public long getFRM_DT()
	{
		return FRM_DT;
	}

	public void setFRM_DT(final long FRM_DT)
	{
		this.FRM_DT = FRM_DT;
	}

	public long getTO_DT()
	{
		return TO_DT;
	}

	public void setTO_DT(final long TO_DT)
	{
		this.TO_DT = TO_DT;
	}

	public double getSEQUENCE()
	{
		return SEQUENCE;
	}

	public void setSEQUENCE(final double SEQUENCE)
	{
		this.SEQUENCE = SEQUENCE;
	}

	public String getTESTCODE()
	{
		return TESTCODE;
	}

	public void setTESTCODE(final String TESTCODE)
	{
		this.TESTCODE = TESTCODE;
	}

	public String getDISCOUNT_TYPE()
	{
		return DISCOUNT_TYPE;
	}

	public void setDISCOUNT_TYPE(final String DISCOUNT_TYPE)
	{
		this.DISCOUNT_TYPE = DISCOUNT_TYPE;
	}

	public String getPERC_AM()
	{
		return PERC_AM;
	}

	public void setPERC_AM(final String PERC_AM)
	{
		this.PERC_AM = PERC_AM;
	}

	public double getACTION()
	{
		return ACTION;
	}

	public void setACTION(final double ACTION)
	{
		this.ACTION = ACTION;
	}

	public String getBANNER_FLG()
	{
		return BANNER_FLG;
	}

	public void setBANNER_FLG(final String BANNER_FLG)
	{
		this.BANNER_FLG = BANNER_FLG;
	}

	@Override
	public String toString()
	{
		return "CarouselData{" + "ID=" + ID + ", DESCRIPTION='" + DESCRIPTION + '\'' + ", IMAGE_URL='" + IMAGE_URL
				+ '\'' + ", CREATED_DATE=" + CREATED_DATE + ", ACTIVE_FLAG='" + ACTIVE_FLAG + '\'' + ", NAME='" + NAME
				+ '\'' + ", FRM_DT=" + FRM_DT + ", TO_DT=" + TO_DT + ", SEQUENCE=" + SEQUENCE + ", TESTCODE='"
				+ TESTCODE + '\'' + ", DISCOUNT_TYPE='" + DISCOUNT_TYPE + '\'' + ", PERC_AM='" + PERC_AM + '\''
				+ ", ACTION=" + ACTION + ", BANNER_FLG='" + BANNER_FLG + '\'' + '}';
	}
}
