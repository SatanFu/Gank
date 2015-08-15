package io.gank.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import io.gank.R;

/**
 * Created by Administrator on 2015/8/15.
 */
public class UpRefreshListView extends ListView {

    private View mFooterView;
    private UpRefreshListView mUpRefreshListView;
    private boolean isLoading = false;
    private UpRefreshListener mUpRefreshListener;

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
        mUpRefreshListView = this;
        mFooterView = LayoutInflater.from(context).inflate(R.layout.listview_footer, null);
        this.setOnScrollListener(new MyOnScrollListener());
    }

    public void setUpRefreshListener(UpRefreshListener listener) {
        mUpRefreshListener = listener;
    }

    public void onRefreshFinish() {
        isLoading = false;
        mUpRefreshListView.removeFooterView(mFooterView);
    }

    class MyOnScrollListener implements OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(final AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            Logger.e(firstVisibleItem + "----" + visibleItemCount + "----" + totalItemCount);
            if (totalItemCount > visibleItemCount) {
                int lastItemId = getLastVisiblePosition(); // 获取当前屏幕最后Item的ID
                Logger.e(lastItemId + "---");
                if ((lastItemId + 1) == totalItemCount) { // 达到数据的最后一条记录
//                    System.out.println("data.size111");
                    if (!isLoading) {
                        isLoading = true;
                        addFooterView(mFooterView);
//                        mUpRefreshListView.addFooterView(mFooterView);
                        if (totalItemCount > 0) {
                            mUpRefreshListener.onUpRefresh();
                        }
                    }
                }
            }

        }
    }

    public interface UpRefreshListener {
        void onUpRefresh();
    }
}
