/*
 * Copyright (c) 2016 3 8.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （主页Activity实现四个Fragmet）
 */
package cn.xiaocool.fish.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;

import cn.xiaocool.fish.BaseActivity;
import cn.xiaocool.fish.R;
import cn.xiaocool.fish.fragment.FisherFragment;
import cn.xiaocool.fish.fragment.NewFragment;
import cn.xiaocool.fish.fragment.BookFragment;
import cn.xiaocool.fish.fragment.HomeFragment;
import cn.xiaocool.fish.utils.IntentUtils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends BaseActivity {
    private Button[] mTabs;
    private Fragment[] fragments; // 定义Fragment数组
    private int index; // 索引空间(4)
    private HomeFragment homeFragment;
    private NewFragment newFragment;
    private BookFragment bookFragment;
    private FisherFragment fisherFragment;
    private int currentTabIndex; // 当前Fragment的索引

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(); // 初始化界面
        initEvent(); // 初始化事件
    }

    private void initEvent() {
        isHomeTabSeclect(); // 默认第一个tab设为选中状态
        isShowFragment(); // 显示哪个Fragment
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
                ifUserNull(); // 判断用户名是否为空
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

}