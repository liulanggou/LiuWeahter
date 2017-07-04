package com.example.administrator.coolweather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

public class BaiDuActivity extends AppCompatActivity {
    private WebView wv_baidu;
    private TextView tv_title;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai_du);
        tv_title = (TextView) findViewById(R.id.tv_titleMovieName);
        tv_title.setText("百度一下");
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaiDuActivity.this,TopActivity.class);
                startActivity(intent);
            }
        });



        wv_baidu = (WebView) findViewById(R.id.wv_baidu);
        wv_baidu.loadUrl("http://www.baidu.com");
        wv_baidu.setWebViewClient(new WebViewClient());

        WebSettings settings = wv_baidu.getSettings();
        settings.setJavaScriptEnabled(true);
    }
}
