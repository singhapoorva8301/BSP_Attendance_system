package com.example.oneloop;

import com.google.gson.annotations.SerializedName;

public class PingResponseModel {

    @SerializedName("status_code")
    public Integer status_code;

    @SerializedName("message")
    public  String message;

    @SerializedName("is_success")
    public  Boolean is_success;

    public void setIs_success(Boolean is_success) {
        this.is_success = is_success;
    }

    public Boolean getIs_success() {
        return is_success;
    }

    public String getMessage() {
        return  message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void  setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }
}
