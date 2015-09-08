package com.hydom.account.service;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Feedback;
import com.hydom.util.dao.DAOSupport;

@Service
public class FeedbackServiceBean extends DAOSupport<Feedback> implements
		FeedbackService {

}
