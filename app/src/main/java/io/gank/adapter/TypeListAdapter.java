package io.gank.adapter;

import android.content.Context;

import java.util.List;

import io.gank.R;
import io.gank.model.GankModel;
import io.gank.util.ViewHolder;

/**
 * Created by Administrator on 2015/8/15.
 */
public class TypeListAdapter extends CommonAdapter<GankModel> {


    public TypeListAdapter(Context context, List<GankModel> gankModels) {
        super(context, gankModels, R.layout.item_type);
    }


    @Override
    public void convert(ViewHolder holder, GankModel model, int position) {
        holder.setText(R.id.tv_who, "via: " + model.getWho())
                .setText(R.id.tv_desc, model.getDesc())
                .setText(R.id.tv_url, model.getUrl())
                .setText(R.id.tv_time, model.getPublishedAt().substring(0, 10));
    }
}
