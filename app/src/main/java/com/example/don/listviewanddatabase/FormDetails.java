package com.example.don.listviewanddatabase;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class FormDetails extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_details);

        String name = getIntent().getStringExtra("name");
        TextView tvfdName = (TextView)findViewById(R.id.tvfdName);
        tvfdName.setText(name);

        String gender = getIntent().getStringExtra("gender");
        TextView tvfdGender = (TextView)findViewById(R.id.tvfdGender);
        tvfdGender.setText(gender);

        String email = getIntent().getStringExtra("email");
        TextView tvfdEmail = (TextView)findViewById(R.id.tvfdEmail);
        tvfdEmail.setText(email);

        String address = getIntent().getStringExtra("address");
        TextView tvfdAddress = (TextView)findViewById(R.id.tvfdAddress);
        tvfdAddress.setText(address);
    }



}
