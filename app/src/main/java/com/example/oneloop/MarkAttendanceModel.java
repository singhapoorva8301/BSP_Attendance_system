package com.example.oneloop;

import com.google.gson.annotations.SerializedName;

public class MarkAttendanceModel {

    @SerializedName("mobile_no")
    public String mobile_no;

    @SerializedName("guard_mobile_no")
    public String guard_mobile_no;


    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getGuard_mobile_no() {
        return guard_mobile_no;
    }

    public void setGuard_mobile_no(String guard_mobile_no) {
        this.guard_mobile_no = guard_mobile_no;
    }

    public  MarkAttendanceModel(String mobile_no, String guard_mobile_no) {
        this.mobile_no = mobile_no;
        this.guard_mobile_no = guard_mobile_no;
    }
}
