package com.vodia.api.dashboard.domain1.queue;

import java.util.List;

public class QueuesDetails {
	private String name;
	private String display;
	private List agents;
	private int calls;
	
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
	public List getAgents() {
		return agents;
	}
	public void setAgents(List agents) {
		this.agents = agents;
	}
	public int getCalls() {
		return calls;
	}
	public void setCalls(int calls) {
		this.calls = calls;
	}
	public QueuesDetails(String name, String display, List agents, int calls) {
		super();
		this.name = name;
		this.display = display;
		this.agents = agents;
		this.calls = calls;
	}
	
	
	

}
