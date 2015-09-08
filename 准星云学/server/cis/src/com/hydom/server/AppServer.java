package com.hydom.server;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.hydom.account.ebean.Account;
import com.hydom.account.service.AccountService;
import com.hydom.credit.ebean.ScoreRecord;
import com.hydom.credit.ebean.ScoreTop;
import com.hydom.credit.ebean.Trophy;
import com.hydom.credit.ebean.TrophyRecord;
import com.hydom.credit.service.ScoreRecordService;
import com.hydom.credit.service.ScoreTopService;
import com.hydom.credit.service.TrophyRecordService;
import com.hydom.credit.service.TrophyService;
import com.hydom.dao.PageView;
import com.hydom.extra.ebean.AppVersion;
import com.hydom.extra.ebean.Message;
import com.hydom.extra.ebean.MessageDeleteRecord;
import com.hydom.extra.ebean.Sense;
import com.hydom.extra.ebean.SystemConfig;
import com.hydom.extra.service.AppVersionService;
import com.hydom.extra.service.MessageDeleteRecordService;
import com.hydom.extra.service.MessageService;
import com.hydom.extra.service.SenseService;
import com.hydom.extra.service.ShortMessageService;
import com.hydom.extra.service.SystemConfigService;
import com.hydom.task.ebean.TaskRecord;
import com.hydom.task.service.TaskRecordService;
import com.hydom.util.HelperUtil;
import com.hydom.util.MD5;
import com.hydom.util.StringGenerator;
import com.hydom.util.WebUtil;

/**
 * 负责与客户端进行数据交互
 * 
 * @author www.hydom.cn [heou]
 * 
 */
@Controller
@Scope(value = "prototype")
public class AppServer {
	@Resource
	private AccountService accountService;
	@Resource
	private ShortMessageService shortMessageService;
	@Resource
	private TaskRecordService taskRecordService;
	@Resource
	private TrophyService trophyService;
	@Resource
	private TrophyRecordService trophyRecordService;
	@Resource
	private MessageService messageService;
	@Resource
	private SystemConfigService systemConfigService;
	@Resource
	private SenseService senseService;
	@Resource
	private MessageDeleteRecordService messageDeleteRecordService;
	@Resource
	private ScoreRecordService scoreRecordService;
	@Resource
	private AppVersionService appVersionService;
	@Resource
	private ScoreTopService scoreTopService;

	private Log log = LogFactory.getLog("appServerLog");

	private String username;
	private String password;
	private String oripwd;// 原密码
	private String newpwd;// 新密码
	private String code;// 验证码
	private int codeType;// 验证码类型：1=注册验证码 2=找回密码验证码
	private long uid;// 用户ID
	private long tid;// 任务ID[对应于TaskRecord ID]
	private String result_str;// 识别结果串
	private int sign;// 识别的结果类型：1=正确 0=错误
	private int num;// 兑换奖品的数量
	private InputStream inputStream;
	private String nickname; // 用户昵称
	private String backname;// 银行名称
	private String backaccount;// 银行帐号
	private String pay;// 支付宝帐号
	private String contact;// 提交建议：联系方式
	private String content;// 提交建议：内容
	private String phone; // 发送短信时的电话
	private String jsonStr;//
	private int page = 1;
	private int maxresult = 10;
	private double version;
	private int type;

