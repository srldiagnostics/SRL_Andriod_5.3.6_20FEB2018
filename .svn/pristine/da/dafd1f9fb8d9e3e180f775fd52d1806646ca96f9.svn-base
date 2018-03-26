package com.srllimited.srl.adapters;

import java.util.ArrayList;
import java.util.List;

import com.srllimited.srl.R;
import com.srllimited.srl.data.UserData;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.SharedPrefsUtil;
import com.srllimited.srl.utilities.AppDataBaseHelper;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Codefyne on 03/01/2017.
 */

public class AddPAteintFamilyMembersAdapter extends RecyclerView.Adapter<AddPAteintFamilyMembersAdapter.ViewHolder>
{
	// UserData _userAppData;
	int selected_position = -1;
	String addressDetails;
	private Context mContext;
	//RestApiCallUtil.dateToEpoch(dob)
	//Data Base
	AppDataBaseHelper appDb = new AppDataBaseHelper(mContext);
	private List<UserData> _userAppData = new ArrayList<>();

	public AddPAteintFamilyMembersAdapter(Context context, List<UserData> myfamilydata)
	{
		this.mContext = context;

		this._userAppData = myfamilydata;
	}

	@Override
	public AddPAteintFamilyMembersAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_family_item, viewGroup, false);
		return new AddPAteintFamilyMembersAdapter.ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final AddPAteintFamilyMembersAdapter.ViewHolder viewHolder, final int i)
	{
		if (viewHolder != null)
		{
			appDb = new AppDataBaseHelper(mContext);
			try
			{
				if (_userAppData.get(i).getStatus().equalsIgnoreCase("false"))
				{
					viewHolder.patientdetailslayout.setVisibility(View.VISIBLE);
					if (!_userAppData.get(i).getGender().equalsIgnoreCase("null")
							&& !_userAppData.get(i).getGender().isEmpty())
					{
						if (_userAppData.get(i).getGender().equalsIgnoreCase("M"))
						{
							viewHolder.genderTVID.setText("Male");
						}
						else
						{
							viewHolder.genderTVID.setText("Female");
						}
					}
					try
					{

						//						long dobValue = Long.valueOf(_userAppData.get(i).getDob());
						//						Log.e("Date of Birth", _userAppData.get(i).getDob());
						String dobStr = RestApiCallUtil.epochToDate(_userAppData.get(i).getDob());
						if (!dobStr.equalsIgnoreCase("null") && dobStr != null && !dobStr.isEmpty())
						{
							viewHolder.dateofbirtTVID.setText(dobStr);
						}
					}
					catch (Exception e)
					{

					}
					if (!_userAppData.get(i).getMobile_no().equalsIgnoreCase("null")
							&& !_userAppData.get(i).getMobile_no().isEmpty()
							&& _userAppData.get(i).getMobile_no() != null)
					{
						viewHolder.mobileNoTVID.setText(_userAppData.get(i).getMobile_no());
					}

					if (!_userAppData.get(i).getEmail_id().equalsIgnoreCase("null")
							&& !_userAppData.get(i).getEmail_id().isEmpty()
							&& _userAppData.get(i).getEmail_id() != null)
					{
						viewHolder.emailIdTVID.setText(_userAppData.get(i).getEmail_id());
					}
					if (!_userAppData.get(i).getFirst_name().equalsIgnoreCase("null")
							&& !_userAppData.get(i).getFirst_name().isEmpty()
							&& _userAppData.get(i).getFirst_name() != null)
					{
						viewHolder.usernameTVID.setText(_userAppData.get(i).getFirst_name());
					}
					if (!_userAppData.get(i).getPtnt_cd().equalsIgnoreCase("null")
							&& _userAppData.get(i).getPtnt_cd() != null && !_userAppData.get(i).getPtnt_cd().isEmpty())
					{
						viewHolder.userIDTVID.setText(_userAppData.get(i).getPtnt_cd());
					}
					//_userAppData.get(i).getLast_name() +
					if (_userAppData.get(i).getGender().equalsIgnoreCase("F"))
					{
						//viewHolder.userswitchTVID.setText("Do you Like to Switch to " + "Mrs." + _userAppData.get(i).getFirst_name() + " Account");
						viewHolder.memberimg2IVID.setBackgroundResource(R.color.emptycolor);
						viewHolder.memberimg2IVID.setBackgroundResource(R.drawable.family_member_female);
					}
					else
					{
						//viewHolder.userswitchTVID.setText("Do you Like to Switch to " + "Mr." + _userAppData.get(i).getFirst_name() + " Account");
						viewHolder.memberimg2IVID.setBackgroundResource(R.color.emptycolor);
						viewHolder.memberimg2IVID.setBackgroundResource(R.drawable.family_member_male);
					}

					//Address Details
					if (!_userAppData.get(i).getAddress1().equalsIgnoreCase("null")
							&& _userAppData.get(i).getAddress1() != null
							&& !_userAppData.get(i).getAddress1().isEmpty())
					{
						addressDetails = _userAppData.get(i).getAddress1();
					}
					if (!_userAppData.get(i).getLandmark().equalsIgnoreCase("null")
							&& _userAppData.get(i).getLandmark() != null
							&& !_userAppData.get(i).getLandmark().isEmpty())
					{
						if (!addressDetails.isEmpty() && addressDetails != null)
						{
							addressDetails = addressDetails + " , " + _userAppData.get(i).getLandmark();
						}
						else
						{
							addressDetails = addressDetails + _userAppData.get(i).getLandmark();
						}
					}
					if (!_userAppData.get(i).getCity().equalsIgnoreCase("null") && _userAppData.get(i).getCity() != null
							&& !_userAppData.get(i).getCity().isEmpty())
					//addressDetails = addressDetails + " , " + _userAppData.get(i).getCity();
					{
						if (!addressDetails.isEmpty() && addressDetails != null)
						{
							addressDetails = addressDetails + " , " + _userAppData.get(i).getCity();
						}
						else
						{
							addressDetails = addressDetails + _userAppData.get(i).getCity();
						}
					}
					if (!_userAppData.get(i).getState().equalsIgnoreCase("null")
							&& _userAppData.get(i).getState() != null && !_userAppData.get(i).getState().isEmpty())
					//addressDetails = addressDetails + " , " + _userAppData.get(i).getState();
					{
						if (!addressDetails.isEmpty() && addressDetails != null)
						{
							addressDetails = addressDetails + " , " + _userAppData.get(i).getState();
						}
						else
						{
							addressDetails = addressDetails + _userAppData.get(i).getState();
						}
					}
					if (!_userAppData.get(i).getZip().equalsIgnoreCase("null") && _userAppData.get(i).getZip() != null
							&& !_userAppData.get(i).getZip().isEmpty())
					//addressDetails = addressDetails + " , " + _userAppData.get(i).getZip();
					{
						if (!addressDetails.isEmpty() && addressDetails != null)
						{
							addressDetails = addressDetails + " , " + _userAppData.get(i).getZip();
						}
						else
						{
							addressDetails = addressDetails + _userAppData.get(i).getZip();
						}
					}

					if (addressDetails != null && !addressDetails.isEmpty())
					{
						viewHolder.addressLabel.setVisibility(View.VISIBLE);
						viewHolder.completeAddress.setText(addressDetails + "");
					}
					else
					{
						viewHolder.addressLabel.setVisibility(View.GONE);
						viewHolder.completeAddress.setVisibility(View.GONE);
					}

				}
				else
				{
					viewHolder.patientdetailslayout.setVisibility(View.GONE);
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			viewHolder.userdetailsframe.setVisibility(View.GONE);

			//            viewHolder.bottom_wrapper.setOnClickListener(new View.OnClickListener()
			//                                                         {
			//                                                             @Override
			//                                                             public void onClick(final View view)
			//                                                             {
			//                                                                 if (Constants.isFamilySel)
			//                                                                 {
			//                                                                     SharedPrefsUtil.setStringPreference(mContext, "selectedPerson", _userAppData.get(i).getPtnt_cd() + "");
			//                                                                     viewHolder.bottom_wrapper.setBackgroundColor(Color.LTGRAY);
			//                                                                     SharedPrefsUtil.setStringPreference(mContext, "selectedName", _userAppData.get(i).getFirst_name() + " " + _userAppData.get(i).getLast_name() + "");
			//                                                                     notifyItemChanged(selected_position);
			//                                                                     selected_position = i;
			//                                                                     notifyItemChanged(selected_position);
			//
			////
			//                                                                 }
			//                                                             }
			//                                                         }
			//            );

			if (selected_position == i)
			{

				viewHolder.bottom_wrapper.setBackgroundColor(Color.LTGRAY);
			}
			else
			{
				viewHolder.bottom_wrapper.setBackgroundColor(Color.TRANSPARENT);
			}

			viewHolder.itemView.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					SharedPrefsUtil.setStringPreference(mContext, "selectedPerson",
							_userAppData.get(i).getPtnt_cd() + "");
					SharedPrefsUtil.setStringPreference(mContext, "selectedName",
							_userAppData.get(i).getFirst_name() + " " + _userAppData.get(i).getLast_name() + "");

					// Updating old as well as new positions
					notifyItemChanged(selected_position);
					selected_position = i;
					notifyItemChanged(selected_position);

					// Do your another stuff for your onClick
				}
			});
		}

	}

	@Override
	public int getItemCount()
	{
		if (_userAppData.size() > 0)
		{
			return _userAppData.size();
		}
		else
		{
			return _userAppData.size();
		}

	}

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{
		public FrameLayout userdetailsframe; // Arrow click first time Visible
		public LinearLayout expanded_layout;
		RelativeLayout bottom_wrapper;
		private TextView usernameTVID, userIDTVID; // visible Button
		private TextView genderTVID, dateofbirtTVID, mobileNoTVID, emailIdTVID; //in Visible TextViews
		private TextView userswitchTVID;
		private TextView addressLabel, completeAddress, landmark, city, state, pincode;
		private FrameLayout expanded_frameLayout; // Arrow Close Icon invisible
		private ImageView memberimg2IVID;
		private LinearLayout patientdetailslayout;

		public ViewHolder(View view)
		{
			super(view);
			try
			{
				patientdetailslayout = (LinearLayout) view.findViewById(R.id.patientdetailslayout);
				//Visible Data
				memberimg2IVID = (ImageView) view.findViewById(R.id.memberimg2IVID);
				genderTVID = (TextView) view.findViewById(R.id.genderTVID);
				dateofbirtTVID = (TextView) view.findViewById(R.id.dateofbirtTVID);
				emailIdTVID = (TextView) view.findViewById(R.id.emailIdTVID);
				usernameTVID = (TextView) view.findViewById(R.id.usernameTVID);
				mobileNoTVID = (TextView) view.findViewById(R.id.mobileNoTVID);

				//
				addressLabel = (TextView) view.findViewById(R.id.addressLabel);
				completeAddress = (TextView) view.findViewById(R.id.completeAddress);
				landmark = (TextView) view.findViewById(R.id.landmark);
				city = (TextView) view.findViewById(R.id.city);
				state = (TextView) view.findViewById(R.id.state);
				pincode = (TextView) view.findViewById(R.id.pincode);

				userswitchTVID = (TextView) view.findViewById(R.id.userswitchTVID);

				userIDTVID = (TextView) view.findViewById(R.id.userIDTVID);

				userdetailsframe = (FrameLayout) view.findViewById(R.id.userdetailsframe);

				expanded_layout = (LinearLayout) view.findViewById(R.id.expanded_layout_familyView);

				bottom_wrapper = (RelativeLayout) view.findViewById(R.id.bottom_wrapper);
				expanded_frameLayout = (FrameLayout) view.findViewById(R.id.expanded_frameLayout);

				//showdetails_layout.setOnClickListener(this);
				expanded_frameLayout.setOnClickListener(this);

			}
			catch (Exception e)
			{

			}
		}

		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.expanded_frameLayout:
					expanded_layout.setVisibility(View.GONE);
					expanded_frameLayout.setVisibility(View.GONE);
					break;
			}
		}
	}

}
