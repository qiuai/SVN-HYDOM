package com.hydom.extra.service;

import org.springframework.stereotype.Service;

import com.hydom.dao.DAOSupport;
import com.hydom.extra.ebean.ShortMessageRecode;

@Service
public class ShortMessageRecordServiceBean extends DAOSupport<ShortMessageRecode> implements
		ShortMessageRecordService {

}
