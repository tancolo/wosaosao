package com.ckt.shrimp.utils;

import android.util.Log;

/**
 * Created by ckt on 3/12/15.
 * the staff info only contain 4 parts, like this.
 * 1) N22288 --> staff id
 * 2) 张三司  --> staff name
 * 3) sansi.zhang@xxxxxx.com  --> staff email
 * 4) SP  --> staff department
 * The format is only one line: N22288, 张三司, sansi.zhang@xxxxxx.com, SP
 */
public class ParseAndWriteInfo {

    //private static final int RETURN_ERROR = -1;
    //private static final int RETURN_OK = 1;
    private static final String TAG = "ParseAndWriteInfo";

    public static int parseStaffInfo(String staffInfo, Book tBookWrap, Staff tStaff) {
        if (staffInfo == null || staffInfo.isEmpty())
            return BookUtil.RETURN_ERROR;
        String[] split = staffInfo.split("[,]");
        //log("split[0]: " + split[0]);
        log("DEBUG split.length = " + split.length);
        if (split.length < 2 ) {
            return BookUtil.RETURN_ERROR;
        }

        try {
            if (tBookWrap != null) {
                //Book tBookWrap = new Book();
                //get the values from staffInfo use "," to split.
                tBookWrap.setBookBoughtStaffId(split[0]);
                tBookWrap.setBookApplicant(split[1]);
                tBookWrap.setBookBoughtStaffEmail(split[2]);
                tBookWrap.setBookApplicantDep(split[3]);

                //dump staff info.
                log(tBookWrap.getBookBoughtStaffId() + ", " + tBookWrap.getBookApplicant() + ", " + tBookWrap.getBookBoughtStaffEmail()
                        + ", " + tBookWrap.getBookApplicantDep());
                //end dump
                //return tBookWrap;
            } else if (tStaff != null) {
                tStaff.setId(split[0]);
                tStaff.setName(split[1]);
                tStaff.setEmail(split[2]);
                tStaff.setDepartment(split[3]);

                //dump staff info.
                log(tStaff.getId() + ", " + tStaff.getName() + ", " + tStaff.getEmail()
                        + ", " + tStaff.getDepartment());
                //end dump
                //return tStaff;
            }else {
                //do nothing
            }
        } catch (Exception e) {
            Log.e(TAG,"Invalid format in staffInfo '"+staffInfo+"'");
            return BookUtil.RETURN_ERROR;
        }
        return BookUtil.RETURN_OK;
    }

    public static void log(String str) {
        Log.e(TAG, str);
    }
}
