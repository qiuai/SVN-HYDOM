package com.hydom.vt.api;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hydom.common.AbstractController;
import com.hydom.common.bean.Pager;
import com.hydom.common.service.CommentService;
import com.hydom.common.service.CourseService;
import com.hydom.common.service.CourseTimeService;
import com.hydom.common.service.UserCourseService;
import com.hydom.common.service.UserService;
import com.hydom.common.service.VideoService;
import com.hydom.util.DateTimeHelper;
import com.hydom.util.HydomUtil;
import com.hydom.vt.entity.Comment;
import com.hydom.vt.entity.Course;
import com.hydom.vt.entity.CourseTime;
import com.hydom.vt.entity.User;
import com.hydom.vt.entity.UserCourse;
import com.hydom.vt.entity.Video;
import com.hydom.vt.util.Constant;
import com.hydom.vt.util.session.SessionListener;

/**
 * 用户登录处理类.
 * 
 * @author rl
 * @version 1.0.0 2015.5.28 新建
 */
@Controller
@RequestMapping("/api")
public class ApiCommonController extends AbstractController {
	/** 类型 */
	protected final static String TYPE = "type";
	/** 错误提示 */
	protected final static String ERROR = "error";
	@Resource
	private UserService userService;
	@Resource
	private CourseService courseService;
	@Resource
	private VideoService videoService;
	@Resource
	private UserCourseService userCourseService;
	@Resource
	private CourseTimeService courseTimeService;
	@Resource
	private CommentService commentService;
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3919253998909066496L;

