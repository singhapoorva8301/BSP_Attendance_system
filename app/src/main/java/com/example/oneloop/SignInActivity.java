package com.example.oneloop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    String mobile_no;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }


    public void takeToScanActivity(View view) {
        Intent scanAndSubmitIntent = new Intent(SignInActivity.this, ScanAndSubmit.class);
        scanAndSubmitIntent.putExtra("GUARD_NUMBER", this.mobile_no);
        startActivity(scanAndSubmitIntent);
    }

    public void onClickLogin(View view) {
        EditText mobile_no_text = (EditText) findViewById(R.id.user_mobile);
        this.mobile_no = mobile_no_text.getText().toString();
        EditText password_text = (EditText) findViewById(R.id.editTextTextPassword);
        this.password = password_text.getText().toString();


        UserService userService = RetrofitClientService.getRetrofit().create(UserService.class);

        SignInRequestModel signInRequestModel = new SignInRequestModel(this.mobile_no, this.password);
        Call<SignInResponseModel> signInResponseModelCall = userService.signIn(signInRequestModel);

        signInResponseModelCall.enqueue(new Callback<SignInResponseModel>() {
            @Override
            public void onResponse(Call<SignInResponseModel> call, Response<SignInResponseModel> response) {
                try {
                    Boolean is_success = response.body().getIs_success();
                    String role = response.body().getRole();
                    Log.d("response", response.body().getMessage());
                    if(is_success == true && role.equals("GUARD") ) {
                        Intent scanAndSubmitIntent = new Intent(SignInActivity.this, ScanAndSubmit.class);
                        scanAndSubmitIntent.putExtra("GUARD_MOBILE_NO", response.body().getMobile_no());
                        startActivity(scanAndSubmitIntent);
                    } else {
                        Toast.makeText(SignInActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(SignInActivity.this, "Some internal server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignInResponseModel> call, Throwable t) {
                Log.getStackTraceString(t);
                Log.d("On failure", t.toString());
            }
        });
    }
}