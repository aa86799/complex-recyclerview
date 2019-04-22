package com.stone.complexrecyclerview.complex;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * desc  : the actual RecyclerView.Adapter。 外层Adapter，-- RV#Adapter
 * author: stone
 * email : aa86799@163.com
 * time  : 14/06/2017 20 07
 */
@SuppressWarnings("all")
public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int TYPE_DEFAULT = generateKeyCode();
    private static final int TYPE_HEADER = generateKeyCode();
    private static final int TYPE_FOOTER = generateKeyCode();

    private List<IAtomAdapter> mAtomAdapters = new ArrayList<>();
    private View mHeaderView;
    private View mFooterView;

    public void setHeader(@NonNull View view) {
        mHeaderView = view;
    }

    public void setFooter(@NonNull View view) {
        mFooterView = view;
    }

    public void setAtomAdapters(IAtomAdapter... adapters) {
        mAtomAdapters = Arrays.asList(adapters);
        for (IAtomAdapter atomAdapter : mAtomAdapters) {
            atomAdapter.setParentAdapter(this);
        }
    }

    public boolean isHeader(int position) {
        int headerCount = getCount(mHeaderView);
        return headerCount != 0 && position == headerCount - 1;
    }

    public boolean isFooter(int position) {
        int footerCount = getCount(mFooterView);
        return footerCount != 0 && position == getItemCount() - footerCount;

    }

    private int getCount(View view) {
        return view == null ? 0 : 1;
    }

    @Override
    public final int getItemCount() {
        int count = getCount(mHeaderView) + getCount(mFooterView);
        if (mAtomAdapters != null) {
            for (IAtomAdapter atomAdapter : mAtomAdapters) {
                count += atomAdapter.getItemCount();
            }
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return TYPE_HEADER;
        } else if (isFooter(position)) {
            return TYPE_FOOTER;
        } else {
            ComplexPosition complexPosition = convertComplexPosition(position);
            if (complexPosition != null && mAtomAdapters != null) {
                return mAtomAdapters.get(complexPosition.getAtomAdapterIndex()).getItemViewType(complexPosition);
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = null;
        if (viewType == TYPE_HEADER) {
            viewHolder = new SimpleViewHolder(mHeaderView);
        } else if (viewType == TYPE_FOOTER) {
            viewHolder = new SimpleViewHolder(mFooterView);
        } else {
            if (mAtomAdapters != null) {
                for (IAtomAdapter<BaseViewHolder> atomAdapter : mAtomAdapters) {
                    viewHolder = atomAdapter.onCreateViewHolder(parent, viewType);//one viewType match one viewHolder
                    if (viewHolder != null) {
                        return viewHolder;
                    }
                }
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        ComplexPosition complexPosition = convertComplexPosition(position);
        if (complexPosition != null && mAtomAdapters != null) {
            mAtomAdapters.get(complexPosition.getAtomAdapterIndex()).onBindViewHolder(holder, complexPosition);
        }
    }

    public final ComplexPosition convertComplexPosition(int position) {
        if (mAtomAdapters != null) {
            int tempLastPosition = getCount(mHeaderView); //0 | 1
            IAtomAdapter atomAdapter;
            for (int i = 0, len = mAtomAdapters.size(), count; i < len; i++) {
                atomAdapter = mAtomAdapters.get(i);
                count = atomAdapter.getItemCount();
                tempLastPosition += count;
                if (tempLastPosition > position) {//match current AtomAdapter index
                    int subPosition = count - (tempLastPosition - position);
                    return new ComplexPosition(i, position, positionToSourcePosition(position),
                            subPosition, atomAdapter.subPositionToSubSourcePosition(subPosition));
                } else if (tempLastPosition == position && isFooter(position)) {
                    return new ComplexPosition(i, position, -1, -1, -1);
                }
            }
        }
        return null;
    }

    public final int positionToSourcePosition(int position) {//relative to this Adapter
        return position - getCount(mHeaderView);
    }

    public final int sourcePositionToPosition(int sourcePosition) {//relative to this Adapter
        return sourcePosition + getCount(mHeaderView);
    }

    public IAtomAdapter getAoAtomAdapter(int position) {
        ComplexPosition complexPosition = convertComplexPosition(position);
        if (complexPosition != null) {
            return mAtomAdapters.get(complexPosition.getAtomAdapterIndex());
        }
        return null;
    }

    private static int generateKeyCode() {
        return UUID.randomUUID().hashCode();
    }
}
