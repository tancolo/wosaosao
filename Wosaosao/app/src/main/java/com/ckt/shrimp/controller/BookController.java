package com.ckt.shrimp.controller;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.ckt.shrimp.database.MyBookDataBaseHelper;
import com.ckt.shrimp.utils.Book;
import com.ckt.shrimp.utils.BookUtil;
import com.ckt.shrimp.utils.MyDbProvider;
import com.ckt.shrimp.wosaosao.R;

import java.util.List;

/**
 * Created by ckt on 09/03/15.
 */
public class BookController {
    private Context mContext;
    private ContentResolver resolver;
    public static MyBookDataBaseHelper bookHelper;

    public  BookController(Context c){
        mContext = c;
        resolver = mContext.getContentResolver();
    }

    public Uri addBook(Book book){
        ContentValues v = fillContentValue(book);
        return resolver.insert(BookUtil.BOOK_URI,v);
    }


    public boolean lendingBook(String bookId,ContentValues value){
        //resolver.query(BookUtil.BOOK_URI,)
        //resolver.insert(BookUtil.BOOK_URI,v);
        if ((!value.containsKey("borrower_id")) || (!value.containsKey("borrower_email")) || (!value.containsKey("borrower")) || (!value.containsKey("borrowing_date"))){
            Toast.makeText(mContext,mContext.getResources().getString(R.string.borrow_success),Toast.LENGTH_SHORT).show();
            return  false;
        }
        if (isBookBorrowed(bookId)){
          return  false;
        }else {
            //resolver.update(BookUtil.BOOK_URI,value,null,null);
            int flag = updateBook(value);
            if (flag == -1){
                return  false;
            }
            return  true;
        }
    }

    public boolean isBookBorrowed(String bookId){
         Cursor c = bookHelper.getReadableDatabase().rawQuery("select * from book where book_id = " + bookId + " and borrower_id !=null", null);
        return  c != null;

    }

    public boolean removeBookById(String bookID){
        resolver.delete(BookUtil.BOOK_URI,"'book_id= ?",new String[]{bookID});
        return  false;
    }


    public int updateBook(ContentValues v){
     return  resolver.update(BookUtil.BOOK_URI,v,null,null);
    }


    public Cursor queryBook(String bookId){
        return resolver.query(BookUtil.BOOK_URI,null,"book_id = ?",new String[]{bookId},null);
    }

    public Cursor queryAllBorrow(){
        if(bookHelper != null) {
            return bookHelper.getReadableDatabase().rawQuery("select book.*,staff.* from book,staff  " +
                    " where  book.staff_id=staff.staff_id", null);
        }
        return  null;
    }


    public Cursor queryBorrowByBookId(String bookId){
       if (resolver == null){
           return  null;
       }
       return resolver.query(BookUtil.BOOK_URI,null,"borrow_id != null",null,null);
    }




    //add other book info.
    //图书购买申请者id
    private String bookBoughtStaffId;
    //图书购买申请者email
    private String bookBoughtStaffEmail;
    private ContentValues fillContentValue(Book book){
        ContentValues values = new ContentValues();
        //for wiki
        values.put("category",book.getBooKCategory());
        values.put("title",book.getTitle());
        values.put("category_id",book.getBookCategoryId());
        values.put("author",book.getAuthor());
        values.put("bought_date",book.getBookBoughtDate());
        values.put("applicant_dep",book.getBookApplicantDep());
        values.put("applicant",book.getBookApplicant());
        values.put("actual_price",book.getBookActualPrice());

        values.put("applicant_id",book.getBookBoughtStaffId());
        values.put("applicant_email",book.getBookBoughtStaffEmail());

        values.put("book_id",book.getId());
        values.put("subtitle",book.getSubTitle());
        values.put("author_info",book.getAuthorInfo());
        values.put("publisher",book.getPublisher());
        values.put("publish_date",book.getPublishDate());
        values.put("isbn",book.getISBN());
        values.put("price",book.getPrice());
        values.put("page",book.getPage());
        values.put("rate",book.getRate());
        values.put("tag",book.getTag());
        values.put("content",book.getContent());
        values.put("summary",book.getSummary());
        return  values;

    }
}
