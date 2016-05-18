/*
 * Copyright (c) 2016 3 8.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （主页Activity实现四个Fragmet）
 */
package cn.xiaocool.fish.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;

import cn.xiaocool.fish.BaseActivity;
import cn.xiaocool.fish.R;
import cn.xiaocool.fish.fragment.FisherFragment;
import cn.xiaocool.fish.fragment.NewFragment;
import cn.xiaocool.fish.fragment.BookFragment;
import cn.xiaocool.fish.fragment.HomeFragment;
import cn.xiaocool.fish.net.HttpTool;
import cn.xiaocool.fish.net.constant.NetBaseConstant;
import cn.xiaocool.fish.utils.IntentUtils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends BaseActivity {
    private boolean isAppExit; // 退出app标志位
    private Button[] mTabs;
    private SharedPreferences sharedPreferences;
    private Fragment[] fragments; // 定义Fragment数组
    private int index; // 索引空间(4)
    private HomeFragment homeFragment;
    private NewFragment newFragment;
    private BookFragment bookFragment;
    private FisherFragment fisherFragment;
    private int currentTabIndex; // 当前Fragment的索引
    private String result_data;
    private String notice_title_1,notice_content_1;
    private String notice_title_2,notice_content_2;
    private String notice_title_3,notice_content_3;
    private String notice_title_4,notice_content_4;


    Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {

            switch (msg.what) {
                case 2:
                    break;
                case 0:
                    try {
                        JSONObject jsonObject = new JSONObject(result_data);
                        String data = jsonObject.getString("data");
                        JSONArray notice_data = new JSONArray(data);

                        JSONObject notice_data_1 = notice_data.getJSONObject(0);
                        notice_title_1 = notice_data_1.getString("title");
                        notice_content_1 = notice_data_1.getString("content");

                        JSONObject notice_data_2 = notice_data.getJSONObject(1);
                        notice_title_2 = notice_data_2.getString("title");
                        notice_content_2 = notice_data_2.getString("content");

                        JSONObject notice_data_3 = notice_data.getJSONObject(2);
                        notice_title_3 = notice_data_3.getString("title");
                        notice_content_3 = notice_data_3.getString("content");

                        JSONObject notice_data_4 = notice_data.getJSONObject(3);
                        notice_title_4 = notice_data_4.getString("title");
                        notice_content_4 = notice_data_4.getString("content");

                        sharedPreferences = getSharedPreferences("home_notice", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("notice_title_1", notice_title_1);
                        editor.putString("notice_title_2", notice_title_2);
                        editor.putString("notice_title_3", notice_title_3);
                        editor.putString("notice_title_4", notice_title_4);
                        editor.putString("notice_content_1", notice_content_1);
                        editor.putString("notice_content_2", notice_content_2);
                        editor.putString("notice_content_3", notice_content_3);
                        editor.putString("notice_content_4", notice_content_4);
                        editor.commit();// 提交修改

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    isAppExit = false;
                    break;
                default:
                    break;
            }

        };

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(); // 初始化界面
        initEvent(); // 初始化事件
        SaveData();
    }

    private void initEvent() {
        isHomeTabSeclect(); // 默认第一个tab设为选中状态
        isShowFragment(); // 显示哪个Fragment
    }

    private void SaveData() {

    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        setContentView(R.layout.activity_home);
        mTabs = new Button[4];
        mTabs[0] = (Button) findViewById(R.id.btn_home);
        mTabs[1] = (Button) findViewById(R.id.btn_new);
        mTabs[2] = (Button) findViewById(R.id.btn_book);
        mTabs[3] = (Button) findViewById(R.id.btn_fisher);

        homeFragment = new HomeFragment();
        newFragment = new NewFragment();
        bookFragment = new BookFragment();
        fisherFragment = new FisherFragment();

        getNotice();
    }



    private void getNotice() {
        new Thread() {
            public void run() {
                if (HttpTool.isConnnected(MainActivity.this)){
                    result_data = HttpTool.HomeNotice(NetBaseConstant.Token);
                    handler.sendEmptyMessage(0); // 调用服务器登录函数
                }else {
                    handler.sendEmptyMessage(2); // 输出：网络连接有问题！
                }
            }
        }.start();
    }


    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_home:
                index = 0;
                break;
            case R.id.btn_new:
                index = 1;
                break;
            case R.id.btn_book:
                index = 2;
                break;
            case R.id.btn_fisher:
                //ifUserNull(); // 判断用户名是否为空
                index = 3;
                break;

        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }

    private void ifUserNull() {
        SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
        String getuser = user.getString("userphone", "");
        if(getuser==null || getuser.isEmpty() ){
            IntentUtils.getIntent(MainActivity.this, LoginActivity.class); // 跳转到首页
        }else{
            index = 3;
        }
    }

    private void isHomeTabSeclect() {
        mTabs[0].setSelected(true);
        registerForContextMenu(mTabs[0]);
    }


    private void isShowFragment() {
        fragments = new Fragment[] {homeFragment,newFragment,bookFragment,fisherFragment};
        //添加显示第一个fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homeFragment)
                .add(R.id.fragment_container, newFragment).hide(newFragment).show(homeFragment).commit();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            AppExit();
            return false;
        } /*else if (keyCode == KeyEvent.KEYCODE_HOME) {
			return false;
		}*/ else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void AppExit() {
        if (!isAppExit) {
            isAppExit = true;
            Toast.makeText(MainActivity.this, "再点击退出APP", Toast.LENGTH_LONG)
                    .show();
            handler.sendEmptyMessageDelayed(1, 2000);

        } else {// 2s内再次按back时,isExit= true，执行以下操作，app退出
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);

        }

    }

}