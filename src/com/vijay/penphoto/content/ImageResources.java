package com.vijay.penphoto.content;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;

public class ImageResources {

	public static Bitmap getVideoFrame(Context context, Uri uri) {
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		
		//Create a new Media Player
		MediaPlayer mp = MediaPlayer.create(context, uri);

		int millis = mp.getDuration();
		for(int i=0;i<millis;i+=100){
		   Bitmap bitmap=retriever.getFrameAtTime(i, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
	       retriever.release();
	       return bitmap;
	    }
		
		return null;
    }
	
}
