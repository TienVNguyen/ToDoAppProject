/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienVNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.todoappproject.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
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
import com.training.tiennguyen.todoappproject.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
    @BindView(R.id.editTaskStartedSelect)
    protected EditText datePickerStartedDate;
    @BindView(R.id.editTaskDueSelect)
    protected EditText datePickerDueDate;
    @BindView(R.id.btnEdit)
    protected Button btnEdit;
    @BindView(R.id.btnRemove)
    protected Button btnRemove;

    private DatePickerDialog datePickerDialogForStartedDate;
    private DatePickerDialog datePickerDialogForDueDate;
    private Date startedDate;
    private Date dueDate;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_or_remove);

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
        setTitle(getString(R.string.title_activity_edit_or_remove));

        final TaskModel taskModel = getIntent().getParcelableExtra(VariableConstant.TASK_DETAILS_INTENT);
        if (taskModel != null) {
            loadViews(taskModel);
        } else {
            Toast.makeText(EditOrRemoveActivity.this, getString(R.string.error_load_details), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * Load views
     *
     * @param taskModel TaskModel
     */
    private void loadViews(final TaskModel taskModel) {
        txtName.setText(taskModel.getmName());
        txtDetails.setText(taskModel.getmDetails());
        txtDetails.requestFocus();

        // Populate data for Priority
        final ArrayAdapter<CharSequence> adapterPriority = ArrayAdapter.createFromResource(this,
                R.array.spinner_priority, android.R.layout.simple_spinner_item);
        adapterPriority.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPriority.setAdapter(adapterPriority);
        spinnerPriority.setSelection(adapterPriority.getPosition(taskModel.getmPriority()));
        spinnerPriority.refreshDrawableState();

        // Populate data for Status
        final ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(this,
                R.array.spinner_status, android.R.layout.simple_spinner_item);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapterStatus);
        spinnerStatus.setSelection(adapterStatus.getPosition(taskModel.getmStatus()));
        spinnerStatus.refreshDrawableState();
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
        final int position = taskModel.getmPercent();
        txtPercent.setText(String.valueOf(position));
        seekBarPercent.setProgress(position);
        seekBarPercent.refreshDrawableState();
        txtPercent.setText(seekBarPercent.getProgress() + "% / " + seekBarPercent.getMax() + "%");
        seekBarPercent.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int currentProgress = taskModel.getmPercent();

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                currentProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

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

        checkBoxCompleted.setChecked(taskModel.ismCompleted());
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
        startedDate = taskModel.getmStartedDate();
        dueDate = taskModel.getmDueDate();

        datePickerStartedDate.setText(dateFormatter.format(startedDate));
        datePickerStartedDate.setInputType(InputType.TYPE_NULL);
        datePickerStartedDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    datePickerDialogForStartedDate.show();
                v.clearFocus();
            }
        });
        datePickerDueDate.setText(dateFormatter.format(dueDate));
        datePickerDueDate.setInputType(InputType.TYPE_NULL);
        datePickerDueDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    datePickerDialogForDueDate.show();
                v.clearFocus();
            }
        });

        final Calendar newCalendar = Calendar.getInstance();
        datePickerDialogForStartedDate = new DatePickerDialog(EditOrRemoveActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                startedDate = newDate.getTime();
                datePickerStartedDate.setText(dateFormatter.format(startedDate));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialogForStartedDate.getDatePicker().setMinDate(currentDate.getTime());
        datePickerDialogForDueDate = new DatePickerDialog(EditOrRemoveActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                dueDate = newDate.getTime();
                datePickerDueDate.setText(dateFormatter.format(dueDate));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialogForDueDate.getDatePicker().setMinDate(currentDate.getTime());

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditFunction(v);
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoveFunction();
            }
        });
    }

    /**
     * Remove Function
     */
    private void RemoveFunction() {
        new android.support.v7.app.AlertDialog.Builder(EditOrRemoveActivity.this)
                .setTitle(getString(R.string.remove_title_confirm))
                .setMessage(getString(R.string.remove_message_confirm))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final TaskModel model = buildObjectTask(true);
                        final Context context = getApplicationContext();
                        final TaskDBHelper taskDBHelper = new TaskDBHelper(context);
                        if (taskDBHelper.deleteTask(model) > 0) {
                            Toast.makeText(context, getString(R.string.successfully_remove) + model.getmName(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(context, getString(R.string.error_remove), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Edit Function
     *
     * @param view View
     */
    private void EditFunction(final View view) {
        if (validateFields(view)) {
            final TaskModel model = buildObjectTask(false);
            final Context context = getApplicationContext();
            final TaskDBHelper dbHelper = new TaskDBHelper(context);
            if (dbHelper.updateTask(model) > 0) {
                Toast.makeText(context, getString(R.string.successfully_edit) + model.getmName(), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(context, getString(R.string.error_edit), Toast.LENGTH_SHORT).show();
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

        if (startedDate.compareTo(dueDate) > 0) {
            datePickerStartedDate.requestFocus();
            Snackbar.make(view, getString(R.string.error_date), Snackbar.LENGTH_LONG).setAction(VariableConstant.ACTION, null).show();
            return false;
        }

        return true;
    }

    /**
     * Build Object Task
     *
     * @param isRemoved boolean
     * @return TaskModel
     */
    private TaskModel buildObjectTask(final boolean isRemoved) {
        final Date date = new Date();
        final TaskModel model = new TaskModel();
        model.setmName(txtName.getText().toString());
        model.setmDetails(txtDetails.getText().toString());
        model.setmPriority(spinnerPriority.getSelectedItem().toString());
        model.setmStatus(spinnerStatus.getSelectedItem().toString());
        model.setmPercent(seekBarPercent.getProgress());
        model.setmRemoved(isRemoved);
        model.setmUpdatedDate(date);
        model.setmStartedDate(startedDate);
        model.setmDueDate(dueDate);
        return model;
    }
}
