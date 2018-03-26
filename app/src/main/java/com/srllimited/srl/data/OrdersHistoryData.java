package com.srllimited.srl.data;

import com.srllimited.srl.util.Validate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by RuchiTiwari on 12/27/2016.
 */

public class OrdersHistoryData
{

	private static final String KEY_ORDERNO = "ORDERNO";

	private static final String KEY_PATIENT_NAME = "PATIENT_NAME";

	private static final String KEY_CREATED_BY = "CREATED_BY";

	private static final String KEY_GROSS_AMT = "GROSS_AMT";

	private static final String KEY_DISCOUNT_FLAG = "DISCOUNT_FLAG";

	private static final String KEY_DISCOUNT = "DISCOUNT";

	private static final String KEY_PROMOCODE = "PROMOCODE";

	private static final String KEY_PAYMENT_ID = "PAYMENT_ID";

	private static final String KEY_ENTERED_ON = "ENTERED_ON";

	private static final String KEY_BOOKING_FROM = "BOOKING_FROM";

	private static final String KEY_BOOKING_TO = "BOOKING_TO";

	private static final String KEY_PAYMENT_MODE = "PAYMENT_MODE";

	private static final String KEY_PAYMENT_STATUS = "PAYMENT_STATUS";

	private static final String KEY_PAYMENT_SOURCE = "PAYMENT_SOURCE";

	private static final String KEY_ORDER_STATUS = "ORDER_STATUS";

	private static final String KEY_PRODUCTS = "PRODUCTS";

	private static final String KEY_ORDER_NO = "ORDER_NO";

	private static final String KEY_PRDCT_CD = "PRDCT_CD";

	private static final String KEY_PRDCT_NM = "PRDCT_NM";

	private static final String KEY_BASIC_COST = "BASIC_COST";

	private static final String KEY__HOMECOLLECT_LINK = "HOMECOLLECT_LINK";

	private static final String KEY_HOMECOLLECT_START_TIME = "HOMECOLLECT_START_TIME";

	private static final String KEY_HOMECOLLECT_END_TIME = "HOMECOLLECT_END_TIME";

	private String ORDERNO;

	private String PATIENT_NAME;

	private String CREATED_BY;

	private double GROSS_AMT;

	private String DISCOUNT_FLAG;

	private double DISCOUNT;

	private String PROMOCODE;

	private String PAYMENT_ID;

	private long ENTERED_ON;

	private long BOOKING_FROM;

	private long BOOKING_TO;

	private String ORDER_STATUS;

	private String PAYMENT_MODE;

	private String PAYMENT_STATUS;

	private String PAYMENT_SOURCE;

	private String HOMECOLLECT_LINK;

	private long HOMECOLLECT_START_TIME;

	private long HOMECOLLECT_END_TIME;

	private String json;

	private ArrayList<ProductList> productLists;

	public static OrdersHistoryData init(JSONObject jsonObject)
	{
		if (Validate.isNull(jsonObject))
			return null;

		OrdersHistoryData ordersHistoryData = new OrdersHistoryData();

		ordersHistoryData.setORDERNO(jsonObject.optString(KEY_ORDERNO));
		ordersHistoryData.setPATIENT_NAME(jsonObject.optString(KEY_PATIENT_NAME));
		ordersHistoryData.setCREATED_BY(jsonObject.optString(KEY_CREATED_BY));

		ordersHistoryData.setGROSS_AMT(jsonObject.optDouble(KEY_GROSS_AMT));
		ordersHistoryData.setDISCOUNT_FLAG(jsonObject.optString(KEY_DISCOUNT_FLAG));
		ordersHistoryData.setDISCOUNT(jsonObject.optDouble(KEY_DISCOUNT));

		ordersHistoryData.setPROMOCODE(jsonObject.optString(KEY_PROMOCODE));
		ordersHistoryData.setPAYMENT_ID(jsonObject.optString(KEY_PAYMENT_ID));
		ordersHistoryData.setENTERED_ON(jsonObject.optLong(KEY_ENTERED_ON));

		ordersHistoryData.setBOOKING_FROM(jsonObject.optLong(KEY_BOOKING_FROM));
		ordersHistoryData.setBOOKING_TO(jsonObject.optLong(KEY_BOOKING_TO));

		ordersHistoryData.setPAYMENT_MODE(jsonObject.optString(KEY_PAYMENT_MODE));

		ordersHistoryData.setPAYMENT_STATUS(jsonObject.optString(KEY_PAYMENT_STATUS));
		ordersHistoryData.setPAYMENT_SOURCE(jsonObject.optString(KEY_PAYMENT_SOURCE));
		ordersHistoryData.setHOMECOLLECT_LINK(jsonObject.optString(KEY__HOMECOLLECT_LINK));
		ordersHistoryData.setHOMECOLLECT_START_TIME(jsonObject.optLong(KEY_HOMECOLLECT_START_TIME));
		ordersHistoryData.setHOMECOLLECT_END_TIME(jsonObject.optLong(KEY_HOMECOLLECT_END_TIME));
		ordersHistoryData.setORDER_STATUS(jsonObject.optString(KEY_ORDER_STATUS));

		JSONArray jsonArray = jsonObject.optJSONArray(KEY_PRODUCTS);
		if (Validate.notNull(jsonArray))
		{
			ArrayList<ProductList> productList = new ArrayList<>();
			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject prodJsonObject = jsonArray.optJSONObject(i);
				if (Validate.notNull(prodJsonObject))
				{
					ProductList products = new ProductList();
					products.setORDER_NO(prodJsonObject.optString(KEY_ORDER_NO));
					products.setPRDCT_CD(prodJsonObject.optString(KEY_PRDCT_CD));
					products.setPRDCT_NM(prodJsonObject.optString(KEY_PRDCT_NM));
					products.setBASIC_COST(prodJsonObject.optDouble(KEY_BASIC_COST));
					productList.add(products);
				}
			}
			ordersHistoryData.setProductLists(productList);
		}

