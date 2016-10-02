/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienVNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.todoappproject.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * {@link }
 *
 * @author TienVNguyen
 */
public class FilterModel implements Parcelable {
    /**
     * After implementing the `Parcelable` interface, we need to create the
     * `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
     */
    public static final Creator<FilterModel> CREATOR = new Creator<FilterModel>() {
        @Override
        public FilterModel createFromParcel(Parcel in) {
            return new FilterModel(in);
        }

        @Override
        public FilterModel[] newArray(int size) {
            return new FilterModel[size];
        }
    };
    /**
     * Filter Name ASC
     */
    private boolean mFilterNameAsc = true;
    /**
     * Filter Priority ASC
     */
    private boolean mFilterPriorityAsc = true;
    /**
     * Filter Status ASC
     */
    private boolean mFilterStatusAsc = true;
    /**
     * Filter Percent ASC
     */
    private boolean mFilterPercentAsc = true;
    /**
     * Filter Due Date ASC
     */
    private boolean mFilterDueDateAsc = true;

    /**
     * Constructor
     */
    public FilterModel() {
    }

    /**
     * Constructor
     *
     * @param mFilterNameAsc     boolean
     * @param mFilterPriorityAsc boolean
     * @param mFilterStatusAsc   boolean
     * @param mFilterPercentAsc  boolean
     * @param mFilterDueDateAsc  boolean
     */
    public FilterModel(boolean mFilterNameAsc, boolean mFilterPriorityAsc,
                       boolean mFilterStatusAsc, boolean mFilterPercentAsc,
                       boolean mFilterDueDateAsc) {
        this.mFilterNameAsc = mFilterNameAsc;
        this.mFilterPriorityAsc = mFilterPriorityAsc;
        this.mFilterStatusAsc = mFilterStatusAsc;
        this.mFilterPercentAsc = mFilterPercentAsc;
        this.mFilterDueDateAsc = mFilterDueDateAsc;
    }

    /**
     * Constructor
     *
     * @param in Parcel
     */
    private FilterModel(Parcel in) {
        mFilterNameAsc = in.readByte() != 0;
        mFilterPriorityAsc = in.readByte() != 0;
        mFilterStatusAsc = in.readByte() != 0;
        mFilterPercentAsc = in.readByte() != 0;
        mFilterDueDateAsc = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (mFilterNameAsc ? 1 : 0));
        dest.writeByte((byte) (mFilterPriorityAsc ? 1 : 0));
        dest.writeByte((byte) (mFilterStatusAsc ? 1 : 0));
        dest.writeByte((byte) (mFilterPercentAsc ? 1 : 0));
        dest.writeByte((byte) (mFilterDueDateAsc ? 1 : 0));
    }

    /**
     * is method
     *
     * @return mFilterNameAsc
     */
    public boolean ismFilterNameAsc() {
        return mFilterNameAsc;
    }

    /**
     * setter method
     */
    public void setmFilterNameAsc(boolean mFilterNameAsc) {
        this.mFilterNameAsc = mFilterNameAsc;
    }

    /**
     * is method
     *
     * @return mFilterPriorityAsc
     */
    public boolean ismFilterPriorityAsc() {
        return mFilterPriorityAsc;
    }

    /**
     * setter method
     */
    public void setmFilterPriorityAsc(boolean mFilterPriorityAsc) {
        this.mFilterPriorityAsc = mFilterPriorityAsc;
    }

    /**
     * is method
     *
     * @return mFilterStatusAsc
     */
    public boolean ismFilterStatusAsc() {
        return mFilterStatusAsc;
    }

    /**
     * setter method
     */
    public void setmFilterStatusAsc(boolean mFilterStatusAsc) {
        this.mFilterStatusAsc = mFilterStatusAsc;
    }

    /**
     * is method
     *
     * @return mFilterPercentAsc
     */
    public boolean ismFilterPercentAsc() {
        return mFilterPercentAsc;
    }

    /**
     * setter method
     */
    public void setmFilterPercentAsc(boolean mFilterPercentAsc) {
        this.mFilterPercentAsc = mFilterPercentAsc;
    }

    /**
     * is method
     *
     * @return mFilterDueDateAsc
     */
    public boolean ismFilterDueDateAsc() {
        return mFilterDueDateAsc;
    }

    /**
     * setter method
     */
    public void setmFilterDueDateAsc(boolean mFilterDueDateAsc) {
        this.mFilterDueDateAsc = mFilterDueDateAsc;
    }

}
