package com.vodia.api.dashboard.domain1.summary;

public class DashboardSummary {
	
	private Integer totalInboundCalls;
	private Integer totalOutboundCalls;
	private Integer totalCallRecieved;
	private Integer totalCallAnswered;
	private Integer totalCallInQueue;
	private Integer totalATT;
	private Integer totalAWT;
	private Integer longestAHT;
	private Integer longestAWT;
	private Integer totalCallAbnd;
	
	
	
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
	public Integer getTotalATT() {
		return totalATT;
	}
	public void setTotalATT(Integer totalATT) {
		this.totalATT = totalATT;
	}
	public Integer getTotalAWT() {
		return totalAWT;
	}
	public void setTotalAWT(Integer totalAWT) {
		this.totalAWT = totalAWT;
	}
	public Integer getLongestAHT() {
		return longestAHT;
	}
	public void setLongestAHT(Integer longestAHT) {
		this.longestAHT = longestAHT;
	}
	public Integer getLongestAWT() {
		return longestAWT;
	}
	public void setLongestAWT(Integer longestAWT) {
		this.longestAWT = longestAWT;
	}
	public DashboardSummary() {
		
	}
	
	

}
