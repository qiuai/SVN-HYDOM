package com.carinsurance.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.carinsurance.abcpinying.CharacterParser;
import com.carinsurance.abcpinying.ChoiceCrewAdapter;
import com.carinsurance.abcpinying.ClearEditText;
import com.carinsurance.abcpinying.PinyinComparator;
import com.carinsurance.abcpinying.SideBar;
import com.carinsurance.abcpinying.SortModel;
import com.carinsurance.abcpinying.SideBar.OnTouchingLetterChangedListener;
import com.carinsurance.infos.CarType;
import com.carinsurance.infos.CarTypeitemModel;
import com.carinsurance.infos.SeriverTypeitemModel;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.utils.JsonUtil;
import com.carinsurance.utils.JumpUtils;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurancer.car.R;
import com.lidroid.xutils.http.RequestParams;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 选择需要被邀请的好友(站内邀请好友列表)
 * 
 * @author Administrator Toast.make
 */
public class ChoiceCarActivity extends BaseActivity {

	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private ChoiceCrewAdapter adapter;
	private ClearEditText mClearEditText;
	private Message message;
	private Bundle bundle;

	/**
	 * 汉字转换成拼音的类
	 */
	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	/**
	 * 根据拼音来排列ListView里面的数据类
	 */
	private PinyinComparator pinyinComparator;

	/**
	 * 选择所有的checkbox
	 */
	private CheckBox checkall;
	/**
	 * 为了让CheckBox不让他响应一次侦听事件而弄的
	 */
	private boolean isSelectClick = false;

	// private List<SortModel> List;
	private String pageNum = "9999";
	private String fid = "-1";

	CarType carType;
	SortModel sort;
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:

