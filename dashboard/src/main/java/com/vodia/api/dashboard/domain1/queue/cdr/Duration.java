package com.vodia.api.dashboard.domain1.queue.cdr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Duration {

	private int ivr;
	private int ring;
	private int talk;
	private int hold;
	
	
	public Duration() {}
	
	@Override
	public String toString() {
		return "Duration [ivr=" + ivr + ", ring=" + ring + ", talk=" + talk + ", hold="
				+ hold + "]";
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
	
	
	
}
