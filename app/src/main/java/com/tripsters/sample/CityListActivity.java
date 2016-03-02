package com.tripsters.sample;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.tripsters.android.model.City;
import com.tripsters.android.model.CityList;
import com.tripsters.android.model.Country;
import com.tripsters.android.task.GetSupportCityTask;
import com.tripsters.sample.adapter.IndexCityListAdapter;
import com.tripsters.sample.adapter.IndexCityListAdapter.IndexCity;
import com.tripsters.sample.composer.BaseComposer;
import com.tripsters.sample.composer.BaseComposer.ComposerType;
import com.tripsters.sample.model.PinyinCity;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.ErrorToast;
import com.tripsters.sample.util.Utils;
import com.tripsters.sample.view.CityItemView;
import com.tripsters.sample.view.CityItemView.OnCityClickListener;
import com.tripsters.sample.view.LetterIndexBar;
import com.tripsters.sample.view.LetterIndexBar.OnIndexChangeListener;
import com.tripsters.sample.view.TitleBar;
import com.tripsters.sample.view.TitleBar.LeftType;
import com.tripsters.sample.view.TitleBar.RightType;

import java.util.ArrayList;
import java.util.List;

public class CityListActivity extends BaseActivity implements OnIndexChangeListener {

    public static final int REQUEST_COUNTRY_CODE = 100;

    private boolean mChangeCountry;
    private int mMaxCount;
    private Country mCountry;
    private List<PinyinCity> mAddCities;
    private List<PinyinCity> mCities;

    private TitleBar mTitleBar;
    private ListView mCityLv;
    private LetterIndexBar mLetterIndexBar;
    private IndexCityListAdapter mAdapter;

