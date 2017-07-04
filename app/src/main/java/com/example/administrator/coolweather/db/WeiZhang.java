package com.example.administrator.coolweather.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/6/27 0027.
 */

public class WeiZhang extends DataSupport {
    private int id;
    private String address;
    private String time;
    private String content;
    private String price;
    private String score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
