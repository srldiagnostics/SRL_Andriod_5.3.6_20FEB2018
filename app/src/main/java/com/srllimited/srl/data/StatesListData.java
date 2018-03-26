package com.srllimited.srl.data;

/**
 * Created by Codefyne on 12-02-2017.
 */

public class StatesListData
{
	private String stateID;

	private String stateName;

	public StatesListData(String stateID, String stateName)
	{
		this.stateID = stateID;
		this.stateName = stateName;
	}

	public String getStateID()
	{
		return stateID;
	}

	public void setStateID(String stateID)
	{
		this.stateID = stateID;
	}

	public String getStateName()
	{
		return stateName;
	}

	public void setStateName(String stateName)
	{
		this.stateName = stateName;
	}
}
