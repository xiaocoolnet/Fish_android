/*
 * Copyright (c) 2016 4 22.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （天气温度详细信息Activity）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import java.text.SimpleDateFormat;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.net.HttpTool;
import cn.xiaocool.fish.view.LoadingDialog;

public class WeatherBaseOneActivity extends Activity implements View.OnClickListener {

    final static String chineseNumber[] = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    static SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private ImageView btn_exit; // 返回上一页
    private String result_data;
    private String key;
    private TextView tv_date_1;
    private TextView tv_next1_date,tv_next1_tmp;
    private TextView tv_next2_date,tv_next2_tmp;
    private TextView tv_next3_date,tv_next3_tmp;
    private TextView tv_next4_date,tv_next4_tmp;
    private TextView tv_date_2,tv_date_3,tv_date_4,tv_date_5,tv_date_6,tv_date_7;
    private TextView tv_nexttmp_2;
    private TextView tv_nexttmp_3;
    private TextView tv_nexttmp_4;
    private TextView tv_nexttmp_5;
    private TextView tv_nexttmp_6;
    private TextView tv_nexttmp_7;
    private LinearLayout ll_next_1,ll_next_2,ll_next_3,ll_next_4,ll_base_one_one;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 2:
                    Toast.makeText(WeatherBaseOneActivity.this, "网络问题，请稍后重试！", 0).show();
                    break;
                case 0:
                    Toast.makeText(WeatherBaseOneActivity.this, "请打开定位权限", 0).show();
                    LoadingDialog dialog = new LoadingDialog(WeatherBaseOneActivity.this);
                    dialog.show();
                    break;
                case 3:
                    try {
                        JSONObject jsonObject = new JSONObject(result_data);
                        String data = jsonObject.getString("HeWeather data service 3.0");
                        JSONArray ojArray = new JSONArray(data);
                        JSONObject ojArrayOne = ojArray.getJSONObject(0);

                        String daily_forecast = ojArrayOne.getString("daily_forecast");
                        JSONArray daily = new JSONArray(daily_forecast);
                        JSONObject one_daily = daily.getJSONObject(0);
                        String one_date = one_daily.getString("date");
                        tv_date_1.setText(one_date);
                        ll_base_one_one.setVisibility(View.VISIBLE);
                        JSONObject two_daily = daily.getJSONObject(1);
                        String two_date = two_daily.getString("date");
                        String tv_nexttmp2 = two_daily.getString("tmp");
                        JSONObject tv_nexttmp22 = new JSONObject(tv_nexttmp2);
                        String tv_nexttmpmax_2 = tv_nexttmp22.getString("max");
                        String tv_nexttmpmin_2 = tv_nexttmp22.getString("min");
                        tv_date_2.setText(two_date);
                        tv_nexttmp_2.setText(tv_nexttmpmax_2+"℃/"+tv_nexttmpmin_2+"℃");

                        JSONObject three_daily = daily.getJSONObject(2);
                        String three_date = three_daily.getString("date");
                        String tv_nexttmp3 = three_daily.getString("tmp");
                        JSONObject tv_nexttmp33 = new JSONObject(tv_nexttmp3);
                        String tv_nexttmpmax_3 = tv_nexttmp33.getString("max");
                        String tv_nexttmpmin_3 = tv_nexttmp33.getString("min");
                        tv_date_3.setText(three_date);
                        tv_nexttmp_3.setText(tv_nexttmpmax_3+"℃/"+tv_nexttmpmin_3+"℃");

                        JSONObject four_daily = daily.getJSONObject(3);
                        String four_date = four_daily.getString("date");
                        String tv_nexttmp4 = four_daily.getString("tmp");
                        JSONObject tv_nexttmp44 = new JSONObject(tv_nexttmp4);
                        String tv_nexttmpmax_4 = tv_nexttmp44.getString("max");
                        String tv_nexttmpmin_4 = tv_nexttmp44.getString("min");
                        tv_date_4.setText(four_date);
                        tv_nexttmp_4.setText(tv_nexttmpmax_4+"℃/"+tv_nexttmpmin_4+"℃");

                        JSONObject fif_daily = daily.getJSONObject(4);
                        String fif_date = fif_daily.getString("date");
                        String tv_nexttmp5 = fif_daily.getString("tmp");
                        JSONObject tv_nexttmp55 = new JSONObject(tv_nexttmp5);
                        String tv_nexttmpmax_5 = tv_nexttmp55.getString("max");
                        String tv_nexttmpmin_5 = tv_nexttmp55.getString("min");
                        tv_date_5.setText(fif_date);
                        tv_nexttmp_5.setText(tv_nexttmpmax_5+"℃/"+tv_nexttmpmin_5+"℃");

                        JSONObject six_daily = daily.getJSONObject(5);
                        String six_date = six_daily.getString("date");
                        String tv_nexttmp6 = six_daily.getString("tmp");
                        JSONObject tv_nexttmp66 = new JSONObject(tv_nexttmp6);
                        String tv_nexttmpmax_6 = tv_nexttmp66.getString("max");
                        String tv_nexttmpmin_6 = tv_nexttmp66.getString("min");
                        tv_date_6.setText(six_date);
                        tv_nexttmp_6.setText(tv_nexttmpmax_6+"℃/"+tv_nexttmpmin_6+"℃");

                        JSONObject se_daily = daily.getJSONObject(6);
                        String se_date = se_daily.getString("date");
                        String tv_nexttmp7 = se_daily.getString("tmp");
                        JSONObject tv_nexttmp77 = new JSONObject(tv_nexttmp7);
                        String tv_nexttmpmax_7 = tv_nexttmp77.getString("max");
                        String tv_nexttmpmin_7 = tv_nexttmp77.getString("min");
                        tv_date_7.setText(se_date);
                        tv_nexttmp_7.setText(tv_nexttmpmax_7+"℃/"+tv_nexttmpmin_7+"℃");

                        String hourly_forecast = ojArrayOne.getString("hourly_forecast");
                        JSONArray daily_hourly = new JSONArray(hourly_forecast);
                        JSONObject one_daily_hourly = daily_hourly.getJSONObject(0);
                        String next_date1 = one_daily_hourly.getString("date");
                        if (!next_date1.equals("")){
                            ll_next_1.setVisibility(View.VISIBLE);
                        }
                        String next_tmp1 = one_daily_hourly.getString("tmp");
                        String next_date1_1[] = next_date1.split(" ");
                        tv_next1_date.setText(next_date1_1[1]);
                        tv_next1_tmp.setText(next_tmp1+"℃");

                        JSONObject tow_daily_hourly = daily_hourly.getJSONObject(1);
                        String next_date2 = tow_daily_hourly.getString("date");
                        if (!next_date2.equals("")){
                            ll_next_2.setVisibility(View.VISIBLE);
                        }
                        String next_tmp2 = tow_daily_hourly.getString("tmp");
                        String next_date2_1[] = next_date2.split(" ");
                        tv_next2_date.setText(next_date2_1[1]);
                        tv_next2_tmp.setText(next_tmp2+"℃");

                        JSONObject three_daily_hourly = daily_hourly.getJSONObject(2);
                        String next_date3 = three_daily_hourly.getString("date");
                        if (!next_date3.equals("")){
                            ll_next_3.setVisibility(View.VISIBLE);
                        }
                        String next_tmp3 = three_daily_hourly.getString("tmp");
                        String next_date3_1[] = next_date3.split(" ");
                        tv_next3_date.setText(next_date3_1[1]);
                        tv_next3_tmp.setText(next_tmp3+"℃");

                        JSONObject four_daily_hourly = daily_hourly.getJSONObject(3);
                        String next_date4 = four_daily_hourly.getString("date");
                        if (!next_date4.equals("")){
                            ll_next_4.setVisibility(View.VISIBLE);
                        }
                        String next_tmp4 = four_daily_hourly.getString("tmp");
                        String next_date4_1[] = next_date4.split(" ");
                        tv_next4_date.setText(next_date4_1[1]);
                        tv_next4_tmp.setText(next_tmp4+"℃");

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(); // 初始化界面
        getWeather();
        initEvent(); // 初始化事件
    }

    private void getWeather() {
        new Thread() {
            public void run() {
                if (HttpTool.isConnnected(WeatherBaseOneActivity.this)){
                    SharedPreferences locate = getSharedPreferences("locate", MODE_PRIVATE);
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

    private void initEvent() {
        // 添加点击事件
        btn_exit.setOnClickListener(this);
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        setContentView(R.layout.activity_weather_base_one);
        // 控件实例化
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        tv_date_1 = (TextView) findViewById(R.id.tv_date_1);
        tv_date_2 = (TextView) findViewById(R.id.tv_date_2);
        tv_date_3 = (TextView) findViewById(R.id.tv_date_3);
        tv_date_4 = (TextView) findViewById(R.id.tv_date_4);
        tv_date_5 = (TextView) findViewById(R.id.tv_date_5);
        tv_date_6 = (TextView) findViewById(R.id.tv_date_6);
        tv_date_7 = (TextView) findViewById(R.id.tv_date_7);
        tv_next1_date = (TextView) findViewById(R.id.tv_next1_date);
        tv_next1_tmp = (TextView) findViewById(R.id.tv_next1_tmp);
        tv_next2_date = (TextView) findViewById(R.id.tv_next2_date);
        tv_next2_tmp = (TextView) findViewById(R.id.tv_next2_tmp);
        tv_next3_date = (TextView) findViewById(R.id.tv_next3_date);
        tv_next3_tmp = (TextView) findViewById(R.id.tv_next3_tmp);
        tv_next4_date = (TextView) findViewById(R.id.tv_next4_date);
        tv_next4_tmp = (TextView) findViewById(R.id.tv_next4_tmp);
        ll_next_1 = (LinearLayout) findViewById(R.id.ll_next_1);
        ll_next_2 = (LinearLayout) findViewById(R.id.ll_next_2);
        ll_next_3 = (LinearLayout) findViewById(R.id.ll_next_3);
        ll_next_4 = (LinearLayout) findViewById(R.id.ll_next_4);
        ll_base_one_one = (LinearLayout) findViewById(R.id.ll_base_one_one);
        tv_nexttmp_2 = (TextView) findViewById(R.id.tv_nexttmp_2);
        tv_nexttmp_3 = (TextView) findViewById(R.id.tv_nexttmp_3);
        tv_nexttmp_4 = (TextView) findViewById(R.id.tv_nexttmp_4);
        tv_nexttmp_5 = (TextView) findViewById(R.id.tv_nexttmp_5);
        tv_nexttmp_6 = (TextView) findViewById(R.id.tv_nexttmp_6);
        tv_nexttmp_7 = (TextView) findViewById(R.id.tv_nexttmp_7);
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
