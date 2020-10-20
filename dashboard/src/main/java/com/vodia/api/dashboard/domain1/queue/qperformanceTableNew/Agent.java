package com.vodia.api.dashboard.domain1.queue.qperformanceTableNew;

public class Agent {
	public String agent;
    public int calls;
    public int missed;
    public int ivr;
    public int ring;
    public int talk;
    public int hold;
    public Idle idle;
	public String getAgent() {
		return agent;
	}
	public void setAgent(String agent) {
		this.agent = agent;
	}
	public int getCalls() {
		return calls;
	}
	public void setCalls(int calls) {
		this.calls = calls;
	}
	public int getMissed() {
		return missed;
	}
	public void setMissed(int missed) {
		this.missed = missed;
	}
	public int getIvr() {
		return ivr;
	}
	public void setIvr(int ivr) {
		this.ivr = ivr;
	}
	public int getRing() {
		return ring;
	}
	public void setRing(int ring) {
		this.ring = ring;
	}
	public int getTalk() {
		return talk;
	}
	public void setTalk(int talk) {
		this.talk = talk;
	}
	public int getHold() {
		return hold;
	}
	public void setHold(int hold) {
		this.hold = hold;
	}
	public Idle getIdle() {
		return idle;
	}
	public void setIdle(Idle idle) {
		this.idle = idle;
	}

    
    
}
