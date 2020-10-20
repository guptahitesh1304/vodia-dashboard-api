package com.vodia.api.dashboard.domain1.queue.qperformanceTableNew;

import java.util.List;

public class LiveACDSStatsAllQueue {
	
	private String name;
	private String display;
	private int calls;
	private List<String> agents;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public int getCalls() {
		return calls;
	}
	public void setCalls(int calls) {
		this.calls = calls;
	}
	public List<String> getAgents() {
		return agents;
	}
	public void setAgents(List<String> agents) {
		this.agents = agents;
	}
	
	

	
	

}
