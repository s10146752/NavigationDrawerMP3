package com.example.navigationdrawermp3;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PagesFrag extends Fragment{
	
	public PagesFrag(){}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.pageslayoutfrag, container, false);
		
		return rootView;
	}

}
