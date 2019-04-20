package com.kingyzll.future;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kingyzll.activityCollector.BaseActivity;


public class ProgramDetailsActivity extends BaseActivity {

    String title, feature, intro, core, imageId;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_details);
        Intent intent = getIntent();
        id = intent.getIntExtra("p_id", 0);
        title = intent.getStringExtra("p_title");
        feature = intent.getStringExtra("p_feature");
        intro = intent.getStringExtra("p_intro");
        core = intent.getStringExtra("p_core");
        imageId = intent.getStringExtra("p_imageId");
        initView();

        Toolbar toolbar = findViewById(R.id.pg_detail_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initView() {
        TextView tv_feature = findViewById(R.id.p_feature);
        TextView tv_intro = findViewById(R.id.p_intro);
        TextView tv_core = findViewById(R.id.p_core);
        ImageView p_image = findViewById(R.id.pg_detail_image);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.pg_detail_collapsing_toolbar);
        collapsingToolbarLayout.setTitle(title);
        tv_feature.setText(feature);
        tv_intro.setText(intro);
        tv_core.setText(core);
        Glide.with(ProgramDetailsActivity.this).load(imageId).into(p_image);
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

    //用于传递参数
    public static void actionStartProgramDetailsActivity(Context context, int p_id, String p_title,
                                                         String p_feature, String p_intro, String p_core, String p_imageId) {
        Intent intent = new Intent(context, ProgramDetailsActivity.class);
        intent.putExtra("p_id", p_id);
        intent.putExtra("p_title", p_title);
        intent.putExtra("p_feature", p_feature);
        intent.putExtra("p_intro", p_intro);
        intent.putExtra("p_core", p_core);
        intent.putExtra("p_imageId", p_imageId);
        context.startActivity(intent);
    }
}
