package io.gank.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import io.gank.R;
import io.gank.activity.ImageGalleryActivity;
import io.gank.model.GankModel;
import io.gank.view.PinchImageView;

public class ViewPagerAdapter extends PagerAdapter {

    private List<GankModel> mGankModels;
    private Context mContext;

    public ViewPagerAdapter(Context context, List<GankModel> gankModels) {
        mGankModels = gankModels;
        mContext = context;
    }

    @Override
    public int getCount() {
        if (mGankModels != null) {
            return mGankModels.size();
        }
        return 0;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_pager_item, null);
        PinchImageView img = (PinchImageView) view.findViewById(R.id.sdv_view);
        Glide.with(mContext).load(mGankModels.get(position).getUrl())
                .placeholder(R.drawable.icon_image_loading).error(R.drawable.icon_image_failure)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(img);
        container.addView(view);
        return view;
    }

}
