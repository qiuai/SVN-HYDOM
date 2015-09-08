package com.hydom.account.service;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.ProductImage;
import com.hydom.util.dao.DAOSupport;

@Service
public class ProductImageServiceBean extends DAOSupport<ProductImage> implements ProductImageService {
	
}
