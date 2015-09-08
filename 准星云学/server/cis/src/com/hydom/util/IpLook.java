package com.hydom.util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class IpLook {
	private static String sinaURL = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=js&ip=";

	/**
	 * 根据ip得到具体的地址
	 * 
	 * @param ip
	 * @return：
	 */
	public static String sinaIpLookup(String ip) {
		try {
			String url = sinaURL + ip;
			Connection conn = Jsoup.connect(url);
			conn.request().timeout(1000);
			conn.timeout(1000);// 设置超时时间为1秒
			Document doc = conn.post();
			String json = doc.body().text();
			json = json.substring(json.indexOf("{"), json.indexOf("}") + 1);
			return parserSinaLoopkUpIpInfo(json, ip);
		} catch (Exception e) {
			e.printStackTrace();
			return "未知地点(" + ip + ")";
		}
	}

	private static String parserSinaLoopkUpIpInfo(String json, String ip) {
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser.parse(json);
		StringBuffer sb = new StringBuffer();
		if (jsonElement.isJsonObject()) {
			JsonObject JsonObject = (JsonObject) jsonElement;
			IpInfo ipInfo = gson.fromJson(JsonObject, IpInfo.class);
			if (ipInfo != null) {
				if (ipInfo.getProvince() != null && ipInfo.getCity() != null) {
					sb.append(ipInfo.getProvince() + ipInfo.getCity() + "(" + ipInfo.getIsp() + ip
							+ ")");
				} else {
					sb.append("局域网" + ip);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String ip = sinaIpLookup("8.25.65.89");
		System.out.println(ip);

	}

	class IpInfo {
		private String ret;
		private String start;
		private String end;
		private String country;
		private String province;
		private String city;
		private String district;
		private String isp;
		private String type;
		private String desc;

		public String getRet() {
			return ret;
		}

		public void setRet(String ret) {
			this.ret = ret;
		}

		public String getStart() {
			return start;
		}

		public void setStart(String start) {
			this.start = start;
		}

		public String getEnd() {
			return end;
		}

		public void setEnd(String end) {
			this.end = end;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getDistrict() {
			return district;
		}

		public void setDistrict(String district) {
			this.district = district;
		}

		public String getIsp() {
			return isp;
		}

		public void setIsp(String isp) {
			this.isp = isp;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
}
