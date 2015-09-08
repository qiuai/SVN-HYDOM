package com.carinsurance.config;

/**
 * 服务器地地址
 * 
 * @author Administrator
 *
 */
public class HttpUrl
{

	public static final String IP = "192.168.0.234:8080";

	public static final String BASE_IMAGE_URL = "http://" + IP;
	
	/**
	 * 服务器地地址
	 */
	public static final String BASE_URL = "http://" + IP + "/test/api/technician/";
	
	/**
	 * 上传图片
	 */
	public static final String BASE_URL_IMAGE = "user/profile/edit";
}
