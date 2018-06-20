package com.wenjing.yinfutong.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    /**
     * MD5加密
     *
     * @param str
     * @return
     */
    public static String strMD5(String str) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
        byte[] strTemp = str.getBytes();
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] s = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                s[k++] = hexDigits[byte0 >>> 4 & 0xf];
                s[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(s);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取文件MD5
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public static String fileMD5(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    /**
     * 字符串倒序
     *
     * @param str
     * @return
     */
    public static String revertStr(String str) {
        if (!isEmpty(str)) {
            return new StringBuffer(str).reverse().toString();
        }
        return "";
    }


    /**
     * 判断登陆密码的正确性
     *
     * @param password
     * @return
     */
    public static boolean isLoginPassword(String password) {
        if (isEmpty(password)) {
            return false;
        }
        String regex = "^[0-9a-zA-Z]{6,20}";
        return Pattern.matches(regex, password);
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 手机号格式判定
     */
    public static boolean matchCellNum(String cellNum) {
        if (isEmpty(cellNum)) {
            return false;
        }
        String regex = "(\\+\\d+)?1[345678]\\d{9}$";
        return Pattern.matches(regex, cellNum);
    }


    /**
     * 身份证号判断合法
     *
     * @param idCard
     * @return
     */
    public static boolean matchIdCard(String idCard) {
        Pattern pattern = Pattern.compile("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$");
        Matcher matcher = pattern.matcher(idCard);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    //去掉小数点后面无用的0
    public static String removeUselessZero(String s) {
        if (s.indexOf(".") > 0) {
            //正则表达
            s = s.replaceAll("0+?$", "");//去掉后面无用的零
            s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }

        return s;
    }

    public static String formatDoubleValue(Double value) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value);
    }

    /*
     * 输入格式：数字、字母、6-20位
	 */
    public static boolean matchAccount(String accountStr) {
        Pattern pattern = Pattern
                .compile("^[A-Za-z0-9_\\-\\u4e00-\\u9fa5]{6,20}$");
        Matcher matcher = pattern.matcher(accountStr);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 隐藏手机号中间四位
     *
     * @param cellphone
     * @return
     */
    public static String hideCellNum(String cellphone) {

        String result = "";
        if (!StringUtils.isEmpty(cellphone))
            if (cellphone.length() >= 11)
                result = cellphone.substring(0, 3) + "****" + cellphone.substring(7);
        return result;
    }

    public static String hideIdcard(String idCard) {
        String result = "";
        result = idCard.substring(0, 3) + "**********" + idCard.substring(idCard.length() - 4);
        return result;
    }

    /**
     * 姓名中间加*
     *
     * @param customerName
     * @return
     */
    public static String hideCustomerName(String customerName) {
        String result = "";
        if (!StringUtils.isEmpty(customerName)) {
            if (customerName.length() > 2) {
                result = customerName.substring(0, 1) + "*" + customerName.substring(customerName.length() - 1);
            } else {
                result = customerName.substring(0, 1) + "*";
            }
        }

        return result;
    }

    public static String formatMoneyAmount(String moneyAmount) {
        if (!isEmpty(moneyAmount)) {
            DecimalFormat dFormat = new DecimalFormat("#,###");
            String dfString = dFormat.format(Double.parseDouble(moneyAmount));
            return dfString;
        }
        return moneyAmount;
    }

    /**
     * long类型的时间转化成指定的时间类型,默认yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getFormatDate(String longTime, String formatString) {
        String foramt = "yyyy-MM-dd HH:mm:ss";
        String result = "";
        Date date = longStrToDate(longTime);
        if (date != null) {
            if (formatString != null && formatString.length() > 0) {
                foramt = formatString;
            }
            SimpleDateFormat oDateFormat = new SimpleDateFormat(foramt);
            result = oDateFormat.format(date);
        }
        return result;
    }


    public static int getDayOfCurrentMonth(long date) {
        Date dt = longStrToDate(String.valueOf(date));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 将日期转换成指定的格式默认为yyyy-MM-dd hh:mm:ss
     *
     * @param oDate        要转换的日期
     * @param foramtString 要转换的格式
     * @return
     */
    public static String dataFormt(Date oDate, String foramtString) {
        String result = "";
        if (oDate != null) {
            String foramt = "yyyy-MM-dd HH:mm:ss";
            if (foramtString != null && foramtString.length() > 0) {
                foramt = foramtString;
            }

            SimpleDateFormat oDateFormat = new SimpleDateFormat(foramt);
            result = oDateFormat.format(oDate);
        }
        return result;
    }

    public static String formatStringDate(String dateStr) {
        if (!StringUtils.isEmpty(dateStr)) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
                String dateString = new SimpleDateFormat("yyyy-MM-dd").format(date);
                return dateString;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static String formatStringDate(String dateStr, String format) {
        if (!StringUtils.isEmpty(dateStr)) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
                String dateString = new SimpleDateFormat(format).format(date);
                return dateString;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    public static long strDateToLongDate(String dateStr) {
        if (!StringUtils.isEmpty(dateStr)) {
            try {
                Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
                return date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * long字符串转换成Date类型
     *
     * @param longTime
     * @return
     */
    public static Date longStrToDate(String longTime) {
        long time = toLong(longTime);
        Date date = new Date(time);
        return date;
    }

    /**
     * 计算多少天后的日期
     *
     * @param dateStr
     * @param day
     * @return
     */

    public static String getDateAfterDay(String dateStr, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(StringUtils.stringToDate("yyyy-MM-dd", dateStr));
        calendar.add(Calendar.DAY_OF_YEAR, day);
        String deadLineTime = StringUtils.dataFormt(calendar.getTime(), "yyyy-MM-dd");
        return deadLineTime;

    }

    /**
     * 计算多少天后的日期
     *
     * @param dateStr
     * @param day
     * @return
     */

    public static String getDateAfterDay(String dateStr, int day, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(StringUtils.stringToDate("yyyy-MM-dd", dateStr));
        calendar.add(Calendar.DAY_OF_YEAR, day);
        String deadLineTime = StringUtils.dataFormt(calendar.getTime(), format);
        return deadLineTime;

    }

    /**
     * 将指定格式的时间类型转换成Date类型
     *
     * @param formatString
     * @param dateStr
     * @return
     */
    public static Date stringToDate(String formatString, String dateStr) {
        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatString);
        try {
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取今天的最小时间
     *
     * @param currentTime
     * @return
     */
    public static long getTodayMinTimeMillis(long currentTime) {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(new Date(currentTime));

        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH);
        int day = mCalendar.get(Calendar.DAY_OF_MONTH);

        mCalendar.set(year, month, day, 0, 0, 0);

        long minToday = mCalendar.getTimeInMillis();

        return minToday;
    }

    public static String changeTime(String time) {
        String year = time.substring(0, 4);
        String month = time.substring(4, 6);
        String day = time.substring(6, 8);
        String hour = time.substring(8, 10);
        String minute = time.substring(10, 12);
        String second = time.substring(12, 14);
        return year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
    }


    /**
     * 字符串转整数
     *
     * @param str
     * @param defValue
     * @return
     */
    public static int toInt(String str, int defValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
        }
        return defValue;
    }

    /**
     * 字符串转长整数
     *
     * @return
     */
    public static long toLong(String obj) {
        try {
            return Long.parseLong(obj);
        } catch (Exception e) {
        }
        return 0;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 限制输入框的长度
     *
     * @param maxLength
     * @param et
     */
    public static void limitEtInputLenth(int maxLength, EditText et) {
        String text = et.getText().toString();

        if (text.length() > maxLength) {
            et.setText(text.substring(0, maxLength - 1));
            et.setSelection(et.getText().length());
        }
    }

    /**
     * 一个textView设置不同字体小
     *
     * @param context
     * @param tvText
     * @param textStr
     * @param firstBegin
     * @param firstEnd
     * @param secondBegin
     * @param secondEnd
     */
    public static void setDifferentSizeText(Context context, TextView tvText, String textStr, int firstBegin, int firstEnd, int style0, int secondBegin, int secondEnd, int style1) {
        SpannableString styledText = new SpannableString(textStr);
        styledText.setSpan(new TextAppearanceSpan(context, style0), firstBegin, firstEnd,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(context, style1), secondBegin, secondEnd,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvText.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    /**
     * 每隔num个字符串换行
     *
     * @param num
     * @param str
     * @return
     */
    public static String lineBreakFormat(int num, String str) {

        String breakStr = "";
        for (int i = 0; i < str.length(); i++) {
            if (i * num + num > str.length()) {
                breakStr += str.substring(i * num, str.length());
                break;
            }
            breakStr += str.substring(i * num, i * num + num) + "\n";
        }
        if (breakStr.endsWith("\n")) {
            breakStr = breakStr.substring(0, breakStr.length() - 1);
        }

        return breakStr;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smDate 较小的时间
     * @param bDate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smDate, Date bDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long between_days = 0;
        try {
            smDate = sdf.parse(sdf.format(smDate));
            bDate = sdf.parse(sdf.format(bDate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smDate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bDate);
            long time2 = cal.getTimeInMillis();
            between_days = (time2 - time1) / (1000 * 3600 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smDate 较小的时间
     * @param bDate  较大的时间
     * @return 相差天数
     */
    public static int daysBetween(long smDate, long bDate) {
        Date sDate = new Date(smDate);
        Date bgDate = new Date(bDate);

        return daysBetween(sDate, bgDate);
    }

    public static int getStrBytes(String str) {
        int bytesNum = 0;
        if (!isEmpty(str)) {
            byte[] bytes = new byte[0];
            try {
                bytes = str.getBytes("GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            bytesNum = bytes.length;
        }

        return bytesNum;
    }

    /**
     * 非四舍五入
     *
     * @param finalMoney
     * @return
     */
    public static String processData(double finalMoney) {
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(2);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(finalMoney);
    }

    /**
     * @param finalMoney
     * @return
     */
    public static String handleData(double finalMoney, int num) {
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(num);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(finalMoney);
    }


    //    public static String data() {
    //        //方案一:
    //        (double) (Math.round(result_value * 100) / 100.0);
    //        //方案二:
    //        DecimalFormat df = new DecimalFormat("#.##");
    //        get_double = Double.ParseDouble(df.format(result_value));
    //        //方案三:
    //        get_double = Double.ParseDouble(String.format("%.2f", result_value));
    //        //方案四:
    //        BigDecimal bd = new BigDecimalresult_value();
    //        BigDecimal bd2 = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
    //        get_double = Double.ParseDouble(bd2.ToString());
    //    }

    public static String hideIdCard(String idCardNumber) {
        if (TextUtils.isEmpty(idCardNumber) || idCardNumber.length() < 18) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(idCardNumber.subSequence(0, 3));
        builder.append("***********");
        builder.append(idCardNumber.subSequence(14, 18));
        return builder.toString();
    }

    public static String hidePhone(String phone) {
        if (TextUtils.isEmpty(phone) || phone.length() < 11) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(phone.subSequence(0, 3));
        builder.append("****");
        builder.append(phone.subSequence(7, 11));
        return builder.toString();
    }


    public static boolean getPhoneNumStates(String phoneNum) {
        if (TextUtils.isEmpty(phoneNum)) {
            return false;
        } else if (phoneNum.length() > 11 || phoneNum.length() < 11) {
            return false;
        } else if (!StringUtils.matchCellNum(phoneNum)) {
            return false;
        }
        return true;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getDateNow() {
        Date newDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(newDate);
    }

    /**
     * double转String,保留小数点后两位
     *
     * @param num
     * @return
     */
    public static String doubleToString(double num) {
        //使用0.00不足位补0，#.##仅保留有效位
        return new DecimalFormat("0.00").format(num);
    }

    /**
     * 判断数组 是否 包含字符串
     *
     * @param strs
     * @param s
     * @return
     */
    public static boolean isHave(String[] strs, String s) {
        //此方法有两个参数，第一个是要查找的字符串数组，第二个是要查找的字符或字符串

        for (int i = 0; i < strs.length; i++) {
            if (strs[i].indexOf(s) != -1) {//循环查找字符串数组中的每个字符串中是否包含所有查找的内容
                return true;//查找到了就返回真，不在继续查询
            }
        }
        return false;//没找到返回false
    }

    /**
     * Object To json String
     *
     * @param obj
     * @return json String
     */
    public static String objToJsonString(Object obj) {

        // 初始化返回值
        String json = "str_empty";

        if (obj == null) {
            return json;
        }

        StringBuilder buff = new StringBuilder();
        Field[] fields = obj.getClass().getFields();
        try {
            buff.append("[");
            buff.append("{");
            int i = 0;
            for (Field field : fields) {
                if (i != 0) {
                    buff.append(",");
                }
                buff.append(field.getName());
                buff.append(":");
                buff.append("\"");
                buff.append(field.get(obj) == null ? "" : field.get(obj));
                buff.append("\"");
                i++;
            }
            buff.append("}");
            buff.append("]");
            json = buff.toString();
        } catch (Exception e) {
            throw new RuntimeException("cause:" + e.toString());
        }
        return json;
    }


    public static boolean isOnlyPointNumber(String number) {//保留两位小数正则
        Pattern pattern = Pattern.compile("^\\d+\\.?\\d{0,2}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }


}
