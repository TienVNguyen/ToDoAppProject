/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienVNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.todoappproject.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.TextView;

import com.training.tiennguyen.todoappproject.R;
import com.training.tiennguyen.todoappproject.constants.VariableConstant;
import com.training.tiennguyen.todoappproject.models.FilterModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link }
 *
 * @author TienVNguyen
 */
public class FilterDialogFragment extends DialogFragment {
    @BindView(R.id.checkbox_name_asc)
    protected CheckBox checkBoxNameAsc;
    @BindView(R.id.checkbox_priority_asc)
    protected CheckBox checkBoxPriorityAsc;
    @BindView(R.id.checkbox_status_asc)
    protected CheckBox checkBoxStatusAsc;
    @BindView(R.id.checkbox_percent_asc)
    protected CheckBox checkBoxPercentAsc;
    @BindView(R.id.checkbox_due_date_asc)
    protected CheckBox checkBoxDueDateAsc;

    /**
     * Defines the listener interface with a method passing back data result.
     */
    public interface FilterDialogListener {
        void onFinishFilterDialog(FilterModel filterModel);
    }

    /**
     * Empty constructor is required for DialogFragment
     * Make sure not to add arguments to the constructor
     * Use `newInstance` instead as shown below
     */
    public FilterDialogFragment() {
    }

    /**
     * Create a new instance of MyDialogFragment, providing "num" as an argument.
     */
    public static FilterDialogFragment newInstance(final FilterModel filterModel) {
        final FilterDialogFragment fragment = new FilterDialogFragment();

        // Supply num input as an argument.
        final Bundle args = new Bundle();
        args.putParcelable(VariableConstant.FILTER_MODEL, filterModel);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Send Back result
     */
    public void sendBackResult (){
        FilterModel filterModel = new FilterModel();
        filterModel.setmFilterNameAsc(checkBoxNameAsc.isChecked());
        filterModel.setmFilterPriorityAsc(checkBoxPriorityAsc.isChecked());
        filterModel.setmFilterStatusAsc(checkBoxStatusAsc.isChecked());
        filterModel.setmFilterPercentAsc(checkBoxPercentAsc.isChecked());
        filterModel.setmFilterDueDateAsc(checkBoxDueDateAsc.isChecked());

        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        FilterDialogListener listener = (FilterDialogListener) getTargetFragment();
        listener.onFinishFilterDialog(filterModel);
        dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_filter, container);

        //checkBoxNameAsc.setOnEditorActionListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        Dialog dialog = getDialog();
        dialog.setTitle(getString(R.string.title_dialog_filter));

        FilterModel filterModel = getArguments().getParcelable(VariableConstant.FILTER_MODEL);
        if (filterModel != null) {
            checkBoxNameAsc.setChecked(filterModel.ismFilterNameAsc());
            checkBoxPriorityAsc.setChecked(filterModel.ismFilterPriorityAsc());
            checkBoxStatusAsc.setChecked(filterModel.ismFilterStatusAsc());
            checkBoxPercentAsc.setChecked(filterModel.ismFilterPercentAsc());
            checkBoxDueDateAsc.setChecked(filterModel.ismFilterDueDateAsc());
        }

        //noinspection ConstantConditions
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


}
