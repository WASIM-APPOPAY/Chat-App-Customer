package com.stuffrs.newappopay.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.Utils.Constants;
import com.stuffrs.newappopay.views.MyEditText;
import com.stuffrs.newappopay.views.MyTextView;
import com.stuffrs.newappopay.views.MyTextViewBoldItalic;

import java.util.Locale;

public class NumberActivity extends AppCompatActivity {
    private AlertDialog dialogEdit;
    private MyEditText edtMobile;
    private MyTextView tvSent;
    private CountryCodePicker edtCustomerCountryCode;
    private String selectedCountryCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        edtCustomerCountryCode = findViewById(R.id.edtCustomerCountryCode);
        edtMobile = (MyEditText) findViewById(R.id.edtMobile);
        tvSent = (MyTextView) findViewById(R.id.tvSent);
        selectedCountryCode = edtCustomerCountryCode.getSelectedCountryCode();
        edtCustomerCountryCode.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                selectedCountryCode = edtCustomerCountryCode.getSelectedCountryCode();
            }
        });
        tvSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(selectedCountryCode)) {
                    Toast.makeText(NumberActivity.this, "Select country code", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(edtMobile.getText().toString().trim())) {
                    Toast.makeText(NumberActivity.this, "Enter mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }
                showEdit();


            }
        });
    }

    public void showEdit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.verify_layout, null);
        MyTextViewBoldItalic btnEdit = dialogLayout.findViewById(R.id.btnEdit);
        MyTextViewBoldItalic btnOk = dialogLayout.findViewById(R.id.btnOk);
        MyTextView tvMobileNumber = dialogLayout.findViewById(R.id.tvMobileNumber);
        tvMobileNumber.setText("+" + selectedCountryCode + " " + edtMobile.getText().toString().trim());
        builder.setView(dialogLayout);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtMobile.requestFocus();
                dialogEdit.dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextScreen();
            }
        });
        dialogEdit = builder.create();
        dialogEdit.setCanceledOnTouchOutside(false);
        dialogEdit.show();

    }

    private void nextScreen() {
        dialogEdit.dismiss();
        Intent intent = new Intent(NumberActivity.this, VerificationActivity.class);
        intent.putExtra(Constants.AREACODE, selectedCountryCode);
        intent.putExtra(Constants.MOBILENUMBER, edtMobile.getText().toString().trim());
        startActivity(intent);
        finish();

    }
}