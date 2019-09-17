package com.example.pickr;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

//import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


//import io.selendroid.client.SelendroidDriver;
//import io.selendroid.common.SelendroidCapabilities;
//import io.selendroid.server.common.exceptions.SelendroidException;

public class NewListActivity extends AppCompatActivity {

    public static final String MESSAGE = "com.example.pickr.Message" ;
    private int editTextCount = 0;
    private ListContract.List.ListDbHelper dbHelper = new ListContract.List.ListDbHelper(this);
    private ArrayList<EditText> optionList = new ArrayList<>();
    @Override


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);
        addOption(findViewById(android.R.id.content));
        addOption(findViewById(android.R.id.content));

    }

    /**
     * If all option fields are filled prompts user to name their new list
     * @param view the view
     */
    public void newListSavePopUp(View view){
        Log.d(getClass().getName(),"In newListSavePopUp()");
        if(allOptionsFilled()){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("New List Name");
            alert.setMessage("Enter new list name:");

            final EditText input = new EditText(this);
            alert.setView(input);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String listName = input.getText().toString();
                    saveList(listName);
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Cancel
                }
            });
            alert.show();
        }else{
            alert("Blank options not allowed");
            return;
        }
    }

    /**
     * Uses the optionList string array to save each option to SharedPreferences
     * @param newListName String, the name for the new list
     */
    private void saveList(String newListName){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
//        String [] projection = {ListContract.List.COLUMN};
//        String selection = "Option IS NOT NULL";

        dbHelper.newTable(db, newListName);
        for (int i = 0; i < optionList.size(); i++) {
            Log.d(getClass().getName(), "saveList() putting " + optionList.get(i).getText().toString());
            values.put("Option", optionList.get(i).getText().toString());
            long newRowId = db.insert(newListName, null, values);
        }

//        dbHelper.getReadableDatabase();
//        Cursor cursor = db.query(newListName, projection, selection, null, null, null, null);
//        List items = new ArrayList<>();
//        while(cursor.moveToNext()){
//            for(int i =0; i < cursor.getColumnCount(); i++){
//                String option = cursor.getString(i);
//                Log.d(getClass().getName(), "saveList() Column" + i + " in database is " + option);
//            }
//        }
//        cursor.close();
    }

    /**
     * Randomly chooses one of N options where N > 1
     * @param view The view which calls the function
     */
    public void chooseOption(View view){
        Random r = new Random();
        int textId = r.nextInt(editTextCount);
        Log.d(getClass().getName(), "chooseOption() textView id = " + textId + " editTextCount = " + editTextCount);
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
            Log.d(getClass().getName(), "addOption() new edittext id = " + editTextCount);
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
            alert("Max Options Reached");
        }
    }

    /**
     * Creates an additional optionEditText
     * @return A new optionEditText
     */
    /*
        Creates and returns a new EditText view for UI option input
     */
    private EditText createOptionEditText(){
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
    private ImageButton createCancelButton(final LinearLayout li, final LinearLayout optionContainer, final EditText option){
        final ImageButton cancelButton = new ImageButton(this);
        cancelButton.setId(editTextCount);
        cancelButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_close));
        cancelButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //disallow less than two options
                if(optionList.size() > 2) {
                    Log.d(getClass().getName(), "createCancelButton() Deleting option " + option.getText());
                    optionList.remove(option);

                    for (int i = 0; i < optionList.size(); i++) {
                        Log.d(getClass().getName(), "createCancelButton() List[" + i + "] = " + optionList.get(i));
                    }
                    li.removeView(optionContainer);
                    editTextCount = optionList.size();
                }
            }
        });
        return cancelButton;
    }

    /**
     * Alerts the user if something is awry
     * @param alertTitle what the alert should say
     */
    private void alert(String alertTitle){
        AlertDialog.Builder aDialog;
        aDialog = new AlertDialog.Builder(this);
        aDialog.setTitle(alertTitle);
        aDialog.setMessage("");
        aDialog.show();
    }

    /**
     * Checks whether or not all the option fields are filled for saving purposes
     * @return false if an option field is empty
     */
    private boolean allOptionsFilled(){
        for(int i = 0; i < optionList.size(); i++){
            Log.d(getClass().getName(), "allOptionsFilled() optionList.toString = " + optionList.get(i).getText());
            if(TextUtils.isEmpty(optionList.get(i).getText()) ){
                return false;
            }
        }
        return true;
    }

    /**
     * Selendroid junk for use elsewhere
     * @throws Exception blah
     */
    //to get selendroid working run in cmd:
    //java -jar selendroid-standalone-0.17.0-with-dependencies.jar -app C:\Users\PeteMiner\AndroidStudioProjects\pickr\app\build\outputs\apk\debug\app-debug.apk
//    void selendroidTest() throws Exception{
//        SelendroidCapabilities capa = new SelendroidCapabilities("com.example.pickr:1.0");
//        WebDriver driver = null;
//        try {
//            driver = new SelendroidDriver(capa);
//        }catch(SelendroidException e){
//            Log.e("webdriver failed", "Selendroid Webdriver failed to start");
//        }
//        WebElement inputField = ((SelendroidDriver) driver).findElement(By.id("my_text_field"));
//        if(inputField.getAttribute("enabled") != "true"){
//            throw new Exception("No input text field (to be expected)", new Throwable((String.valueOf(Exception.class))));
//        }
//        inputField.sendKeys("Selendroid");
//        if(inputField.getText() != "Selendroid"){
//            throw new Exception("Selendroid != true", new Throwable(String.valueOf(Exception.class)));
//        }
//        driver.quit();
//    }

}


