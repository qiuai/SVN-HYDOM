package com.hydom.vt.util.session;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

	public static SessionContext sessionContext = SessionContext.getInstance();

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
	//	System.out.println("sessionCreated");
	//	sessionContext.AddSession(httpSessionEvent.getSession());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
//		System.out.println("sessionDestroyed");
//		sessionContext.DelSession(httpSessionEvent.getSession());
	}

}
