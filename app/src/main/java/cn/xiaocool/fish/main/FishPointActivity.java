/*
 * Copyright (c) 2016 3 30.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （钓点Activity）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.net.HttpTool;
import cn.xiaocool.fish.net.constant.NetBaseConstant;
import cn.xiaocool.fish.utils.IntentUtils;
import cn.xiaocool.fish.view.LoadingDialog;

public class FishPointActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页
    private LinearLayout ll_net_error;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 2:
                    ll_net_error.setVisibility(View.VISIBLE);
                    Toast.makeText(FishPointActivity.this, "网络问题，请稍后重试！", 0).show();
                    break;
                case 1:
                    ll_net_error.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(); // 初始化界面
        initEvent(); // 初始化事件
    }

//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus) {
//        LoadingDialog dialog = new LoadingDialog(this);
//            dialog.show();
//        }
//    }

    private void initEvent() {
        // 添加点击事件
        btn_exit.setOnClickListener(this);
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        setContentView(R.layout.activity_fishpoint); // 钓点界面
        // 控件实例化
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        ll_net_error = (LinearLayout) findViewById(R.id.ll_net_error);
    }

    private void isNet() {
        //线程
        new Thread() {
            public void run() {
                if (HttpTool.isConnnected(FishPointActivity.this)){
                    handler.sendEmptyMessage(1);
                }else {
                    handler.sendEmptyMessage(2); // 输出：网络连接有问题！
                }
            }
        }.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit :
                finish();
            default:
                break;
        }
    }

}
