package com.naman14.qcm.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.naman14.timber.MusicPlayer;
import com.naman14.timber.R;
import com.naman14.timber.models.Song;

public class SongViewHolder extends RecyclerView.ViewHolder {

    protected TextView title, artist;
    protected ImageView albumArt;

    @Nullable
    private ItemClickedListener itemClickedListener;

    public SongViewHolder(View view) {
        super(view);
        this.title = (TextView) view.findViewById(R.id.song_title);
        this.artist = (TextView) view.findViewById(R.id.song_artist);
        this.albumArt = (ImageView) view.findViewById(R.id.albumArt);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickedListener != null) {
                    itemClickedListener.onItemClicked(v, getAdapterPosition());
                }
            }
        });
    }

    public static SongViewHolder buildFor(ViewGroup viewGroup) {
        return new SongViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_song, viewGroup, false));
    }

    public SongViewHolder setItemClickedListener(@Nullable ItemClickedListener itemClickedListener) {
        this.itemClickedListener = itemClickedListener;
        return this;
    }

    public void bind(Song localItem) {
        title.setText(localItem.getTitle());
        artist.setText(localItem.getSubTitle());
        albumArt.setImageResource(localItem.getImage());

        if (MusicPlayer.getCurrentAudioId() == localItem.getId()) {
            title.setTextColor(itemView.getContext().getResources().getColor(R.color.colorCurrentSong));
            artist.setTextColor(itemView.getContext().getResources().getColor(R.color.colorCurrentSong));
        } else {
            title.setTextColor(itemView.getContext().getResources().getColor(R.color.colorNotCurrentSong));
            artist.setTextColor(itemView.getContext().getResources().getColor(R.color.colorNotCurrentSong));
        }
    }

    public interface ItemClickedListener {
        void onItemClicked(View v, int position);
    }


}
