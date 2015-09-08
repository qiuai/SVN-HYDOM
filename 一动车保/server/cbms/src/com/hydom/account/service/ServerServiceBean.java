package com.hydom.account.service;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Server;
import com.hydom.util.dao.DAOSupport;

@Service 
public class ServerServiceBean extends DAOSupport<Server> implements ServerService{

}
