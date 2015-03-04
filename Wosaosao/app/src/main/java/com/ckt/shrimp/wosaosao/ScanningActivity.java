package com.ckt.shrimp.wosaosao;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ScanningActivity extends ActionBarActivity implements View.OnClickListener {
    private Button mButtonScanISbn;
    private Button mButtonScanStuff;

    private TextView mTextScanIsbn;
    private TextView mTextScanStuff;

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
            case R.id.scan_staff_info:
                //call zxing API
                break;
            default:
                break;

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
}
