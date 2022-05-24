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
import android.widget.Toast;

import com.stuffrs.newappopay.stuffers_business.AppoPayApplication;
import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.stuffers_business.activity.wallet.RequestMoney;
import com.stuffrs.newappopay.stuffers_business.communicator.StartActivityListener;
import com.stuffrs.newappopay.stuffers_business.fragments.dialog.ErrorDialogFragment;
import com.stuffrs.newappopay.stuffers_business.utils.AppoConstants;
import com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager;

import org.apache.commons.lang3.StringUtils;

import static com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager.KEY_USER_DETIALS;

public class PaymentFragment extends Fragment {

    LinearLayout llPayments, llPayNow, llAddMoney, llTransferMoney, llRequestMoney, llaCashout;
    LinearLayout llGas;
    private StartActivityListener mListener;

    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_payment, container, false);
        llTransferMoney = mView.findViewById(R.id.llTransferMoney);
        llRequestMoney = mView.findViewById(R.id.llRequestMoney);
        llaCashout = mView.findViewById(R.id.llaCashout);
        llGas = mView.findViewById(R.id.llGas);

        llTransferMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sentToTheirParentActivity(0);
            }
        });

        llRequestMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppoPayApplication.isNetworkAvailable(getContext())) {
                    Intent intentRequest = new Intent(getActivity(), RequestMoney.class);
                    startActivity(intentRequest);
                } else {
                    Toast.makeText(getContext(), getString(R.string.no_inteenet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        llaCashout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppoPayApplication.isNetworkAvailable(getContext())) {
                    sentToTheirParentActivity(1);
                } else {
                    Toast.makeText(getContext(), getString(R.string.no_inteenet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });
        llGas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppoPayApplication.isNetworkAvailable(getContext())) {
                    sentToTheirParentActivity(2);
                } else {
                    Toast.makeText(getContext(), getString(R.string.no_inteenet_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });


        return mView;
    }

    public void sentToTheirParentActivity(int type) {
        String vaultValue = DataVaultManager.getInstance(AppoPayApplication.getInstance()).getVaultValue(KEY_USER_DETIALS);
        if (StringUtils.isEmpty(vaultValue)) {
            ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putString(AppoConstants.INFO, getString(R.string.account_see_error));
            errorDialogFragment.setArguments(bundle);
            errorDialogFragment.setCancelable(false);
            errorDialogFragment.show(getChildFragmentManager(), errorDialogFragment.getTag());
        } else {

            switch (type) {
                case 0:
                    mListener.OnStartActivityRequest(AppoConstants.WALLET_REQUEST_CODE);
                    break;
                case 1:
                    mListener.OnStartActivityRequest(AppoConstants.WALLET_CASH_OUT);
                    break;
                case 2:
                    mListener.OnStartActivityRequest(AppoConstants.GAS_PAY_REQUEST);
                    break;
            }


        }


    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (StartActivityListener) context;

    }
}