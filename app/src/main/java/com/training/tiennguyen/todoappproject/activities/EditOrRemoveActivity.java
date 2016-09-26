/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienVNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.todoappproject.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.training.tiennguyen.todoappproject.R;
import com.training.tiennguyen.todoappproject.constants.VariableConstant;
import com.training.tiennguyen.todoappproject.models.TaskModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link EditOrRemoveActivity}
 *
 * @author TienVNguyen
 */
public class EditOrRemoveActivity extends AppCompatActivity {
    @BindView(R.id.editTaskTxtName)
    protected EditText txtName;
    @BindView(R.id.editTaskTxtDetails)
    protected EditText txtDetails;
    @BindView(R.id.editTaskSpinnerPriority)
    protected Spinner spinnerPriority;
    @BindView(R.id.editTaskSpinnerStatus)
    protected Spinner spinnerStatus;
    @BindView(R.id.editTaskTxtPercent)
    protected TextView txtPercent;
    @BindView(R.id.editTaskSeekBarPercent)
    protected SeekBar seekBarPercent;
    @BindView(R.id.editTaskCheckBoxCompleted)
    protected CheckBox checkBoxCompleted;
    @BindView(R.id.editTaskDatePickerStartedDate)
    protected DatePicker datePickerStartedDate;
    @BindView(R.id.editTaskDatePickerDueDate)
    protected DatePicker datePickerDueDate;
    @BindView(R.id.btnEdit)
    protected Button btnEdit;
    @BindView(R.id.btnRemove)
    protected Button btnRemove;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_or_remove);

        initViews();
    }

    /**
     * Init Views
     */
    private void initViews() {
        ButterKnife.bind(this);

        setTitle(getString(R.string.title_activity_edit_or_remove));

        final TaskModel taskModel = getIntent().getParcelableExtra(VariableConstant.TASK_DETAILS_INTENT);
        if (taskModel != null) {
            txtName.setText(taskModel.getmName());
            txtDetails.setText(taskModel.getmDetails());
            txtPercent.setText(taskModel.getmPercent());
            checkBoxCompleted.setChecked(taskModel.ismCompleted());

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            Log.e("ERROR_TITLE", "ERROR_VALUE");
        }
    }
}
