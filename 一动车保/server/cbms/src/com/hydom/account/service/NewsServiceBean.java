package com.hydom.account.service;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.News;
import com.hydom.util.dao.DAOSupport;
@Service
public class NewsServiceBean extends DAOSupport<News> implements NewsService{

}
