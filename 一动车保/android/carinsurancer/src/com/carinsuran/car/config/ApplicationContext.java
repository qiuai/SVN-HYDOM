package com.carinsuran.car.config;

public class ApplicationContext {
          
	private static String  currentURL;

	public static String getCurrentURL() {
		return currentURL;
	}

	public static void setCurrentURL(String currentURL) {
		ApplicationContext.currentURL = currentURL;
	}
	
	
}
