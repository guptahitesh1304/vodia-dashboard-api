package com.vodia.api.dashboard.domain1.cdr;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ACD_CDRBeanList {

	private List<ACD_CDRBean> acd_cdr_list;

	public List<ACD_CDRBean> getAcd_cdr_list() {
		return acd_cdr_list;
	}

	public void setAcd_cdr_list(List<ACD_CDRBean> acd_cdr_list) {
		this.acd_cdr_list = acd_cdr_list;
	}
	 
    
 
}
