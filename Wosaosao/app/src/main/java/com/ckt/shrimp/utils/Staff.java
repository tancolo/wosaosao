package com.ckt.shrimp.utils;

/**
 * Created by ckt on 09/03/15.
 */
public class Staff {

    private String staffName = null;
    private String staffId = null;
    private String staffDepartment = null;
    private String staffEmail = null;


    public Staff() {
        this.staffName = "";
        this.staffId = "";
        this.staffDepartment = "";
        this.staffEmail = "";
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffDepartment() {
        return staffDepartment;
    }

    public void setStaffDepartment(String staffDepartment) {
        this.staffDepartment = staffDepartment;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staffName='" + staffName + '\'' +
                ", staffId='" + staffId + '\'' +
                ", staffDepartment='" + staffDepartment + '\'' +
                ", staffEmail='" + staffEmail + '\'' +
                '}';
    }
}
