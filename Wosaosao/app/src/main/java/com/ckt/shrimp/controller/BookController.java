package com.ckt.shrimp.controller;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.ckt.shrimp.database.MyBookDataBaseHelper;
import com.ckt.shrimp.utils.Book;
import com.ckt.shrimp.utils.BookUtil;
import com.ckt.shrimp.utils.MyDbProvider;

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

    public void addBook(Book book){
        ContentValues v = fillContentValue(book);
        resolver.insert(BookUtil.BOOK_URI,v);
    }

    public boolean removeBookById(String bookID){
        resolver.delete(BookUtil.BOOK_URI,"'book_id= ?",new String[]{bookID});
        return  false;
    }


    public boolean updateBook(ContentValues v){
        resolver.update(BookUtil.BOOK_URI,v,null,null);
        return  false;
    }


    public Book queryBook(String bookId){
        resolver.query(BookUtil.BOOK_URI,null,"book_id = ?",new String[]{bookId},null);
        return  null;
    }

    public Cursor queryAllBorrow(){
        if(bookHelper != null) {
            return bookHelper.getReadableDatabase().rawQuery("select book.*,staff.* from book,staff  " +
                    " where  book.staff_id=staff.staff_id", null);
        }
        return  null;
    }


    public Cursor queryBorrowByBookId(String bookId){
        if(bookHelper != null) {
            return bookHelper.getReadableDatabase().rawQuery("select book.*,staff.* from book,staff" +
                    " where book.staff_id=staff.staff_id and book_id=?",new String[]{bookId});
        }
        return  null;
    }


    private ContentValues fillContentValue(Book book){
        ContentValues values = new ContentValues();
        values.put("book_id",book.getId());
        values.put("title",book.getTitle());
        values.put("subtitle",book.getSubTitle());
        values.put("author",book.getAuthor());
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
