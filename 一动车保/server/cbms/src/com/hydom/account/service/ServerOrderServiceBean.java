package com.hydom.account.service;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.ServerOrder;
import com.hydom.util.dao.DAOSupport;
@Service
public class ServerOrderServiceBean extends DAOSupport<ServerOrder> implements ServerOrderService{

}
