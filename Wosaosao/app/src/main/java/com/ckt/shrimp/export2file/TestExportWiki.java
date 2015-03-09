package com.ckt.shrimp.export2file;
/** This class will write the data base to txt file, and the format like this:(wiki format)
 */
/*
[[Cd_ck_telecom_library|返回上级]]  ===> 1) start, not be changed

{|cellpadding="5" cellspacing="1" border="0"                    ===> 2) not be changed
|- align="center" style="background-color:#555555; font-weight: bold; color:#ffffff"                ===> 3) not be changed
|序号	||类别||书名||编号||作者||购买时间||申请部门||购买申请人||金额||借取部门||借取人||借出时间             ===> 4) not be changed
|- style="background-color: #0088bb;color:#ffffff;"                                                 ===> 5) not be changed
|1||综合||阿米巴经营||CKT-CD ZH-001	||||2011/7/29||研发部||杨宇彤||28.00 ||未知||未知||未知                ===> 7) |
|- style="background-color: #0088bb;color:#ffffff;"
	|	2	||	综合	||	阿米巴经营	||	CKT-CD ZH-002	||		||	2011/7/29	||	研发部	||	杨宇彤	||	28.00 	||		||		||          ===> 8) ||
|- style="background-color: #0088bb;color:#ffffff;"
	|	3	||	综合	||	阿米巴经营	||	CKT-CD ZH-003	||		||	2011/7/29	||	研发部	||	杨宇彤	||	28.00 	||	SP	||	兰书涛	||	2014/11/4
|- style="background-color: #0088bb;color:#ffffff;"
	|	4	||	综合	||	阿米巴经营	||	CKT-CD ZH-004	||		||	2011/7/29	||	研发部	||	杨宇彤	||	28.00 	||		||		||
|}                  ====> 6) end, not be changed


[[Cd_ck_telecom_library|返回上级]]   ===> 1) not be changed
*/

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by ckt on 3/9/15.
 */
public class TestExportWiki {

    //1)
    private static final String TITLE_FORMAT = "[[Cd_ck_telecom_library|返回上级]] \n";
    //2)
    private static final String CELLPENDING_FORMAT_START = "{|cellpadding=\"5\" cellspacing=\"1\" border=\"0\" \n";
    //3)
    private static final String COLOR_FORMAT = "|- align=\"center\" style=\"background-color:#555555; font-weight: bold; color:#ffffff\" \n";
    //4)
    private static final String INDEX_TITLE_FORMAT = "|序号||类别||书名||编号||作者||购买时间||申请部门||购买申请人||金额||借取部门||借取人||借出时间 \n";
    //5)
    private static final String STYLE_FORMAT = "|- style=\"background-color: #0088bb;color:#ffffff;\" ";
    //6)
    private static final String CELLPENDING_FORMAT_END = "|} \n";

    // 7) "|"
    private static final String SINGLE_UPRIGHT = "|";
    // 8) "||"
    private static final String DUAL_UPRIGHT = "||";

    private static final String TAG = "TestExportWiki";
    private static final String WRITE_FILENAME = "TestRawTxt_write.txt";

    /**
     * write the data base to txt file, that can be used to copy to Wiki.
     * @return 0
     */
    public int writeDataBase2Wiki(ArrayList<BooksInfoWrap> result) {
        File file = new File(Environment.getExternalStorageDirectory(), WRITE_FILENAME);
        log("FILENAME = " + WRITE_FILENAME +",  file = " + file);
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                //new FileWrite object
                FileWriter writer = new FileWriter(file);
                BufferedWriter out = new BufferedWriter(writer);
                //the string contain all content about wiki format.
                String formatString = "";
                //1) should write the title format
                formatString += TITLE_FORMAT;
                //2) should write the CELLPENDING_FORMAT_START
                formatString += CELLPENDING_FORMAT_START;
                //3) should write the COLOR_FORMAT
                formatString += COLOR_FORMAT;
                //4) should write the INDEX_TITLE_FORMAT
                formatString += INDEX_TITLE_FORMAT;
                //5) should write the STYLE_FORMAT
                formatString += STYLE_FORMAT;

                //need to write and flush string.
                out.write(formatString);
                out.newLine();
                out.flush();
                
                //write one line one time, the contents
                int index = 1;
                int count = result.size();
                for (BooksInfoWrap tBookInfo : result) {
                    String tString = SINGLE_UPRIGHT; // "|"
                    tString += String.valueOf(index++);
                    tString += DUAL_UPRIGHT;//  "||"
                    tString += tBookInfo.mBooKCategory;
                    tString += DUAL_UPRIGHT;
                    tString += tBookInfo.mBooKTitle;
                    tString += DUAL_UPRIGHT;
                    tString += tBookInfo.mBookCategoryId;
                    tString += DUAL_UPRIGHT;
                    tString += tBookInfo.mBooKAuthor;
                    tString += DUAL_UPRIGHT;
                    tString += tBookInfo.mBookBoughtDate;
                    tString += DUAL_UPRIGHT;
                    tString += tBookInfo.mBookApplicantDep;
                    tString += DUAL_UPRIGHT;
                    tString += tBookInfo.mBookApplicant;
                    tString += DUAL_UPRIGHT;
                    tString += tBookInfo.mBookActualPrice;
                    tString += DUAL_UPRIGHT;
                    tString += tBookInfo.mBookBorrowerDep;
                    tString += DUAL_UPRIGHT;
                    tString += tBookInfo.mBookBorrower;
                    tString += DUAL_UPRIGHT;
                    tString += tBookInfo.mBookBorrower;

                    if ((count--) != 1) {
                        tString += "\n";
                        tString += STYLE_FORMAT;
                    }

                    out.write(tString);
                    out.newLine();
                    out.flush();
                }
                //write the end format
                //6) should write the CELLPENDING_FORMAT_END
                formatString = "";
                formatString += CELLPENDING_FORMAT_END;
                formatString += "\n";
                formatString += TITLE_FORMAT;

                out.write(formatString);
                //out.newLine();
                out.flush();

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
