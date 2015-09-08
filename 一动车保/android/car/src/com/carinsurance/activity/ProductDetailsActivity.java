package com.carinsurance.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

import com.carinsurance.abcpinying.SortModel;
import com.carinsurance.adapter.AbstractBaseAdapter;
import com.carinsurance.adapter.MyLoopPagerAdapter;
import com.carinsurance.adapter.ViewHolder;
import com.carinsurance.infos.CmModel;
import com.carinsurance.infos.ImgModel;
import com.carinsurance.infos.ParamModel;
import com.carinsurance.infos.ProductDefaultItemModel;
import com.carinsurance.infos.SupModel;
import com.carinsurance.loopviewpager.AutoScrollViewPager;

import android.support.v4.app.FragmentPagerAdapter;

import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.DisplayUtil;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.MathUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurance.viewpagerindicator.CirclePageIndicator;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 产品详情 lq 2015-7-22
 * 
 * @author Administrator
 * 
 */
public class ProductDetailsActivity extends BaseActivity {
	final int ResultOk = -1;
	@ViewInject(R.id.xu_line1)
	LinearLayout xu_line1;
	@ViewInject(R.id.xu_line2)
	LinearLayout xu_line2;
	@ViewInject(R.id.baozhang_gridView)
	GridView baozhang_gridView;

	@ViewInject(R.id.tijiao)
	FrameLayout tijiao;
	@ViewInject(R.id.jia)
	ImageView jia;
	@ViewInject(R.id.jian)
	ImageView jian;
	@ViewInject(R.id.number)
	TextView number;
	@ViewInject(R.id.canshuxiangqing)
	LinearLayout canshuxiangqing;

	@ViewInject(R.id.canshuxiangqing_sg)
	LinearLayout canshuxiangqing_sg;
	@ViewInject(R.id.canshuxiangqing_youjiantou)
	ImageView canshuxiangqing_youjiantou;

	@ViewInject(R.id.canshuxiangqing_youjiantou2)
	ImageView canshuxiangqing_youjiantou2;
	@ViewInject(R.id.canshuxiangqing_sg2)
	LinearLayout canshuxiangqing_sg2;
	SortModel sortModel;

	ProductDefaultItemModel productDefaultItemModel;

	String pid;

	@ViewInject(R.id.tv_pname)
	TextView tv_pname;

	@ViewInject(R.id.tv_price)
	TextView tv_price;
	@ViewInject(R.id.tv_shifukuan)
	TextView tv_shifukuan;

	List<SupModel> suplist;

	@ViewInject(R.id.tv_pcomts)
	TextView tv_pcomts;

	AutoScrollViewPager mLoopViewPager;

	@ViewInject(R.id.myListView_showAll)
	ListView myListView_showAll;

	@ViewInject(R.id.myListView_showAll2)
	ListView myListView_showAll2;
	String type = "0";// 0是特色市场里的单商品
						// 1.该商品是只能看的商品（SelectServer1活动中进入选择服务中进入查看服务类型）
	@ViewInject(R.id.return_btn)
	ImageView return_btn;
	// 是否显示适配车型1显示 0不显示
	public String is_show_shipeichexing = "1";

