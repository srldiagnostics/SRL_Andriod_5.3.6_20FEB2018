package com.srllimited.srl.adapters;

/**
 * Created by Ruchi Tiwari on 02/12/16.
 */

import java.util.ArrayList;
import java.util.List;

import com.srllimited.srl.AddPatientActivity;
import com.srllimited.srl.LoginScreenActivity;
import com.srllimited.srl.R;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.LabLocatorsData;
import com.srllimited.srl.permission.DangerousPermResponseCallBack;
import com.srllimited.srl.permission.DangerousPermissionResponse;
import com.srllimited.srl.permission.DangerousPermissionUtils;
import com.srllimited.srl.util.DateUtil;
import com.srllimited.srl.util.Log;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LabVisitAdapter extends RecyclerView.Adapter<LabVisitAdapter.ViewHolder>
{
	int PHONE_REQUES_CODE = 1;
	private Context mContext;
	private List<LabLocatorsData> mLabLocatorsDatas = new ArrayList<>();

	public LabVisitAdapter(Context context, List<LabLocatorsData> labLocatorsDatas, boolean isCart)
	{
		this.mContext = context;
		this.mLabLocatorsDatas = labLocatorsDatas;

	}

	@Override
	public LabVisitAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lab_details_item, viewGroup, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final LabVisitAdapter.ViewHolder viewHolder, final int i)
	{
		if (viewHolder != null)
		{
			if (mLabLocatorsDatas.get(i) != null)
			{

				viewHolder.labs_layout.setVisibility(View.VISIBLE);
				if (mLabLocatorsDatas.get(i).getTYP().equalsIgnoreCase("0"))
				{
					viewHolder.labIConIVID.setBackgroundResource(R.drawable.lab_icon);
					//					}
					//
					//					if (mLabLocatorsDatas.get(i).getTYP().equalsIgnoreCase("1"))
					//					{
					//						viewHolder.labIConIVID.setBackgroundResource(R.drawable.collection_center_icon);
					//					}

					viewHolder.labname.setText(Html.fromHtml(mLabLocatorsDatas.get(i).getLCTN_NM()));
					if (mLabLocatorsDatas.get(i).getPHN() != null
							&& !mLabLocatorsDatas.get(i).getPHN().equalsIgnoreCase("null"))
					{
						try
						{
							int index = 0;
							if (mLabLocatorsDatas.get(i).getPHN() != null
									&& mLabLocatorsDatas.get(i).getPHN().length() > 10
									&& mLabLocatorsDatas.get(i).getPHN().toString().contains(","))
							{
								index = mLabLocatorsDatas.get(i).getPHN().indexOf(",");
							}
							else
							{
								index = mLabLocatorsDatas.get(i).getPHN().indexOf(" ");
							}
							String ph1 = "";
							String ph2 = "";
							try
							{
								if (index > 0)
								{
									ph1 = mLabLocatorsDatas.get(i).getPHN().substring(0, index);
									ph2 = mLabLocatorsDatas.get(i).getPHN().substring(index + 1,
											mLabLocatorsDatas.get(i).getPHN().length());
								}
								else
								{
									ph1 = mLabLocatorsDatas.get(i).getPHN();
								}
							}
							catch (Exception e)
							{

							}
							if (Validate.notEmpty(ph1))
							{
								viewHolder.phonenumber.setVisibility(View.VISIBLE);
								viewHolder.callIVID.setVisibility(View.VISIBLE);
								viewHolder.phonenumber.setText(ph1);
							}
							else
							{
								viewHolder.phonenumber.setVisibility(View.GONE);
								viewHolder.callIVID.setVisibility(View.GONE);
							}

							if (Validate.notEmpty(ph2))
							{
								viewHolder.phonenumber2.setVisibility(View.VISIBLE);
								viewHolder.call2IVID.setVisibility(View.VISIBLE);
								viewHolder.phonenumber2.setText(ph2);
							}
							else
							{
								viewHolder.phonenumber2.setVisibility(View.GONE);
								viewHolder.call2IVID.setVisibility(View.GONE);
							}

						}
						catch (Exception e)
						{

						}
					}
					else
					{
						viewHolder.phonenumber.setVisibility(View.GONE);
						viewHolder.callIVID.setVisibility(View.GONE);
						viewHolder.phonenumber2.setVisibility(View.GONE);
						viewHolder.call2IVID.setVisibility(View.GONE);
					}

					viewHolder.callIVID.setOnClickListener(new View.OnClickListener()
					{
						@Override
						public void onClick(final View view)
						{

							Util.locatorcallNow(mContext, viewHolder.phonenumber.getText().toString());
						}
					});

					viewHolder.call2IVID.setOnClickListener(new View.OnClickListener()
					{
						@Override
						public void onClick(final View view)
						{

							Util.locatorcallNow(mContext, viewHolder.phonenumber2.getText().toString());
						}
					});

					if (!(mLabLocatorsDatas.get(i).getDISTANCE() + "").equalsIgnoreCase("0.0"))
					{
						viewHolder.distanceIVID.setVisibility(View.VISIBLE);
						viewHolder.km.setText(mLabLocatorsDatas.get(i).getDISTANCE() + " Kms");
					}
					else
					{
						viewHolder.distanceIVID.setVisibility(View.GONE);
					}

					if (mLabLocatorsDatas.get(i).getADDR() != null)
					{
						viewHolder.address_data.setText(Html.fromHtml(mLabLocatorsDatas.get(i).getADDR()));
					}
					if (mLabLocatorsDatas.get(i).getSTATE() != null)
					{
						if (mLabLocatorsDatas.get(i).getSTATE().equalsIgnoreCase("null"))
						{
							viewHolder.state_data.setText("-");
						}
						else
						{
							viewHolder.state_data.setText(mLabLocatorsDatas.get(i).getSTATE());
						}
					}
					if (mLabLocatorsDatas.get(i).getCITY() != null)
					{
						viewHolder.city_data.setText(mLabLocatorsDatas.get(i).getCITY());
					}

					//				if (mLabLocatorsDatas.get(i).getADDR() != null)
					//				{
					//					viewHolder.address_data.setText(mLabLocatorsDatas.get(i).getADDR());
					//				}
				}
				if (mLabLocatorsDatas.get(i).getEMAIL() != null
						&& !mLabLocatorsDatas.get(i).getEMAIL().equalsIgnoreCase("null"))
				{
					viewHolder.relemail.setVisibility(View.VISIBLE);
					viewHolder.emailid.setVisibility(View.VISIBLE);
					viewHolder.emailid.setText(mLabLocatorsDatas.get(i).getEMAIL());
				}
				else
				{
					viewHolder.relemail.setVisibility(View.GONE);
					viewHolder.emailid.setVisibility(View.GONE);
				}

				if (Validate.notEmpty(mLabLocatorsDatas.get(i).getLAB_OPENING_TM())
						&& Validate.notEmpty(mLabLocatorsDatas.get(i).getLAB_CLOSING_TM()))
				{
					viewHolder.timing.setVisibility(View.VISIBLE);
					viewHolder.et_labtiming.setText(mLabLocatorsDatas.get(i).getLAB_OPENING_TM() + " - "
							+ mLabLocatorsDatas.get(i).getLAB_CLOSING_TM());
				}
				else
				{
					viewHolder.timing.setVisibility(View.GONE);
					if (Validate.notEmpty(mLabLocatorsDatas.get(i).getLAB_CLOSING_TM()))
					{
						viewHolder.timing.setVisibility(View.VISIBLE);
						viewHolder.et_labtiming.setText(mLabLocatorsDatas.get(i).getLAB_CLOSING_TM());
					}
					else if (Validate.notEmpty(mLabLocatorsDatas.get(i).getLAB_OPENING_TM()))
					{
						viewHolder.timing.setVisibility(View.VISIBLE);
						viewHolder.et_labtiming.setText(mLabLocatorsDatas.get(i).getLAB_OPENING_TM());
					}
				}

				if (Validate.notEmpty(mLabLocatorsDatas.get(i).getLAB_MSG().trim()))
				{
					viewHolder.et_message.setVisibility(View.VISIBLE);
					viewHolder.et_message.setText(mLabLocatorsDatas.get(i).getLAB_MSG());
				}
				else
					viewHolder.et_message.setVisibility(View.GONE);

				viewHolder.expanded_details.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(final View view)
					{
						viewHolder.expanded_layout.setVisibility(View.GONE);
					}
				});

				viewHolder.showdetails_layout.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(final View view)
					{
						viewHolder.expanded_layout.setVisibility(View.VISIBLE);
					}
				});

				viewHolder.labs_layout.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(final View view)
					{
						SharedPrefsUtil.setStringPreference(mContext, "labcity",
								mLabLocatorsDatas.get(i).getCITY() + "");
						SharedPrefsUtil.setStringPreference(mContext, "labstate",
								mLabLocatorsDatas.get(i).getSTATE() + "");
						SharedPrefsUtil.setStringPreference(mContext, "labaddr",
								mLabLocatorsDatas.get(i).getADDR() + "");
						SharedPrefsUtil.setStringPreference(mContext, "labname",
								mLabLocatorsDatas.get(i).getLCTN_NM() + "");
						SharedPrefsUtil.setStringPreference(mContext, "labphone",
								mLabLocatorsDatas.get(i).getPHN() + "");

						int labid = 0;
						try
						{
							labid = (int) mLabLocatorsDatas.get(i).getLAB_ID();
						}
						catch (Exception e)
						{

						}

						SharedPrefsUtil.setStringPreference(mContext, "labid", labid + "");
						if (Validate.notEmpty(mLabLocatorsDatas.get(i).getLAB_OPENING_TM()))
						{
							long opentime = DateUtil.labtiming(mLabLocatorsDatas.get(i).getLAB_OPENING_TM());
							Log.d("labopen", opentime + "");
							SharedPrefsUtil.setLongPreference(mContext, "labopen", opentime);
							SharedPrefsUtil.setStringPreference(mContext, "open",
									mLabLocatorsDatas.get(i).getLAB_OPENING_TM() + "");
						}
						if (Validate.notEmpty(mLabLocatorsDatas.get(i).getLAB_CLOSING_TM()))
						{
							long closetime = DateUtil.labtiming(mLabLocatorsDatas.get(i).getLAB_CLOSING_TM());
							SharedPrefsUtil.setLongPreference(mContext, "labclose", closetime);
							SharedPrefsUtil.setStringPreference(mContext, "close",
									mLabLocatorsDatas.get(i).getLAB_CLOSING_TM() + "");
						}
						if (Validate.notEmpty(Util.getStoredUsername(mContext)) && !Util.isRem(mContext))
						{

							//					SharedPrefsUtil.setStringPreference(mContext,"labcity",mLabLocatorsDatas.get(i).getCITY()+"");
							//					SharedPrefsUtil.setStringPreference(mContext,"labstate",mLabLocatorsDatas.get(i).getSTATE()+"");
							//					SharedPrefsUtil.setStringPreference(mContext,"labaddr",mLabLocatorsDatas.get(i).getADDR()+"");
							//					SharedPrefsUtil.setStringPreference(mContext,"labname",mLabLocatorsDatas.get(i).getLCTN_NM()+"");
							//					SharedPrefsUtil.setStringPreference(mContext,"labphone",mLabLocatorsDatas.get(i).getPHN()+"");
							Constants.isLabOrCollection = true;
							Util.killAddPatient();
							Intent intent = new Intent(mContext, AddPatientActivity.class);
							mContext.startActivity(intent);
						}
						else
						{
							Util.killLogin();
							SharedPrefsUtil.setIntegerPreference(mContext,
									Constants.sharedPreferenceSelectedLoginActivity, Constants.m_lab);
							Intent intent = new Intent(mContext, LoginScreenActivity.class);
							mContext.startActivity(intent);
						}
					}
				});

				viewHolder.relemail.setOnClickListener(new View.OnClickListener()
				{
					@Override
					public void onClick(final View view)
					{
						try
						{
							Util.shareToGMail(mContext, mLabLocatorsDatas.get(i).getEMAIL() + "");
						}
						catch (Exception e)
						{

						}
					}
				});

			}
			else
			{
				viewHolder.labs_layout.setVisibility(View.GONE);
			}

		}

	}

	@Override
	public int getItemCount()
	{
		if (mLabLocatorsDatas.size() > 0)
		{
			return mLabLocatorsDatas.size();
		}
		else
		{
			return 0;
		}
	}

	private void handleTaskWithUserPermission(int requestCode, final String phn)
	{
		DangerousPermissionUtils.getPermission(mContext,
				new String[] { Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE }, requestCode)
				.enqueue(new DangerousPermResponseCallBack()
				{
					@Override
					public void onComplete(final DangerousPermissionResponse permissionResponse)
					{
						if (permissionResponse.isGranted())
						{
							if (permissionResponse.getRequestCode() == PHONE_REQUES_CODE)
							{
								if (ActivityCompat.checkSelfPermission(mContext,
										Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
										&& ActivityCompat.checkSelfPermission(mContext,
												Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
								{
									return;
								}

								//						               PhoneCallListener phoneListener = new PhoneCallListener(mContext);
								//						               TelephonyManager telephonyManager = (TelephonyManager) mContext
								//								               .getSystemService(Context.TELEPHONY_SERVICE);
								//						               telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

								try
								{

									Intent callIntent = new Intent(Intent.ACTION_CALL);
									callIntent.setData(Uri.parse("tel:" + phn + ""));
									mContext.startActivity(callIntent);
								}
								catch (Exception e)
								{

								}

							}
						}
					}
				});
	}

	public class ViewHolder extends RecyclerView.ViewHolder
	{
		public FrameLayout showdetails_layout;
		public LinearLayout expanded_layout;
		public FrameLayout expanded_details;
		public LinearLayout labs_layout;
		public TextView address_data, state_data, city_data;
		public LinearLayout dynamic_layout1, dynamic_layout, address_tv, state_tv, city_tv, timing;
		ImageView distanceIVID, call2IVID, callIVID;
		RelativeLayout relemail;
		ImageView labIConIVID;
		RelativeLayout contact;
		private TextView labname, phonenumber2, phonenumber, km, address, emailid, et_labtiming, et_message;

		public ViewHolder(View view)
		{
			super(view);
			showdetails_layout = (FrameLayout) view.findViewById(R.id.showdetails_layout);
			expanded_details = (FrameLayout) view.findViewById(R.id.expanded_details);
			expanded_layout = (LinearLayout) view.findViewById(R.id.expanded_layout);
			labname = (TextView) view.findViewById(R.id.labNameTVID);
			address = (TextView) view.findViewById(R.id.address);
			contact = (RelativeLayout) view.findViewById(R.id.contact);
			contact.setVisibility(View.GONE);
			phonenumber = (TextView) view.findViewById(R.id.phoneNoTVID);
			km = (TextView) view.findViewById(R.id.km);
			distanceIVID = (ImageView) view.findViewById(R.id.distanceIVID);
			labs_layout = (LinearLayout) view.findViewById(R.id.labs_layout);
			relemail = (RelativeLayout) view.findViewById(R.id.relemail);
			dynamic_layout1 = (LinearLayout) view.findViewById(R.id.dynamic_layout1);
			dynamic_layout = (LinearLayout) view.findViewById(R.id.dynamic_layout);
			labIConIVID = (ImageView) view.findViewById(R.id.labIConIVID);
			address_tv = (LinearLayout) view.findViewById(R.id.address_tv);
			state_tv = (LinearLayout) view.findViewById(R.id.state_tv);
			city_tv = (LinearLayout) view.findViewById(R.id.city_tv);

			address_data = (TextView) view.findViewById(R.id.address_data);
			city_data = (TextView) view.findViewById(R.id.city_data);
			state_data = (TextView) view.findViewById(R.id.state_data);
			emailid = (TextView) view.findViewById(R.id.emailid);

			dynamic_layout.setVisibility(View.GONE);
			dynamic_layout1.setVisibility(View.VISIBLE);
			call2IVID = (ImageView) view.findViewById(R.id.call2IVID);
			callIVID = (ImageView) view.findViewById(R.id.callIVID);
			phonenumber2 = (TextView) view.findViewById(R.id.phoneNo2TVID);

			timing = (LinearLayout) view.findViewById(R.id.timing);

			et_labtiming = (TextView) view.findViewById(R.id.et_labtiming);
			et_message = (TextView) view.findViewById(R.id.et_labmsg);
		}
	}

}
