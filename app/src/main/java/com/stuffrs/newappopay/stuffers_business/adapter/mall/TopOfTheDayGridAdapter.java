package com.stuffrs.newappopay.stuffers_business.adapter.mall;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.stuffers_business.activity.mall.ProductsActivity;
import com.stuffrs.newappopay.stuffers_business.models.output.GetTopOfTheDayOutputModel;
import com.stuffrs.newappopay.stuffers_business.views.MyTextView;

import java.util.ArrayList;


public class TopOfTheDayGridAdapter extends BaseAdapter {

    ArrayList<GetTopOfTheDayOutputModel.Offer> bean;
    AppCompatActivity main;


    public TopOfTheDayGridAdapter(AppCompatActivity activity, ArrayList<GetTopOfTheDayOutputModel.Offer> bean) {
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
            convertView = layoutInflater.inflate(R.layout.top_of_the_day_list_item, null);


            viewHolder = new ViewHolder();

            viewHolder.txt_top_of_the_day = (MyTextView) convertView.findViewById(R.id.txt_top_of_the_day);


            convertView.setTag(viewHolder);


        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }


        final GetTopOfTheDayOutputModel.Offer bean = (GetTopOfTheDayOutputModel.Offer) getItem(position);

//        viewHolder.image.setImageResource(bean.getImage());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(main, ProductsActivity.class);

                i.putExtra("subcat_id", "null");
                i.putExtra("subcat_name", bean.getChildCatName());
                i.putExtra("child_category_id", bean.getChildCatId());
                main.startActivity(i);


            }
        });

        viewHolder.txt_top_of_the_day.setText(bean.getChildCatName());


        return convertView;


    }

    private class ViewHolder {
        MyTextView txt_top_of_the_day;


    }


}


