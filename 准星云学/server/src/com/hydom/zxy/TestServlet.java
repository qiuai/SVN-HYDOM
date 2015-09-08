package com.hydom.zxy;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class TestServlet
 */
//@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public TestServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Enumeration<String> names = request.getParameterNames();
		System.out.println("================" + new Date() + "=================");
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			System.out.println(name + ":" + request.getParameter(name));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Enumeration<String> names = request.getParameterNames();
		String url = "http://localhost/server/up_notify_url.aspx?";
		System.out.println("================" + new Date() + "=================");
		int index = 0;
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			System.out.println(name + ":" + request.getParameter(name));
			if (index != 0) {
				url += "&";
			}
			
			url += name + "=" +  request.getParameter(name);
			
			index ++;
		}
		
		System.out.println(url);
	}

}
