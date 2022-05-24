package com.stuffrs.newappopay.stuffers_business.fragments.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.stuffers_business.communicator.ProfileUpdateRequest;
import com.stuffrs.newappopay.stuffers_business.utils.AppoConstants;
import com.stuffrs.newappopay.stuffers_business.views.MyButton;
import com.stuffrs.newappopay.stuffers_business.views.MyTextView;

public class ProfileErrorDialogFragment extends DialogFragment {
    ProfileUpdateRequest mProfileRequest;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_profile_error_dialog, container, false);
        MyTextView tvInfo = view.findViewById(R.id.tvInfo);
        MyButton btnYes = view.findViewById(R.id.btnYes);
        MyButton btnClose = view.findViewById(R.id.btnClose);
        Bundle arguments = this.getArguments();
        tvInfo.setText(arguments.getString(AppoConstants.INFO));
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProfileRequest.onProfileUpdate();
                dismiss();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mProfileRequest = (ProfileUpdateRequest) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("parent must implement ProfileUpdateRequest");
        }
    }
}
