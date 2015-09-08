package com.hydom.account.service;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.SystemParam;
import com.hydom.util.dao.DAOSupport;

@Service("SystemParamService")
public class SystemParamServiceBean extends DAOSupport<SystemParam> implements SystemParamService{

}
