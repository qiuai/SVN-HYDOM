package com.hydom.core.server.service;

import com.hydom.account.ebean.Area;
import com.hydom.core.server.ebean.CarTeam;
import com.hydom.util.dao.DAO;
import com.hydom.util.dao.PageView;

/**
 * @Description 车队业务层接口
 * @author WY
 * @date 2015年7月7日 下午5:38:50
 */

public interface CarTeamService extends DAO<CarTeam>{
	
	/**
	 * 
	 * @param areaId 区域
	 * @param page 页码
	 * @return
	 */
	PageView<CarTeam> getPage(Area area, Integer page);

}
