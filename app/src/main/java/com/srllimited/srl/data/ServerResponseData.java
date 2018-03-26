package com.srllimited.srl.data;

import org.json.JSONArray;
import org.json.JSONObject;

import com.srllimited.srl.constants.Constants;

/**
 * Created by RuchiTiwari on 17/12/2016.
 */

public class ServerResponseData
{
	private int code;

	private String msg;

	private JSONObject mFullJsonResponse;

	public ServerResponseData(JSONObject fullJsonResponse)
	{
		mFullJsonResponse = fullJsonResponse;
		try
		{
			if (mFullJsonResponse != null)
			{
				if (!mFullJsonResponse.isNull(Constants.response_code))
				{
					setCode(mFullJsonResponse.getInt(Constants.response_code));
				}
				if (!mFullJsonResponse.isNull(Constants.response_msg))
				{
					setMsg(mFullJsonResponse.getString(Constants.response_msg));
				}
			}
		}
		catch (Exception e)
		{

		}
	}

	public int getCode()
	{
		return code;
	}

	private void setCode(final int code)
	{
		this.code = code;
	}

	public String getMsg()
	{
		return msg;
	}

	private void setMsg(final String msg)
	{
		this.msg = msg;
	}

	public JSONObject getFullData()
	{
		return mFullJsonResponse;
	}

	public JSONObject getObjectData()
	{
		try
		{
			Object obj = mFullJsonResponse.get(Constants.JSON_TAG_DATA);
			if (obj != null && obj instanceof JSONObject)
			{
				return (JSONObject) obj;
			}
		}
		catch (Exception e)
		{

		}
		return null;
	}

	public JSONArray getArrayData()
	{
		try
		{
			Object obj = mFullJsonResponse.get(Constants.JSON_TAG_DATA);
			if (obj != null && obj instanceof JSONArray)
			{
				return (JSONArray) obj;
			}
		}
		catch (Exception e)
		{
		}

		return null;
	}
}
