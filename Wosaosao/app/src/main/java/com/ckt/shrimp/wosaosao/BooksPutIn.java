package com.ckt.shrimp.wosaosao;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ckt.shrimp.controller.BookController;
import com.ckt.shrimp.controller.SaoGlobal;
import com.ckt.shrimp.database.InfoContents;
import com.ckt.shrimp.utils.Book;
import com.ckt.shrimp.utils.Log;
import com.ckt.shrimp.utils.BookUtil;
import java.util.Calendar;

import com.ckt.shrimp.utils.ParseAndWriteInfo;
import com.zxing.activity.CaptureActivity;

public class BooksPutIn extends ActionBarActivity implements OnItemSelectedListener, View.OnClickListener,
 View.OnTouchListener {
    //private BookController  bookController;
    private static final String TAG = "BooksPutIn";
    private static final int DATE_DIALOG_ID = 0x1000;
    //private static final int RESULT_ISBN = 1;
    //private static final int RESULT_STUFF = 2;
    //private String format = "-";
    //private static  final int CATEGORY_COUNTS = 5;
    //private static int[] mStartIndex = new int[CATEGORY_COUNTS];
    //private int mIndex = 0;
    //private float mfActualPrice = 0;

    private Spinner mBookSpinner = null;
    private EditText  mCategoryIndexEdit = null;
    private EditText  mActualPriceEdit = null;
    private EditText  mBookBoughtDate = null;
    private Button mAddAllInfo = null;
    private ProgressDialog mProgressDialog;

    private Book mBooksInfoWrap = new Book();
    private int mYear = 0;
    private int mMonth = 0;
    private int mDay = 0;
    //private long mDateLong = 0;

    //definition the info of ISBN and Staff
    private Button mButtonScanISbn;
    private Button mButtonScanStuff;
    private TextView mTextScanIsbn;
    private TextView mTextScanStuff;

    //selected item
    private String mSeleCategory = null;
    private String mSeleCategoryId = null;
    private String[] mSeleCategoryEng = null;
    //private String[] mSeleCategoryZch = null;
    private int posCategoryIndex = 0;

    //API of Douban website
    private static final String DOUBAN_URL = "https://api.douban.com/v2/book/isbn/:";
    private static final String CAPTURE_RESULT = "result";

    //SaoGlobal
    SaoGlobal mSaoGlobal;
    BookController mBookController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_put_in);

        //book spinner
        mBookSpinner = (Spinner)findViewById(R.id.book_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(BooksPutIn.this,
                R.array.book_category_spinner, android.R.layout.simple_spinner_item);
        mBookSpinner.setAdapter(adapter);
        //mBookSpinner.setOnItemClickListener();
        mBookSpinner.setOnItemSelectedListener(this);

        //category index edit text
        mCategoryIndexEdit = (EditText)findViewById(R.id.category_index);
        //actual price edit text
        mActualPriceEdit = (EditText)findViewById(R.id.actual_price);
        //bought date
        mBookBoughtDate = (EditText)findViewById(R.id.book_bought_date);

        mAddAllInfo = (Button)findViewById(R.id.add_all_info);
        mAddAllInfo.setOnClickListener(this);
        mBookBoughtDate.setOnTouchListener(this);

        //init current date, and get the date strings, "Year", "Month", "Day"
        final Calendar currentDate = Calendar.getInstance();
        mYear = currentDate.get(Calendar.YEAR);
        mMonth = currentDate.get(Calendar.MONTH);
        mDay = currentDate.get(Calendar.DAY_OF_MONTH);

        //init the layout of ISBN and Staff info
        mButtonScanISbn = (Button)findViewById(R.id.scan_ISBN);
        mButtonScanStuff = (Button)findViewById(R.id.scan_staff_info);
        mTextScanIsbn = (TextView)findViewById(R.id.scan_ISBN_result);
        mTextScanStuff = (TextView)findViewById(R.id.scan_stuff_result);
        mButtonScanISbn.setOnClickListener(this);
        mButtonScanStuff.setOnClickListener(this);
        //bookController = new BookController(this);

        //save the category id, it must be english.
        /** The category id is eng string:
         *  "综合"， -->  CKT-CD ZH-0001
         *  "研发",  --> CKT-CD YF-BC-0001
         *  "设计",  --> CKT-CD YF-SJ-0001
         *  "网络",  --> CKT-CD YF-WL-0001
         *  "测试".  --> CKT-CD YF-CS-0001
         */
        //So, need to judge category is string or not. should get the array strings.R.id.book_category_spinner_eng
        mSeleCategoryEng = this.getResources().getStringArray(R.array.book_category_spinner_eng);
        //only for debugging
        if (mSeleCategoryEng != null ) {
            int count = mSeleCategoryEng.length;
            for (int i = 0; i < count; i++) {
                Log.e(this, "mSeleCategoryEng[ " + i + "] = " + mSeleCategoryEng[i]);
            }
        }//debug-end

        Log.e(this, "get instances of SaoGlobal & BookController");
        mSaoGlobal = SaoGlobal.getInstance();
        mBookController = BookController.getInstance();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            Book book = (Book)msg.obj;
            //dismissing the progress bar
            mProgressDialog.dismiss();
            if (book == null) {
                Toast.makeText(BooksPutIn.this, getResources().getString(R.string.notice_not_found_book), Toast.LENGTH_LONG).show();
            }else {
                //should show the info of this book.
                //at present only focus on below info:
                //ISBN, name, author, publisher, price.
                format2text(book);//to show the info
                saveBooksInfo(book);//save info to class object mBooksInfoWrap.
            }
        }
    };

    @Override
    public void onClick(View view) {
        //both call the same api of zxing.
        switch(view.getId()) {
            case R.id.scan_ISBN:
                //Open the capture UI, scan the bar code of ISBN.
                Intent openCameraIntent_ISBN = new Intent(BooksPutIn.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent_ISBN, BookUtil.RESULT_ISBN);
                break;
            case R.id.scan_staff_info:
                //call zxing API
                //Open the capture UI, scan the  two-dimension code.
                Intent openCameraIntent_staff = new Intent(BooksPutIn.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent_staff, BookUtil.RESULT_STUFF);
                break;
            case R.id.add_all_info:
                //get all books info, the class BooksInfoWrap contains isbn info and the inputting info.
                mBooksInfoWrap.setBooKCategory(mSeleCategory);//get the book category.eg. "综合"， "研发", "设计", "网络", "测试".
                /** but the category id is eng string:
                 *  "综合"， -->  CKT-CD ZH-0001
                 *  "研发",  --> CKT-CD YF-BC-0001
                 *  "设计",  --> CKT-CD YF-SJ-0001
                 *  "网络",  --> CKT-CD YF-WL-0001
                 *  "测试".  --> CKT-CD YF-CS-0001
                 */
                //mSeleCategoryEng save the "CKT-CD ZH-" and so on. and the posCategoryIndex will save the pos which selected.
                mBooksInfoWrap.setBookCategoryId(mSeleCategoryEng[posCategoryIndex] + mCategoryIndexEdit.getText().toString());//e.g CKT-CD YF-BC-001
                mBooksInfoWrap.setBookActualPrice(mActualPriceEdit.getText().toString());// e.g 28.50
                mBooksInfoWrap.setBookBoughtDate(mBookBoughtDate.getText().toString());//get the bought date.

                //dump all Book info
                dump(mBooksInfoWrap);

                if(mBookController != null) {
                  int result =  mBookController.addBook(mBooksInfoWrap);
                  if (result != InfoContents.RETURN_ERROR) {
                      Toast.makeText(this, getResources().getString(R.string.notice_add_bookInfo_success),
                              Toast.LENGTH_SHORT).show();
                  }else {
                      Toast.makeText(this, getResources().getString(R.string.notice_add_bookInfo_failed),
                              Toast.LENGTH_SHORT).show();
                  }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e(this, "requestCode = " + requestCode +", resultCode = " + resultCode);
        //process the result of scanning, and show the result for debugging.
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(CAPTURE_RESULT);
            if (BookUtil.RESULT_ISBN == requestCode) {// value 1 means ISBN.
                mTextScanIsbn.setText(scanResult);
                //Is the network connected??
                if(BookUtil.isNetworkConnected(this)) {
                    mProgressDialog = new ProgressDialog(this);
                    mProgressDialog.setMessage(getResources().getString(R.string.notice_read_bookinfo));
                    mProgressDialog.show();
                    String urlStr = DOUBAN_URL + scanResult;
                    Log.e(this, "urlStr : " + urlStr);

                    //Start a new thread to download the book info.
                    new LoadParseBookThread(urlStr).start();
                }else {
                    Toast.makeText(this, getResources().getString(R.string.notice_network_error), Toast.LENGTH_LONG).show();
                }
            }else { // value 2 means two dimension code about stuff info.
                mTextScanStuff.setText(scanResult);
                //need to parse staff info from the 2D code. static function
                //need to pass the BookInfoWrap
                if(BookUtil.RETURN_OK != ParseAndWriteInfo.parseStaffInfo(scanResult, mBooksInfoWrap, null)) {
                    Toast.makeText(BooksPutIn.this, getResources().getString(R.string.notice_get_staff_info_error), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private class LoadParseBookThread extends Thread {
        private String url;

        //pass the url to the constructor
        public LoadParseBookThread(String urlStr) {
            url = urlStr;
        }

        public void run() {
            Log.e(this, "LoadParseBookThread run(): url = " + url);
            Message msg = Message.obtain();
            String result = BookUtil.getHttpRequest(url);
            Log.e(this, "getHttpRequest(): " + result);
            try {
                Book book = new BookUtil().parseBookInfo(result);
                //send the message to UI thread, notify the downloading info.
                msg.obj = book;
            } catch (Exception e) {
                e.printStackTrace();
            }
            mHandler.sendMessage(msg);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            showDialog(DATE_DIALOG_ID);
        }
        return true;
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            //set the editText, the hard code need to change strings later.
            mBookBoughtDate.setText(new StringBuilder()
                    .append(mYear).append("/")
                    .append(mMonth + 1).append("/")//should + 1, the month start 0.
                    .append(mDay));
        }
    };

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        //Toast.makeText(BooksPutIn.this, parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
        mSeleCategory = parent.getItemAtPosition(pos).toString();
        posCategoryIndex = pos;
        Log.e(this, posCategoryIndex + ":  mSeleCategory");
        //mBooksInfoWrap.setBooKCategory(parent.getItemAtPosition(pos).toString());//get the book category.
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_books_put_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,mDateSetListener,mYear, mMonth, mDay);
        }
        return null;
    }

    private void saveBooksInfo(Book book) {
        //below info will save to data base.
        mBooksInfoWrap.setId(book.getId());
        mBooksInfoWrap.setTitle(book.getTitle());
        mBooksInfoWrap.setSubTitle(book.getSubTitle());
        mBooksInfoWrap.setAuthor(book.getAuthor());
        mBooksInfoWrap.setPublisher(book.getPublisher());
        mBooksInfoWrap.setPublishDate(book.getPublishDate());
        mBooksInfoWrap.setISBN(book.getISBN());
        mBooksInfoWrap.setPrice(book.getPrice());
        mBooksInfoWrap.setBitmap(book.getBitmap());

        mBooksInfoWrap.setPage(book.getPage());
        mBooksInfoWrap.setRate(book.getRate());
        mBooksInfoWrap.setTag(book.getTag());

        //below info is not save to data base
        mBooksInfoWrap.setAuthorInfo(book.getAuthorInfo());
        //and ignore the others info, not save. such as
        //Content, Summary... ...
    }

    /**
     * only for showing base info to user
     * @param book
     */
    private void format2text(Book book) {
        //mTextScanIsbn;
        //at present only focus on below info:
        //ISBN, title and subtitle, author, publisher, price.

        StringBuffer scanResult = new StringBuffer();
        //isbn
        scanResult.append(book.getISBN());
        scanResult.append("\n");
        //title
        scanResult.append(book.getTitle());
        scanResult.append("\n");
        //subtitle
        //Log.e(this, "getSubTitle = " + book.getSubTitle().isEmpty() );
        if (book.getSubTitle() != null && !book.getSubTitle().isEmpty()) {
            //Log.e(this, "====here======");
            scanResult.append(book.getSubTitle());
            scanResult.append("\n");
        }
        //author
        scanResult.append(book.getAuthor());
        scanResult.append("\n");

        //publisher
        scanResult.append(book.getPublisher());
        scanResult.append("\n");

        //publish date
        scanResult.append(book.getPrice());

        Log.e(this, "RESULT: " + scanResult.toString() + "\n\n");
        mTextScanIsbn.setText(scanResult.toString());
    }

    /**
     * In our app, the category index is only 4 digits,
     * like this 0001, 0002, ... ... 1000 .
     * So need to add something transformation. User input 1, the out should be 0001.
     * @param strIndex
     * @return
     * Now, this function isn't be used yet.
     */
    private String formatCategoryIndex(String strIndex) {
        if (strIndex == null || strIndex.isEmpty())
            return null;
        String result = "";
        strIndex.trim();
        return null;
    }

/*
    //not used yet, see @Log.java
    public static void log(String str) {
        Log.e(TAG, str);
    }
*/

    private void dump(Book book) {
        //firstly, book's isbn info
        Log.e(this, "======== DUMP START ============= \n");
        Log.e(this, " Book id: " + book.getId()
        +"\n Book ISBN: " + book.getISBN()
        +"\n Book Title: " + book.getTitle()
        +"\n Book SubTitle: " + book.getSubTitle()
        +"\n Book Author: " + book.getAuthor()
        +"\n Book Publisher: " + book.getPublisher()
        +"\n Book Publish date: " + book.getPublishDate()
        +"\n Book Price: " + book.getPrice()

        +"\n Book bitmap: " + book.getBitmap()
        +"\n Book pages: " + book.getPage()
        +"\n Book rates: " + book.getRate()
        +"\n Book tags: " + book.getTag()
        );


        //secondly, the other book info.
        Log.e(this, " Book Category: " + book.getBooKCategory()
        +"\n Book Category Id: " + book.getBookCategoryId()
        +"\n Actual Price: " + book.getBookActualPrice()
        +"\n Bought Date: " + book.getBookBoughtDate());


        //third, the staff who bought book.
        Log.e(this, " Staff id: " + book.getBookBoughtStaffId()
        +"\n Staff name: " + book.getBookApplicantName()
        +"\n Staff email: " + book.getBookBoughtStaffEmail()
        +"\n Staff dep: " + book.getBookApplicantDep());

        Log.e(this, "======== DUMP END ============= \n");
    }
}
