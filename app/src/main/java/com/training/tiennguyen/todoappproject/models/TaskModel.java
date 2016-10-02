/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienVNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.todoappproject.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * {@link TaskModel}
 *
 * @author TienVNguyen
 */
public class TaskModel implements Parcelable {
    /**
     * After implementing the `Parcelable` interface, we need to create the
     * `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
     */
    public static final Creator<TaskModel> CREATOR = new Creator<TaskModel>() {

        @Override
        public TaskModel createFromParcel(Parcel in) {
            return new TaskModel(in);
        }

        @Override
        public TaskModel[] newArray(int size) {
            return new TaskModel[size];
        }
    };
    /**
     * Name
     */
    private String mName = "";
    /**
     * Details
     */
    private String mDetails = "";
    /**
     * Priority
     */
    private String mPriority = "";
    /**
     * Status
     */
    private String mStatus = "";
    /**
     * Percent
     */
    private int mPercent = 0;
    /**
     * Removed
     */
    private boolean mRemoved = false;
    /**
     * Completed
     */
    private boolean mCompleted = false;
    /**
     * Created Date
     */
    private Date mCreatedDate;
    /**
     * Updated Date
     */
    private Date mUpdatedDate;
    /**
     * Started Date
     */
    private Date mStartedDate;
    /**
     * Due Date
     */
    private Date mDueDate;

    /**
     * Constructor
     */
    public TaskModel() {
    }

    /**
     * Constructor
     *
     * @param in {@link Parcel}
     */
    private TaskModel(Parcel in) {
        mName = in.readString();
        mDetails = in.readString();
        mPriority = in.readString();
        mStatus = in.readString();
        mPercent = in.readInt();
        mRemoved = in.readByte() != 0;
        mCompleted = in.readByte() != 0;
        mCreatedDate = new Date(in.readLong());
        mUpdatedDate = new Date(in.readLong());
        mStartedDate = new Date(in.readLong());
        mDueDate = new Date(in.readLong());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mDetails);
        dest.writeString(mPriority);
        dest.writeString(mStatus);
        dest.writeInt(mPercent);
        dest.writeByte((byte) (mRemoved ? 1 : 0));
        dest.writeByte((byte) (mCompleted ? 1 : 0));
        dest.writeLong(mCreatedDate.getTime());
        dest.writeLong(mUpdatedDate.getTime());
        dest.writeLong(mStartedDate.getTime());
        dest.writeLong(mDueDate.getTime());
    }

    /**
     * getter method
     *
     * @return mDueDate
     */
    public Date getmDueDate() {
        return mDueDate;
    }

    /**
     * setter method
     */
    public void setmDueDate(Date mDueDate) {
        this.mDueDate = mDueDate;
    }

    /**
     * getter method
     *
     * @return mName
     */
    public String getmName() {
        return mName;
    }

    /**
     * setter method
     */
    public void setmName(String mName) {
        this.mName = mName;
    }

    /**
     * getter method
     *
     * @return mDetails
     */
    public String getmDetails() {
        return mDetails;
    }

    /**
     * setter method
     */
    public void setmDetails(String mDetails) {
        this.mDetails = mDetails;
    }

    /**
     * getter method
     *
     * @return mPriority
     */
    public String getmPriority() {
        return mPriority;
    }

    /**
     * setter method
     */
    public void setmPriority(String mPriority) {
        this.mPriority = mPriority;
    }

    /**
     * getter method
     *
     * @return mStatus
     */
    public String getmStatus() {
        return mStatus;
    }

    /**
     * setter method
     */
    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    /**
     * getter method
     *
     * @return mPercent
     */
    public int getmPercent() {
        return mPercent;
    }

    /**
     * setter method
     */
    public void setmPercent(int mPercent) {
        this.mPercent = mPercent;
    }

    /**
     * is method
     *
     * @return mRemoved
     */
    public boolean ismRemoved() {
        return mRemoved;
    }

    /**
     * setter method
     */
    public void setmRemoved(boolean mRemoved) {
        this.mRemoved = mRemoved;
    }

    /**
     * is method
     *
     * @return mCompleted
     */
    public boolean ismCompleted() {
        return mCompleted;
    }

    /**
     * setter method
     */
    public void setmCompleted(boolean mCompleted) {
        this.mCompleted = mCompleted;
    }

    /**
     * getter method
     *
     * @return mCreatedDate
     */
    public Date getmCreatedDate() {
        return mCreatedDate;
    }

    /**
     * setter method
     */
    public void setmCreatedDate(Date mCreatedDate) {
        this.mCreatedDate = mCreatedDate;
    }

    /**
     * getter method
     *
     * @return mUpdatedDate
     */
    public Date getmUpdatedDate() {
        return mUpdatedDate;
    }

    /**
     * setter method
     */
    public void setmUpdatedDate(Date mUpdatedDate) {
        this.mUpdatedDate = mUpdatedDate;
    }

    /**
     * getter method
     *
     * @return mStartedDate
     */
    public Date getmStartedDate() {
        return mStartedDate;
    }

    /**
     * setter method
     */
    public void setmStartedDate(Date mStartedDate) {
        this.mStartedDate = mStartedDate;
    }
}
