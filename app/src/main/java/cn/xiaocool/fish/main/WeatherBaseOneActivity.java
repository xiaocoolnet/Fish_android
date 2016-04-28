/*
 * Copyright (c) 2016 4 22.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （天气温度详细信息Activity）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
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
    private TextView tv_wd;
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
                        String astro = one_daily.getString("astro");
                        JSONObject astroob = new JSONObject(astro);
                        String sr = astroob.getString("sr");
                        String ss = astroob.getString("ss");
                        tv_wd.setText(one_date+" "+sr+" "+ss);

//                        Calendar today = Calendar.getInstance();
//                        try {
//                            today.setTime(chineseDateFormat.parse(one_date));
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        DataLunar lunar = new DataLunar( today );
//                        System.out.println("北京时间："+chineseDateFormat.format(today.getTime())+"农历" + lunar);
//					tv_date_1.setText("北京时间："+chineseDateFormat.format(today.getTime()));
//					tv_date_2.setText("农历：" +lunar);
//					tv_weather.setText("天气情况："+txt);
//					tv_wendu.setText("温度："+fl+"℃");
//					tv_wind.setText("风力风向："+dir+" "+sc);
//					tv_shidu.setText("湿度："+hum+"%");
//					tv_rise.setText("日落日出："+sr+"/"+ss);

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
        tv_wd = (TextView) findViewById(R.id.tv_wd);
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
