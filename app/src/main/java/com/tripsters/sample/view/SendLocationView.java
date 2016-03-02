package com.tripsters.sample.view;

import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tripsters.sample.R;
import com.tripsters.sample.task.GeoAddressTask;
import com.tripsters.sample.task.GeoAddressTask.GeoAddressTaskResult;
import com.tripsters.sample.util.LocationHelper;
import com.tripsters.sample.util.LocationHelper.LocationHelperListener;

public class SendLocationView extends LinearLayout {

    public interface OnLocationUpdateListener {
        void onLocationUpdate(String lat, String lng, String address);
    }

    private TextView mLocationNameTv;
    private ProgressBar mLocatingPb;
    private View mDividerView;
    private ImageView mDeleteIv;
    private GeoAddressTask mTask;
    private OnLocationUpdateListener mListener;

    public SendLocationView(Context context) {
        super(context);
        init();
    }

    public SendLocationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SendLocationView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        setBackgroundResource(R.drawable.bg_poi_tip);

        View view = View.inflate(getContext(), R.layout.view_send_location, this);
        mLocationNameTv = (TextView) view.findViewById(R.id.tv_location_name);
        mLocatingPb = (ProgressBar) view.findViewById(R.id.pb_locating);
        mDividerView = view.findViewById(R.id.v_divider);
        mDeleteIv = (ImageView) view.findViewById(R.id.iv_delete);
        mDeleteIv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setDefault();
            }
        });

        setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                location();
            }
        });
        setDefault();
    }

    public void setOnLocationUpdateListener(OnLocationUpdateListener listener) {
        this.mListener = listener;
    }

    public void location() {
        new LocationHelper(getContext(), new LocationHelperListener() {

            @Override
            public void onLocationStart() {
                if (mTask != null) {
                    mTask.cancel(true);
                }

                setLocating();
            }

            @Override
            public void onLocationEnd(Location location) {
                if (location == null) {
                    setLocationFailed();
                } else {
                    mTask = new GeoAddressTask(getContext(), location, new GeoAddressTaskResult() {

                        @Override
                        public void onTaskResult(Address result) {
                            if (result == null) {
                                setLocationFailed();
                            } else {
                                setLocationSuccess(result);
                            }
                        }
                    });

                    mTask.execute();
                }
            }
        }).startLocation();
    }

    private void setDefault() {
        setClickable(true);
        mLocationNameTv.setText(R.string.send_location_default);
        mLocatingPb.setVisibility(View.GONE);
        mDividerView.setVisibility(View.GONE);
        mDeleteIv.setVisibility(View.GONE);

        if (mListener != null) {
            mListener.onLocationUpdate("", "", "");
        }
    }

    private void setLocating() {
        setClickable(false);
        mLocationNameTv.setText(R.string.send_location_locating);
        mLocatingPb.setVisibility(View.VISIBLE);
        mDividerView.setVisibility(View.GONE);
        mDeleteIv.setVisibility(View.GONE);

        if (mListener != null) {
            mListener.onLocationUpdate("", "", "");
        }
    }

    private void setLocationFailed() {
        setClickable(true);
        mLocationNameTv.setText(R.string.send_location_failed);
        mLocatingPb.setVisibility(View.GONE);
        mDividerView.setVisibility(View.GONE);
        mDeleteIv.setVisibility(View.GONE);

        if (mListener != null) {
            mListener.onLocationUpdate("", "", "");
        }
    }

    private void setLocationSuccess(Address address) {
        setClickable(false);
        mLocationNameTv.setText(address.getAddressLine(0));
        mLocatingPb.setVisibility(View.GONE);
        mDividerView.setVisibility(View.VISIBLE);
        mDeleteIv.setVisibility(View.VISIBLE);

        if (mListener != null) {
            mListener.onLocationUpdate(String.valueOf(address.getLatitude()),
                    String.valueOf(address.getLongitude()), address.getAddressLine(0));
        }
    }
}
