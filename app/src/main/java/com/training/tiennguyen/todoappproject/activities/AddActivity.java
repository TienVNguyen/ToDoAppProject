/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienVNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.todoappproject.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.training.tiennguyen.todoappproject.R;
import com.training.tiennguyen.todoappproject.constants.VariableConstant;
import com.training.tiennguyen.todoappproject.databases.TaskDBHelper;
import com.training.tiennguyen.todoappproject.models.TaskModel;
import com.training.tiennguyen.todoappproject.utils.DateUtils;
import com.training.tiennguyen.todoappproject.utils.StringUtil;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Init Views
     */
    private void initViews() {
        ButterKnife.bind(this);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle(getString(R.string.title_activity_add));

        txtName.setText("");
        txtName.requestFocus();
        txtDetails.setText("");

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
                    case 0: // Not start
                        seekBarPercent.setProgress(0);
                        checkBoxCompleted.setChecked(false);
                        break;
                    case 2: // Done
                        seekBarPercent.setProgress(100);
                        checkBoxCompleted.setChecked(true);
                        break;
                    default: // In progress
                        seekBarPercent.setProgress(50);
                        checkBoxCompleted.setChecked(false);
                        break;
                }
                txtPercent.setText(seekBarPercent.getProgress() + "% / " + seekBarPercent.getMax() + "%");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Populate data for Percent
        txtPercent.setText(seekBarPercent.getProgress() + "% / " + seekBarPercent.getMax() + "%");
        seekBarPercent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int currentProgress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                txtPercent.setText(seekBar.getProgress() + "% / " + seekBar.getMax() + "%");
                switch (seekBar.getProgress()) {
                    case 0:
                        spinnerStatus.setSelection(0);
                        checkBoxCompleted.setChecked(false);
                        break;
                    case 100:
                        spinnerStatus.setSelection(2);
                        checkBoxCompleted.setChecked(true);
                        break;
                    default:
                        spinnerStatus.setSelection(1);
                        checkBoxCompleted.setChecked(false);
                        break;
                }
                adapterStatus.notifyDataSetChanged();
            }
        });

        checkBoxCompleted.setChecked(false);
        checkBoxCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    seekBarPercent.setProgress(100);
                    spinnerStatus.setSelection(2);
                } else {
                    seekBarPercent.setProgress(50);
                    spinnerStatus.setSelection(1);
                }
            }
        });

        final Date currentDate = new Date();
        datePickerStartedDate.setMinDate(currentDate.getTime());
        datePickerDueDate.setMinDate(currentDate.getTime());

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFunction(v);
            }
        });
    }

    /**
     * Add Function
     *
     * @param view View
     */
    private void addFunction(final View view) {
        if (validateFields(view)) {
            final Date date = new Date();
            final TaskModel model = new TaskModel();
            model.setmName(txtName.getText().toString());
            model.setmDetails(txtDetails.getText().toString());
            model.setmPriority(spinnerPriority.getSelectedItem().toString());
            model.setmStatus(spinnerStatus.getSelectedItem().toString());
            model.setmPercent(seekBarPercent.getProgress());
            model.setmCompleted(checkBoxCompleted.isChecked());
            model.setmRemoved(false);
            model.setmCreatedDate(date);
            model.setmUpdatedDate(date);
            model.setmStartedDate(DateUtils.getDateFromDatePicker(datePickerStartedDate));
            model.setmDueDate(DateUtils.getDateFromDatePicker(datePickerDueDate));

            final Context context = getApplicationContext();
            final TaskDBHelper dbHelper = new TaskDBHelper(context);
            final long i = dbHelper.insertTask(model);
            if (i > 0) {
                Toast.makeText(context, getString(R.string.successfully_add) + model.getmName(), Toast.LENGTH_SHORT).show();
                finish();
            } else if (i == 0) {
                txtName.requestFocus();
                Toast.makeText(context, getString(R.string.error_add_duplicated), Toast.LENGTH_SHORT).show();
            } else {
                txtName.requestFocus();
                Toast.makeText(context, getString(R.string.error_add), Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Validate Fields
     *
     * @param view View
     * @return boolean
     */
    private boolean validateFields(final View view) {
        if (StringUtil.isEmpty(txtName.getText().toString())) {
            txtName.requestFocus();
            Snackbar.make(view, getString(R.string.edt_name_error_empty), Snackbar.LENGTH_LONG).setAction(VariableConstant.ACTION, null).show();
            return false;
        }

        final Date startedDate = DateUtils.getDateFromDatePicker(datePickerStartedDate);
        final Date dueDate = DateUtils.getDateFromDatePicker(datePickerDueDate);
        if (startedDate.compareTo(dueDate) > 0) {
            datePickerStartedDate.requestFocus();
            Snackbar.make(view, getString(R.string.error_date), Snackbar.LENGTH_LONG).setAction(VariableConstant.ACTION, null).show();
            return false;
        }

        return true;
    }
}
