package com.carinsurance.abcpinying;

import java.util.List;





import com.carinsurancer.car.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class SeeOtherPeopleAdapter extends BaseAdapter implements
		SectionIndexer {
	private List<SortModel> list = null;
	private Context mContext;
	private String cj_uid;

//	private DisplayImageOptions options;
	private ViewOnClistener viewOnClistener;

	public SeeOtherPeopleAdapter(Context mContext, List<SortModel> list) {
		this.mContext = mContext;
		this.list = list;
//		options = new DisplayImageOptions.Builder()
//				.showStubImage(R.drawable.ic_stub)
//				.showImageForEmptyUri(R.drawable.noimage)
//				.showImageOnFail(R.drawable.noimage).cacheInMemory()
//				.cacheOnDisc().displayer(new RoundedBitmapDisplayer(5)).build();
	}

	public SeeOtherPeopleAdapter(Context mContext, List<SortModel> list,
			String cj_uid) {
		this.mContext = mContext;
		this.list = list;
		this.cj_uid = cj_uid;
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
	public View getView(final int position, View view, ViewGroup viewgroup) {

		ViewHolder viewHolder = null;
		final SortModel mContent = list.get(position);
		// 这里可以重写
		if (viewOnClistener != null) {
			view = viewOnClistener.setView(position, view, viewgroup);
		} else {

			if (view == null) {
				viewHolder = new ViewHolder();
				view = LayoutInflater.from(mContext).inflate(
						R.layout.abc_see_other_people_item, null);
				viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
				viewHolder.tvLetter = (TextView) view
						.findViewById(R.id.catalog);
				viewHolder.is_guanliyuan = (TextView) view
						.findViewById(R.id.is_guanliyuan);
				viewHolder.isFriends = (TextView) view
						.findViewById(R.id.isFriends);
				viewHolder.motto = (TextView) view.findViewById(R.id.motto);
				viewHolder.head = (ImageView) view.findViewById(R.id.head);
				viewHolder.is_huodong_fq = (LinearLayout) view
						.findViewById(R.id.is_huodong_fq);
				viewHolder.huodongcanyu_email = (TextView) view
						.findViewById(R.id.huodongcanyu_email);
				viewHolder.huodongcanyu_photo = (TextView) view
						.findViewById(R.id.huodongcanyu_photo);
				view.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) view.getTag();
			}

			// 根据position获取分类的首字母的Char ascii值
//			int section = getSectionForPosition(position);
//
//			// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现getSectionForPosition
//			if (position == getPositionForSection(section)) {
//				viewHolder.tvLetter.setVisibility(View.VISIBLE);
//				viewHolder.tvLetter.setText(mContent.getSortLetters());
//			} else {
//				viewHolder.tvLetter.setVisibility(View.GONE);
//			}
//			viewHolder.tvTitle.setText(list.get(position).getNickName());
//			if (cj_uid != null && cj_uid.equals(Utils.getUid(mContext))) {
//				viewHolder.is_huodong_fq.setVisibility(View.VISIBLE);
//				viewHolder.huodongcanyu_email.setText(list.get(position)
//						.getEmail());
//				viewHolder.huodongcanyu_photo.setText(list.get(position)
//						.getPhone());
//				// Log.v("sss1", "邮箱和手机号是:" + list.get(position).getEmail() +
//				// " "
//				// + list.get(position).getPhone());
//			} else {
//				viewHolder.is_huodong_fq.setVisibility(View.GONE);
//				// Log.v("sss1",
//				// "不是活动发起者id，判断失败" + cj_uid + " "
//				// + list.get(position).getId());
//			}
//			if (list.get(position).getId() != null) {
//				// System.out.println("参与者id" + list.get(position).getId());
//				if (cj_uid != null && list.get(position).getId().equals(cj_uid)) {
//					viewHolder.is_guanliyuan.setVisibility(View.VISIBLE);
//					viewHolder.is_guanliyuan.setText(""
//							+ mContext.getResources().getString(
//									R.string.l_xb181));
//				} else {
//					viewHolder.is_guanliyuan.setVisibility(View.GONE);
//				}
//				if (list.get(position).getId().equals(Utils.getUid(mContext))) {
//					viewHolder.isFriends.setText(""
//							+ mContext.getResources().getString(
//									R.string.l_xb127));
//					viewHolder.isFriends.setTextColor(mContext.getResources()
//							.getColor(R.color.gray_9));
//				} else {
//					if (mContent.getIs_firend().equals("1")) {
//						viewHolder.isFriends.setText(""
//								+ mContext.getResources().getString(
//										R.string.l_xb128));
//						viewHolder.isFriends.setTextColor(mContext
//								.getResources().getColor(R.color.gray_9));
//					} else {
//						viewHolder.isFriends.setText(""
//								+ mContext.getResources().getString(
//										R.string.l_xb129));
//						viewHolder.isFriends.setTextColor(mContext
//								.getResources().getColor(R.color.green));
//						viewHolder.isFriends
//								.setOnClickListener(new View.OnClickListener() {
//									@Override
//									public void onClick(View v) {
//										// TODO Auto-generated method stub
//										// 在这里提交加为好友的申请
//										Intent intent = new Intent(
//												mContext,
//												AddFriendValidationActivity.class);
//										intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//										intent.putExtra("id", list
//												.get(position).getId());
//										mContext.startActivity(intent);
//									}
//								});
//					}
//				}
//			} else {
//				// System.out.println("参与者uid" + list.get(position).getUid());
//				if (cj_uid != null
//						&& list.get(position).getUid().equals(cj_uid)) {
//					viewHolder.is_guanliyuan.setVisibility(View.VISIBLE);
//					viewHolder.is_guanliyuan.setText(""
//							+ mContext.getResources().getString(
//									R.string.l_xb181));
//
//				} else {
//					viewHolder.is_guanliyuan.setVisibility(View.GONE);
//				}
//				if (list.get(position).getUid() != null
//						&& list.get(position).getUid()
//								.equals(Utils.getUid(mContext))) {
//					viewHolder.isFriends.setText(""
//							+ mContext.getResources().getString(
//									R.string.l_xb127));
//					viewHolder.isFriends.setTextColor(mContext.getResources()
//							.getColor(R.color.gray_9));
//				} else {
//					if (mContent.getIs_firend().equals("1")) {
//						viewHolder.isFriends.setText(""
//								+ mContext.getResources().getString(
//										R.string.l_xb128));
//						viewHolder.isFriends.setTextColor(mContext
//								.getResources().getColor(R.color.gray_9));
//					} else {
//						viewHolder.isFriends.setText(""
//								+ mContext.getResources().getString(
//										R.string.l_xb129));
//						viewHolder.isFriends.setTextColor(mContext
//								.getResources().getColor(R.color.green));
//						viewHolder.isFriends
//								.setOnClickListener(new View.OnClickListener() {
//
//									@Override
//									public void onClick(View v) {
//										// TODO Auto-generated method stub
//										// 在这里提交加为好友的申请
//										Intent intent = new Intent(
//												mContext,
//												AddFriendValidationActivity.class);
//										intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//										intent.putExtra("id", list
//												.get(position).getUid());
//										mContext.startActivity(intent);
//									}
//								});
//					}
//				}
//			}
//			viewHolder.motto.setVisibility(View.GONE);
//			ImageLoader.getInstance().displayImage(
//					Constants.BadiDownImgUrl + mContent.getHead()
//							+ Constants.appPhoto4img, viewHolder.head, options);
		}

		return view;
	}

	final class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
		TextView is_guanliyuan;
		TextView isFriends;
		TextView motto;
		ImageView head;

		LinearLayout is_huodong_fq;
		TextView huodongcanyu_email;
		TextView huodongcanyu_photo;

	}

	/**
	 * sdf 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	@Override
	public int getSectionForPosition(int position) {

		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * sdf 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
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

	@Override
	public Object[] getSections() {
		return null;
	}

	public void setViewOnClistener(ViewOnClistener viewOnClistener) {
		this.viewOnClistener = viewOnClistener;
	}

	public interface ViewOnClistener {
		View setView(int position, View view, ViewGroup viewgroup);
	}

	public List<SortModel> getList() {
		return list;
	}

	public void setList(List<SortModel> list) {
		this.list = list;
	}

}