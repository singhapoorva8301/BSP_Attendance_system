package com.example.oneloop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    String name;
    String designation;
    String mobile_no;

    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        telephonyManager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
            return;
        }
        setContentView(R.layout.activity_register_acitivity);


    }



    public void onClickRegisterBtn(View view) {
        EditText nameField =  (EditText) findViewById(R.id.nameInputEditText);

        EditText designationField =  (EditText) findViewById(R.id.designationInputText);


        EditText mobileField = (EditText) findViewById(R.id.mobileNumber);
        this.name = nameField.getText().toString();
        this.designation = designationField.getText().toString();
        this.mobile_no = mobileField.getText().toString();



        // Register User
        UserService userService = RetrofitClientService.getRetrofit().create(UserService.class);


        // Call API to create User
        RegisterUserRequestModel registerUserRequestModel = new RegisterUserRequestModel(this.name, this.mobile_no);
        Call<RegisterUserResponseModel> registerUserResponseModelCall = userService.registerUser(registerUserRequestModel);

        registerUserResponseModelCall.enqueue(new Callback<RegisterUserResponseModel>() {
            @Override
            public void onResponse(Call<RegisterUserResponseModel> call, Response<RegisterUserResponseModel> response) {
                Log.d("Response", response.body().message);

                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage(response.body().message);
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onFailure(Call<RegisterUserResponseModel> call, Throwable t) {
                Log.getStackTraceString(t);
                Log.d("some", "error", t);
                Toast.makeText(RegisterActivity.this, "No Success", Toast.LENGTH_LONG).show();
            }
        });



//        Toast.makeText(RegisterActivity.this, this.name + " " + this.designation, Toast.LENGTH_LONG).show();

    }


}