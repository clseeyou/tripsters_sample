package com.tripsters.sample;

import android.content.Intent;
import android.os.Bundle;

import com.tripsters.sample.util.Constants;
import com.tripsters.sample.util.LoginUser;
import com.tripsters.sample.util.Utils;

public class MainActivity extends BaseFragmentActivity {

    public static final int NOTICE_RECEIVED_ANSWER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TripstersPushMessageReceiver.start(TripstersApplication.mContext);

        if (LoginUser.getCountry() == null) {
            LoginUser.setCountry(Utils.getThailaid());
        }

        if (getIntent().hasExtra(Constants.Extra.NOTICE_TYPE)) {
            goNotice(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        if (intent.hasExtra(Constants.Extra.NOTICE_TYPE)) {
            goNotice(intent);
        }
    }

    private void goNotice(Intent intent) {
        Intent noticeIntent;
        int type = intent.getIntExtra(Constants.Extra.NOTICE_TYPE, NOTICE_RECEIVED_ANSWER);

        switch (type) {
            case NOTICE_RECEIVED_ANSWER:
                noticeIntent = new Intent(this, ReceivedAnswerListActivity.class);
                break;
            default:
                return;
        }
        noticeIntent.putExtras(intent.getExtras());
        startActivity(noticeIntent);
    }
}
