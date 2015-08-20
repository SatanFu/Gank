package io.gank.fragment.type;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import io.gank.databinding.ItemTypeBinding;
import io.gank.model.GankModel;

/**
 * Created by satan on 2015/8/20.
 */
public class DataBindingAdapter extends BaseAdapter {

    private Context mContext;
    private List<GankModel> mGankModels;

    public DataBindingAdapter(Context mContext, List<GankModel> mGankModels) {
        this.mContext = mContext;
        this.mGankModels = mGankModels;
    }

    @Override
    public int getCount() {
        return mGankModels.size();
    }

    @Override
    public Object getItem(int position) {
        return mGankModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemTypeBinding binding = ItemTypeBinding.inflate(LayoutInflater.from(mContext), parent, false);
        binding.setGank(mGankModels.get(position));
        return binding.getRoot();
    }
}
