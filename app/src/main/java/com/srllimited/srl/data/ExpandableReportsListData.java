package com.srllimited.srl.data;

import java.util.ArrayList;

/**
 * Created by Codefyne on 29/12/2016.
 */

public class ExpandableReportsListData
{
	private String reportId;

	private ArrayList<ReportsChildListData> _reportsChildtData;

	public String getReportId()
	{
		return reportId;
	}

	public void setReportId(String reportId)
	{
		this.reportId = reportId;
	}

	public ArrayList<ReportsChildListData> get_reportsChildtData()
	{
		return _reportsChildtData;
	}

	public void set_reportsChildtData(ArrayList<ReportsChildListData> _reportsChildtData)
	{
		this._reportsChildtData = _reportsChildtData;
	}
}
