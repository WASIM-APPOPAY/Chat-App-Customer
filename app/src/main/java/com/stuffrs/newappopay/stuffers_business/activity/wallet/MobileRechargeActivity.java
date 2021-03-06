package com.stuffrs.newappopay.stuffers_business.activity.wallet;

import static com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager.KEY_ACCESSTOKEN;
import static com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager.KEY_USER_DETIALS;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.stuffers_business.AppoPayApplication;
import com.stuffrs.newappopay.stuffers_business.activity.contact.ContactDemoActivity;
import com.stuffrs.newappopay.stuffers_business.adapter.recyclerview.ReceiverAdapter;
import com.stuffrs.newappopay.stuffers_business.api.ApiUtils;
import com.stuffrs.newappopay.stuffers_business.api.MainAPIInterface;
import com.stuffrs.newappopay.stuffers_business.communicator.CarrierSelectListener;
import com.stuffrs.newappopay.stuffers_business.communicator.CustomCountryListener;
import com.stuffrs.newappopay.stuffers_business.communicator.ReceiverListener;
import com.stuffrs.newappopay.stuffers_business.communicator.TransactionPinListener;
import com.stuffrs.newappopay.stuffers_business.fragments.bottom_fragment.BottotmPinFragment;
import com.stuffrs.newappopay.stuffers_business.fragments.dialog.CarrierDialogFragment;
import com.stuffrs.newappopay.stuffers_business.fragments.dialog.CustomCountryDialogFragment;
import com.stuffrs.newappopay.stuffers_business.models.Product.Amount;
import com.stuffrs.newappopay.stuffers_business.models.Product.Product;
import com.stuffrs.newappopay.stuffers_business.models.Product.ProductResponse;
import com.stuffrs.newappopay.stuffers_business.models.output.AccountModel;
import com.stuffrs.newappopay.stuffers_business.models.output.CurrencyResponse;
import com.stuffrs.newappopay.stuffers_business.models.output.CurrencyResult;
import com.stuffrs.newappopay.stuffers_business.models.output.CustomArea;
import com.stuffrs.newappopay.stuffers_business.utils.AppoConstants;
import com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager;
import com.stuffrs.newappopay.stuffers_business.utils.Helper;
import com.stuffrs.newappopay.stuffers_business.views.MyButton;
import com.stuffrs.newappopay.stuffers_business.views.MyEditText;
import com.stuffrs.newappopay.stuffers_business.views.MyTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MobileRechargeActivity extends AppCompatActivity implements CustomCountryListener, CarrierSelectListener, ReceiverListener, TransactionPinListener {


    Switch rechargeSwitch;
    MyTextView tvCarrier, payButton, btnSeePlans;
    MyEditText edtAmount, edtphone_number;


    String strPhone, strOperatorCode, strAmount, strPlan, recharge_type = "1";


    MainAPIInterface mainAPIInterface;
    MainAPIInterface mainAPIInterfaceNode;
    private MyTextView tvAreaCode;
    ArrayList<CustomArea> mListArea;
    private String mAraaCode;
    private ProgressDialog dialog;
    private List<Product> mListProduct;
    private static final String TAG = "MobileRechargeActivity";

    private ArrayList<String> mListCarrier;
    private CarrierDialogFragment carrierDialogFragment;
    private int mCarrierPosition = -1;
    private AlertDialog dialogPayment;

    private String senderAccountnumber, senderAccountstatus, senderCurrencyid, senderCurrentbalance, senderId, senderReserveamount, senderCurrencyCode;
    private String userTransactionPin, currencyId, mDesCurrency, carrier_text = "Select Carrier";

    private JSONArray resultConversion;
    private JSONObject jsonCommission;
    private float bankfees, newamount, processingfees, finalamount, finalAmount1 = 0, chargesAmount = 0;
    //private String AMOUNT_REGEX = "[0-9.]";
    private String AMOUNT_REGEX = "/^([0-9]+(\\.[0-9])?)/";
    private ImageView ivContactList;
    private RecyclerView rvAmountsRecharge;
    private float mRechargeAmount;

    private int mRechargePosition;
    private PhoneNumberUtil phoneUtil;
    ArrayList<AccountModel> mListAccount;
    List<CurrencyResult> result;
    private String mResultReceiver;
    private String receiverEmail;
    //private ArrayList<ChatUser> mList;
    private MainAPIInterface mainAPIInterface2;
    private float newAmount;
    private BottotmPinFragment fragmentBottomSheet;
    private String mDominicaCode = "";
    private int mType = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recharge_activity);
        if (getIntent().getExtras() != null) {
            mType = getIntent().getIntExtra(AppoConstants.WHERE, 0);
        }
        setupActionBar();
        setListOfAreas();


        mainAPIInterface = ApiUtils.getAPIService();
        mainAPIInterfaceNode = ApiUtils.getAPIServiceNode();
        rvAmountsRecharge = findViewById(R.id.rvAmountsRecharge);
        tvAreaCode = (MyTextView) findViewById(R.id.tvAreaCode);
        payButton = (MyTextView) findViewById(R.id.payButton);
        rechargeSwitch = (Switch) findViewById(R.id.rechargeSwitch);

        edtphone_number = (MyEditText) findViewById(R.id.edtphone_number);
        tvCarrier = (MyTextView) findViewById(R.id.tvCarrier);
        edtAmount = (MyEditText) findViewById(R.id.edtAmount);

        btnSeePlans = (MyTextView) findViewById(R.id.btnSeePlans);
        ivContactList = (ImageView) findViewById(R.id.ivContactList);
        rechargeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                Log.v("Switch State=", "" + isChecked);
                if (isChecked) {
                    recharge_type = "2";
                } else {
                    recharge_type = "1";
                }

            }
        });

       /*mAraaCode = "507";
        edtphone_number.setText("63516303");
        showSuccessDialog();*/

        tvAreaCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomCountryDialogFragment customCountryDialogFragment = new CustomCountryDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString(AppoConstants.TITLE, "Please Select Carrier");
                bundle.putParcelableArrayList(AppoConstants.INFO, mListArea);
                customCountryDialogFragment.setArguments(bundle);
                customCountryDialogFragment.setCancelable(false);
                customCountryDialogFragment.show(getSupportFragmentManager(), customCountryDialogFragment.getTag());
            }
        });


        tvCarrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCarrierDialog();
            }
        });


        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyInput();

            }
        });


        ivContactList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentContact = new Intent(MobileRechargeActivity.this, ContactDemoActivity.class);
                startActivityForResult(intentContact, AppoConstants.PICK_CONTACT);


            }
        });
        //showSuccessDialog();

        rvAmountsRecharge.setNestedScrollingEnabled(false);


    }


    private void setListOfAreas() {
        mListArea = new ArrayList<>();
        String data = Helper.AREA_CODE_JSON1;
        try {
            JSONArray rowAreas = new JSONArray(data);
            for (int i = 0; i < rowAreas.length(); i++) {
                JSONObject index = rowAreas.getJSONObject(i);
                CustomArea customArea = new CustomArea(index.getString(AppoConstants.AREACODE), index.getString(AppoConstants.AREACODE_WITH_NAME));
                mListArea.add(customArea);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView menu_icon = toolbar.findViewById(R.id.menu_icon);
        menu_icon.setVisibility(View.GONE);


        TextView toolbarTitle = toolbar.findViewById(R.id.toolbarTitle);
        toolbarTitle.setVisibility(View.VISIBLE);

        toolbarTitle.setText(R.string.toolbar_tilte_recharge);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayUseLogoEnabled(false);
        bar.setDisplayShowTitleEnabled(true);
        bar.setDisplayShowHomeEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);

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
    public void onCustomCountryCodeSelect(String code) {
        tvAreaCode.setText("+" + code);
        mAraaCode = code;
        if (code.equalsIgnoreCase("1809")) {
            mDominicaCode = "809";
        } else if (code.equalsIgnoreCase("1829")) {
            mDominicaCode = "829";
        } else if (code.equalsIgnoreCase("1849")) {
            mDominicaCode = "849";
        } else {
            mDominicaCode = "";
        }

        getCarriers();

    }

    private void getCarriers() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait.");
        dialog.show();
        JsonObject param = new JsonObject();
        param.addProperty(AppoConstants.COUNTRYCODE, mAraaCode);
        ////Log.e(TAG, "getCarriers: " + param.toString());
//        mainAPIInterface.getProductResponse(param).enqueue(new Callback<ProductResponse>() {
        mainAPIInterfaceNode.getProductResponse(param).enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                dialog.dismiss();
                mListProduct = new ArrayList<Product>();
                if (response.isSuccessful()) {
                    //Log.i(TAG, "onResponse: ======"+response.body().getProducts() );
                    mListProduct = response.body().getProducts();
                    if (mListProduct != null) {
                        if (mListProduct.size() > 0) {
                            tvCarrier.setText(carrier_text);
                            getCarrierNames();
                            rvAmountsRecharge.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(MobileRechargeActivity.this, R.string.info_recharge_service_error, Toast.LENGTH_LONG).show();
                            tvCarrier.setText(carrier_text);
                            mListCarrier = null;
                            rvAmountsRecharge.setVisibility(View.GONE);
                            if (response.code() == 500) {
                                ////Log.e(TAG, "onResponse: " + response.toString());
                            }
                        }
                    } else {
                        String data = new Gson().toJson(response.body());
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            //  Log.e(TAG, "onResponse: error :: " + jsonObject);
                            Toast.makeText(MobileRechargeActivity.this, "" + jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                dialog.dismiss();
                Log.i("tag", t.getMessage().toString());
            }
        });


    }

    private void getCarrierNames() {
        mListCarrier = new ArrayList<>();
        for (int i = 0; i < mListProduct.size(); i++) {
            mListCarrier.add(mListProduct.get(i).getCarrier());
        }
    }


    private void showCarrierDialog() {
        if (tvAreaCode.getText().toString().trim().equals(getString(R.string.info_area_code))) {
            Toast.makeText(this, getString(R.string.info_area_code_first), Toast.LENGTH_SHORT).show();
        } else if (mListCarrier == null) {
            Toast.makeText(MobileRechargeActivity.this, getString(R.string.info_recharge_service_error), Toast.LENGTH_LONG).show();
        } else {
            carrierDialogFragment = new CarrierDialogFragment();
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(AppoConstants.INFO, mListCarrier);
            carrierDialogFragment.setArguments(bundle);
            carrierDialogFragment.setCancelable(false);
            carrierDialogFragment.show(getSupportFragmentManager(), carrierDialogFragment.getTag());
        }
    }


    @Override
    public void onCarrierSelect(int pos) {
        ////Log.e(TAG, "onCarrierSelect: carrier position is : " + pos);
        mCarrierPosition = pos;
        tvCarrier.setText(mListProduct.get(pos).getCarrier());
        carrierDialogFragment.dismiss();
        rvAmountsRecharge.setVisibility(View.VISIBLE);
        List<Amount> amounts = mListProduct.get(mCarrierPosition).getAmounts();
        ReceiverAdapter adapter = new ReceiverAdapter(MobileRechargeActivity.this, amounts);
        rvAmountsRecharge.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void verifyInput() {

        if (tvAreaCode.getText().toString().trim().equals("Area code")) {
            Toast.makeText(this, getString(R.string.info_area_code_first), Toast.LENGTH_SHORT).show();
        } else if (mListCarrier == null) {
            Toast.makeText(MobileRechargeActivity.this, getString(R.string.info_recharge_service_error), Toast.LENGTH_LONG).show();
        } else if (tvCarrier.getText().toString().trim().equals(getString(R.string.info_select_carrier))) {
            Toast.makeText(this, getString(R.string.info_please_select_carrier), Toast.LENGTH_SHORT).show();
        } else if (edtAmount.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, getString(R.string.info_enter_recharge_amount), Toast.LENGTH_SHORT).show();
        } else if (edtphone_number.getText().toString().trim().isEmpty()) {
            edtphone_number.setError(getString(R.string.error_enter_phone_number));
            edtphone_number.requestFocus();
        } else if (edtphone_number.getText().toString().trim().length() < 8) {
            edtphone_number.setError(getString(R.string.error_enter_valid_phone_number));
            edtphone_number.requestFocus();
        } /*else if (!edtAmount.getText().toString().trim().matches(AMOUNT_REGEX)) {
           ////Log.e(TAG, "verifyInput: invalid format :: "+edtAmount.getText().toString().trim());
        }*/ else {
            ////Log.e(TAG, "verifyInput: valid format");
            //show wallet dialog
            closeKeyboard();
            showPaymentTypeDialog();
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    private void showPaymentTypeDialog() {
        closeKeyboard();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dilaog_payment_type, null);

        MyButton btnCard = dialogLayout.findViewById(R.id.btnCard);
        MyButton btnWallet = dialogLayout.findViewById(R.id.btnWallet);
        btnCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCardDetails();
            }
        });

        btnWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWalletScreen();
            }
        });
        builder.setView(dialogLayout);

        dialogPayment = builder.create();

        dialogPayment.show();
    }

    private void getCardDetails() {
        if (dialogPayment != null) {
            dialogPayment.dismiss();
        }
    /*    Intent intentCard = new Intent(MobileRechargeActivity.this, TopupCardActivity.class);
        startActivity(intentCard);*/
    }

    private void showWalletScreen() {
        if (dialogPayment != null) {
            dialogPayment.dismiss();
            dialogPayment = null;
        }
        Intent intentWallet = new Intent(this, WalletActivity.class);
        startActivityForResult(intentWallet, AppoConstants.TOPUP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppoConstants.TOPUP_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                senderAccountnumber = data.getStringExtra(AppoConstants.ACCOUNTNUMBER);
                senderAccountstatus = data.getStringExtra(AppoConstants.ACCOUNTSTATUS);
                senderCurrencyid = data.getStringExtra(AppoConstants.CURRENCYID);
                senderCurrentbalance = data.getStringExtra(AppoConstants.CURRENTBALANCE);
                senderId = data.getStringExtra(AppoConstants.ID);
                senderReserveamount = data.getStringExtra(AppoConstants.RESERVEAMOUNT);
                senderCurrencyCode = data.getStringExtra(AppoConstants.CURRENCYCODE);
                getCurrencyConversion();

            }
        } else if (requestCode == AppoConstants.PICK_CONTACT) {
            if (resultCode == Activity.RESULT_OK) {
                //  Log.e(TAG, "onActivityResult: Pick Contact NUmber :: " + data.getStringExtra(AppoConstants.INFO));
                String mMobileNumber = data.getStringExtra(AppoConstants.INFO);
                edtphone_number.setText(mMobileNumber);
                try {
                    // phone must begin with '+'
                    if (phoneUtil == null) {
                        phoneUtil = PhoneNumberUtil.createInstance(MobileRechargeActivity.this);
                    }
                    Phonenumber.PhoneNumber numberProto = phoneUtil.parse(mMobileNumber, "");
                    int countryCode = numberProto.getCountryCode();
                    long nationalNumber = numberProto.getNationalNumber();
                    edtphone_number.setText(String.valueOf(nationalNumber));
                    //  Log.e("code", "code " + countryCode);
                    //  Log.e("code", "national number " + nationalNumber);
                } catch (NumberParseException e) {
                    System.err.println("NumberParseException was thrown: " + e.toString());
                }
            }

        }
    }


    private void getCurrencyConversion() {
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.info_conversion_rate));
        dialog.show();
        String accesstoken = DataVaultManager.getInstance(AppoPayApplication.getInstance()).getVaultValue(KEY_ACCESSTOKEN);
        //String mtype = mListProduct.get(mCarrierPosition).getAmounts().get(mRechargePosition).getDestCurr();
        String bearer_ = Helper.getAppendAccessToken("bearer ", accesstoken);
        mainAPIInterface.getCurrencyConversions(mDesCurrency, bearer_).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    ////Log.e(TAG, "onResponse: conversion : " + new Gson().toJson(response.body()));
                    String src = new Gson().toJson(response.body());
                    try {
                        JSONObject jsonConversion = new JSONObject(src);
                        if (jsonConversion.getString(AppoConstants.MESSAGE).equals(AppoConstants.SUCCESS)) {
                            resultConversion = jsonConversion.getJSONArray(AppoConstants.RESULT);
                            if (resultConversion.length() > 0) {
                                performConversion();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (response.code() == 401) {
                        DataVaultManager.getInstance(MobileRechargeActivity.this).saveUserDetails("");
                        DataVaultManager.getInstance(MobileRechargeActivity.this).saveUserAccessToken("");
                        Intent intent = new Intent(MobileRechargeActivity.this, SignInActivity.class);
                        intent.putExtra(AppoConstants.WHERE,mType);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
                ////Log.e(TAG, "onFailure: " + t.getMessage().toString());
            }
        });

    }

    private void performConversion() {
        try {
            JSONObject indexZero = resultConversion.getJSONObject(0);
            float conversionrate = Float.parseFloat(indexZero.getString(AppoConstants.CONVERSIONRATE));
            newAmount = (float) (conversionrate * mRechargeAmount);
            float currentbalance = Float.parseFloat(senderCurrentbalance);
            currencyId = indexZero.getString(AppoConstants.CURRENCYID);
            if (currentbalance < newAmount) {
                showBalanceError();
            } else {

                showBottomDialog(newAmount);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showBottomDialog(float newAmount) {
        fragmentBottomSheet = new BottotmPinFragment();
        fragmentBottomSheet.show(getSupportFragmentManager(), fragmentBottomSheet.getTag());
        fragmentBottomSheet.setCancelable(false);
    }

    private void showBalanceError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_balance_error, null);

        MyButton btnClose = dialogLayout.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPayment.dismiss();
            }
        });


        builder.setView(dialogLayout);

        dialogPayment = builder.create();

        dialogPayment.setCanceledOnTouchOutside(false);

        dialogPayment.show();
    }


    private void getCommissions(String transaction, final float newAmount) {
        userTransactionPin = transaction;
        ////Log.e(TAG, "getCommissions: pin : " + transaction);
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.info_conversion_rate));
        dialog.show();
        String accesstoken = DataVaultManager.getInstance(AppoPayApplication.getInstance()).getVaultValue(KEY_ACCESSTOKEN);
        String bearer_ = Helper.getAppendAccessToken("bearer ", accesstoken);
        mainAPIInterface.getAllTypeCommissions(bearer_).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    ////Log.e(TAG, "onResponse: commissions : " + new Gson().toJson(response.body()));
                    if (dialogPayment != null) {
                        dialogPayment.dismiss();
                    }
                    String res = new Gson().toJson(response.body());
                    try {
                        jsonCommission = new JSONObject(res);
                        calculateCommission(newAmount);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    if (dialogPayment != null) {
                        dialogPayment.dismiss();
                    }
                    if (response.code() == 401) {
                        DataVaultManager.getInstance(MobileRechargeActivity.this).saveUserDetails("");
                        DataVaultManager.getInstance(MobileRechargeActivity.this).saveUserAccessToken("");
                        Intent intent = new Intent(MobileRechargeActivity.this, SignInActivity.class);
                        intent.putExtra(AppoConstants.WHERE,mType);
                        startActivity(intent);
                        finish();
                    }
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                if (dialogPayment != null) {
                    dialogPayment.dismiss();
                }
                dialog.dismiss();
                ////Log.e(TAG, "onFailure: " + t.getMessage().toString());
            }
        });


    }

    private void calculateCommission(float newAmountParam) {
        try {
            JSONObject result = jsonCommission.getJSONObject(AppoConstants.RESULT);
            float bankcomission = Float.parseFloat(result.getString(AppoConstants.BANKCOMMISSION));
            float processingcomission = Float.parseFloat(result.getString(AppoConstants.PROCESSINGFEES));
            float flatbankcomission = Float.parseFloat(result.getString(AppoConstants.FLATBANKCOMMISSION));
            float flatprocessingcomission = Float.parseFloat(result.getString(AppoConstants.FLATPROCESSINGFEES));
            float fundamount = newAmountParam;
            float taxpercentage = Float.parseFloat(result.getString(AppoConstants.TAXPERCENTAGE));

            bankfees = getTwoDecimal(bankcomission * fundamount);
            newamount = fundamount + bankfees;
            processingfees = getTwoDecimal(fundamount * processingcomission);
            finalamount = newamount + processingfees;
            bankfees = getTwoDecimal(bankfees + flatbankcomission);
            processingfees = getTwoDecimal(processingfees + flatprocessingcomission);
            float flatfees = getTwoDecimal(flatbankcomission + flatprocessingcomission);
            finalamount = (finalamount + flatfees);
            chargesAmount = getTwoDecimal(fundamount * (taxpercentage / 100.0f));
            finalAmount1 = 0;
            finalAmount1 = fundamount + chargesAmount + bankfees;
            finalAmount1 = getTwoDecimal(finalAmount1);
            showYouAboutToPay(newAmountParam);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public float getTwoDecimal(float params) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.UP);
        return Float.parseFloat(df.format(params));
    }

    private void showYouAboutToPay(final float newAmountParam) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        View dialogLayout = inflater.inflate(R.layout.dialog_about_to_pay, null);

        MyTextView tvInfo = dialogLayout.findViewById(R.id.tvInfo);
        MyButton btnYes = dialogLayout.findViewById(R.id.btnYes);
        MyButton btnNo = dialogLayout.findViewById(R.id.btnNo);
        String boldText = "<font color=''><b>" + finalAmount1 + "</b></font>" + " " + "<font color=''><b>" + senderCurrencyCode + "</b></font>";
        //String paymentAmount = getString(R.string.recharge_partial_pay1) + " " + finalamount + " " + senderCurrencyCode + " " + getString(R.string.recharge_partial_pay2);
        String paymentAmount = getString(R.string.recharge_partial_pay1) + " " + boldText + " " + getString(R.string.recharge_partial_pay2);

        tvInfo.setText(Html.fromHtml(paymentAmount));

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePayment(newAmountParam);
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPayment.dismiss();
            }
        });

        builder.setView(dialogLayout);
        dialogPayment = builder.create();
        dialogPayment.setCanceledOnTouchOutside(false);
        dialogPayment.show();
    }


    private void makePayment(float newAmountParam) {
        if (dialogPayment != null) {
            dialogPayment.dismiss();
            dialogPayment = null;
        }
        JsonObject sentParams = new JsonObject();
        //sentParams.addProperty(AppoConstants.AMOUNT, Float.parseFloat(edtAmount.getText().toString().trim()));

        //sentParams.addProperty(AppoConstants.AMOUNT, getTwoDecimal(newAmountParam));
        sentParams.addProperty(AppoConstants.AMOUNT, String.valueOf(mRechargeAmount));
        sentParams.addProperty(AppoConstants.CARRIER, mListProduct.get(mCarrierPosition).getProductName());
        sentParams.addProperty(AppoConstants.CCEXP, (String) null);
        sentParams.addProperty(AppoConstants.CCNUMBER, (String) null);
        sentParams.addProperty(AppoConstants.CHARGES, bankfees);
        sentParams.addProperty(AppoConstants.CVV, (String) null);
        sentParams.addProperty(AppoConstants.FEES, processingfees);
        sentParams.addProperty(AppoConstants.TAXES, chargesAmount);

        sentParams.addProperty(AppoConstants.FROMCURRENCY, currencyId);
        sentParams.addProperty(AppoConstants.FROMCURRENCYCODE, senderCurrencyCode);
        sentParams.addProperty(AppoConstants.FULLNAME, (String) null);
        sentParams.addProperty(AppoConstants.ORIGINALAMOUNT, finalAmount1);
        sentParams.addProperty(AppoConstants.PAYAMOUNT, finalAmount1);
        sentParams.addProperty(AppoConstants.PRODUCTCODE, mListProduct.get(mCarrierPosition).getProductCode());
        String code = "";
        code = mAraaCode;
        if (code.equalsIgnoreCase("1809")) {
            sentParams.addProperty(AppoConstants.RECEIVERAREACODE, "1");
        } else if (code.equalsIgnoreCase("1829")) {
            sentParams.addProperty(AppoConstants.RECEIVERAREACODE, "1");
        } else if (code.equalsIgnoreCase("1849")) {
            sentParams.addProperty(AppoConstants.RECEIVERAREACODE, "1");
        } else {
            sentParams.addProperty(AppoConstants.RECEIVERAREACODE, mAraaCode);
        }

        sentParams.addProperty(AppoConstants.RECEIVERMOBILENUMBER, mDominicaCode + edtphone_number.getText().toString().trim());
        sentParams.addProperty(AppoConstants.SENDERACCOUNTNUMBER, senderAccountnumber);
        String vaultValue = DataVaultManager.getInstance(AppoPayApplication.getInstance()).getVaultValue(KEY_USER_DETIALS);
        try {
            JSONObject jsonUser = new JSONObject(vaultValue);
            JSONObject objResult = jsonUser.getJSONObject(AppoConstants.RESULT);
            sentParams.addProperty(AppoConstants.SENDERAREACODE, objResult.getString(AppoConstants.PHONECODE));
            sentParams.addProperty(AppoConstants.SENDERMOBILENUMBER, objResult.getString(AppoConstants.MOBILENUMBER));
            String senderName = objResult.getString(AppoConstants.FIRSTNAME) + " " + objResult.getString(AppoConstants.LASTNAME);
            sentParams.addProperty(AppoConstants.SENDERNAME, senderName);
            sentParams.addProperty(AppoConstants.TRANSACTIONPIN, userTransactionPin);
            sentParams.addProperty(AppoConstants.USERID, objResult.getString(AppoConstants.ID));
            // Log.e(TAG, "makePayment: " + sentParams.toString());
            makeRechargePayment(sentParams);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void makeRechargePayment(JsonObject sentParams) {
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.info_sending_request));
        dialog.show();
        String accessToken = DataVaultManager.getInstance(AppoPayApplication.getInstance()).getVaultValue(KEY_ACCESSTOKEN);
        String bearer_ = Helper.getAppendAccessToken("bearer ", accessToken);
        mainAPIInterface.postRechargeTopup(sentParams, bearer_).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    ////Log.e(TAG, "onResponse: " + new Gson().toJson(response.body()));
                    String res = new Gson().toJson(response.body());
                    try {
                        JSONObject json = new JSONObject(res);
                        if (json.getString(AppoConstants.RESULT).equalsIgnoreCase(AppoConstants.SUCCESS)) {
                            showSuccessDialog();
                        } else {
                            showErrorDialog(json.getString(AppoConstants.RESULT));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    if (response.code() == 401) {
                        DataVaultManager.getInstance(MobileRechargeActivity.this).saveUserDetails("");
                        DataVaultManager.getInstance(MobileRechargeActivity.this).saveUserAccessToken("");
                        Intent intent = new Intent(MobileRechargeActivity.this, SignInActivity.class);
                        intent.putExtra(AppoConstants.WHERE,mType);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
                ////Log.e(TAG, "onFailure: " + t.getMessage().toString());
            }
        });

    }


    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_success_topup, null);
        MyTextView tvInfo = dialogLayout.findViewById(R.id.tvInfo);
        MyButton btnClose = dialogLayout.findViewById(R.id.btnClose);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateWallet();
            }
        });

        builder.setView(dialogLayout);

        dialogPayment = builder.create();

        dialogPayment.setCanceledOnTouchOutside(false);

        dialogPayment.show();
    }





    private void getCurrencyCode() {
        dialog = new ProgressDialog(MobileRechargeActivity.this);
        dialog.setMessage(getString(R.string.info_get_curreny_code));
        dialog.show();

        mainAPIInterface.getCurrencyResponse().enqueue(new Callback<CurrencyResponse>() {
            @Override
            public void onResponse(Call<CurrencyResponse> call, Response<CurrencyResponse> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    result = response.body().getResult();
                    readUserAccounts();
                }
            }

            @Override
            public void onFailure(Call<CurrencyResponse> call, Throwable t) {
                dialog.dismiss();
                //  Log.e(TAG, "onFailure: " + t.getMessage().toString());
            }
        });

    }

    private void readUserAccounts() {
        mListAccount = new ArrayList<>();
        ArrayList<String> mListWalletNumber = new ArrayList<String>();
        String vaultValue = mResultReceiver;
        try {
            JSONObject root = new JSONObject(vaultValue);
            JSONObject objResult = root.getJSONObject(AppoConstants.RESULT);
            JSONObject objCustomerDetails = objResult.getJSONObject(AppoConstants.CUSTOMERDETAILS);
            JSONArray arrCustomerAccount = objCustomerDetails.getJSONArray(AppoConstants.CUSTOMERACCOUNT);
            receiverEmail = objResult.getString(AppoConstants.EMIAL);

            for (int i = 0; i < arrCustomerAccount.length(); i++) {
                JSONObject index = arrCustomerAccount.getJSONObject(i);
                AccountModel model = new AccountModel();
                model.setAccountnumber(index.getString(AppoConstants.ACCOUNTNUMBER));
                String mIncryptAccount = getAccountNumber(index.getString(AppoConstants.ACCOUNTNUMBER));
                model.setAccountEncrypt(mIncryptAccount);
                //  Log.e(TAG, "readUserAccounts: encrypt ::  " + mIncryptAccount);
                if (index.has(AppoConstants.ACCOUNTSTATUS)) {
                    ////Log.e(TAG, "readUserAccounts: AccountStatus : " + index.getString(AppoConstants.ACCOUNTSTATUS));
                    model.setAccountstatus(index.getString(AppoConstants.ACCOUNTSTATUS));
                } else {
                    ////Log.e(TAG, "readUserAccounts: AccountStatus : " + "null");
                    model.setAccountstatus("");
                }
                model.setCurrencyid(index.getString(AppoConstants.CURRENCYID));
                model.setCurrencyCode(getCurrency(index.getString(AppoConstants.CURRENCYID)));
                String balance = index.getString(AppoConstants.CURRENTBALANCE);
                DecimalFormat df2 = new DecimalFormat("#.00");
                Double doubleV = Double.parseDouble(balance);
                String format = df2.format(doubleV);
                model.setCurrentbalance(format);
                mListWalletNumber.add(getNumberInStyle(index.getString(AppoConstants.ACCOUNTNUMBER)));
                mListAccount.add(model);
            }
            //  Log.e(TAG, "readUserAccounts: send notification user exists");
            //getReceiverToken();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getCurrency(String param) {
        return Helper.getCurrency(param, result);
    }

    public String getNumberInStyle(String param) {
        String accountEncrypt = param;
        char[] charsEncrypt = accountEncrypt.toCharArray();
        int count = -1;
        int count1 = 0;
        String temp = "";
        String finalString = "";
        for (int i = 0; i < charsEncrypt.length; i++) {
            count = count + 1;

            temp = temp + String.valueOf(charsEncrypt[i]);
            if (count == 4) {
                finalString = finalString + temp + "    ";
                temp = "";
                count = -1;
            }
            count1 = count1 + 1;
            if (count1 >= charsEncrypt.length) {
                finalString = finalString + temp;
            }

        }
        return finalString;
    }

    private String getAccountNumber(String param) {
        return Helper.getAccountNumber(param);
    }

    /*private void getReceiverToken() {
        Query mDatabaseQuery = FirebaseDatabase.getInstance().getReference(AppoConstants.FIREBASE_USERS_NODE)
                .orderByChild(AppoConstants.EMIAL_ID)
                .equalTo(receiverEmail);
        mList = new ArrayList<ChatUser>();
        mDatabaseQuery.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            mList.clear();
            if (snapshot.exists()) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatUser chatUser = dataSnapshot.getValue(ChatUser.class);
                    mList.add(chatUser);
                }
                if (mList.size() > 0) {
                    sendNotification();


                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    private void sendNotification() {
        dialog = new ProgressDialog(MobileRechargeActivity.this);
        dialog.setMessage("Please wait...");
        dialog.show();
        mainAPIInterface2 = ApiUtils.getApiServiceForNotification("https://fcm.googleapis.com/");
        Query mDatabaseQuery = FirebaseDatabase.getInstance().getReference(AppoConstants.FIREBASE_USER_TOKENS)
                .orderByKey()
                .equalTo(mList.get(0).getId());
        mDatabaseQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dialog.dismiss();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Token token = dataSnapshot.getValue(Token.class);
                        String message = "An recharge of amount  (" + mDesCurrency + " " + mRechargeAmount + ")" + " has been credit to your phone number";
                        //  Log.e(TAG, "onDataChange: message :: " + message);
                        JsonObject jsonParam = new JsonObject();
                        jsonParam.addProperty(AppoConstants.MESSAGE, message);
                        String sentParam = jsonParam.toString();
                        Data data = new Data(AppoConstants.TOPUP, 1, "Topup Airtime", sentParam,
                                mList.get(0).getId());
                        Sender sender = new Sender(data, token.getToken());
                        mainAPIInterface2.sendNotification(sender).enqueue(new Callback<MyResponse>() {
                            @Override
                            public void onResponse(@NotNull Call<MyResponse> call, @NotNull Response<MyResponse> response) {
                                if (response.code() == 200) {
                                    if (response.body().success == 1) {
                                        //Toast.makeText(MobileRechargeActivity.this, "Recharge Notification Success", Toast.LENGTH_SHORT).show();
                                        updateWallet();
                                    } else {
                                        updateWallet();
                                    }
                                } else {
                                    updateWallet();
                                }
                            }

                            @Override
                            public void onFailure(Call<MyResponse> call, Throwable t) {
                                //  Log.e(TAG, "onFailure: " + t.getMessage().toString());
                                dialog.dismiss();
                                updateWallet();
                            }
                        });

                    }
                } else {
                    //Toast.makeText(getContext(), "User token not exists", Toast.LENGTH_SHORT).show();
                    updateWallet();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                ////Log.e(TAG, "onCancelled: called");
                updateWallet();
            }
        });
    }*/

    /*private void sendNotification() {
        dialog = new ProgressDialog(MobileRechargeActivity.this);
        dialog.setMessage("Please wait...");
        dialog.show();
        mainAPIInterface2 = ApiUtils.getApiServiceForNotification("https://fcm.googleapis.com/");
        Query mDatabaseQuery = FirebaseDatabase.getInstance().getReference(AppoConstants.FIREBASE_USER_TOKENS)
                .orderByKey()
                .equalTo(mList.get(0).getId());
        mDatabaseQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dialog.dismiss();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Token token = dataSnapshot.getValue(Token.class);
                        String message = "An amount of ("+tvAmountCredit.getText().toString().trim() +" "+fomrcurrencycode+")"+ " has been credit to your wallet account during the e-wallet transfer";
                        JsonObject jsonParam = new JsonObject();
                        jsonParam.addProperty(AppoConstants.MESSAGE, message);
                        String sentParam = jsonParam.toString();
                        Data data = new Data(AppoConstants.WALLET_TRANSFER, 1, "Wallet Transfer", sentParam,
                                mList.get(0).getId());
                        Sender sender = new Sender(data, token.getToken());

                        mainAPIInterface2.sendNotification(sender).enqueue(new Callback<MyResponse>() {
                            @Override
                            public void onResponse(@NotNull Call<MyResponse> call, @NotNull Response<MyResponse> response) {
                                if (response.code() == 200) {
                                    if (response.body().success == 1) {
                                        //Toast.makeText(getContext(), "Gift Card Sent", Toast.LENGTH_SHORT).show();
                                        mMoneyTransfer.OnMoneyTransferSuccess();
                                    }else {
                                        mMoneyTransfer.OnMoneyTransferSuccess();
                                    }
                                }else {
                                    mMoneyTransfer.OnMoneyTransferSuccess();
                                }
                            }

                            @Override
                            public void onFailure(Call<MyResponse> call, Throwable t) {
                             //  Log.e(TAG, "onFailure: " + t.getMessage().toString());
                                dialog.dismiss();
                                mMoneyTransfer.OnMoneyTransferSuccess();
                            }
                        });

                    }
                } else {
                    //Toast.makeText(getContext(), "User token not exists", Toast.LENGTH_SHORT).show();
                    mMoneyTransfer.OnMoneyTransferSuccess();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               ////Log.e(TAG, "onCancelled: called");
                mMoneyTransfer.OnMoneyTransferSuccess();
            }
        });
    }*/

    public void updateWallet() {
        /*Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);*/
        finish();
    }

    private void showErrorDialog(String params) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_coomon_error, null);
        MyTextView tvInfo = dialogLayout.findViewById(R.id.tvInfo);
        MyButton btnClose = dialogLayout.findViewById(R.id.btnClose);
        tvInfo.setText(params);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPayment.dismiss();
            }
        });

        builder.setView(dialogLayout);

        dialogPayment = builder.create();

        dialogPayment.setCanceledOnTouchOutside(false);

        dialogPayment.show();
    }

    @Override
    public void onReceiverClick(int pos, int desAmount, String desCurrency) {
        if (edtphone_number.getText().toString().trim().isEmpty()) {
            edtphone_number.setError(getString(R.string.info_receiver_mobile_number));
            edtphone_number.requestFocus();
            return;
        }
        if (edtphone_number.getText().toString().trim().length() < 6) {
            edtphone_number.setError(getString(R.string.info_valid_mobile_number));
            edtphone_number.requestFocus();
            return;
        }

        mRechargePosition = pos;
        mRechargeAmount = (float) desAmount;
        mDesCurrency = desCurrency;
        showPaymentTypeDialog();

    }

    @Override
    public void onPinConfirm(String pin) {
        if (fragmentBottomSheet != null)
            fragmentBottomSheet.dismiss();
        getCommissions(pin.trim(), newAmount);
    }


    /**
     * {
     *         amount: "5.00"
     *         carrier: "CoopRed Panama Claro"
     *         ccexp: null
     *         ccnumber: null
     *         charges: "0.50"
     *         cvv: null
     *         fees: "0.03"
     *         fromcurrency: 1
     *         fromcurrencycode: "USD"
     *         fullName: null
     *         originalAmount: 5.53
     *         payamount: 5.53
     *         productcode: "101209001"
     *         recieverareacode: "507"
     *         recievermobilernumber: "9836683269"
     *         senderaccountnumber: "CS1000000000061"
     *         senderareacode: 91
     *         sendermobilenumber: 9836683269
     *         sendername: "MD WASIM"
     *         transactionpin: "653214"
     *         userid: 82
     *         50763516303
     *     }
     */

    /**
     * amount: "7.00"
     * carrier: "CoopRed Panama Claro"
     * ccexp: null
     * ccnumber: null
     * charges: "0.00"
     * cvv: null
     * fees: "0.00"
     * fromcurrency: 1
     * fromcurrencycode: "USD"
     * fullName: null
     * originalAmount: 7.49
     * payamount: 7.49
     * productcode: "101699002"
     * recieverareacode: "507"
     * recievermobilernumber: "63516303"
     * senderaccountnumber: "6367820001585611774"
     * senderareacode: 507
     * sendermobilenumber: 63516303
     * sendername: "MOE ALHARAZI"
     * taxes: 0.49
     * transactionpin: "777777"
     * userid: 108
     */


}


/**
 * You are about to pay 5.53 USD for purchase of topup . Please confirm ?
 */
