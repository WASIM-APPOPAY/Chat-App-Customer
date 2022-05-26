package com.stuffrs.newappopay.activity;

import static com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager.KEY_USER_LANGUAGE;
import static com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager.TANDC;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.Utils.Helper;
import com.stuffrs.newappopay.bottom_fragments.BottomLanguage;
import com.stuffrs.newappopay.stuffers_business.AppoPayApplication;
import com.stuffrs.newappopay.stuffers_business.MyContextWrapper;
import com.stuffrs.newappopay.stuffers_business.communicator.LanguageListener;
import com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager;
import com.stuffrs.newappopay.views.MyTextView;
import com.stuffrs.newappopay.views.MyTextViewBold;
import com.stuffrs.newappopay.views.MyTextViewBoldItalic;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Text;

public class SplashActivity extends AppCompatActivity implements LanguageListener {
    MyTextView tvAgree;
    private MyTextViewBold tvTapInfo, tvLanguage;
    private BottomLanguage mBottomLanguage;
    private CheckBox tvCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /*DataVaultManager.getInstance(SplashActivity.this).saveUserDetails("");
        DataVaultManager.getInstance(SplashActivity.this).saveUserAccessToken("");*/
        Helper helper = new Helper(this);
        tvCheck = (CheckBox) findViewById(R.id.tvCheck);
        tvAgree = (MyTextView) findViewById(R.id.tvAgree);
        tvTapInfo = (MyTextViewBold) findViewById(R.id.tvTapInfo);
        tvLanguage = (MyTextViewBold) findViewById(R.id.tvLanguage);

        String mTandc = DataVaultManager.getInstance(SplashActivity.this).getVaultValue(TANDC);
        if (!StringUtils.isEmpty(mTandc)) {
            tvCheck.setChecked(true);
        }
        tvAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!tvCheck.isChecked()) {
                    Toast.makeText(SplashActivity.this, getString(R.string.info_term_condition), Toast.LENGTH_SHORT).show();
                    return;
                }
                DataVaultManager.getInstance(SplashActivity.this).saveTerm("check");
                startActivity(new Intent(SplashActivity.this, helper.getLoggedInUser() != null ? HomeActivity.class : NumberActivity.class));
                /*Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                startActivity(intent);*/

            }
        });

        String tapInfoText1 = "<font color='#029DDC'>" + getString(R.string.info_split1) + " " + "&quot;" + getString(R.string.info_split2) + "&quot;" + "</font>" + "<font color='#029DDC'>" + " " + getString(R.string.info_split3) + "</font>";
        String tapInfoText2 = "<font color='#FB8310'>" + " " + getString(R.string.info_split4) + "</font>";
        String wholeText = tapInfoText1 + tapInfoText2;

        tvTapInfo.setText(Html.fromHtml(wholeText));
        tvTapInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCheck.setChecked(true);
            }
        });

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

    @Override
    public void onLanguageSelect(String lan) {
        mBottomLanguage.dismiss();
        DataVaultManager.getInstance(SplashActivity.this).saveLanguage(lan);
        Intent intent = new Intent(SplashActivity.this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        //fetch from shared preference also save to the same when applying. default is English
        //String language = MyPreferenceUtil.getInstance().getString(MyConstants.PARAM_LANGUAGE, "en");
        String userLanguage = DataVaultManager.getInstance(AppoPayApplication.getInstance()).getVaultValue(KEY_USER_LANGUAGE);
        if (StringUtils.isEmpty(userLanguage)) {
            ////Log.e(TAG, "attachBaseContext: english called");
            userLanguage = "en";
        }
        super.attachBaseContext(MyContextWrapper.wrap(newBase, userLanguage));
    }
}