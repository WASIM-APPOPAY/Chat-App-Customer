package com.stuffrs.newappopay.stuffers_business.adapter.mall;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.stuffers_business.activity.mall.ProductsActivity;
import com.stuffrs.newappopay.stuffers_business.api.Constants;
import com.stuffrs.newappopay.stuffers_business.models.output.GetTopShoppingOffersModel;
import com.stuffrs.newappopay.stuffers_business.views.MyTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class TopShoppingOfferAdapter extends BaseAdapter {

    ArrayList<GetTopShoppingOffersModel.Offer> bean;
    AppCompatActivity main;


    public TopShoppingOfferAdapter(AppCompatActivity activity, ArrayList<GetTopShoppingOffersModel.Offer> bean) {
        this.main = activity;
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return bean.size();
    }

    @Override
    public Object getItem(int position) {
        return bean.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final ViewHolder viewHolder;

        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) main.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.top_shopping_offers_grid_item, null);


            viewHolder = new ViewHolder();

            viewHolder.offer_image = (ImageView) convertView.findViewById(R.id.offer_image);
            viewHolder.offer_title = (MyTextView) convertView.findViewById(R.id.offer_title);


            convertView.setTag(viewHolder);


        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }


        final GetTopShoppingOffersModel.Offer bean = (GetTopShoppingOffersModel.Offer) getItem(position);

//        viewHolder.image.setImageResource(bean.getImage());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //       Toast.makeText(main, "item clicked" + bean.getId(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(main, ProductsActivity.class);

                i.putExtra("subcat_id", "null");
                i.putExtra("subcat_name", bean.getChildCatName());
                i.putExtra("child_category_id", bean.getChildCatId());
                main.startActivity(i);
            }
        });


        Picasso.with(main)
                .load(Constants.UPLOAD_IMAGE_FOLDER + bean.getChildCatImage())
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.offer_image);


        viewHolder.offer_title.setText(bean.getOfferName());


        return convertView;


    }

    private class ViewHolder {
        ImageView offer_image;
        MyTextView offer_title;


    }


}