package com.example.administrator.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class County extends DataSupport {
    //id字段
    private int id;
    //县的名称
    private String countyName;
    //当前县对应的weatherid
    private String weatherId;
    //当前县所属市的id
    private int cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