	/**
	 * 注册
	 * 
	 * @return
	 */
	public String signup() {
		log.info("App【用户注册】：" + "用户名=" + username + " 密码=" + password + " 验证码=" + code);
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		try {
			String syscode = shortMessageService.findCode(username);
			if (code != null && code.equals(syscode)) { // 验证码通过
				if (!HelperUtil.isPhoneNumber(username)) {// 手机号格式不正确
					dataMap.put("result", 10);
				} else {
					Account account = new Account(username,password, username);
					accountService.save(account);
					account.setType(1);// 设置为普通用户
					dataMap.put("result", 1);
				}
			} else {
				if ("CODETIMEOUT".equals(syscode)) {
					dataMap.put("result", 15);// 验证码超时
				} else {
					dataMap.put("result", 2);// 验证码错误
				}
			}
		} catch (Exception e) {
			dataMap.put("result", 3);// 用户名已存在
		}
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 登录
	 * 
	 * @return
	 */
	public String signin() {
		log.info("App【用户登录】：" + "用户名=" + username + " 密码=" + password);
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		Account account = accountService.findByUP(username, password);
		if (account != null) { // 登录成功
			account.setLastSigninTime(new Date());
			accountService.update(account);
			dataMap.put("result", 1);
			dataMap.put("username", account.getUsername());
			dataMap.put("nickname", account.getNickname() == null ? "" : account.getNickname());
			dataMap.put("uid", account.getId());
		} else {
			dataMap.put("result", 4);// 用户名或密码错误
			dataMap.put("username", "");
			dataMap.put("nickname", "");
			dataMap.put("uid", "");
		}
		dataFillStream(dataMap);
		return "success";
	}

	public String signout() {
		log.info("App【用户登出】：" + "用户名=" + username + " 密码=" + password + " 用户ID=" + uid);
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		Account account = accountService.findByUP(username, password);
		if (account != null) { // 注销成功
			account.setState(2);// 设置状态为注销
			account.setLastSignoutTime(new Date());
			accountService.update(account);
			dataMap.put("result", 1);
		} else {
			dataMap.put("result", 0);
		}
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 获取分配的题目
	 * 
	 * @return
	 */
	public String fetchNote() {
		log.info("App【获取分配题目】：" + "uid=" + uid);
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TaskRecord taskRecord = taskRecordService.fetchTaskRecord(uid);
		if (taskRecord != null) {
			dataMap.put("tid", taskRecord.getId());
			// 处理MetricPoint对象
			String[] data = taskRecord.getTask().getMetricPoint().replaceAll("},", "}#").split("#");
			List<Map<String, Integer>> lineList = new ArrayList<Map<String, Integer>>();
			for (String str : data) {
				Map<String, Integer> xy = SvgImage.xy(str);
				Map<String, Integer> map = new LinkedHashMap<String, Integer>();
				map.put("x", xy.get("x"));
				map.put("y", xy.get("y"));
				lineList.add(map);
			}
			dataMap.put("result", 1);
			dataMap.put("tid", taskRecord.getId());
			dataMap.put("image", lineList);
			dataMap.put("timeout", taskRecord.getTask().getRecycleTime());
			dataMap.put("mathtime", sdf.format(taskRecord.getMatchTime()));
			dataMap.put("passtime", System.currentTimeMillis()-taskRecord.getMatchTime().getTime()); //分配时间到当前已经过了的秒数
		} else {
			dataMap.put("result", 0);
			dataMap.put("tid", "");
			dataMap.put("image", "");
			dataMap.put("timeout", "");
			dataMap.put("mathtime", "");
		}
		// String data =
		// "{'x':234,'y':1346},{'x':232,'y':1347},{'x':-1,'y':0},{'x':229,'y':1345},{'x':229,'y':1347},{'x':229,'y':1347},{'x':230,'y':1347},{'x':230,'y':1347},{'x':230,'y':1347},{'x':231,'y':1347},{'x':236,'y':1346},{'x':241,'y':1345},{'x':262,'y':1339},{'x':273,'y':1337},{'x':275,'y':1337},{'x':275,'y':1338},{'x':271,'y':1342},{'x':-1,'y':0},{'x':247,'y':1348},{'x':246,'y':1354},{'x':248,'y':1361},{'x':249,'y':1368},{'x':250,'y':1384},{'x':250,'y':1402},{'x':249,'y':1405},{'x':242,'y':1399},{'x':238,'y':1394},{'x':235,'y':1388},{'x':233,'y':1382},{'x':-1,'y':0},{'x':261,'y':1353},{'x':261,'y':1355},{'x':261,'y':1358},{'x':260,'y':1364},{'x':260,'y':1370},{'x':260,'y':1375},{'x':261,'y':1380},{'x':262,'y':1384},{'x':266,'y':1388},{'x':268,'y':1389},{'x':271,'y':1387},{'x':271,'y':1377},{'x':270,'y':1374},{'x':268,'y':1373},{'x':263,'y':1373},{'x':259,'y':1374},{'x':249,'y':1378},{'x':-1,'y':0},{'x':226,'y':1349},{'x':228,'y':1350},{'x':228,'y':1350},{'x':228,'y':1350},{'x':227,'y':1351},{'x':226,'y':1353},{'x':218,'y':1362},{'x':204,'y':1380},{'x':204,'y':1382},{'x':229,'y':1392},{'x':229,'y':1392},{'x':229,'y':1392},{'x':229,'y':1393},{'x':228,'y':1393},{'x':-1,'y':0},{'x':176,'y':1360},{'x':188,'y':1365},{'x':197,'y':1370},{'x':200,'y':1373},{'x':192,'y':1399},{'x':187,'y':1406},{'x':191,'y':1407},{'x':195,'y':1407},{'x':199,'y':1407},{'x':205,'y':1406},{'x':209,'y':1405},{'x':214,'y':1402},{'x':-1,'y':0},{'x':296,'y':1354},{'x':299,'y':1355},{'x':299,'y':1356},{'x':288,'y':1366},{'x':283,'y':1371},{'x':278,'y':1378},{'x':280,'y':1379},{'x':289,'y':1382},{'x':300,'y':1383},{'x':302,'y':1384},{'x':303,'y':1385},{'x':304,'y':1386},{'x':303,'y':1387},{'x':-1,'y':0},{'x':311,'y':1346},{'x':314,'y':1346},{'x':317,'y':1346},{'x':325,'y':1350},{'x':327,'y':1354},{'x':326,'y':1357},{'x':321,'y':1364},{'x':312,'y':1370},{'x':306,'y':1372},{'x':317,'y':1375},{'x':326,'y':1380},{'x':329,'y':1383},{'x':331,'y':1386},{'x':330,'y':1387},{'x':326,'y':1391},{'x':323,'y':1393},{'x':318,'y':1394},{'x':303,'y':1395},{'x':294,'y':1395},{'x':-1,'y':0}";
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 提交识别结果
	 * 
	 * @return
	 */
	public String postNote() {
		log.info("App【提交识别结果】：" + "tid=" + tid + " 识别结果=" + result_str);
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		try {
			TaskRecord record = taskRecordService.find(tid);
			if (record.getIdentState() != null && record.getIdentState() == 0) {// 超时程序检查设置了超时
				record.setPostTime(new Date());
				record.setResult(result_str);
				taskRecordService.update(record);
				dataMap.put("result", 8);
			} else if (record.getPostTime() != null) {// 提交过
				dataMap.put("result", 13);// 重复提交
			} else if (result_str == null || "".equals(result_str)) {
				dataMap.put("result", 0);// 内容为空
			} else {
				int result = taskRecordService.processTaskRecord(tid, result_str);
				dataMap.put("result", result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("result", 0);
		}
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 休息一下
	 * 
	 * @return
	 */
	public String breakNote() {
		log.info("App【休息一下】：" + "用户ID=" + uid);
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		try {
			Account entity = accountService.find(uid);
			entity.setState(0);
			accountService.update(entity);
			dataMap.put("result", 1);
		} catch (Exception e) {
			dataMap.put("result", 0);
		}
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 同步识别结果：sign=1获取成功结果，sign=0获取失败结果
	 * 
	 * @return
	 */
	public String synNote() {
		log.info("App【同步识别结果】：" + "用户ID=" + uid + " sign=" + sign);
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		PageView<TaskRecord> pageView = new PageView<TaskRecord>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer(
				"o.visible=?1 and o.account.id=?2 and o.sign=?3 and o.identState=?4");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(uid);
		params.add(sign);
		params.add(1);
		pageView.setQueryResult(taskRecordService.getScrollData(pageView.getFirstResult(),
				maxresult, jpql.toString(), params.toArray(), orderby));
		List<TaskRecord> records = pageView.getRecords();

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (TaskRecord tr : records) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			/** 处理MetricPoint对象START **/
			String[] data = tr.getTask().getMetricPoint().replaceAll("},", "}#").split("#");
			List<Map<String, Integer>> lineList = new ArrayList<Map<String, Integer>>();
			for (String str : data) {
				Map<String, Integer> xy = SvgImage.xy(str);
				Map<String, Integer> xymap = new LinkedHashMap<String, Integer>();
				xymap.put("x", xy.get("x"));
				xymap.put("y", xy.get("y"));
				lineList.add(xymap);
			}
			/** 处理MetricPoint对象END **/
			map.put("sign", tr.getSign());
			map.put("score", tr.getScore() == null ? 0 : tr.getScore());
			try {
				map.put("post_time", sdf.format(tr.getPostTime()));
			} catch (Exception e) {
				continue;
			}
			map.put("image", lineList);
			map.put("result_str", tr.getResult());
			map.put("right_str", tr.getTask().getResult());
			list.add(map);
		}
		if (list.size() > 0) {
			dataMap.put("result", 1);
			dataMap.put("pages", pageView.getTotalPage());
		} else {
			dataMap.put("result", 7);// 列表为空
			dataMap.put("pages", 0);
		}
		dataMap.put("list", list);
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 获取奖品列表
	 * 
	 * @return
	 */
	public String listTrophy() {
		log.info("App【获取所有奖品列表】：" + "用户ID=" + uid);
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		Account account = accountService.find(uid);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (account != null) {
			PageView<Trophy> pageView = new PageView<Trophy>(maxresult, page);
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			orderby.put("id", "desc");
			StringBuffer jpql = new StringBuffer("o.visible=?1");
			List<Object> params = new ArrayList<Object>();
			params.add(true);
			pageView.setQueryResult(trophyService.getScrollData(pageView.getFirstResult(),
					maxresult, jpql.toString(), params.toArray(), orderby));
			for (Trophy tr : pageView.getRecords()) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("tid", tr.getId());
				map.put("name", tr.getName() + "");
				map.put("detail", WebUtil.htmlReplaceReverse(WebUtil.HtmltoText(tr.getDetail())));
				map.put("stock", tr.getStock());
				map.put("score", tr.getScore());
				map.put("type", tr.getTrophyType().getName() + "");
				try {
					map.put("image", tr.getImage().substring(4));
				} catch (Exception e) {
					map.put("image", "");
				}

				list.add(map);
			}
			if (list.size() > 0) {
				dataMap.put("result", 1);
				dataMap.put("pages", pageView.getTotalPage());
			} else {
				dataMap.put("result", 7);// 列表为空
				dataMap.put("pages", 0);
			}
		} else {
			dataMap.put("result", 9); // 用户ID不存在
		}
		dataMap.put("list", list);
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 提交兑换奖品相关信息
	 * 
	 * @return
	 */
	public String exchangeTrophy() {
		log.info("App【提交奖品兑换信息】：" + "用户ID=" + uid + "奖品ID=" + tid + "奖品数量=" + num);
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		Trophy trophy = trophyService.find(tid);
		Account account = accountService.find(uid);
		if (!account.getVisible() || !trophy.getVisible()) {// 奖品被删除，或者用户被删除
			dataMap.put("result", 0); // 返回未知错误 奖品被删除，或者用户被删除
			dataMap.put("rid", "");
			dataFillStream(dataMap);
			return "success";
		}
		double theScore = num * trophy.getScore(); // 本次兑换需要的积分
		if (theScore > account.getScore()) {// 如果用户积分不足够兑换，直接返回
			dataMap.put("result", 14); // 积分不足
			dataMap.put("rid", "");
			dataFillStream(dataMap);
			return "success";
		}
		TrophyRecord record = new TrophyRecord();
		record.setPostTime(new Date()); // 设置提交兑换的时间为当前时间
		record.setAccount(account);
		record.setNumber(num);
		record.setScore(theScore);
		record.setTrophy(trophy);
		trophyRecordService.save(record);
		/** 处理积分相关 **/
		ScoreRecord scoreRecord = new ScoreRecord();
		scoreRecord.setAccount(account);
		scoreRecord.setSign(false);
		scoreRecord.setCreateTime(new Date());
		scoreRecord.setDetail("兑换奖品");
		scoreRecord.setTrophyRecord(record);
		scoreRecord.setScore(theScore);
		account.setScore(account.getScore() - scoreRecord.getScore());
		accountService.update(account);
		scoreRecordService.save(scoreRecord);
		/** 处理积分相关 **/
		dataMap.put("result", "1");
		dataMap.put("rid", record.getId());
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 获取历史兑换列表
	 * 
	 * @return
	 */
	public String listHistory() {
		log.info("App【获取历史兑换列表】：" + "用户ID=" + uid);
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Account account = accountService.find(uid);
		if (account == null) {
			dataMap.put("result", 9);// 用户ID不存在
			dataMap.put("pages", 0);
			dataMap.put("score", 0);
			dataMap.put("month", 0);
			dataMap.put("total", 0);
			dataMap.put("list", list);
			dataFillStream(dataMap);
			return "success";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PageView<TrophyRecord> pageView = new PageView<TrophyRecord>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer("o.visible=?1 and o.account.id=?2");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		params.add(uid);
		pageView.setQueryResult(trophyRecordService.getScrollData(pageView.getFirstResult(),
				maxresult, jpql.toString(), params.toArray(), orderby));
		List<TrophyRecord> records = pageView.getRecords();
		for (TrophyRecord tr : records) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("tid", tr.getTrophy().getId());
			map.put("name", tr.getTrophy().getName());
			try {
				map.put("image", tr.getTrophy().getImage().substring(4));
			} catch (Exception e) {
				map.put("image", "");
			}
			map.put("type", tr.getTrophy().getTrophyType().getName());
			map.put("detail", WebUtil.htmlReplaceReverse(WebUtil.HtmltoText(tr.getTrophy()
					.getDetail())));
			map.put("rid", tr.getId());
			map.put("score", tr.getScore());
			map.put("post_time", sdf.format(tr.getPostTime()));
			list.add(map);
		}

		dataMap.put("result", 1);
		if (list.size() > 0) {
			dataMap.put("pages", pageView.getTotalPage());
		} else {
			dataMap.put("pages", 0);// 设置总页数为0
		}

		dataMap.put("score", account.getScore());// 用户总积分
		dataMap.put("month", trophyRecordService.countMonth(uid));
		dataMap.put("total", trophyRecordService.countAll(uid));
		dataMap.put("list", list);
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 清空历史兑换记录
	 * 
	 * @return
	 */
	public String clearHistory() {
		log.info("App【清空历史兑换列表】：" + "用户ID=" + uid);
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		try {
			trophyRecordService.clear(uid);
			dataMap.put("result", 1);
		} catch (Exception e) {
			dataMap.put("result", 0);
		}
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 统计相关兑换信息：
	 * 
	 * @return
	 */
	public String countExchange() {
		log.info("App【统计兑换信息】：" + "用户ID=" + uid);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("total", trophyRecordService.countAll(uid));
		dataMap.put("month", trophyRecordService.countMonth(uid));
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 统计相关积分信息：
	 * 
	 * @return
	 */
	public String countScore() {
		log.info("App【统计用户积分信息】：" + "用户ID=" + uid);
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		try {
			Account account = accountService.find(uid);
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String todayStr = sdf.format(now);
			Date todayStartDate = sdf.parse(todayStr);
			Date todayEndDate = HelperUtil.addDays(todayStartDate, 1);
			if (account != null) {
				dataMap.put("result", 1);
				dataMap.put("score", account.getScore());// 用户总积分
				dataMap.put("tnum", taskRecordService.calcToday(uid, 1));// 今日识别成功次数
				dataMap.put("tnumper", taskRecordService.calcTodayExceedPercent(uid));// 今日识别成功超过其他用户的百分比
				dataMap.put("tscore", taskRecordService
						.calcScore(uid, todayStartDate, todayEndDate));// 今日积分
				dataMap.put("tscoreper", taskRecordService.calcTodayExceedPercent(uid));// 今日积分超过其他用户的百分比
				dataMap.put("mscore", taskRecordService.calcScore(uid, HelperUtil
						.firstDayThisMonth(), HelperUtil.lastDayThisMonth()));// 本月积分
				dataMap.put("mper", taskRecordService.calcMonthPercent(uid));// 本月通过率
				dataFillStream(dataMap);
			} else {
				dataMap.put("result", 9);// 用户ID不存在
				dataMap.put("score", 0);
				dataMap.put("tnum", 0);
				dataMap.put("tnumper", 0);
				dataMap.put("tscore", 0);
				dataMap.put("tscoreper", 0);
				dataMap.put("mscore", 0);
				dataMap.put("mper", 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			dataMap.put("result", 0);
			dataMap.put("score", 0);
			dataMap.put("tnum", 0);
			dataMap.put("tnumper", 0);
			dataMap.put("tscore", 0);
			dataMap.put("tscoreper", 0);
			dataMap.put("mscore", 0);
			dataMap.put("mper", 0);
		}
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 获取当天积分榜单
	 * 
	 * @return
	 */
	public String todayTop() {
		log.info("App【统计用户积分信息】：" + "用户ID=" + uid);
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		try {
			Account account = accountService.find(uid);
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String todayStr = sdf.format(now);
			Date todayStartDate = sdf.parse(todayStr);
			Date todayEndDate = HelperUtil.addDays(todayStartDate, 1);
			dataMap.put("score", taskRecordService.calcScore(uid, todayStartDate, todayEndDate));// 今日用户积分
			Trophy trophy = trophyService.newest();
			dataMap.put("result", 1);
			if (trophy != null) {
				dataMap.put("lacksore", trophy.getScore() - account.getScore() + "");
				dataMap.put("tname", trophy.getName());
			} else {
				dataMap.put("lacksore", "none");
				dataMap.put("tname", "");
			}
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<ScoreTop> topList = scoreTopService.listTheDay(new Date());
			for (ScoreTop top : topList) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				String nickName = top.getAccount().getNickname();
				if (nickName != null && !"".equals(nickName)) {
					map.put("name", nickName);
				} else {
					String phone = top.getAccount().getPhone();
					map.put("name", phone.substring(0, 4) + "****" + phone.substring(7, 11));
				}
				map.put("score", top.getScore());
				map.put("cp", cp(top));
				list.add(map);
			}
			dataMap.put("list", list);
		} catch (Exception e) {
			// e.printStackTrace();
			dataMap.put("result", 0);
			dataMap.put("score", 0);
			dataMap.put("lacksore", "none");
			dataMap.put("tname", "");
			dataMap.put("list", new ArrayList<Map<String, Object>>());
		}
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 也昨日相比，帐户排名变化情况
	 * 
	 * @param accout
	 * @return：-1下降；0持平； 1上升
	 */
	private int cp(ScoreTop top) {
		Date now = new Date();
		List<ScoreTop> yesterdayList = scoreTopService.listTheDay(HelperUtil.addDays(now, -1));
		for (ScoreTop st : yesterdayList) {
			if (st.getAccount().getId() == top.getAccount().getId()) {
				if (st.getLv() > top.getLv()) {// 昨日排名小于
					return 1;
				} else if (st.getLv() < top.getLv()) {
					return -1;
				} else {
					return 0;
				}
			}
		}
		return 1;
	}

	/**
	 * 更新个人资料
	 * 
	 * @return
	 */
	public String updateProfile() {
		log.info("App【更新个人资料】：" + "用户ID=" + uid + " 用户昵称=" + nickname + " 银行名称=" + backname
				+ " 银行帐号=" + backaccount + " 支付宝帐号=" + pay);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Account account = accountService.find(uid);
		if (nickname != null && !"".equals(nickname)) {
			account.setNickname(nickname);
			dataMap.put("nickname_result", 1);
		} else {
			dataMap.put("nickname_result", 0);
		}
		if (backname != null && !"".equals(backname)) {
			account.setBackname(backname);
			dataMap.put("backname_result", 1);
		} else {
			dataMap.put("backname_result", 0);
		}
		if (backaccount != null && !"".equals(backaccount)) {
			account.setBackaccount(backaccount);
			dataMap.put("backaccount_result", 1);
		} else {
			dataMap.put("backaccount_result", 0);
		}
		if (pay != null && !"".equals(pay)) {
			account.setPay(pay);
			dataMap.put("pay_result", 1);
		} else {
			dataMap.put("pay_result", 0);
		}
		accountService.update(account);
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 找回密码【重设密码】
	 * 
	 * @return
	 */
	public String resetPassword() {
		log.info("App【找回密码】：" + "用户名=" + username + " 新密码=" + password + " 验证码=" + code);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			String sysCode = shortMessageService.findCode(username); // username为手机号
			if (sysCode != null && sysCode.equals(code)) {// 判断验证码是否正确：验证码超时问题
				Account account = accountService.findByUsername(username);
				if (account != null) {
					account.setPassword(password);
					accountService.update(account);
					dataMap.put("result", 1);
				} else {
					dataMap.put("result", 6);// 用户名不存在
				}
			} else {
				if ("CODETIMEOUT".equals(sysCode)) {
					dataMap.put("result", 15);// 验证码超时
				} else {
					dataMap.put("result", 2);// 验证码错误
				}
			}
		} catch (Exception e) {
			dataMap.put("result", 0);
		}
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 更改密码
	 * 
	 * @return
	 */
	public String updatePassword() {
		log.info("App【更改密码】：" + "用户ID=" + uid + "用户名=" + username + " 原密码=" + oripwd + " 新密码="
				+ newpwd);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Account account = accountService.find(uid);
		if (account != null) {
			if (oripwd != null && oripwd.equals(account.getPassword())) {// 原密码正确
				account.setPassword(newpwd);
				accountService.update(account);
				dataMap.put("result", 1);
			} else {
				dataMap.put("result", 5);
			}
		} else {
			dataMap.put("result", 9); // 用户ID不存在
		}
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 获取消息列表
	 * 
	 * @return
	 */
	public String listMessage() {
		log.info("App【获取消息列表】：" + "用户ID=" + uid + "page=" + page + "maxresult=" + maxresult);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		PageView<Message> pageView = new PageView<Message>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");

		/***** 用户删除消息记录ID集合 STRART ******/
		StringBuffer mids = new StringBuffer();
		List<Long> midList = messageDeleteRecordService.listMidsByAccid(uid);
		for (long mid : midList) {
			mids.append(mid + ",");
		}
		if (mids.length() > 0) {
			mids.deleteCharAt(mids.length() - 1);
		}
		/***** 用户删除的消息记录ID集合 END *****/
		StringBuffer jpql = new StringBuffer("o.visible=?1");
		if (midList.size() > 0) {
			jpql.append(" and o.id not in(" + mids.toString() + ")");
		}
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		pageView.setQueryResult(messageService.getScrollData(pageView.getFirstResult(), maxresult,
				jpql.toString(), params.toArray(), orderby));

		List<Message> messages = pageView.getRecords();
		for (Message message : messages) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("mid", message.getId());
			map.put("title", message.getTitle());
			map.put("content", message.getContent());
			map.put("issuetime", sdf.format(message.getIssueTime()));
			list.add(map);
		}
		if (list.size() > 0) {
			dataMap.put("result", 1);
			dataMap.put("pages", pageView.getTotalPage());
		} else {
			dataMap.put("result", 7);// 列表为空
			dataMap.put("pages", 0);
		}
		dataMap.put("list", list);
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 删除消息
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String deleteMessage() {
		log.info("App【删除消息】：" + "jsonStr=" + jsonStr);
		long accid = 0;
		ArrayList<String> messageIds = new ArrayList<String>();
		Gson gson = new Gson();
		Map<String, Object> map = gson.fromJson(jsonStr, Map.class);
		for (String key : map.keySet()) {
			if ("mids".equals(key)) {
				messageIds = (ArrayList) map.get(key);
			}
			if ("uid".equals(key)) {
				accid = Long.parseLong(map.get(key).toString());
			}
		}
		ArrayList<String> delSucMids = new ArrayList<String>();
		if (accid != 0 && accountService.find(accid) != null) {
			for (String mid : messageIds) {
				Long msgid = Long.parseLong(mid);
				Message message = messageService.find(msgid);
				if (message != null) {
					MessageDeleteRecord existMDR = messageDeleteRecordService.find(accid, msgid);
					if (existMDR == null) {
						MessageDeleteRecord mdr = new MessageDeleteRecord();
						mdr.setAccid(accid);
						mdr.setMsgid(msgid);
						mdr.setDeleteTime(new Date());
						messageDeleteRecordService.save(mdr);
					}
					delSucMids.add(mid + "");
				}
			}
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("result", delSucMids.size() == messageIds.size() ? 1 : 0);
		dataMap.put("records", delSucMids.size());
		dataMap.put("mids", delSucMids);
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 获取积分说明，关于我们，客户电话 【作废】
	 * 
	 * @return
	 */
	public String fetchAbout() {
		log.info("App【获取关于等信息】：" + "用户ID=" + uid);
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		try {
			dataMap.put("manual", systemConfigService.find("manual").getValueText());
		} catch (Exception e) {
			dataMap.put("manual", "");
		}
		try {
			dataMap.put("about", systemConfigService.find("about").getValueText());
		} catch (Exception e) {
			dataMap.put("about", "");
		}
		try {
			dataMap.put("phone", systemConfigService.find("phone").getValueString());
		} catch (Exception e) {
			dataMap.put("phone", "");
		}
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 获取客服电话
	 * 
	 * @return
	 */
	public String fetchPhone() {
		log.info("App【获取关于等信息】：" + "用户ID=" + uid);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("result", 1);
		try {
			dataMap.put("phone", systemConfigService.find("phone").getValueString());
		} catch (Exception e) {
			dataMap.put("phone", "");
		}
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 积分说明返回的页面
	 */
	public String manual() {
		HttpServletRequest request = ServletActionContext.getRequest();
		SystemConfig config = systemConfigService.find("manual");
		request.setAttribute("config", config);
		return "success";
	}

	/**
	 * 关于我们返回的页面
	 */
	public String about() {
		HttpServletRequest request = ServletActionContext.getRequest();
		SystemConfig config = systemConfigService.find("about");
		request.setAttribute("config", config);
		return "success";
	}

	/**
	 * 提交建议，意见
	 * 
	 * @return
	 */
	public String postSense() {
		log.info("App【提交建议】：" + "用户ID=" + uid + " 联系方式=" + contact + " 内容=" + content);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			Sense sense = new Sense();
			sense.setAccount(accountService.find(uid));
			sense.setContact(contact);
			sense.setContent(content);
			sense.setPostTime(new Date());
			senseService.save(sense);
			dataMap.put("result", 1);
		} catch (Exception e) {
			dataMap.put("result", 0);
		}
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 软件更新
	 * 
	 * @return
	 */
	public String update() {
		log.info("App【软件更新】：" + "用户ID=" + uid + "version=" + version + " type=" + type);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		try {
			AppVersion apv = appVersionService.isUpdate(version);
			if (apv != null) {
				dataMap.put("result", 1);
				dataMap.put("upgrade", 1);
				dataMap.put("url", apv.getFilePath().substring(4));
			} else {
				dataMap.put("result", 1);
				dataMap.put("upgrade", 0);
				dataMap.put("url", "");
			}
		} catch (Exception e) {
			dataMap.put("result", 1);
			dataMap.put("upgrade", 0);
			dataMap.put("url", "");
		}
		dataFillStream(dataMap);
		return "success";
	}

	/**
	 * 发送短信
	 * 
	 * @return
	 */
	public String sendCode() {
		log.info("App【发送验证码】：" + "手机号=" + phone + " codeType=" + codeType);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("result", 0);
		dataMap.put("code", "");
		dataMap.put("sendtime", "");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String smscode = StringGenerator.SerialNumber(4);
		String smscontent = "";
		if (codeType == 1) {// 注册验证码
			if (accountService.findByUsername(phone) != null) {// 用户名(手机号)存在
				dataMap.put("result", 3);// 手机号已被注册
				dataFillStream(dataMap);
				return "success";
			}
			smscontent = "本次验证码为：" + smscode + "，请在3分钟内完成注册。";
		} else if (codeType == 2) {// 找回密码
			if (accountService.findByUsername(phone) == null) {// 用户名(手机号)不存在
				dataMap.put("result", 6);// 手机号不存在
				dataFillStream(dataMap);
				return "success";
			}
			smscontent = "本次验证码为：" + smscode + "，请在3分钟内完成密码找回。";
		} else {
			dataMap.put("result", 12);// 验证码类型错误
			dataFillStream(dataMap);
			return "success";
		}

		try {
			boolean sendresult = shortMessageService.sendCode(phone, smscode, smscontent);
			if (sendresult) {
				dataMap.put("result", 1);
				dataMap.put("code", smscode);
				dataMap.put("sendtime", sdf.format(new Date()));
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		dataFillStream(dataMap);
		return "success";
	}

	public static void main(String[] args) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("result", 0);
		dataMap.put("result", 1);
		System.out.println(dataMap.get("result"));

	}

	/**
	 * 将Map<String, Object> dataMap转换成json格式,并传递给InputStream
	 * 
	 * @param dataMap
	 */
	private void dataFillStream(Map<String, Object> dataMap) {
		Gson gson = new Gson();
		String jsonStr = gson.toJson(dataMap);
		log.info("App【响应结果】：" + jsonStr);
		try {
			inputStream = new ByteArrayInputStream(jsonStr.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOripwd() {
		return oripwd;
	}

	public void setOripwd(String oripwd) {
		this.oripwd = oripwd;
	}

	public String getNewpwd() {
		return newpwd;
	}

	public void setNewpwd(String newpwd) {
		this.newpwd = newpwd;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getTid() {
		return tid;
	}

	public void setTid(long tid) {
		this.tid = tid;
	}

	public String getResult_str() {
		return result_str;
	}

	public void setResult_str(String resultStr) {
		result_str = resultStr;
	}

	public int getSign() {
		return sign;
	}

	public void setSign(int sign) {
		this.sign = sign;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getJsonStr() {
		return jsonStr;
	}

	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}

	public int getCodeType() {
		return codeType;
	}

	public void setCodeType(int codeType) {
		this.codeType = codeType;
	}

	public String getBackname() {
		return backname;
	}

	public void setBackname(String backname) {
		this.backname = backname;
	}

	public String getBackaccount() {
		return backaccount;
	}

	public void setBackaccount(String backaccount) {
		this.backaccount = backaccount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getMaxresult() {
		return maxresult;
	}

	public void setMaxresult(int maxresult) {
		this.maxresult = maxresult;
	}

	public double getVersion() {
		return version;
	}

	public void setVersion(double version) {
		this.version = version;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
