package com.example.test.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.content.Context;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

public class ChatWindow extends Activity {
    public static final String ACTIVITY_NAME = "ChatWindow";
    ListView list;
    EditText text;
    Button send;
    Cursor cursor;
    SQLiteDatabase database;

    private ArrayList<String> strText = new ArrayList<>();
    ChatDatabaseHelper dbHelper;

    String[] columns = {ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        dbHelper = new ChatDatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        list = (ListView)findViewById(R.id.listView);
        text = (EditText)findViewById(R.id.editText);
        send = (Button)findViewById(R.id.button_send);
        final ChatAdapter messageAdapter = new ChatAdapter(this);
        list.setAdapter(messageAdapter);




        cursor = database.query(dbHelper.TABLE_NAME, columns, null, null, null, null, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            String newMessage = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
            strText.add(newMessage);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }

        for(int i = 0; i<cursor.getColumnCount();i++){
            Log.i(ACTIVITY_NAME, "cursor's column name = " + cursor.getColumnName(i));
        }
        Log.i(ACTIVITY_NAME, "Cursor's column count = " + cursor.getColumnCount());

        send.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String str = text.getText().toString();

                int strLength = str.trim().length();
                if(TextUtils.isEmpty(str)){
                    text.setError("Empty message ignored");
                }else {
                    strText.add(str);
                    ContentValues values = new ContentValues();
                    values.put(ChatDatabaseHelper.KEY_MESSAGE, str);
                    database.insert(ChatDatabaseHelper.TABLE_NAME, null, values);
                    text.setText("");
                    messageAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    private class ChatAdapter extends ArrayAdapter<String>{
        public ChatAdapter(Context ctx){
            super(ctx,0);
        }

        @Override
        public int getCount() {
            return strText.size();
        }

        @Override
        public String getItem(int position) {
            return strText.get(position);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if(position%2==0){
              result = inflater.inflate(R.layout.chat_row_incoming,null);
            }else{
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            }
            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }
        public long getId(int position){
            return position;
        }
    }
    @Override
   protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        database.close();
    }
}
