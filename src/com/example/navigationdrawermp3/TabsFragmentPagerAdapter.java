package com.example.navigationdrawermp3;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsFragmentPagerAdapter extends FragmentPagerAdapter {
	 
	 public TabsFragmentPagerAdapter(FragmentManager fm) {
	 super(fm);
	 }
	 
	 @Override
	 public Fragment getItem(int index) {
	 if(index == 0)
	 return new FacebookFragment();
	 if(index == 1)
	 return new WhatsappFragment();
	 if(index == 2)
	 return new TwitterFragment();
	 
	 
	 return null;
	 }
	 
	 @Override
	 public int getCount() {
	 // TODO Auto-generated method stub
	 return 3;
	 }
	 
	}