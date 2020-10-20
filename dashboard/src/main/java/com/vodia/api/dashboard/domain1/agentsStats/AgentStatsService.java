package com.vodia.api.dashboard.domain1.agentsStats;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

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
import com.vodia.api.dashboard.domain1.queue.ACD_Queue_List;
import com.vodia.api.dashboard.domain1.queue.ACD_WallBoard_QueueWise;
import com.vodia.api.dashboard.domain1.queue.AGENTS;
import com.vodia.api.dashboard.domain1.queue.QueuePerformanceTable;
import com.vodia.api.dashboard.domain1.queue.QueueService;
import com.vodia.api.dashboard.domain1.queue.qperformanceTableNew.Agent;
import com.vodia.api.dashboard.domain1.queue.qperformanceTableNew.LiveACDSStatsAllQueue;
import com.vodia.api.dashboard.domain1.queue.qperformanceTableNew.QPerformanceTableDashboardNewService;

@Service
public class AgentStatsService {
	
	@Autowired
	private QPerformanceTableDashboardNewService qptdn;
	@Autowired
	private QueueService qs;

	private static final Logger log = LoggerFactory.getLogger(AgentStatsService.class);

	

	public List<ExtentionSetting> getAllAgentSetting(HttpServletRequest request) {
		log.debug("i am in getAllQueuesName SERVICE ");

		// final String url =
		// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/domain/compassoffices.ak1.cloudpbx.net.nz/acds";
		List<ExtentionSetting> all_agent_setting = null;
		try {
			String dn = (String) request.getSession().getAttribute("dn");
			String url_prop = URLConfig.getURL("GET_ALL_EXTENTIONS");
			String GET_ALL_EXTENTIONS = MessageFormat.format(url_prop, dn, dn);
			log.debug("GET_ALL_EXTENTIONS URL  -" + GET_ALL_EXTENTIONS);

			// "https://compassoffices.ak1.cloudpbx.net.nz:8082/rest/user/200@compassoffices.ak1.cloudpbx.net.nz/accounts?type=extensions";
			String token = (String) request.getSession().getAttribute("token");

			log.debug("TOKEN AFTER LOGIN -" + token);
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.add("Cookie", "session=" + token);

			HttpEntity requestEntity = new HttpEntity(null, requestHeaders);

			ResponseEntity<ExtentionSetting[]> response = restTemplate.exchange(GET_ALL_EXTENTIONS, HttpMethod.GET,
					requestEntity, ExtentionSetting[].class);
			all_agent_setting = Arrays.asList(response.getBody());
			log.debug("retrurning acd_cdr_list" + all_agent_setting.size());

		}

		catch (NullPointerException e) {
			log.error("Warning: Nullpointer" + e);
		} catch (Exception e) {
			log.error("Warning: Some Other exception"+e);
		}
		return all_agent_setting;
		// return queueList;
	}
	
	

