package com.wenjing.yinfutong.utils.sign;

import android.util.Log;

import com.wenjing.yinfutong.utils.DecimalFormatUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SignHelper {
    public static final String PARAM_EQUAL = "=";
    public static final String PARAM_AND = "&";

    /**
     * 对Object进行List<NameValuePair>转换后按key进行升序排序，以key=value&...形式返回
     *
     * @return
     */
    public static String sortParamForSignCard(Object order) {
        List<NameValuePair> list = bean2Parameters(order);
        return sortParamForSignCard(list);
    }

    public static String sortParamForSignCard(List<NameValuePair> list) {

        if (list == null) {
            return null;
        }

        Collections.sort(list, new Comparator<NameValuePair>() {
            @Override
            public int compare(NameValuePair lhs, NameValuePair rhs) {
                return lhs.getName().compareToIgnoreCase(rhs.getName());
            }
        });
        StringBuffer sb = new StringBuffer();
        sb.setLength(0);
        for (NameValuePair nameVal : list) {
            sb.append(nameVal.getName());
            sb.append(PARAM_EQUAL);
            sb.append(nameVal.getValue());
            sb.append(PARAM_AND);
        }
        String params = sb.toString();
        if (sb.toString().endsWith(PARAM_AND)) {
            params = sb.substring(0, sb.length() - 1);
        }

        //        TLog.error("待签名串", params);
        return params;
    }

    /**
     * 将bean转换成键值对列表
     *
     * @param bean
     * @return
     */
    public static List<NameValuePair> bean2Parameters(Object bean) {
        if (bean == null) {
            return null;
        }
        List<NameValuePair> parameters = new ArrayList<>();

        // 取得bean所有public 方法
        Method[] Methods = bean.getClass().getMethods();
        for (Method method : Methods) {
            if (method != null && method.getName().startsWith("get")
                    && !method.getName().startsWith("getClass")) {
                // 得到属性的类名
                String value = "";
                // 得到属性值
                try {
                    String className = method.getReturnType().getSimpleName();
                    if (className.equalsIgnoreCase("int")) {
                        int val = 0;
                        try {
                            val = (Integer) method.invoke(bean);
                        } catch (InvocationTargetException e) {
                            Log.e("baseHelper", e.getMessage(), e);
                        }
                        value = String.valueOf(val);
                    } else if (className.equalsIgnoreCase("String")) {
                        try {
                            value = (String) method.invoke(bean);
                        } catch (InvocationTargetException e) {
                            Log.e("baseHelper", e.getMessage(), e);
                        }
                    } else if (className.equalsIgnoreCase("double")) {
                        try {
                            value = DecimalFormatUtil.defFormat((Double) method.invoke(bean));
                        } catch (InvocationTargetException e) {
                            Log.e("baseHelper", e.getMessage(), e);
                        }
                    }
                    if (value != null) {
                        // 添加参数
                        // 将方法名称转化为id，去除get，将方法首字母改为小写
                        String param = method.getName().replaceFirst("get", "");
                        if (param.length() > 0) {
                            String first = String.valueOf(param.charAt(0))
                                    .toLowerCase();
                            param = first + param.substring(1);
                        }
                        //                        BasicNameValuePair
                        parameters.add(new BasicNameValuePair(param, value));
                    }
                } catch (IllegalArgumentException e) {
                    Log.e("baseHelper", e.getMessage(), e);
                } catch (IllegalAccessException e) {
                    Log.e("IllegalAccessException", e.getMessage(), e);
                }
            }
        }
        return parameters;
    }


    /**
     * 对对Object转换后, 以key=value&...形式返回按key进行升序排序，以key=value&...形式返回
     *
     * @param order
     * @return
     */
    public static String sortParam(Object order) {
        if (null == order) {
            return null;
        }


        List<NameValuePair> list = bean2Parameters(order);
        if (null != list) {
            StringBuffer sb = new StringBuffer();
            for (NameValuePair entry : list) {

                if (null != entry.getValue() && !"".equals(entry.getValue())
                        && !entry.getName().equals("id_type")
                        && !entry.getName().equals("id_no")
                        && !entry.getName().equals("acct_name")
                        && !entry.getName().equals("flag_modify")
                        && !entry.getName().equals("user_id")
                        && !entry.getName().equals("no_agree")
                        && !entry.getName().equals("card_no")
                        && !entry.getName().equals("test_mode")
                        && !entry.getName().equals("force_bank")) {
                    sb.append(entry.getName());
                    sb.append(PARAM_EQUAL);
                    sb.append(entry.getValue());
                    sb.append(PARAM_AND);
                }
            }

            String params = sb.toString();
            if (sb.toString().endsWith(PARAM_AND)) {
                params = sb.substring(0, sb.length() - 1);
            }
            Log.v("待签名串", params);
            return params;
        }

        return null;
    }

    public static String toJSONString(Object obj) {
        JSONObject json = new JSONObject();
        try {
            List<NameValuePair> list = bean2Parameters(obj);
            for (NameValuePair nv : list) {
                json.put(nv.getName(), nv.getValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }

    public static JSONObject string2JSON(String str) {
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }
}
