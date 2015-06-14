package com.ckt.shrimp.export2file;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.ckt.shrimp.database.InfoContents;

/**
 * Created by ckt on 3/9/15.
 */

public class TestExportWikiProvider extends ContentProvider {

    public static final Uri CONTENT_URI  = Uri.parse("content://com.ckt.shrimp.export2file.TestExportWikiProvider");
    private static final String AUTHORIY = "com.ckt.shrimp.export2file.TestExportWikiProvider";
    private static final String DATABASE_NAME = "allbooksinfo.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TAG = "TestExportWikiProvider";
    private static final boolean DBG = true;

    private static final String BOOKS_TABLE_NAME = "booksinfo";
    private DatabaseHelper databaseHelper = null;//

    private static class DatabaseHelper extends SQLiteOpenHelper {
        private Context mContext;

        /**
         * DatabaseHelper helper class for creating books and stuff tables into a database.
         *
         * @param context of the user.
         */
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mContext = context;
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            if (DBG) log("dbh.onCreate:+ db=" + db);
            createBooksInfoTable(db);
            if (DBG) log("dbh.onCreate:- db=" + db);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            if (DBG) log("dbh.onOpen:+ db=" + db);
            try {
                // Try to access the table and create it if "no such table"
                db.query(BOOKS_TABLE_NAME, null, null, null, null, null, null);
                if (DBG) log("dbh.onOpen: ok, queried table=" + BOOKS_TABLE_NAME);
            } catch (SQLiteException e) {
                loge("Exception " + BOOKS_TABLE_NAME + "e=" + e);
                if (e.getMessage().startsWith("no such table")) {
                    createBooksInfoTable(db);
                }
            }
            if (DBG) log("dbh.onOpen:- db=" + db);
        }

        private void createBooksInfoTable(SQLiteDatabase db) {
            if (DBG) log("dbh.createBooksInfoTable:+");
            db.execSQL("CREATE TABLE " + BOOKS_TABLE_NAME + "("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + InfoContents.BOOK_ID + " TEXT NOT NULL,"
                    + InfoContents.BOOK_ISBN + " TEXT NOT NULL, "
                    + InfoContents.BOOK_TITLE + " TEXT NOT NULL, "
                    + InfoContents.BOOK_SUBTITLE + " TEXT, "
                    + InfoContents.BOOK_AUTHOR + " TEXT NOT NULL, "
                    + InfoContents.BOOK_PUBLISHER + " TEXT NOT NULL, "
                    + InfoContents.BOOK_PRICE + " TEXT NOT NULL, "
                    + InfoContents.BOOK_CATEGORY + " TEXT NOT NULL, "
                    + InfoContents.BOOK_CATEGORY_ID + " TEXT NOT NULL, "
                    + InfoContents.BOOK_BOUGHT_TIME + "LONG DEFAULT 0, "
                    + InfoContents.BOOK_APPLICANT_DEPARTMENT + " TEXT NOT NULL, "
                    + InfoContents.BOOK_APPLICANT_NAME + " TEXT NOT NULL, "
                    + InfoContents.BOOK_ACTUAL_PRICE_ + " TEXT NOT NULL, "
                    + InfoContents.BOOK_BORROWING_DEP + " TEXT NOT NULL, "
                    + InfoContents.BOOK_BORROWER_NAME + " TEXT NOT NULL, "
                    + InfoContents.BOOK_BORROWING_DATE + " LONG DEFAULT 0 "
                    + ");" );
            if (DBG) log("dbh.createBooksInfoTable:-");
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (DBG) {
                log("dbh.onUpgrade:+ db=" + db + " oldV=" + oldVersion + " newV=" + newVersion);
            }
            //not implementing.
        }
    }//end DatabaseHelper

    @Override
    public boolean onCreate() {
        if (DBG) log("onCreate:+");
        databaseHelper = new DatabaseHelper(getContext());
        if (DBG) log("onCreate:- ret true or false");
        return (databaseHelper != null) ? true : false;
    }

    @Override
    public Cursor query(Uri url, String[] projectionIn, String selection,
                        String[] selectionArgs, String sort) {
        if(DBG)log("query: url = " + url
        +"\n projectionIn = " + projectionIn
        +"\n selection = " + selection
        +"\n selectionArgs = " + selectionArgs
        +"\n sort = " + sort);

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        //qb.setStrict(true); // a little protection from injection attacks
        qb.setTables(BOOKS_TABLE_NAME);

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor ret = null;
        try {
            ret = qb.query(db, projectionIn, selection, selectionArgs, null, null, sort);
        } catch (SQLException e) {
            loge("got exception when querying: " + e);
        }
        if (ret != null)
            ret.setNotificationUri(getContext().getContentResolver(), url);
        return ret;
    }

    @Override
    public String getType(Uri url) {
        //not implement.
        return null;
    }

    @Override
    public Uri insert(Uri url, ContentValues initialValues) {
        //not implements, just for testing

        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        long rowId = db.insert(BOOKS_TABLE_NAME, null, initialValues);
        Uri rowUri = null;
        if (rowId > 0) {
            rowUri = ContentUris.appendId(CONTENT_URI.buildUpon(), rowId).build();
            getContext().getContentResolver().notifyChange(rowUri, null);
            return rowUri;
        }
        throw new SQLException("Failed to insert row into." + rowUri);
    }

    @Override
    public int delete(Uri url, String where, String[] whereArgs) {
        //not implements
        return 0;
    }

    @Override
    public int update(Uri url, ContentValues values, String where, String[] whereArgs) {
        //not implements
        return 0;
    }


    /**
     * Log with debug
     *
     * @param s is string log
     */
    public static void log(String s) {
        Log.d(TAG, s);
    }
    public static void loge(String s) {
        Log.e(TAG, s);
    }
}