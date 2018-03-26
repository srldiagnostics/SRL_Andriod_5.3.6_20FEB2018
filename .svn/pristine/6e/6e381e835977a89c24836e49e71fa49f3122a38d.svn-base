package com.srllimited.srl.data;

import java.util.List;

import org.json.JSONObject;

import com.srllimited.srl.constants.Constants;

/**
 * Created by RuchiTiwari on 12/18/2016.
 */

public class BookTestORPackagesData
{
	private int ID;

	private String PRDCT_CODE;

	private String PRDCT_ALIASES;

	private String GROSS_AMT;

	private double PRICE;

	private double DISC;

	private String DISC_TYPE;

	private String OFR_CD;

	private String PRDCT_CONSTNS;

	private String CATEGORY;

	private String PTNT_INSTRCTN;

	private String REP_TAT;

	private String INFO;

	private boolean isCart = false;

	private boolean isShowDetails = false;

	private String cartPrice;

	private String book_pkg;

	private String HOME_COLL_AVAIL;

	private List<ProductHeaderListItemData> mProductHeaderListItemDataList;

	public static BookTestORPackagesData newInit(final JSONObject jsonObject)
	{
		BookTestORPackagesData booktest_or_pkgs = new BookTestORPackagesData();
		try
		{

			booktest_or_pkgs.setID(jsonObject.getInt(Constants.booktest_package_id));
			booktest_or_pkgs.setPRDCT_CODE(jsonObject.getString(Constants.booktest_package_product_code));
			booktest_or_pkgs.setPRDCT_ALIASES(jsonObject.getString(Constants.booktest_package_product_aliases));
			booktest_or_pkgs.setGROSS_AMT(jsonObject.getString(Constants.booktest_package_gross_amt));
			booktest_or_pkgs.setPRICE(jsonObject.getDouble(Constants.booktest_package_price));
			booktest_or_pkgs.setDISC(jsonObject.getDouble(Constants.booktest_package_disc));
			booktest_or_pkgs.setDISC_TYPE(jsonObject.getString(Constants.booktest_package_disc_type));
			booktest_or_pkgs.setPRDCT_CONSTNS(jsonObject.getString(Constants.booktest_package_prdct_constnts));
			booktest_or_pkgs.setCATEGORY(jsonObject.getString(Constants.booktest_package_category));
			booktest_or_pkgs.setPTNT_INSTRCTN(jsonObject.getString(Constants.booktest_package_product_instrctn));
			booktest_or_pkgs.setREP_TAT(jsonObject.getString(Constants.booktest_package_rep_tat));
			booktest_or_pkgs.setINFO(jsonObject.getString(Constants.booktest_package_info));
			booktest_or_pkgs.setCartPrice("0");
			booktest_or_pkgs.setOFR_CD(jsonObject.getString(Constants.booktest_package_ofr_cd));
			booktest_or_pkgs.setHOME_COLL_AVAIL(jsonObject.getString(Constants.booktest_package_home_coll_avail));

			return booktest_or_pkgs;
		}
		catch (Exception e)
		{
			e.printStackTrace();

		}
		return null;
	}

