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
     * 请求某个url上的图片资源
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
            //将请求返回的字节流编码成Bitmap
            bm = BitmapFactory.decodeStream(bis);
        }catch (Exception e){
            e.printStackTrace();
        }
        //关闭IO流
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
     * 解析图书JSON数据,把解析的数据封装在一个Book对象中
     * @param String
     * @return Book
     */
    public Book parseBookInfo(String str) {
        Book info = new Book();
        try {
            //先从String得到一个JSONObject对象
            JSONObject mess = new JSONObject(str);
            info.setId(mess.getString("id"));
            info.setSubTitle(mess.getString("subtitle"));//add by tancolo
            info.setTitle(mess.getString("title"));
            info.setBitmap(downLoadBitmap(mess.getString("image")));
            info.setAuthor(parseAuthor(mess.getJSONArray("author")));
            info.setPublisher(mess.getString("publisher"));
            info.setPublishDate(mess.getString("pubdate"));
            info.setISBN(mess.getString("isbn13"));
            info.setSummary(mess.getString("summary"));
            info.setAuthorInfo(mess.getString("author_intro"));
            info.setPage(mess.getString("pages"));
            info.setPrice(mess.getString("price"));
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
     * 针对豆瓣图书的特殊信息，抽出一个parseTags方法解析图书标签信息
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
     * 针对豆瓣图书的特殊信息，抽出一个parseAuthor方法解析作者信息
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
     * 对url的地址发送get方式的Http请求
     * @param String url
     * @return String
     */
    public static String getHttpRequest(String url) {
        String content = "";
        try {
            URL getUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
            connection.connect();
            // 取得输入流，并使用Reader读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines = "";
            while ((lines = reader.readLine()) != null) {
                content += lines;
            }
            reader.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //ScanningActivity.log("getHttpRequest(): " + content);
        //BooksPutIn.log("getHttpRequest(): " + content);
        return content;
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
