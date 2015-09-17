package com.carinsurance.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.carinsurance.infos.Content;
import com.carinsurance.net.NetUtils;
import com.carinsurance.net.Task;
import com.carinsurance.nodes.GoodNode;
import com.carinsurance.utils.IOUtils;
import com.carinsurance.utils.ImageLocalLoader;
import com.carinsurance.utils.ImageTools;
import com.carinsurance.utils.MD5;
import com.carinsurance.utils.StringUtil;
import com.carinsurance.utils.Utils;
import com.carinsurance.utils.xUtilsImageLoader;
import com.carinsurancer.car.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class DiscussCommitActivity extends BaseActivity implements
		OnItemClickListener {
	public static final String DISCUSS_TYPE_FOR_GOODS = "1";
	public static final String DISCUSS_TYPE_FOR_SERVER = "2";
	public static final String IMAGE_UNSPECIFIED = "image/*";

	private final int REQUEST_CODE_FOR_PHONES_PIC = 201;
	private final int REQUEST_CODE_FOR_CAMERA_PIC = 202;
	private static String curDir;
	private static String curFileName;

	@ViewInject(R.id.return_btn)
	private ImageView returnBtn;
	@ViewInject(R.id.goods_list)
	private ListView goods;
	@ViewInject(R.id.pics_sel)
	private GridView pics;
	@ViewInject(R.id.btn_commit_discuss)
	private Button btn_commit;
	@ViewInject(R.id.score)
	private RatingBar ratingbar;
	@ViewInject(R.id.edt_discuss_content)
	private EditText edit;

	private BaseAdapter adapter, goodsListAdapter;
	private List<File> datas = new ArrayList<>();
	private List<GoodNode> goodsData = new ArrayList<>();
	private String curDiscussType = "-1";// -1是默认（不存在） 1
											// DISCUSS_TYPE_FOR_GOODS（商品评论） 2
											// 服务评论
											// 传入的数据是2/03b00838-616e-4a1e-b279-badb34643f2a/e6435700-4c91-4e42-a00b-e943cc0e3e64
	private String oid; // 订单编号
	private String pid; // 商品编号
	private String imageUrl;
	private String pname;

	private List<String> urls = new ArrayList<>(); // 图片网络地址列表
	private int curImageUploading = 0; // 正在上传的图片数量

	private ProgressDialog dialog;
	private xUtilsImageLoader imageLoader;

	private Dialog photoChooserDialog;

	@OnClick(R.id.btn_commit_discuss)
	private void onClick(View view) {

		// 如果图片还没有上传完毕，提示用户等待
		if (curImageUploading != 0) {
			Toast.makeText(this, "图片还未上传完毕,请稍等", Toast.LENGTH_SHORT).show();
			return;
		}

		String uid = Utils.getUid(this);
		String token = Utils.getToken(this);
		String imageurl = "";
		for (String str : urls) {
			imageurl += str + "#";
		}

		if (!imageurl.equals("")) {
			imageurl = imageurl.substring(0, imageurl.length() - 1);
		}

		float star = ratingbar.getRating();
		String content = edit.getText().toString();

		if (StringUtil.isNullOrEmpty(content)) {
			Toast.makeText(getApplicationContext(), "请填写评论内容！",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (star == 0) {
			Utils.showMessage(DiscussCommitActivity.this, "请评分！");
			return;
		}
		// && urls.isEmpty()
		// 显示对话框
		dialog.show();
		dialog.setTitle("评论");
		dialog.setMessage("正在提交评论,请稍等...");

		HashMap<String, String> param = new HashMap<>();
		param.put("uid", uid);
		param.put("token", token);
		// if(StringUtil.isNullOrEmpty())
		param.put("imgurl", imageurl);
		int strInt = (int) (star * 2); // 将0-5的小数转换为0-10
		param.put("star", strInt + "");
		param.put("content", content);
		param.put("oid", oid);
		param.put("pid", pid);
		param.put("type", curDiscussType + "");

		NetUtils.getIns().post(Task.COMMENT_SAVE, param, handler);

		// 获取图片

		switch (curDiscussType) {
		case DISCUSS_TYPE_FOR_GOODS:

			break;
		case DISCUSS_TYPE_FOR_SERVER:

			break;
		default:
			break;
		}
	}

	@OnClick(R.id.return_btn)
	private void returnBack(View view) {
		finish();
	}

	@Override
	public void initNetmessageFailure(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageFailure(message, task);

		if (task.equals(Task.COMMENT_SAVE)) {
			Toast.makeText(this, "评论提交失败", Toast.LENGTH_SHORT).show();
			dialog.dismiss();
		} else if (task.equals(Task.COMMENT_UPLOAD_IMAGE)) {
			// 失败之后的处理方式
			System.out.println(">>>图片上传失败");
			dialog.dismiss();
			curImageUploading--;
		}
	}

	@Override
	public void initNetmessageSuccess(String message, String task) {
		// TODO Auto-generated method stub
		super.initNetmessageSuccess(message, task);
		if (task.equals(Task.COMMENT_SAVE)) {
			if (isCommitSuccess(message)) {
				try {
					Toast.makeText(this, "评论提交成功", Toast.LENGTH_SHORT).show();
					finish();
					Content.is_refresh = true;
				} catch (Exception e) {
					// TODO: handle exception
				}

			} else {
				Toast.makeText(this, "评论提交失败", Toast.LENGTH_SHORT).show();
			}

			dialog.dismiss();
		} else if (task.equals(Task.COMMENT_UPLOAD_IMAGE)) {
			String url = getImageUrl(message);
			if (url != null) {
				urls.add(url);
			} else {
				// 失败之后的处理方式

			}
			Toast.makeText(this, "图片上传成功", Toast.LENGTH_SHORT).show();
			curImageUploading--;
			dialog.dismiss();
		}
	}

	// 根据网络返回值获取返回结果
	private String getImageUrl(String msg) {
		try {
			JSONObject obj = new JSONObject(msg);
			String result = obj.getString("result");
			if (!result.equals(NetUtils.NET_SUCCESS_001)) {
				return null;
			}

			String url = obj.getString("imgurl");
			return url;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 根据网络返回值 判断是否提交成功
	private boolean isCommitSuccess(String msg) {
		try {
			JSONObject obj = new JSONObject(msg);
			String result = obj.getString("result");
			if (result.equals(NetUtils.NET_SUCCESS_001)) {
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discuss);
		ViewUtils.inject(this);
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState()) && getExternalCacheDir() != null) {
			curDir = getExternalCacheDir().getAbsolutePath();
		} else {
			curDir = getCacheDir().getAbsolutePath();
		}

		if (!StringUtil.isNullOrEmpty(Utils.getUid(getApplicationContext()))
				&& !StringUtil.isNullOrEmpty(Utils
						.getToken(getApplicationContext()))) {
			// 用户已登录
		} else {
			Toast.makeText(this, "您还没有登录!", Toast.LENGTH_SHORT).show();
			finish();
		}

		Intent result = getIntent();
		curDiscussType = result.getStringExtra("type");
		oid = result.getStringExtra("oid");
		pid = result.getStringExtra("pid");
		imageUrl = result.getStringExtra("imageUrl");
		pname = result.getStringExtra("pname");

		if (StringUtil.isNullOrEmpty(oid)
				|| StringUtil.isNullOrEmpty(pid)
				|| StringUtil.isNullOrEmpty(pname)
				|| (!curDiscussType.equals(DISCUSS_TYPE_FOR_GOODS) && !curDiscussType
						.equals(DISCUSS_TYPE_FOR_SERVER))) {
			// 商品和订单ID必须值
			Toast.makeText(this, "非法进入,传入数据缺失", Toast.LENGTH_SHORT).show();
			finish();
		}

		GoodNode good = new GoodNode();
		good.setPid(pid);
		good.setPname(pname);
		good.setPimage(imageUrl);
		goodsData.add(good);

		datas.add(null); // 添加一个空置，保证添加按钮
		adapter = new DiscussCommitImageSelAdapter(datas, this);
		pics.setAdapter(adapter);

		imageLoader = new xUtilsImageLoader(this);
		goodsListAdapter = new DicussGoodsListAdapter(goodsData, this,
				imageLoader);
		goods.setAdapter(goodsListAdapter);

		pics.setOnItemClickListener(this);
		dialog = new ProgressDialog(this);
	}

	private static class ViewHolder {
		ImageView img;
		TextView name;
		TextView buycounts;
		TextView price;
		TextView comts;
	}

	// 商品列表适配器
	private class DicussGoodsListAdapter extends BaseAdapter {
		private List<GoodNode> datas;
		private LayoutInflater inflater;
		private xUtilsImageLoader loader;

		public DicussGoodsListAdapter(List<GoodNode> datas, Context context,
				xUtilsImageLoader loader) {
			this.datas = datas;
			inflater = LayoutInflater.from(context);
			this.loader = loader;
		}

		@Override
		public int getCount() {
			return datas.size();
		}

		@Override
		public Object getItem(int position) {
			return datas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.hot_category_list_item,
						null);
				holder = new ViewHolder();
				holder.img = (ImageView) convertView
						.findViewById(R.id.hot_category_list_item_img);
				holder.name = (TextView) convertView
						.findViewById(R.id.hot_category_list_item_name);
				holder.buycounts = (TextView) convertView
						.findViewById(R.id.hot_category_list_item_buynum);
				holder.price = (TextView) convertView
						.findViewById(R.id.hot_category_list_item_price);
				holder.comts = (TextView) convertView
						.findViewById(R.id.hot_category_list_item_comts);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			loader.display(holder.img, datas.get(position).getPimage());

			holder.name.setText(datas.get(position).getPname());
			// String buynum = getString(R.string.hot_category_buynum,
			// datas.get(position).getPbuynum());
			// holder.buycounts.setText(buynum);
			// String price = getString(R.string.hot_category_price,
			// datas.get(position).getPrice());
			// holder.price.setText(price);
			// String comts = getString(R.string.hot_category_comts,
			// datas.get(position).getPcomts());
			// holder.comts.setText(comts);

			return convertView;
		}

	}

	// 图片选择适配器
	private class DiscussCommitImageSelAdapter extends BaseAdapter {
		private List<File> datas;
		private LayoutInflater inflater;
		private ContentResolver resolver;

		public DiscussCommitImageSelAdapter(List<File> datas, Context context) {
			this.datas = datas;
			inflater = LayoutInflater.from(context);
			resolver = context.getContentResolver();
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return datas.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return datas.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.discuss_commit_pics_sel_item, null);
			}

			ImageView iv = (ImageView) convertView
					.findViewById(R.id.dicuss_commit_pics_sel_item_imgthumb);
			if (datas.get(position) == null) { // 最后一个
				iv.setImageResource(R.drawable.camera);
				iv.setScaleType(ScaleType.CENTER);
			} else {
				try {
					File bmp = datas.get(position);// new
													// File()//
													// getItem(position);
					// ImageLocalLoader.getInstance().loadImageNoCache(
					// bmp.getAbsolutePath(),
					// holder.getImageView(R.id.imageView1));
					Bitmap bp = ImageLocalLoader
							.decodeSampledBitmapFromResource(
									bmp.getAbsolutePath(), 128, 128);
					// holder.getImageView(R.id.imageView1).setImageBitmap(bp);

					iv.setImageBitmap(bp);
					iv.setScaleType(ScaleType.CENTER_CROP);

					// iv.setImageBitmap(BitmapFactory.decodeStream(resolver.openInputStream(datas.get(position))));
					// iv.setScaleType(ScaleType.CENTER_INSIDE);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			return convertView;
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (position == datas.size() - 1) { // 最後一個
			if (datas.size() == 6) {
				Utils.showMessage(DiscussCommitActivity.this, "最多只能上传5张照片");
				return;
			}
			// Intent intent = new Intent();
			// intent.setType("image/*");
			// intent.setAction(Intent.ACTION_GET_CONTENT);
			// startActivityForResult(intent, 1);
			// Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
			// intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
			// IMAGE_UNSPECIFIED);
			// startActivityForResult(intent, 1);
			showChooserDialog();
		}
	}

	// 获取图片选取返回值
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// if (requestCode == 1) {
		// // Utils.showMessage(DiscussCommitActivity.this, "data===>"+data);
		// if (resultCode == RESULT_OK) {
		// Uri uri = data.getData();
		// // Utils.showMessage(DiscussCommitActivity.this,
		// // "路径："+getFilePathFromUri(uri));
		// if (datas.contains(uri)) {
		// return;
		// }
		// datas.add(0, uri);
		// adapter.notifyDataSetChanged();
		// uploadImage(uri);
		// }
		// }

		if (requestCode == REQUEST_CODE_FOR_PHONES_PIC) {
			if (resultCode == RESULT_OK && data != null) {
				String filepath = getFilePathFromUri(data.getData());
				// 显示
				File file = new File(filepath);
				datas.add(adapter.getCount() - 1, file);
				adapter.notifyDataSetChanged();
				uploadImage(file);
			}
			photoChooserDialog.dismiss();

			return;
		}

		if (requestCode == REQUEST_CODE_FOR_CAMERA_PIC) {
			if (resultCode == RESULT_OK) {
				File file = new File(curDir, curFileName);
				if (file != null && file.exists()) {
					datas.add(adapter.getCount() - 1, file);
					adapter.notifyDataSetChanged();
					uploadImage(file);
				}
			}

			photoChooserDialog.dismiss();
			return;
		}
		// // 读取相册缩放图片
		// if (requestCode == PHOTOZOOM) {
		// startPhotoZoom(data.getData());
		// }
	}

	// 上传图片
	private synchronized void uploadImage(File fileSrc) {
		curImageUploading++;
		String path = fileSrc.getAbsolutePath();
		String uid = Utils.getUid(this);
		String token = Utils.getToken(this);
		RequestParams params = new RequestParams();
		params.addBodyParameter("uid", uid);
		params.addBodyParameter("token", token);

		// 图片文件校正压缩
		try {
			File file = new File(ImageTools.ReducePictureQuality(
					path,
					IOUtils.getSaveObjectPath(DiscussCommitActivity.this,
							MD5.getMD5(path) + ".jpeg")));
			params.addBodyParameter("imageFile", file);

			// 显示对话框
			dialog.show();
			dialog.setTitle("图片上传");
			dialog.setMessage("图片正在上传,请稍后");
			NetUtils.getIns().post(Task.COMMENT_UPLOAD_IMAGE, params, handler);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

	// 根据Uri获取文件路径
	private String getFilePathFromUri(Uri uri) {
		String[] proj = { MediaColumns.DATA };

		Cursor actualimagecursor = getContentResolver().query(uri, proj, null,
				null, null);

		int actual_image_column_index = actualimagecursor
				.getColumnIndexOrThrow(MediaColumns.DATA);
		actualimagecursor.moveToFirst();
		String img_path = actualimagecursor
				.getString(actual_image_column_index);
		return img_path;

	}

	private void showChooserDialog() {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(this).inflate(
				R.layout.photo_choose_dialog, null);
		photoChooserDialog = new Dialog(this,
				R.style.transparentFrameWindowStyle);
		photoChooserDialog.setContentView(view, new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		Window window = photoChooserDialog.getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = getWindowManager().getDefaultDisplay().getHeight();
		// // 以下这两句是为了保证按钮可以水平满屏
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		// 设置显示位置
		photoChooserDialog.onWindowAttributesChanged(wl);

		// 设置点击外围解散
		photoChooserDialog.setCanceledOnTouchOutside(true);
		photoChooserDialog.show();
	}

	public void on_click(View v) {
		switch (v.getId()) {
		case R.id.openCamera:
			photoChooserDialog.cancel();
			openCamera();
			break;
		case R.id.openPhones:
			photoChooserDialog.cancel();
			openPhones();
			break;
		case R.id.cancel:
			photoChooserDialog.cancel();
			break;
		default:
			break;
		}
	}

	/**
	 * 打开相册
	 */
	private void openPhones() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				IMAGE_UNSPECIFIED);
		startActivityForResult(intent, REQUEST_CODE_FOR_PHONES_PIC);
	}

	/**
	 * 打开照相机
	 */
	private void openCamera() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		curFileName = System.currentTimeMillis() + ".jpg";
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(curDir, curFileName)));

		startActivityForResult(intent, REQUEST_CODE_FOR_CAMERA_PIC);
	}
}
