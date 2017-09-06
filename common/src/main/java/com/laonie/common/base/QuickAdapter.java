package com.laonie.common.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-08-25 16:56
 * @DESCRIPTION:
 *      RecyclerView公共适配器父类
 */

public abstract class QuickAdapter<T> extends RecyclerView.Adapter<QuickAdapter.VH>{

    /**
     * 数据源
     */
    private List<T> mDatas;

    public QuickAdapter(List<T> mDatas){
        this.mDatas = mDatas;
    }

    /**
     * 获取布局
     * @return
     */
    public abstract int getLayoutId();

    public abstract void bindViewHolder(VH viewHolder,T data,int position);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return VH.get(parent,getLayoutId());
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        bindViewHolder(holder,getItem(position),position);
    }

    @Override
    public int getItemCount() {
        return null != mDatas ? mDatas.size() : 0;
    }

    public T getItem(int position){
        return null != mDatas ? mDatas.get(position) : null;
    }

    public static class VH extends RecyclerView.ViewHolder{
        private SparseArray<View> mViews;
        private View mContentView;
        public VH(View itemView) {
            super(itemView);
            this.mContentView = itemView;
            mViews = new SparseArray<>();
        }
        public static VH get(ViewGroup parent,int layoutId) {
            View contentView = LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false);
            return new VH(contentView);
        }

        public <T extends View> T getView(int id) {
            View v = mViews.get(id);
            if (v == null) {
                v = mContentView.findViewById(id);
                mViews.put(id,v);
            }
            return (T)v;
        }
        public void setText(int id,String value) {
            TextView v = getView(id);
            v.setText(value);
        }
    }
}
