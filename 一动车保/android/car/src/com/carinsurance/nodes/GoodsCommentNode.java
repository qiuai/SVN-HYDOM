package com.carinsurance.nodes;

public class GoodsCommentNode {
	private String cid;// 评论人id
	private String cpPhoto;// 评论人头像
	private String cperson;// 评论人
	private String cmname; // 车型名称
	private String ctime;// 评论时间
	private int star;// 评论星级
	private String content;// 评论内容
	private String[] imglist;// 图片列表

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getCpPhoto() {
		return cpPhoto;
	}

	public void setCpPhoto(String cpPhoto) {
		this.cpPhoto = cpPhoto;
	}

	public String getCperson() {
		return cperson;
	}

	public void setCperson(String cperson) {
		this.cperson = cperson;
	}

	public String getCmname() {
		return cmname;
	}

	public void setCmname(String cmname) {
		this.cmname = cmname;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getImglist() {
		return imglist;
	}

	public void setImglist(String[] imglist) {
		this.imglist = imglist;
	}

	public int getStar() {
		return star;
	}

	public void setStar(int star) {
		this.star = star;
	}

}
