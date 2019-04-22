package com.stone.complexrecyclerview.complex;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 14/06/2017 17 34
 */
@SuppressWarnings("all")
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    private ComplexPosition mComplexPosition;

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public BaseViewHolder(ViewGroup parent, @LayoutRes int resId) {
        super(LayoutInflater.from(parent.getContext()).inflate(resId, parent, false));
    }

    public BaseViewHolder(Context context, @LayoutRes int resId) {
        super(LayoutInflater.from(context).inflate(resId, null, false));
    }

    public ComplexPosition getComplexPosition() {
        return mComplexPosition;
    }

    final void onBindViewHolderInternal(ComplexPosition complexPosition) {
        this.mComplexPosition = complexPosition;
        onBindViewHolder(complexPosition);
    }

    public abstract void onBindViewHolder(ComplexPosition complexPosition);


    @SuppressWarnings("unchecked")
    protected  <T> T getViewById(@IdRes int resId) {
        return (T) itemView.findViewById(resId);
    }
}
