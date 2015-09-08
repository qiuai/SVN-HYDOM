package com.hydom.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SvgImage {

	private String data; // 原始JSON数据
	// 解析出的数据：<line x1="234" y1="1346" x2="232" y2="1347" ... /> ...
	private String svgdata;
	private int minX = 100000;// 最小的x
	private int minY = 100000;// 最小的y

	/**
	 * 
	 * @param data
	 *            ：原始数据
	 */
	public SvgImage(String data) {
		this(data, 99, 99, 99, 2, false);
	}

	/**
	 * 
	 * @param data
	 *            ：原始数据
	 * @param r
	 *            ：rgb的r值
	 * @param g
	 *            ：rgb的g值
	 * @param b
	 *            ：rgb的b值
	 * @param w
	 *            ：stroke-width的值
	 * @param random
	 *            ：是否随机rgb
	 */
	public SvgImage(String data, int r, int g, int b, int w, boolean random) {
		String rgb = "(" + r + "," + g + "," + b + ")";
		StringBuffer result = new StringBuffer();
		String[] arys = data.split(",\\{x=-1.0, y=0.0\\}");
		for (String ary : arys) {// 每笔
			if (ary.startsWith(",")) {
				ary = ary.replaceFirst(",", "");
			}
			// System.out.println("每笔："+ary);
			String[] ss = ary.split("\\},");
			String p1 = ss[0]; // .replace("{x=", "").replace("y=",
			// "").replace("}", "");
			for (int i = 1; i < ss.length; i++) {// 点
				if (random) {// 随机rgb
					rgb = "(" + this.random(1, 255) + "," + this.random(1, 255) + ","
							+ this.random(1, 255) + ")";
				}
				String p2 = ss[i];// .replace("{x=", "").replace("y=",
				// "").replace("}", "");
				Map<String, Integer> xy1 = xy(p1);
				Map<String, Integer> xy2 = xy(p2);
				int x1 = xy1.get("x");
				int y1 = xy1.get("y");
				int x2 = xy2.get("x");
				int y2 = xy2.get("y");
				if (x1 != -1 && x2 != -1 && y1 != 0 && y2 != 0) {
					String line = "<line x1=\"" + x1 + "\" y1=\"" + y1 + "\"" + " x2=\""
							+ x2 + "\" y2=\"" + y2 + "\"  style=\"stroke:rgb" + rgb
							+ ";stroke-width:" + w + "\" />";
					result.append(line);

					if (x1 < this.getMinX()) {
						this.setMinX(x1);
					}
					if (x2 < this.getMinX()) {
						this.setMinX(x2);
					}

					if (y1 < this.getMinY()) {
						this.setMinY(y1);
					}
					if (y2 < this.getMinY()) {
						this.setMinY(y2);
					}
				}
				p1 = p2;
			}
		}
		this.setSvgdata(result.toString());
	}

	public static Map<String, Integer> xy(String str) {
		// String str = "{x=1083.0, y=1151.0";
		// String str = "{y=1083.0, x=1151.0";
		Map<String, Integer> map = new HashMap<String, Integer>();
		str = str.replace("}", "");
		int xi = str.indexOf("x=");
		int i = str.indexOf(",");
		int yi = str.indexOf("y=");
		if (xi < i) {
			String x = str.substring(xi + 2, i);
			String y = str.substring(yi + 2);
			map.put("x", Math.round(Float.parseFloat(x)));
			map.put("y", Math.round(Float.parseFloat(y)));
		} else {
			String x = str.substring(xi + 2);
			String y = str.substring(yi + 2, i);
			map.put("x", Math.round(Float.parseFloat(x)));
			map.put("y", Math.round(Float.parseFloat(y)));
		}
		return map;
	}

	/**
	 * 生成指定范围的随机数理
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public int random(int min, int max) {
		Random random = new Random();
		return random.nextInt(max) % (max - min + 1) + min;
	}

	public static void main(String[] args) {
		String data = "{x=1083.0, y=1151.0},{x=1083.0, y=1150.0},{x=1085.0, y=1148.0},{x=1090.0, y=1148.0},{x=1092.0, y=1150.0},{x=1093.0, y=1154.0},{x=1092.0, y=1160.0},{x=1086.0, y=1173.0},{x=1082.0, y=1177.0},{x=1078.0, y=1178.0},{x=1077.0, y=1177.0},{x=1077.0, y=1175.0},{x=1079.0, y=1173.0},{x=1080.0, y=1172.0},{x=1087.0, y=1172.0},{x=1089.0, y=1170.0},{x=1091.0, y=1168.0},{x=-1.0, y=0.0},{x=1095.0, y=1156.0},{x=1096.0, y=1154.0},{x=1096.0, y=1151.0},{x=1097.0, y=1149.0},{x=1103.0, y=1139.0},{x=1103.0, y=1148.0},{x=1108.0, y=1174.0},{x=1109.0, y=1176.0},{x=1109.0, y=1175.0},{x=1109.0, y=1167.0},{x=-1.0, y=0.0},{x=1110.0, y=1145.0},{x=1110.0, y=1146.0},{x=1109.0, y=1147.0},{x=1105.0, y=1153.0},{x=1100.0, y=1165.0},{x=1098.0, y=1167.0},{x=-1.0, y=0.0},{x=1103.0, y=1160.0},{x=1106.0, y=1159.0},{x=1119.0, y=1155.0},{x=1122.0, y=1154.0},{x=1123.0, y=1152.0},{x=-1.0, y=0.0},{x=1117.0, y=1147.0},{x=1117.0, y=1147.0},{x=1117.0, y=1148.0},{x=1116.0, y=1160.0},{x=1116.0, y=1163.0},{x=1116.0, y=1166.0},{x=-1.0, y=0.0},{x=1120.0, y=1147.0},{x=1121.0, y=1146.0},{x=1123.0, y=1145.0},{x=1129.0, y=1143.0},{x=1134.0, y=1141.0},{x=1130.0, y=1161.0},{x=1129.0, y=1183.0},{x=1129.0, y=1179.0},{x=1129.0, y=1174.0},{x=-1.0, y=0.0},{x=1140.0, y=1151.0},{x=1140.0, y=1152.0},{x=1145.0, y=1155.0},{x=1149.0, y=1156.0},{x=1152.0, y=1156.0},{x=1142.0, y=1170.0},{x=1141.0, y=1172.0},{x=1141.0, y=1171.0},{x=1143.0, y=1166.0},{x=-1.0, y=0.0},{x=1156.0, y=1141.0},{x=1157.0, y=1140.0},{x=1158.0, y=1140.0},{x=1162.0, y=1141.0},{x=1166.0, y=1145.0},{x=1164.0, y=1151.0},{x=1161.0, y=1154.0},{x=1158.0, y=1156.0},{x=1157.0, y=1157.0},{x=1166.0, y=1153.0},{x=1167.0, y=1153.0},{x=1168.0, y=1155.0},{x=1167.0, y=1160.0},{x=1164.0, y=1165.0},{x=1159.0, y=1172.0},{x=1157.0, y=1173.0},{x=1157.0, y=1168.0},{x=-1.0, y=0.0},{x=1171.0, y=1148.0},{x=1172.0, y=1146.0},{x=1174.0, y=1143.0},{x=1179.0, y=1134.0},{x=1180.0, y=1155.0},{x=1181.0, y=1163.0},{x=1183.0, y=1173.0},{x=1184.0, y=1170.0},{x=1184.0, y=1163.0},{x=-1.0, y=0.0},{x=1185.0, y=1144.0},{x=1184.0, y=1144.0},{x=1171.0, y=1165.0},{x=1168.0, y=1169.0},{x=1167.0, y=1171.0},{x=-1.0, y=0.0},{x=1191.0, y=1151.0},{x=1193.0, y=1151.0},{x=1197.0, y=1149.0},{x=1203.0, y=1149.0},{x=-1.0, y=0.0},{x=1210.0, y=1137.0},{x=1211.0, y=1137.0},{x=1211.0, y=1143.0},{x=1210.0, y=1154.0},{x=1210.0, y=1160.0},{x=1209.0, y=1165.0},{x=1206.0, y=1168.0},{x=-1.0, y=0.0},{x=1234.0, y=1135.0},{x=1233.0, y=1135.0},{x=1233.0, y=1135.0},{x=1231.0, y=1141.0},{x=1229.0, y=1155.0},{x=1229.0, y=1162.0},{x=1230.0, y=1166.0},{x=1233.0, y=1168.0},{x=1241.0, y=1161.0},{x=1245.0, y=1153.0},{x=1248.0, y=1148.0},{x=1248.0, y=1145.0},{x=1246.0, y=1143.0},{x=1242.0, y=1143.0},{x=-1.0, y=0.0},{x=1240.0, y=1144.0},{x=1241.0, y=1144.0},{x=1241.0, y=1146.0},{x=1237.0, y=1159.0},{x=1236.0, y=1163.0},{x=1236.0, y=1168.0},{x=-1.0, y=0.0}";
		SvgImage svg = new SvgImage(data);
		System.out.println(svg.getSvgdata());
		System.out.println(svg.getMinX());
		System.out.println(svg.getMinY());
		// String data2 =
		// "{x=1083.0, y=1151.0},{x=1083.0, y=1150.0},{x=-1.0, y=0.0},{x=1083.0, y=1150.0}";
		// data2.split("\\{x=-1.0, y=0.0\\}") ;
	}

	public String getData() {
		return data;
	}

	public String getSvgdata() {
		return svgdata;
	}

	public void setSvgdata(String svgdata) {
		this.svgdata = svgdata;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getMinX() {
		return minX;
	}

	public void setMinX(int minX) {
		this.minX = minX;
	}

	public int getMinY() {
		return minY;
	}

	public void setMinY(int minY) {
		this.minY = minY;
	}

}
