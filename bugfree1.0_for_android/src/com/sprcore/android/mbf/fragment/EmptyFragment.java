package com.sprcore.android.mbf.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sprcore.android.mbf.base.AppFragment;
import com.sprcore.android.mbf.ui.R;

public class EmptyFragment extends AppFragment {

	
	@Override
	protected void onCreate(Bundle savedInstanceState, Bundle intentExtras) {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater,container,savedInstanceState);
		return inflater.inflate(R.layout.fragment_empty, container, false);
	}

 

}
