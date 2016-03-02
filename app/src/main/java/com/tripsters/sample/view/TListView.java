package com.tripsters.sample.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.tripsters.sample.R;
import com.tripsters.sample.adapter.TAdapter;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.ErrorToast;
import com.tripsters.sample.view.TEmptyView.Type;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TListView extends PullDownView {

    public static final int UNLOAD = 0;
    public static final int RELOAD = 1;
    public static final int LOADMORE = 2;
    public static final int NOMORE = 3;

    public interface ListUpdateListener {
        public void loadPageData(int page);
    }

    private ListView mConentLv;
    private TLoadMoreView mLoadMoreView;
    private TEmptyView mEmptyView;
    private TAdapter<?> mAdapter;

    private int mCurrentPage = 1;
    private int mRequestPage;
    private int mTotalPage;

    private boolean mLoadMoreEnable = true;
    private boolean mLoadMoreAdded;
    private boolean mEmptyAdded;
    private int mEmptyHeight;

    @SuppressWarnings("rawtypes")
    private List mList = new ArrayList();
    private int mLoadType; // 0:未加载 1:重新加载 2:加载更多

    private ListUpdateListener mListener;

    public TListView(Context context) {
        super(context);
        initView();
    }

    public TListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        View view = View.inflate(getContext(), R.layout.view_tb_list, this);
        mConentLv = (ListView) view.findViewById(R.id.lv_content);
        setOnScrollListener(null); // 自动加载更多
        mConentLv.setHeaderDividersEnabled(false);
        mConentLv.setFooterDividersEnabled(false);
        mLoadMoreView = new TLoadMoreView(getContext(), new TLoadMoreView.OnLoadMoreListener() {

            @Override
            public void loadMore() {
                TListView.this.loadMore();
            }
        });
        mEmptyView = new TEmptyView(getContext());
    }

    public void addHeaderView(View v) {
        mConentLv.addHeaderView(v);
    }

    public void addFooterView(View v) {
        mConentLv.addFooterView(v);
    }

    public void setAdapter(TAdapter<?> adapter, ListUpdateListener listener) {
        mAdapter = adapter;
        mListener = listener;
        // mConentLv.addFooterView(mLoadMoreView, null, false);
        mConentLv.addFooterView(mEmptyView, null, false);
        mEmptyAdded = true;
        mConentLv.setAdapter(adapter);

        setUpdateHandle(new UpdateHandle() {

            @Override
            public void onUpdate() {
                reload();
            }
        });

        mConentLv.removeFooterView(mEmptyView);
        mEmptyAdded = false;
        // mConentLv.addFooterView(mLoadMoreView, null, false);
        // mLoadMoreView.setVisibility(View.GONE);
        // mConentLv.addFooterView(mEmptyView, null, false);
        // mEmptyView.setVisibility(View.GONE);

        // adapter.setTbListView(this);
        // mConentLv.setOnScrollListener(new OnScrollListener() {
        //
        // @Override
        // public void onScrollStateChanged(AbsListView view, int scrollState) {
        // // TODO Auto-generated method stub
        //
        // }
        //
        // @Override
        // public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
        // int totalItemCount) {
        // if (visibleItemCount == totalItemCount) {
        // int height = 0;
        //
        // for (int i = 0; i < mConentLv.getChildCount() - 1; i++) {
        // View childView = mConentLv.getChildAt(i);
        // height += childView.getHeight();
        // }
        //
        // mEmptyHeight = getMeasuredHeight() - height;
        // mLoadMoreView.setVisibility(View.VISIBLE);
        // mLoadMoreView.setLayoutParams(new AbsListView.LayoutParams(
        // AbsListView.LayoutParams.MATCH_PARENT, mEmptyHeight));
        // } else {
        // mLoadMoreView.setVisibility(View.GONE);
        // }
        // }
        // });
    }

    public void setDividerHeight(int height) {
        mConentLv.setDividerHeight(height);
    }

    public void setDivider(Drawable divider) {
        mConentLv.setDivider(divider);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mConentLv.setOnItemClickListener(listener);
    }

    public void OnItemLongClickListener(OnItemLongClickListener listener) {
        mConentLv.setOnItemLongClickListener(listener);
    }

    public void setOnScrollListener(final OnScrollListener listener) {
        mConentLv.setOnScrollListener(new OnScrollListener() {

            private boolean retrievable;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE && retrievable) {
                    retrievable = false;

                    if (mLoadMoreAdded && mLoadMoreView.getType() == TLoadMoreView.TYPE_LOADMORE) {
                        loadMore();
                    }
                }

                if (listener != null) {
                    listener.onScrollStateChanged(view, scrollState);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    retrievable = true;
                } else {
                    retrievable = false;
                }

                if (listener != null) {
                    listener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }
            }
        });
    }

    public void setSelection(int position) {
        mConentLv.setSelection(position);
    }

    public void reload() {
        if (mLoadType == UNLOAD) {
            mLoadType = RELOAD;
            mRequestPage = 1;

            // mLoadMoreView.setVisibility(View.GONE);
            // mEmptyView.setVisibility(View.GONE);
            mConentLv.removeFooterView(mLoadMoreView);
            mLoadMoreAdded = false;
            mConentLv.removeFooterView(mEmptyView);
            mEmptyAdded = false;

            if (mListener != null) {
                mListener.loadPageData(mRequestPage);
            }
        }
    }

    public void loadMore() {
        if (mLoadType == UNLOAD) {
            mLoadType = LOADMORE;
            mRequestPage = mCurrentPage + 1;

            // mLoadMoreView.setVisibility(View.GONE);
            // mEmptyView.setVisibility(View.GONE);
            // mConentLv.removeFooterView(mLoadMoreView);
            mLoadMoreView.load(TLoadMoreView.TYPE_LOADING);
            mConentLv.removeFooterView(mEmptyView);
            mEmptyAdded = false;

            if (mListener != null) {
                mListener.loadPageData(mRequestPage);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void endLoadSuccess(List<?> list) {
        mCurrentPage = mRequestPage;

        if (list.size() < Constants.PAGE_COUNT) {
            mTotalPage = mCurrentPage;
        } else {
            mTotalPage = mCurrentPage + 1;
        }

        if (mLoadType == RELOAD) {
            mList = list;
        } else {
            mList.addAll(list);
        }

        mAdapter.notifyData(mList);
        endUpdate(new Date());

        endLoad();
    }

    public void endLoadFailed() {
        endUpdate(new Date());

        endLoad();

        ErrorToast.getInstance().showNetError();
    }

    public void endLoadFailed(String message) {
        // endUpdate(new Date());
        //
        // endLoad();
        //
        // ErrorToast.getInstance().showErrorMessage(message);
        endLoadFailed(message, true);
    }

    // 兼容接口
    public void endLoadFailed(String message, boolean showToast) {
        endUpdate(new Date());

        endLoad();

        if (showToast) {
            ErrorToast.getInstance().showErrorMessage(message);
        }
    }

    private void endLoad() {
        mLoadType = UNLOAD;

        post(new Runnable() {

            @Override
            public void run() {
                mConentLv.removeFooterView(mLoadMoreView);
                mLoadMoreAdded = false;
                mConentLv.removeFooterView(mEmptyView);
                mEmptyAdded = false;

                if (mCurrentPage < mTotalPage) {
                    if (mLoadMoreEnable) {
                        // mLoadMoreView.setVisibility(View.VISIBLE);
                        mConentLv.addFooterView(mLoadMoreView, null, false);
                        mLoadMoreAdded = true;
                        mLoadMoreView.load(TLoadMoreView.TYPE_LOADMORE);
                    }
                } else {
                    if (mList.isEmpty()) {
                        // mLoadMoreView.setVisibility(View.GONE);
                        // mEmptyView.setVisibility(View.VISIBLE);
                        mConentLv.addFooterView(mEmptyView, null, false);
                        mEmptyAdded = true;
                        mEmptyHeight = getEmptyHeight();
                        mEmptyView.setLayoutParams(new AbsListView.LayoutParams(
                                AbsListView.LayoutParams.MATCH_PARENT, mEmptyHeight));
                    } else {
                        if (mLoadMoreEnable) {
                            // mLoadMoreView.setVisibility(View.VISIBLE);
                            mConentLv.addFooterView(mLoadMoreView, null, false);
                            mLoadMoreAdded = true;
                            mLoadMoreView.load(TLoadMoreView.TYPE_NOMORE);
                        }
                    }
                }
            }
        });
    }

    private int getEmptyHeight() {
        int height = getMeasuredHeight();

        if (mConentLv != null && mConentLv.getChildCount() > 0) {
            for (int i = 0; i < mConentLv.getChildCount(); i++) {
                height -= mConentLv.getChildAt(i).getMeasuredHeight();
            }
        }

        return height;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (mEmptyAdded && mEmptyHeight == 0) {
            mEmptyHeight = getMeasuredHeight();
            mEmptyView.setLayoutParams(new AbsListView.LayoutParams(
                    AbsListView.LayoutParams.MATCH_PARENT, mEmptyHeight));
        }
    }

    public int getRequestPage() {
        return mRequestPage;
    }

    public void firstUpdate() {
        update();
        reload();
    }

    public void setEmptyType(Type type) {
        mEmptyView.setType(type);
    }

    @SuppressWarnings("unchecked")
    public void clear() {
        mCurrentPage = 1;
        mRequestPage = 0;
        mTotalPage = 0;

        mList.clear();
        mAdapter.notifyData(mList);
    }

    public void setLoadType(int loadType) {
        this.mLoadType = loadType;
    }

    public void setLoadMoreEnable(boolean loadMoreEnable) {
        this.mLoadMoreEnable = loadMoreEnable;
    }
}
