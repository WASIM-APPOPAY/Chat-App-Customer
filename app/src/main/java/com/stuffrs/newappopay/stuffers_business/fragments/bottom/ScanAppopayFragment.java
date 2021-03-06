package com.stuffrs.newappopay.stuffers_business.fragments.bottom;

import static com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager.KEY_USER_DETIALS;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.stuffrs.newappopay.stuffers_business.AppoPayApplication;
import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.stuffers_business.communicator.InnerScanListener;
import com.stuffrs.newappopay.stuffers_business.communicator.ScanRequestListener;
import com.stuffrs.newappopay.stuffers_business.fragments.dialog.ErrorDialogFragment;
import com.stuffrs.newappopay.stuffers_business.utils.AppoConstants;
import com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager;

import org.apache.commons.lang3.StringUtils;


public class ScanAppopayFragment extends Fragment {
    private static final String TAG = "ScanAppopayFragment";

    private View mView;
    private CodeScanner mCodeScanner;
    private InnerScanListener mListener;
    private int mType=0;

    public ScanAppopayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //mView = inflater.inflate(R.layout.fragment_scan_appopay, container, false);

        mView = inflater.inflate(R.layout.scan_fragment, container, false);
        Bundle arguments = this.getArguments();
        mType= arguments.getInt(AppoConstants.WHERE, 0);


        CodeScannerView scannerView = mView.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(getActivity(), scannerView);


        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "run: called :: " + result.getText());
                        String scanText = result.getText();
                        String[] scanTextArrays = scanText.split("\\|");
                        if (AppoPayApplication.isNetworkAvailable(getContext())) {
                            String vaultValue = DataVaultManager.getInstance(AppoPayApplication.getInstance()).getVaultValue(KEY_USER_DETIALS);
                            if (StringUtils.isEmpty(vaultValue)) {
                                ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString(AppoConstants.INFO, getString(R.string.account_see_error));
                                errorDialogFragment.setArguments(bundle);
                                errorDialogFragment.setCancelable(false);
                                errorDialogFragment.show(getChildFragmentManager(), errorDialogFragment.getTag());
                            } else {
                                try {
                                    /*JSONObject root = new JSONObject(vaultValue);
                                    JSONObject result = root.getJSONObject(AppoConstants.RESULT);
                                    JSONObject customerDetails = result.getJSONObject(AppoConstants.CUSTOMERDETAILS);
                                    if (customerDetails.getString(AppoConstants.COUNTRYID).isEmpty() || customerDetails.getString(AppoConstants.COUNTRYID).equalsIgnoreCase("0")) {
                                        ProfileErrorDialogFragment fragment = new ProfileErrorDialogFragment();
                                        Bundle bundle = new Bundle();
                                        bundle.putString(AppoConstants.INFO, getString(R.string.profile_update_error1));
                                        fragment.setArguments(bundle);
                                        fragment.setCancelable(false);
                                        fragment.show(getChildFragmentManager(), fragment.getTag());
                                    } else {
                                        if (!(scanTextArrays.length > 5)) {
                                            Toast.makeText(getContext(), getString(R.string.error_merchant_details), Toast.LENGTH_SHORT).show();
                                        } else {
                                            mListener.onInnerRequestListener(scanText);
                                        }
                                    }*/
                                    if (!(scanTextArrays.length > 5)) {
                                        Toast.makeText(getContext(), getString(R.string.error_merchant_details), Toast.LENGTH_SHORT).show();
                                    } else {
                                        mListener.onInnerRequestListener(scanText);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            Toast.makeText(getContext(), getString(R.string.no_inteenet_connection), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
//39990571040177995008382|Cerca24|63516303|507|USD|321654876534215|78434

       /* if (AppoPayApplication.isNetworkAvailable(getContext())) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("TAG", "run: ");
                    mListener.onRequestListener("6367820101870174396|Cerca 24|63516303|507| support@cerca24.com|undefined|USD");
                }
            });
        } else {
            Toast.makeText(getContext(), "No internet connection", Toast.LENGTH_SHORT).show();
        }*/


        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });


        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (InnerScanListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("parent must implement ScanRequestListener");
        }
    }
}
