package com.everglow.mvpdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.everglow.mvpdemo.R;
import com.everglow.mvpdemo.contract.IloginContract;
import com.everglow.mvpdemo.model.UserInfoBean;
import com.everglow.mvpdemo.presenter.LoginPresenter;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements IloginContract.IloginView {


    /**
     * 测试账号
     * 企业ID 888
     * 账号   李媛abc
     * 密码  111111
     */
    @Bind(R.id.tv_empty)
    TextView mTvEmpty;
    @Bind(R.id.tv_company)
    TextView mTvCompany;
    @Bind(R.id.et_company)
    EditText mEtCompany;
    @Bind(R.id.tv_user)
    TextView mTvUser;
    @Bind(R.id.et_user)
    EditText mEtUser;
    @Bind(R.id.tv_pwd)
    TextView mTvPwd;
    @Bind(R.id.et_pwd)
    EditText mEtPwd;
    @Bind(R.id.tv_submit)
    TextView mTvSubmit;
    IloginContract.IloginPresenter mIloginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        new LoginPresenter(this).onSubscribe();

        mTvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();
                map.put("keynumber",mEtCompany.getText().toString().trim());
                map.put("username", mEtUser.getText().toString().trim());
                map.put("password", mEtPwd.getText().toString().trim());
                mIloginPresenter.login(map);
            }
        });
    }

    @Override
    public void showLoading() {
        mTvEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mTvEmpty.setVisibility(View.GONE);
    }

    @Override
    public void toMainActivity(UserInfoBean user) {
        if (user != null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void toastMessage(UserInfoBean user) {
        Toast.makeText(this, user.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFailedError() {
        Toast.makeText(this, "网络链接错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIloginPresenter.unSubscribe();
    }

    public void setPresenter(IloginContract.IloginPresenter iloginPresenter) {
        this.mIloginPresenter = iloginPresenter;
    }
}
