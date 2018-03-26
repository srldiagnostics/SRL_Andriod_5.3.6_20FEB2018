package com.srllimited.srl.data;

/**
 * Created by RuchiTiwari on 1/13/2017.
 */

public class PromoCodesData
{
	private String DEVICE_ID;

	private String COUPON_CODE;

	private long ISSUE_DATE;

	private int SCHEME_ID;

	private String DISCOUNT_FLAG;

	private double DISCOUNT;

	private long VALID_FROM;

	private long VALID_TO;

	private String NOTIFICATION_MSG;

	public String getDEVICE_ID()
	{
		return DEVICE_ID;
	}

	public void setDEVICE_ID(final String DEVICE_ID)
	{
		this.DEVICE_ID = DEVICE_ID;
	}

	public String getCOUPON_CODE()
	{
		return COUPON_CODE;
	}

	public void setCOUPON_CODE(final String COUPON_CODE)
	{
		this.COUPON_CODE = COUPON_CODE;
	}

	public long getISSUE_DATE()
	{
		return ISSUE_DATE;
	}

	public void setISSUE_DATE(final long ISSUE_DATE)
	{
		this.ISSUE_DATE = ISSUE_DATE;
	}

	public int getSCHEME_ID()
	{
		return SCHEME_ID;
	}

	public void setSCHEME_ID(final int SCHEME_ID)
	{
		this.SCHEME_ID = SCHEME_ID;
	}

	public String getDISCOUNT_FLAG()
	{
		return DISCOUNT_FLAG;
	}

	public void setDISCOUNT_FLAG(final String DISCOUNT_FLAG)
	{
		this.DISCOUNT_FLAG = DISCOUNT_FLAG;
	}

	public double getDISCOUNT()
	{
		return DISCOUNT;
	}

	public void setDISCOUNT(final double DISCOUNT)
	{
		this.DISCOUNT = DISCOUNT;
	}

	public long getVALID_FROM()
	{
		return VALID_FROM;
	}

	public void setVALID_FROM(final long VALID_FROM)
	{
		this.VALID_FROM = VALID_FROM;
	}

	public long getVALID_TO()
	{
		return VALID_TO;
	}

	public void setVALID_TO(final long VALID_TO)
	{
		this.VALID_TO = VALID_TO;
	}

	public String getNOTIFICATION_MSG()
	{
		return NOTIFICATION_MSG;
	}

	public void setNOTIFICATION_MSG(final String NOTIFICATION_MSG)
	{
		this.NOTIFICATION_MSG = NOTIFICATION_MSG;
	}

}
