package com.carinsurance.bannerviewpager;

import java.io.Serializable;
import java.util.ArrayList;

public class ArticleEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -496329760254552870L;
	public int id;
	public String title;
	public String[] imageTitle;
	public String summary;
	public boolean recommend;
	public String description; //推送专题描述
	public int type;
	public boolean isTopic;
	public Object content;
	public String url;
	
	public long publishTime;
	public String area;
	public int articleType;
	public int commentCount;
	public String source;
	
	public String thumb; //推送专题缩略图
	
	public boolean isFormPush;

	public class  BaseEntity {
		public int code;
		public ArrayList<ArticleEntity> data;
		public ArrayList<ArticleEntity> focus;
		public ArticleEntity header;
	}

}
