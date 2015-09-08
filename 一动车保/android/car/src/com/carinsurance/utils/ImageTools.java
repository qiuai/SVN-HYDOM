package com.carinsurance.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import net.tsz.afinal.http.AjaxParams;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.TextView;

/**
 * http://blog.csdn.net/wangfayinn/article/details/8685310 Tools for handler R
 * picture
 * 
 * @author Ryan.Tang
 * 
 */
public final class ImageTools {

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int TOP = 3;
	public static final int BOTTOM = 4;
	protected static final String TAG = "ImageTools";

	public static AjaxParams params;
	public static Context ct;
	public static Handler handlers;

//	public static android.os.Handler sharehanders = new Handler() {
//		public void handleMessage(Message msg) {
//
//			switch (msg.what) {
//			case 0:// 多张上传图片
//				Log.v(TAG, "运行了一次");
//				FinalHttp fh = new FinalHttp();
//				fh.configTimeout(15000);
//				fh.configRequestExecutionRetryCount(3);
//				Log.v(TAG, "开始发送post请求");
//				fh.post(Constants.BadiUrl + "upload_imgs_pre", params,
//						new AjaxCallBack<Object>() {
//							Message message;
//							Bundle bundle;
//
//							@Override
//							public void onLoading(long count, long current) {
//								// TODO Auto-generated method stub
//								super.onLoading(count, current);
//								Log.v(TAG, "count=" + count + "current="
//										+ current);
//								Message msg = new Message();
//								msg.what = 1;
//								Bundle bun = new Bundle();
//								bun.putInt("count", (int) count);
//								msg.setData(bun);
//								sharehanders.sendMessage(msg);
//							}
//
//							@Override
//							public void onSuccess(Object t) {
//								Log.v(TAG, "接收到的结果是:" + (String) t);
//								if (t != null) {
//									Log.v(TAG, "接收到的结果是:" + (String) t);
//								}
//
//								Results results = Utils.checkResult_NNN(ct,
//										(String) t);
//								if (results != null
//										&& !results.getRetmsg().equals("null")) {
//									message = new Message();
//									bundle = new Bundle();
//									message.what = 2000;
//									bundle.putString("path",
//											results.getRetmsg());
//									message.setData(bundle);
//									handlers.sendMessage(message);
//								} else {
//
//								}
//							}
//						});
//				break;
//			case 1:
//				try {
//					int count = msg.getData().getInt("count");
//					// Log.v("sss1", "数据的状态是:" + count);
//					Utils.setProgress_hen(count);
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//
//				break;
//			case 2:// 单张上传
//				FinalHttp fh2 = new FinalHttp();
//
//				fh2.post(Constants.BadiUrl + "upload_img", params,
//						new AjaxCallBack<Object>() {
//							Message message;
//							Bundle bundle;
//
//							@Override
//							public void onLoading(long count, long current) {
//								// TODO Auto-generated method stub
//								super.onLoading(count, current);
//								Message msg = new Message();
//								msg.what = 1;
//								Bundle bun = new Bundle();
//								bun.putInt("count", (int) count);
//								msg.setData(bun);
//								sharehanders.sendMessage(msg);
//							}
//
//							@Override
//							public void onSuccess(Object t) {
//								// TODO Auto-generated method stub
//								// Log.v("aaa", "t===>" + t);
//								Results results = Utils.checkResult_NNN(ct,
//										(String) t);
//								if (results != null
//										&& !results.getRetmsg().equals("null")
//										&& results.isRet() == true) {
//									message = new Message();
//									bundle = new Bundle();
//									message.what = 2000;
//									bundle.putString("path",
//											results.getRetmsg());
//									message.setData(bundle);
//									handlers.sendMessage(message);
//								} else {
//									sharehanders.sendEmptyMessage(4);
//									Utils.ExitProgress_hen(ct);
//									Log.v(TAG, "t.toString" + t.toString());
//								}
//							}
//						});
//				break;
//			case 3:
//				// 多张上传图片,无逗号
//				FinalHttp fh3 = new FinalHttp();
//				fh3.configTimeout(15000);
//				fh3.configRequestExecutionRetryCount(3);
//				Log.v(TAG, "开始发送post请求");
//				fh3.post(Constants.BadiUrl + "upload_imgs", params,
//						new AjaxCallBack<Object>() {
//							Message message;
//							Bundle bundle;
//
//							@Override
//							public void onLoading(long count, long current) {
//								// TODO Auto-generated method stub
//								super.onLoading(count, current);
//								Log.v(TAG, "count=" + count + "current="
//										+ current);
//								Message msg = new Message();
//								msg.what = 1;
//								Bundle bun = new Bundle();
//								bun.putInt("count", (int) count);
//								msg.setData(bun);
//								sharehanders.sendMessage(msg);
//							}
//
//							@Override
//							public void onSuccess(Object t) {
//								Log.v(TAG, "接收到的结果是:" + (String) t);
//								if (t != null) {
//									Log.v(TAG, "接收到的结果是:" + (String) t);
//								}
//
//								Results results = Utils.checkResult_NNN(ct,
//										(String) t);
//								if (results != null
//										&& !results.getRetmsg().equals("null")) {
//									message = new Message();
//									bundle = new Bundle();
//									message.what = 2000;
//									bundle.putString("path",
//											results.getRetmsg());
//									message.setData(bundle);
//									handlers.sendMessage(message);
//								} else {
//
//								}
//							}
//						});
//				break;
//			case 4:// 上传
//				try {
//					// int count = msg.getData().getInt("count");
//					// // Log.v("sss1", "数据的状态是:" + count);
//					// Utils.setProgress_hen(count);
//					Utils.showMessage(ct,
//							ct.getResources().getString(R.string.wx2_5));
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//
//				break;
//			}
//			;
//		};
//	};

	/**
	 * Transfer drawable to bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) {
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();

		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * Bitmap to drawable
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Drawable bitmapToDrawable(Bitmap bitmap) {
		return new BitmapDrawable(bitmap);
	}

	/**
	 * Input stream to bitmap
	 * 
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public static Bitmap inputStreamToBitmap(InputStream inputStream)
			throws Exception {
		return BitmapFactory.decodeStream(inputStream);
	}

	/**
	 * Byte transfer to bitmap
	 * 
	 * @param byteArray
	 * @return
	 */
	public static Bitmap byteToBitmap(byte[] byteArray) {
		if (byteArray.length != 0) {
			return BitmapFactory
					.decodeByteArray(byteArray, 0, byteArray.length);
		} else {
			return null;
		}
	}

