package com.rubab.simpletodo;

import java.util.List;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by rubab.uddin on 9/10/2016.
 */
public class TaskAdapter extends ArrayAdapter<TaskItem> {

    TaskItemSource taskItemSource;
    List<TaskItem> data = null;
    Context context;
    int layoutResourceId;

    public TaskAdapter(Context context, int layoutResourceId, List<TaskItem> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    final static int PRIORITY_LOW = 0;
    final static int PRIORITY_MEDIUM = 1;
    final static int PRIORITY_HIGH = 2;

    public TaskItem getItem(int pos) {
        return data.get(pos);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View singleRow = convertView;
        final taskItemHolder holder;

        if(singleRow== null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            singleRow = inflater.inflate(layoutResourceId, parent, false);

            holder = new taskItemHolder();
            holder.tvText       = (TextView)singleRow.findViewById(R.id.tvText);
            holder.tvPriority   = (TextView)singleRow.findViewById(R.id.tvPriority);
            holder.tvDate       = (TextView)singleRow.findViewById(R.id.tvDate);

            singleRow.setTag(holder);
        } else {
            holder = (taskItemHolder)singleRow.getTag();
        }

        final TaskItem taskItem = data.get(position);

        holder.tvText.setText(taskItem.getTaskText());
        holder.tvDate.setText(taskItem.getTaskDate());
        int priority = taskItem.getTaskPriority();

        switch(priority) {
            case PRIORITY_LOW:
                singleRow.setBackgroundColor(Color.parseColor("#FFFF99"));
                holder.tvPriority.setText(R.string.priority_low);
                break;
            case PRIORITY_MEDIUM:
                singleRow.setBackgroundColor(Color.parseColor("#FFCC66"));
                holder.tvPriority.setText(R.string.priority_med);
                break;
            case PRIORITY_HIGH:
                singleRow.setBackgroundColor(Color.parseColor("#FF5050"));
                holder.tvPriority.setText(R.string.priority_hi);
                break;
        }
        return singleRow;
    }


    static class taskItemHolder
    {
        TextView tvText, tvPriority, tvDate;
    }



}
