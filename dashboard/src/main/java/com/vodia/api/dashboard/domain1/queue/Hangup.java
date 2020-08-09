package com.vodia.api.dashboard.domain1.queue;

public class Hangup {

	 private int waiting;

	    private int ringing;

	    public void setWaiting(int waiting){
	        this.waiting = waiting;
	    }
	    public int getWaiting(){
	        return this.waiting;
	    }
	    public void setRinging(int ringing){
	        this.ringing = ringing;
	    }
	    public int getRinging(){
	        return this.ringing;
	    }
}
