package com.laonie.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.laonie.common.R;
import com.laonie.common.util.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Aaron
 */
public class MoneyEditText extends CleanableEditText {


    private MyTextWatcher myTextWatcher;
    //输入的最大金额
    private static BigDecimal MAX_VALUE = new BigDecimal("99999999");
    //小数点后的位数
    private static final int POINTER_LENGTH = 2;
    private static final String POINTER = ".";
    private static final String ZERO = "0";

    public MoneyEditText(Context context) {
        super(context);
    }

    public MoneyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributeSet(attrs);
        init();
    }

    public MoneyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributeSet(attrs);
        init();
    }

    private void initAttributeSet(AttributeSet attr) {
        if (null != attr) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attr, R.styleable.money_edit_text_element);
            String max = typedArray.getString(R.styleable.money_edit_text_element_max_value);
            if (!StringUtils.isEmpty(max)) {
                MAX_VALUE = new BigDecimal(max);
            }
        }
    }


    private void init() {
        InputFilter[] filters = this.getFilters();
        InputFilter[] inputFilters = Arrays.copyOf(filters, filters.length + 1);
        inputFilters[inputFilters.length - 1] = new MoneyInputFilter();
        this.setFilters(inputFilters);
        // TODO: 2016/12/4 先输入0 后输入数字的处理等 OnTextChanged
        myTextWatcher = new MyTextWatcher();
        this.addTextChangedListener(myTextWatcher);
    }

    public class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String input = MoneyEditText.this.getText().toString();
            if (TextUtils.isEmpty(input)) return;
            if (input.length() ==2) {
                char[] chars = input.toCharArray();
                String first = String.valueOf(chars[0]);
                String second = String.valueOf(chars[1]);
                if (POINTER.equals(first)) {
                    //如果第一位是. 则前面补充0
                    MoneyEditText.this.setText(new StringBuilder(ZERO).append(POINTER).append(String.valueOf(chars[1])).toString());
                } else if (ZERO.equals(first) && !POINTER.equals(second)) {
                    //如果第一位是0，且第二位不是. 则将0删除
                    MoneyEditText.this.setText(String.valueOf(chars[1]));
                }
                setSelection(MoneyEditText.this.getText().toString().length());
            }
        }
    }


    private class MoneyInputFilter implements InputFilter {
        Pattern mPattern;

        public MoneyInputFilter() {
            mPattern = Pattern.compile("([0-9]|\\.)*");
        }

        /**
         * @param source 新输入的字符串
         * @param start  新输入的字符串起始下标，一般为0
         * @param end    新输入的字符串终点下标，一般为source长度-1
         * @param dest   输入之前文本框内容
         * @param dstart 原内容起始坐标，一般为0
         * @param dend   原内容终点坐标，一般为dest长度 -1
         * @return 处理后的输入内容
         */
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            String sourceText = source.toString();
            String destText = dest.toString();

            //验证删除等按键
            if (TextUtils.isEmpty(sourceText)) {
                return "";
            }
            Matcher matcher = mPattern.matcher(source);
            //非数字或小数点
            if(!matcher.matches()){
                return "";
            }

            //首位不能是小数点
            if ((POINTER.equals(source)) && TextUtils.isEmpty(destText)) {
                return ".";
            }

            //只能有一个小数点
            if(destText.contains(POINTER) && POINTER.equals(sourceText)){
                return "";
            }

            //小数点不能超过2位
            if(destText.contains(POINTER)){
                //验证小数点精度，保证小数点后只能输入两位
                int index = destText.indexOf(POINTER);
                int length = destText.length() - index;
                //如果长度大于2，并且新插入字符在小数点之后
                if (length > POINTER_LENGTH && index < dstart) {
                    //超出2位返回null
                    return "";
                }
            }
            //总输入不能超出8个字符
            if (destText.length() >=8) {
                return "";
            }
            //验证输入金额的大小
            //double sumText = Double.parseDouble(destText + sourceText);
            BigDecimal sum = new BigDecimal(destText + sourceText);
            if (sum.compareTo(MAX_VALUE) > 0) {
                return dest.subSequence(dstart, dend);
            }
            return dest.subSequence(dstart, dend) + sourceText;
        }
    }
}
