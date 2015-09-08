package com.carinsurance.infos;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author Administrator
 *
 */
public class SeriverTypeModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String result;
	private List<SeriverTypeitemModel> list;
	private List<SeriverTypeitemModel> sclist;//和list一样，只是接口不同
	private List<ImgModel> adlist;//
	private List<BModel> blist;//品牌推荐
	
	private List<PcModel> pclist;//热卖分类
	
	
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
	
	
	@Override
	public String toString() {
		return "SeriverTypeModel [id=" + id + ", result=" + result + ", list=" + list + ", sclist=" + sclist + ", adlist=" + adlist + ", blist=" + blist + ", pclist=" + pclist + "]";
	}
	public String getResult() {
		return result;
	}
	public List<SeriverTypeitemModel> getSclist() {
		return sclist;
	}
	public void setSclist(List<SeriverTypeitemModel> sclist) {
		this.sclist = sclist;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public List<SeriverTypeitemModel> getList() {
		return list;
	}
	public void setList(List<SeriverTypeitemModel> list) {
		this.list = list;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<ImgModel> getAdlist() {
		return adlist;
	}
	public void setAdlist(List<ImgModel> adlist) {
		this.adlist = adlist;
	}
	public List<BModel> getBlist() {
		return blist;
	}
	public void setBlist(List<BModel> blist) {
		this.blist = blist;
	}
	public List<PcModel> getPclist() {
		return pclist;
	}
	public void setPclist(List<PcModel> pclist) {
		this.pclist = pclist;
	}

	
}