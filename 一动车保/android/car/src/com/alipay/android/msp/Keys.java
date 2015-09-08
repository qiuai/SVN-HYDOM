/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 * 
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“商家服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */

package com.alipay.android.msp;

//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到商家服务器端去进行。
public final class Keys {
	// 合作身份者id，以2088开头的16位纯数字
	public static final String DEFAULT_PARTNER = "2088021002450046";

	// 收款支付宝账号
	public static final String DEFAULT_SELLER = "ydcbab@163.com";//
    //公钥
	public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC90hcNcZO3Hc4osE+QSpW5ByfN95Ein6QopLYeHSSsKcEB+V55dXOGhM/bwAmh4IzBZVBwmARlDAfw477grMFQ23Sh5yKlHfJlLEIfZryPN3uetksTS9mLi/fvpfoUpvG5sITbqJLtLtdrk9rZ7ENU209dluGXLPohwh6JvugfWQIDAQAB";

	//私钥是pcs8加密后的私钥
	public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAL3SFw1xk7cdziiwT5BKlbkHJ833kSKfpCikth4dJKwpwQH5Xnl1c4aEz9vACaHgjMFlUHCYBGUMB/DjvuCswVDbdKHnIqUd8mUsQh9mvI83e562SxNL2YuL9++l+hSm8bmwhNuoku0u12uT2tnsQ1TbT12W4Zcs+iHCHom+6B9ZAgMBAAECgYEApcZb8Za2TZ7PFAPeiIJKvdu87IkADH/lKsWmcyg6hcy1TdcNpf9oFBvbN+w/vUrRQnkLxjlM5T6blMohQjA9ZrDwbP7logCHMToshSOAHnDcQSrjgs1FEi77LRpWpJiLwMnaVwdjjMQYP3MPujORql5ra6HEe103uXgt/Iz78DECQQDur3KJA7hKt12zUqqhGkWIDNWW+ftaZ2aad2dQqLgpvsJ97p+jmzedmSwM09donypAAlP/iMfo3DZ/mJJfIRMlAkEAy5cxD7gOT+nDou+cfYJTV5WHXyiG16krxeifdbKNAASCftIKZkZl93yOXi5cOBizYlT8QclUufl0KK1wms1/JQJARsZddwVUW0teDHNhxx2MKphrqTX880Sf5wOq7f7phO9cqozcZ136MtAdgLw8LnirxYkrMSV06baKrnEmTfD3xQJBAJjH3CR8rhjgR1UV1W0GiT6X0t/hTNe4d0XsnQW5OUDDIZ7ERtObjtebnEcnKUNbnfpz5l4EFVX+0mHYTkGNZxUCQQC6rSJNnAUUl2k0/H3kK07rSM5Vu9E0b3lB8SC3j1hbZEd3jsAWUJ+Iw8pdPPfpQF6bvAHFOcbet2d6IDrCoF6W";
	
	
	
	
}