	// 总访问接口
	@ResponseBody
	@RequestMapping("/common.do")
	public void common(String httpdata) {
		JSONObject result = new JSONObject();
		try {
			JSONObject data = JSONObject.fromObject(httpdata);
			String type = data.getString("type");
			switch (type) {
			case Constant.TYPE_201: // 登录
				result = login(data);
				break;
			case Constant.TYPE_202: // 注册
				result = register(data);

				break;
			case Constant.TYPE_203: // 验证用户名

				result = checkUserName(data);

				break;
			case Constant.TYPE_204: // 创建房间

				result = createCourse(data);

				break;
			case Constant.TYPE_205: // 我的课程列表（教师）

				result = getCourseListByTea(data, 0);

				break;
			case Constant.TYPE_206: // 我的视频列表

				result = getVideoList(data);

				break;

			case Constant.TYPE_207: // 即将开课列表
				result = getCourseListByStu(data, 0);
				break;
			case Constant.TYPE_208: // 关注老师
				result = focusTea(data);
				break;
			case Constant.TYPE_209: // 关注老师的列表
				result = focusTeaList(data);
				break;
			case Constant.TYPE_211: // 观看历史记录列表
				result = videoHistoryList(data);
				break;
			case Constant.TYPE_213: // 老师个人中心；
				result = getTeaCenter(data);
				break;
			case Constant.TYPE_214: // 学生个人中心
				result = getStuCenter(data);
				break;
			case Constant.TYPE_215: // 学生端课程列表
				result = getCourseListByStu(data, 1);
				break;
			case Constant.TYPE_216: // 学生端推荐课程列表（热门课程）；
				result = getCourseListByStu(data, 2);
				break;
			case Constant.TYPE_217: // 热门课程 （教师）；
				result = getCourseListByTea(data, 1);
				break;
			case Constant.TYPE_218: // 关注课程
				result = focusCourse(data);
				break;
			case Constant.TYPE_219: // 关注课程列表
				result = focusCourseList(data);
				break;
			case Constant.TYPE_221: // 取消关注老师
				result = cancelfocusTeacher(data);
				break;
			case Constant.TYPE_222: // 取消关注课程
				result = cancelfocusCourse(data);
				break;
			case Constant.TYPE_223: // 修改学生信息
				result = editUserInfo(data);
				break;
			case Constant.TYPE_224: // 修改老师信息
				result = editUserInfo(data);
				break;
			case Constant.TYPE_225: // 创建视频（或者修改）
				result = saveDideo(data);
				break;
			case Constant.TYPE_226: // 某个课程的视频列表
				result = getVideoList(data);
				break;
			case Constant.TYPE_227: // 删除视频
				result = delVideo(data);
				break;
			case Constant.TYPE_228: // 课程详情
				// 630A10D33E4F47538E04{"type":228,"username":"chen","password":"1","id":"630A10D33E4F47538E04","startDate":1433494485000,"name":"课程2","score":0,"money":0,"nickname":"陈老师","mainContent":"","course":"","courseNum":0,"isFocus":false}
				result = courseInfo(data);
				break;
			case Constant.TYPE_229: // 修改课程信息
				result = createCourse(data);
				break;

			case Constant.TYPE_230: // 评价课程
				result = commentCourse(data);
				break;
			default:
				result = getErr("没有匹配类型");
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = getErr("修改课程信息失败");
		}
		System.out.println(result);
		ajaxJSONObject(result);
	}

	// 评价课程
	public JSONObject commentCourse(JSONObject data) {
		JSONObject result = data;

		String courseId = data.getString("courseId");
		if (StringUtils.isEmpty(courseId)) {
			return getErr("课程为空");
		}

		if (commentService.isExist(new String[] { "user.id", "course.id" },
				new Object[] { getUser().getId(), courseId })) {
			return getErr("不能重复评论");
		}

		Comment comment = (Comment) JSONObject.toBean(data, Comment.class);
		comment.setUser(getUser());
		comment.setCourse(new Course(data.getString("courseId")));
		String commentId = commentService.save(comment);
		result.put("commentId", commentId);
		result.put("score", courseService.getCourseScore(data.getString("courseId")));
		return result;
	}

	// 课程详情
	public JSONObject courseInfo(JSONObject data) {
		Course course = courseService.load(data.getString("id"));
		data.put("image", StringUtils.isNotEmpty(course.getImage()) ? course.getImage() : "");
		data.put("types", course.getTypes());
		data.put("startDate", course.getStartDate().getTime());
		data.put("endDate", course.getEndDate().getTime());
		data.put("time", course.getTime());
		data.put("maxStuNum", course.getMaxStuNum());
		data.put("remark", course.getRemark());
		data.put("name", course.getName());
		data.put("score", courseService.getCourseScore(course.getId()));
		data.put("money", course.getMoney());
		data.put("coursePeriodic", course.getCoursePeriodic());
		if (getUser().getIsteacher()) {
			data.put("password", course.getPassword());
		} else {
			// 房间人数
			data.put("courseNum", userCourseService.getcount("", course.getId(), 2));
			data.put(
					"isFocus",
					userCourseService.isExist(new String[] { "types", "user.id", "course.id" }, new Object[] { 0,
							getUser().getId(), course.getId() }));
		}
		data.put("nickname", course.getUser().getNickname());
		data.put("username", course.getUser().getUsername());
		data.put("mainContent", course.getMainContent());
		data.put("timeLength", course.getTimeLength());

		return data;
	}

	public JSONObject delVideo(JSONObject data) {

		JSONObject result = data;
		String idstring = data.getString("ids");
		String ids[] = idstring.split(",");
		for (String id : ids) {
			Video tem = videoService.load(id);
			tem.setEnable(0);
			videoService.update(tem);
		}
		return result;

	}

	public JSONObject saveDideo(JSONObject data) {
		JSONObject result = data;

		Video video = new Video();
		if (data.containsKey("id") && StringUtils.isNotEmpty(data.getString("id"))) {
			video = videoService.load(data.getString("id"));
		}
		if (data.containsKey("courseId") && StringUtils.isNotEmpty(data.getString("courseId"))) {
			video.setCourse(new Course(data.getString("courseId")));
		}
		video.setUser(getUser());
		if (data.containsKey("url") && StringUtils.isNotEmpty(data.getString("url"))) {
			video.setUrl(data.getString("url"));
		}
		if (data.containsKey("name") && StringUtils.isNotEmpty(data.getString("name"))) {
			video.setName(data.getString("name"));
		}
		if (StringUtils.isNotEmpty(video.getId())) {
			videoService.update(video);
		} else {
			videoService.save(video);
		}
		result.put("videoId", video.getId());

		return result;
	}

	/** 修改用户信息(学生信息、老师信息) */
	private JSONObject editUserInfo(JSONObject data) {
		JSONObject result = data;
		// result.put("type", data.get("type"));
		User user = userService.load(data.getString("id"));
		// data.remove("type");
		User tem = (User) JSONObject.toBean(data, User.class);
		user.setAddress(tem.getAddress());
		user.setAvatar(tem.getAvatar());
		user.setCity(tem.getCity());
		user.setEmail(tem.getEmail());
		user.setNickname(tem.getNickname());
		user.setRemark(tem.getRemark());
		user.setSchool(tem.getSchool());
		user.setSex(tem.getSex());
		user.setTel(tem.getTel());
		userService.update(user);
		/*
		 * // set对应的属性 Iterator it = data.keys(); Class editUser = null; try { editUser = Class.forName("com.hydom.vt.entity.User");// 获得User类 } catch (ClassNotFoundException e1) {
		 * e1.printStackTrace(); } Method setMethod; while (it.hasNext()) { String key = it.next().toString();// 获得Json的键 String value = null; try { value = new
		 * String(data.getString(key).getBytes("ISO-8859-1"), "UTF-8");// 获得Json的值 } catch (UnsupportedEncodingException e1) { e1.printStackTrace(); } if (value == null) { continue; } else { try { //
		 * 获得User类的set方法，参数为String类型 setMethod = editUser.getMethod( "set" + key.substring(0, 1).toUpperCase() + key.substring(1), String.class); // 调用set方法 setMethod.invoke(user, value.trim()); }
		 * catch (NoSuchMethodException e) { e.printStackTrace(); } catch (SecurityException e) { e.printStackTrace(); } catch (IllegalAccessException e) { e.printStackTrace(); } catch
		 * (IllegalArgumentException e) { e.printStackTrace(); } catch (InvocationTargetException e) { e.printStackTrace(); } } result.put(key, value);// 把转过编码的键值对存入result }
		 */

		return result;
	}

	/** 取消关注老师 */
	private JSONObject cancelfocusTeacher(JSONObject data) {
		JSONObject result = data;
		String teacherId = data.getString("id");
		User user = getUser();
		user = userService.load(user.getId());

		// 判断该老师是否已被关注
		Set<User> set = new HashSet<User>(user.getTeacherSet());
		User teacher = userService.load(teacherId);
		boolean flag = false;
		for (User u : set) {
			if (teacher.equals(u)) {
				flag = true;
			}
		}

		if (flag) {
			user.getTeacherSet().remove(teacher);
			userService.save(user);
			return result;
		}
		return getErr("没有关注过这个老师");
	}

	/** 观看历史记录列表 */
	private JSONObject videoHistoryList(JSONObject data) {
		JSONObject result = new JSONObject();
		result.put("type", data.getString("type"));
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserCourse.class);
		detachedCriteria.add(Restrictions.eq("user", getUser()));
		detachedCriteria.add(Restrictions.eq("types", 1));

		Pager pager = (Pager) JSONObject.toBean(data, Pager.class);
		pager.setOrderBy("modifyDate");
		userCourseService.findByPager(pager, detachedCriteria);
		List<UserCourse> userCourses = (List<UserCourse>) pager.getList();
		JSONArray list = new JSONArray();
		result.put("pageCount", pager.getPageCount());
		result.put("pageNumber", pager.getPageNumber());
		result.put("totalCount", pager.getTotalCount());
		for (UserCourse userCourse : userCourses) {
			Course course = userCourse.getCourse();
			/*
			 * JSONObject tem = new JSONObject(); tem.put("id", course.getId()); tem.put("image", course.getImage()); tem.put("startDate", course.getStartDate().getTime()); tem.put("name",
			 * course.getName()); tem.put("score", courseService.getCourseScore(course.getId())); tem.put("money", course.getMoney()); tem.put("nickname", course.getUser().getNickname());
			 * tem.put("username", course.getUser().getUsername()); tem.put("isFocus", userCourseService.isExist(new String[] { "types", "user.id", "course.id" }, new Object[] { 0, getUser().getId(),
			 * course.getId() }));
			 * 
			 * tem.put("nextStart", courseService.getStartCourse(course.getId()) .getTime());
			 */
			list.add(courseToJson(course));
		}
		result.put("list", list);

		return result;
	}

