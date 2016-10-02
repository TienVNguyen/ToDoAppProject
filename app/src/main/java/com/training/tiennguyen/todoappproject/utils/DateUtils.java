/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienVNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.todoappproject.utils;

import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Date Utils
 *
 * @author TienVNguyen
 */
public class DateUtils {
    /**
     * getDateFromDatePicker
     *
     * @param datePicker {@link DatePicker}
     * @return {@link Date}
     */
    public static Date getDateFromDatePicker(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
