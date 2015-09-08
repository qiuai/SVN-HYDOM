package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;
//@Table(name = "CarType")
public class CarType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String result;
	private List<CarTypeitemModel> list;
//	{
//	    "result": "001",
//	    "list": [
//	        {
//	            "cbid": "973ce43c-dcc7-4a25-bc59-9fc486991506",
//	            "name": "大众",
//	            "fletter": "D",
//	            "cbimage": null
//	        },
//	        {
//	            "cbid": "5",
//	            "name": "福特",
//	            "fletter": "F",
//	            "cbimage": "upload/img/2015/6/3/88cb7f85-9c7c-48f0-9309-e04e8e65617e.jpg"
//	        },
//	        {
//	            "cbid": "3",
//	            "name": "雪佛兰",
//	            "fletter": "X",
//	            "cbimage": null
//	        },
//	        {
//	            "cbid": "2",
//	            "name": "奥迪",
//	            "fletter": "A",
//	            "cbimage": null
//	        },
//	        {
//	            "cbid": "1",
//	            "name": "大众",
//	            "fletter": "D",
//	            "cbimage": null
//	        },
//	        {
//	            "cbid": "048d617f-f94d-4b37-8136-8e95b9ef6d5e",
//	            "name": "汽车",
//	            "fletter": "Q",
//	            "cbimage": null
//	        }
//	    ]
//	}
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
	public List<CarTypeitemModel> getList() {
		return list;
	}
	public void setList(List<CarTypeitemModel> list) {
		this.list = list;
	}
	
	
	
}
