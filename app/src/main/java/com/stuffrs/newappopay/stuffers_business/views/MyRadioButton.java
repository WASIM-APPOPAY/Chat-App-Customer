package com.stuffrs.newappopay.stuffers_business.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatRadioButton;

public class MyRadioButton extends AppCompatRadioButton {
    public MyRadioButton(Context context) {
        super(context);
        init();
    }

    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/MavenPro-Regular.ttf");
            setTypeface(tf);
        }
    }

}
