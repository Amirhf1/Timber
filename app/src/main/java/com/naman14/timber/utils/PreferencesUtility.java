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

package com.naman14.timber.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.naman14.timber.MusicPlayer;
import com.naman14.timber.MusicService;

public final class PreferencesUtility {

    public static final String ARTIST_SORT_ORDER = "artist_sort_order";
    public static final String ARTIST_SONG_SORT_ORDER = "artist_song_sort_order";
    public static final String ARTIST_ALBUM_SORT_ORDER = "artist_album_sort_order";
    public static final String ALBUM_SORT_ORDER = "album_sort_order";
    public static final String ALBUM_SONG_SORT_ORDER = "album_song_sort_order";
    public static final String SONG_SORT_ORDER = "song_sort_order";
    private static final String NOW_PLAYING_SELECTOR = "now_paying_selector";
    private static final String TOGGLE_ANIMATIONS = "toggle_animations";
    private static final String TOGGLE_SYSTEM_ANIMATIONS = "toggle_system_animations";
    private static final String TOGGLE_ARTIST_GRID = "toggle_artist_grid";
    private static final String TOGGLE_ALBUM_GRID = "toggle_album_grid";
    private static final String TOGGLE_PLAYLIST_VIEW = "toggle_playlist_view";
    private static final String TOGGLE_SHOW_AUTO_PLAYLIST = "toggle_show_auto_playlist";
    private static final String LAST_FOLDER = "last_folder";

    private static final String TOGGLE_HEADPHONE_PAUSE = "toggle_headphone_pause";
    private static final String THEME_PREFERNCE = "theme_preference";
    private static final String START_PAGE_INDEX = "start_page_index";
    private static final String START_PAGE_PREFERENCE_LASTOPENED = "start_page_preference_latopened";
    private static final String NOW_PLAYNG_THEME_VALUE = "now_playing_theme_value";
    private static final String TOGGLE_XPOSED_TRACKSELECTOR = "toggle_xposed_trackselector";
    public static final String LAST_ADDED_CUTOFF = "last_added_cutoff";
    public static final String GESTURES = "gestures";

    public static final String FULL_UNLOCKED = "full_version_unlocked";

    private static final String SHOW_LOCKSCREEN_ALBUMART = "show_albumart_lockscreen";
    private static final String ARTIST_ALBUM_IMAGE = "artist_album_image";
    private static final String ARTIST_ALBUM_IMAGE_MOBILE = "artist_album_image_mobile";
    private static final String ALWAYS_LOAD_ALBUM_IMAGES_LASTFM = "always_load_album_images_lastfm";

    private static PreferencesUtility sInstance;

    private static SharedPreferences mPreferences;
    private static Context context;
    private ConnectivityManager connManager = null;

    public PreferencesUtility(final Context context) {
        this.context = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static final PreferencesUtility getInstance(final Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesUtility(context.getApplicationContext());
        }
        return sInstance;
    }

    public boolean pauseEnabledOnDetach() {
        return mPreferences.getBoolean(TOGGLE_HEADPHONE_PAUSE, true);
    }

    public String getTheme() {
        return mPreferences.getString(THEME_PREFERNCE, "light");
    }

    public boolean getXPosedTrackselectorEnabled() {
        return mPreferences.getBoolean(TOGGLE_XPOSED_TRACKSELECTOR, false);
    }

    /** @parm lastAddedMillis timestamp in millis used as a cutoff for last added playlist */
    public void setLastAddedCutoff(long lastAddedMillis) {
        mPreferences.edit().putLong(LAST_ADDED_CUTOFF, lastAddedMillis).apply();
    }

    public boolean isGesturesEnabled() {
        return mPreferences.getBoolean(GESTURES, true);
    }

    public boolean getSetAlbumartLockscreen() {
        return mPreferences.getBoolean(SHOW_LOCKSCREEN_ALBUMART, true);
    }

    public void updateService(Bundle extras) {
        if(!MusicPlayer.isPlaybackServiceConnected())return;
        final Intent intent = new Intent(context, MusicService.class);
        intent.setAction(MusicService.UPDATE_PREFERENCES);
        intent.putExtras(extras);
        context.startService(intent);
    }

    public boolean loadArtistAndAlbumImages() {
        if (mPreferences.getBoolean(ARTIST_ALBUM_IMAGE, true)) {
            if (!mPreferences.getBoolean(ARTIST_ALBUM_IMAGE_MOBILE, true)) {
                if (connManager == null) connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo ni = connManager.getActiveNetworkInfo();
                return ni != null && ni.getType() == ConnectivityManager.TYPE_WIFI;
            }
            return true;
        }
        return false;
    }

    public boolean alwaysLoadAlbumImagesFromLastfm() {
        return mPreferences.getBoolean(ALWAYS_LOAD_ALBUM_IMAGES_LASTFM, false);
    }
}

