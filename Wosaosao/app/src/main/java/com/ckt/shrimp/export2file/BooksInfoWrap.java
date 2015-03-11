package com.ckt.shrimp.export2file;

/**
 * Created by ckt on 3/9/15.
 */
//package class, for saving the data and using for inserting database.
public class BooksInfoWrap {
    //对应数据库字段
    //图书ISBN码
    public String mBooKIsbn = null;
    //图书ID
    public String mBooKId = null;
    //图书标题
    public String mBooKTitle = null;
    //图书副标题
    public String mBooKSubtitle = null;
    //图书作者
    public String mBooKAuthor = null;
    //图书出版社
    public String mBooKPublisher = null;
    //图书价格
    public String mBooKPrice = null;

    //图书所属类别
    public String mBooKCategory = null;
    //图书所属类别编号
    public String mBookCategoryId = null;
    //图书购买时间
    public String mBookBoughtDate = null;
    //图书申请购买部门
    public String mBookApplicantDep = null;
    //图书购买申请者
    public String mBookApplicant = null;
    //图书实际购买金额
    public String mBookActualPrice = null;
    //图书借阅部门
    public String mBookBorrowerDep = null;
    //图书借阅人
    public String mBookBorrower = null;
    //图书借出时间
    public String mBookBorrowingDate = null;

    public BooksInfoWrap() {
    }

}