/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienVNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.todoappproject.utils;

/**
 * {@link StringUtil}
 *
 * @author TienVNguyen
 */
public final class StringUtil {
    /**
     * Checks to see whether the input String is empty. Empty, in this context, means
     * <ul>
     * <li>is null
     * <li>has a length of zero
     * <li>contains only spaces
     * <li>contains the String "null" (not case sensitive)
     * </ul>
     *
     * @param inputStr to be examined.
     * @return true/false.
     */
    public static boolean isEmpty(final String inputStr) {
        return (inputStr == null || inputStr.trim().length() == 0 || ("null").equalsIgnoreCase(inputStr));
    }

    /**
     * Checks to see whether the input Object is not null. Empty, in this context, means
     * <ul>
     * <li>is null
     * </ul>
     *
     * @param inputObj to be examined.
     * @return true/false.
     */
    public static boolean isNotNull(final Object inputObj) {
        return inputObj != null;
    }
}
