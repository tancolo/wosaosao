package com.ckt.shrimp.wosaosao;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ckt.shrimp.database.MyBookDataBaseHelper;
import com.ckt.shrimp.utils.Book;
import com.ckt.shrimp.utils.Staff;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ckt.shrimp.export2file.TestLoadRawTxtFile;


public class WosaoActivity extends ActionBarActivity implements View.OnClickListener {

    private Button mBookLending;
    private Button mBookReturn;
    private Button mBookExport;
    private Button mBookPutIn;
    private Button mStaffPutIn;
	
	//jerry
    private Button mBorrowTable;
    MyBookDataBaseHelper bookHelper;

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
		//jerry
        mBorrowTable = (Button) findViewById(R.id.borrow_table);
		//jerry
        //mBorrowTable.setOnClickListener(this);
        //dbInit();
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        switch(viewId) {
            case R.id.book_input:
                Intent intentBooksPutIn = new Intent(WosaoActivity.this, BooksPutIn.class);
                startActivity(intentBooksPutIn);
                break;
            case R.id.staff_input:
                break;
            case R.id.book_lending:
            case R.id.book_return:
                Intent intent = new Intent(WosaoActivity.this, ScanningActivity.class);
                startActivity(intent);
                break;
            case R.id.book_export:
                //TestLoadRawTxtFile txtFile = new TestLoadRawTxtFile();
                //txtFile.loadRawTxt();
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
	
	//jerry add
    private void dbInit() {

        bookHelper = new MyBookDataBaseHelper(this,"saosao",null,1);
        insertBookAndStaff();

    }
	private void insertBookAndStaff() {
        Book b = new Book();
        b.setId("00001");
        b.setStaffId("N001");
        //wiki demo.....
        b.setBooKCategory("综合");
        b.setTitle("Thinking in java");
        b.setBookCategoryId("CKT-CD ZH-001");
        b.setAuthor("本.麦凯");
        b.setBookBoughtDate("2011/07/09");
        b.setBookApplicantDep("研发部");
        b.setBookApplicant("杨雨");
        b.setPrice("46.5");
        b.setBookBorrowerDep("软件部");
        b.setBookBorrower("Jerry");
        b.setBookBorrowingDate("2015/03/11");


        ContentValues v = new ContentValues();
        v.put("book_id",b.getId());
        v.put("staff_id",b.getStaffId());
        v.put("category",b.getBooKCategory());
        v.put("title",b.getTitle());
        v.put("category_id",b.getBookCategoryId());
        v.put("author",b.getAuthor());
        v.put("bought_date",b.getBookBoughtDate());
        v.put("applicant_dep",b.getTitle());
        v.put("applicant",b.getAuthor());
        v.put("price",b.getBookBoughtDate());
        v.put("borrower_dep",b.getTitle());
        v.put("borrower",b.getAuthor());
        v.put("borrowing_date",b.getId());


        //insert staff ............................
        Staff e = new Staff();
        e.setId("N001");
        e.setName("jerry");
        e.setDepartment("软件部");

        /*-----------borrow --star -----------*/

        ContentValues vstaff = new ContentValues();
        vstaff.put("staff_id",e.getId());
        vstaff.put("name",e.getName());
        vstaff.put("staff_department",e.getDepartment());

        bookHelper.getWritableDatabase().insert("book",null,v);
        bookHelper.getWritableDatabase().insert("staff",null,vstaff);
        Cursor c = bookHelper.getReadableDatabase().rawQuery("select book.*,staff.* from book,staff  where book.staff_id=staff.staff_id",null);
        while (c.moveToNext()){
          Toast.makeText(this,"book title :"+c.getString(c.getColumnIndex("title")),Toast.LENGTH_SHORT).show();
        }
	}
	
}
