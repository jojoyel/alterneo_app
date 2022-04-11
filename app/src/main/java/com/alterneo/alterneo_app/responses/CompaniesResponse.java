package com.alterneo.alterneo_app.responses;

import java.util.List;

public class CompaniesResponse {

	String total_returned;
	List<CompanyResponse> data;

	public CompaniesResponse(String total_returned, List<CompanyResponse> data) {
		this.total_returned = total_returned;
		this.data = data;
	}

	public String getTotal_returned() {
		return total_returned;
	}

	public void setTotal_returned(String total_returned) {
		this.total_returned = total_returned;
	}

	public List<CompanyResponse> getData() {
		return data;
	}

	public void setData(List<CompanyResponse> data) {
		this.data = data;
	}
}
