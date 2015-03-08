package com.ckt.shrimp.utils;

/**
 * Created by ckt on 3/6/15.
 */

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 */
public class Book implements Parcelable {
    //图书ID
    private String id;
    //图书标题
    private String Title;
    //图书副标题
    private String SubTitle;
    //图书作者
    private String Author;
    //作者信息
    private String AuthorInfo;
    //图书出版社
    private String Publisher;
    //出版时间
    private String PublishDate;
    //图书ISBN码
    private String ISBN;
    //图书价格
    private String Price;
    //图书页数
    private String Page;
    //图书评分
    private String Rate;
    //图书标签
    private String Tag;
    //图书目录
    private String Content;
    //图书摘要
    private String Summary;
    //图书图片
    private Bitmap Bitmap;


    //id
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    //title
    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }

    //sub title
    public String getSubTitle() {
        return SubTitle;
    }
    public void setSubTitle(String subTitle) {
        SubTitle = subTitle;
    }

    //author
    public String getAuthor() {
        return Author;
    }
    public void setAuthor(String author) {
        Author = author;
    }

    //author info
    public String getAuthorInfo() {
        return AuthorInfo;
    }
    public void setAuthorInfo(String authorInfo) {
        AuthorInfo = authorInfo;
    }

    //publisher
    public String getPublisher() {
        return Publisher;
    }
    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    //publish date
    public String getPublishDate() {
        return PublishDate;
    }
    public void setPublishDate(String publishDate) {
        PublishDate = publishDate;
    }

    //isbn info
    public String getISBN() {
        return ISBN;
    }
    public void setISBN(String iSBN) {
        ISBN = iSBN;
    }

    //price info
    public String getPrice() {
        return Price;
    }
    public void setPrice(String price) {
        Price = price;
    }

    //page info
    public String getPage() {
        return Page;
    }
    public void setPage(String page) {
        Page = page;
    }

    //rate info
    public String getRate() {
        return Rate;
    }
    public void setRate(String rate) {
        Rate = rate;
    }

    //tag info
    public String getTag() {
        return Tag;
    }
    public void setTag(String tag) {
        Tag = tag;
    }

    //content info
    public String getContent() {
        return Content;
    }
    public void setContent(String content) {
        Content = content;
    }

    //summary info
    public String getSummary() {
        return Summary;
    }
    public void setSummary(String summary) {
        Summary = summary;
    }

    //bitmap info
    public Bitmap getBitmap() {
        return Bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        Bitmap = bitmap;
    }

    /*
     * 实现Parcelable接口的方法
     * 1.getCreator()
     * 2.setCreator()
     * 3.Parcelable.Creator() 构造方法
     * 4.describeContents()
     * 5.writeToParcel()
     */
    public static Parcelable.Creator<Book> getCreator() {
        return CREATOR;
    }

    public static void setCreator(Parcelable.Creator<Book> creator) {
        CREATOR = creator;
    }

    public static Parcelable.Creator<Book> CREATOR = new Creator<Book>() {
        public Book createFromParcel(Parcel source) {
            Book bookInfo = new Book();
            bookInfo.Title = source.readString();
            bookInfo.SubTitle = source.readString();
            bookInfo.Bitmap = source.readParcelable(Bitmap.class.getClassLoader());
            bookInfo.Author = source.readString();
            bookInfo.Publisher = source.readString();
            bookInfo.PublishDate = source.readString();
            bookInfo.ISBN = source.readString();
            bookInfo.Summary = source.readString();
            bookInfo.id = source.readString();;
            bookInfo.AuthorInfo = source.readString();
            bookInfo.Page = source.readString();
            bookInfo.Price = source.readString();;
            bookInfo.Rate = source.readString();
            bookInfo.Tag = source.readString();
            bookInfo.Content = source.readString();
            return bookInfo;
        }
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Title);
        dest.writeString(SubTitle);
        dest.writeParcelable(Bitmap, flags);
        dest.writeString(Author);
        dest.writeString(Publisher);
        dest.writeString(PublishDate);
        dest.writeString(ISBN);
        dest.writeString(Summary);
        dest.writeString(id);
        dest.writeString(AuthorInfo);
        dest.writeString(Page);
        dest.writeString(Price);
        dest.writeString(Rate);
        dest.writeString(Tag);
        dest.writeString(Content);
    }

}