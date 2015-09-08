package com.hydom.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.hydom.common.dao.TestDao;
import com.hydom.vt.entity.Test;


/**
 * DAO实现类 -测试DAO
 * @author Holen
 * @version 1.0.0 2014.6.8 新建
 */
@Repository
public class TestDaoImpl extends BaseDaoImpl<Test, String> implements TestDao {

}
