package com.tripsters.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tripsters.android.model.MediaInfo;
import com.tripsters.sample.composer.BaseComposer;
import com.tripsters.sample.composer.ComposerFactory;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.IntentUtils;
import com.tripsters.sample.util.LoginUser;
import com.tripsters.sample.util.Utils;
import com.tripsters.sample.view.SendLocationView;
import com.tripsters.sample.view.SendLocationView.OnLocationUpdateListener;
import com.tripsters.sample.view.SendPicItemView;
import com.tripsters.sample.view.SendPicItemView.OnPicClickListener;
import com.tripsters.sample.view.TitleBar;
import com.tripsters.sample.view.TitleBar.LeftType;
import com.tripsters.sample.view.TitleBar.RightType;

import java.util.ArrayList;
import java.util.List;

public class SendActivity extends BaseActivity {

    private class DetailTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mComposer.setContent(s.toString());

            mDetailNumTv.setText(Utils.getLimitNum(SendActivity.this, mComposer.getContent(),
                    mComposer.getMaxLenth()));
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }

    // Sub activity request code
    public static final int REQUEST_ADD_CITIES = 101;
    private static final int REQUEST_CODE_CAMERA = 201;
    private static final int REQUEST_CODE_GALLERY = 202;

    private InputMethodManager mInputMethodManager;

    private TitleBar mTitleBar;
    private EditText mDetailEt;
    private TextView mDetailNumTv;
    private TextView mTagsTv;
    private SendLocationView mSendLocationView;
    private TextView mConsumePointsTv;
    private LinearLayout mToolbarLt;
    private LinearLayout mPictureLt;
    private LinearLayout mPhotoLt;
    private SendPicItemView mPicLt;

    private BaseComposer mComposer;

    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        mComposer = ComposerFactory.create(this, getIntent());
        mComposer.setUid(LoginUser.getId());
        mComposer.setCountry(LoginUser.getCountry(this));

        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        mTitleBar = (TitleBar) findViewById(R.id.titlebar);
        mTitleBar.initTitleBar(LeftType.TEXT_CANCEL, mComposer.getTitle(this),
                RightType.TEXT_PUBLISH);
        mTitleBar.setLeftClick(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mComposer.back(SendActivity.this);
            }
        });
        if (mComposer.showAddCities()) {
            mTitleBar.setRightText(getString(R.string.send_question_next));
            mTitleBar.setRightClick(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mComposer.addCities(SendActivity.this);
                }
            });
        } else {
            mTitleBar.setRightClick(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    mComposer.send(SendActivity.this);
                }
            });
        }

        mDetailEt = (EditText) findViewById(R.id.et_detail);
        mDetailEt.setText(mComposer.getContent());
        mDetailEt.setSelection(mComposer.getContent().length());
        mDetailEt.setHint(mComposer.getHint(this));
        mDetailNumTv = (TextView) findViewById(R.id.tv_detail_num);
        mDetailNumTv.setVisibility(mComposer.getMaxLenth() >= 0 ? View.VISIBLE : View.GONE);
        mDetailEt.addTextChangedListener(new DetailTextWatcher());
        mDetailNumTv.setText(Utils.getLimitNum(this, mComposer.getContent(),
                mComposer.getMaxLenth()));

        mTagsTv = (TextView) findViewById(R.id.tv_tags);
        mTagsTv.setVisibility(View.GONE);
        mSendLocationView = (SendLocationView) findViewById(R.id.v_send_location);
        mSendLocationView.setOnLocationUpdateListener(new OnLocationUpdateListener() {

            @Override
            public void onLocationUpdate(String lat, String lng, String address) {
                mComposer.setLat(lat);
                mComposer.setLng(lng);
                mComposer.setAddress(address);
            }
        });
        mSendLocationView.setVisibility(mComposer.isLocationVisiable() ? View.VISIBLE : View.GONE);
        mConsumePointsTv = (TextView) findViewById(R.id.tv_consume_points);
        if (mComposer.getConsumePoints() == 0) {
            mConsumePointsTv.setVisibility(View.GONE);
        } else {
            mConsumePointsTv.setVisibility(View.VISIBLE);
            mConsumePointsTv.setText(getString(R.string.send_question_consume_points,
                    mComposer.getConsumePoints()));
        }

        mToolbarLt = (LinearLayout) findViewById(R.id.lt_toolbar);
        mToolbarLt.setVisibility(mComposer.isToolbarVisiable() ? View.VISIBLE : View.GONE);
        mPictureLt = (LinearLayout) findViewById(R.id.lt_picture);
        mPictureLt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startToMediaActivity();
            }
        });
        mPhotoLt = (LinearLayout) findViewById(R.id.lt_photo);
        mPhotoLt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startToCameraActivity();
            }
        });

        mPicLt = (SendPicItemView) findViewById(R.id.lt_pic);
        mPicLt.setVisibility(View.GONE);
        mPicLt.setOnPicClickListener(new OnPicClickListener() {

            @Override
            public void onPicDel(SendPicItemView view, MediaInfo mediaInfo, int position) {
                mPicLt.setVisibility(View.GONE);
                mComposer.setMediaInfos(new ArrayList<MediaInfo>());
            }

            @Override
            public void onPicClick(SendPicItemView view, MediaInfo mediaInfo, int position) {
//                Utils.startMediaImagesActivity(SendActivity.this, mComposer.getMediaInfos(),
//                        position);
            }
        });

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (Constants.Action.LOGIN_SUCCESS.equals(intent.getAction())) {
                    mComposer.setUid(LoginUser.getId());
                } else if (Constants.Action.LOGOUT_SUCCESS.equals(intent.getAction())) {
                    mComposer.setUid(LoginUser.getId());
                }
            }
        };
        IntentFilter intent = new IntentFilter();
        intent.addAction(Constants.Action.LOGIN_SUCCESS);
        intent.addAction(Constants.Action.LOGOUT_SUCCESS);
        registerReceiver(mReceiver, intent);
    }

    private void startToCameraActivity() {
        setInputMethodVisibility(false);

        IntentUtils.startToCameraActivity(this, REQUEST_CODE_CAMERA, false);
    }

    private void startToMediaActivity() {
        setInputMethodVisibility(false);

        IntentUtils.startToMediaActivity(this, REQUEST_CODE_GALLERY, 1, true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ADD_CITIES:
                    // Country country = data.getParcelableExtra(Constants.Extra.COUNTRY);
                    // ArrayList<City> cities =
                    // data.getParcelableArrayListExtra(Constants.Extra.CITIES);
                    // mComposer.setCountry(country);
                    // mComposer.setCities(cities);
                    finish();
                    break;
                case REQUEST_CODE_CAMERA:
                case REQUEST_CODE_GALLERY:
                    List<MediaInfo> resultMediaInfos =
                            data.getParcelableArrayListExtra(Constants.Extra.MEDIA_INFOS);
                    mComposer.setMediaInfos(resultMediaInfos);
                    mPicLt.setVisibility(View.VISIBLE);
                    mPicLt.update(resultMediaInfos.get(0), 0, false);
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setInputMethodVisibility(boolean visibility) {
        if ((mInputMethodManager != null) && (mDetailEt != null)) {
            if (visibility) {
                mInputMethodManager.showSoftInput(mDetailEt, 0);
            } else {
                if (mInputMethodManager.isActive(mDetailEt)) {
                    mInputMethodManager.hideSoftInputFromWindow(mDetailEt.getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mComposer.back(this);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }

        super.onDestroy();
    }
}
