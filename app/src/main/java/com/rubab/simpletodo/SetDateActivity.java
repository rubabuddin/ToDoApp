package com.rubab.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Button;

/**
 * Created by rubab.uddin on 9/11/2016.
 */

public class SetDateActivity extends AppCompatActivity implements View.OnClickListener {

    DatePicker datePicker;
    Button btnSaveDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_picker);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        btnSaveDate = (Button) findViewById(R.id.btnSaveDate);
        Log.d("SetDAteActivity.btn", "FAILED HERE");
        btnSaveDate.setOnClickListener(this);

    }

    public void onClick(View v) {
        Intent i = getIntent();

        if(v.getId() == R.id.btnSaveDate){
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth();
            int year = datePicker.getYear();

            i.putExtra("day", day);
            i.putExtra("month", month);
            i.putExtra("year", year);

            setResult(RESULT_OK, i);
            finish();
        }
    }

}
