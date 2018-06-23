package com.naman14.timber.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.naman14.timber.R;
import com.naman14.timber.nowplaying.Timber6;

/**
 * Created by naman on 01/01/16.
 */
public class NowPlayingMusicActivity extends BaseMusicActivity {

    public static Intent newInstance(Context context){
        return new Intent(context, NowPlayingMusicActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowplaying);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, Timber6.newInstance())
                .commit();

    }
}
