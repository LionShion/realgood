package com.wenjing.yinfutong.retrofit.api;

import com.wenjing.yinfutong.model.BankCardBean;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.CommonPbBean;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.DepositBankBean;
import com.wenjing.yinfutong.model.MerchantBean;
import com.wenjing.yinfutong.model.MerchantFundBean;
import com.wenjing.yinfutong.model.ProvinceClassBean;
import com.wenjing.yinfutong.model.RegionBean;
import com.wenjing.yinfutong.model.UserBean;
import com.wenjing.yinfutong.model.body.BaseRequestBody;
import com.wenjing.yinfutong.model.body.BindBankCardRequestBody;
import com.wenjing.yinfutong.model.body.DepositBankRequestBody;
import com.wenjing.yinfutong.model.body.LeastRequestBody;
import com.wenjing.yinfutong.model.body.LoginRequestBody;
import com.wenjing.yinfutong.model.body.MerchantFundRequestBody;
import com.wenjing.yinfutong.model.body.PersonalModifyRequestBody;
import com.wenjing.yinfutong.model.body.RegisterRequestBody;
import com.wenjing.yinfutong.model.body.RetrieveLPsdRequestBody;
import com.wenjing.yinfutong.model.body.SMSSendRequestBody;
import com.wenjing.yinfutong.model.body.SMSVerifyRequestBody;
import com.wenjing.yinfutong.model.body.UnbindBankCardRequestBody;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by wenjing on 2017/9/12.
 */

public interface MineApi {

    /**
     * 发送   短信验证码
     *
     * @param request
     * @return
     */
    @POST("/sms")
    Observable<BaseResponse> sendSMS(@Body SMSSendRequestBody request);

    /**
     * 短信验证码     验证
     *
     * @param request
     * @return
     */
    @POST("/sms/validation")
    Observable<BaseResponse> verifySMS(@Body SMSVerifyRequestBody request);

    /**
     * 注册
     *
     * @param request
     * @return
     */
    @POST("/register")
    Observable<BaseResponse<UserBean>> register(@Body RegisterRequestBody request);

    /**
     * 登录
     *
     * @param request
     * @return
     */
    @POST("/login")
    Observable<BaseResponse<Customer>> getLogin(@Body LoginRequestBody request);

    /**
     * 修改个人信息
     *
     * @param request
     * @return
     */
    @POST("/customer/person/edit")
    Observable<BaseResponse> modifyPersonInfo(@Body PersonalModifyRequestBody request);

    /**
     * 获取   银行卡列表
     *
     * @param body
     * @return
     */
    @POST("/customer/bank/list")
    Observable<BaseResponse<List<BankCardBean>>> getCustomerBankList(@Body BaseRequestBody body);

    /**
     * 解绑    银行卡
     *
     * @param body
     * @return
     */
    @POST("customer/delete/BankCard")
    Observable<BaseResponse> unbindBankCard(@Body UnbindBankCardRequestBody body);


    /**
     * 绑定银行卡
     */
    @POST("/customer/person/bank/card/bind")
    Observable<BaseResponse> bindBankCard(@Body BindBankCardRequestBody request);


    /**
     * 商家  注册  开通商家服务
     *
     * @param body
     * @return
     */
    @POST("/merchant/regist")
    Observable<BaseResponse<MerchantBean>> registerMerchant(@Body RequestBody body);


    /**
     * 退出登录
     *
     * @return
     */
    @POST("/logout")
    Observable<BaseResponse> logout();

    /**
     * 忘记密码
     * 找回  登录密码
     *
     * @param body
     * @return
     */
    @POST("/customer/person/forget/pwd")
    Observable<BaseResponse> retrieveLoginPsd(@Body RetrieveLPsdRequestBody body);


    /**
     * 获取商家   地区选择列表
     *
     * @param body
     * @return
     */
    @POST("/merchant/city")
    Observable<BaseResponse<List<ProvinceClassBean>>> getMerchantProAndCity(@Body LeastRequestBody body);

    /**
     * 获取银联 列表
     *
     * @return
     */
    @POST("/customer/payBank")
    Observable<BaseResponse<DepositBankBean>> getDepositBankList(@Body DepositBankRequestBody body);

    /**
     * 获取  手机联系人  区域列表
     *
     * @param body
     * @return
     */
    @POST("/customer/phoneAttribution")
    Observable<BaseResponse<List<RegionBean>>> getResion(@Body LeastRequestBody body);

    /**
     * c客服   常见问题 列表
     *
     * @param body
     * @return
     */
    @POST("/sys/fap/list")
    Observable<BaseResponse<List<CommonPbBean>>> getCommonPbList(@Body LeastRequestBody body);

    /**
     * 商机服务  页
     * 获取  商家  交易金额方面的  信息
     *
     * @param body
     * @return
     */
    @POST("/merchant/fund")
    Observable<BaseResponse<MerchantFundBean>> getMerchantFund(@Body MerchantFundRequestBody body);
}
