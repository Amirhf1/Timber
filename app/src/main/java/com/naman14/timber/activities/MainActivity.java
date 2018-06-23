/*
 * Copyright (C) 2015 Naman Dwivedi
 *
 * Licensed under the GNU General Public License v3
 *
 * This is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */

package com.naman14.timber.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;

import com.naman14.timber.MusicPlayer;
import com.naman14.timber.R;
import com.naman14.timber.fragments.MainFragment;
import com.naman14.timber.slidinguppanel.SlidingUpPanelLayout;
import com.naman14.timber.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {

    private SlidingUpPanelLayout panelLayout;
    private String action;
    private Map<String, Runnable> navigationMap = new HashMap<String, Runnable>();

    private Runnable navigateLibrary = new Runnable() {
        public void run() {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MainFragment())
                    .commitAllowingStateLoss();
        }
    };

    private Runnable navigateNowplaying = new Runnable() {
        public void run() {
            navigateLibrary.run();
            startActivity(new Intent(MainActivity.this, NowPlayingActivity.class));
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {

        action = getIntent().getAction();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationMap.put(Constants.NAVIGATE_LIBRARY, navigateLibrary);
        navigationMap.put(Constants.NAVIGATE_NOWPLAYING, navigateNowplaying);

        panelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);

        setPanelSlideListeners(panelLayout);

        loadEverything();

        addBackstackListener();

        if (Intent.ACTION_VIEW.equals(action)) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MusicPlayer.clearQueue();
                    MusicPlayer.openFile(getIntent().getData().getPath());
                    MusicPlayer.playOrPause();
                    navigateNowplaying.run();
                }
            }, 350);
        }

        if (!panelLayout.isPanelHidden() && MusicPlayer.getTrackName() == null) {
            panelLayout.hidePanel();
        }

    }

    private void loadEverything() {
        Runnable navigation = navigationMap.get(action);
        if (navigation != null) {
            navigation.run();
        } else {
            navigateLibrary.run();
        }

        new initQuickControls().execute("");
    }

    @Override
    public void onBackPressed() {
        if (panelLayout.isPanelExpanded()) {
            panelLayout.collapsePanel();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMetaChanged() {
        super.onMetaChanged();

        if (panelLayout.isPanelHidden() && MusicPlayer.getTrackName() != null) {
            panelLayout.showPanel();
        }
    }

    private void addBackstackListener() {
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                getSupportFragmentManager().findFragmentById(R.id.fragment_container).onResume();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getSupportFragmentManager().findFragmentById(R.id.fragment_container).onActivityResult(requestCode, resultCode, data);
    }
}


