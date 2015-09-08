package com.test;
/**
 * 
 */


import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author rl
 * @version 1.0.0 {date} 新建
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext*.xml"})
public class BaseTestCase extends TestCase {
//
//	protected ApplicationContext context;
//	
//	 @BeforeClass 
//	public void init() { 
//		context = new ClassPathXmlApplicationContext("classpath:applicationContext*.xml"); 
//	} 	
}