		JSONObject json = new JSONObject();
		try
		{
			json.put("array", jsonArray);
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		ordersHistoryData.setJson(json + "");

		return ordersHistoryData;
	}

	public String getORDERNO()
	{
		return ORDERNO;
	}

	public void setORDERNO(String ORDERNO)
	{
		this.ORDERNO = ORDERNO;
	}

	public String getPATIENT_NAME()
	{
		return PATIENT_NAME;
	}

	public void setPATIENT_NAME(String PATIENT_NAME)
	{
		this.PATIENT_NAME = PATIENT_NAME;
	}

	public String getCREATED_BY()
	{
		return CREATED_BY;
	}

	public void setCREATED_BY(String CREATED_BY)
	{
		this.CREATED_BY = CREATED_BY;
	}

	public double getGROSS_AMT()
	{
		return GROSS_AMT;
	}

	public void setGROSS_AMT(double GROSS_AMT)
	{
		this.GROSS_AMT = GROSS_AMT;
	}

	public String getDISCOUNT_FLAG()
	{
		return DISCOUNT_FLAG;
	}

	public void setDISCOUNT_FLAG(String DISCOUNT_FLAG)
	{
		this.DISCOUNT_FLAG = DISCOUNT_FLAG;
	}

	public double getDISCOUNT()
	{
		return DISCOUNT;
	}

	public void setDISCOUNT(double DISCOUNT)
	{
		this.DISCOUNT = DISCOUNT;
	}

	public String getPROMOCODE()
	{
		return PROMOCODE;
	}

	public void setPROMOCODE(String PROMOCODE)
	{
		this.PROMOCODE = PROMOCODE;
	}

	public String getPAYMENT_ID()
	{
		return PAYMENT_ID;
	}

	public void setPAYMENT_ID(String PAYMENT_ID)
	{
		this.PAYMENT_ID = PAYMENT_ID;
	}

	public long getENTERED_ON()
	{
		return ENTERED_ON;
	}

	public void setENTERED_ON(long ENTERED_ON)
	{
		this.ENTERED_ON = ENTERED_ON;
	}

	public long getBOOKING_FROM()
	{
		return BOOKING_FROM;
	}

	public void setBOOKING_FROM(long BOOKING_FROM)
	{
		this.BOOKING_FROM = BOOKING_FROM;
	}

	public long getBOOKING_TO()
	{
		return BOOKING_TO;
	}

	public void setBOOKING_TO(long BOOKING_TO)
	{
		this.BOOKING_TO = BOOKING_TO;
	}

	public String getORDER_STATUS()
	{
		return ORDER_STATUS;
	}

	public void setORDER_STATUS(String ORDER_STATUS)
	{
		this.ORDER_STATUS = ORDER_STATUS;
	}

	public String getPAYMENT_MODE()
	{
		return PAYMENT_MODE;
	}

	public void setPAYMENT_MODE(String PAYMENT_MODE)
	{
		this.PAYMENT_MODE = PAYMENT_MODE;
	}

	public String getPAYMENT_STATUS()
	{
		return PAYMENT_STATUS;
	}

