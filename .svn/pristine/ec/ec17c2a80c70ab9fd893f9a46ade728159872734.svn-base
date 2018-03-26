//package com.codefyne.mysrl;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//import android.widget.Toast;
//
///*import com.loopj.android.http.AsyncHttpClient;
//import com.loopj.android.http.AsyncHttpResponseHandler;
//import com.loopj.android.http.RequestParams;*/
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//
//import com.codefyne.mysrl.data.ServerResponseData;
//import com.codefyne.mysrl.serverapis.ApiRequest;
//import com.codefyne.mysrl.serverapis.ApiRequestHelper;
//import com.codefyne.mysrl.serverapis.ApiRequestManager;
//import com.codefyne.mysrl.serverapis.ApiResponseListener;
//import com.codefyne.mysrl.serverapis.RestApiCallUtil;
//import com.codefyne.mysrl.util.SharedPrefsUtil;
//import com.codefyne.mysrl.util.Util;
//import com.codefyne.mysrl.util.Validate;
//import com.codefyne.mysrl.adapters.OffersListAdapter;
//import com.codefyne.mysrl.constants.Constants;
//import com.codefyne.mysrl.data.OffersListItemData;
//import com.codefyne.mysrl.utilities.Utility;
//
//
///**
// * Created by sri on 12/17/2016.
// */
//
//
//public class OffersListActivity extends MenuBaseActivity
//{
//	private RecyclerView mRecyclerView;
//	TextView nooffers;
//	private OffersListAdapter mAdapter;
//	Activity activity;
//	Context context;
//	public static Activity offerslist;
//	ArrayList<OffersListItemData> mOffersListItemData = new ArrayList<>();
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		super.addContentView(R.layout.activity_offers_list);
//		offerslist = this;
//		context = OffersListActivity.this;
//		String cityid = SharedPrefsUtil.getStringPreference(context, "selectedcityid");
//		header_loc_name.setText("Offers");
//		header_loc_name.setEnabled(false);
//
//		nooffers = (TextView) findViewById(R.id.nooffers);
//
//		if (RestApiCallUtil.isOnline(context))
//		{
//			sendRequest(ApiRequestHelper.getOfferList(cityid,Util.getStoredUsername(context), ""));
//		}
//		else
//		{
//			RestApiCallUtil.NetworkError(context);
//		}
//	}
//
//	private void sendRequest(ApiRequest request)
//	{
//		ApiRequestManager.getInstance().sendRequest(this, request, mResponseListener);
//	}
//
//	private ApiResponseListener<ServerResponseData> mResponseListener = new ApiResponseListener<ServerResponseData>()
//	{
//		@Override
//		public void onResponse(final ApiRequest request, final ServerResponseData serverResponseData)
//		{
//			switch (request.getReferralCode())
//			{
//				case OFFER_LIST:
//				{
//
//					setOffersList(serverResponseData.getFullData());
//				}
//				break;
//			}
//		}
//
//		@Override
//		public void onResponseError(final ApiRequest request, final Exception error)
//		{
//
//		}
//	};
//
//	private void setOffersList(JSONObject jsonObject)
//	{
//		JSONArray jArray = null;
//		try
//		{
//			if (!jsonObject.isNull("data"))
//			{
//				Object obj = jsonObject.get("data");
//				if (obj != null && obj instanceof JSONArray)
//				{
//					jArray = jsonObject.getJSONArray(Constants.response_data_create);
//				}
//			}
//		}
//		catch (Exception e)
//		{
//		}
//		if (jArray != null)
//		{
//			try
//			{
//				if (Validate.notNull(jArray))
//				{
//					mOffersListItemData = new ArrayList<>();
//					for (int i = 0; i < jArray.length(); i++)
//					{
//						OffersListItemData offerlist_data = new OffersListItemData();
//						offerlist_data.setID(jArray.getJSONObject(i).getInt(Constants.offer_id));
//						offerlist_data.setDESCRIPTION(jArray.getJSONObject(i).getString(Constants.offer_description));
//						offerlist_data.setIMAGE_URL(jArray.getJSONObject(i).getString(Constants.offer_image_url));
//						offerlist_data.setCREATED_DATE(jArray.getJSONObject(i).getString(Constants.offer_created_date));
//						offerlist_data.setACTIVE_FLAG(jArray.getJSONObject(i).getString(Constants.offer_active_date));
//						offerlist_data.setNAME(jArray.getJSONObject(i).getString(Constants.offer_name));
//						offerlist_data.setFRM_DT(jArray.getJSONObject(i).getString(Constants.offer_from_date));
//						offerlist_data.setTO_DT(jArray.getJSONObject(i).getLong(Constants.offer_to_date));
//						offerlist_data.setSEQUENCE(jArray.getJSONObject(i).getString(Constants.offer_sequence));
//						offerlist_data.setTESTCODE(jArray.getJSONObject(i).getString(Constants.offer_test_code));
//						offerlist_data.setDISCOUNT_TYPE(jArray.getJSONObject(i).getString(Constants.offer_discount_type));
//						offerlist_data.setPERC_AMT(jArray.getJSONObject(i).getString(Constants.offer_perc_amt));
//						offerlist_data.setACTION(jArray.getJSONObject(i).getString(Constants.offer_action));
//						offerlist_data.setBANNER_FLG(jArray.getJSONObject(i).getString(Constants.offer_banner_flag));
//						if (jArray.getJSONObject(i).getString(Constants.offer_banner_flag).equalsIgnoreCase("N"))
//						{
//							mOffersListItemData.add(offerlist_data);
//						}
//
//					}
//				}
//			}
//			catch (JSONException e)
//			{
//				e.printStackTrace();
//			}
//
//			if (mOffersListItemData != null)
//			{
//
//				setOfferListAdapter();
//			}
//		}
//
//	}
//
//	private void setOfferListAdapter()
//	{
//		mRecyclerView = (RecyclerView) findViewById(R.id.offersList);
//		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//		mRecyclerView.setHasFixedSize(true);
//
//
//		if (mOffersListItemData.size() != 0)
//		{
//			mAdapter = new OffersListAdapter(context, mOffersListItemData);
//			mRecyclerView.setAdapter(mAdapter);
//			mAdapter.setOnItemClickListener(onItemClickListener);
//			nooffers.setVisibility(View.GONE);
//		}
//		else
//		{
//			nooffers.setVisibility(View.VISIBLE);
//		}
//	}
//
//
//	OffersListAdapter.OnItemClickListener onItemClickListener = new OffersListAdapter.OnItemClickListener()
//	{
//		@Override
//		public void onItemClick(View v, int position)
//		{
//			if (mOffersListItemData != null)
//			{
//				Constants.sOffersListItemData = mOffersListItemData.get(position);
//			}
//			Util.killOfferDetails();
//			Intent transitionIntent = new Intent(OffersListActivity.this, OfferDetails.class);
//			//transitionIntent.putExtra("OFFER_TITLE", "" + _offerlist.get(position).getOfferTitle());
//			startActivity(transitionIntent);
//		}
//	};
//
//	//Local Data
//	/*ArrayList<OffersListItemData> offers = new ArrayList<OffersListItemData>() {{
//	        add(new Offer("Exclusive offer!!! Book A Test through MySRL app", "avail 10 % discount", Color.parseColor("#37a9ed")));
//            add(new Offer("Avail 15% discount on the HCP+ package", "from Aug 1st, 2016 to OCt 31st,2016", Color.parseColor("#86c867")));
//            add(new Offer("Exclusive offer!!! Book A Test through MySRL app", "from Aug 1st, 2016 to OCt 31st,2016", Color.parseColor("#F48FB1")));
//        }};*/
//
//        /*_offerlist = new ArrayList<OffersListItemData>();
//        _offerlist.add(new OffersListItemData("Exclusive offer!!! Book A Test through MySRL app", "avail 10 % discount", Color.parseColor("#37a9ed")));
//        _offerlist.add(new OffersListItemData("Avail 15% discount on the HCP+ package", "from Aug 1st, 2016 to OCt 31st,2016", Color.parseColor("#86c867")));
//        _offerlist.add(new OffersListItemData("Avail 15% discount on the HCP+ package", "from Aug 1st, 2016 to OCt 31st,2016", Color.parseColor("#86c867")));*/
//
//
//
//    /*private static class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {
//        Context ctx;
//        private static ClickListener clickListener;
//
//        RecyclerItemClickListener.OnItemClickListener mItemClickListener;
//
//
//        ArrayList<Offer> offers = new ArrayList<Offer>() {{
//            add(new Offer("Exclusive offer!!! Book A Test through MySRL app", "avail 10 % discount", Color.parseColor("#37a9ed")));
//            add(new Offer("Avail 15% discount on the HCP+ package", "from Aug 1st, 2016 to OCt 31st,2016", Color.parseColor("#86c867")));
//            add(new Offer("Exclusive offer!!! Book A Test through MySRL app", "from Aug 1st, 2016 to OCt 31st,2016", Color.parseColor("#F48FB1")));
//        }};
//
//        public OffersAdapter(Context ctx) {
//            this.ctx = ctx;
//        }
//
//        @Override
//        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer, parent, false);
//
//            return new ViewHolder(view);
//
//        }
//
//        @Override
//        public void onBindViewHolder(ViewHolder holder, int position) {
//            Offer offer = offers.get(position);
//
//            holder.title.setText(offer.getOfferTitle());
//            holder.details.setText(offer.getOfferDetails());
//            holder.circle.setColorFilter(offer.getCircleColor());
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return offers.size();
//        }
//
//
//        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//            public final TextView title, details;
//            public final ImageView circle;
//            public RelativeLayout offerItemDetailRLID;
//
//            public ViewHolder(View itemView) {
//                super(itemView);
//                offerItemDetailRLID = (RelativeLayout) itemView.findViewById(R.id.offerItemDetailRLID);
//                title = (TextView) itemView.findViewById(R.id.offer_title);
//                details = (TextView) itemView.findViewById(R.id.offer_details);
//                circle = (ImageView) itemView.findViewById(R.id.offer_circle);
//                offerItemDetailRLID.setOnClickListener(this);
//            }
//
//
//            @Override
//            public void onClick(View v) {
//                clickListener.onItemClick(getAdapterPosition(), v);
//                if (mItemClickListener != null) {
//                    mItemClickListener.onItemClick(itemView, getPosition());
//                }
//            }
//        }
//
//        public interface OnItemClickListener {
//            void onItemClick(View view, int position);
//        }
//
//        public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
//            this.mItemClickListener = mItemClickListener;
//        }
//    }*/
//
//}
//
