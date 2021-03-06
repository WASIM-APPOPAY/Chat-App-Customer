package com.stuffrs.newappopay.stuffers_business.fragments.bottom;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stuffrs.newappopay.stuffers_business.AppoPayApplication;
import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.stuffers_business.activity.wallet.SignInActivity;
import com.stuffrs.newappopay.stuffers_business.adapter.recyclerview.ActiveAccountAdapter;
import com.stuffrs.newappopay.stuffers_business.api.ApiUtils;
import com.stuffrs.newappopay.stuffers_business.api.MainAPIInterface;
import com.stuffrs.newappopay.stuffers_business.communicator.StartActivityListener;
import com.stuffrs.newappopay.stuffers_business.communicator.UserAccountTransferListener;
import com.stuffrs.newappopay.stuffers_business.fragments.dialog.AreaCodeDialog;
import com.stuffrs.newappopay.stuffers_business.models.output.AccountModel;
import com.stuffrs.newappopay.stuffers_business.models.output.CurrencyResponse;
import com.stuffrs.newappopay.stuffers_business.models.output.CurrencyResult;
import com.stuffrs.newappopay.stuffers_business.utils.AppoConstants;
import com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager;
import com.stuffrs.newappopay.stuffers_business.utils.Helper;
import com.stuffrs.newappopay.stuffers_business.views.MyButton;
import com.stuffrs.newappopay.stuffers_business.views.MyEditText;
import com.stuffrs.newappopay.stuffers_business.views.MyTextView;
import com.stuffrs.newappopay.stuffers_business.views.MyTextViewBold;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hbb20.CountryCodePicker;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager.KEY_ACCESSTOKEN;
import static com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager.KEY_USER_DETIALS;

