package com.alipay.android.msp;

import java.io.Serializable;

public class PayModel implements Serializable{
//	支付宝返回的消息resultStatus={9000};memo={};result={partner="2088021002450046"&out_trade_no="5d27614e-eb9d-43eb-8a29-6a1111ccbea8"&subject="商品信息"&body="商品"&total_fee="0.01"&notify_url="http%3A%2F%2Fqq826552818.imwork.net%3A57702%2Fcbms%2Fweb%2Fpay%2Falipay_return"&service="mobile.securitypay.pay"&_input_charset="UTF-8"&return_url="http%3A%2F%2Fm.alipay.com"&payment_type="1"&seller_id="ydcbab@163.com"&it_b_pay="30m"&success="true"&sign_type="RSA"&sign="GDdCeO1Bpz8zgXDTP7rSKsvsWK6xS7d4WQdMkJxDm9oTksR+WEy/1E8Sk/zg9/i0CCRk10Pp/ocQ17MqZ2kSLP5i0zUZ7E5i/X9Y1QAOP6WmnvxC9uLwr7XTja7/6mRxzxL+qEYQ49heB1u6W17g4F9i1TFKxNMKVV29RiaCqis="}//支付宝返回的msgwhat==》1
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	String resultStatus;
	
}
