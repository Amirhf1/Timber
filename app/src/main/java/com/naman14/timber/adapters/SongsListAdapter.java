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

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.naman14.timber.MusicPlayer;
import com.naman14.timber.models.Song;
import com.naman14.timber.utils.NavigationUtils;
import com.naman14.timber.utils.TimberUtils;
import com.naman14.timber.widgets.BubbleTextGetter;

import java.util.List;

public class SongsListAdapter extends RecyclerView.Adapter<SongViewHolder> implements BubbleTextGetter {

    private List<Song> items;
    private long[] songIDs;

    private static final boolean navigateNowPlaying = false;

    public SongsListAdapter(List<Song> items) {
        this.items = items;
        this.songIDs = getSongIds();
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
        return (null != items ? items.size() : 0);
    }

    private long[] getSongIds() {
        long[] ret = new long[getItemCount()];
        for (int i = 0; i < getItemCount(); i++) {
            ret[i] = items.get(i).id;
        }

        return ret;
    }

    @Override
    public String getTextToShowInBubble(final int pos) {
        if (items == null || items.size() == 0)
            return "";
        Character ch = items.get(pos).title.charAt(0);
        if (Character.isDigit(ch)) {
            return "#";
        } else
            return Character.toString(ch);
    }

    private void onSongClicked(final View v, final int adapterPosition) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MusicPlayer.playAll(v.getContext(), songIDs, adapterPosition, -1, TimberUtils.IdType.NA, false);

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


