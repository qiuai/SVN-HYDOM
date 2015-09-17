package com.carinsurance.bannerviewpager;

import android.widget.ImageView;

public class BannerEntity {
	public ImageView imageView;
	public ArticleEntity entity;
	
	public ImageView getImageView() {
		return imageView;
	}
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	public ArticleEntity getEntity() {
		return entity;
	}
	public void setEntity(ArticleEntity entity) {
		this.entity = entity;
	}
}
