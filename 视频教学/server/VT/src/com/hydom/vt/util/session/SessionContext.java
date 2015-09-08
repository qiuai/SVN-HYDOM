package com.hydom.vt.util.session;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.hydom.vt.entity.User;
import com.hydom.vt.util.Constant;

public class SessionContext {
	private static SessionContext instance;
	private HashMap<String, HttpSession> sessionMap;

	private SessionContext() {
		sessionMap = new HashMap<String, HttpSession>();
	}

	public static SessionContext getInstance() {
		if (instance == null) {
			instance = new SessionContext();
		}
		return instance;
	}

	public synchronized void AddSession(HttpSession session) {
		System.out.println(session.getId());
		User user = (User) session.getAttribute(Constant.SESSION_USER);
		HttpSession oldSession = SessionListener.sessionContext.getSession(user
				.getId());
		if (oldSession != null) {
			if (!session.getId().equals(oldSession.getId())) {
				// 注销在线用户
				oldSession.invalidate();
				sessionMap.put(user.getId(), session);
			}
		} else {
			sessionMap.put(user.getId(), session);
		}
	}

	public synchronized void DelSession(HttpSession session) {
		System.out.println("del");
		User user = (User) session.getAttribute(Constant.SESSION_USER);
		if (user != null) {
			sessionMap.remove(user.getId());
		}
	}

	public synchronized HttpSession getSession(String session_id) {
		if (session_id == null)
			return null;
		return (HttpSession) sessionMap.get(session_id);
	}

	public HashMap<String, HttpSession> getSessionMap() {
		return sessionMap;
	}

	public void setMymap(HashMap<String, HttpSession> sessionMap) {
		this.sessionMap = sessionMap;
	}
}
