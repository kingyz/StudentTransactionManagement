package com.kingyzll.future;

import android.app.Application;

import org.litepal.LitePal;

import cn.jpush.android.api.JPushInterface;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        LitePal.initialize(this);
    }
}