	/**
	 * Byte transfer to drawable
	 * 
	 * @param byteArray
	 * @return
	 */
	public static Drawable byteToDrawable(byte[] byteArray) {
		ByteArrayInputStream ins = null;
		if (byteArray != null) {
			ins = new ByteArrayInputStream(byteArray);
		}
		return Drawable.createFromStream(ins, null);
	}

	/**
	 * Bitmap transfer to bytes
	 * 
	 * @param byteArray
	 * @return
	 */
	public static byte[] bitmapToBytes(Bitmap bm) {
		byte[] bytes = null;
		if (bm != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
			bytes = baos.toByteArray();
		}
		return bytes;
	}

	/**
	 * Drawable transfer to bytes
	 * 
	 * @param drawable
	 * @return
	 */
	public static byte[] drawableToBytes(Drawable drawable) {
		BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		Bitmap bitmap = bitmapDrawable.getBitmap();
		byte[] bytes = bitmapToBytes(bitmap);
		;
		return bytes;
	}

	/**
	 * Base64 to byte[] //
	 */
	// public static byte[] base64ToBytes(String base64) throws IOException {
	// byte[] bytes = Base64.decode(base64);
	// return bytes;
	// }
	//
	// /**
	// * Byte[] to base64
	// */
	// public static String bytesTobase64(byte[] bytes) {
	// String base64 = Base64.encode(bytes);
	// return base64;
	// }

