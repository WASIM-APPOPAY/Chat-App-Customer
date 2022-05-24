package com.stuffrs.newappopay.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.views.MyTextView;

public class SignInActivity extends AppCompatActivity {

    private MyTextView tvSignUp, tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        tvSignUp = findViewById(R.id.signup);
        tvSignIn = findViewById(R.id.tvSignIn);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, NumberActivity.class);
                startActivity(intent);
            }
        });
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });


    }
}