	public static BookTestORPackagesData newInit_cart(final JSONObject jsonObject)
	{
		BookTestORPackagesData booktest_or_pkgs = new BookTestORPackagesData();
		try
		{
			try
			{
				booktest_or_pkgs.setID(jsonObject.getInt(Constants.booktest_package_id));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				booktest_or_pkgs.setPRDCT_CODE(jsonObject.getString(Constants.booktest_package_product_code));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				booktest_or_pkgs.setPRDCT_ALIASES(jsonObject.getString(Constants.booktest_package_product_aliases));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				booktest_or_pkgs.setGROSS_AMT(jsonObject.getString(Constants.booktest_package_gross_amt));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				booktest_or_pkgs.setPRICE(jsonObject.getDouble(Constants.booktest_package_price));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				booktest_or_pkgs.setDISC(jsonObject.getDouble(Constants.booktest_package_disc));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				booktest_or_pkgs.setDISC_TYPE(jsonObject.getString(Constants.booktest_package_disc_type));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			try
			{
				booktest_or_pkgs.setOFR_CD(jsonObject.getString(Constants.booktest_package_ofr_cd));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			booktest_or_pkgs.setCart(true);

			try
			{
				booktest_or_pkgs.setCartPrice(jsonObject.getString(Constants.booktest_package_price));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			try
			{
				booktest_or_pkgs.setHOME_COLL_AVAIL(jsonObject.getString(Constants.booktest_package_home_coll_avail));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			//return booktest_or_pkgs;

		}
		catch (Exception e)
		{
			e.printStackTrace();

		}
		return booktest_or_pkgs;
	}

	public int getID()
	{
		return ID;
	}

	public void setID(final int ID)
	{
		this.ID = ID;
	}

	public String getPRDCT_CODE()
	{
		return PRDCT_CODE;
	}

	public void setPRDCT_CODE(final String PRDCT_CODE)
	{
		this.PRDCT_CODE = PRDCT_CODE;
	}

	public String getPRDCT_ALIASES()
	{
		return PRDCT_ALIASES;
	}

	public void setPRDCT_ALIASES(final String PRDCT_ALIASES)
	{
		this.PRDCT_ALIASES = PRDCT_ALIASES;
	}

	public String getGROSS_AMT()
	{
		return GROSS_AMT;
	}

	public void setGROSS_AMT(final String GROSS_AMT)
	{
		this.GROSS_AMT = GROSS_AMT;
	}

	public double getPRICE()
	{
		return PRICE;
	}

	public void setPRICE(final double PRICE)
	{
		this.PRICE = PRICE;
	}

	public double getDISC()
	{
		return DISC;
	}

	public void setDISC(final double DISC)
	{
		this.DISC = DISC;
	}

	public String getDISC_TYPE()
	{
		return DISC_TYPE;
	}

	public void setDISC_TYPE(final String DISC_TYPE)
	{
		this.DISC_TYPE = DISC_TYPE;
	}

	public String getOFR_CD()
	{
		return OFR_CD;
	}

	public void setOFR_CD(final String OFR_CD)
	{
		this.OFR_CD = OFR_CD;
	}

	public String getPRDCT_CONSTNS()
	{
		return PRDCT_CONSTNS;
	}

	public void setPRDCT_CONSTNS(final String PRDCT_CONSTNS)
	{
		this.PRDCT_CONSTNS = PRDCT_CONSTNS;
	}

	public String getCATEGORY()
	{
		return CATEGORY;
	}

	public void setCATEGORY(final String CATEGORY)
	{
		this.CATEGORY = CATEGORY;
	}

	public String getPTNT_INSTRCTN()
	{
		return PTNT_INSTRCTN;
	}

	public void setPTNT_INSTRCTN(final String PTNT_INSTRCTN)
	{
		this.PTNT_INSTRCTN = PTNT_INSTRCTN;
	}

	public String getREP_TAT()
	{
		return REP_TAT;
	}

	public void setREP_TAT(final String REP_TAT)
	{
		this.REP_TAT = REP_TAT;
	}

	public String getINFO()
	{
		return INFO;
	}

	public void setINFO(final String INFO)
	{
		this.INFO = INFO;
	}

	public boolean isCart()
	{
		return isCart;
	}

	public void setCart(final boolean cart)
	{
		isCart = cart;
	}

	public boolean isShowDetails()
	{
		return isShowDetails;
	}

	public void setShowDetails(final boolean showDetails)
	{
		isShowDetails = showDetails;
	}

	public String getCartPrice()
	{
		return cartPrice;
	}

	public void setCartPrice(final String cartPrice)
	{
		this.cartPrice = cartPrice;
	}

	public String getBook_pkg()
	{
		return book_pkg;
	}

	public void setBook_pkg(final String book_pkg)
	{
		this.book_pkg = book_pkg;
	}

	public String getHOME_COLL_AVAIL()
	{
		return HOME_COLL_AVAIL;
	}

	public void setHOME_COLL_AVAIL(String HOME_COLL_AVAIL)
	{
		this.HOME_COLL_AVAIL = HOME_COLL_AVAIL;
	}

	public List<ProductHeaderListItemData> getProductHeaderListItemDataList()
	{
		return mProductHeaderListItemDataList;
	}

	public void setProductHeaderListItemDataList(final List<ProductHeaderListItemData> productHeaderListItemDataList)
	{
		mProductHeaderListItemDataList = productHeaderListItemDataList;
	}

}
