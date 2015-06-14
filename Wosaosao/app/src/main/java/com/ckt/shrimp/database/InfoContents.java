package com.ckt.shrimp.database;


import android.provider.BaseColumns;

/**
 * Created by ckt on 3/6/15.
 * Only contains the contents of book and stuff.
 *
 * 2015.06.14
 * recomposing the key values, consistent with the database in class BookInfoDataBaseHelper.
 * And add some variables for JSON data.
 */
public final class InfoContents implements BaseColumns {

    //database name
    public static final String DATABASE_NAME = "wosaosao";
    public static final String BOOK_TABLE_NAME = "books";
    public static final String STAFF_TABLE_NAME = "staffs";

    public static final int RETURN_ERROR = -1;
    public static final int RETURN_OK = 1;

    //本app 数据库中会使用的字段
    //auto increment for table "books"
    //not used, because of BaseColumns has the "_id"
    //public static final String BOOK_AUTO_ID = "_id";

    //图书ID
    public static final String BOOK_ID = "id";

    //图书标题
    public static final String BOOK_TITLE = "title";

    //图书副标题
    public static final String BOOK_SUBTITLE = "subtitle";

    //图书作者
    public static final String BOOK_AUTHOR = "author";

    //图书出版社
    public static final String BOOK_PUBLISHER = "publisher";

    //出版时间
    public static final String BOOK_PUBLISHDATE = "pubdate";

    //图书ISBN码
    public static final String BOOK_ISBN = "isbn13";

    //图书价格
    public static final String BOOK_PRICE = "price";

    //图书图片
    public static final String BOOK_BITMAP = "image";


    //图书所属类别
    public static final String BOOK_CATEGORY = "category";

    //图书所属类别编号
    public static final String BOOK_CATEGORY_ID = "category_id";

    //图书购买时间
    public static final String BOOK_BOUGHT_TIME = "bought_date";

    //图书申请者ID
    public static final String BOOK_APPLICANT_ID = "applicant_id";

    //图书购买申请者名字
    public static final String BOOK_APPLICANT_NAME = "applicant_name";

    //图书申请购者名字
    public static final String BOOK_APPLICANT_EMAIL = "applicant_email";

    //图书申请购买部门
    public static final String BOOK_APPLICANT_DEPARTMENT = "applicant_dep";

    //图书实际购买金额
    public static final String BOOK_ACTUAL_PRICE_ = "actual_price";


    //图书借阅人ID
    public static final String BOOK_BORROWER_ID = "borrower_id";

    //图书借阅人name
    public static final String BOOK_BORROWER_NAME = "borrower_name";

    //图书借阅人email
    public static final String BOOK_BORROWER_EMAIL = "borrower_email";

    //图书借阅部门
    public static final String BOOK_BORROWING_DEP = "borrower_dep";

    //图书借出时间
    public static final String BOOK_BORROWING_DATE = "borrowed_date";


    //other info of books, need to add database
    //图书页数
    public static final String BOOK_PAGE = "pages";

    //图书评分
    public static final String BOOK_DOUBAN_RATE = "rating";

    //图书平均评分
    public static final String BOOK_DOUBAN_RATE_AVERAGE = "average";

    //图书标签
    public static final String BOOK_DOUBAN_TAG = "tags";



    //可以扩展的字段, 仅仅用于获取JSON数据,不存于数据库
    //作者信息介绍
    public static final String BOOK_AUTHORINFO = "author_intro";
    //图书catalog
    public static final String BOOK_CONTENT = "catalog";
    //图书摘要
    public static final String BOOK_SUMMARY = "summary";


    //本app中使用的借阅人数据表字段
    ////auto increment for table "staffs"
    //not used, because of BaseColumns has the "_id"
    //public static final String STAFF_AUTO_ID = "_id";

    //staff id
    public static final String STAFF_ID = "staff_id";

    //staff name
    public static final String STAFF_NAME = "staff_name";

    //staff email
    public static final String STAFF_EMAIL = "staff_email";

    //staff department
    public static final String STAFF_DEPARTMENT = "staff_dep";


    //可扩展字段
    //staff bitmap
    //public static final String STAFF_BITMAP = "staff_bitmap";
}
