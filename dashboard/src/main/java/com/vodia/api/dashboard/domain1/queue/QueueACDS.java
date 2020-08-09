package com.vodia.api.dashboard.domain1.queue;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueACDS {
	
	private List<String> managers;

    private Redirect redirect;

    private Hangup hangup;

    private Exit exit;

    private Duration duration;

    private Calls calls;
    
    private String qName;
    
    

    public String getqName() {
		return qName;
	}
	public void setqName(String qName) {
		this.qName = qName;
	}
	public void setManagers(List<String> managers){
        this.managers = managers;
    }
    public List<String> getManagers(){
        return this.managers;
    }
    public void setRedirect(Redirect redirect){
        this.redirect = redirect;
    }
    public Redirect getRedirect(){
        return this.redirect;
    }
    public void setHangup(Hangup hangup){
        this.hangup = hangup;
    }
    public Hangup getHangup(){
        return this.hangup;
    }
    public void setExit(Exit exit){
        this.exit = exit;
    }
    public Exit getExit(){
        return this.exit;
    }
    public void setDuration(Duration duration){
        this.duration = duration;
    }
    public Duration getDuration(){
        return this.duration;
    }
    public void setCalls(Calls calls){
        this.calls = calls;
    }
    public Calls getCalls(){
        return this.calls;
    }

}
