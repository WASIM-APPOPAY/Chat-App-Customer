package com.stuffrs.newappopay.stuffers_business.adapter.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.stuffers_business.communicator.RecyclerViewRowItemCLickListener;
import com.stuffrs.newappopay.stuffers_business.models.lunex_giftcard.GiftProductList;
import com.stuffrs.newappopay.stuffers_business.views.MyTextView;
import com.stuffrs.newappopay.stuffers_business.views.MyTextViewBold;

import java.util.List;

public class LunexAdapter extends RecyclerView.Adapter<LunexAdapter.LunexHolder> {
    List<GiftProductList.Amount> mList;
    Context mCtx;
    RecyclerViewRowItemCLickListener mRowClickListener;

    public LunexAdapter(List<GiftProductList.Amount> mList, Context mCtx) {
        this.mList = mList;
        this.mCtx = mCtx;
        this.mRowClickListener = (RecyclerViewRowItemCLickListener) mCtx;
    }

    @NonNull
    @Override
    public LunexHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.row_gift_items,parent,false);
        return new LunexHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull LunexHolder holder, int position) {
             holder.bindProcess();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class LunexHolder extends RecyclerView.ViewHolder {
        CardView cardview;
        MyTextViewBold tvDestinationAmt;
        public LunexHolder(@NonNull View itemView) {
            super(itemView);
            cardview = itemView.findViewById(R.id.cardview);
            tvDestinationAmt = itemView.findViewById(R.id.tvDestinationAmt);
            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRowClickListener.onRowItemClick(getAdapterPosition());
                }
            });
        }
        public void bindProcess() {
            String amountDestCurrency = mList.get(getAdapterPosition()).getDestCurr()+" " + mList.get(getAdapterPosition()).getDestAmt();
            tvDestinationAmt.setText(amountDestCurrency);
        }
    }
}