	/**
	 * Create reflection images 创建反射images
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
		final int reflectionGap = 4;
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w,
				h / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2),
				Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, h, w, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}

	/**
	 * Get rounded corner images 得到圆角图片
	 * 
	 * @param bitmap
	 * @param roundPx
	 *            越大越明显 5 10
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, w, h);
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 得到圆角图片
	 * 
	 * @param sourceBitmap
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap sourceBitmap) {

		try {

			Bitmap targetBitmap = Bitmap.createBitmap(sourceBitmap.getWidth(),
					sourceBitmap.getHeight(), Config.ARGB_8888);

			// 得到画布
			Canvas canvas = new Canvas(targetBitmap);
			// 创建画笔
			Paint paint = new Paint();
			paint.setAntiAlias(true);

			// 值越大角度越明显
			float roundPx = 30;
			float roundPy = 30;

			Rect rect = new Rect(0, 0, sourceBitmap.getWidth(),
					sourceBitmap.getHeight());
			RectF rectF = new RectF(rect);

			// 绘制
			canvas.drawARGB(0, 0, 0, 0);
			canvas.drawRoundRect(rectF, roundPx, roundPy, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(sourceBitmap, rect, rect, paint);
			return targetBitmap;

		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Resize the bitmap 调整位图
	 * 
	 * @param bitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	/**
	 * Resize the drawable 调整drawable
	 * 
	 * @param drawable
	 * @param w
	 * @param h
	 * @return
	 */
	public static Drawable zoomDrawable(Drawable drawable, int w, int h) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		Bitmap oldbmp = drawableToBitmap(drawable);
		Matrix matrix = new Matrix();
		float sx = ((float) w / width);
		float sy = ((float) h / height);
		matrix.postScale(sx, sy);
		Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
				matrix, true);
		return new BitmapDrawable(newbmp);
	}

	/**
	 * Get images from SD card by path and the name of image 从SD卡获得图像的路径和名称
	 * 
	 * @param photoName
	 * @return
	 */
	public static Bitmap getPhotoFromSDCard(String path, String photoName) {
		Bitmap photoBitmap = BitmapFactory.decodeFile(path + "/" + photoName
				+ ".png");
		if (photoBitmap == null) {
			return null;
		} else {
			return photoBitmap;
		}
	}

	/**
	 * Check the SD card 检查sd卡
	 * 
	 * @return
	 */
	public static boolean checkSDCardAvailable() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	/**
	 * Get image from SD card by path and the name of image 从SD卡获得图像的路径和名称是否存在
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean findPhotoFromSDCard(String path, String photoName) {
		boolean flag = false;

		if (checkSDCardAvailable()) {
			File dir = new File(path);
			if (dir.exists()) {
				File folders = new File(path);
				File photoFile[] = folders.listFiles();
				for (int i = 0; i < photoFile.length; i++) {
					String fileName = photoFile[i].getName().split("\\.")[0];
					if (fileName.equals(photoName)) {
						flag = true;
					}
				}
			} else {
				flag = false;
			}
			// File file = new File(path + "/" + photoName + ".jpg" );
			// if (file.exists()) {
			// flag = true;
			// }else {
			// flag = false;
			// }

		} else {
			flag = false;
		}
		return flag;
	}

	/**
	 * Save image to the SD card 存储图片到sd卡
	 * 
	 * @param photoBitmap
	 * @param photoName
	 * @param path
	 */
	public static void savePhotoToSDCard(Bitmap photoBitmap, String path,
			String photoName) {
		if (checkSDCardAvailable()) {
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			File photoFile = new File(path, photoName + ".png");
			FileOutputStream fileOutputStream = null;
			try {
				fileOutputStream = new FileOutputStream(photoFile);
				if (photoBitmap != null) {
					if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
							fileOutputStream)) {
						fileOutputStream.flush();
						// fileOutputStream.close();
					}
				}
			} catch (FileNotFoundException e) {
				photoFile.delete();
				e.printStackTrace();
			} catch (IOException e) {
				photoFile.delete();
				e.printStackTrace();
			} finally {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Delete the image from SD card 从SD卡删除的图片
	 * 
	 * @param context
	 * @param path
	 *            file:///sdcard/temp.jpg
	 */
	public static void deleteAllPhoto(String path) {
		if (checkSDCardAvailable()) {
			File folder = new File(path);
			File[] files = folder.listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
			}
		}
	}

	/**
	 * 删除照片的路径和名称
	 * 
	 * @param path
	 * @param fileName
	 */
	public static void deletePhotoAtPathAndName(String path, String fileName) {
		if (checkSDCardAvailable()) {
			File folder = new File(path);
			File[] files = folder.listFiles();
			for (int i = 0; i < files.length; i++) {
				System.out.println(files[i].getName());
				if (files[i].getName().equals(fileName)) {
					files[i].delete();
				}
			}
		}
	}

	/**
	 * 处理图片 到指定的宽和高
	 * 
	 * @param bm
	 *            所要转换的bitmap
	 * @param newWidth新的宽
	 * @param newHeight新的高
	 * @return 指定宽高的bitmap
	 */
	public static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片 www.2cto.com
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);
		return newbm;
	}

	/**
	 * 图片合成
	 * 
	 * @return
	 */
	public static Bitmap potoMix(int direction, Bitmap... bitmaps) {
		if (bitmaps.length <= 0) {
			return null;
		}
		if (bitmaps.length == 1) {
			return bitmaps[0];
		}
		Bitmap newBitmap = bitmaps[0];
		// newBitmap = createBitmapForFotoMix(bitmaps[0],bitmaps[1],direction);
		for (int i = 1; i < bitmaps.length; i++) {
			newBitmap = createBitmapForFotoMix(newBitmap, bitmaps[i], direction);
		}
		return newBitmap;
	}

	private static Bitmap createBitmapForFotoMix(Bitmap first, Bitmap second,
			int direction) {
		if (first == null) {
			return null;
		}
		if (second == null) {
			return first;
		}
		int fw = first.getWidth();
		int fh = first.getHeight();
		int sw = second.getWidth();
		int sh = second.getHeight();
		Bitmap newBitmap = null;
		if (direction == LEFT) {
			newBitmap = Bitmap.createBitmap(fw + sw, fh > sh ? fh : sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, sw, 0, null);
			canvas.drawBitmap(second, 0, 0, null);
		} else if (direction == RIGHT) {
			newBitmap = Bitmap.createBitmap(fw + sw, fh > sh ? fh : sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, 0, 0, null);
			canvas.drawBitmap(second, fw, 0, null);
		} else if (direction == TOP) {
			newBitmap = Bitmap.createBitmap(sw > fw ? sw : fw, fh + sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, 0, sh, null);
			canvas.drawBitmap(second, 0, 0, null);
		} else if (direction == BOTTOM) {
			newBitmap = Bitmap.createBitmap(sw > fw ? sw : fw, fh + sh,
					Config.ARGB_8888);
			Canvas canvas = new Canvas(newBitmap);
			canvas.drawBitmap(first, 0, 0, null);
			canvas.drawBitmap(second, 0, fh, null);
		}
		return newBitmap;
	}

	/**
	 * 图片去色,返回灰度图片 (不好用，背景要别黑,不推荐使用) 推荐用 getGrayBitmap
	 * 
	 * @param bmpOriginal
	 *            传入的图片
	 * @return 去色后的图片
	 */
	public Bitmap toGrayscale(Bitmap bmpOriginal) {
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();
		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}

	/**
	 * 图片灰化处理
	 * 
	 * @param mBitmap
	 * @return
	 */
	public static Bitmap getGrayBitmap(Bitmap mBitmap) {

		Bitmap mGrayBitmap = Bitmap.createBitmap(mBitmap.getWidth(),
				mBitmap.getHeight(), Config.ARGB_8888);
		Canvas mCanvas = new Canvas(mGrayBitmap);
		Paint mPaint = new Paint();

		// 创建颜色变换矩阵
		ColorMatrix mColorMatrix = new ColorMatrix();
		// 设置灰度影响范围
		mColorMatrix.setSaturation(0);
		// 创建颜色过滤矩阵
		ColorMatrixColorFilter mColorFilter = new ColorMatrixColorFilter(
				mColorMatrix);
		// 设置画笔的颜色过滤矩阵
		mPaint.setColorFilter(mColorFilter);
		// 使用处理后的画笔绘制图像
		mCanvas.drawBitmap(mBitmap, 0, 0, mPaint);

		return mGrayBitmap;
	}

	/**
	 * 去色同时加圆角
	 * 
	 * @param bmpOriginal
	 *            原图
	 * @param pixels
	 *            圆角弧度
	 * @return 修改后的图片
	 */
	public static Bitmap toGrayscale(Bitmap bmpOriginal, int pixels) {
		return toRoundCorner(getGrayBitmap(bmpOriginal), pixels);
	}

	/**
	 * 把图片变成圆角
	 * 
	 * @param bitmap
	 *            需要修改的图片
	 * @param pixels
	 *            圆角的弧度
	 * @return 圆角图片
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 使圆角功能支持BitampDrawable
	 * 
	 * @param bitmapDrawable
	 * @param pixels
	 * @return
	 */
	public static BitmapDrawable toRoundCorner(BitmapDrawable bitmapDrawable,
			int pixels) {
		Bitmap bitmap = bitmapDrawable.getBitmap();
		bitmapDrawable = new BitmapDrawable(toRoundCorner(bitmap, pixels));
		return bitmapDrawable;
	}

	/**
	 * 读取路径中的图片，然后将其转化为缩放后的bitmap
	 * 
	 * @param path
	 */
	public static void saveBefore(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		// 获取这个图片的宽和高
		Bitmap bitmap = BitmapFactory.decodeFile(path, options); // 此时返回bm为空
		options.inJustDecodeBounds = false;
		// 计算缩放比
		int be = (int) (options.outHeight / (float) 200);
		if (be <= 0)
			be = 1;
		options.inSampleSize = 2; // 图片长宽各缩小二分之一
		// 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
		bitmap = BitmapFactory.decodeFile(path, options);
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		System.out.println(w + " " + h);
		// savePNG_After(bitmap,path);
		saveJPGE_After(bitmap, path);
	}

	/**
	 * 保存图片为JPEG
	 * 
	 * @param bitmap
	 * @param path
	 */
	public static void saveJPGE_After(Bitmap bitmap, String path) {
		File file = new File(path);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存图片为PNG
	 * 
	 * @param bitmap
	 * @param name
	 */
	public static void savePNG_After(Bitmap bitmap, String name) {
		File file = new File(name);
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
				out.flush();
				out.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将view转成bitmap
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap loadBitmapFromView(Context context, View view) {
		if (view == null) {
			return null;
		}
		view.measure(MeasureSpec.makeMeasureSpec(
				DisplayUtil.dip2px(context, 60f), MeasureSpec.EXACTLY),
				MeasureSpec.makeMeasureSpec(DisplayUtil.dip2px(context, 80f),
						MeasureSpec.EXACTLY));
		// 这个方法也非常重要，设置布局的尺寸和位置
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		// 生成bitmap
		Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
				Bitmap.Config.ARGB_8888);

		// 利用bitmap生成画布

		Canvas canvas = new Canvas(bitmap);

		// 把view中的内容绘制在画布上

		view.draw(canvas);

		return bitmap;
	}

	/**
	 * 将view转成bitmap
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap loadBitmapFromView(Context context, View view, int w,
			int h) {

		if (view == null) {
			return null;
		}
		view.measure(MeasureSpec.makeMeasureSpec(w, MeasureSpec.EXACTLY),
				MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY));
		// 这个方法也非常重要，设置布局的尺寸和位置
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		// 生成bitmap
		Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
				Bitmap.Config.ARGB_8888);

		// 利用bitmap生成画布

		Canvas canvas = new Canvas(bitmap);

		// 把view中的内容绘制在画布上

		view.draw(canvas);

		return bitmap;
	}

	// //View转换为Bitmap
	// public void getDrawingCache(final ImageView sourceImageView, final
	// ImageView destImageView) {
	//
	// new Handler().postDelayed(new Runnable() {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// //开启bitmap缓存
	// sourceImageView.setDrawingCacheEnabled(true);
	// //获取bitmap缓存
	// Bitmap mBitmap = sourceImageView.getDrawingCache();
	// //显示 bitmap
	// destImageView.setImageBitmap(mBitmap);
	//
	// // Bitmap mBitmap = sourceImageView.getDrawingCache();
	// // Drawable drawable = (Drawable) new BitmapDrawable(mBitmap);
	// // destImageView.setImageDrawable(drawable);
	//
	// new Handler().postDelayed(new Runnable() {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// //不再显示bitmap缓存
	// //destImageView.setImageBitmap(null);
	// destImageView.setImageResource(R.drawable.pet);
	//
	// //使用这句话而不是用上一句话是错误的，空指针调用
	// //destImageView.setBackgroundDrawable(null);
	//
	// //关闭bitmap缓存
	// sourceImageView.setDrawingCacheEnabled(false);
	// //释放bitmap缓存资源
	// sourceImageView.destroyDrawingCache();
	// }
	// }, DELAY_TIME);
	// }
	// }, DELAY_TIME);
	// }

	// /**
	// * 上传本地图片（单）
	// *
	// * @param path
	// * 本地图片地址
	// * @param context
	// * 上下文
	// * @param handler
	// * 拿到服务器返回的图片存储地址的传送者(what=2000)
	// */
	// public static void UpImg(final String path, final Context context,
	// final Handler handler) {
	// // Utils.showPrgress_Noclose(context);
	// Utils.showProgress_hen(context);
	// Utils.setProgress_henMax(100);
	// Log.v(TAG, "运行了");
	// params = new AjaxParams();
	// ct = context;
	// handlers = handler;
	// MyThreadTool.fixedThreadPool.execute(new Runnable() {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// Log.v(TAG,path);
	// try {
	// Log.v(TAG, "运行了");
	// // File file = new File(ReducePictureQuality(
	// // path,
	// // IOUtils.getSaveObjectPath(context, MD5.getMD5(path)
	// // + ".jpeg")));
	// File file=new File(path);
	// if (file.exists()) {
	// params.put("info_head", file);
	// Log.v(TAG,file.getPath());
	// Log.v(TAG, "文件存在");
	// } else {
	// Log.v(TAG, "文件不存在");
	// }
	//
	// } catch (Exception e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// }
	// });
	// sharehanders.sendEmptyMessage(2);
	// // return backpath;
	// }

	// public static void UpImg_ceshi(final ArrayList<String> path,
	// final Context context, final Handler handler) {
	// // Utils.showPrgress_Noclose(context);
	// // if (path.size() != 0)
	// // Utils.setProgress_henMax(path.size() * 100);
	// // AjaxParams params = new AjaxParams();
	// //
	// // try {
	// // for (int i = 0; i < path.size(); i++) {
	// //
	// // // params.put("" + i, new File(path.get(i)));// info_head
	// // File file = new File(ReducePictureQuality(
	// // path.get(i),
	// // IOUtils.getSaveObjectPath(context,
	// // MD5.getMD5(path.get(i)) + ".jpeg")));
	// // // File file=new
	// // //
	// //
	// File(ReducePictureQuality(path.get(i),Environment.getExternalStorageDirectory()+"/12345.JPEG"));
	// // // Log.v(TAG,"我是file="+file.toString());
	// // if (file.exists()) {
	// // params.put("" + i, file);
	// // Log.v(TAG, "纯在");
	// // }
	// //
	// // }
	// //
	// // } catch (Exception e1) {
	// // // TODO Auto-generated catch block
	// // e1.printStackTrace();
	// // }
	// // System.out.println("打印数据:" + params.toString());
	// // FinalHttp fh = new FinalHttp();
	// // fh.post(Constants.BadiUrl + "upload_imgs", params,
	// // new AjaxCallBack<Object>() {
	// // Message message;
	// // Bundle bundle;
	// //
	// // @Override
	// // public void onLoading(long count, long current) {
	// // // TODO Auto-generated method stub
	// // super.onLoading(count, current);
	// // }
	// //
	// // @Override
	// // public void onSuccess(Object t) {
	// // // TODO Auto-generated method stub
	// // // Log.v("aaa", "t===>" + t);
	// // if (t != null) {
	// // System.out.println("接收到的结果是:" + (String) t);
	// // }
	// //
	// // Results results = Utils.checkResult_NNN(context,
	// // (String) t);
	// // if (results != null
	// // && !results.getRetmsg().equals("null")) {
	// // message = new Message();
	// // bundle = new Bundle();
	// // message.what = 2000;
	// // bundle.putString("path", results.getRetmsg());
	// // message.setData(bundle);
	// // handler.sendMessage(message);
	// // } else {
	// //
	// // }
	// // }
	// // });
	// // // return backpath;
	//
	// Utils.showProgress_hen(context);
	// if (path.size() != 0)
	// Utils.setProgress_henMax(path.size() * 100);
	//
	// params = new AjaxParams();
	// ct = context;
	// handlers = handler;
	//
	// MyThreadTool.fixedThreadPool.execute(new Runnable() {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// for (int i = 0; i < path.size(); i++) {
	// try {
	// File file = new File(ReducePictureQuality(
	// path.get(i),
	// IOUtils.getSaveObjectPath(context,
	// MD5.getMD5(path.get(i)) + ".jpeg")));
	// if (file.exists()) {
	// params.put("" + i, file);
	// Log.v(TAG, "纯在");
	// }
	// } catch (Exception e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// }
	// sharehanders.sendEmptyMessage(3);
	// }
	// });
	// }

	// /**
	// * 一次性返回所有的图片
	// *
	// * @param path
	// * @param context
	// * @param handler
	// */
	// public static void UpImg_ceshi_douhao3(final ArrayList<String> path,
	// final Context context, final Handler handler) {
	// Utils.showProgress_hen(context);
	// if (path.size() != 0)
	// Utils.setProgress_henMax(path.size() * 100);
	// // ArrayList<String> path2=new ArrayList<String>();
	// //
	// // String
	// //
	// path2tou=context.getApplicationContext().getFilesDir().getAbsolutePath();
	// // String path=MD5.getMD5(source);
	// // // 封装路径
	// // // File file = new File(Environment.getExternalStorageDirectory(),
	// // path);
	// // File file = new
	// // File(context.getApplicationContext().getFilesDir().getAbsolutePath(),
	// // path);
	// AjaxParams params = new AjaxParams();
	//
	// // while (path.size() != 0) {
	// // File file = new File(ReducePictureQuality(
	// // path.get(0),
	// // IOUtils.getSaveObjectPath(context, MD5.getMD5(path.get(0))
	// // + ".jpeg")));
	// // if (file.exists()) {
	// // try {
	// // params.put("0", file);
	// // } catch (FileNotFoundException e) {
	// // // TODO Auto-generated catch block
	// // e.printStackTrace();
	// // }
	// // }
	//
	// try {
	// for (int i = 0; i < path.size(); i++) {
	//
	// // params.put("" + i, new File(path.get(i)));// info_head
	// File file = new File(ReducePictureQuality(
	// path.get(i),
	// IOUtils.getSaveObjectPath(context,
	// MD5.getMD5(path.get(i)) + ".jpeg")));
	// // File file=new
	// //
	// File(ReducePictureQuality(path.get(i),Environment.getExternalStorageDirectory()+"/12345.JPEG"));
	// // Log.v(TAG,"我是file="+file.toString());
	// if (file.exists()) {
	// params.put("" + i, file);
	// Log.v(TAG, "纯在");
	// }
	//
	// }
	//
	// } catch (Exception e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// System.out.println("打印数据:" + params.toString());
	// FinalHttp fh = new FinalHttp();
	// fh.configTimeout(15000);
	// fh.configRequestExecutionRetryCount(3);
	// Log.v(TAG, "开始发送post请求");
	// fh.post(Constants.BadiUrl + "upload_imgs_pre", params,
	// new AjaxCallBack<Object>() {
	// Message message;
	// Bundle bundle;
	//
	// @Override
	// public void onLoading(long count, long current) {
	// // TODO Auto-generated method stub
	// super.onLoading(count, current);
	// Log.v(TAG, "count=" + count + "current=" + current);
	// Utils.setProgress_hen((int) count);
	// }
	//
	// @Override
	// public void onSuccess(Object t) {
	// // TODO Auto-generated method stub
	// // Log.v("aaa", "t===>" + t);
	// // Utils.ExitProgress_hen(context);
	// Log.v(TAG, "接收到的结果是:" + (String) t);
	// if (t != null) {
	// Log.v(TAG, "接收到的结果是:" + (String) t);
	// }
	//
	// Results results = Utils.checkResult_NNN(context,
	// (String) t);
	// if (results != null
	// && !results.getRetmsg().equals("null")) {
	// message = new Message();
	// bundle = new Bundle();
	// message.what = 2000;
	// bundle.putString("path", results.getRetmsg());
	// message.setData(bundle);
	// handler.sendMessage(message);
	// } else {
	//
	// }
	// }
	// });
	// // return backpath;
	//
	// // path.remove(0);
	// // }
	// }

	// /**
	// * 采用分张（一张一张的发送到hander2000的消息中）
	// *
	// * @param path
	// * @param context
	// * @param handler
	// */
	// public static void UpImg_ceshi_douhao2(final ArrayList<String> path,
	// final Context context, final Handler handler) {
	// Utils.showProgress_hen(context);
	//
	// if (path.size() != 0)
	// Utils.setProgress_henMax(path.size() * 100);
	// // ArrayList<String> path2=new ArrayList<String>();
	// // String
	// //
	// path2tou=context.getApplicationContext().getFilesDir().getAbsolutePath();
	// // String path=MD5.getMD5(source);
	// // // 封装路径
	// // // File file = new File(Environment.getExternalStorageDirectory(),
	// // path);
	// // File file = new
	// // File(context.getApplicationContext().getFilesDir().getAbsolutePath(),
	// // path);
	// AjaxParams params = new AjaxParams();
	//
	// while (path.size() != 0) {
	// File file = new File(ReducePictureQuality(
	// path.get(0),
	// IOUtils.getSaveObjectPath(context, MD5.getMD5(path.get(0))
	// + ".jpeg")));
	// if (file.exists()) {
	// try {
	// params.put("0", file);
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	//
	// // try {
	// // for (int i = 0; i < path.size(); i++) {
	// //
	// // // params.put("" + i, new File(path.get(i)));// info_head
	// // File file = new File(ReducePictureQuality(
	// // path.get(i),
	// // IOUtils.getSaveObjectPath(context,
	// // MD5.getMD5(path.get(i)) + ".jpeg")));
	// // // File file=new
	// // //
	// //
	// File(ReducePictureQuality(path.get(i),Environment.getExternalStorageDirectory()+"/12345.JPEG"));
	// // // Log.v(TAG,"我是file="+file.toString());
	// // if (file.exists())
	// // {
	// // params.put("" + i, file);
	// // Log.v(TAG, "纯在");
	// // }
	// //
	// // }
	// //
	// // } catch (Exception e1) {
	// // // TODO Auto-generated catch block
	// // e1.printStackTrace();
	// // }
	// System.out.println("打印数据:" + params.toString());
	// FinalHttp fh = new FinalHttp();
	// fh.configTimeout(15000);
	// fh.configRequestExecutionRetryCount(3);
	// Log.v(TAG, "开始发送post请求");
	// fh.post(Constants.BadiUrl + "upload_imgs_pre", params,
	// new AjaxCallBack<Object>() {
	// Message message;
	// Bundle bundle;
	//
	// @Override
	// public void onLoading(long count, long current) {
	// // TODO Auto-generated method stub
	// super.onLoading(count, current);
	// Log.v(TAG, "count=" + count + "current=" + current);
	// Utils.setProgress_hen((int) count);
	// }
	//
	// @Override
	// public void onSuccess(Object t) {
	// // TODO Auto-generated method stub
	// // Log.v("aaa", "t===>" + t);
	// // Utils.ExitProgress_hen(context);
	// Log.v(TAG, "接收到的结果是:" + (String) t);
	// if (t != null) {
	// Log.v(TAG, "接收到的结果是:" + (String) t);
	// }
	//
	// Results results = Utils.checkResult_NNN(context,
	// (String) t);
	// if (results != null
	// && !results.getRetmsg().equals("null")) {
	// message = new Message();
	// bundle = new Bundle();
	// message.what = 2000;
	// bundle.putString("path", results.getRetmsg());
	// message.setData(bundle);
	// handler.sendMessage(message);
	// } else {
	//
	// }
	// }
	// });
	// // return backpath;
	//
	// path.remove(0);
	// }
	// }

	// /**
	// * （试试递归）
	// *
	// * @param path
	// * @param context
	// * @param handler
	// */
	// public static void UpImg_ceshi_douhao(final ArrayList<String> path,
	// final Context context, final Handler handler) {
	//
	// Utils.showProgress_hen(context);
	// if (path.size() != 0)
	// Utils.setProgress_henMax(path.size() * 100);
	//
	// params = new AjaxParams();
	// ct = context;
	// handlers = handler;
	// MyThreadTool.fixedThreadPool.execute(new Runnable() {
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// for (int i = 0; i < path.size(); i++) {
	// try {
	// File file = new File(ReducePictureQuality(
	// path.get(i),
	// IOUtils.getSaveObjectPath(context,
	// MD5.getMD5(path.get(i)) + ".jpeg")));
	// if (file.exists()) {
	// params.put("" + i, file);
	// Log.v(TAG, "纯在");
	// }
	// } catch (Exception e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	//
	// }
	// Log.v(TAG, "UpImg_ceshi_douhao1");
	// sharehanders.sendEmptyMessage(0);
	// }
	// });
	// // return backpath;
	// // path.remove(0);
	// // }
	// }

	// /**
	// * 上传本地图片（单）
	// *
	// * @param path
	// * 本地图片地址
	// * @param context
	// * 上下文
	// * @param handler
	// * 拿到服务器返回的图片存储地址的传送者(what=2000)
	// */
	// public static void UpImg(String path, final Context context,
	// final Handler handler, String uid, final int MSG_SUCCESS) {
	// AjaxParams params = new AjaxParams();
	//
	// params.put("uid", uid);
	// Log.v(TAG, "path=" + path);
	// try {
	// params.put("info_head", new File(path));
	// } catch (FileNotFoundException e1) {
	// // TODO Auto-generated catch block
	// e1.printStackTrace();
	// }
	// FinalHttp fh = new FinalHttp();
	//
	// fh.post(Constants.BadiUrl + "upload_img", params,
	// new AjaxCallBack<Object>() {
	// Message message;
	// Bundle bundle;
	//
	// @Override
	// public void onLoading(long count, long current) {
	// // TODO Auto-generated method stub
	// super.onLoading(count, current);
	// }
	//
	// @Override
	// public void onSuccess(Object t) {
	// // TODO Auto-generated method stub
	// Log.v("aaa", "t===>" + t);
	// Results results = Utils.checkResult_NNN(context,
	// (String) t);
	//
	// if (results != null
	// && !results.getRetmsg().equals("null")) {
	// message = new Message();
	// bundle = new Bundle();
	// message.what = MSG_SUCCESS;
	// bundle.putString("path", results.getRetmsg());
	// bundle.putBoolean("isRet", results.isRet());
	// message.setData(bundle);
	// handler.sendMessage(message);
	// } else {
	//
	// }
	// }
	// });
	// // return backpath;
	// }

	public static void addImageToEditText(Context context, TextView textView,
			Bitmap bitmap, String tihuanText, int start, int end) {
		// int randomId = 1 + new Random().nextInt(5); //取得随机数randomId
		// 范围在[1,6)之间
		try {
			// 利用反射机制:根据随机产生的1至5的整数从R.drawable类中获得相应资源ID（静态变量）的Field对象
			// Field field = R.drawable.class.getDeclaredField("f_static_" +
			// "001"); //取得图片的名称+1个随机数
			// 获得资源ID的值，也就是静态变量的值
			// int resourceId = Integer.parseInt(field.get(null).toString());
			/*
			 * 在android重要要显示图片信息，必须使用Bitmap位图的对象来装载。 查看Android
			 * 的BitmapFactory的API文档：Public Methods，这些方法描述了如何讲一些字符串，字节数组转化为字节对象
			 */
			// Bitmap bitmap =
			// BitmapFactory.decodeResource(context.getResources(), resourceId);

			// 要让图片替代指定的文字就要用ImageSpan
			// ImageSpan imageSpan = new ImageSpan(context, bitmap);
			ImageSpan imageSpan = new ImageSpan(context, bitmap);

			// 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
			SpannableString spannableString = new SpannableString(tihuanText);
			// 用ImageSpan对象替换face
			spannableString.setSpan(imageSpan, start, end,
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

			// 将随机获得的图像追加到EditText控件的最后
			textView.append(spannableString);
			Log.v("aaa", textView.getText().toString().trim());
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

//	/**
//	 * 生成二维码
//	 * 
//	 * @param str
//	 * @return
//	 * @throws WriterException
//	 */
//	public static Bitmap Create2DCode(String str, int w, int h)
//			throws WriterException {
//		// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
//		BitMatrix matrix = new MultiFormatWriter().encode(str,
//				BarcodeFormat.QR_CODE, w, h);
//		int width = matrix.getWidth();
//		int height = matrix.getHeight();
//		// 二维矩阵转为一维像素数组,也就是一直横着排了
//		int[] pixels = new int[width * height];
//		for (int y = 0; y < height; y++) {
//			for (int x = 0; x < width; x++) {
//				if (matrix.get(x, y)) {
//					pixels[y * width + x] = 0xff000000;
//				}
//			}
//		}
//		Bitmap bitmap = Bitmap.createBitmap(width, height,
//				Bitmap.Config.ARGB_8888);
//		// 通过像素数组生成bitmap,具体参考api
//		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
//		return bitmap;
//	}

	/**
	 * 列如把几Mb的图片减少到几百kb 将图片质量减少存入savePath路径中
	 * 
	 * @param filePath
	 *            源文件路径
	 * @param savePath
	 *            存入的文件路径
	 */
	public static String ReducePictureQuality(String filePath, String savePath) {
		int MAX_IMAGE_SIZE = 900 * 1024; // max final file size
		// Bitmap bmpPic = BitmapFactory.decodeFile(fileUri.getPath());
		// Bitmap bmpPic = BitmapFactory.decodeFile(filePath);
		File file = new File(filePath);
		Bitmap bmpPic = getScBitmap(filePath);

		if ((bmpPic.getWidth() >= 1024) && (bmpPic.getHeight() >= 1024)) {
			BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
			if (file.length() < 20480) {
				bmpOptions.inSampleSize = 0;
			} else if (file.length() < 51200) { // 20-50k
				bmpOptions.inSampleSize = 0;
			} else if (file.length() < 307200) { // 50-300k
				bmpOptions.inSampleSize = 0;
			} else if (file.length() < 819200) { // 300-800k
				bmpOptions.inSampleSize = 0;
			} else if (file.length() < 1048576) { // 800-1024k
				bmpOptions.inSampleSize = 0;
			} else {
				bmpOptions.inSampleSize = 0;
			}
			// bmpOptions.inSampleSize = 2;
			bmpOptions.inPreferredConfig = Config.ARGB_8888;
			bmpOptions.inPurgeable = true;// 允许可清除
			bmpOptions.inInputShareable = true;// 以上options的两个属性必须联合使用才会有效果

			bmpOptions.inDither = false; // Disable Dithering mode
			bmpOptions.inPurgeable = true;
			// // 计算图片缩放比例
			// final int minSideLength = Math.min(35, 35);
			// opts.inSampleSize = ImageTools.computeSampleSize(opts,
			// minSideLength,
			// 35 * 35);
			// opts.inJustDecodeBounds = false;
			// opts.inInputShareable = true;
			// opts.inPurgeable = true;

			// bmpOptions.ins=8;
			bmpOptions.inJustDecodeBounds = true;
			while ((bmpPic.getWidth() >= 1024) && (bmpPic.getHeight() >= 1024)) {
				bmpOptions.inSampleSize++;
				// bmpPic = BitmapFactory.decodeFile(fileUri.getPath(),
				// bmpOptions);
				bmpPic = BitmapFactory.decodeFile(filePath, bmpOptions);

				/**
				 * 把图片旋转为正的方向
				 */
				// Bitmap newbitmap = ImageTools.rotaingImageView(degree,
				// cbitmap);
			}
			Log.d(TAG, "Resize: " + bmpOptions.inSampleSize);
		}
		int compressQuality = 104; // quality decreasing by 5 every loop. (start
									// from 99)
		int streamLength = MAX_IMAGE_SIZE;
		while (streamLength >= MAX_IMAGE_SIZE) {
			ByteArrayOutputStream bmpStream = new ByteArrayOutputStream();
			compressQuality -= 5;
			Log.d(TAG, "Quality: " + compressQuality);
			bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality,
					bmpStream);
			byte[] bmpPicByteArray = bmpStream.toByteArray();
			streamLength = bmpPicByteArray.length;
			Log.d(TAG, "Size: " + streamLength);
		}

		try {
			// c
			FileOutputStream bmpFile = new FileOutputStream(savePath);
			bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality,
					bmpFile);
			bmpFile.flush();
			bmpFile.close();
		} catch (Exception e) {
			Log.e(TAG, "Error on saving file");
		}
		// savePNG_After(bmpPic, savePath);
		return savePath;
	}

	/**
	 * 将图片转正
	 * 
	 * @param path2
	 * @return
	 */
	public static Bitmap getScBitmap(String path2) {
		File file = new File(path2);

		int degree = ImageTools.readPictureDegree(path2);
		BitmapFactory.Options opts = new BitmapFactory.Options();// 获取缩略图显示到屏幕上
		// opts.inSampleSize = 2;
		if (file.length() < 20480) {
			opts.inSampleSize = 0;
		} else if (file.length() < 51200) { // 20-50k
			opts.inSampleSize = 0;
		} else if (file.length() < 307200) { // 50-300k
			opts.inSampleSize = 0;
		} else if (file.length() < 819200) { // 300-800k
			opts.inSampleSize = 2;
		} else if (file.length() < 1048576) { // 800-1024k
			opts.inSampleSize = 4;
		} else {
			opts.inSampleSize = 6;
		}
		opts.inJustDecodeBounds = true;
		// BitmapFactory.decodeFile(path2, opts);
		// // 计算图片缩放比例
		// final int minSideLength = Math.min(100, 100);

		// opts.inSampleSize = ImageTools.computeSampleSize(opts, minSideLength,
		// 100 * 100);
		opts.inJustDecodeBounds = false;
		opts.inInputShareable = true;
		opts.inPurgeable = true;
		// options.inPreferredConfig = Bitmap.Config.ARGB_4444;
		Bitmap cbitmap = BitmapFactory.decodeFile(path2, opts);
		/**
		 * 把图片旋转为正的方向
		 */
		Bitmap newbitmap = ImageTools.rotaingImageView(degree, cbitmap);
		return newbitmap;
	}

	public static Bitmap getSmallBitmap(String filePath) {

		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		Bitmap bm = BitmapFactory.decodeFile(filePath, options);
		if (bm == null) {
			return null;
		}
		int degree = readPictureDegree(filePath);
		bm = rotateBitmap(bm, degree);
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.JPEG, 30, baos);

		} finally {
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bm;

	}

	private static Bitmap rotateBitmap(Bitmap bitmap, int rotate) {
		if (bitmap == null)
			return null;

		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		// Setting post rotate to 90
		Matrix mtx = new Matrix();
		mtx.postRotate(rotate);
		return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
	}

	/**
	 * 读取图片属性：旋转的角度
	 * 
	 * @param path
	 *            图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/*
	 * 旋转图片
	 * 
	 * @param angle
	 * 
	 * @param bitmap
	 * 
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		System.out.println("angle2=" + angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/*
	 * 旋转图片并且缩小
	 * 
	 * @param angle
	 * 
	 * @param bitmap
	 * http://blog.sina.com.cn/s/blog_783ede0301014mln.html//两种旋转方法
	 * 
	 * @return Bitmap
	 */
	public static Bitmap rotaingImageView2(int angle, Bitmap bitmap) {
		// // 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);

		float targetX, targetY;
		if (angle == 90) {
			targetX = bitmap.getHeight();
			targetY = 0;
		} else {
			targetX = bitmap.getHeight();
			targetY = bitmap.getWidth();
		}
		final float[] values = new float[9];
		matrix.getValues(values);

		float x1 = values[Matrix.MTRANS_X];
		float y1 = values[Matrix.MTRANS_Y];

		matrix.postTranslate(targetX - x1, targetY - y1);

		byte[] b = bitmapToBytes(bitmap);

		BitmapFactory.Options b_options = new BitmapFactory.Options();

		b_options.inSampleSize = 2;

		// Bitmap bm1=BitmapFactory.decodeByteArray(b, 0, b.length,
		// b_options).copy(Bitmap.Config.ARGB_4444, true);
		// .copy(Bitmap.Config.ARGB_8888, isMutable);

		// Bitmap bm1=bitmap.copy(Bitmap.Config.ARGB_8888, false);
		// Bitmap bm1=Bitmap.createScaledBitmap(bitmap, bitmap.getHeight(),
		// bitmap.getWidth(), true);
		Bitmap bm1 = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getWidth(),
				Bitmap.Config.ARGB_4444);
		// Bitmap.Config.ARGB_4444
		Paint paint = new Paint();
		Canvas canvas = new Canvas(bm1);
		// paint.set
		// canvas.
		canvas.drawBitmap(bitmap, matrix, paint);

		return bm1;
		// matrix.postScale(bitmap.getWidth()/5, bitmap.getHeight()/5);
		// System.out.println("angle2=" + angle);
		// // 创建新的图片
		// Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
		// bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		// return resizedBitmap;
	}

	// 设置缩放大小对图片作处理
	public static Bitmap getBitmapFromFile(File dst, int width, int height) {
		if (null != dst && dst.exists()) {
			BitmapFactory.Options opts = null;
			if (width > 0 && height > 0) {
				opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(dst.getPath(), opts);
				// 计算图片缩放比例
				final int minSideLength = Math.min(width, height);
				opts.inSampleSize = computeSampleSize(opts, minSideLength,
						width * height);
				opts.inJustDecodeBounds = false;
				opts.inInputShareable = true;
				opts.inPurgeable = true;
			}
			try {
				return BitmapFactory.decodeFile(dst.getPath(), opts);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	// /**
	// * 读取图片属性：旋转的角度
	// * @param path 图片绝对路径
	// * @return degree旋转的角度
	// */
	// public static int readPictureDegree(String path) {
	// int degree = 0;
	// try {
	// ExifInterface exifInterface = new ExifInterface(path);
	// int orientation =
	// exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
	// ExifInterface.ORIENTATION_NORMAL);
	// switch (orientation) {
	// case ExifInterface.ORIENTATION_ROTATE_90:
	// degree = 90;
	// break;
	// case ExifInterface.ORIENTATION_ROTATE_180:
	// degree = 180;
	// break;
	// case ExifInterface.ORIENTATION_ROTATE_270:
	// degree = 270;
	// break;
	// }
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// return degree;
	// }

	private static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
		}

		return inSampleSize;
	}

	// public void set()
	// {
	// BitmapFactory.Options options = new BitmapFactory.Options();
	// options.inJustDecodeBounds = true;
	// BitmapFactory.decodeResource(getResources(), R.id.myimage, options);
	// int imageHeight = options.outHeight;
	// int imageWidth = options.outWidth;
	// String imageType = options.outMimeType;
	// }
	public static Bitmap readBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	// BitmapFactory.Options options = new BitmapFactory.Options();
	// options.inJustDecodeBounds = true;
	// bitmap = BitmapFactory.decodeFile(photoPath, options);
	// options.inSampleSize = options.outWidth/photoWidth;
	// int height = options.outHeight*photoWidth/options.outWidth;
	// options.outHeight = height;
	// options.outWidth = photoWidth;
	// options.inJustDecodeBounds = false;
	// options.inPreferredConfig = Bitmap.Config.ARGB_4444;
	// options.inPurgeable = true;
	// options.inInputShareable = true;
	// bitmap = BitmapFactory.decodeFile(photoPath, options);
	// mIvConfirmphoto.setImageBitmap(null);
	// mIvConfirmphoto.setImageBitmap(bitmap);
	// 获取最大的运行内存 http://mobile.51cto.com/abased-406980.htm
	public static int getMaxMemory() {
		int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
		Log.d("TAG", "Max memory is " + maxMemory + "KB");
		return maxMemory;
	}

}
