package com.tripsters.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.tripsters.android.model.Country;
import com.tripsters.android.model.CountryList;
import com.tripsters.android.task.GetSupportCountryTask;
import com.tripsters.sample.adapter.CountryRecommandAdapter;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.ErrorToast;
import com.tripsters.sample.util.LoginUser;
import com.tripsters.sample.util.Utils;
import com.tripsters.sample.view.TitleBar;
import com.tripsters.sample.view.TitleBar.LeftType;
import com.tripsters.sample.view.TitleBar.RightType;

public class CountryRecommandActivity extends BaseActivity {

    public static final int TYPE_SPLASH = 1;
    public static final int TYPE_HOME = 2;
    public static final int TYPE_SELECT = 3;

    private int mFrom;

    private TitleBar mTitleBar;
    private ListView mCountryLv;
    private CountryRecommandAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_country_recommand);

        mFrom = getIntent().getIntExtra(Constants.Extra.TYPE, TYPE_HOME);

        mTitleBar = (TitleBar) findViewById(R.id.titlebar);
        if (mFrom == TYPE_SPLASH) {
            mTitleBar.initTitleBar(LeftType.NONE, R.string.titlebar_selected_country,
                    RightType.TEXT_JUMP);
            mTitleBar.setRightClick(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    changeCountry(Utils.getThailaid());

                    finish();
                }
            });
        } else {
            mTitleBar.initTitleBar(LeftType.ICON_BACK, R.string.titlebar_selected_country,
                    RightType.NONE);
            mTitleBar.setLeftClick(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        mCountryLv = (ListView) findViewById(R.id.list);

        mAdapter = new CountryRecommandAdapter(this);
        mCountryLv.setAdapter(mAdapter);

        mCountryLv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Country country = mAdapter.getItem(position);

                if (mFrom == TYPE_SELECT) {
                    selectCountry(country);
                } else {
                    changeCountry(country);
                }
            }

        });

        getData();
    }

    private void changeCountry(Country country) {
        LoginUser.setCountry(this, country);

        Intent intent1 = new Intent().setClass(getApplicationContext(), MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);

        Intent intent2 = new Intent();
        intent2.setAction(Constants.Action.CHANGE_LOCATION);
        sendBroadcast(intent2);
        finish();
    }

    private void selectCountry(Country country) {
        Intent intent = new Intent();
        intent.putExtra(Constants.Extra.COUNTRY, country);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void getData() {
        showProgress();

        new GetSupportCountryTask(TripstersApplication.mContext,
                new GetSupportCountryTask.GetSupportCountryTaskResult() {
            @Override
            public void onTaskResult(CountryList result) {
                dismissProgress();

                if (ErrorToast.getInstance().checkNetResult(result)) {
                    mAdapter.notifyData(result.getList());
                }
            }
        }).execute();
    }
}
