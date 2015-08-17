package io.gank.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

import io.gank.R;

/**
 * Created by satan on 2015/8/15.
 */
public class UpRefreshListView extends ListView {

    private View mFooterView;
    private boolean isLoading = false;
    private UpRefreshListener mUpRefreshListener;
    private LinearLayout llFooterContent;

    public UpRefreshListView(Context context) {
        this(context, null);
    }

    public UpRefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UpRefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mFooterView = LayoutInflater.from(context).inflate(R.layout.listview_footer, this, false);
        llFooterContent = (LinearLayout) mFooterView.findViewById(R.id.ll_footer_content);
        addFooterView(mFooterView);
        this.setOnScrollListener(new MyOnScrollListener());
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

    class MyOnScrollListener implements OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(final AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            if (totalItemCount > visibleItemCount) {
                int lastItemId = getLastVisiblePosition(); // 获取当前屏幕最后Item的ID
                if ((lastItemId + 1) == totalItemCount) { // 达到数据的最后一条记录
                    if (!isLoading) {
                        isLoading = true;
                        updateFooterHeight((int) getResources().getDimension(R.dimen.footer_height));
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
