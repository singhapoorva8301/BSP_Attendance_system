package com.example.oneloop;

import com.google.gson.annotations.SerializedName;

public class RegisterUserRequestModel {
    @SerializedName("name")
    public String name;

    @SerializedName("mobile_no")
    public String mobile_no;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public RegisterUserRequestModel(String name, String mobile_no) {
        this.mobile_no = mobile_no;
        this.name = name;
    }
}
