/*
 * Copyright (c) 2016. Self Training Systems, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by TienVNguyen <tien.workinfo@gmail.com - tien.workinfo@icloud.com>, October 2015
 */

package com.training.tiennguyen.todoappproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.training.tiennguyen.todoappproject.R;
import com.training.tiennguyen.todoappproject.adapters.TaskAdapter;
import com.training.tiennguyen.todoappproject.databases.TaskDBHelper;
import com.training.tiennguyen.todoappproject.models.TaskModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link MainActivity}
 *
 * @author TienVNguyen
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.fab)
    protected FloatingActionButton fab;
    @BindView(R.id.pbList)
    protected ProgressBar pbList;
    @BindView(R.id.txtEmptyList)
    protected TextView txtEmptyList;
    @BindView(R.id.lvTasks)
    protected ListView lvTasks;

    @Override
    protected void onResume() {
        super.onResume();

        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.main_action_filter) {
            openFilterOption();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initViews();
    }

    /**
     * Init Views
     */
    private void initViews() {
        ButterKnife.bind(this);

        setTitle(getString(R.string.title_activity_main));
        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddActivity.class);
                startActivity(intent);
            }
        });

        populateDataForList();
    }

    /**
     * Populate Data For List
     */
    private void populateDataForList() {
        pbList.setVisibility(View.VISIBLE);
        lvTasks.setEmptyView(txtEmptyList);

        final TaskDBHelper dbHelper = new TaskDBHelper(getApplicationContext());
        final List<TaskModel> list = dbHelper.selectAllTasks();
        if (list.size() > 0) {
            final TaskAdapter adapter = new TaskAdapter(getApplicationContext(), R.layout.list_tasks_item, list);
            lvTasks.setAdapter(adapter);
            pbList.setVisibility(View.GONE);
        } else {
            pbList.setVisibility(View.GONE);
        }
    }

    /**
     * Filter Option
     */
    private void openFilterOption() {

    }
}
