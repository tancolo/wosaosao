package com.ckt.shrimp.controller;

import android.content.Context;

import com.ckt.shrimp.utils.Book;
import com.ckt.shrimp.utils.Staff;

import java.util.List;

/**
 * Created by ckt on 09/03/15.
 */
public class StaffController {
    private Context mContext;
    public StaffController(Context context){

        mContext = context;
    }

    public boolean addEmployee(Staff e){

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
