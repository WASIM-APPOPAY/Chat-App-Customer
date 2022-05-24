package com.stuffrs.newappopay.stuffers_business.adapter.mall;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.stuffers_business.models.output.SellerReviewListOutputModel;
import com.stuffrs.newappopay.stuffers_business.views.MyTextView;

import java.util.ArrayList;


/**
 * Created by Sandeep Londhe on 05-02-2019.
 *
 * @Email :  sandeeplondhe54@gmail.com
 * @Author :  https://twitter.com/mesandeeplondhe
 * @Skype :  sandeeplondhe54
 */
public class SellerReviewListAdapter extends RecyclerView.Adapter<SellerReviewListAdapter.MyViewHolder> {

    private ArrayList<SellerReviewListOutputModel.Cat> sellerReviewList;
    Activity activity;

    public SellerReviewListAdapter(ArrayList<SellerReviewListOutputModel.Cat> sellerReviewList, Activity activity) {
        this.sellerReviewList = sellerReviewList;
        this.activity = activity;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyTextView title;
        public MyTextView rateno;
        public MyTextView date;
        public MyTextView username;
        public MyTextView comments;


        public MyViewHolder(View view) {
            super(view);

            title = (MyTextView) view.findViewById(R.id.title);
            rateno = (MyTextView) view.findViewById(R.id.rateno);
            date = (MyTextView) view.findViewById(R.id.date);
            username = (MyTextView) view.findViewById(R.id.username);
            comments = (MyTextView) view.findViewById(R.id.comments);


        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.seller_review_list_item, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final SellerReviewListOutputModel.Cat review = sellerReviewList.get(position);

        holder.title.setText(review.getTitle());
        holder.comments.setText(review.getComments());
        holder.rateno.setText(review.getRating() + "/5");
        holder.date.setText(review.getDateAdded());
        holder.username.setText(review.getCustomerName());

    }

    @Override
    public int getItemCount() {
        return sellerReviewList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}
