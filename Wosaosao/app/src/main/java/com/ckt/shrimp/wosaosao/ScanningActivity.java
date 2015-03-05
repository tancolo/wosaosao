package com.ckt.shrimp.wosaosao;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;
import com.zxing.activity.CaptureActivity;



public class ScanningActivity extends ActionBarActivity implements View.OnClickListener {
    private Button mButtonScanISbn;
    private Button mButtonScanStuff;

    private TextView mTextScanIsbn;
    private TextView mTextScanStuff;

    private static final int RESULT_ISBN = 1;
    private static final int RESULT_STUFF = 2;
    private static final String TAG = "DEBUG_SAO";
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
        //处理扫描结果（在界面上显示）
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            if (RESULT_ISBN == requestCode) {
                mTextScanIsbn.setText(scanResult);
            }else {
                mTextScanStuff.setText(scanResult);
            }
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

    public void log(String str) {
        Log.d(TAG, str);
    }
}
