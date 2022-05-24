package com.stuffrs.newappopay.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.Utils.Helper;
import com.stuffrs.newappopay.model.User;
import com.stuffrs.newappopay.views.MyEditText;
import com.stuffrs.newappopay.views.MyTextView;

public class BasicInfoActivity extends AppCompatActivity {

    private Helper helper;
    private DatabaseReference usersRef;
    private MyEditText userPhone;
    private User userMe;
    private MyEditText userNameEditFirst;
    private MyEditText userNameEditLast;
    private MyTextView tvSave;
    private String mFirstName, mLastName, mFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new Helper(this);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        usersRef = firebaseDatabase.getReference(Helper.REF_USERS);
        setContentView(R.layout.activity_basic_info);
        userPhone = (MyEditText) findViewById(R.id.userPhone);
        userNameEditFirst = (MyEditText) findViewById(R.id.userNameEditFirst);
        userNameEditLast = (MyEditText) findViewById(R.id.userNameEditLast);
        tvSave = (MyTextView) findViewById(R.id.tvSave);
        userPhone.setEnabled(false);
        userMe = helper.getLoggedInUser();
        setUserDetails();
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify();
            }
        });

    }

    private void verify() {
        mFirstName = userNameEditFirst.getText().toString().trim();
        mLastName = userNameEditLast.getText().toString().trim();
        if (TextUtils.isEmpty(mFirstName)) {
            userNameEditFirst.setError("enter first name");
            userNameEditFirst.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mLastName)) {
            userNameEditLast.setError("enter last name");
            userNameEditLast.requestFocus();
            return;
        }

        mFullName = mFirstName + " " + mLastName;
        userMe.setName(mFullName);
        usersRef.child(userMe.getId()).setValue(userMe);
        helper.setLoggedInUser(userMe);
        Intent mIntent = new Intent(BasicInfoActivity.this, HomeActivity.class);
        startActivity(mIntent);
        BasicInfoActivity.this.finish();


    }

    private void setUserDetails() {
        userPhone.setText(userMe.getId());
    }
}