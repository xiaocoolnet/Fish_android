/*
 * Copyright (c) 2016 3 11.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （Fragmet--通讯录）
 */
package cn.xiaocool.fish.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.main.AddFisherActivity;
import cn.xiaocool.fish.main.GroupActivity;
import cn.xiaocool.fish.ui.RefreshableView;
import cn.xiaocool.fish.utils.IntentUtils;

public class BookFragment extends Fragment implements View.OnClickListener {

    private FragmentActivity mContext;
    RefreshableView refreshableView;
    private RelativeLayout rl_logo_add_you;
    private RelativeLayout rl_logo_group;
    ListView listView;
    ArrayAdapter<String> adapter;
    String[] items = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initView(); // 初始化界面
        initEvent(); // 初始化事件
    }

    private void initEvent() {
        DownFresh(); // 刷新操作3秒
        rl_logo_add_you.setOnClickListener(this);
        rl_logo_group.setOnClickListener(this);
    }

    private void initView() {
        refreshableView = (RefreshableView) getView().findViewById(R.id.refreshable_view);
        listView = (ListView) getView().findViewById(R.id.list_view);
        rl_logo_add_you = (RelativeLayout) getView().findViewById(R.id.rl_logo_add_you);
        rl_logo_group = (RelativeLayout) getView().findViewById(R.id.rl_logo_group);
        adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
    }

    private void DownFresh() {
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshableView.finishRefreshing();
            }
        }, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_logo_add_you :
                IntentUtils.getIntent(mContext, AddFisherActivity.class);
                break;
            case R.id.rl_logo_group :
                IntentUtils.getIntent(mContext, GroupActivity.class);
                break;
            default:
                break;
        }
    }
}
