package com.carinsurance.scaleview;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ImageShowFragmentAdapter extends FragmentStatePagerAdapter {
	private List<String> uris = null;

	public ImageShowFragmentAdapter(FragmentManager fm) {
		super(fm);
		uris = new ArrayList<String>();
	}

	public List<String> getUris() {
		return uris;
	}

	@Override
	public int getCount() {
		return uris.size();

	}

	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return super.getItemPosition(object);
	}

	@Override
	public ImageShowFragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return ImageShowFragment.newInstance(uris.get(arg0));
	}

}
