package com.carinsurance.utils;

import android.content.Context;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

public class MyDbUtils<T> {
	static MyDbUtils myDbUtils;
	Context context;
	public final String dbName = "car";

	public MyDbUtils(Context context) {
		this.context = context;
	}

	// public MyDbUtils getInstance(Context context) {
	// if (myDbUtils != null) {
	// return myDbUtils;
	// }
	// return new MyDbUtils(context);
	// }

	// public void findAll(Context context, String dbName, Object obj) throws
	// Exception {
	// DbUtils db = DbUtils.create(context, dbName);
	//
	// }

	@SuppressWarnings("unchecked")
	public T findFirst(Class<T> obj) {
		DbUtils db = DbUtils.create(context, dbName);
		// Parent Parent =
		// db.findFirst(Selector.from(Parent.class).where("name","=","test"));

		try {
			return db.findFirst(obj);
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void save(Class<T> obj) throws Exception {
		DbUtils db = DbUtils.create(context, dbName);
		db.save(obj);

	}

}
