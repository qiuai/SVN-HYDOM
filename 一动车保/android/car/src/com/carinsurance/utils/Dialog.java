package com.carinsurance.utils;

import java.util.Calendar;
import java.util.Locale;

import com.carinsurancer.car.R;
import com.lidroid.xutils.util.LogUtils;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
//http://www.eoeandroid.com/thread-5603-1-1.html
//好不容易找到的，给你分享：
//AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom)); 
//然后自定义自己的样式就可以了
//<?xml version="1.0" encoding="utf-8"?> 
//<resources> 
//    <style name="AlertDialogCustom" parent="@android:style/AlertDialog"> 
//        <item name="android:textColor">#00FF00</item> 
//        <item name="android:typeface">monospace</item> 
//        <item name="android:textSize">10sp</item> 
//    </style> 
//</resources>   
public class Dialog {
	// 设置时间的dialoge
	private String YearMonthDaySetting;
	private UserDateDialogClistener userDateDialogClistener;
	private DialogClistener dialogClistener;
	private ViewDialogClistener viewdialogClistener;

	private AlertDialog arDialog;

	public void Diaglog() {

	}

	/**
	 * 记录语言
	 */
	private int languages = 0;

	// private List<AreaModel> areaModelscountry, areaModelssheng,
	// areaModelscity;

	// /**
	// * 语言切换"language"文件中放入language为key 0：中文 1:英文 2：简体中文
	// */
	// public void LanguageSwitching(final Context context) {
	// // TODO Auto-generated method stub
	// final String[] items = new String[] { "中文", "English", "繁体中文" };
	// new AlertDialog.Builder(context)
	// .setTitle(
	// context.getResources().getString(
	// R.string.settingLanguage))
	// .setSingleChoiceItems(items, 0,
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	// // TODO Auto-generated method stub
	// languages = which;
	// }
	// })
	// .setNegativeButton(
	// context.getResources().getString(R.string.ok),
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	// // TODO Auto-generated method stub
	// MySharePreferences preferences = new MySharePreferences(
	// context, "language");
	// // ShowToast(getApplicationContext(),
	// // "--->which");
	// switch (languages) {
	// case 0:
	// Log.v("aaa",
	// "放入成功"
	// + preferences.put(
	// "language", 0));
	// break;
	// case 1:
	// // preferences.put("language", 1);
	// Log.v("aaa",
	// "放入成功"
	// + preferences.put(
	// "language", 1));
	// break;
	// case 2:
	// // preferences.put("language", "tw_name");
	// Log.v("aaa",
	// "放入成功"
	// + preferences.put(
	// "language", 2));
	// break;
	// }
	// Toast.makeText(context, "设置成功,重启程序后生效",
	// Toast.LENGTH_SHORT).show();
	// // System.exit(0);
	// if (dialogClistener != null) {
	// dialogClistener.ok();
	// }
	// // Resources resources = getResources();
	// // Configuration config =
	// // resources.getConfiguration();
	// // switch(which)
	// // {
	// //
	// // case 0:
	// // config.locale = Locale.SIMPLIFIED_CHINESE;
	// // //简体中文
	// // //TAIWAN 台湾，繁体中文
	// // //US美国，英文
	// // break;
	// // case 1:
	// // config.locale = Locale.US;
	// // break;
	// // case 2:
	// // config.locale = Locale.TAIWAN;
	// // break;
	// // }
	// // DisplayMetrics dm =
	// // resources.getDisplayMetrics();
	// // resources.updateConfiguration(config, dm);
	// // String cwjLocale =
	// // getResources().getConfiguration().locale.getCountry();
	// // Log.v("aaa", cwjLocale);
	// }
	// })
	// .setPositiveButton(
	// context.getResources().getString(R.string.cancel),
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	// // TODO Auto-generated method stub
	// if (dialogClistener != null) {
	// dialogClistener.ret();
	// }
	// }
	// }).show();
	// // .setNegativeButton("返回", new DialogInterface.OnClickListener() {
	// //
	// // @Override
	// // public void onClick(DialogInterface dialog, int which) {
	// // // TODO Auto-generated method stub
	// //
	// // }
	// // }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
	// //
	// // @Override
	// // public void onClick(DialogInterface dialog, int which) {
	// // // TODO Auto-generated method stub
	// //
	// // }
	// // }).show();
	// }

