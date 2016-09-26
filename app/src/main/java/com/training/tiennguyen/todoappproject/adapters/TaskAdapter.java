/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienVNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.todoappproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.training.tiennguyen.todoappproject.R;
import com.training.tiennguyen.todoappproject.activities.EditOrRemoveActivity;
import com.training.tiennguyen.todoappproject.constants.VariableConstant;
import com.training.tiennguyen.todoappproject.models.TaskModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link TaskAdapter}
 *
 * @author TienVNguyen
 */
public class TaskAdapter extends ArrayAdapter<TaskModel> {
    /**
     * mResource
     */
    private int mResource;

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public TaskAdapter(Context context, int resource, List<TaskModel> objects) {
        super(context, 0, objects);
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Caching
        final TaskViewHolder holder;
        if (convertView != null) {
            holder = (TaskViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(mResource, parent, false);
            holder = new TaskViewHolder(convertView);
            convertView.setTag(holder);
        }

        // Populating
        final TaskModel model = getItem(position);
        if (model != null) {
            holder.txtName.setText(model.getmName());
            holder.txtPriority.setText(model.getmPriority());
            holder.txtStatus.setText(model.getmStatus());
            holder.txtPercent.setText(String.valueOf(model.getmPercent()));
            holder.txtDueDate.setText(model.getmDueDate().toString());
        }

        // Activities Edit/Remove
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getContext(), EditOrRemoveActivity.class);
                intent.putExtra(VariableConstant.TASK_DETAILS_INTENT, model);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    /**
     * Task View Holder
     */
    protected static class TaskViewHolder {
        @BindView(R.id.listItemTxtName)
        protected TextView txtName;
        @BindView(R.id.listItemTxtPriority)
        protected TextView txtPriority;
        @BindView(R.id.listItemTxtStatus)
        protected TextView txtStatus;
        @BindView(R.id.listItemTxtPercent)
        protected TextView txtPercent;
        @BindView(R.id.listItemTxtDueDate)
        protected TextView txtDueDate;

        /**
         * Constructor
         *
         * @param view View
         */
        protected TaskViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
