package com.stuffrs.newappopay.stuffers_business.fragments.finance_fragment.media;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.stuffrs.newappopay.R;

public class indicatorHolder extends RecyclerView.ViewHolder{

    public ImageView image;
    private CardView card;
    View positionController;

    indicatorHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.imageIndicator);
        card = itemView.findViewById(R.id.indicatorCard);
        positionController = itemView.findViewById(R.id.activeImage);
    }
}
