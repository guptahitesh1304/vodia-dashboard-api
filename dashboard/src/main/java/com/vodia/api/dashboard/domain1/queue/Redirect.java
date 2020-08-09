package com.vodia.api.dashboard.domain1.queue;

public class Redirect {
	
	  private int waiting;

	    private int ringing;

	    private int anonymous;

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
	    public void setAnonymous(int anonymous){
	        this.anonymous = anonymous;
	    }
	    public int getAnonymous(){
	        return this.anonymous;
	    }

}
