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

import com.training.tiennguyen.todoappproject.R;

import butterknife.ButterKnife;

/**
 * {@link EditOrRemoveActivity}
 *
 * @author TienVNguyen
 */
public class EditOrRemoveActivity extends AppCompatActivity {

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
    }
}
