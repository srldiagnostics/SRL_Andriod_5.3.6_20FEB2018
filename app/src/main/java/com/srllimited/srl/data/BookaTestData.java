package com.srllimited.srl.data;

/**
 * Created by codefyneandroid on 13-12-2016.
 */

public class BookaTestData
{

	private String mtestcode;

	private String mtestname;

	private String mdefaultprice;

	private String moriginalprice;

	private String mpercentage;

	private boolean mselected;

	public BookaTestData()
	{

	}

	public String getMtestcode()
	{
		return mtestcode;
	}

	public void setMtestcode(String mtestcode)
	{
		this.mtestcode = mtestcode;
	}

	public boolean isMselected()
	{
		return mselected;
	}

	public void setMselected(boolean mselected)
	{
		this.mselected = mselected;
	}

	public String getMtestname()
	{
		return mtestname;
	}

	public void setMtestname(String mtestname)
	{
		this.mtestname = mtestname;
	}

	public String getMoriginalprice()
	{
		return moriginalprice;
	}

	public void setMoriginalprice(String moriginalprice)
	{
		this.moriginalprice = moriginalprice;
	}

	public String getMdefaultprice()
	{
		return mdefaultprice;
	}

	public void setMdefaultprice(String mdefaultprice)
	{
		this.mdefaultprice = mdefaultprice;
	}

	public String getMpercentage()
	{
		return mpercentage;
	}

	public void setMpercentage(String mpercentage)
	{
		this.mpercentage = mpercentage;
	}

	@Override
	public String toString()
	{
		return "BookaTestData{" + "mtestcode='" + mtestcode + '\'' + ", mtestname='" + mtestname + '\''
				+ ", mdefaultprice='" + mdefaultprice + '\'' + ", moriginalprice='" + moriginalprice + '\''
				+ ", mpercentage='" + mpercentage + '\'' + ", mselected=" + mselected + '}';
	}
}
