package io.gank.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import io.gank.activity.ImageGalleryActivity;
import io.gank.R;
import io.gank.model.GankModel;

/**
 * Created by satan on 2015/8/15.
 */
public class WelfareAdapter extends RecyclerView.Adapter<WelfareAdapter.ViewHolder> {

    private Context mContext;
    private List<GankModel> mGankModels;

    public WelfareAdapter(Context context, List<GankModel> gankModels) {
        mContext = context;
        mGankModels = gankModels;
    }

    @Override
    public WelfareAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.welfare_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WelfareAdapter.ViewHolder holder, final int position) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.mSimpleDraweeView.getLayoutParams();
        layoutParams.height = (int) mContext.getResources().getDimension(R.dimen.image_height);
        Uri uri = Uri.parse(mGankModels.get(position).getUrl());
        holder.mSimpleDraweeView.setImageURI(uri);
        holder.mSimpleDraweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageGalleryActivity.launch(mContext, mGankModels, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGankModels.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        if (holder.mSimpleDraweeView.getController() != null) {
            holder.mSimpleDraweeView.getController().onDetach();
        }
        if (holder.mSimpleDraweeView.getTopLevelDrawable() != null) {
            holder.mSimpleDraweeView.getTopLevelDrawable().setCallback(null);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mSimpleDraweeView;

        public ViewHolder(View itemView) {
            super(itemView);
            mSimpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.sdv_view);
        }
    }
}
