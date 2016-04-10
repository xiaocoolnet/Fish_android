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
import android.widget.TextView;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.main.LoginActivity;
import cn.xiaocool.fish.utils.IntentUtils;

public class FisherFragment extends Fragment implements View.OnClickListener {
    private FragmentActivity mContext;
    private TextView tv_get_user_name;
    private RelativeLayout fisher_quit;

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
        tv_get_user_name = (TextView) getView().findViewById(R.id.tv_get_user_name);
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
               IntentUtils.getIntent(mContext, LoginActivity.class);
////				user.clearData(mContext);
//                user.clearDataExceptPhone(mContext);
//                SharedPreferences.Editor e = sp.edit();
//                LogUtils.e("删除前", e.toString());
//                e.clear();
//                e.commit();
//                WxtApplication.UID = 0;
//                LogUtils.e("删除后", e.toString());
//                IntentUtils
//                        .getIntent(mContext, LoginActivity.class);
//                mContext.finish();
//                ExitApplication.getInstance().exit();
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