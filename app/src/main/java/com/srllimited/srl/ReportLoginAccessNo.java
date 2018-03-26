package com.srllimited.srl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by sri on 12/13/2016.
 */

public class ReportLoginAccessNo extends MenuBaseActivity
{
	public static Activity reportlogin;
	Button viewReportBTNID;
	EditText accessionNoETID, firstNameETID;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		super.addContentView(R.layout.myreport_accession_no_name);
		viewReportBTNID = (Button) findViewById(R.id.viewReportBTNID);
		accessionNoETID = (EditText) findViewById(R.id.accessionNoETID);
		firstNameETID = (EditText) findViewById(R.id.firstNameETID);
		context = ReportLoginAccessNo.this;

		header_loc_name.setText("My Report");
		header_loc_name.setEnabled(false);

		viewReportBTNID.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				String accessNoStr = accessionNoETID.getText().toString();
				String firstnameStr = firstNameETID.getText().toString();

				if (accessNoStr.length() > 0)
				{
					if (firstnameStr.length() > 0)
					{
						Intent i = new Intent(ReportLoginAccessNo.this, MyReportEntryDetails.class);
						startActivity(i);
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Please Enter Accession No or First Name",
								Toast.LENGTH_LONG).show();
					}
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please Enter Accession No or First Name",
							Toast.LENGTH_LONG).show();
				}

			}
		});

	}
}
