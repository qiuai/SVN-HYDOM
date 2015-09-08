package com.hydom.common.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hydom.common.dao.VideoDao;
import com.hydom.common.service.VideoService;
import com.hydom.vt.entity.Video;

@Service
public class VideoServiceImpl extends BaseServiceImpl<Video, String> implements VideoService {

	@Resource
	private VideoDao videoDao;

	@Resource
	public void setBaseDao(VideoDao videoDao) {
		super.setBaseDao(videoDao);
	}
}
