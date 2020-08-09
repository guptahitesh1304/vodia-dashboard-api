package com.vodia.api.dashboard.domain1.queue;

import java.util.List;

public class Work {
	
	  private int begin;

	    private int end;

	    private double now;

	    //private List<String> segments;

	    public void setBegin(int begin){
	        this.begin = begin;
	    }
	    public int getBegin(){
	        return this.begin;
	    }
	    public void setEnd(int end){
	        this.end = end;
	    }
	    public int getEnd(){
	        return this.end;
	    }
	    public void setNow(double now){
	        this.now = now;
	    }
	    public double getNow(){
	        return this.now;
	    }
	/*
	 * public void setSegments(List<String> segments){ this.segments = segments; }
	 * public List<String> getSegments(){ return this.segments; }
	 */

}
