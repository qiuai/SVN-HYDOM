package com.hydom.core.ebean;

import java.io.File;

public class Test {
	private static long maxFileSize = 0;
	private static String filePath = "";

	public static void main(String[] args) {
		File dir = new File("G:/Workspaces");
		scan(dir);
		System.out.println(maxFileSize);
		System.out.println(filePath);
	}

	public static String scan(File file) {
		if (file.isFile()) {//
			long fileLen = file.length();
			if (file.getName().contains(".java") && fileLen > maxFileSize) {
				maxFileSize = fileLen;
				filePath = file.getAbsolutePath();
				//System.out.println(maxFileSize);
				//System.out.println(filePath);
			}
		} else {
			File[] files = file.listFiles();
			for (File f : files) {
				scan(f);
			}
		}
		return filePath;
	}
}
