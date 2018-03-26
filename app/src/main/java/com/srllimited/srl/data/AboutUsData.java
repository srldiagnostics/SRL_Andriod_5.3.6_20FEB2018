package com.srllimited.srl.data;

/**
 * Created by RuchiTiwari on 2/7/2017.
 */

public class AboutUsData
{
	private String SCREEN_ID;

	private String SCREEN_NAME;

	private String CONTENT;

	private String MYSRLVER;

	public String getSCREEN_ID()
	{
		return SCREEN_ID;
	}

	public void setSCREEN_ID(final String SCREEN_ID)
	{
		this.SCREEN_ID = SCREEN_ID;
	}

	public String getSCREEN_NAME()
	{
		return SCREEN_NAME;
	}

	public void setSCREEN_NAME(final String SCREEN_NAME)
	{
		this.SCREEN_NAME = SCREEN_NAME;
	}

	public String getCONTENT()
	{
		return CONTENT;
	}

	public void setCONTENT(final String CONTENT)
	{
		this.CONTENT = CONTENT;
	}

	public String getMYSRLVER()
	{
		return MYSRLVER;
	}

	public void setMYSRLVER(final String MYSRLVER)
	{
		this.MYSRLVER = MYSRLVER;
	}

	@Override
	public String toString()
	{
		return "AboutUsData{" + "SCREEN_ID='" + SCREEN_ID + '\'' + ", SCREEN_NAME='" + SCREEN_NAME + '\''
				+ ", CONTENT='" + CONTENT + '\'' + ", MYSRLVER='" + MYSRLVER + '\'' + '}';
	}
}
