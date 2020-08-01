package com.example.oneloop;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;


import android.location.LocationManager;
import android.os.Bundle;


import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;



import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;



import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;


public class ScanAndSubmit extends AppCompatActivity {


    String mobile_no;
    Double lat;
    Double lon;

    SurfaceView surfaceView;
    LocationManager locationManager;

    TextView txtBarcodeValue;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;


    private static final int REQUEST_LOCATION = 1;
    // The minimum distance to change Updates in meters


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.startCamera();

        setContentView(R.layout.activity_scan_and_submit);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        txtBarcodeValue = (TextView) findViewById(R.id.textBarCodeValue);
        Log.d("S; ", surfaceView.toString());
        this.mobile_no = getIntent().getExtras().getString("GUARD_MOBILE_NO");
//        if (ActivityCompat.checkSelfPermission(
//                ScanAndSubmit.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                ScanAndSubmit.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions( this,
//                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//        }
//        this.locationManager  = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        if (!this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            OnGPS();
//        } else {
//            getLocation();
//        }



        // call method to scan barcode

        // on success call API for punching details

    }


    public void onMarkAttendance(View v) {






//        MarkAttendanceModel markAttendanceModel = new MarkAttendanceModel(this.mobile_no, this.);
//        UserService userService = RetrofitClientService.getRetrofit().create(UserService.class);
//
//        Call<RegisterUserResponseModel> markUserResponseModelCall = userService.markAttendance(markAttendanceModel);
//
//
//        markUserResponseModelCall.enqueue(new Callback<RegisterUserResponseModel>() {
//            @Override
//            public void onResponse(Call<RegisterUserResponseModel> call, Response<RegisterUserResponseModel> response) {
//                Log.d("Response", response.body().message);
////                Toast.makeText(ScanAndSubmit.this, response.body().message, Toast.LENGTH_LONG).show();
//                AlertDialog.Builder builder = new AlertDialog.Builder(ScanAndSubmit.this);
//                builder.setMessage(response.body().message);
//                AlertDialog dialog = builder.create();
//                dialog.show();
//
//            }
//
//            @Override
//            public void onFailure(Call<RegisterUserResponseModel> call, Throwable t) {
//
//            }
//        });

    }

    public void initializeBarcodeReading() {



        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.TEXT | Barcode.QR_CODE)
                .build();





        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .build();
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScanAndSubmit.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());

                    } else {
                        ActivityCompat.requestPermissions(ScanAndSubmit.this, new
                                String[]{Manifest.permission.CAMERA}, 201);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });





        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
//                    String text = barcodes.valueAt(0).rawValue;
//                    Toast.makeText(ScanAndSubmit.this, text, Toast.LENGTH_LONG).show();
                    txtBarcodeValue.post(new Runnable() {
                        @Override
                        public void run() {

                            if (barcodes.valueAt(0).rawValue != null) {
                                txtBarcodeValue.removeCallbacks(null);
                                String text = barcodes.valueAt(0).rawValue;
//                                Toast.makeText(ScanAndSubmit.this, text, Toast.LENGTH_LONG).show();
                                if (text.contains("@")) {
                                    String guard_mobile_no = getIntent().getExtras().getString("GUARD_MOBILE_NO");
                                    // new intent to display info activity
                                    Intent detailsIntent = new Intent(ScanAndSubmit.this, DetailsActivity.class);
                                    detailsIntent.putExtra("DATA_STRING", text);
                                    detailsIntent.putExtra("GUARD_MOBILE", guard_mobile_no);
                                    startActivity(detailsIntent);
                                }


                            } else {
                                Toast.makeText(ScanAndSubmit.this, "df", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }



    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.initializeBarcodeReading();

        super.onPause();
    }
}