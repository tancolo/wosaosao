package com.ckt.shrimp.wosaosao;

/** Now, this class is only use to scan ISBN and two-dimension code of staff.
 * But don't connect internet, only search info in Date base.
 * If you want to see the process of searching ISBN info by internet, Pls see @BooksPutIn.java.
 * And the 2d code don't use internet and DB.
 */
import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.zxing.activity.CaptureActivity;
import com.ckt.shrimp.utils.*;


public class ScanningActivity extends ActionBarActivity implements View.OnClickListener {
    private Button mButtonScanISbn = null;
    private Button mButtonScanStuff = null;
    private Button mButtonOK = null;

    private TextView mTextScanIsbn = null;
    private TextView mTextScanStaff = null;
    private Book mBorrowedBookInfo = new Book();

    private static final String TAG = "DEBUG_SAO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaning);

        //init the layout
        mButtonScanISbn = (Button)findViewById(R.id.scan_ISBN);
        mButtonScanStuff = (Button)findViewById(R.id.scan_staff_info);
        mButtonOK = (Button)findViewById(R.id.book_lending_ok);

        mTextScanIsbn = (TextView)findViewById(R.id.scan_ISBN_result);
        mTextScanStaff = (TextView)findViewById(R.id.scan_stuff_result);

        mButtonScanISbn.setOnClickListener(this);
        mButtonScanStuff.setOnClickListener(this);
        mButtonOK.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //both call the same api of zxing.
        int viewId = view.getId();
        switch(viewId) {
            case R.id.scan_ISBN:
                //打开扫描界面扫描条形码
                Intent openCameraIntent_ISBN = new Intent(ScanningActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent_ISBN, BookUtil.RESULT_ISBN);
                break;
            case R.id.scan_staff_info:
                //call zxing API
                //打开扫描界面扫描二维码
                Intent openCameraIntent_stuff = new Intent(ScanningActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent_stuff, BookUtil.RESULT_STUFF);
                break;
            case R.id.book_lending_ok:
                //update the book info and borrower's info to DB
                //need jerry to implement.
                Toast.makeText(ScanningActivity.this, "need jerry to implement", Toast.LENGTH_LONG).show();
                break;
            default:
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        log("requestCode = " + requestCode +", resultCode = " + resultCode);
        //Here, we only get the isbn or 2d code info!!!
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            boolean isIsbn = scanResult.substring(0, 3).equals(BookUtil.ISBN_START_STR);//Isbn start with "978"
            log("isIsbn = " + isIsbn +",  sub(0, 3) = " + scanResult.substring(0, 3));

            if (BookUtil.RESULT_ISBN == requestCode) {// value 1 means ISBN.
                mTextScanIsbn.setText(scanResult);
                //should search Book's info in DataBase.
                //Need 2 filed: book's isbn, book category id(only one per book).
                //At present, for testing, I only get the book's isbn to search book's info in DB.
                //The category id, need to be created later.
                if (isIsbn) {
                    mBorrowedBookInfo.setISBN(scanResult);
                    //mBorrowedBookInfo.setBookCategoryId("CKT-CD YF-BC-001");//only test.
                    //call function of searching DB.
                    searchInfoByBookId(mBorrowedBookInfo);
                }

            }else { // value 2 means two dimension code about stuff info.
                mTextScanStaff.setText(scanResult);//for showing staff info
                //parser the scan result.
                if(BookUtil.RETURN_OK != ParseAndWriteInfo.parseStaffInfo(scanResult, mBorrowedBookInfo, null)) {
                    Toast.makeText(ScanningActivity.this, "获取职员二维码信息有误，请重新扫描", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void searchInfoByBookId(Book sBook) {
        //need jerry to implement.
        Toast.makeText(ScanningActivity.this, "need jerry to implement", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scaning, menu);
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

    public static void log(String str) {
        Log.e(TAG, str);
    }

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
}
