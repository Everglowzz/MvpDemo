package com.everglow.mvpdemo.httputils.listener;

/**
 * Created by EverGlow on 2017/8/12 10:36
 */

public interface HttpCallBack<T> {

    void onSuccess(T result);

    void onFailure(String error);
}
