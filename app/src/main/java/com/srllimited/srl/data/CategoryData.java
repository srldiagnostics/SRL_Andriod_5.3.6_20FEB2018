package com.srllimited.srl.data;

/**
 * Created by RuchiTiwari on 12/18/2016.
 */

public class CategoryData
{
	private double ID;

	private String NAME;

	private double C_ORDER;

	public double getID()
	{
		return ID;
	}

	public void setID(final double ID)
	{
		this.ID = ID;
	}

	public String getNAME()
	{
		return NAME;
	}

	public void setNAME(final String NAME)
	{
		this.NAME = NAME;
	}

	public double getC_ORDER()
	{
		return C_ORDER;
	}

	public void setC_ORDER(final double c_ORDER)
	{
		C_ORDER = c_ORDER;
	}

	public String toString()
	{
		return "CategoryData{" + "ID=" + ID + ", NAME='" + NAME + '\'' + ", C_ORDER=" + C_ORDER + '}';
	}
}