	public void setPAYMENT_STATUS(String PAYMENT_STATUS)
	{
		this.PAYMENT_STATUS = PAYMENT_STATUS;
	}

	public String getPAYMENT_SOURCE()
	{
		return PAYMENT_SOURCE;
	}

	public void setPAYMENT_SOURCE(String PAYMENT_SOURCE)
	{
		this.PAYMENT_SOURCE = PAYMENT_SOURCE;
	}

	public String getJson()
	{
		return json;
	}

	public void setJson(String json)
	{
		this.json = json;
	}

	public ArrayList<ProductList> getProductLists()
	{
		return productLists;
	}

	public void setProductLists(ArrayList<ProductList> productLists)
	{
		this.productLists = productLists;
	}

	public String getHOMECOLLECT_LINK()
	{
		return HOMECOLLECT_LINK;
	}

	public void setHOMECOLLECT_LINK(String HOMECOLLECT_LINK)
	{
		this.HOMECOLLECT_LINK = HOMECOLLECT_LINK;
	}

	public long getHOMECOLLECT_START_TIME()
	{
		return HOMECOLLECT_START_TIME;
	}

	public void setHOMECOLLECT_START_TIME(long HOMECOLLECT_START_TIME)
	{
		this.HOMECOLLECT_START_TIME = HOMECOLLECT_START_TIME;
	}

	public long getHOMECOLLECT_END_TIME()
	{
		return HOMECOLLECT_END_TIME;
	}

	public void setHOMECOLLECT_END_TIME(long HOMECOLLECT_END_TIME)
	{
		this.HOMECOLLECT_END_TIME = HOMECOLLECT_END_TIME;
	}

	@Override
	public String toString()
	{
		return "OrdersHistoryData{" + "ORDERNO='" + ORDERNO + '\'' + ", PATIENT_NAME='" + PATIENT_NAME + '\''
				+ ", CREATED_BY='" + CREATED_BY + '\'' + ", GROSS_AMT=" + GROSS_AMT + ", DISCOUNT_FLAG='"
				+ DISCOUNT_FLAG + '\'' + ", DISCOUNT=" + DISCOUNT + ", PROMOCODE='" + PROMOCODE + '\''
				+ ", PAYMENT_ID='" + PAYMENT_ID + '\'' + ", ENTERED_ON=" + ENTERED_ON + ", BOOKING_FROM=" + BOOKING_FROM
				+ ", BOOKING_TO=" + BOOKING_TO + ", ORDER_STATUS='" + ORDER_STATUS + '\'' + ", PAYMENT_MODE='"
				+ PAYMENT_MODE + '\'' + ", PAYMENT_STATUS='" + PAYMENT_STATUS + '\'' + ", PAYMENT_SOURCE='"
				+ PAYMENT_SOURCE + '\'' + ", HOMECOLLECT_LINK='" + HOMECOLLECT_LINK + '\''
				+ ", HOMECOLLECT_START_TIME='" + HOMECOLLECT_START_TIME + '\'' + ", HOMECOLLECT_END_TIME='"
				+ HOMECOLLECT_END_TIME + '\'' + ", json='" + json + '\'' + ", productLists=" + productLists + '}';
	}

	/*@Override
	public String toString()
	{
		return "OrdersHistoryData{" + "ORDERNO='" + ORDERNO + '\'' + ", PATIENT_NAME='" + PATIENT_NAME + '\''
				+ ", CREATED_BY='" + CREATED_BY + '\'' + ", GROSS_AMT=" + GROSS_AMT + ", DISCOUNT_FLAG='"
				+ DISCOUNT_FLAG + '\'' + ", DISCOUNT=" + DISCOUNT + ", PROMOCODE='" + PROMOCODE + '\''
				+ ", PAYMENT_ID='" + PAYMENT_ID + '\'' + ", ENTERED_ON=" + ENTERED_ON + ", BOOKING_FROM=" + BOOKING_FROM
				+ ", BOOKING_TO=" + BOOKING_TO + ", ORDER_STATUS='" + ORDER_STATUS + '\'' + ", PAYMENT_MODE='"
				+ PAYMENT_MODE + '\'' + ", PAYMENT_STATUS='" + PAYMENT_STATUS + '\'' + ", PAYMENT_SOURCE='"
				+ PAYMENT_SOURCE + '\'' + ", HOMECOLLECT_LINK='" + HOMECOLLECT_LINK + '\'' + ", json='" + json + '\''
				+ ", productLists=" + productLists + '}';
	}*/
}
