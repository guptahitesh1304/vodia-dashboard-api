package com.vodia.api.dashboard.domain1.queue;

import java.util.List;

public class QueuePerformanceTable {
	
	private String name;
	private int callsInWaiting;
	private int agentsForQueue;
	private String awt;
	private String aht;
	private int totalAbandonedCalls;
	private int totalNumberOfCalls;
	private String SLinPercentage;
	private int loggedInAgents;
	
	
	
	public int getLoggedInAgents() {
		return loggedInAgents;
	}
	public void setLoggedInAgents(int loggedInAgents) {
		this.loggedInAgents = loggedInAgents;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCallsInWaiting() {
		return callsInWaiting;
	}
	public void setCallsInWaiting(int callsInWaiting) {
		this.callsInWaiting = callsInWaiting;
	}
	public int getAgentsForQueue() {
		return agentsForQueue;
	}
	public void setAgentsForQueue(int agentsForQueue) {
		this.agentsForQueue = agentsForQueue;
	}
	public String getAwt() {
		return awt;
	}
	public void setAwt(String awt) {
		this.awt = awt;
	}
	public String getAht() {
		return aht;
	}
	public void setAht(String aht) {
		this.aht = aht;
	}
	public int getTotalAbandonedCalls() {
		return totalAbandonedCalls;
	}
	public void setTotalAbandonedCalls(int totalAbandonedCalls) {
		this.totalAbandonedCalls = totalAbandonedCalls;
	}
	public int getTotalNumberOfCalls() {
		return totalNumberOfCalls;
	}
	public void setTotalNumberOfCalls(int totalNumberOfCalls) {
		this.totalNumberOfCalls = totalNumberOfCalls;
	}
	public String getSLinPercentage() {
		return SLinPercentage;
	}
	public void setSLinPercentage(String sLinPercentage) {
		SLinPercentage = sLinPercentage;
	}
	public QueuePerformanceTable(String name, int callsInWaiting, int agentsForQueue, String awt, String aht,
			int totalAbandonedCalls, int totalNumberOfCalls, String sLinPercentage) {
		super();
		this.name = name;
		this.callsInWaiting = callsInWaiting;
		this.agentsForQueue = agentsForQueue;
		this.awt = awt;
		this.aht = aht;
		this.totalAbandonedCalls = totalAbandonedCalls;
		this.totalNumberOfCalls = totalNumberOfCalls;
		SLinPercentage = sLinPercentage;
	}
	public QueuePerformanceTable() {
		// TODO Auto-generated constructor stub
	}
	
	

}
