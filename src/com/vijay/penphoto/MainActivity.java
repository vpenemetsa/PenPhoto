package com.vijay.penphoto;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.kinvey.KCSClient;
import com.kinvey.KinveySettings;
import com.kinvey.util.KinveyCallback;
import com.vijay.penphoto.adapter.ContentAdapter;
import com.vijay.penphoto.content.ImageResources;

public class MainActivity extends Activity {

	private ContentAdapter listAdapter;
	private ListView contentList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		KinveySettings settings = new KinveySettings("kid_ee_Mqu0_u5", "f31e5ca2a96941e0bb7f65448d29a415");
		KCSClient kinveyClient = KCSClient.getInstance(this.getApplicationContext(), settings);
		
		kinveyClient.pingService(new KinveyCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean r) {
				Log.d("***************", "Kinvey succesfully pinged!!!!!!!");
			}
			
			public void onFailure(Throwable t) {
		        Log.d("****************", "Kinvey Ping Failed", t);
		    }
		});
		
		contentList = (ListView) findViewById(R.id.listview);
		listAdapter = new ContentAdapter(getApplicationContext());
		contentList.setItemsCanFocus(true);
		contentList.setAdapter(listAdapter);
		
		kinveyClient.resource("IMG_0110.mp4").getUriForResource(new KinveyCallback<String>() {
			@Override
			public void onSuccess(String url) {
				Bitmap image = ImageResources.getVideoFrame(getApplicationContext(), Uri.parse(url));
				Log.d("====================", url);
//				Bitmap bmap = ThumbnailUtils.createVideoThumbnail(url, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
				
				
//				VideoView vv = (VideoView) findViewById(R.id.video);
//				MediaController mc = new MediaController(getApplicationContext());
//				mc.setAnchorView(vv);
//				mc.setMediaPlayer(vv);
//				Uri video = Uri.parse(url);
//				vv.setMediaController(mc);
//				vv.setVideoURI(video);
//				vv.start();
//				vv.pause();
			}
			
			@Override
			  public void onFailure(Throwable t) {
			      Log.e("******************", "error downloading movie.mp4", t);
			  }
		});
		
		
//		Event event = new Event();
//		event.setId("WTH??");
//		event.setName("Tester");
//		event.setLocation("Somewhere in the middle of no where");
//		event.setDate("Today...NOW!");
//		MappedAppdata appData = kinveyClient.mappeddata(Event.class, "events");
//		appData.save(event, new ScalarCallback<Event>() {
//			@Override
//			public void onSuccess(Event r) {
//				Log.d("*****************", r.getName());
//			}
//			
//			@Override
//			  public void onFailure(Throwable e) {
//			      Log.d("******************", "failed to save event data", e);
//			  }
//			
//		});

	}

	

}
