package com.example.administrator.coolweather.gson;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class AQI {
    public AQICity city;

    public class AQICity{
        public String aqi;
        public String pm25;
    }
}
