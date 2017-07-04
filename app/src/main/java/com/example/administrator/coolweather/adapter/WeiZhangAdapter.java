package com.example.administrator.coolweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.coolweather.R;
import com.example.administrator.coolweather.db.WeiZhang;

import java.util.List;

/**
 * Created by Administrator on 2017/6/27 0027.
 */

public class WeiZhangAdapter extends BaseAdapter {
    private List<WeiZhang> mData;
    private Context context;

    public WeiZhangAdapter(List<WeiZhang> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WZViewHolder holder;
        if(convertView==null){
            holder = new WZViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weizhang,parent,false);
            holder.tv_weizhangTime = (TextView) convertView.findViewById(R.id.tv_weizhangTime);
            holder.tv_weizhangAddress = (TextView) convertView.findViewById(R.id.tv_weizhangAddress);
            holder.tv_weizhangContent = (TextView) convertView.findViewById(R.id.tv_weizhangContent);
            holder.tv_weizhangPrice = (TextView) convertView.findViewById(R.id.tv_weizhangPrice);
            holder.tv_weizhangScore = (TextView) convertView.findViewById(R.id.tv_weizhangScore);
            convertView.setTag(holder);
        }else{
            holder = (WZViewHolder) convertView.getTag();
        }
        WeiZhang weiZhang = mData.get(position);
        holder.tv_weizhangTime.setText(weiZhang.getTime());
        holder.tv_weizhangAddress.setText(weiZhang.getAddress());
        holder.tv_weizhangContent.setText(weiZhang.getContent());
        holder.tv_weizhangPrice.setText("罚款金:"+weiZhang.getPrice());
        holder.tv_weizhangScore.setText("扣除分:"+weiZhang.getScore());
        return convertView;
    }

    class WZViewHolder{
        TextView tv_weizhangTime,tv_weizhangAddress,tv_weizhangContent,tv_weizhangPrice,tv_weizhangScore;
    }
}
