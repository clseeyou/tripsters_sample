package com.tripsters.sample.view;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tripsters.sample.R;
import com.tripsters.sample.model.PinyinCity;

public class CityItemView extends FrameLayout {

    public interface OnCityClickListener {
        void onCityClick(CityItemView view, PinyinCity city);
    }

    private TextView mCityNameTv;
    private View mDividerView;
    private CheckBox mCityCb;

    private OnCityClickListener mListener;

    private PinyinCity mCity;

    public CityItemView(Context context) {
        super(context);
        init();
    }

    public CityItemView(Context context, OnCityClickListener listener) {
        this(context);

        this.mListener = listener;
    }

    private void init() {
        setBackgroundResource(R.drawable.bg_item);

        View view = View.inflate(getContext(), R.layout.item_city, this);
        mCityNameTv = (TextView) view.findViewById(R.id.tv_city_name);
        mDividerView = view.findViewById(R.id.v_divider);
        mCityCb = (CheckBox) view.findViewById(R.id.cb_city);
        mCityCb.setClickable(false);

        setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onCityClick(CityItemView.this, mCity);
                }
            }
        });
    }

    public void update(PinyinCity city, int position, boolean checked) {
        mCity = city;

        mCityNameTv.setText(mCity.getCityNameCn());
        mCityCb.setChecked(checked);
    }

    public void setCheckVisiable(boolean checkVisible) {
        mCityCb.setVisibility(checkVisible ? View.VISIBLE : View.INVISIBLE);
    }

    public void showDivider(boolean showDivider) {
        mDividerView.setVisibility(showDivider ? View.VISIBLE : View.INVISIBLE);
    }

    public void setNameSelected(boolean selected) {
        mCityNameTv.setSelected(selected);
    }

    public boolean isCityChecked() {
        return mCityCb.isChecked();
    }

    public void setCityChecked(boolean checked) {
        mCityCb.setChecked(checked);
    }
}
