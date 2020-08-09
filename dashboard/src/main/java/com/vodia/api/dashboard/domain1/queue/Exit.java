package com.vodia.api.dashboard.domain1.queue;

public class Exit {
	
    private int user;

    private int admin;

    private int max_calls;

    private int application;

    private int other;

    public void setUser(int user){
        this.user = user;
    }
    public int getUser(){
        return this.user;
    }
    public void setAdmin(int admin){
        this.admin = admin;
    }
    public int getAdmin(){
        return this.admin;
    }
    public void setMax_calls(int max_calls){
        this.max_calls = max_calls;
    }
    public int getMax_calls(){
        return this.max_calls;
    }
    public void setApplication(int application){
        this.application = application;
    }
    public int getApplication(){
        return this.application;
    }
    public void setOther(int other){
        this.other = other;
    }
    public int getOther(){
        return this.other;
    }

}
