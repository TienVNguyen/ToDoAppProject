/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienVNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.todoappproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.training.tiennguyen.todoappproject.R;
import com.training.tiennguyen.todoappproject.activities.EditOrRemoveActivity;
import com.training.tiennguyen.todoappproject.constants.VariableConstant;
import com.training.tiennguyen.todoappproject.models.TaskModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link TaskAdapter}
 *
 * @author TienVNguyen
 */
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    /**
     * mContext
     */
    private Context mContext;
    /**
     * mResource
     */
    private int mResource;
    /**
     * mList
     */
    private List<TaskModel> mList;

    /**
     * Constructor
     *
     * @param context  {@link Context}
     * @param resource {@link Integer}
     * @param objects  {@link List<TaskModel>}
     */
    public TaskAdapter(Context context, int resource, List<TaskModel> objects) {
        this.mContext = context;
        this.mResource = resource;
        this.mList = objects;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Context context = parent.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(mResource, parent, false);
        return new TaskViewHolder(context, mList, view);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        final TaskModel model = mList.get(position);
        if (model != null) {
            String name = model.getmName();
            if (name.length() > 25) {
                name = name.substring(25);
                name += "...";
            }
            holder.txtName.setText(name);

            holder.txtStatus.setText(model.getmStatus());
            holder.txtPercent.setText(String.valueOf(model.getmPercent()));

            final String priority = model.getmPriority();
            final String[] priorities = mContext.getResources().getStringArray(R.array.spinner_priority);
            if (priority.equalsIgnoreCase(priorities[0])) {
                holder.txtPriority.setTextColor(ContextCompat.getColor(mContext, R.color.colorCyan));
            } else if (priority.equalsIgnoreCase(priorities[1])) {
                holder.txtPriority.setTextColor(ContextCompat.getColor(mContext, R.color.colorLime));
            } else {
                holder.txtPriority.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed));
            }
            holder.txtPriority.setText(priority);

            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            holder.txtDueDate.setText(sdf.format(model.getmDueDate()));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * Task View Holder
     */
    static class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.listItemTxtName)
        TextView txtName;
        @BindView(R.id.listItemTxtStatus)
        TextView txtStatus;
        @BindView(R.id.listItemTxtPercent)
        TextView txtPercent;
        @BindView(R.id.listItemTxtPriority)
        TextView txtPriority;
        @BindView(R.id.listItemTxtDueDate)
        TextView txtDueDate;

        private Context mContext;
        private List<TaskModel> mList;

        /**
         * Constructor
         *
         * @param context {@link Context}
         * @param list    {@link List<TaskModel>}
         * @param view    {@link View}
         */
        TaskViewHolder(Context context, List<TaskModel> list, View view) {
            super(view);
            this.mContext = context;
            this.mList = list;
            ButterKnife.bind(this, view);

            // Activities Edit/Remove
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                final Context context = mContext;
                final Intent intent = new Intent(context, EditOrRemoveActivity.class);
                intent.putExtra(VariableConstant.TASK_DETAILS_INTENT, mList.get(position));
                context.startActivity(intent);
            }
        }
    }
}
