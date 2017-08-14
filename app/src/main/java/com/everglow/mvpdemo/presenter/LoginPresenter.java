package com.everglow.mvpdemo.presenter;

import android.content.Context;

import com.everglow.mvpdemo.config.GlobalConfig;
import com.everglow.mvpdemo.contract.IloginContract;
import com.everglow.mvpdemo.httputils.RequestNetWork;
import com.everglow.mvpdemo.httputils.listener.HttpCallBack;
import com.everglow.mvpdemo.model.UserInfoBean;
import com.everglow.mvpdemo.utils.Tools;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by EverGlow on 2017/7/3 16:22
 */

public class LoginPresenter implements IloginContract.IloginPresenter {

    IloginContract.IloginView view;
    boolean mUnSubscribe;
    Context context;
    private final RequestNetWork<UserInfoBean> mRqeuestNetWork;

    public LoginPresenter(IloginContract.IloginView view) {
        this.view = view;
        context = (Context) view;
        mRqeuestNetWork = new RequestNetWork<>(context);
        view.setPresenter(this);
    }

    @Override
    public void onSubscribe() {

    }

    @Override
    public void unSubscribe() {
        mUnSubscribe = true;
    }

    @Override
    public void login(final HashMap<String, String> map) {

        map.put("token", GlobalConfig.TOKEN);
        map.put("password",Tools.encryptPasswordMD5(map.get("password")));
        mRqeuestNetWork.request(map, GlobalConfig.LOGIN_URl, new HttpCallBack<UserInfoBean>() {
            @Override
            public void onSuccess(UserInfoBean result) {
                if (mUnSubscribe) return;
                view.toMainActivity(result);
                view.toastMessage(result);
            }

            @Override
            public void onFailure(String error) {
                if (mUnSubscribe) return;

            }
        },UserInfoBean.class,"正在登录..");

/**
        ----------------------------------上边是封装的网络求情----------------------------------------------------

        ----------------------------------下边是最早的网络求情------------------------------------------------------
*/

     /*   OkHttpUtils.post()
                .url(GlobalConfig.LOGIN_URl)
                .addParams("keynumber", map.get("keynumber"))
                .addParams("username", map.get("username"))
                .addParams("token", "43378e1b35ae7858e82eba2b27ddefd7")
                .addParams("password", Tools.encryptPasswordMD5(map.get("password")))
                .build()
                .execute(new Callback<UserInfoBean>() {
                    @Override
                    public UserInfoBean parseNetworkResponse(Response response, int id) throws Exception {
                        return new Gson().fromJson(response.body().string(), UserInfoBean.class);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        if (mUnSubscribe) return;
                        view.hideLoading();
                        view.showFailedError();
                    }

                    @Override
                    public void onResponse(UserInfoBean response, int id) {
                        if (mUnSubscribe) return;
                        if (response.getCode() == 1) {
                            view.toMainActivity(response);
                        }
                        view.toastMessage(response);
                        view.hideLoading();
                    }
                });*/
    }
}
