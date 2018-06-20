package com.wenjing.yinfutong;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.wenjing.yinfutong.base.BaseApplication;
import com.wenjing.yinfutong.common.CacheManager;
import com.wenjing.yinfutong.model.Customer;
import com.wenjing.yinfutong.utils.SharedPreferenceUtils;
import com.wenjing.yinfutong.utils.TLog;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by YueXiaoHui on 2015/4/28.
 * 全局应用配置类，用于保存全局应用配置及访问网络信息
 */
public class AppContext extends BaseApplication {

    public static final String LOGIN_USER_KEY = "login_user_obj.txt";//保存登入用户的key
    private static final String GESTURE_PASSWORD = "GESTURE_PASSWORD";


    private String DEVICE_ID;//设备唯一标识
    private static AppContext instance;

    private boolean login = false; // 登录状态
    private String saveImagePath;// 保存图片路径

    public static boolean isPaySuccess = false;

    public static int pushProductId = 0;
    public static int pushNewsId = 0;
    private Customer user;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppContext.instance().initLoginInfo(this);

        JPushInterface.setDebugMode(true);//设置开启日志,发布时请关闭日志
        JPushInterface.init(this);//初始化 JPush
        String registrationID = JPushInterface.getRegistrationID(this);
        TLog.log("Application",registrationID);
        //科大讯飞语音包
        SpeechUtility.createUtility(this, SpeechConstant.APPID +"=598d726f");
    }

    public static AppContext instance() {
        return instance;
    }


    /**
     * 初始化
     */

    public String DeviceId() {
        SharedPreferences prefs = this.getSharedPreferences("device_id.xml", 0);
        DEVICE_ID = prefs.getString("device_id", "");
        return DEVICE_ID;
    }

    /**
     * 判断用户是否登入
     *
     * @return
     */
    public boolean isLogin() {
        return login;
    }


    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo() {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null)
            info = new PackageInfo();
        return info;
    }


    public Customer getLoginUser() {
        return this.user;
    }


    /**
     * 初始化用户信息
     *
     * @param context
     * @return
     */
    public void initLoginInfo(Context context) {
        Customer user = getLoginInfo(context);
        TLog.log(this , String.valueOf(user));
        if (user != null) {
            this.login = true;
            this.user = user;
        }
    }

    /**
     * 获取用户信息
     *
     * @param context
     * @return
     */
    public Customer getLoginInfo(Context context) {
        Customer user = (Customer) CacheManager.readObject(context, LOGIN_USER_KEY);
        return user;
    }

    /**
     * 清除用户信息
     */
    public void clearLoginUser(Context context) {
        CacheManager.deleteCacheData(context, LOGIN_USER_KEY);
        this.login = false;
        this.user = null;
    }

    /**
     * 保存用户信息
     *
     * @param context
     * @param user
     */
    public void saveLoginInfo(Context context, Customer user) {
        this.login = true;
        this.user = user;
        CacheManager.saveObject(context, user, LOGIN_USER_KEY);
    }


    public static final String KEY_ACCESSTOKEN_STRING = "KEY_ACCESSTOKEN_STRING";

    /**
     * 获取accesstoken
     *
     * @return
     */
    public static String getAccesstoken() {
        SharedPreferences sharedPreferences = AppContext.getPreferences();
        String accesstoken = sharedPreferences.getString(KEY_ACCESSTOKEN_STRING, "");
        return accesstoken;
    }

    /**
     * 保存accesstoken
     *
     * @param accessToken
     */
    public static void setAccessToken(String accessToken) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(KEY_ACCESSTOKEN_STRING, accessToken);
        apply(editor);
    }

    /**
     * 拿到  保存语言
     *
     * @return
     */
    public static int getLangulage() {
        String language = SharedPreferenceUtils.getLanguage(AppContext.instance);
        return SharedPreferenceUtils.getLangFlagMap.get(language);
    }


    /**
     * 存放   登录密码
     *
     * @param psd
     * @return
     */
    public static void saveLoginPsd(String psd){
        SharedPreferenceUtils.putLoginpsd(AppContext.instance,psd);
    }

    /**
     * 获取  缓存 的     登录密码
     *
     * @return
     */
    public static String getLoginPsd(){
        return SharedPreferenceUtils.getLoginPsd(AppContext.instance);
    }

    /**
     * 获取缓存 的   语音提醒   状态
     *
     * @return
     */
    public static boolean getVoicePrompt(){
        return SharedPreferenceUtils.getVoiceStatus(AppContext.instance);
    }

    /**
     * 保存  语音提醒状态
     * @param voiceStatus
     */
    public static void saveVoicePrompt(boolean voiceStatus){
        SharedPreferenceUtils.putVoiceStatus(AppContext.instance , voiceStatus);
    }

    /**
     *当前   网络   连接状态
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

}
