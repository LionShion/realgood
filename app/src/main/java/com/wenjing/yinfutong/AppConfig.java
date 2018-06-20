package com.wenjing.yinfutong;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by YueXiaoHui on 2015/4/28.
 * <p/>
 * 应用程序配置类：用于保存用户相关信息及一些配置信息
 */
public class AppConfig {
    private final static String APP_CONFIG = "config";

    public final static String SAVE_IMAGE_PATH = "save_image_path";    //图片保存配置key
    public final static String CONF_APP_UNIQUEID = "app_uniqueid";       //app唯一标识配置key
    public static final String KEY_CHECK_UPDATE = "key_check_update";   //检查更新
    public static final String KEY_UPGRADE_TYPE = "key_upgrade_type";   //更新方式
    public static final String KEY_IS_OPEN_GESTRUE = "key_is_open_gestrue";//手势密码 开关状态
    public static final String KEY_FIRST_OPEN_APP = "key_first_open_app";
    public static final String KEY_GESTRUE_ERROR = "key_gestrue_error";
    public static final String KEY_SHARE = "key_share";          //分享
    public static final String KEY_IS_SHARE = "key_is_share";       //分享
    public static final String PRIVATE_KEY = "private_key";       //私钥
    public static final String GESTRUE_FROM = "GESTRUE_FROM";       //关闭>>0 修改>>1



    // 默认存放文件下载的路径
    public final static String DEFAULT_SAVE_FILE_PATH = Environment
            .getExternalStorageDirectory()
            + File.separator
            + "YinKe"
            + File.separator + "download" + File.separator;
    public static final String KEY_REGISTER_SUCCESS = "REGISTER_SUCCESS";
    public static final String KEY_REGISTER_SUCCESS_BUY = "REGISTER_SUCCESS_BUY";
    public static final String KEY_REGISTER_DIALOG = "KEY_REGISTER_DIALOG";

    private Context mContext;

    private static AppConfig appConfig;
    //默认保存图片的路径
    public final static String DEFAULT_SAVE_IMAGE_PATH = Environment.getExternalStorageDirectory() + File.separator + "yinke" + File.separator;


    public static AppConfig getAppConfig(Context context) {
        if (appConfig == null) {
            appConfig = new AppConfig();
            appConfig.mContext = context;
        }
        return appConfig;
    }


    /**
     * 请求参数
     * @return
     */
    public static RequestBody getRequestBody() {
        return RequestBody.create(MediaType.parse("multipart/form-data"), "无话可说");
    }

}
