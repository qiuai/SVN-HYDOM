package com.hydom.extra.action;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.hydom.dao.PageView;
import com.hydom.extra.ebean.Message;
import com.hydom.extra.service.MessageService;
import com.hydom.server.PushServer;

@Controller
@Scope(value = "prototype")
public class MessageAction {
	@Resource
	private MessageService messageService;
	private Message message;
	private int page = 1;
	private int maxresult = 10;
	private int m = 4;// 识别选中导航菜单
	private long msgId;
	private HttpServletRequest request;
	private InputStream inputStream;

	public String list() {
		request = ServletActionContext.getRequest();
		PageView<Message> pageView = new PageView<Message>(maxresult, page);
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		orderby.put("id", "desc");
		StringBuffer jpql = new StringBuffer("o.visible=?1 ");
		List<Object> params = new ArrayList<Object>();
		params.add(true);
		pageView.setQueryResult(messageService.getScrollData(pageView.getFirstResult(),
				maxresult, jpql.toString(), params.toArray(), orderby));
		request.setAttribute("pageView", pageView);
		return "success";
	}

	public String add() {
		message.setIssueTime(new Date());
		messageService.save(message);
		PushServer.sendPush(message.getTitle(), message.getContent(), message
				.getPushTimeToLive());
		return "success";
	}

	public String delete() {
		try {
			Message entitiy = messageService.find(msgId);
			entitiy.setVisible(false);
			messageService.update(entitiy);
			inputStream = new ByteArrayInputStream("1".getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
		}
		return "success";
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public long getMsgId() {
		return msgId;
	}

	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
