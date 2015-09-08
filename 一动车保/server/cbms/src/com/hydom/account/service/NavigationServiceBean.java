package com.hydom.account.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.hydom.account.ebean.Navigation;
import com.hydom.account.ebean.ServiceType;
import com.hydom.util.ConfigMessage;
import com.hydom.util.bean.NavigationBean;
import com.hydom.util.dao.DAOSupport;

@Service
public class NavigationServiceBean extends DAOSupport<Navigation> implements NavigationService {
	
	@Resource
	private ServiceTypeService serviceTypeService;
	
	@Override
	public List<NavigationBean> getNavigationList() {
		
		List<NavigationBean> beans = new ArrayList<NavigationBean>();
		//服务
		List<ServiceType> serviceTypes =  serviceTypeService.getList(null,null,null);
		for(ServiceType st : serviceTypes){
			NavigationBean navigationBean = new NavigationBean();
			navigationBean.setName(st.getName());
			navigationBean.setUrl(ConfigMessage.service_type + st.getId());
			beans.add(navigationBean);
		}
		//商品 待续
		
		return beans;
	}

}
