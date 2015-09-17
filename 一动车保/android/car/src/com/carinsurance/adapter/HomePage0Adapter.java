package com.carinsurance.adapter;

import java.util.ArrayList;
import java.util.List;

import com.carinsurance.infos.BModel;
import com.carinsurance.infos.BaiduWeatherModel;
import com.carinsurance.infos.ImgModel;
import com.carinsurance.infos.PcModel;
import com.carinsurance.infos.SeriverTypeModel;
import com.carinsurance.infos.SeriverTypeitemModel;
import com.carinsurance.loopviewpager.AutoScrollViewPager;
import com.carinsurance.utils.BitmapHelp;
import com.carinsurance.utils.CalendarTools;
import com.carinsurance.utils.RegexUtils;
import com.carinsurance.utils.Utils;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurance.viewpagerindicator.CirclePageIndicator;
import com.carinsurancer.car.R;

import com.lidroid.xutils.util.LogUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HomePage0Adapter extends BaseAdapter {
	Context context;
	FragmentManager manager;
	// 天气
	BaiduWeatherModel weather = null;
	// 服务列表
	List<SeriverTypeitemModel> fuwu_list;

	List<ImgModel> ad_list;// 广告
	List<BModel> blist;// 品牌推荐

	List<PcModel> pclist;// 热卖分类

	SeriverTypeitemModel SeriverTypeitemModel0;
	SeriverTypeitemModel SeriverTypeitemModel1;
	ViewPagerAdapter adapter;
	AutoScrollViewPager mLoopViewPager;
	OnMySMXCClistener onMySMXCClistener;
	// 服务的item点击侦听
	OnFuWuClistener onFuWuClistener;
	android.os.Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				try {
					Log.v("aaa", "计时器运行了2");
					int y = mLoopViewPager.getCurrentItem();
					Log.v("aaa", "计时器运行了5" + y);
					int size = adapter.getCount();
					Log.v("aaa", "计时器运行了6" + size);

					mLoopViewPager.setCurrentItem((y + 1));

					Log.v("aaa", "计时器运行了7");
				} catch (Exception e) {
					// TODO: handle exception
				}

				break;
			case 0:
				try {
					mLoopViewPager.setCurrentItem(0);
				} catch (Exception e) {
					// TODO: handle exception
				}

				break;
			}

		};
	};

	public HomePage0Adapter(Context context, FragmentManager manager) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.manager = manager;
		ad_list = new ArrayList<ImgModel>();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// @Override
	// public int getItemViewType(int position) {
	// // TODO Auto-generated method stub
	// return 1;
	// }
	//
	// @Override
	// public int getViewTypeCount() {
	// // TODO Auto-generated method stub
	// return 2;
	// }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = LayoutInflater.from(context);
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.homepage_center, null);
			holder.sv = (ScrollView) convertView.findViewById(R.id.scrollView1);
			holder.weather_tianqi = (TextView) convertView.findViewById(R.id.weather_tianqi);
			holder.add_car = (ImageView) convertView.findViewById(R.id.add_car);
			holder.des = (TextView) convertView.findViewById(R.id.des);
			holder.tianqi_img = (ImageView) convertView.findViewById(R.id.tianqi_img);
			holder.servicetype_GridView = (GridView) convertView.findViewById(R.id.servicetype_GridView);
			holder.shangmenxiche = (LinearLayout) convertView.findViewById(R.id.shangmenxiche);
			holder.shangmenbaoyang = (LinearLayout) convertView.findViewById(R.id.shangmenbaoyang);
			holder.show_gridview2 = (GridView) convertView.findViewById(R.id.show_gridview2);
			holder.sm_image = (ImageView) convertView.findViewById(R.id.sm_image);
			holder.sm_smxc = (TextView) convertView.findViewById(R.id.sm_smxc);
			holder.sm_shuoming = (TextView) convertView.findViewById(R.id.sm_shuoming);
			holder.sm1_image = (ImageView) convertView.findViewById(R.id.sm1_image);
			holder.sm1_smxc = (TextView) convertView.findViewById(R.id.sm1_smxc);
			holder.sm1_shuoming = (TextView) convertView.findViewById(R.id.sm1_shuoming);
			holder.show_tianqi = (FrameLayout) convertView.findViewById(R.id.show_tianqi);
			holder.ll_pingpaituijian = (LinearLayout) convertView.findViewById(R.id.ll_pingpaituijian);
			holder.ll_xianliangjiangping = (LinearLayout) convertView.findViewById(R.id.ll_xianliangjiangping);
			holder.ll_tiantiantejia = (LinearLayout) convertView.findViewById(R.id.ll_tiantiantejia);
			holder.ll_lvshechuxing = (LinearLayout) convertView.findViewById(R.id.ll_lvshechuxing);
			holder.iv_pingpaituijian = (ImageView) convertView.findViewById(R.id.iv_pingpaituijian);
			holder.llbtn_more = (LinearLayout) convertView.findViewById(R.id.llbtn_more);
			// holder.
			// ll_tiantiantejia
			// ll_lvshechuxing
			convertView.setTag(holder);
		}
		holder = (ViewHolder) convertView.getTag();

		if (Utils.Is_ShowWeather(context)) {
			// 初始化天气
			initWeather(convertView, holder);// sm_image
			holder.show_tianqi.setVisibility(View.VISIBLE);
		} else {
			holder.show_tianqi.setVisibility(View.GONE);
		}
		// if (ad_list!=null && !ad_list.isEmpty()) {
		Log.v("ccc", "111");
		try {
			// if (adapter==null && ad_list!= null && !ad_list.isEmpty()) {
			initViewPager(convertView);
			// }else if(adapter!= null)
			// {
			// adapter.notifyDataSetChanged();
			// }
		} catch (Exception e) {
			// TODO: handle exception
		}
		// initBannerViewPager(convertView);

		// else {
		// Log.v("ccc", "333");
		// if (ad_list != null) {
		// Log.v("ccc", "444");
		//
		// adapter.setImg_list(ad_list);
		// adapter.notifyDataSetChanged();
		//
		// }
		//
		// }
		// }

		if (fuwu_list != null) {
			if (!fuwu_list.isEmpty()) {
				if (SeriverTypeitemModel0 != null) {
					// SeriverTypeitemModel0
					new xUtilsImageLoader(context).display(holder.sm_image, SeriverTypeitemModel0.getScimage());
					holder.sm_smxc.setText("" + SeriverTypeitemModel0.getScname());
					holder.sm_shuoming.setText("" + SeriverTypeitemModel0.getScremark());
					holder.shangmenxiche.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {

							if (onMySMXCClistener != null) {
								onMySMXCClistener.topleftButtonClistener(SeriverTypeitemModel0, v);
							}

						}
					});

				}
				if (SeriverTypeitemModel1 != null) {
					// SeriverTypeitemModel0
					new xUtilsImageLoader(context).display(holder.sm1_image, SeriverTypeitemModel1.getScimage());
					holder.sm1_smxc.setText("" + SeriverTypeitemModel1.getScname());
					holder.sm1_shuoming.setText("" + SeriverTypeitemModel1.getScremark());
					holder.shangmenbaoyang.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							if (onMySMXCClistener != null) {
								onMySMXCClistener.toprightButtonClistener(SeriverTypeitemModel1, v);
							}

						}
					});
				}
			}
		}

		// /**
		// * 上门洗车
		// */
		// holder.shangmenbaoyang.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// context.startActivity(new Intent(context,BrandActivity.class));
		// }
		// });

		// List<String> a = new ArrayList<String>();
		// for (int i = 0; i < 8; i++) {
		// a.add("换轮胎发");
		// }

		initGridView0(holder);

		initTesheshichang(holder);
		initremaifenlei(holder);

		holder.add_car.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Utils.setIs_ShowWeather(context, false);
				// holder.show_tianqi.setVisibility(View.GONE);
				notifyDataSetChanged();
			}
		});
		return convertView;
	}

	private void initremaifenlei(ViewHolder holder) {
		// TODO Auto-generated method stub
		holder.llbtn_more.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				if (onMySMXCClistener != null) {
					onMySMXCClistener.remai_more(v);
				}
				// sd
				// JumpUtils.jumpto(context,
				// ClassificationOfGoodsActivity.class,
				// null);
			}
		});
		initGridView2(holder);
	}

	/**
	 * 初始化特设市场
	 * 
	 * @param holder
	 */
	private void initTesheshichang(ViewHolder holder) {
		// TODO Auto-generated method stub
		if (blist != null) {
			if (!blist.isEmpty()) {
				new xUtilsImageLoader(context).display(holder.iv_pingpaituijian, blist.get(0).getBimage());
			}

		}

		holder.ll_pingpaituijian.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (onMySMXCClistener != null) {
					onMySMXCClistener.teSheClistener(v, 0);
				}
				// JumpUtils.jumpto(context, Brand_RecommendationActivity.class,
				// );
			}
		});
		holder.ll_xianliangjiangping.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (onMySMXCClistener != null) {
					onMySMXCClistener.teSheClistener(v, 1);
				}
				// JumpUtils.jumpto(context,
				// Brand_RecommendationActivity.class,
				// );
			}
		});
		holder.ll_tiantiantejia.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (onMySMXCClistener != null) {
					onMySMXCClistener.teSheClistener(v, 2);
				}
				// JumpUtils.jumpto(context, Brand_RecommendationActivity.class,
				// );
			}
		});
		holder.ll_lvshechuxing.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (onMySMXCClistener != null) {
					onMySMXCClistener.teSheClistener(v, 3);
				}
				// JumpUtils.jumpto(context, Brand_RecommendationActivity.class,
				// );
			}
		});

	}

	/**
	 * 下面 那个热卖分类
	 * 
	 * @param holder
	 */
	private void initGridView2(ViewHolder holder) {
		// TODO Auto-generated method stub
		// PcModel
		if (pclist != null) {
			holder.show_gridview2.setAdapter(new AbstractBaseAdapter<PcModel>(pclist) {

				@Override
				public View getAdapterViewAtPosition(final int position, View convertView, ViewGroup parent) {
					// TODO Auto-generated method stub

					if (convertView == null) {
						convertView = ((Activity) (context)).getLayoutInflater().inflate(R.layout.gridview2_adapter, parent, false);
					}
					final PcModel model = getItem(position);
					TextView name = com.carinsurance.adapter.ViewHolder.get(convertView, R.id.content);
					ImageView img = com.carinsurance.adapter.ViewHolder.get(convertView, R.id.img);
					LinearLayout ll = com.carinsurance.adapter.ViewHolder.get(convertView, R.id.ll);
					new xUtilsImageLoader(context).display(img, model.getPcimage());

					name.setText(model.getPcname());
					// new xUtilsImageLoader(context).display(img,
					// model.getScimage());
					ll.setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub

							if (onMySMXCClistener != null) {
								onMySMXCClistener.remaiFenleiClistener(model, v, position);
							}

						}
					});

					return convertView;

				}
			});
		}

		// final List<String> list = new ArrayList<String>();
		// // for (int i = 0; i < 4; i++) {
		// list.add("汽车配饰");
		// list.add("汽车影音");
		// list.add("清洁用品");
		// list.add("汽车保养");
		// }

	}

	/**
	 * 服务的
	 * 
	 * @param holder
	 */
	private void initGridView0(ViewHolder holder) {
		// TODO Auto-generated method stub
		holder.servicetype_GridView.setAdapter(new AbstractBaseAdapter<SeriverTypeitemModel>(fuwu_list) {

			@Override
			public View getAdapterViewAtPosition(final int position, View convertView, final ViewGroup parent) {
				// TODO Auto-generated method stub

				if (convertView == null) {
					convertView = ((Activity) (context)).getLayoutInflater().inflate(R.layout.servicetype_gridview_item, parent, false);
				}

				if (fuwu_list != null) {
					if (!fuwu_list.isEmpty()) {
						final SeriverTypeitemModel model = getItem(position);
						TextView name = com.carinsurance.adapter.ViewHolder.get(convertView, R.id.content);
						ImageView img = com.carinsurance.adapter.ViewHolder.get(convertView, R.id.img);
						LinearLayout item_click = com.carinsurance.adapter.ViewHolder.get(convertView, R.id.item_click);
						item_click.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method
								// stub
								if (onFuWuClistener != null) {
									onFuWuClistener.fuwuButtonClistener(position, parent, model, v);
								}
							}
						});
						name.setText(model.getScname());
						new xUtilsImageLoader(context).display(img, model.getScimage());
					}
				}

				return convertView;

			}
		});
	}

	static class ViewHolder {
		public LinearLayout llbtn_more;
		public ImageView iv_pingpaituijian;
		public LinearLayout ll_lvshechuxing;
		public LinearLayout ll_tiantiantejia;
		public LinearLayout ll_xianliangjiangping;
		public LinearLayout ll_pingpaituijian;
		public TextView sm1_shuoming;
		public TextView sm1_smxc;
		public ImageView sm1_image;
		public TextView sm_shuoming;
		public TextView sm_smxc;
		public ImageView sm_image;
		public ScrollView sv;
		TextView weather_tianqi;
		LinearLayout shangmenxiche;
		ImageView add_car;
		TextView des;
		GridView servicetype_GridView;
		GridView show_gridview2;
		ImageView tianqi_img;
		FrameLayout show_tianqi;

		LinearLayout shangmenbaoyang;
	}

	/**
	 * 初始化天氣
	 * 
	 * @param convertView
	 */
	private void initWeather(View convertView, ViewHolder holder) {
		// TODO Auto-generated method stub

		if (weather != null) {
			try {
				StringBuilder buff = new StringBuilder("");
				buff.append("" + weather.getResults().get(0).getWeather_data().get(0).getWeather());
				buff.append(" " + weather.getResults().get(0).getWeather_data().get(0).getTemperature());
				long time = System.currentTimeMillis();
				String tim = CalendarTools.getFormatTime(time, "yyyy,MM,dd");
				String[] ti = tim.split(",");
				int a = Integer.parseInt(ti[0]);
				int b = Integer.parseInt(ti[1]);
				int c = Integer.parseInt(ti[2]);
				holder.weather_tianqi.setText("" + CalendarTools.getInstance().getStringCaculateWeekDay(a, b, c));
				buff.append(" | " + "" + CalendarTools.getInstance().getStringCaculateWeekDay(a, b, c));
				holder.weather_tianqi.setText("" + buff.toString());
				holder.des.setText("" + weather.getResults().get(0).getIndex().get(1).getDes());
				// new BitmapHelp().displayImage(context, holder.tianqi_img,
				// weather.getResults().get(0).getWeather_data().get(0).getDayPictureUrl());
				List<String> tianqi = RegexUtils.getCustomRegex(weather.getResults().get(0).getWeather_data().get(0).getDayPictureUrl(), "[a-z]+[^\\w][png]+");// "[a-z]{+}[^\\]"
				try {
					new BitmapHelp().displayImage(context, holder.tianqi_img, "assets/weather/" + tianqi.get(0));
				} catch (Exception e) {
					// TODO: handle exception
				}
			} catch (Exception e) {
				// TODO: handle exception
			}

			// Log.v("aaa","只付出是"+tianqi.size());
			// if (StringUtil.isNullOrEmpty(weather_list)) {
			// try {
			// long time = System.currentTimeMillis();
			// String tim = CalendarTools.getFormatTime((double) time,
			// "yyyy,MM,dd");
			// String[] ti = tim.split(",");
			// int a = Integer.parseInt(ti[0]);
			// int b = Integer.parseInt(ti[1]);
			// int c = Integer.parseInt(ti[2]);
			// holder.weather_tianqi.setText("" + weather_list.get(6).getName()
			// + "|" + CalendarTools.getInstance().getStringCaculateWeekDay(a,
			// b, c));
			// } catch (Exception e) {
			// // TODO: handle exception
			// }

			// }
		}
	}

	/**
	 * 初始化ViewPager
	 * 
	 * @param convertView
	 */
	private void initViewPager(View convertView) {
		// Log.v("aaa", "ad_list的大小是===》" + ad_list);
		// Log.v("aaa", "ad_list的大小是===》" + ad_list.toString());
		adapter = new ViewPagerAdapter(context, ad_list);
		mLoopViewPager = (AutoScrollViewPager) convertView.findViewById(R.id.loopViewPager);
		CirclePageIndicator indicator = (CirclePageIndicator) convertView.findViewById(R.id.indicators);
		// TestViewPagerAdapter mm=new TestViewPagerAdapter(context);
		// FrameLayout.LayoutParams lp = new
		// FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
		// DisplayUtil.dip2px(context, 250));
		// mLoopViewPager.setLayoutParams(lp);
		// 显示要显示的界面
		mLoopViewPager.setCurrentItem(0);
		// 预先加载的页数
		mLoopViewPager.setOffscreenPageLimit(8);
		mLoopViewPager.setAdapter(adapter);
		mLoopViewPager.setInterval(5000);// 5秒滚动一次
		mLoopViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
		// mLoopViewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_CYCLE);
		mLoopViewPager.startAutoScroll();

		indicator.setViewPager(mLoopViewPager);
	}

	public void setWeather(BaiduWeatherModel weather) {
		this.weather = weather;
	}

	public void setFuwuModel(SeriverTypeModel seriverTypeModel) {
		fuwu_list = seriverTypeModel.getSclist();
		ad_list = seriverTypeModel.getAdlist();
		blist = seriverTypeModel.getBlist();
		pclist = seriverTypeModel.getPclist();

		// Log.v("aaa","广工列表==》"+ad_list.toString());
		// Log.v("aaa","seriverTypeModel.getAdlist()=="+seriverTypeModel.getAdlist());

		try {
			SeriverTypeitemModel0 = fuwu_list.get(0);
			SeriverTypeitemModel1 = fuwu_list.get(1);
			fuwu_list.remove(0);
			fuwu_list.remove(0);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void setOnTop2FuWuClistener(OnMySMXCClistener onMySMXCClistener) {
		this.onMySMXCClistener = onMySMXCClistener;
	}

	public void setOnFuWuItemClistener(OnFuWuClistener onFuWuClistener) {
		this.onFuWuClistener = onFuWuClistener;
	}

	public interface OnFuWuClistener {
		void fuwuButtonClistener(int position, ViewGroup parent, SeriverTypeitemModel model, View v);
	}

	public interface OnMySMXCClistener {
		void topleftButtonClistener(SeriverTypeitemModel SeriverTypeitemModel0, View v);

		void toprightButtonClistener(SeriverTypeitemModel SeriverTypeitemModel1, View v);

		// 特色市场0、品牌推荐 1、限量精品 2、天天特价 3、绿色出行
		void teSheClistener(View v, int type);

		void remaiFenleiClistener(PcModel pcmodel, View v, int position);

		void remai_more(View v);

	}

}
