package com.example.pickr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class OptionChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_choose);
        Intent intent = getIntent();
        String chosenOption = intent.getStringExtra(ListActivity.MESSAGE);

        TextView textView = findViewById(R.id.chosenOptionTextView);
        textView.setText(chosenOption);
        textView.setTextSize(100);

    }
}
