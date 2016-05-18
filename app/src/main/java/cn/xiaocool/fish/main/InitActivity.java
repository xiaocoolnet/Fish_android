/*
 * Copyright (c) 2016 4 20.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （设置Activity）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.DemoHXSDKHelper;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.dao.DataCleanManager;
import cn.xiaocool.fish.utils.IntentUtils;

public class InitActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页
    private RelativeLayout fisher_quit;
    private RelativeLayout rl_fisher_bbs;
    private RelativeLayout rl_fisher_clear;
    private RelativeLayout rl_fisher_newts;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(); // 初始化界面
        initEvent(); // 初始化事件
    }

    private void initEvent() {
        // 添加点击事件
        fisher_quit.setOnClickListener(this);
        rl_fisher_bbs.setOnClickListener(this);
        rl_fisher_clear.setOnClickListener(this);
        rl_fisher_newts.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        setContentView(R.layout.activity_fisher_setting);
        // 控件实例化
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        fisher_quit = (RelativeLayout)findViewById(R.id.fisher_quit);
        rl_fisher_clear = (RelativeLayout)findViewById(R.id.rl_fisher_clear);
        rl_fisher_bbs = (RelativeLayout)findViewById(R.id.rl_fisher_bbs);
        rl_fisher_newts = (RelativeLayout)findViewById(R.id.rl_fisher_newts);
    }

    /**
     * 退出确认对话框
     */
    private void getDialog() {
        LayoutInflater inflaterDl = LayoutInflater.from(InitActivity.this);
        RelativeLayout layout = (RelativeLayout) inflaterDl.inflate(
                R.layout.setting_dialog, null);

        // 对话框
        final Dialog dialog = new AlertDialog.Builder(InitActivity.this)
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
                sharedPreferences = getSharedPreferences("user_id", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("user_id");
                editor.commit();// 提交修改
                logout();
                IntentUtils.getIntent(InitActivity.this, LoginActivity.class);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit :
                finish();
                break;
            case R.id.fisher_quit:
                getDialog();
                break;
            case R.id.rl_fisher_clear:
                DataCleanManager.cleanInternalCache(InitActivity.this);
                DataCleanManager.cleanDatabases(InitActivity.this);
                Delay(1000);
                Toast.makeText(InitActivity.this,"清除缓存成功",0).show();
                break;
            case R.id.rl_fisher_bbs:
                IntentUtils.getIntent(InitActivity.this, FeedBackActivity.class);
                break;
            case R.id.rl_fisher_newts:
                IntentUtils.getIntent(InitActivity.this, NewTestActivity.class);
                break;
            default:
                break;
        }
    }

    private void Delay(int ms){
        try {
            Thread.currentThread();
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void logout() {
        final ProgressDialog pd = new ProgressDialog(InitActivity.this);
        String st = getResources().getString(R.string.Are_logged_out);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        DemoHXSDKHelper.getInstance().logout(true,new EMCallBack() {

            @Override
            public void onSuccess() {
                InitActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                InitActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        pd.dismiss();
                    }
                });
            }
        });
    }

}
