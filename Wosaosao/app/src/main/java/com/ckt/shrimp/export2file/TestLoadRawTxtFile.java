package com.ckt.shrimp.export2file;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by ckt on 3/9/15.
 */
public class TestLoadRawTxtFile {

    private static final String FILENAME = "TestRawTxt.txt";
    private static final String WRITE_FILENAME = "TestRawTxt_write.txt";
    private static final String TAG = "TestLoadRawTxtFile";

    private ArrayList<BooksInfoWrap> result = new ArrayList<BooksInfoWrap>();

    public int loadRawTxt () {
        //load the test data file.

        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
        log("FILENAME = " + FILENAME +",  file = " + file);

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                // 然后将文件转化成字节流读取
                FileReader reader = new FileReader(file);
                //log("debug error 01: reader = " + reader);
                BufferedReader in = new BufferedReader(reader);
                //log("debug error 02 : in = " + in);
                long currentTime = -1;

                //read one line one time.
                String line = in.readLine();
                //log("debug error 03 : line = " + line);
                while (line != null) {
                    BooksInfoWrap rec = parseLine(line);
                    if (rec == null) {
                        Log.e(TAG, "could not parse line: '" + line + "'");
                        break;
                    } else {
                        //Log.i(TAG,"line=" + rec);
                        result.add(rec);
                    }
                    line = in.readLine();
                }
                in.close();
                //lastLog = (booksInfoWarp[]) result.toArray(new booksInfoWarp[result.size()]);
                //return lastLog;

            } catch (Exception e) {
                log("read Error!!!");
            }
        } else {
            // 此时SDcard不存在或者不能进行读写操作的
            log("此时SDcard不存在或者不能进行读写操作!!");
        }

        //get the result.size() and dump the info.
        //dump start
        log("result.size() = " + result.size());
        int index = 1;
        for (BooksInfoWrap tBookInfo : result) {
        //for (int index = 0; index < result.size(); index++) {
            log( (index++) +", " + tBookInfo.mBooKCategory + ", " + tBookInfo.mBooKTitle +", " + tBookInfo.mBookCategoryId
                    +", " + tBookInfo.mBooKAuthor +", " + tBookInfo.mBookBoughtDate +", " + tBookInfo.mBookApplicantDep
                    +", " + tBookInfo.mBookApplicant +", " + tBookInfo.mBookActualPrice +", " + tBookInfo.mBookBorrowerDep
                    +", " + tBookInfo.mBookBorrower +", " + tBookInfo.mBookBorrowingDate );
        }
        //dump end

        //test the write function
        log("DEBUG write date to txt file");
        //writeDb2Wiki();

        //write the data base to wiki as the special format. See @TestExportWiki.java
        new TestExportWiki().writeDataBase2Wiki(result);

        return  0;
    }

    //解析每一行
    private BooksInfoWrap parseLine(String line) {
        if (line == null)
            return null;
        String[] split = line.split("[,]");
        //log("split[0]: " + split[0]);
        log("DEBUG split.length = " + split.length);
        if (split.length < 2 ) {
            return null;
        }
        if (split[0].equals("###")) { //only using for test "###, ###" and break the while loop.
            return null;
        }
        try {
            BooksInfoWrap tBookInfo = new BooksInfoWrap();
            //get the values from RawTxt, use "," to split.
            tBookInfo.mBooKCategory = split[1];
            tBookInfo.mBooKTitle = split[2];
            tBookInfo.mBookCategoryId = split[3];
            tBookInfo.mBooKAuthor = split[4];

            tBookInfo.mBookBoughtDate = split[5];
            tBookInfo.mBookApplicantDep = split[6];
            tBookInfo.mBookApplicant = split[7];
            tBookInfo.mBookActualPrice = split[8];
            tBookInfo.mBookBorrowerDep = split[9];
            tBookInfo.mBookBorrower = split[10];
            tBookInfo.mBookBorrowingDate = split[11];
            //dump book info.
            /**
            log(split[0] +", " + tBookInfo.mBooKCategory + ", " + tBookInfo.mBooKTitle +", " + tBookInfo.mBookCategoryId
            +", " + tBookInfo.mBooKAuthor +", " + tBookInfo.mBookBoughtDate +", " + tBookInfo.mBookApplicantDep
            +", " + tBookInfo.mBookApplicant +", " + tBookInfo.mBookActualPrice +", " + tBookInfo.mBookBorrowerDep
            +", " + tBookInfo.mBookBorrower +", " + tBookInfo.mBookBorrowingDate );
            */
            //end dump
            return tBookInfo;
        } catch (Exception e) {
            Log.e(TAG,"Invalid format in line '"+line+"'");
            return null;
        }
    }

    /**
     * write the data base to txt file, that can be used to copy to Wiki.
     * @return 0
     */
    public int writeDb2Wiki() {
        File file = new File(Environment.getExternalStorageDirectory(), WRITE_FILENAME);
        log("FILENAME = " + WRITE_FILENAME +",  file = " + file);

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                // 然后将文件转化成字节流读取
                FileWriter writer = new FileWriter(file);
                //log("debug error 01: reader = " + reader);
                BufferedWriter out = new BufferedWriter(writer);
                //log("debug error 02 : in = " + in);

                //write one line one time.
                //log("debug error 03 : line = " + line);
                int index = 1;
                for (BooksInfoWrap tBookInfo : result) {
                    String tString = "";
                    tString += String.valueOf(index++);
                    tString += ", ";
                    tString += tBookInfo.mBooKCategory;
                    tString += ", ";
                    tString += tBookInfo.mBooKTitle;
                    tString += ", ";
                    tString += tBookInfo.mBookCategoryId;
                    tString += ", ";
                    tString += tBookInfo.mBooKAuthor;
                    tString += ", ";
                    tString += tBookInfo.mBookBoughtDate;
                    tString += ", ";
                    tString += tBookInfo.mBookApplicantDep;
                    tString += ", ";
                    tString += tBookInfo.mBookApplicant;
                    tString += ", ";
                    tString += tBookInfo.mBookActualPrice;
                    tString += ", ";
                    tString += tBookInfo.mBookBorrowerDep;
                    tString += ", ";
                    tString += tBookInfo.mBookBorrower;
                    tString += ", ";
                    tString += tBookInfo.mBookBorrower;

                    out.write(tString);
                    out.newLine();
                    out.flush();
                }
                out.close();

            } catch (Exception e) {
                log("read Error!!!");
                e.printStackTrace();
            }
        } else {
            // 此时SDcard不存在或者不能进行读写操作的
            log("此时SDcard不存在或者不能进行读写操作!!");
        }

        return 0;
    }

    private static void log(String str) {
        Log.d(TAG, str);
    }
}


