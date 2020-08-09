package com.vodia.api.dashboard.domain1.queue;

public class Calls {

	private int count;

    private int completed;

    public void setCount(int count){
        this.count = count;
    }
    public int getCount(){
        return this.count;
    }
    public void setCompleted(int completed){
        this.completed = completed;
    }
    public int getCompleted(){
        return this.completed;
    }
}
