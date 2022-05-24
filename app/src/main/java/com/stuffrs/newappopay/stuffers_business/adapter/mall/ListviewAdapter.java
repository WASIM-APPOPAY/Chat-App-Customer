package com.stuffrs.newappopay.stuffers_business.adapter.mall;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.stuffers_business.AppoPayApplication;
import com.stuffrs.newappopay.stuffers_business.api.Constants;
import com.stuffrs.newappopay.stuffers_business.models.output.ProductsListModel;
import com.stuffrs.newappopay.stuffers_business.utils.SmallBang;
import com.stuffrs.newappopay.stuffers_business.utils.SmallBangListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ListviewAdapter extends BaseAdapter {


    ArrayList<ProductsListModel.Product> bean;
    Typeface fonts1, fonts2;
    AppCompatActivity main;

    float oldPrice, newPrice, discountPrice;


    public ListviewAdapter(AppCompatActivity activity, ArrayList<ProductsListModel.Product> bean) {

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

        fonts1 = Typeface.createFromAsset(main.getAssets(),
                "fonts/MavenPro-Regular.ttf");

        fonts2 = Typeface.createFromAsset(main.getAssets(),
                "fonts/MavenPro-Regular.ttf");

        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) main.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listview_productlist, null);

            viewHolder = new ViewHolder();

            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);
            viewHolder.cutprice = (TextView) convertView.findViewById(R.id.cutprice);
            viewHolder.discount = (TextView) convertView.findViewById(R.id.discount);
            viewHolder.ratingtext = (TextView) convertView.findViewById(R.id.ratingtext);
            viewHolder.fav1 = (ImageView) convertView.findViewById(R.id.fav1);
            viewHolder.fav2 = (ImageView) convertView.findViewById(R.id.fav2);
            viewHolder.ratingbar = (RatingBar) convertView.findViewById(R.id.ratingbar);


            viewHolder.title.setTypeface(fonts1);
            viewHolder.price.setTypeface(fonts1);
            viewHolder.cutprice.setTypeface(fonts1);
            viewHolder.discount.setTypeface(fonts1);
            viewHolder.ratingtext.setTypeface(fonts1);

//
            convertView.setTag(viewHolder);

            viewHolder.cutprice.setPaintFlags(viewHolder.cutprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }


        ProductsListModel.Product bean = (ProductsListModel.Product) getItem(position);

//        viewHolder.image.setImageResource(bean.getImage());

        Picasso.with(main)

                .load(Constants.PRODUCT_IMAGE_PATH + bean.getProductImage())
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.image);


        if (bean.getDiscount().equalsIgnoreCase("") || bean.getDiscount() == null) {

            oldPrice = Float.valueOf(bean.getPrice());
            newPrice = Float.valueOf(bean.getPrice());


        } else if (Float.valueOf(bean.getDiscount()) > 0) {

            oldPrice = Float.valueOf(bean.getPrice());

            discountPrice = oldPrice * Float.valueOf(bean.getDiscount()) / 100;

            newPrice = oldPrice - discountPrice;


        } else {
            oldPrice = Float.valueOf(bean.getPrice());
            newPrice = Float.valueOf(bean.getPrice());

        }


        viewHolder.title.setText(bean.getProductName());
        viewHolder.price.setText(AppoPayApplication.CURRENCY_SYMBOL + newPrice);
        viewHolder.cutprice.setText(AppoPayApplication.CURRENCY_SYMBOL + oldPrice);
        viewHolder.discount.setText(bean.getDiscount() + "%");
        viewHolder.ratingtext.setText("(" + bean.getNoOfRating() + ")");

        viewHolder.ratingbar.setClickable(false);


        if (bean.getRating() != null) {

            viewHolder.ratingbar.setRating(Float.valueOf(bean.getRating()));

        } else {

            viewHolder.ratingbar.setRating(0);
        }


        LayerDrawable stars = (LayerDrawable) viewHolder.ratingbar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.parseColor("#f8d64e"), PorterDuff.Mode.SRC_ATOP);


        viewHolder.mSmallBang = SmallBang.attach2Window(main);


        viewHolder.fav1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                viewHolder.fav2.setVisibility(View.VISIBLE);
                viewHolder.fav1.setVisibility(View.GONE);
                like(v);

            }

            public void like(View view) {
                viewHolder.fav2.setImageResource(R.drawable.f4);
                viewHolder.mSmallBang.bang(view);
                viewHolder.mSmallBang.setmListener(new SmallBangListener() {
                    @Override
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationEnd() {

                    }


                });
            }

        });


        viewHolder.fav2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                viewHolder.fav2.setVisibility(View.GONE);
                viewHolder.fav1.setVisibility(View.VISIBLE);


            }
        });

        return convertView;
    }

    private class ViewHolder {
        ImageView image;
        TextView title;
        TextView price;
        TextView cutprice;
        TextView discount;
        TextView ratingtext;
        ImageView fav1;
        ImageView fav2;
        SmallBang mSmallBang;
        RatingBar ratingbar;


    }
}



