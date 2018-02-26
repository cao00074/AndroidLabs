package com.example.test.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

public class LoginActivity extends Activity {
    private EditText editText;
    protected static final String ACTIVITY_NAME = "LoginActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginBt = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        SharedPreferences preferences = getSharedPreferences("email", MODE_PRIVATE);//
        String defaultEmail = preferences.getString("DefaultEmail", "email@domain.com");
        editText.setText(defaultEmail);

            loginBt.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v){
                SharedPreferences preferences = getSharedPreferences("email", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("DefaultEmail", editText.getText().toString());
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
                }
                
            });
        Log.i(ACTIVITY_NAME, "In onCreate()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");

    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}
