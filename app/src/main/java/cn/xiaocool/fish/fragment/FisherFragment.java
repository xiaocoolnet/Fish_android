/*
 * Copyright (c) 2016 3 11.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （Fragmet--鱼圈）
 */
package cn.xiaocool.fish.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.main.SettingActivity;
import cn.xiaocool.fish.utils.IntentUtils;

public class FisherFragment extends Fragment implements View.OnClickListener {
    private FragmentActivity mContext;
    private RelativeLayout fisher_setting;

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
        fisher_setting.setOnClickListener(this);
    }

    private void initView() {
        fisher_setting = (RelativeLayout)getView().findViewById(R.id.fisher_setting);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fisher_setting:
                IntentUtils.getIntent(mContext, SettingActivity.class);
                break;
            default:
                break;
        }
    }

}