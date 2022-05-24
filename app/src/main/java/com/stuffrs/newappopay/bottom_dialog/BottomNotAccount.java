package com.stuffrs.newappopay.bottom_dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.interfaces.ProceedRequest;
import com.stuffrs.newappopay.stuffers_business.utils.AppoConstants;
import com.stuffrs.newappopay.views.MyButton;
import com.stuffrs.newappopay.views.MyTextView;

import java.util.Objects;

public class BottomNotAccount extends BottomSheetDialogFragment implements View.OnClickListener {
    private BottomSheetBehavior mBehaviour;
    private MyTextView tvInfo;
    private MyButton btnProceed;
    private ProceedRequest mProceedRequest;
    private int mType=0;


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = this.getArguments();
         mType = arguments.getInt(AppoConstants.WHERE, 0);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog mBottomDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View mView = View.inflate(getActivity(), R.layout.bottom_not_account, null);
        mBottomDialog.setContentView(mView);
        mBehaviour = BottomSheetBehavior.from((View) mView.getParent());
        findIds(mView);
        return mBottomDialog;
    }

    private void findIds(View mView) {
        tvInfo = (MyTextView) mView.findViewById(R.id.tvInfo);
        btnProceed = (MyButton) mView.findViewById(R.id.btnProceed);
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProceedRequest.onProceedRequest(mType);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        View touchOutsideView = Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow())
                .getDecorView()
                .findViewById(com.google.android.material.R.id.touch_outside);
        touchOutsideView.setClickable(false);
        touchOutsideView.setFocusable(false);

        mBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
        mBehaviour.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i) {
                    case BottomSheetBehavior.STATE_DRAGGING:
                        mBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mProceedRequest= (ProceedRequest) context;
    }
}
