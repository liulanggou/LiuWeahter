package com.example.administrator.coolweather;

import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class QueryViolationActivity extends AppCompatActivity {
    private TextView tv_lstype,tv_lsprefis;
    private EditText et_lsnum,et_frameno,et_engineno;
    private TextView tv_titleMovieName;
    private ImageView iv_back;;

    private String[] items = new String[]{"小型汽车号牌","大型汽车号牌","使馆汽车号牌","领馆汽车号牌","" +
            "境外汽车号牌","外籍汽车号牌","两、三轮摩托车号牌","轻便摩托车号牌","使馆摩托车号牌","领馆摩托车号牌","境外摩托车号牌","外籍摩托车号牌","农用运输车号牌","拖拉机号牌","挂车号牌","教练汽车号牌","" +
            "教练摩托车号牌","试验汽车号牌","试验摩托车号牌","临时入境汽车号牌","临时入境摩托车号牌","临时行驶车号牌",
            "警用汽车号牌","警用摩托号牌","原农机号牌","香港入出境车号牌","澳门入出境车号牌","大型新能源汽车号牌","小型新能源汽车号牌"};
    private String select_item = "小型汽车";
    private String select_item01 = "京";
    private String[] items01 = new String[]{
            "京","皖","闽","甘","粤","桂","贵","琼","冀","豫","黑","鄂","湘","吉","苏","赣","辽","蒙",
            "宁","青","鲁","晋","陕","沪","川","津","藏","新","云","浙","渝"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_violation);
        initView();
        tv_titleMovieName.setText("添写信息");
        tv_lstype.setText("小型汽车号牌");
        tv_lsprefis.setText("京");
        tv_lstype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder dialog = new AlertDialog.Builder(QueryViolationActivity.this);
                dialog.setTitle("请选择");
                dialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        select_item = items[which].toString();
                        tv_lstype.setText(select_item);
                        //Toast.makeText(QueryViolationActivity.this, select_item, Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //select_item = items[which-1].toString();
                        dialog.dismiss();
                    }
                });
                dialog.show();

            }
        });

        tv_lsprefis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(QueryViolationActivity.this);
                dialog.setTitle("请选择");
                dialog.setSingleChoiceItems(items01, 0, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        select_item01 = items01[which].toString();
                        tv_lsprefis.setText(select_item01);
                        //Toast.makeText(QueryViolationActivity.this, select_item, Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //select_item = items[which-1].toString();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });





    }


    private void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_titleMovieName = (TextView) findViewById(R.id.tv_titleMovieName);
        tv_lstype = (TextView) findViewById(R.id.tv_lstype);
        tv_lsprefis = (TextView) findViewById(R.id.tv_lsprefis);
        et_lsnum = (EditText) findViewById(R.id.et_lsnum);
        et_frameno = (EditText) findViewById(R.id.et_frameno);
        et_engineno = (EditText) findViewById(R.id.et_engineno);
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

    public void queryWeizhang(View view) {
        if(TextUtils.isEmpty(et_lsnum.getText().toString())){
            Toast.makeText(this, "请输入车牌号码", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(et_frameno.getText().toString())){
            Toast.makeText(this, "请输入发动机号", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(et_engineno.getText().toString())){
            Toast.makeText(this, "请输入车架号", Toast.LENGTH_SHORT).show();
        }else{
            String lsnum = et_lsnum.getText().toString();
            String frageno = et_frameno.getText().toString();
            String engineno = et_engineno.getText().toString();
            String address ="http://api.jisuapi.com/illegal/query?appkey=6451150bb9cb337c&&lsprefix="+tv_lsprefis.getText().toString()+"&lsnum="+lsnum+"&frameno="+frageno+"&engineno="+engineno;
            Intent intent = new Intent(QueryViolationActivity.this,ShowWeiZhangActivity.class);
            intent.putExtra("address",address);
            intent.putExtra("chepaihao",tv_lsprefis.getText().toString()+"  "+lsnum);
            startActivity(intent);
        }
    }
}
