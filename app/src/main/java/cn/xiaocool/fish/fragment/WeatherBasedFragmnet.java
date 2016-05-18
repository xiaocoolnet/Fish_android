package cn.xiaocool.fish.fragment;

import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import cn.xiaocool.fish.main.WeatherBaseOneActivity;
import cn.xiaocool.fish.main.WeatherBaseTwoActivity;
import cn.xiaocool.fish.net.HttpTool;
import cn.xiaocool.fish.utils.IntentUtils;
import cn.xiaocool.fish.view.LoadingDialog;

public class WeatherBasedFragmnet extends Fragment implements View.OnClickListener {

	private FragmentActivity mContext;
	private LinearLayout ll_weather_one,ll_weather_two;
	private LinearLayout ll_weather_base;
	private ImageView iv_base_fx;
	private ImageView iv_base_jy;
	private ImageView iv_line;
	private TextView tv_base_qw;
	private TextView tv_base_qwh_max;
	private TextView tv_base_qwh_min;
	private TextView tv_base_fx;
	private TextView tv_base_j;
	private TextView tv_base_jy;
	private String key;
	private String result_data,result;
	private Handler handler = new Handler() {
	public void handleMessage(android.os.Message msg) {
		switch (msg.what) {
			case 2:
				Toast.makeText(mContext, "网络问题，请稍后重试！", 0).show();
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
					String astro = one_daily.getString("astro");
					JSONObject astroob = new JSONObject(astro);

					String tmp = one_daily.getString("tmp");
					JSONObject tep1 = new JSONObject(tmp);
					String max = tep1.getString("max");
					String min = tep1.getString("min");

					ll_weather_base.setVisibility(View.VISIBLE);
					tv_base_qw.setText(txt);
					tv_base_qwh_max.setText(max+"℃");
					tv_base_qwh_min.setText(min+"℃");
					Typeface typeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/st.TTF");
					tv_base_qwh_max.setTypeface(typeFace);
					tv_base_qwh_min.setTypeface(typeFace);
					iv_line.setImageResource(R.drawable.weather_line);
					int jy = Integer.parseInt(fl);
					tv_base_fx.setText(dir);
					tv_base_j.setText(sc+"级"+"  "+spd+" Km/h");
					tv_base_fx.setTypeface(typeFace);
					tv_base_j.setTypeface(typeFace);

					if (jy>=13 && jy<=20){
						iv_base_jy.setImageResource(R.drawable.jy);
						tv_base_jy.setText("口很好，不钓鱼可惜了");
					}else if(jy>=30 && jy<=5){
						iv_base_jy.setImageResource(R.drawable.jy);
						tv_base_jy.setText("没口，不建议钓鱼");
					} else {
						iv_base_jy.setImageResource(R.drawable.jy);
						tv_base_jy.setText("有口，适合钓鱼");
					}
					tv_base_jy.setTypeface(typeFace);

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
		ll_weather_two.setOnClickListener(this);
	}


	private void initView() {
		ll_weather_one = (LinearLayout) getView().findViewById(R.id.ll_weather_one);
		ll_weather_two = (LinearLayout) getView().findViewById(R.id.ll_weather_two);
		ll_weather_base = (LinearLayout) getView().findViewById(R.id.ll_weather_base);
		tv_base_qw = (TextView) getView().findViewById(R.id.tv_base_qw);
		tv_base_qwh_max = (TextView) getView().findViewById(R.id.tv_base_qwh_max);
		tv_base_qwh_min = (TextView) getView().findViewById(R.id.tv_base_qwh_min);
		tv_base_fx = (TextView) getView().findViewById(R.id.tv_base_fx);
		tv_base_j = (TextView) getView().findViewById(R.id.tv_base_j);
		tv_base_jy = (TextView) getView().findViewById(R.id.tv_base_jy);
		iv_base_fx = (ImageView) getView().findViewById(R.id.iv_base_fx);
		iv_base_jy = (ImageView) getView().findViewById(R.id.iv_base_jy);
		iv_line = (ImageView) getView().findViewById(R.id.iv_line);
		getWeatherBase();
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
			case R.id.ll_weather_two :
				IntentUtils.getIntent(mContext, WeatherBaseTwoActivity.class);
				break;
			default:
				break;
		}

	}
}