package com.stuffrs.newappopay.stuffers_business.adapter.spinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.stuffers_business.models.Country.State;
import com.stuffrs.newappopay.stuffers_business.views.MyTextView;

import java.util.List;

public class StateAdapter extends BaseAdapter {


    private Context mContext;
    private List<State> mStates;

    public StateAdapter(Context context, List<State> states) {
        this.mContext = context;
        this.mStates = states;
    }

    @Override
    public int getCount() {
        return mStates.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.row_state, parent, false);
        MyTextView tvCountryCode = view1.findViewById(R.id.tvState);
        tvCountryCode.setText(mStates.get(position).getStatename());
        return view1;
    }
}
