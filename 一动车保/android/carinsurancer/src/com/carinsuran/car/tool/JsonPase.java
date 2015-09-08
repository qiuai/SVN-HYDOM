package com.carinsuran.car.tool;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.carinsuran.car.model.Login;
import com.carinsuran.car.model.Order;
import com.carinsuran.car.model.OrderBuss;
import com.carinsuran.car.model.Orderbussion;

public class JsonPase {

	public static Login getLogin(String json) {
		// TODO Auto-generated method stub
		try {
			Login login = new Login();
			JSONObject jsonObject = new JSONObject(json);
			login.setResult(jsonObject.getString("result"));
			login.setId(jsonObject.getString("id"));
			return login;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    /**
     * 返回的订单
     * @param json
     * @return
     */
	public static Order getOrder(String json) {
		// TODO Auto-generated method stub
		Order order = new Order();
		try {
			order=JsonUtil.getEntityByJsonString(json, Order.class);
			return order;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 订单列表
	 * @param json
	 * @return
	 */
	public static List<Orderbussion> getNear(String json) {
		// TODO Auto-generated method stub
		List<Orderbussion> orderbus = new ArrayList<Orderbussion>();
		
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONArray jsonArray = jsonObject.getJSONArray("orders");
			for (int i = 0; i < jsonArray.length(); i++){
				JSONObject item = jsonArray.getJSONObject(i);
				Orderbussion orderbussion = new Orderbussion();
				orderbussion.setOrderState(item.getInt("orderState"));
				orderbussion.setOrdernum(item.getString("orderNum"));
				orderbussion.setOrderId(item.getString("orderId"));
				orderbussion.setCleanType(item.getInt("cleanType"));
				orderbussion.setStartDate(item.getString("startDate"));
				orderbus.add(orderbussion);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderbus;
	}
	
	
	public static OrderBuss getOrderBuss(String json) {
		// TODO Auto-generated method stub
		OrderBuss orderBuss = new OrderBuss();
		try {
			JSONObject jsonObject = new JSONObject(json);
			orderBuss.setResult(jsonObject.getString("result"));
			orderBuss.setOrdernum(jsonObject.getString("orderNum"));
			orderBuss.setContact(jsonObject.getString("contact"));
			orderBuss.setPhone(jsonObject.getString("phone"));
			orderBuss.setCar(jsonObject.getString("car"));
			orderBuss.setCarNum(jsonObject.getString("carNum"));  //车牌号
			orderBuss.setCarColor(jsonObject.getString("carColor")); //汽车颜色
			orderBuss.setCleanType(jsonObject.getInt("cleanType"));
			orderBuss.setDistance(jsonObject.getDouble("distance"));
			orderBuss.setStartDate(jsonObject.getString("startDate"));
			orderBuss.setMlng(jsonObject.getDouble("mlng"));
			orderBuss.setMlat(jsonObject.getDouble("mlat"));
			
			JSONObject jsonObjectData = jsonObject.getJSONObject("afterImgs");
			orderBuss.setImageUrl1(jsonObjectData.getString("imgUrl0"));
			orderBuss.setImageUrl2(jsonObjectData.getString("imgUrl1"));
			orderBuss.setImageUrl3(jsonObjectData.getString("imgUrl2"));
			
		    JSONObject jsonObjectDatar = jsonObject.getJSONObject("beforeImgs");
		    orderBuss.setImageUrl4(jsonObjectDatar.getString("imgUrl0"));
			orderBuss.setImageUrl5(jsonObjectDatar.getString("imgUrl1"));
			orderBuss.setImageUrl6(jsonObjectDatar.getString("imgUrl2"));
			
			return orderBuss;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return orderBuss;
	}

	
	
	
}
