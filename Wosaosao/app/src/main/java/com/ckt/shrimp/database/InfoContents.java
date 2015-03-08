package com.ckt.shrimp.database;


import android.provider.BaseColumns;

/**
 * Created by ckt on 3/6/15.
 * Only contains the contents of book and stuff.
 */
public final class InfoContents implements BaseColumns {

    //本app 数据库中会使用的字段
    //图书ISBN码
    public static final String BOOK_ISBN = "book_isbn";
    //图书ID
    public static final String BOOK_ID = "book_id";
    //图书标题
    public static final String BOOK_TITLE = "book_title";
    //图书副标题
    public static final String BOOK_SUBTITLE = "book_subtitle";
    //图书作者
    public static final String BOOK_AUTHOR = "book_author";
    //图书出版社
    public static final String BOOK_PUBLISHER = "book_publisher";
    //图书价格
    public static final String BOOK_PRICE = "book_price";

    /**
    //可以扩展的字段
    //作者信息
    public static final String BOOK_AUTHORINFO = "book_authorInfo";
    //出版时间
    public static final String BOOK_PUBLISHDATE = "book_publishDate";
    //图书页数
    public static final String BOOK_PAGE = "book_page";
    //图书评分
    public static final String BOOK_RATE = "book_rate";
    //图书标签
    public static final String BOOK_TAG = "book_tag";
    //图书目录
    public static final String BOOK_CONTENT = "book_content";
    //图书摘要
    public static final String BOOK_SUMMARY = "book_summary";
    //图书图片
    public static final String BOOK_BITMAP = "book_bitmap";
    */

    //本app中使用的借阅人数据表字段
    //stuff id
    public static final String STUFF_ID = "stuff_id";
    //stuff name
    public static final String STUFF_NAME = "stuff_name";
    //stuff email
    public static final String STUFF_EMAIL = "stuff_email";
    //stuff department
    public static final String STUFF_DEPARTMENT = "stuff_department";

    //可扩展字段
    //stuff bitmap
    //public static final String STUFF_BITMAP = "stuff_bitmap";

}
