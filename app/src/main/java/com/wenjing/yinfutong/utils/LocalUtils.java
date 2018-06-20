package com.wenjing.yinfutong.utils;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.common.UIHelper;
import com.wenjing.yinfutong.constant.RequestConstant;
import com.wenjing.yinfutong.function.RequestCode;
import com.wenjing.yinfutong.interf.OnMerchantStatusListener;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.IfMechantBean;
import com.wenjing.yinfutong.model.IfMerchantBean;
import com.wenjing.yinfutong.model.body.BaseRequestBody;
import com.wenjing.yinfutong.retrofit.api.HomeApi;
import com.wenjing.yinfutong.retrofit.compose.RxSchedulers;
import com.wenjing.yinfutong.retrofit.rxresult.RxResultHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ${luoyingtao} on 2018\4\12 0012.
 */

public class LocalUtils {

    /**
     * 未登录    走向
     *
     * @param context
     * @return
     */
    public static boolean isNotLoginTowards(Context context) {
        if (!AppContext.instance().isLogin()) {//没有登录
            UIHelper.showLogin(context);//进入登录页面
            return true;
        }
        return false;
    }

    /**
     *   商家  进入
     * @return
     */
    public static void rootToMerchant(final BaseFragment fragment, HomeApi homeApi , final OnMerchantStatusListener listener){
        BaseRequestBody body = new BaseRequestBody(AppContext.getLangulage(),AppContext.instance().getLoginUser().getAccountId());
        homeApi.getIfMerchant(body)
                .compose(RxResultHelper.<IfMechantBean>handleRespose())
                .compose(RxSchedulers.<IfMechantBean>applyObservableAsync())
                .subscribe(new Observer<IfMechantBean>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(IfMechantBean bean) {

                        int status = bean.getStatus();
                        int merchantId = bean.getMerchantId();
                        AppContext.instance().getLoginUser().setMerchantId(merchantId);
                        switch (status) {
                            case RequestConstant.STATUS_NO_REGISTER:
                                DialogUtils.showOpenMerchantService(fragment.getContext());
                                break;
                            case RequestConstant.STATUS_UNDER_REVIEW:
                                UIHelper.showApplicationCommited(fragment.getContext());
                                break;
                            case RequestConstant.STATUS_IS_MECHANT:
                                UIHelper.showMerchantService(fragment.getContext());
                                if(listener != null){
                                    listener.isMerchant();
                                }
                                break;
                            case RequestConstant.STATUS_REFUSE:
                                UIHelper.showOpenMerchantRefuse(fragment.getContext());
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppContext.showToast(e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }

                });

    }

    /**
     * 是否符合  手机格式
     * @param etPhone
     * @return
     */
    public static boolean isPhoneFormatOk(EditText etPhone) {
        String phone = etPhone.getText().toString().trim();
        if(Validator.isMobile(phone)){
            return true;
        }
//        AppContext.showToast(R.string.format_no_phone);
        return false;
    }

    /**
     * 是否符合  登录密码格式
     * @param etPassword
     * @return
     */
    public static boolean isLoginPsdFormatOk(EditText etPassword) {
        String psd = etPassword.getText().toString().trim();
        if(Validator.isLoginPassword(psd)){
            return true;
        }
//        AppContext.showToast(R.string.format_no_psd_login);
        return false;
    }


    /**
     * 是否符合    支付密码格式
     * @param etPassword
     * @return
     */
    public static boolean isPayPsdFormatOk(EditText etPassword) {
        String psd = etPassword.getText().toString().trim();
        if(Validator.isPayPassword(psd)){
            return true;
        }
//        AppContext.showToast(R.string.format_no_psd_payment);
        return false;
    }

    /**
     * 是否符合    验证码格式
     * @param etVerifyCode
     * @return
     */
    public static boolean isVerifyCodeFormatOk(EditText etVerifyCode) {
        String verifyCode = etVerifyCode.getText().toString().trim();
        if(Validator.isPayPassword(verifyCode)){//和支付密码一样 的格式
            return true;
        }
        return false;
    }

    /**
     * 是否符合 邮箱格式
     * @param etEmail
     * @return
     */
    public static boolean isEmailFormatOk(EditText etEmail){
        String email = etEmail.getText().toString().trim();
        if(Validator.isEmail(email)){
            return true;
        }
        AppContext.showToast(R.string.format_no_email);
        return false;
    }

    /**
     * 是否符合  身份证号格式
     * @param etIdNo
     * @return
     */
    public static boolean isIdNoFormatOk(EditText etIdNo){
        String idNo = etIdNo.getText().toString().trim();
        if(Validator.isIDCard(idNo)){
            return true;
        }
//        AppContext.showToast(R.string.format_no_idno);
        return false;
    }

    /**
     * 是否符合  银行卡卡号格式
     * @param etBankNo
     * @return
     */
    public static boolean isBankNoFormatOk(EditText etBankNo) {
        String bankNo = etBankNo.getText().toString();
        if(Validator.isBankNo(bankNo)){
            return true;
        }

//        AppContext.showToast(R.string.format_no_bankno);
        return false;
    }

}
