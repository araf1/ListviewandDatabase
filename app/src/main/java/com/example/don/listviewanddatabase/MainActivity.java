package com.example.don.listviewanddatabase;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

    Button btmIncomplete, btmComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btmIncomplete = (Button) findViewById(R.id.btmIncomplete);
        btmComplete = (Button) findViewById(R.id.btmComplete);
    }

    public void onButtonClick(View v) {
        if (v.getId() == R.id.btmIncomplete) {

            EditText etmName = (EditText) findViewById(R.id.etmName);
            String name = etmName.getText().toString();

            EditText etmGender = (EditText) findViewById(R.id.etmGender);
            String gender = etmGender.getText().toString();


            Intent intent = new Intent(MainActivity.this, FormDetails.class);
            intent.putExtra("name", name);
            intent.putExtra("gender", gender);

            startActivity(intent);


        }

        if (v.getId() == R.id.btmComplete) {
            Intent intent = new Intent(MainActivity.this, SignUp.class);
            startActivity(intent);
        }


    }
}
