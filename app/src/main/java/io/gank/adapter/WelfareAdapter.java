package io.gank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import io.gank.R;
import io.gank.activity.ImageGalleryActivity;
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
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.mImageView.getLayoutParams();
        layoutParams.height = (int) mContext.getResources().getDimension(R.dimen.image_height);
        String url = mGankModels.get(position).getUrl();
        Glide.with(mContext).load(url).placeholder(R.color.stay_color).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(holder.mImageView);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
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


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.sdv_view);
        }
    }
}