	private void initView() {
		// TODO Auto-generated method stub
		initViewPager();
		// 参数详情
		initParam();
		// 初始化适配车型
		initCmlist();
		tv_pname.setText("" + productDefaultItemModel.getPname());
		tv_price.setText("￥" + productDefaultItemModel.getPrice());

		double pri = Double.parseDouble(productDefaultItemModel.getPrice());

		double num = Double.parseDouble(number.getText().toString().trim());

		tv_shifukuan.setText("实付款:" + MathUtils.mul2(pri, num, 2));

		tv_pcomts.setText("(" + productDefaultItemModel.getPcomts() + ")");

		xu_line1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速
		xu_line2.setLayerType(View.LAYER_TYPE_SOFTWARE, null);// 关闭硬件加速
		canshuxiangqing_youjiantou.setSelected(false);
		suplist = new ArrayList<SupModel>();
		if (productDefaultItemModel.getSuplist() != null) {
			suplist = productDefaultItemModel.getSuplist();
			baozhang_gridView.setAdapter(new AbstractBaseAdapter<SupModel>(suplist) {

				@Override
				public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
					// TODO Auto-generated method stub
					if (convertView == null) {
						convertView = ProductDetailsActivity.this.getLayoutInflater().inflate(R.layout.baozhang_item, parent, false);
					}
					SupModel str = getItem(position);
					TextView name = com.carinsurance.adapter.ViewHolder.get(convertView, R.id.tv_bz);

					name.setText("" + str.getSuname());
					return convertView;
				}
			});
		}

	}

	private void initCmlist() {
		// TODO Auto-generated method stub
		try {
			List<CmModel> cmlist = productDefaultItemModel.getCmlist();
			myListView_showAll2.setDivider(null);
			myListView_showAll2.setAdapter(new AbstractBaseAdapter<CmModel>(cmlist) {

				@Override
				public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
					// TODO Auto-generated method stub

					if (convertView == null) {
						convertView = getLayoutInflater().inflate(R.layout.shipeichexing, null);
					}
					CmModel cmdel = getItem(position);

					TextView tishi = ViewHolder.get(convertView, R.id.tishi);
					if (position == getCount() - 1)
						tishi.setVisibility(View.VISIBLE);
					else {
						tishi.setVisibility(View.GONE);
					}
					TextView tv_paramvalue = ViewHolder.get(convertView, R.id.cmname);
					tv_paramvalue.setText("" + cmdel.getCmname());

					return convertView;
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void initParam() {
		// TODO Auto-generated method stub
		try {
			List<ParamModel> paramlist = productDefaultItemModel.getParamlist();
			List<ParamModel> temp = new ArrayList<>();
			int i = 0;
			for (int j = 0; j < paramlist.size(); j++) {
				temp.add(paramlist.get(j));
				i++;
				if (i == 2 && j != (paramlist.size() - 1)) { // 每隔两行加一个空白行
																// 并且尾行不添加
					temp.add(null);
					i = 0;
				}
			}
			myListView_showAll.setDivider(null);
			myListView_showAll.setAdapter(new AbstractBaseAdapter<ParamModel>(paramlist) {

				@Override
				public View getAdapterViewAtPosition(int position, View convertView, ViewGroup parent) {
					// TODO Auto-generated method stub

					if (convertView == null) {
						convertView = getLayoutInflater().inflate(R.layout.canshuxiangqing, null);
					}

					ParamModel p = getItem(position);
					LinearLayout ii = ViewHolder.get(convertView, R.id.ii);
					ii.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

						}
					});
					LinearLayout jianju = ViewHolder.get(convertView, R.id.jianju);
					jianju.setVisibility(View.GONE);
					if (position % 2 == 0) {
						jianju.setVisibility(View.VISIBLE);
					} else {
						jianju.setVisibility(View.GONE);
					}

					TextView tv_paramname = ViewHolder.get(convertView, R.id.tv_paramname);
					TextView tv_paramvalue = ViewHolder.get(convertView, R.id.tv_paramvalue);

					if (p == null) {
						tv_paramname.setText("");
						tv_paramvalue.setText("");
					} else {
						tv_paramname.setText("" + p.getParamname() + ":");
						tv_paramvalue.setText("" + p.getParamvalue());
					}

					return convertView;
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void initViewPager() {
		// TODO Auto-generated method stub

		List<ImgModel> list = productDefaultItemModel.getImglist();
		FragmentPagerAdapter adapter = new MyLoopPagerAdapter(getSupportFragmentManager(), list);

		mLoopViewPager = (AutoScrollViewPager) findViewById(R.id.loopViewPager);

		CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicators);
		// TestViewPagerAdapter mm=new TestViewPagerAdapter(context);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, (int) DisplayUtil.getDip(ProductDetailsActivity.this, 160));
		mLoopViewPager.setLayoutParams(lp);
		// 显示要显示的界面
		mLoopViewPager.setCurrentItem(0);
		// 预先加载的页数
		mLoopViewPager.setOffscreenPageLimit(8);
		mLoopViewPager.setAdapter(adapter);
		mLoopViewPager.setInterval(5000);
		mLoopViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
		// mLoopViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_CYCLE);
		mLoopViewPager.startAutoScroll();
		// TimerTask task = new TimerTask() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		//
		// if (context != null) {
		// handler.sendEmptyMessage(1);
		//
		// }
		// }
		// };
		// Timer timer = new Timer(true);
		// timer.schedule(task, 5000, 5000); // 延时1000ms后执行，1000ms执行一次
		// FrameLayout.LayoutParams lp1 = new
		// FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
		// FrameLayout.LayoutParams.WRAP_CONTENT);
		// indicator.setLayoutParams(lp1);
		// indicator.setPadding(10, 10, 10, 10);
		// mLoopViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_CYCLE);
		mLoopViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			// 滑动了之后的选中项
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				LogUtils.d("onPageSelected========>arg0=" + arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				// arg0滑动过程中的选中项 （和onPageSelected一样，但是是实时监听的）
				//
				// arg1====>滑动的比例 0-1
				// arg2=====》滑动的位置 0-480左右
				LogUtils.d("onPageScrolled========>arg0=" + arg0 + "/arg1=" + arg1 + "/arg2=" + arg2);
			}

			// 滑动之前的选中项
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				LogUtils.d("onPageScrollStateChanged========>arg0=" + arg0);
			}
		});
		indicator.setViewPager(mLoopViewPager);

	}

	@ViewInject(R.id.ll_goumaishu)
	LinearLayout ll_goumaishu;
	@ViewInject(R.id.ll_bottom_goumai)
	LinearLayout ll_bottom_goumai;
	@ViewInject(R.id.shipeichexin)
	LinearLayout shipeichexin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_details);
		ViewUtils.inject(this);
		// initView();

		is_show_shipeichexing = JumpUtils.getString(ProductDetailsActivity.this, "is_show_shipeichexing");

		if(StringUtil.isNullOrEmpty(is_show_shipeichexing))
		{
			is_show_shipeichexing="1";
		}
		if (is_show_shipeichexing.equals("1")) {
			shipeichexin.setVisibility(View.VISIBLE);
		} else {
			shipeichexin.setVisibility(View.GONE);
		}

		// 注意，进入到这个界面只需要保证ProductDefaultItemModel中有个id就行了
		productDefaultItemModel = (ProductDefaultItemModel) JumpUtils.getSerializable(ProductDetailsActivity.this);
		pid = productDefaultItemModel.getPid();
		type = JumpUtils.getString(ProductDetailsActivity.this, "type");
		if (!StringUtil.isNullOrEmpty(type)) {
			if (type.equals("1")) {
				ll_goumaishu.setVisibility(View.GONE);
				ll_bottom_goumai.setVisibility(View.GONE);
			}

		}

		getProductDetail();

	}

	/**
	 * 获取商品详情
	 */
	private void getProductDetail() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("uid", Utils.getUid(this));
		map.put("token", Utils.getToken(this));
		map.put("pid", pid);
		NetUtils.getIns().post(Task.GET_PRODUCT_DETAIL, map, handler);
	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		// android:listSelector="@drawable/list_item_color_bg"
		if (task.equals(Task.GET_PRODUCT_DETAIL)) {
			try {

				String results = message;
				Log.v("aaa", "运行到了这3");
				String result = new JSONObject(results).getString("result");
				if (result.equals("001")) {
					productDefaultItemModel = JsonUtil.getEntityByJsonString(results, ProductDefaultItemModel.class);
					initView();
				} else {
					Utils.showMessage(ProductDetailsActivity.this, "连接失败，错误码:" + result);
				}
			} catch (Exception e) {
			}
		}

	}

	public void OnClicks(View v) {
		switch (v.getId()) {
		case R.id.jia:
			Utils.initNumber(ProductDetailsActivity.this, number, 1);
			double pri = Double.parseDouble(productDefaultItemModel.getPrice());
			double num = Double.parseDouble(number.getText().toString().trim());
			Log.d("aaa", "pri=" + pri + "///" + "num=" + num + "///" + (pri * num));
			tv_shifukuan.setText("实付款:" + MathUtils.mul2(pri, num, 2));

			break;
		case R.id.jian:
			Utils.initNumber(ProductDetailsActivity.this, number, 0);
			double pri2 = Double.parseDouble(productDefaultItemModel.getPrice());
			double num2 = Double.parseDouble(number.getText().toString().trim());
			Log.d("aaa", "pri2=" + pri2 + "///" + "num2=" + num2 + "///" + (pri2 * num2));
			tv_shifukuan.setText("实付款:" + MathUtils.mul2(pri2, num2, 2));
			break;
		case R.id.pinjia:
			
			Log.d("aaa","点击了评价");
			HashMap<String, String> param = new HashMap<>();
			param.put("data", pid);
			JumpUtils.jumpto(this, GoodsDiscussShowActivity.class, param);
			break;
		case R.id.tuwenxiangqing:
			// 图文详解
			if (productDefaultItemModel != null)
				if (!StringUtil.isNullOrEmpty(productDefaultItemModel.getPurl())) {
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("url", productDefaultItemModel.getPurl());
					map.put("title", "图文详情");
					JumpUtils.jumpto(ProductDetailsActivity.this, WebViewActivity.class, map);
				}
			break;
		case R.id.canshuxiangqing:
			if (canshuxiangqing_youjiantou.isSelected() == true) {
				canshuxiangqing_youjiantou.setSelected(false);
				canshuxiangqing_sg.setVisibility(View.GONE);
			} else if (canshuxiangqing_youjiantou.isSelected() == false) {
				canshuxiangqing_youjiantou.setSelected(true);
				canshuxiangqing_sg.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.shipeichexin:

			if (canshuxiangqing_youjiantou2.isSelected() == true) {
				canshuxiangqing_youjiantou2.setSelected(false);
				canshuxiangqing_sg2.setVisibility(View.GONE);
			} else if (canshuxiangqing_youjiantou2.isSelected() == false) {
				canshuxiangqing_youjiantou2.setSelected(true);
				canshuxiangqing_sg2.setVisibility(View.VISIBLE);
			}

			break;
		case R.id.tijiao:
			// JumpUtils.jumpto(ProductDetailsActivity.this,
			// ServiceOrderActivity.class, sor);
			// JumpUtils.jumpResultfinish(context, Return_zhi, map,
			// isOpenAnima);
			// HashMap<String, String> map = new HashMap<String, String>();
			// map.put("num", number.getText().toString().trim());
			// JumpUtils.jumpResultfinish(ProductDetailsActivity.this, ResultOk,
			// map);
			if (!StringUtil.isNullOrEmpty(Utils.getUid(ProductDetailsActivity.this)) && !StringUtil.isNullOrEmpty(Utils.getUid(ProductDetailsActivity.this))) {
				productDefaultItemModel.setNumber(number.getText().toString().trim());
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "2");
				JumpUtils.jumpto(ProductDetailsActivity.this, ServiceOrderActivity.class, productDefaultItemModel, map);
			} else {
				Utils.showMessage(ProductDetailsActivity.this, "请登录！");
				JumpUtils.jumpActivityForResult(ProductDetailsActivity.this, LoginActivity.class, null, 255);
			}

			break;
		case R.id.return_btn:
			JumpUtils.jumpfinish(ProductDetailsActivity.this);
			break;

		}
	}
	//
	// /**
	// *
	// * @param i
	// * 0减 1 加
	// */
	// private void initNumber(int i) {
	// // TODO Auto-generated method stub
	// int num = Integer.parseInt(number.getText().toString().trim());
	//
	// if (i == 0) {
	// if (num > 1) {
	// number.setText("" + (num - 1));
	// } else {
	// Utils.showMessage(ProductDetailsActivity.this, "请至少选择一件商品！");
	// }
	// }
	// if (i == 1) {
	// number.setText("" + (num + 1));
	// }
	// }

}
