package com.hydom.util;

/***
 * 优惠券使用异常
 * 
 * @author Administrator
 * 
 */
public class CouponUseExcepton extends Exception {

	private static final long serialVersionUID = 2195606412246438264L;

	public CouponUseExcepton() {
	}

	public CouponUseExcepton(String message) {
		super(message);
	}
}
