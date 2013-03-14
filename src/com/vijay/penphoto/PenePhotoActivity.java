package com.vijay.penphoto;

import java.util.ArrayList;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.kinvey.KCSClient;
import com.kinvey.KinveySettings;
import com.kinvey.KinveyUser;
import com.vijay.Constants;
import com.vijay.penphoto.fragment.ContentFragment;

public class PenePhotoActivity extends FragmentActivity {

	SectionsPagerAdapter mSectionsPagerAdapter;
	private KCSClient mKinveyClient;
	
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pene_photo);
		
		KinveySettings settings = new KinveySettings("kid_ee_Mqu0_u5", "f31e5ca2a96941e0bb7f65448d29a415");
		mKinveyClient = KCSClient.getInstance(this.getApplicationContext(), settings);
		
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(this, mViewPager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		ContentFragment cf = new ContentFragment();
		mSectionsPagerAdapter.addTab(cf, ContentFragment.class, new Bundle());
		mSectionsPagerAdapter.addTab(cf, ContentFragment.class, new Bundle());
		mSectionsPagerAdapter.addTab(cf, ContentFragment.class, new Bundle());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_pene_photo, menu);
		return true;
	}
	
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
 
        case R.id.prefs_logout:
            KinveyUser user = mKinveyClient.getActiveUser();
            if (user != null) {
            	user.logout();
            }
            
            SharedPreferences userdetails = getSharedPreferences(Constants.USER_DETAILS, Application.MODE_PRIVATE);
            SharedPreferences.Editor userDetailsEdit = userdetails.edit();
    	    userDetailsEdit.putBoolean(Constants.SIGNED_IN_PREF, false);
    	    userDetailsEdit.commit();
            
            Intent i = new Intent(this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        	startActivity(i);
        	finish();
            break;
 
        }
 
        return true;
    }
	
	
	
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener{

		private final Context mContext;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
		
		public SectionsPagerAdapter(FragmentActivity activity, ViewPager pager) {
			super(activity.getSupportFragmentManager());
			mContext = activity;
			mViewPager = pager;
			mViewPager.setAdapter(this);
			mViewPager.setOnPageChangeListener(this);
		}
		
		final class TabInfo {
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(Class<?> _class, Bundle _args) {
                clss = _class;
                args = _args;
            }
        }
		
		public void addTab(Fragment fragment, Class<?> clss, Bundle args) {
			TabInfo info = new TabInfo(clss, args);
			mTabs.add(info);
			notifyDataSetChanged();
		}
		
		@Override
		public Fragment getItem(int position) {
			TabInfo info = mTabs.get(position);
			return Fragment.instantiate(getApplicationContext(), info.clss.getName(), info.args);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return mTabs.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase();
			case 1:
				return getString(R.string.title_section2).toUpperCase();
			case 2:
				return getString(R.string.title_section3).toUpperCase();
			}
			return null;
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			
		}
	}

//	/**
//	 * A dummy fragment representing a section of the app, but that simply
//	 * displays dummy text.
//	 */
//	public static class DummySectionFragment extends Fragment {
//		/**
//		 * The fragment argument representing the section number for this
//		 * fragment.
//		 */
//		public static final String ARG_SECTION_NUMBER = "section_number";
//
//		public DummySectionFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			// Create a new TextView and set its text to the fragment's section
//			// number argument value.
//			TextView textView = new TextView(getActivity());
//			textView.setGravity(Gravity.CENTER);
//			textView.setText(Integer.toString(getArguments().getInt(
//					ARG_SECTION_NUMBER)));
//			return textView;
//		}
//	}

}
