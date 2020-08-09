package com.vodia.api.dashboard.domain1.queue;

public class Duration {
	
	private Sum sum;

    private Average average;

    public void setSum(Sum sum){
        this.sum = sum;
    }
    public Sum getSum(){
        return this.sum;
    }
    public void setAverage(Average average){
        this.average = average;
    }
    public Average getAverage(){
        return this.average;
    }

}
