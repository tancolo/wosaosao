package com.ckt.shrimp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.ckt.shrimp.wosaosao.BooksPutIn;
import com.ckt.shrimp.wosaosao.ScanningActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class BookUtil {

   public final static String bookAuthority = "com.ckt.saosao.authority";
   public final static Uri BOOK_URI = Uri.parse("content://"+bookAuthority+"/book");
   public final static Uri STAFF_URI = Uri.parse("content://"+bookAuthority+"/staff");


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
        try{
            URL  url = new URL(bmurl);
            URLConnection connection = url.openConnection();
            bis = new BufferedInputStream(connection.getInputStream());
            //Transform the byte stream into Bitmap
            bm = BitmapFactory.decodeStream(bis);
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
            info.setId(mess.getString("id"));
            info.setTitle(mess.getString("title"));
            info.setSubTitle(mess.getString("subtitle"));//add by tancolo
            info.setAuthor(parseAuthor(mess.getJSONArray("author")));
            info.setPublisher(mess.getString("publisher"));
            info.setPublishDate(mess.getString("pubdate"));
            info.setISBN(mess.getString("isbn13"));
            info.setBitmap(downLoadBitmap(mess.getString("image")));//bitmap
            info.setPrice(mess.getString("price"));

            //not used values, you can add it which you wanted.
            info.setSummary(mess.getString("summary"));
            info.setAuthorInfo(mess.getString("author_intro"));
            info.setPage(mess.getString("pages"));
            info.setContent(mess.getString("catalog"));
            info.setRate(mess.getJSONObject("rating").getString("average"));
            info.setTag(parseTags(mess.getJSONArray("tags")));
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
