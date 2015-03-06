package com.ckt.shrimp.wosaosao;

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
    private Button mButtonScanISbn;
    private Button mButtonScanStuff;

    private TextView mTextScanIsbn;
    private TextView mTextScanStuff;

    private static final int RESULT_ISBN = 1;
    private static final int RESULT_STUFF = 2;
    private static final String TAG = "DEBUG_SAO";

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaning);

        //init the layout
        mButtonScanISbn = (Button)findViewById(R.id.scan_ISBN);
        mButtonScanStuff = (Button)findViewById(R.id.scan_staff_info);

        mTextScanIsbn = (TextView)findViewById(R.id.scan_ISBN_result);
        mTextScanStuff = (TextView)findViewById(R.id.scan_stuff_result);

        mButtonScanISbn.setOnClickListener(this);
        mButtonScanStuff.setOnClickListener(this);
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            Book book= (Book)msg.obj;
            //进度条消失
            mProgressDialog.dismiss();
            if (book == null) {
                Toast.makeText(ScanningActivity.this, "没有找到这本书", Toast.LENGTH_LONG).show();
            }else {
                //should show the info of this book.
                //at present only focus on below info:
                //ISBN, name, author, publisher, price.
                format2text(book);
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
                Intent openCameraIntent_ISBN = new Intent(ScanningActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent_ISBN, RESULT_ISBN);
                break;
            case R.id.scan_staff_info:
                //call zxing API
                //打开扫描界面扫描二维码
                Intent openCameraIntent_stuff = new Intent(ScanningActivity.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent_stuff, RESULT_STUFF);
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
            if (RESULT_ISBN == requestCode) {// value 1 means ISBN.
                //mTextScanIsbn.setText(scanResult);
                //判断网络是否连接
                if(BookUtil.isNetworkConnected(this)){
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
        Log.d(TAG, str);
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
