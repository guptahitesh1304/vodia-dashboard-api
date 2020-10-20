package com.vodia.api.dashboard.domain1.queue.cdr;

import com.vodia.api.dashboard.domain1.queue.cdr.Duration;;;

public class QueueWiseCDR {

	private String start;

	private String from;

	private Duration duration;

	private String agent;

	private String to;

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public QueueWiseCDR() {

	}

}
