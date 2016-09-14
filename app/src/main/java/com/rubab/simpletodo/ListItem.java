package com.rubab.simpletodo;

import java.io.Serializable;

/**
 * Created by rubab.uddin on 9/8/2016.
 */
public class ListItem implements Serializable {
    private static final long serialVersionUID = 5177222050535318633L;
    private String taskName;
    private String priority;
    private String date;

    public ListItem(String itemText, String priorityLevel, String currentDateTimeString) {
        taskName = itemText;
        priority = priorityLevel;
        date = currentDateTimeString;
    }
}

