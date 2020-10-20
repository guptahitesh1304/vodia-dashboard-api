package com.vodia.api.dashboard.domain1.queue.qperformanceTableNew;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.vodia.api.dashboard.config.URLConfig;
import com.vodia.api.dashboard.config.Utility;
import com.vodia.api.dashboard.domain1.queue.ACD_Queue_List;
import com.vodia.api.dashboard.domain1.queue.QueueService;
import com.vodia.api.dashboard.domain1.summary.ACDInfo;
import com.vodia.api.dashboard.domain1.summary.DashboardSummaryService;

@Service
public class QPerformanceTableDashboardNewService {
	
	private static DecimalFormat df2 = new DecimalFormat("#.##");
	
	@Autowired
	private QueueService qs;
	
	@Autowired
	private DashboardSummaryService dss;
	
	private static final Logger log = LoggerFactory.getLogger(QPerformanceTableDashboardNewService.class);

	public List<QPerformanceTableDashboardNew> getQueuePerformanceTableForDashboard(HttpServletRequest request) {
		// TODO Auto-generated method stub

		log.debug("i am in getQueuePerformanceTableForDashboard SERVICE ");
		long startTime = System.nanoTime();
		List<QPerformanceTableDashboardNew> qptl = new ArrayList<QPerformanceTableDashboardNew>();

		String queue_id;
		String queue_name;
		int callsInWaiting = 0;
		int agentsForQueue;
		int totalAgents;
		int call_back_request = 0;
		String awt = "0";
		String aht = "0";
		int totalAbandonedCalls = 0;
		int totalNumberOfCalls = 0;
		double aht_d = 0;
		double awt_d = 0;
		//String SLinPercentage = "";
		int loggedInAgents;
		try {
			long list_acd_queue_wallboard_time_start = System.nanoTime();
			log.debug("calling getACDForAllQueue from getQueuePerformanceTable");
			LiveACDSStatsAllQueue_Part2 list_acd_queue_wallboard;
			ACDInfo acd_info_queue;
			long list_acd_queue_wallboard_time = System.nanoTime();

			log.debug("list_acd_queue_wallboard  execution tijme"+(list_acd_queue_wallboard_time-list_acd_queue_wallboard_time_start));
			long getLiveACDSStatsAllQueue_START = System.nanoTime();
			List<LiveACDSStatsAllQueue> liveACDSStatsAllQueue_list = getLiveACDSStatsAllQueue(request);
			long list_acd_queue_wallboard_time_END = System.nanoTime();
			log.debug("list_acd_queue_wallboard_time  execution tijme---"+(list_acd_queue_wallboard_time_END-getLiveACDSStatsAllQueue_START));
			if(liveACDSStatsAllQueue_list !=null && liveACDSStatsAllQueue_list.size() > 0) {

			for (int i = 0; i < liveACDSStatsAllQueue_list.size(); i++) {
				QPerformanceTableDashboardNew qpt = new QPerformanceTableDashboardNew();
				queue_id = liveACDSStatsAllQueue_list.get(i).getName();
				queue_name = liveACDSStatsAllQueue_list.get(i).getDisplay();
				agentsForQueue = 8;//list_acd_queue_wallboard.get(i).getAgents().size();
				loggedInAgents = liveACDSStatsAllQueue_list.get(i).getAgents().size();
				callsInWaiting = liveACDSStatsAllQueue_list.get(i).getCalls();
				
				long getPerformanceByQueuePart2_START = System.nanoTime();
				list_acd_queue_wallboard = getPerformanceByQueuePart2(request,queue_id);
				long getPerformanceByQueuePart2_END = System.nanoTime();
				log.debug("getPerformanceByQueuePart2  execution tijme---"+(getPerformanceByQueuePart2_END-getPerformanceByQueuePart2_START));
				
				long getQueuePerformanceTableForDashboard_GET_ACD_START = System.nanoTime();
				acd_info_queue  = dss.getACD(request, queue_id);
				long getQueuePerformanceTableForDashboard_GET_ACD_END = System.nanoTime();
						log.debug("getQueuePerformanceTableForDashboard_GET_ACD_END  execution tijme---"+(getQueuePerformanceTableForDashboard_GET_ACD_END-getQueuePerformanceTableForDashboard_GET_ACD_START));
				if(acd_info_queue.getAvgdurationivr() !=null) {
				awt_d = acd_info_queue.getAvgdurationivr()/60000;
				awt = df2.format(awt_d);
				}
				
				if(acd_info_queue.getAvgdurationtalk() !=null) {
				aht_d = acd_info_queue.getAvgdurationtalk()/60000;
				aht = df2.format(aht_d);
				}

				if(list_acd_queue_wallboard !=null) {
				totalAbandonedCalls = list_acd_queue_wallboard.getHangup_ringing() + list_acd_queue_wallboard.getHangup_waiting();
				call_back_request = list_acd_queue_wallboard.getOther();
				}
				
				if(acd_info_queue.getCalls() !=null) {
				totalNumberOfCalls = acd_info_queue.getCalls();
				}
				
				
				
				
				log.debug("queue id --" + queue_id);
				log.debug(" queue_name --" + queue_name);
				log.debug("agentsForQueue--" + agentsForQueue);
				log.debug("loggedInAgents--" + loggedInAgents);
				log.debug("awt--" + awt +"--awt_d"+awt_d);
				log.debug("aht--" + aht +"---aht_d"+aht_d);
				log.debug("totalAbandonedCalls--" + totalAbandonedCalls);
				log.debug("totalNumberOfCalls--" + totalNumberOfCalls);
				//log.debug("SLinPercentage--" + SLinPercentage);
				log.debug("call_back_request--" + call_back_request);
				qpt.setName(queue_id);
				qpt.setDisplayName(queue_name);
				qpt.setAht(aht);
				qpt.setAwt(awt);
				qpt.setTotalAbandonedCalls(totalAbandonedCalls);
				qpt.setAgentsForQueue(agentsForQueue);
				qpt.setCallsInWaiting(callsInWaiting);
				qpt.setLoggedInAgents(loggedInAgents);
				qpt.setTotalNumberOfCalls(totalNumberOfCalls);
				//qpt.setSLinPercentage(SLinPercentage);
				qpt.setCall_back_request(call_back_request);
				qptl.add(i, qpt);
			}
		}

			log.debug("returning total list for getQueuePerformanceTable on the Dashboard--" + qptl.size());
			long qptl_time = System.nanoTime();
			log.debug("qptl_time  execution tijme"+(qptl_time-list_acd_queue_wallboard_time));

		}

		catch (NullPointerException e) {
			log.error("Warning: Nullpointer" + e);
		} catch (Exception e) {
			log.error("Warning: Some Other exception"+e);
		}

		log.debug("getting total number of agents");
		totalAgents = qptl.stream().map(c -> c.getAgentsForQueue()).collect(Collectors.toList()).stream()
		.mapToInt(val -> val).max().orElse(0);
		request.getSession().setAttribute("totalAgents", totalAgents);
		log.debug("setting totalAgents info into session"+totalAgents);
		log.debug("returning Queue Performance List" + qptl.size());
		
		log.debug("setting qPerformanceTable info into session NEW");
		request.getSession().setAttribute("qPerformanceTableNEW", qptl);
		return qptl;
	
	}

