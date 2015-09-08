package com.carinsurance.nodes;

import java.util.List;

public class HotCategoryListResp {
	private int resultCode;
	private int totalPageCount;
	private List<GoodNode> datas;

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

	public List<GoodNode> getDatas() {
		return datas;
	}

	public void setDatas(List<GoodNode> datas) {
		this.datas = datas;
	}
}
