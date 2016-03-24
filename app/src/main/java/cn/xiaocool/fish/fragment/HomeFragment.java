/*
 * Copyright (c) 2016 3 11.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （Fragmet--首页）
 */
package cn.xiaocool.fish.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

import java.util.ArrayList;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.adapter.HomeImagePagerAdapter;
import cn.xiaocool.fish.main.HomeBaseWebActivity;
import cn.xiaocool.fish.service.LocationService;
import cn.xiaocool.fish.view.Indicator.CircleFlowIndicator;
import cn.xiaocool.fish.view.HomeViewFlow;

public class HomeFragment extends Fragment implements View.OnClickListener{

    private RelativeLayout rl_logo_weather, rl_logo_fishing_point, rl_logo_fishing_boat; // 天气，钓点，船钓
    private FragmentActivity mContext;

    private HomeViewFlow mViewFlow;
    private CircleFlowIndicator mFlowIndicator;
    private ArrayList<String> imageUrlList = new ArrayList<String>();
    ArrayList<String> linkUrlArray= new ArrayList<String>();
    ArrayList<String> titleList= new ArrayList<String>();
    private int mCurrPos;

    public LocationClient mLocationClient = null; // 定位
    public BDLocationListener myListener = new MyLocationListener();
    TextView getLocation ;

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
        rl_logo_weather.setOnClickListener(this);
        rl_logo_fishing_point.setOnClickListener(this);
        rl_logo_fishing_boat.setOnClickListener(this);

        AddImageGroup(); // 往数组添加图片链接索引
        StartLocation(); // 开始定位

    }

    private void initView() {
        rl_logo_weather = (RelativeLayout) getView().findViewById(R.id.rl_logo_weather);
        rl_logo_fishing_point = (RelativeLayout) getView().findViewById(R.id.rl_logo_fishing_point);
        rl_logo_fishing_boat = (RelativeLayout) getView().findViewById(R.id.rl_logo_fishing_boat);

        mViewFlow = (HomeViewFlow) getView().findViewById(R.id.viewflow);
        mFlowIndicator = (CircleFlowIndicator) getView().findViewById(R.id.viewflowindic);

        getLocation = (TextView) getView().findViewById(R.id.getLocation);
    }

    private void setView(int curr, int next) {

        View noticeView = getLayoutInflater(null).inflate(R.layout.activity_home_notice_item,
                null);
        TextView notice_tv = (TextView) noticeView.findViewById(R.id.notice_tv);
        if ((curr < next) && (next > (titleList.size() - 1))) {
            next = 0;
        } else if ((curr > next) && (next < 0)) {
            next = titleList.size() - 1;
        }
        notice_tv.setText(titleList.get(next));
        notice_tv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Bundle bundle = new Bundle();
                bundle.putString("url", linkUrlArray.get(mCurrPos));
                bundle.putString("title", titleList.get(mCurrPos));
                Intent intent = new Intent(mContext,
                        HomeBaseWebActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mCurrPos = next;
    }

    private void initBanner(ArrayList<String> imageUrlList) {
        mViewFlow.setAdapter(new HomeImagePagerAdapter(mContext, imageUrlList,
                linkUrlArray, titleList).setInfiniteLoop(true));
        mViewFlow.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
        // 我的ImageAdapter实际图片张数为3

        mViewFlow.setFlowIndicator(mFlowIndicator);
        mViewFlow.setTimeSpan(4500);
        mViewFlow.setSelection(imageUrlList.size() * 1000); // 设置初始位置
        mViewFlow.startAutoFlowTimer(); // 启动自动播放

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_logo_weather:
                Toast.makeText(mContext, "天气", Toast.LENGTH_SHORT).show();
                //IntentUtils.getIntent(mContext, WeatherActivity.class);
                break;
            case R.id.rl_logo_fishing_point:
                Toast.makeText(mContext, "钓点", Toast.LENGTH_SHORT).show();
                //IntentUtils.getIntent(mContext, FishingPointActivity.class);
                break;
            case R.id.rl_logo_fishing_boat:
                Toast.makeText(mContext, "船钓", Toast.LENGTH_SHORT).show();
                //IntentUtils.getIntent(mContext, FishingBoatActivity.class);
                break;
            default:
                break;

        }
    }

    private void AddImageGroup() {
        imageUrlList.add("http://7xrjcc.com1.z0.glb.clouddn.com/h1.png");
        imageUrlList.add("http://7xrjcc.com1.z0.glb.clouddn.com/h2.png");
        imageUrlList.add("http://7xrjcc.com1.z0.glb.clouddn.com/h3.png");
        imageUrlList.add("http://7xrjcc.com1.z0.glb.clouddn.com/h4.png");
//        linkUrlArray.add("http://blog.csdn.net/a1260157543/article/details/50853656");
//        linkUrlArray.add("http://blog.csdn.net/a1260157543/article/details/50853706");
//        linkUrlArray.add("http://blog.csdn.net/a1260157543/article/details/50853759");
//        linkUrlArray.add("http://blog.csdn.net/a1260157543/article/details/50853820");
        titleList.add("1");
        titleList.add("2");
        titleList.add("3");
        titleList.add("4");
        initBanner(imageUrlList);
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
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                //Toast.makeText(mContext,"服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因",Toast.LENGTH_SHORT).show();
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
            getLocation.setText(locate3[0]);
            //getLocation.setText(locate3[0]+"--"+getLocate);
        }
    }

    private void StartLocation() {
        LocationService locationService = new LocationService(mContext);
        locationService.registerListener(myListener);
        locationService.start();
    }

}