//package com.hydom.zxy.controller;
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.hydom.common.AbstractController;
//import com.hydom.zxy.entity.Music;
//import com.hydom.zxy.service.PersionEventService;
//import com.hydom.zxy.service.TestService;
//
//@Controller
//public class TestController extends AbstractController {
//
//	@Resource
//	private TestService testService;
//	
//	@Resource
//	private PersionEventService persionEventService;
//	
//	@RequestMapping(value="/test/list", method = RequestMethod.GET)
//	public String list(Model model) {
////		System.out.println("查询所有Music.");
////		List<Music> list = testService.getAll();
////		model.addAttribute("list", list);
//		
//		persionEventService.getAll();
//		return "/test/test_list";
//	}
//	
//	@RequestMapping(value="/test/exception", method = RequestMethod.GET)
//	public String exception() throws Exception {
//		throw new Exception();
//	}
//}
