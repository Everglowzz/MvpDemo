package com.everglow.mvpdemo.httputils.callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;

import com.everglow.mvpdemo.R;
import com.everglow.mvpdemo.bean.BaseData;
import com.everglow.mvpdemo.httputils.listener.HttpCallBack;
import com.everglow.mvpdemo.utils.CustomToast;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by EverGlow on 2017/8/12 16:37
 */

public class DataCallBack<T> extends StringCallback {
    private Context context;
    private HttpCallBack<T> callBack;
    private String message;
    private boolean isShow;
    private ProgressDialog mDialog;
    private Type type;



    public DataCallBack(Context context, HttpCallBack<T> callBack, Type type, String message, boolean isShow) {
        this.context = context;
        this.callBack = callBack;
        this.type = type;
        this.message = message;
        this.isShow = isShow;
        if (isShow) {
            mDialog = new ProgressDialog(context);
            mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.setMessage(TextUtils.isEmpty(message) ? "正在加载..." : message);
        }

    }

    @Override
    public void onError(Call call, Exception e, int id) {
        e.printStackTrace();
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (TextUtils.isEmpty(e.getMessage())) {
            CustomToast.showToast(context.getString(R.string.error500));
            callBack.onFailure(context.getString(R.string.error500));
            return;
        }
        switch (e.getMessage()) {
            case "404":
                CustomToast.showToast(context.getString(R.string.error404));
                break;
            case "500":
                CustomToast.showToast(context.getString(R.string.error500));
                break;
            default:
                CustomToast.showToast(context.getString(R.string.networkError));
                break;
        }
        callBack.onFailure(null);

    }

    @Override
    public void onResponse(String response, int id) {
        Gson gson = new Gson();
        if (TextUtils.isEmpty(response)) {
            CustomToast.showToast(context.getString(R.string.NetworkNoData));
            callBack.onFailure(context.getString(R.string.NetworkNoData));
            return;
        }
        BaseData baseData = new BaseData();
        try {
            baseData = gson.fromJson(response, BaseData.class);
        } catch (Exception e) {
            Log.e("error", e.getLocalizedMessage());
        }
        if (baseData == null) {
            CustomToast.showToast(context.getString(R.string.dataerror));
            callBack.onFailure(context.getString(R.string.dataerror));
        } else if (baseData.getCode() == 1) {

            try {
                T obj = gson.fromJson(response, type);
                callBack.onSuccess(obj);
            } catch (Exception e) {
                CustomToast.showToast(context.getString(R.string.gsonError));
                callBack.onFailure(context.getString(R.string.gsonError));
            }

        } else {
            callBack.onFailure(baseData.getMessage()==null? context.getString(R.string.dataerror):baseData.getMessage());
            CustomToast.showToast(baseData.getMessage()==null? context.getString(R.string.dataerror):baseData.getMessage());
        }
    }

    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    @Override
    public void onAfter(int id) {
        super.onAfter(id);
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }


}
