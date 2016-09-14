package com.rubab.simpletodo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.List;

/**
 * Created by rubab.uddin on 9/10/2016.
 */
public class TaskItemSource {
    private static final int UPDATE_TEXT = 0;
    private static final int UPDATE_PRIORITY = 1;
    private static final int UPDATE_YEAR = 2;
    private static final int UPDATE_MONTH = 3;
    private static final int UPDATE_DAY = 4;


    private SQLiteDatabase database;
    private TasksDatabaseHelper dbHelper;

    private String[] allColumns = {
            TasksDatabaseHelper.KEY_ID,
            TasksDatabaseHelper.KEY_TEXT,
            TasksDatabaseHelper.KEY_PRIORITY,
            TasksDatabaseHelper.KEY_YEAR,
            TasksDatabaseHelper.KEY_MONTH,
            TasksDatabaseHelper.KEY_DAY};

    public TaskItemSource(Context context) {
        dbHelper = new TasksDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public boolean isOpen(){
        return database.isOpen();
    }

    public boolean addTaskItem(TaskItem taskItem){
        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TasksDatabaseHelper.KEY_ID, taskItem.getId());
        values.put(TasksDatabaseHelper.KEY_TEXT, taskItem.getTaskText());
        values.put(TasksDatabaseHelper.KEY_PRIORITY, taskItem.getTaskPriority());
        values.put(TasksDatabaseHelper.KEY_YEAR, taskItem.getTaskYear());
        values.put(TasksDatabaseHelper.KEY_MONTH, taskItem.getTaskMonth());
        values.put(TasksDatabaseHelper.KEY_DAY, taskItem.getTaskDay());

        long result = database.insert(TasksDatabaseHelper.TABLE_NAME, null, values);
        return result != -1;
    }

    public void deleteTaskItem(int taskId) {
        if (!this.isOpen()) {
            this.open();
        }
        database.delete(TasksDatabaseHelper.TABLE_NAME, TasksDatabaseHelper.KEY_ID
                + " = " + taskId, null);
    }

    public int updateTaskItem(int changedEntry, TaskItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        switch (changedEntry){
            case UPDATE_TEXT:
                values.put(TasksDatabaseHelper.KEY_TEXT, item.getTaskText());
                break;
            case UPDATE_PRIORITY:
                values.put(TasksDatabaseHelper.KEY_PRIORITY, item.getTaskPriority());
                break;
            case UPDATE_YEAR:
                values.put(TasksDatabaseHelper.KEY_YEAR, item.getTaskYear());
                break;
            case UPDATE_MONTH:
                values.put(TasksDatabaseHelper.KEY_MONTH, item.getTaskMonth());
                break;
            case UPDATE_DAY:
                values.put(TasksDatabaseHelper.KEY_DAY, item.getTaskDay());
                break;
        }

        int result = db.update(dbHelper.TABLE_NAME, values, dbHelper.KEY_ID + " = " + item.getId(), null);
        return result;
    }

    public void preserveOrder(){
        database.execSQL("SELECT * FROM tasks_table ORDER BY priority DESC");
    }

    public List<TaskItem> getAllTaskItems(List<TaskItem> taskItems){
        Cursor cursor = database.query(TasksDatabaseHelper.TABLE_NAME, allColumns, null, null, null, null, "priority DESC");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            TaskItem taskItem = cursorRetrieve(cursor);
            taskItems.add(taskItem);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return taskItems;
    }

    private TaskItem cursorRetrieve(Cursor cursor) {
        int id = cursor.getInt(0);
        String text = cursor.getString(1);
        int priority = cursor.getInt(2);
        int year = cursor.getInt(3);
        int month = cursor.getInt(4);
        int day = cursor.getInt(5);

        return new TaskItem(id, text, priority, year, month, day);
    }
}
