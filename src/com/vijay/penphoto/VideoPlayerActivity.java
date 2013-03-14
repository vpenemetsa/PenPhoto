package com.vijay.penphoto;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.kinvey.KCSClient;
import com.kinvey.KinveySettings;
import com.kinvey.util.KinveyCallback;
import com.vijay.Constants;
import com.vijay.penphoto.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class VideoPlayerActivity extends Activity {
	
	private VideoView contentView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_video_player);

//		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		contentView = (VideoView) findViewById(R.id.video);
		
		Intent intent = getIntent();
		String id = intent.getStringExtra(Constants.VIDEO_ID);
		Log.d("----------------", id);
		
//		KinveySettings settings = new KinveySettings("kid_ee_Mqu0_u5", "f31e5ca2a96941e0bb7f65448d29a415");
//		KCSClient kinveyClient = KCSClient.getInstance(this.getApplicationContext(), settings);
//		
//		kinveyClient.resource(id).getUriForResource(new KinveyCallback<String>() {
//			@Override
//			public void onSuccess(String url) {
////				MediaController mc = new MediaController(getApplicationContext());
////				mc.setAnchorView(contentView);
////				mc.setMediaPlayer(contentView);
////				Uri video = Uri.parse(url);
////				contentView.setMediaController(mc);
////				contentView.setVideoURI(video);
////				contentView.requestFocus();
////				contentView.start();
//				//get current window information, and set format, set it up differently, if you need some special effects
//			    getWindow().setFormat(PixelFormat.TRANSLUCENT);
//			    //the VideoView will hold the video
//			    VideoView videoHolder = new VideoView(getApplicationContext());
//			    //MediaController is the ui control howering above the video (just like in the default youtube player).
//			    videoHolder.setMediaController(new MediaController(getApplicationContext()));
//			    //assing a video file to the video holder
//			    videoHolder.setVideoURI(Uri.parse(url));
//			    //get focus, before playing the video.
//			    videoHolder.requestFocus();
//			    
//			    videoHolder.setOnCompletionListener(new OnCompletionListener() {
//					@Override
//					public void onCompletion(MediaPlayer mp) {
//						mp.release();
//						Intent i = new Intent(getApplicationContext(), PenePhotoActivity.class);
//						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//						startActivity(i);
//						finish();
//					}
//				});
//				
////				contentView.setOnCompletionListener(new OnCompletionListener() {
////					@Override
////					public void onCompletion(MediaPlayer mp) {
////						mp.release();
//////						Intent i = new Intent(getApplicationContext(), PenePhotoActivity.class);
//////						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//////						startActivity(i);
//////						finish();
////					}
////				});
//				
//			}
//			
//			@Override
//			  public void onFailure(Throwable t) {
//			      Log.e("******************", "error downloading movie.mp4", t);
//			  }
//		});
		
		
		
		

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
//		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
//				HIDER_FLAGS);
//		mSystemUiHider.setup();
//		mSystemUiHider
//				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
//					// Cached values.
//					int mControlsHeight;
//					int mShortAnimTime;
//					
//					@Override
//					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//					public void onVisibilityChange(boolean visible) {
//						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//							// If the ViewPropertyAnimator API is available
//							// (Honeycomb MR2 and later), use it to animate the
//							// in-layout UI controls at the bottom of the
//							// screen.
//							if (mControlsHeight == 0) {
//								mControlsHeight = controlsView.getHeight();
//							}
//							if (mShortAnimTime == 0) {
//								mShortAnimTime = getResources().getInteger(
//										android.R.integer.config_shortAnimTime);
//							}
//							controlsView
//									.animate()
//									.translationY(visible ? 0 : mControlsHeight)
//									.setDuration(mShortAnimTime);
//						} else {
//							// If the ViewPropertyAnimator APIs aren't
//							// available, simply show or hide the in-layout UI
//							// controls.
//							controlsView.setVisibility(visible ? View.VISIBLE
//									: View.GONE);
//						}
//
//						if (visible && AUTO_HIDE) {
//							// Schedule a hide().
//							delayedHide(AUTO_HIDE_DELAY_MILLIS);
//						}
//					}
//				});
//
//		// Set up the user interaction to manually show or hide the system UI.
//		contentView.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				if (TOGGLE_ON_CLICK) {
//					mSystemUiHider.toggle();
//				} else {
//					mSystemUiHider.show();
//				}
//			}
//		});

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
//		findViewById(R.id.dummy_button).setOnTouchListener(
//				mDelayHideTouchListener);
		
		videoPlayer(id);
	}

//	@Override
//	protected void onPostCreate(Bundle savedInstanceState) {
//		super.onPostCreate(savedInstanceState);
//
//		// Trigger the initial hide() shortly after the activity has been
//		// created, to briefly hint to the user that UI controls
//		// are available.
//		delayedHide(100);
//	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
//	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
//		@Override
//		public boolean onTouch(View view, MotionEvent motionEvent) {
//			if (AUTO_HIDE) {
//				delayedHide(AUTO_HIDE_DELAY_MILLIS);
//			}
//			return false;
//		}
//	};
//
//	Handler mHideHandler = new Handler();
//	Runnable mHideRunnable = new Runnable() {
//		@Override
//		public void run() {
//			mSystemUiHider.hide();
//		}
//	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
//	private void delayedHide(int delayMillis) {
//		mHideHandler.removeCallbacks(mHideRunnable);
//		mHideHandler.postDelayed(mHideRunnable, delayMillis);
//	}
//	
	public void videoPlayer(String id){
		KinveySettings settings = new KinveySettings("kid_ee_Mqu0_u5", "f31e5ca2a96941e0bb7f65448d29a415");
		KCSClient kinveyClient = KCSClient.getInstance(this.getApplicationContext(), settings);
		
		kinveyClient.resource(id).getUriForResource(new KinveyCallback<String>() {
			@Override
			public void onSuccess(String url) {
				MediaController mc = new MediaController(getApplicationContext());
				mc.setAnchorView(contentView);
				mc.setMediaPlayer(contentView);
				Uri video = Uri.parse(url);
				contentView.setMediaController(mc);
				contentView.setVideoURI(video);
				contentView.start();
				//get current window information, and set format, set it up differently, if you need some special effects
//			    getWindow().setFormat(PixelFormat.TRANSLUCENT);
//			    //the VideoView will hold the video
//			    VideoView videoHolder = new VideoView(getApplicationContext());
//			    //MediaController is the ui control howering above the video (just like in the default youtube player).
//			    videoHolder.setMediaController(new MediaController(getApplicationContext()));
//			    Log.d("**************", url);
//			    //assing a video file to the video holder
//			    videoHolder.setVideoURI(Uri.parse(url));
//			    //get focus, before playing the video.
//			    videoHolder.requestFocus();
//			    videoHolder.start();
			    
			    contentView.setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						Intent i = new Intent(getApplicationContext(), PenePhotoActivity.class);
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
						finish();
					}
				});
				
				
			}
			
			@Override
			  public void onFailure(Throwable t) {
			      Log.e("******************", "error downloading movie.mp4", t);
			  }
		});
		
	 }
}