	/**
	 * 消息框
	 * 
	 * @param context
	 * @param title
	 * @param message
	 */
	public void CreateDialog(Context context, String title, String message) {
		arDialog=new AlertDialog.Builder(context).setTitle(title).setMessage(message).setNegativeButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (dialogClistener != null) {
					dialogClistener.ok();
				}
			}
		}).setPositiveButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (dialogClistener != null) {
					dialogClistener.ret();
				}
			}
		}).show();
	}

	public void CreateDialog(Context context, String title, String message, final View view) {
		arDialog=new AlertDialog.Builder(context).setTitle(title).setMessage(message).setView(view).setNegativeButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (viewdialogClistener != null) {
					viewdialogClistener.ok(view);
				}
			}
		}).setPositiveButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (viewdialogClistener != null) {
					viewdialogClistener.ret(view);
				}
			}
		}).show();
	}

	/**
	 * 只有View的dialog
	 * 
	 * @param context
	 * @param view
	 */
	public void CreateDialog(Context context, View view) {
		arDialog = new AlertDialog.Builder(context).setView(view).show();
	}
	/**
	 * 只有View的dialog
	 * 
	 * @param context
	 * @param view
	 */
	public void CreateDialog(Context context, View view,int themeres) {
		arDialog=new AlertDialog.Builder(new ContextThemeWrapper(context, themeres)).setView(view).show();
//		arDialog = new AlertDialog.Builder(context).setView(view).show();
	}
	public void setViewDialogCanClose(boolean is_close) {
		if (arDialog != null) {
			if (is_close == true) {
				arDialog.setCancelable(true);
			} else {
				arDialog.setCancelable(false);
			}
		}

	}

	public void DialogDissMiss() {
		if (arDialog != null) {
			arDialog.dismiss();
		}
	}

	public void setOnDialogClistener(DialogClistener clistener) {
		dialogClistener = clistener;
	}

	public interface DialogClistener {
		void ok();

		void ret();
	}

	public void setOnDialogClistener(ViewDialogClistener viewdialogclistener) {
		viewdialogClistener = viewdialogclistener;
	}

	public interface ViewDialogClistener {
		void ok(View view);

		void ret(View view);
	}

	/**
	 * 初始化语言 0 中文 1英文 3 繁体中文 -1 不变 获取到系统自带的语言参数
	 */
	public void initLanguage(Context context) {

		MySharePreferences preferences = new MySharePreferences(context, "language");
		int whitch = preferences.get("language", 0);
		Log.v("onn", "" + whitch);

		Resources resources = context.getResources();
		Configuration config = resources.getConfiguration();
		LogUtils.d("config.locale=" + config.locale);

		if (config.locale.equals(Locale.SIMPLIFIED_CHINESE)) {
			preferences.put("language", 0);
			whitch = 0;
		} else if (config.locale.equals(Locale.US)) {
			preferences.put("language", 1);
			whitch = 1;
		} else if (config.locale.equals(Locale.ENGLISH)) {
			preferences.put("language", 1);
			whitch = 1;
		} else if (config.locale.equals("en_US")) {
			preferences.put("language", 1);
			whitch = 1;
		} else if (config.locale.equals(Locale.SIMPLIFIED_CHINESE)) {
			preferences.put("language", 0);
			whitch = 0;
		} else if (config.locale.equals(Locale.TAIWAN)) {
			preferences.put("language", 2);
			whitch = 2;
		} else if (config.locale.equals(Locale.TRADITIONAL_CHINESE)) {
			preferences.put("language", 2);
			whitch = 2;
		} else if (config.locale.equals("zh_HK")) {
			preferences.put("language", 2);
			whitch = 2;
		} else if (config.locale.equals("zh_CN")) {
			preferences.put("language", 0);
			whitch = 0;
		} else {
			preferences.put("language", 0);
			whitch = 0;
		}
		LogUtils.d("Y" + Dialog.getSystemLanguageType(context));
		// switch (whitch) {
		// case 0:
		// config.locale = Locale.SIMPLIFIED_CHINESE;
		// break;
		// case 1:
		// config.locale = Locale.US;
		// break;
		// case 2:
		// config.locale = Locale.TAIWAN;
		// break;
		// }
		preferences.put("panduanlanguage", whitch);
		// if (whitch.equals("cn_name")) {
		// config.locale = Locale.SIMPLIFIED_CHINESE;
		// } else if (whitch.equals("en_name")) {
		// config.locale = Locale.US;
		// } else if (whitch.equals("tw_name")) {
		// config.locale = Locale.TAIWAN;
		// }
		// config.locale = Locale.SIMPLIFIED_CHINESE; //简体中文
		// TAIWAN 台湾，繁体中文
		// US美国，英文
		DisplayMetrics dm = resources.getDisplayMetrics();
		resources.updateConfiguration(config, dm);
	}

	/**
	 * 无法复用
	 * 
	 * @return
	 */
	public static String getSystemLanguageType(Context context) {
		MySharePreferences preferences = new MySharePreferences(context, "language");
		int LanguageType = 0;
		LanguageType = preferences.get("language", 0);

		switch (LanguageType) {
		case 0:
			return "cn_name";
		case 1:
			return "en_name";
		case 2:
			return "tw_name";
		}
		return "";
	}

	/**
	 * 无法复用
	 * 
	 * @return
	 */
	public static String getSystemLanguageTypegrzx(Context context) {
		MySharePreferences preferences = new MySharePreferences(context, "language");
		int LanguageType = 0;
		LanguageType = preferences.get("language", 0);

		switch (LanguageType) {
		case 0:
			return "name";
		case 1:
			return "en_name";
		case 2:
			return "tw_name";
		}
		return "";
	}

	/**
	 * 无法复用
	 * 
	 * @return
	 */
	public static String getSystemLanguageTypegy(Context context) {
		MySharePreferences preferences = new MySharePreferences(context, "language");
		int LanguageType = 0;
		LanguageType = preferences.get("language", 0);

		switch (LanguageType) {
		case 0:
			return "zh-cn";
		case 1:
			return "en-us";
		case 2:
			return "zh-tw";
		}
		return "";
	}

	/**
	 * 设置年月日的Dialog(/)
	 */
	public static void createDateDialog(Context context, final TextView textView) {

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
				textView.setText(c.get(Calendar.YEAR) + "/" + strMonth + "/" + strDate);
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
		// 显示对话框
		dialog.show();
	}

	/**
	 * 设置年月日的Dialog(-)
	 */
	public static void createDateDialog_1(Context context, final TextView textView) {

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

	/** 设置时间的dialog */
	public static void createTimeDialog(Context context, final TextView textView) {
		final Calendar c = Calendar.getInstance();// 定义日历控件
		final TimePicker tp = new TimePicker(context);

		tp.setIs24HourView(true);
		tp.setBackgroundColor(0x50556098);
		AlertDialog di = new AlertDialog.Builder(context).setIcon(R.drawable.ic_launcher).setTitle("设置").setView(tp).setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 取得设置的开始时间，秒及毫秒设为0
				c.setTimeInMillis(System.currentTimeMillis());
				tp.clearFocus();// 解决手动输入返回不了时间的问题
				c.set(Calendar.HOUR_OF_DAY, tp.getCurrentHour());
				c.set(Calendar.MINUTE, tp.getCurrentMinute());
				c.set(Calendar.SECOND, 0);
				c.set(Calendar.MILLISECOND, 0);
				textView.setText("" + tp.getCurrentHour() + ":" + tp.getCurrentMinute());

			}
		}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
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
	 * 设置年月日的Dialog
	 */
	public void createDateDialog(final Context context) {

		// final UserInfoModel userInfoModel2;
		// userInfoModel2 = userInfoModel;
		// if(userInfoModel2!=null)
		// {
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
				// userInfoModel2.setBirthday(c.get(Calendar.YEAR) + "-"
				// + strMonth + "-" + strDate);
				// UserDataSet userDataSet = new UserDataSet(context);
				//
				// userDataSet.UserDataSet(userInfoModel2, 0, "", false);
				if (userDateDialogClistener != null) {
					userDateDialogClistener.ok();
				}

				// textView.setText(c.get(Calendar.YEAR) + "/" +
				// strMonth
				// + "/" + strDate);
			}
		}, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
		// 显示对话框
		dialog.show();
		// }
	}

	public void setOnDataChangeClistener(UserDateDialogClistener userDateDialogClistener) {
		this.userDateDialogClistener = userDateDialogClistener;
	}

	public interface UserDateDialogClistener {
		void ok();

	}

	// public Dialog(Context ct)
	// {
	// context=ct;
	// }
	// private Handler handler = new Handler() {
	// @Override
	// public void handleMessage(Message msg) {
	// switch (msg.what) {
	// case 1:
	// Utils.ExitPrgress(context);
	// Is_Area_Dialog(context);
	// areaAdapter = new AreaAdapter(context,
	// areaModelscountry);
	// holderView.area_country_name_spinner.setAdapter(areaAdapter);
	// break;
	// case 2:
	// Utils.ExitPrgress(context);
	// Utils.showMessage(context, "连接网络出错");
	// break;
	// case 3:
	// Utils.ExitPrgress(context);
	// areaAdapter = new AreaAdapter(context,
	// areaModelssheng);
	// holderView.area_province_name_spinner.setAdapter(areaAdapter);
	// break;
	// case 4:
	// Utils.ExitPrgress(context);
	// areaAdapter = new AreaAdapter(context,
	// areaModelscity);
	// holderView.area_city_name_spinner.setAdapter(areaAdapter);
	// break;
	// case 5:
	// Utils.ExitPrgress(context);
	// Utils.showMessage(context, msg.getData()
	// .getString("注册"));
	//
	// Intent intent =new Intent(context,
	// ValidationEmailActivity.class);
	// intent.putExtra(Key, ""+et_mail.getText().toString().trim());
	// startActivity(intent);
	// ((Activity) context).finish();
	// break;
	// case 6:
	// Utils.ExitPrgress(RegirstrationActivity.this);
	// Utils.showMessage(RegirstrationActivity.this, msg.getData()
	// .getString("注册"));
	// break;
	// default:
	// break;
	// }
	// }
	// };

	// public void Getareathread(final Context context,final String
	// type,TextView textView) {
	// Utils.showPrgress(context);
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	// Map<String, Object> map = new HashMap<String, Object>();
	//
	// int t=Integer.parseInt(type);
	// switch(t)
	// {
	// case 0:
	// map.put("languageType", "cn_name");
	// break;
	// case 1:
	// map.put("languageType", "en_name");
	// break;
	// case 2:
	// map.put("languageType", "tw_name");
	// break;
	// }
	// String content = Utils.sendRequest(map, Constants.BadiUrl
	// + "area");
	// if (content != null) {
	// // System.out.println("结果是:" + content);
	// Results results = Utils.checkResult_NNN(
	// context, content);
	// if (results != null && results.getRetmsg() != null) {
	// // System.out.println("得到的结果是：" + results.getRetmsg());
	// try {
	// areaModelscountry = Utils.getListByJsonString(
	// results.getRetmsg(), AreaModel.class);
	// handler.sendEmptyMessage(1);
	// } catch (JSONException e) {
	// System.out.println("解析出错");
	// e.printStackTrace();
	// }
	// }
	// } else {
	// handler.sendEmptyMessage(2);
	// }
	// }
	// }).start();
	// }

}
