package com.laonie.commonlibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ScrollView;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-09-07 14:58
 * @DESCRIPTION:
 */

public class FlexibleScrollView extends ScrollView {
    private static int mMaxOverDistance = 50;
    public FlexibleScrollView(Context context) {
        this(context,null);
    }

    public FlexibleScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlexibleScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context mContext) {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        float density = metrics.density;
        mMaxOverDistance = (int) (density * mMaxOverDistance);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxOverDistance, isTouchEvent);
    }
}
