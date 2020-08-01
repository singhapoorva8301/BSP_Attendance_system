package com.example.oneloop;

import com.google.gson.annotations.SerializedName;

public class SignInRequestModel {
    @SerializedName("mobile_no")
    public String mobile_no;

    @SerializedName("password")
    public String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    SignInRequestModel(String mobile_no, String password) {
        this.mobile_no = mobile_no;
        this.password = password;
    }
}
