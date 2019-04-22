package com.stone.complexrecyclerview.complex;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * desc  : 原子适配器。 各种可以含有 List<>数据的 子 adapter
 * author: stone
 * email : aa86799@163.com
 * time  : 14/06/2017 17 33
 */
@SuppressWarnings("all")
public abstract class BaseAtomAdapter<T> implements IAtomAdapter<BaseViewHolder> {

    private static final int TYPE_DEFAULT = generateKeyCode();
    private final int TYPE_HEADER = generateKeyCode();//has more BaseAtomAdapter, so can not declared to static
    private final int TYPE_FOOTER = generateKeyCode();

    private List<T> mDataList;
    private RecyclerView.Adapter<BaseViewHolder> mParentAdapter;
    private View mHeaderView;
    private View mFooterView;

    public BaseAtomAdapter(List<T> dataList) {
        this.mDataList = dataList;
    }

    @Override
    public void setParentAdapter(RecyclerView.Adapter<BaseViewHolder> parentAdapter) {
        mParentAdapter = parentAdapter;
    }


    public List<T> getDataList() {
        return mDataList = mDataList == null ? new ArrayList<T>() : mDataList;
    }

    public void setDataList(List<T> dataList) {
        this.mDataList = dataList == null ? new ArrayList<T>() : dataList;
    }

    @Override
    public void setHeader(@NonNull View view) {
        mHeaderView = view;
    }

    @Override
    public void setFooter(@NonNull View view) {
        mFooterView = view;
    }

    @Override
    public boolean isHeader(int subPosition) {
        int headerCount = getCount(mHeaderView);
        return headerCount != 0 && subPosition == headerCount - 1; // true: subPosition = 0
    }

    @Override
    public boolean isFooter(int subPosition) {
        int footerCount = getCount(mFooterView);
        return footerCount != 0 && subPosition == getItemCount() - footerCount;
    }

    private int getCount(View view) {
        return null == view ? 0 : 1;
    }

    @Override
    public int getItemViewType(ComplexPosition complexPosition) {
        int subPosition = complexPosition.getSubPosition();
        if (isHeader(subPosition)) {
            return TYPE_HEADER;
        } else if (isFooter(subPosition)) {
            return TYPE_FOOTER;
        } else {
            return getSourceItemViewType(complexPosition.getSubSourcePosition());
        }
    }

    //the method should be override when this atomAdapter have more viewType
    protected int getSourceItemViewType(int subSourcePosition) {
        return TYPE_DEFAULT;
    }

    @Override
    public final BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder;
        if (viewType == TYPE_HEADER) {
            viewHolder = new SimpleViewHolder(mHeaderView);
        } else if (viewType == TYPE_FOOTER) {
            viewHolder = new SimpleViewHolder(mFooterView);
        } else {
            viewHolder = onCreateTypeViewHolder(parent, viewType);
        }
        return viewHolder;
    }

    protected abstract BaseViewHolder onCreateTypeViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(BaseViewHolder holder, ComplexPosition complexPosition) {
        holder.onBindViewHolderInternal(complexPosition);
    }

    @Override
    public int getItemCount() {
        return getSourceItemCount() + getCount(mHeaderView) + getCount(mFooterView);
    }

    public int getSourceItemCount() {
        return getDataList().size();
    }

    @Override
    public long getItemId(int subPosition) {
        return subPosition;
    }

    @Override
    public int subPositionToSubSourcePosition(int subPosition) {
        return subPosition - getCount(mHeaderView);
    }

    @Override
    public int subSourcePositionToSubPosition(int subSourcePosition) {
        return subSourcePosition + getCount(mHeaderView);
    }

    @Override
    public void notifyDataSetChanged() {
        if (null != mParentAdapter) {
            mParentAdapter.notifyDataSetChanged();
        }
    }

    public abstract Object getItem(int subSourcePosition);

    private static int generateKeyCode() {
        return UUID.randomUUID().hashCode();
    }
}
