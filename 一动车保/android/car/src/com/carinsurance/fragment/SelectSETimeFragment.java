package com.carinsurance.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.carinsurance.infos.YTimeModel;
import com.carinsurance.utils.CalendarTools;
import com.carinsurance.utils.DisplayUtil;
import com.carinsurancer.car.R;

public class SelectSETimeFragment extends Fragment implements OnClickListener {

	/**
	 * 6*7格=42
	 */
	private int zonggeshu = 42;
	// private ViewPager viewPager;
	private GridView mGridView;

	private String TAG = "CalendarFragment";
	/** GridView列数 */
	private final int COLUMN_NUMBER = 7;
	/** GridView每个子类的高度 */
	private int HEIGHT_CHILDHEIGHT = 47;
	/** 月 */
	private int month;
	/** 年 */
	private int year;
	/** 是否为瑞年 */
	private boolean IsLeapYear;
	/** 当前月的前一个月的天数 */
	private int before_month_day;
	/** 保存当前月的天数 */
	private int month_day;
	/** 保存下一个月的天数 */
	private int after_month_day;
	/** 接收ViewPager的layout */
	private LinearLayout import_layout;
	/** 分为三个阶段，第一个阶段是上个月的，第二个阶段是本月的，第三个阶段是下月的 */
	private int jieduan = 0;
	private List<Integer> mlist;
	private CalendarTools mcalendarTools;
	private TextView tv_Year, tv_Month;

	private CalendarGridViewAdapter calendaradapter;
	private DisplayUtil displayUtil;

	private Integer color[];
	private int dip5_px;

	static CalendarFragment calendarFragment;

	private Button last_month, next_month;
	/**
	 * 往日里中加内容的监听器
	 */
	private CalendarListener calendarListener;
	/**
	 * 网日里中的Item项目设置侦听的监听器
	 */
	private CalendarGridViewListener calendarGridViewListener;

	private Map<String, Object> map;

	private int temp_year = 0, temp_month = 0;

	// private boolean is_betweenActivity = false;// 用于判断是否是期间内的活动
	private int temp_num;// 如果日历显示在当前月份则为0，否则上一月则-1，下一月则+1
	private Calendar calendar_temp;
	private int StartYY, StartMM, StartDD;
	private int EndYY, EndMM, EndDD;

	List<YTimeModel> YTimelist;

	int temp_day = 0;
	TextView text_yue;

