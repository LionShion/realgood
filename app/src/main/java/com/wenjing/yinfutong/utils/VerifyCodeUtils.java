package com.wenjing.yinfutong.utils;

import android.content.Context;

import com.wenjing.yinfutong.base.BaseFragment;
import com.wenjing.yinfutong.constant.RequestConstant;
import com.wenjing.yinfutong.interf.OnResponseListener;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.ToSignSmsBean;
import com.wenjing.yinfutong.model.body.SMSSendRequestBody;
import com.wenjing.yinfutong.retrofit.api.MineApi;
import com.wenjing.yinfutong.utils.sign.SignHelper;

/**
 * Created by ${luoyingtao} on 2018\4\2 0002.
 */

public class VerifyCodeUtils {

    public static final String TAG = "VerifyCodeUtils";

    public static void obtainSmsVerifyCode(Context context, MineApi sMineApi, SMSSendRequestBody body, final OnResponseListener<BaseResponse> listener){

        ToSignSmsBean toSignSmsBean = new ToSignSmsBean(body.getAccountType(),body.getAccount(),body.getSmsType());
        String content = SignHelper.sortParamForSignCard(toSignSmsBean);
        String sign = MD5Util.getInstance().sign(content, RequestConstant.PRIVATE_KEY);
        body.setSign(sign);

        TLog.log(TAG,body.toString());
        new MyObservable<BaseResponse>().observe(context,sMineApi.sendSMS(body), listener);

    }

}
