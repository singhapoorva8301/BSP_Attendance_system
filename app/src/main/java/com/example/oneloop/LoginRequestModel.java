package com.example.oneloop;

import com.google.gson.annotations.SerializedName;

public class LoginRequestModel {
    @SerializedName("mobile_no")
    public String mobile_no;
    @SerializedName("password")
    public String password;

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    LoginRequestModel(String mobile_no, String password) {
        this.mobile_no = mobile_no;
        this.password = password;
    }
}
