package com.hydom.util.duanxin;


public class SendMessage {
	
	private static String url = "http://www.duanxin10086.com/sms.aspx";
	private static String userid = "11760";
	private static String accont = "一动车保";
	private static String password = "123456";
	
	/**
	 * true 表示发送成功 false 表示发送失败
	 * @param phone
	 * @param content
	 * @return
	 */
	public static Boolean sendMessage(String phone,String content){
			/*String send = SmsClientAccessTool.getInstance().doAccessHTTPPost(
					"http://www.duanxin10086.com/sms.aspx", "action=send&userid=11760&account=一动车保&password=123456&mobile="+phone+"&content="+content+"&sendTime=&checkcontent=0", "UTF-8");
			System.out.println(send);*/
			
			String mm = SmsClientSend.sendSms(url, userid, accont, password, phone, content);
			if (mm.contains("<returnstatus>Success</returnstatus>")
					&& mm.contains("<message>ok</message>")) {
				return true;
			} 
			return false;
	}
	
	public static void main(String[] args) {
		sendMessage("13547808440","1111");
	}
}
