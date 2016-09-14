package com.rubab.simpletodo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class EditItemActivity extends AppCompatActivity{

    public static final String TASK_ITEM_KEY = "taskText";

    EditText etEditItem;
    Spinner spPriority;
    TextView tvDate;
    Button btnSetDate;

    boolean taskExists;

    int year, month, day, hour, minute;
    int priority;
    int id;

    Intent callIntent;
    final int SET_DATE_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.task_toolbar);
        setSupportActionBar(toolbar);

        callIntent = getIntent();
        taskExists = callIntent.getBooleanExtra("exists", true);

        setupEditText(taskExists);
        setupPrioritySpinner(taskExists);
        setupDateView(taskExists);

        btnSetDate = (Button) findViewById(R.id.btnSetDate);
        btnSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate();
            }
        });

    }

    private void setupDateView(boolean taskExists){
        tvDate = (TextView) findViewById(R.id.tvDate);

        if(taskExists){
            String[] dateFormatted = callIntent.getStringExtra("date").split("/");
            month = Integer.valueOf(dateFormatted[0]);
            day = Integer.valueOf(dateFormatted[1]);
            year = Integer.valueOf(dateFormatted[2]);
        } else {
            Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH) + 1; //months start from 0
            day = c.get(Calendar.DAY_OF_MONTH);
        }

        String dateSet = ((month < 10) ? "0" + month : month) + "/"
                + ((day < 10) ? "0" + day : day) + "/" + year;
        tvDate.setText(dateSet);
    }

    private void setupEditText(boolean taskExists){
        etEditItem = (EditText) findViewById(R.id.etEditItem);

        if(taskExists) {
            String taskText = callIntent.getStringExtra(TASK_ITEM_KEY);
            etEditItem.setText(taskText);
            etEditItem.setSelection(etEditItem.getText().length());
        }
        etEditItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0){
                    etEditItem.setError("Empty Field");
                    //Menu menu = (Menu) findViewById(R.menu.menu_task);
                    //menu.getItem(0).setEnabled(false);
                }
                else{
                    invalidateOptionsMenu();
                }
            }
        });
    }

    private void setupPrioritySpinner(boolean taskExists){
        spPriority = (Spinner) findViewById(R.id.spPriority);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPriority.setAdapter(adapter);

        if(taskExists){
            priority = callIntent.getIntExtra("priority", 0);
            spPriority.setSelection(priority);
        } else {
            spPriority.setSelection(0);
        }

        spPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priority = spPriority.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setDate(){
        Intent intent = new Intent(this, SetDateActivity.class);
        startActivityForResult(intent, SET_DATE_REQUEST_CODE);
    }

    public void onClickSave(){
        Intent passbackData = getIntent();

        Calendar now = GregorianCalendar.getInstance();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, day);
        String taskText = etEditItem.getText().toString();
        int id = callIntent.getIntExtra("id", 0);

        //make sure user didn't pick past date
        if(c.compareTo(now) >= 0){
            passbackData.putExtra("day", day);
            passbackData.putExtra("month", month);
            passbackData.putExtra("year", year);
            passbackData.putExtra("priority", priority);
            passbackData.putExtra("year", year);
            passbackData.putExtra(TASK_ITEM_KEY, taskText);
            passbackData.putExtra("exists", taskExists);
            passbackData.putExtra("id", id);
            setResult(RESULT_OK, passbackData);
            finish();
        }
        else
            Toast.makeText(this, "Set completion to future date", Toast.LENGTH_SHORT).show();
    }


    public void onClickDelete(){
     //   if(taskExists){
            Intent passbackData = getIntent();
            setResult(RESULT_CANCELED, passbackData);
            finish();
       // }
     //   else{
      //      finish();
     //   }
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        //disable save button if new task and fields are empty
       // if(!taskExists){
         //   menu.getItem(0).setEnabled(false);
        //}

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task, menu);
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {
            if (requestCode == SET_DATE_REQUEST_CODE) {
                day = data.getIntExtra("day", 17);
                month = data.getIntExtra("month", 10);
                year = data.getIntExtra("year", 2016);
                String dateSet = ((month < 10) ? "0" + month : month) + "/"
                        + ((day < 10) ? "0" + day : day) + "/" + year;
                tvDate.setText(dateSet);
            }
        }
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