	public AgentStatsToDashboard agentStatsToDashboard(HttpServletRequest request) {

		log.debug("calling agentStatsToDashboard Start");

		Integer TotalNumberOfAgents;
		Integer Agent_Ready;
		Integer Agent_NotReady;
		Integer Agent_Working = 0;
		Integer Agent_Talking = 0;
		AgentStatsToDashboard agent_Stats = new AgentStatsToDashboard();
		List<String> total_agents_in_queue = new ArrayList<String>();

		//(int) request.getSession().getAttribute("totalAgents");
		//List<ACD_Queue_List> acd_queue_list = qs.getAllQueuesName(request);
		List<LiveACDSStatsAllQueue> liveACDSStatsAllQueue_list = qptdn.getLiveACDSStatsAllQueue(request);
		
		//Java8 flatMap lambda
		
		total_agents_in_queue = liveACDSStatsAllQueue_list.stream()
        .map(LiveACDSStatsAllQueue::getAgents)
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
		
		Agent_Talking = liveACDSStatsAllQueue_list.stream().map(c-> c.getCalls()).collect(Collectors.summingInt(Integer::intValue));
		
		log.debug("Agent_Talking----"+Agent_Talking);
		
//		for (int i = 0; i < liveACDSStatsAllQueue_list.size(); i++) {
//			total_agents_in_queue.addAll(i, liveACDSStatsAllQueue_list.get(i).getAgents());
//			//Agent_Working +=liveACDSStatsAllQueue_list.get(i).getAgents().size();
//			Agent_Talking += liveACDSStatsAllQueue_list.get(i).getCalls();
//			
//		}
		List<String> distinctElements = total_agents_in_queue.stream()
                .distinct()
                .collect(Collectors.toList());
		

		int totalAgents = distinctElements.size();
		Agent_Working = totalAgents;
		
		log.debug("Total_login=Agent_Working--"+totalAgents);

		/*
		 * log.debug(
		 * "getting AgentStatsToDashboard from list_acd_queue_wallboard for Agent Stats data from queue acd Session"
		 * ); List<ACD_WallBoard_QueueWise> list_acd_queue_wallboard =
		 * (List<ACD_WallBoard_QueueWise>) request.getSession()
		 * .getAttribute("list_acd_queue_wallboard");
		 * 
		 * log.debug("checking if list_acd_queue_wallboard is in Session"); if
		 * (list_acd_queue_wallboard != null && list_acd_queue_wallboard.size() > 0) {
		 * log.debug("getting getQueuePerformanceTable is in Session and size "
		 * +list_acd_queue_wallboard.size()); List<List<AGENTS>>
		 * list_agents_per_acd_queue = list_acd_queue_wallboard.stream().map(c ->
		 * c.getAgents()) .collect(Collectors.toList()); int total_login_agents = 0; int
		 * total_talking_agents = 0; List<String> total_agents_List=null; for(int i
		 * =0;i<list_agents_per_acd_queue.size();i++) { total_login_agents +=
		 * list_agents_per_acd_queue.get(i).stream().map(c->c.getLogin()).count();
		 * total_talking_agents +=
		 * list_agents_per_acd_queue.get(i).stream().map(c->c.getDnd()).count();
		 * 
		 * }
		 */
			log.debug("Total_login=Agent_Working--"+Agent_Working);
			/*
			 * totalAgents = list_agents_per_acd_queue.get(0).stream().map(a ->
			 * a.getAccount()) .collect(Collectors.toList()).stream()
			 * .collect(Collectors.groupingBy(Function.identity(),
			 * Collectors.counting())).keySet().size();
			 */
			//Agent_Working = (int) list_agents_per_acd_queue.get(0).stream().filter(a -> a.getLogin()).count();
		
			TotalNumberOfAgents = totalAgents;// all_agent_setting.size();
			Agent_Ready = Agent_Working - Agent_Talking;
			Agent_NotReady = TotalNumberOfAgents - Agent_Working;
			agent_Stats.setTotalNumberOfAgents(TotalNumberOfAgents);
			agent_Stats.setAgent_Talking(Agent_Talking);
			agent_Stats.setAgent_Working(Agent_Working);
			agent_Stats.setAgent_NotReady(Agent_NotReady);
			agent_Stats.setAgent_Ready(Agent_Ready);
		

		log.debug("calling agent_Stats END");
		return agent_Stats;

	}

	public int getAgentsLoggedIn(List<ExtentionSetting> agents) {

		int loggedInAgents = 0;

		List<ExtentionSetting> ag = agents;

		long count = ag.stream().filter(c -> c.getLogin()).count();

		return loggedInAgents = (int) count;
	}

	public int getAgentsTalkingn(List<ExtentionSetting> agents) {

		int connectedAgents = 0;

		List<ExtentionSetting> ag = agents;

		long count = ag.stream().filter(c -> c.getChatstatus().equalsIgnoreCase("online")).count();

		return connectedAgents = (int) count;
	}

	public int getAgentsWorking(List<ExtentionSetting> agents) {

		int agentsWorking = 0;

		List<ExtentionSetting> ag = agents;

		long count = ag.stream().filter(c -> c.getLogin()).count();

		return agentsWorking = (int) count;
	}

}