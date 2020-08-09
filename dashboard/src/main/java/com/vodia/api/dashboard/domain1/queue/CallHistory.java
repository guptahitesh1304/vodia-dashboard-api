package com.vodia.api.dashboard.domain1.queue;

public class CallHistory {
	
	 private String start;

	    private String from;

	    private int duration;

	    private String agent;

	    public void setStart(String start){
	        this.start = start;
	    }
	    public String getStart(){
	        return this.start;
	    }
	    public void setFrom(String from){
	        this.from = from;
	    }
	    public String getFrom(){
	        return this.from;
	    }
	    public void setDuration(int duration){
	        this.duration = duration;
	    }
	    public int getDuration(){
	        return this.duration;
	    }
	    public void setAgent(String agent){
	        this.agent = agent;
	    }
	    public String getAgent(){
	        return this.agent;
	    }

}
