/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienVNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.todoappproject.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.training.tiennguyen.todoappproject.constants.DatabaseConstant;
import com.training.tiennguyen.todoappproject.models.TaskModel;
import com.training.tiennguyen.todoappproject.utils.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * {@link TaskDBHelper}
 *
 * @author TienVNguyen
 */
public class TaskDBHelper extends SQLiteOpenHelper {
    /**
     * CREATE TABLE TASKS
     * (
     * NAME PRIMARY KEY  NOT NULL ,
     * DETAILS KEY ,
     * PRIORITY KEY  NOT NULL ,
     * STATUS KEY  NOT NULL ,
     * PERCENT INTEGER KEY ,
     * REMOVED INTEGER KEY ,
     * COMPLETED INTEGER KEY ,
     * CREATED_DATE INTEGER KEY ,
     * UPDATED_DATE INTEGER KEY ,
     * STARTED_DATE INTEGER KEY ,
     * COLUMN_DUE_DATE INTEGER KEY
     * ) ;
     */
    private static final String SQL_CREATE_ENTRIES = DatabaseConstant.CREATE_TABLE + TaskDBContract.TaskContain.TABLE_NAME
            + DatabaseConstant.OPEN_BRACKETS
            + TaskDBContract.TaskContain.COLUMN_NAME + DatabaseConstant.PRIMARY_KEY + DatabaseConstant.NOT_NULL + DatabaseConstant.COMMA_SEP
            + TaskDBContract.TaskContain.COLUMN_DETAILS + DatabaseConstant.STRING_KEY + DatabaseConstant.COMMA_SEP
            + TaskDBContract.TaskContain.COLUMN_PRIORITY + DatabaseConstant.STRING_KEY + DatabaseConstant.NOT_NULL + DatabaseConstant.COMMA_SEP
            + TaskDBContract.TaskContain.COLUMN_STATUS + DatabaseConstant.STRING_KEY + DatabaseConstant.NOT_NULL + DatabaseConstant.COMMA_SEP
            + TaskDBContract.TaskContain.COLUMN_PERCENT + DatabaseConstant.INTEGER_KEY + DatabaseConstant.COMMA_SEP
            + TaskDBContract.TaskContain.COLUMN_REMOVED + DatabaseConstant.INTEGER_KEY + DatabaseConstant.COMMA_SEP
            + TaskDBContract.TaskContain.COLUMN_COMPLETED + DatabaseConstant.INTEGER_KEY + DatabaseConstant.COMMA_SEP
            + TaskDBContract.TaskContain.COLUMN_CREATED_DATE + DatabaseConstant.INTEGER_KEY + DatabaseConstant.COMMA_SEP
            + TaskDBContract.TaskContain.COLUMN_UPDATED_DATE + DatabaseConstant.INTEGER_KEY + DatabaseConstant.COMMA_SEP
            + TaskDBContract.TaskContain.COLUMN_STARTED_DATE + DatabaseConstant.INTEGER_KEY + DatabaseConstant.COMMA_SEP
            + TaskDBContract.TaskContain.COLUMN_DUE_DATE + DatabaseConstant.INTEGER_KEY
            + DatabaseConstant.CLOSE_BRACKETS + DatabaseConstant.SEMICOLON;

