package com.carinsuran.car.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.carinsuran.car.MyApplication;
import com.carinsuran.car.R;
import com.carinsuran.car.adapter.OrderAdapter;
import com.carinsuran.car.config.HttpUrl;
import com.carinsuran.car.http.RequestParams;
import com.carinsuran.car.interfaces.CascadingMenuViewOnSelectListener;
import com.carinsuran.car.model.MenuItem;
import com.carinsuran.car.tool.JsonPase;
import com.carinsuran.car.tool.SPUtil;
import com.carinsuran.car.widget.CascadingMenuPopWindow;
import com.carinsuran.car.widget.MorePopWindow;
import com.carinsuran.car.widget.MorePopWindow.SexListener;
import com.carinsuran.car.widget.XListView;
import com.carinsuran.car.widget.XListView.IXListViewListener;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class OrderManagementActivity extends BaseHttpActivity implements OnClickListener, SexListener,IXListViewListener{

	private TextView menuViewPopWindow,menuViewFragment;
	private ArrayList<MenuItem> menuItems=new ArrayList<MenuItem>();
	private CascadingMenuPopWindow cascadingMenuPopWindow=null;
	private PopupWindow popupwindow;
	private SPUtil sp;
	String year;
	Integer mother;
	Integer flag;
	
	String sex_type;
	
	/**
	 * 当前页数第一页
	 */
	private int pageNo = 1;
	
	private OrderAdapter orderadapter;
	
	/**
	 * 下拉刷新空控件
	 */
	private XListView businessList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_manage);
		showTitleBar(true);
		findView();
		setListener();
		fillData();
	}

	@Override
	public void netAsyncCallBack(String json) {
		// TODO Auto-generated method stub
		System.out.println("订单列表"+json);
		try {
			JSONObject jsonObject = new JSONObject(json);
			String res = jsonObject.getString("result");
			if("1004".equals(res)){
				Toast("该技师账号已删除");
				openNewActivity(LoginActivity.class);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(orderadapter == null) {
			orderadapter = new OrderAdapter(OrderManagementActivity.this,
					JsonPase.getNear(json));
			businessList.setAdapter(orderadapter);
		} else {
			orderadapter
					.addMoreMainItem(JsonPase.getNear(json));
			orderadapter.notifyDataSetChanged();
		}	
		onLoad();
	}
	
	
	private void onLoad() {
		// TODO Auto-generated method stub
		businessList.stopRefresh();
		businessList.stopLoadMore();
		businessList.setRefreshTime(currentTime());
	}

	private String currentTime() {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String time = df.format(new Date());
		return time;
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		pageNo = 1;
		orderadapter = null;
		executeLoadData();
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		pageNo++;
		orderadapter = null;
		executeLoadData();
	}
	

	@Override
	public void findView() {
		// TODO Auto-generated method stub
		sp = new SPUtil(this, SPUtil.USER_LOGIN_INFO, Context.MODE_APPEND);
		menuViewPopWindow = (TextView) findViewById(R.id.menuViewPopWindow);
		menuViewFragment = (TextView) findViewById(R.id.menuViewFragment);
		businessList = (XListView) findViewById(R.id.near_listview);
	}

	@Override
	public void setListener() {
		// TODO Auto-generated method stub
		businessList.setPullLoadEnable(true);
		businessList.setXListViewListener(this);
		menuViewPopWindow.setOnClickListener(this);
		menuViewFragment.setOnClickListener(this);
	}

	@Override
	public void fillData() {
		// TODO Auto-generated method stub
        setTitleText("订单管理");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy");    
        String date=sdf.format(new java.util.Date());
        ArrayList<MenuItem> tempMenuItems =null;
		for (int j = Integer.parseInt(date)-1; j < Integer.parseInt(date)+1; j++) {
			tempMenuItems =new ArrayList<MenuItem>(); 
			for (int i = 1; i <13; i++) {
				tempMenuItems.add(new MenuItem(false,i+"月",null));
			}
			menuItems.add(new MenuItem(true,j+"年",tempMenuItems));
		}
		executeLoadData();
	}

	private void executeLoadData() {
		// TODO Auto-generated method stub
		post(HttpUrl.ORDERS, createParams(sex_type));
	}

	@Override
	public void Sex(String sex_type) {
		// TODO Auto-generated method stub
		this.sex_type = sex_type;
		orderadapter = null;
		post(HttpUrl.ORDERS, createParams(sex_type));
	}
	
	private RequestParams createParams(String sex_type) {
		// TODO Auto-generated method stub
		RequestParams params = new RequestParams();
		params.put("techId", sp.getValue("techId", ""));
		System.out.println("");
		if(MyApplication.years == null){
		}else{
			params.put("year", MyApplication.years);
		}
		if(mother == null){
		}else{
			params.put("month", mother);
		}
		if(this.sex_type == null){
			
		}else{
			params.put("orderState",this.sex_type);
		}
		return params;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.menuViewPopWindow:
			showPopMenu();
			break;
		case R.id.menuViewFragment:
			MorePopWindow morePopWindow=new MorePopWindow(OrderManagementActivity.this,this);
			morePopWindow.showPopupWindow(menuViewFragment);
			break;
		}
	}

        

	private void showPopMenu() {
		// TODO Auto-generated method stub
		if(cascadingMenuPopWindow==null){
			cascadingMenuPopWindow=new CascadingMenuPopWindow(getApplicationContext(),menuItems);
			cascadingMenuPopWindow.setMenuViewOnSelectListener(new NMCascadingMenuViewOnSelectListener());
			cascadingMenuPopWindow.showAsDropDown(menuViewPopWindow,5,5);
		}
	     else if(cascadingMenuPopWindow!=null&&cascadingMenuPopWindow.isShowing()){
			cascadingMenuPopWindow.dismiss();
		}else{
			cascadingMenuPopWindow.showAsDropDown(menuViewPopWindow,5,5);
		}
	}
	
	 //级联菜单选择回调接口
		class NMCascadingMenuViewOnSelectListener implements CascadingMenuViewOnSelectListener{

			@Override
			public void getValue(MenuItem menuItem) {
			//	cascadingMenuFragment=null;
				Toast.makeText(getApplicationContext(), ""+menuItem.toString(), 1000).show();
				System.out.println("点击获取的当前月份"+menuItem.toString());
				String regEx="[^0-9]"; 
				Pattern p = Pattern.compile(regEx);  
				Matcher m = p.matcher(menuItem.toString());
				mother = Integer.parseInt(m.replaceAll("").trim());
			}
			
		}

}
