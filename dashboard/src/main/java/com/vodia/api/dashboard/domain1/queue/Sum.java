package com.vodia.api.dashboard.domain1.queue;

public class Sum {
	
	  private int ivr;

	    private int ring;

	    private int talk;

	    private int hold;

	    private int idle;

	    public void setIvr(int ivr){
	        this.ivr = ivr;
	    }
	    public int getIvr(){
	        return this.ivr;
	    }
	    public void setRing(int ring){
	        this.ring = ring;
	    }
	    public int getRing(){
	        return this.ring;
	    }
	    public void setTalk(int talk){
	        this.talk = talk;
	    }
	    public int getTalk(){
	        return this.talk;
	    }
	    public void setHold(int hold){
	        this.hold = hold;
	    }
	    public int getHold(){
	        return this.hold;
	    }
	    public void setIdle(int idle){
	        this.idle = idle;
	    }
	    public int getIdle(){
	        return this.idle;
	    }

}
