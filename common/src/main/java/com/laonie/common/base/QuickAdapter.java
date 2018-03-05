package com.laonie.common.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.laonie.common.listener.ItemClickListener;
import com.laonie.common.view.EmptyViewHolder;

import java.util.List;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2017-08-25 16:56
 * @DESCRIPTION:
 *      RecyclerView公共适配器父类
 */

public abstract class QuickAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    /**
     * 数据源
     */
    private List<T> mDatas;

    public QuickAdapter(List<T> mDatas){
        this.mDatas = mDatas;
    }

    /**
     * 列表item点击事件
     */
    private ItemClickListener<T> itemClickListener;

    private View emptyView;
    private static final int EMPTY_VIEW = 1;

    /**
     * 获取布局
     * @return
     */
    public abstract int getLayoutId();

    public abstract void bindViewHolder(VH viewHolder,T data,int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == EMPTY_VIEW && emptyView != null) {
            return new EmptyViewHolder(emptyView);
        } else {
            return VH.get(parent,getLayoutId());
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VH) {
            VH vh = (VH) holder;
            T data = getItem(position);
            if (null == data) return;
            bindViewHolder(vh,data,position);
        }
    }

    @Override
    public int getItemCount() {
        return null != mDatas && mDatas.size() > 0 ? mDatas.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        return null == mDatas || mDatas.size() == 0 ? EMPTY_VIEW : super.getItemViewType(position);
    }

    public T getItem(int position){
        return null != mDatas && mDatas.size() > 0 ? mDatas.get(position) : null;
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    public void setItemClickListener(ItemClickListener<T> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    protected void onItemClick(T data) {
        if (null != itemClickListener) {
            itemClickListener.itemClick(data);
        }
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
