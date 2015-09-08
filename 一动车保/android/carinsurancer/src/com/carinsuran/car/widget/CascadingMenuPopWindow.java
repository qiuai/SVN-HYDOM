package com.carinsuran.car.widget;
import java.util.ArrayList;

import com.carinsuran.car.interfaces.CascadingMenuViewOnSelectListener;
import com.carinsuran.car.model.MenuItem;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

/**
 * @author lrf
 * 下午1:48:27
 * 提供PopWindow调用方法
 */
public class CascadingMenuPopWindow extends PopupWindow{

	private Context context;
	private CascadingMenuView cascadingMenuView;
	private ArrayList<MenuItem> menuItems=null;
    //提供给外的接口
	private CascadingMenuViewOnSelectListener menuViewOnSelectListener;
	
	public void setMenuItems(ArrayList<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	public void setMenuViewOnSelectListener(
			CascadingMenuViewOnSelectListener menuViewOnSelectListener) {
		this.menuViewOnSelectListener = menuViewOnSelectListener;
	}
     
	public CascadingMenuPopWindow(Context context,ArrayList<MenuItem> list) {
		super(context);
		this.context=context;
		this.menuItems=list;
		init();
	}
	
	public void init(){
		//实例化级联菜单
		cascadingMenuView=new CascadingMenuView(context,menuItems);
		setContentView(cascadingMenuView);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.MATCH_PARENT);
		//设置回调接口
		cascadingMenuView.setCascadingMenuViewOnSelectListener(new MCascadingMenuViewOnSelectListener());
	}
	//级联菜单选择回调接口
	class MCascadingMenuViewOnSelectListener implements CascadingMenuViewOnSelectListener{

		@Override
		public void getValue(MenuItem menuItem) {
			if(menuViewOnSelectListener!=null){
				menuViewOnSelectListener.getValue(menuItem);
				dismiss();
			}
		}
		
	}
}
