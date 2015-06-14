package com.ckt.shrimp.wosaosao;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ckt.shrimp.controller.SaoGlobal;
import com.ckt.shrimp.utils.BookUtil;
import com.ckt.shrimp.utils.Log;


public class WosaoActivity extends ActionBarActivity implements View.OnClickListener {

    private Button mBookLending;
    private Button mBookReturn;
    private Button mBookExport;
    private Button mBookPutIn;
    private Button mStaffPutIn;

    //SaoGlobal.java
    //Log.e(this, "")
    SaoGlobal mSaoGlobal;

    private static final String SCANNING_CLASS = "./ScanningActivity";
    //private static final String BOOK_TEST_CLASS = "./ScanningActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wosao);

        //init the buttons
        mBookLending = (Button)findViewById(R.id.book_lending);
        mBookReturn = (Button)findViewById(R.id.book_return);
        mBookExport = (Button)findViewById(R.id.book_export);
        
		mBookPutIn = (Button)findViewById(R.id.book_input);
        mStaffPutIn = (Button)findViewById(R.id.staff_input);

        mBookLending.setOnClickListener(this);
        mBookReturn.setOnClickListener(this);
        mBookExport.setOnClickListener(this);
        mBookPutIn.setOnClickListener(this);

        mStaffPutIn.setOnClickListener(this);

        //new class SaoGlobal
        Log.e(this, "new SaoGlobal...");
        mSaoGlobal = new SaoGlobal(this);
        mSaoGlobal.onCreate();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        Intent onClickIntent = new Intent();
        switch(viewId) {
            case R.id.book_input:
                onClickIntent.setComponent(new ComponentName(WosaoActivity.this, BooksPutIn.class));
                break;
            case R.id.staff_input:
                onClickIntent.setComponent(new ComponentName(WosaoActivity.this, StaffPutIn.class));
                break;
            case R.id.book_lending:
                onClickIntent.setComponent(new ComponentName(WosaoActivity.this, ScanningActivity.class));
                onClickIntent.putExtra(BookUtil.ACTIVITY_TYPE, BookUtil.TYPE_BORROW);
                break;
            case R.id.book_return:
                onClickIntent.setComponent(new ComponentName(WosaoActivity.this, ScanningActivity.class));
                onClickIntent.putExtra(BookUtil.ACTIVITY_TYPE, BookUtil.TYPE_RETURN);
                break;
            case R.id.book_export:
                //TestLoadRawTxtFile txtFile = new TestLoadRawTxtFile();
                //txtFile.loadRawTxt();
                break;
            default:
                break;
        }
        if (viewId != R.id.book_export ) {
            startActivity(onClickIntent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wosao, menu);
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
	
}
