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

package com.naman14.qcm;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.naman14.qcm.adapters.SongsListAdapter;
import com.naman14.timber.R;
import com.naman14.timber.activities.BaseMusicActivity;
import com.naman14.timber.listeners.MusicStateListener;
import com.naman14.timber.models.Song;

import java.util.List;

public class SongsFragment extends Fragment implements MusicStateListener {

    private RecyclerView recyclerView;

    public static SongsFragment newInstance() {
        final Bundle args = new Bundle();
        final SongsFragment fragment = new SongsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final List<Song> allSongs = SongLoader.getAllSongs(getActivity());

        recyclerView.setAdapter(new SongsListAdapter(allSongs));

        final Activity activity = getActivity();
        if (activity instanceof BaseMusicActivity) {
            ((BaseMusicActivity) activity).setMusicStateListenerListener(this);
        }
    }

    public void restartLoader() {

    }

    public void onPlaylistChanged() {

    }

    public void onMetaChanged() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
