package com.naman14.timber.nowplaying;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.naman14.timber.MusicPlayer;
import com.naman14.timber.R;
import com.naman14.qcm.SongLoader;
import com.naman14.timber.models.Song;

/**
 * Created by naman on 22/02/17.
 */

public class Timber6 extends BaseNowplayingFragment {

    TextView nextSong;
    ImageView nextArt;

    public static Fragment newInstance(){
        return new Timber6();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_timber6, container, false);

        setMusicStateListener();
        setSongDetails(rootView);

        initGestures(rootView.findViewById(R.id.album_art));

        ((SeekBar) rootView.findViewById(R.id.song_progress)).getProgressDrawable().setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY));
        ((SeekBar) rootView.findViewById(R.id.song_progress)).getThumb().setColorFilter(new PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP));

        nextSong = (TextView) rootView.findViewById(R.id.title_next);
        nextArt = (ImageView) rootView.findViewById(R.id.album_art_next);

        rootView.findViewById(R.id.nextView).setVisibility(View.GONE);

        return rootView;
    }

    @Override
    public void onMetaChanged() {
        super.onMetaChanged();
        if (getActivity() != null) {
            long nextId = MusicPlayer.getNextAudioId();
            final Song next = SongLoader.getSongForID(nextId);
            if (next != null) {
                nextSong.setText(next.getTitle());
                nextArt.setImageResource(next.getImage());
            }
        }
    }
}
