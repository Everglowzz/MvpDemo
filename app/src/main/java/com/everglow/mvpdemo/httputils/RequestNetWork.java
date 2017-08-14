package com.everglow.mvpdemo.httputils;

import android.content.Context;
import android.util.Log;

import com.everglow.mvpdemo.httputils.callback.DataCallBack;
import com.everglow.mvpdemo.httputils.listener.HttpCallBack;
import com.zhy.http.okhttp.OkHttpUtils;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by EverGlow on 2017/8/12 10:32
 */

public  class RequestNetWork<T>   {

    private Context context;

    public RequestNetWork(Context context) {
        this.context = context;
    }


    public void request(HashMap<String,String> map, String URL, HttpCallBack<T> callback, final Type type){
        request(map,URL,callback,type,null);
    }
    public void request(HashMap<String,String> map, String URL, HttpCallBack<T> callback, final Type type, String message){
        request(map,URL,callback,type,message,true);
    }

    /**
     *
     * @param map  参数集合
     * @param URL  访问接口
     * @param callback  网络请求回调
     * @param type  json解析的对象类型，以class类型传入
     * @param message 请求弹窗的文字显示，默认显示（正在加载...）
     * @param isShow 是否显示请求弹窗，默认显示
     */
    public void request(HashMap<String,String> map, String URL, HttpCallBack<T> callback, final Type type, String message, boolean isShow) {
        OkHttpUtils.post()
                .url(URL)
                .params(builderParams(map))
                .build()
                .execute(new DataCallBack(context,callback,type,message,isShow));

    }

    /**
     * 请求参数的集合做非空判断
     * */
    private Map<String,String> builderParams(HashMap<String, String> params){

        if (params == null) {
            params = new HashMap<>();
        }
        params = putCommon(params);
        Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry<String, String> entry = it.next();
            if(entry.getKey()==null||entry.getValue()==null){
                it.remove();
            }
        }
            return  params;
    }

    /**
     *
     * @param params  传入请求参数集合
     * @return 返回添加通用参数后的集合
     */
    private HashMap<String, String> putCommon(HashMap<String, String> params) {


        return params;
    }
}
