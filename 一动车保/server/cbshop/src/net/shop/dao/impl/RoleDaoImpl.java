/*
 * 
 * 
 * 
 */
package net.shop.dao.impl;

import net.shop.dao.RoleDao;
import net.shop.entity.Role;

import org.springframework.stereotype.Repository;

/**
 * Dao - 角色
 * 
 * 
 * 
 */
@Repository("roleDaoImpl")
public class RoleDaoImpl extends BaseDaoImpl<Role, Long> implements RoleDao {

}