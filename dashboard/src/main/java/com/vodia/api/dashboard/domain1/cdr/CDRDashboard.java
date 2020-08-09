package com.vodia.api.dashboard.domain1.cdr;

import java.util.List;

public class CDRDashboard {

	private String id;
	private String call_id;
	private String from;
	private String to;
	private String start;
	private String connect;
	private String end;
	private String extensions;
	private String trunks;
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
	public String getExtensions() {
		return extensions;
	}
	public void setExtensions(String extensions) {
		this.extensions = extensions;
	}
	public String getTrunks() {
		return trunks;
	}
	public void setTrunks(String trunks) {
		this.trunks = trunks;
	}
	public CDRDashboard() {
	}
	
	
}
