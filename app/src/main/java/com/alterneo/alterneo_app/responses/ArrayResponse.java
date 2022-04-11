package com.alterneo.alterneo_app.responses;

import java.util.List;

public class ArrayResponse<T extends BasicResponse> {

	String total_returned;

	List<T> data;

	public ArrayResponse(String total_returned, List<T> data) {
		this.total_returned = total_returned;
		this.data = data;
	}

	public String getTotal_returned() {
		return total_returned;
	}

	public void setTotal_returned(String total_returned) {
		this.total_returned = total_returned;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
