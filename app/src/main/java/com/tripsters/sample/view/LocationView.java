package com.tripsters.sample.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripsters.sample.R;

public class LocationView extends LinearLayout {

    private String mCountry;
    private String mAddress;

    private TextView mPoiNameTv;

    public LocationView(Context context) {
        super(context);
        init();
    }

    public LocationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LocationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        // setBackgroundResource(R.drawable.bg_poi_tip);

        View view = View.inflate(getContext(), R.layout.view_location, this);
        mPoiNameTv = (TextView) view.findViewById(R.id.tv_location_name);
        // setOnClickListener(new OnClickListener() {
        //
        // @Override
        // public void onClick(View v) {
        // Utils.startPoiDetailActivity(getContext(), mPoi);
        // }
        // });
    }

    public void update(String country, String address) {
        mCountry = country;
        mAddress = address;

        setLocationName();
    }

    private void setLocationName() {
        StringBuilder text = new StringBuilder();

        if (TextUtils.isEmpty(mCountry)) {
            if (TextUtils.isEmpty(mAddress)) {
                setVisibility(View.GONE);
            } else {
                setVisibility(View.VISIBLE);

                text.append(mAddress);
            }
        } else {
            setVisibility(View.VISIBLE);

            if (TextUtils.isEmpty(mAddress)) {
                // text.append(getResources().getString(R.string.location_country_text, mCountry));
                text.append(mCountry);
            } else {
                text.append(mAddress);
            }
        }

        // if (TextUtils.isEmpty(mCountry)) {
        // mPoiNameTv.setText(text);
        // } else {
        // int start = text.indexOf(mCountry);
        //
        // Spannable spannable = new SpannableStringBuilder(text);
        // spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.tb_blue)),
        // start, start + mCountry.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //
        // mPoiNameTv.setText(spannable);
        // }
        mPoiNameTv.setText(text);
    }
}
