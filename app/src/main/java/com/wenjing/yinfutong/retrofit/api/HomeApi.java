package com.wenjing.yinfutong.retrofit.api;

import com.wenjing.yinfutong.model.BankCardBean;
import com.wenjing.yinfutong.model.BaseResponse;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.model.ExchangeRateBean;
import com.wenjing.yinfutong.model.HistoryBillsBean;
import com.wenjing.yinfutong.model.HomeDataBean;
import com.wenjing.yinfutong.model.IfMechantBean;
import com.wenjing.yinfutong.model.InstantMsgBean;
import com.wenjing.yinfutong.model.MerchantBillsListBean;
import com.wenjing.yinfutong.model.NoticePageBean;
import com.wenjing.yinfutong.model.PayPsdBean;
import com.wenjing.yinfutong.model.PersonalBillsBean;
import com.wenjing.yinfutong.model.PersonalBillsPayDetailsBean;
import com.wenjing.yinfutong.model.PersonalBillsRechargeDetailsBean;
import com.wenjing.yinfutong.model.PersonalBillsWithdrawDetailsBean;
import com.wenjing.yinfutong.model.QRCodeBean;
import com.wenjing.yinfutong.model.QRCodeResultBean;
import com.wenjing.yinfutong.model.QRContentBean;
import com.wenjing.yinfutong.model.RechargeOnlineBean;
import com.wenjing.yinfutong.model.TradeMessageBean;
import com.wenjing.yinfutong.model.UsQRBean;
import com.wenjing.yinfutong.model.body.BaseRequestBody;
import com.wenjing.yinfutong.model.body.CancelQueueRequestBody;
import com.wenjing.yinfutong.model.body.CashOutRequestBody;
import com.wenjing.yinfutong.model.body.MerchantBillsListRequestBody;
import com.wenjing.yinfutong.model.body.MerchantBillsRequestBody;
import com.wenjing.yinfutong.model.body.NoticePageRequestBody;
import com.wenjing.yinfutong.model.body.OnlyAccountIdRequestBody;
import com.wenjing.yinfutong.model.body.PayQRRequestBody;
import com.wenjing.yinfutong.model.body.PaypsdVerifyRequestBody;
import com.wenjing.yinfutong.model.body.PersonalBillsDetailsRequestBody;
import com.wenjing.yinfutong.model.body.PersonalBillsRequestBody;
import com.wenjing.yinfutong.model.body.QRCodeRequestBody;
import com.wenjing.yinfutong.model.body.QRContentRequestBody;
import com.wenjing.yinfutong.model.body.RechargeOfflineRequestBody;
import com.wenjing.yinfutong.model.body.RechargeOnlineRequestBody;
import com.wenjing.yinfutong.model.body.TradeMessageRequestBody;
import com.wenjing.yinfutong.model.body.UsQRRequestBody;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by wenjing on 2017/9/12.
 */

public interface HomeApi {

    /**
     * 获取首页
     *
     * @param body
     * @return
     */
    @POST("homepage")
    Observable<BaseResponse<HomeDataBean>> getHomeData(@Body BaseRequestBody body);

    /**
     * 个人账单
     *
     * @param body
     * @return
     */
    @POST("customer/person/account/list")
    Observable<BaseResponse<PersonalBillsBean>> getPersonalBills(@Body PersonalBillsRequestBody body);

    /**
     * 个人账单充值详情
     *
     * @param body
     * @return
     */
    @POST("customer/person/account/detail")
    Observable<BaseResponse<PersonalBillsRechargeDetailsBean>> getPersonalRechargeBillsDetails(@Body PersonalBillsDetailsRequestBody body);

    /**
     * 个人账单付款详情
     *
     * @param body
     * @return
     */
    @POST("customer/person/account/detail")
    Observable<BaseResponse<PersonalBillsPayDetailsBean>> getPersonalPayBillsDetails(@Body PersonalBillsDetailsRequestBody body);

    /**
     * 个人账单提现详情
     *
     * @param body
     * @return
     */
    @POST("customer/person/account/detail")
    Observable<BaseResponse<PersonalBillsWithdrawDetailsBean>> getPersonalWinthdrawBillsDetails(@Body PersonalBillsDetailsRequestBody body);


    /**
     * 验证是否为商家
     *
     * @param body
     * @return
     */
    @POST("merchant/validate/merchant")
    Observable<BaseResponse<IfMechantBean>> getIfMerchant(@Body BaseRequestBody body);

