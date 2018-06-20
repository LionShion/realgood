package com.wenjing.yinfutong.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wenjing on 2017/6/28.
 */

public class Validator {
    public static final String TAG = Validator.class.getSimpleName();

    /**
     * 区号
     * 正则表达式:验证区号(不包含特殊字符)
     */
    public static final String REGEX_REGION_CODE    = "^[0-9]{1,3}$";


    /**
     * 正则表达式:验证用户名(不包含中文和特殊字符)如果用户名使用手机号码或邮箱 则结合手机号验证和邮箱验证
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 登录密码
     * 正则表达式:验证密码(不包含特殊字符)
     */
    public static final String REGEX_PASSWORD_LOGIN     = "^[a-zA-Z0-9]{6,20}$";

    /**
     * 支付密码
     * 正则表达式:验证密码(不包含特殊字符)
     */
    public static final String REGEX_PASSWORD_PAYMENT   = "^[0-9]{6}$";

    /**
     * 正则表达式:验证手机号
     */
    public static final String REGEX_MOBILE = "^(0|86|17951)?(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])[0-9]{8}$";

    /**
     * 正则表达式:验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 正则表达式:验证汉字(1-9个汉字)  {1,9} 自定义区间
     */
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5]{1,9}$";

    /**
     * 正则表达式:验证身份证
     */
    //    public static final String REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";
    //定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
    public static final String REGEX_ID_CARD      = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])";


    /**
     * 正则表达式:银行卡卡号
     *
     * 借记卡  16 -19
     * 信用卡  18 - 21
     */
    public static final String REGEX_BANKNO_CARD  = "^([1-9]{1})(\\d{15,20})";


    /**
     * 正则表达式:验证IP地址
     */
    public static final String REGEX_IP_ADDR = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";


    /**
     * 正则表达式：验证长度是否符合
     */
    public static final String REGEX_LENGTH = "^[a-zA-Z0-9]{6,8}$";

    /**
     * 校验区号
     * @param regionCode
     * @return
     */
    public static boolean isRegionCode(String regionCode){
        return Pattern.matches(REGEX_REGION_CODE,regionCode);
    }

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUserName(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 获取中间四位数  加密的手机号
     * @param phone
     * @return
     */
    public static String getStarPhone(String phone){
        return phone.replaceAll("^(.{3})(.*)(.{4})$","$1****$3");
    }

    /**
     * 获取中间四位数  加密的邮箱号
     * @param email
     * @return
     */
    public static String getStarEmail(String email){
        String[] split = email.split("@");
        String first = split[0];
        String front = "";
        if(first.length() > 7){
            front = first.replaceAll("^(.{3})(.*)(.{4})$", "$1****$3");
        }else if(first.length() > 5){
            front = first.replaceAll("^(.{2})(.*)(.{2})$", "$1****$3");
        }else {
            front = first.replaceAll("^(.{1})(.*)(.{1})$", "$1****$3");
        }
        return front + "@" + split[1];
    }

    /**
     * 每隔4个数字  加一个空格
     * @param content
     * @return
     */
    public static String getPerFourRegex(String content){
            return content.replaceAll("\\d{4}(?!$)","$0 ");
    }

    /**
     * 每三个  数字加一个逗号
     *
     * @return
     */
    public static String getPerThreeCommaAmount(String amount){
        TLog.log(TAG, "amount: " + amount);
        if(TextUtil.isEmpty(amount)){
            return "";
        }

        //默认
        String front = amount;
        String behind = "";
        if(amount.contains(".")){
            int commaIndex = amount.indexOf(".");
            front = amount.substring(0 , commaIndex);//后面不包含
            behind = amount.substring(commaIndex);
        }

        TLog.log(TAG , "front : " + front + " , behind : " + behind);

        front = new StringBuilder(front).reverse().toString();     //先将字符串颠倒顺序

        String str2 = "";
        for(int i=0; i<front.length(); i++){
            if(i*3+3 > front.length()){
                str2 += front.substring(i*3, front.length());
                break;
            }
            str2 += front.substring(i*3, i*3+3)+",";
        }
        if(str2.endsWith(",")){
            str2 = str2.substring(0, str2.length()-1);
        }
        //最后再将顺序反转过来
        return new StringBuilder(str2).reverse().toString() + behind;
    }

    /**
     * 校验   登录密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isLoginPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD_LOGIN, password);
    }

    /**
     * 校验   支付密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPayPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD_PAYMENT, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isChinese(String chinese) {
        return Pattern.matches(REGEX_CHINESE, chinese);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }


    /**
     * 校验  银行卡卡号
     * @param idCard
     * @return
     */
    public static boolean isBankNo(String idCard) {
        return Pattern.matches(REGEX_BANKNO_CARD, idCard);
    }

    /**
     * 校验IP地址
     *
     * @param ipAddress
     * @return
     */
    public static boolean isIPAddress(String ipAddress) {
        return Pattern.matches(REGEX_IP_ADDR, ipAddress);
    }

    /**
     * 检验长度6-8位
     *
     * @param length
     * @return
     */
    public static boolean isLength(String length) {
        return Pattern.matches(REGEX_LENGTH, length);
    }


}
