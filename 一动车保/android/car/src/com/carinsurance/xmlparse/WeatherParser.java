package com.carinsurance.xmlparse;

import java.io.InputStream;
import java.util.List;

import com.carinsurance.xmlparsedemo.Book;

public interface WeatherParser {
	/**
	 * 解析输入流 得到Book对象集合
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public List<Weather> parse(InputStream is) throws Exception;
	/**
	 * 序列化Book对象集合 得到XML形式的字符串
	 * 
	 * @param books
	 * @return
	 * @throws Exception
	 */
	public String serialize(List<Book> books) throws Exception;
}
