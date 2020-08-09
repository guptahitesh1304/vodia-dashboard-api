package com.vodia.api.dashboard.domain1.cdr;

//{"id":"83","name":"WFH-CC","type":"register","disabled":"false","account":"6499652316",
//	"proxy":"sip:fsr-ak.compassnet.co.nz","registrar":"fsr-ak.compassnet.co.nz",
//	"direction":"","ani":"6499652316","global":"false","own":true,
//	"status1":"200 OK","status2":"30 s","status3":"udp:10.254.43.10:5060 udp:10.254.43.9:5060"}
public class Trunks {
	
	private String id;
	private String name;
	private String type;
	private String disabled;
	private String account;
	private String proxy;
	private String registrar;
	private String direction;
	private String ani;
	private String global;
	private Boolean own;
	private String status1;
	private String status2;
	private String status3;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDisabled() {
		return disabled;
	}
	public void setDisabled(String disabled) {
		this.disabled = disabled;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getProxy() {
		return proxy;
	}
	public void setProxy(String proxy) {
		this.proxy = proxy;
	}
	public String getRegistrar() {
		return registrar;
	}
	public void setRegistrar(String registrar) {
		this.registrar = registrar;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getAni() {
		return ani;
	}
	public void setAni(String ani) {
		this.ani = ani;
	}
	public String getGlobal() {
		return global;
	}
	public void setGlobal(String global) {
		this.global = global;
	}
	public Boolean getOwn() {
		return own;
	}
	public void setOwn(Boolean own) {
		this.own = own;
	}
	public String getStatus1() {
		return status1;
	}
	public void setStatus1(String status1) {
		this.status1 = status1;
	}
	public String getStatus2() {
		return status2;
	}
	public void setStatus2(String status2) {
		this.status2 = status2;
	}
	public String getStatus3() {
		return status3;
	}
	public void setStatus3(String status3) {
		this.status3 = status3;
	}

	

}
