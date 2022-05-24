package com.stuffrs.newappopay.stuffers_business.activity.wallet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.stuffrs.newappopay.activity.ChatActivity;
import com.stuffrs.newappopay.stuffers_business.AppoPayApplication;
import com.stuffrs.newappopay.stuffers_business.MyContextWrapper;
import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.stuffers_business.activity.forgopassword.ForgotPasswordActvivity;
import com.stuffrs.newappopay.stuffers_business.api.ApiUtils;
import com.stuffrs.newappopay.stuffers_business.api.MainAPIInterface;
import com.stuffrs.newappopay.stuffers_business.communicator.AreaSelectListener;
import com.stuffrs.newappopay.stuffers_business.fragments.bottom.chatnotification.Token;
import com.stuffrs.newappopay.stuffers_business.fragments.bottom_fragment.BottomPasswordPolicy;
import com.stuffrs.newappopay.stuffers_business.fragments.dialog.AreaCodeDialog;
import com.stuffrs.newappopay.stuffers_business.models.output.AuthorizationResponse;
import com.stuffrs.newappopay.stuffers_business.models.output.MappingResponse;
import com.stuffrs.newappopay.stuffers_business.utils.AppoConstants;
import com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager;
import com.stuffrs.newappopay.stuffers_business.views.MyEditText;
import com.stuffrs.newappopay.stuffers_business.views.MyTextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonObject;
import com.hbb20.CountryCodePicker;
import com.stuffrs.newappopay.stuffers_business.views.MyTextViewBold;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager.KEY_ACCESSTOKEN;
import static com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager.KEY_FIREBASE_TOKEN;
import static com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager.KEY_UNIQUE_NUMBER;
import static com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager.KEY_USER_LANGUAGE;
import static com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager.TANDC;

public class SignInActivity extends AppCompatActivity implements AreaSelectListener {

    MyTextView signup;
    MyTextView signin1, signin11;

    MyEditText edtMobile, edtPassword;
    String strMobile, strPassword;
    ProgressDialog dialog;
    MainAPIInterface mainAPIInterface;
    private String TAG = "TAG";
    private AppCompatSpinner spCountry;
    private ImageView ivRefresh;


    private CountryCodePicker edtCustomerCountryCode;
    private String selectedCountryCode;
    private FirebaseAuth mAuth;
    private long mLastClickTime = 0;
    MyTextView tvForgotPassword;
    private MyTextView tvPwdPolicy;
    Button tvEmailTest;
    private String mUserId;
    private DatabaseReference reference;
    private MyTextViewBold tvAreaCodeDo;
    private String selectedCountryNameCode = "";
    private String mDominicaAreaCode = "";
    private ArrayList<String> mAreaList;
    private AreaCodeDialog mAreaDialog;
    private int mType = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        if (getIntent().getExtras() != null) {
            mType = getIntent().getIntExtra(AppoConstants.WHERE, 0);
        }
        tvAreaCodeDo = (MyTextViewBold) findViewById(R.id.tvAreaCodeDo);
        mAuth = FirebaseAuth.getInstance();
        mainAPIInterface = ApiUtils.getAPIService();
        edtCustomerCountryCode = findViewById(R.id.edtCustomerCountryCode);
        signup = (MyTextView) findViewById(R.id.signup);
        signin1 = (MyTextView) findViewById(R.id.signin1);
        signin11 = findViewById(R.id.signin11);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvPwdPolicy = findViewById(R.id.tvPwdPolicy);
        getRandomNumberString();


        edtMobile = (MyEditText) findViewById(R.id.edtMobile);
        edtPassword = (MyEditText) findViewById(R.id.edtPassword);

        spCountry = (AppCompatSpinner) findViewById(R.id.spCountry);
        ivRefresh = (ImageView) findViewById(R.id.ivRefresh);

        edtCustomerCountryCode.setExcludedCountries(getString(R.string.info_exclude_countries));


