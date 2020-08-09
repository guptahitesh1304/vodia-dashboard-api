package com.vodia.api.dashboard.domain1.queue;

import java.util.List;

public class ACD_WallBoard_QueueWise {
	private ACDS acds;
	private List<AGENTS> agents;
	private List<CurrentCalls> current;
	private List<CallHistory> history;
	
	
	
	
	public ACD_WallBoard_QueueWise() {
		
	}
	public ACDS getAcds() {
		return acds;
	}
	public void setAcds(ACDS acds) {
		this.acds = acds;
	}
	public List<AGENTS> getAgents() {
		return agents;
	}
	public void setAgents(List<AGENTS> agents) {
		this.agents = agents;
	}
	public List<CurrentCalls> getCurrent() {
		return current;
	}
	public void setCurrent(List<CurrentCalls> current) {
		this.current = current;
	}
	public List<CallHistory> getHistory() {
		return history;
	}
	public void setHistory(List<CallHistory> history) {
		this.history = history;
	}
	
	

}
