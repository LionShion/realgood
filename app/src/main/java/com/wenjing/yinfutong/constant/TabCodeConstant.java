package com.wenjing.yinfutong.constant;

/**
 * Created by ${luoyingtao} on 2018\3\21 0021.
 */

public class TabCodeConstant {
    public static final String KEY_TABCODE                       = "tab_code";
    public static final String KEY_ABOVE_VERIFIED_PHONE         = "above_verified_phone";
    public static final String KEY_ABOVE_VERIFIED_VERIFYCODE    = "above_verified_verifycode";
    public static final String KEY_ABOVE_REGIONID                 = "above_regionid";

    /**
     *  进入   的页面码
     */
    public static final int TAB_REGISTER           = 1;
    public static final int TAB_LOGIN              = 2;
    public static final int TAB_TIEBANKCARD       = 3;
    public static final int TAB_RESETPAYMENTPSD   = 4;
    public static final int TAB_RETRIEVEPSD        = 5;
    public static final int TAB_PAYMENTPASSWORD    = 6;



    /**
     * 开通商家服务   地区选择   传递参数的Key
     *
     */
    public static final String KEY_CITY_POSITION         = "city_position";
    public static final String KEY_PRO_POSITION          = "pro_position";
    public static final String KEY_CITYCLASSLIST         = "city_class_list";
    public static final String KEY_CITYNAME              = "city_class_name";
    public static final String KEY_PRONAME               = "pro_class_name";


    //跳转到  区域页面的请求码值
    public static final int REQUESTCODE_REGIONSELECT             = 110;

    //跳转到  商家区域选择City级别
    public static final int REQUESTCODE_MERCHANT_CITYSELECT     = 120;
    //跳转到  商家区域选择Province级别
    public static final int REQUESTCODE_MERCHANT_PROSELECT      = 130;

    /**
     * 国家/地区  传递参数 的Key值
     *
     */
    public static final String KEY_REGION_POSITION = "region_position";
    public static final String KEY_REGION_BEAN      = "region_bean";


    /**
     * 常见问题 详情 传递参数  的Key值
     *
     */
    public static final String KEY_COMMONPB_BEAN = "commonpb_bean";

    /**
     * 收款页面  传递   支付平台  参数的  key
     *
     */
    public static final String KEY_PAYCHANNEL    = "pay_channel";

    public static final int SUCCESS = 0;

}
