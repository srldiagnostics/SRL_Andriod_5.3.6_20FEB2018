package com.srllimited.srl.data;

import java.util.List;

/**
 * Created by RuchiTiwari on 12/29/2016.
 */

public class ReportParentList
{
	private String parentid;

	private List<ReportsData> mReportsDataList;

	public String getParentid()
	{
		return parentid;
	}

	public void setParentid(final String parentid)
	{
		this.parentid = parentid;
	}

	public List<ReportsData> getReportsDataList()
	{
		return mReportsDataList;
	}

	public void setReportsDataList(final List<ReportsData> reportsDataList)
	{
		mReportsDataList = reportsDataList;
	}

	@Override
	public String toString()
	{
		return "ReportParentList{" + "parentid='" + parentid + '\'' + ", mReportsDataList=" + mReportsDataList + '}';
	}
}
