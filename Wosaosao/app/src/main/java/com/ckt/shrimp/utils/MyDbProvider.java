package com.ckt.shrimp.utils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.ckt.shrimp.controller.BookController;
import com.ckt.shrimp.database.MyBookDataBaseHelper;

/**
 * Created by ckt on 09/03/15.
 */
public class MyDbProvider extends ContentProvider{
    private final static int BOOK = 1;
    private final static int STAFF = 2;
    private final static int BORROW = 3;
    private MyBookDataBaseHelper bookHelper;

    @Override
    public boolean onCreate() {
        bookHelper = new MyBookDataBaseHelper(this.getContext(),"saosao",null,1);
        BookController.bookHelper = bookHelper;
        return false;
    }
    private static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        matcher.addURI(BookUtil.bookAuthority,"book",BOOK);
        matcher.addURI(BookUtil.bookAuthority,"staff",STAFF);
        matcher.addURI(BookUtil.bookAuthority,"borrow",BORROW);
    }

    @Override
    public Cursor query(Uri uri, String[] columns, String s, String[] args, String s2) {
        switch (matcher.match(uri)){
            case BOOK:
              return bookHelper.getReadableDatabase().query("book",columns,s,args,null,null,null,null);
            case  STAFF :
              return bookHelper.getReadableDatabase().query("staff",columns,s,args,null,null,null,null);
            case  BORROW :
              return bookHelper.getReadableDatabase().query("borrow",columns,s,args,null,null,null,null);
            default: return  null;
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        switch (matcher.match(uri)){
            case BOOK:
                 bookHelper.getWritableDatabase().insert("book","_id",contentValues);
            case  STAFF :
                bookHelper.getWritableDatabase().insert("staff","_id",contentValues);
            case  BORROW :
                bookHelper.getWritableDatabase().insert("borrow","_id",contentValues);
            default: return  null;
        }
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        switch (matcher.match(uri)){
            case BOOK:
              return   bookHelper.getWritableDatabase().delete("book",s,strings);
            case  STAFF :
               return bookHelper.getWritableDatabase().delete("staff",s,strings);
            case  BORROW :
              return   bookHelper.getWritableDatabase().delete("borrow",s,strings);
            default: return -1;
        }
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        switch (matcher.match(uri)){
            case BOOK:
                return   bookHelper.getWritableDatabase().update("book",contentValues,s,strings);
            case  STAFF :
                return   bookHelper.getWritableDatabase().update("staff",contentValues,s,strings);
            case  BORROW :
                return   bookHelper.getWritableDatabase().update("borrow",contentValues,s,strings);
            default: return -1;
        }
    }
}
