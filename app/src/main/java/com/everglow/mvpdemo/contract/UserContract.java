package com.everglow.mvpdemo.contract;

import com.everglow.mvpdemo.model.UserInfoBean;
import com.everglow.mvpdemo.presenter.BasePresenter;

/**
 * Created by EverGlow on 2017/6/29 9:44
 */

public interface UserContract {
    interface LoginView extends BaseView<Persenter>{
        String getCompanyId();

        String getUserName();

        String getPassword();

        void showLoading();

        void hideLoading();

        void cleanEdit();

        void toMainActivity(UserInfoBean user);

        void toastMessage(UserInfoBean user);

        void showFailedError();

    }
    interface  Persenter extends BasePresenter{
        void Login();

    }
}
