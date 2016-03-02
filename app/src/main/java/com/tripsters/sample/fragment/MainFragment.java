package com.tripsters.sample.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.tripsters.sample.CountryRecommandActivity;
import com.tripsters.sample.R;
import com.tripsters.sample.SendActivity;
import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.LoginUser;
import com.tripsters.sample.view.TitleBar;
import com.tripsters.sample.view.TitleBar.LeftType;
import com.tripsters.sample.view.TitleBar.RightType;

public class MainFragment extends BaseFragment {

    private class MainAdapter extends FragmentStatePagerAdapter {

        public MainAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (mAllQuestionListFragment == null) {
                        mAllQuestionListFragment = new QuestionListFragment();
                    }

                    return mAllQuestionListFragment;
                case 1:
                    if (mAppQuestionListFragment == null) {
                        mAppQuestionListFragment = new AppQuestionListFragment();
                    }

                    return mAppQuestionListFragment;
            }

            return null;
        }
    }

    private class MainPageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                mTitleBar.setTitleLeftSelected(true);
                mTitleBar.setTitleRightSelected(false);
            } else if (position == 1) {
                mTitleBar.setTitleLeftSelected(false);
                mTitleBar.setTitleRightSelected(true);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {}
    }

    private TitleBar mTitleBar;
    private QuestionListFragment mAllQuestionListFragment = null;
    private AppQuestionListFragment mAppQuestionListFragment = null;

    private MainAdapter mAdapter;
    private ViewPager mPager;
    private MainPageChangeListener mMainPageChangeListener;

    private BroadcastReceiver mReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mTitleBar = (TitleBar) v.findViewById(R.id.titlebar);
        mTitleBar.initTitleBar(LeftType.NONE, R.string.title_all, R.string.title_app,
                RightType.ICON_SEND_QUESTION);
        mTitleBar.setLeftArrowVisible(true);
        if (LoginUser.getCountry(getActivity()) != null) {
            mTitleBar.setLeftText(LoginUser.getCountry(getActivity()).getCountryNameCn());
        }
        mTitleBar.setTitleLeftClick(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(0);
            }
        });
        mTitleBar.setTitleRightClick(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(1);
            }
        });
        mTitleBar.setLeftClick(new OnClickListener() {

            @Override
            public void onClick(View v) {
                selectCountry();
            }
        });
        mTitleBar.setRightClick(new OnClickListener() {

            @Override
            public void onClick(View v) {
                sendQuestion();
            }
        });

        mAdapter = new MainAdapter(getActivity().getSupportFragmentManager());
        mMainPageChangeListener = new MainPageChangeListener();

        mPager = (ViewPager) v.findViewById(R.id.vp_question);
        mPager.setAdapter(mAdapter);
        mPager.setOnPageChangeListener(mMainPageChangeListener);
        mPager.setCurrentItem(0);
        mTitleBar.setTitleLeftSelected(true);
        mTitleBar.setTitleRightSelected(false);

        mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                if (Constants.Action.CHANGE_LOCATION.equals(intent.getAction())) {
                    String name1 = mTitleBar.getLeftText();
                    String name2 = LoginUser.getCountry(getActivity()).getCountryNameCn();

                    if (!name1.equals(name2)) {
                        mTitleBar.setLeftText(name2);

                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.Action.CHANGE_LOCATION);
        getActivity().registerReceiver(mReceiver, intentFilter);

        return v;
    }

    @Override
    public void onDestroyView() {
        if (mReceiver != null) {
            getActivity().unregisterReceiver(mReceiver);
        }

        super.onDestroy();
    }

    private void sendQuestion() {
        Intent intent = new Intent(getActivity(), SendActivity.class);
        startActivity(intent);
    }

    private void selectCountry() {
        Intent intent = new Intent(getActivity(), CountryRecommandActivity.class);
        intent.putExtra(Constants.Extra.TYPE, CountryRecommandActivity.TYPE_HOME);
        startActivity(intent);
    }
}