	public List<LiveACDSStatsAllQueue> getLiveACDSStatsAllQueue(HttpServletRequest request) {
		// TODO Auto-generated method stub
		

		log.debug("i am in getLiveACDSStatsAllQueue SERVICE ");

		// final String url =
		// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/acds";
		List<LiveACDSStatsAllQueue> liveACDSStatsAllQueue = null;
		try {
			String dn = (String) request.getSession().getAttribute("dn");
			String url_prop = URLConfig.getURL("GET_QUEUE_STATS_DASHBOARD_NEW");
			String GET_QUEUE_STATS_DASHBOARD_NEW = MessageFormat.format(url_prop, dn, dn);
			log.debug("GET_QUEUE_STATS_DASHBOARD_NEW URL  -" + GET_QUEUE_STATS_DASHBOARD_NEW);

			// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/user/200@compassoffices.ak1.cloudpbx.net.nz/accounts?type=extensions";
			String token = (String) request.getSession().getAttribute("token");

			log.debug("TOKEN AFTER LOGIN -" + token);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("Cookie", "session=" + token);

			HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

			ResponseEntity<LiveACDSStatsAllQueue[]> response = restTemplate.exchange(GET_QUEUE_STATS_DASHBOARD_NEW, HttpMethod.GET,
					requestEntity, LiveACDSStatsAllQueue[].class);
			liveACDSStatsAllQueue = Arrays.asList(response.getBody());
			log.debug("retrurning acd_cdr_list" + liveACDSStatsAllQueue.size());

		}

		catch (NullPointerException e) {
			log.error("Warning: Nullpointer" + e);
		} catch (Exception e) {
			log.error("Warning: Some Other exception"+e);
		}
		return liveACDSStatsAllQueue;
		// return queueList;
	
	}

