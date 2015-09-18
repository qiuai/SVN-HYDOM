package com.hydom.core.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.hydom.core.ebean.Room;
import com.hydom.core.ebean.Video;

public class VideoServiceBean implements VideoService {

	private JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Video> listByRoomId(String roomId) {
		List<Video> videos = this.jdbcTemplate
				.query("select id,name,createDate,courseId,userId from t_video where courseId = ? and enable = ? order by createDate asc",
						new Object[] { roomId, 1 }, new RowMapper<Video>() {
							public Video mapRow(ResultSet rs, int rowNum) throws SQLException {
								Video video = new Video();
								video.setId(rs.getString("id"));
								video.setName(rs.getString("name"));
								video.setCreateDate(rs.getDate("createDate"));
								video.setRoomId(rs.getString("courseId"));
								video.setUserId(rs.getString("userId"));
								return video;
							}
						});
		return videos;
	}

	@Override
	public Video findOne(String id) {
		try {
			Video video = this.jdbcTemplate.queryForObject(
					"select id,name,createDate,courseId,userId from t_video where id = ?", new Object[] { id },
					new RowMapper<Video>() {
						public Video mapRow(ResultSet rs, int rowNum) {
							try {
								Video video = new Video();
								video.setId(rs.getString("id"));
								video.setName(rs.getString("name"));
								video.setCreateDate(rs.getDate("createDate"));
								video.setRoomId(rs.getString("courseId"));
								video.setUserId(rs.getString("userId"));
								return video;
							} catch (Exception e) {
								e.printStackTrace();
								return null;
							}
						}
					});
			return video;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
