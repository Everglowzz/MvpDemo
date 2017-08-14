package com.everglow.mvpdemo.contract;

import com.everglow.mvpdemo.model.UserInfoBean;
import com.everglow.mvpdemo.presenter.BasePresenter;
import com.everglow.mvpdemo.view.BaseView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by EverGlow on 2017/6/29 9:44
 */

public interface IloginContract {

    interface IloginView extends BaseView<IloginPresenter> {
        void showLoading();

        void hideLoading();

        void toMainActivity(UserInfoBean user);

        void toastMessage(UserInfoBean user);

        void showFailedError();

    }

    interface IloginPresenter extends BasePresenter {
        void login(HashMap<String, String> map);

    }
}
