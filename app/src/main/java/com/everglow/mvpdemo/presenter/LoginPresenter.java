package com.everglow.mvpdemo.presenter;
import com.everglow.mvpdemo.contract.UserContract;
import com.everglow.mvpdemo.model.UserInfoBean;
import com.everglow.mvpdemo.utils.Tools;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.https.HttpsUtils;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by EverGlow on 2017/7/3 16:22
 */

public    class LoginPresenter  implements UserContract.Persenter  {

    UserContract.LoginView view;
    public LoginPresenter(UserContract.LoginView view) {

        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void Login() {
        view.showLoading();
        OkHttpUtils.post()
                .url("http://yl.cgsoft.net/index.php?g=cgapik&m=index&a=do_login")
                .addParams("keynumber",view.getCompanyId())
                .addParams("username",view.getUserName())
                .addParams("token","43378e1b35ae7858e82eba2b27ddefd7")
                .addParams("password", Tools.encryptPasswordMD5(view.getPassword()))
                .build()
                .execute(new Callback<UserInfoBean>() {
                    @Override
                    public UserInfoBean parseNetworkResponse(Response response, int id) throws Exception {
                        return new Gson().fromJson(response.body().string(), UserInfoBean.class);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        e.printStackTrace();
                        view.hideLoading();
                        view.showFailedError();
                    }

                    @Override
                    public void onResponse(UserInfoBean response, int id) {
                        if(response.getCode() ==1){
                            view.toMainActivity(response);
                        }else{
                            view.cleanEdit();
                        }
                        view.toastMessage(response);
                        view.hideLoading();
                    }
                });
    }

    @Override
    public void start() {
            Login();
    }
}