	/** 关注的老师列表 */
	private JSONObject focusTeaList(JSONObject data) {
		JSONObject result = data;
		User user = getUser();
		user = userService.load(user.getId());
		JSONArray list = new JSONArray();
		JSONObject team = new JSONObject();
		for (User teacher : user.getTeacherSet()) {
			team.put("id", teacher.getId());
			team.put("username", teacher.getUsername());
			team.put("nickname", teacher.getNickname());
			team.put("sex", teacher.getSex());
			team.put("city", teacher.getCity());
			team.put("level", teacher.getLevel());
			team.put("income", teacher.getIncome());
			team.put("email", teacher.getEmail());
			team.put("tel", teacher.getTel());
			team.put("avatar", teacher.getAvatar());
			team.put("address", teacher.getAddress());
			list.add(team);
			team.clear();
		}
		result.put("list", list);
		return result;
	}

	/** 关注老师 */
	private JSONObject focusTea(JSONObject data) {
		JSONObject result = data;
		String teacherId = data.getString("id");
		User user = getUser();
		user = userService.load(user.getId());

		// 判断该老师是否已被关注
		Set<User> set = new HashSet<User>(user.getTeacherSet());
		User teacher = userService.load(teacherId);
		for (User u : set) {
			if (teacher.equals(u)) {
				return getErr("老师已被关注过了");
			}
		}

		user.getTeacherSet().add(teacher);
		userService.save(user);
		return result;
	}

