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

package com.naman14.timber.models;

import android.net.Uri;

import com.naman14.timber.R;

public class Song {

    private final int id;
    private final String title;
    private final int rawId;
    private final int image;
    private final int duration;
    private final String subTitle = "Fiches Plateau Moto";

    public Song(int id, String title, int rawId, int imageId, int duration) {
        this.id = id;
        this.title = title;
        this.rawId = rawId;
        this.image = imageId;
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getRawId() {
        return rawId;
    }

    public int getImage() {
        return image;
    }

    public String getSubTitle() {
        return subTitle;
    }
}
