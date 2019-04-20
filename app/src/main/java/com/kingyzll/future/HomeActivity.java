package com.kingyzll.future;

import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kingyzll.activityCollector.ActivityCollector;
import com.kingyzll.activityCollector.BaseActivity;
import com.kingyzll.adpater.FragmentAdapter;
import com.kingyzll.eventbus.getmessage.MessageEvent;
import com.kingyzll.fragment.CheckResultFragment;
import com.kingyzll.fragment.DepartmentFragment;
import com.kingyzll.information.StudentInformation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;

public class HomeActivity extends BaseActivity {
    private static final String TAG = "HomeActivity";
    static String NOTIFICATION_CONTENT, NOTIFICATION_DATA;
    public static String STUDENT_NUM;
    private DrawerLayout mDrawerLayout;
    private TextView mTextDepm, mTextMajor;
    private ImageView mIcon;
    private String[] result;

    private ViewPager mViewPager;
    private Intent mIntent;
    private String tag1, tag2;
    //String data;

    private long firstTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Intent intent = getIntent();
        //STUDENT_NUM = intent.getStringExtra("accountNum");
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        STUDENT_NUM = pref.getString("accountNum", "");

        EventBus.getDefault().register(this);
        LitePal.getDatabase();
        Toolbar homeToolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(homeToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.home_menu);
        }
        initView();
        if (JPushInterface.isPushStopped(this)) {
            JPushInterface.resumePush(getApplicationContext());
        }

    }

    private void initView() {
        //获取navigationView里面的XXX
        mDrawerLayout = findViewById(R.id.home_drawer_layout);
        NavigationView mNavigationView = findViewById(R.id.nav_view);
        View headerView = mNavigationView.getHeaderView(0);
        mTextDepm = headerView.findViewById(R.id.nav_department);
        mTextMajor = headerView.findViewById(R.id.nav_major);
        mIcon = findViewById(R.id.icon_image);
        mViewPager = findViewById(R.id.home_viewpager);
        //初始化碎片
        initFragment();
        initData();
        //取出用户数据


        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull View view) {
                initData();
            }

            @Override
            public void onDrawerClosed(@NonNull View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        //mNavigationView.setCheckedItem(R.id.nav_information);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_information:
                        mIntent = new Intent(HomeActivity.this, StudentInformationActivity.class);
                        startActivity(mIntent);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_files:
                        DownloadListActivity.actionStartDownloadListActivity(HomeActivity.this);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_notification:
                        mIntent = new Intent(HomeActivity.this, NotificationListActivity.class);
                        startActivity(mIntent);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.nav_quit:
                        ActivityCollector.finishAll();
                        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                        editor.putString("accountNum", "");
                        editor.putString("login", "0");
                        editor.apply();
                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_update:
                        Intent intent1 = new Intent(HomeActivity.this, UpdatePasswordActivity.class);
                        startActivity(intent1);
                        break;
                    default:
                        break;
                }
                return false;

            }
        });

    }


    private void initFragment() {
        TabLayout mTabLayout = findViewById(R.id.home_tabs);

        List<String> titles = new ArrayList<>();
        titles.add("查看成绩");
        titles.add("院系介绍");

        for (int i = 0; i < titles.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(i)));
        }


        List<Fragment> fragments = new ArrayList<>();

        fragments.add(new CheckResultFragment());
        fragments.add(new DepartmentFragment());

        FragmentAdapter mFragmentAdapteradapter = new FragmentAdapter(getSupportFragmentManager(),
                fragments, titles);
        mViewPager.setAdapter(mFragmentAdapteradapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(mFragmentAdapteradapter);

    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                result = StudentInformation.basicInformation(STUDENT_NUM);
                if (result != null) {
                    upDateNavText(result);
                }

            }
        }).start();
    }

    //更新navigation
    private void upDateNavText(final String[] result) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextDepm.setText(result[1]);
                mTextMajor.setText(result[2]);
                tag1 = result[1];
                tag2 = result[2];
                Set<String> tag = new HashSet<>();
                tag.add(tag1);
                tag.add(tag2);
                Log.e(TAG, tag1 + tag2);
                JPushInterface.setTags(HomeActivity.this, 0, tag);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_tool_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }

        return true;
    }

    //启动活动
    public static void actionStartHomeActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        //intent.putExtra("accountNum", data);
        context.startActivity(intent);
    }


    //接收推送广播的消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getNotificationEvent(MessageEvent messageEvent) {
        NOTIFICATION_CONTENT = messageEvent.getMessage();
        NOTIFICATION_DATA = messageEvent.getDate();
    }

    //退出


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                Toast.makeText(HomeActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
