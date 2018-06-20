package com.wenjing.yinfutong.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.constant.RequestConstant;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by wenjing on 2018/2/8.
 */

public class SharedPreferenceUtils {
    public static final String TAG = SharedPreferenceUtils.class.getSimpleName();

    public static final String DEFAULT_SP_NAME = "preference" ;

    /**
     * 是否显示欢迎界面,true表示显示，false表示不显示
     */
    public static final String SHOW_GUIDE     = "showguide";

    /**
     * 切换语言
     */
    public static final String SP_NAME_LANGUAGE = "language";
    public static final String KEY_LANGUAGE     = "language";

    /**
     * 密码保存
     */

    public static final String SP_NAME_PSD    = "psd";
    public static final String KEY_LOGIN_PSD  = "login_psd";

    /**
     * 语言提醒  状态保存
     *
     */
    public static final String SP_NAME_VOICEPROMPT     = "voiceprompt";
    public static final String KEY_STATUS_VOICEPROMPT  = "status_voiceprompt";

    public static final Map<Integer, String> getLangStrMap = new HashMap<Integer, String>() {
        {
            put(RequestConstant.REGISTER_LANGUAGE_CHINESE, "zh");
            put(RequestConstant.REGISTER_LANGUAGE_ENGLISH, "en");
            put(RequestConstant.REGISTER_LANGUAGE_CAMBODIA, "km");//km  - KH
        }
    };

    public static final Map<String, Integer> getLangFlagMap = new HashMap<String, Integer>() {
        {
            put("zh", RequestConstant.REGISTER_LANGUAGE_CHINESE);
            put("en", RequestConstant.REGISTER_LANGUAGE_ENGLISH);
            put("km", RequestConstant.REGISTER_LANGUAGE_CAMBODIA);//km  - KH
        }
    };


    /**
     * 保存到Preference
     */
    public static void setBoolean(Context context , String spName , String key, boolean value) {
        // 得到SharedPreferences
        SharedPreferences preferences = context.getSharedPreferences(
                spName , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key , value);
        editor.commit();
    }

    /**
     * 从Preference取出数据
     */
    public static boolean getBoolean(Context context , String spName , String key , boolean defValue) {
        SharedPreferences preferences = context.getSharedPreferences(
                spName, Context.MODE_PRIVATE);
        // 返回key值，key值默认值是false
        return preferences.getBoolean(key , defValue);
    }

    /**
     * 保存  语音提醒
     *
     * @param context
     * @param voiceStatus
     */
    public static void putVoiceStatus(Context context , boolean voiceStatus){
        setBoolean(context , SP_NAME_VOICEPROMPT , KEY_STATUS_VOICEPROMPT , voiceStatus);
    }

    /**
     * 获取  语音提醒状态
     * 默认为true    开启
     *
     * @param context
     * @return
     */
    public static boolean getVoiceStatus(Context context){
        boolean voiceStatus = getBoolean(context , SP_NAME_VOICEPROMPT , KEY_STATUS_VOICEPROMPT , true);
        return voiceStatus;
    }

    /**
     * 去除  存放的 语言
     *
     * @param context
     * @return
     */
    public static String getLanguage(Context context) {
        String language_addr = getString(context, SP_NAME_LANGUAGE, KEY_LANGUAGE);

        TLog.log(TAG , "language_addr : " + language_addr + " , Locale.getDefault().getLanguage() : " + Locale.getDefault().getLanguage());
//        Locale curLocale = AppContext.instance().getResources().getConfiguration().locale;
//        //通过Locale的equals方法，判断出当前语言环境
//        if (curLocale.equals(Locale.SIMPLIFIED_CHINESE)) {
//            //中文
//            language_addr = "zh";
//        } else if (curLocale.equals(Locale.ENGLISH)) {
//            //英文
//            language_addr = "en";
//        }

        if(TextUtil.isEmpty(language_addr)){
            TLog.log(TAG , "初始状态本地保存 language_addr 为空 , 使用系统当前语言  Locale.getDefault().getLanguage() : " + Locale.getDefault().getLanguage());
            language_addr = Locale.getDefault().getLanguage();
        }
        return language_addr;
    }


    /**
     * 存放   语言
     *
     * @param context
     * @param languageFlag
     */
    public static void putLanguage(Context context, int languageFlag) {
        String language = getLangStrMap.get(languageFlag);
        if (language == null)
            language = "en";
        putLanguage(context, language);
    }

    /**
     * 存放 语言
     *
     * @param context
     * @param language
     */
    public static void putLanguage(Context context, String language) {
        putString(context, SP_NAME_LANGUAGE, KEY_LANGUAGE, language);
    }

    /**
     * 存放  String类型数据  的 概要
     *
     * @param context
     * @param spName  轻量型缓存   xml文件名称
     * @param key     键
     * @param value   值
     */
    private static void putString(Context context, String spName, String key, String value) {
        // 得到SharedPreferences
        SharedPreferences preferences = context.getSharedPreferences(
                spName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取  String类型数据  的 概要
     *
     * @param context
     * @param spName
     * @param key
     * @return
     */
    private static String getString(Context context, String spName, String key) {
        SharedPreferences preferences = context.getSharedPreferences(
                spName, Context.MODE_PRIVATE);
        // 返回key值，key值默认值是false
        return preferences.getString(key, null);
    }


    /**
     * 存放   登录密码
     *
     * @param context
     * @param psd
     */
    public static void putLoginpsd(Context context, String psd) {
        putString(context, SP_NAME_PSD, KEY_LOGIN_PSD, psd);
    }

    /**
     * 获取 登录密码
     *
     * @param context
     * @return
     */
    public static String getLoginPsd(Context context) {
        return getString(context, SP_NAME_PSD, KEY_LOGIN_PSD);
    }

}
