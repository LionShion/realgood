package com.wenjing.yinfutong.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.wenjing.yinfutong.constant.TabCodeConstant;


public class UIHelper {
    public static final String CELL_PHONE = "cellphone";
    public static String HF_URL_KEY = "url";
    public static String HF_TITLE_KEY = "title";
    public static String HF_CONTENT_KEY = "content";
    public static final String SIX_PWD = "TransactionPassword";
    private Context context;


    //显示HTML5单独页面
    public static void showSingleWebView(Context context, String url, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(HF_URL_KEY, url);
        bundle.putString(HF_TITLE_KEY, title);
        showSimpleBack(context, SimpleBackPage.SINGLEWEBVIEW, bundle);
    }


    //不带参数
    public static void showSimpleBack(Context context, SimpleBackPage page) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        context.startActivity(intent);
    }

    //不带参数,自定义customView
    public static void showSimpleBack(Context context, SimpleBackPage page, int customView) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_CUSTOM_VIEW, customView);
        context.startActivity(intent);
    }

    //带参数
    public static void showSimpleBack(Context context, SimpleBackPage page, Bundle args) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_ARGS, args);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        context.startActivity(intent);
    }

    //带参数
    public static void showSimpleBack(Context context, SimpleBackPage page, Bundle args, int customView) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_ARGS, args);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_CUSTOM_VIEW, customView);
        context.startActivity(intent);
    }

    //需要返回结果，不带参数
    public static void showSimpleBackForResult(Activity context, int requestCode, SimpleBackPage page) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        context.startActivityForResult(intent, requestCode);
    }

    //需要返回结果，带参数
    public static void showSimpleBackForResult(Activity context, int requestCode, SimpleBackPage page, Bundle args) {
        Intent intent = new Intent(context, SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_ARGS, args);
        context.startActivityForResult(intent, requestCode);
    }

    //Fragment中跳转,需要返回结果，带参数
    public static void showSimpleBackForResult(Fragment fragment, int requestCode, SimpleBackPage page, Bundle args) {
        Intent intent = new Intent(fragment.getActivity(),
                SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_ARGS, args);
        fragment.startActivityForResult(intent, requestCode);
    }

    //Fragment中跳转,需要返回结果，不带参数
    public static void showSimpleBackForResult(Fragment fragment, int requestCode, SimpleBackPage page) {
        Intent intent = new Intent(fragment.getActivity(),
                SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        fragment.startActivityForResult(intent, requestCode);
    }

    //Fragment中跳转,需要返回结果，不带参数
    public static void showSimpleBackForResult(Fragment fragment, int requestCode, SimpleBackPage page, Bundle args, int customView) {
        Intent intent = new Intent(fragment.getActivity(),
                SimpleBackActivity.class);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_PAGE, page.getValue());
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_ARGS, args);
        intent.putExtra(SimpleBackActivity.BUNDLE_KEY_CUSTOM_VIEW, customView);
        fragment.startActivityForResult(intent, requestCode);
    }


    /**
     * 二维码
     *
     * @param context
     */
    public static void showTwoDimensionalCode(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.TWODIMENSIONALCODE, bundle);
    }

    /**
     * 扫码付款
     *
     * @param context
     * @param bundle
     */
    public static void showPayment(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.PAYMENT, bundle);
    }


    /**
     * 新用户注册
     *
     * @param context
     */
    public static void showRegister(Context context) {
        showSimpleBack(context, SimpleBackPage.REGISTER);
    }

    /**
     * 忘记密码  验证身份
     *
     * @param context
     */
    public static void showRetrievePsd(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.RETRIEVE_PSD, bundle);
    }

    /**
     * 用户登录
     *
     * @param context
     */
    public static void showLogin(Context context) {
        showSimpleBack(context, SimpleBackPage.LOGIN);
    }

    /**
     * 修改昵称
     *
     * @param context
     * @param bundle
     */
    public static void showNickname(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.NICKNAME, bundle);
    }

    /**
     * 个人信息
     *
     * @param context
     * @param bundle
     */
    public static void showPersonalInfo(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.PERSONAL_INFO, bundle);
    }


    /**
     * 绑定手机号
     *
     * @param context
     * @param bundle
     */
    public static void showTiedPhone(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.TIED_PHONENUM, bundle);
    }

    /**
     * 绑定邮箱
     *
     * @param context
     * @param bundle
     */
    public static void showTiedEmail(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.TIED_EMAIL, bundle);
    }


    /**
     * 商家服务
     *
     * @param context
     */
    public static void showMerchantService(Context context) {
        showSimpleBack(context, SimpleBackPage.MERCHANT_SERVICE);
    }

    /**
     * 语音提醒
     *
     * @param context
     */
    public static void showVoicePrompt(Context context) {
        showSimpleBack(context, SimpleBackPage.VOICE_PROMPT);
    }


    /**
     * 找回密码  设置新登录密码
     *
     * @param context
     */
    public static void showRetrievePsdSecond(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.RETRIEVE_PSD_SECOND, bundle);
    }


    /**
     * 找回支付密码    设置新支付密码
     *
     * @param context
     * @param bundle
     */
    public static void showRetrievePaymentPsd(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.RETRIEVE_PAYMENT_PSD, bundle);
    }

    /**
     * 设置密码
     *
     * @param context
     * @param bundle
     */
    public static void showSetPsd(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.SETPSD, bundle);
    }

    /**
     * 设置  支付密码
     *
     * @param context
     */
    public static void showSetPaymentPsd(Context context) {
        showSimpleBack(context, SimpleBackPage.SET_PAYMENTPSD);
    }

    /**
     * 地区
     *
     * @param fragment
     * @param bundle
     */
    public static void showRegionForResult(Fragment fragment, Bundle bundle) {
        showSimpleBackForResult(fragment, TabCodeConstant.REQUESTCODE_REGIONSELECT, SimpleBackPage.REGION, bundle);
    }


    /**
     * 语言选择
     *
     * @param context
     */
    public static void showSwitchLanguage(Context context) {
        showSimpleBack(context, SimpleBackPage.SWITCH_LANGUAGE);
    }

    /**
     * 安全管理
     *
     * @param context
     */
    public static void showSafetyManager(Context context) {
        showSimpleBack(context, SimpleBackPage.SAFETY_MANAGER);
    }

    /**
     * 客户服务
     *
     * @param context
     */
    public static void showCustomerService(Context context) {
        showSimpleBack(context, SimpleBackPage.CUTOMER_SERVICE);
    }

    /**
     * 常见问题   详情
     *
     * @param context
     * @param bundle
     */
    public static void showCommonPbDetails(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.COMMONPB_DETAILS, bundle);
    }

    /**
     * 银行卡
     *
     * @param context
     */
    public static void showBankCard(Context context) {
        showSimpleBack(context, SimpleBackPage.BANKCARD);
    }


    /**
     * 绑定银行卡
     *
     * @param context
     */
    public static void showBindBankCard(Context context) {
        showSimpleBack(context, SimpleBackPage.TIE_BANKCARD);
    }

    /**
     * 支持银行
     *
     * @param fragment
     * @param requestCode
     */
    public static void showDepositBank(Fragment fragment, int requestCode) {
        showSimpleBackForResult(fragment, requestCode, SimpleBackPage.DEPOSITBANK);
    }

    /**
     * 重置登录密码
     *
     * @param context
     */
    public static void showResetLoginPsd(Context context) {
        showSimpleBack(context, SimpleBackPage.RESET_LOGIN_PSD);
    }


    /**
     * 商家审核中
     *
     * @param context
     */
    public static void showApplicationCommited(Context context) {
        showSimpleBack(context, SimpleBackPage.APPLICATION_COMMITED);
    }

    /**
     * 账户余额
     *
     * @param context
     */
    public static void showAccountBanlace(Context context) {
        showSimpleBack(context, SimpleBackPage.ACCOUNT_BALANCE);
    }


    /**
     * 充值
     *
     * @param context
     * @param bundle
     */
    public static void showRecharge(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.RECHARGE, bundle);
    }

    /**
     * 收款记录
     *
     * @param context
     */
    public static void showCollectionRecord(Context context) {
        showSimpleBack(context, SimpleBackPage.COLLECTION_RECORD);
    }

    /**
     * 消息中心
     *
     * @param context
     */
    public static void showNewsCenter(Context context) {
        showSimpleBack(context, SimpleBackPage.NEWS_CENTER);
    }

    /**
     * 平台公告
     *
     * @param context
     */
    public static void showPlatformAnnouncement(Context context) {
        showSimpleBack(context, SimpleBackPage.PLATFORM_ANNOUNCEMENT);
    }

    /**
     * 交易信息
     *
     * @param context
     */
    public static void showTradeInformation(Context context) {
        showSimpleBack(context, SimpleBackPage.TRADE_INFORMATION);
    }

    /**
     * 开通商家服务
     *
     * @param context
     */
    public static void showOpenMerchantServices(Context context) {
        showSimpleBack(context, SimpleBackPage.OPEN_MERCHANTSERVICE);
    }

    /**
     * 开通商家服务    地区选择 省级
     *
     * @param fragment
     * @param bundle
     */
    public static void showSelectMerchantRegion(Fragment fragment, Bundle bundle) {
        showSimpleBackForResult(fragment, TabCodeConstant.REQUESTCODE_MERCHANT_PROSELECT, SimpleBackPage.SELECT_MERCHANT_REGION, bundle);
    }

    /**
     * 开通商家服务    地区选择 市级
     *
     * @param fragment
     * @param bundle
     */
    public static void showSelectMerchantCity(Fragment fragment, Bundle bundle) {
        showSimpleBackForResult(fragment, TabCodeConstant.REQUESTCODE_MERCHANT_CITYSELECT, SimpleBackPage.SELECT_MERCHANT_CITY, bundle);
    }

    /**
     * 历史账单
     *
     * @param context
     */
    public static void showHistoryBill(Context context) {
        showSimpleBack(context, SimpleBackPage.HISTORY_BILL);
    }

    /**
     * 个人账单
     *
     * @param context
     */
    public static void showPersonalBills(Context context) {
        showSimpleBack(context, SimpleBackPage.PERSONAL_BILLS);
    }

    /**
     * 个人账单充值详情
     *
     * @param context
     * @param bundle
     */
    public static void showPersonalBillsDetails(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.PERSONAL_BILLS_RECHARGE_DETAILS, bundle);
    }

    /**
     * 个人账单付款详情
     *
     * @param context
     * @param bundle
     */
    public static void showPersonalPayBillsDetails(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.PERSONAL_BILLS_PAY_DETAILS, bundle);
    }

    /**
     * 个人账单提现详情
     *
     * @param context
     * @param bundle
     */
    public static void showPersonalWithdrawBillsDetails(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.PERSONAL_BILLS_WITHDRAW_DETAILS, bundle);
    }

    /**
     * 商家收款详情
     * @param context
     * @param bundle
     */
    public static void showMerchantBillDetails(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.MERCHANT_BILL_DETAILS, bundle);
    }

    /**
     * 日账单，月账单列表
     *
     * @param context
     * @param bundle
     */
    public static void showMerchantBillsList(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.MERCHANTBILLSLIST, bundle);
    }

    /**
     * 线上充值
     *
     * @param context
     */
    public static void showRechargeOnline(Context context) {
        showSimpleBack(context, SimpleBackPage.RECHARGE_ONLINE);
    }

    /**
     * 提现
     *
     * @param context
     */
    public static void showCashOut(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.CASH_OUT, bundle);
    }

    /**
     * 充值成功
     *
     * @param context
     */
    public static void showRechargeSucc(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.RECHARGE_SUCC, bundle);
    }

    /**
     * 待充值
     *
     * @param context
     * @param bundle
     */
    public static void showToRecharge(Context context, Bundle bundle) {
        showSimpleBack(context, SimpleBackPage.TO_RECHARGE, bundle);
    }

    /**
     * 商家提现等待
     *
     * @param context
     */
    public static void showWithdrawWait(Context context) {
        showSimpleBack(context, SimpleBackPage.WITHDRAW_WAIT);
    }

    /**
     * 支付成功
     *
     * @param context
     */
    public static void showPaySucc(Context context) {
        showSimpleBack(context, SimpleBackPage.PAY_SUCC);
    }

    /**
     * 开通商家被拒绝
     *
     * @param context
     */
    public static void showOpenMerchantRefuse(Context context) {
        showSimpleBack(context, SimpleBackPage.DEFEATED);
    }

    /**
     * 收款人
     *
     * @param context
     */
    public static void showGathering(Context context) {
        showSimpleBack(context, SimpleBackPage.GATHERING);
    }


}