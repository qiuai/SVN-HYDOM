package org.red5.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.red5.logging.Red5LoggerFactory;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.api.scope.IScope;
import org.red5.server.api.service.IServiceCapableConnection;
import org.red5.server.api.stream.IStreamPublishSecurity;
import org.red5.server.stream.ClientBroadcastStream;
import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hydom.core.ebean.Room;
import com.hydom.core.ebean.User;
import com.hydom.core.service.RoomService;
import com.hydom.core.service.UserService;
import com.hydom.parse.json.JsonData;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class MyPublishSecurity implements IStreamPublishSecurity {

	private RoomService roomService;
	private UserService userService;

	private static Logger log = Red5LoggerFactory.getLogger(MyPublishSecurity.class, "vts");

	public MyPublishSecurity() {

	}

	public MyPublishSecurity(RoomService roomService, UserService userService) {
		this.roomService = roomService;
		this.userService = userService;
	}

	/**
	 * ʵ��Open Declaration org.red5.server.api.stream.IStreamPublishSecurity�ӿ�
	 * 
	 * Check if publishing a stream with the given name is allowed.
	 * 
	 * @param scope
	 *            Scope the stream is about to be published in.
	 * @param name
	 *            Name of the stream to publish.
	 * @param mode
	 *            Publishing mode.
	 * @return
	 */
	@Override
	public boolean isPublishAllowed(IScope scope, String streamName, String mode) {
		// 直播流名（roomid-teacherid-“live”-fileid-mode）//mode:1为续录，0为覆盖
		log.info("流发布 安全控制器-->scope:" + scope.getContextPath() + "###" + scope.getName() + "##" + scope.getType());
		log.info("流发布 安全控制器-->mode:" + mode);
		log.info("流发布 安全控制器-->streamname:" + streamName);
		String rid = streamName.substring(0, streamName.indexOf("-"));
		String uid = streamName.substring(streamName.indexOf("-") + 1, streamName.lastIndexOf("-"));
		if (streamName.contains("-live-")) { // 视频直播流
			uid = streamName.substring(streamName.indexOf("-") + 1, streamName.indexOf("-live-"));
		}
		log.info("通过流名得到房间ID:" + rid);
		log.info("通过流名得到用户ID:" + uid);
		Room room = roomService.findOne(rid);
		User user = userService.findOne(uid);
		IConnection localConn = Red5.getConnectionLocal();
		User currentUser = (User) localConn.getAttribute("user");
		Room currentRomm = (Room) localConn.getAttribute("room");
		if (currentUser != null) {
			log.info("currentUser.getid：" + currentUser.getId());
		}
		if (currentRomm != null) {
			log.info("currentRoom.getid：" + currentRomm.getId());
		}
		if (room != null && user != null) {
			log.info(room.getUserId());
			log.info("允许发布流：" + streamName);
			JsonData jsonData = new JsonData();
			jsonData.setType(304);
			jsonData.setUserid(uid);
			jsonData.setLivestream(streamName);
			jsonData.setMode(1);
			// streamRecord(rid, streamName, jsonData);
			return true;
		} else {
			log.info("禁止发布流：" + streamName);
			return false;
		}

	}

	private void streamRecord(String roomId, String streamName, JsonData jsonData) {
		try {
			/** 录制视频流 STRART **/
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
			String fileName = sdf.format(new Date());
			ClientBroadcastStream stream = (ClientBroadcastStream) new Application().getBroadcastStream(Red5
					.getConnectionLocal().getScope(), streamName);
			log.info("流发布安全 控制器中录制：filename:" + fileName);
			log.info("流发布安全 控制器中录制：stream:" + stream);
			log.info("流发布安全 控制器中录制：publis'name:" + stream.getPublishedName());
			stream.saveAs(roomId + "/" + fileName, true);
			/** 录制视频流 END **/
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_NULL);
			String responseData = mapper.writeValueAsString(jsonData);
			if (jsonData.getMode() == 0) {// 测试流，只通知流发布者
				IConnection localConn = Red5.getConnectionLocal();
				IServiceCapableConnection sc = (IServiceCapableConnection) localConn;
				sc.invoke("sendToClient", new Object[] { responseData });
			} else if (jsonData.getMode() == 1) {// 正式流：通知本房间内的所有客户端
				log.info("正式流：数据：" + responseData);
				for (IConnection conn : listUserInRoom(roomId)) {
					IServiceCapableConnection sc = (IServiceCapableConnection) conn;
					sc.invoke("sendToClient", new Object[] { responseData });
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void broad(JsonData jsonData, String roomid) {

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

	public static void main(String[] args) {
		String streamName = "efefef-121212-live-filex-1";
		String rid = streamName.substring(0, streamName.indexOf("-"));
		String uid = streamName.substring(streamName.indexOf("-") + 1, streamName.indexOf("-live-"));
		String fileId = streamName.substring(streamName.indexOf("-live-") + 6, streamName.lastIndexOf("-"));
		char model = streamName.charAt(streamName.length() - 1);
		char m = 0;
		System.out.println(m == 0);
		boolean isAppend = (m == 0 ? false : true);
		System.out.println(isAppend);
	}

}
