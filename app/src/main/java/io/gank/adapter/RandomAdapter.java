package io.gank.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import io.gank.activity.ImageGalleryActivity;
import io.gank.R;
import io.gank.activity.WebViewActivity;
import io.gank.model.GankModel;

/**
 * Created by satan on 2015/8/15.
 */
public class RandomAdapter extends RecyclerView.Adapter<RandomAdapter.ViewHolder> {

    private Context mContext;
    private List<GankModel> mGankModels;
    private String mType;
    private static final int WELFARE_TYPE = 100;
    private static final int OTHER_TYPE = 101;


    public RandomAdapter(Context context, List<GankModel> gankModels, String type) {
        mContext = context;
        mGankModels = gankModels;
        mType = type;
    }

    public void setType(String type) {
        mType = type;
    }

    @Override
    public RandomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.random_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (mContext.getResources().getStringArray(R.array.type)[0].equals(mType)) {
            return WELFARE_TYPE;
        } else {
            return OTHER_TYPE;
        }
    }


    @Override
    public void onBindViewHolder(RandomAdapter.ViewHolder holder, final int position) {
        final GankModel gankModel = mGankModels.get(position);
        if (getItemViewType(position) == WELFARE_TYPE) {
            holder.mOther.setVisibility(View.GONE);
            holder.mSimpleDraweeView.setVisibility(View.VISIBLE);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.mSimpleDraweeView.getLayoutParams();
            layoutParams.height = (int) mContext.getResources().getDimension(R.dimen.random_image_height);
            Uri uri = Uri.parse(gankModel.getUrl());
            holder.mSimpleDraweeView.setImageURI(uri);
            holder.mSimpleDraweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageGalleryActivity.launch(mContext, mGankModels, position);
                }
            });
        } else {
            holder.mOther.setVisibility(View.VISIBLE);
            holder.mSimpleDraweeView.setVisibility(View.GONE);
            holder.mWho.setText("via: " + gankModel.getWho());
            holder.mDesc.setText(gankModel.getDesc());
            holder.mUrl.setText(gankModel.getUrl());
            holder.mType.setText(gankModel.getType());
            holder.mOther.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebViewActivity.launch(mContext, gankModel);
                }
            });
        }
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
        RelativeLayout mOther;
        TextView mWho;
        TextView mDesc;
        TextView mUrl;
        TextView mType;

        public ViewHolder(View itemView) {
            super(itemView);
            mSimpleDraweeView = (SimpleDraweeView) itemView.findViewById(R.id.sdv_view);
            mOther = (RelativeLayout) itemView.findViewById(R.id.rl_other);
            mWho = (TextView) itemView.findViewById(R.id.tv_who);
            mDesc = (TextView) itemView.findViewById(R.id.tv_desc);
            mUrl = (TextView) itemView.findViewById(R.id.tv_url);
            mType = (TextView) itemView.findViewById(R.id.tv_type);
        }
    }
}
