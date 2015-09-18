package com.hydom.core.service;

import java.sql.ResultSet;

import javax.sql.DataSource;

import org.antlr.grammar.v3.ANTLRv3Parser.exceptionGroup_return;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.hydom.core.ebean.Room;

public class RoomServiceBean implements RoomService {

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Room findByUC(String cid, String uid) {
		try {

			Room room = this.jdbcTemplate.queryForObject(
					"select id, userId, types, nowFlow, name from t_course where id = ? and userId=? ", new Object[] {
							cid, uid }, new RowMapper<Room>() {
						public Room mapRow(ResultSet rs, int rowNum) {
							try {
								Room room = new Room();
								room.setId(rs.getString("id"));
								room.setUserId(rs.getString("userId"));
								room.setTypes(rs.getInt("types"));
								room.setNowFlow(rs.getString("nowFlow"));
								room.setName(rs.getString("name"));
								return room;
							} catch (Exception e) {
								e.printStackTrace();
								return null;
							}
						}
					});
			return room;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public double avgScore(String roomId) {
		try {
			double avgscore = this.jdbcTemplate.queryForObject("select AVG(score) from t_comment where courseId = ?",
					Double.class, roomId);
			return avgscore;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public Room findOne(String id) {
		try {
			Room room = this.jdbcTemplate.queryForObject("select id, userId,types,nowFlow,name from t_course where id = ?",
					new Object[] { id }, new RowMapper<Room>() {
						public Room mapRow(ResultSet rs, int rowNum) {
							try {
								Room room = new Room();
								room.setId(rs.getString("id"));
								room.setUserId(rs.getString("userId"));
								room.setTypes(rs.getInt("types"));
								room.setNowFlow(rs.getString("nowFlow"));
								room.setName(rs.getString("name"));
								return room; 
							} catch (Exception e) {
								e.printStackTrace();
								return null;
							}
						}
					});
			return room;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public int setCurrentLiveStream(String roomId, String streamName) {
		return this.jdbcTemplate.update("update t_course set nowFlow = ? where id = ?", streamName, roomId);
	}

	@Override
	public int setRoomStatus(String roomId, int status) {
		return this.jdbcTemplate.update("update t_course set status = ? where id = ?", status, roomId);
	}

}
