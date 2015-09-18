package org.red5.core;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.mina.core.buffer.IoBuffer;
import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.service.IServiceCapableConnection;
import org.red5.server.api.stream.IBroadcastStream;
import org.red5.server.api.stream.IStreamFilenameGenerator;
import org.red5.server.api.stream.IStreamListener;
import org.red5.server.api.stream.IStreamPacket;
import org.red5.server.api.stream.ISubscriberStream;
import org.red5.server.api.stream.ResourceExistException;
import org.red5.server.api.stream.ResourceNotFoundException;
import org.red5.server.net.rtmp.event.Notify;
import org.red5.server.net.rtmp.message.Constants;
import org.red5.server.stream.ClientBroadcastStream;
import org.red5.server.stream.RecordingListener;
import org.red5.server.stream.consumer.FileConsumer;
import org.red5.server.util.ScopeUtils;
import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hydom.core.ebean.Room;
import com.hydom.core.ebean.User;
import com.hydom.core.ebean.UserCourse;
import com.hydom.core.ebean.Video;
import com.hydom.core.service.RoomService;
import com.hydom.core.service.UserCourseService;
import com.hydom.core.service.UserService;
import com.hydom.core.service.VideoService;
import com.hydom.parse.json.JsonData;
import com.hydom.parse.json.JsonUser;
import com.hydom.parse.json.JsonVideo;
import com.sun.xml.internal.messaging.saaj.packaging.mime.Header;

public class Application extends MultiThreadedApplicationAdapter {
	private RoomService roomService;
	private UserService userService;
	private VideoService videoService;
	private UserCourseService userCourseService;

	protected static Logger log = Red5LoggerFactory.getLogger(Application.class, "vts");

