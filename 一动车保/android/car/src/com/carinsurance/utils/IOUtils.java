package com.carinsurance.utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.MediaColumns;
import android.util.Base64;
import android.util.Log;

/**
 * IO 工具类
 */
public class IOUtils {

	// /**
	// * 写入序列化对象到sdcard
	// * @throws IOException
	// */
	public static void WriteSerializedObjectsToSDcard(String path, Object object) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}

		FileOutputStream fl = null;
		ObjectOutputStream f = null;
		try {
			fl = new FileOutputStream(path);
			f = new ObjectOutputStream(fl);

			f.writeObject(object);
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			try {
				fl.close();
				f.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * 读取序列化对象到sdcard
	 */
	public static Object readSerializedObjectsToSDcard(String path) {
		FileInputStream fs = null;
		ObjectInputStream os = null;
		Object object = null;
		try {
			fs = new FileInputStream(path);
			os = new ObjectInputStream(fs);
			object = os.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			// System.out.println();
		}
		return object;
	}

	/**
	 * 只适用于评论的 返回文件安装包储存路径，在软件卸载后也能一起卸载，ljm
	 * 
	 * @param context
	 * @param filename
	 * @return
	 */
	public static String getSavePinLunObjectPath(Context context) {
		String filename = "BadiBadiAppPingLunLuJing";
		/**
		 * 获取包app安装路径+filename;路径
		 * 
		 * @param context
		 * @param filename
		 * @return
		 */
		return (context.getApplicationContext().getFilesDir().getAbsolutePath() + "/" + filename);
	}

	/**
	 * 只适用登录后菜单 返回文件安装包储存路径，在软件卸载后也能一起卸载，ljm
	 * 
	 * @param context
	 * @param filename
	 * @return
	 */
	public static String getObjectYouHuaCaiDanPath(Context context) {
		String filename = "BadiBadiAppYouHuaCaiDanLuJing";
		/**
		 * 获取包app安装路径+filename;路径
		 * 
		 * @param context
		 * @param filename
		 * @return
		 */
		return (context.getApplicationContext().getFilesDir().getAbsolutePath() + "/" + filename);
	}

	/**
	 * 返回文件安装包储存路径，在软件卸载后也能一起卸载，ljm
	 * 
	 * @param context
	 * @param filename
	 * @return
	 */
	public static String getSaveObjectPath(Context context, String filename) {
		// String filename="BadiBadiAppPingLunLuJing";
		/**
		 * 获取包app安装路径+filename;路径
		 * 
		 * @param context
		 * @param filename
		 * @return
		 */
		return (context.getApplicationContext().getFilesDir().getAbsolutePath() + "/" + filename);
	}

	/**
	 * 返回文件安装包储存路径，在软件卸载后也能一起卸载，ljm
	 * 
	 * @param context
	 * @return
	 */
	public static String getSaveObjectPath(Context context) {
		// String filename="BadiBadiAppPingLunLuJing";
		/**
		 * 获取包app安装路径+filename;路径
		 * 
		 * @param context
		 * @param filename
		 * @return
		 */
		return (context.getApplicationContext().getFilesDir().getAbsolutePath());
	}

	// static FileOutputStream fl;
	// static ObjectOutputStream f;
	// /**
	// * 写入ser文件
	// * 序列化存储
	// */
	// public static void SerializableSave(String serFile,)
	// {
	// try {
	// fl=new FileOutputStream(serFile);
	// f=new ObjectOutputStream(fl);
	// clas s=new clas();
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	/**
	 * 复制文件功能: 将文件src复制为dst 只能复制文件! 不支持文件夹的复制!
	 * 
	 * @param src
	 *            源文件
	 * @param dst
	 *            目标文件
	 */
	public static void cp(String src, String dst) {
		try {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dst);
			byte[] buf = new byte[10 * 1024];
			int count;
			while ((count = in.read(buf)) != -1) {
				// System.out.println(count);
				out.write(buf, 0, count);
			}
			// System.out.println("结束了:"+count);
			in.close();
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void cp2(String src, String dst) {
		try {
			InputStream in = new FileInputStream(src);
			OutputStream out = new FileOutputStream(dst);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void print(File file) {
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			int b;
			int i = 1;
			while ((b = raf.read()) != -1) {
				if (b <= 0xf)
					System.out.print("0");
				System.out.print(Integer.toHexString(b) + " ");
				if (i++ % 16 == 0) {
					System.out.println();
				}
			}
			System.out.println();
			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	public static void print(byte[] buf) {
		int i = 1;
		for (int b : buf) {
			b = b & 0xff;
			if (b <= 0xf)
				System.out.print("0");
			System.out.print(Integer.toHexString(b) + " ");
			if (i++ % 16 == 0) {
				System.out.println();
			}

		}
		System.out.println();
	}

	public static byte[] ReadFileFromSdcardByte(String filename) {
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
		// try {
		// System.out.println("Available bytes:" + in.available());
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		byte[] temp = new byte[1024];

		int size = 0;

		try {
			while ((size = in.read(temp)) != -1) {

				out.write(temp, 0, size);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] content = out.toByteArray();
		return content;
	}

	/**
	 * 实现将文件读取到一个byte数组
	 */
	public static byte[] ReadFileFromSdcard2(String filename) {
		try {
			InputStream in = new FileInputStream(filename);
			byte[] buf = new byte[in.available()];
			in.read(buf);
			in.close();
			return buf;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将小文件 一次性 读取到 byte数组中返回
	 * 
	 * @param file
	 *            文件名
	 * @return 全部文件内容
	 */
	public static byte[] ReadFileFromSdcard(String file) {
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			byte[] buf = new byte[(int) raf.length()];
			raf.read(buf);
			raf.close();
			return buf;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static byte[] ReadFileFromSdcard(File file) {
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			byte[] buf = new byte[(int) raf.length()];
			raf.read(buf);
			raf.close();
			return buf;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 在控制台上按照16进制格式输出文件的内容 每16个byte为一行
	 * 
	 * @param file
	 *            文件名
	 * @throws RuntimeException
	 *             如果文件读取失败,跑出异常
	 */
	public static void print(String file) {// IOUtils.java
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			int b;
			int i = 1;
			while ((b = raf.read()) != -1) {
				if (b <= 0xf) {
					System.out.print('0');
				}
				System.out.print(Integer.toHexString(b) + " ");
				if (i++ % 16 == 0) {
					System.out.println();
				}
			}
			System.out.println();
			raf.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 是否有sdcard
	 * 
	 * @return
	 */
	public static boolean hasSDcard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}

		// return hasSD;
	}

	/**
	 * (只适用于sdCard的)uri转化为路径
	 * 
	 * @return
	 */
	public static String UriToPath(Context context, Uri uri) {
		Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
		cursor.moveToFirst();
		String url = cursor.getString(1);
		// for (int i = 0; i < cursor.getColumnCount(); i++)
		// {// 取得图片uri的列名和此列的详细信息
		// Log.v("aaa",i + "-" + cursor.getColumnName(i) + "-" +
		// cursor.getString(i));
		// }
		return url;
	}

	// 根据Uri获取文件路径
	private String getFilePathFromUri(Context context,Uri uri) {
		String[] proj = { MediaColumns.DATA };

		Cursor actualimagecursor = context.getContentResolver().query(uri, proj, null, null, null);

		int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaColumns.DATA);
		actualimagecursor.moveToFirst();
		String img_path = actualimagecursor.getString(actual_image_column_index);
		return img_path;

	}

	/**
	 * 获取 sdCard的路径
	 * 
	 * @return
	 */
	public static String getSDPath() {
		// File sdDir = null;
		// boolean sdCardExist = Environment.getExternalStorageState()
		// .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
		// if (sdCardExist)
		// {
		// sdDir = Environment.getExternalStorageDirectory();//获取跟目录
		// }
		// return sdDir.toString();
		return Environment.getExternalStorageDirectory().toString();
	}

	/**
	 * 获取app的hash值
	 * 
	 * @param context
	 * @param pageName
	 *            包名的
	 * 
	 * 
	 * */
	public static String showHashKey(Context context, String pageName) {// "com.badibadi.uniclubber"
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(pageName, PackageManager.GET_SIGNATURES); // Your
																													// package
																													// name
																													// here
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
				return Base64.encodeToString(md.digest(), Base64.DEFAULT);
			}
		} catch (NameNotFoundException e) {
		} catch (NoSuchAlgorithmException e) {
		}
		return "";
	}

	// 下面两个是用来上传图片的
	// /** 调用文件选择软件来选择文件 **/
	// private void showFileChooser() {
	// Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
	// intent.setType("*/*");
	// intent.addCategory(Intent.CATEGORY_OPENABLE);
	// try {
	// startActivityForResult(Intent.createChooser(intent, "请选择一个要上传的文件"),
	// FILE_SELECT_CODE);
	// } catch (android.content.ActivityNotFoundException ex) {
	// // Potentially direct the user to the Market with a Dialog
	// Toast.makeText(getActivity(), "请安装文件管理器", Toast.LENGTH_SHORT)
	// .show();
	// }
	// }

	// /** 根据返回选择的文件，来进行上传操作 **/
	// @Override
	// public void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// // TODO Auto-generated method stub
	// if (resultCode == Activity.RESULT_OK) {
	// // Get the Uri of the selected file
	// Uri uri = data.getData();
	// String url;
	// try {
	// url = FFileUtils.getPath(getActivity(), uri);
	// Log.i("ht", "url" + url);
	// String fileName = url.substring(url.lastIndexOf("/") + 1);
	// intent = new Intent(getActivity(), UploadServices.class);
	// intent.putExtra("fileName", fileName);
	// intent.putExtra("url", url);
	// intent.putExtra("type ", "");
	// intent.putExtra("fuid", "");
	// intent.putExtra("type", "");
	//
	// getActivity().startService(intent);
	//
	// } catch (URISyntaxException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// super.onActivityResult(requestCode, resultCode, data);
	// }
}