        signin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strMobile = edtMobile.getText().toString();
                strPassword = edtPassword.getText().toString();
                if (strMobile.length() < 5) {
                    edtMobile.setFocusable(true);
                    edtMobile.setError(getString(R.string.info_enter_mobile_number));
                } else if (strPassword.length() < 5) {
                    edtPassword.setFocusable(true);
                    edtPassword.setError(getString(R.string.info_enter_password));
                } else {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        ////Log.e(TAG, "onClick: RETURN CALLED");
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    if (AppoPayApplication.isNetworkAvailable(SignInActivity.this)) {
                        userMapping();
                    } else {
                        Toast.makeText(SignInActivity.this, getString(R.string.no_inteenet_connection), Toast.LENGTH_SHORT).show();
                    }
                }
                //237,465

            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newIntent = new Intent(SignInActivity.this, MobileNumberRegistrationActivity.class);
                startActivity(newIntent);
                finish();
                /*Intent intent = new Intent(SignInActivity.this, ContactDemoActivity.class);
                startActivity(intent);*/
            }
        });


        signin11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                throw new RuntimeException();
            }
        });
        signin11.setVisibility(View.GONE);

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppoPayApplication.isNetworkAvailable(SignInActivity.this)) {
                    Intent intentForgot = new Intent(SignInActivity.this, ForgotPasswordActvivity.class);
                    startActivity(intentForgot);
                } else {
                    Toast.makeText(SignInActivity.this, getString(R.string.no_inteenet_connection), Toast.LENGTH_SHORT).show();
                }
                //  getGiftCards();
            }
        });
        tvPwdPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomPasswordPolicy bottomPasswordPolicy = new BottomPasswordPolicy();
                bottomPasswordPolicy.show(getSupportFragmentManager(), bottomPasswordPolicy.getTag());
                //create();

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
        mAreaDialog.show(getSupportFragmentManager(), mAreaDialog.getTag());

    }


    public void getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        Log.e("TAG", "getRandomNumberString: " + String.format("%06d", number));
    }


    private void userMapping() {
        dialog = new ProgressDialog(SignInActivity.this);
        dialog.setMessage(getString(R.string.info_mapping_user));
        dialog.show();
        selectedCountryCode = edtCustomerCountryCode.getSelectedCountryCode();
        mainAPIInterface.getMapping("+" + selectedCountryCode + mDominicaAreaCode + edtMobile.getText().toString().trim()).enqueue(new Callback<MappingResponse>() {
            @Override
            public void onResponse(Call<MappingResponse> call, Response<MappingResponse> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(200)) {
                        DataVaultManager.getInstance(SignInActivity.this).saveUniqueNumber(response.body().getResult().getUniquenumber());
                        getAccessToken();
                    }
                } else {
                    if (response.code() == 502) {
                        Toast.makeText(SignInActivity.this, getString(R.string.info_bad_request), Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 503) {
                        Toast.makeText(SignInActivity.this, getString(R.string.info_503), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "onResponse: mapping :: " + response);
                        Toast.makeText(SignInActivity.this, getString(R.string.info_user_not_exist), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MappingResponse> call, Throwable t) {
                dialog.dismiss();
                Log.e("tag", t.getMessage().toString());
            }
        });
    }

    private void getAccessToken() {
        dialog = new ProgressDialog(SignInActivity.this);
        dialog.setMessage("Please wait, getting access token.");
        dialog.show();
        String strUniqueNumber = DataVaultManager.getInstance(AppoPayApplication.getInstance()).getVaultValue(KEY_UNIQUE_NUMBER);
        String userName = "devglan-client";
        String password = "devglan-secret";
        String base = userName + ":" + password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
        String loginPassword = edtPassword.getText().toString().trim();

        mainAPIInterface.getAuthorization(authHeader, strUniqueNumber, loginPassword, "password").enqueue(new Callback<AuthorizationResponse>() {
            @Override
            public void onResponse(Call<AuthorizationResponse> call, Response<AuthorizationResponse> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    String accessToken = response.body().getAccessToken();
                    DataVaultManager.getInstance(SignInActivity.this).saveUserAccessToken(accessToken);
                    //generateOtp();
                    getSignInDetails();
                } else {
                    Toast.makeText(SignInActivity.this, getString(R.string.error_account_verification), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthorizationResponse> call, Throwable t) {
                dialog.dismiss();
                //Log.e("tag", t.getMessage().toString());
            }
        });


    }

    private void generateOtp() {
        dialog = new ProgressDialog(SignInActivity.this);
        dialog.setMessage(getString(R.string.info_generating_otp));
        dialog.show();
        JsonObject param = new JsonObject();
        param.addProperty("phone_number", "+" + selectedCountryCode + edtMobile.getText().toString().trim());
        mainAPIInterface.getOtpforUserVerificaiton(param).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    //response.body();
                    verifyUserOtp();
                } else {
                    Toast.makeText(SignInActivity.this, getString(R.string.info_otp_failed), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                dialog.dismiss();
                ////Log.e("tag", t.getMessage().toString());
            }
        });
    }

    private void verifyUserOtp() {
        String phNoWithCode = "+" + selectedCountryCode + edtMobile.getText().toString().trim();
        Intent intent = new Intent(SignInActivity.this, VerifyOtpActivity.class);
        intent.putExtra("phone_number", phNoWithCode);
        startActivityForResult(intent, 121);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 121) {
            if (resultCode == Activity.RESULT_OK) {
                getSignInDetails();
            }
        }
    }

    private void getSignInDetails() {
        dialog = new ProgressDialog(SignInActivity.this);
        dialog.setMessage(getString(R.string.info_verifying_user));
        dialog.show();
        String phNumber = edtMobile.getText().toString().trim();
        String vaultValue = "bearer " + DataVaultManager.getInstance(SignInActivity.this).getVaultValue(KEY_ACCESSTOKEN);
        String phWithDominica = mDominicaAreaCode + phNumber;

        mainAPIInterface.getLoginDetails(Long.parseLong(phWithDominica), Integer.parseInt(selectedCountryCode), vaultValue).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dialog.dismiss();
                JsonObject body = response.body();
                if (response.isSuccessful()) {
                    try {
                        JSONObject mPrev = new JSONObject(body.toString());
                        if (mPrev.getString("message").equalsIgnoreCase("success")) {
                            String jsonUserDetails = mPrev.toString();
                            DataVaultManager.getInstance(SignInActivity.this).saveUserDetails(jsonUserDetails);
                            try {
                                JSONObject obj = new JSONObject(jsonUserDetails);
                                JSONObject jsonObject = obj.getJSONObject(AppoConstants.RESULT);
                                mUserId = jsonObject.getString(AppoConstants.ID);
                                setType(mType);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(SignInActivity.this, "login failed", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
                // Log.e("tag", t.getMessage().toString());
            }
        });
    }

    private void setType(int mType) {
        Intent mIntent = null;
        switch (mType) {
            case 1:
                mIntent = new Intent(SignInActivity.this, AddMoneyToWallet.class);

                break;
            case 2:
                mIntent = new Intent(SignInActivity.this, MobileRechargeActivity.class);
                break;
            case 3:
                mIntent = new Intent(SignInActivity.this, P2PTransferActivity.class);
                break;

        }
        mIntent.putExtra(AppoConstants.WHERE, mType);
        startActivity(mIntent);
        finish();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        String userLanguage = DataVaultManager.getInstance(AppoPayApplication.getInstance()).getVaultValue(KEY_USER_LANGUAGE);
        if (StringUtils.isEmpty(userLanguage)) {
            userLanguage = "en";
        }
        super.attachBaseContext(MyContextWrapper.wrap(newBase, userLanguage));
    }

    @Override
    public void onAreaSelected(int pos) {
        if (mAreaDialog != null) {
            mAreaDialog.dismiss();
        }
        mDominicaAreaCode = mAreaList.get(pos);
        tvAreaCodeDo.setText("Area Code : " + mDominicaAreaCode);
    }
}
