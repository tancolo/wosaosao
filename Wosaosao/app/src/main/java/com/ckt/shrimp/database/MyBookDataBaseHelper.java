package com.ckt.shrimp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by ckt on 06/03/15.
 */
public class MyBookDataBaseHelper extends SQLiteOpenHelper {

    private String TABLE_SQL_BOOK = "create table book (_id integer primary key autoincrement,"+
                                    "book_id varchar(8) unique not NULL," +
                                    "category varchar(8), " +
                                    "title varchar(30), "+
                                    "category_id  varchar(16), " +
                                    "author varchar(16), "+
                                    "bought_date  varchar(12), " +
                                    "applicant_dep  varchar(12), " +
                                    "applicant  varchar(8), " +
                                    "actual_price varchar(7)," +
                                    "borrower_dep  varchar(12), " +
                                    "borrower  varchar(12), " +
                                    "borrowing_date  varchar(12), " +
                                    "applicant_id varchar(6) ,"+
                                    "applicant_email varchar(20) ,"+
                                    "borrower_id varchar(6),"+
                                    "borrower_email varchar(20),"+
                                    "subtitle varchar(30), "+
                                    "author_info varchar(100), "+
                                    "publisher  varchar(30), "+
                                    "publish_date  datetime(16), "+
                                    "isbn  varchar(20)," +
                                    "page  integer(5), "+
                                    "rate  integer(1)," +
                                    "tag  varchar(30), " +
                                    "content  varchar(300), " +
                                    "summary  varchar(100), " +
                                    "price float(7),"+
                                    "bitmap  varchar(100))";

    /*private String TABLE_SQL_BORROW = "create table borrow (_id integer primary key autoincrement, " +
            "book_id varchar(8)," +
            "staff_id varchar(6)," +
            "library_date varchar(16)," +
            "expire_time varchar(16))";*/

    private String TABLE_SQL_STAFF = "create table staff (_id integer primary key autoincrement, "+
                                        "name varchar(8)," +
                                        "staff_department varchar(8)," +
                                        "staff_email varchar(20) unique,"+
                                        "staff_id varchar(6) unique not NULL)";
    private Context mContext;
    public MyBookDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
          db.execSQL(TABLE_SQL_BOOK);
          db.execSQL(TABLE_SQL_STAFF);
          //db.execSQL(TABLE_SQL_BORROW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           // super.onUpgrade(db,oldVersion,newVersion);
    }
}
