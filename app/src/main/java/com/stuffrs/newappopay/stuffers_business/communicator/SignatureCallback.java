package com.stuffrs.newappopay.stuffers_business.communicator;

import android.graphics.Bitmap;

public interface SignatureCallback {
    public void onSignatureConfirm(Bitmap bitmap,boolean status);
}
