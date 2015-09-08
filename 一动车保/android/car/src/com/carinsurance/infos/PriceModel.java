package com.carinsurance.infos;

import java.io.Serializable;

public class PriceModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String result;
  
	private String ocmoney;
	private String opmoney;
	private String orimoney;
	private String cpmoney;
	private String paymoney;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getOcmoney() {
		return ocmoney;
	}
	public void setOcmoney(String ocmoney) {
		this.ocmoney = ocmoney;
	}
	public String getOpmoney() {
		return opmoney;
	}
	public void setOpmoney(String opmoney) {
		this.opmoney = opmoney;
	}
	public String getOrimoney() {
		return orimoney;
	}
	public void setOrimoney(String orimoney) {
		this.orimoney = orimoney;
	}
	public String getCpmoney() {
		return cpmoney;
	}
	public void setCpmoney(String cpmoney) {
		this.cpmoney = cpmoney;
	}
	public String getPaymoney() {
		return paymoney;
	}
	public void setPaymoney(String paymoney) {
		this.paymoney = paymoney;
	}
	
	
}