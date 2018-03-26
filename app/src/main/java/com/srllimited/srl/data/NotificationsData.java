package com.srllimited.srl.data;

import org.json.JSONObject;

import com.srllimited.srl.util.Validate;

/**
 * Created by Ruchi Tiwari on 21-04-2017.
 */

public class NotificationsData
{

	private static final String KEY_QUEUE_ID = "QUEUE_ID";

	private static final String KEY_PTNT_CD = "PTNT_CD";

	private static final String KEY_DT_TIME = "DT_TIME";

	private static final String KEY_MSG_TYP = "MSG_TYP";

	private static final String KEY_DISPLAY_FLAG = "DISPLAY_FLAG";

	private static final String KEY_MSG_TITLE = "MSG_TITLE";

	private static final String KEY_MSG_MAIN = "MSG_MAIN";

	private static final String KEY_NOTIFICATIONS = "NOTIFICATIONS";

	int QUEUE_ID;

	String PTNT_CD;

	long DT_TIME;

	int MSG_TYP;

	String DISPLAY_FLAG;

	String MSG_TITLE;

	String MSG_MAIN;

	public static NotificationsData init(JSONObject jsonObject)
	{
		if (Validate.isNull(jsonObject))
			return null;

		NotificationsData notificationsData = new NotificationsData();

		notificationsData.setQUEUE_ID(jsonObject.optInt(KEY_QUEUE_ID));
		notificationsData.setPTNT_CD(jsonObject.optString(KEY_PTNT_CD));
		notificationsData.setDT_TIME(jsonObject.optLong(KEY_DT_TIME));

		notificationsData.setMSG_TYP(jsonObject.optInt(KEY_MSG_TYP));
		notificationsData.setDISPLAY_FLAG(jsonObject.optString(KEY_DISPLAY_FLAG));
		notificationsData.setMSG_TITLE(jsonObject.optString(KEY_MSG_TITLE));
		notificationsData.setMSG_MAIN(jsonObject.optString(KEY_MSG_MAIN));

		return notificationsData;
	}

	public int getQUEUE_ID()
	{
		return QUEUE_ID;
	}

	public void setQUEUE_ID(int QUEUE_ID)
	{
		this.QUEUE_ID = QUEUE_ID;
	}

	public String getPTNT_CD()
	{
		return PTNT_CD;
	}

	public void setPTNT_CD(String PTNT_CD)
	{
		this.PTNT_CD = PTNT_CD;
	}

	public long getDT_TIME()
	{
		return DT_TIME;
	}

	public void setDT_TIME(long DT_TIME)
	{
		this.DT_TIME = DT_TIME;
	}

	public int getMSG_TYP()
	{
		return MSG_TYP;
	}

	public void setMSG_TYP(int MSG_TYP)
	{
		this.MSG_TYP = MSG_TYP;
	}

	public String getDISPLAY_FLAG()
	{
		return DISPLAY_FLAG;
	}

	public void setDISPLAY_FLAG(String DISPLAY_FLAG)
	{
		this.DISPLAY_FLAG = DISPLAY_FLAG;
	}

	public String getMSG_TITLE()
	{
		return MSG_TITLE;
	}

	public void setMSG_TITLE(String MSG_TITLE)
	{
		this.MSG_TITLE = MSG_TITLE;
	}

	public String getMSG_MAIN()
	{
		return MSG_MAIN;
	}

	public void setMSG_MAIN(String MSG_MAIN)
	{
		this.MSG_MAIN = MSG_MAIN;
	}

	@Override
	public String toString()
	{
		return "NotificationsData{" + "QUEUE_ID=" + QUEUE_ID + ", PTNT_CD='" + PTNT_CD + '\'' + ", DT_TIME=" + DT_TIME
				+ ", MSG_TYP=" + MSG_TYP + ", DISPLAY_FLAG='" + DISPLAY_FLAG + '\'' + ", MSG_TITLE='" + MSG_TITLE + '\''
				+ ", MSG_MAIN='" + MSG_MAIN + '\'' + '}';
	}
}
