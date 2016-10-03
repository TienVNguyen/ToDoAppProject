/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienVNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.todoappproject.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;

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
    @BindView(R.id.ckbNameAsc)
    protected CheckBox checkBoxNameAsc;
    @BindView(R.id.ckbPriorityAsc)
    protected CheckBox checkBoxPriorityAsc;
    @BindView(R.id.ckbStatusAsc)
    protected CheckBox checkBoxStatusAsc;
    @BindView(R.id.ckbPercentAsc)
    protected CheckBox checkBoxPercentAsc;
    @BindView(R.id.ckbDueDateAsc)
    protected CheckBox checkBoxDueDateAsc;
    @BindView(R.id.btnCancel)
    protected Button btnCancel;
    @BindView(R.id.btnAccept)
    protected Button btnAccept;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_filter, container);
        ButterKnife.bind(this, view);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        return view;
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

    @Override
    public void onDismiss(DialogInterface dialog) {
        final FilterModel filterModel = new FilterModel();
        filterModel.setmFilterNameAsc(checkBoxNameAsc.isChecked());
        filterModel.setmFilterPriorityAsc(checkBoxPriorityAsc.isChecked());
        filterModel.setmFilterStatusAsc(checkBoxStatusAsc.isChecked());
        filterModel.setmFilterPercentAsc(checkBoxPercentAsc.isChecked());
        filterModel.setmFilterDueDateAsc(checkBoxDueDateAsc.isChecked());

        final FilterDialogListener listener = (FilterDialogListener) getActivity();
        listener.onFinishFilterDialog(filterModel);

        super.onDismiss(dialog);
    }

    /**
     * Defines the listener interface with a method passing back data result.
     */
    public interface FilterDialogListener {
        void onFinishFilterDialog(FilterModel filterModel);
    }
}
