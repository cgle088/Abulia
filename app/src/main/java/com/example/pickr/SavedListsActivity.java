package com.example.pickr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

public class SavedListsActivity extends AppCompatActivity {
    private ListContract.List.ListDbHelper dbHelper = new ListContract.List.ListDbHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_lists);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final ScrollView sv = findViewById(R.id.savedListsScrollView);
        final LinearLayout ll = findViewById(R.id.listLayout);

        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name!='android_metadata' AND name != 'sqlite_sequence'", null);
        Log.d(getClass().getName(),"cursor count = " + cursor.getCount() + DatabaseUtils.dumpCursorToString(cursor));
        if(cursor.moveToFirst()) {
            int i = 0;
            do{
                final TableRow tr = new TableRow(this);
                String listName = cursor.getString(cursor.getColumnIndex("name"));
                Log.d(getClass().getName(), "Tables found " + listName);
                TextView listTextView = createListTextView(listName, i);
                listTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

//                listTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

//                listTextView.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
//                listTextView.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//                listTextView.setLayoutParams(new LayoutParams());
                Button editButton = createButton();
                Button chooseButton = createButton();
                editButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                editButton.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                editButton.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                chooseButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tr.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                tr.addView(editButton);
                tr.addView(listTextView);

               //tr.addView(chooseButton);
                ll.addView(tr);
                i++;
            }
            while (cursor.moveToNext());
        }

    }

    private TextView createListTextView(String listName, int ID){
        TextView listText = new TextView(this);
        listText.setText(listName);
        listText.setId(ID);
        listText.setTextSize(34);
//        listText.setWidth(25);
        return listText;
    }

    private Button createButton(){
        Button listButton = new Button(this);
        listButton.setText("Edit");
        //listButton.setWidth(20);

        return listButton;

    }
}




