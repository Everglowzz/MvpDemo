package com.everglow.mvpdemo.contract;

import com.everglow.mvpdemo.model.UserInfoBean;
import com.everglow.mvpdemo.presenter.BasePresenter;

import java.util.Map;

/**
 * Created by EverGlow on 2017/6/29 9:44
 */

public interface UserContract {

    interface LoginView extends BaseView<Presenter> {
        void showLoading();

        void hideLoading();

        void toMainActivity(UserInfoBean user);

        void toastMessage(UserInfoBean user);

        void showFailedError();

    }

    interface Presenter extends BasePresenter {
        void login(Map<String, String> map);

    }
}
