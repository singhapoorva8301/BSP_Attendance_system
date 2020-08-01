package com.example.oneloop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    String name;
    String mobile_number;
    String guard_mobile_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle extraStringIntent = getIntent().getExtras();
        String extraVal = extraStringIntent.getString("DATA_STRING");
        String name = extraVal.split(",")[2];
        String mobile_no = extraVal.split(",")[3];
        String unique_id = extraVal.split(",")[4];
        TextView nameTextView = (TextView) findViewById(R.id.name);
        nameTextView.setText(name);
        TextView mobileTextView = (TextView) findViewById(R.id.mobile_number);
        mobileTextView.setText(mobile_no);

        TextView uniqueNumberTextView = (TextView) findViewById(R.id.unique_number);
        uniqueNumberTextView.setText(unique_id);
        this.mobile_number = mobile_no;
        this.name = name;
        this.guard_mobile_number = extraStringIntent.getString("GUARD_MOBILE");

    }

    public void onClickEntry(View v) {
        // Call API to mark entry

        UserService userService = RetrofitClientService.getRetrofit().create(UserService.class);
        MarkAttendanceModel markAttendanceModel = new MarkAttendanceModel(this.mobile_number, this.guard_mobile_number);
        Call<RegisterUserResponseModel> markAttendanceResponse = userService.markCheckInAttendance(markAttendanceModel);

        markAttendanceResponse.enqueue(new Callback<RegisterUserResponseModel>() {
            @Override
            public void onResponse(Call<RegisterUserResponseModel> call, Response<RegisterUserResponseModel> response) {
                try {
                    if (response.body().is_success == true) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                        builder.setMessage(response.body().message);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                } catch (Exception e) {
                    //
                    Toast.makeText(DetailsActivity.this, "Some internal server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterUserResponseModel> call, Throwable t) {

            }
        });

    }

    public void onClickExit(View v) {
        // Call API to mark exit

        UserService userService = RetrofitClientService.getRetrofit().create(UserService.class);
        MarkAttendanceModel markAttendanceModel = new MarkAttendanceModel(this.mobile_number, this.guard_mobile_number);
        Call<RegisterUserResponseModel> markAttendanceResponse = userService.markCheckOutAttendance(markAttendanceModel);

        markAttendanceResponse.enqueue(new Callback<RegisterUserResponseModel>() {
            @Override
            public void onResponse(Call<RegisterUserResponseModel> call, Response<RegisterUserResponseModel> response) {
                try {
                    if (response.body().is_success == true) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);
                        builder.setMessage(response.body().message);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                } catch (Exception e) {
                    //
                    Toast.makeText(DetailsActivity.this, "Some internal server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterUserResponseModel> call, Throwable t) {

            }
        });
    }
}