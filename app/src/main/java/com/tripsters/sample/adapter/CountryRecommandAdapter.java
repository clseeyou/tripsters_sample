package com.tripsters.sample.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.tripsters.android.model.Country;
import com.tripsters.sample.view.CountryRecommandItemView;

public class CountryRecommandAdapter extends TAdapter<Country> {

    private Context mContext;

    public CountryRecommandAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        }

        return mList.size();
    }

    @Override
    public Country getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CountryRecommandItemView itemView;

        if (convertView == null) {
            itemView = new CountryRecommandItemView(mContext);
        } else {
            itemView = (CountryRecommandItemView) convertView;
        }

        itemView.update(getItem(position), position);

        return itemView;
    }
}
