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

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.afollestad.appthemeengine.Config;
import com.naman14.timber.MusicPlayer;
import com.naman14.timber.R;
import com.naman14.timber.models.Song;
import com.naman14.timber.utils.Helpers;
import com.naman14.timber.utils.NavigationUtils;
import com.naman14.timber.utils.TimberUtils;
import com.naman14.timber.widgets.MusicVisualizer;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class BaseQueueAdapter extends RecyclerView.Adapter<BaseQueueAdapter.ItemHolder> {

    public static int currentlyPlayingPosition;
    private List<Song> arraylist;

    public BaseQueueAdapter( List<Song> arraylist) {
        this.arraylist = arraylist;
        currentlyPlayingPosition = MusicPlayer.getQueuePosition();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return ItemHolder.buildFor(this, viewGroup);
    }

    @Override
    public void onBindViewHolder(ItemHolder itemHolder, int i) {
        itemHolder.bind(arraylist.get(i));
    }

    @Override
    public int getItemCount() {
        return (null != arraylist ? arraylist.size() : 0);
    }

    public long[] getSongIds() {
        long[] ret = new long[getItemCount()];
        for (int i = 0; i < getItemCount(); i++) {
            ret[i] = arraylist.get(i).id;
        }

        return ret;
    }

    public void removeSongAt(int i){
        arraylist.remove(i);
    }

    public static class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final BaseQueueAdapter baseQueueAdapter;
        protected TextView title, artist;
        protected ImageView albumArt;
        private MusicVisualizer visualizer;

        public static ItemHolder buildFor(BaseQueueAdapter baseQueueAdapter, ViewGroup viewGroup){
            return new ItemHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_song_timber1, viewGroup, false), baseQueueAdapter);
        }

        public ItemHolder(View view, BaseQueueAdapter baseQueueAdapter) {
            super(view);
            this.baseQueueAdapter = baseQueueAdapter;
            this.title = (TextView) view.findViewById(R.id.song_title);
            this.artist = (TextView) view.findViewById(R.id.song_artist);
            this.albumArt = (ImageView) view.findViewById(R.id.albumArt);
            visualizer = (MusicVisualizer) view.findViewById(R.id.visualizer);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MusicPlayer.setQueuePosition(getAdapterPosition());
                    Handler handler1 = new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            baseQueueAdapter.notifyItemChanged(currentlyPlayingPosition);
                            baseQueueAdapter.notifyItemChanged(getAdapterPosition());
                        }
                    }, 50);
                }
            }, 100);

        }

        public void bind(Song localItem) {
            title.setText(localItem.title);
            artist.setText(localItem.artistName);

            if (MusicPlayer.getCurrentAudioId() == localItem.id) {
                title.setTextColor(Color.parseColor("#3E3E3E"));
                if (MusicPlayer.isPlaying()) {
                    visualizer.setColor(itemView.getContext().getResources().getColor(R.color.colorAccent));
                    visualizer.setVisibility(View.VISIBLE);
                } else {
                    visualizer.setVisibility(View.GONE);
                }
            } else {
                title.setTextColor(Color.parseColor("#3E3E3E"));
                visualizer.setVisibility(View.GONE);
            }
            ImageLoader.getInstance().displayImage(TimberUtils.getAlbumArtUri(localItem.albumId).toString(),
                    albumArt, new DisplayImageOptions.Builder().cacheInMemory(true)
                            .showImageOnLoading(R.drawable.ic_empty_music2).resetViewBeforeLoading(true).build());
        }
    }

}



