package com.stone.complexrecyclerview.example.complex;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stone.complexrecyclerview.common.ItemClickSupport;
import com.stone.complexrecyclerview.complex.BaseAtomAdapter;
import com.stone.complexrecyclerview.complex.BaseRecyclerAdapter;
import com.stone.complexrecyclerview.complex.BaseViewHolder;
import com.stone.complexrecyclerview.complex.ComplexPosition;
import com.stone.complexrecyclerview.example.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * desc  :
 * author: stone
 * email : aa86799@163.com
 * time  : 14/06/2017 15 54
 */
public class MainActivity extends Activity {

    private RecyclerView mRecyclerView;
    private BaseRecyclerAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_rv);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mAdapter = new BaseRecyclerAdapter() {};
        mAdapter.setHeader(getView(R.layout.adapter_header));
        mAdapter.setFooter(getView(R.layout.adapter_footer));

        Adapter1 adapter1 = new Adapter1(generateList("stone", 3));
        adapter1.setHeader(getView(R.layout.adapter_one_header));
        adapter1.setFooter(getView(R.layout.adapter_one_footer));

        Adapter2 adapter2 = new Adapter2(generateList("admin", 5));
        adapter2.setHeader(getView(R.layout.adapter_two_header));
        adapter2.setFooter(getView(R.layout.adapter_two_footer));

        mAdapter.setAtomAdapters(adapter1, adapter2);
        mRecyclerView.setAdapter(mAdapter);


        ItemClickSupport itemClickSupport = ItemClickSupport.addTo(mRecyclerView);
        itemClickSupport.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                ComplexPosition complexPosition = mAdapter.convertComplexPosition(position);
                System.out.println("stone--> " + complexPosition);
            }
        });

    }

    private View getView(int resId) {
        return LayoutInflater.from(this).inflate(resId, mRecyclerView, false);
    }

    private List<String> generateList(String label, int count) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(label + " ---- " + i);
        }
        return list;
    }

    private static class Adapter1 extends BaseAtomAdapter<String> {
        private static final int TYPE_ONE_A = MainActivity.generateKeyCode();
        private static final int TYPE_ONE_B = MainActivity.generateKeyCode();

        public Adapter1(List<String> dataList) {
            super(dataList);
        }

        @Override
        protected int getSourceItemViewType(int subSourcePosition) {
            if (subSourcePosition < 2) {
                return TYPE_ONE_A;
            } else {
                return TYPE_ONE_B;
            }
//            return super.getSourceItemViewType(subSourcePosition);
        }

        @Override
        protected BaseViewHolder onCreateTypeViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ONE_A) {
                return new ViewHolderOneA(parent, this);
            } else if (viewType == TYPE_ONE_B) {
                return new ViewHolderOneB(parent, this);
            } else {
                return null;
            }
        }

        @Override
        public Object getItem(int subSourcePosition) {
            return getDataList().get(subSourcePosition);
        }
    }

    private static class ViewHolderOneA extends BaseViewHolder {
        private Adapter1 adapter;

        public ViewHolderOneA(ViewGroup parent, Adapter1 adapter) {
            super(parent, R.layout.adapter_one_item_a);
            this.adapter = adapter;
        }

        @Override
        public void onBindViewHolder(ComplexPosition complexPosition) {
            int subSourcePosition = complexPosition.getSubSourcePosition();
            TextView tv = getViewById(R.id.adapter_one_item_a_title_tv);
            tv.setText(adapter.getItem(subSourcePosition).toString());
        }
    }

    private static class ViewHolderOneB extends BaseViewHolder {
        private Adapter1 adapter;

        public ViewHolderOneB(ViewGroup parent, Adapter1 adapter) {
            super(parent, R.layout.adapter_one_item_b);
            this.adapter = adapter;
        }

        @Override
        public void onBindViewHolder(ComplexPosition complexPosition) {
            int subSourcePosition = complexPosition.getSubSourcePosition();
            TextView tv = getViewById(R.id.adapter_one_item_b_title_tv);
            tv.setText(adapter.getItem(subSourcePosition).toString());
        }
    }

    private static class Adapter2 extends BaseAtomAdapter<String> {
        private static final int TYPE_TWO_A = MainActivity.generateKeyCode();
        private static final int TYPE_TWO_B = MainActivity.generateKeyCode();

        public Adapter2(List<String> dataList) {
            super(dataList);
        }

        @Override
        protected int getSourceItemViewType(int subSourcePosition) {
            if (subSourcePosition < 3) {
                return TYPE_TWO_A;
            } else {
                return TYPE_TWO_B;
            }
//            return super.getSourceItemViewType(subSourcePosition);
        }

        @Override
        protected BaseViewHolder onCreateTypeViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_TWO_A) {
                return new ViewHolderTwoA(parent, this);
            } else if (viewType == TYPE_TWO_B) {
                return new ViewHolderTwoB(parent, this);
            } else {
                return null;
            }
        }

        @Override
        public Object getItem(int subSourcePosition) {
            return getDataList().get(subSourcePosition);
        }
    }

    private static class ViewHolderTwoA extends BaseViewHolder {
        private Adapter2 adapter;

        public ViewHolderTwoA(ViewGroup parent, Adapter2 adapter) {
            super(parent, R.layout.adapter_two_item_a);
            this.adapter = adapter;
        }

        @Override
        public void onBindViewHolder(ComplexPosition complexPosition) {
            int subSourcePosition = complexPosition.getSubSourcePosition();
            TextView tv = getViewById(R.id.adapter_two_item_a_title_tv);
            tv.setText(adapter.getItem(subSourcePosition).toString());
        }
    }

    private static class ViewHolderTwoB extends BaseViewHolder {
        private Adapter2 adapter;

        public ViewHolderTwoB(ViewGroup parent, Adapter2 adapter) {
            super(parent, R.layout.adapter_two_item_b);
            this.adapter = adapter;
        }

        @Override
        public void onBindViewHolder(ComplexPosition complexPosition) {
            int subSourcePosition = complexPosition.getSubSourcePosition();
            TextView tv = getViewById(R.id.adapter_two_item_b_title_tv);
            tv.setText(adapter.getItem(subSourcePosition).toString());
        }
    }

    private static int generateKeyCode() {
        return UUID.randomUUID().hashCode();
    }
}
