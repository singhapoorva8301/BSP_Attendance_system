package com.example.oneloop;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String device_id = "Android ";
    TelephonyManager telephonyManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.deviceID();
        setContentView(R.layout.activity_main);

        Log.d(device_id, "Started main activity");


        // Ping Server for handshake
        PingService pingService = RetrofitClientService.getRetrofit().create(PingService.class);

        // Call ping route

        Call<PingResponseModel> pingResponseModelCall = pingService.pingServer();


        pingResponseModelCall.enqueue(new Callback<PingResponseModel>() {
            @Override
            public void onResponse(Call<PingResponseModel> call, Response<PingResponseModel> response) {
                try {
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("Response: ", response.body().getMessage());
                }
                catch (NullPointerException e) {
                    Toast.makeText(MainActivity.this, "No net", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PingResponseModel> call, Throwable t) {
                Log.getStackTraceString(t);
                Log.d("some", "error", t);
                Toast.makeText(MainActivity.this, "No Success", Toast.LENGTH_LONG).show();
            }
        });

    }


    public void onClickScanAndSubmit(View view) {
        Intent scanAndSubmitIntent = new Intent(MainActivity.this, ScanAndSubmit.class);
        startActivity(scanAndSubmitIntent);
    }

    public void onclickSignIn(View view) {
        Intent signInIntent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(signInIntent);
    }


    public void onClickRegister(View view) {
        Intent registerActivity = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(registerActivity);
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void deviceID() {
        telephonyManager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
            return;
        }
        String phoneNumber = "0000000000";
        try
        {
            phoneNumber = telephonyManager.getLine1Number();
        }
        catch(NullPointerException ex)
        {

        }
        if(phoneNumber.equals("")){
            phoneNumber = telephonyManager.getVoiceMailNumber();
        }
        if (phoneNumber.equals("")) {
            phoneNumber = this.getNoFromWatsApp();
        }

        this.device_id = phoneNumber;
//        Toast.makeText(MainActivity.this, this.device_id, Toast.LENGTH_LONG).show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 101);
                        return;
                    }
                    String phoneNumber = "0000000000";
                    try
                    {
                        phoneNumber = telephonyManager.getLine1Number();
                    }
                    catch(NullPointerException ex)
                    {

                    }
                    if(phoneNumber.equals("")){
                        phoneNumber = telephonyManager.getVoiceMailNumber();
                    }
                    if (phoneNumber.equals("")) {
                        phoneNumber = this.getNoFromWatsApp();
                    }
                    Toast.makeText(MainActivity.this,phoneNumber,Toast.LENGTH_LONG).show();
                    this.device_id = phoneNumber;
                } else {
                    Toast.makeText(MainActivity.this,"Without permission we check",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private String getNoFromWatsApp(){
        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccounts();
        String phoneNumber = "";

        for (Account ac : accounts) {
            String acname = ac.name;
            String actype = ac.type;
            // Take your time to look at all available accounts
            if(actype.equals("com.whatsapp")) {
                phoneNumber = ac.name;
            }
        }
        return phoneNumber;
    }
//    public void startService(View view) {
//        startService(new Intent(getBaseContext(), MyService.class));
//    }
//
//    public void stopService(View view) {
//        stopService(new Intent(getBaseContext(), MyService.class));
//    }
}