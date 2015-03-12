package com.ckt.shrimp.wosaosao;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ckt.shrimp.controller.BookController;


public class ShowBorrowActivity extends ActionBarActivity implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{

    private ListView displayAll;
    private BookController bookController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_borrow);
        displayAll = (ListView)findViewById(R.id.lv_borrow);

        bookController = new BookController(this);
        BorrowDetailActivity.controller = bookController;
        Cursor cursor =bookController.queryAllBorrow();
        Toast.makeText(this,"count :" + cursor.getCount(),Toast.LENGTH_SHORT).show();
        displayAll.setAdapter(new MyCursorAdapter(this,R.layout.list_row,cursor));
        displayAll.setOnItemClickListener(this);
        displayAll.setOnItemLongClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //TextView bookID=(TextView)view.findViewById(R.id.book_id);
        /*Intent borrowDetail = new Intent(ShowBorrowActivity.this,BorrowDetailActivity.class);
        borrowDetail.putExtra("bookid",bookID.getText().toString());*/
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }


    public class MyCursorAdapter extends CursorAdapter{
        private int resource_id = 0;
        public MyCursorAdapter(Context context, int resource, Cursor cursor) {
            super(ShowBorrowActivity.this,cursor);
            resource_id = resource;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (resource_id == 0){
                return null;
            }
            return inflater.inflate(resource_id,viewGroup,false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            //TextView bookId =  (TextView)view.findViewById(R.id.book_id);
            TextView staffId =  (TextView)view.findViewById(R.id.staff_id);
            TextView bookName =  (TextView)view.findViewById(R.id.book_name);
            TextView category =  (TextView)view.findViewById(R.id.book_category);
            TextView categoryId =  (TextView)view.findViewById(R.id.book_category_id);
            TextView applicant =  (TextView)view.findViewById(R.id.book_applicant);
            TextView appliDep =  (TextView)view.findViewById(R.id.appli_dep);
            TextView author =  (TextView)view.findViewById(R.id.book_author);
            TextView boughtDate =  (TextView)view.findViewById(R.id.book_bought_date);
            TextView price =  (TextView)view.findViewById(R.id.book_price);
            TextView borrower =  (TextView)view.findViewById(R.id.borrower);
            TextView borrowingDate =  (TextView)view.findViewById(R.id.borrwing_date);
            if (cursor != null) {
                //bookId.setText(cursor.getString(cursor.getColumnIndex("book_id")));
                bookName.setText(cursor.getString(cursor.getColumnIndex("title")));
                staffId.setText(cursor.getString(cursor.getColumnIndex("staff_id")));
                category.setText(cursor.getString(cursor.getColumnIndex("category")));

                categoryId.setText(cursor.getString(cursor.getColumnIndex("category_id")));
                applicant.setText(cursor.getString(cursor.getColumnIndex("applicant")));

                price.setText(cursor.getString(cursor.getColumnIndex("price")));
                appliDep.setText(cursor.getString(cursor.getColumnIndex("applicant_dep")));
                author.setText(cursor.getString(cursor.getColumnIndex("author")));
                borrower.setText(cursor.getString(cursor.getColumnIndex("borrower")));
                borrowingDate.setText(cursor.getString(cursor.getColumnIndex("borrowing_date")));
                boughtDate.setText(cursor.getString(cursor.getColumnIndex("bought_date")));
            }
        }

    }
}
