package com.hydom.core.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.hydom.core.ebean.Room;
import com.hydom.core.ebean.UserCourse;

public class UserCourseServiceBean implements UserCourseService {

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void saveOrUpdate(String roomId, String userId) {
		UserCourse userCourse = this.findByUC(roomId, userId);
		if (userCourse != null) {// 有对应的观看记录，只对“modifyDate”作更新
			this.jdbcTemplate.update("update t_usercourse set modifyDate = ? where id = ?", new Date(),
					userCourse.getId());
		} else {
			this.jdbcTemplate.update(
					"insert into t_usercourse (id, createDate,types,userId,courseId) values (?,?,?,?,?)", UUID
							.randomUUID().toString().replaceAll("-", ""), new Date(), 1, userId, roomId);
		}
		// 设置用户当前所在状态
		deleteCurrentRoomState(userId);
		this.jdbcTemplate.update("insert into t_usercourse (id, createDate,types,userId,courseId) values (?,?,?,?,?)",
				UUID.randomUUID().toString().replaceAll("-", ""), new Date(), 2, userId, roomId);

	}

	@Override
	public int deleteCurrentRoomState(String userId) {
		return this.jdbcTemplate.update("delete from t_usercourse where userId=? and types=?", userId, 2);
	}

	/**
	 * 通过用户id和房间(课程)ID找到最近的对应记录
	 * 
	 * @param roomId
	 * @param userId
	 * @return
	 */
	private UserCourse findByUC(String roomId, String userId) {
		try {
			UserCourse userCourse = this.jdbcTemplate
					.queryForObject(
							"select id,types,userId,courseId,modifyDate from t_usercourse where courseId = ? and userId=? and types=?",
							new Object[] { roomId, userId, 1 }, new RowMapper<UserCourse>() {
								public UserCourse mapRow(ResultSet rs, int rowNum) {
									try {
										UserCourse userCourese = new UserCourse();
										userCourese.setId(rs.getString("id"));
										userCourese.setUserId(rs.getString("userId"));
										userCourese.setRoomid(rs.getString("courseId"));
										userCourese.setTypes(rs.getInt("types"));
										userCourese.setModifyDate(rs.getDate("modifyDate"));
										return userCourese;
									} catch (SQLException e) {
										e.printStackTrace();
										return null;
									}
								}
							});
			return userCourse;
		} catch (Exception e) {
			return null;
		}
	}
}
