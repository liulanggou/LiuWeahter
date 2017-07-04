package com.example.administrator.coolweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.coolweather.R;

import java.util.List;

/**
 * Created by Administrator on 2017/6/26 0026.
 */

public class MyAdapter extends BaseAdapter {
    private List<String> mData;
    private Context context;

    public MyAdapter(List<String> mData, Context context) {
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
        LeixingViewHolder holder;
        if(convertView==null){
            holder = new LeixingViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cheleixing,parent,false);
            holder.tv_leixing = (TextView) convertView.findViewById(R.id.tv_leixingItem);
            convertView.setTag(holder);
        }else{
            holder = (LeixingViewHolder) convertView.getTag();
        }
        holder.tv_leixing.setText(mData.get(position));
        return convertView;
    }

    class LeixingViewHolder{
        TextView tv_leixing;
    }
}
