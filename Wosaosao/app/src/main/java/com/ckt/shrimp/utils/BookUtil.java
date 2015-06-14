package com.ckt.shrimp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import com.ckt.shrimp.database.InfoContents;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/** 2015.06.14 add the description.
 * This class's main job is get the book info from JSON.
 *
 * should add the timeout of the http connection?
 */
public class BookUtil {

    //used for ContentProvider
   public final static String bookAuthority = "com.ckt.saosao.authority";
   public final static Uri BOOK_URI = Uri.parse("content://"+bookAuthority+"/books");
   public final static Uri STAFF_URI = Uri.parse("content://"+bookAuthority+"/staffs");


   public static final int RETURN_ERROR = -1;
   public static final int RETURN_OK = 1;
   public static final int RESULT_ISBN = 1;
   public static final int RESULT_STUFF = 2;
   public static final String ISBN_START_STR = "978";

   public static final String ACTIVITY_TYPE = "type";
   public static final int TYPE_BORROW = 1;
   public static final int TYPE_RETURN = 2;

    /**
     * get the bitmap resource from the url
     * @param String bmurl
     * @return Bitmap
     */
    public Bitmap downLoadBitmap(String bmurl) {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        Log.e(this, "downLoadBitmap: bmurl " + bmurl);

        try{
            URL  url = new URL(bmurl);
            URLConnection connection = url.openConnection();
            bis = new BufferedInputStream(connection.getInputStream());
            //Transform the byte stream into Bitmap
            bm = BitmapFactory.decodeStream(bis);

            //close the connection
            //connection.disconnect(); //wrong api
        }catch (Exception e){
            e.printStackTrace();
        }
        //close the IO stream
        finally {
            try {
                if(bis != null)
                    bis.close();
                if (is != null)
                    is.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return bm;
    }

    /**
     * For Parsing the JSON data of the books, and then packaging these data as a Book.
     * @param String
     * @return Book
     */
    public Book parseBookInfo(String str) {
        Book info = new Book();
        try {
            //Getting the JSONObject from the result string.
            //key values
            JSONObject mess = new JSONObject(str);
            info.setId(mess.getString(InfoContents.BOOK_ID));
            info.setTitle(mess.getString(InfoContents.BOOK_TITLE));
            info.setSubTitle(mess.getString(InfoContents.BOOK_SUBTITLE));
            info.setAuthor(parseAuthor(mess.getJSONArray(InfoContents.BOOK_AUTHOR)));
            info.setPublisher(mess.getString(InfoContents.BOOK_PUBLISHER));
            info.setPublishDate(mess.getString(InfoContents.BOOK_PUBLISHDATE));
            info.setISBN(mess.getString(InfoContents.BOOK_ISBN));
            info.setPrice(mess.getString(InfoContents.BOOK_PRICE));
            info.setBitmap(downLoadBitmap(mess.getString(InfoContents.BOOK_BITMAP)));//bitmap
            info.setPage(mess.getString(InfoContents.BOOK_PAGE));
            info.setRate(mess.getJSONObject(InfoContents.BOOK_DOUBAN_RATE).getString(InfoContents.BOOK_DOUBAN_RATE_AVERAGE));
            info.setTag(parseTags(mess.getJSONArray(InfoContents.BOOK_DOUBAN_TAG)));

            //not used values, you can add it to database which you wanted.
            info.setSummary(mess.getString(InfoContents.BOOK_SUMMARY));
            info.setAuthorInfo(mess.getString(InfoContents.BOOK_AUTHORINFO));
            info.setContent(mess.getString(InfoContents.BOOK_CONTENT));

        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return info;
    }


    /**
     * For parsing the information of the book's TAG, because the Douban has some special info in their books.
     * @param JSONArray obj
     * @return String
     */
    public String parseTags (JSONArray obj) {
        StringBuffer str = new StringBuffer();
        for(int i = 0; i < obj.length(); i++) {
            try {
                str = str.append(obj.getJSONObject(i).getString("name")).append(" ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str.toString();
    }

    /**
     * For parsing the information of the Author, because the Douban has some special info in their books.
     * @param JSONArray arr
     * @return String
     */
    public String parseAuthor (JSONArray arr) {
        StringBuffer str = new StringBuffer();
        for(int i = 0; i < arr.length(); i++) {
            try{
                str = str.append(arr.getString(i)).append(" ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str.toString();
    }


    /**
     * Send the Http's request with the address of url
     * @param String url
     * @return String
     */
    public static String getHttpRequest(String url) {
        //should use the StringBuilder or StringBuffer, String is not used.
        StringBuffer jsonContent = new StringBuffer();
        try {
            URL getUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
            connection.connect();
            //Getting the inputting stream, and then read the stream.
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines = "";
            while ((lines = reader.readLine()) != null) {
                jsonContent.append(lines);
            }
            reader.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //ScanningActivity.log("getHttpRequest(): " + content);
        //BooksPutIn.log("getHttpRequest(): " + content);
        return jsonContent.toString();
    }


    /**
     *
     * @param Context context
     * @return boolean
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
