package com.vodia.api.dashboard.domain1.cdr;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//{"id":20753,"call_id":"1490779371@10.254.43.9","from":"\"099652200\" <sip:099652200@compassoffices.ak1.cloudpbx.net.nz>",
//"to":"\"6499655250\" <sip:6499655250@compassoffices.ak1.cloudpbx.net.nz>",
//"start":"1595572559.444","connect":"1595572559.445","end":"1595572656.860",
//"rating":"","pcap":false,"extensions":[],"trunks":[106]},
@JsonIgnoreProperties(ignoreUnknown = true)
public class ACD_CDRBean {
	
	private String id;
	private String call_id;
	private String from;
	private String to;
	private String start;
	private String connect;
	private String end;
	private List<Integer> extensions;
	private List<String> trunks;
	
	
	public ACD_CDRBean() {}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getCall_id() {
		return call_id;
	}


	public void setCall_id(String call_id) {
		this.call_id = call_id;
	}


	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}


	public String getStart() {
		return start;
	}


	public void setStart(String start) {
		this.start = start;
	}


	public String getConnect() {
		return connect;
	}


	public void setConnect(String connect) {
		this.connect = connect;
	}


	public String getEnd() {
		return end;
	}


	public void setEnd(String end) {
		this.end = end;
	}


	public List<Integer> getExtensions() {
		return extensions;
	}


	public void setExtensions(List<Integer> extensions) {
		this.extensions = extensions;
	}


	public List<String> getTrunks() {
		return trunks;
	}


	public void setTrunks(List<String> trunks) {
		this.trunks = trunks;
	}
	
}
