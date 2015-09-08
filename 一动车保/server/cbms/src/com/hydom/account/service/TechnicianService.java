package com.hydom.account.service;

import com.hydom.account.ebean.Technician;
import com.hydom.util.dao.DAO;

/**
 * 技师管理模块接口
 * 
 * @author FXW
 * 
 */
public interface TechnicianService extends DAO<Technician> {

	/**
	 * 查询是否有空闲技师
	 * 
	 * @return
	 */
	public boolean isFree();

	/**
	 * 服务完成要先到数据库里查询没有被接单的订单
	 */
	public void serverFinish();

	/**
	 * 验证账户名是否重复
	 * 
	 * @param account
	 *            账号
	 * 
	 * @return
	 */
	public boolean isExist(String account);

	/**
	 * 根据账号和密码获得技师实体
	 * 
	 * @param account
	 *            账号
	 * @param password
	 *            密码
	 * @return 技师实体
	 */
	public Technician findTechnician(String account, String password);

	/**
	 * 验证技师电话是否重复
	 * 
	 * @param name
	 * @return
	 */
	public boolean isExistPhoneNumber(String phonenumber);
}
