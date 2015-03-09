package com.ckt.shrimp.wosaosao;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ckt.shrimp.export2file.TestLoadRawTxtFile;


public class WosaoActivity extends ActionBarActivity implements View.OnClickListener {

    private Button mBookLending;
    private Button mBookReturn;
    private Button mBookExport;
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

        mBookLending.setOnClickListener(this);
        mBookReturn.setOnClickListener(this);
        mBookExport.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        switch(viewId) {
            case R.id.book_lending:
            case R.id.book_return:
                Intent intent = new Intent(WosaoActivity.this, ScanningActivity.class);
                startActivity(intent);
                break;
            case R.id.book_export:
                TestLoadRawTxtFile txtFile = new TestLoadRawTxtFile();
                txtFile.loadRawTxt();
                break;
            default:
                break;

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
