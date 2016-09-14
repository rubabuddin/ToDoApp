package com.rubab.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    public static final String TASK_ITEM_KEY = "TaskItem";
    private EditText editItemText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.task_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        String item = this.getIntent().getStringExtra(TASK_ITEM_KEY);
        editItemText = (EditText) findViewById(R.id.etEditItem);
        editItemText.setText(item);
        editItemText.setSelection(editItemText.getText().length());
    }

    public void onClickSave(){
        EditText editedItem = (EditText) findViewById(R.id.etEditItem);
        Intent passbackData = new Intent();
        passbackData.putExtra(TASK_ITEM_KEY, editedItem.getText().toString());
        setResult(RESULT_OK, passbackData);
        finish();
    };

    public void onClickDelete(){
        Intent passbackData = new Intent();
        setResult(RESULT_CANCELED, passbackData);
        finish();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.save_item:
                onClickSave();
                return true;
            case R.id.delete_item:
                onClickDelete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
