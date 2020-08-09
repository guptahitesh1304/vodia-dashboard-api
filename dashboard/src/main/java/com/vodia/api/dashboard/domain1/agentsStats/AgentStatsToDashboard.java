package com.vodia.api.dashboard.domain1.agentsStats;

public class AgentStatsToDashboard {
	
	private Integer TotalNumberOfAgents;
	private Integer Agent_Ready;
	private Integer Agent_NotReady;
	private Integer Agent_Working;
	private Integer Agent_Talking;
	
	
	public Integer getTotalNumberOfAgents() {
		return TotalNumberOfAgents;
	}
	public void setTotalNumberOfAgents(Integer totalNumberOfAgents) {
		TotalNumberOfAgents = totalNumberOfAgents;
	}
	public Integer getAgent_Ready() {
		return Agent_Ready;
	}
	public void setAgent_Ready(Integer agent_Ready) {
		Agent_Ready = agent_Ready;
	}
	public Integer getAgent_NotReady() {
		return Agent_NotReady;
	}
	public void setAgent_NotReady(Integer agent_NotReady) {
		Agent_NotReady = agent_NotReady;
	}
	public Integer getAgent_Working() {
		return Agent_Working;
	}
	public void setAgent_Working(Integer agent_Working) {
		Agent_Working = agent_Working;
	}
	public Integer getAgent_Talking() {
		return Agent_Talking;
	}
	public void setAgent_Talking(Integer agent_Talking) {
		Agent_Talking = agent_Talking;
	}
	
	

}
