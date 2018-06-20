package com.wenjing.yinfutong.interf;

/**
 * Created by ${luoyingtao} on 2018\3\27 0027.
 */

public interface OnResponseListener<T> {
    void onNext(T data);
    void onError(Throwable e);
}
