package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;

public class BaiduWeatherModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int error;
	private String status;
	private String date;
	private List<BaiduWeatheritem1Model> results;
	
	public BaiduWeatherModel() {
		// TODO Auto-generated constructor stub
	}

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<BaiduWeatheritem1Model> getResults() {
		return results;
	}

	public void setResults(List<BaiduWeatheritem1Model> results) {
		this.results = results;
	}
	
//	list解析出来的数据是---------------------》{
//	    "error": 0,
//	    "status": "success",
//	    "date": "2015-06-23",
//	    "results": [
//	        {
//	            "currentCity": "成都",
//	            "pm25": "68",
//	            "index": [
//	                {
//	                    "title": "穿衣",
//	                    "zs": "舒适",
//	                    "tipt": "穿衣指数",
//	                    "des": "建议着长袖T恤、衬衫加单裤等服装。年老体弱者宜着针织长袖衬衫、马甲和长裤。"
//	                },
//	                {
//	                    "title": "洗车",
//	                    "zs": "不宜",
//	                    "tipt": "洗车指数",
//	                    "des": "不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。"
//	                },
//	                {
//	                    "title": "旅游",
//	                    "zs": "适宜",
//	                    "tipt": "旅游指数",
//	                    "des": "有降水，温度适宜，在细雨中游玩别有一番情调，可不要错过机会呦！但记得出门要携带雨具。"
//	                },
//	                {
//	                    "title": "感冒",
//	                    "zs": "少发",
//	                    "tipt": "感冒指数",
//	                    "des": "各项气象条件适宜，无明显降温过程，发生感冒机率较低。"
//	                },
//	                {
//	                    "title": "运动",
//	                    "zs": "较不宜",
//	                    "tipt": "运动指数",
//	                    "des": "有降水，推荐您在室内进行健身休闲运动；若坚持户外运动，须注意携带雨具并注意避雨防滑。"
//	                },
//	                {
//	                    "title": "紫外线强度",
//	                    "zs": "弱",
//	                    "tipt": "紫外线强度指数",
//	                    "des": "紫外线强度较弱，建议出门前涂擦SPF在12-15之间、PA+的防晒护肤品。"
//	                }
//	            ],
//	            "weather_data": [
//	                {
//	                    "date": "周二 06月23日 (实时：21℃)",
//	                    "dayPictureUrl": "http://api.map.baidu.com/images/weather/day/zhenyu.png",
//	                    "nightPictureUrl": "http://api.map.baidu.com/images/weather/night/zhongyu.png",
//	                    "weather": "阵雨转小到中雨",
//	                    "wind": "北风微风",
//	                    "temperature": "27 ~ 21℃"
//	                },
//	                {
//	                    "date": "周三",
//	                    "dayPictureUrl": "http://api.map.baidu.com/images/weather/day/yin.png",
//	                    "nightPictureUrl": "http://api.map.baidu.com/images/weather/night/zhenyu.png",
//	                    "weather": "阴转阵雨",
//	                    "wind": "北风微风",
//	                    "temperature": "29 ~ 22℃"
//	                },
//	                {
//	                    "date": "周四",
//	                    "dayPictureUrl": "http://api.map.baidu.com/images/weather/day/duoyun.png",
//	                    "nightPictureUrl": "http://api.map.baidu.com/images/weather/night/duoyun.png",
//	                    "weather": "多云",
//	                    "wind": "南风微风",
//	                    "temperature": "31 ~ 23℃"
//	                },
//	                {
//	                    "date": "周五",
//	                    "dayPictureUrl": "http://api.map.baidu.com/images/weather/day/duoyun.png",
//	                    "nightPictureUrl": "http://api.map.baidu.com/images/weather/night/zhenyu.png",
//	                    "weather": "多云转阵雨",
//	                    "wind": "南风微风",
//	                    "temperature": "32 ~ 25℃"
//	                }
//	            ]
//	        }
//	    ]
//	}
}
