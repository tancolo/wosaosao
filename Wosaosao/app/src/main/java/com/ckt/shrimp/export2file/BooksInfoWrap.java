package com.ckt.shrimp.export2file;

/**
 * Created by ckt on 3/9/15.
 */
//package class, for saving the data and using for inserting database.
public class BooksInfoWrap {
    //对应数据库字段
    //图书ISBN码
    String mBooKIsbn = null;
    //图书ID
    String mBooKId = null;
    //图书标题
    String mBooKTitle = null;
    //图书副标题
    String mBooKSubtitle = null;
    //图书作者
    String mBooKAuthor = null;
    //图书出版社
    String mBooKPublisher = null;
    //图书价格
    String mBooKPrice = null;

    //图书所属类别
    String mBooKCategory = null;
    //图书所属类别编号
    String mBookCategoryId = null;
    //图书购买时间
    String mBookBoughtDate = null;
    //图书申请购买部门
    String mBookApplicantDep = null;
    //图书购买申请者
    String mBookApplicant = null;
    //图书实际购买金额
    String mBookActualPrice = null;
    //图书借阅部门
    String mBookBorrowerDep = null;
    //图书借阅人
    String mBookBorrower = null;
    //图书借出时间
    String mBookBorrowingDate = null;

    BooksInfoWrap() {
    }

}