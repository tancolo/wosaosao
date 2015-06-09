package com.ckt.shrimp.utils;

/**
 * Created by ckt on 3/6/15.
 */

/** This class will contains some information of book.
 * The Key values are:
 * Title, SubTitle, Author, Publisher, ISBN, Bitmap, Price.
 * And For our company's wiki page. We need some others information.
 * Such as the category, who and  when bought it.
 *
 */
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 *implements the Parcelable, using for expending.
 */
public class Book implements Parcelable {
    //wiki need
    //图书所属类别
    private String booKCategory;

    //图书所属类别编号
    private String bookCategoryId;

    //图书购买时间
    private String bookBoughtDate;

    //图书申请购买部门
    private String bookApplicantDep;

    //图书购买申请者name
    private String bookApplicantName;

    //图书实际购买金额
    private String bookActualPrice;

    //图书借阅者部门
    private String bookBorrowerDep;

    //图书借阅人name
    private String bookBorrower;

    //图书借出时间
    private String bookBorrowingDate;

    //add other book info.
    //图书购买申请者id
    private String bookBoughtStaffId;

    //图书购买申请者email
    private String bookBoughtStaffEmail;

    //图书借阅人 id
    private String bookBorrowerId;

    //图书借阅者email
    private String bookBorrowerEmail;


    //Basic Book info, we get it from website (Douban). Very important.
    //using key values.
    //图书ID
    private String IsbnId;//the same books has one id. different from ISBN.

    //图书标题
    private String Title;

    //图书副标题
    private String SubTitle;

    //图书作者
    private String Author;

    //图书出版社
    private String Publisher;

    //出版时间
    private String PublishDate;

    //图书ISBN码
    private String ISBN;

    //图书图片
    private Bitmap Bitmap;

    //图书价格
    private String Price;


    //not using key value. You can save these values if you want.
    //作者信息
    private String AuthorInfo;
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



    public String getBookBorrowerEmail() {
        return bookBorrowerEmail;
    }

    public void setBookBorrowerEmail(String bookBorrowerEmail) {
        this.bookBorrowerEmail = bookBorrowerEmail;
    }

    public String getBookBoughtStaffEmail() {
        return bookBoughtStaffEmail;
    }

    public void setBookBoughtStaffEmail(String bookBoughtStaffEmail) {
        this.bookBoughtStaffEmail = bookBoughtStaffEmail;
    }

    public String getBookBorrowerId() {
        return bookBorrowerId;
    }

    public void setBookBorrowerId(String bookBorrowerId) {
        this.bookBorrowerId = bookBorrowerId;
    }

    public String getBookBoughtStaffId() {
        return bookBoughtStaffId;
    }
    public void setBookBoughtStaffId(String bookBoughtStaffId) {
        this.bookBoughtStaffId = bookBoughtStaffId;
    }

    public String getBooKCategory() {
        return booKCategory;
    }

    public void setBooKCategory(String booKCategory) {
        this.booKCategory = booKCategory;
    }

    public String getBookCategoryId() {
        return bookCategoryId;
    }

    public void setBookCategoryId(String bookCategoryId) {
        this.bookCategoryId = bookCategoryId;
    }

    public String getBookBoughtDate() {
        return bookBoughtDate;
    }

    public void setBookBoughtDate(String bookBoughtDate) {
        this.bookBoughtDate = bookBoughtDate;
    }

    public String getBookApplicantDep() {
        return bookApplicantDep;
    }

    public void setBookApplicantDep(String bookApplicantDep) {
        this.bookApplicantDep = bookApplicantDep;
    }

    public String getBookApplicantName() {
        return bookApplicantName;
    }

    public void setBookApplicantName(String bookApplicantName) {
        this.bookApplicantName = bookApplicantName;
    }

    public String getBookActualPrice() {
        return bookActualPrice;
    }

    public void setBookActualPrice(String bookActualPrice) {
        this.bookActualPrice = bookActualPrice;
    }

    public String getBookBorrowerDep() {
        return bookBorrowerDep;
    }

    public void setBookBorrowerDep(String bookBorrowerDep) {
        this.bookBorrowerDep = bookBorrowerDep;
    }

    public String getBookBorrower() {
        return bookBorrower;
    }

    public void setBookBorrower(String bookBorrower) {
        this.bookBorrower = bookBorrower;
    }

    public String getBookBorrowingDate() {
        return bookBorrowingDate;
    }

    public void setBookBorrowingDate(String bookBorrowingDate) {
        this.bookBorrowingDate = bookBorrowingDate;
    }


    //For basic information. The function should be Used.
    //id
    public String getId() {
        return IsbnId;
    }
    public void setId(String id) {
        this.IsbnId = id;
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

    //bitmap info
    public Bitmap getBitmap() {
        return Bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        Bitmap = bitmap;
    }


    //For the other basic information. The function not be Used.
    //author info
    public String getAuthorInfo() {
        return AuthorInfo;
    }
    public void setAuthorInfo(String authorInfo) {
        AuthorInfo = authorInfo;
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
            bookInfo.IsbnId = source.readString();
            bookInfo.Title = source.readString();
            bookInfo.SubTitle = source.readString();
            bookInfo.Author = source.readString();
            bookInfo.Publisher = source.readString();
            bookInfo.PublishDate = source.readString();
            bookInfo.ISBN = source.readString();
            bookInfo.Bitmap = source.readParcelable(Bitmap.class.getClassLoader());
            bookInfo.Price = source.readString();

            //It should add other book info. But I don't add those info at present.
            /*bookInfo.Summary = source.readString();
            bookInfo.AuthorInfo = source.readString();
            bookInfo.Page = source.readString();
            bookInfo.Rate = source.readString();
            bookInfo.Tag = source.readString();
            bookInfo.Content = source.readString();
            */

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
        dest.writeString(IsbnId);
        dest.writeString(Title);
        dest.writeString(SubTitle);
        dest.writeString(Author);
        dest.writeString(Publisher);
        dest.writeString(PublishDate);
        dest.writeString(ISBN);
        dest.writeParcelable(Bitmap, flags);
        dest.writeString(Price);

        //add other book info.but I don't add those info at present.
        /*
        dest.writeString(Summary);
        dest.writeString(AuthorInfo);
        dest.writeString(Page);
        dest.writeString(Rate);
        dest.writeString(Tag);
        dest.writeString(Content);
        */
    }

    //init all strings to ""
    public Book() {
        //The basic info of book. The key values.
        IsbnId = "";
        Title = "";
        SubTitle = "";
        Author = "";
        Publisher = "";
        PublishDate = "";
        ISBN = "";
        Bitmap = null;
        Price = "";

        //book's other info.
        booKCategory = "";
        bookCategoryId = "";
        bookBoughtDate = "";
        bookApplicantDep = "";
        bookApplicantName = "";
        bookActualPrice = "";
        bookBorrowerDep = "";
        bookBorrower = "";
        bookBorrowingDate = "";
        bookBoughtStaffId = "";
        bookBoughtStaffEmail = "";
        bookBorrowerId = "";
        bookBorrowerEmail = "";

        //not used values.
        Page = "";
        Rate = "";
        Tag = "";
        Content = "";
        Summary = "";
        AuthorInfo = "";
    }

}
