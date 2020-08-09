package com.vodia.api.dashboard.domain1.queue;

public class AGENTS {
	
	private String account;

    private String name;

    private boolean dnd;

    private boolean login;

    private boolean jumpin;

    private Work work;

    private int calls;

    private int missed;

    private Duration duration;

    private Average average;

    public void setAccount(String account){
        this.account = account;
    }
    public String getAccount(){
        return this.account;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setDnd(boolean dnd){
        this.dnd = dnd;
    }
    public boolean getDnd(){
        return this.dnd;
    }
    public void setLogin(boolean login){
        this.login = login;
    }
    public boolean getLogin(){
        return this.login;
    }
    public void setJumpin(boolean jumpin){
        this.jumpin = jumpin;
    }
    public boolean getJumpin(){
        return this.jumpin;
    }
    public void setWork(Work work){
        this.work = work;
    }
    public Work getWork(){
        return this.work;
    }
    public void setCalls(int calls){
        this.calls = calls;
    }
    public int getCalls(){
        return this.calls;
    }
    public void setMissed(int missed){
        this.missed = missed;
    }
    public int getMissed(){
        return this.missed;
    }
    public void setDuration(Duration duration){
        this.duration = duration;
    }
    public Duration getDuration(){
        return this.duration;
    }
    public void setAverage(Average average){
        this.average = average;
    }
    public Average getAverage(){
        return this.average;
    }

}
