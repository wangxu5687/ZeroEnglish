package com.jason.zeroenglish.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jason.zeroenglish.bean.WordInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wx on 2017/7/28.
 */

public class DBHelperAssets {

    private static final String TAG = DBHelperAssets.class.getSimpleName();

    private static final String WORD_ID = "id";
    private static final String WORD_NAME = "word";
    private static final String WORD_PHONOGRAM = "phonogram";
    private static final String WORD_TRANSLATE = "translate";
    private static final String QUERY_WORD_NAME_BY_LIMIT = "select " + WORD_NAME + " from english_word order by id limit ?,?";
    private static final String QUERY_WORD_INFO_BY_WORD_NAME = "select * from english_word where word = ?";

    private static final String DATABASE_DIR = "databases";

    private Context mContext;
    private String mAssetsDatabaseName;

    public DBHelperAssets(Context context, String assetsDatabaseName) {

        this.mContext = context;
        this.mAssetsDatabaseName = assetsDatabaseName;

    }

    public SQLiteDatabase openDatabase() {

        File database = new File(mContext.getDir(DATABASE_DIR, Context.MODE_PRIVATE), mAssetsDatabaseName);
        if (!database.exists()) {
            Log.i(TAG, "openDatabase: 数据不存在");
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = mContext.getAssets().open(mAssetsDatabaseName);
                fos = new FileOutputStream(database);
                byte[] buffer = new byte[1204];
                int len;

                while ((len = is.read(buffer, 0, buffer.length)) > 0) {
                    fos.write(buffer, 0, len);
                }


            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                //最后关闭就可以了
                try {
                    if (fos != null) {
                        fos.flush();
                        fos.close();
                    }

                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
        return SQLiteDatabase.openDatabase(database.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
    }


    public List<String> queryWordNameByLimitOfPage(int pageIndex, int size) {
        int fromCount = (pageIndex - 1) * size;
        return queryWordNameByLimit(fromCount, size);
    }

    public List<String> queryWordNameByLimit(int fromIndex, int size) {
        List<String> result = new ArrayList<>();

        SQLiteDatabase db = openDatabase();

        //
        Cursor cursor = db.rawQuery(QUERY_WORD_NAME_BY_LIMIT
                , new String[]{String.valueOf(fromIndex), String.valueOf(size)});
        while (cursor.moveToNext()) {
            result.add(cursor.getString(cursor.getColumnIndex(WORD_NAME)));
        }

        cursor.close();
        db.close();

        return result;
    }

    public WordInfo queryWordInfoByWord(String word) {

        SQLiteDatabase db = openDatabase();
        Cursor cursor = db.rawQuery(QUERY_WORD_INFO_BY_WORD_NAME, new String[]{word});

        WordInfo wordInfo = null;
        if (cursor.moveToFirst()) {
            wordInfo = new WordInfo();
            wordInfo.setId(cursor.getInt(cursor.getColumnIndex(WORD_ID)));
            wordInfo.setWord(cursor.getString(cursor.getColumnIndex(WORD_NAME)));
            wordInfo.setPhonogram(cursor.getString(cursor.getColumnIndex(WORD_PHONOGRAM)));
            wordInfo.setTranslate(cursor.getString(cursor.getColumnIndex(WORD_TRANSLATE)));
        }

        cursor.close();
        db.close();

        return wordInfo;
    }


}
