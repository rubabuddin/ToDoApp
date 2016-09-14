package com.rubab.simpletodo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int UPDATE_TEXT = 0;
    private static final int UPDATE_PRIORITY = 1;
    private static final int UPDATE_DATE = 2;
    private static final String TASK_ID_LIST = "taskIdList";

    final int SET_DATE_REQUEST_CODE = 0;

    TaskItemSource taskItemSource;
    List<TaskItem> taskItems;
    TaskAdapter taskAdapter;
    ListView lvItems;

    private static int taskId;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(TASK_ID_LIST, 0);
        taskId = settings.getInt("taskId", 0);

        taskItemSource = new TaskItemSource(this);
        taskItemSource.open();

        taskItems = new ArrayList<>();
        taskItemSource.getAllTaskItems(taskItems);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        taskAdapter = new TaskAdapter(this, R.layout.list_item, taskItems);
        lvItems = (ListView) findViewById(R.id.lvItems);
        assert lvItems != null;
        lvItems.setAdapter(taskAdapter);

        setupListViewListener();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int pos, long id) {
                TaskItem selectedItem = taskItems.get(pos);
                taskItemSource.deleteTaskItem(selectedItem);
                taskItems.remove(pos);
                taskAdapter.notifyDataSetChanged();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                Intent intent = new Intent(getBaseContext(), EditItemActivity.class);
                TaskItem selectedItem = taskItems.get(pos);
                intent.putExtra("id", selectedItem.getId());
                intent.putExtra("exists", true);
                intent.putExtra("taskText", selectedItem.getTaskText());
                intent.putExtra("priority", selectedItem.getTaskPriority());
                intent.putExtra("date", selectedItem.getTaskDate());
                startActivityForResult(intent, SET_DATE_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // REQUEST_CODE is defined above
        if (resultCode != RESULT_CANCELED) {
            String taskText = data.getStringExtra("taskText");
            int priority = data.getIntExtra("priority", 0);
            int day = data.getIntExtra("day", 17);
            int month = data.getIntExtra("month", 10);
            int year = data.getIntExtra("year", 2016);
            String date = ((month < 10) ? "0" + month : month) + "/"
                    + ((day < 10) ? "0" + day : day) + "/" + year;

            TaskItem taskItem = new TaskItem(taskId, taskText, priority, date);
            boolean taskExists = data.getBooleanExtra("exists", false);
            if (!taskExists) {
                boolean addSuccess = taskItemSource.addTaskItem(taskItem);
                if (addSuccess == true) {
                    taskItems.add(new TaskItem(taskId, taskText, priority, date));
                    taskAdapter.notifyDataSetChanged();
                    Toast.makeText(this, "New task added: " + taskText, Toast.LENGTH_SHORT).show();
                }
            } else {
                int id = data.getIntExtra("id", 0);
                taskItemSource.updateTaskItem(UPDATE_TEXT, taskItem);
                taskItemSource.updateTaskItem(UPDATE_PRIORITY, taskItem);
                taskItemSource.updateTaskItem(UPDATE_DATE, taskItem);
                for (TaskItem existingItem : taskItems) {
                    if (existingItem.getId() == id) {
                        existingItem.setTaskText(taskText);
                        existingItem.setTaskPriority(priority);
                        existingItem.setTaskDate(date);
                    }
                }
                taskAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Task modified to: " + taskText, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_item) {
            //launchAddItem();
            return true;
        } else if (id == R.id.action_settings)
            return true;
        else
            return super.onOptionsItemSelected(item);
    }

    public void launchAddTask(View v) {
        Intent intent = new Intent(this, EditItemActivity.class);
        intent.putExtra("exists", false);
        startActivityForResult(intent, SET_DATE_REQUEST_CODE);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.rubab.simpletodo/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    public void onPause(){
        super.onPause();

        SharedPreferences settings = getSharedPreferences(TASK_ID_LIST, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("taskId", taskId);

        // Commit the edits!
        editor.apply();

        if (taskItemSource.isOpen()) {
            taskItemSource.close();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences settings = getSharedPreferences(TASK_ID_LIST, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("taskId", taskId);

        // Commit the edits!
        editor.apply();

        if (taskItemSource.isOpen()) {
            taskItemSource.close();
        }
    }
}