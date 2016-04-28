/*
 * Copyright (c) 2016 3 11.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （Fragmet--消息）
 */
package cn.xiaocool.fish.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.main.NewNoticeActivity;
import cn.xiaocool.fish.net.HttpTool;
import cn.xiaocool.fish.utils.IntentUtils;

public class NewFragment extends Fragment implements View.OnClickListener {
    private FragmentActivity mContext;
    private RelativeLayout rl_new_notice;
    private RelativeLayout rl_new_isnet;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 2:
                    rl_new_isnet.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    rl_new_isnet.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initView(); // 初始化界面
        initEvent(); // 初始化事件
    }

    private void initEvent() {
        rl_new_notice.setOnClickListener(this);
    }

    private void initView() {
        rl_new_notice = (RelativeLayout)getView().findViewById(R.id.rl_new_notice);
        rl_new_isnet = (RelativeLayout)getView().findViewById(R.id.rl_new_isnet);
        isNet();
    }

    private void isNet() {
        //线程
        new Thread() {
            public void run() {
                if (HttpTool.isConnnected(mContext)){
                    handler.sendEmptyMessage(1);
                }else {
                    handler.sendEmptyMessage(2);
                }
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_new_notice:
                IntentUtils.getIntent(mContext, NewNoticeActivity.class);
                break;
            default:
                break;
        }
    }

}
