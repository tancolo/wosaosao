package com.ckt.shrimp.wosaosao;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ckt.shrimp.controller.StaffController;
import com.ckt.shrimp.utils.BookUtil;
import com.ckt.shrimp.utils.ParseAndWriteInfo;
import com.ckt.shrimp.utils.Staff;
import com.zxing.activity.CaptureActivity;

public class StaffPutIn extends ActionBarActivity implements View.OnClickListener{

    //definition Staff info
    private Button mButtonScanStuff;
    private TextView mTextScanStuff;
    private Button mButtonAddStaff;
    private static final String TAG = "StaffPutIn";
    private Staff mStaffInfo = new Staff();

    private static final String CAPTURE_RESULT = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_put_in);

        //init staff layout
        mButtonScanStuff = (Button)findViewById(R.id.scan_staff_info);
        mButtonAddStaff = (Button)findViewById(R.id.add_staff_info);
        mTextScanStuff = (TextView)findViewById(R.id.scan_stuff_result);

        mButtonScanStuff.setOnClickListener(this);
        mButtonAddStaff.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //both call the same api of zxing.
        int viewId = view.getId();
        switch(viewId) {
            case R.id.scan_staff_info:
                //call zxing API
                //Open the capture UI, scan the  two-dimension code.
                Intent openCameraIntent_stuff = new Intent(StaffPutIn.this, CaptureActivity.class);
                startActivityForResult(openCameraIntent_stuff, BookUtil.RESULT_STUFF);
                break;
            case R.id.add_staff_info:

                //get all books info, the class BooksInfoWrap contains isbn info and the inputting info.
                boolean isSuccessInsert = new StaffController(this).addStaff(mStaffInfo);
                if (isSuccessInsert){
                    //need to package all the toast, one function named showToast()
                    Toast.makeText(this, getResources().getString(R.string.notice_success_insert_staff), Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, getResources().getString(R.string.notice_failed_insert_staff), Toast.LENGTH_SHORT).show();
                }
                //Toast.makeText(StaffPutIn.this, "need jerry to implement", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        log("requestCode = " + requestCode +", resultCode = " + resultCode);
        //process the result of scanning, and show the result for debugging.
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(CAPTURE_RESULT);
            if (BookUtil.RESULT_STUFF == requestCode) {
                // value 2 means two dimension code about stuff info.
                mTextScanStuff.setText(scanResult);
                //need to parse staff info from the 2D code. static function
                //need to pass the BookWrap
                if(BookUtil.RETURN_OK != ParseAndWriteInfo.parseStaffInfo(scanResult, null, mStaffInfo)) {
                    Toast.makeText(StaffPutIn.this, getResources().getString(R.string.notice_get_staff_info_error), Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_staff_put_in, menu);
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
}
