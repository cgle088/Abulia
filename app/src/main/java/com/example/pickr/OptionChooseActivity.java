package com.example.pickr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class OptionChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_choose);
        Intent intent = getIntent();
        String chosenOption = intent.getStringExtra(MainActivity.MESSAGE);
        TextView textView = new TextView(this);
        textView.setText(chosenOption);
        textView.setTextSize(24);
        ConstraintLayout li = findViewById(R.id.answerLayout);
        li.addView(textView);

    }
}