	@SuppressWarnings("null")
	public LiveACDSStatsAllQueue_Part2 getPerformanceByQueuePart2(HttpServletRequest request, String qid) {
		// TODO Auto-generated method stub

		log.debug("i am in getAgentPerformanceByQueue SERVICE ");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String today = LocalDate.now().format(formatter);

		// final String url =
		// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/acd/306@compassoffices.ak1.cloudpbx.net.nz/stat?date=20200902&days=1";
		LiveACDSStatsAllQueue_Part2 agents_live_stats_QWise_part2 = null;
		try {
			String dn = (String) request.getSession().getAttribute("dn");
			String url_prop = URLConfig.getURL("GET_QUEUEWISE_AND_AGENT_STATS_DASHBOARD_NEW");
			String GET_QUEUEWISE_AND_AGENT_STATS_DASHBOARD_NEW = MessageFormat.format(url_prop, dn, qid, dn, today);
			log.debug(
					"GET_QUEUEWISE_AND_AGENT_STATS_DASHBOARD_NEW URL  -" + GET_QUEUEWISE_AND_AGENT_STATS_DASHBOARD_NEW);

			String token = (String) request.getSession().getAttribute("token");

			log.debug("TOKEN AFTER LOGIN -" + token);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("Cookie", "session=" + token);

			HttpEntity requestEntity = new HttpEntity(null, requestHeaders);
			ResponseEntity<String> response = restTemplate.exchange(GET_QUEUEWISE_AND_AGENT_STATS_DASHBOARD_NEW,
					HttpMethod.GET, requestEntity, String.class);
			String stats_by_queue = response.getBody();

			JSONObject jsonObject_root = (JSONObject) JSONValue.parse(stats_by_queue);

			agents_live_stats_QWise_part2 = new Gson().fromJson(jsonObject_root.toString(),
					LiveACDSStatsAllQueue_Part2.class);
			log.debug("returning from  agents_live_stats_QWise_part2 END"
					+ agents_live_stats_QWise_part2.getHangup_ringing() + "------"
					+ agents_live_stats_QWise_part2.getHangup_waiting() + "-----"
					+ agents_live_stats_QWise_part2.getOther());

		}

		catch (NullPointerException e) {
			log.error("Warning: Nullpointer" + e);
		} catch (Exception e) {
			log.error("Warning: Some Other exception" + e);
		}
		return agents_live_stats_QWise_part2;
		// return queueList;

	}
	
