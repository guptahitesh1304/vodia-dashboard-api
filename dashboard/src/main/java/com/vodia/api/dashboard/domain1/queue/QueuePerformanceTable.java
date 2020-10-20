package com.vodia.api.dashboard.domain1.queue;

import java.util.List;

public class QueuePerformanceTable {
	
	private String name;
	private String displayName;
	private int callsInWaiting;
	private int agentsForQueue;
	private String awt;
	private String aht;
	private int totalAbandonedCalls;
	private int totalNumberOfCalls;
	private String SLinPercentage;
	private int loggedInAgents;
	private int call_back_request;
	
	
	
	public int getCall_back_request() {
		return call_back_request;
	}
	public void setCall_back_request(int call_back_request) {
		this.call_back_request = call_back_request;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
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
	public QueuePerformanceTable(String name, String display_name, int callsInWaiting, int agentsForQueue, String awt, String aht,
			int totalAbandonedCalls, int totalNumberOfCalls, String sLinPercentage) {
		super();
		this.name = name;
		this.displayName = display_name;
		this.callsInWaiting = callsInWaiting;
		this.agentsForQueue = agentsForQueue;
		this.awt = awt;
		this.aht = aht;
		this.totalAbandonedCalls = totalAbandonedCalls;
		this.totalNumberOfCalls = totalNumberOfCalls;
		SLinPercentage = sLinPercentage;
	}
	
	
	public QueuePerformanceTable(String name, String displayName, int callsInWaiting, int agentsForQueue, String awt,
			String aht, int totalAbandonedCalls, int totalNumberOfCalls, String sLinPercentage, int loggedInAgents,
			int call_back_request) {
		super();
		this.name = name;
		this.displayName = displayName;
		this.callsInWaiting = callsInWaiting;
		this.agentsForQueue = agentsForQueue;
		this.awt = awt;
		this.aht = aht;
		this.totalAbandonedCalls = totalAbandonedCalls;
		this.totalNumberOfCalls = totalNumberOfCalls;
		SLinPercentage = sLinPercentage;
		this.loggedInAgents = loggedInAgents;
		this.call_back_request = call_back_request;
	}
	public QueuePerformanceTable() {
		// TODO Auto-generated constructor stub
	}
	
	

}
