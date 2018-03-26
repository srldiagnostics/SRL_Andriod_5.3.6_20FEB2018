package com.srllimited.srl.adapters;

import java.util.ArrayList;
import java.util.List;

import com.daimajia.swipe.SwipeLayout;
import com.srllimited.srl.NotificationsActivity;
import com.srllimited.srl.R;
import com.srllimited.srl.constants.Constants;
import com.srllimited.srl.data.NotificationsData;
import com.srllimited.srl.data.ServerResponseData;
import com.srllimited.srl.database.NotificationDatabase;
import com.srllimited.srl.interfaces.AlertListner;
import com.srllimited.srl.serverapis.ApiRequest;
import com.srllimited.srl.serverapis.ApiRequestHelper;
import com.srllimited.srl.serverapis.ApiRequestManager;
import com.srllimited.srl.serverapis.ApiResponseListener;
import com.srllimited.srl.serverapis.RestApiCallUtil;
import com.srllimited.srl.util.DateUtil;
import com.srllimited.srl.util.Util;
import com.srllimited.srl.util.Validate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Codefyne on 03/01/2017.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder>
{
	NotificationDatabase notificationsDB;
	ViewHolder vholder;
	int pos;
	private Context mContext;
	private NotificationsActivity activity;
	private List<NotificationsData> notificationsDatas = new ArrayList<>();
	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
	{
		@Override
		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
		{
			switch (request.getReferralCode())
			{
				case DELETE_NOTIFICATION:
				{

					try
					{

						if (serverResponseData.getObjectData() != null
								&& serverResponseData.getObjectData().getBoolean("RESPONSE"))
						{
							notificationsDB.deleteNotification(notificationsDatas.get(pos).getQUEUE_ID());
							Toast.makeText(mContext, "Notification has been deleted successfully.. ", Toast.LENGTH_LONG)
									.show();
							notificationsDatas.remove(pos);
							if (vholder != null)
								vholder.swipeLayout.close();
							activity.updateAdapter();
						}
						else
						{
							Toast.makeText(mContext, "Failed to delete the notification.. ", Toast.LENGTH_LONG).show();
						}

					}
					catch (Exception e)
					{

					}

				}
					break;
			}
		}

		@Override
		public void onResponseError(final ApiRequest request, final Exception error)
		{

		}
	};

	public NotificationsAdapter(NotificationsActivity context, List<NotificationsData> notifications)
	{
		this.mContext = context;
		this.activity = context;
		this.notificationsDatas = notifications;
		notificationsDB = new NotificationDatabase(context);
	}

	@Override
	public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
	{
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_list_item, viewGroup,
				false);
		return new NotificationsAdapter.ViewHolder(view);
	}

	@Override
	public void onBindViewHolder(final NotificationsAdapter.ViewHolder viewHolder, final int i)
	{
		if (viewHolder != null)
		{

			if (notificationsDatas != null && notificationsDatas.size() > 0)
			{

				if (Validate.notEmpty(notificationsDatas.get(i).getMSG_TITLE()))
				{
					viewHolder.msg_title.setText(notificationsDatas.get(i).getMSG_TITLE().replace("&amp;", "& "));
				}

				if (Validate.notEmpty(notificationsDatas.get(i).getMSG_MAIN()))
				{

					viewHolder.msg_main.setText(notificationsDatas.get(i).getMSG_MAIN().replace("&amp;", "& "));
				}

				if (Validate.notNull(notificationsDatas.get(i).getDT_TIME()))
				{
					try
					{
						String datetime = DateUtil.notificationepochToDate(notificationsDatas.get(i).getDT_TIME());
						viewHolder.date_time.setText(datetime);
					}
					catch (Exception e)
					{

					}
				}
			}
			viewHolder.btnEdit.setOnClickListener(onEditListener(i, viewHolder));
			viewHolder.btnDelete.setOnClickListener(onDeleteListener(i, viewHolder));
		}
	}

	@Override
	public int getItemCount()
	{
		if (notificationsDatas.size() > 0)
		{
			return notificationsDatas.size();
		}
		else
		{
			return notificationsDatas.size();
		}

	}

	private View.OnClickListener onEditListener(final int position, final ViewHolder holder)
	{
		return new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				showEditDialog(position, holder);
			}
		};
	}

	private View.OnClickListener onDeleteListener(final int position, final ViewHolder holder)
	{
		return new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				vholder = holder;
				pos = position;
				if (RestApiCallUtil.isOnline(mContext))

					Util.confirmAlert(mContext, "", "Do you want to delete Notification ?", new AlertListner()
					{
						@Override
						public void onOK()
						{
							try
							{
								sendRequest(ApiRequestHelper.deleteNotifications(mContext,
										notificationsDatas.get(position).getQUEUE_ID() + "",
										Util.getStoredUsername(mContext), Constants.devicetobepassed + "", "ANDROID"));

							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
						}

						@Override
						public void onCancel()
						{

						}
					});

				else
					RestApiCallUtil.NetworkError(mContext);

			}
		};
	}

	private void showEditDialog(final int position, final ViewHolder holder)
	{
		holder.swipeLayout.close();
		//mContext.updateAdapter();
	}

	private void sendRequest(ApiRequest request)
	{
		ApiRequestManager.getInstance().sendRequest(mContext, request, mResponseListener);
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{
		public FrameLayout userdetailsframe; // Arrow click first time Visible
		public LinearLayout expanded_layout;
		RelativeLayout bottom_wrapper;
		private TextView msg_title, msg_main, date_time; // visible Button
		private TextView genderTVID, dateofbirtTVID, mobileNoTVID, emailIdTVID; //in Visible TextViews
		private TextView userswitchTVID;
		private TextView addressLabel, completeAddress, landmark, city, state, pincode;
		private FrameLayout expanded_frameLayout; // Arrow Close Icon invisible
		private ImageView memberimg2IVID;
		private LinearLayout patientdetailslayout;
		private View btnDelete;
		private View btnEdit;
		private SwipeLayout swipeLayout;

		public ViewHolder(View view)
		{
			super(view);
			try
			{

				msg_title = (TextView) view.findViewById(R.id.msg_title);
				msg_main = (TextView) view.findViewById(R.id.msg_main);
				date_time = (TextView) view.findViewById(R.id.date_time);

				swipeLayout = (SwipeLayout) view.findViewById(R.id.swipe_layout);
				btnDelete = view.findViewById(R.id.delete);
				btnEdit = view.findViewById(R.id.edit_query);
			}
			catch (Exception e)
			{

			}
			swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);

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
