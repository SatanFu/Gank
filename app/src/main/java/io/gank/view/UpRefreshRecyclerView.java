package io.gank.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import io.gank.R;

/**
 * Created by Administrator on 2015/8/16.
 */
public class UpRefreshRecyclerView extends RecyclerView {

    private View mFooterView;
    private boolean isLoading = false;
    private UpRefreshListener mUpRefreshListener;
    private LinearLayout llFooterContent;
    private LayoutManager mLayoutManager;

    public UpRefreshRecyclerView(Context context) {
        this(context, null);
    }

    public UpRefreshRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UpRefreshRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mFooterView = LayoutInflater.from(context).inflate(R.layout.listview_footer, null);
        llFooterContent = (LinearLayout) mFooterView.findViewById(R.id.ll_footer_content);
        this.addOnScrollListener(new MyOnScrollListener());
        updateFooterHeight(0);
    }

    public void setUpRefreshListener(UpRefreshListener listener) {
        mUpRefreshListener = listener;
    }

    public void onRefreshFinish() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                isLoading = false;
                updateFooterHeight(0);
            }
        }, 1000);

    }

    class MyOnScrollListener extends OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            mLayoutManager = recyclerView.getLayoutManager();
            int visibleItemCount = 0;
            int[] firstVisibleItems = {0, 0};
            int[] lastVisibleItems = {0, 0};
            if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                firstVisibleItems = ((StaggeredGridLayoutManager) mLayoutManager).findFirstVisibleItemPositions(null);
                lastVisibleItems = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);

                visibleItemCount = (lastVisibleItems[1] - firstVisibleItems[0]) + 1;

            }
            int totalItemCount = getAdapter().getItemCount();

            if (totalItemCount > visibleItemCount) {
                int lastItemId = Math.max(lastVisibleItems[0], lastVisibleItems[1]); // 获取当前屏幕最后Item的ID
                if ((lastItemId + 1) == totalItemCount) { // 达到数据的最后一条记录
                    if (!isLoading) {
                        isLoading = true;
                        if (totalItemCount > 0) {
                            mUpRefreshListener.onUpRefresh();
                        }
                    }
                }
            }
        }
    }

    public void updateFooterHeight(int headerHeight) {
        llFooterContent.getLayoutParams().height = headerHeight;
        llFooterContent.requestLayout();
    }

    public interface UpRefreshListener {
        void onUpRefresh();
    }
}
