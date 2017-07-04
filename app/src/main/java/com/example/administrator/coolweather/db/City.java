package com.example.administrator.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class City extends DataSupport {
    //id字段
    private int id;
    //市名称
    private String cityName;
    //市的代号
    private int cityCode;
    //当前市所属省的id
    private int provinceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
