package com.stuffrs.newappopay.stuffers_business.activity.mall;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.stuffrs.newappopay.R;
import com.stuffrs.newappopay.stuffers_business.AppoPayApplication;
import com.stuffrs.newappopay.stuffers_business.adapter.mall.YourAppointmentListAdapter;
import com.stuffrs.newappopay.stuffers_business.api.ApiUtils;
import com.stuffrs.newappopay.stuffers_business.api.MainAPIInterface;
import com.stuffrs.newappopay.stuffers_business.models.output.YourAppointmentOutputModel;
import com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager;
import com.stuffrs.newappopay.stuffers_business.views.MyTextView;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static  com.stuffrs.newappopay.stuffers_business.utils.DataVaultManager.KEY_USER_ID;


/**
 * Created by Sandeep Londhe on 03-02-2019.
 *
 * @Email :  sandeeplondhe54@gmail.com
 * @Author :  https://twitter.com/mesandeeplondhe
 * @Skype :  sandeeplondhe54
 */
public class YourAppointmentsActivity extends AppCompatActivity {

    private MainAPIInterface mainAPIInterface;


    RecyclerView myAppointmentsList;

    YourAppointmentListAdapter yourAppointmentListAdapter;

    private ProgressDialog dialog;

    public ArrayList<YourAppointmentOutputModel.Booking> bookingArrayList;

    LinearLayout ll_appintments_layout;

    MyTextView go_to_homepage;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.your_appointments_activity);

        setupActionBar();

        mainAPIInterface = ApiUtils.getAPIService();

        myAppointmentsList = (RecyclerView) findViewById(R.id.myAppointmentsList);
        go_to_homepage = (MyTextView) findViewById(R.id.go_to_homepage);
        ll_appintments_layout = (LinearLayout) findViewById(R.id.ll_appintments_layout);


        if (AppoPayApplication.isNetworkAvailable(YourAppointmentsActivity.this)) {

            getAllAppointments();

        } else {

            Toast.makeText(YourAppointmentsActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();

        }
    }

    private void setupActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView menu_icon = toolbar.findViewById(R.id.menu_icon);
        menu_icon.setVisibility(View.GONE);

        ImageView appLogo = toolbar.findViewById(R.id.appLogo);
        appLogo.setVisibility(View.GONE);

        TextView toolbarTitle = toolbar.findViewById(R.id.toolbarTitle);
        toolbarTitle.setVisibility(View.VISIBLE);

        toolbarTitle.setText("Your Appointment");


        ActionBar bar = getSupportActionBar();
        bar.setDisplayUseLogoEnabled(false);
        bar.setDisplayShowTitleEnabled(true);
        bar.setDisplayShowHomeEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setHomeButtonEnabled(true);
        bar.setTitle("Your Appointment");

        toolbar.setTitleTextColor(Color.WHITE);
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


    public void getAllAppointments() {

        String strUserId = DataVaultManager.getInstance(AppoPayApplication.getInstance()).getVaultValue(KEY_USER_ID);

        String xAccessToken = "mykey";

        dialog = new ProgressDialog(YourAppointmentsActivity.this);

        dialog.setMessage("Getting All Appointments.");
        dialog.show();

        MultipartBody.Part customer_id_body = MultipartBody.Part.createFormData("customer_id", strUserId);


        mainAPIInterface.getAllYourAppointments(xAccessToken, customer_id_body).enqueue(new Callback<YourAppointmentOutputModel>() {
            @Override
            public void onResponse(Call<YourAppointmentOutputModel> call, Response<YourAppointmentOutputModel> response) {


                if (response.isSuccessful()) {

                    dialog.dismiss();

                    bookingArrayList = response.body().getBooking();


                    if (bookingArrayList.isEmpty()) {

                        myAppointmentsList.setVisibility(View.GONE);
                        ll_appintments_layout.setVisibility(View.VISIBLE);

                        go_to_homepage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                finish();
                            }
                        });


                    } else {

                        myAppointmentsList.setVisibility(View.VISIBLE);
                        ll_appintments_layout.setVisibility(View.GONE);

                        yourAppointmentListAdapter = new YourAppointmentListAdapter(bookingArrayList, YourAppointmentsActivity.this);

                        LinearLayoutManager layoutManager
                                = new LinearLayoutManager(YourAppointmentsActivity.this, RecyclerView.VERTICAL, false);

                        myAppointmentsList.setLayoutManager(layoutManager);

                        myAppointmentsList.setItemAnimator(new DefaultItemAnimator());
                        myAppointmentsList.setAdapter(yourAppointmentListAdapter);

                        yourAppointmentListAdapter.notifyDataSetChanged();


                    }

                }
            }

            @Override
            public void onFailure(Call<YourAppointmentOutputModel> call, Throwable t) {
                dialog.dismiss();
                Log.i("tag", t.getMessage().toString());
            }
        });
    }
}

