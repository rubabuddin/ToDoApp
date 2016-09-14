package com.rubab.simpletodo;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;


/**
 * Created by rubab.uddin on 9/9/2016.
 */
public class TasksDatabaseHelper extends SQLiteOpenHelper {

    //Database info
    public static final String DATABASE_NAME = "tasksDatabase";
    public static final int DATABASE_VERSION = 1;

    // Table Names
    public static final String TABLE_NAME = "tasks_table";

    // Task Table Columns
    public static final String KEY_ID = "_id";
    public static final String KEY_TEXT = "taskText";
    public static final String KEY_PRIORITY = "priority";
    public static final String KEY_DATE = "date";

    private static final String CREATE_TASKS_TABLE = "create table " + TABLE_NAME + " (" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
            KEY_TEXT + " TEXT," +
            KEY_PRIORITY + " INTEGER," +
            KEY_DATE + " TEXT);";

   public TasksDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.d("SQLError:", CREATE_TASKS_TABLE);
        db.execSQL(CREATE_TASKS_TABLE);
        db.execSQL("INSERT INTO tasks_table VALUES (7, 'Task2', 2, '19/10/2017');");
    }

  @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
      onCreate(db);
  }
}