				try {
					Utils.ExitPrgress(ChoiceCarActivity.this);
					Utils.showMessage(ChoiceCarActivity.this, "网络错误");
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			case 2:
				Utils.ExitPrgress(ChoiceCarActivity.this);
				try {
					// initViews();
					Updata();
				} catch (Exception e) {
					// TODO: handle exception
				}

				break;
			case 3:
				Utils.ExitPrgress(ChoiceCarActivity.this);
				// onLoad();
				// try {
				// Utils.showMessage(ChoiceCrewActivity.this, getResources()
				// .getString(R.string.l_xa10));
				// } catch (Exception e) {
				// // TODO: handle exception
				// }
				break;
			case 4:
				Utils.ExitPrgress(ChoiceCarActivity.this);
				try {
					// Utils.showMessage(PinyinSeeOtherPeopleFriendActivity.this,
					// results2.getRetmsg());
					Intent intent = new Intent();
					intent.putExtra("res", msg.getData().getString("res"));
					setResult(21, intent);
					finish();
				} catch (Exception e) {
					// TODO: handle exception
				}
			default:
				break;
			}
		}
	};

	@Override
	public void initNetmessageSuccess(String message, String task) {

		super.initNetmessageSuccess(message, task);
		try {
			Utils.ExitPrgress(ChoiceCarActivity.this);
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (task.equals(Task.GET_CARBRAND)) {
			try {
				String results = message;
				carType = JsonUtil.getEntityByJsonString(results, CarType.class);
				if (carType.getResult().equals("" + NetUtils.NET_SUCCESS_001)) {
					initViews();

				}

				// Updata();
				// adapter.setFuwuModel(seriverTypeModel);
				// is_getfuwu_finish = true;
				// if (is_getfuwu_finish == true && is_gettianqi_finish == true)
				// {
				// adapter.notifyDataSetChanged();
				// }
				// xListView.stopLoadMore();
				// xListView.stopRefresh();
			} catch (Exception e) {
			}
		}

	};

	String fuwutypeid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// goToTheTitle();
		setContentView(R.layout.choice_car);
		// initViews();
		sort = (SortModel) JumpUtils.getSerializable(ChoiceCarActivity.this);
		if(sort==null)
		{
			sort=new SortModel();
			SeriverTypeitemModel s=new SeriverTypeitemModel();
			sort.setSeriverTypeitemModel0(s);
		}
//		fuwutypeid = JumpUtils.getString(ChoiceCarActivity.this, "fuwutypeid");

		initInterNet();

	}

	private void initViews() {

		// 实例化汉字转拼音类
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();
		sideBar = (SideBar) findViewById(R.id.sidrbar);
		dialog = (TextView) findViewById(R.id.dialog);
		SourceDateList = new ArrayList<SortModel>();
		sideBar.setTextView(dialog);

		// 设置右侧触摸监听
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}
			}
		});

		// 更新了数据
		initData();
		// 根据a-z进行排序源数据
		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new ChoiceCrewAdapter(this, SourceDateList);
		sortListView = (ListView) findViewById(R.id.country_lvcountry);
		sortListView.setAdapter(adapter);
		// 初始化listView的侦听
		initsortListViewClistener();
		// initCheckAll();
		mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

		// 根据输入框输入值的改变来过滤搜索
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		this.findViewById(R.id.return_btn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JumpUtils.jumpfinish(ChoiceCarActivity.this);
			}
		});
	}

	protected void initsortListViewClistener() {
		// TODO Auto-generated method stub
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				SortModel sortModel = (SortModel) adapter.getItem(position);

//				sortModel.setFuwutypeid(fuwutypeid);
				sortModel.setType("0");
				
				
				
				if (sort != null) {
//					sortModel.setSctop(sort.getSctop());
//					sortModel.setScname(sort.getScname());
//					sortModel.setScimage(sort.getScimage());
//					sortModel.setScremark(sort.getScremark());
					sort.setType(sortModel.getType());
					sort.setCbid(sortModel.getCbid());
					sort.setCbimage(sortModel.getCbimage());
					sort.setId(sortModel.getId());
					sort.setFletter(sortModel.getFletter());
					sort.setName(sortModel.getName());
				}
				// JumpUtils.jumpto(ChoiceCarActivity.this,
				// SelectModels1Activity.class, map);
				JumpUtils.jumpto(ChoiceCarActivity.this, SelectModels1Activity.class, sort, false);

				// // 接着是对checkbox的
				// CheckBox check = (CheckBox)
				// view.findViewById(R.id.ChoiceCrew_select);//
				// // check是选中的，那么去掉key，没有选中则添加key到map
				// if (check.isChecked()) {
				// // if(checkall.isChecked()==true)
				// // {
				// // checkall.setChecked(false);
				// // }
				// // 因为在这里checkall设置setChecked为false的时候
				// if (checkall.isChecked() == true) {
				// isSelectClick = true;
				// checkall.setChecked(false);
				// }
				// check.setChecked(false);
				// if (adapter.getMap().containsKey(position)) {
				// adapter.getMap().remove(position);
				// }
				//
				// } else if (check.isChecked() == false) {
				// check.setChecked(true);
				// adapter.getMap().put(position, adapter.getItem(position));
				// }
				// Iterator it = adapter.getMap().entrySet().iterator();
				// while (it.hasNext()) {
				// Map.Entry entry = (Map.Entry) it.next();
				// int key = (Integer) entry.getKey();
				// String value = ((SortModel) entry.getValue()).getName();
				// Log.v("aaa", "key=" + key + "value=" + value);
				// }
				//
				// // setButtonUpNumber();

			}
		});

	}

	private void initInterNet() {
		// TODO Auto-generated method stub
		Utils.showPrgress(ChoiceCarActivity.this);

		RequestParams params = new RequestParams();
		if (!StringUtil.isNullOrEmpty(Utils.getUid(ChoiceCarActivity.this)) && !StringUtil.isNullOrEmpty(Utils.getToken(ChoiceCarActivity.this))) {
			params.addBodyParameter("uid", Utils.getUid(ChoiceCarActivity.this));
			params.addBodyParameter("token", Utils.getToken(ChoiceCarActivity.this));
		}
		NetUtils.getIns().post(Task.GET_CARBRAND, params, handler);

		// MyThreadTool.fixedThreadPool.execute(new Runnable() {
		// @Override
		// public void run() {
		// List = new ArrayList<SortModel>();
		// String invite_content = Utils.getRequest(Constants.BadiUrl
		// + "friends?uid="
		// + Utils.getUid(ChoiceCrewActivity.this) + "&page=" + 1
		// + "&pageNum=" + pageNum);
		// if (invite_content != null) {
		// Results results = Utils.checkResult_NNN(
		// ChoiceCrewActivity.this, invite_content);
		// if (results != null && !results.getRetmsg().equals("null")) {
		// // Log.v(TAG, "我是解析出来的数据:" + results.getRetmsg());
		// System.out.println("我是解析出来的数据:" + results.getRetmsg());
		// try {
		// List = JSONUtils.getListByJsonString(
		// results.getRetmsg(), SortModel.class);
		// // mList.addAll(list);
		// mHandler.sendEmptyMessage(2);
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// } else {
		// mHandler.sendEmptyMessage(3);
		// }
		// } else {
		//
		// mHandler.sendEmptyMessage(1);
		// }
		//
		// }
		// });
	}

	protected void Updata() {
		System.out.println("执行更新数据");
		SourceDateList.addAll(filledData(carType.getList()));
		Collections.sort(SourceDateList, pinyinComparator);
		adapter.notifyDataSetChanged();
	}

	// private void initCheckAll() {
	// // TODO Auto-generated method stub
	// checkall = (CheckBox) this.findViewById(R.id.checkall);
	// checkall.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	//
	// @Override
	// public void onCheckedChanged(CompoundButton buttonView, boolean
	// isChecked) {
	// // TODO Auto-generated method stub
	// Log.v("aaa", "isChecked==>" + isChecked);
	// if (isChecked == true) {
	// if (isSelectClick == true) {
	// isSelectClick = false;
	// } else {
	// adapter.CheckAll();
	// }
	//
	// } else if (isChecked == false) {
	// if (isSelectClick == true) {
	// isSelectClick = false;
	// } else {
	// adapter.ClearAll();
	// }
	//
	// }
	// setButtonUpNumber();
	// }
	// });
	// }

	protected void initData() {
		// TODO Auto-generated method stub
		// SourceDateList =
		// filledData(getResources().getStringArray(R.array.data));
		SourceDateList.addAll(filledData(carType.getList()));
	}

	/**
	 * 为ListView填充数据
	 * 
	 * @param date
	 * @return
	 */
	private List<SortModel> filledData(List<CarTypeitemModel> date) {
		List<SortModel> mSortList = new ArrayList<SortModel>();
		if (date != null && !date.isEmpty()) {
			System.out.println("不为空");
			for (int i = 0; i < date.size(); i++) {
				SortModel sortModel = new SortModel();
				sortModel.setCbid(date.get(i).getCbid());
				sortModel.setCbimage(date.get(i).getCbimage());
				sortModel.setId(date.get(i).getId());
				sortModel.setFletter(date.get(i).getFletter());
				sortModel.setName(date.get(i).getCbname());
				// sortModel.setIs_firend(date.get(i).getIs_firend());
				// 汉字转换成拼音
				String pinyin = characterParser.getSelling(date.get(i).getCbname());
				String sortString = pinyin.substring(0, 1).toUpperCase();

				// 正则表达式，判断首字母是否是英文字母
				if (sortString.matches("[A-Z]")) {
					sortModel.setSortLetters(sortString.toUpperCase());
				} else {
					sortModel.setSortLetters("#");
				}

				// sortModel.setMotto("这只是一项测试");

				mSortList.add(sortModel);
			}
		} else {
			System.out.println("为空");
		}
		return mSortList;

	}

	/**
	 * 根据输入框中的值来过滤数据并更新ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<SortModel> filterDateList = new ArrayList<SortModel>();
		// 是否为空
		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
		} else {
			filterDateList.clear();
			for (SortModel sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		// 根据a-z进行排序
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
	}

	/**
	 * 设置按钮上面的显示的图片的数量
	 */
	protected void setButtonUpNumber() {
		if (adapter != null) {
			int KeyNum = 0;
			Iterator it = adapter.getMap().entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				// int key=(Integer) entry.getKey();
				// String value=((SortModel)entry.getValue()).getName();
				// Log.v("aaa", "key="+key+"value="+value);
				KeyNum++;
			}
			String text = String.format(getResources().getString(R.string.Finish_), KeyNum);
			((TextView) (this.findViewById(R.id.Finish_btn))).setText(text);
			// Finish_btn.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View arg0) {
			// Iterator it = adapter.getMap().entrySet().iterator();
			// String id_num = "";
			// while (it.hasNext()) {
			// Map.Entry entry = (Map.Entry) it.next();
			// String value = ((SortModel) entry.getValue()).getId();
			// id_num += ((SortModel) entry.getValue()).getId() + ",";
			// }
			// // System.out.println("打印出来的数据是:"
			// // + id_num.substring(0, id_num.length() - 1));
			// if (!StringUtil.isNullOrEmpty(id_num)) {
			// message = new Message();
			// bundle = new Bundle();
			// message.what = 4;
			// Log.v("sss1", "得到的是:" + id_num + " " + id_num.length());
			// bundle.putString("res", id_num.substring(0, id_num.length() -
			// 1));
			//
			// message.setData(bundle);
			// mHandler.sendMessage(message);
			// }
			// }
			// });
		}
	}

	public ListView getSortListView() {
		return sortListView;
	}

	public void setSortListView(ListView sortListView) {
		this.sortListView = sortListView;
	}

	public ChoiceCrewAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(ChoiceCrewAdapter adapter) {
		this.adapter = adapter;
	}

	// /**
	// * 选择组员
	// *
	// * @author Administrator
	// *
	// */
	// public class ChoiceCrewAdapter extends BaseExpandableListAdapter {
	//
	// public String[] ABC = { "A", "C" };
	//
	// @Override
	// public int getGroupCount() {
	// // TODO Auto-generated method stub
	// return ABC.length;
	// }
	//
	// @Override
	// public View getGroupView(int groupPosition, boolean isExpanded,
	// View convertView, ViewGroup parent) {
	// // TODO Auto-generated method stub
	// TextView atv = new TextView(ChoiceCrewActivity.this);
	// atv.setText("" + ABC[groupPosition]);
	// return atv;
	// }
	//
	// @Override
	// public int getChildrenCount(int groupPosition) {
	// // TODO Auto-generated method stub
	// return 10;
	// }
	//
	// @Override
	// public View getChildView(int groupPosition, int childPosition,
	// boolean isLastChild, View convertView, ViewGroup parent) {
	// // TODO Auto-generated method stub
	// LayoutInflater inflater = LayoutInflater
	// .from(ChoiceCrewActivity.this);
	// convertView = inflater.inflate(R.layout.choice_crew_import_layout,
	// null);
	//
	// return convertView;
	// }
	//
	// @Override
	// public Object getChild(int groupPosition, int childPosition) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public long getChildId(int groupPosition, int childPosition) {
	// // TODO Auto-generated method stub
	// return 0;
	// }
	//
	// @Override
	// public Object getGroup(int groupPosition) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// @Override
	// public long getGroupId(int groupPosition) {
	// // TODO Auto-generated method stub
	// return 0;
	// }
	//
	// @Override
	// public boolean hasStableIds() {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// @Override
	// public boolean isChildSelectable(int groupPosition, int childPosition) {
	// // TODO Auto-generated method stub
	// return false;
	// }
	//
	// }
}
