package com.hydom.zxy.dao.impl;

import com.hydom.common.dao.impl.BaseDaoImpl;
import com.hydom.zxy.entity.Version;
import com.hydom.zxy.dao.VersionDao;
import org.springframework.stereotype.Repository;

@Repository
public class VersionDaoImpl extends BaseDaoImpl<Version , String> implements VersionDao {

}