package com.tripsters.sample;

import android.os.Bundle;

import com.tripsters.sample.util.LoginUser;
import com.tripsters.sample.util.Utils;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (LoginUser.getCountry() == null) {
            LoginUser.setCountry(Utils.getThailaid());
        }
    }
}
