package com.stuffrs.newappopay.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.Utils.Helper;
import com.stuffrs.newappopay.bottom_fragments.BottomLanguage;
import com.stuffrs.newappopay.views.MyTextView;
import com.stuffrs.newappopay.views.MyTextViewBold;
import com.stuffrs.newappopay.views.MyTextViewBoldItalic;

import org.w3c.dom.Text;

public class SplashActivity extends AppCompatActivity {
    MyTextView tvAgree;
    private MyTextViewBold tvTapInfo, tvLanguage;
    private BottomLanguage mBottomLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Helper helper = new Helper(this);
        tvAgree = (MyTextView) findViewById(R.id.tvAgree);
        tvTapInfo = (MyTextViewBold) findViewById(R.id.tvTapInfo);
        tvLanguage = (MyTextViewBold) findViewById(R.id.tvLanguage);

        tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SplashActivity.this, helper.getLoggedInUser() != null ? HomeActivity.class : NumberActivity.class));
                /*Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                startActivity(intent);*/

            }
        });

        String tapInfoText1 = "<font color='#029DDC'>" + getString(R.string.info_split1) + " " + "&quot;" + getString(R.string.info_split2) + "&quot;" + "</font>" + "<font color='#029DDC'>" + " " + getString(R.string.info_split3) + "</font>";
        String tapInfoText2 = "<font color='#FB8310'>" + " " + getString(R.string.info_split4) + "</font>";
        String wholeText = tapInfoText1 + tapInfoText2;

        tvTapInfo.setText(Html.fromHtml(wholeText));


        tvLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLanDialogue();
            }
        });
    }

    private void showLanDialogue() {
        //mBottomNotCard = new BottomNotCard();
        //mBottomNotCard.show(getSupportFragmentManager(), mBottomNotCard.getTag());
        //mBottomNotCard.setCancelable(false);

        mBottomLanguage = new BottomLanguage();
        mBottomLanguage.show(getSupportFragmentManager(), mBottomLanguage.getTag());
        mBottomLanguage.setCancelable(false);

    }
}