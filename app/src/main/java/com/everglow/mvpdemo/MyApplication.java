package com.everglow.mvpdemo;
import android.app.Application;

import com.everglow.mvpdemo.config.LogInterceptor;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by EverGlow on 2017/7/1 11:08
 */

public    class MyApplication extends Application   {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        //初始化okhttpUtils
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5000L, TimeUnit.MILLISECONDS)
                .readTimeout(5000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LogInterceptor(this))
                .cookieJar(cookieJar)
                .build();
        OkHttpUtils.initClient(okHttpClient);

    }
}
