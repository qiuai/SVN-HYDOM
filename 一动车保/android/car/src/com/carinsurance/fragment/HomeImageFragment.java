package com.carinsurance.fragment;

import java.util.HashMap;
import com.carinsurance.activity.WebViewActivity;
import com.carinsurance.infos.ImgModel;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurancer.car.R;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class HomeImageFragment extends BaseFragment {

	// public HomeImageFragment(List<String> img) {
	// // TODO Auto-generated constructor stub
	// for()
	// {
	//
	// }
	// }
	String imgid;

	String type = "0";// 0是首页的ViewPager

	ImgModel imgmodel;

	public HomeImageFragment(ImgModel imgmodel) {
		// // TODO Auto-generated constructor stub
		this.imgmodel = imgmodel;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.img, container, false);
		ImageView img = (ImageView) view.findViewById(R.id.img);
		// img.setImageResource(imgid);
		Log.d("ccc", "img="+imgmodel.toString());
	     if(!StringUtil.isNullOrEmpty(imgmodel.getAdimg()))
	     {
	    	 new xUtilsImageLoader(getActivity()).display(img, imgmodel.getAdimg());
	    	 
	    	 if(!StringUtil.isNullOrEmpty(imgmodel.getAdurl()))
	    	 {
	    		 img.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						HashMap<String, String> map=new HashMap<String, String>();
						map.put("title", "详情");
						map.put("url", ""+imgmodel.getAdurl());
						JumpUtils.jumpto(getActivity(), WebViewActivity.class, map);
					}
				});
	    	 }else
	    	 {
	    		 img.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							
						}
					});
	    	 }
	    	 
	     }
	     else
	     if(!StringUtil.isNullOrEmpty(imgmodel.getPimg())){
	    	 new xUtilsImageLoader(getActivity()).display(img, imgmodel.getPimg());
	     }
			
	
		// new xUtilsImageLoader(getActivity()).display(img, imgid);
		return view;
	}
}
