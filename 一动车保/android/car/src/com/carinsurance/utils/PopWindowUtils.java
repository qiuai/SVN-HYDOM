package com.carinsurance.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.carinsurance.adapter.Mendian_leftlistview_adapter;
import com.carinsurancer.car.R;
import com.lidroid.xutils.util.LogUtils;

public class PopWindowUtils {

	static PopupWindow popupWindow;

	public static void showPop(Context context, View showDropview, int heig) {

		View root = ((Activity) (context)).getLayoutInflater().inflate(R.layout.shuang_listview, null);
		ListView left_listview = (ListView) root.findViewById(R.id.left_listview);
		ListView right_listview = (ListView) root.findViewById(R.id.right_listview);
		Log.v("aaa", "left_listview=>" + left_listview);
		left_listview.setAdapter(new Mendian_leftlistview_adapter(context));
		right_listview.setAdapter(new Mendian_leftlistview_adapter(context));
		// root
		// =((Activity)(context)).getLayoutInflater().inflate(R.layout.shuang_listview,
		// null);
		float height = SystemUtils.getScreenH(context);
		// int heig=(int) (height-DisplayUtil.dip2px(context, 140));
		LogUtils.v("Pop==================>" + heig);
		// float h=height-200;
		popupWindow = new PopupWindow(root, LayoutParams.MATCH_PARENT, (heig - DisplayUtil.dip2px(context, 45)));
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setFocusable(true);
		popupWindow.setTouchable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.showAsDropDown(showDropview);
	}
}
