package cn.xiaocool.fish.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import cn.xiaocool.fish.R;
import cn.xiaocool.fish.view.ProgressWebView;

public class HomeBaseWebActivity extends Activity {

	protected ProgressWebView mWebView;
	private ProgressBar web_progressbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homebaseweb);

		mWebView = (ProgressWebView) findViewById(R.id.baseweb_webview);
		mWebView.getSettings().setJavaScriptEnabled(true);
		initData();
	}

	protected void initData() {
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String url = bundle.getString("url");

		// if(!TextUtils.isEmpty(url)&&TextUtils.isEmpty(title)){
		mWebView.loadUrl(url);
		// }
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mWebView = null;

	}

}
