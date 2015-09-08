package com.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.quartz.impl.calendar.WeeklyCalendar;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JSONUtils;

import com.google.gson.JsonArray;
import com.hydom.common.bean.Pager;
import com.hydom.common.service.UserService;
import com.hydom.common.service.VideoService;
import com.hydom.vt.entity.Course;
import com.hydom.vt.entity.User;
import com.hydom.vt.entity.Video;


public class Test extends BaseTestCase {
	@Resource
	private UserService memberService;
	
	@Resource
	private VideoService videoService;
	
	@org.junit.Test
	public  void tt(){
		/*type:204,
		image:xxx.jpg, //房间图片
		mainContent:  //主要知识点
		maxStuNum:50,  //最多人数
		money:60,   //费用（默认0免费）
		name:C  //房间名称
		password:123456, // 房间密码
		remark://备注（房间描叙）
		courseStartdate：2015-6-29//课程开始日期
		courseEnddate：2015-7-29//课程结束日期
		coursePeriodic：[1,3,5,7]//课程周期
		dailyStarttime：14:30//每日开课时间
		dailyEndtime：16:30//每日结束时间
*/	
		
		JSONArray array=new JSONArray();
		array.add(2);
		array.add(1);array.add(3);
		System.out.println(array.toString().replace("[", "").replace("]", ""));
		
	/*JSONObject json=new JSONObject();
	json.put("type", 204);
	json.put("mainContent", "java速成");
	json.put("maxStuNum", 50);
	json.put("money", 100);
	json.put("name", "java开发");
	json.put("password", "111111");
	json.put("courseStartdate", "2015-6-29");
	json.put("courseEnddate", "2015-7-29");
	json.put("dailyStarttime", "14:30");
	json.put("dailyEndtime", "16:30");
	
	int[] week=new int[]{1,3,5};
	json.put("coursePeriodic", week);
	System.out.println(json);*/
	}
	
	public void focusTea(){
		User mebmer=memberService.get("1DEDA1A28D8240B9AEFF");
		mebmer.getTeacherSet().add(new User("408BDAD3B5F04FA88729"));
		memberService.save(mebmer);
	}
	
	public void focusCourse(){
		JSONObject json=new JSONObject();
		json.put("courseId", "AE931A5BCF4D48059F44");
		json.put("type", 218);
		System.out.println(json);
	}
	
	
	public void addVideo(){
		Video video=new Video();
		video.setUser(new User("0D1B336B8E664BF0AFC6"));
	//	video.setCourse(new Course("029772CA0F494A5BA055"));
		video.setName("视频01");
		videoService.save(video);
	}
	
	public void getCourseList(){
		Pager pager=new Pager();
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "createDate", "list",
				"orderType", "pageCount", "totalCount","orderBy","property"});
		System.out.println(JSONObject.fromObject(pager,jsonConfig));
	}
	
	public void createCourse(){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonValueProcessor() {
			private SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");


			public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
				return value == null ? "" : sd.format(value);
			}

			public Object processArrayValue(Object value, JsonConfig jsonConfig) {
				return null;
			}
		});
		Course course=new Course();
		course.setImage("xxx.jpg");
		course.setName("C++从0到精通课程");
		course.setStartDate(new Date());
		course.setMaxStuNum(50);
		course.setMainContent("基础算法");
		course.setMoney(60f);
		course.setPassword("123456");
		course.setTypes(0);
		course.setRemark("此课程适合没有基础的童鞋");
		System.out.println(JSONObject.fromObject(course, jsonConfig));
	}
}
