package com.joanflo.tagit;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CategoryListFragment extends Fragment {

	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_categorylist, container, false);
        
        return rootView;
    }
	
	
	
	public void onClickButton(View v) {
		
	}

}
