/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienVNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.todoappproject.databases;

import android.provider.BaseColumns;

/**
 * {@link TaskDBContract}
 * 
 * Created by TienVNguyen
 */
class TaskDBContract {
    /**
     * DATABASE_VERSION
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * DATABASE_NAME
     */
    private static final String DATABASE_NAME = "TASKS.db";

    /**
     * TABLE_TASKS
     */
    private static final String TABLE_TASKS = "TASKS";

    /**
     * KEY_NAME
     */
    private static final String KEY_NAME = "NAME";

    /**
     * KEY_DETAILS
     */
    private static final String KEY_DETAILS = "DETAILS";

    /**
     * KEY_PRIORITY
     */
    private static final String KEY_PRIORITY = "PRIORITY";

    /**
     * KEY_STATUS
     */
    private static final String KEY_STATUS = "STATUS";

    /**
     * KEY_PERCENT
     */
    private static final String KEY_PERCENT = "PERCENT";

    /**
     * KEY_REMOVED
     */
    private static final String KEY_REMOVED = "REMOVED";

    /**
     * KEY_COMPLETED
     */
    private static final String KEY_COMPLETED = "COMPLETED";

    /**
     * KEY_CREATED_DATE
     */
    private static final String KEY_CREATED_DATE = "CREATED_DATE";

    /**
     * KEY_UPDATED_DATE
     */
    private static final String KEY_UPDATED_DATE = "UPDATED_DATE";

    /**
     * KEY_STARTED_DATE
     */
    private static final String KEY_STARTED_DATE = "STARTED_DATE";

    /**
     * KEY_COMPLETED_DATE
     */
    private static final String KEY_COMPLETED_DATE = "COMPLETED_DATE";

    /**
     * NULL
     */
    private static final String NULL = "NULL";

    /**
     * To prevent someone from accidentally instantiating the contract class, give it an empty constructor.
     */
    public TaskDBContract() {
    }

    /**
     * Inner class that defines the table contents
     */
    protected static abstract class TaskContain implements BaseColumns {
        protected static final int DATABASE_VERSION_VALUE = DATABASE_VERSION;
        protected static final String DATABASE_NAME_VALUE = DATABASE_NAME;
        protected static final String TABLE_NAME = TABLE_TASKS;
        protected static final String COLUMN_NAME = KEY_NAME;
        protected static final String COLUMN_DETAILS = KEY_DETAILS;
        protected static final String COLUMN_PRIORITY = KEY_PRIORITY;
        protected static final String COLUMN_STATUS = KEY_STATUS;
        protected static final String COLUMN_PERCENT = KEY_PERCENT;
        protected static final String COLUMN_REMOVED = KEY_REMOVED;
        protected static final String COLUMN_COMPLETED = KEY_COMPLETED;
        protected static final String COLUMN_CREATED_DATE = KEY_CREATED_DATE;
        protected static final String COLUMN_UPDATED_DATE = KEY_UPDATED_DATE;
        protected static final String COLUMN_STARTED_DATE = KEY_STARTED_DATE;
        protected static final String COLUMN_COMPLETED_DATE = KEY_COMPLETED_DATE;
        protected static final String COLUMN_NAME_NULLABLE = NULL;
    }
}
