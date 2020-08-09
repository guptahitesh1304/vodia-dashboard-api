package com.vodia.api.dashboard.domain1.queue;

public class ACD_Queue_List {
	
	private String name;
	private String display;
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public ACD_Queue_List(String name, String display) {
		super();
		this.name = name;
		this.display = display;
	}
	public ACD_Queue_List() {
		// TODO Auto-generated constructor stub
	}
	
	
	

}
