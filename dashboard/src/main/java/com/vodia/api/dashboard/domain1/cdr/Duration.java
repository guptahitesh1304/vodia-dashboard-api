package com.vodia.api.dashboard.domain1.cdr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Duration {

	private String ivrTime;
	private String ringTime;
	private String talkTime;
	private String holdTime;
	
	
	public Duration() {}
	
	@Override
	public String toString() {
		return "Duration [ivrTime=" + ivrTime + ", ringTime=" + ringTime + ", talkTime=" + talkTime + ", holdTime="
				+ holdTime + "]";
	}

	public String getIvrTime() {
		return ivrTime;
	}
	public void setIvrTime(String ivrTime) {
		this.ivrTime = ivrTime;
	}
	public String getRingTime() {
		return ringTime;
	}
	public void setRingTime(String ringTime) {
		this.ringTime = ringTime;
	}
	public String getTalkTime() {
		return talkTime;
	}
	public void setTalkTime(String talkTime) {
		this.talkTime = talkTime;
	}
	public String getHoldTime() {
		return holdTime;
	}
	public void setHoldTime(String holdTime) {
		this.holdTime = holdTime;
	}
	
	
	
}
