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
import android.util.Log;
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
import com.ckt.shrimp.utils.Book;
import com.ckt.shrimp.utils.BookUtil;
import java.util.Calendar;

import com.ckt.shrimp.utils.ParseAndWriteInfo;
import com.zxing.activity.CaptureActivity;

public class BooksPutIn extends ActionBarActivity implements OnItemSelectedListener, View.OnClickListener,
 View.OnTouchListener {
    private BookController  bookController;
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
        bookController = new BookController(this);

        //save the category id, it must be english.
        /** The category id is eng string:
         *  "综合"， -->  CKT-CD ZH-0001
         *  "研发",  --> CKT-CD YF-BC-0001
         *  "设计",  --> CKT-CD YF-SJ-0001
         *  "网络",  --> CKT-CD YF-WL-0001
         *  "测试".  --> CKT-CD YF-CS-0001
         */
        //So, need to judge category is string. should get the array strings.R.id.book_category_spinner_eng
        mSeleCategoryEng = this.getResources().getStringArray(R.array.book_category_spinner_eng);
        //only for debugging
        if (mSeleCategoryEng != null ) {
            int count = mSeleCategoryEng.length;
            for (int i = 0; i < count; i++) {
                log("mSeleCategoryEng[ " + i + "] = " + mSeleCategoryEng[i]);
            }
        }//debug-end
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            Book book = (Book)msg.obj;
            //进度条消失
            mProgressDialog.dismiss();
            if (book == null) {
                Toast.makeText(BooksPutIn.this, "没有找到这本书", Toast.LENGTH_LONG).show();
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
        int viewId = view.getId();
        switch(viewId) {
            case R.id.scan_ISBN:
                //打开扫描界面扫描条形码
                Intent openCameraIntent_ISBN = new Intent(BooksPutIn.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent_ISBN, BookUtil.RESULT_ISBN);
                break;
            case R.id.scan_staff_info:
                //call zxing API
                //打开扫描界面扫描二维码
                Intent openCameraIntent_stuff = new Intent(BooksPutIn.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent_stuff, BookUtil.RESULT_STUFF);
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
                if(bookController != null){
                 Uri u =  bookController.addBook(mBooksInfoWrap);
                  if (u != null){
                      Toast.makeText(this,"add success",Toast.LENGTH_SHORT).show();
                  }else {
                      Toast.makeText(this,"add not success",Toast.LENGTH_SHORT).show();
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

        log("requestCode = " + requestCode +", resultCode = " + resultCode);
        //处理扫描结果（在界面上显示）仅仅是用于测试，后续有显示的地方
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            if (BookUtil.RESULT_ISBN == requestCode) {// value 1 means ISBN.
                mTextScanIsbn.setText(scanResult);
                //判断网络是否连接
                if(BookUtil.isNetworkConnected(this)) {
                    mProgressDialog = new ProgressDialog(this);
                    mProgressDialog.setMessage("请稍候，正在读取信息...");
                    mProgressDialog.show();
                    String urlstr = "https://api.douban.com/v2/book/isbn/:"+scanResult;
                    log("urlstr : " + urlstr);

                    //扫到ISBN后，启动下载线程下载图书信息
                    new LoadParseBookThread(urlstr).start();
                }else {
                    Toast.makeText(this, "网络异常，请检查你的网络连接", Toast.LENGTH_LONG).show();
                }
            }else { // value 2 means two dimension code about stuff info.
                mTextScanStuff.setText(scanResult);
                //need to parse staff info from the 2D code. static function
                //need to pass the BookWrap
                if(BookUtil.RETURN_OK != ParseAndWriteInfo.parseStaffInfo(scanResult, mBooksInfoWrap, null)) {
                    Toast.makeText(BooksPutIn.this, "获取职员二维码信息有误，请重新扫描", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private class LoadParseBookThread extends Thread {
        private String url;

        //通过构造函数传递url地址
        public LoadParseBookThread(String urlstr) {
            url = urlstr;
        }

        public void run() {
            log("LoadParseBookThread run(): url = " + url);
            Message msg = Message.obtain();
            String result = BookUtil.getHttpRequest(url);
            log("getHttpRequest(): " + result);
            try {
                Book book = new BookUtil().parseBookInfo(result);
                //给主线程UI界面发消息，提醒下载信息，解析信息完毕
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
        log(posCategoryIndex + ",  mSeleCategory");
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
        mBooksInfoWrap.setISBN(book.getISBN());
        mBooksInfoWrap.setTitle(book.getTitle());
        mBooksInfoWrap.setSubTitle(book.getSubTitle());
        mBooksInfoWrap.setAuthor(book.getAuthor());
        mBooksInfoWrap.setPublisher(book.getPublisher());
        mBooksInfoWrap.setPrice(book.getPrice());//may be not save, because the actual price.

        //below info is not save to data base
        mBooksInfoWrap.setPage(book.getPage());
        mBooksInfoWrap.setAuthorInfo(book.getAuthorInfo());
        mBooksInfoWrap.setPublishDate(book.getPublishDate());
        //and ignore the others info, not save. such as
        //Rate, tag, Content, Summary, bitmap
        // ... ...
    }

    /**
     * only for showing base info to user
     * @param book
     */
    private void format2text(Book book) {
        //mTextScanIsbn;
        //at present only focus on below info:
        //ISBN, title and subtitle, author, publisher, price.

        String scanResult = "";
        //isbn
        scanResult += book.getISBN();
        scanResult += "\n";
        //title
        scanResult += book.getTitle();
        scanResult += "\n";
        //subtitle
        log("getSubTitle = " + book.getSubTitle().isEmpty() );
        if (book.getSubTitle() != null && !book.getSubTitle().isEmpty()) {
            log("====here======");
            scanResult += book.getSubTitle();
            scanResult += "\n";
        }
        //author
        scanResult += book.getAuthor();
        scanResult += "\n";

        //publisher
        scanResult += book.getPublisher();
        scanResult += "\n";

        //publish date
        scanResult += book.getPrice();

        log("RESULT: " + scanResult);
        mTextScanIsbn.setText(scanResult.toString());
    }

    /**
     * In our app, the category index is only 4 digits,
     * like this 0001, 0002, ... ... 1000 .
     * So need to add something transformation. User input 1, the out should be 0001.
     * @param strIndex
     * @return
     */
    private String formatCategoryIndex(String strIndex) {
        if (strIndex == null || strIndex.isEmpty())
            return null;
        String result = "";
        strIndex.trim();
        return null;
    }

    public static void log(String str) {
        Log.e(TAG, str);
    }

    private void dump(Book book) {
        //firstly, book's isbn info
        log("======== DUMP START ============= \n");
        log(" Book id: " + book.getId()
        +"\n Book ISBN: " + book.getISBN()
        +"\n Book Title: " + book.getTitle()
        +"\n Book SubTitle: " + book.getSubTitle()
        +"\n Book Author: " + book.getAuthor()
        +"\n Book Publisher: " + book.getPublisher()
        +"\n Book Price: " + book.getPrice());

        //secondly, the other book info.
        log(" Book Category: " + book.getBooKCategory()
        +"\n Book Category Id: " + book.getBookCategoryId()
        +"\n Actual Price: " + book.getBookActualPrice()
        +"\n Bought Date: " + book.getBookBoughtDate());

        //third, the staff who bought book.
        log(" Staff id: " + book.getBookBoughtStaffId()
        +"\n Staff name: " + book.getBookApplicant()
        +"\n Staff email: " + book.getBookBoughtStaffEmail()
        +"\n Staff dep: " + book.getBookApplicantDep());

        log("======== DUMP END ============= \n");
    }
}
