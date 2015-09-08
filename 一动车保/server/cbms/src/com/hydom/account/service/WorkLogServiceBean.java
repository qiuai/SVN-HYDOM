package com.hydom.account.service;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.WorkLog;
import com.hydom.util.dao.DAOSupport;

@Service
public class WorkLogServiceBean extends DAOSupport<WorkLog> implements WorkLogService {
}