	public AgentsLiveStatsQWise getAgentPerformanceByQueue(HttpServletRequest request, String qid) {
		// TODO Auto-generated method stub

		log.debug("i am in getAgentPerformanceByQueue SERVICE ");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String today = LocalDate.now().format(formatter);
		
		Agent[] agents_live_stats_QWise_part2 = null;
		List<Agent> l = new ArrayList<Agent>();
		AgentsLiveStatsQWise alsqw = new AgentsLiveStatsQWise();

		// final String url =
		// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/acd/306@compassoffices.ak1.cloudpbx.net.nz/stat?date=20200902&days=1";
		
		try {
			String dn = (String) request.getSession().getAttribute("dn");
			String url_prop = URLConfig.getURL("GET_QUEUEWISE_AND_AGENT_STATS_DASHBOARD_NEW");
			String GET_QUEUEWISE_AND_AGENT_STATS_DASHBOARD_NEW = MessageFormat.format(url_prop, dn, qid, dn, today);
			log.debug(
					"GET_QUEUEWISE_AND_AGENT_STATS_DASHBOARD_NEW URL  -" + GET_QUEUEWISE_AND_AGENT_STATS_DASHBOARD_NEW);

			String token = (String) request.getSession().getAttribute("token");

			log.debug("TOKEN AFTER LOGIN -" + token);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("Cookie", "session=" + token);

			HttpEntity requestEntity = new HttpEntity(null, requestHeaders);
			ResponseEntity<String> response = restTemplate.exchange(GET_QUEUEWISE_AND_AGENT_STATS_DASHBOARD_NEW,
					HttpMethod.GET, requestEntity, String.class);
			String stats_by_queue = response.getBody();

			JSONObject jsonObject_root = (JSONObject) JSONValue.parse(stats_by_queue);
			JSONArray jsonObject_agents  = (JSONArray) jsonObject_root.get("agents");

			agents_live_stats_QWise_part2 = new Gson().fromJson(jsonObject_agents.toString(), Agent[].class);
			l = Arrays.asList(agents_live_stats_QWise_part2);
			/*
			 * // updaing start date for (int i = 0; i < l.size(); i++) {
			 * l.get(i).setIvr(Integer.parseInt(Utility.getConvertedTimeInHrsMinSec(l.get(i)
			 * .getIvr())));
			 * l.get(i).setTalk(Integer.parseInt(Utility.getConvertedTimeInHrsMinSec(l.get(i
			 * ).getTalk()))); l.get(i).getIdle().setDuration((Integer.parseInt(Utility.
			 * getConvertedTimeInHrsMinSec(l.get(i).getIdle().getDuration()))));
			 * 
			 * }
			 */
			alsqw.setAgents(l);
			alsqw.setQid(qid);
			log.debug("returning from  agents_live_stats_QWise_part2 END");

		}

		catch (NullPointerException e) {
			log.error("Warning: Nullpointer" + e);
		} catch (Exception e) {
			log.error("Warning: Some Other exception" + e);
		}
		return alsqw;
		// return queueList;

	}
	
	@SuppressWarnings("null")
	public List<AgentsLiveStatsQWise> getAgentPerformanceComplete(HttpServletRequest request){
		
		log.debug("calling getACDForAllQueue Start");
		
		List<ACD_Queue_List> acd_queue_list = qs.getAllQueuesName(request);
		List<AgentsLiveStatsQWise> agent_stats_complete_queue_list = new ArrayList<AgentsLiveStatsQWise>();
		List<Object> list_of_agent_list = new ArrayList<Object>();
		
		for(int i=0; i < acd_queue_list.size(); i++) 
        {
			log.debug("getting queue details of +++"+acd_queue_list.get(i).getName());
			agent_stats_complete_queue_list.add(i,getAgentPerformanceByQueue(request, acd_queue_list.get(i).getName()));
			//getACDByQueue(auth_token, acd_queue_list.get(i).getName());
        }
		
		
		log.debug("calling getACDForAllQueue END");
		return agent_stats_complete_queue_list;
		
	}
	
	@SuppressWarnings("null")
	public List<LiveACDSStatsAllQueue_Part2> getACDStatsForAllQueuePart2(HttpServletRequest request){
		
		log.debug("calling getACDForAllQueue Start");
		
		List<ACD_Queue_List> acd_queue_list = qs.getAllQueuesName(request);
		List<LiveACDSStatsAllQueue_Part2> agents_live_stats_Q_part2 = new ArrayList<LiveACDSStatsAllQueue_Part2>();
		List<Object> list_of_agent_list = new ArrayList<Object>();
		
		for(int i=0; i < acd_queue_list.size(); i++) 
        {
			log.debug("getting queue details of +++"+acd_queue_list.get(i).getName());
			agents_live_stats_Q_part2.add(i,getPerformanceByQueuePart2(request, acd_queue_list.get(i).getName()));
			//getACDByQueue(auth_token, acd_queue_list.get(i).getName());
        }
		
		log.debug("setting up agents info into session");
		request.getSession().setAttribute("LiveACDSStatsAllQueue_Part2", agents_live_stats_Q_part2);
		
		log.debug("calling LiveACDSStatsAllQueue_Part2 END");
		return agents_live_stats_Q_part2;
		
	}
	

}
