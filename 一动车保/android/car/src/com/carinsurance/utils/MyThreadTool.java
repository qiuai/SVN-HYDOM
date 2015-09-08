package com.carinsurance.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyThreadTool {
	// 定义一个线程池，用于加载一些微量数据
	static public ExecutorService fixedThreadPool = Executors
			.newFixedThreadPool(10);
}
