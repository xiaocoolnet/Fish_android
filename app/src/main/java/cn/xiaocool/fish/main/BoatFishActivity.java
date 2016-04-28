/*
 * Copyright (c) 2016 3 30.
 * 公司:北京校酷网络有限公司
 * 工作室：Hello Fish  闲来垂钓APP （船钓Activity）
 */
package cn.xiaocool.fish.main;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.adapter.boat.BoatListAdapter;
import cn.xiaocool.fish.adapter.boat.BoatListBean;
import cn.xiaocool.fish.net.HttpTool;

public class BoatFishActivity extends Activity implements View.OnClickListener {

    private ImageView btn_exit; // 返回上一页
    private ListView mListView;
    private static String jsonURL = "http://www.imooc.com/api/teacher?type=4&num=30"; // json数据网址

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(); // 初始化界面
        initEvent(); // 初始化事件
        new NewsAsyncTask().execute(jsonURL);
    }

    private void initEvent() {
        // 添加点击事件
        btn_exit.setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(BoatFishActivity.this, BoatFishSingleContentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题栏
        setContentView(R.layout.activity_boatfish); // 船钓列表界面

        IfHttpSussess();
        // 控件实例化
        btn_exit = (ImageView) findViewById(R.id.btn_exit);
        mListView = (ListView) findViewById(R.id.lv_main);
    }

    private void IfHttpSussess() {
        if (HttpTool.isConnnected(BoatFishActivity.this)) {

        }else {
            Toast.makeText(BoatFishActivity.this,"网络问题，请稍后重试！",0).show();
        }
    }

    /** * 将URL网址上的json数据转化为我们所需的newsbean对象 */
    private List<BoatListBean> getJsonData(String url)
    {
        List<BoatListBean> newsBeanList = new ArrayList<BoatListBean>(); // 保存解析出来的所有的数据
        try {
            //获取到json字符串
            String jsonString = readStream(new URL(url).openStream());// 和url.openConnection().getInputStream()一样
            // Log.d("MainActivity", jsonString);
            // 将获取到的json字符串变为jsonObject对象，打开网址可以看出data节点是一个jsonArray,array里面存放了一个个的jsonObject
            BoatListBean newsBean;
            JSONObject jsonObject;
            String newsUrl = null;
            String newsTitle = null;
            String newsContent = null;
            jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                newsUrl = jsonObject.getString("picSmall"); // 图片网址
                newsTitle = jsonObject.getString("name"); // title
                newsContent = jsonObject.getString("description"); // content
                newsBean = new BoatListBean(newsUrl, newsTitle, newsContent);
                newsBeanList.add(newsBean);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return newsBeanList;
    }

    /** * 解析网络返回的数据 */
    private String readStream(InputStream is)
    {
        InputStreamReader isReader;
        String result = "";
        String line = "";
        try {
            isReader = new InputStreamReader(is, "utf-8"); // 将字节流转化为字符流
            BufferedReader buffReader = new BufferedReader(isReader); // 从字符输入流中读取文本，缓冲各个字符，从而实现字符、数组和行的高效读取
            while ((line = buffReader.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }
    /**
     * 构造一个AsyncTask，传入String类型的URL，返回一个NewsBean对象，每一个对象就是
     * listview中的每一行数据，包括一个icon,title,content
     */
    class NewsAsyncTask extends AsyncTask<String, Void, List<BoatListBean>>
    {

        @Override
        protected List<BoatListBean> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<BoatListBean> result) {
            super.onPostExecute(result);
            // 访问网络并解析json成功后返回结果，即我们设置的List<BoatListBean>
            BoatListAdapter adapter = new BoatListAdapter(BoatFishActivity.this, result, mListView);
            mListView.setAdapter(adapter);
        }
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