public class BankFragment extends Fragment {
    private static final String TAG = "BankFragment";
    View mView;
    private MyEditText edtphone_number;
    private MyButton btnSearch, btnChange;
    private MainAPIInterface mainAPIInterface;
    private ProgressDialog dialog;
    CountryCodePicker edtCustomerCountryCode;
    private JSONObject indexUser;
    private List<CurrencyResult> resultCurrency;
    private ArrayList<AccountModel> mListAccount;
    private MyTextView tvUserMobileName, tvAccounts;
    private CardView cardUser;
    private UserAccountTransferListener mListener;
    private String currencyResponse;
    private RecyclerView rvActiveAccounts;
    private String recivername;
    String recieverareacode;
    String recivermobilenumber;
    private String reciveruserid;
    private int fromPosition;
    private View ivContactList;
    private String reciveremail;
    private PhoneNumberUtil phoneUtil;
    private StartActivityListener mListenerStart;
    private MyTextViewBold tvNameH;
    private MyTextViewBold tvBalanceH;
    private String selectedCountryNameCode;
    private String mDominicaAreaCode = "";
    private MyTextViewBold tvAreaCodeDo;
    private ArrayList<String> mAreaList;
    private AreaCodeDialog mAreaDialog;
    private String selectedCountryCode;
    private int mType=0;


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.bank_fragment, container, false);
        Bundle arguments = this.getArguments();
         mType = arguments.getInt(AppoConstants.WHERE, 0);
        tvAreaCodeDo = (MyTextViewBold) mView.findViewById(R.id.tvAreaCodeDo);
        btnChange = mView.findViewById(R.id.btnChange);
        tvNameH = (MyTextViewBold) mView.findViewById(R.id.tvNameH);
        tvBalanceH = (MyTextViewBold) mView.findViewById(R.id.tvBalanceH);

        edtCustomerCountryCode = mView.findViewById(R.id.edtCustomerCountryCode);
        edtphone_number = mView.findViewById(R.id.edtphone_number);
        ivContactList = mView.findViewById(R.id.ivContactList);
        rvActiveAccounts = mView.findViewById(R.id.rvActiveAccount);


        btnSearch = mView.findViewById(R.id.btnSearch);
        mainAPIInterface = ApiUtils.getAPIService();
        String defaultCountryCode = edtCustomerCountryCode.getDefaultCountryCode();
        //Log.e(TAG, "onCreateView: " + defaultCountryCode);
        rvActiveAccounts.setLayoutManager(new LinearLayoutManager(getContext()));


        edtCustomerCountryCode.setExcludedCountries(getString(R.string.info_exclude_countries));


        btnChange.setVisibility(View.GONE);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAccountTransfer(null, null, null);
            }
        });

        String keyUserDetails = DataVaultManager.getInstance(getActivity()).getVaultValue(DataVaultManager.KEY_USER_DETIALS);
        if (StringUtils.isEmpty(keyUserDetails)) {
            tvBalanceH.setText("$0.00");
            tvNameH.setText("USER NAME");
        } else {
            String senderName = Helper.getSenderName();
            tvNameH.setText(senderName);
            String currantBalance = Helper.getCurrantBalance();
            DecimalFormat df2 = new DecimalFormat("#.00");
            Double doubleV = Double.parseDouble(currantBalance);
            String format = df2.format(doubleV);
            tvBalanceH.setText("$" + format);
        }

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtphone_number.getText().toString().trim().isEmpty()) {
                    edtphone_number.setError(getString(R.string.info_enter_phone_number));
                    edtphone_number.requestFocus();
                    return;
                }

                if (edtphone_number.getText().toString().trim().length() < 4) {
                    edtphone_number.setError(getString(R.string.info_enter_valid_phone_number));
                    edtphone_number.requestFocus();
                    return;
                }

                String vaultValue = DataVaultManager.getInstance(AppoPayApplication.getInstance()).getVaultValue(KEY_USER_DETIALS);
                try {
                    JSONObject toUserObject = new JSONObject(vaultValue);
                    JSONObject result = toUserObject.getJSONObject(AppoConstants.RESULT);
                    if (result.getString(AppoConstants.MOBILENUMBER).equalsIgnoreCase(edtphone_number.getText().toString().trim())) {
                        Helper.hideKeyboard(btnChange, getContext());
                        Helper.showCommonErrorDialog(getContext(), getString(R.string.mobile_no_error), getString(R.string.mobile_no_error_info));
                    } else {
                        //Log.e(TAG, "onClick: " + edtCustomerCountryCode.getSelectedCountryCode());
                        Helper.hideKeyboard(btnChange, getContext());
                        selectedCountryCode = edtCustomerCountryCode.getSelectedCountryCode();
                        //selectedCountryCode = selectedCountryCode + mDominicaAreaCode;
                        onSearchRequest(mDominicaAreaCode+edtphone_number.getText().toString().trim(),selectedCountryCode );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        ivContactList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mListenerStart.OnStartActivityRequest(AppoConstants.PICK_CONTACT);

            }
        });

        edtCustomerCountryCode.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                selectedCountryNameCode = edtCustomerCountryCode.getSelectedCountryNameCode();
                if (selectedCountryNameCode.equalsIgnoreCase("DO")) {
                    mDominicaAreaCode = "";
                    tvAreaCodeDo.setVisibility(View.VISIBLE);
                } else {
                    mDominicaAreaCode = "";
                    tvAreaCodeDo.setVisibility(View.GONE);
                }
            }
        });

        tvAreaCodeDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAreaCodes();
            }
        });


        return mView;
    }

    private void getAreaCodes() {
        mAreaList = new ArrayList<String>();
        mAreaList.add("809");
        mAreaList.add("829");
        mAreaList.add("849");

        mAreaDialog = new AreaCodeDialog();
        Bundle bundle = new Bundle();
        bundle.putString(AppoConstants.TITLE, "Please Select Area Code");
        bundle.putStringArrayList(AppoConstants.INFO, mAreaList);
        mAreaDialog.setArguments(bundle);
        mAreaDialog.setCancelable(false);
        mAreaDialog.show(getChildFragmentManager(), mAreaDialog.getTag());

    }

    private void onSearchRequest(String ph, String area) {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("please wait, getting user account");
        dialog.show();

        recieverareacode = area;
        recivermobilenumber = ph;

        String accessToken = DataVaultManager.getInstance(getContext()).getVaultValue(KEY_ACCESSTOKEN);
        String bearer_ = Helper.getAppendAccessToken("bearer ", accessToken);
        mainAPIInterface.getProfileDetails(Long.parseLong(ph), Integer.parseInt(area), bearer_).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    //String res = new Gson().toJson(response.body());
                    JsonObject body = response.body();
                    String res = body.toString();

                    //Log.e(TAG, "onResponse: getprofile :" + res);
                    try {
                        indexUser = new JSONObject(res);
                        if (indexUser.isNull("result")) {
                            //Log.e(TAG, "onResponse: " + true);
                            Toast.makeText(getContext(), getString(R.string.error_user_detail_not_found), Toast.LENGTH_SHORT).show();
                        } else {
                            getCurrency();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    if (response.code() == 401) {
                        DataVaultManager.getInstance(getContext()).saveUserDetails("");
                        DataVaultManager.getInstance(getContext()).saveUserAccessToken("");
                        Intent intent = new Intent(getContext(), SignInActivity.class);
                        intent.putExtra(AppoConstants.WHERE,mType);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    } else if (response.code() == 400) {
                        Toast.makeText(getContext(), getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
                //Log.e(TAG, "onFailure: " + t.getMessage().toString());
            }
        });

    }

    private void getCurrency() {
        dialog = new ProgressDialog(getContext());
        dialog.setMessage(getString(R.string.info_getting_currency_code));
        dialog.show();

        mainAPIInterface.getCurrencyResponse().enqueue(new Callback<CurrencyResponse>() {
            @Override
            public void onResponse(Call<CurrencyResponse> call, Response<CurrencyResponse> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    currencyResponse = new Gson().toJson(response.body().getResult());
                    //Log.e(TAG, "onResponse: =========" + currencyResponse);
                    resultCurrency = response.body().getResult();
                    readUserAccounts();
                }
            }

            @Override
            public void onFailure(Call<CurrencyResponse> call, Throwable t) {
                dialog.dismiss();
                //Log.e(TAG, "onFailure: " + t.getMessage().toString());
            }
        });
    }

    private void readUserAccounts() {
        mListAccount = new ArrayList<>();
        String vaultValue = DataVaultManager.getInstance(AppoPayApplication.getInstance()).getVaultValue(KEY_USER_DETIALS);

        //Log.e(TAG, "readUserAccounts: " + vaultValue);
        String nameWithMobile = null;
        try {
            JSONObject root = indexUser;
            JSONObject objResult = root.getJSONObject(AppoConstants.RESULT);
            nameWithMobile = objResult.getString(AppoConstants.FIRSTNAME) + " " + objResult.getString(AppoConstants.LASTNAME) + "-" + objResult.getString(AppoConstants.MOBILENUMBER);
            recivername = objResult.getString(AppoConstants.FIRSTNAME) + " " + objResult.getString(AppoConstants.LASTNAME);
            reciveruserid = objResult.getString(AppoConstants.ID);
            reciveremail = objResult.getString(AppoConstants.EMIAL);

            JSONObject objCustomerDetails = objResult.getJSONObject(AppoConstants.CUSTOMERDETAILS);
            JSONArray arrCustomerAccount = objCustomerDetails.getJSONArray(AppoConstants.CUSTOMERACCOUNT);

            for (int i = 0; i < 1; i++) {
                JSONObject index = arrCustomerAccount.getJSONObject(i);
                AccountModel model = new AccountModel();
                model.setAccountnumber(index.getString(AppoConstants.ACCOUNTNUMBER));
                model.setAccountEncrypt(null);
                if (index.has(AppoConstants.ACCOUNTSTATUS)) {
                    //Log.e(TAG, "readUserAccounts: AccountStatus : " + index.getString(AppoConstants.ACCOUNTSTATUS));
                    model.setAccountstatus(index.getString(AppoConstants.ACCOUNTSTATUS));
                    model.setCurrencyid(index.getString(AppoConstants.CURRENCYID));
                    model.setCurrencyCode(getCurrency(index.getString(AppoConstants.CURRENCYID)));
                    model.setCurrentbalance(index.getString(AppoConstants.CURRENTBALANCE));
                    mListAccount.add(model);
                }

            }

            if (mListAccount.size() > 0) {
                ActiveAccountAdapter activeAccountAdapter = new ActiveAccountAdapter(getContext(), mListAccount, nameWithMobile);
                rvActiveAccounts.setAdapter(activeAccountAdapter);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private String getCurrency(String param) {
        String res = null;
        for (int i = 0; i < resultCurrency.size(); i++) {
            String sid = resultCurrency.get(i).getId().toString();
            if (sid.equals(param)) {

                res = resultCurrency.get(i).getCurrencyCode();
                break;
            }
        }
        return res;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (UserAccountTransferListener) context;
        mListenerStart = (StartActivityListener) context;

    }

    public void getBaseConversion(int pos) {
        getConversionBaseRate(pos);
    }

    private void getConversionBaseRate(int pos) {
        fromPosition = pos;
        ////https://api.exchangeratesapi.io/latest?base=USD
        //String url = "https://api.exchangeratesapi.io/latest?base=" + mListAccount.get(pos).getCurrencyCode();
        //Log.e(TAG, "getConversionBaseRate: url :: " + url);
        sentParam();
    }

    private void sentParam() {
        JSONObject objReceiver = new JSONObject();
        try {
            objReceiver.put(AppoConstants.RECIEVERACCOUNTNUMBER, mListAccount.get(fromPosition).getAccountnumber());
            objReceiver.put(AppoConstants.FROMCURRENCY, mListAccount.get(fromPosition).getCurrencyid());
            objReceiver.put(AppoConstants.FROMCURRENCYCODE, mListAccount.get(fromPosition).getCurrencyCode());
            objReceiver.put(AppoConstants.RECEIVERMOBILENUMBER, recivermobilenumber);
            objReceiver.put(AppoConstants.RECEIVERAREACODE, recieverareacode);
            objReceiver.put(AppoConstants.RECIEVERNAME, recivername);
            objReceiver.put(AppoConstants.RECIEVERUSERID, reciveruserid);
            objReceiver.put(AppoConstants.EMIAL, reciveremail);
            //Log.e(TAG, "sentParam: " + objReceiver.toString());
            //mListener.onAccountTransfer(objReceiver, resultCurrency, response);
            mListener.onAccountTransfer(objReceiver, resultCurrency, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppoConstants.PICK_CONTACT) {
            if (resultCode == Activity.RESULT_OK) {
                Log.e(TAG, "onActivityResult: Pick Contact NUmber :: " + data.getStringExtra(AppoConstants.INFO));
                String mMobileNumber = data.getStringExtra(AppoConstants.INFO);
                edtphone_number.setText(mMobileNumber);
                try {
                    // phone must begin with '+'
                    if (phoneUtil == null) {
                        phoneUtil = PhoneNumberUtil.createInstance(getActivity());
                    }
                    Phonenumber.PhoneNumber numberProto = phoneUtil.parse(mMobileNumber, "");
                    int countryCode = numberProto.getCountryCode();
                    Log.e(TAG, "onActivityResult: " + countryCode);
                    edtCustomerCountryCode.setCountryForPhoneCode(countryCode);
                    long nationalNumber = numberProto.getNationalNumber();
                    edtphone_number.setText(String.valueOf(nationalNumber));
                } catch (NumberParseException e) {
                    System.err.println("NumberParseException was thrown: " + e.toString());
                }
            }
        }

    }

    public void passPhoneNumber(String mMobileNumber) {
        edtphone_number.setText(mMobileNumber);
        try {
            // phone must begin with '+'
            if (phoneUtil == null) {
                phoneUtil = PhoneNumberUtil.createInstance(getActivity());
            }
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(mMobileNumber, "");
            int countryCode = numberProto.getCountryCode();
            Log.e(TAG, "onActivityResult: " + countryCode);
            edtCustomerCountryCode.setCountryForPhoneCode(countryCode);

            long nationalNumber = numberProto.getNationalNumber();
            edtphone_number.setText(String.valueOf(nationalNumber));
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
        }
    }


    public void updateAreaCode(int pos) {
        if (mAreaDialog != null) {
            mAreaDialog.dismiss();
        }
        mDominicaAreaCode = mAreaList.get(pos);
        tvAreaCodeDo.setText("Area Code : " + mDominicaAreaCode);
    }
}
