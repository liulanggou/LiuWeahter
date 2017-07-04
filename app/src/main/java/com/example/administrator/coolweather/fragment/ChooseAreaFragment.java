package com.example.administrator.coolweather.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.coolweather.MainActivity;
import com.example.administrator.coolweather.R;
import com.example.administrator.coolweather.WeatherActivity;
import com.example.administrator.coolweather.db.City;
import com.example.administrator.coolweather.db.County;
import com.example.administrator.coolweather.db.Province;
import com.example.administrator.coolweather.util.HttpUtil;
import com.example.administrator.coolweather.util.UtilForJson;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class ChooseAreaFragment extends Fragment {
    //选中的级别，默认是省级别
    private int currentLevel = 0;
    //设置点击的时候判断当前的省级别还是市级别还是县级别
    public static final int PROVINCE = 0;
    public static final int CITY = 1;
    public static final int COUNTY = 2;
    //加载条
    private ProgressDialog progressDialog;
    //标题栏
    private TextView titleText;
    private Button backButton;
    private ListView listView;

    private ArrayAdapter<String> adapter;

    private List<String> dataList = new ArrayList<>();
    //省集合
    private List<Province> provinceList;
    //市集合
    private List<City> cityList;
    //县集合
    private List<County> countyList;
    //选中的省份
    private Province selectedProvince;
    //选中的市
    private City selectedCity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area, container, false);
        titleText = (TextView) view.findViewById(R.id.title_text);
        backButton = (Button) view.findViewById(R.id.back_button);
        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //先把省集合查出来
        queryProvinces();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == PROVINCE) {
                    selectedProvince = provinceList.get(position);
                    queryCities();
                } else if (currentLevel == CITY) {
                    selectedCity = cityList.get(position);
                    queryCounties();
                } else if (currentLevel == COUNTY) {
                    String weatherId = countyList.get(position).getWeatherId();
                    /*Intent intent = new Intent(getActivity(), WeatherActivity.class);
                    intent.putExtra("weather_id", weatherId);
                    startActivity(intent);
                    getActivity().finish();*/
                    if(getActivity() instanceof MainActivity){
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);
                        intent.putExtra("weather_id", weatherId);
                        startActivity(intent);
                        getActivity().finish();
                    }else if(getActivity() instanceof WeatherActivity){
                        WeatherActivity activity = (WeatherActivity) getActivity();
                        activity.drawerLayout.closeDrawers();
                        activity.swopRefresh.setRefreshing(true);
                        activity.requestWeather(weatherId);
                    }
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == COUNTY) {
                    queryCities();
                } else if (currentLevel == CITY) {
                    queryProvinces();
                }
            }
        });

    }


    //查询所有的省，优先在数据库中查询，如果没有再到服务器中查询
    private void queryProvinces() {
        titleText.setText("刘浪的殖民地");
        backButton.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        if (provinceList.size() > 0) {
            //如果数据库中查询到了
            dataList.clear();
            for (Province province : provinceList) {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            //让listView 从第一条显示
            listView.setSelection(0);
            //让当前的界面等级变为省的
            currentLevel = PROVINCE;
        } else {
            //如果数据库中没查询到，则到网络中获取
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "province");
        }
    }

    //查询所有的市，优先在数据库中查询，如果没有再到服务器中查询
    private void queryCities() {
        titleText.setText(selectedProvince.getProvinceName());
        backButton.setVisibility(View.VISIBLE);
        cityList = DataSupport.where("provinceid=?", String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = CITY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            queryFromServer(address, "city");
        }
    }

    //查询所有的县，优先在数据库查询，如果没有再到服务器中查询
    private void queryCounties() {
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityid=?", String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() > 0) {
            dataList.clear();
            for (County county : countyList) {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = COUNTY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String addres = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            queryFromServer(addres, "county");
        }
    }

    //根据传入的地址和类型从服务器中查询省，市，县
    private void queryFromServer(String address, final String type) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("province".equals(type)) {
                    result = UtilForJson.handleProbinceResponse(responseText);
                } else if ("city".equals(type)) {
                    result = UtilForJson.handlerCityResponse(responseText, selectedProvince.getId());
                } else if ("county".equals(type)) {
                    result = UtilForJson.handleCountyResponse(responseText, selectedCity.getId());
                }
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });
    }

    //显示进度对话框
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    //关闭对话框
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }


}
