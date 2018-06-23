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

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.naman14.timber.R;
import com.naman14.timber.models.Song;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SongLoader {

    private static final List<Song> songs = new ArrayList<>();

    public static List<Song> getSongsForAssets(Context context){
        if(songs.isEmpty()){
            songs.addAll(
                    Arrays.asList(
                            songFromFile(context, "Fiche 1", R.raw.fiche1, R.drawable.ic_song_qcm),
                            songFromFile(context, "Fiche 2", R.raw.fiche1, R.drawable.ic_empty_music2),
                            songFromFile(context, "Fiche 3", R.raw.fiche1, R.drawable.ic_empty_music2)
                    )
            );
        }
        return songs;
    }

    public static Song getSongForID(long nextId) {
        for (Song song : songs) {
            if(song.getId() == nextId){
                return song;
            }
        }
        return null;
    }

    public static List<Song> getAllSongs(Context context) {
        return getSongsForAssets(context);
    }

    public static Song songFromFile(Context context, String name, int rawId, int imageId) {
        final AssetFileDescriptor afd = context.getResources().openRawResourceFd(rawId);

        final MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());

        return new Song(
                rawId,
                name,
                rawId,
                imageId,
                Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION))
        );
    }
}
