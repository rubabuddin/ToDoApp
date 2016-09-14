package com.rubab.simpletodo;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;



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
    public static final String KEY_YEAR = "year";
    public static final String KEY_MONTH = "month";
    public static final String KEY_DAY = "day";


    private static final String CREATE_TASKS_TABLE = "create table " + TABLE_NAME + " (" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + // Define a primary key
            KEY_TEXT + " TEXT," +
            KEY_PRIORITY + " INTEGER," +
            KEY_YEAR + " INTEGER," +
            KEY_MONTH + " INTEGER," +
            KEY_DAY + " INTEGER);";

   public TasksDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Log.d("SQLError:", CREATE_TASKS_TABLE);
        db.execSQL(CREATE_TASKS_TABLE);
        db.execSQL("INSERT INTO tasks_table VALUES (78, 'Finish CodePath App', 2, 2016, 09, 14);");
        db.execSQL("INSERT INTO tasks_table VALUES (88, 'Buy milk', 0, 2016, 09, 17);");
        db.execSQL("INSERT INTO tasks_table VALUES (79, 'Watch Coursera lecture', 2, 2016, 09, 24);");
        db.execSQL("INSERT INTO tasks_table VALUES (76, 'Catch up on Narcos', 0, 2016, 10, 14);");
        db.execSQL("INSERT INTO tasks_table VALUES (75, 'Get a car wash', 1, 2016, 09, 18);");
    }

  @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
      onCreate(db);
  }
}