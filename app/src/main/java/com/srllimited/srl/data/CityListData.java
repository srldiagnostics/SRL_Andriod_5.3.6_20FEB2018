package com.srllimited.srl.data;

import java.io.Serializable;

import com.srllimited.srl.util.Validate;

/**
 * Created by codefyneandroid on 08-12-2016.
 */

public class CityListData implements Serializable
{
	private int CITY_ID;

	private String CITY_NAME;

	private String DISPLAY_NAME;

	private int STATE_ID;

	private String FAVOURITE;

	private String ALIASES;

	public CityListData()
	{
	}

	public String getFAVOURITE()
	{
		return FAVOURITE;
	}

	public void setFAVOURITE(String FAVOURITE)
	{
		this.FAVOURITE = FAVOURITE;
	}

	public boolean isFavourite()
	{
		return Validate.notEmpty(getFAVOURITE()) ? getFAVOURITE().equalsIgnoreCase("Y") : false;
	}

	public String getCITY_NAME()
	{
		return CITY_NAME;
	}

	public void setCITY_NAME(String CITY_NAME)
	{
		this.CITY_NAME = CITY_NAME;
	}

	public String getDISPLAY_NAME()
	{
		return DISPLAY_NAME;
	}

	public void setDISPLAY_NAME(String DISPLAY_NAME)
	{
		this.DISPLAY_NAME = DISPLAY_NAME;
	}

	public int getSTATE_ID()
	{
		return STATE_ID;
	}

	public void setSTATE_ID(int STATE_ID)
	{
		this.STATE_ID = STATE_ID;
	}

	public int getCITY_ID()
	{
		return CITY_ID;
	}

	public void setCITY_ID(int CITY_ID)
	{
		this.CITY_ID = CITY_ID;
	}

	public String getALIASES()
	{
		return ALIASES;
	}

	public void setALIASES(String ALIASES)
	{
		this.ALIASES = ALIASES;
	}

	@Override
	public String toString()
	{
		return "CityListData{" + "CITY_ID=" + CITY_ID + ", CITY_NAME='" + CITY_NAME + '\'' + ", DISPLAY_NAME='"
				+ DISPLAY_NAME + '\'' + ", STATE_ID=" + STATE_ID + ", FAVOURITE='" + FAVOURITE + '\'' + ", ALIASES='"
				+ ALIASES + '\'' + '}';
	}
}
