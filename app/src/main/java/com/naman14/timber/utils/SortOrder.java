/*
 * Copyright (C) 2012 Andrew Neal
 * Copyright (C) 2014 The CyanogenMod Project
 * Licensed under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.naman14.timber.utils;

import android.provider.MediaStore;

/**
 * Holds all of the sort orders for each list type.
 *
 * @author Andrew Neal (andrewdneal@gmail.com)
 */
public final class SortOrder {

    /**
     * This class is never instantiated
     */
    public SortOrder() {
    }

    /**
     * Song sort order entries.
     */
    public interface SongSortOrder {
        /* Song sort order A-Z */
        String SONG_A_Z = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
    }

}
