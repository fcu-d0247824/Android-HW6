package me.blackteatoas.android_hw6;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by red on 2017/5/2.
 */

public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS note (id integer primary key autoincrement, title TEXT, content TEXT, created_at DATE, updated_at DATE)";
    public DBOpenHelper(Context context) {
        super(context, "demo.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
