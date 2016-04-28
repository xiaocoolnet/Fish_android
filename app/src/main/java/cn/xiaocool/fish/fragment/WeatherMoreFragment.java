package cn.xiaocool.fish.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.adapter.DataLunar;
import cn.xiaocool.fish.main.WeatherActivity;
import cn.xiaocool.fish.net.HttpTool;
import cn.xiaocool.fish.utils.IntentUtils;
import cn.xiaocool.fish.view.LoadingDialog;

public class WeatherMoreFragment extends Fragment implements View.OnClickListener {

	private TextView tv_date_1,tv_date_2,tv_weather,tv_wendu,tv_wind,tv_shidu,tv_rise;
	private String result_data;
	private String key;

	final static String chineseNumber[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
	static SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private FragmentActivity mContext;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
				case 2:
					Toast.makeText(mContext,"网络问题，请稍后重试！",0).show();
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

						String daily_forecast = ojArrayOne.getString("daily_forecast");
						JSONArray daily = new JSONArray(daily_forecast);
						JSONObject one_daily = daily.getJSONObject(0);
						String one_date = one_daily.getString("date");
						String astro = one_daily.getString("astro");
						JSONObject astroob = new JSONObject(astro);
						String sr = astroob.getString("sr");
						String ss = astroob.getString("ss");
						Calendar today = Calendar.getInstance();
						try {
							today.setTime(chineseDateFormat.parse(one_date));
						} catch (ParseException e) {
							e.printStackTrace();
						}
						DataLunar lunar = new DataLunar( today );
//                        System.out.println("北京时间："+chineseDateFormat.format(today.getTime())+"农历" + lunar);
						tv_date_1.setText("北京时间："+chineseDateFormat.format(today.getTime()));
						tv_date_2.setText("农历：" +lunar);
						tv_weather.setText("天气情况："+txt);
						tv_wendu.setText("温度："+fl+"℃");
						tv_wind.setText("风力风向："+dir+" "+sc);
						tv_shidu.setText("湿度："+hum+"%");
						tv_rise.setText("日落日出："+sr+"/"+ss);
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
		return inflater.inflate(R.layout.fargment_weather_more, container, false);
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
		tv_date_1.setOnClickListener(this);
	}

	private void initView() {
		// 控件实例化
		tv_date_1 = (TextView) getView().findViewById(R.id.tv_date_1);
		tv_date_2 = (TextView) getView().findViewById(R.id.tv_date_2);
		tv_weather = (TextView) getView().findViewById(R.id.tv_weather);
		tv_wendu = (TextView) getView().findViewById(R.id.tv_wendu);
		tv_wind = (TextView) getView().findViewById(R.id.tv_wind);
		tv_shidu = (TextView) getView().findViewById(R.id.tv_shidu);
		tv_rise = (TextView) getView().findViewById(R.id.tv_rise);
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
			case R.id.tv_date_1 :
				IntentUtils.getIntent(mContext, WeatherActivity.class);
				break;
			default:
				break;
		}
	}


}