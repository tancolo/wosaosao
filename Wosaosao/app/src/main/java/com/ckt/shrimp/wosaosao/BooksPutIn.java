package com.ckt.shrimp.wosaosao;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ckt.shrimp.export2file.*;


public class BooksPutIn extends ActionBarActivity implements OnItemSelectedListener, View.OnClickListener {

    private static  final String TAG = "BooksPutIn";
    private String format = "-";
    private static  final int CATEGORY_COUNTS = 5;
    //private static int[] mStartIndex = new int[CATEGORY_COUNTS];
    private int mIndex = 0;
    private float mfActualPrice = 0;

    private Spinner mBookSpinner = null;
    private EditText  mCategoryIndexEdit = null;
    private EditText  mActualPriceEdit = null;
    private Button mAddAllInfo = null;
    private BooksInfoWrap mBooksInfoWrap = new BooksInfoWrap();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_put_in);

        //book spinner
        mBookSpinner = (Spinner)findViewById(R.id.book_spinner);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        //        BooksPutIn.this, android.R.layout.simple_spinner_item, getData());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(BooksPutIn.this,
                R.array.book_category_spinner, android.R.layout.simple_spinner_item);
        mBookSpinner.setAdapter(adapter);

        //mBookSpinner.setOnItemClickListener();
        mBookSpinner.setOnItemSelectedListener(this);

        //category index edit text
        mCategoryIndexEdit = (EditText)findViewById(R.id.category_index);
        //actual price edit text
        mActualPriceEdit = (EditText)findViewById(R.id.actual_price);

        mAddAllInfo = (Button)findViewById(R.id.add_all_info);
        mAddAllInfo.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        //both call the same api of zxing.
        int viewId = view.getId();
        switch(viewId) {
            case R.id.scan_ISBN:
                //打开扫描界面扫描条形码
                //Intent openCameraIntent_ISBN = new Intent(ScanningActivity.this, CaptureActivity.class);
                //startActivityForResult(openCameraIntent_ISBN, RESULT_ISBN);
                break;
            case R.id.scan_staff_info:
                //call zxing API
                //打开扫描界面扫描二维码
                //Intent openCameraIntent_stuff = new Intent(ScanningActivity.this, CaptureActivity.class);
                //startActivityForResult(openCameraIntent_stuff, RESULT_STUFF);
                break;
            case R.id.add_all_info:
                //get all books info, the class BooksInfoWrap contains isbn info and the inputting info.
                mBooksInfoWrap.mBookCategoryId = "-" + mCategoryIndexEdit.getText().toString();//e.g -001
                mBooksInfoWrap.mBookActualPrice = "-" + mActualPriceEdit.getText().toString();// e.g 28.50

                break;
            default:
                break;

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        //log("selected item is : " + parent.getItemAtPosition(pos).toString());
        //Toast.makeText(BooksPutIn.this, parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
        //mSeleCategory = parent.getItemAtPosition(pos).toString();
        mBooksInfoWrap.mBooKCategory = parent.getItemAtPosition(pos).toString();
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

    public static void log(String str) {
        Log.d(TAG, str);
    }
}
