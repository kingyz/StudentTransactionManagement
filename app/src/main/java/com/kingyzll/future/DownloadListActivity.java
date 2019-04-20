package com.kingyzll.future;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kingyzll.activityCollector.BaseActivity;
import com.kingyzll.adpater.DownloadListAdapter;
import com.kingyzll.bean.Files;
import com.kingyzll.information.GetDownloadList;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DownloadListActivity extends BaseActivity {

    private List<Files> filesList = new ArrayList<>();
    private DownloadListAdapter adapter;
    private SwipeRefreshLayout msSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_list);


        Toolbar downloadListToolBar = findViewById(R.id.download_list_toolbar);
        setSupportActionBar(downloadListToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //TextView turnToFileList = findViewById(R.id.download_list_downloaded);
        LinearLayout downloadLayout = findViewById(R.id.downloaded_layout);
        downloadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DownloadListActivity.this, FileListActivity.class);
                startActivity(intent);
            }
        });

        initDownloadList();
//        adapter = new DownloadlistAdapter(DownloadListActivity.this, R.layout.item_download_list, filesList);
//        ListView listView = findViewById(R.id.download_list_view);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Files file = filesList.get(position);
//                DownloadFileActivity.actionStartHomeActivity(DownloadListActivity.this,
//                        file.getF_id(), file.getF_title(), file.getF_content(),
//                        file.getF_date(), file.getF_publisher(), file.getF_url());
//            }
//        });
        RecyclerView downloadListRecyclerVeiw = findViewById(R.id.download_list_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        downloadListRecyclerVeiw.setLayoutManager(layoutManager);

        adapter = new DownloadListAdapter(filesList);
        downloadListRecyclerVeiw.setAdapter(adapter);

        msSwipeRefreshLayout = findViewById(R.id.downlist_swipe_refresh);
        msSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        msSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                filesList.clear();
                initDownloadList();

            }
        });

    }

    private void initDownloadList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Files> list = GetDownloadList.getJSONLastDownload();
                if (list != null) {
                    setDownloadListUI(list);
                }

            }
        }).start();
    }

    private void setDownloadListUI(final List<Files> list) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Files files : list) {
                    Files file = new Files(files.getF_id(), files.getF_title(), files.getF_content(),
                            files.getF_date(), files.getF_publisher(), files.getF_url());
                    filesList.add(file);
                }
                Collections.reverse(filesList);
                adapter.notifyDataSetChanged();
                msSwipeRefreshLayout.setRefreshing(false);
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //启动活动
    public static void actionStartDownloadListActivity(Context context) {
        Intent intent = new Intent(context, DownloadListActivity.class);
        context.startActivity(intent);
    }
}
