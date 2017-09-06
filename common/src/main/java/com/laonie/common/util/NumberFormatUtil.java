package com.laonie.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-09-06 17:55
 * @DESCRIPTION:
 *      格式化帮助类
 */

public class NumberFormatUtil {
    private static DecimalFormat mDecimalFormat = new DecimalFormat("#0.00");
    private static DecimalFormat _mDecimalFormat = new DecimalFormat("#0.0");
    public static DecimalFormat doubleFormat = new DecimalFormat("#,##0.00");
    public static DecimalFormat doubleFourFormat = new DecimalFormat("#,##0.0000");
    public static DecimalFormat intFormat = new DecimalFormat("#,###");
    public static DecimalFormat doubleDealZeroFormat = new DecimalFormat("#,###.##");
    public static DecimalFormat doubleDealZeroFormat1 = new DecimalFormat("###.##");
    private static final String MONEY_MAKER = "￥";

    public static double format(double number) {
        BigDecimal _number = new BigDecimal(number);
        return _number.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String formatToStr(double number) {
        BigDecimal _number = new BigDecimal(number);
        return formatDouble(_number.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public static String formatToString(double number) {
        return mDecimalFormat.format(number) + "";
    }

    public static String _formatToString(double number) {
        return _mDecimalFormat.format(number) + "";
    }

    public static String formatToString(String number){
        BigDecimal _number = new BigDecimal(number);
        return _number.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"";
    }



    /**
     * 比较两个双精度包装类型值的大小
     * @param d1
     * @param d2
     * @return
     */
    public static boolean compareDouble(Double d1, Double d2){
        try{
            if (null == d1) {
                return d2 == null;
            }
            if (null == d2){
                return false;
            }
            BigDecimal bd1 = new BigDecimal(d1+"");
            BigDecimal bd2 = new BigDecimal(d2+"");
            int re = bd1.compareTo(bd2);
            return (re == 0);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 格式化Double类型数据
     * 例：12545.12格式化为 12,545.12
     * @param d
     * @return
     */
    public static String formatDouble(Double d){
        if (null != d){
            return doubleFormat.format(d);
        }
        return "0.00";
    }


    /**
     * 格式化Double类型数据
     * 例：12545.12格式化为 12,545.12
     * @param d
     * @return
     */
    public static String formatBigDecimal(BigDecimal d){
        if (null != d){
            d = d.setScale(2,BigDecimal.ROUND_DOWN);
            return doubleFormat.format(d);
        }
        return "0.00";
    }

    /**
     * 格式化金钱类型
     * 例：12545.12格式化为 ￥12,545.12
     * @param d
     * @return
     */
    public static String formatBigDecimalToMoney(BigDecimal d){
        if (null != d){
            d = d.setScale(2,BigDecimal.ROUND_DOWN);
            return MONEY_MAKER+doubleFormat.format(d);
        }
        return MONEY_MAKER+"0.00";
    }

    /**
     * 格式化金钱类型
     * 例：12545.12格式化为 ￥12,545.12
     * @param d
     * @return
     */
    public static String formatBigDecimalToMoney(BigDecimal d,int scale,DecimalFormat decimalFormat){
        if (null == decimalFormat) {
            decimalFormat = doubleFormat;
        }
        if (null != d){
            d = d.setScale(scale,BigDecimal.ROUND_DOWN);
            return MONEY_MAKER+decimalFormat.format(d);
        }
        return MONEY_MAKER+"0.00";
    }
    /**
     * 格式化Double类型数据
     * 例：12545.12格式化为 12,545.12
     * @param d
     * @return
     */
    public static String formatBigDecimal(BigDecimal d,DecimalFormat decimalFormat){
        if (null == decimalFormat) {
            decimalFormat = doubleFormat;
        }
        if (null != d){
            d = d.setScale(2,BigDecimal.ROUND_DOWN);
            return decimalFormat.format(d);
        }
        return "0.00";
    }

    /**
     * 格式化Double类型数据
     * 例：12545.12格式化为 12,545.12
     * @param d
     * @return
     */
    public static String formatBigDecimal(BigDecimal d,DecimalFormat decimalFormat,int scale){
        if (null == decimalFormat) {
            decimalFormat = doubleFormat;
        }
        if (null != d){
            d = d.setScale(scale,BigDecimal.ROUND_DOWN);
            return decimalFormat.format(d);
        }
        return "0.00";
    }

    public static String formatBigDecimal(BigDecimal d, DecimalFormat decimalFormat,int scale,int roundingMode) {
        if (null == decimalFormat) {
            decimalFormat = doubleFormat;
        }
        if (null != d){
            d = d.setScale(scale,roundingMode);
            return decimalFormat.format(d);
        }
        return "0.00";
    }

    /**
     * 格式化Double类型数据
     * 例：12545.12格式化为 12,545.12
     * @param d
     * @param scale 精度
     * @return
     */
    public static String formatBigDecimal(BigDecimal d,int scale){
        if (null != d){
            d = d.setScale(scale,BigDecimal.ROUND_DOWN);
            return doubleFormat.format(d);
        }
        return "0.00";
    }

    /**
     * 格式化Double类型数据
     * 例：12545.12格式化为 12,545.12
     * @param d
     * @return
     */
    public static String formatBigDecimalFour(BigDecimal d){
        if (null != d){
            d = d.setScale(4,BigDecimal.ROUND_DOWN);
            return doubleFourFormat.format(d);
        }
        return "0.0000";
    }

    /**
     * 格式化Double类型数据
     * 例：12545.12格式化为 12,545.12
     * @param d
     * @return
     */
    public static String formatBigDecimalFourToMoney(BigDecimal d){
        if (null != d){
            d = d.setScale(4,BigDecimal.ROUND_DOWN);
            return MONEY_MAKER + doubleFourFormat.format(d);
        }
        return MONEY_MAKER + "0.0000";
    }

    /**
     * 格式化金额，向上取整
     * @param value
     * @return
     */
    public static String ceilingBigDecimal(String value) {
        try {
            if (StringUtils.isNotEmpty(value)) {
                BigDecimal d = new BigDecimal(value);
                if (null != d){
                    d = d.setScale(2,BigDecimal.ROUND_CEILING);
                    return mDecimalFormat.format(d);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "0.00";
    }

    /**
     *  截取
     * @param value
     * @return
     */
    public static String truncBigdecimal(String value){
        try {
            if (StringUtils.isNotEmpty(value)) {
                BigDecimal d = new BigDecimal(value);
                if (null != d){
                    d = d.setScale(2,BigDecimal.ROUND_DOWN);
                    return mDecimalFormat.format(d);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "0.00";
    }

    /**
     * 格式化Double类型数据
     * 例：12545.12格式化为 12,545.12
     * @param d
     * @return
     */
    public static String formatFloat(Float d){
        if (null != d){
            return doubleFormat.format(d);
        }
        return "0.00";
    }

    /**
     * 格式化Integer类型数据
     * 例：12546212格式化为 12,546,212
     * @param i
     * @return
     */
    public static String formatInteger(Integer i){
        if (null != i){
            return intFormat.format(i);
        }
        return "0";
    }

    /**
     * 去除金额字符串中多余的0
     * <p>
     *     1. 00010.010 -> 10.01
     *     2.1001.00 -> 1001
     * </p>
     * @param money
     * @return
     */
    public static String deleteZero(String money) {
        if (StringUtils.isEmpty(money)) return null;
        try {
            return new BigDecimal(money).stripTrailingZeros().toPlainString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
