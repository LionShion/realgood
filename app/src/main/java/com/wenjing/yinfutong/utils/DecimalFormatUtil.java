package com.wenjing.yinfutong.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by wenjing on 2017/11/27.
 */

public class DecimalFormatUtil {

    private static final String DEFAULT_PATTERN = "0.00";

    /**
     * 根据格式转换
     * @param pattern 要转换的模式，例如000，
     * @param data 数据
     * 例：当data是5，pattern是000，则结果是005，当data是100时，结果是100
     * @return
     */
    private static String format (String pattern,Object data){
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
//      手动什么为  小数点 .   不让其随语言环境而变化
        dfs.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat(pattern);
        df.setDecimalFormatSymbols(dfs);
        return df.format(data);
    }

    /**
     * 本app  中  金额 默认使用  格式
     * @param data
     * @return
     */
    public static String defFormat(Object data){
        return format(DEFAULT_PATTERN , data);
    }

}