    /**
     * DROP TABLE TASK IF EXISTS TASKS;
     */
    private static final String SQL_DELETE_ENTRIES =
            DatabaseConstant.DROP_TABLE_EXISTED + TaskDBContract.TaskContain.TABLE_NAME + DatabaseConstant.SEMICOLON;

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     */
    public TaskDBHelper(Context context) {
        super(context, TaskDBContract.TaskContain.DATABASE_NAME_VALUE, null, TaskDBContract.TaskContain.DATABASE_VERSION_VALUE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        deleteDatabase(db);
        onCreate(db);
    }

    /**
     * Delete Database
     *
     * @param db SQLiteDatabase
     */
    private void deleteDatabase(SQLiteDatabase db) {
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    /**
     * This function will select all record(s) of table inside of database
     *
     * @return List<TaskModel>
     */
    public List<TaskModel> selectAllTasks() {
        // Get the lock
        final SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // Select table columns
        final String[] columns = new String[]{
                TaskDBContract.TaskContain.COLUMN_NAME, TaskDBContract.TaskContain.COLUMN_DETAILS,
                TaskDBContract.TaskContain.COLUMN_PRIORITY, TaskDBContract.TaskContain.COLUMN_STATUS,
                TaskDBContract.TaskContain.COLUMN_PERCENT, TaskDBContract.TaskContain.COLUMN_REMOVED,
                TaskDBContract.TaskContain.COLUMN_COMPLETED, TaskDBContract.TaskContain.COLUMN_CREATED_DATE,
                TaskDBContract.TaskContain.COLUMN_UPDATED_DATE, TaskDBContract.TaskContain.COLUMN_STARTED_DATE,
                TaskDBContract.TaskContain.COLUMN_DUE_DATE
        };

        // Get the result
        final Cursor cursor = sqLiteDatabase.query(TaskDBContract.TaskContain.TABLE_NAME, columns, null, null, null, null, null);
        final List<TaskModel> resultSelect = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                TaskModel taskModel = new TaskModel();
                taskModel.setmName(cursor.getString(0));
                taskModel.setmDetails(cursor.getString(1));
                taskModel.setmPriority(cursor.getString(2));
                taskModel.setmStatus(cursor.getString(3));
                taskModel.setmPercent(cursor.getInt(4));
                taskModel.setmRemoved(cursor.getInt(5) == 1);
                taskModel.setmCompleted(cursor.getInt(6) == 1);
                taskModel.setmCreatedDate(new Date(cursor.getInt(7)));
                taskModel.setmUpdatedDate(new Date(cursor.getInt(8)));
                taskModel.setmStartedDate(new Date(cursor.getInt(9)));
                taskModel.setmDueDate(new Date(cursor.getInt(10)));
                resultSelect.add(taskModel);
            } while (cursor.moveToNext());
        }

        // Close connection
        if (cursor != null) cursor.close();
        sqLiteDatabase.close();

        return resultSelect;
    }

    /**
     * This function will insert a record of table inside of database
     *
     * @param taskModel TaskModel
     * @return long
     */
    public long insertTask(final TaskModel taskModel) {
        // Get the lock
        final SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        final ContentValues contentValues = new ContentValues();
        contentValues.put(TaskDBContract.TaskContain.COLUMN_NAME, taskModel.getmName());
        if (!StringUtil.isEmpty(taskModel.getmDetails())) {
            contentValues.put(TaskDBContract.TaskContain.COLUMN_DETAILS, taskModel.getmDetails());
        }
        if (!StringUtil.isEmpty(taskModel.getmPriority())) {
            contentValues.put(TaskDBContract.TaskContain.COLUMN_PRIORITY, taskModel.getmPriority());
        }
        if (!StringUtil.isEmpty(taskModel.getmStatus())) {
            contentValues.put(TaskDBContract.TaskContain.COLUMN_STATUS, taskModel.getmStatus());
        }
        contentValues.put(TaskDBContract.TaskContain.COLUMN_PERCENT, taskModel.getmPercent());
        contentValues.put(TaskDBContract.TaskContain.COLUMN_REMOVED, taskModel.ismRemoved() ? 1 : 0);
        contentValues.put(TaskDBContract.TaskContain.COLUMN_COMPLETED, taskModel.ismCompleted() ? 1 : 0);
        if (StringUtil.isNotNull(taskModel.getmCreatedDate())) {
            contentValues.put(TaskDBContract.TaskContain.COLUMN_CREATED_DATE, taskModel.getmCreatedDate().getTime());
        }
        if (StringUtil.isNotNull(taskModel.getmUpdatedDate())) {
            contentValues.put(TaskDBContract.TaskContain.COLUMN_UPDATED_DATE, taskModel.getmUpdatedDate().getTime());
        }
        if (StringUtil.isNotNull(taskModel.getmStartedDate())) {
            contentValues.put(TaskDBContract.TaskContain.COLUMN_STARTED_DATE, taskModel.getmStartedDate().getTime());
        }
        if (StringUtil.isNotNull(taskModel.getmDueDate())) {
            contentValues.put(TaskDBContract.TaskContain.COLUMN_DUE_DATE, taskModel.getmDueDate().getTime());
        }

        // Insert
        final long newRowId = sqLiteDatabase.insert(
                TaskDBContract.TaskContain.TABLE_NAME,
                TaskDBContract.TaskContain.COLUMN_NAME_NULLABLE,
                contentValues);

        // Close connection
        sqLiteDatabase.close();

        return newRowId;
    }