    /**
     * 每日账单
     *
     * @param body
     * @return
     */
    @POST("merchant/order/day/sum/list")
    Observable<BaseResponse<HistoryBillsBean>> getDailyBills(@Body MerchantBillsRequestBody body);

    /**
     * 每月账单
     *
     * @param body
     * @return
     */
    @POST("merchant/order/month/sum/list")
    Observable<BaseResponse<HistoryBillsBean>> getMonthBills(@Body MerchantBillsRequestBody body);

    /**
     * 获取二维码
     *
     * @param body
     * @return
     */
    @POST("customer/QRcode")
    Observable<QRCodeBean> getQRcode(@Body BaseRequestBody body);

    /**
     * 商家每日账单
     *
     * @param body
     * @return
     */
    @POST("merchant/order/day/list")
    Observable<BaseResponse<MerchantBillsListBean>> getMerchantBillsList(@Body MerchantBillsListRequestBody body);

    /**
     * 线上充值
     *
     * @param body
     * @return
     */
    @POST("customer/recharge/online")
    Observable<BaseResponse<RechargeOnlineBean>> getRechargeOnline(@Body RechargeOnlineRequestBody body);

    /**
     * 线下充值
     *
     * @param body
     * @return
     */
    @POST("customer/recharge/offline")
    Observable<BaseResponse<RechargeOnlineBean>> getRechargeOffline(@Body RechargeOfflineRequestBody body);

    /**
     * 扫码付款
     *
     * @param body
     * @return
     */
    @POST("trader/pay")
    Observable<BaseResponse<RechargeOnlineBean>> payMoney(@Body PayQRRequestBody body);

    /**
     * 获取当前汇率
     *
     * @param description
     * @return
     */
    @POST("main/exchange/rate")
    Observable<BaseResponse<ExchangeRateBean>> getExchangeRate(@Query("description") RequestBody description);

    /**
     * 获取银行卡列表
     *
     * @param body
     * @return
     */
    @POST("customer/bank/list")
    Observable<BaseResponse<List<BankCardBean>>> getBankCardList(@Body BaseRequestBody body);

    /**
     * 获取扫描结果
     *
     * @param body
     * @return
     */
    @POST("customer/analyse/QRcode")
    Observable<BaseResponse<QRCodeResultBean>> getQRCodeResult(@Body QRCodeRequestBody body);

    /**
     * 提现
     *
     * @param body
     * @return
     */
    @POST("merchant/withdraw")
    Observable<BaseResponse> getCashOut(@Body CashOutRequestBody body);


    /**
     * 获取用户信息
     *
     * @param body
     * @return
     */
    @POST("customer/detail")
    Observable<BaseResponse<Customer>> getUserInfo(@Body BaseRequestBody body);

    /**
     * 获取平台公告信息列表
     *
     * @param body
     * @return
     */
    @POST("notice/page")
    Observable<BaseResponse<NoticePageBean>> getNoticePage(@Body NoticePageRequestBody body);

    /**
     * 交易信息
     *
     * @param body
     * @return
     */
    @POST("tradeMessage")
    Observable<BaseResponse<TradeMessageBean>> getTradeMessage(@Body TradeMessageRequestBody body);


    /**
     * 获取  获取订单信息  用于生成二维码
     * @param body
     * @return
     */
    @POST("/merchant/create/order_no")
    Observable<BaseResponse<QRContentBean>> getQRContent(@Body QRContentRequestBody body);

    /**
     * 站内扫临时二维码   付款
     * @param body
     * @return
     */
    @POST("/trader/UsQRcode")
    Observable<BaseResponse<UsQRBean>> getUsQROrder(@Body UsQRRequestBody body);

    /**
     * 验证  交易密码
     * @param body
     * @return
     */
    @POST("/customer/validation/trader/password")
    Observable<BaseResponse<PayPsdBean>> verifyPayPsd(@Body PaypsdVerifyRequestBody body);

    /**
     * 刷新   公告 或者 交易 信息
     * @param body
     * @return
     */
    @POST("/refresh/message")
    Observable<BaseResponse<InstantMsgBean>> getInstantMsg(@Body OnlyAccountIdRequestBody body);

    /**
     * 取消  充值  排队
     * @param body
     * @return
     */
    @POST("/customer/recharge/cancel/queue")
    Observable<BaseResponse> cancelQueue(@Body CancelQueueRequestBody body);

}
