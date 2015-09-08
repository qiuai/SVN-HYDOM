package com.hydom.util.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hydom.util.CommonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 保养订单服务bean
 * 
 * @author Administrator
 * 
 */
public class ServerOrderBean implements Serializable {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 919684707138432850L;

	private String serverId;

	private String serverName;

	private String serverPrice;

	private List<ServerOrderProductBean> serverOrderProductBeans;

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerPrice() {
		return serverPrice;
	}

	public void setServerPrice(String serverPrice) {
		this.serverPrice = serverPrice;
	}

	public List<ServerOrderProductBean> getServerOrderProductBeans() {
		return serverOrderProductBeans;
	}

	public void setServerOrderProductBeans(
			List<ServerOrderProductBean> serverOrderProductBeans) {
		this.serverOrderProductBeans = serverOrderProductBeans;
	}

	public static List<ServerOrderBean> conver2Bean(String content) {
		List<ServerOrderBean> beas = new ArrayList<ServerOrderBean>();
		try {
			JSONArray array = JSONArray.fromObject(content);
			// JSONArray array = obj.getJSONArray("server");
			for (int i = 0; i < array.size(); i++) {

				JSONObject server = array.getJSONObject(i);
				ServerOrderBean serverOrderBean = new ServerOrderBean();
				serverOrderBean.setServerId(server.getString("serverId"));
				serverOrderBean.setServerName(server.getString("serverName"));
				serverOrderBean.setServerPrice(server.getString("serverPrice"));

				/*
				 * id:"d91099f3-8c03-4d35-bb54-fe473be39516",//ID
				 * name:"32323",//名字 count:"1",//数量 price:"323",//单价 sum:"323"
				 */

				List<ServerOrderProductBean> productBeans = new ArrayList<ServerOrderProductBean>();
				JSONArray productArray = server.getJSONArray("products");
				for (int n = 0; n < productArray.size(); n++) {
					try {
						JSONObject productObj = productArray.getJSONObject(n);
						ServerOrderProductBean productBean = serverOrderBean.new ServerOrderProductBean();
						productBean.setId(productObj.getString("id"));

						String count = productObj.getString("count");
						String price = productObj.getString("price");

						productBean.setCount(count);
						productBean.setName(productObj.getString("name"));
						productBean.setPrice(price);

						Float sum = CommonUtil.mul(count, price);
						if (sum < 0) {
							continue;
						}
						productBean.setSum(sum + "");
						productBeans.add(productBean);
					} catch (Exception e) {
						continue;
					}

				}
				serverOrderBean.setServerOrderProductBeans(productBeans);
				beas.add(serverOrderBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return beas;
	}

	// 获取服务费 跟 产品费用
	public static Map<String, String> getSum(List<ServerOrderBean> beans) {
		Map<String, String> map = new HashMap<String, String>();
		Float productSum = 0f;// 商品总计
		Float serverSum = 0f;// 服务总计

		for (ServerOrderBean bean : beans) {
			serverSum = CommonUtil.add(serverSum + "", bean.getServerPrice());
			for (ServerOrderProductBean productBean : bean
					.getServerOrderProductBeans()) {
				productSum = CommonUtil.add(productSum + "",
						productBean.getSum());
			}
		}

		map.put("productSum", productSum + "");
		map.put("serverSum", serverSum + "");

		return map;
	}

	// 获取商品ID集合
	public static List<String> getProductIds(List<ServerOrderBean> beans) {
		List<String> productIds = new ArrayList<String>();
		for (ServerOrderBean bean : beans) {
			for (ServerOrderProductBean productBean : bean
					.getServerOrderProductBeans()) {
				productIds.add(productBean.getId());
			}
		}
		return productIds;
	}

	public class ServerOrderProductBean {
		private String id;
		private String name;
		private String price;
		private String count;
		private String sum;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPrice() {
			return price;
		}

		public void setPrice(String price) {
			this.price = price;
		}

		public String getCount() {
			return count;
		}

		public void setCount(String count) {
			this.count = count;
		}

		public String getSum() {
			return sum;
		}

		public void setSum(String sum) {
			this.sum = sum;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

	}

}
