package com.carinsurance.infos;

import java.io.Serializable;

//@Table(name = "MyCarInfosModel")
public class MyCarInfosModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String id;
    private String result;
    private String ucid;//用户车辆id
    private String defaultCar;//是否是默认车辆//    default=1 默认车辆//    default=0 非默认车辆

    private String color;//颜色
    private String plateNumber;//车牌号
    private String fuel;//油耗 单位L 
    
    private String drange;//行驶里程 单位km
    private String engines;//排量
    
    private String date;//上路日期 格式示例：2015-08-19
    
    private String cmid;//车型ID
    private String cmname;//车型名称
    private String csid;//车系ID
    private String csname;//车系名称
    private String cbid;//车辆品牌ID
    private String cbname;//车辆品牌名称
    private String cbimage;
    
    
	public String getCbimage() {
		return cbimage;
	}
	public void setCbimage(String cbimage) {
		this.cbimage = cbimage;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getPlateNumber() {
		return plateNumber;
	}
	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}
	public String getUcid() {
		return ucid;
	}
	public void setUcid(String ucid) {
		this.ucid = ucid;
	}
	public String getDefaultCar() {
		return defaultCar;
	}
	public void setDefaultCar(String defaultCar) {
		this.defaultCar = defaultCar;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getFuel() {
		return fuel;
	}
	public void setFuel(String fuel) {
		this.fuel = fuel;
	}
	public String getDrange() {
		return drange;
	}
	public void setDrange(String drange) {
		this.drange = drange;
	}
	public String getEngines() {
		return engines;
	}
	public void setEngines(String engines) {
		this.engines = engines;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getCmid() {
		return cmid;
	}
	public void setCmid(String cmid) {
		this.cmid = cmid;
	}
	public String getCmname() {
		return cmname;
	}
	public void setCmname(String cmname) {
		this.cmname = cmname;
	}
	public String getCsid() {
		return csid;
	}
	public void setCsid(String csid) {
		this.csid = csid;
	}
	public String getCsname() {
		return csname;
	}
	public void setCsname(String csname) {
		this.csname = csname;
	}
	public String getCbid() {
		return cbid;
	}
	public void setCbid(String cbid) {
		this.cbid = cbid;
	}
	public String getCbname() {
		return cbname;
	}
	public void setCbname(String cbname) {
		this.cbname = cbname;
	}
	
	public boolean isDefaultCar(){
		if(getDefaultCar() == null || getDefaultCar().equals("0")){
			return false;
		}else{
			return true;
		}
	}
}
