package com.laonie.common.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.laonie.common.R;
import com.laonie.common.util.PreferenceHelper;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2018-03-02 17:14
 * @DESCRIPTION:
 *          空布局，当列表数据为空时显示的布局
 */

public class EmptyView extends RelativeLayout {
    private static final String WINDOW_HEIGHT_KEY = "window_height";
    private TextView emptyTv;
    private ImageView emptyIv;

    public EmptyView(Context context,int windowHeight) {
        this(context,null,0);
        PreferenceHelper.setValue(WINDOW_HEIGHT_KEY,windowHeight);
    }


    public EmptyView(Context context) {
        this(context,null,0);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        View rootView = LayoutInflater.from(context).inflate(R.layout.view_data_empty,null);
        emptyIv = rootView.findViewById(R.id.empty_view_iv);
        emptyTv = rootView.findViewById(R.id.empty_view_tv);
        int windowHeight = PreferenceHelper.getInt(WINDOW_HEIGHT_KEY);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                windowHeight);
        addView(rootView,layoutParams);
    }

    public void setEmptyText(String text) {
        if (null != emptyTv) {
            emptyTv.setText(text);
        }
    }

    public void setEmptyImage(int res){
        if (null != emptyIv) {
            emptyIv.setBackgroundResource(res);
        }
    }
}
