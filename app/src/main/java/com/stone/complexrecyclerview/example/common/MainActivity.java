package com.stone.complexrecyclerview.example.common;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.stone.complexrecyclerview.common.BaseAdapter;
import com.stone.complexrecyclerview.common.BaseHolder;
import com.stone.complexrecyclerview.HeaderAndFooterWrapper;
import com.stone.complexrecyclerview.example.R;

import java.util.ArrayList;
import java.util.List;

/**
 * desc     :
 * author   : stone
 * homepage : http://stone86.top
 * email    : aa86799@163.com
 * time     : 01/12/2017 21 21
 */
public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private HeaderAndFooterWrapper<String> mWrapperAdapter;
    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_main_rv);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        mAdapter = new MyAdapter(this);
//        mAdapter.addHeaderView(getView(R.layout.adapter_two_header));
//        mAdapter.addFooterView(getView(R.layout.adapter_two_footer));
//        mRecyclerView.setAdapter(mAdapter);

        for (int i = 0; i < 10; i++) {
            mList.add("中华人民共和国" + i);
        }
        mAdapter.appendList(mList);

        //使用装饰类 添加 header和footer
        mWrapperAdapter = new HeaderAndFooterWrapper<>(mAdapter);
        mWrapperAdapter.addHeaderView(getView(R.layout.adapter_two_header));
        mWrapperAdapter.addFootView(getView(R.layout.adapter_st_footer));
        mWrapperAdapter.addFootView(getView(R.layout.refresh_layout));

        LinearInterpolator linearInterpolator = new LinearInterpolator();
        Animation rotateAnimation = new RotateAnimation(
                0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setInterpolator(linearInterpolator);
        rotateAnimation.setDuration(150);
        rotateAnimation.setFillAfter(true);
//        mWrapperAdapter.setHeaderAnimation(rotateAnimation);

        Animation resetRotateAnimation = new RotateAnimation(
                360, 0, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        resetRotateAnimation.setInterpolator(linearInterpolator);
        resetRotateAnimation.setDuration(150);
        resetRotateAnimation.setFillAfter(true);
//        mWrapperAdapter.setFooterAnimation(resetRotateAnimation);

        mRecyclerView.setAdapter(mWrapperAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                View childView=mRecyclerView.getChildAt(0);
                int top=childView.getTop();
                int topEdge=mRecyclerView.getPaddingTop();
                    System.out.println("top=" + top + ",,topedge=" + topEdge);
                if(top>=topEdge){
                    //子view完全显示
                }
                System.out.println(linearLayoutManager.getItemCount());
                System.out.println(mAdapter.getItemCount());
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private View getView(int resId) {
        return LayoutInflater.from(this).inflate(resId, mRecyclerView, false);
    }

    private class MyAdapter extends BaseAdapter<String, MyViewHolder> {

        public MyAdapter(Context context) {
            super(context);
        }

        public MyAdapter(Context context, List<String> list) {
            super(context, list);

        }

        @Override
        public MyViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
//            return new MyViewHolder(parent, R.layout.adapter_one_item_a);
            return new MyViewHolder(getView(R.layout.adapter_one_item_a));
        }

        @Override
        public void bindCustomViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(mList.get(position - getHeaderExtraViewCount()));
        }

        @Override
        public int getCustomViewType(int position) {
            return 0;
        }
    }

    private class MyViewHolder extends BaseHolder {
        private TextView tv;

        public MyViewHolder(ViewGroup parent, int resId) {
            super(parent, resId);
            tv = getView(R.id.adapter_one_item_a_title_tv);
        }

        public MyViewHolder(View view) {
            super(view);
            tv = getView(R.id.adapter_one_item_a_title_tv);
        }

    }
}
