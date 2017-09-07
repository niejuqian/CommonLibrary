/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.laonie.common.view.refresh;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.laonie.common.R;
import com.laonie.common.util.UIUtils;

// TODO: Auto-generated Javadoc

/**
 * © 2012 amsoft.cn
 * 名称：AbListViewHeader.java
 * 描述：下拉刷新的Header View类.
 *
 * @author 还如一梦中
 * @version v1.0
 * @date：2013-01-17 下午11:52:13
 */
public class AbListViewHeaderMine extends LinearLayout {

	/** 上下文. */
	private Context mContext;

	/** 主View. */
	private LinearLayout headerView;

	/** 进度图标View. */
	private ProgressBar headerProgressBar;

	/** 当前状态. */
	private int mState = -1;

	/** 显示 下拉刷新. */
	public final static int STATE_NORMAL = 0;

	/** 显示 松开刷新. */
	public final static int STATE_READY = 1;

	/** 显示 正在刷新.... */
	public final static int STATE_REFRESHING = 2;

	/**  Header的高度. */
	private int headerHeight;

	/**
	 * 初始化Header.
	 *
	 * @param context the context
	 */
	public AbListViewHeaderMine(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * 初始化Header.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public AbListViewHeaderMine(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * 初始化View.
	 *
	 * @param context the context
	 */
	private void initView(Context context) {

		mContext  = context;

		//顶部刷新栏整体内容
		headerView = new LinearLayout(context);
		headerView.setOrientation(LinearLayout.HORIZONTAL);
		headerView.setGravity(Gravity.CENTER);

		UIUtils.setPadding(headerView, 0, 10, 0, 10);

		//显示箭头与进度
		FrameLayout headImage =  new FrameLayout(context);

		//style="?android:attr/progressBarStyleSmall" 默认的样式
		headerProgressBar = new ProgressBar(context,null,android.R.attr.progressBarStyle);
		headerProgressBar.setIndeterminateDrawable(mContext.getResources().getDrawable(R.drawable.refresh_anim_mine));

		LayoutParams layoutParamsWW = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWW.gravity = Gravity.CENTER;
		layoutParamsWW.width = UIUtils.scaleValue(mContext, 60);
		layoutParamsWW.height = UIUtils.scaleValue(mContext, 61);
		headImage.addView(headerProgressBar,layoutParamsWW);

		LayoutParams layoutParamsWW3 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParamsWW3.gravity = Gravity.CENTER;
		layoutParamsWW3.rightMargin = UIUtils.scaleValue(mContext, 10);

		LinearLayout headerLayout = new LinearLayout(context);
		headerLayout.setOrientation(LinearLayout.HORIZONTAL);
		headerLayout.setGravity(Gravity.CENTER);

		headerLayout.addView(headImage,layoutParamsWW3);

		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.BOTTOM;
		//添加大布局
		headerView.addView(headerLayout,lp);

		this.addView(headerView,lp);
		//获取View的高度
		UIUtils.measureView(this);
		headerHeight = this.getMeasuredHeight();

		setState(STATE_NORMAL);
	}

	/**
	 * 设置状态.
	 *
	 * @param state the new_icon state
	 */
	public void setState(int state) {
		if (state == mState) return ;

		if (state == STATE_REFRESHING) {
			headerProgressBar.setVisibility(View.VISIBLE);
		}
		mState = state;
	}

	/**
	 * 设置header可见的高度.
	 *
	 * @param height the new_icon visiable height
	 */
	public void setVisiableHeight(int height) {
		if (height < 0) height = 0;
		LayoutParams lp = (LayoutParams) headerView.getLayoutParams();
		lp.height = height;
		headerView.setLayoutParams(lp);
	}

	/**
	 * 获取header可见的高度.
	 *
	 * @return the visiable height
	 */
	public int getVisiableHeight() {
		LayoutParams lp = (LayoutParams)headerView.getLayoutParams();
		return lp.height;
	}

	/**
	 * 描述：获取HeaderView.
	 *
	 * @return the header view
	 */
	public LinearLayout getHeaderView() {
		return headerView;
	}

	/**
	 * 获取header的高度.
	 *
	 * @return 高度
	 */
	public int getHeaderHeight() {
		return headerHeight;
	}

	/**
	 * 描述：设置背景颜色.
	 *
	 * @param color the new_icon background color
	 */
	public void setBackgroundColor(int color){
		headerView.setBackgroundColor(color);
	}

	/**
	 * 描述：获取Header ProgressBar，用于设置自定义样式.
	 *
	 * @return the header progress bar
	 */
	public ProgressBar getHeaderProgressBar() {
		return headerProgressBar;
	}

	/**
	 * 描述：设置Header ProgressBar样式.
	 *
	 * @param indeterminateDrawable the new_icon header progress bar drawable
	 */
	public void setHeaderProgressBarDrawable(Drawable indeterminateDrawable) {
		headerProgressBar.setIndeterminateDrawable(indeterminateDrawable);
	}

	/**
	 * 描述：得到当前状态.
	 *
	 * @return the state
	 */
	public int getState(){
		return mState;
	}
}
