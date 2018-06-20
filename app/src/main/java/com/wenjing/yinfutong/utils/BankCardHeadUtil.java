package com.wenjing.yinfutong.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.wenjing.yinfutong.AppContext;
import com.wenjing.yinfutong.R;

import java.lang.reflect.Field;

/**
 * Created by yuexiaohui on 2017/5/17
 */

public class BankCardHeadUtil {

    public static String getBankServerPhone(String serialNO) {
        if (!StringUtils.isEmpty(serialNO)) {
            if ("01020000".equals(serialNO)) {
                return "95588";
            }
            if ("01030000".equals(serialNO)) {
//                "bankName": "农业银行",
//                        "bankNo": "01030000",
                return "95599";
            }
            if ("01040000".equals(serialNO)) {
//                "bankName": "中国银行",
//                        "bankNo": "01040000",
                return "95566";
            }
            if ("01050000".equals(serialNO)) {
//                "bankName": "建设银行",
//                        "bankNo": "01050000",
                return "95533";
            }
            if ("03030000".equals(serialNO)) {
//                "bankName": "光大银行",
//                        "bankNo": "03030000",
                return "95595";
            }
            if ("03040000".equals(serialNO)) {
//                "bankName": "华夏银行",
//                        "bankNo": "03040000",
                return "95577";
            }
            if ("03050000".equals(serialNO)) {
//                "bankName": "民生银行",
//                        "bankNo": "03050000",
                return "95568";
            }
            if ("03060000".equals(serialNO)) {
//                "bankName": "广发银行",
//                        "bankNo": "03060000",
                return "95508";
            }
            if ("03080000".equals(serialNO)) {
//                "bankName": "招商银行",
//                        "bankNo": "03080000",
                return "95555";
            }
            if ("03090000".equals(serialNO)) {
//                "bankName": "兴业银行",
//                        "bankNo": "03090000",
                return "95561";
            }
            if ("03100000".equals(serialNO)) {
//                "bankName": "浦发银行",
//                        "bankNo": "03100000",
                return "95528";
            }
            if ("03070000".equals(serialNO)) {
//                "bankName": "平安银行",
//                        "bankNo": "03070000",
                return "95511";
            }
            if ("01000000".equals(serialNO)) {
//                "bankName": "邮政储蓄银行",
//                        "bankNo": "01000000",
                return "95580";
            }
            if ("03010000".equals(serialNO)) {
//                "bankName": "交通银行",
//                        "bankNo": "03010000",
                return "95559";
            }
            if ("04031000".equals(serialNO)) {
//                "bankName": "北京银行",
//                        "bankNo": "04031000",
                return "95526";
            }
            if ("04233310".equals(serialNO)) {
//                "bankName": "杭州银行",
//                        "bankNo": "04233310",
                return "95398";
            }
            if ("03160000".equals(serialNO)) {
//                "bankName": "浙商银行",
//                        "bankNo": "03160000",
                return "95527";
            }
            if ("04012900".equals(serialNO)) {
//                "bankName": "上海银行",
//                        "bankNo": "04012900",
                return "95594";
            }

            if ("64135810".equals(serialNO)) {
//                "bankName": "广州银行",
//                        "bankNo": "64135810",
                return "96699";
            }

            if ("03020000".equals(serialNO)) {
//                "bankName": "中信银行",
//                        "bankNo": "03020000",
                return "95558";
            }
        }
        return "";
    }

    public static Drawable getBankDrawable(String serialNO) {
        Context context = null;
        if (!StringUtils.isEmpty(serialNO)) {
            context = AppContext.instance();
            if ("01020000".equals(serialNO)) {
//                "bankName": "工商银行",
//                        "bankNo": "01020000",
                return getIcon(context, "bank_gongshang");
            }
            if ("01030000".equals(serialNO)) {
//                "bankName": "农业银行",
//                        "bankNo": "01030000",
                return getIcon(context, "bank_nongye");
            }
            if ("01040000".equals(serialNO)) {
//                "bankName": "中国银行",
//                        "bankNo": "01040000",
                return getIcon(context, "bank_zhongguo");
            }
            if ("01050000".equals(serialNO)) {
//                "bankName": "建设银行",
//                        "bankNo": "01050000",
                return getIcon(context, "bank_jianshe");
            }
            if ("03020000".equals(serialNO)) {
                return getIcon(context, "bank_zhongxin");
            }
            if ("03030000".equals(serialNO)) {
//                "bankName": "光大银行",
//                        "bankNo": "03030000",
                return getIcon(context, "bank_guangda");
            }
            if ("03040000".equals(serialNO)) {
//                "bankName": "华夏银行",
//                        "bankNo": "03040000",
                return getIcon(context, "bank_huaxia");
            }
            if ("03050000".equals(serialNO)) {
//                "bankName": "民生银行",
//                        "bankNo": "03050000",
                return getIcon(context, "bank_minsheng");
            }
            if ("03060000".equals(serialNO)) {
//                "bankName": "广发银行",
//                        "bankNo": "03060000",

                return getIcon(context, "bank_guangfa");
            }
            if ("03080000".equals(serialNO)) {
//                "bankName": "招商银行",
//                        "bankNo": "03080000",
                return getIcon(context, "bank_zhaoshang");
            }
            if ("03090000".equals(serialNO)) {
//                "bankName": "兴业银行",
//                        "bankNo": "03090000",
                return getIcon(context, "bank_xingye");
            }
            if ("03100000".equals(serialNO)) {
//                "bankName": "浦发银行",
//                        "bankNo": "03100000",
                return getIcon(context, "bank_pufa");
            }
            if ("03070000".equals(serialNO)) {
//                "bankName": "平安银行",
//                        "bankNo": "03070000",
                return getIcon(context, "bank_pingan");
            }
            if ("01000000".equals(serialNO)) {
//                "bankName": "邮政储蓄银行",
//                        "bankNo": "01000000",
                return getIcon(context, "bank_chuxu");
            }
            if ("03010000".equals(serialNO)) {
//                "bankName": "交通银行",
//                        "bankNo": "03010000",
                return getIcon(context, "bank_jiaotong");
            }
            if ("04031000".equals(serialNO)) {
//                "bankName": "北京银行",
//                        "bankNo": "04031000",
                return getIcon(context, "bank_beijing");
            }
            if ("04233310".equals(serialNO)) {
//                "bankName": "杭州银行",
//                        "bankNo": "04233310",
                return getIcon(context, "bank_hangzhou");
            }
            if ("03160000".equals(serialNO)) {
//                "bankName": "浙商银行",
//                        "bankNo": "03160000",
                return getIcon(context, "bank_zheshang");
            }
            if ("04012900".equals(serialNO)) {
//                "bankName": "上海银行",
//                        "bankNo": "04012900",
                return getIcon(context, "bank_shanghai");
            }

            if ("64135810".equals(serialNO)) {
//                "bankName": "广州银行",
//                        "bankNo": "64135810",
                return getIcon(context, "bank_guangzhou");
            }

            if ("03020000".equals(serialNO)) {
//                "bankName": "中信银行",
//                        "bankNo": "03020000",
                return getIcon(context, "bank_zhongxin");
            }
        }
        return getIcon(context, "bank_gongshang");
    }

    public static Drawable getIcon(Context context, String resName) {
        try {
            Field field = R.mipmap.class.getField(resName);
            int resId = field.getInt(R.drawable.class);
            return context.getResources().getDrawable(resId);
        } catch (Exception e) {
        }

        return null;
    }
}
