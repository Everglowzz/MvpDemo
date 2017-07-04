package com.everglow.mvpdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EverGlow on 2017/6/29 9:16
 */

public    class UserInfoBean implements Parcelable {

    private int code;
    private String message;

    public int getCode() {
        return  code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.message);
    }

    public UserInfoBean() {
    }

    protected UserInfoBean(Parcel in) {
        this.code = in.readInt();
        this.message = in.readString();
    }

    public static final Parcelable.Creator<UserInfoBean> CREATOR = new Parcelable.Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel source) {
            return new UserInfoBean(source);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };
}
