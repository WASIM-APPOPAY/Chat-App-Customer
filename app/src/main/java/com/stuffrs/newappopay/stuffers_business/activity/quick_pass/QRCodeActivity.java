package com.stuffrs.newappopay.stuffers_business.activity.quick_pass;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.stuffers_business.activity.wallet.PayUserActivity;
import com.stuffrs.newappopay.stuffers_business.communicator.ScanRequestListener;
import com.stuffrs.newappopay.stuffers_business.communicator.TransactionPinListener;
import com.stuffrs.newappopay.stuffers_business.communicator.UnionPayCardListener;
import com.stuffrs.newappopay.stuffers_business.utils.AppoConstants;
import com.google.android.material.tabs.TabLayout;
import com.stuffrs.newappopay.stuffers_business.views.MyTextViewBold;

import java.util.ArrayList;
import java.util.List;

public class QRCodeActivity extends AppCompatActivity implements ScanRequestListener, TransactionPinListener, UnionPayCardListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private static final String TAG = "QRCodeActivity";
    private ViewPagerAdapter adapter;
    String selectedColor = "#4B2987";
    String unSelectedColor = "#ff0099cc";
    QrGenerateFragment mQrGenerateFragment;
    QrScanFragment mQrScanFragment;
    MyTextViewBold tvQrGenerate, tvQrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_code);
        setupActionBar();
        tvQrGenerate = (MyTextViewBold) findViewById(R.id.tvQrGenerate);
        tvQrScan = (MyTextViewBold) findViewById(R.id.tvQrScan);
        tvQrGenerate.setTextColor(Color.parseColor(selectedColor));
        tvQrScan.setTextColor(Color.parseColor(unSelectedColor));

        mQrGenerateFragment = new QrGenerateFragment();

        mQrScanFragment = new QrScanFragment();
        if (savedInstanceState == null) {
            initFragments(mQrGenerateFragment);
        }

        tvQrGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvQrGenerate.setTextColor(Color.parseColor(selectedColor));
                tvQrScan.setTextColor(Color.parseColor(unSelectedColor));
                initFragments(mQrGenerateFragment);
            }
        });

        tvQrScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvQrGenerate.setTextColor(Color.parseColor(unSelectedColor));
                tvQrScan.setTextColor(Color.parseColor(selectedColor));
                initFragments(mQrScanFragment);
            }
        });


    }

    private void initFragments(Fragment mFragment) {
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.qrContainer, mFragment);
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }


    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView menu_icon = toolbar.findViewById(R.id.menu_icon);
        menu_icon.setVisibility(View.GONE);
        TextView toolbarTitle = toolbar.findViewById(R.id.toolbarTitle);
        toolbarTitle.setVisibility(View.VISIBLE);
        toolbarTitle.setText("Qr Code");
        ActionBar bar = getSupportActionBar();
        bar.setDisplayUseLogoEnabled(false);
        bar.setDisplayShowTitleEnabled(true);
        bar.setDisplayShowHomeEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);
    }

    @Override
    public void onRequestListener(String param) {
        //Toast.makeText(this, "SUCCESS", Toast.LENGTH_LONG).show();
        //Log.e(TAG, "onRequestListener: " + param);
        Intent payUserActivity = new Intent(this, PayUserActivity.class);
        payUserActivity.putExtra(AppoConstants.MERCHANTSCANCODE, param);
        startActivityForResult(payUserActivity, AppoConstants.QR_SCAN_CODE_REQUEST);

    }

    @Override
    public void onPinConfirm(String pin) {
        Fragment fragment = (Fragment) adapter.instantiateItem(viewPager, 0);
        if (fragment instanceof QrGenerateFragment) {
            ((QrGenerateFragment) fragment).closeBottomDialog(pin);
        }
    }

    @Override
    public void onCardRequest() {
        Fragment fragment = (Fragment) adapter.instantiateItem(viewPager, 0);
        if (fragment instanceof QrGenerateFragment) {
            ((QrGenerateFragment) fragment).redirectCardEnrollment();
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // action bar menu behaviour
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}