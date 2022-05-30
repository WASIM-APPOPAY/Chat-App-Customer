package com.stuffrs.newappopay.activity;

import static android.os.Build.VERSION.SDK_INT;
import static com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager.KEY_USER_LANGUAGE;
import static com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager.TANDC;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.stuffrs.newappopay.BuildConfig;
import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.Utils.Helper;
import com.stuffrs.newappopay.bottom_fragments.BottomLanguage;
import com.stuffrs.newappopay.stuffers_business.AppoPayApplication;
import com.stuffrs.newappopay.stuffers_business.MyContextWrapper;
import com.stuffrs.newappopay.stuffers_business.communicator.LanguageListener;
import com.stuffrs.newappopay.stuffers_business.fragments.bottom.chat.TransferChatActivity;
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
    private Helper helper;
    private LinearLayout rLayout;

    public void showPermission(String permission_desc) {
        Snackbar snackbar = Snackbar.make(rLayout, permission_desc, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.settings, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (SDK_INT >= Build.VERSION_CODES.R) {
                            try {
                                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                                intent.addCategory("android.intent.category.DEFAULT");
                                intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                                startActivityForResult(intent, 2296);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                                startActivityForResult(intent, 2296);

                            }
                        } else {
                            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2298);
                        }
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        View view = snackbar.getView();
        TextView sbTextView = view.findViewById(com.google.android.material.R.id.snackbar_text);
        sbTextView.setMaxLines(3);
        sbTextView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        rLayout = findViewById(R.id.rLayout);
        /*DataVaultManager.getInstance(SplashActivity.this).saveUserDetails("");
        DataVaultManager.getInstance(SplashActivity.this).saveUserAccessToken("");*/
        helper = new Helper(this);
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
                if (checkPermission()) {
                    if (!tvCheck.isChecked()) {
                        Toast.makeText(SplashActivity.this, getString(R.string.info_term_condition), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    DataVaultManager.getInstance(SplashActivity.this).saveTerm("check");
                    startActivity(new Intent(SplashActivity.this, helper.getLoggedInUser() != null ? HomeActivity.class : NumberActivity.class));
                } else {
                    showPermission(getString(R.string.permission_desc_storage));
                }


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
        /*DataVaultManager.getInstance(SplashActivity.this).saveUserDetails("");
        DataVaultManager.getInstance(SplashActivity.this).saveUserAccessToken("");*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2296) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    if (!tvCheck.isChecked()) {
                        Toast.makeText(SplashActivity.this, getString(R.string.info_term_condition), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    DataVaultManager.getInstance(SplashActivity.this).saveTerm("check");
                    startActivity(new Intent(SplashActivity.this, helper.getLoggedInUser() != null ? HomeActivity.class : NumberActivity.class));
                } else {
                    showPermission(getString(R.string.permission_desc_storage));
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2298) {
            if (grantResults.length > 0) {
                boolean p1 = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean p2 = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (p1 && p2) {
                    if (!tvCheck.isChecked()) {
                        Toast.makeText(SplashActivity.this, getString(R.string.info_term_condition), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    DataVaultManager.getInstance(SplashActivity.this).saveTerm("check");
                    startActivity(new Intent(SplashActivity.this, helper.getLoggedInUser() != null ? HomeActivity.class : NumberActivity.class));
                } else {
                    showPermission(getString(R.string.permission_desc_storage));
                }
            }
        }
    }

    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result1 = ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
            int result2 = ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED;
        }
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