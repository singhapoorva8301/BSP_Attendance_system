package com.example.oneloop;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PingService {
    @GET("/api/ping/")
    Call<PingResponseModel> pingServer();
}
