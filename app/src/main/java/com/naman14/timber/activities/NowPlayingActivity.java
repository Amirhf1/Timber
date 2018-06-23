package com.naman14.timber.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.naman14.timber.R;
import com.naman14.timber.nowplaying.Timber6;

/**
 * Created by naman on 01/01/16.
 */
public class NowPlayingActivity extends BaseActivity {

    public static Intent newInstance(Context context){
        return new Intent(context, NowPlayingActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowplaying);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new Timber6())
                .commit();

    }
}
