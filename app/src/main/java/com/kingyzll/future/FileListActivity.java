package com.kingyzll.future;

import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.kingyzll.activityCollector.BaseActivity;
import com.kingyzll.adpater.DeleteFileAdapter;
import com.kingyzll.adpater.FilelistAdapter;
import com.kingyzll.bean.DownloadedFiles;
import com.kingyzll.bean.Files;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import static com.kingyzll.updateUI.DownloadTask.FILE_DIRECTORY;
import static com.kingyzll.util.codeOfURL.CodeToWord;
import static com.kingyzll.util.codeOfURL.isUtf8Url;

public class FileListActivity extends BaseActivity{

    ArrayList<DownloadedFiles> filesList = new ArrayList<>();

    FilelistAdapter adapter;

    private static final String TAG = "FileListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_list);

//        RecyclerView recyclerView = findViewById(R.id.file_list_recycle_view);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        adapter = new FilelistAdapter(filesList);
//        recyclerView.setAdapter(adapter);

        DeleteFileAdapter deleteFileAdapter = new DeleteFileAdapter(filesList);
        ListView listView = findViewById(R.id.downloaded_listview);
        listView.setAdapter(deleteFileAdapter);
        Toolbar fileListToolBar = findViewById(R.id.file_list_toolbar);
        setSupportActionBar(fileListToolBar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        initView();
    }

    private void initView() {
        File file = new File(FILE_DIRECTORY);
        String titleName;
        int imageId;
        if (file.isDirectory()) {
            File[] files = file.listFiles();

            for (File file1 : files) {
                titleName = file1.getName();
                //String newName = titleName.substring(0, titleName.indexOf("."));
                Log.e(TAG, titleName);
                if (titleName.endsWith(".doc") || titleName.endsWith(".docx")) {
                    imageId = R.drawable.word;
                } else if (titleName.endsWith(".xls") || titleName.endsWith(".xlsx")) {
                    imageId = R.drawable.xls;
                } else if (titleName.endsWith(".zip") || titleName.endsWith(".rar")) {
                    imageId = R.drawable.zip;
                } else if (titleName.endsWith(".jpg") || titleName.endsWith(".jpeg") || titleName.endsWith("png")) {
                    imageId = R.drawable.pic;
                } else {
                    imageId = R.drawable.unknow;
                }

                try {
                    String initTitleName = URLDecoder.decode(titleName, "UTF-8");
                    //Files files1 = new Files(initTitleNae, imageId);
                    //filesList.add(files1);
                    String type = initTitleName.substring(initTitleName.lastIndexOf("."));
                    initTitleName = initTitleName.substring(0, initTitleName.indexOf("."));
                    DownloadedFiles downloadedFiles = new DownloadedFiles(initTitleName, type, imageId);
                    filesList.add(downloadedFiles);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
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


}
