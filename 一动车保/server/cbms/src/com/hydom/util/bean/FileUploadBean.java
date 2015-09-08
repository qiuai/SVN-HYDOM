package com.hydom.util.bean;

import org.springframework.web.multipart.MultipartFile;


public class FileUploadBean {
	
	private MultipartFile specificationFiles;
	private String specificationNames;
	private Integer specificationOrders;
	
	public MultipartFile getSpecificationFiles() {
		return specificationFiles;
	}
	public void setSpecificationFiles(MultipartFile specificationFiles) {
		this.specificationFiles = specificationFiles;
	}
	public String getSpecificationNames() {
		return specificationNames;
	}
	public void setSpecificationNames(String specificationNames) {
		this.specificationNames = specificationNames;
	}
	public Integer getSpecificationOrders() {
		return specificationOrders;
	}
	public void setSpecificationOrders(Integer specificationOrders) {
		this.specificationOrders = specificationOrders;
	}
	
	
}
