
  package com.vodia.api.dashboard.domain1.queue;
  
  public class CurrentCalls {
	  
	  	private String start;

	    private String from;

	    private String acd;

	    private String agent;

		public String getStart() {
			return start;
		}

		public void setStart(String start) {
			this.start = start;
		}

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}

		public String getAcd() {
			return acd;
		}

		public void setAcd(String acd) {
			this.acd = acd;
		}

		public String getAgent() {
			return agent;
		}

		public void setAgent(String agent) {
			this.agent = agent;
		}
	    
	    
  
  }
  
 /* ================================== package vodia_acds_queue; public class
 * Redirect { private int waiting;
 * 
 * private int ringing;
 * 
 * private int anonymous;
 * 
 * public void setWaiting(int waiting){ this.waiting = waiting; } public int
 * getWaiting(){ return this.waiting; } public void setRinging(int ringing){
 * this.ringing = ringing; } public int getRinging(){ return this.ringing; }
 * public void setAnonymous(int anonymous){ this.anonymous = anonymous; } public
 * int getAnonymous(){ return this.anonymous; } }
 * 
 * ================================== package vodia_acds_queue; public class
 * Hangup { private int waiting;
 * 
 * private int ringing;
 * 
 * public void setWaiting(int waiting){ this.waiting = waiting; } public int
 * getWaiting(){ return this.waiting; } public void setRinging(int ringing){
 * this.ringing = ringing; } public int getRinging(){ return this.ringing; } }
 * 
 * ================================== package vodia_acds_queue; public class
 * Exit { private int user;
 * 
 * private int admin;
 * 
 * private int max-calls;
 * 
 * private int application;
 * 
 * private int other;
 * 
 * public void setUser(int user){ this.user = user; } public int getUser(){
 * return this.user; } public void setAdmin(int admin){ this.admin = admin; }
 * public int getAdmin(){ return this.admin; } public void setMax-calls(int
 * max-calls){ this.max-calls = max-calls; } public int getMax-calls(){ return
 * this.max-calls; } public void setApplication(int application){
 * this.application = application; } public int getApplication(){ return
 * this.application; } public void setOther(int other){ this.other = other; }
 * public int getOther(){ return this.other; } }
 * 
 * ================================== package vodia_acds_queue; public class Sum
 * { private int ivr;
 * 
 * private int ring;
 * 
 * private int talk;
 * 
 * private int hold;
 * 
 * private int idle;
 * 
 * public void setIvr(int ivr){ this.ivr = ivr; } public int getIvr(){ return
 * this.ivr; } public void setRing(int ring){ this.ring = ring; } public int
 * getRing(){ return this.ring; } public void setTalk(int talk){ this.talk =
 * talk; } public int getTalk(){ return this.talk; } public void setHold(int
 * hold){ this.hold = hold; } public int getHold(){ return this.hold; } public
 * void setIdle(int idle){ this.idle = idle; } public int getIdle(){ return
 * this.idle; } }
 * 
 * ================================== package vodia_acds_queue; public class
 * Average { private int ivr;
 * 
 * private int ring;
 * 
 * private int talk;
 * 
 * private int hold;
 * 
 * private int idle;
 * 
 * public void setIvr(int ivr){ this.ivr = ivr; } public int getIvr(){ return
 * this.ivr; } public void setRing(int ring){ this.ring = ring; } public int
 * getRing(){ return this.ring; } public void setTalk(int talk){ this.talk =
 * talk; } public int getTalk(){ return this.talk; } public void setHold(int
 * hold){ this.hold = hold; } public int getHold(){ return this.hold; } public
 * void setIdle(int idle){ this.idle = idle; } public int getIdle(){ return
 * this.idle; } }
 * 
 * ================================== package vodia_acds_queue; public class
 * Duration { private Sum sum;
 * 
 * private Average average;
 * 
 * public void setSum(Sum sum){ this.sum = sum; } public Sum getSum(){ return
 * this.sum; } public void setAverage(Average average){ this.average = average;
 * } public Average getAverage(){ return this.average; } }
 * 
 * ================================== package vodia_acds_queue; public class
 * Calls { private int count;
 * 
 * private int completed;
 * 
 * public void setCount(int count){ this.count = count; } public int getCount(){
 * return this.count; } public void setCompleted(int completed){ this.completed
 * = completed; } public int getCompleted(){ return this.completed; } }
 * 
 * ================================== package vodia_acds_queue; import
 * java.util.ArrayList; import java.util.List; public class 301 { private
 * List<String> managers;
 * 
 * private Redirect redirect;
 * 
 * private Hangup hangup;
 * 
 * private Exit exit;
 * 
 * private Duration duration;
 * 
 * private Calls calls;
 * 
 * public void setManagers(List<String> managers){ this.managers = managers; }
 * public List<String> getManagers(){ return this.managers; } public void
 * setRedirect(Redirect redirect){ this.redirect = redirect; } public Redirect
 * getRedirect(){ return this.redirect; } public void setHangup(Hangup hangup){
 * this.hangup = hangup; } public Hangup getHangup(){ return this.hangup; }
 * public void setExit(Exit exit){ this.exit = exit; } public Exit getExit(){
 * return this.exit; } public void setDuration(Duration duration){ this.duration
 * = duration; } public Duration getDuration(){ return this.duration; } public
 * void setCalls(Calls calls){ this.calls = calls; } public Calls getCalls(){
 * return this.calls; } }
 * 
 * ================================== package vodia_acds_queue; public class
 * Acds { private 301 301;
 * 
 * public void set301(301 301){ this.301 = 301; } public 301 get301(){ return
 * this.301; } }
 * 
 * ================================== package vodia_acds_queue; import
 * java.util.ArrayList; import java.util.List; public class Work { private int
 * begin;
 * 
 * private int end;
 * 
 * private double now;
 * 
 * private List<String> segments;
 * 
 * public void setBegin(int begin){ this.begin = begin; } public int getBegin(){
 * return this.begin; } public void setEnd(int end){ this.end = end; } public
 * int getEnd(){ return this.end; } public void setNow(double now){ this.now =
 * now; } public double getNow(){ return this.now; } public void
 * setSegments(List<String> segments){ this.segments = segments; } public
 * List<String> getSegments(){ return this.segments; } }
 * 
 * ================================== package vodia_acds_queue; public class
 * Duration { private int talk;
 * 
 * private int hold;
 * 
 * private int idle;
 * 
 * public void setTalk(int talk){ this.talk = talk; } public int getTalk(){
 * return this.talk; } public void setHold(int hold){ this.hold = hold; } public
 * int getHold(){ return this.hold; } public void setIdle(int idle){ this.idle =
 * idle; } public int getIdle(){ return this.idle; } }
 * 
 * ================================== package vodia_acds_queue; public class
 * Average { private int talk;
 * 
 * private int hold;
 * 
 * private int idle;
 * 
 * public void setTalk(int talk){ this.talk = talk; } public int getTalk(){
 * return this.talk; } public void setHold(int hold){ this.hold = hold; } public
 * int getHold(){ return this.hold; } public void setIdle(int idle){ this.idle =
 * idle; } public int getIdle(){ return this.idle; } }
 * 
 * ================================== package vodia_acds_queue; public class
 * Agents { private String account;
 * 
 * private String name;
 * 
 * private boolean dnd;
 * 
 * private boolean login;
 * 
 * private boolean jumpin;
 * 
 * private Work work;
 * 
 * private int calls;
 * 
 * private int missed;
 * 
 * private Duration duration;
 * 
 * private Average average;
 * 
 * public void setAccount(String account){ this.account = account; } public
 * String getAccount(){ return this.account; } public void setName(String name){
 * this.name = name; } public String getName(){ return this.name; } public void
 * setDnd(boolean dnd){ this.dnd = dnd; } public boolean getDnd(){ return
 * this.dnd; } public void setLogin(boolean login){ this.login = login; } public
 * boolean getLogin(){ return this.login; } public void setJumpin(boolean
 * jumpin){ this.jumpin = jumpin; } public boolean getJumpin(){ return
 * this.jumpin; } public void setWork(Work work){ this.work = work; } public
 * Work getWork(){ return this.work; } public void setCalls(int calls){
 * this.calls = calls; } public int getCalls(){ return this.calls; } public void
 * setMissed(int missed){ this.missed = missed; } public int getMissed(){ return
 * this.missed; } public void setDuration(Duration duration){ this.duration =
 * duration; } public Duration getDuration(){ return this.duration; } public
 * void setAverage(Average average){ this.average = average; } public Average
 * getAverage(){ return this.average; } }
 * 
 * ================================== package vodia_acds_queue; public class
 * History { private String start;
 * 
 * private String from;
 * 
 * private int duration;
 * 
 * private String agent;
 * 
 * public void setStart(String start){ this.start = start; } public String
 * getStart(){ return this.start; } public void setFrom(String from){ this.from
 * = from; } public String getFrom(){ return this.from; } public void
 * setDuration(int duration){ this.duration = duration; } public int
 * getDuration(){ return this.duration; } public void setAgent(String agent){
 * this.agent = agent; } public String getAgent(){ return this.agent; } }
 * 
 * ================================== package vodia_acds_queue; import
 * java.util.ArrayList; import java.util.List; public class Root { private Acds
 * acds;
 * 
 * private List<Agents> agents;
 * 
 * private List<String> current;
 * 
 * private List<History> history;
 * 
 * public void setAcds(Acds acds){ this.acds = acds; } public Acds getAcds(){
 * return this.acds; } public void setAgents(List<Agents> agents){ this.agents =
 * agents; } public List<Agents> getAgents(){ return this.agents; } public void
 * setCurrent(List<String> current){ this.current = current; } public
 * List<String> getCurrent(){ return this.current; } public void
 * setHistory(List<History> history){ this.history = history; } public
 * List<History> getHistory(){ return this.history; } }
 * 
 */