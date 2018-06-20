package com.wenjing.yinfutong.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.constant.RequestConstant;
import com.wenjing.yinfutong.constant.ResponseConstant;
import com.wenjing.yinfutong.function.mine.fragment.LoginFragment;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.body.LoginRequestBody;
import com.wenjing.yinfutong.view.CustomDialog;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.wenjing.yinfutong.base.BaseFragment.sMineApi;

/**
 * Created by ${luoyingtao} on 2018\4\2 0002.
 */

public class MyObservable<T extends BaseResponse>{
    private static final String TAG = "MyObservable<T>";

    public void observe(final Context context, Observable<T> observable, final OnResponseListener<? super T> listener){
        observable.subscribeOn(Schedulers.io()) //订阅
                .observeOn(AndroidSchedulers.mainThread())  //在主线程观察
                .subscribe(new Observer<T>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T data) {
                        //登录已过时
                        TLog.log(TAG,data.toString());
                        if(data.getCode() == ResponseConstant.RESPONSECODE_LOGIN_DISABLED){
                            autoLogin(context);
                            return;
                        }

                        if(listener != null){
                            listener.onNext(data);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        TLog.log(TAG,e.getMessage());
                        if(listener != null){
                            listener.onError(e);
                            DialogUtils.showOneTipDialog(context,e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    private void autoLogin(final Context context) {
        Customer customer = AppContext.instance().getLoginUser();

        LoginRequestBody bean = new LoginRequestBody();
        bean.setAccount(customer.getCellphone());
        bean.setAccountType(customer.getAccountType());
        bean.setLoginLang(AppContext.getLangulage());
        bean.setPassword(MD5Util.encrypt(AppContext.getLoginPsd()));
        bean.setDevice(TDevice.getIMEI(context));
        bean.setDeviceType(TDevice.getPhoneName());

        new MyObservable<BaseResponse<Customer>>().observe(context,
                sMineApi.getLogin(bean),
                new OnResponseListener<BaseResponse<Customer>>() {
                    @Override
                    public void onNext(BaseResponse<Customer> data) {
                        if (data.getCode() == 0 && data.getData() != null) {
                            Customer customer = data.getData();
                            AppContext.instance().saveLoginInfo(context, customer);
                        }else {
                            AppContext.showToast(data.getMsg());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });

    }

}
