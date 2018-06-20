package com.wenjing.yinfutong.utils.sign;

/**
 * Created by yuexiaohui on 2017/5/6 14:28
 */

public class TextUtils {

    public static boolean isBlank(final CharSequence s) {
        if (s == null) {
            return true;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
