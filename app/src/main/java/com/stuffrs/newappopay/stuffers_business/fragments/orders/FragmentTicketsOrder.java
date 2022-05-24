package com.stuffrs.newappopay.stuffers_business.fragments.orders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.stuffrs.newappopay.R;


public class FragmentTicketsOrder extends Fragment {

    View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.recharge_orders_fragment, container, false);

        return mView;
    }
}
