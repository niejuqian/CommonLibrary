package com.laonie.common.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.laonie.common.R;
import com.laonie.common.network.callback.Callback;
import com.laonie.common.util.StringUtils;

public abstract class TitleBarActivity extends BaseAppCompatActivity {

    private TextView titleView;
    private ImageButton backBtn;
    private TextView backTv;
    protected Toolbar toolbar;
    public TextView rightText;
    private LinearLayout back_ll;
    private LinearLayout ll_tab;
    private TextView leftTv;
    private TextView rightTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        LayoutInflater from = LayoutInflater.from(this);
        ViewGroup rootView = (ViewGroup) from.inflate(R.layout.title_screen_simple, null);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        View inflate = from.inflate(layoutResID, null);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        rootView.addView(inflate, layoutParams);
        setSupportActionBar(toolbar);
        super.setContentView(rootView);
        toolbar.setTitle("");
        initTitleView(rootView);
        initTitlebar();
    }

    /**
     * 获取标题栏高度
     * @return
     */
    protected void getToolBarHeight(Callback<Integer> callback){
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                callback.call(toolbar.getHeight());
            }
        });
    }

    /**
     * 对titlebar进行设置
     */
    public void initTitlebar(){
        titleView.setText(initToolbarTitle());
        //设置返回键
        if (null != back_ll && back_ll.getVisibility() == View.VISIBLE){
            back_ll.setOnClickListener(view -> finish());
        }
    }

    protected abstract String initToolbarTitle();

    protected void initTitleView(View parentView) {
        titleView = (TextView) parentView.findViewById(R.id.titleView);
        backBtn = (ImageButton) parentView.findViewById(R.id.back);
        backTv = (TextView) parentView.findViewById(R.id.tv_back_tx);
        rightText = (TextView) parentView.findViewById(R.id.right_text_tv);
        back_ll = (LinearLayout) parentView.findViewById(R.id.back_ll);
        ll_tab = (LinearLayout) parentView.findViewById(R.id.ll_tab);
        leftTv = (TextView) parentView.findViewById(R.id.tv_left);
        rightTv = (TextView) parentView.findViewById(R.id.tv_right);
    }

    /**
     * 设置返回键文本内容
     * @param backTvText
     */
    public void setBackTvText(String backTvText){
        if (StringUtils.isEmpty(backTvText)) {
            backTvText = "";
        }
        backTv.setText(backTvText);
    }

    protected void setBackClickListener(View.OnClickListener listener) {
        back_ll.setOnClickListener(listener);
    }

    protected void setRightTextClickListener(View.OnClickListener listener) {
        if (null != rightText)
            rightText.setOnClickListener(listener);
    }


    protected void setTitle(String text) {
        if (StringUtils.isEmpty(text)) {
            text = "";
        }
        titleView.setText(text);
    }

    protected void setTabTitle(String leftText, String rightText) {
        if (StringUtils.isEmpty(leftText)) {
            leftText = "";
        }
        if (StringUtils.isEmpty(rightText)) {
            rightText = "";
        }
        leftTv.setText(leftText);
        rightTv.setText(rightText);
    }

    protected void showTabView() {
        titleView.setVisibility(View.GONE);
        ll_tab.setVisibility(View.VISIBLE);
    }

    protected void setRightText(String text) {
        if (null == rightText) return;
        rightText.setText(text);
        if (!TextUtils.isEmpty(text)) {
            rightText.setVisibility(View.VISIBLE);
        } else {
            rightText.setVisibility(View.GONE);
        }
    }

    protected String getRightText() {
        if (null == rightText) return "";
        return rightText.getText().toString();
    }

    protected ImageView getBackBtn() {
        return backBtn;
    }

    /**
     * 设置头部颜色
     */
    protected void setToolBarBackgroundColor(@ColorInt int color) {
        toolbar.setBackgroundColor(color);
//        getWindow().getDecorView().setBackgroundColor(color);
        //getSupportActionBar().getCustomView().setAlpha(0);
    }

    protected void setBackIcon(@DrawableRes int resId) {
        backBtn.setImageResource(resId);
    }

    protected void setBackBtnVisible(int visible) {
        backBtn.setVisibility(visible);
    }

    public void setBackVisibility(int visibility) {
        back_ll.setVisibility(visibility);
    }
}