	// 关注课程列表
	public JSONObject focusCourseList(JSONObject data) {

		JSONObject result = new JSONObject();
		result.put("type", data.getString("type"));
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserCourse.class);
		detachedCriteria.add(Restrictions.eq("user", getUser()));
		detachedCriteria.add(Restrictions.eq("types", 0));
		Pager pager = (Pager) JSONObject.toBean(data, Pager.class);
		userCourseService.findByPager(pager, detachedCriteria);
		List<UserCourse> userCourses = (List<UserCourse>) pager.getList();
		JSONArray list = new JSONArray();
		result.put("keyword", pager.getKeyword());
		result.put("pageCount", pager.getPageCount());
		result.put("pageNumber", pager.getPageNumber());
		result.put("totalCount", pager.getTotalCount());
		for (UserCourse userCourse : userCourses) {
			Course course = userCourse.getCourse();
			list.add(courseToJson(course));
		}
		result.put("list", list);

		return result;
	}

	// 取消关注课程
	public JSONObject cancelfocusCourse(JSONObject data) {
		JSONObject result = data;
		String courseId = data.getString("courseId");
		UserCourse userCourse = userCourseService.get(new String[] { "user", "course.id" }, new Object[] { getUser(),
				courseId });
		if (userCourse == null) {
			return getErr("您未关注该课程");
		}
		userCourseService.delete(userCourse);
		return result;
	}

	// 关注课程
	public JSONObject focusCourse(JSONObject data) {
		JSONObject result = data;
		String courseId = data.getString("courseId");
		if (userCourseService.isExist(new String[] { "types", "user", "course.id" }, new Object[] { 0, getUser(),
				courseId })) {
			return getErr("该课程已关注");
		}

		UserCourse userCourse = new UserCourse();
		userCourse.setUser(getUser());
		userCourse.setCourse(new Course(courseId));
		userCourseService.save(userCourse);
		return result;
	}

	// 学生个人中心
	public JSONObject getStuCenter(JSONObject data) {
		User tem = getUser();
		User user = userService.get(tem.getId());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "createDate", "modifyDate", "teacherSet", "studentSet", "level",
				"income", "password" });
		JSONObject result = JSONObject.fromObject(user, jsonConfig);
		result.put(TYPE, data.getString("type"));
		/*
		 * JSONArray list = new JSONArray(); for (user m : user.getTeacherSet()) { JSONObject json = new JSONObject(); json.put("id", m.getId()); json.put("avatar", m.getAvatar() + "");
		 * json.put("username", m.getUsername()); json.put("nickname", m.getNickname()); list.add(json); } result.put("list", list);
		 */
		return result;
	}

	// 教师个人中心
	public JSONObject getTeaCenter(JSONObject data) {
		User tem = getUser();
		User user = userService.get(tem.getId());
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "createDate", "modifyDate", "teacherSet", "studentSet", "money",
				"password" });
		JSONObject result = JSONObject.fromObject(user, jsonConfig);
		result.put(TYPE, data.getString("type"));
		JSONArray list = new JSONArray();
		for (User m : user.getStudentSet()) {
			if (m.getStatus() == 0) {
				JSONObject json = new JSONObject();
				json.put("id", m.getId());
				json.put("avatar", m.getAvatar() + "");
				json.put("username", m.getUsername());
				json.put("nickname", m.getNickname());
				list.add(json);
			}
		}

		result.put("list", list);
		return result;
	}

	// type=0即将开始的课程 type=1 课程列表（学生）2热门课程 按观看量倒序
	public JSONObject getCourseListByStu(JSONObject data, Integer type) {
		JSONObject result = new JSONObject();
		result.put("type", data.getString("type"));
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Course.class);
		// detachedCriteria.add(Restrictions.ge("endDate", new Date()));
		Pager pager = (Pager) JSONObject.toBean(data, Pager.class);
		pager.setProperty("name");
		if (type == 0) {// 即将开始的课程 按照开始时间顺序；
			courseService.getStartCourse(pager);
		} else if (type == 1) {// 默认创建时间倒序

			String keywork = pager.getKeyword();
			String address = data.getString("address");
			String school = data.getString("school");
			// String course = data.getString("course");
			detachedCriteria.createAlias("user", "user");
			if (StringUtils.isNotEmpty(keywork)) {
				// 模糊查询条件
				SimpleExpression[] pathSEs = new SimpleExpression[4];
				pathSEs[0] = Restrictions.like("name", "%" + keywork + "%");
				pathSEs[1] = Restrictions.like("remark", "%" + keywork + "%");
				pathSEs[2] = Restrictions.like("user.nickname", "%" + keywork + "%");
				pathSEs[3] = Restrictions.like("mainContent", "%" + keywork + "%");
				detachedCriteria.add(Restrictions.or(pathSEs));
			}
			/*
			 * if (StringUtils.isNotEmpty(address) || StringUtils.isNotEmpty(address)) {
			 * 
			 * }
			 */
			if (StringUtils.isNotEmpty(address)) {
				detachedCriteria.add(Restrictions.like("user.address", "%" + address + "%"));
			}
			if (StringUtils.isNotEmpty(school)) {
				detachedCriteria.add(Restrictions.like("user.school", "%" + school + "%"));
			}
			/*
			 * if (StringUtils.isNotEmpty(course)) { detachedCriteria.add(Restrictions.like("name", "%" + course + "%")); }
			 */
			pager.setKeyword(null);
			courseService.findByPager(pager, detachedCriteria);
		} else if (type == 2) {// 热门课程 按观看量倒序
			pager.setOrderBy("number");
			courseService.findByPager(pager, detachedCriteria);
		}
		result.put("keyword", pager.getKeyword());
		result.put("pageCount", pager.getPageCount());
		result.put("pageNumber", pager.getPageNumber());
		result.put("totalCount", pager.getTotalCount());

		List<Course> courses = (List<Course>) pager.getList();
		result.put("list", coursesToJson(courses));
		return result;

	}

	public JSONArray coursesToJson(List<Course> courses) {
		JSONArray list = new JSONArray();

		for (Course course : courses) {

			list.add(courseToJson(course));
		}
		return list;
	}

	public JSONArray coursesTimeToJson(List<CourseTime> courses) {
		JSONArray list = new JSONArray();

		for (CourseTime courseTime : courses) {
			Course course = courseTime.getCourse();

			list.add(courseToJson(course));
		}
		return list;
	}

	public JSONObject courseToJson(Course course) {
		JSONObject tem = new JSONObject();
		tem.put("id", course.getId());
		tem.put("image", course.getImage());
		tem.put("startDate", course.getStartDate().getTime());
		tem.put("name", course.getName());
		tem.put("score", courseService.getCourseScore(course.getId()));
		tem.put("money", course.getMoney());
		User u = userService.load(course.getUser().getId());
		tem.put("nickname", u.getNickname());
		tem.put("username", u.getUsername());
		tem.put("status", course.getStatus());
		String password = course.getPassword();
		tem.put("isPassword", StringUtils.isNotEmpty(password));
		// 关注人数
		tem.put("focusNum", userCourseService.getcount("", course.getId(), 0));
		tem.put("isFocus",
				userCourseService.isExist(new String[] { "types", "user.id", "course.id" }, new Object[] { 0,
						getUser().getId(), course.getId() }));
		Date nextStart = course.getNextStart();
		if (nextStart == null) {
			nextStart = courseService.getStartCourse(course.getId());
		}
		/*
		 * if (nextStart == null) { nextStart = new Date(); }
		 */
		// 已过期的课程，下次开课时间-1
		tem.put("nextStart", nextStart == null ? -1 : nextStart.getTime());
		tem.put("timeLength", course.getTimeLength() == null ? "" : course.getTimeLength());
		return tem;
	}

	// 我的视频列表
	@SuppressWarnings("deprecation")
	public JSONObject getVideoList(JSONObject data) {
		Pager pager = (Pager) JSONObject.toBean(data, Pager.class);
		pager.setProperty("name");
		User user = getUser();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Video.class);
		detachedCriteria.add(Restrictions.eq("enable", 1));
		if (!data.containsKey("courseId") || StringUtils.isEmpty(data.getString("courseId"))) {
			detachedCriteria.createAlias("course", "course", DetachedCriteria.LEFT_JOIN);

			detachedCriteria.add(Restrictions.or(Restrictions.eq("user.id", user.getId()),
					Restrictions.eq("course.user", user)));
		} else {
			detachedCriteria.add(Restrictions.eq("course.id", data.getString("courseId")));
		}
		videoService.findByPager(pager, detachedCriteria);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "createDate", "modifyDate", "user", "orderType", "property", "orderBy",
				"course" });
		JSONObject result = JSONObject.fromObject(pager, jsonConfig);
		result.put("type", data.getString("type"));

		return result;
	}

	// type=0我的课程列表 type=1 热门课程(教师)
	public JSONObject getCourseListByTea(JSONObject data, Integer type) {
		Pager pager = (Pager) JSONObject.toBean(data, Pager.class);
		pager.setProperty("name");
		if (type == 1) {
			pager.setOrderBy("number");
		}
		User user = getUser();

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Course.class);
		detachedCriteria.add(Restrictions.eq("user.id", user.getId()));
		courseService.findByPager(pager, detachedCriteria);
		JsonConfig jsonConfig = getJsonConfig();
		jsonConfig.setExcludes(new String[] { "createDate", "modifyDate", "user", "orderType", "property", "orderBy",
				"list" });
		@SuppressWarnings("unchecked")
		List<Course> courses = (List<Course>) pager.getList();
		JSONArray list = coursesToJson(courses);
		JSONObject result = JSONObject.fromObject(pager, jsonConfig);
		result.put("list", list);
		result.put("type", data.getString("type"));
		return result;
	}

	// 创建课程（房间）
	public JSONObject createCourse(JSONObject data) throws Exception {
		User user = getUser();
		if (!user.getIsteacher()) {
			return getErr("对不起，您没有权限!");
		}
		JSONObject result = data;
		// 每周星期几上课（[1,3,5]）
		JSONArray coursePeriodic = data.getJSONArray("coursePeriodic");
		if (coursePeriodic.size() == 0) {
			return getErr("请选择每周上课时间");
		}
		data.remove("coursePeriodic");
		Course course = (Course) JSONObject.toBean(data, Course.class);
		if (StringUtils.isNotEmpty(course.getPassword())) {
			course.setPassword(HydomUtil.md5Hex(course.getPassword()));
		}
		course.setCoursePeriodic(coursePeriodic.toString().replace("[", "").replace("]", ""));
		course.setUser(user);
		/*
		 * course.setLivestream(HydomUtil.getUUID()); course.setRecordstream(HydomUtil.getUUID()); course.setMicrophonestream(HydomUtil.getUUID());
		 */
		String dailyStarttime = data.getString("dailyStarttime");
		String dailyEndtime = data.getString("dailyEndtime");
		course.setTime(dailyStarttime + "-" + dailyEndtime);

		Date courseStartdate = DateTimeHelper.parseToDate(data.getString("courseStartdate"), "yyyy-MM-dd");
		Date courseEnddate = DateTimeHelper.parseToDate(data.getString("courseEnddate"), "yyyy-MM-dd");
		course.setStartDate(courseStartdate);
		course.setEndDate(courseEnddate);
		if (courseEnddate.getTime() < courseStartdate.getTime()) {
			return getErr("开始或结束时间不正确");
		}
		if (coursePeriodic == null || coursePeriodic.size() == 0) {
			return getErr("请选择星期");
		}
		// 是否需要添加 （修改上课时间）
		Boolean isAddCoureseTime = true;
		Course oldCourse = null;
		String courseId = null;

		// 是否修改
		if (StringUtils.isNotEmpty(course.getId())) {

			oldCourse = courseService.get(course.getId());
			if (oldCourse == null) {
				return getErr("课程不存在");
			}
			courseId = course.getId();
			// 上课时间是否变动,变动则删除以前的上课时间
			if (!oldCourse.getTime().equals(course.getTime())
					|| oldCourse.getStartDate().getTime() != course.getStartDate().getTime()
					|| oldCourse.getEndDate().getTime() != course.getEndDate().getTime()
					|| !course.getCoursePeriodic().equals(oldCourse.getCoursePeriodic())) {
				if (!courseTimeService.delByCourseId(course.getId())) {
					return getErr("课程删除失败");
				}
				oldCourse.setTime(course.getTime());
				oldCourse.setStartDate(course.getStartDate());
				oldCourse.setEndDate(course.getEndDate());
				oldCourse.setCoursePeriodic(course.getCoursePeriodic());
			} else {
				// 未变动则不加添加新的上课时间
				isAddCoureseTime = false;
			}
			oldCourse.setImage(course.getImage());
			oldCourse.setMainContent(course.getMainContent());
			oldCourse.setMaxStuNum(course.getMaxStuNum());
			oldCourse.setMoney(course.getMoney());
			oldCourse.setName(course.getName());
			oldCourse.setPassword(course.getPassword());
			oldCourse.setRemark(course.getRemark());
			oldCourse.setTypes(course.getTypes());
			oldCourse.setTimeLength(course.getTimeLength());
			courseService.update(oldCourse);

		} else {
			courseId = courseService.save(course);
		}

		if (isAddCoureseTime) {
			try {
				int day = (int) DateTimeHelper.getDaysOfTwoDate(course.getEndDate(), course.getStartDate());
				for (int i = 0; i < day; i++) {
					Date date = DateTimeHelper.addDays(course.getStartDate(), i);
					int week = DateTimeHelper.getDayOfWeek(date);
					if (week == 0) {
						week = 7;
					}
					if (coursePeriodic.contains(week)) {
						CourseTime courseTime = new CourseTime(date, dailyStarttime, dailyEndtime, courseId);
						courseTimeService.save(courseTime);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return getErr("课程创建失败");

			}
		}
		result.put("roomid", courseId);
		return result;
	}

	// 注册
	public JSONObject register(JSONObject data) {

		JSONObject result = data;
		String uid = null;
		if (!checkUserName(data).getBoolean("isExist")) { // 用户名是否存在
			User user = (User) JSONObject.toBean(data, User.class);
			user.setPassword(HydomUtil.md5Hex(user.getPassword()));
			uid = userService.save(user);
			result.put("userid", uid);
			getSession().setAttribute(Constant.SESSION_USER, user);
		} else {
			result.put(TYPE, Constant.TYPE_99);

			result.put(ERROR, "用户名已存在");
		}
		return result;
	}

	// 登录
	public JSONObject login(JSONObject data) {
		JSONObject result = new JSONObject();

		User user = userService.get(new String[] { "username", "password" }, new Object[] { data.get("username"),
				HydomUtil.md5Hex(data.getString("password")) });
		if (user == null) {

			result.put(TYPE, Constant.TYPE_99);
			result.put(ERROR, "用户名或密码错误");
		} else if (user.getStatus() != 0) {
			result.put(TYPE, Constant.TYPE_99);
			result.put(ERROR, "对不起，您的账号不可用，请联系管理员");
		} else {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setExcludes(new String[] { "createDate", "modifyDate", "teacherSet", "studentSet", "password" });
			result = JSONObject.fromObject(user, jsonConfig);
			result.put(TYPE, data.getString("type"));
			getSession().setAttribute(Constant.SESSION_USER, user);
			// SessionListener.sessionContext.AddSession(getSession());
		}

		return result;
	}

	// 检查用户名是否存在
	public JSONObject checkUserName(JSONObject data) {
		JSONObject result = data;

		Boolean isExist = userService.isExist("username", data.getString("username"));

		result.put("isExist", isExist);
		if (isExist) {
			result.put(TYPE, Constant.TYPE_99);
			result.put(ERROR, "用户名已存在");
		}
		return result;
	}

	public User getUser() {
		User user = (User) getSession().getAttribute(Constant.SESSION_USER);
		return user;
	}

	public JSONObject getErr(String err) {
		JSONObject result = new JSONObject();
		result.put(TYPE, Constant.TYPE_99);
		result.put(ERROR, err);
		return result;
	}

	public JsonConfig getJsonConfig() {
		JsonConfig jsonConfig = new JsonConfig();

		jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {

			public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
				return value == null ? "" : ((Date) value).getTime();
			}

			public Object processArrayValue(Object value, JsonConfig jsonConfig) {
				return null;
			}
		});
		return jsonConfig;
	}

}
