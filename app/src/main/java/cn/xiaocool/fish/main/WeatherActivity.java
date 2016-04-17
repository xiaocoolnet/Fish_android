package cn.xiaocool.fish.main;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.net.HttpTool;
import cn.xiaocool.fish.view.LoadingDialog;

public class WeatherActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页
    private TextView weather_api,weather_api2,weather_api3;
    private String result_data;
    private String key;
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:

                        Toast.makeText(WeatherActivity.this,"请打开定位权限",0).show();
                        LoadingDialog dialog = new LoadingDialog(WeatherActivity.this);
                        dialog.show();
                    break;
                case 3:
                    try {
                        JSONObject jsonObject = new JSONObject(result_data);
                        String data = jsonObject.getString("HeWeather data service 3.0");
                        JSONArray ojArray = new JSONArray(data);
                        JSONObject ojArrayOne = ojArray.getJSONObject(0);
                        String basic = ojArrayOne.getString("basic");
                        JSONObject ob1 = new JSONObject(basic);
                        String city = ob1.getString("city");
                        String cnty = ob1.getString("cnty");
                        String id = ob1.getString("id");
                        String lat = ob1.getString("lat");
                        String lon = ob1.getString("lon");

                        String nowOb = ojArrayOne.getString("now");
                        JSONObject ob2 = new JSONObject(nowOb);
                        String fl = ob2.getString("fl");
                        String hum = ob2.getString("pcpn");
                        String pres = ob2.getString("pres");
                        String tmp = ob2.getString("tmp");
                        String vis = ob2.getString("vis");

                        String wind = ob2.getString("wind");
                        JSONObject ob3 = new JSONObject(wind);
                        String deg = ob3.getString("deg");
                        String dir = ob3.getString("dir");
                        String sc = ob3.getString("sc");
                        String spd = ob3.getString("spd");

                        weather_api.setText(city+"---"+cnty+"---"+id+"---"+lat+"---"+lon);
                        weather_api2.setText(fl+"---"+hum+"---"+pres+"---"+tmp+"---"+lon);
                        weather_api3.setText(deg+"---"+dir+"---"+sc+"---"+spd);
                        Log.i("city", city+"---"+cnty+"---"+id+"---"+lat+"---"+vis);
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
        initEvent(); // 初始化事件
    }

    private void initEvent() {
        // 添加点击事件
        btn_exit.setOnClickListener(this);
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        setContentView(R.layout.activity_weather); // 登录界面
        // 控件实例化
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        weather_api = (TextView) findViewById(R.id.weather_api);
        weather_api2 = (TextView) findViewById(R.id.weather_api2);
        weather_api3 = (TextView) findViewById(R.id.weather_api3);

        new Thread() {
            public void run() {
                SharedPreferences locate = getSharedPreferences("locate", MODE_PRIVATE);
                String getlocate = locate.getString("userLocate", "");
                if(getlocate.equals("")||getlocate==null){
                    getlocate="烟台";
                    handler.sendEmptyMessage(0);// 提示
                }
                key = "1058a995f42145d7b9b7208dafe23720";
                result_data = HttpTool.WeatherAPI(getlocate,key);
                handler.sendEmptyMessage(3);// 调用服务器登录函数
            }
        }.start();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit :
                finish();
                break;
            default:
                break;
        }
    }

}
