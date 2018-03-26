package com.srllimited.srl.adapters;

/**
 * Created by Ruchi Tiwari on 02/12/16.
 */

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.srllimited.srl.R;
import com.srllimited.srl.data.LabLocatorsData;
import com.srllimited.srl.permission.DangerousPermResponseCallBack;
import com.srllimited.srl.permission.DangerousPermissionResponse;
import com.srllimited.srl.permission.DangerousPermissionUtils;
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

public class LabLocatorAdapter extends RecyclerView.Adapter<LabLocatorAdapter.ViewHolder>
{
	public static LabLocatorAdapter.NotifyActivity notifyActivity;
	int PHONE_REQUES_CODE = 1;
	private Context mContext;
	private List<LabLocatorsData> mLabLocatorsDatas = new ArrayList<>();

	public LabLocatorAdapter(Context context, List<LabLocatorsData> labLocatorsDatas, boolean isCart)
	{
		this.mContext = context;
		this.mLabLocatorsDatas = labLocatorsDatas;

	}

	@Override
	public LabLocatorAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lab_details_item, viewGroup, false);
		return new ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final LabLocatorAdapter.ViewHolder viewHolder, final int i)
	{
		if (viewHolder != null)
		{
			if (mLabLocatorsDatas.get(i) != null)
			{

				viewHolder.labname.setText(Html.fromHtml(mLabLocatorsDatas.get(i).getLCTN_NM()));

				if (mLabLocatorsDatas.get(i) != null)
				{
					if (mLabLocatorsDatas.get(i).getTYP().equalsIgnoreCase("0"))
					{
						viewHolder.labIConIVID.setBackgroundResource(R.drawable.lab_icon);
					}

					if (mLabLocatorsDatas.get(i).getTYP().equalsIgnoreCase("1"))
					{
						viewHolder.labIConIVID.setBackgroundResource(R.drawable.collection_center_icon);
					}
				}

				if (mLabLocatorsDatas.get(i).getCNTCT_PRSN() != null
						&& !mLabLocatorsDatas.get(i).getCNTCT_PRSN().equalsIgnoreCase("null"))
				{
					viewHolder.contact.setVisibility(View.VISIBLE);
					viewHolder.contact_person.setText(mLabLocatorsDatas.get(i).getCNTCT_PRSN());
				}
				else
				{
					viewHolder.contact.setVisibility(View.GONE);
				}

				if (mLabLocatorsDatas.get(i).getPHN() != null
						&& !mLabLocatorsDatas.get(i).getPHN().equalsIgnoreCase("null"))
				{
					try
					{

						int index = 0;
						if (mLabLocatorsDatas.get(i).getPHN() != null && mLabLocatorsDatas.get(i).getPHN().length() > 10
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

				if (mLabLocatorsDatas.get(i).getDISTANCE() != 0.0)
				{
					viewHolder.distanceIVID.setVisibility(View.VISIBLE);
					viewHolder.km.setVisibility(View.VISIBLE);

					viewHolder.km.setText(mLabLocatorsDatas.get(i).getDISTANCE() + " Kms");
				}
				else
				{
					viewHolder.km.setVisibility(View.GONE);
					viewHolder.distanceIVID.setVisibility(View.GONE);
				}

				if (mLabLocatorsDatas.get(i).getADDR() != null)
				{
					viewHolder.address.setText(Html.fromHtml(mLabLocatorsDatas.get(i).getADDR()));
				}
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
			viewHolder.expanded_details.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					viewHolder.expanded_layout.setVisibility(View.GONE);
				}
			});

			viewHolder.showdetails_layout.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View view)
				{
					viewHolder.expanded_layout.setVisibility(View.VISIBLE);
				}
			});
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

		viewHolder.addrId.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(final View view)
			{
				notifyActivity = (LabLocatorAdapter.NotifyActivity) mContext;
				if (mLabLocatorsDatas.get(i).getLATITUDE() != 0 && mLabLocatorsDatas.get(i).getLONGITUDE() != 0)
				{
					LatLng latLng = new LatLng(mLabLocatorsDatas.get(i).getLATITUDE(),
							mLabLocatorsDatas.get(i).getLONGITUDE());
					if (mLabLocatorsDatas.get(i).getCITY() != null
							&& !mLabLocatorsDatas.get(i).getCITY().equalsIgnoreCase("null"))
						notifyActivity.onChangingListItem(latLng, mLabLocatorsDatas.get(i).getCITY());
				}

			}
		});

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

	public interface NotifyActivity
	{
		void onChangingListItem(LatLng latLng, String city);
	}

	public class ViewHolder extends RecyclerView.ViewHolder
	{
		public FrameLayout showdetails_layout;
		public LinearLayout expanded_layout, timing;
		public FrameLayout expanded_details;
		RelativeLayout relemail, contact;
		ImageView labIConIVID, addrId;
		private TextView labname, phonenumber, km, address, emailid, phonenumber2, contact_person, et_labtiming,
				et_message;
		private ImageView distanceIVID, call2IVID, callIVID;

		public ViewHolder(View view)
		{
			super(view);
			showdetails_layout = (FrameLayout) view.findViewById(R.id.showdetails_layout);
			expanded_details = (FrameLayout) view.findViewById(R.id.expanded_details);
			expanded_layout = (LinearLayout) view.findViewById(R.id.expanded_layout);
			labname = (TextView) view.findViewById(R.id.labNameTVID);
			address = (TextView) view.findViewById(R.id.address);
			contact_person = (TextView) view.findViewById(R.id.contact_person);
			phonenumber = (TextView) view.findViewById(R.id.phoneNoTVID);
			emailid = (TextView) view.findViewById(R.id.emailid);
			relemail = (RelativeLayout) view.findViewById(R.id.relemail);
			contact = (RelativeLayout) view.findViewById(R.id.contact);
			km = (TextView) view.findViewById(R.id.km);
			distanceIVID = (ImageView) view.findViewById(R.id.distanceIVID);
			call2IVID = (ImageView) view.findViewById(R.id.call2IVID);
			callIVID = (ImageView) view.findViewById(R.id.callIVID);
			phonenumber2 = (TextView) view.findViewById(R.id.phoneNo2TVID);
			labIConIVID = (ImageView) view.findViewById(R.id.labIConIVID);
			addrId = (ImageView) view.findViewById(R.id.addrId);
			timing = (LinearLayout) view.findViewById(R.id.timing);

			et_labtiming = (TextView) view.findViewById(R.id.et_labtiming);
			et_message = (TextView) view.findViewById(R.id.et_labmsg);

		}
	}

}
