package com.stuffrs.newappopay.stuffers_business.communicator;

import android.net.Uri;

public interface CameraListener {
    void onCameraCapturePerform(String picturePath, Uri uri);
}
