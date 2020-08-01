package com.example.oneloop;

import com.google.gson.annotations.SerializedName;

public class SignInResponseModel {
    @SerializedName("status_code")
    public Integer status_code;
    @SerializedName("message")
    public String message;
    @SerializedName("is_success")
    public Boolean is_success;
    @SerializedName("role")
    public String role;

    @SerializedName("mobile_no")
    public String mobile_no;


    public Boolean getIs_success() {
        return is_success;
    }

    public void setIs_success(Boolean is_success) {
        this.is_success = is_success;
    }

    public Integer getStatus_code() {
        return status_code;
    }

    public void setStatus_code(Integer status_code) {
        this.status_code = status_code;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }


    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
}
