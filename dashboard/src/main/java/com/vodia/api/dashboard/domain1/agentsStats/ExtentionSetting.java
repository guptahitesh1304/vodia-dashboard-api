package com.vodia.api.dashboard.domain1.agentsStats;


/*{"name":"201","alias":["201"],"ani":[],"displayname":"Karim  Hussona","icon":false,"dnd":false,
	"chatstatus":"","connected":false,"login":false,"department":"","position":"","registered":true*/
public class ExtentionSetting {
	
	private String name;
	private String displayname;
	private String chatstatus;
	private String department;
	private Boolean connected;
	private Boolean login;
	private Boolean registered;
	private Boolean dnd;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayname() {
		return displayname;
	}
	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	public String getChatstatus() {
		return chatstatus;
	}
	public void setChatstatus(String chatstatus) {
		this.chatstatus = chatstatus;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Boolean getConnected() {
		return connected;
	}
	public void setConnected(Boolean connected) {
		this.connected = connected;
	}
	public Boolean getLogin() {
		return login;
	}
	public void setLogin(Boolean login) {
		this.login = login;
	}
	public Boolean getRegistered() {
		return registered;
	}
	public void setRegistered(Boolean registered) {
		this.registered = registered;
	}
	public Boolean getDnd() {
		return dnd;
	}
	public void setDnd(Boolean dnd) {
		this.dnd = dnd;
	}
	public ExtentionSetting() {
		
	}
	
	

}
