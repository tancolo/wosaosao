package com.ckt.shrimp.utils;

import android.content.ContentProvider;
import android.content.ContentUris;
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
        Cursor cursor = null;
        switch (matcher.match(uri)){
            case BOOK:
               cursor = bookHelper.getReadableDatabase().query("book",columns,s,args,null,null,null,null);
            break;
            case  STAFF :
                cursor = bookHelper.getReadableDatabase().query("staff",columns,s,args,null,null,null,null);
            break;
            default: return  null;
        }
         return  cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        Uri mUri = null;
        long id  = 0;
        switch (matcher.match(uri)){
            case BOOK:
             id = bookHelper.getWritableDatabase().insert("book","_id",contentValues);
            break;
            case  STAFF :
            id =  bookHelper.getWritableDatabase().insert("staff","_id",contentValues);
            break;
            default: return  null;
        }
        if(id != 0){
            return ContentUris.withAppendedId(uri,id);
        }else {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int id = 0;
        switch (matcher.match(uri)){
            case BOOK:
              id = bookHelper.getWritableDatabase().delete("book",s,strings);
            break;
            case  STAFF :
              id = bookHelper.getWritableDatabase().delete("staff",s,strings);
            break;
            case  BORROW :
              id = bookHelper.getWritableDatabase().delete("borrow",s,strings);
            break;
            default: return -1;
        }
        return id;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int id;
        switch (matcher.match(uri)){
            case BOOK:
               id = bookHelper.getWritableDatabase().update("book",contentValues,s,strings);
            break;
            case  STAFF :
                id = bookHelper.getWritableDatabase().update("staff",contentValues,s,strings);
            break;
            default: return -1;
        }
        return id;
    }
}
