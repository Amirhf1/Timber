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

package com.naman14.timber.adapters;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.naman14.timber.MusicPlayer;
import com.naman14.timber.models.Song;
import com.naman14.timber.utils.NavigationUtils;
import com.naman14.timber.widgets.BubbleTextGetter;

import java.util.ArrayList;
import java.util.List;

public class SongsListAdapter extends RecyclerView.Adapter<SongViewHolder> implements BubbleTextGetter {

    private static final boolean navigateNowPlaying = false;
    private final List<Song> items = new ArrayList<>();

    public SongsListAdapter(List<Song> items) {
        if (items != null) {
            this.items.addAll(items);
        }
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return SongViewHolder.buildFor(viewGroup)
                .setItemClickedListener(new SongViewHolder.ItemClickedListener() {
                    @Override
                    public void onItemClicked(final View v, final int position) {
                        onSongClicked(v, position);
                    }
                });
    }

    @Override
    public void onBindViewHolder(SongViewHolder itemHolder, int i) {
        itemHolder.bind(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public String getTextToShowInBubble(final int pos) {
        if (items.size() == 0)
            return "";
        final Character ch = items.get(pos).title.charAt(0);
        if (Character.isDigit(ch)) {
            return "#";
        } else
            return Character.toString(ch);
    }

    private void onSongClicked(final View v, final int adapterPosition) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MusicPlayer.playAll(items, adapterPosition);

                if (navigateNowPlaying) {
                    NavigationUtils.navigateToNowplaying(v.getContext(), true);
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notifyItemChanged(adapterPosition);
                    }
                }, 50);

            }
        }, 100);
    }
}


