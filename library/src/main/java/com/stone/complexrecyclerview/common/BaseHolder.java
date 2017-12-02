package com.stone.complexrecyclerview.common;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * from http://blog.devwiki.net/index.php/2016/07/17/Recycler-View-Adapter-ViewHolder-optimized.html
 * 基础的ViewHolder</br>
 * ViewHolder只作View的缓存,不关心数据内容
 * Created by DevWiki on 2016/5/17.
 *
 * extends RecyclerView.ViewHolder
 * > 构造方法传入resId/view, 以完成 itemView的装载
 * > 内部维护一个SparseArray<View> mViewArray， 提供 T getView(viewId)
 */
public class BaseHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViewArray;

    /**
     * 构造ViewHolder  (该方法涉及到parent，不常用)
     *
     * @param parent 父类容器
     * @param resId  布局资源文件id
     */
    public BaseHolder(ViewGroup parent, @LayoutRes int resId) {
        super(LayoutInflater.from(parent.getContext()).inflate(resId, parent, false));
        mViewArray = new SparseArray<>();
    }

    /**
     * 构造ViewHolder
     *
     * @param context
     * @param resId   布局资源文件id
     */
    public BaseHolder(Context context, @LayoutRes int resId) {
        super(LayoutInflater.from(context).inflate(resId, null, false));
        mViewArray = new SparseArray<>();
    }

    /**
     * 构建ViewHolder
     *
     * @param view 布局View
     */
    public BaseHolder(View view) {
        super(view);
        mViewArray = new SparseArray<>();
    }

    /**
     * 获取布局中的View
     *
     * @param viewId view的Id
     * @param <T>    View的类型
     * @return view
     */
    public <T extends View> T getView(@IdRes int viewId) {
        View view = mViewArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViewArray.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 获取Context实例
     *
     * @return context
     */
    public Context getContext() {
        return itemView.getContext();
    }
}