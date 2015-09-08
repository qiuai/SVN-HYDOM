package com.carinsurance.abcpinying;

import java.util.HashMap;
import java.util.List;
import java.util.Map;





import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurancer.car.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class ChoiceCrewAdapter extends BaseAdapter implements SectionIndexer {
	private List<SortModel> list = null;
	private Context mContext;

	private setSelectWeiFalse mSelectWeiFalse;
//	private DisplayImageOptions options;
	/**
	 * 保存选择的数据的map
	 */
	private Map<Integer, Object> map;

	public ChoiceCrewAdapter(Context mContext, List<SortModel> list) {
		this.mContext = mContext;
		this.list = list;
		map = new HashMap<Integer, Object>();
//		options = new DisplayImageOptions.Builder()
//				.showStubImage(R.drawable.ic_stub)
//				.showImageForEmptyUri(R.drawable.noimage)
//				.showImageOnFail(R.drawable.noimage).cacheInMemory()
//				.cacheOnDisc().displayer(new RoundedBitmapDisplayer(5)).build();
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<SortModel> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return this.list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final SortModel mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(
					R.layout.abc_choice_crew_item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.abc_choice_img = (ImageView) view
					.findViewById(R.id.abc_choice_img);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		// 根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);

		// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if (position == getPositionForSection(section)) {
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		} else {
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
		viewHolder.tvTitle.setText(this.list.get(position).getName());
		new xUtilsImageLoader(mContext).display(viewHolder.abc_choice_img, list.get(position).getCbimage());
//		ImageLoader.getInstance().displayImage(
//				Constants.BadiDownImgUrl + list.get(position).getHead()+Constants.appPhoto4img,
//				viewHolder.abc_choice_img, options);
		SynchronizedToTheMap(view, position);

		return view;
	}

	final class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
		ImageView abc_choice_img;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	@Override
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	@Override
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	public void OnSelectWeiFalseClistener(setSelectWeiFalse set) {
		mSelectWeiFalse = set;
	}

	/**
	 * 这个接口用于设置点击之后将全部选中/不选中置为空
	 * 
	 * @author Administrator
	 * 
	 */
	interface setSelectWeiFalse {
		void setFalse();
	}

	/**
	 * 将所有的全部选中放入map中
	 */
	public void CheckAll() {
		for (int i = 0; i < list.size(); i++) {
			if (!map.containsKey(i))
				map.put(i, list.get(i));
			notifyDataSetChanged();
		}
	}

	/**
	 * 清除掉所有
	 */
	public void ClearAll() {
		map.clear();
		notifyDataSetChanged();
	}

	/**
	 * 这个方法是为了和map同步，主要是用于点击全选和不全选后的操作（原理是点击全选后将list中的数据全部加入到map当中 配合CheckAll使用）
	 * ,这个操作必须根据map中的数据来设置，否则没办法
	 */
	private void SynchronizedToTheMap(View view, int position) {
		CheckBox mCheckbox = (CheckBox) view
				.findViewById(R.id.ChoiceCrew_select);
		// 这里要与map中的数据同步
		if (map.containsKey(position)) {
			mCheckbox.setChecked(true);
		} else {
			mCheckbox.setChecked(false);
		}

	}

	@Override
	public Object[] getSections() {
		return null;
	}

	public Map<Integer, Object> getMap() {
		return map;
	}

	public void setMap(Map<Integer, Object> map) {
		this.map = map;
	}

	public List<SortModel> getList() {
		return list;
	}

	public void setList(List<SortModel> list) {
		this.list = list;
	}

}