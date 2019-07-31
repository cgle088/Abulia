package com.example.pickr;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Random;


import io.selendroid.client.SelendroidDriver;
import io.selendroid.common.SelendroidCapabilities;
import io.selendroid.server.common.exceptions.SelendroidException;

public class ListActivity extends AppCompatActivity {

    public static final String MESSAGE = "com.example.pickr.Message" ;
    private int editTextCount = 0;
    private ArrayList<EditText> optionList = new ArrayList<>();
    @Override


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        addOption(findViewById(android.R.id.content));
        addOption(findViewById(android.R.id.content));
    }

    /**
     * Randomly chooses one of N options where N > 1
     * @param view The view which calls the function
     */
    public void chooseOption(View view){


        Random r = new Random();
        int textId = r.nextInt(editTextCount);
        Log.d(getClass().getName(), "TextView id = " + textId + " editTextCount = " + editTextCount);
        EditText chosenEditText = optionList.get(textId);
        String chosenOption = chosenEditText.getText().toString();
        Intent intent = new Intent(this, OptionChooseActivity.class);
        intent.putExtra(MESSAGE, chosenOption);
        startActivity(intent);
    }

    /**
     * Adds a new optionEditText and corresponding cancel button to view
     * @param view The view which holds the new optionEditText and corresponding cancel button
     */

    public void addOption(View view){
        final LinearLayout li = findViewById(R.id.optionLayout);
        if(editTextCount < 9) {

            EditText optionEditText = createOptionEditText();
            optionEditText.setId(editTextCount);
            Log.d(getClass().getName(), "New edittext id = " + editTextCount);
            editTextCount++;


            LinearLayout optionContainerLL = new LinearLayout(this);
            optionContainerLL.setOrientation(LinearLayout.HORIZONTAL);
            optionContainerLL.setId(editTextCount);

            ImageButton cancelButton = createCancelButton(li, optionContainerLL, optionEditText);

            optionContainerLL.addView(optionEditText);
            optionContainerLL.addView(cancelButton);
            li.addView(optionContainerLL);
            optionList.add(optionEditText);
        }else{
            alertMaxOptionsReached();
        }
    }

    /**
     * Creates an additional optionEditText
     * @return A new optionEditText
     */
    /*
        Creates and returns a new EditText view for UI option input
     */
    EditText createOptionEditText(){
        EditText tv = new EditText(this);
        //limits the number of characters per option
        tv.setFilters(new InputFilter[]{new InputFilter.LengthFilter(22)});
        tv.setLayoutParams(new ViewGroup.LayoutParams(800,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setTextSize(24);
        tv.setId(editTextCount);
        tv.setHint("Enter option");
        return tv;
    }

    /**
     * Creates a new cancel button and associates it with a corresponding optionEditText
     * @param li The parent layout
     * @param optionContainer The corresponding optionEditText and Button's container
     * @param option The corresponding optionEditText, for removing from optionList array
     * @return The cancel button
     */
    ImageButton createCancelButton(final LinearLayout li, final LinearLayout optionContainer, final EditText option){
        final ImageButton cancelButton = new ImageButton(this);
        cancelButton.setId(editTextCount);
        cancelButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //disallow less than two options
                if(optionList.size() > 2) {
                    Log.d(getClass().getName(), "Deleting option " + option.getText());
                    optionList.remove(option);

                    for (int i = 0; i < optionList.size(); i++) {
                        Log.d(getClass().getName(), "List[" + i + "] = " + optionList.get(i));
                    }
                    li.removeView(optionContainer);
                    editTextCount = optionList.size();
                }
            }
        });
        return cancelButton;
    }

    /**
     * Used to alert user when maximum number of options is reached
     */
    void alertMaxOptionsReached(){
        AlertDialog.Builder aDialog;
        aDialog = new AlertDialog.Builder(this);
        aDialog.setTitle("Max Options Reached");
        aDialog.setMessage("");
        aDialog.show();
    }

    /**
     * Selendroid junk for use elsewhere
     * @throws Exception blah
     */
    //to get selendroid working run in cmd:
    //java -jar selendroid-standalone-0.17.0-with-dependencies.jar -app C:\Users\PeteMiner\AndroidStudioProjects\pickr\app\build\outputs\apk\debug\app-debug.apk
    void selendroidTest() throws Exception{
        SelendroidCapabilities capa = new SelendroidCapabilities("com.example.pickr:1.0");
        WebDriver driver = null;
        try {
            driver = new SelendroidDriver(capa);
        }catch(SelendroidException e){
            Log.e("webdriver failed", "Selendroid Webdriver failed to start");
        }
        WebElement inputField = ((SelendroidDriver) driver).findElement(By.id("my_text_field"));
        if(inputField.getAttribute("enabled") != "true"){
            throw new Exception("No input text field (to be expected)", new Throwable((String.valueOf(Exception.class))));
        }
        inputField.sendKeys("Selendroid");
        if(inputField.getText() != "Selendroid"){
            throw new Exception("Selendroid != true", new Throwable(String.valueOf(Exception.class)));
        }
        driver.quit();
    }

}


