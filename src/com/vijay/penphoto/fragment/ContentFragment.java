package com.vijay.penphoto.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vijay.penphoto.R;
import com.vijay.penphoto.adapter.ContentAdapter;
import com.vijay.penphoto.loader.ContentLoader;
import com.vijay.penphoto.objects.ContentObj;

public class ContentFragment extends Fragment implements LoaderCallbacks<ArrayList<ContentObj>>{


	private ListView contentList;
	private ContentAdapter contentAdapter;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.activity_main, container, false);
		contentList = (ListView) v.findViewById(R.id.listview);
		contentAdapter = new ContentAdapter(getActivity().getApplicationContext());
		contentList.setItemsCanFocus(true);
		contentList.setAdapter(contentAdapter);
		
		ArrayList<ContentObj> result = new ArrayList<ContentObj>();
		
		
		ContentObj c5 = new ContentObj();
		c5.setId("video1.mp4");
		c5.setTitle("Sample 1");
		
		ContentObj c6 = new ContentObj();
		c6.setId("video2.mp4");
		c6.setTitle("Sample 2");
		
		result.add(c5);
		result.add(c6);
		
		contentAdapter.setData(result);
		
//		getLoaderManager().initLoader(0, null, this);
		
		return v;
	}
	
	@Override
	public Loader<ArrayList<ContentObj>> onCreateLoader(int id, Bundle bundle) {
		return new ContentLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<ArrayList<ContentObj>> loader,
			ArrayList<ContentObj> data) {
		Log.d("*********", Integer.toString(data.size()));
		contentAdapter.setData(data);
	}

	@Override
	public void onLoaderReset(Loader<ArrayList<ContentObj>> loader) {
		contentAdapter.setData(null);
	}
	
}
