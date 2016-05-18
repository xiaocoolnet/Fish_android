/*
 * Copyright (c) 2016 4 22.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （天气风详细信息Activity）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.content.SharedPreferences;
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

public class WeatherBaseTwoActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页
    private String result_data;
    private String key;
    private TextView tv_date_1,tv_date_2,tv_date_3,tv_date_4,tv_date_5,tv_date_6,tv_date_7;
    private TextView tv_next_date1,tv_next_date2,tv_next_date3,tv_next_date4;
    private TextView tv_next1_fx,tv_next1_fl,tv_next1_fs;
    private TextView tv_next_fx2,tv_next_fl2,tv_next_fs2;
    private TextView tv_next_fx3,tv_next_fl3,tv_next_fs3;
    private TextView tv_next_fx4,tv_next_fl4,tv_next_fs4;
    private TextView tv_next2_fx,tv_next2_fl,tv_next2_fs;
    private TextView tv_next3_fx,tv_next3_fl,tv_next3_fs;
    private TextView tv_next4_fx,tv_next4_fl,tv_next4_fs;
    private TextView tv_next5_fx,tv_next5_fl,tv_next5_fs;
    private TextView tv_next6_fx,tv_next6_fl,tv_next6_fs;
    private TextView tv_next7_fx,tv_next7_fl,tv_next7_fs;
    private LinearLayout ll_next_1,ll_next_2,ll_next_3,ll_next_4,ll_base_two_two;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 2:
                    Toast.makeText(WeatherBaseTwoActivity.this, "网络问题，请稍后重试！", 0).show();
                    break;
                case 0:
                    Toast.makeText(WeatherBaseTwoActivity.this, "请打开定位权限", 0).show();
                    LoadingDialog dialog = new LoadingDialog(WeatherBaseTwoActivity.this);
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
                        ll_base_two_two.setVisibility(View.VISIBLE);

                        JSONObject two_daily = daily.getJSONObject(1);
                        String two_date = two_daily.getString("date");
                        String daily_wind2 = two_daily.getString("wind");
                        JSONObject wind_2 = new JSONObject(daily_wind2);
                        String daily_dir2 = wind_2.getString("dir");
                        String daily_sc2 = wind_2.getString("sc");
                        String daily_spd2 = wind_2.getString("spd");
                        tv_date_2.setText(two_date);
                        tv_next2_fx.setText(daily_dir2);
                        tv_next2_fl.setText(daily_sc2);
                        tv_next2_fs.setText(daily_spd2);

                        JSONObject three_daily = daily.getJSONObject(2);
                        String three_date = three_daily.getString("date");
                        String daily_wind3 = three_daily.getString("wind");
                        JSONObject wind_3 = new JSONObject(daily_wind3);
                        String daily_dir3 = wind_3.getString("dir");
                        String daily_sc3 = wind_3.getString("sc");
                        String daily_spd3 = wind_3.getString("spd");
                        tv_date_3.setText(three_date);
                        tv_next3_fx.setText(daily_dir3);
                        tv_next3_fl.setText(daily_sc3);
                        tv_next3_fs.setText(daily_spd3);

                        JSONObject four_daily = daily.getJSONObject(3);
                        String four_date = four_daily.getString("date");
                        String daily_wind4 = four_daily.getString("wind");
                        JSONObject wind_4 = new JSONObject(daily_wind4);
                        String daily_dir4 = wind_4.getString("dir");
                        String daily_sc4 = wind_4.getString("sc");
                        String daily_spd4 = wind_4.getString("spd");
                        tv_date_4.setText(four_date);
                        tv_next4_fx.setText(daily_dir4);
                        tv_next4_fl.setText(daily_sc4);
                        tv_next4_fs.setText(daily_spd4);

                        JSONObject fif_daily = daily.getJSONObject(4);
                        String fif_date = fif_daily.getString("date");
                        String daily_wind5 = fif_daily.getString("wind");
                        JSONObject wind_5 = new JSONObject(daily_wind5);
                        String daily_dir5 = wind_5.getString("dir");
                        String daily_sc5 = wind_5.getString("sc");
                        String daily_spd5 = wind_5.getString("spd");
                        tv_date_5.setText(fif_date);
                        tv_next5_fx.setText(daily_dir5);
                        tv_next5_fl.setText(daily_sc5);
                        tv_next5_fs.setText(daily_spd5);

                        JSONObject six_daily = daily.getJSONObject(5);
                        String six_date = six_daily.getString("date");
                        String daily_wind6 = six_daily.getString("wind");
                        JSONObject wind_6 = new JSONObject(daily_wind6);
                        String daily_dir6 = wind_6.getString("dir");
                        String daily_sc6 = wind_6.getString("sc");
                        String daily_spd6 = wind_6.getString("spd");
                        tv_date_6.setText(six_date);
                        tv_next6_fx.setText(daily_dir6);
                        tv_next6_fl.setText(daily_sc6);
                        tv_next6_fs.setText(daily_spd6);

                        JSONObject se_daily = daily.getJSONObject(6);
                        String se_date = se_daily.getString("date");
                        String daily_wind7 = se_daily.getString("wind");
                        JSONObject wind_7 = new JSONObject(daily_wind7);
                        String daily_dir7 = wind_7.getString("dir");
                        String daily_sc7 = wind_7.getString("sc");
                        String daily_spd7 = wind_7.getString("spd");
                        tv_date_7.setText(se_date);
                        tv_next7_fx.setText(daily_dir7);
                        tv_next7_fl.setText(daily_sc7);
                        tv_next7_fs.setText(daily_spd7);

                        String hourly_forecast = ojArrayOne.getString("hourly_forecast");
                        JSONArray daily_hourly = new JSONArray(hourly_forecast);

                        JSONObject one_daily_hourly = daily_hourly.getJSONObject(0);
                        String next_date1 = one_daily_hourly.getString("date");
                        if (!next_date1.equals("")){
                            ll_next_1.setVisibility(View.VISIBLE);
                        }
                        String hourly_wind1 = one_daily_hourly.getString("wind");
                        JSONObject wind1 = new JSONObject(hourly_wind1);
                        String dir1 = wind1.getString("dir");
                        String sc1 = wind1.getString("sc");
                        String spd1 = wind1.getString("spd");
                        String next_date1_1[] = next_date1.split(" ");
                        tv_next_date1.setText(next_date1_1[1]);
                        tv_next1_fx.setText(dir1);
                        tv_next1_fl.setText(sc1);
                        tv_next1_fs.setText(spd1);

                        JSONObject tow_daily_hourly = daily_hourly.getJSONObject(1);
                        String next_date2 = tow_daily_hourly.getString("date");
                        if (!next_date2.equals("")){
                            ll_next_2.setVisibility(View.VISIBLE);
                        }
                        String hourly_wind2 = tow_daily_hourly.getString("wind");
                        JSONObject wind2 = new JSONObject(hourly_wind2);
                        String dir2 = wind2.getString("dir");
                        String sc2 = wind2.getString("sc");
                        String spd2 = wind2.getString("spd");
                        String next_date2_1[] = next_date2.split(" ");
                        tv_next_date2.setText(next_date2_1[1]);
                        tv_next_fx2.setText(dir2);
                        tv_next_fl2.setText(sc2);
                        tv_next_fs2.setText(spd2);

                        JSONObject three_daily_hourly = daily_hourly.getJSONObject(2);
                        String next_date3 = three_daily_hourly.getString("date");
                        if (!next_date3.equals("")){
                            ll_next_3.setVisibility(View.VISIBLE);
                        }
                        String hourly_wind3 = three_daily_hourly.getString("wind");
                        JSONObject wind3 = new JSONObject(hourly_wind3);
                        String dir3 = wind3.getString("dir");
                        String sc3 = wind3.getString("sc");
                        String spd3 = wind3.getString("spd");
                        String next_date3_1[] = next_date3.split(" ");
                        tv_next_date3.setText(next_date3_1[1]);
                        tv_next_fx3.setText(dir3);
                        tv_next_fl3.setText(sc3);
                        tv_next_fs3.setText(spd3);

                        JSONObject four_daily_hourly = daily_hourly.getJSONObject(3);
                        String next_date4 = four_daily_hourly.getString("date");
                        if (!next_date4.equals("")){
                            ll_next_4.setVisibility(View.VISIBLE);
                        }
                        String hourly_wind4 = four_daily_hourly.getString("wind");
                        JSONObject wind4 = new JSONObject(hourly_wind4);
                        String dir4 = wind4.getString("dir");
                        String sc4 = wind4.getString("sc");
                        String spd4 = wind4.getString("spd");
                        String next_date4_1[] = next_date4.split(" ");
                        tv_next_date4.setText(next_date4_1[1]);
                        tv_next_fx4.setText(dir4);
                        tv_next_fl4.setText(sc4);
                        tv_next_fs4.setText(spd4);

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
                if (HttpTool.isConnnected(WeatherBaseTwoActivity.this)){
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
        setContentView(R.layout.activity_weather_base_two);
        // 控件实例化
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        tv_date_1 = (TextView) findViewById(R.id.tv_date_1);
        tv_date_2 = (TextView) findViewById(R.id.tv_date_2);
        tv_date_3 = (TextView) findViewById(R.id.tv_date_3);
        tv_date_4 = (TextView) findViewById(R.id.tv_date_4);
        tv_date_5 = (TextView) findViewById(R.id.tv_date_5);
        tv_date_6 = (TextView) findViewById(R.id.tv_date_6);
        tv_date_7 = (TextView) findViewById(R.id.tv_date_7);
        tv_next_date1 = (TextView) findViewById(R.id.tv_next_date1);
        tv_next_date2 = (TextView) findViewById(R.id.tv_next_date2);
        tv_next_date3 = (TextView) findViewById(R.id.tv_next_date3);
        tv_next_date4 = (TextView) findViewById(R.id.tv_next_date4);
        tv_next1_fx = (TextView) findViewById(R.id.tv_next1_fx);
        tv_next1_fl = (TextView) findViewById(R.id.tv_next1_fl);
        tv_next1_fs = (TextView) findViewById(R.id.tv_next1_fs);
        tv_next_fx2 = (TextView) findViewById(R.id.tv_next_fx2);
        tv_next_fl2 = (TextView) findViewById(R.id.tv_next_fl2);
        tv_next_fs2 = (TextView) findViewById(R.id.tv_next_fs2);
        tv_next_fx3 = (TextView) findViewById(R.id.tv_next_fx3);
        tv_next_fl3 = (TextView) findViewById(R.id.tv_next_fl3);
        tv_next_fs3 = (TextView) findViewById(R.id.tv_next_fs3);
        tv_next_fx4 = (TextView) findViewById(R.id.tv_next_fx4);
        tv_next_fl4 = (TextView) findViewById(R.id.tv_next_fl4);
        tv_next_fs4 = (TextView) findViewById(R.id.tv_next_fs4);
        tv_next2_fx = (TextView) findViewById(R.id.tv_next2_fx);
        tv_next2_fl = (TextView) findViewById(R.id.tv_next2_fl);
        tv_next2_fs = (TextView) findViewById(R.id.tv_next2_fs);
        tv_next3_fx = (TextView) findViewById(R.id.tv_next3_fx);
        tv_next3_fl = (TextView) findViewById(R.id.tv_next3_fl);
        tv_next3_fs = (TextView) findViewById(R.id.tv_next3_fs);
        tv_next4_fx = (TextView) findViewById(R.id.tv_next4_fx);
        tv_next4_fl = (TextView) findViewById(R.id.tv_next4_fl);
        tv_next4_fs = (TextView) findViewById(R.id.tv_next4_fs);
        tv_next5_fx = (TextView) findViewById(R.id.tv_next5_fx);
        tv_next5_fl = (TextView) findViewById(R.id.tv_next5_fl);
        tv_next5_fs = (TextView) findViewById(R.id.tv_next5_fs);
        tv_next6_fx = (TextView) findViewById(R.id.tv_next6_fx);
        tv_next6_fl = (TextView) findViewById(R.id.tv_next6_fl);
        tv_next6_fs = (TextView) findViewById(R.id.tv_next6_fs);
        tv_next7_fx = (TextView) findViewById(R.id.tv_next7_fx);
        tv_next7_fl = (TextView) findViewById(R.id.tv_next7_fl);
        tv_next7_fs = (TextView) findViewById(R.id.tv_next7_fs);
        ll_next_1 = (LinearLayout) findViewById(R.id.ll_next_1);
        ll_next_2 = (LinearLayout) findViewById(R.id.ll_next_2);
        ll_next_3 = (LinearLayout) findViewById(R.id.ll_next_3);
        ll_next_4 = (LinearLayout) findViewById(R.id.ll_next_4);
        ll_base_two_two = (LinearLayout) findViewById(R.id.ll_base_two_two);

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
