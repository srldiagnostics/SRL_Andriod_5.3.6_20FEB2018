package com.srllimited.srl.adapters;

/**
 * Created by Ruchi Tiwari on 02/12/16.
 */

import java.util.ArrayList;
import java.util.List;

import com.srllimited.srl.MyReportsExpandableListView;
import com.srllimited.srl.R;
import com.srllimited.srl.ShowWebView;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.AccessionListData;
import com.srllimited.srl.data.ReportsData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;
import com.srllimited.srl.util.Validation;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AccessionListAdapter extends RecyclerView.Adapter<AccessionListAdapter.ViewHolder>
{
	ImageView myreportPDfIVID, close_popup;
	Dialog alertDialog;
	TextView enter_user_id;
	TextView submit;
	EditText promo;
	String url = "";
	boolean pdfAction = false;
	private Context mContext;
	private List<AccessionListData> mAccessionListDatas = new ArrayList<>();
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			switch (request.getReferralCode())
			{
				case GET_PDF:
				{

					shareAlert();
					//                    //String url = "";
					//                    try {
					//                        url = Html.fromHtml(serverResponseData.getFullData().getString("data")) + "";
					//                        Log.d("url", url + "");
					//
					//                            //Toast.makeText(mContext.getApplicationContext(), "Opening Pdf : " + url, Toast.LENGTH_SHORT).show();
					//                            try {
					//                                if (url != null) {
					//                                    //url = "http://104.211.96.182:1127/GenerateReports.aspx?acc=0002PL000124&lid=2&ctyp=W&session=WEB-APP-QA";
					//                                    Util.killShowWeb();
					//                                    Intent i1 = new Intent(mContext, ShowWebView.class);
					//                                    mContext.startActivity(i1);
					//                                  /*  String tempUrl = convertURL(url);
					//                                    Log.e("url", tempUrl);
					//                                    Intent i = new Intent(Intent.ACTION_VIEW);
					//                                    i.setData(Uri.parse(tempUrl));
					//                                    mContext.startActivity(i);*/
					//                                }
					//                            } catch (Exception e) {
					//
					//                        }
					//                    } catch (Exception e) {
					//
					//                    }

				}
					break;
			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{

		}
	};

	public AccessionListAdapter(Context context, List<AccessionListData> accessionListDatas)
	{
		this.mContext = context;
		this.mAccessionListDatas = accessionListDatas;

		alertDialog = new Dialog(mContext);
		alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//		alertDialog.setCancelable(false);
		alertDialog.setContentView(R.layout.got_promocode);

		submit = (TextView) alertDialog.findViewById(R.id.alert_apply);
		submit.setText("Submit");
		enter_user_id = (TextView) alertDialog.findViewById(R.id.enter_user_id);
		promo = (EditText) alertDialog.findViewById(R.id.et_promo);
		close_popup = (ImageView) alertDialog.findViewById(R.id.close_popup);

		enter_user_id.setText("Email id");

		close_popup.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				if (alertDialog != null)
				{
					promo.setText("");
					alertDialog.dismiss();
				}
			}
		});

		submit.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				String cityid = SharedPrefsUtil.getStringPreference(mContext, "selectedcityid");
				Util.hideSoftKeyboard(mContext, view);
				if (Validate.notEmpty(promo.getText().toString().trim()))
				{
					if (Validation.isEmailAddress(promo, true))
					{

						sendRequest(ApiRequestHelper.getPdfReports(mContext, cityid, Constants.storedAccesstion,
								Util.getStoredUsername(mContext), "9704683480", promo.getText().toString(),
								Constants.devicetobepassed, "1", "ANDROID", "1", "1"));
					}
				}
				else
				{
					Toast.makeText(mContext, "Please enter email id", Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	public static String convertURL(String str)
	{
		String url = null;
		try
		{
			url = new String(str.trim().replace(" ", "%20").replace("&", "%26").replace(",", "%2c").replace("(", "%28")
					.replace(")", "%29").replace("!", "%21").replace("=", "%3D").replace("<", "%3C").replace(">", "%3E")
					.replace("#", "%23").replace("$", "%24").replace("'", "%27").replace("*", "%2A").replace("-", "%2D")
					.replace(".", "%2E").replace("/", "%2F").replace(":", "%3A").replace(";", "%3B").replace("?", "%3F")
					.replace("@", "%40").replace("[", "%5B").replace("\\", "%5C").replace("]", "%5D")
					.replace("_", "%5F").replace("`", "%60").replace("{", "%7B").replace("|", "%7C")
					.replace("}", "%7D"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return url;
	}

	@Override
	public AccessionListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_id_list_item, viewGroup, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final AccessionListAdapter.ViewHolder viewHolder, final int i)
	{

		if (viewHolder != null)
		{
			if (mAccessionListDatas.get(i) != null)
			{

				viewHolder.accession_no.setText(mAccessionListDatas.get(i).getAcc_id());

				try
				{
					//
					String stDate = RestApiCallUtil
							.epochToDate(mAccessionListDatas.get(i).getReportsDatas().get(0).getACC_DT());
					if (!stDate.equalsIgnoreCase("null") && !stDate.isEmpty() && stDate != null)
					{
						String dateToEpochMonthOnly = RestApiCallUtil
								.longtoMonth(mAccessionListDatas.get(i).getReportsDatas().get(0).getACC_DT());
						String dateToEpochYearOnly = RestApiCallUtil
								.longtoYear(mAccessionListDatas.get(i).getReportsDatas().get(0).getACC_DT());
						String dateToEpochDateOnly = RestApiCallUtil
								.longtoDate(mAccessionListDatas.get(i).getReportsDatas().get(0).getACC_DT());

						viewHolder.month.setText(dateToEpochMonthOnly + "");//, date, username;
						viewHolder.date.setText(dateToEpochDateOnly + "");//, date, username;
						viewHolder.accession_date.setText(dateToEpochYearOnly + "");//, date, username;
					}

					viewHolder.username.setText(Util.getStoredUsername(mContext) + "");

					//viewHolder.accession_date.setText(stDate);
				}
				catch (Exception e)
				{

				}
			}

			viewHolder.accession_no.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(final View view)
				{
					Constants.sReportsDatas = new ArrayList<ReportsData>();
					Constants.sReportsDatas = mAccessionListDatas.get(i).getReportsDatas();

					Constants.storedAccesstion = mAccessionListDatas.get(i).getAcc_id();
					Util.killMyReportsExpandable();
					Intent intent = new Intent(mContext, MyReportsExpandableListView.class);
					mContext.startActivity(intent);

				}
			});

			viewHolder.pdfBTNID.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(final View view)
				{
					Util.killShowWeb();
					Constants.storedAccesstion = mAccessionListDatas.get(i).getAcc_id();
					Intent intent = new Intent(mContext, ShowWebView.class);
					mContext.startActivity(intent);
					/*pdfAction = true;
					sendRequest(ApiRequestHelper.getPdfReports("6", Constants.storedAccesstion, Util.getStoredUsername(mContext),
					        "9704683480", "", Constants.devicetobepassed, "1", "ANDROID", "1", "1"));*/

				}
			});

			viewHolder.shareBTNID.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(final View view)
				{
					promo.setText("");
					pdfAction = false;//a > b ? x : y;
					Constants.storedAccesstion = mAccessionListDatas.get(i).getAcc_id() != null
							? mAccessionListDatas.get(i).getAcc_id()
							: "";
					//                    if (Validate.notEmpty(url))
					//                        shareAlert();
					if (alertDialog != null)
						alertDialog.dismiss();
					alertDialog.show();
				}
			});
		}
	}

	@Override
	public int getItemCount()
	{
		if (mAccessionListDatas.size() > 0)
		{
			return mAccessionListDatas.size();
		}
		else
		{

			return 0;
		}

	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(mContext, request, mResponseListener);
	}

	public void shareAlert()
	{
		try
		{
			android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext,
					AlertDialog.THEME_HOLO_LIGHT);
			builder.setMessage("The result report will be sent on provided email shortly.").setCancelable(false)
					.setPositiveButton("Ok", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{

							if (alertDialog != null)
							{
								alertDialog.dismiss();
							}
							dialog.cancel();

						}
					});
			builder.show();
		}
		catch (Exception e)
		{

		}
	}

	public class ViewHolder extends RecyclerView.ViewHolder
	{
		ImageView pdfBTNID, shareBTNID;
		private TextView accession_date, accession_no;
		private TextView month, date, username;

		public ViewHolder(View view)
		{
			super(view);
			accession_no = (TextView) view.findViewById(R.id.accession_no);
			accession_date = (TextView) view.findViewById(R.id.accession_date);

			month = (TextView) view.findViewById(R.id.month);
			date = (TextView) view.findViewById(R.id.date);
			username = (TextView) view.findViewById(R.id.username);
			pdfBTNID = (ImageView) view.findViewById(R.id.pdfBTNID);
			shareBTNID = (ImageView) view.findViewById(R.id.shareBTNID);
		}
	}

}
