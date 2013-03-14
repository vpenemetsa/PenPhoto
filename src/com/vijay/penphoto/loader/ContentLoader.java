package com.vijay.penphoto.loader;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.vijay.penphoto.objects.ContentObj;

public class ContentLoader extends AsyncTaskLoader<ArrayList<ContentObj>> {

	private static final String TAG = ContentLoader.class.getSimpleName();
	
	public ContentLoader(Context context) {
		super(context);
		Log.d("*********", "Content loader constructor");
	}

	@Override
	public ArrayList<ContentObj> loadInBackground() {
		Log.d(TAG, "Started loadInBackground. ");
		
		ArrayList<ContentObj> result = new ArrayList<ContentObj>();
		ContentObj c1 = new ContentObj();
		c1.setId("IMG_0011.mp4");
		c1.setTitle("Piaashi video 1");
		
		ContentObj c2= new ContentObj();
		c2.setId("IMG_0012.mp4");
		c2.setTitle("Piaashi video 2");
		
		ContentObj c3 = new ContentObj();
		c3.setId("IMG_0042.mp4");
		c3.setTitle("Piaashi video 3");
		
		ContentObj c4 = new ContentObj();
		c4.setId("IMG_0110.mp4");
		c4.setTitle("Piaashi video 4");
		
		result.add(c1);
		result.add(c2);
		result.add(c3);
		result.add(c4);
		
		return result;
	}
}
