package com.hydom.core.service;

import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.hydom.core.ebean.User;

public class UserServiceBean implements UserService {
	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public User findOne(String id) {
		try {
			User user = this.jdbcTemplate.queryForObject("select id, nickname,isteacher from t_user where id = ?",
					new Object[] { id }, new RowMapper<User>() {
						public User mapRow(ResultSet rs, int rowNum) {
							try {
								User user = new User();
								user.setId(rs.getString("id"));
								user.setNickname(rs.getString("nickname"));
								user.setIsteacher(rs.getBoolean("isteacher"));
								return user;
							} catch (Exception e) {
								e.printStackTrace();
								return null;
							}
						}
					});
			return user;
		} catch (Exception e) {
			return null;
		}
	}
}
