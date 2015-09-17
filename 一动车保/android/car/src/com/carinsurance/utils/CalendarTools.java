package com.carinsurance.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.carinsurancer.car.R;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class CalendarTools {
	Calendar c = Calendar.getInstance();

	/**
	 * CalendarTools的实例。
	 */
	private static CalendarTools mCalendarTools;

	/**
	 * 获取新的时间（加:如果9，改为09）
	 * 
	 * @param _tim
	 * @return
	 */
	public String getNewTime(int _tim) {
		if (_tim <= 9) {
			return "0" + _tim;
		} else
			return "" + _tim;
	}

	/**
	 * 获取现在的年份
	 */
	public String getNowYear() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();

		return dateFormat.format(date);

	}

	/**
	 * 获取现在的月份
	 */
	public String getNowMonth() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
		Date date = new Date();
		return dateFormat.format(date);

	}

	/**
	 * 获取现在的日
	 */
	public String getNowDay() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
		Date date = new Date();
		return dateFormat.format(date);

	}

	/**
	 * 获取当前是某月的第几天
	 * 
	 * @return
	 */
	public String getToDayIs_DayOfMonth() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	/**
	 * 将以毫秒为单位的时间解析成指定格式
	 * 
	 * @param time
	 *            毫秒为单位的时间
	 * @param fomatTimeType
	 *            时间格式 如 : yyyy-MM-dd HH-mm-ss;//年月日，时分秒
	 * 
	 * @return
	 */
	public static String getFormatTime(double time, String fomatTimeType) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(fomatTimeType);
		String a = dateFormat.format(time);

		return a;
	}

	/**
	 * 是否是润年
	 */
	public boolean isLeapYear(int year) {
		if (year % 400 == 0) {
			return true;
		} else if (year % 4 == 0) {
			return true;
		}
		return false;
	}

	// private int getMondayPlus() {
	// // TODO Auto-generated method stub
	// Calendar cd=Calendar.getInstance();
	// int dayOfWeek=cd.get(Calendar.DAY_OF_WEEK)-1;
	// if(dayOfWeek==1)
	// {
	// return 0;
	// }
	// else
	// {
	// return 1-dayOfWeek;
	// }
	// }

	/**
	 * 获取指定的那一 年 月 中有多少天
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 天数
	 */
	public int getMonthHasHowManeyDay(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
			return 31;

		if (month == 4 || month == 6 || month == 9 || month == 11)
			return 30;
		if (month == 2 && isLeapYear(year)) {
			return 29;
		} else if (month == 2 && !isLeapYear(year)) {
			return 28;
		}
		return -1;
	}

	// public String getNowTime(String pattern)
	// {
	// Date date=new Date();
	// SimpleDateFormat simpleDateFormat=new SimpleDateFormat(pattern);
	// String a=simpleDateFormat.format(date);
	// return a;
	// }
	/**
	 * 
	 * 根据年月日得到今天是星期几
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return(0：星期日 1：星期一 2:星期二 3:星期三 4：星期四。。。类推 )
	 */
	public int getCaculateWeekDay(int y, int m, int d) {
		String S = null;
		GregorianCalendar gc = new GregorianCalendar();
		final String[] kor_week = { "0", "1", "2", "3", "4", "5", "6" };
		gc.set(y, m - 1, d);
		String week = kor_week[gc.get(Calendar.DAY_OF_WEEK) - 1];
		S = "这天是星期" + week;
		return Integer.parseInt(week);
	}

	/**
	 * 
	 * 获取String格式的星期几
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return(星期日 星期一 星期二 星期三 4：星期四。。。类推 )
	 */
	public String getStringCaculateWeekDay(int y, int m, int d) {
		int a = getCaculateWeekDay(y, m, d);
		switch (a) {
		case 0:
			return "星期日";
		case 1:
			return "星期一";
		case 2:
			return "星期二";
		case 3:
			return "星期三";
		case 4:
			return "星期四";
		case 5:
			return "星期五";
		case 6:
			return "星期六";
		default:
			return "参数错误";
		}
	}

	protected String whichDayMonth() {
		// TODO Auto-generated method stub
		return "" + c.get(Calendar.DAY_OF_MONTH);
	}

	protected String whichDayYear() {
		// TODO Auto-generated method stub

		return "" + c.get(Calendar.DAY_OF_YEAR);
	}

	// public String getCurrentWeekday()
	// {//返回指定日期（星期几）
	// int mondayPlue=this.getMondayPlus();
	// //GregorianCalendar是Calendar的一个具体子类，提供了世界上大多数国家/地区使用的标准日历系统
	// GregorianCalendar currentDate=new GregorianCalendar();
	// //获取本周日的系统时间
	// currentDate.add(GregorianCalendar.DATE, mondayPlue+6);
	// Date monday=currentDate.getTime();
	//
	// DateFormat df=DateFormat.getDateInstance();//获取系统时间格式化对象
	// String preMonday=df.format(monday);//对系统时间做格式化
	// return preMonday;
	// }
	// ""+CalendarTools.getInstance().getNowTime("yyyy年MM月dd日HH时mm分ss秒")
	// ""+CalendarTools.getInstance().getNowTime("HH:mm:ss")
	/**
	 * 获取现在的时间
	 * 
	 * @param type
	 * @return
	 */
	public String getNowTime(String type) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(type);

		return dateFormat.format(new Date());
	}

	/**
	 * 设置年月日的Dialog
	 */
	public void createDateDialog(Context context, final TextView textView) {

		// 获取当前时间
		Calendar calendar = Calendar.getInstance();
		// 时间对话框
		DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
			/**
			 * 日期对话框时间设置
			 */
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// 获取当前时间
				Calendar c = Calendar.getInstance();
				// 设置年
				c.set(Calendar.YEAR, year);
				// 设置月
				c.set(Calendar.MONTH, monthOfYear);
				// 设置日
				c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				// 系统自定义输出月份小一月 所以输出MONTH值时强转整形+1
				// 判断 如果月日小于10 在前面加0
				String strMonth = "";
				String strDate = "";
				if ((Integer.valueOf(c.get(Calendar.MONTH)) + 1) < 10) {
					strMonth = "0" + (Integer.valueOf(c.get(Calendar.MONTH)) + 1);
				} else {
					strMonth = String.valueOf(Integer.valueOf(c.get(Calendar.MONTH)) + 1);
				}
				if ((Integer.valueOf(c.get(Calendar.DATE))) < 10) {
					strDate = "0" + (Integer.valueOf(c.get(Calendar.DATE)));
				} else {
					strDate = String.valueOf(Integer.valueOf(c.get(Calendar.DATE)));
				}
				// 将时间设置到txtDate
				// YearMonthDaySetting=""+c.get(Calendar.YEAR) + "/" +
				// strMonth
				// + "/" + strDate;
				textView.setText(c.get(Calendar.YEAR) + "-" + strMonth + "-" + strDate);
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
		// 显示对话框
		dialog.show();
	}

	/**
	 * 设置年月日的Dialog start end
	 */
	/**
	 * 
	 * @param context
	 * @param textView
	 * @param judge
	 *            start or end
	 */
	public void createDateDialog_1(final Context context, final TextView textView, final String judge) {

		// 获取当前时间
		Calendar calendar = Calendar.getInstance();
		// 时间对话框
		DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
			/**
			 * 日期对话框时间设置
			 */
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// 获取当前时间
				Calendar c = Calendar.getInstance();
				// 设置年
				c.set(Calendar.YEAR, year);
				// 设置月
				c.set(Calendar.MONTH, monthOfYear);
				// 设置日
				c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				// 系统自定义输出月份小一月 所以输出MONTH值时强转整形+1
				// 判断 如果月日小于10 在前面加0
				String strMonth = "";
				String strDate = "";
				if ((Integer.valueOf(c.get(Calendar.MONTH)) + 1) < 10) {
					strMonth = "0" + (Integer.valueOf(c.get(Calendar.MONTH)) + 1);
				} else {
					strMonth = String.valueOf(Integer.valueOf(c.get(Calendar.MONTH)) + 1);
				}
				if ((Integer.valueOf(c.get(Calendar.DATE))) < 10) {
					strDate = "0" + (Integer.valueOf(c.get(Calendar.DATE)));
				} else {
					strDate = String.valueOf(Integer.valueOf(c.get(Calendar.DATE)));
				}
				// 将时间设置到txtDate
				// YearMonthDaySetting=""+c.get(Calendar.YEAR) + "/" +
				// strMonth
				// + "/" + strDate;
				textView.setText(c.get(Calendar.YEAR) + "-" + strMonth + "-" + strDate);
				// if (judge.equals("start")) {
				// Constants.START_TIME = textView.getText()
				// .toString().trim();
				// } else if (judge.equals("end")) {
				// Constants.END_TIME = textView.getText().toString()
				// .trim();
				// }
				// Constants.content_or_time = true;
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
		// 显示对话框
		dialog.show();
	}

	/** 设置时间的dialog */
	public void createTimeDialog(Context context, final TextView textView) {
		final Calendar c = Calendar.getInstance();// 定义日历控件
		final TimePicker tp = new TimePicker(context);
		tp.setIs24HourView(true);
		tp.setBackgroundColor(0x50556098);
		AlertDialog di = new AlertDialog.Builder(context).setIcon(R.drawable.ic_launcher).setTitle("设置").setView(tp).setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 取得设置的开始时间，秒及毫秒设为0
				c.setTimeInMillis(System.currentTimeMillis());
				c.set(Calendar.HOUR_OF_DAY, tp.getCurrentHour());
				c.set(Calendar.MINUTE, tp.getCurrentMinute());
				c.set(Calendar.SECOND, 0);
				c.set(Calendar.MILLISECOND, 0);
				textView.setText("" + tp.getCurrentHour() + ":" + tp.getCurrentMinute());

			}
		})

		.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		}).create();
		c.setTimeInMillis(System.currentTimeMillis());// 将当前系统时间放入日历控件
		Log.v("aaa", "" + c.getTimeInMillis());
		tp.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));// 设置timepick控件为日历控件的小时数
		Log.v("aaa", "" + tp.getCurrentHour());
		tp.setCurrentMinute(c.get(Calendar.MINUTE));// 设置timepick的时间为日历控件的分钟数
		Log.v("aaa", "" + tp.getCurrentMinute());
		di.show();
	}

	/**
	 * 获取CalendarTools的实例。
	 * 
	 * @return CalendarTools的实例。
	 */
	public static CalendarTools getInstance() {
		if (mCalendarTools == null) {
			mCalendarTools = new CalendarTools();
		}
		return mCalendarTools;
	}

	/**
	 * 全是以秒为单位的字符串 将以秒为单位的时间转化为支付窜 如：刚刚，之内的
	 * 
	 * @param
	 * @param fabiaoTime
	 *            发表时间
	 * @return
	 */
	public static String getTimeChangeToString(Context context, String fabiaoTime) {
		try {
			if (fabiaoTime == null)
				return "";
			if (fabiaoTime.equals("-")) {
				return fabiaoTime;
			}
			String xianzaiTime = "" + System.currentTimeMillis();
			Double time1 = Double.parseDouble((xianzaiTime));
			Double time2 = Double.parseDouble((fabiaoTime + "000"));

			Double timeCha = time1 - time2;

			if (timeCha <= 60000) {
				return context.getResources().getString(R.string.ganggang);
			} else if (timeCha <= 1800000)// 30分钟内
			{
				return (((int) (timeCha / 60000)) + context.getResources().getString(R.string.fenzhongqian));
			} else if (timeCha <= 3600000) {
				return context.getResources().getString(R.string.yixiaoshiqian);
			} else if (timeCha <= 86400000) {
				return ((int) ((timeCha) / 3600000) + context.getResources().getString(R.string.xiaoshiqian));
			} else if (timeCha <= 172800000)// 2天前
			{
				return context.getResources().getString(R.string.twotianqian);
			} else if (timeCha <= 259200000)// 4天前
			{
				return context.getResources().getString(R.string.fourtianqian);
			} else {
				return getFormatTime(Double.parseDouble(("" + time2)), "MM-dd");// 返回发表时间
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return fabiaoTime;
	}

	/**
	 * 对原始日期增加[或减少]天数
	 * 
	 * @param srcDate
	 *            ：原始日期
	 * @param day
	 *            ：增加的天数：为正值;减少的天数：为负值
	 * @return
	 */
	public static Date addDays(Date srcDate, int day) {
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = Calendar.getInstance();
		cale.setTime(srcDate);
		cale.set(Calendar.DATE, cale.get(Calendar.DATE) + day);
		Date date = null;
		try {
			date = dft.parse(dft.format(cale.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

}
// /**
// * 获取给定时间距离现在多久，若超过2天，则显示月份和号数，用于动态时间轴
// * @param $dataTime 数据时间
// * @param $showType 时间显示样式字符串
// * @return bool|string
// */
// function getTime($dataTime,$showType='m-d')
// {
// $theTime = time();
// $equalTime = $theTime - $dataTime;
// if ($equalTime < 3600) {
// $return = (int)($equalTime / 60) . L('minutes_ago');
// } elseif ($equalTime < 86400) {
// $return = (int)($equalTime / 3600) . L('hours_ago');
// } elseif ($equalTime < 172800) {
// $return = L('yesterday');
// } elseif ($equalTime < 259200) {
// $return = L('before_yesterday');
// } else {
// $return = date($showType, $dataTime);
// }
// return $return;
// }
