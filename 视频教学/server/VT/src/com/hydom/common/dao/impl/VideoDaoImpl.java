package com.hydom.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hydom.common.dao.VideoDao;
import com.hydom.vt.entity.Video;

@Repository
public class VideoDaoImpl extends BaseDaoImpl<Video, String> implements VideoDao {

}
