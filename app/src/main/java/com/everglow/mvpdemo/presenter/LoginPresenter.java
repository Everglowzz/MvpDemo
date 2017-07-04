package com.everglow.mvpdemo.presenter;

import com.everglow.mvpdemo.contract.UserContract;
import com.everglow.mvpdemo.model.UserInfoBean;
import com.everglow.mvpdemo.utils.Tools;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by EverGlow on 2017/7/3 16:22
 */

public class LoginPresenter implements UserContract.Presenter {

    UserContract.LoginView view;
    boolean mUnSubscribe;

    public LoginPresenter(UserContract.LoginView view) {
        this.view = view;
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
    public void login(Map<String, String> map) {
        view.showLoading();
        OkHttpUtils.post()
                .url("http://yl.cgsoft.net/index.php?g=cgapik&m=index&a=do_login")
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
                });
    }
}
