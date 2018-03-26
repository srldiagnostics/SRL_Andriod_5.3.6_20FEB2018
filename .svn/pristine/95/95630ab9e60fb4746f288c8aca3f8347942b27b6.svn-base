package com.srllimited.srl;

import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.OffersListItemData;
import com.srllimited.srl.serverapis.RestApiCallUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by sri on 12/17/2016.
 */

public class OfferDetails extends MenuBaseActivity
{
	public static Activity offersdetail;

	Context context;

	//TextView name, discount, fromtime;
	TextView offer_discount, disease, methology, preferred, expiredate;

	OffersListItemData mOffersListItemData;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.offer_details_actvity);
		offersdetail = this;
		context = OfferDetails.this;
		header_loc_name.setText("Offers Details");
		header_loc_name.setEnabled(false);

		offer_discount = (TextView) findViewById(R.id.offer_discount);
		disease = (TextView) findViewById(R.id.disease);
		methology = (TextView) findViewById(R.id.methology);
		preferred = (TextView) findViewById(R.id.preferred);
		expiredate = (TextView) findViewById(R.id.expiredate);

		mOffersListItemData = Constants.sOffersListItemData;

		if (mOffersListItemData.getDESCRIPTION() != null
				&& !mOffersListItemData.getDESCRIPTION().equalsIgnoreCase("null")
				&& !mOffersListItemData.getDESCRIPTION().isEmpty())
		{
			preferred.setText(mOffersListItemData.getDESCRIPTION() + "");
			offer_discount.setText(mOffersListItemData.getDESCRIPTION() + "");
			methology.setText(":    " + mOffersListItemData.getNAME() + "");
		}

		if (mOffersListItemData.getNAME() != null)
			disease.setText(":    " + mOffersListItemData.getNAME());

		try
		{
			String todate = RestApiCallUtil.epochToDate(mOffersListItemData.getTO_DT());

			if (!todate.equalsIgnoreCase("null") && todate != null && !todate.isEmpty())
			{
				expiredate.setText("Expires on : " + todate);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();

		}

	}
}
