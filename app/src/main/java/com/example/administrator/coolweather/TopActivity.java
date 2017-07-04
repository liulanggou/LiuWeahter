package com.example.administrator.coolweather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class TopActivity extends AppCompatActivity {
    private TextView tv_queryWeather,tv_queryViolation,tv_queryBaidu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        initView();
        tv_queryWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        tv_queryViolation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopActivity.this,QueryViolationActivity.class);
                startActivity(intent);
            }
        });
        tv_queryBaidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopActivity.this,BaiDuActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        tv_queryWeather = (TextView) findViewById(R.id.tv_queryWeather);
        tv_queryViolation = (TextView) findViewById(R.id.tv_queryViolation);
        tv_queryBaidu = (TextView) findViewById(R.id.tv_queryBaidu);

    }
}
