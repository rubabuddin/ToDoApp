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
    private static final int UPDATE_DATE = 2;

    private SQLiteDatabase database;
    private TasksDatabaseHelper dbHelper;

    private String[] allColumns = {
            TasksDatabaseHelper.KEY_ID,
            TasksDatabaseHelper.KEY_TEXT,
            TasksDatabaseHelper.KEY_PRIORITY,
            TasksDatabaseHelper.KEY_DATE};

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

    public boolean addTaskItem(TaskItem item){
        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TasksDatabaseHelper.KEY_ID, item.getId());
        values.put(TasksDatabaseHelper.KEY_TEXT, item.getTaskText());
        values.put(TasksDatabaseHelper.KEY_PRIORITY, item.getTaskPriority());
        values.put(TasksDatabaseHelper.KEY_DATE, item.getTaskDate());

        long result = database.insert(TasksDatabaseHelper.TABLE_NAME, null, values);
        return result != -1;
    }

    public void deleteTaskItem(TaskItem item) {
        if (!this.isOpen()) {
            this.open();
        }
        int id = item.getId();
        database.delete(TasksDatabaseHelper.TABLE_NAME, TasksDatabaseHelper.KEY_ID
                + " = " + id, null);
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
            case UPDATE_DATE:
                values.put(TasksDatabaseHelper.KEY_DATE, item.getTaskDate());
                break;
        }

        int result = db.update(dbHelper.TABLE_NAME, values, dbHelper.KEY_ID + " = " + item.getId(), null);
        return result;
    }

    public List<TaskItem> getAllTaskItems(List<TaskItem> taskItems){
        Cursor cursor = database.query(TasksDatabaseHelper.TABLE_NAME, allColumns, null, null, null, null, null);

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
        String date = cursor.getString(3);

        return new TaskItem(id, text, priority, date);
    }
}
