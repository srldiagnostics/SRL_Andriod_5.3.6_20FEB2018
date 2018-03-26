//package com.codefyne.mysrl.adapters;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import android.text.Html;
//
//import com.codefyne.mysrl.OfferDetails;
//import com.codefyne.mysrl.OffersListActivity;
//import com.codefyne.mysrl.R;
//import com.codefyne.mysrl.data.OffersListItemData;
//
//import java.util.ArrayList;
//
//
//public class OffersListAdapter extends RecyclerView.Adapter<OffersListAdapter.ViewHolder> {
//    Context mContext;
//    OnItemClickListener mItemClickListener;
//    private ArrayList<OffersListItemData> listData;
//
//    public OffersListAdapter(Context context, ArrayList<OffersListItemData> listData) {
//        this.mContext = context;
//        this.listData = listData;
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        public final TextView title, details;
//        public final ImageView circle;
//        public RelativeLayout offerItemDetailRLID;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            offerItemDetailRLID = (RelativeLayout) itemView.findViewById(R.id.offerItemDetailRLID);
//            title = (TextView) itemView.findViewById(R.id.offer_title);
//            details = (TextView) itemView.findViewById(R.id.offer_details);
//            circle = (ImageView) itemView.findViewById(R.id.offer_circle);
//            offerItemDetailRLID.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View v) {
//            if (mItemClickListener != null) {
//                mItemClickListener.onItemClick(itemView, getPosition());
//            }
//        }
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(View view, int position);
//    }
//
//    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
//        this.mItemClickListener = mItemClickListener;
//    }
//
//
//    public int getItemCount() {
//        return listData.size();
//    }
//
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer, parent, false);
//        return new ViewHolder(view);
//    }
//
//    public void onBindViewHolder(final ViewHolder holder, final int position) {
//
//
//        holder.title.setText("" + listData.get(position).getDESCRIPTION());
//        if (listData.get(position).getNAME().equals("HCP+")) {
//            holder.details.setText("" + Html.fromHtml(listData.get(position).getNAME()));
//            holder.circle.setColorFilter(Color.parseColor("#86c867"));
//        } else if (listData.get(position).getNAME().equals("Thyroid")) {
//            holder.circle.setColorFilter(Color.parseColor("#F48FB1"));
//        } else {
//            holder.circle.setColorFilter(Color.parseColor("#37a9ed"));
//        }
//        //}
//    }
//}
