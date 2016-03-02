package com.tripsters.sample.view;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tripsters.android.model.Country;
import com.tripsters.sample.R;
import com.tripsters.sample.util.ImageUtils;

public class CountryRecommandItemView extends FrameLayout {

    private ImageView mBgIv;
    private TextView mNameTv;
    private TextView mNameEnTv;
    private ImageView mTagIv;

    public CountryRecommandItemView(Context context) {
        super(context);
        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.item_country_recommand, this);
        mBgIv = (ImageView) view.findViewById(R.id.iv_bg);
        mNameTv = (TextView) view.findViewById(R.id.tv_name);
        mNameEnTv = (TextView) view.findViewById(R.id.tv_name_en);
        mTagIv = (ImageView) view.findViewById(R.id.iv_tag);
    }

    public void update(Country country, int position) {
        ImageUtils.setCountryPic(getContext(), mBgIv, country.getPic(), position);
        mNameTv.setText(country.getCountryNameCn());
        mNameEnTv.setText(country.getCountryNameEn());

        if (country.getHot() == Country.OPENED) {
            mTagIv.setVisibility(View.VISIBLE);
            mTagIv.setImageResource(R.drawable.icon_country_opened);
        } else if (country.getHot() == Country.OPERATED) {
            mTagIv.setVisibility(View.VISIBLE);
            mTagIv.setImageResource(R.drawable.icon_country_operated);
        } else {
            mTagIv.setVisibility(View.GONE);
        }
    }
}
