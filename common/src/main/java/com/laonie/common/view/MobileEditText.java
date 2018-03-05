package com.laonie.common.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import com.laonie.common.util.StringUtils;

/**
 * @AUTHOR：niejq@1dian.la
 * @DATETIME：2016 12 14 16:14
 * @DESC：手机号码输入文本框
 */
public class MobileEditText extends CleanableEditText{
    private MyTextWatcher myTextWatcher;
    private boolean isDel;
    public MobileEditText(Context context) {
        super(context);
    }

    public MobileEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setListener();
    }

    public MobileEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setListener();
    }

    private void setListener(){
        myTextWatcher = new MyTextWatcher();
        MobileEditText.this.addTextChangedListener(myTextWatcher);
        this.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    isDel = true;
                }
                return false;
            }
        });
    }

    /**
     * 获取手机号码（去除空格）
     * @return
     */
    public String getMobile(){
        String s = getText().toString();
        return StringUtils.placeMobileSpace(s);
    }

    public class MyTextWatcher implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable sb) {
            String input = MobileEditText.this.getText().toString();
            if (StringUtils.isEmpty(input)) return;
            int selectionEnd = MobileEditText.this.getSelectionEnd();
            int selection = selectionEnd;
            StringBuilder s = new StringBuilder(sb.toString().replace(" ",""));
            if (s.length() > 3) {//号码前三位第一个空格
                s.insert(3, " ");
                if(!isDel){
                    //普通情况光标加1,
                    if(selection == 4)
                    selection++;
                }else {
                    if (selection == 4) {//光标在空格后
                        //点删除情况光标减1,
                        selection--;
                    }
                }
            }
            if (s.length() > 8 ) {//号码前7位第二个空格
                s.insert(8, " ");
                if(!isDel){
                    if (selection == selectionEnd)
                        if(selection == 9)
                        selection++;
                }else {
                    if (selection == 9) {
                        if (selection == selectionEnd) {
                            selection--;
                        }
                    }
                }
            }
            isDel = false;
            MobileEditText.this.removeTextChangedListener(myTextWatcher);
            setText(s);
            if(selection > s.length()){
                selection = s.length();
            }
            //Logger.e(MobileEditText.class.getSimpleName(),s.toString()+";selectionEnd:"+selectionEnd+",selection:"+selection);
            MobileEditText.this.setSelection(selection);
            MobileEditText.this.addTextChangedListener(myTextWatcher);
            if (isHasFocus) {
                boolean isVisible = getText().toString().length() >= 1;
                setClearDrawableVisible(isVisible);
            } else {
                setClearDrawableVisible(false);
            }
        }
    }
}
