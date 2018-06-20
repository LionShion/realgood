package com.wenjing.yinfutong.constant;

import android.util.Log;

import com.wenjing.yinfutong.R;
import com.wenjing.yinfutong.utils.Validator;

/**
 * Created by ${luoyingtao} on 2018\3\21 0021.
 */

public class RequestConstant {
    public static final String TAG = "RequestConstant";
    /**
     * 短信验证码  签名
     */

    public static final String PRIVATE_KEY = "21804102_tset345100000170804101";

    /**
     * 发送消息类型：register、注册消息；login、重置登录密码；trader、重置交易密码；
     */
    public static final String SMS_TYPE_REGISTER     = "register";
    public static final String SMS_TYPE_LOGIN         = "login";
    public static final String SMS_TYPE_TRADE         = "trade";
    public static final String SMS_TYPE_BINDCARD      = "bindcard";
    public static final String SMS_TYPE_BINDEMAIL     = "bindemail";

    /**
     * 注册语言：1中文；2英文；3柬埔寨文
     */
    public static final int REGISTER_LANGUAGE_CHINESE  = 1;
    public static final int REGISTER_LANGUAGE_ENGLISH  = 2;
    public static final int REGISTER_LANGUAGE_CAMBODIA = 3;


    /**
     * 账户类型：1、手机号；2、邮箱
     */
    public static final int ACCOUNT_TYPE_PHONE = 1;
    public static final int ACCOUNT_TYPE_EMAIL = 2;

    /**
     * 银行卡类别 0：储蓄卡 1：信用卡
     */
    public static final int BANKCARD_TYPE_SAVINGSCARD = 0;
    public static final int BANKCARD_TYPE_CREDITCARD = 1;

    /**
     *
     * 账户角色类别
     * 角色类型 1：用户 2：商户
     */
    public static final int STATUS_NO_REGISTER     = 0;
    public static final int STATUS_UNDER_REVIEW   = 1;
    public static final int STATUS_IS_MECHANT     = 2;
    public static final int STATUS_REFUSE         = 3;

    public static final int DEPOSITBANK_TYPE_UNIONPAY      = 0;
    public static final int DEPOSITBANK_TYPE_NOTUNIONPAY   = 1;

    /**
     * 交易密码标识：0-未设置，1-设置交易密码
     *
     */
    public static final int TRADEPSD_FLAG_NO  = 0;
    public static final int TRADEPSD_FLAG_YES = 1;

    /**
     *支付平台
     * 0   系统支付  (银付通    自己的)
     *
     * 1  微信支付
     *
     * 2   支付宝支付
     *
     */
    public static final int PAYCHANNEL_TYPE_SYSTEM    = 0;
    public static final int PAYCHANNEL_TYPE_WEPAY     = 1;
    public static final int PAYCHANNEL_TYPE_ALIPAY    = 2;


    /**
     * 手机联系人     区域默认  数据
     */
    public static final int    REGION_DEFAULT_ID          = 1;
    public static final int    REGION_DEFAULTNAME_RESID  = R.string.Chinese_Mainland;
    public static final int    REGION_DEFAULT_CODE        = 86;

    public static final String STATUS_OFFLINE_RECHARGE_QUEUE = "queue" ;


    /**
     * 获取 账户类型
     * @param account
     * @return
     */
    public static int getAccountType(String account){
        int accountType = -1;
        if(Validator.isMobile(account)){
            accountType = ACCOUNT_TYPE_PHONE;
        }else if(Validator.isEmail(account)){
            accountType = ACCOUNT_TYPE_EMAIL;
        }
        Log.i(TAG,"accountType : " + accountType);
        return accountType;
    }

}
