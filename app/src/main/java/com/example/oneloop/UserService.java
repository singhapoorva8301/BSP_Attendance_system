package com.example.oneloop;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface UserService {

    @POST("/api/user/login")
    Call<SignInResponseModel> signIn(@Body SignInRequestModel signInRequestModel);

    @POST("/api/register-user")
    Call<RegisterUserResponseModel> registerUser(@Body RegisterUserRequestModel registerUserRequestModel);

    @POST("/api/attendance/checkin")
    Call<RegisterUserResponseModel> markCheckInAttendance(@Body MarkAttendanceModel markAttendanceModel);

    @PATCH("/api/attendance/checkout")
    Call<RegisterUserResponseModel> markCheckOutAttendance(@Body MarkAttendanceModel markAttendanceModel);


}
