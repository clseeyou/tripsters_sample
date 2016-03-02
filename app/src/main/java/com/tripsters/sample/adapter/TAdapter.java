// package com.tripsters.android.adapter;
//
// import java.util.ArrayList;
// import java.util.List;
//
// import android.content.Context;
// import android.view.View;
// import android.view.ViewGroup;
// import android.widget.BaseAdapter;
//
// import com.tripsters.android.view.TListView;
//
// public abstract class TAdapter<Iv extends TAdapter.IItemViewUpdate<M>, M> extends BaseAdapter {
//
// public interface IItemViewUpdate<M> {
// public void updateView(M model);
// }
//
// protected Context mContext;
// private TListView mTbListView;
// private boolean mFirst;
// private boolean mLoading;
// private boolean mMore;
// private List<M> mList;
//
// public TAdapter(Context context) {
// mContext = context;
// }
//
// @Override
// public int getCount() {
// // if (mList == null || mList.isEmpty()) {
// // if (mFirst || mLoading) {
// // return 0;
// // } else {
// // return 1; // 空白提示
// // }
// // }
// //
// // if (mMore) {
// // return mList.size() + 1; // 更多
// // } else {
// // return mList.size();
// // }
// return mList.size();
// }
//
// @Override
// public M getItem(int position) {
// // if (mList == null || mList.isEmpty()) {
// // return null;
// // }
// //
// // if (position < mList.size()) {
// // return mList.get(position);
// // } else {
// // return null;
// // }
// return mList.get(position);
// }
//
// @Override
// public long getItemId(int position) {
// return position;
// }
//
// @Override
// public int getItemViewType(int position) {
// return 1;
// }
//
// @SuppressWarnings({"rawtypes", "unchecked"})
// @Override
// public View getView(int position, View convertView, ViewGroup parent) {
// // if (mList == null || mList.isEmpty()) {
// // if (mFirst || mLoading) {
// // return null;
// // } else {
// // return getEmptyView(mTbListView.getContext()); // 空白提示
// // }
// // }
// //
// // if (position < mList.size()) {
// // IItemViewUpdate iv;
// //
// // if (convertView != null && convertView instanceof IItemViewUpdate) {
// // iv = (IItemViewUpdate) convertView;
// // } else {
// // iv = getItemView();
// // }
// // iv.updateView(getItem(position));
// //
// // return (View) iv;
// // } else {
// // return getMoreView(mTbListView.getContext()); // 更多
// // }
// IItemViewUpdate iv;
//
// if (convertView != null && convertView instanceof IItemViewUpdate) {
// iv = (IItemViewUpdate) convertView;
// } else {
// iv = getItemView();
// }
// iv.updateView(getItem(position));
//
// return (View) iv;
// }
//
// abstract protected Iv getItemView();
//
// protected View getEmptyView(Context context) {
// return new View(context);
// }
//
// protected View getMoreView(Context context) {
// return new View(context);
// }
//
// public boolean getLoading() {
// return mLoading;
// }
//
// public void setLoading(boolean loading) {
// this.mLoading = loading;
// }
//
// public void setList(List<M> list) {
// this.mList = list;
// }
// }
package com.tripsters.sample.adapter;

import android.widget.BaseAdapter;

import java.util.List;

public abstract class TAdapter<M> extends BaseAdapter {

    protected List<M> mList;

    public void notifyData(List<M> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void notifyData(List<M> list, boolean notify) {
        mList = list;
        if (notify) {
            notifyDataSetChanged();
        }
    }

    public List<M> getList() {
        return mList;
    }
}
