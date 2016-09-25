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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.training.tiennguyen.todoappproject.R;
import com.training.tiennguyen.todoappproject.databases.TaskDBHelper;
import com.training.tiennguyen.todoappproject.models.TaskModel;
import com.training.tiennguyen.todoappproject.utils.DateUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link AddActivity}
 *
 * @author TienVNguyen
 */
public class AddActivity extends AppCompatActivity {
    @BindView(R.id.addTaskTxtName)
    protected EditText txtName;
    @BindView(R.id.addTaskTxtDetails)
    protected EditText txtDetails;
    @BindView(R.id.addTaskSpinnerPriority)
    protected Spinner spinnerPriority;
    @BindView(R.id.addTaskSpinnerStatus)
    protected Spinner spinnerStatus;
    @BindView(R.id.addTaskTxtPercent)
    protected TextView txtPercent;
    @BindView(R.id.addTaskSeekBarPercent)
    protected SeekBar seekBarPercent;
    @BindView(R.id.addTaskCheckBoxCompleted)
    protected CheckBox checkBoxCompleted;
    @BindView(R.id.addTaskDatePickerStartedDate)
    protected DatePicker datePickerStartedDate;
    @BindView(R.id.addTaskDatePickerDueDate)
    protected DatePicker datePickerDueDate;
    @BindView(R.id.btnAdd)
    protected Button btnAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add);

        initViews();
    }

    /**
     * Init Views
     */
    private void initViews() {
        ButterKnife.bind(this);

        setTitle(getString(R.string.title_activity_add));

        txtName.setText("");
        txtDetails.setText("");
        checkBoxCompleted.setChecked(false);

        // Minimum date
        Date date = new Date();
        datePickerStartedDate.setMinDate(date.getTime());
        datePickerDueDate.setMinDate(date.getTime());

        // Populate data for Priority
        final ArrayAdapter<CharSequence> adapterPriority = ArrayAdapter.createFromResource(this,
                R.array.spinner_priority, android.R.layout.simple_spinner_item);
        adapterPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(adapterPriority);

        // Populate data for Status
        final ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(this,
                R.array.spinner_status, android.R.layout.simple_spinner_item);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapterStatus);
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        // Not start
                        seekBarPercent.setProgress(0);
                        break;
                    case 2:
                        // Done
                        seekBarPercent.setProgress(100);
                        break;
                    default:
                        // In progress
                        seekBarPercent.setProgress(50);
                        break;
                }
                txtPercent.setText(seekBarPercent.getProgress() + "% of" + seekBarPercent.getMax() + "%");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // Populate data for Percent
        txtPercent.setText(seekBarPercent.getProgress() + "% of" + seekBarPercent.getMax() + "%");
        seekBarPercent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int currentProgress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                txtPercent.setText(seekBar.getProgress() + "% of" + seekBar.getMax() + "%");
                switch (seekBar.getProgress()) {
                    case 0:
                        spinnerStatus.setSelection(0);
                        break;
                    case 100:
                        spinnerStatus.setSelection(2);
                        break;
                    default:
                        spinnerStatus.setSelection(1);
                        break;
                }
                adapterStatus.notifyDataSetChanged();
            }
        });

        // Add
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Date date = new Date();
                final TaskModel model = new TaskModel();
                model.setmName(txtName.getText().toString());
                model.setmDetails(txtDetails.getText().toString());
                model.setmPriority(spinnerPriority.getSelectedItem().toString());
                model.setmStatus(spinnerStatus.getSelectedItem().toString());
                model.setmPercent(seekBarPercent.getProgress());
                model.setmCreatedDate(date);
                model.setmUpdatedDate(date);
                model.setmStartedDate(DateUtils.getDateFromDatePicker(datePickerStartedDate));
                model.setmDueDate(DateUtils.getDateFromDatePicker(datePickerDueDate));

                final TaskDBHelper dbHelper = new TaskDBHelper(getApplicationContext());
                if (dbHelper.insertTask(model) > 0) {
                    finish();
                } else {
                    txtName.requestFocus();
                    Toast.makeText(AddActivity.this, "ERROR_ADD", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
