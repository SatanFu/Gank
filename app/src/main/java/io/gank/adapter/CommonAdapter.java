package io.gank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import io.gank.util.ViewHolder;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {

    public Context mContext;
    private List<T> mDatas;
    private LayoutInflater inflater;
    private int mViewId;

    public CommonAdapter(Context context, List<T> datas, int viewId) {
        this.mContext = context;
        this.mDatas = datas;
        this.mViewId = viewId;
        inflater = LayoutInflater.from(mContext);
    }

    public List<T> getData() {
        return mDatas;
    }

    public void setData(List<T> datas) {
        this.mDatas = datas;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        // TODO Auto-generated method stub
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent,
                mViewId, position);
        convert(holder, getItem(position), position);
        return holder.getConvertView();
    }


    public abstract void convert(ViewHolder holder, T model, int position);


}
