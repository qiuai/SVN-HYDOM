package com.hydom.parse.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonData {

	private int type;
	private String roomid;// 房间id
	private String roomname;// 房间名
	private String roomscroe;// 房间评分
	private String userid;// 用户id
	private String nickname;// 用户昵称
	private Boolean isteacher;
	private Integer roommode; // 房间模式，1为直播，0为录播
	private Boolean microphoneenable;// 麦克风是否可用
	private Boolean screenshareenable;// 屏幕共享是否可用
	private String teacherid;

	private String videoid; // 视频文件ID
	private String videoname; // 视频文件名称

	private String livestream;// 直播流名
	private String streamname; // 流名
	private Integer streammode; // 流模式，1为桌面分享申请，0为语音申请。
	private Boolean isallow;// true或false

	private String content; // 聊天内容
	private String fileid;// 图片ID
	private String fileurl;// 图片地址
	private String filename;// 文件名
	private String filetype;// 文件类型

	private Integer audiolength;// 音频时间

	private String roomlivestream;// 当前直播视频流名

	private Integer sex;//

	private Integer mode;// 模式，0为测试流，1为正式流

	private JsonUser teacher;
	private List<JsonUser> students;
	private List<JsonVideo> roomvideos;

	private String sendNickname; // 发送端用户昵称
	private String sendUserid; // 发送端用户id

	private String toNickname;// 接收端用户昵称
	private String toUserid;// 接收端用户id

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getTeacherid() {
		return teacherid;
	}

	public void setTeacherid(String teacherid) {
		this.teacherid = teacherid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getRoommode() {
		return roommode;
	}

	public void setRoommode(Integer roommode) {
		this.roommode = roommode;
	}

	public JsonUser getTeacher() {
		return teacher;
	}

	public void setTeacher(JsonUser teacher) {
		this.teacher = teacher;
	}

	public List<JsonUser> getStudents() {
		return students;
	}

	public void setStudents(List<JsonUser> students) {
		this.students = students;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFileid() {
		return fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	public Boolean getIsteacher() {
		return isteacher;
	}

	public void setIsteacher(Boolean isteacher) {
		this.isteacher = isteacher;
	}

	public Boolean getMicrophoneenable() {
		return microphoneenable;
	}

	public void setMicrophoneenable(Boolean microphoneenable) {
		this.microphoneenable = microphoneenable;
	}

	public Boolean getScreenshareenable() {
		return screenshareenable;
	}

	public void setScreenshareenable(Boolean screenshareenable) {
		this.screenshareenable = screenshareenable;
	}

	public String getLivestream() {
		return livestream;
	}

	public void setLivestream(String livestream) {
		this.livestream = livestream;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public String getStreamname() {
		return streamname;
	}

	public void setStreamname(String streamname) {
		this.streamname = streamname;
	}

	public Integer getStreammode() {
		return streammode;
	}

	public void setStreammode(Integer streammode) {
		this.streammode = streammode;
	}

	public Boolean getIsallow() {
		return isallow;
	}

	public void setIsallow(Boolean isallow) {
		this.isallow = isallow;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFiletype() {
		return filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	public String getRoomlivestream() {
		return roomlivestream;
	}

	public void setRoomlivestream(String roomlivestream) {
		this.roomlivestream = roomlivestream;
	}

	public List<JsonVideo> getRoomvideos() {
		return roomvideos;
	}

	public void setRoomvideos(List<JsonVideo> roomvideos) {
		this.roomvideos = roomvideos;
	}

	public String getRoomname() {
		return roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}

	public String getVideoid() {
		return videoid;
	}

	public void setVideoid(String videoid) {
		this.videoid = videoid;
	}

	public String getVideoname() {
		return videoname;
	}

	public void setVideoname(String videoname) {
		this.videoname = videoname;
	}

	public String getSendNickname() {
		return sendNickname;
	}

	public void setSendNickname(String sendNickname) {
		this.sendNickname = sendNickname;
	}

	public String getSendUserid() {
		return sendUserid;
	}

	public void setSendUserid(String sendUserid) {
		this.sendUserid = sendUserid;
	}

	public String getToNickname() {
		return toNickname;
	}

	public void setToNickname(String toNickname) {
		this.toNickname = toNickname;
	}

	public String getToUserid() {
		return toUserid;
	}

	public void setToUserid(String toUserid) {
		this.toUserid = toUserid;
	}

	public String getRoomscroe() {
		return roomscroe;
	}

	public void setRoomscroe(String roomscroe) {
		this.roomscroe = roomscroe;
	}

	public Integer getAudiolength() {
		return audiolength;
	}

	public void setAudiolength(Integer audiolength) {
		this.audiolength = audiolength;
	}

	public static void main(String[] args) {
		JsonData jsonData = new JsonData();
		jsonData.setNickname("ck");
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_NULL);
		mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
		try {
			String str = mapper.writeValueAsString(jsonData);
			System.out.println(str);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
