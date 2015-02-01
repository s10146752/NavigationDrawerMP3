package com.example.navigationdrawermp3;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CommunityFrag extends Fragment{
	
	public CommunityFrag(){}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.communityfrag, container, false);
		
		return rootView;
	}

}
