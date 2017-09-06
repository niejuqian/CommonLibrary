package com.laonie.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * @AUTHOR：聂久乾
 * @DATETIME：2016年11月18日 09时34分
 * @GROUP：银河战队
 * @COPYRIGHT：深圳世纪银河网络发展有限公司
 * @DESC：
 */
public abstract class BaseFragment extends Fragment {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    protected static String TAG;

    protected Activity mActivity; // 把fragment绑定到哪个Activity上, 上下文对象就是那个Activity.
    protected int mLayoutID;
    protected View rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    public void onCreate(Bundle savedInstanceState, int layoutId) {
        super.onCreate(savedInstanceState);
        this.mLayoutID = layoutId;
        TAG = this.getClass().getSimpleName();
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commitAllowingStateLoss();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(mLayoutID, container, false);
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setView();
        initView();
        initData();
        setListener();
    }

    protected void setView(){}

    protected void setListener(){}

    protected abstract void initData();

    public abstract void initView();

    public View findViewById(int layoutId) {
        return rootView.findViewById(layoutId);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 刷新数据
     */
    public void refresh() {

    }


    public void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    protected void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(mActivity,cls));
    }

    protected void startActivityForResult(Class<? extends Activity> cls,int code) {
        startActivityForResult(new Intent(mActivity,cls),code);
    }

}
