package com.example.pickr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ListTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_type);
    }

    /**
     * Starts NewListActivity
     * @param view The view
     */
    public void newList(View view){
        Intent intent = new Intent(this, NewListActivity.class);
        startActivity(intent);
    }

    public void showLists(View view){
        Intent intent = new Intent(this, SavedListsActivity.class);
        startActivity(intent);
    }
}
