/*
 * Copyright (c) 2016 3 11.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （Fragmet--首页）
 */
package cn.xiaocool.fish.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.adapter.HomeNoticeAdapter;
import cn.xiaocool.fish.main.BoatFishActivity;
import cn.xiaocool.fish.main.FishPointActivity;
import cn.xiaocool.fish.main.HomeNoticeActivity;
import cn.xiaocool.fish.main.UserActivity;
import cn.xiaocool.fish.main.WeatherActivity;
import cn.xiaocool.fish.service.LocationService;
import cn.xiaocool.fish.utils.IntentUtils;
import cn.xiaocool.fish.utils.ToastUtils;
import cn.xiaocool.fish.view.HomeGalleryView;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private RelativeLayout rl_logo_weather, rl_logo_fishing_point, rl_logo_fishing_boat; // 天气，钓点，船钓
    private FragmentActivity mContext;
    private TextView tvTitle;
    private HomeGalleryView gallery;
    private HomeNoticeAdapter adapter;
    private ImageView iv_slidingmunu_btn;

    private Handler h = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:
                    ToastUtils.ToastShort(mContext, "网络连接成功");
                    break;
                case 2:
                    ToastUtils.ToastShort(mContext, "网络问题，请稍后重试！");
                    break;
                default:
                    break;
            }
        }

    };

    public BDLocationListener myListener = new MyLocationListener();
    TextView getLocation ;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            this.update();
            handler.postDelayed(this, 1000*8);// 间隔8秒
        }
        void update() {
            // 刷新msg的内容
            StartLocation(); // 开始定位
            getLocate(); // 获得位置
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        initView(); // 初始化界面
        initEvent(); // 初始化事件
    }

    private void initEvent() {
        iv_slidingmunu_btn.setOnClickListener(this);
        handler.postDelayed(runnable, 1000);
        rl_logo_weather.setOnClickListener(this);
        rl_logo_fishing_point.setOnClickListener(this);
        rl_logo_fishing_boat.setOnClickListener(this);
        getLocation.setOnClickListener(this);
        ImageNotice();
    }

    private void initView() {
        rl_logo_weather = (RelativeLayout) getView().findViewById(R.id.rl_logo_weather);
        rl_logo_fishing_point = (RelativeLayout) getView().findViewById(R.id.rl_logo_fishing_point);
        rl_logo_fishing_boat = (RelativeLayout) getView().findViewById(R.id.rl_logo_fishing_boat);
        iv_slidingmunu_btn = (ImageView) getView().findViewById(R.id.iv_slidingmunu_btn);

        getLocation = (TextView) getView().findViewById(R.id.getLocation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_logo_weather :
                IntentUtils.getIntent(mContext, WeatherActivity.class);
                break;
            case R.id.rl_logo_fishing_point :
                IntentUtils.getIntent(mContext, FishPointActivity.class);
                break;
            case R.id.rl_logo_fishing_boat :
                IntentUtils.getIntent(mContext, BoatFishActivity.class);
                break;
            case R.id.getLocation :
                //IntentUtils.getIntent(mContext, LocationActivity.class);
            case R.id.iv_slidingmunu_btn :
                IntentUtils.getIntent(mContext, UserActivity.class);
            default:
                break;
        }
    }


    // 定位监听器
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            StringBuffer sb = new StringBuffer(256);
            sb.append(location.getAddrStr());
            if (location.getLocType()== BDLocation.TypeGpsLocation) {
                //Toast.makeText(mContext,"gps定位成功",Toast.LENGTH_SHORT).show(); //不知道怎么让它只显示一次:现在就是每3秒刷新一次显示一次Toast
            }else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                //Toast.makeText(mContext,"网络定位成功",Toast.LENGTH_SHORT).show();
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                //Toast.makeText(mContext,"离线定位成功，离线定位结果也是有效的",Toast.LENGTH_SHORT).show();
            } else if (location.getLocType() == BDLocation.TypeServerError) {Toast.makeText(mContext,"服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因",Toast.LENGTH_SHORT).show();
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                //Toast.makeText(mContext,"网络不同导致定位失败，请检查网络是否通畅",Toast.LENGTH_SHORT).show();
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                //Toast.makeText(mContext,"无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机",Toast.LENGTH_SHORT).show();
            }
            String getLocate=sb.toString(); // 待处理
            String [] locate1 = null;
            locate1 = getLocate.split("省");
            String locate2 = locate1[1];
            String [] locate3 = null;
            locate3 = locate2.split("市");
            //getLocation.setText(locate3[0]);
            //getLocation.setText(locate3[0]+"--"+getLocate);

            // 将用户名保存到user.xml文件中
            Context locateCtx = mContext; // 获取SharedPreferences对象
            SharedPreferences sp = locateCtx.getSharedPreferences("locate", mContext.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit(); // 存入数据
            editor.putString("userLocate", locate3[0]);
            editor.commit();
            return;
        }
    }

    private void StartLocation() {
        LocationService locationService = new LocationService(mContext);
        locationService.registerListener(myListener);
        locationService.start();
        return;
    }

    private void getLocate() {
        SharedPreferences locate = getActivity().getSharedPreferences("locate", mContext.MODE_PRIVATE);
        String getlocate = locate.getString("userLocate", "");
        getLocation.setText(getlocate);
        return;
    }

    private void ImageNotice(){
        tvTitle = (TextView) getActivity().findViewById(R.id.tvTitle);
        gallery = (HomeGalleryView) getActivity().findViewById(R.id.mygallery);

        adapter = new HomeNoticeAdapter(mContext);
        adapter.createReflectedImages();
        gallery.setAdapter(adapter);

        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvTitle.setText(adapter.titles[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() { // 设置点击事件监听
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "第" + (position + 1) + "条公告信息", Toast.LENGTH_SHORT).show();
                if (position == 2) {
                    IntentUtils.getIntent(mContext, HomeNoticeActivity.class);
                }
            }
        });
    }

}