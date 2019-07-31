package com.example.pickr;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Starts activity for a list of options
     * @param view The containing view
     */
    public void newList(View view){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

}