	// 选中项
	private int selectposition = -1;
	CalendarOkCancelListener calendarOkCancelListener;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 3:
				try {
					// Utils.ExitPrgress(getActivity());

					setOnCalendarOnClistener(new CalendarListener() {

						@Override
						public boolean AddStartButton(int Year, int Month, int Day) {
							// TODO Auto-generated method stub
							return ShowStartButton(Year, Month, Day, StartYY, StartMM, StartDD);
						}

						@Override
						public boolean AddEndButton(int Year, int Month, int Day) {
							// TODO Auto-generated method stub
							return ShowEndButton(Year, Month, Day, EndYY, EndMM, EndDD);
						}

						@Override
						public String AddActivity(int Year, int Month, int Day) {
							// TODO Auto-generated method stub

							return "";
						}
					});
					calendaradapter.notifyDataSetChanged();
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			default:
				break;
			}
		}
	};

	public SelectSETimeFragment(List<YTimeModel> list) {
		// TODO Auto-generated constructor stub
		YTimelist = list;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		map = new HashMap<String, Object>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_calender_import_layout, null);
		import_layout = (LinearLayout) view.findViewById(R.id.fragment_calender_import_layout);
		view.findViewById(R.id.ll_zhou).setVisibility(View.GONE);
		text_yue = (TextView) view.findViewById(R.id.text_yue);
		try {
			temp_year = getArguments().getInt("year");
			temp_month = getArguments().getInt("month");
			temp_day = getArguments().getInt("day");
			Log.d("bbb", "temp_year=" + temp_year + "/temp_month=" + temp_month + "/temp_day=" + temp_day);
			// temp_year = 2015;
			// temp_month = 3;
		} catch (Exception e) {
			// TODO: handle exception
			temp_month = 0;
			temp_year = 0;
			Log.v("sss1", "报错:" + e.getMessage());
		}

		temp_num = 0;
		// setOnCalendarItemClistener(new CalendarGridViewListener() {
		//
		// @Override
		// public void ItemListener(AdapterView<?> arg0, View view,
		// int position, long arg3,
		// CalendarGridViewAdapter calendaradapter) {
		// // TODO Auto-generated method stub
		// // if (backdd(Constants.temp_map,
		// // calendaradapter.getYearMonthDay(position)).size() > 0) {
		// // Intent intent = new Intent(getActivity(),
		// // Data_Me_Activity.class);
		// // // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// // intent.putExtra("日期",
		// // calendaradapter.getYearMonthDay(position));
		// // Log.v("sss1", calendaradapter.getYearMonthDay(position)
		// // + " " + uid);
		// // intent.putExtra("uid", uid);
		// // if (cid != null) {
		// // intent.putExtra("cid", cid);
		// // }
		// // intent.putExtra("fid", Utils.getUid(getActivity()));
		// // startActivity(intent);
		// // } else {
		//
		// // }
		// }
		// });
		LinearLayout queding = (LinearLayout) view.findViewById(R.id.queding);

		LinearLayout cancel = (LinearLayout) view.findViewById(R.id.cancel);
		queding.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (calendarOkCancelListener != null) {
					calendarOkCancelListener.ok();
				}
			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (calendarOkCancelListener != null) {
					calendarOkCancelListener.cancle();
				}
			}
		});
		init(view);
		initData(temp_year, temp_month);
		initGridView();

		return view;
	}

	protected boolean ShowStartButton(int Y, int M, int D, int YY, int MM, int DD) {
		if (YY == Y && MM == M && DD == D) {
			return true;
		} else {
			return false;
		}
	}

	protected boolean ShowEndButton(int Y, int M, int D, int YY, int MM, int DD) {
		if (YY == Y && MM == M && DD == D) {
			return true;
		} else {
			return false;
		}
	}

	// 将时间中的年份提取出来并转换为int
	public static int BackYear(String time) {
		if (time != null) {
			return Integer.valueOf(time.substring(0, 4));
		} else {
			return 0;
		}

	}

	// 将时间中的月份提取出来并转换为int
	public static int BackMonth(String time) {
		if (time != null) {
			return Integer.valueOf(time.substring(5, 7));
		} else {
			return 0;
		}
	}

	// 将时间中的日提取出来并转换为int
	public static int BackDay(String time) {
		if (time != null) {
			return Integer.valueOf(time.substring(8, time.length()));
		} else {
			return 0;
		}
	}

	private void init(View view) {
		// TODO Auto-generated method stub
		mcalendarTools = CalendarTools.getInstance();
		calendar_temp = Calendar.getInstance();
		tv_Year = (TextView) view.findViewById(R.id.fragment_calendar_year);
		tv_Month = (TextView) view.findViewById(R.id.frament_calendar_month);
		mlist = new ArrayList<Integer>();
		color = new Integer[] {
				// 灰色
				R.drawable.calendar_gridview_click_gray,
				// 深蓝
				R.drawable.calendar_gridview_click_shen_blue,
				// 白色
				R.drawable.calendar_gridview_click_white,
				// 黄色
				R.drawable.calendar_gridview_click_yellow,

		};
		last_month = (Button) view.findViewById(R.id.fragment_last_month);
		next_month = (Button) view.findViewById(R.id.fragment_next_month);
		last_month.setVisibility(View.INVISIBLE);
		next_month.setVisibility(View.INVISIBLE);
		// last_month.setOnClickListener(SelectSETimeFragment.this);
		// next_month.setOnClickListener(SelectSETimeFragment.this);
	}

	/**
	 * 初始化数据 可以把时间 中的月份改成 01 或者 1
	 */
	private void initData(int nian, int yue) {
		// TODO Auto-generated method stub
		Log.v("aaa", "nian=" + nian + "/yue=" + yue);
		if (nian != 0 && yue != 0) {
			year = nian;
			month = yue;
			String mon = "" + month;// mcalendarTools.getNewTime(month);
			tv_Year.setText("" + year);
			String ad = "" + temp_day;
			tv_Month.setText("" + mon);
			text_yue.setText("月" + ad + "日");
		} else {
			year = Integer.parseInt(mcalendarTools.getNowYear());
			month = Integer.parseInt(mcalendarTools.getNowMonth());
			String mon = "" + month;// mcalendarTools.getNewTime(month);
			tv_Year.setText("" + year);
			String ad = "" + temp_day;
			tv_Month.setText("" + mon);
			text_yue.setText("月" + ad + "日");
		}
	}

	private void initGridView() {
		// TODO Auto-generated method stub
		if (month != 1 && month != 12) {
			before_month_day = mcalendarTools.getMonthHasHowManeyDay(year, month - 1);
			month_day = mcalendarTools.getMonthHasHowManeyDay(year, month);
			after_month_day = mcalendarTools.getMonthHasHowManeyDay(year, month + 1);
		} else if (month == 1) {
			before_month_day = mcalendarTools.getMonthHasHowManeyDay(year - 1, 12);
			month_day = mcalendarTools.getMonthHasHowManeyDay(year, month);
			after_month_day = mcalendarTools.getMonthHasHowManeyDay(year, month + 1);
		} else if (month == 12) {
			before_month_day = mcalendarTools.getMonthHasHowManeyDay(year, month - 1);
			month_day = mcalendarTools.getMonthHasHowManeyDay(year, month);
			after_month_day = mcalendarTools.getMonthHasHowManeyDay(year + 1, 1);
		}

		ViewHolder holder = new ViewHolder(before_month_day, month_day, after_month_day);
		mGridView = new GridView(getActivity());

		mGridView.setNumColumns(3);
		mGridView.setColumnWidth(HEIGHT_CHILDHEIGHT);
		mGridView.setScrollbarFadingEnabled(false);
		mGridView.setVerticalScrollBarEnabled(false);
		mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		// 去除GridView的边框
		// mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

		calendaradapter = new CalendarGridViewAdapter(holder);
		// mGridView.setDuplicateParentStateEnabled(true);
		mGridView.setAdapter(calendaradapter);
		// mGridView.setOnItemLongClickListener(new OnItemLongClickListener() {
		//
		// @Override
		// public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
		// int arg2, long arg3) {
		// // TODO Auto-generated method stub
		// Toast.makeText(getActivity(), ""+arg2, 0).show();
		// return true;
		// }
		// });
		Log.v("aaa", "成功运行了1");
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
				// TODO Auto-generated method stub

				if (calendarGridViewListener != null) {
					calendarGridViewListener.ItemListener(arg0, view, position, arg3, calendaradapter);
				}
				// Log.v("aaa","成功运行了");
				// Toast.makeText(getActivity(),
				// ""+calendaradapter.getItem(position), 0).show();
			}
		});
		Log.v("aaa", "成功运行了");
		import_layout.addView(mGridView);
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	// private class MyPagerAdapter extends PagerAdapter

	public class ViewHolder {
		int before_month_day;
		int month_day;
		int after_month_day;

		public ViewHolder(int before_month_day, int month_day, int after_month_day) {
			this.before_month_day = before_month_day;
			this.month_day = month_day;
			this.after_month_day = after_month_day;
		}

	}

	/**
	 * 具体思路是这样的，将本月，下月，上月的天数传过来，判断本月的一号是星期几 ，然后将它前面的填充为上月的天数，下月的似乎不用传过来也可以
	 * 
	 * @author Administrator
	 * 
	 */
	public class CalendarGridViewAdapter extends BaseAdapter {

		private List<Integer> mlist;
		private int before_month_day;
		private int month_day;
		private int after_month_day;
		// 记录三个阶段的数量
		private int yijieNumber = 0;
		private int erjieNumber = 0;
		private int sanjieNumber = 0;
		/**
		 * 当前月的第一天是星期几
		 */
		private int month_day0;

		public CalendarGridViewAdapter(ViewHolder vHolder) {
			before_month_day = vHolder.before_month_day;
			month_day = vHolder.month_day;
			after_month_day = vHolder.after_month_day;

			mlist = new ArrayList<Integer>();
			month_day0 = mcalendarTools.getCaculateWeekDay(year, month, 1);

			// 如果是星期4，那么前面三格将填充上个月的;
			if (month_day0 == 0) {
				for (int i = (before_month_day) - 6; i <= before_month_day; i++) {
					Log.v("aaa", "before_month_day - month_day0=" + (before_month_day - month_day0));
					mlist.add(i);
					yijieNumber++;
				}
			} else {
				for (int i = (before_month_day - month_day0) + 1; i <= before_month_day; i++) {
					Log.v("aaa", "before_month_day - month_day0=" + (before_month_day - month_day0));
					mlist.add(i);
					yijieNumber++;
				}
			}

			for (int i = 1; i <= month_day; i++) {
				mlist.add(i);
				erjieNumber++;
			}
			int mlistSize = mlist.size();
			for (int i = 1; i <= (zonggeshu - mlistSize); i++) {
				// Log.v(TAG,"mlist.size()======>"+mlist.size());
				mlist.add(i);
				sanjieNumber++;
			}

		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			if (YTimelist == null)
				return 0;
			return YTimelist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return YTimelist.get(position);
		}

		// 获取选中项的年月日
		public String getYearMonthDay(int position) {
			String str = "";
			String year = tv_Year.getText().toString();
			String month = tv_Month.getText().toString();
			String day = "" + getItem(position);
			if (position >= yijieNumber && position < (yijieNumber + erjieNumber)) {
				if (Integer.parseInt(day) < 10) {
					day = "0" + day;
				}

				str = year + "-" + month + "-" + day;
			}
			return str;

		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = LayoutInflater.from(getActivity());

			convertView = inflater.inflate(R.layout.select_timeitem, null);// #D2D2D2
			FrameLayout hong_yuan = (FrameLayout) convertView.findViewById(R.id.hong_yuan);
			if (selectposition == position) {

//				hong_yuan.setVisibility(View.VISIBLE);
				hong_yuan.setSelected(true);
			} else {
				hong_yuan.setSelected(false);
			}
			TextView tv = (TextView) convertView.findViewById(R.id.tv);
			tv.setTextColor(Color.parseColor("#000000"));
			FrameLayout ll = (FrameLayout) convertView.findViewById(R.id.ll);
			tv.setText("" + YTimelist.get(position).getStime() + "-" + YTimelist.get(position).getEtime());

			// // 在这里判定设置什么背景色
			// if (position >= 0 && position < yijieNumber) {
			// tv.setTextColor(Color.parseColor("#D2D2D2"));
			// } else if (position >= yijieNumber && position < (yijieNumber +
			// erjieNumber)) {
			//
			// tv.setTextColor(Color.parseColor("#000000"));
			// } else if (position >= (yijieNumber + erjieNumber) && position <
			// (yijieNumber + erjieNumber + sanjieNumber)) {
			// tv.setTextColor(Color.parseColor("#D2D2D2"));
			// }

			// // ll是整个gridView的每个item子类
			// LinearLayout ll = new LinearLayout(getActivity());
			// ll.setOrientation(LinearLayout.VERTICAL);
			// // ll.setClickable(true);
			// // ll0是item上面的小部分
			// LinearLayout ll0 = new LinearLayout(getActivity());
			// ll0.setOrientation(LinearLayout.HORIZONTAL);
			// // tv是显示日期
			// TextView tv = new TextView(getActivity());
			// tv.setTextSize(10);
			// tv.setText("" + mlist.get(position));
			// if (mlist.get(position) < 10) {
			// tv.setText("" + mlist.get(position) + "  ");
			// }
			//
			// // 在这里判定设置什么背景色
			// if (position >= 0 && position < yijieNumber) {
			// ll.setBackgroundResource(color[0]);
			// } else if (position >= yijieNumber && position < (yijieNumber +
			// erjieNumber)) {
			//
			// ll.setBackgroundResource(color[2]);
			// } else if (position >= (yijieNumber + erjieNumber) && position <
			// (yijieNumber + erjieNumber + sanjieNumber)) {
			// ll.setBackgroundResource(color[0]);
			// }
			// int todayIsDay_Of_Month =
			// Integer.parseInt(mcalendarTools.getToDayIs_DayOfMonth());
			// //
			// // 这是确定哪天今天，并且改背景为黄色
			// if (Integer.parseInt(tv_Year.getText().toString()) ==
			// Integer.parseInt(mcalendarTools.getNowYear()) &&
			// Integer.parseInt(tv_Month.getText().toString()) ==
			// Integer.parseInt(mcalendarTools.getNowMonth()) && position ==
			// todayIsDay_Of_Month + yijieNumber - 1) {
			// ll.setBackgroundResource(color[3]);
			// }
			// dip5_px = DisplayUtil.dip2px(getActivity(), 5);
			// if (position >= yijieNumber && position < (yijieNumber +
			// erjieNumber)) {
			// tv.setTextColor(Color.parseColor("#00a2d0"));
			// }
			// tv.setPadding(dip5_px, dip5_px, 0, 0);
			// ll0.addView(tv);
			//
			// Log.v("ttt", "" + mlist.get(position));
			//
			// // ll1是下面的视图
			// LinearLayout ll1 = new LinearLayout(getActivity());
			// ll1.setOrientation(LinearLayout.HORIZONTAL);
			//
			// if (calendarListener != null && position >= yijieNumber &&
			// position < (yijieNumber + erjieNumber)) {
			// // 开始活动的提示（）
			// if
			// (calendarListener.AddStartButton(Integer.parseInt(tv_Year.getText().toString().trim()),
			// Integer.parseInt(tv_Month.getText().toString().trim()),
			// mlist.get(position))) {
			// // 在这里添加开始
			// ll0.addView(AddStartTitle());
			// }
			// // 添加结束的提示
			// if
			// (calendarListener.AddEndButton(Integer.parseInt(tv_Year.getText().toString().trim()),
			// Integer.parseInt(tv_Month.getText().toString().trim()),
			// mlist.get(position))) {
			// ll0.addView(AddEndTitle());
			// }
			// // 添加活动的内容
			// if
			// (!StringUtil.isNullOrEmpty(calendarListener.AddActivity(Integer.parseInt(tv_Year.getText().toString().trim()),
			// Integer.parseInt(tv_Month.getText().toString().trim()),
			// mlist.get(position)))) {
			// TextView lvcheng = new TextView(getActivity());
			// // 在这里设置有哪些活动
			// lvcheng.setText(calendarListener.AddActivity(Integer.parseInt(tv_Year.getText().toString().trim()),
			// Integer.parseInt(tv_Month.getText().toString().trim()),
			// mlist.get(position)));
			// String key =
			// Integer.parseInt(tv_Year.getText().toString().trim()) + "-" +
			// Integer.parseInt(tv_Month.getText().toString().trim()) + "-" +
			// mlist.get(position);
			// map.put(key, lvcheng.getText().toString());
			//
			// int sp6_px = DisplayUtil.sp2px(getActivity(), 6);
			// // lvcheng是活动显示的textView
			// lvcheng.setTextSize(sp6_px);
			// lvcheng.setPadding(dip5_px, 0, dip5_px, 0);
			// lvcheng.setTextColor(Color.parseColor("#ffffff"));
			// ll.setBackgroundResource(color[1]);
			// ll1.addView(lvcheng);
			// }
			// }
			//
			// int dip50_px = DisplayUtil.dip2px(getActivity(), 50);
			// LinearLayout.LayoutParams vl = new
			// LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, dip50_px);
			// ll1.setLayoutParams(vl);
			//
			// ll.addView(ll0);
			// ll.addView(ll1);

			return convertView;
		}

		/**
		 * 添加一个开始的提示
		 * 
		 * @return
		 */
		public Button AddStartTitle() {
			Button start = new Button(getActivity());
			start.setFocusable(false);
			start.setTextSize(5);
			int dip20_px = DisplayUtil.dip2px(getActivity(), 15);
			LinearLayout.LayoutParams ml = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, dip20_px);
			start.setLayoutParams(ml);
			start.setTextColor(Color.parseColor("#ffffff"));
			start.setBackgroundResource(R.drawable.start_time);
			return start;
		}

		/**
		 * 添加一个结束的提示
		 * 
		 * @return
		 */
		public Button AddEndTitle() {
			Button start = new Button(getActivity());
			start.setFocusable(false);
			start.setTextSize(5);
			int dip20_px = DisplayUtil.dip2px(getActivity(), 15);
			LinearLayout.LayoutParams ml = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, dip20_px);
			start.setLayoutParams(ml);
			start.setTextColor(Color.parseColor("#ffffff"));
			start.setBackgroundResource(R.drawable.over_time);
			return start;
		}

		public List<Integer> getMlist() {
			return mlist;
		}

		public void setMlist(List<Integer> mlist) {
			this.mlist = mlist;
		}

		/**
		 * 根据gridView 选中项，獲取年份
		 * 
		 * @param position
		 *            是GridView中选中的是第几项
		 * @return
		 */
		public String getYear(int position) {

			int year = Integer.parseInt(tv_Year.getText().toString());
			int month = Integer.parseInt(tv_Month.getText().toString());

			int yijie = getYijieNumber();
			int erjie = getErjieNumber();
			int sanjie = getSanjieNumber();
			if (month != 1 && month != 12) {
				return ("" + year);
			} else if (month == 1) {

				if ((position + 1) <= yijie) {
					return ("" + (year - 1));
				}
			} else if (month == 12) {
				if ((position + 1) > erjie) {
					return ("" + (year + 1));
				}
			} else {
				return ("" + year);
			}
			return ("" + year);

		}

		/**
		 * 根据GridView选中项，获取月份
		 * 
		 * @param position
		 * @return
		 */
		public String getMonth(int position) {
			int year = Integer.parseInt(tv_Year.getText().toString());
			int month = Integer.parseInt(tv_Month.getText().toString());
			int yijie = getYijieNumber();
			int erjie = getErjieNumber();
			int sanjie = getSanjieNumber();

			if (month != 1 && month != 12) {
				if ((position + 1) <= yijie) {
					return ("" + (month - 1));
				} else if ((position + 1) > yijie && ((position + 1) <= (yijie + erjie))) {
					return ("" + (month));
				} else if (((position + 1) > (yijie + erjie))) {
					return ("" + (month + 1));
				}
				return ("" + (month + 1));
			} else if (month == 1) {

				if ((position + 1) <= yijie) {
					return ("12");
				}
			} else if (month == 12) {
				if ((position + 1) > erjie) {
					return ("1");
				}
			} else {
				return ("" + (month + 1));
			}
			return ("" + (month + 1));

		}

		public String getDay(int position) {
			// int year = Integer.parseInt(tv_Year.getText().toString());
			// int month = Integer.parseInt(tv_Month.getText().toString());
			// int yijie = getYijieNumber();
			// int erjie = getErjieNumber();
			// int sanjie = getSanjieNumber();
			// int month_day0 = mcalendarTools.getCaculateWeekDay(year, month,
			// 1);
			// //第一阶段
			// if ((position + 1) <= yijie) {
			// if(month==1)
			// {
			// //点击处的年
			// int y=Integer.parseInt(getYear(position));
			// //点击处的月
			// int m=Integer.parseInt(getMonth(position));
			// //单机处的那一个月的天数
			// int dd=mcalendarTools.getMonthHasHowManeyDay(y, m);
			// //空位数量
			// int kongweishu=mcalendarTools.getCaculateWeekDay(y,m,1);
			// for(int i=0;i<=position;i++)
			// {
			// // int a=(dd-())
			// }
			//
			//
			// }
			//
			//
			//
			//
			// return ("" + (position + 1));
			// } else if ((position + 1) > yijie && (position + 1) <= (yijie +
			// erjie)) {
			// return ("" + (position - (yijie - 1)));
			// } else if ((position + 1) > (erjie + yijie)) {
			// return (""+((zonggeshu-yijie-erjie)+1));
			// }
			return ("" + mlist.get(position));
		}

		public int getYijieNumber() {
			return yijieNumber;
		}

		public void setYijieNumber(int yijieNumber) {
			this.yijieNumber = yijieNumber;
		}

		public int getErjieNumber() {
			return erjieNumber;
		}

		public void setErjieNumber(int erjieNumber) {
			this.erjieNumber = erjieNumber;
		}

		public int getSanjieNumber() {
			return sanjieNumber;
		}

		public void setSanjieNumber(int sanjieNumber) {
			this.sanjieNumber = sanjieNumber;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fragment_last_month:
			UpPage();
			--temp_num;

			break;
		case R.id.fragment_next_month:
			DownPage();
			++temp_num;

			break;
		}
	}

	/**
	 * 获取当前时间日期的Data
	 * 
	 * @param timetime
	 * @return
	 * @throws ParseException
	 */
	private Date Get___Data(String timetime) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		date = df.parse(timetime);
		return date;
	}

	// 上翻
	public void UpPage() {
		if (!map.isEmpty()) {
			map.clear();
		}
		selectposition = -1;
		// 不是第一月的话
		if (Integer.parseInt(tv_Month.getText().toString()) != 1) {
			if (Integer.parseInt(tv_Month.getText().toString()) < 11) {
				tv_Month.setText("0" + (Integer.parseInt(tv_Month.getText().toString()) - 1));

			} else {
				tv_Month.setText("" + (Integer.parseInt(tv_Month.getText().toString()) - 1));
			}
		} else {
			tv_Month.setText("12");
			tv_Year.setText("" + (Integer.parseInt(tv_Year.getText().toString()) - 1));
		}
		notifyCalendar(Integer.parseInt(tv_Year.getText().toString()), Integer.parseInt(tv_Month.getText().toString()));
	}

	/**
	 * 更新日历
	 * 
	 * @param Year
	 * @param Month
	 */
	public void notifyCalendar(int Year, int Month) {
		int yijie = 0;
		int erjie = 0;
		int sanjie = 0;
		this.year = Year;
		this.month = Month;
		if (Month != 1 && Month != 12) {
			before_month_day = mcalendarTools.getMonthHasHowManeyDay(Year, Month - 1);
			month_day = mcalendarTools.getMonthHasHowManeyDay(Year, Month);
			after_month_day = mcalendarTools.getMonthHasHowManeyDay(Year, Month + 1);
		} else if (Month == 1) {
			before_month_day = mcalendarTools.getMonthHasHowManeyDay(Year - 1, 12);
			Log.v("aaa", "befff___>" + before_month_day);
			month_day = mcalendarTools.getMonthHasHowManeyDay(Year, Month);
			after_month_day = mcalendarTools.getMonthHasHowManeyDay(Year, Month + 1);
		} else if (Month == 12) {
			before_month_day = mcalendarTools.getMonthHasHowManeyDay(Year, Month - 1);
			month_day = mcalendarTools.getMonthHasHowManeyDay(Year, Month);
			after_month_day = mcalendarTools.getMonthHasHowManeyDay(Year + 1, 1);
		}
		List<Integer> mylist = new ArrayList<Integer>();
		int month_day0 = mcalendarTools.getCaculateWeekDay(Year, Month, 1);
		if (month_day0 == 0) {
			for (int i = (before_month_day) - 6; i <= before_month_day; i++) {
				Log.v("aaa", "before_month_day - month_day0=" + (before_month_day - month_day0));
				mylist.add(i);
				yijie++;
			}
		} else
			for (int i = (before_month_day - month_day0) + 1; i <= before_month_day; i++) {
				mylist.add(i);
				yijie++;
				Log.v("aaa", "i=====>" + i);
			}
		for (int i = 1; i <= month_day; i++) {
			mylist.add(i);
			erjie++;
		}
		int mlistSize = mylist.size();
		for (int i = 1; i <= (zonggeshu - mlistSize); i++) {
			// Log.v(TAG,"mlist.size()======>"+mlist.size());
			mylist.add(i);
			sanjie++;
		}
		calendaradapter.setMlist(mylist);
		calendaradapter.setYijieNumber(yijie);
		calendaradapter.setErjieNumber(erjie);
		calendaradapter.setSanjieNumber(sanjie);
		initData(year, month);
		// tv_Year.setText("" + year);
		// tv_Month.setText("" + month);
		calendaradapter.notifyDataSetInvalidated();
	}

	/** 点击下一页后下翻 */
	public void DownPage() {
		if (!map.isEmpty()) {
			map.clear();
		}
		selectposition = -1;
		if (Integer.parseInt(tv_Month.getText().toString()) != 12) {
			if (Integer.parseInt(tv_Month.getText().toString()) < 9) {
				tv_Month.setText("0" + (Integer.parseInt(tv_Month.getText().toString()) + 1));
			} else {
				tv_Month.setText("" + (Integer.parseInt(tv_Month.getText().toString()) + 1));
			}
		} else {
			tv_Month.setText("01");
			tv_Year.setText("" + (Integer.parseInt(tv_Year.getText().toString()) + 1));
		}
		notifyCalendar(Integer.parseInt(tv_Year.getText().toString()), Integer.parseInt(tv_Month.getText().toString()));
	}

	public static CalendarFragment getInstance() {
		if (calendarFragment == null) {
			return new CalendarFragment();
		}
		return calendarFragment;
	}

	public void setOnCalendarOnClistener(CalendarListener cListener) {
		calendarListener = cListener;
	}

	public void setOnCalendarItemClistener(CalendarGridViewListener clistener) {
		calendarGridViewListener = clistener;
	}

	public void getMyMapItemClistener(CalendarFragment.getHashMap hashMap) {
		hashMap.getMap(map);
	}

	public interface CalendarListener {
		boolean AddStartButton(int Year, int Month, int Day);

		boolean AddEndButton(int Year, int Month, int Day);

		String AddActivity(int Year, int Month, int Day);
	}

	public interface CalendarGridViewListener {
		void ItemListener(AdapterView<?> arg0, View view, int position, long arg3, CalendarGridViewAdapter calendaradapter);
	}

	public interface getHashMap {
		void getMap(Map<String, Object> map);
	}// CalendarGridViewAdapter

	public void getCalendarAdapter(getAdapter adapter) {
		adapter.getAdapter(calendaradapter);
	}

	public interface getAdapter {
		void getAdapter(CalendarGridViewAdapter adapter);
	}

	/**
	 * 
	 * 获取选中项目
	 * 
	 * @param get
	 */
	public void getSelectorItem(getSelectInterface get) {
		get.getSelect(selectposition);
	}

	public interface getSelectInterface {
		void getSelect(int selectposition);
	}

	// 设置选中的item
	public void setSelectorItem(int position) {
		selectposition = position;
	}

	public TextView getTv_Month() {
		return tv_Month;
	}

	public void setTv_Month(TextView tv_Month) {
		this.tv_Month = tv_Month;
	}

	public void setOnOkCancelListener(CalendarOkCancelListener calendarOkCancelListener) {
		this.calendarOkCancelListener = calendarOkCancelListener;

	}

	public interface CalendarOkCancelListener {
		void ok();

		void cancle();
	}
}