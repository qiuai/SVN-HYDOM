package com.hydom.common;

import javax.annotation.Resource;

import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.hydom.common.service.TestService;

@Component
@Log4j
public class Initializing implements InitializingBean {
	@Resource
	private TestService testService;

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}

	public void init() {
		
	}

}
