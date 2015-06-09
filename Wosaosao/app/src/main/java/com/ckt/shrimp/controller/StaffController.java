package com.ckt.shrimp.controller;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.ckt.shrimp.utils.Book;
import com.ckt.shrimp.utils.BookUtil;
import com.ckt.shrimp.utils.Staff;

import java.util.List;

/**
 * Created by ckt on 09/03/15.
 */
public class StaffController {
    private Context mContext;
    private ContentResolver resolver;
    public StaffController(Context context){

        mContext = context;
        resolver = mContext.getContentResolver();
    }

    public boolean addStaff(Staff e){
        ContentValues v = new ContentValues();
        if (e == null ||"".equals(e.getStaffId()) || "".equals(e.getStaffDepartment()) ||
                "".equals(e.getStaffEmail()) || "".equals(e.getStaffName())){
            return  false;
        }
        v.put("staff_id",e.getStaffId());
        v.put("name",e.getStaffName());
        v.put("staff_department",e.getStaffDepartment());
        v.put("staff_email",e.getStaffEmail());
        Uri uri = resolver.insert(BookUtil.STAFF_URI, v);
        if (uri != null){
            return  true;
        }
        return  false;
    }

    public boolean removeStaff(Staff e){
        return  false;
    }


    public boolean updatStaff(Staff e){
        return  false;
    }


    public Book queryStaff(String staffId){
        return  null;
    }


    public List<Staff> queryAll(){
        return null;
    }
}
