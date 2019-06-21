package com.example.pickr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;


import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final String MESSAGE = "com.example.pickr.Message" ;
    private int editTextCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addOption(findViewById(android.R.id.content));
        addOption(findViewById(android.R.id.content));
    }
    /*
        Randomly chooses one of N "options" where N > 1
     */
    public void chooseOption(View view){
        Intent intent = new Intent(this, OptionChooseActivity.class);
        Random r = new Random();
        int textId = r.nextInt(editTextCount - 1) + 1;
        Log.d(getClass().getName(), "TextView id = " + textId + " editTextCount = " + editTextCount);
        EditText chosenEditText = this.findViewById(textId);
        String chosenOption = chosenEditText.getText().toString();
        intent.putExtra(MESSAGE, chosenOption);
        startActivity(intent);
    }

    /*Adds TextField and cancelButton to view
     */
    public void addOption(View view){
        final LinearLayout li = findViewById(R.id.optionLayout);
        if(editTextCount < 9) {
            editTextCount++;
            EditText tv = createEditText();
            ImageButton cancelButton = createCancelButton( li);

            LinearLayout optionContainerLL = new LinearLayout(this);
            optionContainerLL.setOrientation(LinearLayout.HORIZONTAL);
            optionContainerLL.setId(editTextCount);
            optionContainerLL.addView(tv);
            optionContainerLL.addView(cancelButton);

            li.addView(optionContainerLL);
        }else{
            alertMaxOptionsReached();
        }
    }

    /*
        Creates and returns a new EditText view for option input
     */
    EditText createEditText(){
        EditText tv = new EditText(this);
        tv.setLayoutParams(new ViewGroup.LayoutParams(800,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setTextSize(24);
        tv.setId(editTextCount);
        tv.setHint("Enter option");
        return tv;
    }
    /*
        Creates the close button for each option
        Passed the layout that holds it and the "option" EditText
     */
    ImageButton createCancelButton(final LinearLayout li){
        final ImageButton cancelButton = new ImageButton(this);
        cancelButton.setId(editTextCount);
        cancelButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                li.removeView(findViewById(cancelButton.getId()));
            }
        });
        return cancelButton;
    }
    /*
        Used to alert user when maximum number of options is reached
     */
    void alertMaxOptionsReached(){
        AlertDialog.Builder aDialog;
        aDialog = new AlertDialog.Builder(this);
        aDialog.setTitle("Max Options Reached");
        aDialog.setMessage("");
        aDialog.show();
    }
}
