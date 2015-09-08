package com.hydom.account.service;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Parameter;
import com.hydom.util.dao.DAOSupport;

@Service
public class ParameterServiceBean extends DAOSupport<Parameter> implements  ParameterService {
	
}