	public void server(Object[] params) {
		for (Object obj : params) {
			log.info("Classzz:" + obj.getClass());
			log.info("parma:" + obj.toString());
		}
		try {
			String json = params[0].toString();
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL); // NULL属性不传递
			JsonData jsonData = mapper.readValue(json, JsonData.class);
			if (jsonData.getType() == 104) {// 进入房间 请求
				process_104(jsonData);
			} else if (jsonData.getType() == 105) {// 流发布 请求
				process_105(jsonData);
			} else if (jsonData.getType() == 106) {// 流录制 请求
				process_106(jsonData);
			} else if (jsonData.getType() == 107) {// 老师响应学生 请求
				process_107(jsonData);
			} else if (jsonData.getType() == 108) {// 学生操作申请 请求
				process_108(jsonData);
			} else if (jsonData.getType() == 109) {// 文本聊天 请求
				process_109(jsonData);
			} else if (jsonData.getType() == 110) {// 图片上传 请求
				process_110(jsonData);
			} else if (jsonData.getType() == 112) {// 语音录制 请求
				process_112(jsonData);
			} else if (jsonData.getType() == 113) {// 语音广播 请求
				process_113(jsonData);
			} else if (jsonData.getType() == 114) {// 图片上传完成 请求
				process_114(jsonData);
			} else if (jsonData.getType() == 115) {// 离开房间 请求
				process_115(jsonData);
			} else if (jsonData.getType() == 116) {// 图片上传完成 请求
				process_116(jsonData);
			} else if (jsonData.getType() == 117) {// 用户之间私聊 请求
				process_117(jsonData);
			} else if (jsonData.getType() == 118) {// 教师踢出学生
				process_118(jsonData);
			}
		} catch (Exception e) {
			log.warn(e.toString());
			e.printStackTrace();
		}
		// ISharedObject so = getSharedObject(scope, "sampleSO");
	}

	private void process_118(JsonData jsonData) {
		IConnection localConn = Red5.getConnectionLocal();
		Room room = (Room) localConn.getAttribute("room");
		User user = (User) localConn.getAttribute("user");
		if (room == null || !room.getUserId().equals(user.getId())) {
			log.info("无权踢出用户：" + room == null ? "room is null" : room.getId() + "--" + user == null ? "user is null"
					: user.getId());
			return;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		jsonData.setType(318);
		try {
			String responseData = mapper.writeValueAsString(jsonData);
			log.info("踢出通知所有用户 响应的内容：" + responseData);
			for (IConnection conn : listUserInRoom(room.getId())) {
				IServiceCapableConnection sc = (IServiceCapableConnection) conn;
				sc.invoke("sendToClient", new Object[] { responseData });
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			log.warn(e.toString());
		}
	}

	private void process_117(JsonData jsonData) {
		try {
			IConnection localConn = Red5.getConnectionLocal();

			/** 私聊响应数据 **/
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			jsonData.setType(317);
			String responseData = mapper.writeValueAsString(jsonData);

			Set<IConnection> conns = localConn.getScope().getClientConnections();
			for (IConnection conn : conns) {
				User user = (User) conn.getAttribute("user");
				if (user != null && user.getId().equals(jsonData.getToUserid())) {// 通知接受的用户
					IServiceCapableConnection sc = (IServiceCapableConnection) conn;
					log.info("私聊通知接受端 数据 ：" + responseData);
					sc.invoke("sendToClient", new Object[] { responseData });
					break;
				}
			}
			IServiceCapableConnection sc = (IServiceCapableConnection) localConn;
			log.info("私聊通知发送端 数据 ：" + responseData);
			sc.invoke("sendToClient", new Object[] { responseData });

		} catch (Exception e) {
			log.warn(e.toString());
			e.printStackTrace();
		}
	}

	private void process_116(JsonData jsonData) {
		try {
			IConnection localConn = Red5.getConnectionLocal();
			Set<IConnection> conns = localConn.getScope().getClientConnections();
			String roomId = jsonData.getRoomid();
			for (IConnection conn : conns) {
				Room room = (Room) conn.getAttribute("room");
				if (room != null && room.getId().equals(roomId)) {
					User user = (User) conn.getAttribute("user");
					if (user != null && user.getIsteacher() && room.getUserId().equals(user.getId())) {// 通知老师
						IServiceCapableConnection sc = (IServiceCapableConnection) conn;
						ObjectMapper mapper = new ObjectMapper();
						mapper.setSerializationInclusion(Include.NON_NULL);
						jsonData.setType(314);
						String responseData = mapper.writeValueAsString(jsonData);
						log.info("学生取消操作请求，只通知老师 响应数据 ：" + responseData);
						sc.invoke("sendToClient", new Object[] { responseData });
						break;
					}
				}
			}
		} catch (Exception e) {
			log.warn(e.toString());
			e.printStackTrace();
		}
	}

	private void process_115(JsonData jsonData) {
		try {
			jsonData.setType(313);
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			String responseData = mapper.writeValueAsString(jsonData);
			for (IConnection conn : listUserInRoom(jsonData.getRoomid())) {
				IServiceCapableConnection sc = (IServiceCapableConnection) conn;
				sc.invoke("sendToClient", new Object[] { responseData });
			}
		} catch (Exception e) {
			log.warn(e.toString());
			e.printStackTrace();
		}
	}

	private void process_113(JsonData jsonData) {
		IConnection localConn = Red5.getConnectionLocal();
		Room room = (Room) localConn.getAttribute("room");
		if (room == null) {
			return;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		jsonData.setType(312);
		try {
			String responseData = mapper.writeValueAsString(jsonData);
			log.info("语音聊天通知 响应的内容：" + responseData);
			for (IConnection conn : listUserInRoom(room.getId())) {
				IServiceCapableConnection sc = (IServiceCapableConnection) conn;
				sc.invoke("sendToClient", new Object[] { responseData });
			}
		} catch (JsonProcessingException e) {
			log.warn(e.toString());
			e.printStackTrace();
		}
	}

	private void process_112(JsonData jsonData) {
		IConnection localConn = Red5.getConnectionLocal();
		Room room = (Room) localConn.getAttribute("room");
		User user = (User) localConn.getAttribute("user");
		if (room == null && user == null) {
			return;
		}
		try {
			/** 录制音频流 代码 START **/
			String fileName = user.getId() + "-" + Long.toHexString(System.currentTimeMillis()) + "";
			// String fileName = System.currentTimeMillis() + "";
			log.info("jsonData.getStreamName：" + jsonData.getStreamname());
			ClientBroadcastStream stream = (ClientBroadcastStream) this.getBroadcastStream(Red5.getConnectionLocal()
					.getScope(), jsonData.getStreamname());
			log.info("filename:" + fileName);
			log.info("stream:" + stream);
			log.info("publis'name:" + stream.getPublishedName());
			stream.saveAs(room.getId() + "/" + fileName, false);
			/** 录制音频流 代码 END **/

			/** 通知用户已开始录制流 **/
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			jsonData.setType(311);
			jsonData.setFilename(fileName);
			String responseData = mapper.writeValueAsString(jsonData);
			log.info("用户录制音频流 响应代码 ：" + responseData);
			IServiceCapableConnection sc = (IServiceCapableConnection) localConn;
			sc.invoke("sendToClient", new Object[] { responseData });
		} catch (Exception e) {
			log.warn(e.toString());
			e.printStackTrace();
		}
	}

	private void process_114(JsonData jsonData) {
		IConnection localConn = Red5.getConnectionLocal();
		Room room = (Room) localConn.getAttribute("room");
		if (room == null) {
			return;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		jsonData.setType(310);
		try {
			String responseData = mapper.writeValueAsString(jsonData);
			log.info("图片聊天地址通知 响应的内容：" + responseData);
			for (IConnection conn : listUserInRoom(room.getId())) {
				IServiceCapableConnection sc = (IServiceCapableConnection) conn;
				sc.invoke("sendToClient", new Object[] { responseData });
			}
		} catch (JsonProcessingException e) {
			log.warn(e.toString());
			e.printStackTrace();
		}

	}

	private void process_110(JsonData jsonData) {
		IConnection localConn = Red5.getConnectionLocal();
		Room room = (Room) localConn.getAttribute("room");
		if (room == null) {
			return;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		jsonData.setType(309);
		try {
			String responseData = mapper.writeValueAsString(jsonData);
			log.info("图片聊天响应的内容：" + responseData);
			for (IConnection conn : listUserInRoom(room.getId())) {
				IServiceCapableConnection sc = (IServiceCapableConnection) conn;
				sc.invoke("sendToClient", new Object[] { responseData });
			}
		} catch (JsonProcessingException e) {
			log.warn(e.toString());
			e.printStackTrace();
		}

	}

	private void process_109(JsonData jsonData) {
		IConnection localConn = Red5.getConnectionLocal();
		Room room = (Room) localConn.getAttribute("room");
		if (room == null) {
			return;
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		jsonData.setType(308);
		try {
			String responseData = mapper.writeValueAsString(jsonData);
			log.info("文本聊天响应的内容：" + responseData);
			for (IConnection conn : listUserInRoom(room.getId())) {
				IServiceCapableConnection sc = (IServiceCapableConnection) conn;
				sc.invoke("sendToClient", new Object[] { responseData });
			}
		} catch (JsonProcessingException e) {
			log.warn(e.toString());
			e.printStackTrace();
		}

	}

	/**
	 * 学生操作申请<br>
	 * 只通知老师
	 * 
	 * @param jsonData
	 */
	private void process_108(JsonData jsonData) {
		String roomId = jsonData.getRoomid();
		IConnection localConn = Red5.getConnectionLocal();
		Room currentRoom = (Room) localConn.getAttribute("room");
		if (roomId == null || currentRoom == null || !roomId.equals(currentRoom.getId())) {// roomid为null,或者用户不在roomid指定的房间
			log.info("学生操作申请错误：roomid:" + roomId + " currentRomm:" + currentRoom);
			if (currentRoom != null) {
				log.info("学生操作申请错误：current roomid:" + currentRoom.getId());
			}
			return;
		}
		Set<IConnection> conns = localConn.getScope().getClientConnections();
		for (IConnection conn : conns) {
			Room room = (Room) conn.getAttribute("room");
			if (room != null && room.getId().equals(roomId)) {
				User user = (User) conn.getAttribute("user");
				if (user != null && user.getIsteacher() && room.getUserId().equals(user.getId())) {// 通知老师
					try {
						IServiceCapableConnection sc = (IServiceCapableConnection) conn;
						ObjectMapper mapper = new ObjectMapper();
						mapper.setSerializationInclusion(Include.NON_NULL);
						jsonData.setType(306);
						String responseData = mapper.writeValueAsString(jsonData);
						log.info("学生操作请求，通知老师响应数据 ：" + responseData);
						sc.invoke("sendToClient", new Object[] { responseData });
						break;
					} catch (JsonProcessingException e) {
						log.warn(e.toString());
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * 老师响应学生的申请 <br>
	 * 只通知对应的学生
	 * 
	 * @param jsonData
	 */
	private void process_107(JsonData jsonData) {
		String roomId = jsonData.getRoomid();
		IConnection localConn = Red5.getConnectionLocal();
		Room currentRoom = (Room) localConn.getAttribute("room");
		if (roomId == null || currentRoom == null || !roomId.equals(currentRoom.getId())) {// roomid为null,或者用户不在roomid指定的房间
			log.info("老师响应学生请求错误：roomid:" + roomId + " currentRomm:" + currentRoom);
			if (currentRoom != null) {
				log.info("老师响应学生请求错误：current roomid:" + currentRoom.getId());
			}
			return;
		}
		Set<IConnection> conns = localConn.getScope().getClientConnections();
		List<IConnection> list = new ArrayList<IConnection>();
		for (IConnection conn : conns) {
			Room room = (Room) conn.getAttribute("room");
			if (room != null && room.getId().equals(roomId)) {
				User user = (User) conn.getAttribute("user");
				if (user != null && user.getId().equals(jsonData.getUserid())) {// 通知对应的学生
					try {
						IServiceCapableConnection sc = (IServiceCapableConnection) conn;
						ObjectMapper mapper = new ObjectMapper();
						mapper.setSerializationInclusion(Include.NON_NULL);
						jsonData.setType(307);
						String responseData = mapper.writeValueAsString(jsonData);
						log.info("老师响应学生请求，通知对应的学生。数据 ：" + responseData);
						sc.invoke("sendToClient", new Object[] { responseData });
						break;
					} catch (JsonProcessingException e) {
						log.warn(e.toString());
						e.printStackTrace();
					}
				}
			}
		}

	}

	private void process_106(JsonData jsonData) {
		IScope scope = Red5.getConnectionLocal().getScope();
		/*
		 * type:106 userid：用户id streamname：流名
		 */
		ClientBroadcastStream stream = (ClientBroadcastStream) this.getBroadcastStream(scope, jsonData.getStreamname());
		// stream.saveAs(file, false);

	}

	/**
	 * 直播流发布 请求 <br>
	 * ：暂时不用 测试模式：通知发布者(通常是老师) 正式模式：通知房间内的所有学生
	 * 
	 * @param jsonData
	 */
	@Deprecated
	private void process_105(JsonData jsonData) {
		String streamName = jsonData.getLivestream();

		String roomId = streamName.substring(0, streamName.indexOf("-"));
		String uid = streamName.substring(streamName.indexOf("-") + 1, streamName.lastIndexOf("-"));
		if (streamName.contains("-live-")) { // 视频直播流
			uid = streamName.substring(streamName.indexOf("-") + 1, streamName.indexOf("-live-"));
		}
		Room room = roomService.findOne(roomId);
		if (room == null) {
			log.info("流名：" + jsonData.getLivestream() + "错误，查找不到对应的房间：ROOMID=" + roomId);
			return;
		}
		String userId = jsonData.getUserid();
		if (userId == null || !userId.equals(room.getUserId())) {
			log.info("当前用户ID:" + userId + " 房间不属于此用户：Romm-userId:" + room.getUserId() + "");
			return;
		}

		/** 录制视频流 STRART **/
		try {
			Thread.sleep(5000);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String fileName = sdf.format(new Date());
			// String fileName = System.currentTimeMillis() + "";
			log.info("jsonData.getLivestream：" + jsonData.getLivestream());
			IScope sc = Red5.getConnectionLocal().getScope();
			log.info("----->scope：" + sc.getContextPath() + "##" + sc.getPath() + "##" + sc.getName());
			ClientBroadcastStream stream = (ClientBroadcastStream) this.getBroadcastStream(Red5.getConnectionLocal()
					.getScope(), jsonData.getLivestream());
			log.info("====" + roomId + "/" + jsonData.getLivestream());
			log.info("filename:" + fileName);
			log.info("stream:" + stream);
			// log.info("stream:" + stream.getBroadcastStreamPublishName()+"##"+stream.getScope().getContextPath());
			log.info("publis'name:" + stream.getPublishedName());
			stream.saveAs(roomId + "/" + fileName, true);
		} catch (Exception e) {
			log.warn(e.toString());
			e.printStackTrace();
		}
		/** 录制视频流 END **/

		/** 生成要发送的数据 ：直播流进行通知 **/
		if (streamName.contains("-live-")) {// 直播正式流：通知本房间内的所有客户端
			try {
				jsonData.setType(304);
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				String responseData = mapper.writeValueAsString(jsonData);
				for (IConnection conn : listUserInRoom(roomId)) {
					IServiceCapableConnection sc = (IServiceCapableConnection) conn;
					sc.invoke("sendToClient", new Object[] { responseData });
				}
			} catch (JsonProcessingException e) {
				log.warn(e.toString());
				e.printStackTrace();
			}
		}

	}

	private void process_104(JsonData jsonData) {
		IScope scope = Red5.getConnectionLocal().getScope();
		IConnection localConn = Red5.getConnectionLocal();
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		/** STEP 1：存储当前连接用户的数据 **/
		log.info("userId:" + jsonData.getUserid());
		log.info("roomID:" + jsonData.getRoomid());
		User currentUser = userService.findOne(jsonData.getUserid());
		Room currentRoom = roomService.findOne(jsonData.getRoomid());
		if (currentUser == null || currentRoom == null) {// 用户不能进入房间
			log.info("进入房间失败：" + jsonData.getUserid() + "##" + jsonData.getRoomid());
			return;
		}
		// -更新或保存用户观看历史记录：并更新用户对应的当前课程状态
		userCourseService.saveOrUpdate(currentRoom.getId(), currentUser.getId());
		log.info("当前连接用户进入房间成功---------->");
		currentUser.setScreenshareenable(jsonData.getScreenshareenable());
		localConn.setAttribute("user", currentUser);
		localConn.setAttribute("room", currentRoom);

		/** STEP 2：通知同一房间内的所有客户端 **/
		List<User> students = new ArrayList<User>();
		User teacher = null;
		Set<IConnection> conns = scope.getClientConnections();
		for (IConnection conn : conns) {
			Room room = (Room) conn.getAttribute("room");
			/** 前置条件：必须属于同一个房间的连接 **/
			if (room != null && currentRoom != null && !room.getId().equals(currentRoom.getId())) {
				continue;
			}

			log.info(conn.getSessionId() + "--LOCAL-SID:" + localConn.getSessionId());
			User user = (User) conn.getAttribute("user");

			/** 为当前连接用户准备响应数据 START **/
			if (user != null) {
				log.info("sessinId:" + conn.getSessionId() + "##" + localConn.getSessionId());
				if (user.getId().equals(jsonData.getUserid()) && !conn.getSessionId().equals(localConn.getSessionId())) { // T掉相同帐号用户

					JsonData responseJsonData = new JsonData();
					responseJsonData.setType(315);
					responseJsonData.setUserid(user.getId());
					responseJsonData.setNickname(user.getNickname());
					try {
						String responseData = mapper.writeValueAsString(responseJsonData);
						IServiceCapableConnection sc = (IServiceCapableConnection) conn;
						log.info("T掉多端登录用户：" + responseData);
						// sc.close();
						sc.invoke("sendToClient", new Object[] { responseData });
					} catch (JsonProcessingException e) {
						log.warn(e.toString());
						e.printStackTrace();
					}
				} else {
					log.info("User is not null");
					if (user.getIsteacher() && currentRoom.getUserId().equals(user.getId())) {
						log.info("设置为房间的老师：");
						teacher = user;
					} else {
						log.info("设置为房间的学生：");
						students.add(user);
					}
				}
			}
			/** 为当前连接用户准备响应数据 END **/
			if (conn.getSessionId().equals(localConn.getSessionId())) {
				continue;
			}
			if (conn instanceof IServiceCapableConnection) {
				log.info("is IServiceCapableConnection");
				if (user != null) {
					JsonData responseJsonData = new JsonData();
					responseJsonData.setType(302);
					responseJsonData.setNickname(currentUser.getNickname());
					responseJsonData.setUserid(currentUser.getId());
					responseJsonData.setTeacherid(currentRoom.getUserId());
					responseJsonData.setIsteacher(currentUser.getIsteacher());
					try {
						String responseData = mapper.writeValueAsString(responseJsonData);
						log.info("响应数据：" + responseData);
						IServiceCapableConnection sc = (IServiceCapableConnection) conn;
						sc.invoke("sendToClient", new Object[] { responseData });
					} catch (JsonProcessingException e) {
						log.warn(e.toString()); 
						e.printStackTrace();
					}
				}
			}
		}
		/** STEP 3：通知当前连接客户端 **/
		JsonData responseCurrentJsonData = new JsonData();
		responseCurrentJsonData.setType(303);
		responseCurrentJsonData.setRoommode(currentRoom.getTypes());
		responseCurrentJsonData.setRoomid(currentRoom.getId());
		responseCurrentJsonData.setRoomname(currentRoom.getName());
		responseCurrentJsonData.setRoomscroe(roomService.avgScore(currentRoom.getId()) + "");
		responseCurrentJsonData.setRoomlivestream(currentRoom.getNowFlow());
		responseCurrentJsonData.setTeacherid(currentRoom.getUserId());
		log.info("TEST--->");

		/** 获取视频列表 START **/
		List<JsonVideo> roomvideos = new ArrayList<JsonVideo>();
		for (Video video : videoService.listByRoomId(jsonData.getRoomid())) {
			JsonVideo jv = new JsonVideo();
			jv.setId(video.getId());
			jv.setName(video.getName());
			String currentStreamName = currentRoom.getNowFlow();
			if (currentStreamName != null) {
				jv.setIslive(currentStreamName.contains(jv.getId()) ? true : false);
			} else {
				jv.setIslive(false);
			}
			// jv.setCreateDate(video.getCreateDate());
			roomvideos.add(jv);
		}
		responseCurrentJsonData.setRoomvideos(roomvideos);
		/** 获取视频列表 END **/

		log.info("teacher:" + teacher);
		if (teacher != null) {
			JsonUser teacherUser = new JsonUser();
			teacherUser.setUserid(teacher.getId());
			teacherUser.setNickname(teacher.getNickname());
			responseCurrentJsonData.setTeacher(teacherUser);
		}
		log.info("students SiZE：" + students.size());
		if (students.size() > 0) {
			List<JsonUser> jsonUsers = new ArrayList<JsonUser>();
			for (User student : students) {
				JsonUser jsonuser = new JsonUser();
				jsonuser.setUserid(student.getId());
				jsonuser.setNickname(student.getNickname());
				log.info("jsonUser:" + jsonUsers + "--" + student);
				log.info("jsonUser:" + student.getMicrophoneenable());
				jsonuser.setMicrophoneenable(student.getMicrophoneenable());
				jsonuser.setScreenshareenable(student.getScreenshareenable());
				jsonUsers.add(jsonuser);
			}
			responseCurrentJsonData.setStudents(jsonUsers);
		}
		try {
			String responseData = mapper.writeValueAsString(responseCurrentJsonData);
			log.info("响应数据给当前连接用户：" + responseData);
			IServiceCapableConnection sc = (IServiceCapableConnection) localConn;
			sc.invoke("sendToClient", new Object[] { responseData });
		} catch (JsonProcessingException e) {
			log.warn(e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * 获取指定房间内的所有连接
	 * 
	 * @param roomid
	 * @return
	 */
	private List<IConnection> listUserInRoom(String roomId) {
		Set<IConnection> conns = Red5.getConnectionLocal().getScope().getClientConnections();
		List<IConnection> list = new ArrayList<IConnection>();
		for (IConnection conn : conns) {
			Room room = (Room) conn.getAttribute("room");
			if (room != null && room.getId().equals(roomId)) {
				list.add(conn);
			}
		}
		return list;
	}

	@Override
	public boolean appConnect(IConnection conn, Object[] params) {
		for (Object obj : params) {
			log.info("appConnect--> Classzz:" + obj.getClass());
			log.info("appConnect--> parma:" + obj.toString());
		}
		return true;
	}

	@Override
	public void streamPublishStart(IBroadcastStream stream) {
		/** 兼容H264视频 ：代码开始 */
		/*
		 * stream.addStreamListener(new IStreamListener() { protected boolean bFirst = true;
		 * 
		 * @Override public void packetReceived(IBroadcastStream iBroadcastStream, IStreamPacket packet) { IoBuffer in = packet.getData(); if (packet.getDataType() == 0x09) { byte[] data = new
		 * byte[in.limit()]; IoBuffer ioBuffer = in.get(data); byte[] foredata = { 0, 0, 0, 1 }; // ioBuffer3.put(data); // buflimit3 += in.limit(); if (bFirst) { // AVCsequence header
		 * ioBuffer.put(foredata); // 获取sps int spsnum = data[10] & 0x1f; int number_sps = 11; int count_sps = 1; while (count_sps <= spsnum) { int spslen = (data[number_sps] & 0x000000FF) << 8 |
		 * (data[number_sps + 1] & 0x000000FF); number_sps += 2; ioBuffer.put(data, number_sps, spslen); ioBuffer.put(foredata); number_sps += spslen; count_sps++; } // 获取pps int ppsnum =
		 * data[number_sps] & 0x1f; int number_pps = number_sps + 1; int count_pps = 1; while (count_pps <= ppsnum) { int ppslen = (data[number_pps] & 0x000000FF) << 8 | data[number_pps + 1] &
		 * 0x000000FF; number_pps += 2; ioBuffer.put(data, number_pps, ppslen); ioBuffer.put(foredata); number_pps += ppslen; count_pps++; } bFirst = false;
		 * 
		 * } else { // AVCNALU int len = 0; int num = 5; ioBuffer.put(foredata); while (num < data.length) { len = (data[num] & 0x000000FF) << 24 | (data[num + 1] & 0x000000FF) << 16 | (data[num + 2]
		 * & 0x000000FF) << 8 | data[num + 3] & 0x000000FF; num = num + 4; ioBuffer.put(data, num, len); ioBuffer.put(foredata); num = num + len; } } } else if (packet.getDataType() == 0x08) { //
		 * 这存储处理音频数据 Audio data } } });
		 */
		/** 兼容H264视频 ：代码END */

		log.info("流发布开始：：：：-------->");
		String streamName = stream.getPublishedName();

		log.info("streamName:" + streamName);
		String rid = streamName.substring(0, streamName.indexOf("-"));
		String uid = streamName.substring(streamName.indexOf("-") + 1, streamName.lastIndexOf("-"));
		if (streamName.contains("-live-")) { // 视频直播流
			uid = streamName.substring(streamName.indexOf("-") + 1, streamName.indexOf("-live-"));
		}
		log.info("通过流名得到房间ID:" + rid);
		log.info("通过流名得到用户ID:" + uid);

		try {
			if (streamName.contains("-live-")) {// 录制视频流
				/** 录制视频流 STRART **/
				String fileId = streamName.substring(streamName.indexOf("-live-") + 6, streamName.lastIndexOf("-"));
				int model = Integer.parseInt(streamName.charAt(streamName.length() - 1) + "");
				boolean isAppend = (model == 0 ? false : true);
				log.info("fileId：" + fileId);
				log.info("stream：" + stream);
				log.info("model isAppend：" + model + "###" + isAppend);
				stream.saveAs(rid + "/" + fileId, isAppend);
				// stream.saveAs(System.currentTimeMillis() + "", isAppend);

				log.info("视频流编码格式：" + stream.getCodecInfo().getVideoCodecName());
				log.info("音频流编码格式：" + stream.getCodecInfo().getAudioCodecName());
				int resultLive = roomService.setCurrentLiveStream(rid, streamName);
				int resultRoom = roomService.setRoomStatus(rid, 1);
				log.info("更新房间当前直播流名结果：" + resultLive);
				log.info("更新房间状态结果：" + resultRoom);
				/** 录制视频流 END **/

				Video video = videoService.findOne(fileId);
				/** 生成要发送的数据 **/
				JsonData jsonData = new JsonData();
				jsonData.setType(304);
				jsonData.setUserid(uid);
				jsonData.setLivestream(streamName);
				jsonData.setVideoid(video.getId());
				jsonData.setVideoname(video.getName());
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				String responseData = mapper.writeValueAsString(jsonData);
				for (IConnection conn : listUserInRoom(rid)) {
					IServiceCapableConnection sc = (IServiceCapableConnection) conn;
					sc.invoke("sendToClient", new Object[] { responseData });
				}
			}
		} catch (Exception e) {
			log.warn(e.toString());
			e.printStackTrace();
		}

		super.streamPublishStart(stream);
	}

	@Override
	public void streamRecordStart(IBroadcastStream stream) {
		log.info("开始录制流，保存的文件名：" + stream.getSaveFilename());
		Notify nf = stream.getMetaData();
		Map<String, Object> maps = nf.getConnectionParams();
		log.info("获取map size:" + (maps != null ? maps.size() : 0));
		for (String key : maps.keySet()) {
			log.info("###" + key + "==" + maps.get(key));
		}
		super.streamRecordStart(stream);
	}

	@Override
	public void streamRecordStop(IBroadcastStream stream) {
		log.info("直播流停止录制：" + stream.getPublishedName());
		String streamName = stream.getPublishedName();
		String rid = streamName.substring(0, streamName.indexOf("-"));
		int resultLive = roomService.setCurrentLiveStream(rid, null);
		int resultRoom = roomService.setRoomStatus(rid, 0);
		log.info("停止发布，更新房间当前直播流名结果：" + resultLive);
		log.info("停止发布，更新房间状态结果：" + resultRoom);
		try {
			if (streamName.contains("-live-")) {// 录制视频流
				String videoId = streamName.substring(streamName.indexOf("-live-") + 6, streamName.lastIndexOf("-"));
				JsonData jsonData = new JsonData();
				jsonData.setType(316);
				jsonData.setVideoid(videoId);
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				String responseData = mapper.writeValueAsString(jsonData);
				log.info("停止发布，通知所有用户数据：" + responseData);
				for (IConnection conn : listUserInRoom(rid)) {
					IServiceCapableConnection sc = (IServiceCapableConnection) conn;
					sc.invoke("sendToClient", new Object[] { responseData });
				}
			}
		} catch (JsonProcessingException e) {
			log.warn(e.toString());
			e.printStackTrace();
		}
		super.streamRecordStop(stream);
	}

	@Override
	public boolean connect(IConnection conn, IScope scope, Object[] params) {
		return true;
	}

	@Override
	public void disconnect(IConnection conn, IScope scope) {
		try {
			IConnection localConn = Red5.getConnectionLocal();
			User user = (User) localConn.getAttribute("user");
			Room room = (Room) localConn.getAttribute("room");
			// 删除用户当前课程状态：通常发生在用户离开房间后
			// userCourseService.deleteCurrentRoomState(user.getId());
			if (user != null && room != null) {
				log.info("监听到连接断开--user info:" + user.getId() + "##" + user.getNickname() + "##" + user.getIsteacher());
				log.info("监听到连接断开--room info:" + room.getId() + "##" + room.getUserId());
				JsonData jsonData = new JsonData();
				jsonData.setType(313);
				jsonData.setUserid(user.getId());
				jsonData.setNickname(user.getNickname());
				jsonData.setIsteacher(user.getIsteacher());
				ObjectMapper mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				String responseData = mapper.writeValueAsString(jsonData);
				for (IConnection iconn : listUserInRoom(room.getId())) {
					log.info("用户强制或主动 断开连接：" + responseData);
					User userPer = (User) iconn.getAttribute("user");
					if (!user.getId().equals(userPer.getId())) {// 不通知相同ID的用户端
						IServiceCapableConnection sc = (IServiceCapableConnection) iconn;
						sc.invoke("sendToClient", new Object[] { responseData });
					}
				}
			}
		} catch (Exception e) {
			log.warn(e.toString());
			e.printStackTrace();
		}
		super.disconnect(conn, scope);
	}

	@Override
	public synchronized boolean start(IScope scope) {
		log.info("注册流发布检测组件----");
		registerStreamPublishSecurity(new MyPublishSecurity(roomService, userService));
		return super.start(scope);
	}

	/********************** 业务Bean START **************************/
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}

	public void setRoomService(RoomService roomService) {
		this.roomService = roomService;
	}

	public void setUserCourseService(UserCourseService userCourseService) {
		this.userCourseService = userCourseService;
	}

}
