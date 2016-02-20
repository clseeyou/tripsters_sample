package com.tripsters.sample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tripsters.android.model.CityList;
import com.tripsters.android.model.Country;
import com.tripsters.android.model.CountryList;
import com.tripsters.android.task.GetSupportCityTask;
import com.tripsters.android.task.GetSupportCountryTask;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GetSupportCountryTask(getApplicationContext(), new GetSupportCountryTask.GetSupportCountryTaskResult() {
            @Override
            public void onTaskResult(CountryList result) {
                if (result != null) {
                    if (result.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "country success", Toast.LENGTH_SHORT).show();

                        List<Country> countries = result.getList();

                        new GetSupportCityTask(getApplicationContext(), countries.get(0).getCountryCode(), new GetSupportCityTask.GetSupportCityTaskResult() {
                            @Override
                            public void onTaskResult(CityList result) {
                                if (result != null) {
                                    if (result.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "city success", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), result.getErrmsg(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }).execute();
                    } else {
                        Toast.makeText(getApplicationContext(), result.getErrmsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
