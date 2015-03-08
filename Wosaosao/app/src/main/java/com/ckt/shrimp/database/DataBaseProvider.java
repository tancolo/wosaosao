package com.ckt.shrimp.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * Created by ckt on 3/8/15.
 */
public class DataBaseProvider extends ContentProvider {

    private static final String AUTHORIY = "com.ckt.shrimp.database.DataBaseProvider";
    private static final String DATABASE_NAME = "lendbook.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TAG = "DataBaseProvider";
    private static final boolean DBG = true;

    private static final String BOOKS_TABLE_NAME = "booksinfo";
    private static final String STUFF_TABLE_NAME = "stuffinfo";

    private DatabaseHelper databaseHelper;//

    //private static final int URL_
    private static final int URL_BOOKS = 1;
    private static final int URL_STUFF = 2;
    private static final int URL_BOOK_ID = 3;
    private static final int URL_STUFF_ID = 4;
    //private static final int URL_BOOK_ID = 3;
    //private static final int URL_BOOK_ID = 3;

    private static final UriMatcher s_urlMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        s_urlMatcher.addURI(AUTHORIY, "booksinfo", URL_BOOKS);
        s_urlMatcher.addURI(AUTHORIY, "booksinfo/#", URL_BOOK_ID);
        s_urlMatcher.addURI(AUTHORIY, "stuffinfo", URL_STUFF);
        s_urlMatcher.addURI(AUTHORIY, "stuffinfo/#", URL_STUFF_ID);
        //s_urlMatcher.addURI(AUTHORIY, "siminfo", URL_SIMINFO);
    }

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
            createStuffInfoTable(db);
            //initDatabase(db); //not used.
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
            try {
                db.query(STUFF_TABLE_NAME, null, null, null, null, null, null);
                if (DBG) log("dbh.onOpen: ok, queried table=" + STUFF_TABLE_NAME);
            } catch (SQLiteException e) {
                loge("Exception " + STUFF_TABLE_NAME + " e=" + e);
                if (e.getMessage().startsWith("no such table")) {
                    createStuffInfoTable(db);
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
                    + InfoContents.BOOK_PRICE + " TEXT NOT NULL"
                    + ");" );

                    /** refence code
                    + SubscriptionManager.SIM_ID + " INTEGER DEFAULT " + SubscriptionManager.SIM_NOT_INSERTED + ","
                    + SubscriptionManager.DISPLAY_NAME + " TEXT,"
                    + SubscriptionManager.NAME_SOURCE + " INTEGER DEFAULT " + SubscriptionManager.NAME_SOURCE_DEFAULT_SOURCE + ","
                    + SubscriptionManager.COLOR + " INTEGER DEFAULT " + SubscriptionManager.COLOR_DEFAULT + ","
                    + SubscriptionManager.NUMBER + " TEXT,"
                    + SubscriptionManager.DISPLAY_NUMBER_FORMAT + " INTEGER NOT NULL DEFAULT " + SubscriptionManager.DISLPAY_NUMBER_DEFAULT + ","
                    + SubscriptionManager.DATA_ROAMING + " INTEGER DEFAULT " + SubscriptionManager.DATA_ROAMING_DEFAULT + ","
                    + SubscriptionManager.MCC + " INTEGER DEFAULT 0,"
                    + SubscriptionManager.MNC + " INTEGER DEFAULT 0" + ","
                    + SubscriptionManager.SUB_STATE + " INTEGER DEFAULT " + SubscriptionManager.ACTIVE + ","
                    + SubscriptionManager.NETWORK_MODE+ " INTEGER DEFAULT " + SubscriptionManager.DEFAULT_NW_MODE
                    + ");");
                    */
            if (DBG) log("dbh.createBooksInfoTable:-");
        }

        private void createStuffInfoTable(SQLiteDatabase db) {
            if (DBG) log("dbh.createStuffInfoTable:+");
            //STUFF_ID, like this N22xxx
            //STUFF_NAME, chinese name "张三司"
            //STUFF_EMAIL, like this sansi.zhang@xxx.com
            //STUFF_DEPARTMENT, like this SP OR FP
            db.execSQL("CREATE TABLE " + STUFF_TABLE_NAME + "("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + InfoContents.STUFF_ID + " TEXT NOT NULL,"
                    + InfoContents.STUFF_NAME + " TEXT NOT NULL, "
                    + InfoContents.STUFF_EMAIL + " TEXT NOT NULL, "
                    + InfoContents.STUFF_DEPARTMENT + " TEXT NOT NULL "
                    + ");" );

            if (DBG) log("dbh.createStuffInfoTable:-");
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
        if (DBG) log("onCreate:- ret true");
        return true;
    }

    @Override
    public Cursor query(Uri url, String[] projectionIn, String selection,
                        String[] selectionArgs, String sort) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        //qb.setStrict(true); // a little protection from injection attacks
        qb.setTables(BOOKS_TABLE_NAME);

        int match = s_urlMatcher.match(url);
        switch (match) {
            //intentional fall through from above case
            // do nothing
            case URL_BOOKS: {
                break;
            }

            case URL_STUFF: {
                qb.setTables(STUFF_TABLE_NAME);
                break;
            }

            case URL_BOOK_ID: {
                qb.appendWhere("_id = " + url.getPathSegments().get(1));
                break;
            }

            case URL_STUFF_ID: {
                qb.appendWhere("_id = " + url.getPathSegments().get(1));
                break;
            }
        }

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
    public Uri insert(Uri url, ContentValues initialValues)
    {
        Uri result = null;

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int match = s_urlMatcher.match(url);
        boolean notify = false;
        switch (match)
        {
            case URL_BOOKS:
            {
                String subIdString = url.getLastPathSegment();
                try {
                    subId = Long.parseLong(subIdString);
                } catch (NumberFormatException e) {
                    loge("NumberFormatException" + e);
                    return result;
                }
                if (DBG) log("subIdString = " + subIdString + " subId = " + subId);
            }
            //intentional fall through from above case

            case URL_STUFF:
            {
                ContentValues values;
                if (initialValues != null) {
                    values = new ContentValues(initialValues);
                } else {
                    values = new ContentValues();
                }

                values = DatabaseHelper.setDefaultValue(values);

                long rowID = db.insert(CARRIERS_TABLE, null, values);
                if (rowID > 0)
                {
                    result = ContentUris.withAppendedId(Telephony.Carriers.CONTENT_URI, rowID);
                    notify = true;
                }

                if (VDBG) log("inserted " + values.toString() + " rowID = " + rowID);
                break;
            }

            case URL_BOOK_ID:
            {
                String subIdString = url.getLastPathSegment();
                try {
                    subId = Long.parseLong(subIdString);
                } catch (NumberFormatException e) {
                    loge("NumberFormatException" + e);
                    return result;
                }
                if (DBG) log("subIdString = " + subIdString + " subId = " + subId);
                // FIXME use subId in the query
            }
            //intentional fall through from above case

            case URL_STUFF_ID:
            {
                // null out the previous operator
                db.update("carriers", s_currentNullMap, "current IS NOT NULL", null);

                String numeric = initialValues.getAsString("numeric");
                int updated = db.update("carriers", s_currentSetMap,
                        "numeric = '" + numeric + "'", null);

                if (updated > 0)
                {
                    if (VDBG) log("Setting numeric '" + numeric + "' to be the current operator");
                }
                else
                {
                    loge("Failed setting numeric '" + numeric + "' to the current operator");
                }
                break;
            }

            case URL_SIMINFO: {
                long id = db.insert(SIMINFO_TABLE, null, initialValues);
                result = ContentUris.withAppendedId(SubscriptionManager.CONTENT_URI, id);
                break;
            }
        }

        if (notify) {
            getContext().getContentResolver().notifyChange(Telephony.Carriers.CONTENT_URI, null);
        }

        return result;
    }

    @Override
    public int delete(Uri url, String where, String[] whereArgs)
    {
        int count = 0;
        long subId = SubscriptionManager.getDefaultSubId();

        checkPermission();

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = s_urlMatcher.match(url);
        switch (match)
        {
            case URL_TELEPHONY_USING_SUBID:
            {
                String subIdString = url.getLastPathSegment();
                try {
                    subId = Long.parseLong(subIdString);
                } catch (NumberFormatException e) {
                    loge("NumberFormatException" + e);
                    throw new IllegalArgumentException("Invalid subId " + url);
                }
                if (DBG) log("subIdString = " + subIdString + " subId = " + subId);
                // FIXME use subId in query
            }
            //intentional fall through from above case

            case URL_TELEPHONY:
            {
                count = db.delete(CARRIERS_TABLE, where, whereArgs);
                break;
            }

            case URL_CURRENT_USING_SUBID: {
                String subIdString = url.getLastPathSegment();
                try {
                    subId = Long.parseLong(subIdString);
                } catch (NumberFormatException e) {
                    loge("NumberFormatException" + e);
                    throw new IllegalArgumentException("Invalid subId " + url);
                }
                if (DBG) log("subIdString = " + subIdString + " subId = " + subId);
                // FIXME use subId in query
            }
            //intentional fall through from above case

            case URL_CURRENT:
            {
                count = db.delete(CARRIERS_TABLE, where, whereArgs);
                break;
            }

            case URL_ID:
            {
                count = db.delete(CARRIERS_TABLE, Telephony.Carriers._ID + "=?",
                        new String[] { url.getLastPathSegment() });
                break;
            }

            case URL_RESTOREAPN_USING_SUBID: {
                String subIdString = url.getLastPathSegment();
                try {
                    subId = Long.parseLong(subIdString);
                } catch (NumberFormatException e) {
                    loge("NumberFormatException" + e);
                    throw new IllegalArgumentException("Invalid subId " + url);
                }
                if (DBG) log("subIdString = " + subIdString + " subId = " + subId);
                // FIXME use subId in query
            }
            case URL_RESTOREAPN: {
                count = 1;
                restoreDefaultAPN(subId);
                break;
            }

            case URL_PREFERAPN_USING_SUBID:
            case URL_PREFERAPN_NO_UPDATE_USING_SUBID: {
                String subIdString = url.getLastPathSegment();
                try {
                    subId = Long.parseLong(subIdString);
                } catch (NumberFormatException e) {
                    loge("NumberFormatException" + e);
                    throw new IllegalArgumentException("Invalid subId " + url);
                }
                if (DBG) log("subIdString = " + subIdString + " subId = " + subId);
            }
            //intentional fall through from above case

            case URL_PREFERAPN:
            case URL_PREFERAPN_NO_UPDATE:
            {
                setPreferredApnId((long)-1, subId);
                if ((match == URL_PREFERAPN) || (match == URL_PREFERAPN_USING_SUBID)) count = 1;
                break;
            }

            case URL_SIMINFO: {
                count = db.delete(SIMINFO_TABLE, where, whereArgs);
                break;
            }

            default: {
                throw new UnsupportedOperationException("Cannot delete that URL: " + url);
            }
        }

        if (count > 0) {
            getContext().getContentResolver().notifyChange(Telephony.Carriers.CONTENT_URI, null);
        }

        return count;
    }

    @Override
    public int update(Uri url, ContentValues values, String where, String[] whereArgs)
    {
        int count = 0;
        int uriType = URL_UNKNOWN;
        long subId = SubscriptionManager.getDefaultSubId();

        checkPermission();

        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int match = s_urlMatcher.match(url);
        switch (match)
        {
            case URL_TELEPHONY_USING_SUBID:
            {
                String subIdString = url.getLastPathSegment();
                try {
                    subId = Long.parseLong(subIdString);
                } catch (NumberFormatException e) {
                    loge("NumberFormatException" + e);
                    throw new IllegalArgumentException("Invalid subId " + url);
                }
                if (DBG) log("subIdString = " + subIdString + " subId = " + subId);
                //FIXME use subId in the query
            }
            //intentional fall through from above case

            case URL_TELEPHONY:
            {
                count = db.update(CARRIERS_TABLE, values, where, whereArgs);
                break;
            }

            case URL_CURRENT_USING_SUBID:
            {
                String subIdString = url.getLastPathSegment();
                try {
                    subId = Long.parseLong(subIdString);
                } catch (NumberFormatException e) {
                    loge("NumberFormatException" + e);
                    throw new IllegalArgumentException("Invalid subId " + url);
                }
                if (DBG) log("subIdString = " + subIdString + " subId = " + subId);
                //FIXME use subId in the query
            }
            //intentional fall through from above case

            case URL_CURRENT:
            {
                count = db.update(CARRIERS_TABLE, values, where, whereArgs);
                break;
            }

            case URL_ID:
            {
                if (where != null || whereArgs != null) {
                    throw new UnsupportedOperationException(
                            "Cannot update URL " + url + " with a where clause");
                }
                count = db.update(CARRIERS_TABLE, values, Telephony.Carriers._ID + "=?",
                        new String[] { url.getLastPathSegment() });
                break;
            }

            case URL_PREFERAPN_USING_SUBID:
            case URL_PREFERAPN_NO_UPDATE_USING_SUBID:
            {
                String subIdString = url.getLastPathSegment();
                try {
                    subId = Long.parseLong(subIdString);
                } catch (NumberFormatException e) {
                    loge("NumberFormatException" + e);
                    throw new IllegalArgumentException("Invalid subId " + url);
                }
                if (DBG) log("subIdString = " + subIdString + " subId = " + subId);
            }

            case URL_PREFERAPN:
            case URL_PREFERAPN_NO_UPDATE:
            {
                if (values != null) {
                    if (values.containsKey(COLUMN_APN_ID)) {
                        setPreferredApnId(values.getAsLong(COLUMN_APN_ID), subId);
                        if ((match == URL_PREFERAPN) ||
                                (match == URL_PREFERAPN_USING_SUBID)) {
                            count = 1;
                        }
                    }
                }
                break;
            }

            case URL_SIMINFO: {
                count = db.update(SIMINFO_TABLE, values, where, whereArgs);
                uriType = URL_SIMINFO;
                break;
            }

            default: {
                throw new UnsupportedOperationException("Cannot update that URL: " + url);
            }
        }

        if (count > 0) {
            switch (uriType) {
                case URL_SIMINFO:
                    getContext().getContentResolver().notifyChange(
                            SubscriptionManager.CONTENT_URI, null);
                    break;
                default:
                    getContext().getContentResolver().notifyChange(
                            Telephony.Carriers.CONTENT_URI, null);
            }
        }

        return count;
    }


    /**
     * Log with debug
     *
     * @param s is string log
     */
    private static void log(String s) {
        Log.d(TAG, s);
    }
    private static void loge(String s) {
        Log.e(TAG, s);
    }
}
