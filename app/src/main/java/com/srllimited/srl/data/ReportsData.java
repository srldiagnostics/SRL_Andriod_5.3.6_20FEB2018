package com.srllimited.srl.data;

import java.io.Serializable;

/**
 * Created by RuchiTiwari on 12/28/2016.
 */

public class ReportsData implements Serializable
{
	private String ACC_ID;

	private int PRDCT_ID;

	private String PRDCT_CD;

	private String PRDCT_NAME;

	private int ELMNT_ID;

	private String ELMNT_CD;

	private String ELMNT_NAME;

	private String ELMNT_RSLT_TYP;

	private String ELMNT_MIN_RANGE;

	private String ELMNT_MAX_RANGE;

	private String ELMNT_RSLT_UNIT;

	private String RSLT;

	private String P_CMMNT;

	private Integer RANGE_VAL;

	private String PRNT_RNG_TXT;

	private String RSLT_NORMAL_FLAG;

	private int SR_NO;

	private int PARENT_PRDCT_ID;

	private String PARENT_PRDCT_CD;

	private String PARENT_PRDCT_NAME;

	private long ACC_DT;

	private String PTNT_CD;

	private String GENERIC_NAME;

	private String CPT_CODE;

	private String PDT_EL;

	public String getACC_ID()
	{
		return ACC_ID;
	}

	public void setACC_ID(String ACC_ID)
	{
		this.ACC_ID = ACC_ID;
	}

	public int getPRDCT_ID()
	{
		return PRDCT_ID;
	}

	public void setPRDCT_ID(int PRDCT_ID)
	{
		this.PRDCT_ID = PRDCT_ID;
	}

	public String getPRDCT_CD()
	{
		return PRDCT_CD;
	}

	public void setPRDCT_CD(String PRDCT_CD)
	{
		this.PRDCT_CD = PRDCT_CD;
	}

	public String getPRDCT_NAME()
	{
		return PRDCT_NAME;
	}

	public void setPRDCT_NAME(String PRDCT_NAME)
	{
		this.PRDCT_NAME = PRDCT_NAME;
	}

	public int getELMNT_ID()
	{
		return ELMNT_ID;
	}

	public void setELMNT_ID(int ELMNT_ID)
	{
		this.ELMNT_ID = ELMNT_ID;
	}

	public String getELMNT_CD()
	{
		return ELMNT_CD;
	}

	public void setELMNT_CD(String ELMNT_CD)
	{
		this.ELMNT_CD = ELMNT_CD;
	}

	public String getELMNT_NAME()
	{
		return ELMNT_NAME;
	}

	public void setELMNT_NAME(String ELMNT_NAME)
	{
		this.ELMNT_NAME = ELMNT_NAME;
	}

	public String getELMNT_RSLT_TYP()
	{
		return ELMNT_RSLT_TYP;
	}

	public void setELMNT_RSLT_TYP(String ELMNT_RSLT_TYP)
	{
		this.ELMNT_RSLT_TYP = ELMNT_RSLT_TYP;
	}

	public String getELMNT_MIN_RANGE()
	{
		return ELMNT_MIN_RANGE;
	}

	public void setELMNT_MIN_RANGE(String ELMNT_MIN_RANGE)
	{
		this.ELMNT_MIN_RANGE = ELMNT_MIN_RANGE;
	}

	public String getELMNT_MAX_RANGE()
	{
		return ELMNT_MAX_RANGE;
	}

	public void setELMNT_MAX_RANGE(String ELMNT_MAX_RANGE)
	{
		this.ELMNT_MAX_RANGE = ELMNT_MAX_RANGE;
	}

	public String getELMNT_RSLT_UNIT()
	{
		return ELMNT_RSLT_UNIT;
	}

	public void setELMNT_RSLT_UNIT(String ELMNT_RSLT_UNIT)
	{
		this.ELMNT_RSLT_UNIT = ELMNT_RSLT_UNIT;
	}

	public String getRSLT()
	{
		return RSLT;
	}

	public void setRSLT(String RSLT)
	{
		this.RSLT = RSLT;
	}

	public String getP_CMMNT()
	{
		return P_CMMNT;
	}

	public void setP_CMMNT(String p_CMMNT)
	{
		P_CMMNT = p_CMMNT;
	}

	public Integer getRANGE_VAL()
	{
		return RANGE_VAL;
	}

	public void setRANGE_VAL(Integer RANGE_VAL)
	{
		this.RANGE_VAL = RANGE_VAL;
	}

