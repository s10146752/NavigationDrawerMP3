package com.example.navigationdrawermp3;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PeopleFrag extends Fragment{
	
	public PeopleFrag(){}
	
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.peoplelayoutfrag, container, false);
		
			return rootView;
		}

}
