/*
 * Copyright (c) 2016 3 11.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （Fragmet--鱼圈）
 */
package cn.xiaocool.fish.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.main.LoginActivity;
import cn.xiaocool.fish.utils.IntentUtils;

public class FisherFragment extends Fragment implements View.OnClickListener {
    private FragmentActivity mContext;
    private RelativeLayout fisher_quit;
    private SharedPreferences sharedPreferences;

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
        fisher_quit.setOnClickListener(this);
    }

    private void initView() {
        fisher_quit = (RelativeLayout)getView().findViewById(R.id.fisher_quit);
    }


    /**
     * 退出确认对话框
     */
    private void getDialog() {
        LayoutInflater inflaterDl = LayoutInflater.from(mContext);
        RelativeLayout layout = (RelativeLayout) inflaterDl.inflate(
                R.layout.setting_dialog, null);

        // 对话框
        final Dialog dialog = new AlertDialog.Builder(mContext)
                .create();
        dialog.show();
        dialog.getWindow().setContentView(layout);

        // 取消按钮
        Button btnCancel = (Button) layout.findViewById(R.id.dialog_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // 确定按钮
        Button btnOK = (Button) layout.findViewById(R.id.dialog_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = mContext.getSharedPreferences("user_id", mContext.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("user_id");
                editor.commit();// 提交修改
                IntentUtils.getIntent(mContext, LoginActivity.class);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fisher_quit:
                getDialog();
                break;
            default:
                break;
        }
    }

}