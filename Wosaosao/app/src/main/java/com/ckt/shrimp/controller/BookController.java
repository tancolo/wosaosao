package com.ckt.shrimp.controller;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.ckt.shrimp.database.InfoContents;
import com.ckt.shrimp.utils.*;
import com.ckt.shrimp.wosaosao.R;

/**
 * Created by ckt on 09/03/15.
 */
public class BookController {

    private static final String TAG = "BookController";
    private Context mContext;
    private ContentResolver resolver;
    private static BookController sInstance;

    final private SaoGlobal mSaoGlobal;

    public static  BookController init(SaoGlobal app) {
        synchronized (BookController.class) {
            if(sInstance == null) {
                //throw new IllegalStateException("No BookController here!");
                sInstance = new BookController(app);
            }else {
                log("init() called multiple times!  sInstance = " + sInstance);
            }

            return sInstance;
        }
    }

    public static BookController getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("No SaoGlobal here!");
        }

        return sInstance;
    }


    private BookController(SaoGlobal app) {
        Log.e(this, "BookController constructor!!!");
        mSaoGlobal = app;
        mContext = app.getContext();
        resolver = mContext.getContentResolver();
    }

    public int addBook(Book book) {
        Log.e(this, "add book!!!");

        ContentValues values = fillContentValue(book);
        //get SQLiteDatabase object, and call the function insert.
        try {
            if (mSaoGlobal.getInstanceWritableDatabase() != null) {
                Log.e(this, "insert the database... ...");
                mSaoGlobal.getInstanceWritableDatabase().insert(InfoContents.BOOK_TABLE_NAME, null, values);
            }
        }catch (Exception exp) {
            Log.e(this, "Insert data base Error!"
            +"\n exp = " + exp);
            mSaoGlobal.getInstanceWritableDatabase().close();
            return InfoContents.RETURN_ERROR;
        }

        return InfoContents.RETURN_OK;
    }

    public boolean lendingBook(String isbn,ContentValues value){

        log("lendingBook==> isbn = " + isbn + ", value = " + value);
        if ((!value.containsKey("borrower_id")) || (!value.containsKey("borrower_email")) || (!value.containsKey("borrower")) || (!value.containsKey("borrowing_date"))){
            Toast.makeText(mContext,mContext.getResources().getString(R.string.notice_borrow_success),Toast.LENGTH_SHORT).show();
            return  false;
        }

        log("lendingBook==> isBookBorrowed() : " + isBookBorrowed(isbn) );
        if (isBookBorrowed(isbn)){
          return  false;
        }else {
            //resolver.update(BookUtil.BOOK_URI,value,null,null);
            int flag = updateBook(value);
            log("lendingBook==> flag:  " + flag );
            if (flag == -1){
                return  false;
            }
            return  true;
        }
    }

    public boolean isBookBorrowed(String isbn){
         Cursor c = mSaoGlobal.getInstanceBaseHelper().getReadableDatabase().rawQuery(
                 "select * from book where isbn = " + isbn + " and borrower_id !=null", null);
        return  (c != null && c.getCount() != 0);

    }

    public boolean removeBookById(String bookID){
        resolver.delete(BookUtil.BOOK_URI,"'book_id= ?",new String[]{bookID});
        return  false;
    }


    public int updateBook(ContentValues v){
     return  resolver.update(BookUtil.BOOK_URI,v,null,null);
    }


    public Cursor queryBook(String bookId) {
        return resolver.query(BookUtil.BOOK_URI,null,"book_id = ?",new String[]{bookId},null);
    }

    public Cursor queryAllBorrow() {
        if(mSaoGlobal.getInstanceBaseHelper() != null) {
            return mSaoGlobal.getInstanceBaseHelper().getReadableDatabase().rawQuery(
                    "select book.*,staff.* from book,staff  "
                    + " where  book.staff_id=staff.staff_id", null);
        }
        return  null;
    }


    public Cursor queryBorrowByBookId(String bookId) {
       if (resolver == null){
           return  null;
       }
       return resolver.query(BookUtil.BOOK_URI,null,"borrow_id != null",null,null);
    }


    private ContentValues fillContentValue(Book book) {
        ContentValues values = new ContentValues();

        //book basic info
        values.put(InfoContents.BOOK_ID, book.getId());
        values.put(InfoContents.BOOK_TITLE, book.getTitle());
        values.put(InfoContents.BOOK_SUBTITLE, book.getSubTitle());
        values.put(InfoContents.BOOK_AUTHOR, book.getAuthor());
        values.put(InfoContents.BOOK_PUBLISHER, book.getPublisher());
        values.put(InfoContents.BOOK_PUBLISHDATE, book.getPublishDate());
        values.put(InfoContents.BOOK_ISBN, book.getISBN());
        values.put(InfoContents.BOOK_PRICE, book.getPrice());

        //for wiki, category info
        values.put(InfoContents.BOOK_CATEGORY, book.getBooKCategory());
        values.put(InfoContents.BOOK_CATEGORY_ID, book.getBookCategoryId());
        values.put(InfoContents.BOOK_BOUGHT_TIME, book.getBookBoughtDate());
        values.put(InfoContents.BOOK_APPLICANT_ID, book.getBookBoughtStaffId());
        values.put(InfoContents.BOOK_APPLICANT_NAME, book.getBookApplicantName());
        values.put(InfoContents.BOOK_APPLICANT_EMAIL, book.getBookBoughtStaffEmail());
        values.put(InfoContents.BOOK_APPLICANT_DEPARTMENT, book.getBookApplicantDep());
        values.put(InfoContents.BOOK_ACTUAL_PRICE_, book.getBookActualPrice());

        //borrower info
        values.put(InfoContents.BOOK_BORROWER_ID, book.getBookBorrowerId());
        values.put(InfoContents.BOOK_BORROWER_NAME, book.getBookBorrower());
        values.put(InfoContents.BOOK_BORROWER_EMAIL, book.getBookBorrowerEmail());
        values.put(InfoContents.BOOK_BORROWING_DEP, book.getBookBorrowerDep());
        values.put(InfoContents.BOOK_BORROWING_DATE, book.getBookBoughtDate());

        //other info of book
        values.put(InfoContents.BOOK_PAGE, book.getPage());
        values.put(InfoContents.BOOK_DOUBAN_RATE, book.getRate());
        values.put(InfoContents.BOOK_DOUBAN_TAG, book.getTag());

        //bitmap, should save the binary, not Bitmap object. later add!!
        //values.put(InfoContents.BOOK_BITMAP, book.getBitmap());

        //return values
        return  values;

    }

    public Cursor queryBorrowByBookISDN(String isbn) {
         if(mSaoGlobal.getInstanceBaseHelper() != null) {
            Cursor c = mSaoGlobal.getInstanceBaseHelper().getReadableDatabase().rawQuery(
                    "select book.* from book  "
                    + " where  book.isbn= ?", new String[]{isbn});

             if (c != null && c.getCount() != 0) {
                 return  c;
             }
        };
        return null;
    }

    public static void log(String str) {
        Log.e(TAG, str);
    }
}