    /**
     * This function will update a record of table inside of database
     *
     * @param taskModel TaskModel
     * @return int
     */
    public int updateTask(final TaskModel taskModel) {
        // Get the lock
        final SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // New value for one column
        final ContentValues contentValues = new ContentValues();
        contentValues.put(TaskDBContract.TaskContain.COLUMN_NAME, taskModel.getmName());
        if (!StringUtil.isEmpty(taskModel.getmDetails())) {
            contentValues.put(TaskDBContract.TaskContain.COLUMN_DETAILS, taskModel.getmDetails());
        }
        if (!StringUtil.isEmpty(taskModel.getmPriority())) {
            contentValues.put(TaskDBContract.TaskContain.COLUMN_PRIORITY, taskModel.getmPriority());
        }
        if (!StringUtil.isEmpty(taskModel.getmStatus())) {
            contentValues.put(TaskDBContract.TaskContain.COLUMN_STATUS, taskModel.getmStatus());
        }
        contentValues.put(TaskDBContract.TaskContain.COLUMN_PERCENT, taskModel.getmPercent());
        contentValues.put(TaskDBContract.TaskContain.COLUMN_REMOVED, taskModel.ismRemoved() ? 1 : 0);
        contentValues.put(TaskDBContract.TaskContain.COLUMN_COMPLETED, taskModel.ismCompleted() ? 1 : 0);
        if (StringUtil.isNotNull(taskModel.getmUpdatedDate())) {
            contentValues.put(TaskDBContract.TaskContain.COLUMN_UPDATED_DATE, taskModel.getmUpdatedDate().getTime());
        }
        if (StringUtil.isNotNull(taskModel.getmStartedDate())) {
            contentValues.put(TaskDBContract.TaskContain.COLUMN_STARTED_DATE, taskModel.getmStartedDate().getTime());
        }
        if (StringUtil.isNotNull(taskModel.getmDueDate())) {
            contentValues.put(TaskDBContract.TaskContain.COLUMN_DUE_DATE, taskModel.getmDueDate().getTime());
        }

        // Which row to update, based on the NAME
        final String selection = TaskDBContract.TaskContain.COLUMN_NAME + DatabaseConstant.SELECTION_IS;

        // Specify arguments in placeholder order
        final String[] selectionArgs = {String.valueOf(taskModel.getmName())};

        // Update
        final int count = sqLiteDatabase.update(
                TaskDBContract.TaskContain.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs);

        // Close connection
        sqLiteDatabase.close();

        return count;
    }

    /**
     * This function will delete a record of table inside of database
     *
     * @param taskModel TaskModel
     * @return int
     */
    public int deleteTask(final TaskModel taskModel) {
        // Get the lock
        final SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        // New value for one column
        final ContentValues contentValues = new ContentValues();
        contentValues.put(TaskDBContract.TaskContain.COLUMN_NAME, taskModel.getmName());
        contentValues.put(TaskDBContract.TaskContain.COLUMN_REMOVED, taskModel.getmName());
        if (StringUtil.isNotNull(taskModel.getmUpdatedDate())) {
            contentValues.put(TaskDBContract.TaskContain.COLUMN_UPDATED_DATE, taskModel.getmUpdatedDate().getTime());
        }

        // Which row to delete, based on the NAME
        final String selection = TaskDBContract.TaskContain.COLUMN_NAME + DatabaseConstant.SELECTION_IS;

        // Specify arguments in placeholder order
        final String[] selectionArgs = {String.valueOf(taskModel.getmName())};

        // Delete
        /*final int count = sqLiteDatabase.delete(
                TaskDBContract.TaskContain.TABLE_NAME,
                selection,
                selectionArgs);*/
        final int count = sqLiteDatabase.update(
                TaskDBContract.TaskContain.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs);

        // Close connection
        sqLiteDatabase.close();

        return count;
    }
}
