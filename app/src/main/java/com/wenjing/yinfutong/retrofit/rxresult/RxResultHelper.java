package com.wenjing.yinfutong.retrofit.rxresult;


import android.support.annotation.NonNull;
import android.util.Log;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.constant.ResponseConstant;
import com.wenjing.yinfutong.constant.TabCodeConstant;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.BaseResult;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.body.LoginRequestBody;
import com.wenjing.yinfutong.retrofit.YinFuTongFactory;
import com.wenjing.yinfutong.retrofit.api.MineApi;
import com.wenjing.yinfutong.retrofit.compose.RxSchedulers;
import com.wenjing.yinfutong.utils.MD5Util;
import com.wenjing.yinfutong.utils.TDevice;
import com.wenjing.yinfutong.utils.TLog;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

import static io.reactivex.Observable.error;
import static io.reactivex.Observable.just;



public class RxResultHelper {

    public static <T> ObservableTransformer<BaseResult<T>, T> handleResult() {
        return new ObservableTransformer<BaseResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<BaseResult<T>> upstream) {
                return upstream.flatMap(new Function<BaseResult<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull BaseResult<T> result) throws Exception {
                        if (result.getCode() == TabCodeConstant.SUCCESS && result.getData() != null) {
                            return just(result.getData());
                        } else {
                            return error(new Throwable(result.getMsg()));
                        }
                    }
                });
            }
        };
    }
    
    public static <T> ObservableTransformer<BaseResponse<T>, T> handleRespose() {
        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<BaseResponse<T>> upstream) {
                return upstream.flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(@NonNull BaseResponse<T> result) throws Exception {
                        TLog.log("RxResultHelper" , result.toString());
                        if (result.getCode() == TabCodeConstant.SUCCESS && result.getData() != null) {

                            if (result.getCode()== ResponseConstant.RESPONSECODE_LOGIN_DISABLED){
                                autoLogin();
                            }

                            return just(result.getData());
                        } else {
                            return error(new Throwable(result.getMsg()));
                        }
                    }
                });
            }
        };
    }

    private static void autoLogin() {
        MineApi sMineApi= YinFuTongFactory.getMineApiSingleton();
        Customer customer = AppContext.instance().getLoginUser();

        LoginRequestBody bean = new LoginRequestBody();
        bean.setAccount(customer.getCellphone());
        bean.setAccountType(customer.getAccountType());
        bean.setLoginLang(AppContext.getLangulage());
        bean.setPassword(MD5Util.encrypt(AppContext.getLoginPsd()));
        bean.setDevice(TDevice.getIMEI(AppContext.instance()));
        bean.setDeviceType(TDevice.getPhoneName());

        sMineApi.getLogin(bean)
                .compose(RxResultHelper.<Customer>handleRespose())
                .compose(RxSchedulers.<Customer>applyObservableAsync())
                .subscribe(new Observer<Customer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Customer bean) {
                        AppContext.instance().saveLoginInfo(AppContext.instance(), bean);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
