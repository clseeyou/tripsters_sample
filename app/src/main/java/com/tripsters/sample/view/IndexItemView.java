package com.tripsters.sample.view;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.tripsters.sample.R;

public class IndexItemView extends FrameLayout {

    private TextView mIndexTv;

    public IndexItemView(Context context) {
        super(context);

        init();
    }

    private void init() {
        View view = View.inflate(getContext(), R.layout.item_index, this);

        mIndexTv = (TextView) view.findViewById(R.id.tv_index);
    }

    public void update(String index, boolean hot) {
        mIndexTv.setText(index);

        if (hot) {
            mIndexTv.setTextColor(getContext().getResources().getColor(R.color.tb_blue));
        } else {
            mIndexTv.setTextColor(getContext().getResources().getColor(R.color.tb_light_grey));
        }
    }
}
