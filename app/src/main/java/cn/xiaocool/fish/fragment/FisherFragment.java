/*
 * Copyright (c) 2016 3 11.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （Fragmet--鱼圈）
 */
package cn.xiaocool.fish.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.view.HomeViewFlow;
import cn.xiaocool.fish.view.Indicator.CircleFlowIndicator;

public class FisherFragment extends Fragment {

    private FragmentActivity mContext;
    private TextView tv_get_user_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fisher, container, false);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initView(); // 初始化界面
        initEvent(); // 初始化事件
    }

    private void initEvent() {
        getUser(); // 获取用户名
    }

    private void initView() {
        tv_get_user_name = (TextView) getView().findViewById(R.id.tv_get_user_name);
    }


    private void getUser() {
        SharedPreferences user = getActivity().getSharedPreferences("user", mContext.MODE_PRIVATE);
        String getuser = user.getString("userphone", "");
        tv_get_user_name.setText(getuser);
    }

}