package com.test;

import java.util.List;

import javax.annotation.Resource;

import lombok.extern.log4j.Log4j;

import com.hydom.common.service.TestService;
import com.hydom.vt.entity.Test;

@Log4j
public class DBTest extends BaseTestCase {

	@Resource
	private TestService testService;

	// @Override
	// @BeforeClass
	// public void init() {
	// super.init();
	// testService = (TestService) context.getBean("testService");
	// }
	//
	@org.junit.Test
	public void testSearch() {
		System.out.println("lllllllllll");
		List<Test> list = testService.getAll();
		try {
			for (Test test : list) {
				System.out.println("id:" + test.getId());
				System.out.println("name:" + test.getName());
				System.out.println("enabled:" + test.getEnabled());
			}
		} catch (Exception e) {
		}
	}

	// @org.junit.Test
	public void save() {
		Test test = new Test();
		test.setName("Test2");
		test.setRemark("junit 测试添加.");
		testService.save(test);
	}
}
