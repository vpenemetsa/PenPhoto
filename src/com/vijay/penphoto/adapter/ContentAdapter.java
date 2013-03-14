package com.vijay.penphoto.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vijay.Constants;
import com.vijay.penphoto.R;
import com.vijay.penphoto.VideoPlayerActivity;
import com.vijay.penphoto.objects.ContentObj;

public class ContentAdapter extends ArrayAdapter<ContentObj> {

	Context mContext;
	
	public ContentAdapter(Context context) {
		super(context, -1);
		mContext = context;
	}
	
	public void setData(List<ContentObj> data) {
	    clear();
	    if (data != null) {
	        addAll(data);
	    }
    }
	
	@Override 
    public View getView(int position, View convertView, ViewGroup parent) {
		
		LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = layoutInflater.inflate(R.layout.row_content, parent, false);
		
		final ContentObj obj = getItem(position);
		
		TextView title = (TextView) convertView.findViewById(R.id.title);
		ImageView playButton = (ImageView) convertView.findViewById(R.id.play_button);
		
		title.setText(obj.getTitle());
		playButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(mContext, VideoPlayerActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				i.putExtra(Constants.VIDEO_ID, obj.getId());
				mContext.startActivity(i);
//				Toast.makeText(mContext, "Playing video with title " + obj.getTitle(), Toast.LENGTH_SHORT).show();
			}
		});
		
		return convertView;
	}

}
