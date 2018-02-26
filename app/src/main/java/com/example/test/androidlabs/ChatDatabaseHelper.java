package com.example.test.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;
import android.util.Log;

/**
 * Created by test on 2018-02-20.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "Message.db";
    private final static int VERSION_NUM = 2;
    final static String KEY_ID = "COLUMN_ID";
    final static String KEY_MESSAGE = "MESSAGE";
    static String TABLE_NAME = "Message";


    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME
            + "( "
            + KEY_ID
            + " integer primary key autoincrement, "
            + KEY_MESSAGE
            + " text not null);";

    public ChatDatabaseHelper (Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade,oldVersion = " + oldVersion + "newVersion = "+ newVersion);
        Log.w(ChatDatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


}
