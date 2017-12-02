package com.stone.complexrecyclerview.complex;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 14/06/2017 16 57
 */
public interface IAtomAdapter<VH extends RecyclerView.ViewHolder> {

    void setParentAdapter(RecyclerView.Adapter<VH> parentAdapter);

    void setHeader(@NonNull View view);

    void setFooter(@NonNull View view);

    boolean isHeader(int subPosition);

    boolean isFooter(int subPosition);

    int getItemViewType(ComplexPosition complexPosition);

    VH onCreateViewHolder(ViewGroup parent, int viewType);

    void onBindViewHolder(VH holder, ComplexPosition complexPosition);

    int getItemCount();

    long getItemId(int subPosition);

    int subPositionToSubSourcePosition(int subPosition);

    int subSourcePositionToSubPosition(int subSourcePosition);

    void notifyDataSetChanged();
}
