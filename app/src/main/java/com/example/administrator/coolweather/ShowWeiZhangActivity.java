package com.example.administrator.coolweather;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.coolweather.adapter.WeiZhangAdapter;
import com.example.administrator.coolweather.db.WeiZhang;
import com.example.administrator.coolweather.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ShowWeiZhangActivity extends AppCompatActivity {
    private ImageView iv_back;
    private TextView tv_chepaihao;
    private List<WeiZhang> weiZhangList = new ArrayList<>();
    private ListView lv_showWZ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_wei_zhang);
        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        String chepaihao = intent.getStringExtra("chepaihao");
        initView();
        tv_chepaihao.setText(chepaihao);

        Log.d("TAG",address);
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseJson = response.body().string();
                try {
                    JSONObject object = new JSONObject(responseJson);
                    String msg = object.getString("msg");
                    if(msg.equals("车牌号有误")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ShowWeiZhangActivity.this, "车牌号有误", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //车牌号有误
                    }else if(msg.equals("发动机号有误或无违章！")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ShowWeiZhangActivity.this, "发动机号有误或无违章!", Toast.LENGTH_SHORT).show();
                            }
                        });
                        //{"status":"0","msg":"发动机号有误或无违章！","result":{"lsprefix":"沪","lsnum":"C6AB35","carorg":"shanghai","usercarid":"","count":"0","totalprice":"0","totalscore":"0","list":[]}}
                    }else{
                        object = object.getJSONObject("result");
                        JSONArray array = object.getJSONArray("list");
                        for (int i = 0; i < array.length(); i++) {
                            object = array.getJSONObject(i);
                            WeiZhang weiZhang = new WeiZhang();
                            weiZhang.setAddress(object.getString("address"));
                            weiZhang.setTime(object.getString("time"));
                            weiZhang.setContent(object.getString("content"));
                            weiZhang.setPrice(object.getString("price"));
                            weiZhang.setScore(object.getString("score"));
                            Log.d("TAG",weiZhang.getContent());
                            weiZhangList.add(weiZhang);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                WeiZhangAdapter adapter = new WeiZhangAdapter(weiZhangList,ShowWeiZhangActivity.this);
                                lv_showWZ.setAdapter(adapter);
                            }
                        });

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView() {
        lv_showWZ = (ListView) findViewById(R.id.lv_showWZ);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_chepaihao = (TextView) findViewById(R.id.tv_chepaihao);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public void run() {
                        try {
                            Instrumentation inst = new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        } catch (Exception e) {
                        }
                    }
                }.start();
            }
        });
    }
}
