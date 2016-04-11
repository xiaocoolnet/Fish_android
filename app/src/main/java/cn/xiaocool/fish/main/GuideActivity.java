/*
 * Copyright (c) 2016 4 10.
 * 版权所有 Hello Fish工作室
 * 用途：社交软件
 */

package cn.xiaocool.fish.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.utils.DensityUtil;
import cn.xiaocool.fish.utils.IntentUtils;
import cn.xiaocool.fish.utils.MyConstants;
import cn.xiaocool.fish.utils.SpTools;

// 向导界面
public class GuideActivity extends Activity {
   private ViewPager vp_guids;
   private LinearLayout ll_points;
   private View v_redpoint;
   private Button bt_startExp;
   private List<ImageView> guids;
   private MyAdapter adapter;
   private int	disPoints;//点与点之间的距离

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       initView(); // 初始化界面
       initData(); // 初始化数据
       initEvent(); // 初始化事件
   }

   private void initEvent() {
       // 监听布局完成 ，触发的结果
       v_redpoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
           @Override
           public void onGlobalLayout() {
               // 取消注册 界面变化而发生的回调结果
               v_redpoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
               // 计算点与点之间的距离
               disPoints = (ll_points.getChildAt(1).getLeft() - ll_points.getChildAt(0)
                       .getLeft());
           }
       });

       //给按钮添加点击事件
       bt_startExp.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // 保存设置的状态
               SpTools.setBoolean(getApplicationContext(), MyConstants.ISSETUP, true); // 保存设置完成的状态
               IntentUtils.getIntent(GuideActivity.this, LoginActivity.class);
               finish(); // 关闭自己
           }
       });

       //给ViewPage添加页码改变的事件
       vp_guids.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageSelected(int position) {
               // 当前ViewPager显示的页码
               // 如果ViewPager滑动到第三个页码(最后一页)，显示button
               if (position == guids.size() - 1) {
                   bt_startExp.setVisibility(View.VISIBLE); // 设置设置按钮的显示
               } else {
                   bt_startExp.setVisibility(View.GONE); // 不是最后一页，隐藏该button按钮
               }
           }

           @Override
           public void onPageScrolled(int position, float positionOffset,
                                      int positionOffsetPixels) {

               //positionOffset 移动的比例值
               float leftMargin = disPoints * (position + positionOffset); // 计算红点的左边距

               //设置红点的左边距
               RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v_redpoint.getLayoutParams();
               layoutParams.leftMargin = Math.round(leftMargin); // 对float类型四舍五入
               v_redpoint.setLayoutParams(layoutParams); // 重新设置布局
           }
           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });
   }

   private void initData() {

       int[] pics = new int[] { R.drawable.login_background, R.drawable.login_background, R.drawable.login_background ,R.drawable.login_background }; // 图片的数据
       guids = new ArrayList<ImageView>(); // 定义Viewpager使用的容器
       // 初始化容器中的数据
       for (int i = 0; i < pics.length; i++) {
           ImageView iv_temp = new ImageView(getApplicationContext());
           iv_temp.setBackgroundResource(pics[i]);
           guids.add(iv_temp); // 添加界面的数据
           View v_point = new View(getApplicationContext()); // 给点的容器Linearlayout初始化添加灰色点
           v_point.setBackgroundResource(R.drawable.gray_point);
           int dip = 10;
           // 设置灰色点的大小
           LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(getApplicationContext(), dip),
                   DensityUtil.dip2px(getApplicationContext(), dip)); // 注意单位是px 不是dp

           // 设置点与点直接的空隙
           // 第一个点不需要指定
           if (i != 0)// 过滤第一个点
               params.leftMargin = 10;// px
               v_point.setLayoutParams(params);// 无缝隙的挨一起
               ll_points.addView(v_point); // 添加灰色的点到线性布局中
       }
       // 界面没有布局前，点的位置是确定不了的，布局完成，再求出点直接的距离
       adapter = new MyAdapter(); // 创建ViewPager的适配器
       vp_guids.setAdapter(adapter); // 设置适配器
   }

   private class MyAdapter extends PagerAdapter
   {

       @Override
       public int getCount() {
           return guids.size(); // 返回数据的个数
       }
       @Override
       public boolean isViewFromObject(View arg0, Object arg1) {
           return arg0 == arg1; // 过滤和缓存的作用
       }
       @Override
       public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView((View) object); // 从Viewpager中移除
       }

       @Override
       public Object instantiateItem(ViewGroup container, int position) {
           View child = guids.get(position); // 获取View
           container.addView(child); // 添加View
           return child;
       }
   }

   private void initView() {
       setContentView(R.layout.activity_guide);
       vp_guids = (ViewPager) findViewById(R.id.vp_guide_pages); // ViewPage组件
       ll_points = (LinearLayout) findViewById(R.id.ll_guide_points); // 动态加点容器
       v_redpoint = findViewById(R.id.v_guide_redpoint); // 红点
       bt_startExp = (Button) findViewById(R.id.bt_guide_startexp); // 开始体验的按钮
   }
}
