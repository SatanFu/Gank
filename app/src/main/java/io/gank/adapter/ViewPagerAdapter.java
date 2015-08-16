package io.gank.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import io.gank.R;
import io.gank.model.GankModel;

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
        SimpleDraweeView img = (SimpleDraweeView) view.findViewById(R.id.sdv_view);
        img.setImageURI(Uri.parse(mGankModels.get(position).getUrl()));
        container.addView(view);
        return view;
    }

}
