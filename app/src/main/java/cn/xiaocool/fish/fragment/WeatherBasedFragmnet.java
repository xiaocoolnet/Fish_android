package cn.xiaocool.fish.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.adapter.DataLunar;
import cn.xiaocool.fish.main.LoginActivity;
import cn.xiaocool.fish.main.WeatherBaseOneActivity;
import cn.xiaocool.fish.net.HttpTool;
import cn.xiaocool.fish.utils.IntentUtils;
import cn.xiaocool.fish.view.LoadingDialog;

public class WeatherBasedFragmnet extends Fragment implements View.OnClickListener {

	final static String chineseNumber[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
	static SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private FragmentActivity mContext;
	private LinearLayout ll_weather_one;
	private ImageView iv_base_fx;
	private ImageView iv_base_jy;
	private ImageView iv_base_cx;
	private TextView tv_base_qw;
	private TextView tv_base_qwh;
	private TextView tv_base_fx;
	private TextView tv_base_j;
	private TextView tv_base_jy;
	private TextView tv_base_cx;
	private String key;
	private String result_data,result;
	private Handler handler = new Handler() {
	public void handleMessage(android.os.Message msg) {
		switch (msg.what) {
			case 2:
				Toast.makeText(mContext, "网络问题，请稍后重试！", 0).show();
				break;
			case 4:
				try {
					JSONObject jsonObject = new JSONObject(result);
				} catch (JSONException e) {
					e.printStackTrace();
				}

				break;
			case 0:
				Toast.makeText(mContext, "请打开定位权限", 0).show();
				LoadingDialog dialog = new LoadingDialog(mContext);
				dialog.show();
				break;
			case 3:
				try {
					JSONObject jsonObject = new JSONObject(result_data);
					String data = jsonObject.getString("HeWeather data service 3.0");
					JSONArray ojArray = new JSONArray(data);
					JSONObject ojArrayOne = ojArray.getJSONObject(0);

					String nowOb = ojArrayOne.getString("now");
					JSONObject ob2 = new JSONObject(nowOb);
					String cond = ob2.getString("cond");
					String hum = ob2.getString("hum");
					String fl = ob2.getString("fl");
					JSONObject condob = new JSONObject(cond);
					String txt = condob.getString("txt");

					String wind = ob2.getString("wind");
					JSONObject ob3 = new JSONObject(wind);
					String dir = ob3.getString("dir");
					String sc = ob3.getString("sc");
					String spd = ob3.getString("spd");

					String daily_forecast = ojArrayOne.getString("daily_forecast");
					JSONArray daily = new JSONArray(daily_forecast);
					JSONObject one_daily = daily.getJSONObject(0);
					String one_date = one_daily.getString("date");
					String astro = one_daily.getString("astro");
					JSONObject astroob = new JSONObject(astro);
					String sr = astroob.getString("sr");
					String ss = astroob.getString("ss");
//					Calendar today = Calendar.getInstance();
//					try {
//						today.setTime(chineseDateFormat.parse(one_date));
//					} catch (ParseException e) {
//						e.printStackTrace();
//					}
//					DataLunar lunar = new DataLunar( today );
//                        System.out.println("北京时间："+chineseDateFormat.format(today.getTime())+"农历" + lunar);
//					tv_date_1.setText("北京时间："+chineseDateFormat.format(today.getTime()));
//					tv_date_2.setText("农历：" +lunar);
//					tv_weather.setText("天气情况："+txt);
//					tv_wendu.setText("温度："+fl+"℃");
//					tv_wind.setText("风力风向："+dir+" "+sc);
//					tv_shidu.setText("湿度："+hum+"%");
//					tv_rise.setText("日落日出："+sr+"/"+ss);
					tv_base_qw.setText(txt);
					tv_base_qwh.setText(fl+"℃");
					int jy = Integer.parseInt(fl);
					tv_base_fx.setText(dir);
					tv_base_j.setText(sc+"级"+"  "+spd+"Kmph");

					if (jy>=13 && jy<=20){
//						iv_base_fx.setImageResource(R.drawable.weather_mk_fxtb_db);
						tv_base_jy.setText("适合钓鱼！");
					}else if(jy>=30 && jy<=5){
//						iv_base_fx.setImageResource(R.drawable.weather_mk_fxtb_d);
						tv_base_jy.setText("不适合钓鱼！");
					} else {
//						iv_base_fx.setImageResource(R.drawable.weather_mk_fxtb_db);
						tv_base_jy.setText("可以选择去或不去！");
					}

					if (dir.equals("东北风")){
						iv_base_fx.setImageResource(R.drawable.weather_mk_fxtb_db);
					}else if(dir.equals("东风")){
						iv_base_fx.setImageResource(R.drawable.weather_mk_fxtb_d);
					}else if(dir.equals("西风")){
						iv_base_fx.setImageResource(R.drawable.weather_mk_fxtb_x);
					} else if(dir.equals("南风")){
						iv_base_fx.setImageResource(R.drawable.weather_mk_fxtb_n);
					}else if(dir.equals("北风")){
						iv_base_fx.setImageResource(R.drawable.weather_mk_fxtb_b);
					}else if(dir.equals("东南风")){
						iv_base_fx.setImageResource(R.drawable.weather_mk_fxtb_dn);
					}else if(dir.equals("西北风")){
						iv_base_fx.setImageResource(R.drawable.weather_mk_fxtb_xb);
					}else if(dir.equals("西南风")){
						iv_base_fx.setImageResource(R.drawable.weather_mk_fxtb_xn);
					} else {
						iv_base_fx.setImageResource(R.drawable.weather_mk_fxtb_db);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
		}
	}

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_weather_based, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mContext = getActivity();
		initView(); // 初始化界面
		initEvent(); // 初始化事件
	}

	private void initEvent() {
		// 添加点击事件
		ll_weather_one.setOnClickListener(this);
	}


	private void initView() {
		ll_weather_one = (LinearLayout) getView().findViewById(R.id.ll_weather_one);
		tv_base_qw = (TextView) getView().findViewById(R.id.tv_base_qw);
		tv_base_qwh = (TextView) getView().findViewById(R.id.tv_base_qwh);
		tv_base_fx = (TextView) getView().findViewById(R.id.tv_base_fx);
		tv_base_j = (TextView) getView().findViewById(R.id.tv_base_j);
		tv_base_jy = (TextView) getView().findViewById(R.id.tv_base_jy);
		tv_base_cx = (TextView) getView().findViewById(R.id.tv_base_cx);
		iv_base_fx = (ImageView) getView().findViewById(R.id.iv_base_fx);
		iv_base_jy = (ImageView) getView().findViewById(R.id.iv_base_jy);
		iv_base_cx = (ImageView) getView().findViewById(R.id.iv_base_cx);
		getWeatherBase();
		getCX();
	}

	private void getCX() {
		new Thread() {
			public void run() {
				if (HttpTool.isConnnected(mContext)){
					result = HttpTool.WeatherCX();
					handler.sendEmptyMessage(4);// 调用服务器登录函数
				}else {
					handler.sendEmptyMessage(2); // 输出：网络连接有问题！
				}
			}
		}.start();
	}

	private void getWeatherBase() {
		new Thread() {
			public void run() {
				if (HttpTool.isConnnected(mContext)){
					SharedPreferences locate = mContext.getSharedPreferences("locate", mContext.MODE_PRIVATE);
					String getlocate = locate.getString("userLocate", "");
					if(getlocate.equals("")||getlocate==null){
						getlocate="烟台";
						handler.sendEmptyMessage(0);// 提示
					}
					key = "1058a995f42145d7b9b7208dafe23720";
					result_data = HttpTool.WeatherAPI(getlocate, key);
					handler.sendEmptyMessage(3);// 调用服务器登录函数
				}else {
					handler.sendEmptyMessage(2); // 输出：网络连接有问题！
				}
			}
		}.start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.ll_weather_one :
				IntentUtils.getIntent(mContext, WeatherBaseOneActivity.class);
				break;
			default:
				break;
		}

	}
}