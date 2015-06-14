package com.ckt.shrimp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.ckt.shrimp.utils.Log;

/**
 * Created by ckt on 06/03/15.
 *
 * 2015.06.09
 * optimize the BookInfoDataBaseHelper, contain string, the function, the container of key values.
 */

/** The construction of Table
 * ------Field-------------type-----------full size-----------e.g value-------------------------
 * |    _id            |  integer       |   infinite   |    1
 * |    isbn_id        |  varchar       |    16        |    20480692
 ... ...
 * ---------------------------------------------------------------------------------------------
 */

public class BookInfoDataBaseHelper extends SQLiteOpenHelper {

    private Context mContext;
    private static final int DATABASE_VERSION = 1;
    //private static final String DATABASE_NAME = "wosaosao";

    private static final String SQL_TABLE_BOOK = "create table books ("
            + "_id integer primary key autoincrement, "
            + "id varchar(16) unique not NULL, "//book's id, not isbn.
            + "title nvarchar(60), "
            + "subtitle nvarchar(60), "
            + "author nvarchar(60), "
            + "publisher nvarchar(60), "
            + "pubdate  varchar(12), "//2012-08-06, varchar?? or date?
            + "isbn13  varchar(20), "//978 71....
            + "price float(7), "

            + "category nvarchar(20), "//综合， 测试，研发...
            + "category_id  varchar(16), "//CKT-CD ZH-0001
            + "bought_date  varchar(12), " //varchar?? or date?
            + "applicant_id varchar(10) ,"//N222xxxx
            + "applicant_name  nvarchar(20), "
            + "applicant_email varchar(30), "
            + "applicant_dep  nvarchar(20), "
            + "actual_price float(7), "  //is float or varchar?

            + "borrower_id varchar(10), "
            + "borrower_name  nvarchar(20), "
            + "borrower_email varchar(20), "
            + "borrower_dep  nvarchar(20), "
            + "borrowed_date  varchar(12), " //varchar?? or date?

            + "pages  integer(5), "
            + "rating  float(5), " //rate is float ? e.g 5.3,  8.6
            + "tags  nvarchar(100), "
            //+ "author_info nvarchar(200), "//maybe too large, not used.
            //+ "content  nvarchar(1000), " //maybe too large, not used.
            //+ "summary  nvarchar(1000), " //maybe too large, not used.
            + "image   blob)"; //image: bitmap for book.

    //not used
    /*private String SQL_TABLE_BORROW = "create table borrow (_id integer primary key autoincrement, " +
            "book_id varchar(8)," +
            "staff_id varchar(6)," +
            "library_date varchar(16)," +
            "expire_time varchar(16))";*/

    private static final String SQL_TABLE_STAFF = "create table staffs ("
            + "_id integer primary key autoincrement, "
            + "staff_id varchar(10) unique not NULL, "
            + "staff_name nvarchar(20), "
            + "staff_email varchar(30), "
            + "staff_dep nvarchar(20))";

    public BookInfoDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        Log.e(this, "BookInfoDataBaseHelper: name = " + name);
        mContext = context;
    }

    public BookInfoDataBaseHelper(Context context) {
        super(context, InfoContents.DATABASE_NAME, null, DATABASE_VERSION);

        Log.e(this, "BookInfoDataBaseHelper: name = " + InfoContents.DATABASE_NAME);
        //Log.e(this, android.util.Log.getStackTraceString( new Throwable()));//debug stack
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    Log.e(this, "onCreate db = " + db
    +"\n SQL_TABLE_BOOK = " + SQL_TABLE_BOOK
    +"\n SQL_TABLE_STAFF = " + SQL_TABLE_STAFF +"\n");

        try {
            db.execSQL(SQL_TABLE_BOOK);
            db.execSQL(SQL_TABLE_STAFF);
        }catch (Exception exp) {
            Log.e(this, "Error, Create Table books, staffs, tables are exist!!!");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
           // super.onUpgrade(db,oldVersion,newVersion);
    }
}
