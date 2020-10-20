package com.vodia.api.dashboard.domain1.summary;

public class DashboardSummary {
	
	private Integer totalInboundCalls;
	private Integer totalOutboundCalls;
	private Integer totalCallRecieved;
	private Integer totalCallAnswered;
	private Integer totalCallInQueue;
	private String totalATT;
	private String totalAWT;
	private String longestAHT;
	private String longestAWT;
	private Integer totalCallAbnd;
	private Integer totalMissedCalls;
	
	
	
	public Integer getTotalMissedCalls() {
		return totalMissedCalls;
	}
	public void setTotalMissedCalls(Integer totalMissedCalls) {
		this.totalMissedCalls = totalMissedCalls;
	}
	public Integer getTotalCallAbnd() {
		return totalCallAbnd;
	}
	public void setTotalCallAbnd(Integer totalCallAbnd) {
		this.totalCallAbnd = totalCallAbnd;
	}
	public Integer getTotalInboundCalls() {
		return totalInboundCalls;
	}
	public void setTotalInboundCalls(Integer totalInboundCalls) {
		this.totalInboundCalls = totalInboundCalls;
	}
	public Integer getTotalOutboundCalls() {
		return totalOutboundCalls;
	}
	public void setTotalOutboundCalls(Integer totalOutboundCalls) {
		this.totalOutboundCalls = totalOutboundCalls;
	}
	public Integer getTotalCallRecieved() {
		return totalCallRecieved;
	}
	public void setTotalCallRecieved(Integer totalCallRecieved) {
		this.totalCallRecieved = totalCallRecieved;
	}
	public Integer getTotalCallAnswered() {
		return totalCallAnswered;
	}
	public void setTotalCallAnswered(Integer totalCallAnswered) {
		this.totalCallAnswered = totalCallAnswered;
	}
	public Integer getTotalCallInQueue() {
		return totalCallInQueue;
	}
	public void setTotalCallInQueue(Integer totalCallInQueue) {
		this.totalCallInQueue = totalCallInQueue;
	}
	public String getTotalATT() {
		return totalATT;
	}
	public void setTotalATT(String totalATT) {
		this.totalATT = totalATT;
	}
	public String getTotalAWT() {
		return totalAWT;
	}
	public void setTotalAWT(String totalAWT) {
		this.totalAWT = totalAWT;
	}
	public String getLongestAHT() {
		return longestAHT;
	}
	public void setLongestAHT(String longestAHT) {
		this.longestAHT = longestAHT;
	}
	public String getLongestAWT() {
		return longestAWT;
	}
	public void setLongestAWT(String longestAWT) {
		this.longestAWT = longestAWT;
	}
	public DashboardSummary() {
		
	}
	
	

}
