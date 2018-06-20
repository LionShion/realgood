package com.wenjing.yinfutong.view;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;

import com.wenjing.yinfutong.utils.TLog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by luoyingtao on 2018\5\11 0011.
 */

public class CashierInputFilter implements InputFilter{

    private Pattern mPattern;
    //输入最大金额
    public static final int MAX_VALUE = 50000;

    //小数点后面的   位数
    public static final int POINTER_LENGTH = 2;

    public static final String POINTER = ".";
    public static final String ZERO = "0";

    public CashierInputFilter() {
        mPattern = Pattern.compile("([0-9]|\\.)*");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        TLog.log(this , "dest : " + dest + " , dstart : " + dstart + " ,dend : " + dend);
        TLog.log(this , "source : " + source + " , start : " + start + " ,end : " + end);

        String sourceTxt = source.toString();
        String destTxt = dest.toString();

        //验证  删除等按键
        if(TextUtils.isEmpty(sourceTxt)){
            return "";
        }

        Matcher matcher = mPattern.matcher(sourceTxt);

        //已经输入小数点的情况下      只能输入数字
        if(destTxt.contains(POINTER)){
            if(!matcher.matches()){
                return "";
            }else {
                if(source.equals(POINTER)){//只能输入 一个小数点
                    TLog.log(this , "只能输入 一个小数点");
                    return "";
                }
            }

            //验证  小数点精度   保证小数点后面只有两位
            int index = destTxt.indexOf(POINTER);
            int length = destTxt.length() - index;
            TLog.log(this , "destTxt : " + destTxt + "含有 一个小数点 , " + " ,其中: index : " + index + " , length : " + length);
            if(length > POINTER_LENGTH && index < dstart ){
                return "";
            }
        }

        //验证输入    金额大小
        double sumTxt = Double.parseDouble(destTxt + sourceTxt);
        if(sumTxt > MAX_VALUE){
            TLog.log(this , "金额超过最大值");
            return dest.subSequence(dstart , dend);
        }
        return dest.subSequence(dstart , dend) + sourceTxt;
    }

}