	public String getPRNT_RNG_TXT()
	{
		return PRNT_RNG_TXT;
	}

	public void setPRNT_RNG_TXT(String PRNT_RNG_TXT)
	{
		this.PRNT_RNG_TXT = PRNT_RNG_TXT;
	}

	public String getRSLT_NORMAL_FLAG()
	{
		return RSLT_NORMAL_FLAG;
	}

	public void setRSLT_NORMAL_FLAG(String RSLT_NORMAL_FLAG)
	{
		this.RSLT_NORMAL_FLAG = RSLT_NORMAL_FLAG;
	}

	public int getSR_NO()
	{
		return SR_NO;
	}

	public void setSR_NO(int SR_NO)
	{
		this.SR_NO = SR_NO;
	}

	public int getPARENT_PRDCT_ID()
	{
		return PARENT_PRDCT_ID;
	}

	public void setPARENT_PRDCT_ID(int PARENT_PRDCT_ID)
	{
		this.PARENT_PRDCT_ID = PARENT_PRDCT_ID;
	}

	public String getPARENT_PRDCT_CD()
	{
		return PARENT_PRDCT_CD;
	}

	public void setPARENT_PRDCT_CD(String PARENT_PRDCT_CD)
	{
		this.PARENT_PRDCT_CD = PARENT_PRDCT_CD;
	}

	public String getPARENT_PRDCT_NAME()
	{
		return PARENT_PRDCT_NAME;
	}

	public void setPARENT_PRDCT_NAME(String PARENT_PRDCT_NAME)
	{
		this.PARENT_PRDCT_NAME = PARENT_PRDCT_NAME;
	}

	public long getACC_DT()
	{
		return ACC_DT;
	}

	public void setACC_DT(long ACC_DT)
	{
		this.ACC_DT = ACC_DT;
	}

	public String getPTNT_CD()
	{
		return PTNT_CD;
	}

	public void setPTNT_CD(String PTNT_CD)
	{
		this.PTNT_CD = PTNT_CD;
	}

	public String getGENERIC_NAME()
	{
		return GENERIC_NAME;
	}

	public void setGENERIC_NAME(String GENERIC_NAME)
	{
		this.GENERIC_NAME = GENERIC_NAME;
	}

	public String getCPT_CODE()
	{
		return CPT_CODE;
	}

	public void setCPT_CODE(String CPT_CODE)
	{
		this.CPT_CODE = CPT_CODE;
	}

	public String getPDT_EL()
	{
		return PDT_EL;
	}

	public void setPDT_EL(String PDT_EL)
	{
		this.PDT_EL = PDT_EL;
	}

	@Override
	public String toString()
	{
		return "ReportsData{" + "ACC_ID='" + ACC_ID + '\'' + ", PRDCT_ID=" + PRDCT_ID + ", PRDCT_CD='" + PRDCT_CD + '\''
				+ ", PRDCT_NAME='" + PRDCT_NAME + '\'' + ", ELMNT_ID=" + ELMNT_ID + ", ELMNT_CD='" + ELMNT_CD + '\''
				+ ", ELMNT_NAME='" + ELMNT_NAME + '\'' + ", ELMNT_RSLT_TYP='" + ELMNT_RSLT_TYP + '\''
				+ ", ELMNT_MIN_RANGE='" + ELMNT_MIN_RANGE + '\'' + ", ELMNT_MAX_RANGE='" + ELMNT_MAX_RANGE + '\''
				+ ", ELMNT_RSLT_UNIT='" + ELMNT_RSLT_UNIT + '\'' + ", RSLT='" + RSLT + '\'' + ", P_CMMNT='" + P_CMMNT
				+ '\'' + ", RANGE_VAL=" + RANGE_VAL + ", PRNT_RNG_TXT='" + PRNT_RNG_TXT + '\'' + ", RSLT_NORMAL_FLAG='"
				+ RSLT_NORMAL_FLAG + '\'' + ", SR_NO=" + SR_NO + ", PARENT_PRDCT_ID=" + PARENT_PRDCT_ID
				+ ", PARENT_PRDCT_CD='" + PARENT_PRDCT_CD + '\'' + ", PARENT_PRDCT_NAME='" + PARENT_PRDCT_NAME + '\''
				+ ", ACC_DT=" + ACC_DT + ", PTNT_CD='" + PTNT_CD + '\'' + ", GENERIC_NAME='" + GENERIC_NAME + '\''
				+ ", CPT_CODE='" + CPT_CODE + '\'' + ", PDT_EL='" + PDT_EL + '\'' + '}';
	}
}
