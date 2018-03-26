package com.srllimited.srl.data;

/**
 * Created by RuchiTiwari on 1/20/2017.
 */

public class TrackOrderData
{
	private String ID;

	private String STATUS;

	private String FLAG;

	private String S_DATE;

	public String getS_DATE()
	{
		return S_DATE;
	}

	public void setS_DATE(final String s_DATE)
	{
		S_DATE = s_DATE;
	}

	public String getID()
	{
		return ID;
	}

	public void setID(final String ID)
	{
		this.ID = ID;
	}

	public String getSTATUS()
	{
		return STATUS;
	}

	public void setSTATUS(final String STATUS)
	{
		this.STATUS = STATUS;
	}

	public String getFLAG()
	{
		return FLAG;
	}

	public void setFLAG(final String FLAG)
	{
		this.FLAG = FLAG;
	}

}
