package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;

public class CarseriesModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String result;
	private List<CarseriesItemModel> list;
//	接口地址=http: //192.168.0.234: 8080/api/server/category/网络访问状况=onSuccess返回消息内容={
//    "result": "001",
//    "list": [
//        {
//            "sctop": 1,
//            "scid": "a4c3ec1d-6a4d-4bdd-aa8a-8323ce862194",
//            "scname": "换轮胎",
//            "scimage": null
//        },
//        {
//            "sctop": 2,
//            "scid": "326b007c-b940-4fa7-be68-9be35157977f",
//            "scname": "洗车",
//            "scimage": null
//        }
//    ]
//}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<CarseriesItemModel> getList() {
		return list;
	}
	public void setList(List<CarseriesItemModel> list) {
		this.list = list;
	}


	
}