    private boolean mOpenedHidden;
    private BaseComposer mComposer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citylist);

        mChangeCountry = getIntent().getBooleanExtra(Constants.Extra.CHANGE_COUNTRY, true);
        mMaxCount =
                getIntent().getIntExtra(Constants.Extra.MAX_COUNT,
                        Constants.QUESTION_CITY_MAX_COUNT);
        mOpenedHidden = getIntent().getBooleanExtra(Constants.Extra.CIYT_OPENED_HIDDEN, false);
        mComposer = getIntent().getParcelableExtra(Constants.Extra.COMPOSER);

        List<City> cities;
        if (mComposer == null) {
            mCountry = getIntent().getParcelableExtra(Constants.Extra.COUNTRY);
            cities = getIntent().getParcelableArrayListExtra(Constants.Extra.CITIES);
        } else {
            mCountry = mComposer.getCountry();
            cities = mComposer.getCities();
        }
        mAddCities = Utils.initPinyin(TripstersApplication.mContext, cities);

        if (mCountry == null) {
            finish();
            return;
        }

        if (mAddCities == null) {
            mAddCities = new ArrayList<PinyinCity>();
        }

        mTitleBar = (TitleBar) findViewById(R.id.titlebar);
        if (mComposer != null && mComposer.getType() == ComposerType.SENT_QUESTION) {
            mTitleBar.initTitleBar(LeftType.ICON_BACK, mCountry.getCountryNameCn(),
                    RightType.TEXT_PUBLISH);
        } else/* if (mComposer.getType() == ComposerType.SEND_BLOG) */ {
            mTitleBar.initTitleBar(LeftType.ICON_BACK, mCountry.getCountryNameCn(),
                    RightType.TEXT_DONE);
        }
        if (mChangeCountry) {
            mTitleBar.setTitleClick(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    selectCountry();
                }
            });
        }
        mTitleBar.setLeftClick(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
        mTitleBar.setRightClick(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mComposer != null && mComposer.getType() == ComposerType.SENT_QUESTION) {
                    mComposer.setCountry(mCountry);
                    List<City> cities = new ArrayList<City>();
                    cities.addAll(mAddCities);
                    mComposer.setCities(cities);
                    mComposer.sendWithCities(CityListActivity.this);
                } else/* if (mComposer.getType() == ComposerType.SEND_BLOG) */ {
                    Intent intent = new Intent();
                    intent.putExtra(Constants.Extra.COUNTRY, mCountry);
                    ArrayList<City> cities = new ArrayList<City>();
                    cities.addAll(mAddCities);
                    intent.putParcelableArrayListExtra(Constants.Extra.CITIES, cities);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        mCityLv = (ListView) findViewById(R.id.list);

        mAdapter = new IndexCityListAdapter(this, mAddCities, mOpenedHidden);
        mAdapter.setOnCityClickListener(new OnCityClickListener() {

            @Override
            public void onCityClick(CityItemView view, PinyinCity city) {
                if (view.isCityChecked()) {
                    view.setCityChecked(false);

                    mAddCities.remove(city);
                } else {
                    if (mAddCities.size() < mMaxCount) {
                        view.setCityChecked(true);

                        if (!mAddCities.contains(city)) {
                            mAddCities.add(city);
                        }
                    } else {
                        ErrorToast.getInstance().showErrorMessage(
                                getString(R.string.titlebar_selected_city, mMaxCount));
                    }
                }

                mAdapter.notifyDataSetChanged();
            }
        });
        mCityLv.setAdapter(mAdapter);

        String[] indexLetter;
        if (mOpenedHidden) {
            indexLetter = new String[27];
            indexLetter[26] = "#";
            for (int i = 0; i < indexLetter.length - 1; i++) {
                indexLetter[i] = String.valueOf((char) ('A' + i));
            }
        } else {
            indexLetter = new String[28];
            indexLetter[0] = "↑";
            indexLetter[27] = "#";
            for (int i = 0; i < indexLetter.length - 2; i++) {
                indexLetter[i + 1] = String.valueOf((char) ('A' + i));
            }
        }

        mLetterIndexBar = (LetterIndexBar) findViewById(R.id.libIndex);
        mLetterIndexBar.setIndexChangeListener(this);
        mLetterIndexBar.setIndexLetter(indexLetter);

        getData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_COUNTRY_CODE) {
            if (resultCode == RESULT_OK) {
                Country country = data.getParcelableExtra(Constants.Extra.COUNTRY);

                if (!mCountry.equals(country)) {
                    mCountry = country;

                    mTitleBar.setTitle(mCountry.getCountryNameCn());
                    mLetterIndexBar.setVisibility(View.GONE);
                    mAddCities.clear();
                    mAdapter.clear();
                    mAdapter.notifyDataSetChanged();

                    getData();
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getData() {
        if (TextUtils.isEmpty(mCountry.getCountryCode())) {
            return;
        }

        showProgress();

        new GetSupportCityTask(TripstersApplication.mContext,
                mCountry.getCountryCode(), new GetSupportCityTask.GetSupportCityTaskResult() {
            @Override
            public void onTaskResult(CityList result) {
                dismissProgress();

                if (ErrorToast.getInstance().checkNetResult(result)) {
                    mCities = Utils.initPinyin(TripstersApplication.mContext, result.getList());

                    notifyData();
                }
            }
        }).execute();
    }

    private void notifyData() {
        if (mCities.size() > 10) {
            mLetterIndexBar.setVisibility(View.VISIBLE);
        } else {
            mLetterIndexBar.setVisibility(View.GONE);
        }

        mAdapter.filter(mCities);
        mAdapter.notifyDataSetChanged();
    }

    private void selectCountry() {
        Intent intent = new Intent(this, CountryRecommandActivity.class);
        intent.putExtra(Constants.Extra.TYPE, CountryRecommandActivity.TYPE_SELECT);
        startActivityForResult(intent, REQUEST_COUNTRY_CODE);
    }

    @Override
    public void onIndexChange(int index) {
        if (index < 0) {
            return;
        }

        int followIndex = mAdapter.getIndex(new IndexCity(index, -1), index);
        if (followIndex != -1) {
            mCityLv.setSelection(followIndex);
        }
    }
}
