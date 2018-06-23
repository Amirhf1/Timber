package com.naman14.qcm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.naman14.timber.R;

public class QcmImageLoader {
    public static Bitmap loadImage(Context context, int id){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), id);
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_empty_music2);
        }
        return bitmap;
    }
}
