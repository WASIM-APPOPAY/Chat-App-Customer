package com.stuffrs.newappopay.stuffers_business.fragments.homeitem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.stuffers_business.activity.wallet.HotelSearchActivity;
import com.stuffrs.newappopay.stuffers_business.communicator.StartActivityListener;


public class TravelFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private StartActivityListener mListener;
    private LinearLayout llHotels,llFlightTicket,llBus,llTrainTicket;

    public TravelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView= inflater.inflate(R.layout.fragment_travel, container, false);
        llHotels = (LinearLayout)mView.findViewById(R.id.llHotels);
        llFlightTicket =(LinearLayout) mView.findViewById(R.id.llFlightTicket);
        llBus = (LinearLayout) mView.findViewById(R.id.llBus);
        llTrainTicket =(LinearLayout) mView.findViewById(R.id.llTrainTicket);
        llHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), HotelSearchActivity.class);
                startActivity(intent);

            }
        });



        return mView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (StartActivityListener) context;

    }
}