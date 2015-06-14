package com.ckt.shrimp.controller;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.ckt.shrimp.database.BookInfoDataBaseHelper;
import com.ckt.shrimp.database.InfoContents;
import com.ckt.shrimp.utils.Log;


/**
 * Created by shrimpcolo on 2015/6/13.
 */
public class SaoGlobal extends ContextWrapper{
    //private static final  String TAG = "SaoGlobal";

    private Context mContext;
    private static SaoGlobal sMe;
    private static BookInfoDataBaseHelper sBookHelper = null;
    private static SQLiteDatabase mSqliteWritableDatabase = null;
    BookController mBookController;


    public SaoGlobal(Context context) {
        super(context);
        Log.e(this, "SaoGlobal constructor!");
        mContext = context;
        sMe = this;
    }

    public void onCreate() {
        Log.e(this, "onCreate... sBookHelper = " + sBookHelper);

        //new an instance of BookInfoDataBaseHelper if it's not exist.
        if (sBookHelper == null) {
            sBookHelper = new BookInfoDataBaseHelper(mContext);

            //Only the database created or opened if you call the function getReadableDatabase or getWritableDatabase.
            //Here, we need it to create the database. And of cause, it's created only once.
            mSqliteWritableDatabase = sBookHelper.getWritableDatabase();
        }

        //init BookController
        mBookController.init(this);

        //init StaffController
        //not implement.

    }

    public static SQLiteDatabase getInstanceWritableDatabase() {
        if (mSqliteWritableDatabase == null) {
            throw new IllegalStateException("No SQLiteDatabase here!");
        }

        return mSqliteWritableDatabase;
    }

    public static SaoGlobal getInstance() {
        if (sMe == null) {
            throw new IllegalStateException("No SaoGlobal here!");
        }

        return sMe;
    }

    public static BookInfoDataBaseHelper getInstanceBaseHelper() {
        if (sBookHelper == null) {
            throw new IllegalStateException("No BookInfoDataBaseHelper here!");
        }

        return sBookHelper;
    }

    public Context getContext() {
        if (mContext != null) {
            return mContext;
        }else {
            return  null;
        }
    }



}
